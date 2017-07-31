package yunju.com.huiqitian.vm.login.model;

import android.content.Context;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.CommonResponse;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.CidReq;
import yunju.com.huiqitian.http.entity.LoginReq;

/**
 * Created by 高英祥 on 2016/7/11 0011.
 */
public class LoginModel extends BaseModel {
    /*登陆回调的接口*/
    private LoginInterface loginInterface;

    /*标记区分*/
    public LoginModel(Context context) {
        super(context);
        loginInterface = (LoginInterface) context;
    }

    /**
     * 登陆
     */
    public void login(String name, String password) {
        LoginReq loginReq = new LoginReq();

        loginReq.setUser_name(name);
        loginReq.setPassword(password);

        // LogUtils.error(LoginModel.class, "ok" +toJSONString(loginReq));

        sendGet(HttpConstant.PATH_LOGIN, loginReq, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(LoginModel.class, "ok" + response);
                CommonResponse commonResponse = parseObject(response, CommonResponse.class);

                switch (commonResponse.getState()) {
                    case Constant.LOGIN_FAILURE://登陆成功（1）
                        LogUtils.error(LoginModel.class, "登陆成功");
                        loginInterface.loginSuccess();
                        break;
                    case Constant.LOGIN_SUCCESS://登陆失败（0）
                        loginInterface.loginFailure(commonResponse.getMsg());
                        break;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
                LogUtils.error(LoginModel.class, "no" + response);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpLoadingDialog.dismiss();
            }
        });
    }

    /**
     * 绑定用户cid
     */
    public void bindCid(String cid) {
        CidReq cidReq = new CidReq();
        cidReq.setCID(cid);
        sendGet(HttpConstant.PATH_BIND_CID, cidReq, new HttpHandler() {
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
            }
        });
    }

    /**
     * 登陆的接口回调事件
     */
    public interface LoginInterface {
        void loginSuccess();

        void loginFailure(String msg);
    }
}
