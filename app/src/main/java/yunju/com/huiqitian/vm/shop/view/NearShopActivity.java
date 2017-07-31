package yunju.com.huiqitian.vm.shop.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.ShopInfo;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.vm.adapter.ShopAdapter;
import yunju.com.huiqitian.vm.menu.model.MenuModel;
import yunju.com.huiqitian.vm.shop.model.ShopModel;

public class NearShopActivity extends BaseActivity implements ShopModel.ShopInterface {

    /*头布局*/
    private LinearLayout ivShopBack;//返回键
    private TextView tvShopTitle;//标题
    private RelativeLayout ivShopFind;//搜索

    /*筛选*/
    private LinearLayout lyShopNum;//销量
    private LinearLayout lyShopSend;//配送
    private LinearLayout lyShopEvaluate;//评价
    private LinearLayout lyShopMoney;//销量

    /*图片控件*/
    private ImageView imgShopNum;
    private ImageView imgShopSend;
    private ImageView imgShopEvaluate;
    private ImageView imgShopMoney;

    /*点击排序的标示*/
    private boolean isUpSales = false;
    private boolean isUpCount = true;
    private boolean isUpPrice = true;
    private boolean isUpScreen = true;

    /*刷新控件*/
//    private PullToRefreshLayout pullToRefreshLayout;
//    private PullableListView lstShop;
    private ListView lstShop;

    /*数据的model*/
    private ShopModel shopModel;
    private MenuModel menuModel;

    /*展示数据*/
    private ShopAdapter shopAdapter;

    /*搜索到的商品*/
    private List<ShopInfo> shops;
    private TextView tvShopNum;
    private TextView tvShopSend;
    private TextView tvShopEvaluate;
    private TextView tvShopMoney;
    private FrameLayout flNoNearShop;

    public static NearShopActivity instance = null;

    private String sortname;//排序字段
    private String sortorder;//排序方式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_near_shop);
        instance=this;
    }

    @Override
    public void initBoot() {
        shopModel = new ShopModel(activity);
        menuModel=new MenuModel(activity);
    }

    @Override
    public void initViews() {
        flNoNearShop = findView(R.id.fl_no_near_shop);
        /*标题*/
        ivShopBack = findView(R.id.iv_shop_back);
        tvShopTitle = findView(R.id.tv_shop_title);
        ivShopFind = findView(R.id.iv_shop_find);

        /*筛选按钮*/
        lyShopNum = findView(R.id.ly_shop_num);
        lyShopSend = findView(R.id.ly_shop_send);
        lyShopEvaluate = findView(R.id.ly_shop_evaluate);
        lyShopMoney = findView(R.id.ly_shop_money);

        /*图片控件*/
        imgShopNum = findView(R.id.img_shop_num);
        imgShopSend = findView(R.id.img_shop_send);
        imgShopEvaluate = findView(R.id.img_shop_evaluate);
        imgShopMoney = findView(R.id.img_shop_money);

        /*图片旁的文字控件*/
        tvShopNum = findView(R.id.tv_shop_num);//销量

        tvShopSend = findView(R.id.tv_shop_send);//配送
        tvShopEvaluate = findView(R.id.tv_shop_evaluate);//评价
        tvShopMoney = findView(R.id.tv_shop_money);//起送


        /*刷新控件*/
//        pullToRefreshLayout = findView(R.id.pr_shop);
        lstShop = findView(R.id.lst_shop);

        /*刚进来的时候默认祖选择的是第一个页面*/
        imgShopNum.setImageResource(R.mipmap.img_find_red_down); //展开
        tvShopNum.setTextColor(NearShopActivity.this.getResources().getColor(R.color.color_e51c23));
    }

    @Override
    public void initData(Bundle bundle) {
        tvShopTitle.setText(R.string.shop_title);
        shopModel.searchShop("qty", "desc");
        sortname="qty";
        sortorder="desc";

    }

    @Override
    public void initEvents() {

        /*返回键*/
        ivShopBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*搜索*/
        ivShopFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /*销量*/
        lyShopNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUpSales) {
                    /*设置其他图片都是灰色的向下，文字是黑色的*/
                    selectImg();
                    imgShopNum.setImageResource(R.mipmap.img_find_red_down); //展开
                    shopModel.searchShop("qty", "desc");
                    sortname = "qty";
                    sortorder = "desc";
                } else {
                    selectImg();
                    shopModel.searchShop("qty", "asc");
                    sortname = "qty";
                    sortorder = "asc";
                    imgShopNum.setImageResource(R.mipmap.img_find_red_up);
                }
                isUpSales = !isUpSales;
                /*防止导航栏图片二次点击切换*/
                isUpCount = true;
                isUpPrice = true;
                isUpScreen = true;
                tvShopNum.setTextColor(NearShopActivity.this.getResources().getColor(R.color.color_e51c23));

            }
        });

        /*配送*/
        lyShopSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUpCount) {
                    /*设置其他图片都是灰色的向下*/
                    selectImg();
                    imgShopSend.setImageResource(R.mipmap.img_find_red_down); //升序

                    /*按照XXX升序请求网络*/
                    shopModel.searchShop("distance", "asc");
                    sortname = "distance";
                    sortorder = "asc";
                } else {
                    selectImg();
                     /*按照XXX升序降落网络*/
                    shopModel.searchShop("distance", "desc");
                    sortname = "distance";
                    sortorder = "desc";
                    imgShopSend.setImageResource(R.mipmap.img_find_red_up);

                }
                tvShopSend.setTextColor(NearShopActivity.this.getResources().getColor(R.color.color_e51c23));
                isUpCount = !isUpCount;
                isUpSales = true;
                isUpScreen = true;
                isUpPrice = true;

            }
        });

        /*评价*/
        lyShopEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUpPrice) {
                    /*设置其他图片都是灰色的向下*/
                    selectImg();
                    imgShopEvaluate.setImageResource(R.mipmap.img_find_red_down); //升序
                    /*按照升序的请求网络*/
                    shopModel.searchShop("score", "asc");
                    sortname = "score";
                    sortorder = "asc";
                } else {
                    selectImg();
                    shopModel.searchShop("score", "desc");
                    sortname = "score";
                    sortorder = "desc";
                    imgShopEvaluate.setImageResource(R.mipmap.img_find_red_up);
                }
                tvShopEvaluate.setTextColor(NearShopActivity.this.getResources().getColor(R.color.color_e51c23));
                isUpPrice = !isUpPrice;
                isUpCount = true;
                isUpSales = true;
                isUpScreen = true;

            }
        });

        /*起送*/
        lyShopMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUpScreen) {
                    /*设置其他图片都是灰色的向下*/
                    selectImg();
                    imgShopMoney.setImageResource(R.mipmap.img_find_red_down); //升序

                    shopModel.searchShop("dispatchFee", "asc");
                    sortname = "dispatchFee";
                    sortorder = "asc";
                } else {
                    selectImg();
                    shopModel.searchShop("dispatchFee", "desc");
                    sortname = "dispatchFee";
                    sortorder = "desc";
                    imgShopMoney.setImageResource(R.mipmap.img_find_red_up);
                }
                tvShopMoney.setTextColor(NearShopActivity.this.getResources().getColor(R.color.color_e51c23));
                isUpScreen = !isUpScreen;
                isUpPrice = true;
                isUpCount = true;
                isUpSales = true;


            }
        });

        /*lst的点击事件*/
        lstShop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.SHOP_NAME, shops.get(position).getName());
                bundle.putString(Constant.SHOP_DISTANCE, shops.get(position).getDistance() + "");
                bundle.putString(Constant.SHOP_SEND_MONEY, shops.get(position).getDispatchFee() + "");
                bundle.putInt(Constant.SHOP_MARKET_ID, shops.get(position).getId());
                startActivity(ShopGoodsActivity.class, bundle);
            }
        });

        /*获取不到当前位置，从新调取经纬度接口重新加载*/
        menuModel.setLocateInterface(new MenuModel.LocateInterface() {
            @Override
            public void LocateSuccess(String mes) {
                shopModel.searchShop(sortname, sortorder);
            }

            @Override
            public void LocateFailure(String mes) {

            }
        });
    }


    /**
     * 设置图片位向下，灰色
     */
    private void selectImg() {
        imgShopNum.setImageResource(R.mipmap.img_find_default_up);
        imgShopSend.setImageResource(R.mipmap.img_find_default_up);
        imgShopEvaluate.setImageResource(R.mipmap.img_find_default_up);
        imgShopMoney.setImageResource(R.mipmap.img_find_default_up);

        /*设置文字的颜色*/
        tvShopNum.setTextColor(NearShopActivity.this.getResources().getColor(R.color.color_333333));
        tvShopSend.setTextColor(NearShopActivity.this.getResources().getColor(R.color.color_333333));
        tvShopEvaluate.setTextColor(NearShopActivity.this.getResources().getColor(R.color.color_333333));
        tvShopMoney.setTextColor(NearShopActivity.this.getResources().getColor(R.color.color_333333));

    }

    @Override
    public void hasShop(List<ShopInfo> shopInfos) {
        shops = new ArrayList<ShopInfo>();
        shops = shopInfos;
        if (shops==null||shops.size()==0){
            lstShop.setVisibility(View.GONE);
            flNoNearShop.setVisibility(View.VISIBLE);

        }else {
            lstShop.setVisibility(View.VISIBLE);
            flNoNearShop.setVisibility(View.GONE);
        }
        shopAdapter = new ShopAdapter(activity, shops);
        lstShop.setAdapter(shopAdapter);
    }

    @Override
    public void noShopMarket(String mes) {
        flNoNearShop.setVisibility(View.VISIBLE);
    }

    /*或许不到当前经纬度*/
    @Override
    public void LocateError(String mes) {
        menuModel.buyerLocate(Double.parseDouble(MyUtils.getLog()), Double.parseDouble(MyUtils.getLag()));
    }
}
