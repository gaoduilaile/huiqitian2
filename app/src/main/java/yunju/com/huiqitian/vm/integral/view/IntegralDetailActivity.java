package yunju.com.huiqitian.vm.integral.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.AppApplication;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.IntegralGoods;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.utils.ImageOptions;
import yunju.com.huiqitian.vm.integral.model.IntegralModel;
import yunju.com.huiqitian.vm.login.view.LoginActivity;
import yunju.com.huiqitian.vm.menu.model.MenuPersonModel;

/**
 * Created by liuGang on 2016/8/19 0019.
 */
public class IntegralDetailActivity extends BaseActivity implements IntegralModel.IntegralStoreInterface {

    /*图片加载类*/
    private ImageLoader imageLoader = ImageLoader.getInstance();

    /*控件*/

    private TextView tvTitle;
    private LinearLayout ivTitleBack;
    private ImageView ivIntegralDetail;
    private TextView tvIntegralDetailGoodsName;
    private TextView tvIntegralDetailNum;
    private TextView tvIntegralDetailExchange;
    /*积分商品*/
    private IntegralGoods integralGoods;
    private IntegralModel integralModel;
    /*我的总积分*/
    private int store;

    /*判断是否登录*/
    private int login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_integral_detail);

    }

    @Override
    public void initBoot() {
        integralModel = new IntegralModel(activity);
    }

    @Override
    public void initViews() {
        /*初始化控件*/
        tvTitle = findView(R.id.tv_title);
        ivTitleBack = findView(R.id.iv_title_back);
        ivIntegralDetail = findView(R.id.iv_integralDetail);
        tvIntegralDetailGoodsName = findView(R.id.tv_integralDetail_goodsName);
        tvIntegralDetailNum = findView(R.id.tv_integralDetail_num);
        tvIntegralDetailExchange = findView(R.id.tv_integralDetail_exchange);

    }

    @Override
    public void initData(Bundle bundle) {
        String jsonString = bundle.getString(Constant.TO_INTEGRAL_DETAIL);
        store = bundle.getInt(Constant.MY_TOTAL_STORE);
        integralGoods = parseObject(jsonString, IntegralGoods.class);
    }

    @Override
    public void initEvents() {

        tvTitle.setText("商品兑换");
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        tvIntegralDetailGoodsName.setText(integralGoods.getGoodsName());
        tvIntegralDetailNum.setText(integralGoods.getConsumption().toString());
        //加载图片
        imageLoader.displayImage(HttpConstant.ROOT_PATH + integralGoods.getPicUrl(), ivIntegralDetail, ImageOptions.getDefaultOptions());

        /*如果我的总积分小于当前要兑换的积分 颜色变为暗色 */
        if (store < integralGoods.getConsumption()) {
//            tvIntegralDetailExchange.setBackgroundResource(R.color.et_phone_register);
            tvIntegralDetailExchange.setBackground(getResources().getDrawable(R.drawable.bg_soon_take_grad));
        }

        tvIntegralDetailExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login == 2) {
                    showToast("未登录");
                    startActivity(LoginActivity.class);
                    IntegralDetailActivity.this.finish();
                } else {
                    if (store < integralGoods.getConsumption()) {
                        showToast("你的积分不足");
                    } else {
                        showAlertDialog();
                    }
                }
            }
        });
    }

    /*弹窗 提示*/
    private void showAlertDialog() {
        LayoutInflater inflaterDl = LayoutInflater.from(IntegralDetailActivity.this);
        View mView = inflaterDl.inflate(R.layout.dialog_normal_layout, null);
        //对话框
        final Dialog dialog = new AlertDialog.Builder(IntegralDetailActivity.this).create();
        dialog.show();
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setContentView(mView);

                /*设置dialog出现在屏幕的位置*/
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        // lp.x =100; // 新位置X坐标
        lp.y = 600; // 新位置Y坐标
        // lp.width = 300; // 宽度
        // lp.height = 300; // 高度
        // lp.alpha = 1.0f; // 透明度
        dialogWindow.setAttributes(lp);

        TextView positiveBtn = (TextView) dialog.findViewById(R.id.btn_ok);
        TextView negativeBtn = (TextView) dialog.findViewById(R.id.btn_cancle);
        TextView tvContent = (TextView) dialog.findViewById(R.id.tv_content);

        tvContent.setText("此次兑换将使用" + integralGoods.getConsumption().toString() + "积分,确定兑换？");

        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(IntegralDetailActivity.this, store + "你点击了确定" + integralGoods.getId(), Toast.LENGTH_SHORT).show();
                sendContentToBack();
                dialog.dismiss();

            }
        });
    }

    /*当兑换时 将数据传到后台*/
    private void sendContentToBack() {
        integralModel.exchangeGoods(integralGoods.getId());//将数据传到后台服务器
    }

    @Override
    public void integralGoods(String resp) {

    }

    @Override
    public void integralGoodsList(String response) {

    }

    @Override
    public void startToLoginAct() {
        //未登录 接口回调   跳到登录界面
        LogUtils.error(IntegralDetailActivity.class, "-------------未登录登录");
        login = 2;
        startActivity(LoginActivity.class);
        IntegralDetailActivity.this.finish();
    }

    @Override
    public void exchangeGoodsSuccess(String response) {
        showToast("兑换成功,请到兑换记录中领取商品");

        //积分兑换 后再次到达 我的见面 要刷新
        AppApplication.putBoolean("person_fragment_refresh", true);

        finish();
    }

    /**
     * 判断用户是否登录
     *
     * @return 登录返回true
     */
    private boolean isLogin() {
        if (AppApplication.getPreferenceHelper().getString(Constant.SHARED_USER_CELL_PHONE, null) != null
                && AppApplication.getPreferenceHelper().getString(Constant.SHARED_USER_PASSWORD, null) != null) {
            return true;
        }
        return false;
    }

}
