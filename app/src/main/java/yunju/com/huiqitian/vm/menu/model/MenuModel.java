package yunju.com.huiqitian.vm.menu.model;

import android.content.Context;

import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.LocateReq;
import yunju.com.huiqitian.http.entity.LocateResp;

/**
 * Created by 高英祥 on 2016/7/14 0014.
 */
public class MenuModel extends BaseModel{

    private LocateInterface locateInterface;

    public void setLocateInterface(LocateInterface locateInterface) {
        this.locateInterface = locateInterface;
    }

    public MenuModel(Context context) {
        super(context);
    }

    /**
     * 上传信息
     * @param lg
     * @param lt
     */
    public void buyerLocate(double lg,double lt){

        final LocateReq locateReq = new LocateReq();
        locateReq.setLng(lg);//31.837988
        locateReq.setLat(lt);//117.185538
        sendGet(HttpConstant.PATH_BUYER_LOCATE, locateReq,new HttpHandler() {

            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }
            @Override
            public void onSuccess (String response){
                super.onSuccess(response);
                LocateResp locateResp=parseObject(response,LocateResp.class);
                switch (locateResp.getState()){
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        locateInterface.LocateSuccess(locateResp.getMsg());
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）附近无超市
                        locateInterface.LocateFailure(locateResp.getMsg());
                        break;

                }

            }

            @Override
            public void onFailure ( int statusCode, String response, Throwable error){
                super.onFailure(statusCode, response, error);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpLoadingDialog.dismiss();
            }
        });
    }

    public interface LocateInterface{
        void LocateSuccess(String mes);
        void LocateFailure(String mes);
    }

}
