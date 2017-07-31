package yunju.com.huiqitian.vm.menu.view;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.autoupdatesdk.BDAutoUpdateSDK;
import com.baidu.autoupdatesdk.UICheckUpdateCallback;
import com.igexin.sdk.PushManager;

import cn.trinea.android.common.base.CommonFragmentActivity;
import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.AppApplication;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.service.MyLocationService;
import yunju.com.huiqitian.vm.find.view.FindActivity;

public class MainMenu extends CommonFragmentActivity implements View.OnClickListener {

    //静态变量
    public static Activity instance;

    /*声明控件变量*/
    private TextView tvMenuClass;//分类
    private TextView tvMenuHome;//主页
    private TextView tvMenuPerson;//我的
    private TextView tvMenuSopCart;//购物车

    //声明fragment成员变量
    private MenuHomeFragment menuHomeFragment;//主页fragment
    private MenuSopCartFragment menuSopCartFragment;//购物车fragment
    private MenuPersonFragment menuPersonFragment;//我的fragment
    private ClassifyFragment classifyFragment;//分类的fragment

    private static final int REQUEST_PERMISSION = 0;
    public static final int LOCATION_REQUEST_CODE = 1;
    private PushManager pushManger;
    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main_menu);
        instance = this;

    }


    @Override
    public void initBoot() {
        LogUtils.error(MainMenu.class, "initBoot");
        pushManger = PushManager.getInstance();
    }

    @Override
    public void initViews() {

        LogUtils.error(MainMenu.class, "initViews");
        //初始化控件
        tvMenuClass = findView(R.id.tv_menu_class);
        tvMenuHome = findView(R.id.tv_menu_home);
        tvMenuPerson = findView(R.id.tv_menu_person);
        tvMenuSopCart = findView(R.id.tv_menu_sopcart);

        //控件添加点击监听事件
        tvMenuClass.setOnClickListener(this);
        tvMenuHome.setOnClickListener(this);
        tvMenuPerson.setOnClickListener(this);
        tvMenuSopCart.setOnClickListener(this);


        //第一次进入menu页面默认底部导航在home页
        //设置图标为选中状态
         /* 接收从其他界面跳转到购物车*/
        Intent intent = getIntent();

         /*接收从登录界面，再跳到主页*/
        int login = intent.getIntExtra(Constant.LOGIN_TO_HOME, -1);

        /*接受从退出当前账号，在跳到我的页面*/
        int exitFlag = intent.getIntExtra(Constant.EXIT_LOGIN_TO_PERSONAL, -1);
        /*接受跳到购物车页面的数据*/
        int shopCart = intent.getIntExtra(Constant.SHOPCART, -1);

        /*从商品列表*/
        int goodListFlag = intent.getIntExtra(Constant.GOODS_LIST_TO_CART, -1);
        /*从商品详情*/
        int goodDetailFlag = intent.getIntExtra(Constant.GOODS_DETAIL_TO_CART, -1);
        //从确认订单页返回购物车刷新数据
        int affirmOrderFlag = intent.getIntExtra(Constant.AFFIRM_ORDER_TO_CART, -1);
        //从进口商品返回购物车
        int stuffGoosFlag = intent.getIntExtra(Constant.STUFF_GOODS_TO_CART, -1);

           /*显示购物车页面*/
        if (stuffGoosFlag == 1) {
            RestoreIconSelect();
            tvMenuSopCart.setSelected(true);
            RestoreTextColor();
            tvMenuSopCart.setTextColor(MainMenu.this.getResources().getColor(R.color.color_menu_btm_select));
            initFragment(2);
        }

        /*显示购物车页面*/
        if (shopCart == 3) {
            RestoreIconSelect();
            tvMenuSopCart.setSelected(true);
            RestoreTextColor();
            tvMenuSopCart.setTextColor(MainMenu.this.getResources().getColor(R.color.color_menu_btm_select));
            initFragment(2);
        }

        /*显示我的页面*/
        if (exitFlag == 4 ) {
            RestoreIconSelect();
            tvMenuPerson.setSelected(true);
            RestoreTextColor();
            tvMenuPerson.setTextColor(MainMenu.this.getResources().getColor(R.color.color_menu_btm_select));
            initFragment(3);
        }


        if (login == 1) {
            //显示MenuHomeFragment
            RestoreIconSelect();
            tvMenuHome.setSelected(true);
            RestoreTextColor();
            tvMenuHome.setTextColor(MainMenu.this.getResources().getColor(R.color.color_menu_btm_select));
            initFragment(0);
        }
        if(stuffGoosFlag==-1&&shopCart==-1&&exitFlag==-1&&login==-1){
            RestoreIconSelect();
            tvMenuHome.setSelected(true);
            RestoreTextColor();
            //设置字体为选中状态
            tvMenuHome.setTextColor(MainMenu.this.getResources().getColor(R.color.color_menu_btm_select));
            initFragment(0);
        }

    }

    private class MyUICheckUpdateCallback implements UICheckUpdateCallback {

        @Override
        public void onCheckComplete() {
        }

    }

    @Override
    public void initData(Bundle bundle) {
        /*百度自动更新只有从引导页面进来的是时候，检测即可。取出从MainActivity中取出来的数据*/
        int upData = getIntent().getIntExtra("upData", 0);

        if (upData == 1) {
            /**
             * 百度自动更新SDK
             */
            BDAutoUpdateSDK.uiUpdateAction(this, new MyUICheckUpdateCallback());
        }

        LogUtils.error(MainMenu.class, "initdate走进来了");
        if (Build.VERSION.SDK_INT >= 23) {
            LogUtils.error(MainMenu.class, "走进来了");
            PackageManager pkgManager = getPackageManager();
            // 读写 sd card 权限非常重要, android6.0默认禁止的, 建议初始化之前就弹窗让用户赋予该权限
            boolean sdCardWritePermission =
                    pkgManager.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, getPackageName()) == PackageManager.PERMISSION_GRANTED;
            // read phone state用于获取 imei 设备信息
            boolean phoneSatePermission =
                    pkgManager.checkPermission(Manifest.permission.READ_PHONE_STATE, getPackageName()) == PackageManager.PERMISSION_GRANTED;

            if (Build.VERSION.SDK_INT >= 23 && !sdCardWritePermission || !phoneSatePermission) {
                requestPermission();
            } else {
                LogUtils.error(MainMenu.class, "走进来了开始实例化了");
                // SDK初始化，第三方程序启动时，都要进行SDK初始化工作
                PushManager.getInstance().initialize(MainMenu.this);
            }

            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //申请定位需要的权限
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainMenu.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Log.e("zmm", "这个地方是点击拒绝以后的，需要给用户个解释");
                } else {
                    Log.e("zmm", "这个地方开始去申请权限");//申请权限成功以后会去到onRequestPermissionsResult()方法里面执行，根据请求码去处理就可以了
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            LOCATION_REQUEST_CODE);//最后一个参数是请求码
                }

            } else {//这里是已经有权限了
                if (hasNetWork()) {
                    LogUtils.error(MainMenu.class, "-------------有网络 ，开启定位");
                    openMyService();//开启服务
                }
            }


        } else {
            if (hasNetWork()) {
                openMyService();//开启服务
            }

            PushManager.getInstance().initialize(this.getApplicationContext());
            String cid = pushManger.getClientid(this);
        }
    }

    /*添加权限*/
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE},
                REQUEST_PERMISSION);
    }

    /*权限*/
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == REQUEST_PERMISSION) {
            if ((grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                PushManager.getInstance().initialize(MainMenu.this);
            } else {
                Log.e("GetuiSdkDemo",
                        "we highly recommend that you need to grant the special permissions before initializing the SDK, otherwise some "
                                + "functions will not work");
                PushManager.getInstance().initialize(MainMenu.this);
            }
        } else if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                if (hasNetWork()) {
                    LogUtils.error(MainMenu.class, "-------------有网络 ，开启定位");
                    openMyService();//开启服务
                }
            } else {
                Toast.makeText(MainMenu.this, "申请权限失败", Toast.LENGTH_SHORT).show();
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
            return;
        } else {
            onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void initEvents() {

    }


    /*底部导航TextView的点击事件的处理*/
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            //点击首页
            case R.id.tv_menu_home:
                //恢复底部导航所有图标为默认图标
                RestoreIconSelect();
                initFragment(0);
                break;

            //点击分类
            case R.id.tv_menu_class:

                initFragment(1);
                break;
            //点击购物车
            case R.id.tv_menu_sopcart:
                initFragment(2);
                break;
            //点击我的
            case R.id.tv_menu_person:
                initFragment(3);
                break;
            case R.id.iv_menu_top_search:
                startActivity(FindActivity.class);
                break;
        }
    }

    /*设置底部导航图片全部为为未选中状态*/
    private void RestoreIconSelect() {
        //设置底部导航图标的为初始图标
        tvMenuClass.setSelected(false);
        tvMenuHome.setSelected(false);
        tvMenuPerson.setSelected(false);
        tvMenuSopCart.setSelected(false);
    }

    /*设置底部导航字体全部为未选中时的状态*/
    private void RestoreTextColor() {
        //设置底部导航字体的颜色为初始颜色
        tvMenuClass.setTextColor(MainMenu.this.getResources().getColor(R.color.color_menu_btm_default));
        tvMenuHome.setTextColor(MainMenu.this.getResources().getColor(R.color.color_menu_btm_default));
        tvMenuPerson.setTextColor(MainMenu.this.getResources().getColor(R.color.color_menu_btm_default));
        tvMenuSopCart.setTextColor(MainMenu.this.getResources().getColor(R.color.color_menu_btm_default));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 打开服务
     */
    private void openMyService() {
         /*启动service*/
        Intent intent = new Intent(activity, MyLocationService.class);
        startService(intent);
    }


    /**
     * Created by gaoqiong on 2016/9/21 0021 11:14
     * 捕捉返回事件按钮
     * 因为此 Activity 继承 TabActivity 用 onKeyDown 无响应，所以改用 dispatchKeyEvent
     * 一般的 Activity 用 onKeyDown 就可以了
     */

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                this.exitApp();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


    /**
     * Created by gaoqiong on 2016/9/21 0021 11:14
     * 退出程序
     **/
    private void exitApp() {
        // 判断2次点击事件时间
        if ((System.currentTimeMillis() - exitTime) > 2500) {
            Toast.makeText(MainMenu.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }

    }

    public void initFragment(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        RestoreIconSelect();/*设置底部导航图片全部为为未选中状态*/
        RestoreTextColor();//恢复底部导航所有字体默认颜色
        switch (index) {
            case 0:
                if (menuHomeFragment == null) {
                    menuHomeFragment = new MenuHomeFragment();
                    transaction.add(R.id.fl_menu_content, menuHomeFragment);
                    AppApplication.putBoolean("home_fragment_refresh", false);
                } else {
                    transaction.show(menuHomeFragment);
                }
                //将当前选择的底部导航图标切换成已选择状态
                tvMenuHome.setSelected(true);
                //将当前选择的底部导航字体切换成已选择状态
                tvMenuHome.setTextColor(MainMenu.this.getResources().getColor(R.color.color_menu_btm_select));
                break;
            case 1:
                if (classifyFragment == null) {
                    classifyFragment = new ClassifyFragment();
                    transaction.add(R.id.fl_menu_content, classifyFragment);
                } else {
                    transaction.show(classifyFragment);
                }
                tvMenuClass.setSelected(true);
                tvMenuClass.setTextColor(MainMenu.this.getResources().getColor(R.color.color_menu_btm_select));
                break;
            case 2:
              /*  if (menuSopCartFragment == null) {*/
                    menuSopCartFragment = new MenuSopCartFragment();
                    transaction.add(R.id.fl_menu_content, menuSopCartFragment);
                    AppApplication.putBoolean("shop_fragment_refresh", false);
                    LogUtils.error( MainMenu.class,"...............................");
               /* } else {
                    transaction.show(menuSopCartFragment);
                    LogUtils.error(MainMenu.class, "1234564578979879879879879798798797");
                }*/
                tvMenuSopCart.setSelected(true);
                tvMenuSopCart.setTextColor(MainMenu.this.getResources().getColor(R.color.color_menu_btm_select));
                break;
            case 3:
                if (menuPersonFragment == null) {
                    menuPersonFragment = new MenuPersonFragment();
                    transaction.add(R.id.fl_menu_content, menuPersonFragment);
                    AppApplication.putBoolean("person_fragment_refresh", false);
                } else {
                    transaction.show(menuPersonFragment);

                }
                tvMenuPerson.setSelected(true);
                tvMenuPerson.setTextColor(MainMenu.this.getResources().getColor(R.color.color_menu_btm_select));
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (menuHomeFragment != null) {
            transaction.hide(menuHomeFragment);
        }
        if (classifyFragment != null) {
            transaction.hide(classifyFragment);
        }
        if (menuSopCartFragment != null) {
            transaction.hide(menuSopCartFragment);
        }
        if (menuPersonFragment != null) {
            transaction.hide(menuPersonFragment);
        }

    }
}
