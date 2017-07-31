package yunju.com.huiqitian.vm.vouchers.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import yunju.com.huiqitian.AppApplication;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.entity.SearchResp;
import yunju.com.huiqitian.http.entity.VoucherResp;
import yunju.com.huiqitian.vm.adapter.VoucherAdapter;
import yunju.com.huiqitian.vm.vouchers.model.VoucherModel;

/**
 * @author 张超群
 * @since 2016/8/11
 * 代金券页面
 */
public class VoucherActivity extends BaseActivity implements VoucherModel.VoucherInterface {

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

    /*代金券model*/
    private VoucherModel voucherModel;

    /*代金券数据*/
    private List<VoucherResp> voucherList;

    /*适配器*/
    private VoucherAdapter voucherAdapter;

    public static VoucherActivity voucherActivity = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_voucher);
        voucherActivity=this;
    }

    @Override
    public void initBoot() {
        voucherModel = new VoucherModel(activity);
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
        //设置标题栏
        tvTitle.setText(R.string.voucher_title);
        //标题栏右边的图标
        ivTitleShare.setVisibility(View.VISIBLE);

        lvVoucher.setVisibility(View.GONE);
        llVoucherNoDate.setVisibility(View.GONE);

        /*得到传值*/
        int state = bundle.getInt(Constant.VOUCHER_MARKS);
        switch (state) {
            case Constant.ONE:
                //得到的是1，加载未使用的代金券
                setTextViewSelected();
                tvNoUseVoucher.setSelected(true);
                setViewInvisible();
                viewNoUseVoucher.setVisibility(View.VISIBLE);
                voucherModel.getVouchers(Constant.ONE);
                break;
            case Constant.TWO:
                //2：加载已使用的代金券
                setTextViewSelected();
                tvUseVoucher.setSelected(true);
                setViewInvisible();
                viewUseVoucher.setVisibility(View.VISIBLE);
                voucherModel.getVouchers(Constant.TWO);
                break;
            case Constant.THREE:
                //3：加载已过期的代金券
                setTextViewSelected();
                tvOverdueVoucher.setSelected(true);
                setViewInvisible();
                viewOverdueVoucher.setVisibility(View.VISIBLE);
                voucherModel.getVouchers(Constant.THREE);
                break;
        }


    }

    @Override
    public void initEvents() {
        /*返回键*/
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*未使用*/
        rlNoUseVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewSelected();
                tvNoUseVoucher.setSelected(true);
                setViewInvisible();
                viewNoUseVoucher.setVisibility(View.VISIBLE);
                voucherModel.getVouchers(Constant.ONE);
            }
        });

        /*已使用*/
        rlUseVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewSelected();
                tvUseVoucher.setSelected(true);
                setViewInvisible();
                viewUseVoucher.setVisibility(View.VISIBLE);
                voucherModel.getVouchers(Constant.TWO);
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
                voucherModel.getVouchers(Constant.THREE);
            }
        });

        /*代金券点击*/
        lvVoucher.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (voucherList.get(position).getAllGoods() != (byte) 1) {
                    voucherModel.getVoucherGoods(1, 20, "qty", "desc", voucherList.get(position).getId());
                    AppApplication.putInt("voucher_id", voucherList.get(position).getId());
                } else {
                    showToast("这是针对所有商品的代金券");
                }
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

    /*获取数据后的回调*/
    @Override
    public void voucherDateSuccess(List<VoucherResp> voucherRespList, int status) {
        voucherList = voucherRespList;
        if (voucherRespList.size() == 0) {
            llVoucherNoDate.setVisibility(View.VISIBLE);
            lvVoucher.setVisibility(View.GONE);
        } else {
            llVoucherNoDate.setVisibility(View.GONE);
            lvVoucher.setVisibility(View.VISIBLE);
            /*去加载适配器显示*/
            voucherAdapter = new VoucherAdapter(activity, voucherRespList, status);
            lvVoucher.setAdapter(voucherAdapter);
        }
    }

    @Override
    public void voucherDateFailure(String msg) {

    }

    @Override
    public void voucherGoods(SearchResp searchResp) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("voucher", searchResp);
        startActivity(VoucherGoodsActivity.class, bundle);
    }

    @Override
    public void noVoucherGoods(String msg) {

    }
}
