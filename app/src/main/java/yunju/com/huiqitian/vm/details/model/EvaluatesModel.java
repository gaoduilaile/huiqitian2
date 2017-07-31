package yunju.com.huiqitian.vm.details.model;

import android.content.Context;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.EvalTextResp;
import yunju.com.huiqitian.http.entity.EvaluateListReq;

/**
 * Created by Administrator on 2016/10/8 0008.
 * <p/>
 * 商品评论 网络接口回调
 */
public class EvaluatesModel extends BaseModel {

    private EvalTextInterface evalTextInterface;

    public EvaluatesModel(Context context) {
        super(context);
        evalTextInterface= (EvalTextInterface) context;
    }

    /**
     * 获取全部评价、好评、中评、差评类容
     */
    public void EvaluateList(int id,byte type,int curpage,int rp,int evalLevelId){
        EvaluateListReq evaluateListReq=new EvaluateListReq();
        evaluateListReq.setId(id);
        evaluateListReq.setCurpage(curpage);
        evaluateListReq.setRp(rp);
        evaluateListReq.setType(type);
        evaluateListReq.setEvalLevelId(evalLevelId);
        sendGet(HttpConstant.PATH_EVAL_LIST, evaluateListReq, new HttpHandler() {

            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                LogUtils.error(EvaluatesModel.class,response);
                EvalTextResp evalTextResp=parseObject(response,EvalTextResp.class);
                switch (evalTextResp.getState()) {
                    case Constant.LOGIN_FAILURE://添加成功（1）
                        evalTextInterface.evalTextSuccess(evalTextResp);
                        break;
                    case Constant.LOGIN_SUCCESS://添加失败（0）
                        evalTextInterface.evalTextFailure(evalTextResp.getMsg());
                        break;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
                LogUtils.error(DetailsModel.class, "错误了错误了错误了错误了错误了错误了错误了错误了");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpLoadingDialog.dismiss();
            }
        });

    }

    public interface EvalTextInterface{
        void evalTextSuccess(EvalTextResp evalTextResp);
        void evalTextFailure(String mes);
    }
}
