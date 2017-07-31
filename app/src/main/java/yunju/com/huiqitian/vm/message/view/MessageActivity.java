package yunju.com.huiqitian.vm.message.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;

/**
 * Created by 胡月 on 2016/8/3 0003.
 */
public class MessageActivity extends BaseActivity  {
    /*title*/
    private LinearLayout ivTitleBack;//返回键
    private TextView tvTitle;//标题

    /*显示控件*/
    private TextView tvSystemMessage;

    private TextView tvMerchantMessage;

    private TextView tvExerciseMessage;

    private TextView tvDeliveryMessage;

    /*点击布局*/
    private RelativeLayout rlSystemMessage;//系统消息
    private RelativeLayout rlMerchantMessage;//商家消息
    private RelativeLayout rlExerciseMessage;// 活动消息
    private RelativeLayout rlDeliveryMessage;//配送消息

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_message);

    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        /*title*/
        ivTitleBack = findView(R.id.iv_title_back);
        tvTitle = findView(R.id.tv_title);

        /*显示控件*/
        tvSystemMessage = findView(R.id.tv_system_message);
        tvMerchantMessage = findView(R.id.tv_merchant_message);
        tvExerciseMessage = findView(R.id.tv_exercise_message);
        tvDeliveryMessage = findView(R.id.tv_delivery_message);

         /*点击布局*/
        rlSystemMessage = findView(R.id.rl_system_message);
        rlMerchantMessage = findView(R.id.rl_merchant_message);
        rlExerciseMessage = findView(R.id.rl_exercise_message);
        rlDeliveryMessage = findView(R.id.rl_delivery_message);
    }

    @Override
    public void initData(Bundle bundle) {
        tvTitle.setText(getResources().getText(R.string.tv_title_message));
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

        /*系统消息*/
        rlSystemMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /* startActivity(MessageSystemAvtivity.class);*/
            }
        });

        /*商家消息*/
        rlMerchantMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*startActivity(MessageResultActivity.class);*/
            }
        });

        /*活动消息*/
        rlExerciseMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*startActivity(MessageExerciseAvtivity.class)*/;
            }
        });

        /*配送消息*/
        rlDeliveryMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* startActivity(MessageSendActivity.class);*/
            }
        });
    }

}
