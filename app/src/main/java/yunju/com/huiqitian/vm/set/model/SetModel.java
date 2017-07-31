package yunju.com.huiqitian.vm.set.model;

import android.content.Context;

import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;

/**
 * Created by 胡月 on 2016/8/15 0015.
 */
public class SetModel extends BaseModel{
    private LoginUotInterface loginUotInterface;

    public SetModel(Context context) {
        super(context);
        loginUotInterface= (LoginUotInterface) context;
    }

    /**
     * 退出账号
     */
    public void loginOut(){
        sendGet(HttpConstant.PATH_LOGIN_OUT,new HttpHandler(){
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                loginUotInterface.loginUotSuccess(response);
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

    public interface LoginUotInterface{
        void loginUotSuccess(String  mes);
        void loginUotFailure(String mes);
    }

}
