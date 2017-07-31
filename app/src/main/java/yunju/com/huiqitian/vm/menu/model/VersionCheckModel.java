package yunju.com.huiqitian.vm.menu.model;

import android.content.Context;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.VersionCheckReq;

/**
 * app版本信息检测
 * Created by CCTV-1 on 2017/1/4 0004.
 */
public class VersionCheckModel extends BaseModel {
    private CheckVersion checkVersion;

    public VersionCheckModel(Context context) {
        super(context);
    }

    public void checkVersion(byte type,String version,byte audience){
        VersionCheckReq versionCheckReq = new VersionCheckReq();
        versionCheckReq.setType(type);
        versionCheckReq.setVersion(version);
        versionCheckReq.setAudience(audience);
        /*发送请求*/
        sendGet(HttpConstant.PATH_CHECK_VERSION, versionCheckReq, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(VersionCheckModel.class, response);
                VersionCheckReq versionCheckReq = parseObject(response, VersionCheckReq.class);
                switch (versionCheckReq.getState()) {
                    case Constant.LOGIN_SUCCESS://查询失败（1）写反了
                        checkVersion.addFailure(versionCheckReq.getMsg());
                        break;
                    case Constant.LOGIN_FAILURE://查询成功（0）
                        checkVersion.addSuccess(response);
                        break;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
                LogUtils.error(VersionCheckModel.class, "+++++++++++++++++++++++++");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpLoadingDialog.dismiss();
            }
        });

    }
    /*定义接口*/
    public interface CheckVersion{
        void addSuccess(String obj);
        void addFailure(String msg);
    }
    /*回调接口的实现方法*/
    public void getCheckVersionInterface(CheckVersion checkVersion){
        this.checkVersion = checkVersion;
    }
}
