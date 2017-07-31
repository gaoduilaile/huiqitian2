package yunju.com.huiqitian.vm.orders.model;

import android.content.Context;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.OrderDetailed;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.OrderDetailedResp;
import yunju.com.huiqitian.http.entity.OrderIdReq;

/**
 * Created by 张超群 on 2016-08-16.
 * <p/>
 * 订单详细的接口
 */
public class OrderDetailedModel extends BaseModel {

    private OrderDetailInterface orderDetailInterface;

    public OrderDetailedModel(Context context) {
        super(context);
        orderDetailInterface = (OrderDetailInterface) context;
    }


    /**
     * 获取订单详情
     *
     * @param id 订单号
     */
    public void getOrderDetail(String id) {
        OrderIdReq orderIdReq = new OrderIdReq();
        orderIdReq.setId(id);

        sendGet(HttpConstant.PATH_ORDER_DETAIL, orderIdReq, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(OrderDetailedModel.class, "订单详细信息：" + response);
                OrderDetailedResp orderDetailedResp = parseObject(response, OrderDetailedResp.class);
                switch (orderDetailedResp.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        orderDetailInterface.orderDetailDateSuccess(orderDetailedResp.getObj());
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        orderDetailInterface.orderDetailDateFailure(orderDetailedResp.getMsg());
                        break;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpLoadingDialog.dismiss();
            }
        });
    }


    /*订单详细信息获取回调*/
    public interface OrderDetailInterface {
        void orderDetailDateSuccess(OrderDetailed orderDetailed);

        /*void orderDetailAddressSuccess(OrderDetailAddress orderDetailAddress);*/

        void orderDetailDateFailure(String msg);
    }
}
