package yunju.com.huiqitian.utils.pay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import java.util.HashMap;

/**
 * Created by zcq on 2016/6/8.
 * http://hiapk.org
 */
public class AliPayUtils {
    Activity context;
    AliPlayInterface aliPlaylisten;
    public interface AliPlayInterface{
        public void fail();
        public void success(String result, HashMap<String, String> map);
        public void ensureing();

    }

    public AliPayUtils(Activity context){
        this.context=context;
    }
    private static final int SDK_PAY_FLAG = 1;
    @SuppressLint("HandlerLeak")
    public   Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {
                        String[] strs = resultInfo.split("&");
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("str",resultInfo);
                        for (int i = 0; i < strs.length; i++) {
                            map.put(strs[i].split("=")[0], strs[i].split("=")[1]);
                        }
                        Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
                        aliPlaylisten.success(resultStatus,map);
                    } else {
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(context, "支付结果确认中", Toast.LENGTH_SHORT).show();
                            aliPlaylisten.ensureing();

                        } else {
                            Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
                            aliPlaylisten.fail();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    /**
     *
     *
     * @param payMessage
     * @param aliPlaylisten
     */
    public  void payGood(String payMessage,AliPlayInterface aliPlaylisten){
        this.aliPlaylisten=aliPlaylisten;
        final String orderInfo=payMessage;
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(context);
                String result = alipay.pay(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }



}

