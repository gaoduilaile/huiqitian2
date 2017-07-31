package yunju.com.huiqitian.http;

import android.content.Context;

import com.http.session.BaseHttpClient;
import com.http.session.HttpCallBack;
import com.http.session.HttpLoginInterface;
import com.http.session.HttpRequest;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 网络连接类
 */
public class HttpClient extends BaseHttpClient implements HttpLoginInterface {

    private Context context;

    public static final int SEND_TYPE_FILE = 2;

    private static final String TAG = HttpClient.class.getSimpleName();

    @Override
    public ResponseHandlerInterface getResponseHandler(HttpRequest httpRequest, HttpCallBack httpCallBack) {
        return new ResponseHandlerAdapter(httpRequest,httpCallBack);
    }

    @Override
    public void login(String username, String passWord, final HttpCallBack httpCallBack) {

    }

    /**
     * 上传文件
     *
     * @param url
     * @param file
     */
    public void sendFile(String url, File file, HttpCallBack httpCallBack) {

        RequestParams params = new RequestParams();
        try {
            params.put("file", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        sendPost(url, params, httpCallBack);
    }

}
