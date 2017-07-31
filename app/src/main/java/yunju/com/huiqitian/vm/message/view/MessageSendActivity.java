package yunju.com.huiqitian.vm.message.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.entity.MessageInfo;
import yunju.com.huiqitian.vm.adapter.MessageSendAdapter;
import yunju.com.huiqitian.vm.message.model.MessageSendModel;

/**
 * Created by Administrator on 2016/9/13 0013.
 *
 * 配送消息
 */
public class MessageSendActivity extends BaseActivity {
    /*title*/
    private LinearLayout ivTitleBack;//返回键
    private TextView tvTitle;//标题
    private ListView lvContent;//消息内容

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_message_send);
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
        tvTitle.setText("推送消息");

        MessageSendModel messageSendModel = new MessageSendModel(this);
        ArrayList<MessageInfo> mlist = messageSendModel.getSendItem();

        MessageSendAdapter adapter = new MessageSendAdapter(this, mlist);
        lvContent.setAdapter(adapter);
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
