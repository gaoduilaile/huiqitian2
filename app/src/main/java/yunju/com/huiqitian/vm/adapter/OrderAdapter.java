package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;
import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.AppApplication;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.AllOrders;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.entity.WeiXinReq;
import yunju.com.huiqitian.utils.pay.AliPayUtils;
import yunju.com.huiqitian.vm.orders.model.DeleteAndCancelOrdersModel;
import yunju.com.huiqitian.vm.orders.view.EvaluationActivity;
import yunju.com.huiqitian.vm.orders.view.LookDistributionActivity;
import yunju.com.huiqitian.vm.orders.view.OrderDetailedActivity;
import yunju.com.huiqitian.vm.orders.view.OrdersActivity;
import yunju.com.huiqitian.vm.pay.model.PayModel;
import yunju.com.huiqitian.vm.widget.MyListView;

/**
 * Created by 张超群 on 2016-08-08.
 * <p/>
 * 订单显示适配器
 */
public class OrderAdapter extends CommonAdapter<AllOrders> {

    /*需要传递的值*/
    private Bundle bundle;

    /*订单model*/
    private DeleteAndCancelOrdersModel deleteAndCancelOrdersModel;

    /*支付model*/
    private PayModel payModel;
    /*支付工具类*/
    private AliPayUtils aliPayUtils;
    /*商品总价*/
    private BigDecimal bdPrice;
    /*微信支付与支付宝支付的标示*/
    private int pay = 1;

    public OrderAdapter(Activity activity, List<AllOrders> list) {
        super(activity, list);
        bundle = new Bundle();
        deleteAndCancelOrdersModel = new DeleteAndCancelOrdersModel(activity);
        payModel = new PayModel(activity);
        aliPayUtils = new AliPayUtils(activity);
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            convertView = getLayoutInflater().inflate(R.layout.orders_list_item, parent, false);
            viewHolder.tvSumpermarketName = findView(convertView, R.id.tv_sumpermarket_name);
            viewHolder.tvSumpermarketState = findView(convertView, R.id.tv_sumpermarket_state);
            viewHolder.mlvOrderDetails = findView(convertView, R.id.mlv_order_details);
            viewHolder.tvShowGoodNumber = findView(convertView, R.id.tv_show_good_number);
            viewHolder.tvShowGoodMoney = findView(convertView, R.id.tv_show_good_money);
            viewHolder.tvShowFreight = findView(convertView, R.id.tv_show_freight);
            viewHolder.tvPayOrder = findView(convertView, R.id.tv_pay_order);
            viewHolder.tvCountersignOrder = findView(convertView, R.id.tv_countersign_order);

             /*订单的操作*/
            viewHolder.tvEvaluationOrder = findView(convertView, R.id.tv_evaluation_order);
            viewHolder.tvLookDistribution = findView(convertView, R.id.tv_look_distribution);
            viewHolder.tvDeleteOrder = findView(convertView, R.id.tv_delete_ordel);
            viewHolder.tvCancelOrder = findView(convertView, R.id.tv_cancle_order);

            /*进入超市*/
            viewHolder.llSumpermarketState = findView(convertView, R.id.ll_sumpermarket_state);

            /*订单操作的总布局*/
            viewHolder.rlCancleOrder = findView(convertView, R.id.rl_cancle_order);
            viewHolder.rlTradingCompleteOrder = findView(convertView, R.id.rl_trading_complete_order);
            viewHolder.rlDeleteOrder = findView(convertView, R.id.rl_delete_order);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (Constant.RIGHT != Integer.parseInt(getItem(position).getOrderState().toString()) && Constant.NINE != Integer.parseInt(getItem(position).getOrderState().toString())) {
            //设置文字显示
            viewHolder.tvSumpermarketName.setText(getItem(position).getMarketName());//超市名字
        /*订单状态*/
            int state = Integer.parseInt(getItem(position).getOrderState().toString());
            String stateShow = null;
            switch (state) {
                case Constant.ONE:
                    //未付款
                    stateShow = Constant.NO_PAY;
                    viewHolder.rlCancleOrder.setVisibility(View.VISIBLE);
                    viewHolder.rlTradingCompleteOrder.setVisibility(View.GONE);
                    viewHolder.rlDeleteOrder.setVisibility(View.GONE);
                    break;
                case Constant.TWO:
                    //已付款
                    stateShow = Constant.PAY;
                    viewHolder.rlCancleOrder.setVisibility(View.GONE);
                    viewHolder.rlDeleteOrder.setVisibility(View.GONE);
                    viewHolder.rlTradingCompleteOrder.setVisibility(View.GONE);
                    viewHolder.tvEvaluationOrder.setVisibility(View.VISIBLE);
                    viewHolder.tvDeleteOrder.setVisibility(View.VISIBLE);
                    viewHolder.tvLookDistribution.setVisibility(View.VISIBLE);
                    break;
                case Constant.THREE:
                    //待发货
                    stateShow = Constant.WAITE_SEND_GOOD;
                    viewHolder.rlCancleOrder.setVisibility(View.GONE);
                    viewHolder.rlTradingCompleteOrder.setVisibility(View.VISIBLE);
                    viewHolder.rlDeleteOrder.setVisibility(View.GONE);
                    viewHolder.tvCountersignOrder.setVisibility(View.GONE);
                    break;
                case Constant.FOUR:
                    //待收货
                    stateShow = Constant.WAITE_GET_GOOD;
                    viewHolder.rlCancleOrder.setVisibility(View.GONE);
                    viewHolder.rlTradingCompleteOrder.setVisibility(View.VISIBLE);
                    viewHolder.rlDeleteOrder.setVisibility(View.GONE);
                    break;
                case Constant.FIVE:
                    //已完成
                    stateShow = Constant.HAS_COMPLET;
                    viewHolder.rlCancleOrder.setVisibility(View.GONE);
                    viewHolder.rlTradingCompleteOrder.setVisibility(View.GONE);
                    viewHolder.rlDeleteOrder.setVisibility(View.VISIBLE);
                    break;
                case Constant.SIX:
                    //已取消
                    stateShow = Constant.HAS_CANCLE;
                    viewHolder.tvEvaluationOrder.setVisibility(View.GONE);
                    viewHolder.rlCancleOrder.setVisibility(View.GONE);
                    viewHolder.rlDeleteOrder.setVisibility(View.VISIBLE);
                    viewHolder.rlTradingCompleteOrder.setVisibility(View.GONE);
                    break;
                case Constant.SEVEN:
                    //待收货
                    stateShow = Constant.HAS_COMPLET;
                    viewHolder.rlCancleOrder.setVisibility(View.GONE);
                    viewHolder.rlTradingCompleteOrder.setVisibility(View.GONE);
                    viewHolder.rlDeleteOrder.setVisibility(View.VISIBLE);
                    viewHolder.tvEvaluationOrder.setVisibility(View.GONE);

            }
            viewHolder.tvSumpermarketState.setText(stateShow);
            //展示购买的商品
            viewHolder.mlvOrderDetails.setAdapter(new GoodsAdapter(getActivity(),
                    getItem(position).getAppOrderGoodsList()));

            //显示商品件数
            if (getItem(position).getAppOrderGoodsList().size() == 0) {
                viewHolder.tvShowGoodNumber.setText(String.valueOf(Constant.ZERO));
            } else {
                viewHolder.tvShowGoodNumber.setText(String.valueOf(getItem(position).getAppOrderGoodsList().size()));
            }
            //显示总的钱数
            viewHolder.tvShowGoodMoney.setText(getItem(position).getPayFee().toString());
            //显示运费
            if (getItem(position).getFreight() != null) {
                viewHolder.tvShowFreight.setText(getItem(position).getFreight().toString());
            }


        /*订单操作监听*/
            //评价订单
            viewHolder.tvEvaluationOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bundle.putString(Constant.EVALUATION_ORDER, getItem(position).getId());
                    startActivity(EvaluationActivity.class, bundle);
                }
            });
            //配送查看
            viewHolder.tvLookDistribution.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bundle.putString(Constant.LOOK_DISTRIBUTION, getItem(position).getId());
                    startActivity(LookDistributionActivity.class, bundle);
                }
            });


            //删除订单（可以加一个dialog）
            viewHolder.tvDeleteOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    builder.setMessage("你真的要删除订单？");

                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            deleteAndCancelOrdersModel.deleteAndCancelOrder(HttpConstant.PATH_DELETE_ORDER,
                                    getItem(position).getId(), position);
                            deleteAndCancelOrdersModel.setDeleteOrderInterface(new DeleteAndCancelOrdersModel.DeleteAndCancelOrderInterface() {
                                @Override
                                public void deleteAndCancelOrderSuccess(String obj, int position) {
                                    //订单取消是我的页面刷新
                                    AppApplication.putBoolean("person_fragment_refresh", true);
                                    deleteAndCancelSuccessInterface.deleteAndCancelSuccess(position, true);

                                }

                                @Override
                                public void deleteAndCancelOrderSuccess(String msg) {
                                    //删除失败
                                    LogUtils.error(OrderAdapter.class, msg);
                                    showToast(msg);
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
            });


            //取消订单
            viewHolder.tvCancelOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("你真的要取消订单？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            deleteAndCancelOrdersModel.deleteAndCancelOrder(HttpConstant.PATH_CANCEL_ORDER,
                                    getItem(position).getId(), position);

                            deleteAndCancelOrdersModel.setDeleteOrderInterface(new DeleteAndCancelOrdersModel.DeleteAndCancelOrderInterface() {
                                @Override
                                public void deleteAndCancelOrderSuccess(String obj, int position) {
                                    //订单取消是我的页面刷新
                                    AppApplication.putBoolean("person_fragment_refresh", true);
                                    deleteAndCancelSuccessInterface.deleteAndCancelSuccess(position, false);
                                }

                                @Override
                                public void deleteAndCancelOrderSuccess(String msg) {
                                    //取消失败
                                    LogUtils.error(OrderAdapter.class, msg);
                                    showToast(msg);
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
            });

            //确认收货
            viewHolder.tvCountersignOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*增加确认收货的对话框*/
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    //设置对话框图标，可以使用自己的图片，Android本身也提供了一些图标供我们使用
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    //设置对话框标题
                    builder.setTitle("注意");
                    //设置对话框内的文本
                    builder.setMessage("是否确认收货?");
                    //设置确定按钮，并给按钮设置一个点击侦听，注意这个OnClickListener使用的是DialogInterface类里的一个内部接口
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteAndCancelOrdersModel.deleteAndCancelOrder(HttpConstant.PATH_AFFIRM_ORDER,
                                    getItem(position).getId(), position);

                            deleteAndCancelOrdersModel.setDeleteOrderInterface(new DeleteAndCancelOrdersModel.DeleteAndCancelOrderInterface() {
                                @Override
                                public void deleteAndCancelOrderSuccess(String obj, int position) {
                                    //订单取消是我的页面刷新
                                    AppApplication.putBoolean("person_fragment_refresh", true);
                                    deleteAndCancelSuccessInterface.deleteAndCancelSuccess(position, false);
                                }

                                @Override
                                public void deleteAndCancelOrderSuccess(String msg) {
                                    LogUtils.error(OrderAdapter.class, msg);
                                    showToast(msg);
                                }
                            });
                            dialog.dismiss();

                        }
                    });
                    //设置取消按钮
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 执行点击取消按钮的业务逻辑
                            dialog.dismiss();
                        }
                    });
                    //使用builder创建出对话框对象
                    AlertDialog dialog = builder.create();
                    //显示对话框
                    dialog.show();
                }
            });

            //立即支付
            viewHolder.tvPayOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String[] id = new String[1];
                    id[0] = getItem(position).getId();
                /*bdPrice = new BigDecimal(Double.valueOf(0.00));
                bdPrice = getItem(position).getTotalPrice();
                String[] priceStr = bdPrice.toString().split("\\.");
                bundle.putStringArray(Constant.PAY_PRICE, priceStr);
                bundle.putStringArray(Constant.PAY_ORDERS, id);
                bundle.putInt(Constant.PAY_LENGTH, id.length);
                startActivity(LookDistributionActivity.class, bundle);*/
                    /**
                     * 弹出对话框，让客户选择使用微信还是支付宝支付
                     */
                    AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                    //设置对话框的图标
                    builder.setIcon(R.drawable.wb_list_search_icon);
                    //设置对话框的标题
                    builder.setTitle("选择支付平台");
                    builder.setSingleChoiceItems(R.array.hobby, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            LogUtils.error(OrderAdapter.class, "which" + which);
                            if (which == 0) {
                                /*支付宝*/
                                pay = 1;
                            } else if (which == 1) {
                                /*微信*/
                                pay = 2;
                            }
                        }
                    });
                    //添加一个确定按钮
                    builder.setPositiveButton(" 支 付 ", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (pay == 1) {
                                /*支付宝*/
                                payModel.getPayResult(id);
                                payModel.setPayInterface(new PayModel.PayInterface() {
                                    @Override
                                    public void payResult(String result) {
                                        aliPayUtils.payGood(result, new AliPayUtils.AliPlayInterface() {
                                            @Override
                                            public void fail() {
                                                LogUtils.error(OrderAdapter.class, "失败失败失败失败失败失败失败失败失败失败");
                                            }

                                            @Override
                                            public void success(String result, HashMap<String, String> map) {
                                                //支付成功
                                                Log.e("setPayInterface", "支付结果：" + result);
                                                Log.e("setPayInterface", "支付集合：" + map.size());
                                                //订单取消是我的页面刷新
                                                AppApplication.putBoolean("person_fragment_refresh", true);
                                                deleteAndCancelSuccessInterface.deleteAndCancelSuccess(position, false);
                                            }

                                            @Override
                                            public void ensureing() {

                                            }
                                        });
                                    }
                                });
                            } else if (pay == 2) {
                                /*微信*/
                                payModel.weiXinPay(id);
                                payModel.setWeiXinInterface(new PayModel.WeiXinInterface() {
                                    @Override
                                    public void weiXinPay(WeiXinReq.ObjBean result) {
                                        /*注册微信*/
                                        IWXAPI api = WXAPIFactory.createWXAPI(getContext(), Constant.WECHAT_APPID, true);
                                        api.registerApp(Constant.WECHAT_APPID);
                                        PayReq payReq = new PayReq();
                                        payReq.appId = result.getAppid();
                                        payReq.partnerId = result.getPartnerid();
                                        payReq.prepayId = result.getPrepayid();
                                        payReq.nonceStr = result.getNoncestr();
                                        payReq.packageValue = "Sign=WXPay";
                                        payReq.timeStamp = result.getTimestamp();
                                        payReq.sign = result.getSign();
                                        api.sendReq(payReq);
                                        //订单取消是我的页面刷新
                                        AppApplication.putBoolean("person_fragment_refresh", true);
                                        deleteAndCancelSuccessInterface.deleteAndCancelSuccess(position, false);
                                    }
                                });

                            }
                        }
                    });
                    //创建一个单选按钮对话框
                    Dialog dialog = builder.create();
                    dialog.show();
                }
            });

            //跳转到订单详细页面
            viewHolder.mlvOrderDetails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                    bundle.putString(Constant.ORDER_GOOD_ID, getItem(position).getId());
                    bundle.putInt(Constant.DELETE_ORDER, position);
                    startActivity(OrderDetailedActivity.class, bundle);
                /*销毁页面*/
                    OrdersActivity activity = (OrdersActivity) getActivity();
                    activity.finish();


                }
            });
        }

        return convertView;
    }


    //region 删除成功回调接口
    private DeleteAndCancelSuccessInterface deleteAndCancelSuccessInterface;

    public void setDeleteSuccessInterface(DeleteAndCancelSuccessInterface deleteAndCancelSuccessInterface) {
        this.deleteAndCancelSuccessInterface = deleteAndCancelSuccessInterface;
    }

    /*删除成功回调*/
    public interface DeleteAndCancelSuccessInterface {
        void deleteAndCancelSuccess(int position, boolean isDelete);
    }
    //endregion

    /**
     * ViewHolder类用来解决滑动出现的紊乱问题
     */
    private class ViewHolder {
        private TextView tvSumpermarketName;//超市名称
        private TextView tvSumpermarketState;//订单状态
        private MyListView mlvOrderDetails;//购买的商品
        private TextView tvShowGoodNumber;//商品数量
        private TextView tvShowGoodMoney;//商品总钱数
        private TextView tvShowFreight;//运费

        /*订单的操作*/
        private TextView tvEvaluationOrder;//评价订单
        private TextView tvLookDistribution;//配送查看
        private TextView tvDeleteOrder;//删除订单
        private TextView tvCancelOrder;//取消订单
        private TextView tvPayOrder;//立即支付
        private TextView tvCountersignOrder;//确认收货

        /*进入该超市*/
        private LinearLayout llSumpermarketState;

        /*显示订单的操作*/
        private RelativeLayout rlCancleOrder;//未付款可以取消订单
        private RelativeLayout rlTradingCompleteOrder;//其他显示布局
        private RelativeLayout rlDeleteOrder;//删除订单布局
    }


}
