package yunju.com.huiqitian.vm.details.view;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.AppApplication;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.db.dao.CollectDao;
import yunju.com.huiqitian.db.dao.HistoryDao;
import yunju.com.huiqitian.db.entity.Collect;
import yunju.com.huiqitian.db.entity.History;
import yunju.com.huiqitian.entity.CartGoods;
import yunju.com.huiqitian.entity.CartMarket;
import yunju.com.huiqitian.entity.GoodsInfo;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.entity.CartSettleResp;
import yunju.com.huiqitian.http.entity.ClassifyGoodsReq;
import yunju.com.huiqitian.http.entity.GoodEvalResp;
import yunju.com.huiqitian.http.entity.LookCartResp;
import yunju.com.huiqitian.http.entity.PropResp;
import yunju.com.huiqitian.http.entity.SearchResp;
import yunju.com.huiqitian.utils.ImageOptions;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.vm.adapter.EvalImageAdapter;
import yunju.com.huiqitian.vm.adapter.SimilarGoodsAdapter;
import yunju.com.huiqitian.vm.classify.view.ClassifyResultActivity;
import yunju.com.huiqitian.vm.details.model.DetailsModel;
import yunju.com.huiqitian.vm.find.view.FoodstuffActivity;
import yunju.com.huiqitian.vm.history.view.HistoryActivity;
import yunju.com.huiqitian.vm.find.view.FindActivity;
import yunju.com.huiqitian.vm.find.view.FindResultActivity;
import yunju.com.huiqitian.vm.login.view.LoginActivity;
import yunju.com.huiqitian.vm.orders.view.AffirmOrderActivity;
import yunju.com.huiqitian.vm.shop.view.NearShopActivity;
import yunju.com.huiqitian.vm.shop.view.ShopGoodsActivity;
import yunju.com.huiqitian.vm.vouchers.view.VoucherActivity;
import yunju.com.huiqitian.vm.vouchers.view.VoucherGoodsActivity;
import yunju.com.huiqitian.vm.widget.CircleBitmapDisplayer;
import yunju.com.huiqitian.vm.widget.MyGridView;
import yunju.com.huiqitian.vm.widget.PullToRefreshLayout;
import yunju.com.huiqitian.vm.widget.PullableScrollView;
import yunju.com.huiqitian.vm.widget.TagCloudView;

public class DetailsActivity extends BaseActivity implements PullToRefreshLayout.OnRefreshListener, DetailsModel.GoodsPropInterface {


    /*推荐商品的数量*/
    private int num = 4;
    /*收藏显示图片标签*/
    private int collectPosition;//收藏的位置第几条收藏数据
    private int showType = 0;
    private int type = 0;
    private int historyType = 1;
    /*商品数量*/
    private int number;
    /*商品属性数据*/
    private PropResp propResps;
    /*立即购买数据*/
    private CartSettleResp cartSettleResp;
    /*title*/
    private LinearLayout ivTitleBack;//返回键
    private TextView tvTitle;//标题
    private ImageView ivTitleShare;//分享

    private PullToRefreshLayout pullToRefreshLayout;
    /*底部控件*/
    private LinearLayout llCollectDetails;//收藏
    private TextView tvShoppingCatDetails;//购物车
    private TextView tvAddShoppingDetails;//加入购物车
    private TextView tvBuyDetails;//立即购买
    private ImageView ivCollectDetails;//收藏图片

    /*商品属性的点击布局*/
    private RelativeLayout lyGoodsParticular;
    /*商品保质期*/
    private TextView tvExpirationData;//商品保质期
    /*保质期时间*/
    private TextView tvTimeDetails;//保质期时间
    /*购物车数量*/
    private TextView tvCountDetails;
    /*配送说明*/
    private TextView tvExplainTypeDetails;

    /*显示部分*/
    private ImageView imgGoodsDetails;//图像
    private TextView tvNowPriceDetails;//价格
    private TextView tvOldPriceDetails;//市场价
    private TextView tvShopNameDetails;//商品名称
    private TextView tvAddressDetails;//地址

    private View viewLine;//价格下面的那一条线

    /*显示推荐商品*/
    private MyGridView gvSimilarGoodsDetails;

    /*更多商品详情*/
    private ImageView ivDetailMoreMsg;

    /*图片加载*/
    private ImageLoader imageLoader;

    /*数据的model*/
    private DetailsModel detailsModel;

    /*位置*/
    private int position;
    /*商品*/
    private Map<String, Object> map = new HashMap<String, Object>();
    private List<GoodsInfo> goodsInfos = new ArrayList<>();
    /*当前商品*/
    private GoodsInfo goodsInfo;

    /*数据库相关做历史记录*/
    private History history;
    private HistoryDao historyDao;
    private List<History> historiesList;

    /*数据库相关的收藏*/
    private Collect collect;
    private CollectDao collectDao;
    private List<Collect> collectList = new ArrayList<>();

    /*购物车相关*/
    private CartMarket cartMarkets;
    private CartGoods cartGoodses;

    /*底部购物车*/
    private RelativeLayout rlShopCart;

    /*查看更多评论*/
    private Button btnCheckAllDetails;

    /*推荐商品的Adapter*/
    private SimilarGoodsAdapter similarGoodsAdapter;

    /*商品评价*/
    private TagCloudView tcvGvaluationNormalTagDetails;//商品评价的云标签
    private List<String> goodTag = new ArrayList<>();//整个标签存放集合
    private List<Integer> total = new ArrayList<>();//整个标签数量集合
    private ImageView imgUserDetails;//评价用户头像
    private TextView tvUserNameDetails;//评价用户昵称
    private TextView tvCommentDetails;//评价内容
    private MyGridView gvCommentImgDetails;//显示评价的图片
    private TextView tvCommentDetaols;
    private SearchResp searchResp;
    /*评价详情*/
    private GoodEvalResp goodEvalResp;
    private PullableScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_details);
    }

    @Override
    public void initBoot() {
        imageLoader = ImageLoader.getInstance();
        detailsModel = new DetailsModel(activity);
        history = new History();
        historyDao = new HistoryDao();

        collect = new Collect();
        collectDao = new CollectDao();

    }

    @Override
    public void initViews() {
        /*title*/
        ivTitleBack = findView(R.id.iv_title_back);
        tvTitle = findView(R.id.tv_title);
        ivTitleShare = findView(R.id.iv_title_share);
        /*底部控件*/
        llCollectDetails = findView(R.id.ll_collect_details);
        tvShoppingCatDetails = findView(R.id.tv_shopping_cat_details);
        tvAddShoppingDetails = findView(R.id.tv_add_shopping_details);
        tvBuyDetails = findView(R.id.tv_buy_details);
        ivCollectDetails = findView(R.id.iv_collect_details);

        lyGoodsParticular = findView(R.id.ly_goods_particular);
        /*商品保质期*/
        tvExpirationData = findView(R.id.tv_expiration_data);
        /*保质期时间*/
        tvTimeDetails = findView(R.id.tv_time_details);
        /*购物车数量*/
        tvCountDetails = findView(R.id.tv_count_details);
        /*配送说明*/
        tvExplainTypeDetails = findView(R.id.tv_explain_type_details);

        /*商品显示*/
        imgGoodsDetails = findView(R.id.img_goods_details);
        tvNowPriceDetails = findView(R.id.tv_now_price_details);
        tvOldPriceDetails = findView(R.id.tv_old_price_details);

        tvShopNameDetails = findView(R.id.tv_shop_name_details);
        tvAddressDetails = findView(R.id.tv_address_details);

        /*显示推荐商品*/
        gvSimilarGoodsDetails = findView(R.id.gv_similar_goods_details);

        /*更多商品信息*/
        ivDetailMoreMsg = findView(R.id.iv_detail_more_msg);

        pullToRefreshLayout = findView(R.id.refresh_view);
        /*底部购物车*/
        rlShopCart = findView(R.id.rl_add_shopping_details);

        /*查看更多评价*/
        btnCheckAllDetails = findView(R.id.btn_check_all_details);
        /*商品评价*/
        tcvGvaluationNormalTagDetails = findView(R.id.tcv_evaluation_normalTag_details);
        imgUserDetails = findView(R.id.img_user_details);//评价人头像
        tvUserNameDetails = findView(R.id.tv_user_name_details);//评价人昵称
        tvCommentDetails = findView(R.id.tv_comment_details);//评价内容
        gvCommentImgDetails = findView(R.id.gv_comment_img_details);//商品评价图片
        tvCommentDetaols = findView(R.id.tv_comment_detaols);

        /*滑动控件*/
        scrollView = findView(R.id.scrollview_details);


    }

    @Override
    public void initData(Bundle bundle) {
        tvTitle.setText("商品详细");//头部标题：商品详细
        ivTitleShare.setVisibility(View.VISIBLE);//分享显示

          /* 焦点*/
        imgGoodsDetails.setFocusable(true);
        imgGoodsDetails.setFocusableInTouchMode(true);

        //取出传过来的position
        position = bundle.getInt(Constant.ITEM_POSITION);

        if (bundle.getInt(Constant.START_NEXT_KEY) == 0) {
            //取出缓存的数据
            goodsInfos = AppApplication.getGoodsInfos();
            dataDisposeOne(goodsInfos, position);
        } else if (bundle.getInt(Constant.START_NEXT_KEY) == 1) {
            goodsInfos = (List<GoodsInfo>) bundle.getSerializable(Constant.GOODS_PROP);
            dataDisposeOne(goodsInfos, position);
        } else if (bundle.getInt(Constant.START_NEXT_KEY) == 2) {
            historiesList = (List<History>) bundle.getSerializable(Constant.GOODS_PROP);
            dataDisposeTwo(historiesList);
        } else if (bundle.getInt(Constant.START_NEXT_KEY) == 4) {
            collectList = (List<Collect>) bundle.getSerializable(Constant.GOODS_PROP);
            dataDisposeThree(collectList);
        } else if (bundle.getInt(Constant.START_NEXT_KEY) == 5) {
            cartMarkets = (CartMarket) bundle.getSerializable(Constant.GOODS_MARKET);
            cartGoodses = (CartGoods) bundle.getSerializable(Constant.GOODS_PROP);
            dataDisposeFour(cartGoodses, cartMarkets);
        } else if (bundle.getInt(Constant.START_NEXT_KEY) == 3) {
            goodsInfos = (List<GoodsInfo>) bundle.getSerializable(Constant.GOODS_PROP);
            dataDisposeOne(goodsInfos, position);
        } else if (bundle.getInt(Constant.START_NEXT_KEY) == 7) {
            /*取出分类条目中的集合和position在上面已经取到*/
            List<ClassifyGoodsReq.ObjBean> list = (List<ClassifyGoodsReq.ObjBean>) bundle.getSerializable(Constant.GOODS_PROP);
            dataDisposeSeven(list, position);
        }else if (bundle.getInt(Constant.START_NEXT_KEY)==8){
            /*取出从进口食品中的集合以及点击的索引值*/
            List<GoodsInfo> goodsInfos = (List<GoodsInfo>) bundle.getSerializable(Constant.GOODS_PROP);
            dataDisposeEight(goodsInfos,position);
        }
        /*LogUtils.error(FindActivity.class, goodsInfos.size() + "" + position + HttpConstant.ROOT_PATH + goodsInfos.get(position).getPicUrl());*/
        //设置数据显示
        imageLoader.displayImage(HttpConstant.ROOT_PATH + map.get("picUrl"), imgGoodsDetails, ImageOptions.getDefaultOptions());//加载图片
        tvNowPriceDetails.setText("¥" + map.get("retailPrice"));
        tvOldPriceDetails.setText("" + map.get("marketPrice"));
        tvShopNameDetails.setText((String) map.get("name"));
        tvAddressDetails.setText((String) map.get("marketName"));
        tvExplainTypeDetails.setText("满" + map.get("freeDispatchLimit") + "元免配送");
        tvOldPriceDetails.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); //中间横线

        /*判断次商品是否为收藏商品*/
        try {
            List<Collect> collects = collectDao.select();
            if (collects != null && collects.size() > 0) {
                for (int i = 0; i < collects.size(); i++) {
                    if (collects.get(i).getId() == (int) map.get("id") & collects.get(i).getType() == (byte) map.get("type")) {
                        collectPosition = i;
                        showType = 1;
                    } else {
                    }
                }
            }
            if (showType == 1) {
                ivCollectDetails.setImageResource(R.mipmap.img_two);
                type = 1;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        detailsModel.goodProp((int) map.get("id"), (byte) map.get("type"));//调获得商品属性接口

        /*获取购物车商品数量*/
        detailsModel.lookShoppingCart();

        detailsModel.GoodsRecommend(1, num, "qty", "desc", (Integer) map.get("marketId"));


        detailsModel.GoodEval((int) map.get("id"), (byte) map.get("type"), 10, 1);
        /*判断是否存在历史记录*/
        try {
            List<History> histories = historyDao.select();
            if (histories != null && histories.size() > 0) {
                for (int i = 0; i < histories.size(); i++) {
                    if (histories.get(i).getId() == (int) map.get("id") & histories.get(i).getType() == (byte) map.get("type")) {
                        historyType = 0;
                    }
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        if (historyType == 1) {
            /*历史记录相关*/
            /*historyDao.addColumn();//增加数据库一列
            historyDao.addColumnTwo();
            historyDao.addColumnThree();*/
            try {
                history.setId((int) map.get("id"));
                history.setGoodsId((int) map.get("id"));
                history.setGoodsName((String) map.get("name"));
                history.setGoodsMoney(map.get("retailPrice") + "");
                history.setImgUrl((String) map.get("picUrl"));
                history.setShopDistance((String) map.get("distance"));
                history.setShopName((String) map.get("marketName"));
                history.setType((byte) map.get("type"));
                history.setMarketPrice(map.get("marketPrice") + "");
                history.setFreeDispatchLimit((String) map.get("freeDispatchLimit"));
                history.setMarketId((Integer) map.get("marketId"));
                historyDao.save(history);
                AppApplication.putBoolean("person_fragment_refresh", true);
            } catch (DbException e) {
                e.printStackTrace();
            }
        }

    }


    /*设置价格中间线的宽度*/
    private void setViewLineWith(int width) {
        //假设MyView是你自定义的view

        LinearLayout myLinear = new LinearLayout(this);

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                width
        );
        myLinear.addView(viewLine, params1);
    }

    @Override
    public void initEvents() {

        /*查看更多评价*/
        btnCheckAllDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goodEvalResp != null && goodEvalResp.getObj().getEvalList().size() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list", goodEvalResp);
                    bundle.putInt("id", (Integer) map.get("id"));
                    bundle.putByte("type", (Byte) map.get("type"));
                    startActivity(EvaluatesActivity.class, bundle);
                }
            }
        });


        /*底部购物车控件点击事件*/
        rlShopCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*关闭之前打开的activity*/
                if (HistoryActivity.historyActivity != null) {
                    HistoryActivity.historyActivity.finish();
                }
                if (VoucherActivity.voucherActivity != null) {
                    VoucherActivity.voucherActivity.finish();
                }
                if (VoucherGoodsActivity.voucherGoodsActivity != null) {
                    VoucherGoodsActivity.voucherGoodsActivity.finish();
                }
                if (NearShopActivity.instance != null) {
                    NearShopActivity.instance.finish();
                }
                if(ShopGoodsActivity.shopGoodsActivity!=null){
                    ShopGoodsActivity.shopGoodsActivity.finish();
                }
                if (ClassifyResultActivity.instance!=null){
                    ClassifyResultActivity.instance.finish();
                }
                if (FindActivity.instance !=null){
                    FindActivity.instance.finish();
                }
                if (FindResultActivity.instance!=null){
                    FindResultActivity.instance.finish();
                }
                if(FoodstuffActivity.foodstuffActivity!=null){
                    FoodstuffActivity.foodstuffActivity.finish();
                }
                AppApplication.putBoolean("home_fragment_refresh", true);
                finish();

            }
        });

        pullToRefreshLayout.setOnRefreshListener(this);
         /*返回键响应事件*/
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*更多商品详情*/
        lyGoodsParticular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, GoodsDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.GOODS_PROP, propResps);
                intent.putExtras(bundle);
                DetailsActivity.this.startActivity(intent);
                /*startActivity(GoodsDetailsActivity.class);*/
            }
        });

        /*添加购物车*/
        tvAddShoppingDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsModel.addCartNumber((Integer) map.get("id"), (Byte) map.get("type"));

            }
        });

        /*添加收藏*/
        llCollectDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*collectDao.addCollectOne();
                collectDao.addCollectTwo();
                collectDao.addCollectThree();*/
                if (type == 0) {
                    ivCollectDetails.setImageResource(R.mipmap.img_two);
                    try {
                        collect.setId((int) map.get("id"));
                        collect.setGoodsId((int) map.get("id"));
                        collect.setGoodsName((String) map.get("name"));
                        collect.setGoodsMoney((String) map.get("retailPrice"));
                        collect.setImgUrl((String) map.get("picUrl"));
                        collect.setShopDistance((String) map.get("distance"));
                        collect.setShopName((String) map.get("marketName"));
                        collect.setType((byte) map.get("type"));
                        collect.setMarketPrice((String) map.get("marketPrice"));
                        collect.setFreeDispatchLimit((String) map.get("freeDispatchLimit"));
                        collect.setMarketId((Integer) map.get("marketId"));
                        collectDao.save(collect);
                        AppApplication.putBoolean("person_fragment_refresh", true);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    type = 1;
                } else if (type == 1) {
                    ivCollectDetails.setImageResource(R.mipmap.img_details_collect);
                    collectDao.deleteOne(collectPosition);
                    type = 0;
                }

            }
        });
        /*立即购买*/
        tvBuyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsModel.newBuy((Integer) map.get("id"), (Byte) map.get("type"));

            }
        });
    }

    /**
     * 页面跳转的数据处理
     */
    /*传递一个List<GoodsInfo>集合所做的出数处理*/
    public void dataDisposeOne(List<GoodsInfo> data, int position) {
        map.put("id", data.get(position).getId());
        map.put("picUrl", data.get(position).getPicUrl());
        map.put("retailPrice", data.get(position).getRetailPrice().toString());
        map.put("marketPrice", data.get(position).getMarketPrice().toString());
        map.put("name", data.get(position).getName());
        map.put("marketName", data.get(position).getMarketName());
        map.put("freeDispatchLimit", data.get(position).getFreeDispatchLimit().toString());
        map.put("type", data.get(position).getType());
        map.put("distance", data.get(position).getDistance() + "");
        map.put("marketId",data.get(position).getMarketId());
    }

    /*传递从分类中查看商品点击条目集合所做的处理*/
    public void dataDisposeSeven(List<ClassifyGoodsReq.ObjBean> data, int position) {
        map.put("id", data.get(position).getId());
        map.put("picUrl", data.get(position).getPicUrl());
        map.put("retailPrice", data.get(position).getRetailPrice().toString());
        map.put("marketPrice", data.get(position).getMarketPrice().toString());
        map.put("name", data.get(position).getName());
        map.put("marketName", data.get(position).getMarketName());
        map.put("freeDispatchLimit", data.get(position).getFreeDispatchLimit().toString());
        map.put("type", data.get(position).getType());
        map.put("distance", data.get(position).getDistance() + "");
        map.put("marketId",data.get(position).getMarketId());

    }
    private void dataDisposeEight(List<GoodsInfo> data, int position) {
        map.put("id", data.get(position).getId());
        map.put("picUrl", data.get(position).getPicUrl());
        map.put("retailPrice", data.get(position).getRetailPrice().toString());
        map.put("marketPrice", data.get(position).getMarketPrice().toString());
        map.put("name", data.get(position).getName());
        map.put("marketName", data.get(position).getMarketName());
        map.put("freeDispatchLimit", data.get(position).getFreeDispatchLimit().toString());
        map.put("type", data.get(position).getType());
        map.put("distance", data.get(position).getDistance() + "");
        map.put("marketId", data.get(position).getMarketId());
    }

    /*传递一个List<History>集合所做的出数处理*/
    public void dataDisposeTwo(List<History> data) {
        map.put("id", data.get(position).getId());
        map.put("picUrl", data.get(position).getImgUrl());
        map.put("retailPrice", data.get(position).getGoodsMoney());
        map.put("marketPrice", data.get(position).getMarketPrice());
        map.put("name", data.get(position).getGoodsName());
        map.put("marketName", data.get(position).getShopName());
        map.put("freeDispatchLimit", data.get(position).getFreeDispatchLimit());
        map.put("type", data.get(position).getType());
        map.put("distance", data.get(position).getShopDistance());
        map.put("marketId", data.get(position).getMarketId());
    }

    /*传递一个List<Collect>集合所做的出数处理*/
    public void dataDisposeThree(List<Collect> data) {
        map.put("id", data.get(position).getId());
        map.put("picUrl", data.get(position).getImgUrl());
        map.put("retailPrice", data.get(position).getGoodsMoney());
        map.put("marketPrice", data.get(position).getMarketPrice());
        map.put("name", data.get(position).getGoodsName());
        map.put("marketName", data.get(position).getShopName());
        map.put("freeDispatchLimit", data.get(position).getFreeDispatchLimit());
        map.put("type", data.get(position).getType());
        map.put("distance", data.get(position).getShopDistance());
        map.put("marketId",data.get(position).getMarketId());
    }

    public void dataDisposeFour(CartGoods cartGoods, CartMarket cartMarket) {
        map.put("id", cartGoods.getGoodsId());
        map.put("picUrl", cartGoods.getPicUrl());
        map.put("retailPrice", cartGoods.getRetailPrice().toString());
        map.put("marketPrice", cartGoods.getMarketPrice().toString());
        map.put("name", cartGoods.getName());
        map.put("marketName", cartGoods.getMarketName());
        map.put("freeDispatchLimit", cartMarket.getFreeDispatchLimit().toString());
        map.put("type", cartGoods.getType());
        map.put("distance", cartMarket.getDistance() + "");
        map.put("marketId", cartMarket.getMarketId());
    }


    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        MyUtils.setRefresh(pullToRefreshLayout);

    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        num += 10;
        detailsModel.GoodsRecommend(1, num, "qty", "desc", (Integer) map.get("marketId"));
        MyUtils.setLoadMore(pullToRefreshLayout);
    }

    //商品属性
    @Override
    public void goodsDetail(PropResp propResp) {
        propResps = propResp;
    }

    @Override
    public void noGoodsProp(String mes) {

    }

    /*查看购物车数据*/
    @Override
    public void cartGoods(int count) {
        number = count;
        if (number > 0) {
            tvCountDetails.setVisibility(View.VISIBLE);
            tvCountDetails.setText(String.valueOf(number));
        }
    }

    /*购物车无数据*/
    @Override
    public void noCartGoods(LookCartResp lookCartResp) {

    }

    /*立即购买回调接口*/
    @Override
    public void newBuyData(CartSettleResp settleResp) {
        cartSettleResp = settleResp;
        if (cartSettleResp.getObj() != null) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constant.START_NEXT_KEY, 1);
            bundle.putSerializable(Constant.AFFIRM_INFO, cartSettleResp);
            startActivity(AffirmOrderActivity.class, bundle);
        } else {
            LogUtils.error(DetailsActivity.class, "没有数据");
        }

    }

    @Override
    public void noNewBuyData(String mes) {
        Toast.makeText(this, mes, Toast.LENGTH_SHORT);
    }

    @Override
    public void startToLoginAct() {
        Intent intent = new Intent(DetailsActivity.this,LoginActivity.class);
        intent.putExtra("Details",1);
        startActivity(intent);
        /*startActivity(LoginActivity.class);
        DetailsActivity.this.finish();*/
    }

    /*添加购物车成功*/
    @Override
    public void addSuccess() {
        //添加购物车之后显示小红点
        tvCountDetails.setText(++number + "");
        tvCountDetails.setVisibility(View.VISIBLE);
    }

    /*添加购物车失败*/
    @Override
    public void addFailure(String msg) {
        startActivity(LoginActivity.class);
        DetailsActivity.this.finish();
    }


    /*推荐商品*/
    @Override
    public void recommendInterface(SearchResp searchResps) {
        searchResp = searchResps;
        similarGoodsAdapter = new SimilarGoodsAdapter(activity, searchResp.getObj());
        gvSimilarGoodsDetails.setAdapter(similarGoodsAdapter);

        gvSimilarGoodsDetails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
                showType = 0;
                type = 0;
                dataDisposeOne(searchResp.getObj(), position);
                imageLoader.displayImage(HttpConstant.ROOT_PATH + map.get("picUrl"), imgGoodsDetails, ImageOptions.getDefaultOptions());//加载图片
                tvNowPriceDetails.setText("¥" + map.get("retailPrice"));
                tvOldPriceDetails.setText("" + map.get("marketPrice"));
                tvShopNameDetails.setText((String) map.get("name"));
                tvAddressDetails.setText((String) map.get("marketName"));
                tvExplainTypeDetails.setText("满" + map.get("freeDispatchLimit") + "元免配送");
                tvOldPriceDetails.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线
                LogUtils.error(DetailsActivity.class, map.get("name") + "");

                /*商品属性*/
                detailsModel.goodProp(searchResp.getObj().get(position).getId(), goodsInfos.get(position).getType());//调获得商品属性接口
                LogUtils.error(DetailsActivity.class,goodsInfos.size()+"  属性商品");
                LogUtils.error(DetailsActivity.class,+searchResp.getObj().size()+"  1111属性商品");
                 /*判断次商品是否为收藏商品*/
                try {
                    List<Collect> collects = collectDao.select();
                    for (int i = 0; i < collects.size(); i++) {
                        LogUtils.error(DetailsActivity.class, "id=" + collects.get(i).getGoodsId());
                        if (collects.get(i).getGoodsName().equals(map.get("name")) & collects.get(i).getType() == (byte) map.get("type")) {
                            collectPosition = i;
                            showType = 1;
                        } else {
                        }
                    }
                    if (showType == 1) {
                        ivCollectDetails.setImageResource(R.mipmap.img_two);
                        type = 1;
                    } else {
                        ivCollectDetails.setImageResource(R.mipmap.img_details_collect);
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }

                tvUserNameDetails.setVisibility(View.VISIBLE);
                imgUserDetails.setVisibility(View.VISIBLE);
                gvCommentImgDetails.setVisibility(View.VISIBLE);
                goodTag.clear();
                total.clear();

            /*获取购物车商品数量*/
                detailsModel.lookShoppingCart();

                detailsModel.GoodsRecommend(1, num, "qty", "desc",(Integer) map.get("marketId"));

                detailsModel.GoodEval((int) map.get("id"), (byte) map.get("type"), 10, 1);

            }
        });


    }

    @Override
    public void noRecommendInterface(String mes) {

    }

    @Override
    public void goodEval(final GoodEvalResp goodEvalResp) {
        this.goodEvalResp = goodEvalResp;
        /*评价标签*/
        if (goodEvalResp.getObj().getLevelList().get(0).getTotal() > 999) {
            tvCommentDetaols.setText("999+");
        } else {
            tvCommentDetaols.setText(String.valueOf(goodEvalResp.getObj().getLevelList().get(0).getTotal()));
        }
        /*评价标签*/
        for (int i = 0; i < goodEvalResp.getObj().getTagList().size(); i++) {
            goodTag.add(goodEvalResp.getObj().getTagList().get(i).getTag());
            total.add(goodEvalResp.getObj().getTagList().get(i).getTotal());
        }
        tcvGvaluationNormalTagDetails.setCommentTags(goodTag, total);
        /*评价类容*/
        if (goodEvalResp.getObj().getEvalList() != null && goodEvalResp.getObj().getEvalList().size() > 0) {
            tvCommentDetails.setText(goodEvalResp.getObj().getEvalList().get(0).getEvalText());
            tvUserNameDetails.setText(goodEvalResp.getObj().getEvalList().get(0).getEvalUser());
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .displayer(new CircleBitmapDisplayer())
                    .build();
            imageLoader.displayImage(HttpConstant.ROOT_PATH + goodEvalResp.getObj().getEvalList().get(0).getAvatarPicUrl(),
                    imgUserDetails, options);
            gvCommentImgDetails.setAdapter(new EvalImageAdapter(activity, goodEvalResp.getObj().getEvalList().get(0).getPics()));
            gvCommentImgDetails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle bundle = new Bundle();
                    bundle.putString("picName", goodEvalResp.getObj().getEvalList().get(0).getPics().get(position).getPicUrl());
                    startActivity(PictureActivity.class, bundle);
                }
            });
        } else {
            tvUserNameDetails.setVisibility(View.GONE);
            imgUserDetails.setVisibility(View.GONE);
            gvCommentImgDetails.setVisibility(View.GONE);
            tvCommentDetails.setText("该商品暂无评论，期待您的评论咯......");
        }
    }

    @Override
    public void noGoodEval(String mes) {

    }


}
