package yunju.com.huiqitian.vm.message.view;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;

/**
 * create by gaoqiong 2016/9/2
 * 活动消息 detail
 */
public class MessageExerciseDetailActivity extends BaseActivity {
    /*title*/
    private LinearLayout ivTitleBack;//返回键
    private TextView tvTitle;//标题
    private WebView webView;
    private LinearLayout ll_title_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_message_exercise_detail);

    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        /*title*/
        ivTitleBack = findView(R.id.iv_title_back);
        tvTitle = findView(R.id.tv_title);
        ll_title_back = findView(R.id.ll_title_back);


        ll_title_back.setVisibility(View.VISIBLE);
        ivTitleBack.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);

        webView = findView(R.id.wv_webView);
    }

    @Override
    public void initData(Bundle bundle) {

        String messageUrl = getIntent().getStringExtra(Constant.MESSAGE_URL);
        LogUtils.error(MessageExerciseDetailActivity.class,messageUrl);

        tvTitle.setText("查看详情");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);//是否可以缩放，默认true
        webView.getSettings().setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        webView.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        webView.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        // webView.loadUrl("file:///android_asset/example.html");
        //"http://news.sina.com.cn/gov/xlxw/2016-09-01/doc-ifxvqctu5868239.shtml"

        webView.loadUrl(messageUrl);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
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
