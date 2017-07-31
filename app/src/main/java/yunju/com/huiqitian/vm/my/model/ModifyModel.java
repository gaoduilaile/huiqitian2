package yunju.com.huiqitian.vm.my.model;

import android.content.Context;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.CommonResponse;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.ChickPhoneReq;
import yunju.com.huiqitian.http.entity.PersonModifyMsgReq;
import yunju.com.huiqitian.http.entity.PersonMsgSetMobile;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.vm.register.view.RegisterActivity;

/**
 * Created by 张超群 on 2016-08-03.
 * <p/>
 * 用户的修改model
 */
public class ModifyModel extends BaseModel {

    /*修改用户昵称接口*/
    private ModifyNickNameInterface modifyNickNameInterface;

    public ModifyModel(Context context) {
        super(context);
        modifyNickNameInterface = (ModifyNickNameInterface) context;
    }

    /**
     * 修改用户昵称
     *
     * @param nickname 修改内容
     */
    public void modifyNickName(final String nickname) {
        PersonModifyMsgReq personModifyMsgReq = new PersonModifyMsgReq();
        personModifyMsgReq.setNickName(nickname);
        sendGet(HttpConstant.PATH_PERSON_CHANGE_NICKNAME, personModifyMsgReq, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                //把用户昵称保存起来
                MyUtils.changePersonNickName(nickname);
                CommonResponse commonResponse = parseObject(response, CommonResponse.class);
                LogUtils.error(ModifyModel.class, "修改后的信息：" + commonResponse);
                switch (commonResponse.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        modifyNickNameInterface.modifyNickNameSuccess(commonResponse.getMsg());
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        modifyNickNameInterface.modifyNickNameFailure(commonResponse.getMsg());
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
     * 修改用户的手机号
     *
     * @param mobile 手机号
     * @param pin    验证码
     */
    public void modifyMobile(final String mobile, String pin) {
        PersonMsgSetMobile personMsgSetMobile = new PersonMsgSetMobile();
        personMsgSetMobile.setMobile(mobile);
        personMsgSetMobile.setPin(pin);


        sendGet(HttpConstant.PATH_PERSON_SETMOBILE, personMsgSetMobile, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                //把用户电话保存起来
                MyUtils.changePersonPhone(mobile);
                CommonResponse commonResponse = parseObject(response, CommonResponse.class);
                LogUtils.error(ModifyModel.class, "修改后的信息：" + commonResponse);
                switch (commonResponse.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        modifyNickNameInterface.modifyPhoneSuccess(commonResponse.getMsg());
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        modifyNickNameInterface.modifyPhoneFailure(commonResponse.getMsg());
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
     * 验证手机号码是否已经被注册
     */
    public void chickPhone(final String mobile) {
        LogUtils.error(RegisterActivity.class, mobile);
        ChickPhoneReq chickPhoneReq = new ChickPhoneReq();
        chickPhoneReq.setMobile(mobile);
        sendGet(HttpConstant.PATH_CHICK_MOBILE, chickPhoneReq, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(ModifyModel.class, response);
                CommonResponse commonResponse = parseObject(response, CommonResponse.class);
                if (commonResponse.getState() == 1) {
                    getPhoneCode(mobile);//调用获取验证码的接口
                } else {
                    showToast(commonResponse.getMsg());
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
                LogUtils.error(ModifyModel.class, response);
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
    public void getPhoneCode(String phone) {
        ChickPhoneReq chickPhoneReq = new ChickPhoneReq();
        chickPhoneReq.setMobile(phone);
        sendGet(HttpConstant.PATH_GET_PHONE_CODE, chickPhoneReq, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(ModifyModel.class, response);
                modifyNickNameInterface.changeTime();
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

    /*修改用户昵称接口*/
    public interface ModifyNickNameInterface {
        void modifyNickNameSuccess(String msg);

        void modifyNickNameFailure(String msg);

        void modifyPhoneSuccess(String msg);

        void modifyPhoneFailure(String msg);

        void changeTime();
    }


}
