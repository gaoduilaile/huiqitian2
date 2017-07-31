package yunju.com.huiqitian.vm.vouchers.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import yunju.com.huiqitian.AppApplication;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.GoodsInfo;
import yunju.com.huiqitian.http.entity.SearchResp;
import yunju.com.huiqitian.http.entity.VoucherResp;
import yunju.com.huiqitian.vm.adapter.VoucherGoodsAdapter;
import yunju.com.huiqitian.vm.details.view.DetailsActivity;
import yunju.com.huiqitian.vm.vouchers.model.VoucherModel;
import yunju.com.huiqitian.vm.widget.MyGridView;
import yunju.com.huiqitian.vm.widget.PullToRefreshLayout;

/**
 * Created by huYue on 2017/1/18 0018.
 */
public class VoucherGoodsActivity extends BaseActivity implements VoucherModel.VoucherInterface{

    /*title*/
    private TextView tvTitle;//标题
    private LinearLayout ivTitleBack;//返回键
    private ImageView ivTitleShare;//右边图片按钮

    /*显示代金券商品的控件*/
    private MyGridView gvVoucherGoods;

    /*代金券商品数据*/
    SearchResp searchResp;
    /*代金券model*/
    private VoucherModel voucherModel;

    /*下拉 上拉刷新控件*/
    private PullToRefreshLayout pullRefreshResult;

    private PullToRefreshLayout mPullToRefreshLayout;
    private int voucher_id;
    private int num = 20;
    /*判断下拉1， 还是上拉2*/
    private int refreshState = 0;

    public static VoucherGoodsActivity voucherGoodsActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_voucher_goods);
        voucherGoodsActivity=this;
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

        gvVoucherGoods = findView(R.id.gv_voucher_goods);

           /*刷新*/
        pullRefreshResult = findView(R.id.pull_refresh_result);

    }

    @Override
    public void initData(Bundle bundle) {
        voucher_id = AppApplication.getInt("voucher_id");
        //设置标题栏
        tvTitle.setText(R.string.voucher_goods_title);
        //标题栏右边的图标
        /*ivTitleShare.setVisibility(View.VISIBLE);
        ivTitleShare.setImageResource(R.mipmap.shengluohao);*/
        searchResp = (SearchResp) bundle.getSerializable("voucher");
        gvVoucherGoods.setAdapter(new VoucherGoodsAdapter(activity, searchResp.getObj()));

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

        /*代金券商品点击事件*/
        gvVoucherGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                start(position, searchResp.getObj());
            }
        });


        pullRefreshResult.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

                if (voucher_id != -1) {
                    voucherModel.getVoucherGoods(1, num, "qty", "desc", voucher_id);
                }
                refreshState = 1;
                mPullToRefreshLayout = pullToRefreshLayout;

            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

                num += 10;
                if (voucher_id != -1) {
                    voucherModel.getVoucherGoods(1, num, "qty", "desc", voucher_id);
                }
                refreshState = 2;
                mPullToRefreshLayout = pullToRefreshLayout;

            }
        });
    }

    /**
     * 页面跳转
     */
    public void start(int position, List<GoodsInfo> goodsInfo) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.START_NEXT_KEY, Constant.START_BY_HOME);
        bundle.putInt(Constant.ITEM_POSITION, position);
        bundle.putSerializable(Constant.GOODS_PROP, (Serializable) goodsInfo);
        startActivity(DetailsActivity.class, bundle);
    }

    @Override
    public void voucherDateSuccess(List<VoucherResp> voucherRespList, int status) {

    }

    @Override
    public void voucherDateFailure(String msg) {

    }

    @Override
    public void voucherGoods(SearchResp searchResp) {
        this.searchResp = searchResp;
        VoucherGoodsAdapter voucherGoodsAdapter = new VoucherGoodsAdapter(activity, searchResp.getObj());
        gvVoucherGoods.setAdapter(voucherGoodsAdapter);
        voucherGoodsAdapter.notifyDataSetChanged();

        if (refreshState == 1) {
            mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
        } else if (refreshState == 2) {
            mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
        }

    }

    @Override
    public void noVoucherGoods(String msg) {

    }
}
