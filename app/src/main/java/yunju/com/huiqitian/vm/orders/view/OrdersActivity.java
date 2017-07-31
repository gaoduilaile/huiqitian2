package yunju.com.huiqitian.vm.orders.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.AllOrders;
import yunju.com.huiqitian.vm.adapter.OrderAdapter;
import yunju.com.huiqitian.vm.orders.model.ObtainOrdersModel;


/**
 * @author 张超群
 *         <p/>
 *         查看所有的订单页面
 */
public class OrdersActivity extends BaseActivity implements ObtainOrdersModel.AllOrdersInterface {

    /*title*/
    private TextView tvTitle;//标题
    private LinearLayout ivTitleBack;//返回键

    /*fragment页切换相关*/
    private RelativeLayout rlOrdersAll;//全部订单总布局
    private TextView tvOrdersAll;//全部
    private View viewOrdersAll;//全部下面的线

    private RelativeLayout rlWaitPay;//待支付总布局
    private TextView tvWaitPay;//待支付
    private View viewWaitPay;//待支付下面的线

    private RelativeLayout rlWaitSendGoods;//待发货总布局
    private TextView tvWaitSendGoods;//待发货
    private View viewWaitSendGoods;//待发货下面的线

    private RelativeLayout rlWaitGetGood;//待收货总布局
    private TextView tvWaitGetGood;//待收货
    private View viewWaitGetGood;//待收货下面的线

    private RelativeLayout rlWaitEvaluation;//待评价总布局
    private TextView tvWaitEvaluation;//待评价
    private View viewWaitEvaluation;//待评价下面的线

    /*订单显示listView*/
    private ListView lvOrders;

    private LinearLayout flOrdersNo;

    /*订单适配器*/
    private OrderAdapter orderAdapter;

    /*所有订单的model*/
    private ObtainOrdersModel obtainOrdersModel;

/*当前选择的是哪一个*/

    private int selected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_orders);
    }

    @Override
    public void initBoot() {
        obtainOrdersModel = new ObtainOrdersModel(activity);
        obtainOrdersModel.setAllOrdersInterface(this);
    }

    @Override
    public void initViews() {
        /*初始化控件*/
        tvTitle = findView(R.id.tv_title);
        ivTitleBack = findView(R.id.iv_title_back);
        /*全部订单*/
        rlOrdersAll = findView(R.id.rl_orders_all);
        tvOrdersAll = findView(R.id.tv_orders_all);
        viewOrdersAll = findView(R.id.view_orders_all);
        /*待付款*/
        rlWaitPay = findView(R.id.rl_wait_pay);
        tvWaitPay = findView(R.id.tv_wait_pay);
        viewWaitPay = findView(R.id.view_wait_pay);
        /*待发货*/
        rlWaitSendGoods = findView(R.id.rl_wait_send_goods);
        tvWaitSendGoods = findView(R.id.tv_wait_send_goods);
        viewWaitSendGoods = findView(R.id.view_wait_send_goods);
        /*待收货*/
        rlWaitGetGood = findView(R.id.rl_wait_get_good);
        tvWaitGetGood = findView(R.id.tv_wait_get_good);
        viewWaitGetGood = findView(R.id.view_wait_get_good);
        /*待评价*/
        rlWaitEvaluation = findView(R.id.rl_wait_evaluation);
        tvWaitEvaluation = findView(R.id.tv_wait_evaluation);
        viewWaitEvaluation = findView(R.id.view_wait_evaluation);

        /*所有的订单列表*/
        lvOrders = findView(R.id.lv_orders);
        flOrdersNo = findView(R.id.fl_orders_no);

    }

    @Override
    public void initData(Bundle bundle) {
        tvTitle.setText(R.string.all_orders);

        switch (bundle.getInt(Constant.ORDERS_MARKS)) {
            case Constant.ALL_ORDERS:
                //全部订单
                setTextViewSelected();
                tvOrdersAll.setSelected(true);
                setViewInvisible();
                viewOrdersAll.setVisibility(View.VISIBLE);
                obtainOrdersModel.getOrders(Constant.ZERO);
                selected = 0;
                break;
            case Constant.WAITE_PAY:
                //待付款
                setTextViewSelected();
                tvWaitPay.setSelected(true);
                setViewInvisible();
                viewWaitPay.setVisibility(View.VISIBLE);
                obtainOrdersModel.getOrders(Constant.ONE);
                selected = 1;
                break;
            case Constant.WAITE_SEND:
                //待发货
                setTextViewSelected();
                tvWaitSendGoods.setSelected(true);
                setViewInvisible();
                viewWaitSendGoods.setVisibility(View.VISIBLE);
                obtainOrdersModel.getOrders(Constant.THREE);
                selected = 3;
                break;
            case Constant.WAITE_GET:
                //待收货
                setTextViewSelected();
                tvWaitGetGood.setSelected(true);
                setViewInvisible();
                viewWaitGetGood.setVisibility(View.VISIBLE);
                obtainOrdersModel.getOrders(Constant.FOUR);
                selected = 4;
                break;
            case Constant.WAITE_EVALUATE:
                //待评价
                setTextViewSelected();
                tvWaitEvaluation.setSelected(true);
                setViewInvisible();
                viewWaitEvaluation.setVisibility(View.VISIBLE);
                obtainOrdersModel.getOrders(Constant.FIVE);
                selected = 5;
                break;
        }
    }

    @Override
    public void initEvents() {
         /*返回键*/
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                setResult(2, intent);
                finish();
            }
        });

        /*全部订单*/
        rlOrdersAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewSelected();
                tvOrdersAll.setSelected(true);
                setViewInvisible();
                viewOrdersAll.setVisibility(View.VISIBLE);
                obtainOrdersModel.getOrders(Constant.ZERO);
                selected = 0;

            }
        });

         /*待付款*/
        rlWaitPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewSelected();
                tvWaitPay.setSelected(true);
                setViewInvisible();
                viewWaitPay.setVisibility(View.VISIBLE);
                obtainOrdersModel.getOrders(Constant.ONE);
                selected = 1;
            }
        });

         /*待发货*/
        rlWaitSendGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //待发货
                setTextViewSelected();
                tvWaitSendGoods.setSelected(true);
                setViewInvisible();
                viewWaitSendGoods.setVisibility(View.VISIBLE);
                obtainOrdersModel.getOrders(Constant.THREE);
                selected = 3;
            }
        });

         /*待收货*/
        rlWaitGetGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewSelected();
                tvWaitGetGood.setSelected(true);
                setViewInvisible();
                viewWaitGetGood.setVisibility(View.VISIBLE);
                obtainOrdersModel.getOrders(Constant.FOUR);
                selected = 4;
            }
        });

         /*待评价*/
        rlWaitEvaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewSelected();
                tvWaitEvaluation.setSelected(true);
                setViewInvisible();
                viewWaitEvaluation.setVisibility(View.VISIBLE);
                obtainOrdersModel.getOrders(Constant.FIVE);
                selected = 5;
            }
        });

    }


    /**
     * 重置所有选择框的状态（点击换颜色）
     */
    private void setTextViewSelected() {
        tvOrdersAll.setSelected(false);
        tvWaitPay.setSelected(false);
        tvWaitSendGoods.setSelected(false);
        tvWaitGetGood.setSelected(false);
        tvWaitEvaluation.setSelected(false);
    }

    /**
     * 让所有线隐藏
     */
    private void setViewInvisible() {
        viewOrdersAll.setVisibility(View.INVISIBLE);
        viewWaitPay.setVisibility(View.INVISIBLE);
        viewWaitSendGoods.setVisibility(View.INVISIBLE);
        viewWaitGetGood.setVisibility(View.INVISIBLE);
        viewWaitEvaluation.setVisibility(View.INVISIBLE);
    }

    @Override
    public void allOrdersListSuccess(final List<AllOrders> allOrdersRespList) {
        // showToast(allOrdersRespList.toString());
         /*如果没有订单，提示没有订单*/
        if (allOrdersRespList.size() == 0||allOrdersRespList==null) {
            lvOrders.setVisibility(View.GONE);
            flOrdersNo.setVisibility(View.VISIBLE);
        } else {
            orderAdapter = new OrderAdapter(activity, allOrdersRespList);
            flOrdersNo.setVisibility(View.GONE);
            lvOrders.setVisibility(View.VISIBLE);
            lvOrders.setAdapter(orderAdapter);
            orderAdapter.setDeleteSuccessInterface(new OrderAdapter.DeleteAndCancelSuccessInterface() {
                @Override
                public void deleteAndCancelSuccess(int position, boolean isDelete) {
                    if (isDelete) {
                        allOrdersRespList.remove(position);
                        orderAdapter.notifyDataSetChanged();
                    } else {
                        setTextViewSelected();
                        setViewInvisible();
                        if (selected == 0) {
                            tvOrdersAll.setSelected(true);
                            viewOrdersAll.setVisibility(View.VISIBLE);
                            obtainOrdersModel.getOrders(Constant.ZERO);
                        } else if (selected == 1) {
                            tvWaitPay.setSelected(true);
                            viewWaitPay.setVisibility(View.VISIBLE);
                            obtainOrdersModel.getOrders(Constant.ONE);
                        } else if (selected == 3) {
                            tvWaitSendGoods.setSelected(true);
                            viewWaitSendGoods.setVisibility(View.VISIBLE);
                            obtainOrdersModel.getOrders(Constant.THREE);
                        } else if (selected == 4) {
                            tvWaitGetGood.setSelected(true);
                            viewWaitGetGood.setVisibility(View.VISIBLE);
                            obtainOrdersModel.getOrders(Constant.FOUR);
                        } else if (selected == 5) {
                            tvWaitEvaluation.setSelected(true);
                            viewWaitEvaluation.setVisibility(View.VISIBLE);
                            obtainOrdersModel.getOrders(Constant.FIVE);
                        }
                        orderAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    @Override
    public void allOrdersListFailure(String msg) {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setTextViewSelected();
        tvOrdersAll.setSelected(true);
        setViewInvisible();
        viewOrdersAll.setVisibility(View.VISIBLE);
        obtainOrdersModel.getOrders(Constant.ZERO);
        if (orderAdapter != null) {
            orderAdapter.notifyDataSetChanged();
        }
    }
}
