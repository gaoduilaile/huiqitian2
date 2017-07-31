package yunju.com.huiqitian.vm.my.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.trinea.android.common.util.LogUtils;
import cn.trinea.android.common.util.ToastUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.constant.ConstantNotice;
import yunju.com.huiqitian.utils.MyActivityManager;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.vm.my.model.ModifyModel;
import yunju.com.huiqitian.vm.widget.MyClearEditText;

/**
 * @author 张超群
 *
 * 个人信息的修改界面
 */

public class ModifyActivity extends BaseActivity implements ModifyModel.ModifyNickNameInterface{

    /*title*/
    private TextView tvTitle;//标题
    private LinearLayout ivTitleBack;//返回键

    /*昵称修改控件*/
    private LinearLayout llPersonNickname;//昵称总布局
    private MyClearEditText etClearModify;//带有清除按钮的EditText
    private Button btnNicknameChangeCommit;//提交按钮

    /*手机修改控件*/
    private RelativeLayout rlPersonPhoneChange;//手机修改总布局

    /*显示手机号绑定控件*/
    private LinearLayout llPersonBindPhone;//显示手机号总布局
    private TextView tvPersonBindPhone;//显示绑定手机号
    private Button btnPersonRemovePhone;//更换手机号

    /*修改绑定手机号界面控件*/
    private LinearLayout llPersonUnbindPhone;//修改绑定手机号的总布局
    private EditText etBindPhone;//绑定手机号
    private EditText etBindCode;//验证码
    private Button btnBindGetCode;//发送验证码
    private Button btnBind;//绑定提交

    /*个人信息修改model*/
    ModifyModel modifyModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_modify);

    }

    @Override
    public void initBoot() {
        modifyModel = new ModifyModel(activity);
    }

    @Override
    public void initViews() {
        /*初始化控件*/
        tvTitle = findView(R.id.tv_title);
        ivTitleBack = findView(R.id.iv_title_back);

         /*昵称修改控件*/
        llPersonNickname = findView(R.id.ll_person_nickname);
        etClearModify = findView(R.id.et_clear_modify);
        btnNicknameChangeCommit = findView(R.id.btn_nickname_change_commit);

         /*手机修改控件*/
        rlPersonPhoneChange = findView(R.id.rl_person_phone_change);

        /*显示手机号绑定控件*/
        llPersonBindPhone = findView(R.id.ll_person_bind_phone);
        tvPersonBindPhone = findView(R.id.tv_person_bind_phone);
        btnPersonRemovePhone = findView(R.id.btn_person_remove_phone);

        /*修改绑定手机号界面控件*/
        llPersonUnbindPhone= findView(R.id.ll_person_unbind_phone);
        etBindPhone= findView(R.id.et_bind_phone);
        etBindCode= findView(R.id.et_bind_code);
        btnBindGetCode= findView(R.id.btn_bind_get_code);
        btnBind= findView(R.id.btn_bind);
    }

    @Override
    public void initData(Bundle bundle) {

        int mark = bundle.getInt(Constant.MODIFY_KEY);

        int nickNameMark = getIntent().getIntExtra(Constant.MODIFY_KEY,0);//获取昵称值
        if (nickNameMark!=-1){
            //昵称
            tvTitle.setText(R.string.person_change_nickname);
            llPersonNickname.setVisibility(View.VISIBLE);
            rlPersonPhoneChange.setVisibility(View.GONE);
            //用户昵称显示
            etClearModify.setText(MyUtils.getPersonNickname());
        }

      switch (mark){
          case Constant.MODIFY_ACCOUNT:
                //账号

                break;
          case Constant.MODIFY_BIND_PHONE:
              //修改手机号码
              tvTitle.setText(R.string.person_change_bind_phone);
              llPersonNickname.setVisibility(View.GONE);
              rlPersonPhoneChange.setVisibility(View.VISIBLE);
              llPersonBindPhone.setVisibility(View.VISIBLE);
              llPersonUnbindPhone.setVisibility(View.GONE);

              //显示用户绑定手机号
              tvPersonBindPhone.setText(MyUtils.getPersonPhone());
              break;
      }
    }

    @Override
    public void initEvents() {

        /*返回按键*/
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             backResult();
//                finish();
            }
        });

        /*用户昵称修改提交*/
        btnNicknameChangeCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickName = etClearModify.getText().toString();
                if (nickName.length()>20){
                    ToastUtils.show(activity,R.string.person_nickname_length);
                }else{
                    modifyModel.modifyNickName(nickName);
                }
            }
        });

        /*更换手机号*/
        btnPersonRemovePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置更换手机号界面布局显示
                llPersonUnbindPhone.setVisibility(View.VISIBLE);
                llPersonBindPhone.setVisibility(View.GONE);
            }
        });

        /*发送验证码*/
        btnBindGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etBindPhone.getText().toString().isEmpty()) {
                    if (MyUtils.isMobileNum(etBindPhone.getText().toString())) {
                        modifyModel.chickPhone(etBindPhone.getText().toString());
                    } else {
                        showToast(ConstantNotice.LOGIN_WRONG_PHONE);
                    }

                } else {
                    showToast(ConstantNotice.LOGIN_NO_PHONE);
                }
            }
        });

        /*提交绑定手机号*/
        btnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更改手机号
                if (!etBindPhone.getText().toString().isEmpty() && !etBindCode.getText().toString().isEmpty()) {
                    modifyModel.modifyMobile(etBindPhone.getText().toString(), etBindCode.getText().toString());
                } else if (etBindPhone.getText().toString().isEmpty()) {
                    showToast(ConstantNotice.REGISTER_NO_PHONE);
                } else {
                    showToast(ConstantNotice.REGISTER_NO_CODE);
                }
            }
        });

    }

    @Override
    public void modifyNickNameSuccess(String msg) {
        LogUtils.error(ModifyActivity.class,"修改成功："+msg);
        ToastUtils.show(activity, R.string.person_nickname_modify_success);
        //数据是使用Intent返回
        Intent intent = new Intent();
        //把返回数据存入Intent
        intent.putExtra(Constant.MODIFY_KEY, etClearModify.getText().toString());
        //设置返回数据
        setResult(RESULT_OK, intent);
        MyUtils.changePersonNickName(etClearModify.getText().toString());
        LogUtils.error(ModifyActivity.class,"3333333333333333333333333"+MyUtils.getPersonNickname());
        finish();
    }

    @Override
    public void modifyNickNameFailure(String msg) {
        LogUtils.error(ModifyActivity.class, "修改失败：" + msg);
    }

    @Override
    public void modifyPhoneSuccess(String msg) {
        //数据是使用Intent返回
        Intent intent = new Intent();
        //把返回数据存入Intent
        intent.putExtra(Constant.MODIFY_KEY,etBindPhone.getText().toString());
        //设置返回数据
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void modifyPhoneFailure(String msg) {

    }

    /**
     * 定时60秒
     */
    @Override
    public void changeTime() {
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btnBindGetCode.setClickable(false);
                btnBindGetCode.setText((millisUntilFinished / 1000) + "秒倒计时");
                btnBindGetCode.setBackgroundResource(R.drawable.bkg_regest_press);
                btnBindGetCode.setTextColor(Color.rgb(153, 153, 153));
            }

            @Override
            public void onFinish() {
                btnBindGetCode.setText(R.string.getcode_activity_register);
                btnBindGetCode.setBackgroundResource(R.drawable.bkg_register_code);
                btnBindGetCode.setTextColor(Color.rgb(229, 28, 35));
                btnBindGetCode.setClickable(true);
            }
        }.start();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            backResult();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 当用户点击返回按钮或者物理返回按键时，返回的值
     */
    private void backResult(){
        //数据是使用Intent返回
        Intent intent = new Intent();
        if (llPersonNickname.getVisibility()== View.VISIBLE){
            //把返回数据存入Intent
            intent.putExtra(Constant.MODIFY_KEY,etClearModify.getText().toString());
        }else if (rlPersonPhoneChange.getVisibility() == View.VISIBLE){
            if (llPersonBindPhone.getVisibility() == View.VISIBLE){
                //把显示绑定手机号存入intent
                intent.putExtra(Constant.MODIFY_KEY,tvPersonBindPhone.getText().toString());
            }

            if (llPersonUnbindPhone.getVisibility() == View.VISIBLE){
                //从shared中取出手机号
                intent.putExtra(Constant.MODIFY_KEY,MyUtils.getPersonPhone());
            }
        }
        //设置返回数据
        setResult(RESULT_OK, intent);
        finish();
    }
}
