package yunju.com.huiqitian.vm.shop.model;

import android.content.Context;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.NoLocationResp;
import yunju.com.huiqitian.http.entity.SearchResp;
import yunju.com.huiqitian.http.entity.ShopGoodsReq;

/**
 * Created by gao on 2016/8/12 0012.
 */
public class ShopGoodsModel extends BaseModel {


    private ShopGoodsInterface shopGoodsInterface;

    public ShopGoodsModel(Context context) {
        super(context);
        shopGoodsInterface = (ShopGoodsInterface) context;
    }

    /**
     * 热销商品
     *
     * @param marketId
     * @param curpage
     * @param rp
     * @param storname
     * @param sortorder
     */
    public void searchGoodsHot(final int marketId, final int curpage, final int rp, final String storname, final String sortorder) {
        ShopGoodsReq shopGoodsReq = new ShopGoodsReq();
        shopGoodsReq.setMarketId(marketId);
        shopGoodsReq.setCurpage(curpage);
        shopGoodsReq.setRp(rp);
        shopGoodsReq.setSortname(storname);
        shopGoodsReq.setSortorder(sortorder);
        sendGet(HttpConstant.PATH_GOOD_HOST, shopGoodsReq, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(ShopGoodsModel.class, response);
                SearchResp searchResp = parseObject(response, SearchResp.class);
                switch (searchResp.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        shopGoodsInterface.hotGoods(searchResp);
                        searchNowGoods(marketId, curpage, rp, storname, sortorder);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        break;

                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
                NoLocationResp noLocationResp=parseObject(response,NoLocationResp.class);
                switch (noLocationResp.getState()){
                    case Constant.LOACATION_ERROR://经纬度失败。。。后台的存经纬度的的缓存过期
                        shopGoodsInterface.LocateError(noLocationResp.getMsg());
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
     * 今日上新
     */
    public void searchNowGoods(int marketId, int curpage, int rp, String storname, String sortorder) {
        ShopGoodsReq shopGoodsReq = new ShopGoodsReq();
        shopGoodsReq.setMarketId(marketId);
        shopGoodsReq.setCurpage(curpage);
        shopGoodsReq.setRp(rp);
        shopGoodsReq.setSortname(storname);
        shopGoodsReq.setSortorder(sortorder);
        sendGet(HttpConstant.PATH_GOOD_LATEST, shopGoodsReq, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(ShopGoodsModel.class, "new:" + response);
                SearchResp searchResp = parseObject(response, SearchResp.class);
                switch (searchResp.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        shopGoodsInterface.newGoods(searchResp);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
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
     * 推荐商品
     * @param marketId
     * @param curpage
     * @param rp
     * @param storname
     * @param sortorder
     */
    public void recommendGoods(int marketId, int curpage, int rp, String storname, String sortorder) {
        ShopGoodsReq shopGoodsReq = new ShopGoodsReq();
        shopGoodsReq.setMarketId(marketId);
        shopGoodsReq.setCurpage(curpage);
        shopGoodsReq.setRp(rp);
        shopGoodsReq.setSortname(storname);
        shopGoodsReq.setSortorder(sortorder);
        sendGet(HttpConstant.PATH_GOOD_RECOMMEND, shopGoodsReq, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                SearchResp searchResp = parseObject(response, SearchResp.class);
                switch (searchResp.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        shopGoodsInterface.commendGoods(searchResp);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
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

    public interface ShopGoodsInterface {
        void hotGoods(SearchResp searchResp);

        void newGoods(SearchResp searchResp);

        void commendGoods(SearchResp searchResp);

        void LocateError(String mes);//获取不到当前位置
    }
}
