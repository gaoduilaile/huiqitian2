package yunju.com.huiqitian.vm.address.model;

import android.content.Context;

import java.util.List;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.ReceiveAddress;
import yunju.com.huiqitian.http.CommonResponse;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;

/**
 * Created by java1 on 2016-08-12.
 */
public class ReceiverAddressModel extends BaseModel {


    /*收货地址接口*/
    private ReceiverAddressInderface receiverAddressInderface;

    public ReceiverAddressModel(Context context) {
        super(context);
        receiverAddressInderface = (ReceiverAddressInderface) context;
    }


    /**
     * 获取收货地址
     */
    public void getAddress() {
        sendGet(HttpConstant.PATH_RECEIVER_ADDRESS, new HttpHandler() {

            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(ReceiverAddressModel.class, response);
                CommonResponse commonResponse = parseObject(response,CommonResponse.class);
                List<ReceiveAddress> receiverAddressResp = parseArray(commonResponse.getObj(), ReceiveAddress.class);
//                LogUtils.error(ReceiverAddressModel.class, "receiverAddressResp000000000000000000000000:"+receiverAddressResp.get(0).getAddressInfo());
                switch (commonResponse.getState()) {
                    case Constant.LOGIN_FAILURE://登陆成功（1）
                        receiverAddressInderface.addressDateSuccess(receiverAddressResp);
                        break;
                    case Constant.LOGIN_SUCCESS://登陆失败（0）
                        receiverAddressInderface.addressDateFailure(commonResponse.getMsg());
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

    /*收货地址接口*/
    public interface ReceiverAddressInderface {
        void addressDateSuccess(List<ReceiveAddress> receiverAddressResps);
        void addressDateFailure(String msg);
    }

}
