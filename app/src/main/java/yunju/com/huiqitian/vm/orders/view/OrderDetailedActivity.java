package yunju.com.huiqitian.vm.orders.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.AppApplication;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.OrderDetailed;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.vm.adapter.GoodsAdapter;
import yunju.com.huiqitian.vm.adapter.OrderAdapter;
import yunju.com.huiqitian.vm.orders.model.DeleteAndCancelOrdersModel;
import yunju.com.huiqitian.vm.orders.model.OrderDetailedModel;
import yunju.com.huiqitian.vm.widget.MyListView;

public class OrderDetailedActivity extends BaseActivity implements OrderDetailedModel.OrderDetailInterface {

    /*判断是取消订单还是删除订单*/
    private int type = 0;
    /*title*/
    private TextView tvTitle;//标题
    private LinearLayout ivTitleBack;//返回键

    /*订单详细的控件*/
    private TextView tvTradingState;//交易状态
    private TextView tvOrderMarketName;//超市名字
    private TextView tvOrderDetailName;//收货人
    private TextView tvOrderDetailNumber;//收货电话号码
    private TextView tvOrderDetailReceiveAddress;//收货的地址
    private LinearLayout llOrderDetailState;//超市
    private MyListView mlvOrderDetail;//list列表显示所有的商品
    private TextView tvOrderDetailAllPrise;//商品总价
    private TextView tvOrderDetailDistributionMoney;//配送费用
    private TextView tvOrderAllMoney;//订单总价
    private TextView tvOrderCoupons;//优惠券
    private TextView tvOrderRealPayMoney;//实际支付
    private TextView tvOrderNumber;//订单编号
    private TextView tvOrderPayNumber;//支付编号
    private TextView tvOrderCreateTime;//创建时间
    private TextView tvOrderPayTime;//付款时间
    private TextView tvOrderSendTime;//发货时间
    private TextView tvOrderCompleteTime;//成交时间
    private LinearLayout llOrderDetailDelete;//删除订单
    private TextView tvOrderDetailDelete;

    /*订单id*/
    private String id;
    /*删除订单的位置*/
    private int position;
    /*删除订单接口*/
    private DeleteAndCancelOrdersModel deleteAndCancelOrdersModel;

    /*获取订单详细的model*/
    private OrderDetailedModel orderDetailedModel;

    /*区分订单状态*/
    private int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_order_detailed);
    }

    @Override
    public void initBoot() {
        deleteAndCancelOrdersModel = new DeleteAndCancelOrdersModel(activity);

        orderDetailedModel = new OrderDetailedModel(activity);
    }

    @Override
    public void initViews() {
        /*初始化控件*/
        tvTitle = findView(R.id.tv_title);
        ivTitleBack = findView(R.id.iv_title_back);

        tvTradingState = findView(R.id.tv_trading_state);
        tvOrderMarketName = findView(R.id.tv_order_market_name);
        tvOrderDetailName = findView(R.id.tv_order_detail_name);
        tvOrderDetailNumber = findView(R.id.tv_order_detail_number);
        tvOrderDetailReceiveAddress = findView(R.id.tv_order_detail_receiveAddress);
        llOrderDetailState = findView(R.id.ll_order_detail_state);
        tvOrderDetailAllPrise = findView(R.id.tv_order_detail_all_prise);
        mlvOrderDetail = findView(R.id.mlv_order_detail);
        tvOrderDetailDistributionMoney = findView(R.id.tv_order_detail_distribution_money);
        tvOrderAllMoney = findView(R.id.tv_order_all_money);
        tvOrderCoupons = findView(R.id.tv_order_coupons);
        tvOrderRealPayMoney = findView(R.id.tv_order_real_pay_money);
        tvOrderNumber = findView(R.id.tv_order_number);
        tvOrderPayNumber = findView(R.id.tv_order_pay_number);
        tvOrderCreateTime = findView(R.id.tv_order_create_time);
        tvOrderPayTime = findView(R.id.tv_order_pay_time);
        tvOrderSendTime = findView(R.id.tv_order_send_time);
        tvOrderCompleteTime = findView(R.id.tv_order_complete_time);
        llOrderDetailDelete = findView(R.id.ll_order_detail_delete);
        tvOrderDetailDelete = findView(R.id.tv_order_detail_delete);
    }

    @Override
    public void initData(Bundle bundle) {
        tvTitle.setText(R.string.order_detail_title);

        //把获取到的订单编号传出去
        id = bundle.getString(Constant.ORDER_GOOD_ID);
        LogUtils.error(OrderDetailedActivity.class, "id是多少==" + id);
        position = bundle.getInt(Constant.DELETE_ORDER);
        if (!TextUtils.isEmpty(id)) {
            // TODO: 2016-08-15 根据订单号获取详细的信息 进行展示
            orderDetailedModel.getOrderDetail(id);
        }
    }

    @Override
    public void initEvents() {
           /*返回键*/
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                switch (state) {
                    case Constant.ONE:
                        //未付款
                        //跳转到待付款页面
                        bundle.putInt(Constant.ORDERS_MARKS, Constant.WAITE_PAY);
                        startActivity(OrdersActivity.class, bundle);
                        break;
                    case Constant.TWO:
                        //已付款
                        //跳转到全部订单页面
                        bundle.putInt(Constant.ORDERS_MARKS, Constant.ALL_ORDERS);
                        startActivity(OrdersActivity.class, bundle);
                        break;
                    case Constant.THREE:
                        //待发货
                        //跳转到待发货页面
                        bundle.putInt(Constant.ORDERS_MARKS, Constant.WAITE_SEND);
                        startActivity(OrdersActivity.class, bundle);
                        break;
                    case Constant.FOUR:
                        //待收货
                        //跳转到待收货页面
                        bundle.putInt(Constant.ORDERS_MARKS, Constant.WAITE_GET);
                        startActivity(OrdersActivity.class, bundle);
                        break;
                    case Constant.FIVE:
                        //已完成
                        //跳转到待评价页面
                        bundle.putInt(Constant.ORDERS_MARKS, Constant.WAITE_EVALUATE);
                        startActivity(OrdersActivity.class, bundle);
                        break;
                    case Constant.SIX:
                        //已取消
                        //跳转到全部订单页面
                        bundle.putInt(Constant.ORDERS_MARKS, Constant.ALL_ORDERS);
                        startActivity(OrdersActivity.class, bundle);
                        break;
                }
                finish();
            }
        });

        /*删除订单*/
        llOrderDetailDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage("你真的要删除订单？");

                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            deleteAndCancelOrdersModel.deleteAndCancelOrder(HttpConstant.PATH_DELETE_ORDER,
                                    id, position);
                            deleteAndCancelOrdersModel.setDeleteOrderInterface(new DeleteAndCancelOrdersModel.DeleteAndCancelOrderInterface() {
                                @Override
                                public void deleteAndCancelOrderSuccess(String obj, int position) {
                                    //订单取消是我的页面刷新
                                    AppApplication.putBoolean("person_fragment_refresh", true);
                                    finish();
                                }

                                @Override
                                public void deleteAndCancelOrderSuccess(String msg) {
                                    //删除失败
                                    LogUtils.error(OrderAdapter.class, msg);
                                }
                            });
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builder.create().show();
                } else if (type == 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage("你确定取消订单!");

                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            deleteAndCancelOrdersModel.deleteAndCancelOrder(HttpConstant.PATH_CANCEL_ORDER,
                                    id, position);
                            deleteAndCancelOrdersModel.setDeleteOrderInterface(new DeleteAndCancelOrdersModel.DeleteAndCancelOrderInterface() {
                                @Override
                                public void deleteAndCancelOrderSuccess(String obj, int position) {
                                    //订单取消是我的页面刷新
                                    AppApplication.putBoolean("person_fragment_refresh", true);
                                    finish();
                                }

                                @Override
                                public void deleteAndCancelOrderSuccess(String msg) {
                                    //删除失败
                                    LogUtils.error(OrderAdapter.class, msg);
                                }
                            });
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builder.create().show();
                }
            }
        });
    }

    /*获取订单成功后的回调*/
    @Override
    public void orderDetailDateSuccess(OrderDetailed orderDetailed) {
        if (orderDetailed != null) {
             /*订单状态*/
            state = Integer.parseInt(orderDetailed.getOrderState().toString());
            String stateShow = null;
            switch (state) {
                case Constant.ONE:
                    //未付款
                    stateShow = Constant.NO_PAY;
                    tvOrderDetailDelete.setText(R.string.order_details_cancel);
                    type = 1;
                    break;
                case Constant.TWO:
                    //已付款
                    stateShow = Constant.PAY;
                    break;
                case Constant.THREE:
                    //待发货
                    stateShow = Constant.WAITE_SEND_GOOD;
                    break;
                case Constant.FOUR:
                    //待收货
                    stateShow = Constant.WAITE_GET_GOOD;
                    break;
                case Constant.FIVE:
                    //已完成
                    stateShow = Constant.HAS_COMPLET;
                    break;
                case Constant.SIX:
                    //已取消
                    stateShow = Constant.HAS_CANCLE;
                    break;
            }
            /*订单状态*/
            tvTradingState.setText(stateShow);
            /*超市名称*/
            tvOrderMarketName.setText(orderDetailed.getMarketName());
            /*商品总价*/
            if (orderDetailed.getTotalPrice() != null && orderDetailed.getFreight() != null) {
                /*商品总价=订单总价-配送费*/
                String totalPrice = orderDetailed.getTotalPrice().toString();//订单总价
                String freight = orderDetailed.getFreight().toString();//配送费
                double v = Double.parseDouble(totalPrice);
                double v1 = Double.parseDouble(freight);
                BigDecimal bigDecimal = new BigDecimal(Double.toString(v));
                BigDecimal bigDecimal1 = new BigDecimal(Double.toString(v1));
                BigDecimal subtract = bigDecimal.subtract(bigDecimal1);
                tvOrderDetailAllPrise.setText(subtract+"");
            }
            /*配送费*/
            if (orderDetailed.getFreight() != null) {
                tvOrderDetailDistributionMoney.setText(orderDetailed.getFreight().toString());
            }
            /*订单总价*/
            if (orderDetailed.getAppOrderGoodsList().get(0).getRetailPrice() != null) {
                tvOrderAllMoney.setText(orderDetailed.getTotalPrice().toString());
            }
            /*优惠券*/
            if (orderDetailed.getVoucherAmount() != null) {
                tvOrderCoupons.setText(orderDetailed.getVoucherAmount().toString());
            }
            /*实际支付*/
            if (orderDetailed.getPayFee() != null) {
                tvOrderRealPayMoney.setText(orderDetailed.getPayFee().toString());
            }
            /*订单编号*/
            if (!TextUtils.isEmpty(orderDetailed.getId()))
                tvOrderNumber.setText(orderDetailed.getId());
            /*支付单号*/
            if (!TextUtils.isEmpty(orderDetailed.getPaymentId()))
                tvOrderPayNumber.setText(orderDetailed.getPaymentId());
            /*创建时间*/
            if (!TextUtils.isEmpty(orderDetailed.getStrOrderTime()))
                tvOrderCreateTime.setText(orderDetailed.getStrOrderTime());
            /*付款时间*/
            if (!TextUtils.isEmpty(orderDetailed.getStrPayTime()))
                tvOrderPayTime.setText(orderDetailed.getStrPayTime());
            /*发货时间*/
            if (!TextUtils.isEmpty(orderDetailed.getStrDispatchTime()))
                tvOrderSendTime.setText(orderDetailed.getStrDispatchTime());
            /*成交时间*/
            if (!TextUtils.isEmpty(orderDetailed.getStrFinishTime()))
                tvOrderCompleteTime.setText(orderDetailed.getStrFinishTime());

            if (!TextUtils.isEmpty(orderDetailed.getName()))
                    tvOrderDetailName.setText(orderDetailed.getName());
            if (!TextUtils.isEmpty(orderDetailed.getTel()))
                    tvOrderDetailNumber.setText(orderDetailed.getTel());
            if (!TextUtils.isEmpty(orderDetailed.getAddressInfo()))
                    tvOrderDetailReceiveAddress.setText(orderDetailed.getAddressInfo());


            /*显示订单商品list*/
            mlvOrderDetail.setAdapter(new GoodsAdapter(activity, orderDetailed.getAppOrderGoodsList()));

        }
    }

    /*获取地址信息*/


    @Override
    public void orderDetailDateFailure(String msg) {

        LogUtils.error(OrderDetailedActivity.class, "订单详情失败:" + msg);
    }
}
