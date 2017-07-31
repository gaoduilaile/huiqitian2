package yunju.com.huiqitian.vm.integral.model;

import android.content.Context;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.IntegralGoodsDuihuan;
import yunju.com.huiqitian.http.CommonResponse;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.ExchangeRecord;
import yunju.com.huiqitian.http.entity.InregralStoreReq;
import yunju.com.huiqitian.http.entity.IntegralGoodsResp;
import yunju.com.huiqitian.http.entity.IntegralListReq;

/**
 * Created by liuGang on 2016/8/15 0015.
 */
public class IntegralModel extends BaseModel {

    private IntegralStoreInterface integralStoreInterface;

    public IntegralModel(Context context) {
        super(context);
        integralStoreInterface = (IntegralStoreInterface) context;
    }

    /**
     * @param curPage 当前页数
     * @param rp      每页多少条
     */
    /*接口名称：查看所有积分商品
    接口Url：http://root_path/app/buyer/pointgood/list.do*/
    public void lookIntegralGoods(int curPage, int rp) {
        IntegralListReq req = new IntegralListReq(curPage, rp);
        sendGet(HttpConstant.PATH_POINT_GOOD_LIST, req, new HttpHandler() {
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                if (response != null) {
                    LogUtils.error(IntegralModel.class, "jsonLi查询所有积分商品" + response);
                    IntegralGoodsResp integralGoodsResp = parseObject(response, IntegralGoodsResp.class);
                    switch (integralGoodsResp.getState()) {
                        case Constant.ONE://查询成功
                            integralStoreInterface.integralGoods(response);
                            break;
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
                LogUtils.error(IntegralModel.class, "jsonLi查询所onFailure有积分商品" + response);
                //没有登录
                if (response != null) {
                    CommonResponse commonResponse = parseObject(response, CommonResponse.class);
                    if (commonResponse.getState() == Constant.TWO) {
                        //回调登录接口
                        integralStoreInterface.startToLoginAct();
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
     * 积分商品兑换记录
     *
     * @param curPage 当前页数
     * @param rp      每页多少条
     * @param type    兑换状态 （Byte ,1未兑换 2已兑换 3已过期）
     */

    public void exchangeRecord(int curPage, int rp, Byte type) {
        ExchangeRecord recordrReq = new ExchangeRecord(curPage, rp, type);
        sendGet(HttpConstant.PATH_EXCHANGE_LIST, recordrReq, new HttpHandler() {
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(IntegralModel.class, "jsonRe积分兑换记录" + response);
                if (response != null) {
                    IntegralGoodsDuihuan integralGoodsResp = parseObject(response, IntegralGoodsDuihuan.class);
                    switch (integralGoodsResp.getState()) {
                        case Constant.ONE://查询成功
                            integralStoreInterface.integralGoodsList(response);
                            break;
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
                //没有登录
                LogUtils.error(IntegralModel.class, "jsonRe积分onFailure兑换记录" + response);
                if (response != null) {
                    CommonResponse commonResponse = parseObject(response, CommonResponse.class);
                    if (commonResponse.getState() == Constant.TWO) {
                        //回调登录接口
                        integralStoreInterface.startToLoginAct();
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
     * 兑换商品
     *
     * @param idCode 积分商品id
     *               http://root_path/app/buyer/pointgood/exchange.do
     */
    public void exchangeGoods(int idCode) {
        InregralStoreReq intregralReq = new InregralStoreReq(idCode);
        sendGet(HttpConstant.PATH_POINT_GOODEXCHANGE_LIST, intregralReq, new HttpHandler() {
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                if (response != null) {
                    LogUtils.error(IntegralModel.class, "jsonEx兑换商品--------------" + response);
                    IntegralGoodsResp integralGoodsResp = parseObject(response, IntegralGoodsResp.class);

                    switch (integralGoodsResp.getState()) {
                        case Constant.ONE://查询成功
                            integralStoreInterface.exchangeGoodsSuccess(response);
                            break;
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

//            @Override
//            public void onFailure(int statusCode, String response, Throwable error) {
//                super.onFailure(statusCode, response, error);
//                //没有登录
//                LogUtils.error(IntegralModel.class, "jsonEx兑换onFailure商品--------------" + response);
//                if (response != null) {
//                    CommonResponse commonResponse = parseObject(response, CommonResponse.class);
//                    if (commonResponse.getState() == Constant.TWO) {
//                        //回调登录接口
//                        integralStoreInterface.startToLoginAct();
//                    }
//                }
//            }


        });
    }

    public interface IntegralStoreInterface {
        void integralGoods(String resp);//查询所有积分商品

        void integralGoodsList(String response);//积分商品兑换记录

        void startToLoginAct(); //未登录

        void exchangeGoodsSuccess(String response);//兑换商品
    }
}
