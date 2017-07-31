package yunju.com.huiqitian.vm.integral.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.entity.IntegralGoodsDuihuan;
import yunju.com.huiqitian.vm.adapter.IntegralGoodsAdapter2;
import yunju.com.huiqitian.vm.integral.model.IntegralModel;
import yunju.com.huiqitian.vm.login.view.LoginActivity;

/**
 * Created by liuGang on 2016/8/18 0018.
 */
public class ExchangeRecordActivity extends BaseActivity implements IntegralModel.IntegralStoreInterface{

    /*title*/
    private TextView tvTitle;//标题
    private LinearLayout ivTitleBack;//返回键
    private ImageView ivTitleShare;//右边图片按钮

    /*未使用*/
    private RelativeLayout rlNoUseVoucher;//总布局
    private TextView tvNoUseVoucher;//未使用文字
    private View viewNoUseVoucher;//未使用下划线

    /*已使用*/
    private RelativeLayout rlUseVoucher;//总布局
    private TextView tvUseVoucher;//已使用文字
    private View viewUseVoucher;//已使用下划线

    /*已过期*/
    private RelativeLayout rlOverdueVoucher;//总布局
    private TextView tvOverdueVoucher;//已过期文字
    private View viewOverdueVoucher;//已过期下划线

    /*加载显示内容*/
    private ListView lvVoucher;

    /*没有数据时显示*/
    private LinearLayout llVoucherNoDate;

    /*积分model*/
    private IntegralModel integralModel;

    /*兑换记录集合*/
/*    private List<IntegralGoodsDuihuan.IntegralGoods2> goodsList = new ArrayList<>();*/
    private IntegralGoodsAdapter2 integralGoodsAdapter2;
    /*当前请求页*/
    private int curPage = 1;
    /*每次请求的数量*/
    private int requestNum = 20;
    private byte state=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_voucher);
    }

    @Override
    public void initBoot() {
        integralModel=new IntegralModel(ExchangeRecordActivity.this);
        integralModel.exchangeRecord(curPage, requestNum, state);
    }

    @Override
    public void initViews() {

        /*标题栏*/
        tvTitle = findView(R.id.tv_title);
        ivTitleBack = findView(R.id.iv_title_back);
        ivTitleShare = findView(R.id.iv_title_share);
        /*未使用*/
        rlNoUseVoucher = findView(R.id.rl_no_use_voucher);
        tvNoUseVoucher = findView(R.id.tv_no_use_voucher);
        viewNoUseVoucher = findView(R.id.view_no_use_voucher);
         /*已使用*/
        rlUseVoucher = findView(R.id.rl_use_voucher);
        tvUseVoucher = findView(R.id.tv_use_voucher);
        viewUseVoucher = findView(R.id.view_use_voucher);
         /*已过期*/
        rlOverdueVoucher = findView(R.id.rl_overdue_voucher);
        tvOverdueVoucher = findView(R.id.tv_overdue_voucher);
        viewOverdueVoucher = findView(R.id.view_overdue_voucher);

        /*加载显示内容*/
        lvVoucher = findView(R.id.lv_voucher);

        /*没有数据时显示*/
       llVoucherNoDate = findView(R.id.ll_voucher_no_date);

    }

    @Override
    public void initData(Bundle bundle) {



    }

    @Override
    public void initEvents() {

        /*默认加载未领取*/
        setTextViewSelected();
        tvNoUseVoucher.setSelected(true);
        setViewInvisible();
        viewNoUseVoucher.setVisibility(View.VISIBLE);

        tvNoUseVoucher.setText("未领取");
                tvUseVoucher.setText("已领取");
        tvOverdueVoucher.setText("已过期");
        tvTitle.setText("兑换记录");
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExchangeRecordActivity.this.finish();
            }
        });

        ivTitleShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("好东西记得分享给好盆友哦！");
            }
        });

        /*未领取*/
        rlNoUseVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewSelected();
                tvNoUseVoucher.setSelected(true);
                setViewInvisible();
                viewNoUseVoucher.setVisibility(View.VISIBLE);
                state=1;
                integralModel.exchangeRecord(curPage, requestNum , state);
                /*integralGoodsAdapter2.notifyDataSetChanged();*/
            }
        });

        /*已领取*/
        rlUseVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewSelected();
                tvUseVoucher.setSelected(true);
                setViewInvisible();
                viewUseVoucher.setVisibility(View.VISIBLE);
                state=2;
                integralModel.exchangeRecord(curPage, requestNum , state);
                /*integralGoodsAdapter2.notifyDataSetChanged();*/
            }
        });

        /*已过期*/
        rlOverdueVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewSelected();
                tvOverdueVoucher.setSelected(true);
                setViewInvisible();
                viewOverdueVoucher.setVisibility(View.VISIBLE);
                state=3;
                integralModel.exchangeRecord(curPage, requestNum , state);
                /*integralGoodsAdapter2.notifyDataSetChanged();*/
            }
        });
    }


    /**
     * 重置所有选择框的状态（点击换颜色）
     */
    private void setTextViewSelected() {
        tvNoUseVoucher.setSelected(false);
        tvUseVoucher.setSelected(false);
        tvOverdueVoucher.setSelected(false);
    }

    /**
     * 让所有线隐藏
     */
    private void setViewInvisible() {
        viewNoUseVoucher.setVisibility(View.GONE);
        viewUseVoucher.setVisibility(View.GONE);
        viewOverdueVoucher.setVisibility(View.GONE);
    }

    @Override
    public void integralGoods(String resp) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //兑换之后数据刷新
        if (resultCode == RESULT_OK) {
            state=1;
            integralModel.exchangeRecord(curPage, requestNum, state);
        }
    }

    @Override
    public void integralGoodsList(String response) {
        //积分兑换记录
        LogUtils.error(ExchangeRecordActivity.class, "----------------"+response);
        //解析json数据将data传递到adapter中
        IntegralGoodsDuihuan integralGoodsResp = parseObject(response, IntegralGoodsDuihuan.class);
        List<IntegralGoodsDuihuan.IntegralGoods2> goods2List = integralGoodsResp.getObj();

        if (goods2List.isEmpty() || goods2List == null) {
            //无数据
            lvVoucher.setVisibility(View.GONE);
            llVoucherNoDate.setVisibility(View.VISIBLE);
            // pullVoucherList.loadmoreFinish(PullToRefreshLayout.FAIL);
        } else {
            //有数据
            lvVoucher.setVisibility(View.VISIBLE);
            llVoucherNoDate.setVisibility(View.GONE);
                    /*goodsList.addAll(goods2List);*/
            LogUtils.error(ExchangeRecordActivity.class,"jsonRe---"+goods2List.size());
                    /*if (integralGoodsAdapter2 == null) {*/
            integralGoodsAdapter2 = new IntegralGoodsAdapter2(activity, goods2List);
            lvVoucher.setAdapter(integralGoodsAdapter2);
                   /* } else {

                        integralGoodsAdapter2.notifyDataSetChanged();
                        lvVoucher.setAdapter(integralGoodsAdapter2);
                        //加载成功刷新数据
                        // pullVoucherList.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }*/
        }
    }

    @Override
    public void startToLoginAct() {
        //未登录 接口回调   跳到登录界面
        startActivity(LoginActivity.class);
        ExchangeRecordActivity.this.finish();
    }

    @Override
    public void exchangeGoodsSuccess(String response) {

    }
}
