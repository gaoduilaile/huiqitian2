package yunju.com.huiqitian.vm.details.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.entity.PropResp;
import yunju.com.huiqitian.vm.adapter.GoodsDetailsAdapter;

/**
 * Created by 胡月 on 2016/8/4 0004.
 */
public class GoodsDetailsActivity extends BaseActivity{

    /*title*/
    private LinearLayout ivTitleBack;//返回键
    private TextView tvTitle;//标题

    /*显示控件*/
    private ListView lvGoodsDetails;

    /*传递的数据*/
    PropResp propResp;

    /*适配器*/
    private GoodsDetailsAdapter goodsDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_goods_details);
    }
    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        /*title*/
        ivTitleBack=findView(R.id.iv_title_back);//返回键
        tvTitle=findView(R.id.tv_title);//标题
        /*显示控件*/
        lvGoodsDetails=findView(R.id.lv_goods_details);

    }

    @Override
    public void initData(Bundle bundle) {
        tvTitle.setText(getResources().getText(R.string.tv_title_goods_details));//头部标题（商品详细）

        //取出传过来的信息

        propResp= (PropResp) getIntent().getSerializableExtra(Constant.GOODS_PROP);
        goodsDetailsAdapter=new GoodsDetailsAdapter(GoodsDetailsActivity.this,propResp.getObj());


        lvGoodsDetails.setAdapter(goodsDetailsAdapter);
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
