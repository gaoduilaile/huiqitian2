package yunju.com.huiqitian.vm.address.model;

import android.content.Context;

import java.math.BigDecimal;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.CommonResponse;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.ReceiverAddressIdReq;
import yunju.com.huiqitian.http.entity.UpDateAddressReq;

/**
 * Created by 张超群 on 2016-08-12.
 * <p/>
 * 设置默认和删除地址
 */
public class DefAndDelModel extends BaseModel {

    /*删除*/
    private DefAndDelReceiverAddressInderface defAndDelAddressDateSuccess;

    /*跟新*/
    private UpDateAddressInterface upDateAddressInterface;

    /*修改收货地址*/
    private SetAlterAddressInterface setAlterAddressInterface;

    public DefAndDelModel(Context context) {
        super(context);
    }

    public void setDefAndDelAddressDateSuccess(DefAndDelReceiverAddressInderface defAndDelAddressDateSuccess) {
        this.defAndDelAddressDateSuccess = defAndDelAddressDateSuccess;
    }

    public void setUpDateAddressInterface(UpDateAddressInterface upDateAddressInterface) {
        this.upDateAddressInterface = upDateAddressInterface;
    }

    public void setSetAlterAddressInterface(SetAlterAddressInterface setAlterAddressInterface) {
        this.setAlterAddressInterface = setAlterAddressInterface;
    }

    /**
     * 删除订单
     *
     * @param url     地址
     * @param id      收货信息编号
     * @param postion 改变位置
     */
    public void setDefAndDelAddress(String url, int id, final int postion) {
        ReceiverAddressIdReq receiverAddressIdReq = new ReceiverAddressIdReq();
        receiverAddressIdReq.setId(id);
        sendGet(url, receiverAddressIdReq, new HttpHandler() {
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(ReceiverAddressModel.class, response);
                CommonResponse commonResponse = parseObject(response, CommonResponse.class);
                switch (commonResponse.getState()) {
                    case Constant.LOGIN_FAILURE://登陆成功（1）
                        defAndDelAddressDateSuccess.defAndDelAddressDateSuccess(commonResponse.getObj(), postion);
                        break;
                    case Constant.LOGIN_SUCCESS://登陆失败（0）
                        defAndDelAddressDateSuccess.defAndDelAddressDateFailure(commonResponse.getMsg());
                        break;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
            }

            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpLoadingDialog.dismiss();
            }
        });
    }

    /**
     * 跟新收获地址
     */
    public void upDateAddress(int id, String name, byte isDefault, byte sex, int areaId, int cityId, int provinceId, String tel,
                              String spareTel, String postCode, String addressInfo,String detailAddr ,BigDecimal lbslng, BigDecimal lbslat, final int position) {
        UpDateAddressReq upDateAddressReq = new UpDateAddressReq();
        upDateAddressReq.setId(id);
        upDateAddressReq.setName(name);
        upDateAddressReq.setIsDefault(isDefault);
        upDateAddressReq.setCityId(cityId);
        upDateAddressReq.setSex(sex);
        upDateAddressReq.setAreaId(areaId);
        upDateAddressReq.setProvinceId(provinceId);
        upDateAddressReq.setTel(tel);
        upDateAddressReq.setSpareTel(spareTel);
        upDateAddressReq.setPostCode(postCode);
        upDateAddressReq.setAddressInfo(addressInfo);
        upDateAddressReq.setLbslng(lbslng);
        upDateAddressReq.setLbslat(lbslat);
        upDateAddressReq.setDetailAddr(detailAddr);
        sendGet(HttpConstant.PATH_UP_DATE_ADDRESS, upDateAddressReq, new HttpHandler() {
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                CommonResponse commonResponse = parseObject(response, CommonResponse.class);
                switch (commonResponse.getState()) {
                    case Constant.LOGIN_FAILURE://登陆成功（1）
                        upDateAddressInterface.upDateAddressSuccess( position);
                        LogUtils.error(DefAndDelModel.class, "88888888888888888888888888888" + commonResponse.getObj());
                        break;
                    case Constant.LOGIN_SUCCESS://登陆失败（0）
                        upDateAddressInterface.upDateAddressFailure(commonResponse.getMsg());
                        break;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
            }

            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpLoadingDialog.dismiss();
            }
        });
    }

    /**
     * 修改默认地址
     */
    public void setAlterAddress(String url, int id, final int postion) {
        ReceiverAddressIdReq receiverAddressIdReq = new ReceiverAddressIdReq();
        receiverAddressIdReq.setId(id);
        sendGet(url, receiverAddressIdReq, new HttpHandler() {
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(ReceiverAddressModel.class, response);
                CommonResponse commonResponse = parseObject(response, CommonResponse.class);
                switch (commonResponse.getState()) {
                    case Constant.LOGIN_FAILURE://登陆成功（1）
                        setAlterAddressInterface.setAlterAddressSuccess(commonResponse.getObj(), postion);
                        break;
                    case Constant.LOGIN_SUCCESS://登陆失败（0）
                        setAlterAddressInterface.setAlterAddressFailure(commonResponse.getMsg());
                        break;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
            }

            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpLoadingDialog.dismiss();
            }
        });
    }


    /*删除收货地址接口*/
    public interface DefAndDelReceiverAddressInderface {
        void defAndDelAddressDateSuccess(String obj, int position);

        void defAndDelAddressDateFailure(String msg);
    }

    /*跟新收获地址接口*/
    public interface UpDateAddressInterface {
        void upDateAddressSuccess( int position);

        void upDateAddressFailure(String mes);
    }

    /*修改收货地址*/
    public interface SetAlterAddressInterface {
        void setAlterAddressSuccess(String obj, int position);

        void setAlterAddressFailure(String mes);
    }
}
