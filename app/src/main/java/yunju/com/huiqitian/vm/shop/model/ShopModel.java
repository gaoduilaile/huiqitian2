package yunju.com.huiqitian.vm.shop.model;

import android.content.Context;

import java.util.List;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.ShopInfo;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.NoLocationResp;
import yunju.com.huiqitian.http.entity.SearchShopReq;
import yunju.com.huiqitian.http.entity.SearchShopResp;

/**
 * Created by gao on 2016/8/11 0011.
 */
public class ShopModel extends BaseModel {

    private ShopInterface shopInterface;//搜索商铺的接口

    public ShopModel(Context context) {
        super(context);
        shopInterface = (ShopInterface) context;
    }

    /**
     * 搜索附近的商铺
     */
    public void searchShop(String sortname, String sortorder) {
        SearchShopReq searchShopReq = new SearchShopReq();
        searchShopReq.setSortname(sortname);
        searchShopReq.setSortorder(sortorder);
        sendGet(HttpConstant.PATH_BUYER_MARKER_LIST, searchShopReq, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(ShopModel.class, response);
                SearchShopResp searchShopResp = parseObject(response, SearchShopResp.class);
                switch (Integer.valueOf(searchShopResp.getState())) {
                    case Constant.LOGIN_FAILURE://登陆成功（1）
                        shopInterface.hasShop(searchShopResp.getObj());
                        break;
                    case Constant.LOGIN_SUCCESS://失败（2）
                        break;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
                NoLocationResp noLocationResp=parseObject(response,NoLocationResp.class);
                switch (noLocationResp.getState()){
                    case Constant.LOACATION_ERROR://经纬度失败。。。后台的存经纬度的的缓存过期
                        shopInterface.LocateError(noLocationResp.getMsg());
                        break;
                    case Constant.LOGIN_THREE:
                        shopInterface.noShopMarket(noLocationResp.getMsg());
                        break;
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpLoadingDialog.dismiss();
            }
        });
    }


    /**
     * 搜索商铺的接口
     */
    public interface ShopInterface {
        void hasShop(List<ShopInfo> shopInfos);
        void noShopMarket(String mes);
        void LocateError(String mes);//获取不到当前位置

    }
}
