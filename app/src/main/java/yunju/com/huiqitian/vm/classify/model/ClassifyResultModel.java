package yunju.com.huiqitian.vm.classify.model;

import android.content.Context;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.ClassifyGoodsReq;
import yunju.com.huiqitian.http.entity.NoLocationResp;
import yunju.com.huiqitian.vm.classify.view.ClassifyResultActivity;

/**
 * 分类中查找的网络请求
 * Created by CCTV-1 on 2016/11/28 0028.
 */
public class ClassifyResultModel extends BaseModel {

    private ClassifyGoodsReq classifyGoodsReq;
    private ClassifyInterfaceModel classifyInterfaceModel;

    public ClassifyResultModel(Context context) {
        super(context);
    }

    /*网络请求的方法*/
    public void getClassifyGoods(int curpage,int rp,String sortname,String sortorder,int catId){
        classifyGoodsReq = new ClassifyGoodsReq();
        classifyGoodsReq.setCatId(catId);
        classifyGoodsReq.setCurpage(curpage);
        classifyGoodsReq.setRp(rp);
        classifyGoodsReq.setSortname(sortname);
        classifyGoodsReq.setSortorder(sortorder);

        sendGet(HttpConstant.PATH_CLASS_SORT, classifyGoodsReq, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(ClassifyResultActivity.class,"分类中的商品"+response);
                ClassifyGoodsReq classifyGoodsReq = parseObject(response, ClassifyGoodsReq.class);
                switch (classifyGoodsReq.getState()) {
                    case Constant.LOGIN_SUCCESS://查询失败（1）写反了
                        classifyInterfaceModel.addFailure(classifyGoodsReq.getMsg());
                        break;
                    case Constant.LOGIN_FAILURE://查询成功（0）
                        classifyInterfaceModel.addSuccess(response);
                        break;

                }

            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
                NoLocationResp noLocationResp=parseObject(response,NoLocationResp.class);
                switch (noLocationResp.getState()){
                    case Constant.LOACATION_ERROR://经纬度失败。。。后台的存经纬度的的缓存过期
                        classifyInterfaceModel.LocateError(noLocationResp.getMsg());
                        break;
                    case Constant.LOGIN_THREE:
                        classifyInterfaceModel.noMarket(classifyGoodsReq.getMsg());
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

    public void ClassifyInterface(ClassifyInterfaceModel classifyInterfaceModel){
        this.classifyInterfaceModel = classifyInterfaceModel;
    }
    /*设置接口*/
    public interface ClassifyInterfaceModel{
        void addSuccess(String obj);
        void addFailure(String msg);
        void LocateError(String mes);//获取不到当前位置
        void noMarket(String mes);
    }
}
