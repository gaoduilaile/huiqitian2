package yunju.com.huiqitian.vm.set.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.utils.MyUtils;

/**
 * Created by 胡月 on 2016/8/10 0010.
 */
public class MessagePushSetActivity extends BaseActivity {

    /*title*/
    private TextView tvTitle;//标题
    private LinearLayout ivTitleBack;//返回键
    private TextView tvMessageOpenClose;//消息推送设置
    private ImageView ivMessagePushSound;//消息推送音效
    private int isAnonymitySet = 1;//消息推送设置标记
    private int isAnonymitySound = 1;  //消息推送音效标记

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_message_push);
    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        /*初始化控件*/
        tvTitle = findView(R.id.tv_title);
        ivTitleBack = findView(R.id.iv_title_back);
        tvMessageOpenClose = findView(R.id.message_push_open_close);
        ivMessagePushSound = findView(R.id.iv_message_push_sound);
    }

    @Override
    public void initData(Bundle bundle) {
        //设置标题
        tvTitle.setText(R.string.tv_title_message_push);
        if(MyUtils.getPushType().equals(Constant.YES)){
            tvMessageOpenClose.setText("已开启");
        }else if(MyUtils.getPushType().equals(Constant.NO)){
            tvMessageOpenClose.setText("未开启");
        }

        if(MyUtils.getPushSound().equals(Constant.YES)){
            ivMessagePushSound.setImageResource(R.mipmap.img_indent_button_yes);
        }else if(MyUtils.getPushSound().equals(Constant.NO)){
            ivMessagePushSound.setImageResource(R.mipmap.img_affirm_anonymity_close);
        }

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

        tvMessageOpenClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyUtils.getPushType().equals(Constant.NO)) {
                    tvMessageOpenClose.setText("已开启");
                    MyUtils.savePushType(Constant.YES);
                } else if(MyUtils.getPushType().equals(Constant.YES)){
                    tvMessageOpenClose.setText("未开启");
                    MyUtils.savePushType(Constant.NO);
                }

            }
        });


        ivMessagePushSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyUtils.getPushSound().equals(Constant.YES)) {
                    ivMessagePushSound.setImageResource(R.mipmap.img_affirm_anonymity_close);
                    MyUtils.savePushSound(Constant.NO);
                } else if(MyUtils.getPushSound().equals(Constant.NO)){
                    ivMessagePushSound.setImageResource(R.mipmap.img_indent_button_yes);
                    MyUtils.savePushSound(Constant.YES);
                }
            }
        });
    }

}
