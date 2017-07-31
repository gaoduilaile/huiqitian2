package yunju.com.huiqitian.vm.orders.model;

import android.content.Context;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.CommonResponse;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.EvaluationCommitReq;
import yunju.com.huiqitian.http.entity.EvaluationOrderReq;
import yunju.com.huiqitian.http.entity.EvaluationOrderResp;

/**
 * Created by 张超群 on 2016-08-09.
 *
 * 获取要评价的订单
 */
public class EvaluationModel extends BaseModel {

    private EvaluationOrderInterface evaluationOrderInterface;

    public EvaluationModel(Context context) {
        super(context);
        evaluationOrderInterface = (EvaluationOrderInterface) context;
    }


    /**
     * 获取要评价的订单
     * @param orderID
     */
    public void getOrders(String orderID){
        /*请求参数*/
        final EvaluationOrderReq evaluationOrderReq = new EvaluationOrderReq();
        evaluationOrderReq.setId(orderID);
        sendGet(HttpConstant.PATH_EVALUATION_ORDERS,evaluationOrderReq, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                //把用户电话保存起来
                CommonResponse commonResponse = parseObject(response, CommonResponse.class);
                LogUtils.error(EvaluationModel.class, "订单内容：" + commonResponse.getObj());
                //解析拿到评价数据
                EvaluationOrderResp evaluationOrderResp = parseObject(response,EvaluationOrderResp.class);
                switch (commonResponse.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        evaluationOrderInterface.evaluationDateSuccess(evaluationOrderResp);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        evaluationOrderInterface.evaluationFailure(commonResponse.getMsg());
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
     * 提交评价订单
     *
     * @param evaluationCommitReq 请求参数
     */
    public void evaluationOrders(EvaluationCommitReq evaluationCommitReq){
        sendPost(HttpConstant.PATH_COMMIT_EVALUATION_ORDERS,evaluationCommitReq, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                //把用户电话保存起来
                CommonResponse commonResponse = parseObject(response, CommonResponse.class);
                switch (commonResponse.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        evaluationOrderInterface.evaluationOrderSuccess(commonResponse.getObj());
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        evaluationOrderInterface.evaluationFailure(commonResponse.getMsg());
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
     * 订单相关接口
     */
    public interface EvaluationOrderInterface{
        //获取订单相关内容成功
        void evaluationDateSuccess(EvaluationOrderResp evaluationOrderResp);
        //评价订单成功
        void evaluationOrderSuccess(String msg);
        //失败
        void evaluationFailure(String msg);
    }

}
