package yunju.com.huiqitian.vm.signin.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.AppApplication;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.entity.GoldDetailResp;
import yunju.com.huiqitian.http.entity.GoldRecordResp;
import yunju.com.huiqitian.http.entity.SignInResp;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.vm.adapter.GoldRecordAdapter;
import yunju.com.huiqitian.vm.login.view.LoginActivity;
import yunju.com.huiqitian.vm.menu.model.MenuModel;
import yunju.com.huiqitian.vm.menu.view.MainMenu;
import yunju.com.huiqitian.vm.signin.model.SignInModel;
import yunju.com.huiqitian.vm.widget.MyListView;

/**
 * Created by 胡月 on 2016/8/11 0011.
 */
public class SignInActivity extends BaseActivity implements SignInModel.GoldRecordInterface {

    /*当前签到或未签到*/
    private int currId;

    /*我的金币数*/
    private String goldNumber;

    /*当前位置的经纬度,地址*/
    private double latitude;
    private double longitude;
    private String myLocation;
    /*title*/
    private LinearLayout ivTitleBack;//返回键
    private TextView tvTitle;//标题

    /*签到部分*/
    private TextView tvSignIn;//中间签到圆球
    private TextView tvBallSignInOne;//周围四个小圆球
    private TextView tvBallSignInTwo;
    private TextView tvBallSignInThree;
    private TextView tvBallSignInFour;
    private TextView tvSignInNine;//签到之后显示textView
    private TextView tvSignInTen;//签到之后显示textView;
    private TextView tvSignInEleven;//签到之后的变化textView
    private TextView tvSignInTwelve;

    /*签到日期获得金币部分*/
    private TextView tvGoldSignInOne;//显示签到获得金币数
    private TextView tvGoldSignInTwo;
    private TextView tvGoldSignInThree;
    private TextView tvGoldSignInFour;
    private TextView tvGoldSignInFive;
    private TextView tvGoldSignInSix;
    private TextView tvGoldSignInSeven;

    private TextView tvTimeSignInOne;
    private TextView tvTimeSignInTwo;
    private TextView tvTimeSignInThree;
    private TextView tvTimeSignInFour;
    private TextView tvTimeSignInFive;
    private TextView tvTimeSignInSix;
    private TextView tvTimeSignInSeven;

    /*显示我的金币数量*/
    private TextView tvGoldNumSignIn;

    /*中奖记录*/
    private MyListView mlvRecordSignIn;

    /*我的金币明细*/
    private TextView tvGoldDetailSignIn;
    /*数据model*/
    private SignInModel signInModel;
    private MenuModel menuModel;

    /*广播相关*/
    private MyLocationAddress myLocationAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_sign_in);

    }

    @Override
    public void initBoot() {
        /*数据model实例化*/
        signInModel = new SignInModel(activity);
        menuModel = new MenuModel(activity);

    }

    @Override
    public void initViews() {
        /*title*/
        ivTitleBack = findView(R.id.iv_title_back);
        tvTitle = findView(R.id.tv_title);

        /*签到部分实例化*/
        tvSignIn = findView(R.id.tv_sign_in);//中间签到圆形
        tvBallSignInOne = findView(R.id.tv_ball_sign_in_one);//周围四个小圆球
        tvBallSignInTwo = findView(R.id.tv_ball_sign_in_two);
        tvBallSignInThree = findView(R.id.tv_ball_sign_in_three);
        tvBallSignInFour = findView(R.id.tv_ball_sign_in_four);
        tvSignInNine=findView(R.id.tv_sign_in_nine);//签到之后显示textView
        tvSignInTen=findView(R.id.tv_sign_in_ten);
        tvSignInEleven=findView(R.id.tv_sign_in_eleven);//签到之后的变化textView
        tvSignInTwelve=findView(R.id.tv_sign_in_twelve);

        /*签到时间部分实例化*/
        tvGoldSignInOne = findView(R.id.tv_gold_sign_in_one);//签到可获得的金币数
        tvGoldSignInTwo = findView(R.id.tv_gold_sign_in_two);
        tvGoldSignInThree = findView(R.id.tv_gold_sign_in_three);
        tvGoldSignInFour = findView(R.id.tv_gold_sign_in_four);
        tvGoldSignInFive = findView(R.id.tv_gold_sign_in_five);
        tvGoldSignInSix = findView(R.id.tv_gold_sign_in_six);
        tvGoldSignInSeven = findView(R.id.tv_gold_sign_in_seven);

        tvTimeSignInOne = findView(R.id.tv_time_sign_in_one);//时间
        tvTimeSignInTwo = findView(R.id.tv_time_sign_in_two);
        tvTimeSignInThree = findView(R.id.tv_time_sign_in_three);
        tvTimeSignInFour = findView(R.id.tv_time_sign_in_four);
        tvTimeSignInFive = findView(R.id.tv_time_sign_in_five);
        tvTimeSignInSix = findView(R.id.tv_time_sign_in_six);
        tvTimeSignInSeven = findView(R.id.tv_time_sign_in_seven);

        /*我的金币数量*/
        tvGoldNumSignIn=findView(R.id.tv_gold_num_sign_in);
        /*中奖记录ListView实例化*/
        mlvRecordSignIn = findView(R.id.mlv_record_sign_in);
        /*金币明细实例化*/
        tvGoldDetailSignIn=findView(R.id.tv_gold_detail_sign_in);
    }

    @Override
    public void initData(Bundle bundle) {
        /*设置标题*/
        tvTitle.setText(getResources().getText(R.string.tv_title_sign_in));

        /*中奖记录*/
        signInModel.goldRecord(1, 20);

        /*签到初始化*/
        signInModel.SignIn();

        /*打开广播*/
        openBroadCasts();

        /*打开定位*/
        openLocationServicer();

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

        /*点击签到*/
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*签到接口*/
                signInModel.getSignIn(myLocation, longitude, latitude, currId);

            }
        });

        /*金币明细点击*/
        tvGoldDetailSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInModel.goldDetails();
            }
        });

        /*签到周围四个小圆球的移动*/
        AnimatorSet animSetOne = new AnimatorSet();
        ObjectAnimator translateXOne = ObjectAnimator.ofFloat(tvBallSignInOne, "translationX", 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50);
        ObjectAnimator translateYOne = ObjectAnimator.ofFloat(tvBallSignInOne, "translationY", 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50, 0, 50);
        animSetOne.play(translateXOne).with(translateYOne);
        animSetOne.setDuration(200000);
        animSetOne.start();

        AnimatorSet animSetTwo = new AnimatorSet();
        ObjectAnimator translateXTwo = ObjectAnimator.ofFloat(tvBallSignInTwo, "translationX", 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0, -100, 0);
        ObjectAnimator translateYTwo = ObjectAnimator.ofFloat(tvBallSignInTwo, "translationY", 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 0, 60, 60, 0);
        animSetTwo.play(translateXTwo).with(translateYTwo);
        animSetTwo.setDuration(200000);
        animSetTwo.start();

        AnimatorSet animSetThree = new AnimatorSet();
        ObjectAnimator translateXThree = ObjectAnimator.ofFloat(tvBallSignInThree, "translationX", 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0, 110, 0);
        ObjectAnimator translateYThree = ObjectAnimator.ofFloat(tvBallSignInThree, "translationY", 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0, -70, 0);
        animSetThree.play(translateXThree).with(translateYThree);
        animSetThree.setDuration(200000);
        animSetThree.start();

        AnimatorSet animSetFour = new AnimatorSet();
        ObjectAnimator translateXFour = ObjectAnimator.ofFloat(tvBallSignInFour, "translationX", 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0);
        ObjectAnimator translateYFour = ObjectAnimator.ofFloat(tvBallSignInFour, "translationY", 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0, -50, 0);
        animSetFour.play(translateXFour).with(translateYFour);
        animSetFour.setDuration(200000);
        animSetFour.start();

    }

    /*中奖记录接口回调*/
    @Override
    public void goldRecordInfo(GoldRecordResp goldRecordResp) {
        mlvRecordSignIn.setAdapter(new GoldRecordAdapter(activity, goldRecordResp.getObj()));
    }

    /*签到初始化接口回调*/
    @Override
    public void initializeSignIn(SignInResp signInResp) {
        goldNumber=signInResp.getObj().getLeftCoins()+"";
        tvGoldNumSignIn.setText(goldNumber);
        if(signInResp.getObj().getIsSigin()==0){
            tvGoldSignInTwo.setBackgroundResource(R.mipmap.bkg_data_sign_in_white);

        }else if(signInResp.getObj().getIsSigin()==1){
            tvSignInNine.setVisibility(View.VISIBLE);
            tvSignInTen.setVisibility(View.VISIBLE);
            tvSignInTen.setText(signInResp.getObj().getSeriesDay() + "天");
            tvGoldSignInTwo.setBackgroundResource(R.mipmap.bkg_data_sign_in_red);
            tvSignInEleven.setText("明日签到可领");
            tvSignInTwelve.setText(signInResp.getObj().getSettings().get(2).getSettingsValue());
            tvSignIn.setVisibility(View.GONE);
            tvBallSignInOne.setVisibility(View.GONE);
            tvBallSignInTwo.setVisibility(View.GONE);
            tvBallSignInThree.setVisibility(View.GONE);
            tvBallSignInFour.setVisibility(View.GONE);
        }
        currId=signInResp.getObj().getCurrId();

        tvGoldSignInOne.setText(signInResp.getObj().getSettings().get(0).getSettingsValue());
        tvGoldSignInTwo.setText(signInResp.getObj().getSettings().get(1).getSettingsValue());
        tvGoldSignInThree.setText(signInResp.getObj().getSettings().get(2).getSettingsValue());
        tvGoldSignInFour.setText(signInResp.getObj().getSettings().get(3).getSettingsValue());
        tvGoldSignInFive.setText(signInResp.getObj().getSettings().get(4).getSettingsValue());
        tvGoldSignInSix.setText(signInResp.getObj().getSettings().get(5).getSettingsValue());
        tvGoldSignInSeven.setText(signInResp.getObj().getSettings().get(6).getSettingsValue());

        /*获取当前时间段*/
        for (int i = -1; i < 6; i++) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
            Date date = new Date();
            String str = sdf.format(date);
            // 创建一个 Calendar 用于比较时间i
            Calendar calendarNew = Calendar.getInstance();
            calendarNew.add(Calendar.DAY_OF_MONTH, i);
            if (i == -1) {
                tvTimeSignInOne.setText(sdf.format(calendarNew.getTime()));
            }  else if (i == 2) {
                tvTimeSignInFour.setText(sdf.format(calendarNew.getTime()));
            } else if (i == 3) {
                tvTimeSignInFive.setText(sdf.format(calendarNew.getTime()));
            } else if (i == 4) {
                tvTimeSignInSix.setText(sdf.format(calendarNew.getTime()));
            } else if (i == 5) {
                tvTimeSignInSeven.setText(sdf.format(calendarNew.getTime()));
            }
        }
    }
    /*签到接口回调*/
    @Override
    public void getSignIn(SignInResp signInResp) {
        goldNumber=signInResp.getObj().getLeftCoins()+"";
        tvGoldNumSignIn.setText(goldNumber);
        tvGoldSignInTwo.setBackgroundResource(R.mipmap.bkg_data_sign_in_red);
        tvSignIn.setVisibility(View.GONE);
        tvBallSignInOne.setVisibility(View.GONE);
        tvBallSignInTwo.setVisibility(View.GONE);
        tvBallSignInThree.setVisibility(View.GONE);
        tvBallSignInFour.setVisibility(View.GONE);
        tvSignInNine.setVisibility(View.VISIBLE);
        tvSignInTen.setVisibility(View.VISIBLE);
        tvSignInTen.setText(signInResp.getObj().getSeriesDay() + "天");
        tvSignInEleven.setText("明日签到可领");
        tvSignInTwelve.setText(signInResp.getObj().getSettings().get(2).getSettingsValue());

        //签到后再次到达 我的见面 要刷新
        AppApplication.putBoolean("person_fragment_refresh",true);

    }

    @Override
    public void goldDetailsInfo(GoldDetailResp goldDetailResp) {
        Bundle bundle=new Bundle();
        bundle.putSerializable(Constant.GOODS_PROP, (Serializable) goldDetailResp.getObj());
        bundle.putString(Constant.GOLD_DETAILS, goldNumber);
        startActivity(GoldDetailActivity.class, bundle);
    }

    @Override
    public void noLogin() {
        Intent intent = new Intent(this,LoginActivity.class);
        intent.putExtra("Details", 1);
        startActivity(intent);
    }

    /**
     * 打开定位向服务中发送广播
     */
    public void openLocationServicer() {
        Intent intent = new Intent(Constant.MY_CONTROL_ACTION);
        intent.putExtra(Constant.MY_CONTROL_MSG_KEY, Constant.OPEN);
        activity.sendBroadcast(intent);
    }

    /**
     * 打开广播
     */
    private void openBroadCasts() {
        /*打开接受定位的广播*/
        MyUtils.saveLocationType("two");
        myLocationAddress = new MyLocationAddress();
        IntentFilter itFilter = new IntentFilter();
        itFilter.addAction(Constant.MY_BROADCAST_ACTION);
        activity.registerReceiver(myLocationAddress, itFilter);

    }

    /**
     * 广播接受，用来接收服务中发来的地址信息
     */
    public class MyLocationAddress extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.error(MainMenu.class, "接受定位数据成功");
            myLocation = intent.getStringExtra(Constant.MY_LOCATION_MSG_KEY);

            latitude = intent.getDoubleExtra(Constant.MY_LATITUDE, 0.0);
            longitude = intent.getDoubleExtra(Constant.MY_Longitude, 0.0);

            LogUtils.error(MainMenu.class, latitude + "JHI" + longitude + "myLocation:" + myLocation);
            closeLocation();//关闭广播
        }
    }

    /**
     * 关闭定位 向服务中发送广播
     */
    public void closeLocation() {
        Intent intent = new Intent(Constant.MY_CONTROL_ACTION);
        intent.putExtra(Constant.MY_CONTROL_MSG_KEY, Constant.CLOSE);
        activity.sendBroadcast(intent);
        LogUtils.error(SignInModel.class, "发送关闭广播成功");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*注销广播*/
        if (myLocationAddress != null) {
            activity.unregisterReceiver(myLocationAddress);
        }
    }
}
