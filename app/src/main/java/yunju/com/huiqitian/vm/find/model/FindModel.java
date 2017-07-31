package yunju.com.huiqitian.vm.find.model;

import android.content.Context;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.NearSearchReq;
import yunju.com.huiqitian.http.entity.SearchReq;
import yunju.com.huiqitian.http.entity.SearchResp;
import yunju.com.huiqitian.vm.find.view.FindResultActivity;

/**
 * Created by 高英祥 on 2016/7/14 0014.
 */
public class FindModel extends BaseModel {

    private GoodsInterface goodsInterface;

    public FindModel(Context context) {
        super(context);
        goodsInterface= (GoodsInterface) context;
    }

    /**
     * 附近超市中搜索商品
     *
     */
    public void nearSearchGoods(int curpage, int rp, final String name, String storname, String sortorder,int marketId){
        final NearSearchReq searchReq = new NearSearchReq();

        searchReq.setCurpage(curpage);
        searchReq.setRp(rp);
        searchReq.setName(name);
        searchReq.setSortname(storname);
        searchReq.setSortorder(sortorder);
        searchReq.setMarketId(marketId);

        sendGet(HttpConstant.PATH_GOOD_SEARCH, searchReq, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                SearchResp searchResp = parseObject(response, SearchResp.class);
                //LogUtils.error(FindModel.class, "gaogao" + searchResp.getObj().get(0).getName());
                switch (searchResp.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        goodsInterface.goodsInfo(response,name);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        goodsInterface.noGoods(searchResp.getMsg());
                        break;
                    case Constant.LOACATION_ERROR://经纬度失败。。。后台的存经纬度的的缓存过期
                        goodsInterface.LocateError(searchResp.getMsg());
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
     * 搜索商品
     *
     * @param name
     */
    public void searchGoods(int curpage, int rp, final String name, String storname, String sortorder) {

        final SearchReq searchReq = new SearchReq();

        searchReq.setCurpage(curpage);
        searchReq.setRp(rp);
        searchReq.setName(name);
        searchReq.setSortname(storname);
        searchReq.setSortorder(sortorder);

        sendGet(HttpConstant.PATH_GOOD_SEARCH, searchReq, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                SearchResp searchResp = parseObject(response, SearchResp.class);
                //LogUtils.error(FindModel.class, "gaogao" + searchResp.getObj().get(0).getName());
                switch (searchResp.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        goodsInterface.goodsInfo(response,name);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        goodsInterface.noGoods(searchResp.getMsg());
                        LogUtils.error(FindResultActivity.class, "主页查询失败");
                        break;
                    case Constant.LOACATION_ERROR://经纬度失败。。。后台的存经纬度的的缓存过期
                        goodsInterface.LocateError(searchResp.getMsg());
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


    public interface GoodsInterface {
        void goodsInfo(String resp,String name);

        void noGoods(String msg);

        void addSuccess();

        void addFailure(String msg);

        void LocateError(String mes);//获取不到当前位置

    }
}
