package yunju.com.huiqitian.vm.menu.view;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.autoupdatesdk.BDAutoUpdateSDK;
import com.baidu.autoupdatesdk.UICheckUpdateCallback;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.AppApplication;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseFragment;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.db.dao.MessageDao;
import yunju.com.huiqitian.entity.CarouselImage;
import yunju.com.huiqitian.entity.GoodsInfo;
import yunju.com.huiqitian.entity.MessageBean;
import yunju.com.huiqitian.entity.OrderMessageInfo;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.entity.CarouselResp;
import yunju.com.huiqitian.http.entity.SearchResp;
import yunju.com.huiqitian.http.entity.VersionCheckReq;
import yunju.com.huiqitian.http.service.MyLocationService;
import yunju.com.huiqitian.utils.ImageOptions;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.vm.adapter.ImportedAdapter;
import yunju.com.huiqitian.vm.adapter.SalesAdapter;
import yunju.com.huiqitian.vm.details.view.DetailsActivity;
import yunju.com.huiqitian.vm.find.view.FindActivity;
import yunju.com.huiqitian.vm.find.view.FoodstuffActivity;
import yunju.com.huiqitian.vm.history.view.HistoryActivity;
import yunju.com.huiqitian.vm.integral.view.IntegralStoreActivity;
import yunju.com.huiqitian.vm.map.view.AddressActivity;
import yunju.com.huiqitian.vm.menu.model.HomeFragmentModel;
import yunju.com.huiqitian.vm.menu.model.MenuModel;
import yunju.com.huiqitian.vm.menu.model.VersionCheckModel;
import yunju.com.huiqitian.vm.message.view.MessageExerciseDetailActivity;
import yunju.com.huiqitian.vm.orders.view.OrdersActivity;
import yunju.com.huiqitian.vm.shop.view.NearShopActivity;
import yunju.com.huiqitian.vm.signin.view.SignInActivity;
import yunju.com.huiqitian.vm.widget.MyGridView;
import yunju.com.huiqitian.vm.widget.PullToRefreshLayout;
import yunju.com.huiqitian.vm.widget.PullableScrollView;

/**
 * 首页
 * Created by liuGang on 2016/7/18 0018.
 */
public class MenuHomeFragment extends BaseFragment implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {

    /*接收消息的类型*/
    private int push_type = 0;
    /*订单状态*/
    private int order_type=0;
    /*受众*/
    public byte audience = 1;

    /*数据*/
    private List<GoodsInfo> goodsInfoData;//每日精选
    private List<GoodsInfo> goodsInfoLike;//每日精选
    private List<GoodsInfo> goodsInfoImport;//每日精选
    private List<GoodsInfo> goodsInfoHot;//每日精选

    /*title控件*/
    private LinearLayout llHomeMenu;

    private TextView tvMenuTopAddress;//地址
    private RelativeLayout ivMenuTopMsg;//消息
    private LinearLayout ivMenuTopSearch;//搜索
    private TextView tvMessageOval;//消息提示圆点

    /*中间菜单界面*/
    private LinearLayout menu;
    private ImageView ivMenuShop;//附近的店铺
    private ImageView ivMenuSign;//每日签到
    private ImageView ivMenuFood;//进口食品
    private ImageView ivMenuIntegral;//积分商城
    private ImageView ivMenuNotes;//浏览记录

    /*轮播图*/
    private List<String> items;
    private ConvenientBanner pager;

    /*广告通知相关*/
    private RelativeLayout rlMenuAd;//广告栏
    private TextView tvHomeAd;//广告内容
    private ImageView ivHomeDeleteAd;//删除广告

    private String messageUrl;//消息中的url
    private String messageTitle;//消息标题

    /*无网络显示*/
    private LinearLayout llNoNetwork;
    /*无网络重新加载*/
    private Button btnReload;

    /*附近无超市布局*/
    private LinearLayout llApplyMarket;
    private Button btnApplyMarket;

    /*正常的整体布局*/
    private LinearLayout llMenuHome;

    /*每日精选、猜你喜欢、进口食品、销量爆款布局、暂无数据*/
    private LinearLayout llEverydaySelectionMenuHome;//每日精选
    private LinearLayout llLikeMenuHome;//猜你喜欢
    private LinearLayout importGoodsMenuHome;//进口食品
    private LinearLayout hotSaleMenuHome;//销量爆款
    /*每日精选firs商品详情*/
    private TextView tvHomeGoodsOne;//第一个商品的名字
    private TextView tvHomeGoodsTwo;//第二个商品的名字
    private TextView tvHomeGoodsThree;//第三个商品的名字
    private TextView tvHomeGoodsFour;//第四个商品的名字
    private TextView tvHomeGoodsFive;//第五个商品的名字

    private TextView tvGoodsOneMoney;//第一个商品的价格
    private TextView tvGoodsTwoMoney;//第二个商品的价格
    private TextView tvGoodsThreeMoney;//第三个商品的价格
    private TextView tvGoodsFourMoney;//第四个商品的价格
    private TextView tvGoodsFiveMoney;//第五个商品的价格

    private ImageView ivGoodsOnePic;//第一个商品的图片
    private ImageView ivGoodsTwoPic;//第二个商品的图片
    private ImageView ivGoodsThreePic;//第三个商品的图片
    private ImageView ivGoodsFourPic;//第四个商品的图片
    private ImageView ivGoodsFivePic;//第五个商品的图片

    /*每日精选first布局点击*/
    private RelativeLayout rlGoodsOne;//第一个商品点击
    private RelativeLayout rlGoodsTwo;//第二个商品点击
    private RelativeLayout rlGoodsThree;//第三个商品点击
    private RelativeLayout rlGoodsFour;//第四个商品点击
    private RelativeLayout rlGoodsFive;//第五个商品点击


    /*每日精选second商品详情*/
    private TextView tvHomeGoodsOneSecond;//第一个商品的名字
    private TextView tvGoodsOneMoneySecond;//第一个商品的价格
    private ImageView ivGoodsOnePicSecond;//第一个商品的图片
    /*每日精选second布局点击*/
    private RelativeLayout rlGoodsOneSecond;

   /* private LinearLayout llTwoMenuHome;//每日精选、猜你喜欢、进口零食、销量爆款总布局*/

    /*每日精选Third商品详情*/
    private TextView tvHomeGoodsOneThird;//第一个商品的名字
    private TextView tvHomeGoodsTwoThird;//第二个商品的名字
    private TextView tvHomeGoodsThreeThird;//第三个商品的名字
    private TextView tvHomeGoodsFourThird;//第四个商品的名字

    private TextView tvGoodsOneMoneyThird;//第一个商品的价格
    private TextView tvGoodsTwoMoneyThird;//第二个商品的价格
    private TextView tvGoodsThreeMoneyThird;//第三个商品的价格
    private TextView tvGoodsFourMoneyThird;//第四个商品的价格

    private ImageView ivGoodsOnePicThird;//第一个商品的图片
    private ImageView ivGoodsTwoPicThird;//第二个商品的图片
    private ImageView ivGoodsThreePicThird;//第三个商品的图片
    private ImageView ivGoodsFourPicThird;//第四个商品的图片
    /*每日精选third布局点击*/
    private RelativeLayout rlGoodsOneThird;
    private RelativeLayout rlGoodsTwoThird;
    private RelativeLayout rlGoodsThreeThird;
    private RelativeLayout rlGoodsFourThird;


    /*每日精选几种布局*/
    private LinearLayout firstLayout;
    private LinearLayout secondLayout;
    private LinearLayout thirdLayout;


    /*猜你喜欢*/
    private TextView tvLikeNameOne;//第一个商品的名字
    private TextView tvLikeNameTwo;//第二个商品的名字
    private TextView tvLikeNameThree;//第三个商品的名字
    private TextView tvLikeNameFour;//第四个商品的名字
    private TextView tvLikeNameFive;//第五个商品的名字

    private TextView tvHomeLikeMoneyOne;//第一个商品的价格
    private TextView tvHomeLikeMoneyTwo;//第二个商品的价格
    private TextView tvHomeLikeMoneyThree;//第三个商品的价格
    private TextView tvHomeLikeMoneyFour;//第四个商品的价格
    private TextView tvHomeLikeMoneyFive;//第五个商品的价格

    private ImageView ivLikePicOne;//第一个商品的图片
    private ImageView ivLikePicTwo;//第二个商品的图片
    private ImageView ivLikePicThree;//第三个商品的图片
    private ImageView ivLikePicFour;//第四个商品的图片
    private ImageView ivLikePicFive;//第五个商品的图片

    /*猜你喜欢点击布局*/
    private RelativeLayout rlLikeOne;
    private RelativeLayout rlLikeTwo;
    private RelativeLayout rlLikeThree;
    private RelativeLayout rlLikeFour;
    private RelativeLayout rlLikeFive;

    /*刷新加载的参数*/
    private int num = 10;
    private boolean hasAnimation = false;

    /*刷新加载控件*/
    private PullToRefreshLayout pullToRefreshLayout;
    private PullableScrollView slHomeView;


    /*图片加载器*/
    private ImageLoader imageLoader = ImageLoader.getInstance();
    /*进口食品*/
    private MyGridView gvImportedHome;
    /*销量爆款*/
    private MyGridView gvSalesHome;

    /*上啦加载下拉刷新*/

    /*广播相关*/
    private MyLocationReceiver myLocationReceiver;//广播接收器
    private MyMessageReceiver myMessageReceiver;//接受推送消息的广播

    //model
    private MenuModel menuModel;
    private HomeFragmentModel homeFragmentModel;

    /*存储推送的公告消息*/
    private MessageDao messageDao;
    private yunju.com.huiqitian.db.entity.Message  message;

    /*开始定位的城市*/
    private String city;

    /*检查版本更新*/
    private VersionCheckModel versionCheckModel;

    /*判断有无网络刷新表示，无网络不让其刷新*/
    private int noNetworkType;
    private String versionCode;


    @Override
    public View bootView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_menu_home, viewGroup, false);
    }

    @Override
    public void initBoot() {
        menuModel = new MenuModel(activity);
        homeFragmentModel = new HomeFragmentModel(activity);
        messageDao=new MessageDao();
        message=new yunju.com.huiqitian.db.entity.Message();
    }

    @Override
    public void initViews() {
        //title控件的实例化
        llHomeMenu=findView(R.id.ll_home_menu);

        tvMenuTopAddress = findView(R.id.tv_menu_top_address);
        ivMenuTopMsg = findView(R.id.iv_menu_top_msg);
        ivMenuTopSearch = findView(R.id.iv_menu_top_search);
        gvImportedHome = findView(R.id.gv_imported_home);
        gvSalesHome = findView(R.id.gv_sales_home);
        tvMessageOval = findView(R.id.tv_message_oval);
        //注册监听
        ivMenuTopSearch.setOnClickListener(this);
        ivMenuTopMsg.setOnClickListener(this);
        tvMenuTopAddress.setOnClickListener(this);

        /*菜单*/
        menu=findView(R.id.menu);
        ivMenuShop = findView(R.id.iv_menu_shop);
        ivMenuSign = findView(R.id.iv_menu_sign);
        ivMenuFood = findView(R.id.iv_menu_food);
        ivMenuIntegral = findView(R.id.iv_menu_integral);
        ivMenuNotes = findView(R.id.iv_menu_notes);

           /*轮播图相关*/
        pager = findView(R.id.pager);

        /*广告*/
        rlMenuAd = findView(R.id.rl_menu_ad);
        tvHomeAd = findView(R.id.tv_home_ad);
        ivHomeDeleteAd = findView(R.id.iv_home_delete_ad);

        //注册监听
        ivMenuShop.setOnClickListener(this);
        ivMenuSign.setOnClickListener(this);
        ivMenuFood.setOnClickListener(this);
        ivMenuIntegral.setOnClickListener(this);
        ivMenuNotes.setOnClickListener(this);
        ivHomeDeleteAd.setOnClickListener(this);

        /*附近暂无超市申请开店布局*/
        llApplyMarket=findView(R.id.ll_apply_market);
        btnApplyMarket=findView(R.id.btn_apply_market);

        /*无网络显示*/
        llNoNetwork=findView(R.id.ll_no_network);
        /*无网络重新加载*/
        btnReload=findView(R.id.btn_reload);

        /*正常显示的整体布局*/
        llMenuHome=findView(R.id.ll_menu_home);

        //每日精选、猜你喜欢、进口零食、销量爆款、附近无超市总布局
        llEverydaySelectionMenuHome = findView(R.id.ll_everyday_selection_menu_home);//每日精选
        llLikeMenuHome = findView(R.id.ll_like__menu_home);//猜你喜欢
        importGoodsMenuHome = findView(R.id.import_goods_menu_home);//进口食品
        hotSaleMenuHome = findView(R.id.hot_sale_menu_home);//销量爆款

        /*每日精选*/
        tvHomeGoodsOne = findView(R.id.tv_home_goods_one);//商品名
        tvHomeGoodsTwo = findView(R.id.tv_home_goods_two);
        tvHomeGoodsThree = findView(R.id.tv_home_goods_three);
        tvHomeGoodsFour = findView(R.id.tv_home_goods_four);
        tvHomeGoodsFive = findView(R.id.tv_home_goods_five);
        tvGoodsOneMoney = findView(R.id.tv_goods_one_money);//商品价格
        tvGoodsTwoMoney = findView(R.id.tv_goods_two_money);
        tvGoodsThreeMoney = findView(R.id.tv_goods_three_money);
        tvGoodsFourMoney = findView(R.id.tv_goods_four_money);
        tvGoodsFiveMoney = findView(R.id.tv_goods_five_money);
        ivGoodsOnePic = findView(R.id.iv_goods_one_pic);//商品图片
        ivGoodsTwoPic = findView(R.id.iv_goods_two_pic);
        ivGoodsThreePic = findView(R.id.iv_goods_three_pic);
        ivGoodsFourPic = findView(R.id.iv_goods_four_pic);
        ivGoodsFivePic = findView(R.id.iv_goods_five_pic);


        tvHomeGoodsOneSecond = findView(R.id.tv_home_goods_one_second);
        tvGoodsOneMoneySecond = findView(R.id.tv_goods_one_money_second);//商品价格
        ivGoodsOnePicSecond = findView(R.id.iv_goods_one_pic_second);//商品图片

        tvHomeGoodsOneThird = findView(R.id.tv_home_goods_one_third);//商品名
        tvHomeGoodsTwoThird = findView(R.id.tv_home_goods_two_third);
        tvHomeGoodsThreeThird = findView(R.id.tv_home_goods_third_third);
        tvHomeGoodsFourThird = findView(R.id.tv_home_goods_four_third);
        tvGoodsOneMoneyThird = findView(R.id.tv_goods_one_money_third);//商品价格
        tvGoodsTwoMoneyThird = findView(R.id.tv_goods_two_money_third);
        tvGoodsThreeMoneyThird = findView(R.id.tv_goods_third_money_third);
        tvGoodsFourMoneyThird = findView(R.id.tv_goods_four_money_third);
        ivGoodsOnePicThird = findView(R.id.iv_goods_one_pic_third);//商品图片
        ivGoodsTwoPicThird = findView(R.id.iv_goods_two_pic_third);
        ivGoodsThreePicThird = findView(R.id.iv_goods_third_pic_third);
        ivGoodsFourPicThird = findView(R.id.iv_goods_four_pic_third);



         /*每日精选布局first——2，3,5个数*/
        firstLayout = findView(R.id.rl_first_layout);
          /*每日精选布局second——1个数*/
        secondLayout = findView(R.id.rl_second_layout);
          /*每日精选布局third——4个数*/
        thirdLayout = findView(R.id.rl_third_layout);

        /*每日精first布局选点击2,3,5个*/
        rlGoodsOne = findView(R.id.rl_goods_one);
        rlGoodsTwo = findView(R.id.rl_goods_two);
        rlGoodsThree = findView(R.id.rl_goods_three);
        rlGoodsFour = findView(R.id.rl_goods_four);
        rlGoodsFive = findView(R.id.rl_goods_five);

         /*每日精second布局选点击 1个*/
        rlGoodsOneSecond = findView(R.id.rl_goods_one_second);

        /*每日精third布局选点击 4个*/
        rlGoodsOneThird = findView(R.id.rl_goods_one_third);
        rlGoodsTwoThird = findView(R.id.rl_goods_two_third);
        rlGoodsThreeThird = findView(R.id.rl_goods_third_third);
        rlGoodsFourThird = findView(R.id.rl_goods_four_third);




        /*猜你喜欢*/
        tvLikeNameOne = findView(R.id.tv_like_name_one);//商品名
        tvLikeNameTwo = findView(R.id.tv_like_name_two);
        tvLikeNameThree = findView(R.id.tv_like_name_three);
        tvLikeNameFour = findView(R.id.tv_like_name_four);
        tvLikeNameFive = findView(R.id.tv_like_name_five);
        tvHomeLikeMoneyOne = findView(R.id.tv_home_like_money_one);//商品价格
        tvHomeLikeMoneyTwo = findView(R.id.tv_home_like_money_two);
        tvHomeLikeMoneyThree = findView(R.id.tv_home_like_money_three);
        tvHomeLikeMoneyFour = findView(R.id.tv_home_like_money_four);
        tvHomeLikeMoneyFive = findView(R.id.tv_home_like_money_five);
        ivLikePicOne = findView(R.id.iv_like_pic_one);//商品图片
        ivLikePicTwo = findView(R.id.iv_like_pic_two);
        ivLikePicThree = findView(R.id.iv_like_pic_three);
        ivLikePicFour = findView(R.id.iv_like_pic_four);
        ivLikePicFive = findView(R.id.iv_like_pic_five);

        /*猜你喜欢点击布局*/
        rlLikeOne = findView(R.id.rl_like_one);
        rlLikeTwo = findView(R.id.rl_like_two);
        rlLikeThree = findView(R.id.rl_like_three);
        rlLikeFour = findView(R.id.rl_like_four);
        rlLikeFive = findView(R.id.rl_like_five);

        /*刷新加载控件实例化*/
        pullToRefreshLayout = findView(R.id.rl_menu_home);
        pullToRefreshLayout.setOnRefreshListener(this);
        slHomeView = findView(R.id.sl_home_view);
    }

    @Override
    public void initData(Bundle bundle) {
        /**
         * 检测版本信息检测
         * type：类型 (byte) 必填  1Android买家 2Android卖家 3IOS买家 4IOS卖家
         * version : 版本号(String)  必填 xxx.xxx.xxx
         * audience : 受众 (byte) 必填  1所有用户 2测试用户
         */
        versionCheckModel = new VersionCheckModel(activity);

        versionCheckModel.getCheckVersionInterface(new VersionCheckModel.CheckVersion() {
            @Override
            public void addSuccess(String obj) {
                VersionCheckReq versionCheckReq = parseObject(obj, VersionCheckReq.class);
                int obj1 = versionCheckReq.getObj();
                /**
                 *  0最新版本， 1普通升级， 2灰度升级， 3强制升级
                 */
                switch (obj1) {
                    case 0:
                    case 1:
                    case 2:
                        openLocation();//打开定位
                        openBroadCast();//打开广播
                        noNetworkType=0;
                        break;
                    case 3:
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        //设置对话框图标，可以使用自己的图片，Android本身也提供了一些图标供我们使用
                        builder.setIcon(android.R.drawable.ic_dialog_alert);
                        //设置对话框标题
                        builder.setTitle("警告！");
                        //设置对话框内的文本
                        builder.setMessage("您的版本太低，无法使用，请立即更新。");
                        //设置确定按钮，并给按钮设置一个点击侦听，注意这个OnClickListener使用的是DialogInterface类里的一个内部接口
                        builder.setPositiveButton("立刻升级", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                BDAutoUpdateSDK.uiUpdateAction(activity, new MyUICheckUpdateCallback());
                            }
                        });

                        //使用builder创建出对话框对象
                        AlertDialog dialog = builder.create();
                        //显示对话框
                        dialog.show();
                        break;
                }
            }

            @Override
            public void addFailure(String msg) {

            }
        });

        if (noNetWork()) {
            noNetworkType=1;
            showNetWorkError();
            pullToRefreshLayout.setVisibility(View.VISIBLE);
            llNoNetwork.setVisibility(View.VISIBLE);
            llMenuHome.setVisibility(View.GONE);

        } else {
            /*检测版本号*/
            PackageManager packageManager = activity.getPackageManager();
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(activity.getPackageName(), 0);
                versionCode = packageInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            versionCheckModel.checkVersion(Constant.VERSION_TYPE_ANDROID, versionCode, audience);
            getDataCallBack();
        }
    }

    private class MyUICheckUpdateCallback implements UICheckUpdateCallback {
        @Override
        public void onCheckComplete() {
        }
    }

    @Override
    public void initEvents() {

        /*让轮播图聚焦，打开直接显示最上方*/
        pager.setFocusable(true);
        pager.setFocusableInTouchMode(true);
        pager.requestFocus();




        /*消息按钮的点击事件*/
        /*ivMenuTopMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MessageActivity.class);
                tvMessageOval.setVisibility(View.GONE);
            }
        });*/


        /*每日精选点击事件*/
        rlGoodsOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(0, goodsInfoData);
            }
        });
        rlGoodsTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(1, goodsInfoData);
            }
        });
        rlGoodsThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(2, goodsInfoData);
            }
        });
        rlGoodsFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(3, goodsInfoData);
            }
        });
        rlGoodsFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(4, goodsInfoData);
            }
        });

        /*猜你喜欢点击事件*/
        rlLikeOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(0, goodsInfoLike);
            }
        });
        rlLikeTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(1, goodsInfoLike);
            }
        });
        rlLikeThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(2, goodsInfoLike);
            }
        });
        rlLikeFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(3, goodsInfoLike);
            }
        });
        rlLikeFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(4, goodsInfoLike);
            }
        });

        /*猜你喜欢GridView的点击事件*/
        gvImportedHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                start(position, goodsInfoImport);
            }
        });

        /*销量爆款GridView的点击事件*/
        gvSalesHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                start(position, goodsInfoHot);
            }
        });

        /*我要申请开店点击*/
        btnApplyMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + "400-6433369");
                intent.setData(data);
                startActivity(intent);
            }
        });

        /*无网络重新加载*/
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!noNetWork()){
                    /*开启定位服务*/
                    Intent intent = new Intent(activity, MyLocationService.class);
                    activity.startService(intent);
                /*检测版本号*/
                    versionCheckModel.checkVersion(Constant.VERSION_TYPE_ANDROID, versionCode, audience);
                    getDataCallBack();
                }else {
                    showNetWorkError();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            /*地址*/
            case R.id.tv_menu_top_address:
                //startActivity(AddressActivity.class);
                Intent intent=new Intent(getContext(), AddressActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("address",city);
                intent.putExtras(bundle);
                startActivityForResult(intent, Constant.HOME_FREGMENT_TO_ADDRESS);
                break;
            /*搜索*/
            case R.id.iv_menu_top_search:
                startActivity(FindActivity.class);
                break;
            /*消息*/
            /*case R.id.iv_menu_top_msg:
                break;*/
            /*附近的店铺*/
            case R.id.iv_menu_shop:
                startActivity(NearShopActivity.class);
                break;
            /*每日签到*/
            case R.id.iv_menu_sign:
                startActivity(SignInActivity.class);
                break;
             /*进口食品*/
            case R.id.iv_menu_food:
                startActivity(FoodstuffActivity.class);
                break;
             /*积分商城*/
            case R.id.iv_menu_integral:
                startActivity(IntegralStoreActivity.class);
                break;
             /*浏览记录*/
            case R.id.iv_menu_notes:
                Bundle bundles = new Bundle();
                bundles.putInt(Constant.START_WHERE, Constant.START_FORM_HISTORY);
                startActivity(HistoryActivity.class, bundles);
                break;
            /*删除广告*/
            case R.id.iv_home_delete_ad:
                rlMenuAd.setVisibility(View.INVISIBLE);//不显示广告栏
                break;

        }
    }

    /**
     * 打开广播
     */
    private void openBroadCast() {
        /*打开接受定位的广播*/
        myLocationReceiver = new MyLocationReceiver();
        IntentFilter itFilter = new IntentFilter();
        itFilter.addAction(Constant.MY_BROADCAST_ACTION_HOME);
        getActivity().registerReceiver(myLocationReceiver, itFilter);

        /*打开接受推送消息的广播*/
        myMessageReceiver = new MyMessageReceiver();
        IntentFilter itFilterMessage = new IntentFilter();
        itFilterMessage.addAction(Constant.PUSH_ACTION);
        getActivity().registerReceiver(myMessageReceiver, itFilterMessage);

    }

    /*刷新加载*/
    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {

        if(noNetworkType!=1){
             /*将经纬度传给后台*/
            menuModel.buyerLocate(Double.parseDouble(MyUtils.getLog()), Double.parseDouble(MyUtils.getLag()));
            /*轮播图接口*/
            homeFragmentModel.carousel();
            getDataCallBack();
        }
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
            }
        }.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
        hasAnimation = true;
        homeFragmentModel.hotGoods(1, num, "qty", "desc");
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
            }
        }.sendEmptyMessageDelayed(0, 3000);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            MyUtils.saveLag(String.valueOf(data.getDoubleExtra("latitude", 0.00)),String.valueOf(data.getDoubleExtra("longitude", 0.00)),data.getStringExtra(Constant.MY_CITY),data.getStringExtra("address"));
            menuModel.buyerLocate(data.getDoubleExtra("longitude", 0.00), data.getDoubleExtra("latitude", 0.00));
            tvMenuTopAddress.setText(data.getStringExtra("address"));
            city=data.getStringExtra(Constant.MY_CITY);
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        if(MyUtils.getLogType().equals("yes")){
            if(MyUtils.getAddresss()!=null&&!MyUtils.getAddresss().equals("")){
                tvMenuTopAddress.setText(MyUtils.getAddresss());
                city=MyUtils.getCity();
                menuModel.buyerLocate(Double.parseDouble(MyUtils.getLog()), Double.parseDouble(MyUtils.getLag()));
                MyUtils.saveLocationType("no");
            }
        }
        if(AppApplication.getBoolean("home_fragment_refresh")){
            AppApplication.putBoolean("home_fragment_refresh",false);
            MainMenu mainMenu= (MainMenu) getActivity();
            mainMenu.initFragment(2);

        }
    }

    /**
     * 广播接受，用来接收服务中发来的地址信息
     */
    public class MyLocationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.error(MainMenu.class, "接受定位数据成功");
            String myLocation = intent.getStringExtra(Constant.MY_LOCATION_MSG_KEY);

            double latitude = intent.getDoubleExtra(Constant.MY_LATITUDE, 0.0);
            double longitude = intent.getDoubleExtra(Constant.MY_Longitude, 0.0);
            city=intent.getStringExtra(Constant.MY_CITY);
            MyUtils.saveLag(String.valueOf(latitude), String.valueOf(longitude), city, myLocation);
            tvMenuTopAddress.setText(myLocation);
            /*将定位传给后台*/
            menuModel.buyerLocate(longitude, latitude);
            /*轮播图接口*/
            homeFragmentModel.carousel();

            closeLocation();//关闭广播
        }
    }

    /**
     * 广播接受，接受推送消息的广播
     */
    public class MyMessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (MyUtils.getPushType().equals(Constant.YES)) {
                push_type = intent.getIntExtra(Constant.MY_MESSAGE_FROM_TYPE, 0);
                if (push_type == 1) {
                    MessageBean messageBean = (MessageBean) intent.getSerializableExtra(Constant.MY_MESSAGE_FROM_PUS);
                    rlMenuAd.setVisibility(View.VISIBLE);
                    messageTitle = messageBean.getTitle();//消息头文件
                    messageUrl = messageBean.getUrl();//消息内容Url
                    message.setType(push_type);
                    message.setTitle(messageTitle);
                    message.setUrl(messageUrl);
//                    try {
//                       messageDao.save(message);
//                    } catch (DbException e) {
//                        e.printStackTrace();
//                    }
                    notificationDefault(context);
                } else if (push_type == 2) {
                    OrderMessageInfo orderMessageInfo = (OrderMessageInfo) intent.getSerializableExtra(Constant.MY_MESSAGE_FROM_PUS);
                    messageTitle = orderMessageInfo.getMsg();
                    order_type = orderMessageInfo.getType();
                    notificationDefault(context);
                }

            }
            closeLocation();
        }
    }

    /**
     * 系统下拉栏默认的通用通知
     */
    public void notificationDefault(Context context) {
        // 获取NotificationManager管理者对象
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (push_type == 1) {
            /*普通的消息推送*/
            Intent intent = new Intent(context, MessageExerciseDetailActivity.class);//点击跳转到Main22Activity
            intent.putExtra(Constant.MESSAGE_URL, HttpConstant.ROOT_PATH + messageUrl);//将url传过去

            // 创建一个PendingIntent，和Intent类似，不同的是由于不是马上调用，需要在下拉状态条出发的Activity，所以采用的是PendingIntent,即点击Notification跳转启动到哪个Activity
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            // 获取Notification对象
            Notification.Builder builder = new Notification.Builder(context);
            // builder.setContentInfo("补充内容");
            builder.setContentText("活动地址：" + messageUrl);
            builder.setContentTitle(messageTitle);
            // 设置显示的小图标
            builder.setSmallIcon(R.mipmap.icon);
            // 大图标
            builder.setLargeIcon(BitmapFactory.decodeResource(
                    context.getResources(), R.mipmap.icon));
            //设置提示内容
            builder.setTicker("新消息");
            builder.setAutoCancel(true);//点击后消失
            if (MyUtils.getPushSound().equals(Constant.YES)) {
                //使用默认的声音、振动、闪光
                builder.setDefaults(Notification.DEFAULT_ALL);

                //使用默认的震动和声音
                builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
                // builder.setDefaults(Notification.)
                LogUtils.error(MenuHomeFragment.class, "777777777777777777推送消息有声音和震动");
            }

            // 获取当前系统时间
            builder.setWhen(System.currentTimeMillis());

            // 设置显示的信息
            builder.setContentIntent(pendingIntent);
            Notification notification = builder.build();
            manager.notify(1, notification);
        } else if (push_type == 2) {
            /*订单消息推送*/
            if (order_type == 1) {
                /*商家已接单，消息提示*/
                Intent intent = new Intent(context, OrdersActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.ORDERS_MARKS, Constant.WAITE_SEND);
                intent.putExtras(bundle);
                // 创建一个PendingIntent，和Intent类似，不同的是由于不是马上调用，需要在下拉状态条出发的Activity，所以采用的是PendingIntent,即点击Notification跳转启动到哪个Activity
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                // 获取Notification对象
                Notification.Builder builder = new Notification.Builder(context);
                // builder.setContentInfo("补充内容");
                builder.setContentText(messageTitle);
                builder.setContentTitle("您的订单已接收了快看一下吧");
                // 设置显示的小图标
                builder.setSmallIcon(R.mipmap.icon);
                // 大图标
                builder.setLargeIcon(BitmapFactory.decodeResource(
                        context.getResources(), R.mipmap.icon));
                //设置提示内容
                builder.setTicker("新消息");
                builder.setAutoCancel(true);//点击后消失
                if (MyUtils.getPushSound().equals(Constant.YES)) {
                    //使用默认的声音、振动、闪光
                    builder.setDefaults(Notification.DEFAULT_ALL);

                    //使用默认的震动和声音
                    builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
                    // builder.setDefaults(Notification.)
                }

                // 获取当前系统时间
                builder.setWhen(System.currentTimeMillis());

                // 设置显示的信息
                builder.setContentIntent(pendingIntent);
                Notification notification = builder.build();
                manager.notify(1, notification);
                AppApplication.putBoolean("person_fragment_refresh", true);
            } else if (order_type == 2) {
                /*订单已配送，消息提示*/
                Intent intent = new Intent(context, OrdersActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.ORDERS_MARKS, Constant.WAITE_GET);
                intent.putExtras(bundle);

                // 创建一个PendingIntent，和Intent类似，不同的是由于不是马上调用，需要在下拉状态条出发的Activity，所以采用的是PendingIntent,即点击Notification跳转启动到哪个Activity
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                // 获取Notification对象
                Notification.Builder builder = new Notification.Builder(context);
                // builder.setContentInfo("补充内容");
                builder.setContentText(messageTitle);
                builder.setContentTitle("您的订单已发货了快看一下吧");
                // 设置显示的小图标
                builder.setSmallIcon(R.mipmap.icon);
                // 大图标
                builder.setLargeIcon(BitmapFactory.decodeResource(
                        context.getResources(), R.mipmap.icon));
                //设置提示内容
                builder.setTicker("新消息");
                builder.setAutoCancel(true);//点击后消失
                if (MyUtils.getPushSound().equals(Constant.YES)) {
                    //使用默认的声音、振动、闪光
                    builder.setDefaults(Notification.DEFAULT_ALL);

                    //使用默认的震动和声音
                    builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
                    // builder.setDefaults(Notification.)
                }

                // 获取当前系统时间
                builder.setWhen(System.currentTimeMillis());
                // 设置显示的信息
                builder.setContentIntent(pendingIntent);
                Notification notification = builder.build();
                manager.notify(1, notification);
                AppApplication.putBoolean("person_fragment_refresh", true);
            }
        }
    }

    /**
     * 打开定位向服务中发送广播
     */
    public void openLocation() {
        MyUtils.saveLocationType("one");
        Intent intent = new Intent(Constant.MY_CONTROL_ACTION);
        intent.putExtra(Constant.MY_CONTROL_MSG_KEY, Constant.OPEN);
        intent.putExtra(Constant.LOCATION_TYPE, 1);
        getActivity().sendBroadcast(intent);
    }

    /**
     * 关闭定位 向服务中发送广播
     */
    public void closeLocation() {
        Intent intent = new Intent(Constant.MY_CONTROL_ACTION);
        intent.putExtra(Constant.MY_CONTROL_MSG_KEY, Constant.CLOSE);
        intent.putExtra(Constant.LOCATION_TYPE, 1);
        getActivity().sendBroadcast(intent);
        LogUtils.error(MenuHomeFragment.class, "发送关闭广播成功");
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();

        /*注销广播*/
        if (myLocationReceiver != null) {
            getActivity().unregisterReceiver(myLocationReceiver);
        }
        if (myMessageReceiver != null) {
            getActivity().unregisterReceiver(myMessageReceiver);
        }
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
     * Created by gaoqiong on 2016/9/28 0028 20:23
     * 轮播图
     */

    private void intoConventBanner(List<String> localImages) {

        //自定义你的 Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        pager.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                        //设置指示器的方向
                        //.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .startTurning(2000);  //设置自动切换（同时设置了切换时间间隔）
        //.setManualPageable(false);//设置不能手动影响
        //设置翻页的效果，不需要翻页效果可用不设
        //.setPageTransformer(Transformer.DefaultTransformer);    集成特效之后会有白屏现象，新版已经分离，如果要集成特效的例子可以看 Demo 的点击响应。

        /*轮播图的点击*/
        /*pager.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showToast(items.get(position));
            }
        });*/

    }


    public class LocalImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, String data) {
            imageLoader.displayImage(HttpConstant.ROOT_PATH + data, imageView, ImageOptions.getDefaultOptions());//商品图片
        }
    }


    /**
     * 定位成功的接口回调
     */
    public void getDataCallBack(){
        homeFragmentModel.setCaroouselInterface(new HomeFragmentModel.CaroouselInterface() {
            @Override
            public void carouselInfo(CarouselResp carouselResp) {
                List<CarouselImage> obj = carouselResp.getObj();
                /*存放轮播图片的url*/
                items = new ArrayList<String>();
                for (CarouselImage carouselImage : obj) {
                    items.add(carouselImage.getPicUrl());
                }

                /*将得到的集合添加到conventbanner中*/
                intoConventBanner(items);
            }

            @Override
            public void noCarouselInfo(String mes) {
            }
        });
        menuModel.setLocateInterface(new MenuModel.LocateInterface() {
            /*经纬度上传成功当前有超市*/
            @Override
            public void LocateSuccess(String mes) {
                llApplyMarket.setVisibility(View.GONE);
                llHomeMenu.setBackgroundResource(R.color.white);
                pullToRefreshLayout.setVisibility(View.VISIBLE);
                llMenuHome.setVisibility(View.VISIBLE);
                llNoNetwork.setVisibility(View.GONE);
                /*显示每日精选*/
                homeFragmentModel.everyDayGoods(1, 20, "price", "desc");

                 /*猜你喜欢*/
                homeFragmentModel.likeGoods(1, 20, "qty", "desc");

                 /*进口食品*/
                homeFragmentModel.importFood(1, 20);

                /*销量爆款*/
                homeFragmentModel.hotGoods(1, 10, "qty", "desc");

            }

            /*经纬度上传成功当前无超市*/
            @Override
            public void LocateFailure(String mes) {
                llHomeMenu.setBackgroundResource(R.mipmap.bg_map);
                pullToRefreshLayout.setVisibility(View.GONE);
                llApplyMarket.setVisibility(View.VISIBLE);

            }

        });

        homeFragmentModel.setLikeGoodsInterface(new HomeFragmentModel.LikeGoodsInterface() {
            /*猜你喜欢*/
            @Override
            public void likeGoodsInfo(SearchResp searchResp) {
                goodsInfoLike = searchResp.getObj();
                if (goodsInfoLike.size() == 0) {
                    llLikeMenuHome.setVisibility(View.GONE);
                } else {
                    tvLikeNameOne.setText(searchResp.getObj().get(0).getName());//商品名字
                    tvLikeNameTwo.setText(searchResp.getObj().get(1).getName());
                    tvLikeNameThree.setText(searchResp.getObj().get(2).getName());
                    tvLikeNameFour.setText(searchResp.getObj().get(3).getName());
                    tvLikeNameFive.setText(searchResp.getObj().get(4).getName());
                    tvHomeLikeMoneyOne.setText(searchResp.getObj().get(0).getRetailPrice().toString());//商品价格
                    tvHomeLikeMoneyTwo.setText(searchResp.getObj().get(1).getRetailPrice().toString());
                    tvHomeLikeMoneyThree.setText(searchResp.getObj().get(2).getRetailPrice().toString());
                    tvHomeLikeMoneyFour.setText(searchResp.getObj().get(3).getRetailPrice().toString());
                    tvHomeLikeMoneyFive.setText(searchResp.getObj().get(4).getRetailPrice().toString());
                    imageLoader.displayImage(HttpConstant.ROOT_PATH + searchResp.getObj().get(0).getPicUrl(), ivLikePicOne, ImageOptions.getDefaultOptions());//商品图片
                    imageLoader.displayImage(HttpConstant.ROOT_PATH + searchResp.getObj().get(1).getPicUrl(), ivLikePicTwo, ImageOptions.getDefaultOptions());
                    imageLoader.displayImage(HttpConstant.ROOT_PATH + searchResp.getObj().get(2).getPicUrl(), ivLikePicThree, ImageOptions.getDefaultOptions());
                    imageLoader.displayImage(HttpConstant.ROOT_PATH + searchResp.getObj().get(3).getPicUrl(), ivLikePicFour, ImageOptions.getDefaultOptions());
                    imageLoader.displayImage(HttpConstant.ROOT_PATH + searchResp.getObj().get(4).getPicUrl(), ivLikePicFive, ImageOptions.getDefaultOptions());
                }

            }

            @Override
            public void noLikeGoodsInfo(String msg) {
                llLikeMenuHome.setVisibility(View.GONE);
            }

            @Override
            public void noEveryDayGoodsInfo(String msg) {
                llEverydaySelectionMenuHome.setVisibility(View.GONE);
            }

            /*进口零食*/
            @Override
            public void classGoodsInfo(SearchResp searchResp) {
                goodsInfoImport = searchResp.getObj();
                if (goodsInfoImport.size() == 0) {
                    importGoodsMenuHome.setVisibility(View.GONE);
                } else {
                    gvImportedHome.setAdapter(new ImportedAdapter(activity, searchResp.getObj()));
                }

            }

            @Override
            public void noClassGoodsInfo(String msg) {
                importGoodsMenuHome.setVisibility(View.GONE);
            }

            /*热卖商品（销量排序）*/
            @Override
            public void hotGoodsInfo(SearchResp searchResp) {
                goodsInfoHot = searchResp.getObj();
                if (goodsInfoHot.size() == 0) {
                    hotSaleMenuHome.setVisibility(View.GONE);
                } else {
                    gvSalesHome.setAdapter(new SalesAdapter(activity, searchResp.getObj()));
                    num = num + 6;
                    if (hasAnimation) {
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }
                }

            }

            @Override
            public void noHotGoodsInfo(String msg) {
                hotSaleMenuHome.setVisibility(View.GONE);
            }

            /*获取不到当前位置*/
            @Override
            public void LocateError(String mes) {
                menuModel.buyerLocate(Double.parseDouble(MyUtils.getLog()), Double.parseDouble(MyUtils.getLag()));
            }


            /*每日精选*/
            @Override
            public void everyDayGoodsInfo(SearchResp searchResp) {
                goodsInfoData = searchResp.getObj();
                if (goodsInfoData.size() == 0) {
                    llEverydaySelectionMenuHome.setVisibility(View.GONE);
                    firstLayout.setVisibility(View.GONE);
                    secondLayout.setVisibility(View.GONE);
                    thirdLayout.setVisibility(View.GONE);
                } else {
                    if (goodsInfoData.size() == 1) {
                        firstLayout.setVisibility(View.GONE);
                        secondLayout.setVisibility(View.VISIBLE);
                        thirdLayout.setVisibility(View.GONE);

                        rlGoodsOneSecond.setVisibility(View.VISIBLE);

                        tvHomeGoodsOneSecond.setText(searchResp.getObj().get(0).getName());//商品名
                        tvGoodsOneMoneySecond.setText(searchResp.getObj().get(0).getRetailPrice().toString());//商品价格
                        imageLoader.displayImage(HttpConstant.ROOT_PATH + searchResp.getObj().get(0).getPicUrl(), ivGoodsOnePicSecond, ImageOptions.getDefaultOptions());

                    } else if (goodsInfoData.size() == 4) {
                        firstLayout.setVisibility(View.GONE);
                        secondLayout.setVisibility(View.GONE);
                        thirdLayout.setVisibility(View.VISIBLE);

                        rlGoodsOneThird.setVisibility(View.VISIBLE);
                        rlGoodsTwoThird.setVisibility(View.VISIBLE);
                        rlGoodsThreeThird.setVisibility(View.VISIBLE);
                        rlGoodsFourThird.setVisibility(View.VISIBLE);

                        tvHomeGoodsOneThird.setText(searchResp.getObj().get(0).getName());//商品名
                        tvHomeGoodsTwoThird.setText(searchResp.getObj().get(1).getName());
                        tvHomeGoodsThreeThird.setText(searchResp.getObj().get(2).getName());
                        tvHomeGoodsFourThird.setText(searchResp.getObj().get(3).getName());
                        tvGoodsOneMoneyThird.setText(searchResp.getObj().get(0).getRetailPrice().toString());//商品价格
                        tvGoodsTwoMoneyThird.setText(searchResp.getObj().get(1).getRetailPrice().toString());
                        tvGoodsThreeMoneyThird.setText(searchResp.getObj().get(2).getRetailPrice().toString());
                        tvGoodsFourMoneyThird.setText(searchResp.getObj().get(3).getRetailPrice().toString());
                        imageLoader.displayImage(HttpConstant.ROOT_PATH + searchResp.getObj().get(0).getPicUrl(), ivGoodsOnePicThird, ImageOptions.getDefaultOptions());//商品图片
                        imageLoader.displayImage(HttpConstant.ROOT_PATH + searchResp.getObj().get(1).getPicUrl(), ivGoodsTwoPicThird, ImageOptions.getDefaultOptions());
                        imageLoader.displayImage(HttpConstant.ROOT_PATH + searchResp.getObj().get(2).getPicUrl(), ivGoodsThreePicThird, ImageOptions.getDefaultOptions());
                        imageLoader.displayImage(HttpConstant.ROOT_PATH + searchResp.getObj().get(3).getPicUrl(), ivGoodsFourPicThird, ImageOptions.getDefaultOptions());

                    } else {
                        firstLayout.setVisibility(View.VISIBLE);
                        secondLayout.setVisibility(View.GONE);
                        thirdLayout.setVisibility(View.GONE);

                        if (goodsInfoData.size() == 2) {
                            rlGoodsThree.setVisibility(View.GONE);
                            rlGoodsFour.setVisibility(View.GONE);
                            rlGoodsFive.setVisibility(View.GONE);
                            tvHomeGoodsOne.setText(searchResp.getObj().get(0).getName());//商品名
                            tvHomeGoodsTwo.setText(searchResp.getObj().get(1).getName());
                            tvGoodsOneMoney.setText(searchResp.getObj().get(0).getRetailPrice().toString());//商品价格
                            tvGoodsTwoMoney.setText(searchResp.getObj().get(1).getRetailPrice().toString());
                            imageLoader.displayImage(HttpConstant.ROOT_PATH + searchResp.getObj().get(0).getPicUrl(), ivGoodsOnePic, ImageOptions.getDefaultOptions());//商品图片
                            imageLoader.displayImage(HttpConstant.ROOT_PATH + searchResp.getObj().get(1).getPicUrl(), ivGoodsTwoPic, ImageOptions.getDefaultOptions());

                        } else if (goodsInfoData.size() == 3) {
                            rlGoodsOne.setVisibility(View.GONE);
                            rlGoodsTwo.setVisibility(View.GONE);
                            tvHomeGoodsThree.setText(searchResp.getObj().get(0).getName());
                            tvHomeGoodsFour.setText(searchResp.getObj().get(1).getName());
                            tvHomeGoodsFive.setText(searchResp.getObj().get(2).getName());
                            tvGoodsThreeMoney.setText(searchResp.getObj().get(0).getRetailPrice().toString());
                            tvGoodsFourMoney.setText(searchResp.getObj().get(1).getRetailPrice().toString());
                            tvGoodsFiveMoney.setText(searchResp.getObj().get(2).getRetailPrice().toString());
                            imageLoader.displayImage(HttpConstant.ROOT_PATH + searchResp.getObj().get(0).getPicUrl(), ivGoodsThreePic, ImageOptions.getDefaultOptions());
                            imageLoader.displayImage(HttpConstant.ROOT_PATH + searchResp.getObj().get(1).getPicUrl(), ivGoodsFourPic, ImageOptions.getDefaultOptions());
                            imageLoader.displayImage(HttpConstant.ROOT_PATH + searchResp.getObj().get(2).getPicUrl(), ivGoodsFivePic, ImageOptions.getDefaultOptions());


                        } else if (goodsInfoData.size() >= 5) {
                            rlGoodsOne.setVisibility(View.VISIBLE);
                            rlGoodsTwo.setVisibility(View.VISIBLE);
                            rlGoodsThree.setVisibility(View.VISIBLE);
                            rlGoodsFour.setVisibility(View.VISIBLE);
                            rlGoodsFive.setVisibility(View.VISIBLE);
                            tvHomeGoodsOne.setText(searchResp.getObj().get(0).getName());//商品名
                            tvHomeGoodsTwo.setText(searchResp.getObj().get(1).getName());
                            tvHomeGoodsThree.setText(searchResp.getObj().get(2).getName());
                            tvHomeGoodsFour.setText(searchResp.getObj().get(3).getName());
                            tvHomeGoodsFive.setText(searchResp.getObj().get(4).getName());
                            tvGoodsOneMoney.setText(searchResp.getObj().get(0).getRetailPrice().toString());//商品价格
                            tvGoodsTwoMoney.setText(searchResp.getObj().get(1).getRetailPrice().toString());
                            tvGoodsThreeMoney.setText(searchResp.getObj().get(2).getRetailPrice().toString());
                            tvGoodsFourMoney.setText(searchResp.getObj().get(3).getRetailPrice().toString());
                            tvGoodsFiveMoney.setText(searchResp.getObj().get(4).getRetailPrice().toString());
                            imageLoader.displayImage(HttpConstant.ROOT_PATH + searchResp.getObj().get(0).getPicUrl(), ivGoodsOnePic, ImageOptions.getDefaultOptions());//商品图片
                            imageLoader.displayImage(HttpConstant.ROOT_PATH + searchResp.getObj().get(1).getPicUrl(), ivGoodsTwoPic, ImageOptions.getDefaultOptions());
                            imageLoader.displayImage(HttpConstant.ROOT_PATH + searchResp.getObj().get(2).getPicUrl(), ivGoodsThreePic, ImageOptions.getDefaultOptions());
                            imageLoader.displayImage(HttpConstant.ROOT_PATH + searchResp.getObj().get(3).getPicUrl(), ivGoodsFourPic, ImageOptions.getDefaultOptions());
                            imageLoader.displayImage(HttpConstant.ROOT_PATH + searchResp.getObj().get(4).getPicUrl(), ivGoodsFivePic, ImageOptions.getDefaultOptions());
                        }
                    }
                }
            }
        });
    }


}
