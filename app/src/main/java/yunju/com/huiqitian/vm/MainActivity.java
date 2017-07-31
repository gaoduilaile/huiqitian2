package yunju.com.huiqitian.vm;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import yunju.com.huiqitian.AppApplication;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.vm.adapter.WelcomeAdapter;
import yunju.com.huiqitian.vm.login.model.LoginModel;
import yunju.com.huiqitian.vm.login.view.LoginActivity;
import yunju.com.huiqitian.vm.menu.view.MainMenu;

/**
 * 引导页界面
 * create by 高英祥 2016/7/13 0013
 */
public class MainActivity extends BaseActivity implements LoginModel.LoginInterface {

    /*数据相关*/
    private LoginModel loginModel;//登陆的model

    private Handler handler = new Handler();

    /*引导页相关*/
    private RelativeLayout rlWelcome;//布局
    private ViewPager vpNew;//滑动
    private WelcomeAdapter welcomeAdapter;//加载数据
    private List<View> viewList;//布局集合
    private int welcomeType;//类型

    private Button btnCancel;//按钮（结束引导页）

    private CirclePageIndicator circlePageIndicator;
    private ImageView imgMain;
    private View viewThird;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);
        checkNetWork();

    }

    public void checkNetWork() {
        final long timeMillis = System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {
                long timeMillis1 = System.currentTimeMillis();
                if (timeMillis1 - timeMillis >= 5000) {
                    if (!hasNetWork()) {
                        showToast("请检查你的网络");
                        return;
                    }
                }
            }
        }).start();
    }

    @Override
    public void initBoot() {
        loginModel = new LoginModel(activity);//数据相关
    }

    @Override
    public void initViews() {
        /*布局实例化*/
        rlWelcome = findView(R.id.rl_welcome);
        vpNew = findView(R.id.vp_welcome);
        circlePageIndicator = findView(R.id.circle_page_indicator);
        /*控件实例化*/
        btnCancel = findView(R.id.btn_cancel);
    }

    @Override
    public void initData(Bundle bundle) {
        // 做延迟处理是为了防止新启动的页面立即覆盖本页面
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (!AppApplication.getPreferenceHelper().getBoolean(Constant.SHARED_FIRST_USE, true)) {
                    // 不是第一次加载
                    /*检查用户是否记住了密码*/
                    if (MyUtils.checkUser()) {
                        if (!hasNetWork()) {
                            showToast("请打开网络");
                            Intent intent = new Intent(activity,MainMenu.class);
                            intent.putExtra("upData",1);
                            startActivity(intent);
                            finish();

                        } else {
                            loginModel.login(MyUtils.getCellPhone(), MyUtils.getPassword());
                        }
                    } else {

                        Intent intent = new Intent(activity,MainMenu.class);
                        intent.putExtra("upData",1);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    AppApplication.getPreferenceHelper().putBoolean(Constant.SHARED_FIRST_USE, false);

                    rlWelcome.setVisibility(View.VISIBLE);

                    welcomeType = Constant.WELCOME_TYPE_START;
                    viewList = new ArrayList<>();

                    View viewFirst = LayoutInflater.from(activity).inflate(R.layout.item_welcome_first, null);
                    View viewSecond = LayoutInflater.from(activity).inflate(R.layout.item_welcome_second, null);
                    viewThird = LayoutInflater.from(activity).inflate(R.layout.item_welcome_third, null);

                    viewList.add(viewFirst);
                    viewList.add(viewSecond);
                    viewList.add(viewThird);

                    welcomeAdapter = new WelcomeAdapter(viewList);
                    vpNew.setAdapter(welcomeAdapter);
                    circlePageIndicator.setViewPager(vpNew);
                }
            }
        }, 1000);
    }

    @Override
    public void initEvents() {
        //取消按钮
        /*btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (welcomeType == Constant.WELCOME_TYPE_START) {
                    Intent intent = new Intent(activity, MainMenu.class);
                    intent.putExtra("upData", 1);
                    startActivity(intent);
                    finish();
                    showToast("--------");
                }
            }
        });*/

        vpNew.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //滑到第三个界面自动跳转
                /*if (position == 2) {
                    Intent intent = new Intent(activity,MainMenu.class);
                    intent.putExtra("upData",1);
                    startActivity(intent);
                    finish();
                }*/
                if (position == 2) {
                    btnCancel = (Button) viewThird.findViewById(R.id.btn_cancel);
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(activity, MainMenu.class);
                            intent.putExtra("upData", 1);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void loginSuccess() {
        loginModel.bindCid(MyUtils.getCid());
        if (!MyUtils.getPushType().equals(Constant.YES) && !MyUtils.getPushType().equals(Constant.NO)) {
            MyUtils.savePushType(Constant.YES);
        }
        if (!MyUtils.getPushSound().equals(Constant.YES) && !MyUtils.getPushSound().equals(Constant.NO)) {
            MyUtils.savePushSound(Constant.YES);
        }
        Intent intent = new Intent(activity, MainMenu.class);
        intent.putExtra("upData", 1);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFailure(String msg) {
        showToast(msg);
        startActivity(LoginActivity.class);
    }


}
