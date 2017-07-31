package yunju.com.huiqitian.vm.map.model;

import android.content.Context;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;

/**
 * Created by gao on 2016/8/4 0004.
 */
public class AddressModel extends BaseModel {
    public AddressModel(Context context) {
        super(context);
    }

    /**
     * 获得个人的地址
     */
    public void personAddress(){
        sendGet(HttpConstant.PATH_BUYER_ADDRESS_LIST,new HttpHandler(){
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(AddressModel.class,response);
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
            }
        });
    }
}
