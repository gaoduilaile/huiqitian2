package yunju.com.huiqitian.vm.menu.model;

import android.content.Context;

import java.util.List;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.CommonResponse;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.PersonMsgResp;
import yunju.com.huiqitian.http.entity.VoucherReq;
import yunju.com.huiqitian.http.entity.VoucherResp;
import yunju.com.huiqitian.utils.MyUtils;

/**
 * Created by 张超群 on 2016-08-05.
 * <p/>
 * 我的界面
 */
public class MenuPersonModel extends BaseModel {

    /*得到个人信息接口*/
    private PersonMsgInterface personMsgInterface;

    /*我的钱包接口*/
    private MyWalletInterface myWalletInterface;

    /*代金券接口*/
    private MyVoucherInterface myVoucherntIerface;


    public MenuPersonModel(Context context) {
        super(context);
    }

    /*获取用户信息set接口*/
    public void setPersonMsgInterface(PersonMsgInterface personMsgInterface) {

        this.personMsgInterface = personMsgInterface;
    }

    /*我的钱包set接口*/
    public void setMyWalletInterface(MyWalletInterface myWalletInterface) {
        this.myWalletInterface = myWalletInterface;
    }

    /*代金券set接口*/
    public void setMyVoucherntIerface(MyVoucherInterface myVoucherntIerface) {
        this.myVoucherntIerface = myVoucherntIerface;
    }

    /**
     * 张超群
     * <p/>
     * 获取个人信息
     */
    public void getPersonMsg() {

        sendGet(HttpConstant.PATH_PERSON_MSG, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(MenuPersonModel.class, response);

                CommonResponse commonResponse = parseObject(response, CommonResponse.class);

                PersonMsgResp personMsgResp = parseObject(commonResponse.getObj(), PersonMsgResp.class);

                //保存个人信息
                String personMsg = null;
                if (personMsgResp.getSex() != null) {
                    personMsg = personMsgResp.getSex().toString();
                } else {
                    personMsg = " ";
                }
                MyUtils.savePersonMsg(personMsgResp.getName(), personMsgResp.getNickName(), personMsg,
                        personMsgResp.getMobile(), personMsgResp.getPicUrl());

                LogUtils.error(MenuPersonModel.class, personMsgResp.getName());//手机号码

                switch (commonResponse.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        personMsgInterface.personMsg(personMsgResp);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        personMsgInterface.noPersonMsg(commonResponse.getMsg());
                        break;
                    case 2://没有登陆
                        personMsgInterface.noLoginMsg();

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
     * 获得我的积分
     */
    public void getMyGetPoint() {
        sendGet(HttpConstant.PATH_MY_GETPOINT, new HttpHandler() {
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(MenuPersonModel.class, "积分：" + response);
//                Log.e("MenuPersonModel", "积分：" + response);
                CommonResponse commonResponse = parseObject(response, CommonResponse.class);
                switch (commonResponse.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        myWalletInterface.myGetWallet(commonResponse.getObj(), HttpConstant.PATH_MY_GETPOINT);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        myWalletInterface.failureGetWallet(commonResponse.getMsg());
                        break;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
            }
        });
    }

    /**
     * 获得我的金币
     */
    public void getMyGetCoin() {
        sendGet(HttpConstant.PATH_MY_GETCOIN, new HttpHandler() {
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(MenuPersonModel.class, "金币：" + response);
                CommonResponse commonResponse = parseObject(response, CommonResponse.class);
                switch (commonResponse.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        myWalletInterface.myGetWallet(commonResponse.getObj(), HttpConstant.PATH_MY_GETCOIN);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        myWalletInterface.failureGetWallet(commonResponse.getMsg());
                        break;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
            }
        });
    }

    /**
     * 获得代金券
     */
    public void getVoucher(final int status) {
        VoucherReq voucherReq = new VoucherReq();
        voucherReq.setStatus((byte) status);
        sendGet(HttpConstant.PATH_MY_VOUCHER, voucherReq, new HttpHandler() {
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(MenuPersonModel.class, "代金券：" + response);
//                Log.e("MenuPersonModel", "代金券：" + response);
                CommonResponse commonResponse = parseObject(response, CommonResponse.class);
                List<VoucherResp> voucherRespList = parseArray(commonResponse.getObj(), VoucherResp.class);
                switch (commonResponse.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        myVoucherntIerface.myVouchers(String.valueOf(voucherRespList.size()), status);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        myVoucherntIerface.failureGetVouchers(commonResponse.getMsg());
                        break;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
            }
        });
    }

    /*获取用户信息接口*/
    public interface PersonMsgInterface {
        void personMsg(PersonMsgResp personMsgResp);

        void noPersonMsg(String msg);

        void noLoginMsg();
    }

    /*我的钱包相关接口*/
    public interface MyWalletInterface {
        void myGetWallet(String obj, String url);

        void failureGetWallet(String msg);
    }

    /*获取代金券数量接口*/
    public interface MyVoucherInterface {
        void myVouchers(String voucherSize, int status);

        void failureGetVouchers(String msg);
    }

}
