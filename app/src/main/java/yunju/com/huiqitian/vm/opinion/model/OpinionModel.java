package yunju.com.huiqitian.vm.opinion.model;

import android.content.Context;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.CommonResponse;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.OpinionCommitReq;

/**
 * Created by 张超群 on 2016-08-13.
 * <p/>
 * 意见反馈model
 */
public class OpinionModel extends BaseModel {

    private  OpinionCommitSuccessInterface opinionCommitSuccessInterface;

    public OpinionModel(Context context) {
        super(context);
        opinionCommitSuccessInterface = (OpinionCommitSuccessInterface) context;
    }


    /**
     * 提交用户的意见反馈
     *
     * @param opinionCommitReq 意见反馈实体类
     */
    public void commitOpinion(OpinionCommitReq opinionCommitReq) {

        sendGet(HttpConstant.PATH_OPINION_ADD, opinionCommitReq, new HttpHandler() {

            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(OpinionModel.class, response);
                CommonResponse commonResponse = parseObject(response, CommonResponse.class);
                switch (commonResponse.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        opinionCommitSuccessInterface.opinionCommitSuccess(commonResponse.getObj());
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        opinionCommitSuccessInterface.opinionCommitFailure(commonResponse.getMsg());
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

    /*意见反馈成功后的回调接口*/
    public interface OpinionCommitSuccessInterface {
        void opinionCommitSuccess(String obj);

        void opinionCommitFailure(String msg);
    }

}
