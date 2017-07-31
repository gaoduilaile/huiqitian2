package yunju.com.huiqitian.vm.find.model;

import android.content.Context;

import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.ClassSortReq;
import yunju.com.huiqitian.http.entity.NoLocationResp;
import yunju.com.huiqitian.http.entity.SearchResp;

/**
 * Created by gao on 2016/8/17 0017.
 */
public class FoodStuffModel extends BaseModel{

    private StuffInterface stuffInterface;

    public FoodStuffModel(Context context) {
        super(context);
        stuffInterface = (StuffInterface) context;
    }

    /**
     * 分类点击进去数据
     * 通过分类和排序获取商品分类
     */
    public void stuffGoods(int curpage, int rp, String sortname, String sortorder){
        final ClassSortReq classSortReq=new ClassSortReq();

        classSortReq.setCurpage(curpage);
        classSortReq.setRp(rp);
        classSortReq.setSortname(sortname);
        classSortReq.setSortorder(sortorder);

        sendGet(HttpConstant.PATH_IMPORT_FOOD, classSortReq, new HttpHandler() {
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
                if (response!=null){
                    SearchResp searchResp = parseObject(response, SearchResp.class);
                    switch (searchResp.getState()) {
                        case Constant.LOGIN_FAILURE://查询成功（1）
                            stuffInterface.hasStuff(response);
                            break;
                        case Constant.LOGIN_SUCCESS://查询失败（0）
                            stuffInterface.noStuff(searchResp.getMsg());
                            break;
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
                NoLocationResp noLocationResp=parseObject(response,NoLocationResp.class);
                switch (noLocationResp.getState()){
                    case Constant.LOACATION_ERROR://经纬度失败。。。后台的存经纬度的的缓存过期
                        stuffInterface.LocateError(noLocationResp.getMsg());
                        break;
                }
            }
        });
    }


    public interface StuffInterface{
        void hasStuff(String resp);
        void noStuff(String msg);
        void LocateError(String mes);//获取不到当前位置
    }

}
