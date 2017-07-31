package yunju.com.huiqitian.vm.register.model;

import android.content.Context;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.http.CommonResponse;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.CheckCodeReq;
import yunju.com.huiqitian.http.entity.ChickPhoneReq;
import yunju.com.huiqitian.http.entity.RegisterReq;
import yunju.com.huiqitian.vm.register.view.RegisterActivity;

/**
 * Created by 高英祥 on 2016/7/11 0011.
 */
public class RegisterModel extends BaseModel{

    /*数据的回调接口*/
    private RegisterInterface registerInterface;

    public RegisterModel(Context context) {
        super(context);
        registerInterface = (RegisterInterface) context;
    }

    /**
     * 注册
     */
    public void register(String phone,String password,String code){
        LogUtils.error(RegisterModel.class,phone+password+code);
        RegisterReq registerReq = new RegisterReq();
        registerReq.setMobile(phone);
        registerReq.setPassword(password);
        registerReq.setPin(code);
        sendGet(HttpConstant.PATH_REGISTER, registerReq, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(RegisterActivity.class, response);
                registerInterface.registerSuccess();
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
                LogUtils.error(RegisterActivity.class, response);
                CommonResponse commonResponse = parseObject(response,CommonResponse.class);
                registerInterface.registerFailure(commonResponse.getMsg());
            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpLoadingDialog.dismiss();
            }
        });
    }

    /**
     * 验证手机号码是否已经被注册
     */
    public void chickPhone(final String mobile){
        LogUtils.error(RegisterActivity.class, mobile);
        ChickPhoneReq chickPhoneReq = new ChickPhoneReq();
        chickPhoneReq.setMobile(mobile);
        sendGet(HttpConstant.PATH_CHICK_MOBILE,chickPhoneReq, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(RegisterActivity.class, response);
                CommonResponse commonResponse = parseObject(response,CommonResponse.class);
                if (commonResponse.getState()==1){
                getPhoneCode(mobile);//调用获取验证码的接口
                }else {
                    showToast(commonResponse.getMsg());
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
                LogUtils.error(RegisterActivity.class, response);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpLoadingDialog.dismiss();
            }
        });
    }

    /**
     * 验证手机号码是否已经被注册(为了密码重置)
     */
    public void chickPhoneForReSet(final String mobile){
        LogUtils.error(RegisterActivity.class, mobile);
        ChickPhoneReq chickPhoneReq = new ChickPhoneReq();
        chickPhoneReq.setMobile(mobile);
        sendGet(HttpConstant.PATH_CHICK_MOBILE,chickPhoneReq, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(RegisterActivity.class, response);
                CommonResponse commonResponse = parseObject(response,CommonResponse.class);
                if (commonResponse.getState()==0){
                    getPhoneCodeForResetPwd(mobile);//调用获取验证码的接口
                }else {
                    showToast("该用户还没有注册");
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
                LogUtils.error(RegisterActivity.class, response);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpLoadingDialog.dismiss();
            }
        });
    }

    /**
     * 获取手机验证码
     */
    public void getPhoneCode(String phone){
        ChickPhoneReq chickPhoneReq = new ChickPhoneReq();
        chickPhoneReq.setMobile(phone);
        sendGet(HttpConstant.PATH_GET_PHONE_CODE,chickPhoneReq,new HttpHandler(){
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(RegisterActivity.class, response);
                registerInterface.changeTime();
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
     * 获取手机验证码(为重置密码)
     */
    public void getPhoneCodeForResetPwd(String phone){
        ChickPhoneReq chickPhoneReq = new ChickPhoneReq();
        chickPhoneReq.setMobile(phone);
        sendGet(HttpConstant.PATH_GET_PHONE_CODE,chickPhoneReq,new HttpHandler(){
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(RegisterActivity.class, response);
                registerInterface.changeTime();
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
     * 验证手机验证码
     */
    public void checkCode(final String code,String phone){
        CheckCodeReq checkCodeReq = new CheckCodeReq();
        checkCodeReq.setPin(code);
        checkCodeReq.setMobile(phone);
        sendGet(HttpConstant.PATH_PIN_VERIFY, checkCodeReq, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(RegisterModel.class, response);
                CommonResponse commonResponse = parseObject(response,CommonResponse.class);
                if (commonResponse.getState()==1){
                    registerInterface.rightCode(code);//验证码正确
                }else {
                    showToast(commonResponse.getMsg());
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
                LogUtils.error(RegisterModel.class, response);
                showToast("验证码错误");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpLoadingDialog.dismiss();
            }
        });
    }


    /**
     * 验证手机验证码(为了重置密码)
     */
    public void checkCodeForRest(final String code,String phone){
        CheckCodeReq checkCodeReq = new CheckCodeReq();
        checkCodeReq.setPin(code);
        checkCodeReq.setMobile(phone);
        sendGet(HttpConstant.PATH_PIN_VERIFY, checkCodeReq, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(RegisterModel.class, response);
                CommonResponse commonResponse = parseObject(response,CommonResponse.class);
                if (commonResponse.getState()==1){
                    registerInterface.rightResetCode(code);//验证码正确
                }else {
                    showToast("请输入正确的验证码");
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
                LogUtils.error(RegisterModel.class, response);
                showToast("获取验证码失败，请重试");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpLoadingDialog.dismiss();
            }
        });
    }

    /**
     * 数据的接口回调
     */
    public interface RegisterInterface{
        void registerSuccess();
        void registerFailure(String msg);
        void rightCode(String code);
        void rightResetCode(String code);
        void changeTime();
    }
}
