package yunju.com.huiqitian.vm.details.model;

import android.content.Context;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.CommonResponse;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.AddCartReq;
import yunju.com.huiqitian.http.entity.CartSettleResp;
import yunju.com.huiqitian.http.entity.EvalSummaryReq;
import yunju.com.huiqitian.http.entity.GoodEvalResp;
import yunju.com.huiqitian.http.entity.LookCartResp;
import yunju.com.huiqitian.http.entity.PropResp;
import yunju.com.huiqitian.http.entity.RecommendReq;
import yunju.com.huiqitian.http.entity.SearchResp;

/**
 * Created by 胡月 on 2016/7/19 0019.
 */
public class DetailsModel extends BaseModel {

    /*接口回调数据显示*/
    private GoodsPropInterface goodsPropInterface;//接口

    public DetailsModel(Context context) {
        super(context);
        goodsPropInterface = (GoodsPropInterface) context;
    }

    /**
     * 商品属性
     */
    public void goodProp(int id, byte type) {
        //这里复用添加购物车的req
        AddCartReq addCartReq = new AddCartReq();
        addCartReq.setId(id);
        addCartReq.setType(type);
        sendGet(HttpConstant.PATH_GOOD_PROP, addCartReq, new HttpHandler() {
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(DetailsModel.class, response);
                PropResp propResp = parseObject(response, PropResp.class);
                switch (propResp.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        goodsPropInterface.goodsDetail(propResp);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        goodsPropInterface.noGoodsProp(propResp.getMsg());
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
     * 查看购物车数据
     */
    public void lookShoppingCart() {
        sendGet(HttpConstant.PATH_CART_LIST, new HttpHandler() {
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LookCartResp lookCartResp = parseObject(response, LookCartResp.class);
                int count = 0;//商品数量
                /*获取商品数量*/
                for (int i = 0; i < lookCartResp.getObj().size(); i++) {
                    for (int j = 0; j < lookCartResp.getObj().get(i).getAppCartGoodsList().size(); j++) {
                        count = count + lookCartResp.getObj().get(i).getAppCartGoodsList().get(j).getQty();
                    }
                }
                switch (lookCartResp.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        goodsPropInterface.cartGoods(count);

                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        goodsPropInterface.noCartGoods(lookCartResp);
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
     * 立即购买数据
     */
    public void newBuy(int id, byte type) {
        //这里复用添加购物车的req
        AddCartReq addCartReq = new AddCartReq();
        addCartReq.setId(id);
        addCartReq.setType(type);
        sendGet(HttpConstant.PATH_NEW_BUY, addCartReq, new HttpHandler() {
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(DetailsModel.class, "9999999999999999999999999888888888888888888888888888" + response);
                CartSettleResp cartSettleResp = parseObject(response, CartSettleResp.class);
                switch (cartSettleResp.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        goodsPropInterface.newBuyData(cartSettleResp);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        goodsPropInterface.noNewBuyData(cartSettleResp.getMsg());
                        break;
                }

            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
                if (response != null) {
                    CommonResponse commonResponse = parseObject(response, CommonResponse.class);
                    if (commonResponse.getState() == Constant.TWO) {
                        //回调登录接口
                        goodsPropInterface.startToLoginAct();
                    }

                }
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
     * 添加购物车
     */
    public void addCartNumber(int id, byte type) {
        AddCartReq addCartReq = new AddCartReq();
        addCartReq.setId(id);
        addCartReq.setType(type);
        sendGet(HttpConstant.PATH_CART_ADD, addCartReq, new HttpHandler() {
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                CommonResponse commonResponse = parseObject(response, CommonResponse.class);
                switch (commonResponse.getState()) {
                    case Constant.LOGIN_FAILURE://添加成功（1）
                        goodsPropInterface.addSuccess();
                        break;
                    case Constant.LOGIN_SUCCESS://添加失败（0）
                        goodsPropInterface.addFailure(commonResponse.getMsg());
                        break;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
                if (response != null) {
                    CommonResponse commonResponse = parseObject(response, CommonResponse.class);
                    if (commonResponse.getState() == Constant.TWO) {
                        //回调登录接口
                        goodsPropInterface.startToLoginAct();
                    }

                }
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
     * 商品推荐
     */
    public void GoodsRecommend(int curpage, int rp, String sortname, String sortorder,int marketId) {
        RecommendReq recommendReq = new RecommendReq();
        recommendReq.setCurpage(curpage);
        recommendReq.setRp(rp);
        recommendReq.setSortname(sortname);
        recommendReq.setSortorder(sortorder);
        recommendReq.setMarketId(marketId);

        sendGet(HttpConstant.PATH_GOOD_RECOMMEND, recommendReq, new HttpHandler() {
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                SearchResp searchResp = parseObject(response, SearchResp.class);
                switch (searchResp.getState()) {
                    case Constant.LOGIN_FAILURE://添加成功（1）
                        goodsPropInterface.recommendInterface(searchResp);
                        break;
                    case Constant.LOGIN_SUCCESS://添加失败（0）
                        goodsPropInterface.noRecommendInterface(searchResp.getMsg());
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
     * 获取评价内容
     * 商品的详细评价
     */
    public void GoodEval(int id,byte type,int rp,int curpage){
        EvalSummaryReq evalSummaryReq=new EvalSummaryReq();
        evalSummaryReq.setId(id);
        evalSummaryReq.setCurpage(curpage);
        evalSummaryReq.setRp(rp);
        evalSummaryReq.setType(type);
        sendGet(HttpConstant.PATH_GOODS_EVAL, evalSummaryReq, new HttpHandler() {
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                GoodEvalResp goodEvalResp = parseObject(response, GoodEvalResp.class);
                switch (goodEvalResp.getState()) {
                    case Constant.LOGIN_FAILURE://添加成功（1）
                        goodsPropInterface.goodEval(goodEvalResp);

                        break;
                    case Constant.LOGIN_SUCCESS://添加失败（0）
                        goodsPropInterface.noGoodEval(goodEvalResp.getMsg());
                        break;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
                LogUtils.error(DetailsModel.class,"错误了错误了错误了错误了错误了错误了错误了错误了");
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
     * 接口返回数据
     */
    public interface GoodsPropInterface {
        void goodsDetail(PropResp propResp);//查看商品属性

        void noGoodsProp(String mes);//查看我商品，无属性

        void cartGoods(int count);//查看购物车商品数量

        void noCartGoods(LookCartResp lookCartResp);//查看购物车，无信息

        void newBuyData(CartSettleResp cartSettleResp);//立即购买

        void noNewBuyData(String mes);

        void startToLoginAct();//登陆提示

        void addSuccess();//添加购物车成功

        void addFailure(String mes);//添加购物车失败

        void recommendInterface(SearchResp searchResp);//推荐呢商品

        void noRecommendInterface(String mes);//返回推荐上平失败

        void goodEval(GoodEvalResp goodEvalResp);//评价内容

        void noGoodEval(String mes);
    }
}
