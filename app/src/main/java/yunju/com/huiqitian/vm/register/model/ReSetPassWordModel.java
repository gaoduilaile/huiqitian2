package yunju.com.huiqitian.vm.register.model;

import android.content.Context;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.CommonResponse;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.RegisterReq;
import yunju.com.huiqitian.vm.register.view.RegisterActivity;

/**
 * Created by 高英祥 on 2016/7/20 0020.
 */
public class ReSetPassWordModel extends BaseModel{

    /*重新修改密码*/
    private ResetPwdInterface resetPwdInterface;

    public ReSetPassWordModel(Context context) {
        super(context);
        resetPwdInterface = (ResetPwdInterface) context;
    }

    /**
     * 密码重置
     */
    public void resetPwd(String phone,String password,String code){
        LogUtils.error(RegisterModel.class, phone + password + code);
        RegisterReq registerReq = new RegisterReq();
        registerReq.setMobile(phone);
        registerReq.setPassword(password);
        registerReq.setPin(code);
        sendGet(HttpConstant.PATH_BUYER_RESET_PW, registerReq, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(RegisterActivity.class, response);

                CommonResponse commonResponse=parseObject(response,CommonResponse.class);

                switch (commonResponse.getState()){
                    case Constant.LOGIN_FAILURE ://登陆成功（1）
                        resetPwdInterface.resetSuccess();
                        break;
                    case Constant.LOGIN_SUCCESS ://登陆失败（0）
                        resetPwdInterface.resetFailure(commonResponse.getMsg());
                        break;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
                LogUtils.error(RegisterActivity.class, response);
                resetPwdInterface.resetError();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpLoadingDialog.dismiss();
            }
        });
    }

    /**
     * 设置密码成功和失败的接口
     */
    public interface ResetPwdInterface{
        void resetSuccess();
        void resetFailure(String msg);
        void resetError();
    }
}
