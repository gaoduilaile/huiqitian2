package yunju.com.huiqitian.vm.about.view;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.vm.set.view.AboutAPKActivity;
import yunju.com.huiqitian.vm.widget.PopShowUtils;

public class AboutActivity extends BaseActivity {

    /*title*/
    private TextView tvTitle;//标题
    private LinearLayout ivTitleBack;//返回键

    private RelativeLayout rlAboutApk;
    private TextView tvVersionName;
    private ImageView imgCodeAbout;
    private View view;
    private TextView popOkShow;
    private TextView popCancelShow;
    private PopupWindow popupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_about);
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        /*title*/
        tvTitle = findView(R.id.tv_title);
        ivTitleBack = findView(R.id.iv_title_back);
        rlAboutApk = findView(R.id.rl_about_apk);
        tvVersionName = findView(R.id.version_name);

         /*popWindow相关初始化*/
        view = LayoutInflater.from(activity).inflate(R.layout.pop_show_select_image, null);
        popOkShow = (TextView) view.findViewById(R.id.pop_ok_show);
        popCancelShow = (TextView) view.findViewById(R.id.pop_cancel_show);


        /*二维码的图片*/
        imgCodeAbout = findView(R.id.img_code_about);
        /*获取版本号*/
        PackageManager packageManager = this.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
            String versionName = packageInfo.versionName;
            tvVersionName.setText("版本 v" + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData(Bundle bundle) {
        //设置标题
        tvTitle.setText(R.string.tv_title_about);
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

        /*关于惠七天*/
        rlAboutApk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AboutAPKActivity.class);
            }
        });
        /*二维码的长按事件*/
        imgCodeAbout.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                popupWindow = PopShowUtils.showPopwindow(view, getWindow(), imgCodeAbout);
                return false;
            }
        });
        /*保存至本地相册*/
        popOkShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("以成功保存至本地");
                Bitmap bitmap = ((BitmapDrawable) imgCodeAbout.getDrawable()).getBitmap();
                // 首先保存图片
                File appDir = new File(Environment.getExternalStorageDirectory(), "YunJu");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                String fileName = System.currentTimeMillis() + ".jpg";
                File file = new File(appDir, fileName);
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // 其次把文件插入到系统图库
                try {
                    MediaStore.Images.Media.insertImage(activity.getContentResolver(),
                            file.getAbsolutePath(), fileName, null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                // 最后通知图库更新
                activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                popupWindow.dismiss();
            }
        });
        popCancelShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
}
