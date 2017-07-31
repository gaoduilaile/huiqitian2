package yunju.com.huiqitian.vm.history.model;

import android.content.Context;

import java.util.List;

import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.entity.FlexiSimpleQuery;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.GoodsDetailsReq;

/**
 * Created by Administrator on 2016/8/16 0016.
 */
public class HistoryModel extends BaseModel {
    public HistoryModel(Context context) {
        super(context);
    }


    public void goodsDetail(int curpage, int rp,List<FlexiSimpleQuery> querys) {
        //这里复用添加购物车的req
        GoodsDetailsReq goodsDetailsReq=new GoodsDetailsReq();
        goodsDetailsReq.setCurpage(curpage);
        goodsDetailsReq.setRp(rp);
        goodsDetailsReq.setQuerys(querys);
        sendGet(HttpConstant.PATH_GOOD_DETAILS, goodsDetailsReq, new HttpHandler() {
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);

            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
            }
        });
    }
}
