package yunju.com.huiqitian.vm.set.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;

public class AboutAPKActivity extends BaseActivity {

    /*title*/
    private LinearLayout ivTitleBack;//开始的返回键
    private TextView tvTitle;//标题

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_about_apk);
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {

        /*title*/
        tvTitle = findView(R.id.tv_title);
        ivTitleBack = findView(R.id.iv_title_back);
    }

    @Override
    public void initData(Bundle bundle) {
        tvTitle.setText(R.string.about_apk);
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
    }
}
