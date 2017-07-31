package yunju.com.huiqitian.vm.integral.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.IntegralGoods;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.entity.IntegralGoodsResp;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.vm.adapter.IntegralGoodsAdapter;
import yunju.com.huiqitian.vm.integral.model.IntegralModel;
import yunju.com.huiqitian.vm.login.view.LoginActivity;
import yunju.com.huiqitian.vm.menu.model.MenuPersonModel;
import yunju.com.huiqitian.vm.widget.PullToRefreshLayout;

/**
 * Created by liuGang on 2016/8/15 0015.
 */
public class IntegralStoreActivity extends BaseActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener, IntegralModel.IntegralStoreInterface {

    private IntegralModel integralModel;
    /*顶部导航分享*/
    private ImageView ivShare;
    /*顶部导航标题*/
    private TextView tvTitle;
    /*顶部导航返回*/
    private LinearLayout llTitleBack;
    /*总积分数*/
    private TextView tvIntegralNum;
    /*如何获取更多积分*/
    private TextView tvGainMore;
    /*兑换记录*/
    private TextView tvIntegralRecord;
    /*积分商品列表*/
    private GridView gVIntegral;
    /*积分商品类表刷新*/
    private PullToRefreshLayout pullVoucherList;

    private IntegralGoodsAdapter integralGoodsAdapter;

    private LinearLayout llShare;

    /*个人信息model*/
    private MenuPersonModel menuPersonModel;

    /*当前请求页*/
    private int curPage = 1;
    /*每次请求的数量*/
    private int requestNum = 20;
    /*积分商品集合*/
    private List<IntegralGoods> goodsList = new ArrayList<>();

    /*总积分*/
    private int store = 0;

    /*是否登录*/
    private int login;

    /*无数据*/
    private LinearLayout llNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_integral_store);
        LogUtils.error(IntegralStoreActivity.class,"onCreate");
    }

    @Override
    public void initBoot() {
        integralModel = new IntegralModel(activity);
        menuPersonModel = new MenuPersonModel(getApplicationContext());
        LogUtils.error(IntegralStoreActivity.class,"initBoot");
    }

    @Override
    public void initViews() {
        /*初始化控件*/
        ivShare = findView(R.id.iv_integral_share);
        tvTitle = findView(R.id.tv_title);
        llTitleBack = findView(R.id.iv_title_back);
        tvIntegralNum = findView(R.id.tv_integral_num);
        tvGainMore = findView(R.id.tv_integral_gainMore);
        tvIntegralRecord = findView(R.id.tv_integral_record);
        gVIntegral = findView(R.id.gv_integral);
        llShare = findView(R.id.ll_integral_share);
        llNoData = findView(R.id.ll_no_data);


        /*注册监听*/
        llShare.setOnClickListener(this);
        llTitleBack.setOnClickListener(this);
        tvGainMore.setOnClickListener(this);
        tvIntegralRecord.setOnClickListener(this);

        pullVoucherList = findView(R.id.pull_voucher_list);
        pullVoucherList.setOnRefreshListener(this);

        LogUtils.error(IntegralStoreActivity.class, "initViews");

    }

    @Override
    public void initData(Bundle bundle) {
        //请求我的积分
        menuPersonModel.getMyGetPoint();//积分
        LogUtils.error(IntegralStoreActivity.class, "initData");
    }

    @Override
    public void initEvents() {
        LogUtils.error(IntegralStoreActivity.class, "initEvents");
        /*积分商城列表监听*/
        gVIntegral.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (!MyUtils.checkUser()) {
                    //用户未登录就调到登陆界面
                    startActivity(LoginActivity.class);

                } else {
                    IntegralGoods itemAtPosition = (IntegralGoods) parent.getItemAtPosition(position);
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.TO_INTEGRAL_DETAIL, JSON.toJSONString(itemAtPosition));
                    bundle.putInt(Constant.MY_TOTAL_STORE, store);
                    startActivity(IntegralDetailActivity.class, bundle);
                }
               // IntegralStoreActivity.this.finish();
            }
        });

        /*设置顶部导航标题*/
        tvTitle.setText("积分商城");

        //这个方法的参数是调试参数
        integralModel.lookIntegralGoods(curPage, requestNum);

          /*获取我的钱包的数据*/
        menuPersonModel.setMyWalletInterface(new MenuPersonModel.MyWalletInterface() {
            @Override
            public void myGetWallet(String obj, String url) {
                LogUtils.error(IntegralStoreActivity.class, "获取积分的回调函数");
                /*判断是金币，余额，还是积分*/
                if (url.equals(HttpConstant.PATH_MY_GETCOIN)) {
                    //金币
                    // tvMyCoin.setText(obj);
                } else if (url.equals(HttpConstant.PATH_MY_GETPOINT)) {

                    //动态获取我的积分
                    store = Integer.parseInt(obj);
                    //积分
                    tvIntegralNum.setText(store + "");
                } else {
                    //余额接口（暂时没给）
                }
            }

            @Override
            public void failureGetWallet(String msg) {
                login = 2;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_integral_share:
                // showToast("马上分享给好友");
                break;
            case R.id.iv_title_back:
                this.finish();
                break;
            case R.id.tv_integral_gainMore:
                showToast("进入获取更多积分哦");
                break;
            case R.id.tv_integral_record:
                 /*兑换记录事件监听*/
                if (login == 2) {
                    startActivity(LoginActivity.class);
                    IntegralStoreActivity.this.finish();
                } else {
                    startActivity(ExchangeRecordActivity.class);
                }
                break;
        }
    }

    /*下拉刷新*/
    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        // 下拉刷新操作
        new Handler() {
            @Override
            public void handleMessage(Message msg) {

                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
            }
        }.sendEmptyMessageDelayed(0, 3000);
    }

    /*上拉加载更多*/
    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
        requestNum+=10;
        integralModel.lookIntegralGoods(curPage, requestNum);
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
            }
        }.sendEmptyMessageDelayed(0, 3000);
    }

    //查询积分商品列表成功后的回调方法
    @Override
    public void integralGoods(String resp) {
        LogUtils.error(IntegralStoreActivity.class, "integralGoods");
        //解析json数据将data传递到adapter中
        IntegralGoodsResp integralGoodsResp = parseObject(resp, IntegralGoodsResp.class);
        List<IntegralGoods> integralGoodsList = integralGoodsResp.getObj();
        if (integralGoodsList.isEmpty() || integralGoodsList == null) {
            //加载数据失败
            // pullVoucherList.loadmoreFinish(PullToRefreshLayout.FAIL);
            llNoData.setVisibility(View.VISIBLE);
            gVIntegral.setVisibility(View.GONE);

        } else {
            llNoData.setVisibility(View.GONE);
            gVIntegral.setVisibility(View.VISIBLE);
            if (integralGoodsAdapter == null) {
                integralGoodsAdapter = new IntegralGoodsAdapter(activity, integralGoodsList);
                gVIntegral.setAdapter(integralGoodsAdapter);
            } else {
                integralGoodsAdapter.notifyDataSetChanged();
                //加载成功刷新数据
                pullVoucherList.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        }
    }

    @Override
    public void integralGoodsList(String resp) {

    }

    /*没有登录回调的接口函数  跳到登陆界面*/
    @Override
    public void startToLoginAct() {
//                startActivity(LoginActivity.class);
//                IntegralStoreActivity.this.finish();
        login = 2;
    }

    @Override
    public void exchangeGoodsSuccess(String response) {

    }

    @Override
    protected void onResume() {
        LogUtils.error(IntegralStoreActivity.class, "onResume");
        menuPersonModel.getMyGetPoint();
        super.onResume();
    }
}
