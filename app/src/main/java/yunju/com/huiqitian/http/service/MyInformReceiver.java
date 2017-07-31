package yunju.com.huiqitian.http.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;

import cn.trinea.android.common.util.JsonUtils;
import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.MessageBean;
import yunju.com.huiqitian.entity.OrderMessageInfo;
import yunju.com.huiqitian.entity.PushInfo;
import yunju.com.huiqitian.utils.MyUtils;

/**
 * Created by gao on 2016/8/15 0015.
 */
public class MyInformReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.error(MyInformReceiver.class, "开始了12121221222121122212121212121212121212121");
        Bundle bundle = intent.getExtras();
        Log.d("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));

        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:
                // 获取透传数据
                String appid = bundle.getString("appid");
                byte[] payload = bundle.getByteArray("payload");

                String taskid = bundle.getString("taskid");
                String messageid = bundle.getString("messageid");

                // smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
                boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
                System.out.println("第三方回执接口调用" + (result ? "成功" : "失败"));

                if (payload != null) {
                    String data = new String(payload);// 消息
                    LogUtils.error(MyInformReceiver.class, data);

                    //发送广播
                    PushInfo pushInfo1 = JsonUtils.parseObject(data, PushInfo.class);
                    int type = pushInfo1.getType();
                    Object obj = pushInfo1.getObj();
                    LogUtils.error(MyInformReceiver.class,"..........................."+type+"............"+obj);
                    if (type==1){
                        MessageBean messageBean = JsonUtils.parseObject(String.valueOf(obj), MessageBean.class);
                        LogUtils.error(MyInformReceiver.class,"数据................."+messageBean.getTitle());
                        Intent intentMessage=new Intent(Constant.PUSH_ACTION);
                        Bundle bundleMessage=new Bundle();
                        bundleMessage.putSerializable(Constant.MY_MESSAGE_FROM_PUS,messageBean);
                        bundleMessage.putInt(Constant.MY_MESSAGE_FROM_TYPE,1);
                        intentMessage.putExtras(bundleMessage);
                        context.sendBroadcast(intentMessage);

                    }else  if (type==2){
                        OrderMessageInfo orderMessageInfo=JsonUtils.parseObject(String.valueOf(obj),OrderMessageInfo.class);
                        LogUtils.error(MyInformReceiver.class,"数据................"+orderMessageInfo.getMsg());
                        Intent intentOrder=new Intent(Constant.PUSH_ACTION);
                        Bundle bundleOrder=new Bundle();
                        bundleOrder.putSerializable(Constant.MY_MESSAGE_FROM_PUS,orderMessageInfo);
                        bundleOrder.putInt(Constant.MY_MESSAGE_FROM_TYPE, 2);
                        intentOrder.putExtras(bundleOrder);
                        context.sendBroadcast(intentOrder);
                    }

                }
                break;

            case PushConsts.GET_CLIENTID:
                // 获取ClientID(CID)
                // 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
                String cid = bundle.getString("clientid");
                Log.e("CID:", cid);
                MyUtils.saveCID(cid);
                break;
            case PushConsts.GET_SDKONLINESTATE:
                boolean online = bundle.getBoolean("onlineState");
                Log.d("GetuiSdkDemo", "online = " + online);
                break;

            default:
                break;
        }
    }

}
