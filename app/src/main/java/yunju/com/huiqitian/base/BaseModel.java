package yunju.com.huiqitian.base;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.http.session.HttpCallBack;
import com.loopj.android.http.RequestParams;
import com.mirror.common.commondialog.httploadingdialog.HttpLoadingDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import cn.trinea.android.common.base.CommonModel;
import cn.trinea.android.common.util.PreferenceHelper;
import yunju.com.huiqitian.AppApplication;
import yunju.com.huiqitian.http.CommonResponse;
import yunju.com.huiqitian.http.HttpClient;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.RequestParamsUtil;

/**
 * 父类Model
 * Created by 高英祥 on 16/1/4.
 */
public class BaseModel extends CommonModel {

    public Context context;
    public HttpClient httpClient;
    public PreferenceHelper preferenceHelper;
    public HttpLoadingDialog httpLoadingDialog;

    public BaseModel(Context context) {
        super(context);

        this.context = context;
        httpClient = AppApplication.getHttpClient();
        preferenceHelper = AppApplication.getPreferenceHelper();
        httpLoadingDialog = new HttpLoadingDialog(context);
    }

    /**
     * 判断object是否为空
     *
     * @param obj
     * @return
     */
    public boolean isEmpty(Object obj) {
        return obj == null;
    }

    /**
     * 判断object是否为非空
     *
     * @param obj
     * @return
     */
    public boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 判断没有网络
     *
     * @return
     */
    public boolean noNetWork() {
        return !hasNetWork();
    }

    /**
     * 无网络的提醒
     */
    public void showNetWorkError() {
        showToast("请检查您的网络!");
    }

    /**
     * 处理Http请求失败之后的响应
     *
     * @param response
     */
    protected void handleFailure(String response) {
        CommonResponse resp = parseObject(response, CommonResponse.class);
        if (resp != null && isNotEmpty(resp.getMsg())) {
            showToast(resp.getMsg());
        }
    }

    /**
     * 把Object对象转换为RequestParams对象
     *
     * @param object
     * @return
     */
    public static RequestParams toParams(Object object) {
        return RequestParamsUtil.toRequestParams(object);
    }

    /**
     * 发送Get请求
     *
     * @param url
     * @param httpHandler
     */
    public void sendGet(String url, HttpHandler httpHandler) {
        if (hasNetWork()) {
            httpClient.sendGet(url, httpHandler);
        } else {
            showNetWorkError();
        }
    }

    /**
     * 发送Post请求
     *
     * @param url
     * @param httpHandler
     */
    public void sendPost(String url, HttpHandler httpHandler) {
        if (hasNetWork()) {
            httpClient.sendPost(url, httpHandler);
        } else {
            showNetWorkError();
        }
    }

    /**
     * 发送Post请求
     *
     * @param url
     * @param req
     * @param httpHandler
     */
    public void sendPost(String url, Object req, HttpHandler httpHandler) {
        if (hasNetWork()) {
            httpClient.sendPost(url, toJSONString(req), httpHandler);
        } else {
            showNetWorkError();
        }
    }

    /**
     * 发送Get请求
     *
     * @param url
     * @param req
     * @param httpHandler
     */
    public void sendGet(String url, Object req, HttpHandler httpHandler) {
        if (hasNetWork()) {
            httpClient.sendGet(url, toParams(req), httpHandler);
        } else {
            showNetWorkError();
        }
    }

    public <T> List<T> parseArray(String text, Class<T> clazz) {
        try {
            return JSON.parseArray(text, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 文件上传
     * @param url
     * @param file
     * @param httpCallBack
     */
    public void sendFile(String url,File file,HttpCallBack httpCallBack){
        RequestParams params  = new RequestParams();
        try {
            params.put("file",file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        httpClient.sendPost(url,params,httpCallBack);
    }
}

