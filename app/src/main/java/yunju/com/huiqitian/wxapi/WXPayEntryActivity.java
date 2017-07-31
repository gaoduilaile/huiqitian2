package yunju.com.huiqitian.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import cn.trinea.android.common.util.LogUtils;
import cn.trinea.android.common.util.ToastUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.vm.orders.view.OrderDetailedActivity;
import yunju.com.huiqitian.vm.pay.view.PayActivity;

/**
 * 微信支付页面
 * Created by CCTV-1 on 2017/1/10 0010.
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PayActivity.payActivity.finish();
        overridePendingTransition(R.anim.go_in, R.anim.go_out);
        api = WXAPIFactory.createWXAPI(this, Constant.WECHAT_APPID);
        api.handleIntent(getIntent(), this);

    }

    @Override
    public void onReq(BaseReq baseReq) {
    }


    @Override
    public void onResp(BaseResp baseResp) {
        int errCode = baseResp.errCode;

        if (baseResp.getType()== ConstantsAPI.COMMAND_PAY_BY_WX){
            if (errCode == 0) {
                //0 成功 展示成功页面
                /*取出保存的支付编号*/
                String payId = MyUtils.getPayId();
                Intent intent = new Intent(WXPayEntryActivity.this,OrderDetailedActivity.class);
                LogUtils.error(WXPayEntryActivity.class, "订单编号取出来的是：" + payId);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.ORDER_GOOD_ID, payId);
                bundle.putInt(Constant.DELETE_ORDER, 6);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.go_in,R.anim.go_out);

            } else if (errCode == -1) {
                //-1 错误 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
                ToastUtils.show(WXPayEntryActivity.this, "支付失败");
            } else if (errCode == -2) {
                //-2 用户取消 无需处理。发生场景：用户不支付了，点击取消，返回APP。
                ToastUtils.show(WXPayEntryActivity.this,"已取消订单");
            }
            finish();
        }
    }
}

