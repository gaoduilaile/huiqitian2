package yunju.com.huiqitian.vm.signin.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.GoldDetail;
import yunju.com.huiqitian.vm.adapter.GoldDeatilAdapter;

/**
 * Created by 胡月 on 2016/8/18 0018.
 */
public class GoldDetailActivity extends BaseActivity{
    /*金币明细的数据*/
    private List<GoldDetail> goldDetails;

    /*显示的ListView*/
    private ListView lvGoldDetail;

    /*我的金币总数量*/
    private String goldNumber;

    private TextView tvGoldDetails;

    /*title*/
    private LinearLayout ivTitleBack;//返回键
    private TextView tvTitle;//标题
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_gold_detail);

    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {

        /*title*/
        ivTitleBack = findView(R.id.iv_title_back);
        tvTitle = findView(R.id.tv_title);

        lvGoldDetail=findView(R.id.lv_gold_detail);
        tvGoldDetails=findView(R.id.tv_tv_gold_details);
    }

    @Override
    public void initData(Bundle bundle) {

        /*设置标题*/
        tvTitle.setText(getResources().getText(R.string.tv_title_sign_detail));
        /*接受跳转数据*/
        goldDetails= (List<GoldDetail>) bundle.getSerializable(Constant.GOODS_PROP);
        goldNumber=bundle.getString(Constant.GOLD_DETAILS);
        tvGoldDetails.setText(goldNumber);
        lvGoldDetail.setAdapter(new GoldDeatilAdapter(activity,goldDetails));
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
