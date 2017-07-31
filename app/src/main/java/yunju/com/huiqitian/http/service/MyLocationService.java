package yunju.com.huiqitian.http.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.utils.MyUtils;

/**
 * Created by 高英祥 on 2016/7/19 0019.
 */
public class MyLocationService extends Service implements BDLocationListener {

    private LocationClient locationClient;//定位管理类
    private LocationClientOption option;//设置定位参数

    /*控制定位的广播*/
    private ControlLocationReceiver controlReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.error(MyLocationService.class, "服务已经启动");
        /*打开广播*/
        openBroadCast();
        init();
    }

    private void init() {
        /*定位相关*/
        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(this);
        /*参数相关*/
        option = new LocationClientOption();
        option.setCoorType("bd0911");
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置高精度定位定位模式
        option.setOpenGps(true);//打开gps
        option.setIsNeedAddress(true);//需要地址信息
        option.setPriority(LocationClientOption.NetWorkFirst); // 设置定位优先级
        option.setIsNeedLocationDescribe(true);
        option.disableCache(true);//不要缓存
        option.setScanSpan(3000);
        /*设置参数*/
        locationClient.setLocOption(option);
        locationClient.start();
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        LogUtils.error(MyLocationService.class, "定位成功");

        //蜀山区 黄山路 624号
        if (MyUtils.getLocationType().equals("one")) {
            //定位之后获得信息然后通过广播发送到主界面显示
            if ((bdLocation.getLocationDescribe().toString().charAt(0) + "").trim().equals("在")) {
                //取出后两位判断是不是附近两个字。
                if ((bdLocation.getLocationDescribe().toString().charAt(bdLocation.getLocationDescribe().length() - 2) + ""
                        + bdLocation.getLocationDescribe().toString().charAt(bdLocation.getLocationDescribe().length() - 1) + "").equals("附近")) {

                    LogUtils.error(MyLocationService.class, "............................................" + bdLocation.getLocationDescribe().toString().substring(1, bdLocation.getLocationDescribe().length() - 2));

                    //发送广播到MenuHomeFragment
                    Intent intent = new Intent(Constant.MY_BROADCAST_ACTION_HOME);
                    intent.putExtra(Constant.MY_LOCATION_MSG_KEY, bdLocation.getLocationDescribe().toString().substring(1, bdLocation.getLocationDescribe().length() - 2));

                    intent.putExtra(Constant.MY_LOCATION_MSG_STREET, bdLocation.getAddress().district + bdLocation.getAddress().street + bdLocation.getAddress().streetNumber);//街道

                    intent.putExtra(Constant.MY_LATITUDE, bdLocation.getLatitude());//经度
                    intent.putExtra(Constant.MY_Longitude, bdLocation.getLongitude());//维度
                    intent.putExtra(Constant.MY_CITY, bdLocation.getCity());


                    intent.putExtra(Constant.MY_LOCATION_TYPE, 0); //service 中来的广播

                    sendBroadcast(intent);
                } else {
                    //发送广播
                    Intent intent = new Intent(Constant.MY_BROADCAST_ACTION_HOME);
                    intent.putExtra(Constant.MY_LOCATION_MSG_KEY, bdLocation.getLocationDescribe().toString().substring(1));
                    intent.putExtra(Constant.MY_LATITUDE, bdLocation.getLatitude());//经度
                    intent.putExtra(Constant.MY_Longitude, bdLocation.getLongitude());//维度
                    intent.putExtra(Constant.MY_CITY, bdLocation.getCity());

                    intent.putExtra(Constant.MY_LOCATION_MSG_STREET, bdLocation.getAddress().district + bdLocation.getAddress().street + bdLocation.getAddress().streetNumber);//街道

                    intent.putExtra(Constant.MY_LOCATION_TYPE, 0); //service 中来的广播

                    sendBroadcast(intent);
                }

            } else {
                Log.e("位置3：", bdLocation.getLocationDescribe().toString().substring(1));
                //发送广播
                Intent intent = new Intent(Constant.MY_BROADCAST_ACTION_HOME);
                intent.putExtra(Constant.MY_LOCATION_MSG_KEY, bdLocation.getLocationDescribe().toString().substring(1));
                intent.putExtra(Constant.MY_LOCATION_MSG_STREET, bdLocation.getAddress().district + bdLocation.getAddress().street + bdLocation.getAddress().streetNumber);//街道

                intent.putExtra(Constant.MY_LOCATION_TYPE, 0); //service 中来的广播

                sendBroadcast(intent);
            }
        }else if(MyUtils.getLocationType().equals("two")){
            Intent intent = new Intent(Constant.MY_BROADCAST_ACTION);
            intent.putExtra(Constant.MY_LOCATION_MSG_KEY, bdLocation.getLocationDescribe().toString().substring(1));
            intent.putExtra(Constant.MY_LATITUDE, bdLocation.getLatitude());//经度
            intent.putExtra(Constant.MY_Longitude, bdLocation.getLongitude());//维度
            intent.putExtra(Constant.MY_CITY,bdLocation.getCity());

            intent.putExtra(Constant.MY_LOCATION_MSG_STREET, bdLocation.getAddress().district + bdLocation.getAddress().street + bdLocation.getAddress().streetNumber);//街道

            intent.putExtra(Constant.MY_LOCATION_TYPE,0); //service 中来的广播

            sendBroadcast(intent);
        }





    }

    /**
     * 接受定位广播接收器
     */
    public class ControlLocationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.error(MyLocationService.class, "广播启动成功");
            switch (intent.getIntExtra(Constant.MY_CONTROL_MSG_KEY, 10)) {
                case Constant.OPEN:
                    startLocation();
                    break;
                case Constant.CLOSE:
                    LogUtils.error(MyLocationService.class, "接受到关闭的指令");
                    stopLocation();
                    break;
            }
        }
    }
    /**
     * 打开广播
     */
    private void openBroadCast() {
        controlReceiver = new ControlLocationReceiver();
        IntentFilter itFilter = new IntentFilter();
        itFilter.addAction(Constant.MY_CONTROL_ACTION);
        registerReceiver(controlReceiver, itFilter);




    }

    /**
     * 开启定位
     */
    private void startLocation() {
        locationClient.start();//开启定位
    }

    /**
     * 关闭定位
     */
    private void stopLocation() {
        locationClient.stop();
        LogUtils.error(MyLocationService.class, "关闭广播成功");
    }
}
