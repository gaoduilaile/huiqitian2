package yunju.com.huiqitian.vm.pay.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.HashMap;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.entity.WeiXinReq;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.utils.pay.AliPayUtils;
import yunju.com.huiqitian.vm.orders.view.OrderDetailedActivity;
import yunju.com.huiqitian.vm.pay.model.PayModel;

/**
 * 支付页面。包括支付宝和微信支付
 * Created by CCTV-1 on 2017/1/11 0025.
 */
public class PayActivity extends BaseActivity {

    /*title*/
    private LinearLayout ivTitleBack;//返回键
    private TextView tvTitle;//标题

    private TextView tvPayMoney;//要支付的钱数
    private TextView tvPayOtherMoney;//小数点之后的钱数
    private LinearLayout llZhiFuBao;//点击支付宝的总布局
    private ImageView ivZhiFuBao;//是否选中
    private LinearLayout llWeiXin;//微信支付宝总布局
    private ImageView ivWeiXin;//是否选中
    private Button btnNowPay;//立即支付

    /*选择时候的标志位*/
    private int zhifubaoMark = 0;
    private int weixinMark = 0;

    /*支付宝起调model*/
    private PayModel payModel;

    /*支付宝支付*/
    private AliPayUtils aliPayUtils;

    /*总价格*/
    private String[] priceStr;
    /*总的订单*/
    private String[] orderIds;

    /**
     * 注册微信
     */
    private IWXAPI api;
    public static PayActivity payActivity;

    private void regToWx() {
        api = WXAPIFactory.createWXAPI(this, Constant.WECHAT_APPID, true);
        api.registerApp(Constant.WECHAT_APPID);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_pay);
         /*注册微信*/
        regToWx();
        payActivity = this;
    }

    @Override
    public void initBoot() {
        payModel = new PayModel(activity);
        aliPayUtils = new AliPayUtils(activity);
    }

    @Override
    public void initViews() {
        /*title*/
        ivTitleBack = findView(R.id.iv_title_back);
        tvTitle = findView(R.id.tv_title);

        tvPayMoney = findView(R.id.tv_pay_money);
        tvPayOtherMoney = findView(R.id.tv_pay_other_money);
        llZhiFuBao = findView(R.id.ll_zhi_fu_bao);
        ivZhiFuBao = findView(R.id.iv_zhi_fu_bao);
        llWeiXin = findView(R.id.ll_wei_xin);
        ivWeiXin = findView(R.id.iv_wei_xin);
        btnNowPay = findView(R.id.btn_now_pay);
    }

    @Override
    public void initData(Bundle bundle) {
        tvTitle.setText(R.string.sure_pay);
        ivZhiFuBao.setBackgroundResource(R.mipmap.xuanzhong);
        zhifubaoMark = 1;
        priceStr = new String[2];
        priceStr = bundle.getStringArray(Constant.PAY_PRICE);
        if (priceStr.length > 0) {
            tvPayMoney.setText(priceStr[0]);
            tvPayOtherMoney.setText(priceStr[1]);
        }
        orderIds = new String[bundle.getInt(Constant.PAY_LENGTH)];
        orderIds = bundle.getStringArray(Constant.PAY_ORDERS);

        /*将订单编号保存在本地*/
        String s = orderIds[0];
        LogUtils.error(PayActivity.class,"订单编号+"+s);
        MyUtils.savePayId(s);
    }

    @Override
    public void initEvents() {
        /*返回键响应事件*/
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.ORDER_GOOD_ID, orderIds[0]);
                bundle.putInt(Constant.DELETE_ORDER, 6);
                startActivity(OrderDetailedActivity.class, bundle);
                finish();
            }
        });

        /*点击支付宝布局*/
        llZhiFuBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhifubaoMark++;
                if (zhifubaoMark == 1) {
                    ivZhiFuBao.setBackgroundResource(R.mipmap.xuanzhong);
                    weixinMark = 0;
                } else {
                    ivZhiFuBao.setBackgroundResource(R.mipmap.weixuanzhong);
                    zhifubaoMark = 0;
                }
                ivWeiXin.setBackgroundResource(R.mipmap.weixuanzhong);
            }
        });


        /*微信支付总布局*/
        llWeiXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weixinMark++;
                if (weixinMark == 1) {
                    ivWeiXin.setBackgroundResource(R.mipmap.xuanzhong);
                    zhifubaoMark = 0;
                } else {
                    ivWeiXin.setBackgroundResource(R.mipmap.weixuanzhong);
                    weixinMark = 0;
                }
                ivZhiFuBao.setBackgroundResource(R.mipmap.weixuanzhong);
            }
        });

        /*立即支付按钮*/
        btnNowPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (weixinMark == 1) {
                    payModel.weiXinPay(orderIds);
                }
                if (zhifubaoMark == 1) {
                    payModel.getPayResult(orderIds);
                }
            }
        });
        /**
         * 微信的回调接口
         */
        payModel.setWeiXinInterface(new PayModel.WeiXinInterface() {
            @Override
            public void weiXinPay(WeiXinReq.ObjBean result) {
                PayReq payReq = new PayReq();
                payReq.appId = result.getAppid();
                payReq.partnerId = result.getPartnerid();
                payReq.prepayId = result.getPrepayid();
                payReq.nonceStr = result.getNoncestr();
                payReq.packageValue = "Sign=WXPay";
                payReq.timeStamp = result.getTimestamp();
                payReq.sign = result.getSign();
                api.sendReq(payReq);

            }
        });


          /*支付宝回调接口*/
        payModel.setPayInterface(new PayModel.PayInterface() {
            @Override
            public void payResult(String result) {
                Log.e("setPayInterface", "请求结果：" + result);
                  /*起调支付宝支付*/
                aliPayUtils.payGood(result, new AliPayUtils.AliPlayInterface() {
                    @Override
                    public void fail() {

                    }

                    @Override
                    public void success(String result, HashMap<String, String> map) {
                        Log.e("setPayInterface", "支付结果：" + result);
                        Log.e("setPayInterface", "支付集合：" + map.size());
                        /*如果用户需要支付另一个超市的时候，需要重新起调支付宝*/
                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.ORDER_GOOD_ID, orderIds[0]);
                        bundle.putInt(Constant.DELETE_ORDER, 6);
                        startActivity(OrderDetailedActivity.class, bundle);
                        finish();

                    }

                    @Override
                    public void ensureing() {

                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.ORDER_GOOD_ID, orderIds[0]);
        bundle.putInt(Constant.DELETE_ORDER, 6);
        startActivity(OrderDetailedActivity.class, bundle);
        finish();
    }

}
