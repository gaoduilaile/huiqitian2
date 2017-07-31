package yunju.com.huiqitian.vm.login.view;

import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import yunju.com.huiqitian.AppApplication;
import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.vm.login.model.LoginModel;
import yunju.com.huiqitian.vm.menu.view.MainMenu;
import yunju.com.huiqitian.vm.register.view.RegisterActivity;

/**
 * 登錄界面
 */
public class LoginActivity extends BaseActivity implements LoginModel.LoginInterface {
    /*title*/
    private TextView tvTitle;//标题
    private LinearLayout ivTitleBack;//返回键

    /*控件*/
    private EditText etLoginTel;//输入手机号
    private EditText etLoginPsd;//输入密码
    private ImageView pngLoginChange;//密码是否可见
    private Button btnLogin;//登陆
    private TextView tvLoginForgetPsd;//忘记密码
    private TextView tvLoginFastRegister;//快速注册

    /*登陆的model*/
    private LoginModel loginModel;//登陆的数据源

    /*判断是否显示*/
    private boolean isShow = false;
    /*判断intent是否来自购物车*/
    private int intFromCart;

    /*判断是否来之退出账户界面*/
    private int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_login);
    }


    @Override
    public void initBoot() {
        loginModel = new LoginModel(activity);


    }

    @Override
    public void initViews() {
         /*初始化控件*/
        tvTitle = findView(R.id.tv_title);
        ivTitleBack = findView(R.id.iv_title_back);
        etLoginTel = findView(R.id.et_login_tel);
        etLoginPsd = findView(R.id.et_login_psd);
        pngLoginChange = findView(R.id.png_login_change);
        btnLogin = findView(R.id.btn_login);
        tvLoginForgetPsd = findView(R.id.tv_login_forget_psd);
        tvLoginFastRegister = findView(R.id.tv_login_fast_register);
    }

    @Override
    public void initData(Bundle bundle) {
        //设置标题
        tvTitle.setText(R.string.str_login_title);
        intFromCart = getIntent().getIntExtra(Constant.FROM_SHOP_CART, -1);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            //do something...
            if (intFromCart == 1) {
                AppApplication.putBoolean("shop_no_login",true);
                finish();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void initEvents() {
        /*返回键*/
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intFromCart == 1) {
                    AppApplication.putBoolean("shop_no_login",true);
                }
                finish();

            }
        });

        /*按钮显示的点击事件*/
        pngLoginChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditTextPassWordSee(etLoginPsd, pngLoginChange);
            }
        });

        /*登陆按钮*/
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 /*登陆*/
                loginModel.login(etLoginTel.getText().toString(), etLoginPsd.getText().toString());
            }
        });

        /*忘记密码*/
        tvLoginForgetPsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title", "找回密码");
                startActivity(RegisterActivity.class, bundle);
            }
        });

        /*快速注册*/
        tvLoginFastRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title", "快速注册");
                startActivity(RegisterActivity.class, bundle);
            }
        });
    }

    @Override
    public void loginSuccess() {
        /*保存用户名和密码*/
        MyUtils.saveUser(etLoginTel.getText().toString(), etLoginPsd.getText().toString());
        /*第一次登陆的时候默认设置是可以收到推送的消息，有提示声音*/
        if (!MyUtils.getPushType().equals(Constant.YES) && !MyUtils.getPushType().equals(Constant.NO)) {
            MyUtils.savePushType(Constant.YES);
        }
        if (!MyUtils.getPushSound().equals(Constant.YES) && !MyUtils.getPushSound().equals(Constant.NO)) {
            MyUtils.savePushSound(Constant.YES);
        }
        /*登陆的成功也要把用户的cid绑定给后台*/
        loginModel.bindCid(MyUtils.getCid());


        /*登陆成功之后，保存boolean值。在fragment中onResume刷新数据，重新跑接口*/
        AppApplication.putBoolean("person_fragment_refresh", true);
        if (intFromCart == 1) {
            AppApplication.putBoolean("shop_fragment_refresh",true);
        }

        //从详情中进入登陆页面
        /*int details = intent1.getIntExtra("Details", -1);*/
        /*int set = intent1.getIntExtra("set", -1);
        if (set==1){
            Intent intent = new Intent(activity, MainMenu.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("setAll", 5);
            startActivity(intent);
            overridePendingTransition(R.anim.go_in, R.anim.go_out);
        }*/
        /*else if (details!=1){
            Intent intent = new Intent(activity, MainMenu.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(Constant.LOGIN_TO_HOME, 1);
            startActivity(intent);
            overridePendingTransition(R.anim.go_in, R.anim.go_out);
        }*/
        finish();
    }

    @Override
    public void loginFailure(String msg) {
        showToast(msg);
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


}
