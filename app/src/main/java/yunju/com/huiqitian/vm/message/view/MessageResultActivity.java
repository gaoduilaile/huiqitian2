package yunju.com.huiqitian.vm.message.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;

/**
 * Created by 胡月 on 2016/8/3 0003.
 *
 * 商家已接单
 */
public class MessageResultActivity extends BaseActivity{
    /*title*/
    private LinearLayout ivTitleBack;//返回键
    private TextView tvTitle;//标题
    private ListView lvContent;//消息内容
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_message_result);

    }
    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
/*title*/
        ivTitleBack = findView(R.id.iv_title_back);
        tvTitle = findView(R.id.tv_title);

        lvContent = findView(R.id.lv_message_exercise);

    }

    @Override
    public void initData(Bundle bundle) {
        tvTitle.setText("商家消息");

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
