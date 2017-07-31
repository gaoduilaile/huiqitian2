package yunju.com.huiqitian.vm.shop.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.common.util.ToastUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.GoodsInfo;
import yunju.com.huiqitian.http.entity.SearchResp;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.vm.adapter.SalesAdapter;
import yunju.com.huiqitian.vm.details.view.DetailsActivity;
import yunju.com.huiqitian.vm.find.view.FindResultActivity;
import yunju.com.huiqitian.vm.menu.model.MenuModel;
import yunju.com.huiqitian.vm.shop.model.ShopGoodsModel;
import yunju.com.huiqitian.vm.widget.MyGridView;
import yunju.com.huiqitian.vm.widget.PullToRefreshLayout;

public class ShopGoodsActivity extends BaseActivity implements ShopGoodsModel.ShopGoodsInterface, PullToRefreshLayout.OnRefreshListener {

    /*返回键*/
    private LinearLayout lyShopGoodsBack;//返回键
    private LinearLayout tvShopGoodsSearch;//搜索键
    private EditText etShopGoodsFind;//输入框

    /*控件*/
    private com.joooonho.SelectableRoundedImageView ivShopGoodsHead;//商店头像
    private TextView tvShopName;//店铺名称
    private TextView tvShopDistanceMoney;//距离加钱

    /*商品分类显示*/
    private MyGridView gvShopHotGoods;//热卖商品
    private MyGridView gvShopNewGoods;//最新上线商品
    private MyGridView gvShopSuggestGoods;//推荐商品
    private TextView tvShopHotGoods;
    private TextView tvShopNewGoods;
    private TextView tvShopSuggestGoods;

    /*超市的market id*/
    private int marketId;

    /*数据的model*/
    private ShopGoodsModel shopGoodsModel;
    private MenuModel menuModel;

    /*显示列表*/
    private PullToRefreshLayout pullToRefreshLayout;
    private int num = 10;

    public static ShopGoodsActivity shopGoodsActivity = null;

    private int saveMarketId;//超市id
    private int saveCurpage;//当前页数
    private int saveRp;//每页多少条
    private String saveSortname;//排序字段
    private String saveSortorder;//排序方式


    /*三种商品的集合*/
    private List<GoodsInfo> hotGoodsInfos = new ArrayList<GoodsInfo>();
    private List<GoodsInfo> newGoodsInfos = new ArrayList<GoodsInfo>();
    private List<GoodsInfo> commendGoodsInfos = new ArrayList<GoodsInfo>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_shop_goods);
        shopGoodsActivity = this;
    }

    @Override
    public void initBoot() {
        shopGoodsModel = new ShopGoodsModel(activity);
        menuModel = new MenuModel(activity);
    }

    @Override
    public void initViews() {
        PullToRefreshLayout menuHome = (PullToRefreshLayout) findViewById(R.id.al_menu_home);
        menuHome.setOnRefreshListener(this);

        /*title*/
        lyShopGoodsBack = findView(R.id.ly_shop_goods_back);
        tvShopGoodsSearch = findView(R.id.tv_shop_goods_search);
        etShopGoodsFind = findView(R.id.et_shop_goods_find);

        /*控件*/
        ivShopGoodsHead = findView(R.id.iv_shop_goods_head);
        tvShopName = findView(R.id.tv_shop_name);
        tvShopDistanceMoney = findView(R.id.tv_shop_distance_money);

        /*分类显示*/
        gvShopHotGoods = findView(R.id.gv_shop_hot_goods);
        gvShopNewGoods = findView(R.id.gv_shop_new_goods);
        gvShopSuggestGoods = findView(R.id.gv_shop_suggest_goods);
        tvShopHotGoods = findView(R.id.tv_shop_hot_goods);
        tvShopNewGoods = findView(R.id.tv_shop_new_goods);
        tvShopSuggestGoods = findView(R.id.tv_shop_suggest_goods);

        pullToRefreshLayout = findView(R.id.pr_address);
    }

    @Override
    public void initData(Bundle bundle) {
        marketId = bundle.getInt(Constant.SHOP_MARKET_ID);
        tvShopName.setText(bundle.getString(Constant.SHOP_NAME));
        tvShopDistanceMoney.setText(bundle.getString(Constant.SHOP_DISTANCE) + "米 / 配送费￥" + bundle.getString(Constant.SHOP_SEND_MONEY));

        shopGoodsModel.searchGoodsHot(marketId, 1, 10, "qty", "asc");
        saveReq(marketId, 1, 10, "qty", "asc");
        shopGoodsModel.recommendGoods(marketId, 1, 10, "qty", "asc");
    }

    @Override
    public void initEvents() {

        /*返回键*/
        lyShopGoodsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*搜索键*/
        tvShopGoodsSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*得到输入框的内容*/
                String editData = etShopGoodsFind.getText().toString().trim();
                if (!TextUtils.isEmpty(editData)) {
                    Bundle bundle = new Bundle();
                    /*输入框内容*/
                    bundle.putString("editData", editData);
                    bundle.putInt("marketId", marketId);
                    /*2代表着从附近超市搜索进入的*/
                    bundle.putInt("tag", 2);
                    startActivity(FindResultActivity.class, bundle);

                } else {
                    ToastUtils.show(getApplication(), "请输入搜索商品的名称");
                }

            }
        });

        /*热门商品*/
        gvShopHotGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                start(position, hotGoodsInfos);
            }
        });

        /*最新上架*/
        gvShopNewGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                start(position, newGoodsInfos);
            }
        });

        /*推荐商品*/
        gvShopSuggestGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                start(position, commendGoodsInfos);
            }
        });

        /*获取不到当前位置，从新调取经纬度接口重新加载*/
        menuModel.setLocateInterface(new MenuModel.LocateInterface() {
            @Override
            public void LocateSuccess(String mes) {
                shopGoodsModel.searchGoodsHot(saveMarketId, saveCurpage, saveRp, saveSortname, saveSortorder);
            }
            @Override
            public void LocateFailure(String mes) {

            }
        });

    }

    @Override
    public void hotGoods(SearchResp searchResp) {
        hotGoodsInfos = searchResp.getObj();
        gvShopHotGoods.setAdapter(new SalesAdapter(activity, hotGoodsInfos));
        tvShopHotGoods.setVisibility(View.VISIBLE);
    }

    @Override
    public void newGoods(SearchResp searchResp) {
        newGoodsInfos = searchResp.getObj();
        gvShopNewGoods.setAdapter(new SalesAdapter(activity, newGoodsInfos));
        tvShopNewGoods.setVisibility(View.VISIBLE);
    }

    @Override
    public void commendGoods(SearchResp searchResp) {
        commendGoodsInfos = searchResp.getObj();
        gvShopSuggestGoods.setAdapter(new SalesAdapter(activity, commendGoodsInfos));
        /*if (hasAnimation) {
            pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
        }
        num = num + 6;*/
        tvShopSuggestGoods.setVisibility(View.VISIBLE);
    }

    @Override
    public void LocateError(String mes) {
        menuModel.buyerLocate(Double.parseDouble(MyUtils.getLog()), Double.parseDouble(MyUtils.getLag()));
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        MyUtils.setRefresh(pullToRefreshLayout);

    }

    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
        num = num + 10;
        shopGoodsModel.recommendGoods(marketId, 1, num, "qty", "asc");
        shopGoodsModel.searchGoodsHot(marketId, 1, num, "qty", "asc");
        saveReq(marketId, 1, 10, "qty", "asc");

        // 加载超时操作
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 2000);
    }

    /**
     * 页面跳转
     */
    public void start(int position, List<GoodsInfo> goodsInfo) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.START_NEXT_KEY, Constant.START_BY_HOME);
        bundle.putInt(Constant.ITEM_POSITION, position);
        bundle.putSerializable(Constant.GOODS_PROP, (Serializable) goodsInfo);
        startActivity(DetailsActivity.class, bundle);
    }

    /**
     * 保存之前调用接口参数
     */
    public void saveReq(int marketId, int curpage, int rp, String storname, String sortorder) {
        saveMarketId = marketId;
        saveCurpage = curpage;
        saveRp = rp;
        saveSortname = storname;
        saveSortorder = sortorder;
    }
}
