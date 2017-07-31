package yunju.com.huiqitian.vm.menu.model;

import android.content.Context;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.CartGoods;
import yunju.com.huiqitian.http.CommonResponse;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.AlterGoodsNumReq;
import yunju.com.huiqitian.http.entity.AlterGoodsNumResp;
import yunju.com.huiqitian.http.entity.CartSettleResp;
import yunju.com.huiqitian.http.entity.LookCartResp;

/**
 * Created by liuGang on 2016/8/4 0004.
 */
public class ShopCartModel extends BaseModel {
    //ShopCartModel中的接口
    private CartGoodsInterface cartGoodsInterface;
    public void setCartGoodsInterface(CartGoodsInterface cartGoodsInterface) {
        //接口通过外部set进行实例化
        this.cartGoodsInterface = cartGoodsInterface;
    }

    public ShopCartModel(Context context) {
        super(context);
    }

    /**
     * 获取查看购物车的数据
     *
     * @param
     */
    public void lookShopCart() {

        sendGet(HttpConstant.PATH_CART_LIST, new HttpHandler() {
            //用户已登录


            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LookCartResp lookCartResp = parseObject(response, LookCartResp.class);

                switch (lookCartResp.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        cartGoodsInterface.cartGoods(response);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        cartGoodsInterface.noCartGoods(lookCartResp.getMsg());
                        break;
                }
            }

            //没有登录
            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
                LogUtils.error(ShopCartModel.class, "startact" + response);
                //没有登录
                if (response != null) {
                    CommonResponse commonResponse = parseObject(response, CommonResponse.class);
                    if (commonResponse.getState() == Constant.LOGIN_NONE) {
                        //回调登录接口
                        cartGoodsInterface.startToLoginAct();
                    }

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
     * 修改购物车产品数量
     * objectList中既有CartMarket对象也有CartGoods对象
     *
     * @param goodsNumList
     */
    public void alterGoodsNum( List<AlterGoodsNumReq> goodsNumList) {

        LogUtils.error(ShopCartModel.class, "Jsonalter" + JSON.toJSONString(goodsNumList));

        //传递list集合形式的参数到网络请求
        sendPost(HttpConstant.PATH_CART_ALTERQTY, goodsNumList, new HttpHandler() {
            //用户已登录
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                AlterGoodsNumResp alterGoodsNumResp = parseObject(response, AlterGoodsNumResp.class);
                switch (alterGoodsNumResp.getState()) {
                    case Constant.LOGIN_FAILURE://修改成功（1）
                        cartGoodsInterface.alterGoodsSuccess();
                        break;
                    case Constant.LOGIN_SUCCESS://修改失败（0）

                        break;
                }
            }

            //没有登录
            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
            }
        });

    }

    /**
     * 从购物车中删除产品
     *
     * @param delGoodsList
     */
    public void delCartGoods(List<CartGoods> delGoodsList) {
        List<Integer> cartGoodsIds = new ArrayList<Integer>();
        //遍历集合获取要删除的CartGoods的id添加到集合cartGoodsIds
        for (CartGoods cartGoods : delGoodsList) {
            cartGoodsIds.add(cartGoods.getId());
        }

        //传递id集合列表到网络请求
        sendPost(HttpConstant.PATH_CART_DEL, cartGoodsIds, new HttpHandler() {
            //用户已登录
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                CommonResponse delResponse = parseObject(response, CommonResponse.class);
                LogUtils.error(ShopCartModel.class, "Jsondel" + response);
                //删除成功后回调方法
                switch (delResponse.getState()) {
                    case Constant.LOGIN_FAILURE://删除成功（1）
                        cartGoodsInterface.delGoodsSuccess();
                        break;
                    case Constant.LOGIN_SUCCESS://删除失败（0）

                        break;
                }
            }

            //没有登录
            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
            }
        });

    }


    /**
     *
     * 购物车去结算
     * @param settleGoodsList
     */
    public void goSettle(List<CartGoods> settleGoodsList){
        List<Integer> cartGoodsIds = new ArrayList<Integer>();
        //遍历集合获取要删除的CartGoods的id添加到集合cartGoodsIds
        for (CartGoods cartGoods : settleGoodsList) {
            cartGoodsIds.add(cartGoods.getId());
        }

        //传递id集合列表到网络请求
        sendPost(HttpConstant.PATH_CART_ESTIMATE, cartGoodsIds, new HttpHandler(){
            //用户已登录
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);

                CartSettleResp settleResp = parseObject(response, CartSettleResp.class);
                LogUtils.error(ShopCartModel.class, "Jsonsettle" + response);
                //去结算请求成功后回调方法
                switch (settleResp.getState()){
                    case Constant.LOGIN_FAILURE://去结算成功（1）
                        cartGoodsInterface.settleData(response);
                        break;
                    case Constant.LOGIN_SUCCESS://去结算失败（0）
                        showToast(settleResp.getMsg());
                        break;
                }
            }

            //没有登录
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
     * 购物车接口（查看购物车，修改购物车数量）
     */
    public interface CartGoodsInterface {
        //请求购物车列表获取到产品
        void cartGoods(String resp);
        //请求购物车列表无产品
        void noCartGoods(String msg);
        //购物车产品修改成功
        void alterGoodsSuccess();
        //跳转登录页面
        void startToLoginAct();
        //删除成功
        void delGoodsSuccess();
        //去结算 获取确认订单列表成功
        void settleData(String resp);
    }

}
