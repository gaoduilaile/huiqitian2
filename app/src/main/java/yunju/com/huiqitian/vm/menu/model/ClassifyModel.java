package yunju.com.huiqitian.vm.menu.model;

import android.content.Context;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.LeftClassifyReq;

/**
 * 分类页面的网络请求
 * Created by CCTV-1 on 2016/11/25 0025.
 */
public class ClassifyModel extends BaseModel {
    private LeftModelInterface leftModelInterface;

    public ClassifyModel(Context context) {
        super(context);
    }
    /*父listView的网络请求*/
    public void leftListView(){
        sendGet(HttpConstant.PATH_CLASSIFY_FU, new HttpHandler() {

            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(ClassifyModel.class, "父" + response);
                /*解析数据*/

                LeftClassifyReq leftClassifyReq = parseObject(response, LeftClassifyReq.class);
                switch (leftClassifyReq.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        leftModelInterface.addSuccess(response);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        leftModelInterface.addFailure(leftClassifyReq.getMsg());
                        break;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
                LogUtils.error(ClassifyModel.class, "父控件请求网络数据+++失败");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpLoadingDialog.dismiss();
            }
        });
    }

    /*实现左边的接口实现方法*/
    public void LeftInterface(LeftModelInterface leftModelInterface){
        this.leftModelInterface = leftModelInterface;

    }
    /*添加左边回调接口*/
    public interface LeftModelInterface{
        void addSuccess(String obj);
        void addFailure(String msg);
    }
}
