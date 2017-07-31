package yunju.com.huiqitian.vm.orders.model;

import android.content.Context;

import java.util.List;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.Order;
import yunju.com.huiqitian.entity.OrdersIds;
import yunju.com.huiqitian.http.CommonResponse;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.NowSubmitOrderReq;
import yunju.com.huiqitian.http.entity.SubmitOrderReq;

/**
 * Created by liuGang on 2016/8/12 0012.
 */
public class AffirmOrderModel extends BaseModel {

    private OrderGoPayInterface orderGoPayInterface;

    public AffirmOrderModel(Context context) {
        super(context);
        orderGoPayInterface = (OrderGoPayInterface) context;
    }

    /**
     * 购物车去结算
     */
    public void submitOrder(SubmitOrderReq submitOrderReq) {
        sendPost(HttpConstant.PATH_ORDER_ADD, submitOrderReq, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(AffirmOrderModel.class, "jsonsub" + response);
                /*CommonResponse commonResponse = parseObject(response, CommonResponse.class);*/
                OrdersIds ordersIds = parseObject(response, OrdersIds.class);
               /* LogUtils.error(AffirmOrderModel.class, "ordersIds:" + ordersIds.getObj()[0]);*/
                switch (ordersIds.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        orderGoPayInterface.orderCreatSuccess(ordersIds.getObj());
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        orderGoPayInterface.orderCreatFailure(ordersIds.getMsg());
                        break;
                }

            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
                LogUtils.error(AffirmOrderModel.class, "失败————购物车购买——");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpLoadingDialog.dismiss();
            }
        });
    }

    /**
     * 立即购买
     */
    public void nowSubmitOrder(NowSubmitOrderReq nowSubmitOrderReq){

        sendGet(HttpConstant.PATH_NOW_BUY_SUBMITORDER,nowSubmitOrderReq,new HttpHandler(){
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                /*LogUtils.error(AffirmOrderModel.class, "333333333333333333333jsonsub" + response);*/
                CommonResponse commonResponse = parseObject(response, CommonResponse.class);
                OrdersIds ordersIds = parseObject(response, OrdersIds.class);
               /* LogUtils.error(AffirmOrderModel.class, "33333333333333333333ordersIds:" + ordersIds.getObj()[0]);*/
                switch (ordersIds.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        orderGoPayInterface.orderCreatSuccess(ordersIds.getObj());
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        orderGoPayInterface.orderCreatFailure(commonResponse.getMsg());
                        break;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
                LogUtils.error(AffirmOrderModel.class, "失败——立即购买————");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpLoadingDialog.dismiss();
            }
        });
    }

    /*订单创建成功后去起调 支付宝接口*/
    public interface OrderGoPayInterface {
        void orderCreatSuccess(List<Order> list);

        void orderCreatFailure(String msg);
    }
}
