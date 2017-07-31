package yunju.com.huiqitian.vm.set.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.xutils.ex.DbException;

import yunju.com.huiqitian.AppApplication;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.db.dao.CollectDao;
import yunju.com.huiqitian.db.dao.HistoryDao;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.vm.about.view.AboutActivity;
import yunju.com.huiqitian.vm.set.model.SetModel;
import yunju.com.huiqitian.vm.widget.PopShowUtils;

/**
 * 更多設置界面
 */
public class SetActivity extends BaseActivity implements SetModel.LoginUotInterface{

    /*数据model*/
    private SetModel setModel;

    ImageLoader imageLoader = ImageLoader.getInstance();
    /*title*/
    private TextView tvTitle;//标题
    private LinearLayout ivTitleBack;//返回键

    /*控件*/
    private RelativeLayout rlPushSet;//推送设置
    private RelativeLayout rlClearCache;//清除缓存
    private RelativeLayout rlWifiLoadPic;//非wifi情况下手动下载图片
    private RelativeLayout rlAboutHuiqitian;//关于惠七天
    private TextView tvShowVersion;//显示版本号
    private TextView tvExistAccount;//退出账号

    /*popWindow相关*/
    private View view;//压缩的布局
    private TextView tvClear;//删除
    private TextView popCancelClear;//取消

    private HistoryDao historyDao;
    private CollectDao collectDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_set);
    }

    @Override
    public void initBoot() {
        setModel=new SetModel(activity);
        historyDao = new HistoryDao();
        collectDao = new CollectDao();
    }

    @Override
    public void initViews() {
        /*初始化控件*/
        tvTitle = findView(R.id.tv_title);
        ivTitleBack = findView(R.id.iv_title_back);
        rlPushSet = findView(R.id.rl_push_set);
        rlClearCache = findView(R.id.rl_clear_cache);
        rlWifiLoadPic = findView(R.id.rl_wifi_load_pic);
        rlAboutHuiqitian = findView(R.id.rl_about_huiqitian);
        tvShowVersion = findView(R.id.tv_show_version);
        tvExistAccount = findView(R.id.tv_exist_account);

        /*popWindow相关*/
        this.view = LayoutInflater.from(activity).inflate(R.layout.pop_show_clear, null);
        tvClear = (TextView) this.view.findViewById(R.id.tv_clear);
        popCancelClear = (TextView) this.view.findViewById(R.id.pop_cancel_clear);

        /*获取应用的版本号*/
        PackageManager packageManager = this.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
            String versionName = packageInfo.versionName;
            tvShowVersion.setText("v"+versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData(Bundle bundle) {
        //设置标题
        tvTitle.setText(R.string.more_set);
    }
    
    @Override
    public void initEvents() {
        /*返回键*/
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*推送设置*/
        rlPushSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到推送设置页面
            startActivity(MessagePushSetActivity.class);

            }
        });

        /*清楚缓存*/
        rlClearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopShowUtils.showPopwindow(view, getWindow(), rlClearCache);

                /*清楚缓存*/
                tvClear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            historyDao.delete();
                            collectDao.delete();
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                        PopShowUtils.closePopWindowPage();
                    }
                });

                  /*取消*/
                popCancelClear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopShowUtils.closePopWindowPage();
                    }
                });
            }
        });

        /*关于惠七天*/
        rlAboutHuiqitian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AboutActivity.class);
            }
        });

        /*退出当前账号*/
        tvExistAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog dialog = new AlertDialog.Builder(SetActivity.this)
                        .setMessage("退出后你将不能查看个人信息，确定退出吗？")

                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                AppApplication.putBoolean("person_fragment_refresh", true);
                                //清除用户
                                MyUtils.clearUser();
                                MyUtils.clearPersonMsg();
                                MyUtils.saveHeadPath("");

                                //清除缓存
                                try {
                                    historyDao.delete();
                                    collectDao.delete();
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }

                                //调退出接口
                                setModel.loginOut();

                            }
                        }).create();

                dialog.show();

            }
        });
    }

    @Override
    public void loginUotSuccess(String mes) {
        finish();

    }

    @Override
    public void loginUotFailure(String mes) {

    }
}
