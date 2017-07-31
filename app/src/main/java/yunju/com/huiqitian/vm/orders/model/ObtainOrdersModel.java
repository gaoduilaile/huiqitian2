package yunju.com.huiqitian.vm.orders.model;

import android.content.Context;

import java.util.List;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.AllOrders;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.AllOrdersResp;
import yunju.com.huiqitian.http.entity.OrdersReq;

/**
 * Created by 张超群 on 2016-08-08.
 * <p/>
 * 获取订单的model
 */
public class ObtainOrdersModel extends BaseModel {


    public ObtainOrdersModel(Context context) {
        super(context);
        //allOrdersInterface = (AllOrdersInterface) context;
    }

    private AllOrdersInterface allOrdersInterface;

    public void setAllOrdersInterface(AllOrdersInterface allOrdersInterface) {
        this.allOrdersInterface = allOrdersInterface;
    }

    /**
     * 获取所有的订单
     */
    public void getOrders(int type) {
        OrdersReq ordersReq = new OrdersReq();
        ordersReq.setType((byte) type);
        sendGet(HttpConstant.PATH_ALL_ORDERS, ordersReq, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                AllOrdersResp allOrdersResp = parseObject(response, AllOrdersResp.class);
                LogUtils.error(ObtainOrdersModel.class, "所有商品的集合：" + allOrdersResp);
                switch (allOrdersResp.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        allOrdersInterface.allOrdersListSuccess(allOrdersResp.getObj());
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        allOrdersInterface.allOrdersListFailure(allOrdersResp.getMsg());
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


    /**
     * 所有订单的回调接口
     */
    public interface AllOrdersInterface {
        void allOrdersListSuccess( List<AllOrders> allOrdersRespList);

        void allOrdersListFailure(String msg);
    }
}
