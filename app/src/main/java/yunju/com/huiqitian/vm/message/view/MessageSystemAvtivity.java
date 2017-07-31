package yunju.com.huiqitian.vm.message.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.MessageBean;
import yunju.com.huiqitian.entity.MessageInfo;
import yunju.com.huiqitian.vm.adapter.MessageSystemAdapter;
import yunju.com.huiqitian.vm.menu.view.MainMenu;
import yunju.com.huiqitian.vm.menu.view.MenuHomeFragment;
import yunju.com.huiqitian.vm.message.model.MessageSystemModel;

/**
 * Create by gaoqiong 2016/9/2
 * 系统消息
 */

public class MessageSystemAvtivity extends BaseActivity {
    /*title*/
    private LinearLayout ivTitleBack;//返回键
    private TextView tvTitle;//标题
    private ListView lvContent;//消息内容
    private int isAnonymitySet;
    private int isAnonymitySound;
    private ArrayList<MessageBean> mlist;//消息集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_message_system);

    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
    /*title*/
        ivTitleBack = findView(R.id.iv_title_back);
        tvTitle = findView(R.id.tv_title);

        lvContent = findView(R.id.lv_message_system);
    }

    @Override
    public void initData(Bundle bundle) {
        tvTitle.setText("系统消息");
       // MessageSystemModel messageSystemModel = new MessageSystemModel(this);
       // ArrayList<MessageInfo> mlist = messageSystemModel.getMessageSystem();



        MessageSystemAdapter adapter = new MessageSystemAdapter(this, mlist);
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



    /**
     * 广播接受，接受推送消息的广播
     */
    public class MyMessageReceiver2 extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.error(MainMenu.class, "接受到推送来的数据成功");
            String message = intent.getStringExtra(Constant.MY_MESSAGE_FROM_PUS);
            LogUtils.error(MenuHomeFragment.class, message);

            //解析消息
            Gson gson = new Gson();
            MessageBean messageBean = gson.fromJson(message, MessageBean.class);

            if (isAnonymitySet==1){
                //表示开启 消息推送设置
                LogUtils.error(MenuHomeFragment.class, "777777777777777777推送消息可以使用");
                mlist.add(messageBean);//将消息添加到集合
            }
        }
    }

    /**
     * 广播接受，接受推送消息设置界面发送过来的广播
     */
    public class MyMessagePushSetReceiver2 extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.error(MainMenu.class, "接受到推送设置来的数据成功");

            isAnonymitySet = intent.getIntExtra("isAnonymitySet",-1);
            isAnonymitySound = intent.getIntExtra("isAnonymitySound",-1);
            LogUtils.error(MenuHomeFragment.class, isAnonymitySet+"-------555555555555555--------"+isAnonymitySound);
        }
    }
}
