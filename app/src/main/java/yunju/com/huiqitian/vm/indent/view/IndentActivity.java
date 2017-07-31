package yunju.com.huiqitian.vm.indent.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;

public class IndentActivity extends BaseActivity{

    /*title*/
    private LinearLayout ivTitleBack;//返回键
    private TextView tvTitle;//标题
    private ImageView ivTitleShare;//分享

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_indent);

    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        /*title*/
        ivTitleBack=findView(R.id.iv_title_back);
        tvTitle = findView(R.id.tv_title);
        ivTitleShare=findView(R.id.iv_title_share);


    }

    @Override
    public void initData(Bundle bundle) {
        tvTitle.setText("确认订单");//设置头部标题
    }

    @Override
    public void initEvents() {
         /*返回键响应事件*/
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
