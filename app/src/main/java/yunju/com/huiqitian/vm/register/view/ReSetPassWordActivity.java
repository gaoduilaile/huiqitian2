package yunju.com.huiqitian.vm.register.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.vm.login.model.LoginModel;
import yunju.com.huiqitian.vm.menu.view.MainMenu;
import yunju.com.huiqitian.vm.register.model.ReSetPassWordModel;
import yunju.com.huiqitian.vm.register.model.RegisterModel;

/**
 * Created by 胡月 on 2016/7/14 0014.
 */
public class ReSetPassWordActivity extends BaseActivity implements RegisterModel.RegisterInterface, LoginModel.LoginInterface,ReSetPassWordModel.ResetPwdInterface {

    /*title*/
    private LinearLayout ivTitleBack;//返回键
    private TextView tvTitle;//标题;
    /*定义谈吐丝的标示，注册和登陆只需要谈一个土司即可*/
    private boolean show = true;
    private boolean noShow = false;

    /*控件实例化*/
    private EditText etResetPsdFirs;  //输入密码
    private ImageView ibResetPsdEye1; //点击可看见或不可见密码
    private EditText etResetPsdAgain; //再次输入密码
    private ImageView ibResetPsdEye2; //点击可看见或不可见密码

    /*完成注册的按钮*/
    private Button btnRegisterOk;

    /*判断是否显示*/
    private boolean isShow = false;

    /*上一个界面传递过来的电话号码,code*/
    private String phone;
    private String code;

    /*注册的model*/
    private RegisterModel registerModel;
    private LoginModel loginModel;
    private ReSetPassWordModel reSetPassWordModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_resetpassword);
    }

    @Override
    public void initBoot() {
        registerModel = new RegisterModel(activity);
        loginModel = new LoginModel(activity);
        reSetPassWordModel = new ReSetPassWordModel(activity);
    }

    @Override
    public void initViews() {
        /*title*/
        ivTitleBack = findView(R.id.iv_title_back);
        tvTitle = findView(R.id.tv_title);
        /*控件*/
        etResetPsdFirs = findView(R.id.et_resetpsd_first);
        etResetPsdAgain = findView(R.id.et_resetpsd_again);
        ibResetPsdEye1 = findView(R.id.ib_resetpsd_eye1);
        ibResetPsdEye2 = findView(R.id.ib_resetpsd_eye2);
        btnRegisterOk = findView(R.id.btn_register_ok);
    }

    @Override
    public void initData(Bundle bundle) {
        if (bundle != null) {
            tvTitle.setText(bundle.getString("title"));//设置标题为（密码设置）
            phone = bundle.getString("phone");
            code = bundle.getString("code");
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

        /*点击是否可见密码的响应事件*/
        //第一个EditText密码的显示与隐藏
        ibResetPsdEye1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditTextPassWordSee(etResetPsdFirs, ibResetPsdEye1);
            }
        });

        //第二个EditText密码的显示与隐藏
        ibResetPsdEye2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditTextPassWordSee(etResetPsdAgain, ibResetPsdEye2);
            }
        });

        /*完成*/
        btnRegisterOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LogUtils.error(ReSetPassWordActivity.class, phone + judgePassWord(etResetPsdFirs, etResetPsdAgain));

                if (!etResetPsdFirs.getText().toString().trim().isEmpty()&&!etResetPsdAgain.getText().toString().trim().isEmpty()){

                    if (tvTitle.getText().toString().trim().equals(Constant.NEXT_TITLE)) {

                        registerModel.register(phone, judgePassWord(etResetPsdFirs, etResetPsdAgain, show), code);//注册


                    } else if (tvTitle.getText().toString().trim().equals(Constant.NEXT_TITLE_FOR_RESET)){

                        reSetPassWordModel.resetPwd(phone, judgePassWord(etResetPsdFirs, etResetPsdAgain,show), code);//重置密码
                    }

                }else {
                    showToast("密码不能为空");
                }
            }
        });
    }

    /**
     * 将光标移动到最后
     *
     * @param editText
     */
    public void setEditTextCursorLocation(EditText editText) {
        CharSequence text = editText.getText();
        if (text instanceof Spannable) {
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());
        }
    }

    /**
     * 点击是否可见密码
     *
     * @param editText
     * @param imageView
     */
    public void setEditTextPassWordSee(EditText editText, ImageView imageView) {
        if (!isShow) {
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            imageView.setImageResource(R.mipmap.img_show_psd);
            setEditTextCursorLocation(editText);
            isShow = true;
        } else {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            imageView.setImageResource(R.mipmap.img_closed_psd);
            setEditTextCursorLocation(editText);
            isShow = false;
        }
    }

    /**
     * 判断两次密码是否为空 密码是否一致 长度最少为6位
     */
    private String judgePassWord(EditText pwOne, EditText pwTwo,boolean b) {
        if (!isEmpty(pwOne) && !isEmpty(pwTwo)) {
            if (pwOne.getText().toString().trim().length() >= 6 && pwTwo.getText().toString().trim().length() >= 6) {
                if (pwOne.getText().toString().trim().equals(pwTwo.getText().toString().trim())) {
                   // MyUtils.saveUser(phone,pwOne.getText().toString().trim());
                    return pwOne.getText().toString().trim();
                } else {
                    if (b){
                        showToast("两次密码不一致");
                    }
                }
            } else {
                if (b){
                    showToast("密码长度最少为6位");
                }
            }

        } else {
            if (b){
                showToast("请将密码填写完整");
            }
        }
        return null;
    }

    @Override
    public void registerSuccess() {
        /*注册成功直接 登陆*/
        loginModel.login(phone, etResetPsdFirs.getText().toString());

    }

    @Override
    public void registerFailure(String msg) {

        showToast(msg);
    }

    @Override
    public void rightCode(String code) {

    }

    @Override
    public void rightResetCode(String code) {

    }

    @Override
    public void changeTime() {

    }

    @Override
    public void loginSuccess() {
        MyUtils.saveUser(phone, etResetPsdFirs.getText().toString().trim());//保存用户名和密码
        Intent intent = new Intent(activity, MainMenu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constant.LOGIN_TO_HOME, 1);
        startActivity(intent);
        overridePendingTransition(R.anim.go_in, R.anim.go_out);
        finish();
    }

    @Override
    public void loginFailure(String msg) {
        showToast(msg);
    }

    //重置密码成功
    @Override
    public void resetSuccess() {
        /*隐式登陆*/
        loginModel.login(phone, etResetPsdFirs.getText().toString().trim());
    }

    //重置密码失败
    @Override
    public void resetFailure(String msg) {
        showToast(msg);
    }

    @Override
    public void resetError() {
        showToast("链接服务器失败，请检查你的网络");
    }
}
