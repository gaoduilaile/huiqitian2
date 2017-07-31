package yunju.com.huiqitian.vm.pay.model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.CommonResponse;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.WeiXinReq;

/**
 * Created by 张超群 on 2016-08-11.
 * <p/>
 * 支付model
 */
public class PayModel extends BaseModel {


    private WeiXinInterface weiXinInterface;

    public PayModel(Context context) {
        super(context);
    }

    private PayInterface payInterface;

    /*支付的set接口*/
    public void setPayInterface(PayInterface payInterface) {
        this.payInterface = payInterface;
    }


    /*支付的地址，要移走*/

    public void getPayResult(String[] id) {
        List<String> ids = new ArrayList<>();
        for (int i=0;i<id.length;i++){
            ids.add(id[i]);
        }
        sendPost(HttpConstant.PATH_PAY_URL, ids, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                Log.e("MenuPersonModel", "支付result：" + response);
                CommonResponse commonResponse = parseObject(response, CommonResponse.class);
                Log.e("MenuPersonModel", "支付result：" + commonResponse.getObj());
                switch (commonResponse.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        payInterface.payResult(commonResponse.getObj());
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）

                        break;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
                LogUtils.error(PayModel.class,"支付失败支付失败支付失败支付失败支付失败支付失败支付失败");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpLoadingDialog.dismiss();
            }
        });

    }

    /**
     * 支付订单编号
     */
    public class PayResult {
        /**
         * 订单编号
         */
        private String[] id;

        public String[] getId() {
            return id;
        }

        public void setId(String[] id) {
            this.id = id;
        }
    }


    /**
     * 支付的回调接口
     */
    public interface PayInterface {
        void payResult(String result);
    }

    /**
     * 微信的回调接口
     */
    public void setWeiXinInterface(WeiXinInterface weiXinInterface){
        this.weiXinInterface = weiXinInterface;
    }
    public interface WeiXinInterface{
        void weiXinPay(WeiXinReq.ObjBean result);
    }


    /**
     * 微信支付
     */
    public void weiXinPay(String[] id){
        /*传进来的id遍历加入集合中*/
        List<String> ids = new ArrayList<>();
        for (int i=0;i<id.length;i++){
            ids.add(id[i]);
        }
        sendPost(HttpConstant.PATH_WEI_XIN,ids,new HttpHandler(){
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(PayModel.class,"微信支付结果"+response);
                /*解析数据*/
                WeiXinReq weiXinReq = parseObject(response, WeiXinReq.class);
                WeiXinReq.ObjBean obj = weiXinReq.getObj();
                switch (weiXinReq.getState()){
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        weiXinInterface.weiXinPay(obj);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）

                        break;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
                LogUtils.error(PayModel.class, "微信支付结果失败" );
            }
        });

    }


    //region 支付宝测试 放在要支付的页面
        /*支付宝测试*/
//        menuPersonModel.setPayInterface(new MenuPersonModel.PayInterface() {
//            @Override
//            public void payResult(String result) {
//                Log.e("setPayInterface","请求结果："+result);
                  /*起调支付宝支付*/
//                aliPayUtils.payGood(result, new AliPayUtils.AliPlayInterface() {
//                    @Override
//                    public void fail() {
//
//                    }
//
//                    @Override
//                    public void success(String result, HashMap<String, String> map) {
//                        Log.e("setPayInterface","支付结果："+result);
//                        Log.e("setPayInterface","支付集合："+map.size());
//                    }
//
//                    @Override
//                    public void ensureing() {
//
//                    }
//                });
//            }
//        });
    //endregion
}
