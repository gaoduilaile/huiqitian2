package yunju.com.huiqitian.vm.signin.model;

import android.content.Context;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.GoldDetailResp;
import yunju.com.huiqitian.http.entity.GoldRecordReq;
import yunju.com.huiqitian.http.entity.GoldRecordResp;
import yunju.com.huiqitian.http.entity.SignInReq;
import yunju.com.huiqitian.http.entity.SignInResp;

/**
 * Created by 胡月 on 2016/8/11 0011.
 */
public class SignInModel extends BaseModel{

    private GoldRecordInterface goldRecordInterface;

    public SignInModel(Context context) {
        super(context);
        goldRecordInterface= (GoldRecordInterface) context;
    }

    /**
     *中奖记录
     */
    public void goldRecord(int curpage, int rp){
        GoldRecordReq goldRecordReq=new GoldRecordReq();

        goldRecordReq.setCurpage(curpage);
        goldRecordReq.setRp(rp);

        sendGet(HttpConstant.PATH_GOLD_RECORD, goldRecordReq, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);

                GoldRecordResp goldRecordResp = parseObject(response, GoldRecordResp.class);

                LogUtils.error(SignInModel.class, "......................555555555555555...................." + goldRecordResp.getObj());

                switch (goldRecordResp.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        goldRecordInterface.goldRecordInfo(goldRecordResp);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        break;
                    case  Constant.LOGIN_NONE://登录
                        goldRecordInterface.noLogin();
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
     * 签单初始化
     */
    public void SignIn(){
        sendGet(HttpConstant.PATH_SIGN_IN,new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                SignInResp signInResp = parseObject(response, SignInResp.class);
                LogUtils.error(SignInModel.class, "......................6666666666666666666666...................." + signInResp.getObj().getSettings().get(0).getSettingsName());
                switch (signInResp.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        goldRecordInterface.initializeSignIn(signInResp);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        break;
                    case  Constant.LOGIN_NONE://登录
                        goldRecordInterface.noLogin();
                        break;
                }

            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);

                goldRecordInterface.noLogin();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpLoadingDialog.dismiss();
            }
        });
    }

    /**
     * 签到接口
     */
    public void getSignIn(String signInAddress ,double lbsLng ,double lbsLat ,Integer currId){
        SignInReq signInReq=new SignInReq();
        signInReq.setCurrId(currId);
        signInReq.setSigninAddress(signInAddress);
        signInReq.setLbslat(lbsLat);
        signInReq.setLbslng(lbsLng);
        LogUtils.error(SignInModel.class, "999999999999999999999999999999999999999999999999"+signInAddress + "....." + lbsLng + "......" + lbsLat + "........." + currId);
        sendGet(HttpConstant.PATH_GET_SIGN_IN,signInReq ,new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                SignInResp signInResp = parseObject(response, SignInResp.class);
                switch (signInResp.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        goldRecordInterface.getSignIn(signInResp);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        break;
                    case  Constant.LOGIN_NONE://登录
                        goldRecordInterface.noLogin();
                        break;
                }

            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
                goldRecordInterface.noLogin();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpLoadingDialog.dismiss();
            }
        });
    }

    /**
     *金币明细
     */
    public void goldDetails(){
        sendGet(HttpConstant.PATH_GOLD_DETAILS,new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);

                GoldDetailResp goldDetailResp = parseObject(response, GoldDetailResp.class);

                LogUtils.error(SignInModel.class, "......................888888888888888888888888...................." + goldDetailResp.getObj());

                switch (goldDetailResp.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        goldRecordInterface.goldDetailsInfo(goldDetailResp);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        break;
                    case  Constant.LOGIN_NONE://登录
                        goldRecordInterface.noLogin();
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

    public interface GoldRecordInterface{
        void goldRecordInfo(GoldRecordResp goldRecordResp);
        void initializeSignIn(SignInResp signInResp);
        void getSignIn(SignInResp signInResp);
        void goldDetailsInfo(GoldDetailResp goldDetailResp);
        void noLogin();
    }

}
