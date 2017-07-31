package yunju.com.huiqitian.vm.vouchers.model;

import android.content.Context;

import java.util.List;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.CommonResponse;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.SearchResp;
import yunju.com.huiqitian.http.entity.VoucherGoodsReq;
import yunju.com.huiqitian.http.entity.VoucherReq;
import yunju.com.huiqitian.http.entity.VoucherResp;

/**
 * Created by 张超群 on 2016-08-11.
 * <p/>
 * 代金券model
 */
public class VoucherModel extends BaseModel {

    private VoucherInterface voucherInterface;

    public VoucherModel(Context context) {
        super(context);
        voucherInterface = (VoucherInterface) context;
    }


    /**
     * 代金券请求数据
     *
     * @param status 1未使用 2已使用 3已过期
     */
    public void getVouchers(final int status) {
        VoucherReq voucherReq = new VoucherReq();
        voucherReq.setStatus((byte) status);
        sendGet(HttpConstant.PATH_VOUCHER, voucherReq, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            public void onSuccess(String response) {
                super.onSuccess(response);
                CommonResponse commonResponse = parseObject(response, CommonResponse.class);
                LogUtils.error(VoucherResp.class, "代金券：" + commonResponse.getObj());
                List<VoucherResp> voucherRespList = parseArray(commonResponse.getObj(), VoucherResp.class);
                LogUtils.error(VoucherResp.class, "代金券数量：" + voucherRespList.size());
                switch (commonResponse.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        voucherInterface.voucherDateSuccess(voucherRespList,status);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        voucherInterface.voucherDateFailure(commonResponse.getMsg());
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

    /*代金券商品*/
    public void getVoucherGoods(int curpage, int rp,String sortname, String sortorder,int voucherId) {
        VoucherGoodsReq voucherGoodsReq=new VoucherGoodsReq();
        voucherGoodsReq.setCurpage(curpage);
        voucherGoodsReq.setRp(rp);
        voucherGoodsReq.setSortname(sortname);
        voucherGoodsReq.setSortorder(sortorder);
        voucherGoodsReq.setVoucherId(voucherId);
        sendGet(HttpConstant.PATH_GOOD_SEARCH, voucherGoodsReq, new HttpHandler() {
            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                SearchResp searchResp = parseObject(response, SearchResp.class);
                switch (searchResp.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        voucherInterface.voucherGoods(searchResp);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        voucherInterface.noVoucherGoods(searchResp.getMsg());
                        break;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
            }
        });
    }

    /*代金券请求接口*/
    public interface VoucherInterface {
        void voucherDateSuccess(List<VoucherResp> voucherRespList,int status);

        void voucherDateFailure(String msg);

        void voucherGoods(SearchResp searchResp);

        void noVoucherGoods(String msg);
    }
}
