package yunju.com.huiqitian.vm.menu.model;

import android.content.Context;

import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.CarouselResp;
import yunju.com.huiqitian.http.entity.ImportReq;
import yunju.com.huiqitian.http.entity.SearchReq;
import yunju.com.huiqitian.http.entity.SearchResp;
import yunju.com.huiqitian.http.entity.SelectionReq;

/**
 * Created by gao on 2016/8/10 0010.
 */
public class HomeFragmentModel extends BaseModel{

    private LikeGoodsInterface likeGoodsInterface;

    public void setLikeGoodsInterface(LikeGoodsInterface likeGoodsInterface) {
        this.likeGoodsInterface = likeGoodsInterface;
    }
    private CaroouselInterface caroouselInterface;

    public void setCaroouselInterface(CaroouselInterface caroouselInterface) {
        this.caroouselInterface = caroouselInterface;
    }

    public HomeFragmentModel(Context context) {
        super(context);
    }

    /**
     * 每日精选
     */
    public void everyDayGoods(int curpage, int rp, String storname, String sortorder){

        SelectionReq selectionReq = new SelectionReq();
        selectionReq.setCurpage(curpage);
        selectionReq.setRp(rp);
        selectionReq.setSortname(storname);
        selectionReq.setSortorder(sortorder);

        sendGet(HttpConstant.PATH_GOOD_DAILY_PICK, selectionReq, new HttpHandler() {

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
                        likeGoodsInterface.everyDayGoodsInfo(searchResp);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        likeGoodsInterface.noEveryDayGoodsInfo(searchResp.getMsg());
                        break;
                    case Constant.LOACATION_ERROR://经纬度失败。。。后台的存经纬度的的缓存过期
                        likeGoodsInterface.LocateError(searchResp.getMsg());
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
     *猜你喜欢
     */
    public void likeGoods(int curpage, int rp,String sortname, String sortorder) {

        final SearchReq searchReq = new SearchReq();
        searchReq.setName("");
        searchReq.setCurpage(curpage);
        searchReq.setRp(rp);
        searchReq.setSortname(sortname);
        searchReq.setSortorder(sortorder);
        sendGet(HttpConstant.PATH_GOOD_SEARCH, searchReq, new HttpHandler() {
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                SearchResp searchResp = parseObject(response, SearchResp.class);
                switch (searchResp.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        likeGoodsInterface.likeGoodsInfo(searchResp);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        likeGoodsInterface.noLikeGoodsInfo(searchResp.getMsg());
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
     *进口食品
     */
    public void importFood(int curpage, int rp){

        ImportReq importReq = new ImportReq();
        importReq.setCurpage(curpage);
        importReq.setRp(rp);
        sendGet(HttpConstant.PATH_IMPORT_FOOD, importReq, new HttpHandler() {
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                SearchResp searchResp = parseObject(response, SearchResp.class);
                switch (searchResp.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        likeGoodsInterface.classGoodsInfo(searchResp);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        likeGoodsInterface.noClassGoodsInfo(searchResp.getMsg());
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
     * 热卖接口（销量排序即为销量爆款）
     */
    public void hotGoods(int curpage, int rp, String storname, String sortorder){

        SelectionReq selectionReq = new SelectionReq();
        selectionReq.setCurpage(curpage);
        selectionReq.setRp(rp);
        selectionReq.setSortname(storname);
        selectionReq.setSortorder(sortorder);

        sendGet(HttpConstant.PATH_HOT_GOODS, selectionReq, new HttpHandler() {

            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                SearchResp searchResp = parseObject(response, SearchResp.class);
                switch (searchResp.getState()){
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        likeGoodsInterface.hotGoodsInfo(searchResp);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        likeGoodsInterface.noHotGoodsInfo(searchResp.getMsg());
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
     * 轮播图接口
     */
    public void carousel(){
        sendGet(HttpConstant.PATH_CAROUSEL,new HttpHandler() {
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                    CarouselResp carouselResp = parseObject(response, CarouselResp.class);
                    if(carouselResp.getObj().size()>0){
                        switch (carouselResp.getState()){
                            case Constant.LOGIN_FAILURE://查询成功（1）
                                caroouselInterface.carouselInfo(carouselResp);
                                break;
                            case Constant.LOGIN_SUCCESS://查询失败（0）
                                caroouselInterface.noCarouselInfo(carouselResp.getMsg());
                                break;
                        }
                    }

            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
            }
        });
    }

    public interface LikeGoodsInterface{
        void likeGoodsInfo(SearchResp searchResp);//猜你喜欢返回接口
        void noLikeGoodsInfo(String msg);//没有喜欢商品返回
        void everyDayGoodsInfo(SearchResp searchResp);//每日精选返回接口
        void noEveryDayGoodsInfo(String msg);//没有精选商品
        void classGoodsInfo(SearchResp searchResp);//进口食品返回接口
        void noClassGoodsInfo(String msg);//没有进口食品
        void hotGoodsInfo(SearchResp searchResp);//热卖商品返回接口
        void noHotGoodsInfo(String msg);//没有热卖商品
        void LocateError(String mes);//获取不到当前位置

    }

    public interface CaroouselInterface{
        void carouselInfo(CarouselResp carouselResp);
        void noCarouselInfo(String mes);
    }

}
