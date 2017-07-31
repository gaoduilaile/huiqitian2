package yunju.com.huiqitian.vm.orders.model;

import android.content.Context;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.CommonResponse;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.OrderIdReq;

/**
 * Created by 张超群 on 2016-08-05.
 * <p/>
 * 全部订单
 */
public class DeleteAndCancelOrdersModel extends BaseModel {


    /*删除订单接口*/
    private DeleteAndCancelOrderInterface deleteAndCancelOrderInterface;
    /*删除订单set接口*/
    public void setDeleteOrderInterface(DeleteAndCancelOrderInterface deleteAndCancelOrderInterface) {
        this.deleteAndCancelOrderInterface = deleteAndCancelOrderInterface;
    }

    public DeleteAndCancelOrdersModel(Context context) {
        super(context);
//        deleteOrderInterface = (DeleteOrderInterface) context;
    }


    /**
     * 删除订单/取消订单
     *
     * @param id 订单id
     */
    public void deleteAndCancelOrder(String ur,String id, final int position) {
        OrderIdReq orderIdReq = new OrderIdReq();
        orderIdReq.setId(id);
        sendGet(ur, orderIdReq, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            public void onSuccess(String response) {
                super.onSuccess(response);
                CommonResponse commonResponse = parseObject(response, CommonResponse.class);
                LogUtils.error(ObtainOrdersModel.class, "删除信息：" + commonResponse.getObj());
                switch (commonResponse.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        deleteAndCancelOrderInterface.deleteAndCancelOrderSuccess(commonResponse.getObj(), position);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        deleteAndCancelOrderInterface.deleteAndCancelOrderSuccess(commonResponse.getMsg());
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

    /*删除订单回调接口*/
    public interface DeleteAndCancelOrderInterface {
        void deleteAndCancelOrderSuccess(String obj, int position);

        void deleteAndCancelOrderSuccess(String msg);
    }


}
