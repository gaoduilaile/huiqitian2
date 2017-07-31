package yunju.com.huiqitian.vm.orders.model;

import android.content.Context;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.DistributionDate;
import yunju.com.huiqitian.http.CommonResponse;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.OrderIdReq;

/**
 * Created by 张超群 on 2016-08-10.
 * <p/>
 * 查看配送
 */
public class LookDistributionModel extends BaseModel {

    private DistributionInterface distributionInterface;

    public LookDistributionModel(Context context) {
        super(context);
        distributionInterface = (DistributionInterface) context;
    }

    /**
     * 查看订单
     *
     * @param id 订单编号
     */
    public void getDistribution(String id) {
        /*请求参数*/
        final OrderIdReq orderID = new OrderIdReq();
        orderID.setId(id);
        sendGet(HttpConstant.PATH_LOOK_DISTRIBUTION, orderID, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(LookDistributionModel.class,"33"+response);
                CommonResponse commonResponse = parseObject(response, CommonResponse.class);
                LogUtils.error(LookDistributionModel.class, "物流配送：" + commonResponse.getObj());
                //解析拿到物流数据
                DistributionDate distributionDate = parseObject(commonResponse.getObj(),DistributionDate.class);
                switch (commonResponse.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        distributionInterface.distributionDateSuccess(distributionDate);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        distributionInterface.distributionDateFailure(commonResponse.getMsg());
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


     /*配送物流数据获取回调接口*/
     public interface DistributionInterface{
         void distributionDateSuccess(DistributionDate distributionDate);
         void distributionDateFailure(String msg);
     }

}
