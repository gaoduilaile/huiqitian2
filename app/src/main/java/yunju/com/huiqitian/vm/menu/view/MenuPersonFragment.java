package yunju.com.huiqitian.vm.menu.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.xutils.ex.DbException;

import java.util.List;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.AppApplication;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseFragment;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.db.dao.CollectDao;
import yunju.com.huiqitian.db.dao.HistoryDao;
import yunju.com.huiqitian.db.entity.Collect;
import yunju.com.huiqitian.db.entity.History;
import yunju.com.huiqitian.entity.AllOrders;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.entity.PersonMsgResp;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.vm.address.view.ReceiverAddressActivity;
import yunju.com.huiqitian.vm.history.view.HistoryActivity;
import yunju.com.huiqitian.vm.login.view.LoginActivity;
import yunju.com.huiqitian.vm.menu.model.MenuPersonModel;
import yunju.com.huiqitian.vm.my.view.PersonalMsgActivity;
import yunju.com.huiqitian.vm.opinion.view.OpinionActivity;
import yunju.com.huiqitian.vm.orders.model.ObtainOrdersModel;
import yunju.com.huiqitian.vm.orders.view.OrdersActivity;
import yunju.com.huiqitian.vm.set.view.SetActivity;
import yunju.com.huiqitian.vm.vouchers.view.VoucherActivity;
import yunju.com.huiqitian.vm.widget.CircleBitmapDisplayer;

/**
 * Created by liuGang on 2016/7/18 0018.
 */
public class MenuPersonFragment extends BaseFragment {


    /*控件相关*/
    //个人信息
    private LinearLayout llIntentPersonMsg;
    private ImageView ivMySet;//设置
    private ImageView ivMyUserHead;//头像
    private TextView tvMyName;//用户名
    private TextView tvMyDes;//描述（签名）
    private TextView tvMyDesTwo;//描述（签名）
    private LinearLayout llNameMobileOne;
    private ImageView ivMyMoreDes;//更多个人信息
    private TextView tvMyGoodsCount;//收藏的商品数量
    private TextView tvMyFeetCount;//浏览的足迹
    private LinearLayout lyMyFeet;//足迹按钮的点击事件
    private LinearLayout lyMyCollect;//收藏
    //我的订单
    private TextView tvMyIndent;//查看全部订单
    private RelativeLayout tvMyPay;//待付款
    private RelativeLayout tvMySend;//待发货
    private RelativeLayout tvMyTake;//待收货
    private RelativeLayout tvMyEvaluate;//待评价

    private TextView tvMyPayTop;//待付款top的数量
    private TextView tvMySendTop;//待发货top的数量
    private TextView tvMyTakeTop;//待收货top的数量
    private TextView tvMyEvaluateTop;//待评价top的数量

    //我的订单数量
    private int num1;//待付款数量
    private int num3;//待发货数量
    private int num4;//待收货数量
    private int num5;//待评价数量


    /*所有订单的model*/
    private ObtainOrdersModel obtainOrdersModel;
    private ObtainOrdersModel.AllOrdersInterface allOrdersInterface;

    //我的钱包
    private TextView tvMyIntegral;//积分
    private TextView tvMyCoin;//金币
    //代金卷
    private RelativeLayout rlMyMoreVoucher;//代金卷
    private TextView tvMyVoucher;//未使用的代金卷
    private TextView tvMyOutVoucher;//过期的代金卷
    private TextView tvMyUseVoucher;//已经使用的代金卷
    //其他
    private RelativeLayout ivMyMoreAddress;//收货地址
    private RelativeLayout ivMyMoreSuggest;//意见反馈
    private RelativeLayout ivMyMoreEvaluate;//欢迎评价

    /*跳转要显示fragment*/
    private Bundle bundle;

    /*个人信息model*/
    private MenuPersonModel menuPersonModel;


    /*图片加载类*/
    private ImageLoader imageLoader = ImageLoader.getInstance();

    /*数据库相关*/
    private HistoryDao historyDao;
    private CollectDao collectDao;

    /*初始化（我的）布局文件填充到fragment*/
    @Override
    public View bootView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_menu_person, viewGroup, false);
    }


    @Override
    public void initBoot() {
        obtainOrdersModel = new ObtainOrdersModel(getContext());
        menuPersonModel = new MenuPersonModel(getContext());
        bundle = new Bundle();
        historyDao = new HistoryDao();
        collectDao = new CollectDao();
    }


    @Override
    public void initViews() {
        /*控件实例化*/
        //个人信息
        ivMySet = findView(R.id.iv_my_set);
        ivMyUserHead = findView(R.id.iv_my_user_head);
        tvMyName = findView(R.id.tv_my_name);
        tvMyDes = findView(R.id.tv_my_des);
        tvMyDesTwo = findView(R.id.tv_my_des_two);
        llNameMobileOne = findView(R.id.ll_name_mobile_one);
        ivMyMoreDes = findView(R.id.iv_my_more_des);
        tvMyGoodsCount = findView(R.id.tv_my_goods_count);
        tvMyFeetCount = findView(R.id.tv_my_feet_count);
        lyMyFeet = findView(R.id.ly_my_feet);
        lyMyCollect = findView(R.id.ly_my_collect);
        //我的订单
        tvMyIndent = findView(R.id.tv_my_indent);
        tvMyPay = findView(R.id.tv_my_pay);
        tvMySend = findView(R.id.tv_my_send);
        tvMyTake = findView(R.id.tv_my_take);
        tvMyEvaluate = findView(R.id.tv_my_evaluate);

        //我的订单对应的每个条目上的数量
        tvMyPayTop = findView(R.id.tv_my_pay_top);
        tvMySendTop = findView(R.id.tv_my_send_top);
        tvMyTakeTop = findView(R.id.tv_my_take_top);
        tvMyEvaluateTop = findView(R.id.tv_my_evaluate_top);


        //我的钱包
        tvMyIntegral = findView(R.id.tv_my_integral);
        tvMyCoin = findView(R.id.tv_my_coin);
        //代金卷
        rlMyMoreVoucher = findView(R.id.rl_my_more_voucher);
        tvMyVoucher = findView(R.id.tv_my_voucher);
        tvMyOutVoucher = findView(R.id.tv_my_out_voucher);
        tvMyUseVoucher = findView(R.id.tv_my_use_voucher);
        //其他
        ivMyMoreAddress = findView(R.id.iv_my_more_address);
        ivMyMoreSuggest = findView(R.id.iv_my_more_suggest);
        llIntentPersonMsg = findView(R.id.ll_intent_person_msg);
    }

    @Override
    public void initData(Bundle bundle) {
        LogUtils.error(MenuPersonFragment.class, "data");
        if (isLogin()) {

            if (noNetWork()) {
                showNetWorkError();
            }else {
                getData();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.error(MenuPersonFragment.class,"销毁时走的逻辑");
        getData();
    }

    @Override
    public void initEvents() {
        LogUtils.error(MenuPersonFragment.class,"events");
        llIntentPersonMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 /*判断用户是否登录若没登录跳转到登录界面，否则跳转到用户个人信息界面*/
                if (isLogin()) {
                    //startActivity(PersonalMsgActivity.class);
                    startActivityForResult(new Intent(getContext(),PersonalMsgActivity.class),100);
                } else {
                    startActivity(LoginActivity.class);
                }
            }
        });

        /*点击个人信息跳转到个人信息*/
        ivMyMoreDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 /*判断用户是否登录若没登录跳转到登录界面，否则跳转到用户个人信息界面*/
                if (isLogin()) {
                    startActivity(PersonalMsgActivity.class);
                } else {
                    startActivity(LoginActivity.class);
                }
            }
        });

        /*当可点击时，点击登录注册跳转到登录界面*/
        if (llNameMobileOne.isClickable()) {
            llNameMobileOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(LoginActivity.class);
                }
            });
        }

        /*跳转设置页面*/
        ivMySet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin()) {
                    startActivity(SetActivity.class);
                } else {
                    startActivity(LoginActivity.class);
                }
            }
        });

         /*获取订单信息*/
        obtainOrdersModel.setAllOrdersInterface(new ObtainOrdersModel.AllOrdersInterface() {
            @Override
            public void allOrdersListSuccess(List<AllOrders> allOrdersRespList) {

                for (AllOrders allOrders : allOrdersRespList) {
                    int orderState = (int) allOrders.getOrderState();
                    if (orderState == 1) {
                        num1++;
                    } else if (orderState == 3) {
                        num3++;
                    } else if (orderState == 4) {
                        num4++;
                    } else if (orderState == 5) {
                        num5++;
                    }
                }
                //给我的订单下面的每个条目 添加数量
                tvMyPayTop.setText(num1 + "");
                tvMySendTop.setText(num3 + "");
                tvMyTakeTop.setText(num4 + "");
                tvMyEvaluateTop.setText(num5 + "");


                if (num1 > 0) {
                    tvMyPayTop.setVisibility(View.VISIBLE);
                }
                if (num3 > 0) {
                    tvMySendTop.setVisibility(View.VISIBLE);
                }
                if (num4 > 0) {
                    tvMyTakeTop.setVisibility(View.VISIBLE);
                }
                if (num5 > 0) {
                    tvMyEvaluateTop.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void allOrdersListFailure(String msg) {
            }
        });

        /*获取用户信息*/
        menuPersonModel.setPersonMsgInterface(new MenuPersonModel.PersonMsgInterface() {
            @Override
            public void personMsg(PersonMsgResp personMsgResp) {

                LogUtils.error(MenuPersonFragment.class,"重新跑了获取用户信息");

                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .displayer(new CircleBitmapDisplayer())
                        .build();

                /*加载头像*/
                imageLoader.displayImage(HttpConstant.ROOT_PATH + personMsgResp.getPicUrl(),
                        ivMyUserHead, options);
                /*昵称*/

                if (personMsgResp.getNickName() == null || personMsgResp.getNickName() == "") {
                    tvMyDesTwo.setVisibility(View.VISIBLE);
                    llNameMobileOne.setVisibility(View.GONE);

                    tvMyDesTwo.setText(personMsgResp.getMobile());
                } else {
                    tvMyDesTwo.setVisibility(View.GONE);
                    llNameMobileOne.setVisibility(View.VISIBLE);

                    tvMyName.setText(personMsgResp.getNickName());

                 /*账号*/
                    tvMyDes.setText(personMsgResp.getMobile());
                }

//                tvMyName.setText(personMsgResp.getNickName() != null && personMsgResp.getNickName() != "" ? personMsgResp.getNickName() : "当前用户无昵称");

            }

            @Override
            public void noPersonMsg(String msg) {
                LogUtils.error(MenuPersonFragment.class, "noPersonMsg：" + msg);
            }

            @Override
            public void noLoginMsg() {
            }
        });

        /*获取我的钱包的数据*/
        menuPersonModel.setMyWalletInterface(new MenuPersonModel.MyWalletInterface() {
            @Override
            public void myGetWallet(String obj, String url) {
                /*判断是金币，余额，还是积分*/
                if (url.equals(HttpConstant.PATH_MY_GETCOIN)) {
                    //金币
                    tvMyCoin.setText(obj);
                } else if (url.equals(HttpConstant.PATH_MY_GETPOINT)) {
                    //积分
                    tvMyIntegral.setText(obj);
                } else {
                    //余额接口（暂时没给）
                }
            }

            @Override
            public void failureGetWallet(String msg) {

            }
        });

        /*获取代金券*/
        menuPersonModel.setMyVoucherntIerface(new MenuPersonModel.MyVoucherInterface() {
            @Override
            public void myVouchers(String voucherSize, int status) {
                Log.e("MenuPersonFragment", "代金券个数：" + voucherSize);
                switch (status) {
                    case Constant.UNUSE_VOUCHER:
                        //未使用
                        tvMyVoucher.setText(voucherSize);
                        break;
                    case Constant.USE_VOUCHER:
                        //已使用
                        tvMyUseVoucher.setText(voucherSize);
                        break;
                    case Constant.OVERDUE_VOUCHER:
                        //已过期
                        tvMyOutVoucher.setText(voucherSize);
                        break;
                }
            }

            @Override
            public void failureGetVouchers(String msg) {

            }
        });

        /*我的全部订单*/
        tvMyIndent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin()) {
                    //跳转到全部订单页面
                    bundle.putInt(Constant.ORDERS_MARKS, Constant.ALL_ORDERS);
                    startActivity(OrdersActivity.class, bundle);
                    num1 = 0;
                    num3 = 0;
                    num4 = 0;
                    num5 = 0;
                } else {
                    startActivity(LoginActivity.class);

                }
            }
        });

        /*待付款*/
        tvMyPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin()) {
                    //跳转到待付款页面
                    bundle.putInt(Constant.ORDERS_MARKS, Constant.WAITE_PAY);
                    startActivity(OrdersActivity.class, bundle);
                    num1 = 0;
                    num3 = 0;
                    num4 = 0;
                    num5 = 0;
                } else {
                    startActivity(LoginActivity.class);
                }
            }
        });

        /*待发货*/
        tvMySend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin()) {
                    //跳转到待发货页面
                    bundle.putInt(Constant.ORDERS_MARKS, Constant.WAITE_SEND);
                    startActivity(OrdersActivity.class, bundle);
                    num1 = 0;
                    num3 = 0;
                    num4 = 0;
                    num5 = 0;
                } else {
                    startActivity(LoginActivity.class);
                }
            }
        });

        /*待收货*/
        tvMyTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin()) {
                    //跳转到待收货页面
                    bundle.putInt(Constant.ORDERS_MARKS, Constant.WAITE_GET);
                    startActivity(OrdersActivity.class, bundle);
                    num1 = 0;
                    num3 = 0;
                    num4 = 0;
                    num5 = 0;
                } else {
                    startActivity(LoginActivity.class);
                }
            }
        });

        /*待评价*/
        tvMyEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin()) {
                    //跳转到待评价页面
                    bundle.putInt(Constant.ORDERS_MARKS, Constant.WAITE_EVALUATE);
                    startActivity(OrdersActivity.class, bundle);
                    num1 = 0;
                    num3 = 0;
                    num4 = 0;
                    num5 = 0;
                } else {
                    startActivity(LoginActivity.class);
                }
            }
        });

        /*代金券页面*/
        rlMyMoreVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin()) {
                    //跳转代金券页面,首次加载未使用(传值为1)
                    bundle.putInt(Constant.VOUCHER_MARKS, Constant.ONE);
                    startActivity(VoucherActivity.class, bundle);
                } else {
                    startActivity(LoginActivity.class);
                }
            }
        });

        /*点击跳转到收货地址页面*/
        ivMyMoreAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin()) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.START_NEXT_KEY, "address");
                    startActivity(ReceiverAddressActivity.class, bundle);
                } else {
                    startActivity(LoginActivity.class);
                }
            }
        });

        /*跳转到意见反馈页面*/
        ivMyMoreSuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin()) {
                    startActivity(OpinionActivity.class);
                } else {
                    startActivity(LoginActivity.class);
                }
            }
        });

        /*足迹*/
        lyMyFeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.START_WHERE, Constant.START_FORM_HISTORY);
                startActivity(HistoryActivity.class, bundle);
            }
        });

        /*收藏*/
        lyMyCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.START_WHERE, Constant.START_FROM_PERSON);
                startActivity(HistoryActivity.class, bundle);
            }
        });
    }

    /**
     * 判断用户是否登录
     *
     * @return 登录返回true
     */
    private boolean isLogin() {

        if (MyUtils.checkUser()) {
            return true;
        }
        return false;
    }
    /**
     * 当界面重新展示时（fragment.show）,调用Eequest刷新界面
     */
    @Override
    public void onResume() {
        super.onResume();
        if (AppApplication.getBoolean("person_fragment_refresh")) {
            LogUtils.error(MenuPersonFragment.class,"进入刷新页面个人中心");
            //判断用户是否登录
            if (isLogin()) {
                getData();
            } else {
                tvMyDesTwo.setVisibility(View.VISIBLE);
                llNameMobileOne.setVisibility(View.GONE);
                LogUtils.error(MenuPersonFragment.class,"999999999999999999999999999999999999999999999999999999999999");
            /*设置登录按钮可点击*/
                LogUtils.error(MenuPersonFragment.class, "没有登陆");
                llNameMobileOne.setClickable(true);
                tvMyDesTwo.setText("请点击登录");
                tvMyGoodsCount.setText("0");
                tvMyFeetCount.setText("0");
                tvMyPayTop.setVisibility(View.GONE);
                tvMySendTop.setVisibility(View.GONE);
                tvMyTakeTop.setVisibility(View.GONE);
                tvMyEvaluateTop.setVisibility(View.GONE);
                tvMyVoucher.setText("0");
                tvMyOutVoucher.setText("0");
                tvMyUseVoucher.setText("0");
                tvMyIntegral.setText("0");
                tvMyCoin.setText("0");
            }
            AppApplication.putBoolean("person_fragment_refresh", false);
            LogUtils.error(MenuPersonFragment.class, "8555555555555555555555555555555555555555555555555555555");
        }
    }


    /**
     * 处理数据
     */
    public void getData() {
        LogUtils.error(MenuPersonFragment.class,"getData");
        /*请求我的钱包数据*/
        menuPersonModel.getMyGetCoin();//金币
        menuPersonModel.getMyGetPoint();//积分
        /*代金券*/
        menuPersonModel.getVoucher(Constant.UNUSE_VOUCHER);//未使用
        menuPersonModel.getVoucher(Constant.OVERDUE_VOUCHER);//已过期
        menuPersonModel.getVoucher(Constant.USE_VOUCHER);//已使用
        num1 = 0;
        num3 = 0;
        num4 = 0;
        num5 = 0;

         /*足迹、收藏*/
        try {
            List<History> history = historyDao.select();

            if (history != null && history.size() > 0) {
                tvMyFeetCount.setText(history.size() + "");
            } else {
                tvMyFeetCount.setText(Constant.ZERO + "");
            }
            List<Collect> collect = collectDao.select();
            if (collect != null) {
                if (collect.size() > 0) {
                    tvMyGoodsCount.setText(collect.size() + "");
                } else {
                    tvMyGoodsCount.setText(Constant.ZERO + "");
                }
            }
       /* try {
            tvMyFeetCount.setText(historyDao.select().size()+"");
            //LogUtils.error(MenuHomeFragment.class,collectDao.select().size() + "1212");
            tvMyGoodsCount.setText(collectDao.select().size() + "");*/
        } catch (DbException e) {
            e.printStackTrace();
        }

         /*设置登录按钮不可点击*/
        tvMyName.setClickable(false);

            /*tvMyName.setText(MyUtils.getPersonName());*/

            /*判断在手机是否有缓存用户信息*/
        if (TextUtils.isEmpty(MyUtils.getPersonPhone())) {
            menuPersonModel.getPersonMsg();
        } else {
            /*加载头像*/
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .displayer(new CircleBitmapDisplayer())
                    .build();
            imageLoader.displayImage("file://" + MyUtils.getHeadPath(), ivMyUserHead, options);
               /* imageLoader.displayImage(HttpConstant.ROOT_PATH + MyUtils.getPersonPicUrl(),
                        ivMyUserHead,options);*/

             /*昵称*/
            if (MyUtils.getPersonNickname() == null || MyUtils.getPersonNickname() == "") {
                tvMyDesTwo.setVisibility(View.VISIBLE);
                llNameMobileOne.setVisibility(View.GONE);

                tvMyDesTwo.setText(MyUtils.getPersonName());

            } else {
                tvMyDesTwo.setVisibility(View.GONE);
                llNameMobileOne.setVisibility(View.VISIBLE);

                tvMyName.setText(MyUtils.getPersonNickname());
                    /*账号*/
                tvMyDes.setText(MyUtils.getPersonName());
            }
//                tvMyName.setText(MyUtils.getPersonNickname() != null && MyUtils.getPersonNickname() != "" ? MyUtils.getPersonNickname() : "当前用户无昵称");
//                LogUtils.error(PersonalMsgActivity.class, "9999999999999999999999999999999999" + MyUtils.getPersonNickname());
//
//            /*账号*/
//                tvMyDes.setText(MyUtils.getPersonName());
        }
            /*订单的刷新*/
            /*请求订单数据*/
        obtainOrdersModel.getOrders(Constant.ZERO);

            /* 获取订单信息*/
        obtainOrdersModel.setAllOrdersInterface(new ObtainOrdersModel.AllOrdersInterface() {
            @Override
            public void allOrdersListSuccess(List<AllOrders> allOrdersRespList) {

                for (AllOrders allOrders : allOrdersRespList) {
                    int orderState = (int) allOrders.getOrderState();
                    if (orderState == 1) {
                        num1++;
                    } else if (orderState == 3) {
                        num3++;
                    } else if (orderState == 4) {
                        num4++;
                    } else if (orderState == 5) {
                        num5++;
                    }
                }
                //给我的订单下面的每个条目 添加数量
                tvMyPayTop.setText(num1 + "");
                tvMySendTop.setText(num3 + "");
                tvMyTakeTop.setText(num4 + "");
                tvMyEvaluateTop.setText(num5 + "");


                if (num1 > 0) {
                    tvMyPayTop.setVisibility(View.VISIBLE);
                } else {
                    tvMyPayTop.setVisibility(View.INVISIBLE);
                }
                if (num3 > 0) {
                    tvMySendTop.setVisibility(View.VISIBLE);
                } else {
                    tvMySendTop.setVisibility(View.INVISIBLE);
                }
                if (num4 > 0) {
                    tvMyTakeTop.setVisibility(View.VISIBLE);
                } else {
                    tvMyTakeTop.setVisibility(View.INVISIBLE);
                }
                if (num5 > 0) {
                    tvMyEvaluateTop.setVisibility(View.VISIBLE);
                } else {
                    tvMyEvaluateTop.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void allOrdersListFailure(String msg) {
            }
        });
    }
}
