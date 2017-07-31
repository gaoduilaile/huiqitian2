package yunju.com.huiqitian.http;

import com.http.session.DefaultResponseHandlerAdapter;
import com.http.session.HttpCallBack;
import com.http.session.HttpRequest;

import cn.trinea.android.common.util.JsonUtils;
import cn.trinea.android.common.util.LogUtils;
import cz.msebera.android.httpclient.Header;

/**
 * ResponseHandlerAdapter.java
 * <p/>
 * <pre>
 *     处理HttpClient接收到的网络响应数据
 * </pre>
 */
public class ResponseHandlerAdapter extends DefaultResponseHandlerAdapter {
    /**
     * Logger for this class
     */
    private static final String NETWORK_ERROR_CODE_0 = "您的网络不稳定，请再次尝试!";
    private static final String NETWORK_ERROR_CODE_500 = "服务器开小差了，请您稍后再试!";

    /*请求成功*/
    private static final int SUCCESS_CODE = 1;

    /*请求失败*/
    private static final int FAILURE_CODE = 0;

    public ResponseHandlerAdapter(HttpRequest httpRequest, HttpCallBack httpCallBack) {
        super(httpRequest, httpCallBack);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        super.onSuccess(statusCode, headers, responseBody);

        if (responseBody != null) {

            String response = new String(responseBody);
            LogUtils.error(ResponseHandlerAdapter.class, "1111111111"+response);

            CommonResponse resp = JsonUtils.parseObject(response, CommonResponse.class);
            if (resp != null) {
                if (Integer.valueOf(resp.getState()) == SUCCESS_CODE || Integer.valueOf(resp.getState()) == FAILURE_CODE ) {
                    getHttpCallBack().onSuccess(response);
                } else {
                    getHttpCallBack().onFailure(statusCode, response, null);
                }
            } else {
                getHttpCallBack().onFailure(statusCode, "", null);
            }
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        super.onFailure(statusCode, headers, responseBody, error);

        // TODO: 2016/7/12 0012  
//        String response = new String(responseBody);
//        LogUtils.error(ResponseHandlerAdapter.class, "00000000000"+response);
        
        if (responseBody == null || responseBody.length == 0) {
            getHttpCallBack().onFailure(statusCode, "", error);
        } else {
            getHttpCallBack().onFailure(statusCode, new String(responseBody), error);
        }
    }

}