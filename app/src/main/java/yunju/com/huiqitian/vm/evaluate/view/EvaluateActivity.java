package yunju.com.huiqitian.vm.evaluate.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;

/**
 * Created by 胡月 on 2016/8/4 0004.
 */
public class EvaluateActivity extends BaseActivity{

    /*title*/
    private LinearLayout ivTitleBack;//返回键
    private TextView tvTitle;//标题

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_evaluate);
    }
    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
         /*title*/
        ivTitleBack=findView(R.id.iv_title_back);
        tvTitle = findView(R.id.tv_title);
    }

    @Override
    public void initData(Bundle bundle) {

        tvTitle.setText(getResources().getText(R.string.tv_title_evaluate));
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
