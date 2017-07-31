package yunju.com.huiqitian.vm.register.view;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.constant.ConstantNotice;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.vm.register.model.RegisterModel;

public class RegisterActivity extends BaseActivity implements RegisterModel.RegisterInterface {

    /*title*/
    private LinearLayout ivTitleBack;//返回键
    private TextView tvTitle;//标题

    /*控件实例化*/
    private EditText etRegisterPhone;//手机号码
    private EditText etRegisterCode;//验证码
    private Button btnRegisterGetCode;//获取验证码
    private Button btnRegisterNext;//下一步

    /*协议*/
    private TextView tvAgreement;

    /*数据的model*/
    private RegisterModel registerModel;
    private String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_register);

    }

    @Override
    public void initBoot() {
        registerModel = new RegisterModel(activity);
    }

    @Override
    public void initViews() {
        /*title*/
        ivTitleBack = findView(R.id.iv_title_back);
        tvTitle = findView(R.id.tv_title);

        /*控件*/
        etRegisterPhone = findView(R.id.et_register_phone);
        etRegisterCode = findView(R.id.et_register_code);
        btnRegisterGetCode = findView(R.id.btn_register_get_code);
        btnRegisterNext = findView(R.id.btn_register_next);

        /*协议*/
        tvAgreement = findView(R.id.tv_agreement);
    }

    @Override
    public void initData(Bundle bundle) {
        if (bundle!=null){
            title = bundle.getString("title");

            LogUtils.error(RegisterActivity.class,"=--title-----"+title);
            if (title.equals("找回密码")) {
                tvTitle.setText(title);//设置bundle传递过来标题
                tvAgreement.setVisibility(View.INVISIBLE);//设置协议为不可见的
            }else if (title.equals("快速注册")){
                tvTitle.setText(title);//设置bundle传递过来标题
            }
        }

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

        /*获取手机验证码*/
        btnRegisterGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tvTitle.getText().toString().trim().equals("找回密码")) {
                    //修改密码
                    if (!etRegisterPhone.getText().toString().isEmpty()) {
                        if (MyUtils.isMobileNum(etRegisterPhone.getText().toString())){
                            registerModel.chickPhoneForReSet(etRegisterPhone.getText().toString());

                        }else {
                            showToast(ConstantNotice.LOGIN_WRONG_PHONE);
                        }
                    }else {
                        showToast(ConstantNotice.LOGIN_NO_PHONE);
                    }

                } else if (tvTitle.getText().toString().trim().equals("快速注册")) {
                    //注册
                    if (!etRegisterPhone.getText().toString().isEmpty()) {
                        if (MyUtils.isMobileNum(etRegisterPhone.getText().toString())) {
                            registerModel.chickPhone(etRegisterPhone.getText().toString());
                        } else {
                            showToast(ConstantNotice.LOGIN_WRONG_PHONE);
                        }

                    } else {
                        showToast(ConstantNotice.LOGIN_NO_PHONE);
                    }
                }
            }
        });


        /*下一步*/
        btnRegisterNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (tvTitle.getText().toString().trim().equals("找回密码")) {
                    //修改密码
                    if (!etRegisterPhone.getText().toString().isEmpty()) {
                        if (MyUtils.isMobileNum(etRegisterPhone.getText().toString())) {
                            if (!etRegisterCode.getText().toString().isEmpty()) {
                                registerModel.checkCodeForRest(etRegisterCode.getText().toString(), etRegisterPhone.getText().toString());
                            } else {
                                showToast(ConstantNotice.REGISTER_NO_CODE);
                            }
                        } else {
                            showToast(ConstantNotice.LOGIN_WRONG_PHONE);
                        }
                    } else {
                        showToast(ConstantNotice.REGISTER_NO_PHONE);
                    }
                } else if (tvTitle.getText().toString().trim().equals("快速注册")) {
                    //注册
                    if (!etRegisterPhone.getText().toString().isEmpty()) {
                        if (MyUtils.isMobileNum(etRegisterPhone.getText().toString())) {
                            if (!etRegisterCode.getText().toString().isEmpty()) {
                                registerModel.checkCode(etRegisterCode.getText().toString(), etRegisterPhone.getText().toString());
                            } else {
                                showToast(ConstantNotice.REGISTER_NO_CODE);
                            }
                        } else {
                            showToast(ConstantNotice.LOGIN_WRONG_PHONE);
                        }
                    } else {
                        showToast(ConstantNotice.REGISTER_NO_PHONE);
                    }
                }

            }
        });


          /*textview的内容变化侦听，当输入验证码时， 将下一步按钮颜色变为红色*/
        etRegisterCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                btnRegisterNext.setBackground(getResources().getDrawable(R.drawable.bkg_et_reg));

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnRegisterNext.setBackground(getResources().getDrawable(R.drawable.bkg_et_reg));
            }

            @Override
            public void afterTextChanged(Editable s) {

                btnRegisterNext.setBackground(getResources().getDrawable(R.drawable.bkg_btn_reg));
                btnRegisterNext.setTextColor(Color.WHITE);
                //btnRegisterGetCode.setTextColor(getColor(R.color.btn_getMessage_register));
            }
        });


    }

    @Override
    public void registerSuccess() {

    }

    @Override
    public void registerFailure(String msg) {

    }

    @Override
    public void rightCode(String code) {
        Bundle bundle = new Bundle();
        bundle.putString("title", Constant.NEXT_TITLE);
        bundle.putString("code", code);
        bundle.putString("phone", etRegisterPhone.getText().toString());
        startActivity(ReSetPassWordActivity.class, bundle);
        finish();
    }

    /*重置密码获得的验证码*/
    @Override
    public void rightResetCode(String code) {
        Bundle bundle = new Bundle();
        bundle.putString("title", Constant.NEXT_TITLE_FOR_RESET);
        bundle.putString("code", code);
        bundle.putString("phone", etRegisterPhone.getText().toString());
        startActivity(ReSetPassWordActivity.class, bundle);
        finish();
    }

    /**
     * 定时 60秒
     */
    @Override
    public void changeTime() {
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btnRegisterGetCode.setClickable(false);
                btnRegisterGetCode.setText((millisUntilFinished / 1000) + "秒倒计时");
                btnRegisterGetCode.setBackgroundResource(R.drawable.bkg_regest_press);
                btnRegisterGetCode.setTextColor(Color.rgb(153, 153, 153));
            }

            @Override
            public void onFinish() {
                btnRegisterGetCode.setText(R.string.getcode_activity_register);
                btnRegisterGetCode.setBackgroundResource(R.drawable.bkg_register_code);
                btnRegisterGetCode.setTextColor(Color.rgb(229, 28, 35));
                btnRegisterGetCode.setClickable(true);
            }
        }.start();
    }
}
