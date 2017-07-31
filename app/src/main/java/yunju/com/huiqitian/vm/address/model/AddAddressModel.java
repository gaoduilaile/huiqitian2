package yunju.com.huiqitian.vm.address.model;

import android.content.Context;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.CommonResponse;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.AddAddressReq;

/**
 * Created by zcq on 2016-08-13.
 * <p/>
 * 添加收货地址的model
 */
public class AddAddressModel extends BaseModel {

    private AddAddressInterface addAddressInterface;

    public AddAddressModel(Context context) {
        super(context);
        addAddressInterface = (AddAddressInterface) context;
    }

    /**
     * 添加收货地址
     *
     * @param addAddressReq 请求参数
     */
    public void addAddress(AddAddressReq addAddressReq) {
        sendGet(HttpConstant.PATH_ADD_ADDRESS, addAddressReq, new HttpHandler() {

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

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(AddAddressModel.class, "添加返回结果：" + response);
                CommonResponse commonResponse = parseObject(response, CommonResponse.class);
                switch (commonResponse.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        addAddressInterface.addAddressSuccess(commonResponse.getObj());
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        addAddressInterface.addAddressFailure(commonResponse.getMsg());
                        break;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
            }

        });
    }


    /*添加成功回调接口*/
    public interface AddAddressInterface {
        void addAddressSuccess(String obj);

        void addAddressFailure(String msg);
    }
}
