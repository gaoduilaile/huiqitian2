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
import yunju.com.huiqitian.utils.MyActivityManager;
import yunju.com.huiqitian.vm.adapter.MessageExerciseAdapter;
import yunju.com.huiqitian.vm.adapter.MessageSystemAdapter;
import yunju.com.huiqitian.vm.message.model.MessageExerciseModel;
import yunju.com.huiqitian.vm.message.model.MessageSystemModel;

/**
 * Create by gaoqiong 2016/9/2
 * 活动消息
 * */

public class MessageExerciseAvtivity extends BaseActivity {
    /*title*/
    private LinearLayout ivTitleBack;//返回键
    private TextView tvTitle;//标题
    private ListView lvContent;//消息内容

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_message_exercise);

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
        tvTitle.setText("优惠促销");
        MessageExerciseModel messageSystemModel = new MessageExerciseModel(this);
        ArrayList<MessageInfo> mlist = messageSystemModel.getMessageSystem();

        MessageExerciseAdapter adapter=new MessageExerciseAdapter(this,mlist);
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
