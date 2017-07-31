package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.trinea.android.common.adapter.CommonAdapter;
import cn.trinea.android.common.util.LogUtils;
import cn.trinea.android.common.util.ToastUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.entity.AffirmOrderMarket;
import yunju.com.huiqitian.entity.AffirmOrderVouchers;
import yunju.com.huiqitian.entity.CartGoods;
import yunju.com.huiqitian.vm.orders.view.AffirmOrderActivity;
import yunju.com.huiqitian.vm.widget.PopShowUtils;

/**
 * Created by liuGang on 2016/8/10 0010.
 */
public class AffirmOrderAdapter extends CommonAdapter<AffirmOrderMarket> {

    public void setAffirmOrderInterface(AffirmOrderInterface affirmOrderInterface) {
        this.affirmOrderInterface = affirmOrderInterface;
    }

    private AffirmOrderInterface affirmOrderInterface;

    private Activity activity;


    /*checkBox实现单选框，存放的map集合*/
    private Map<Integer, Boolean> isSelected;
    /*点击选中的单选框，保存在list集合中*/
    private List beSelectedData = new ArrayList();



    /*多家超市购物车中选择代金券存放选择的代金券id*/
    private List voucherListId = new ArrayList();
    /*多家超市购物车中选择代金券存放选择的代金券的marketId*/
    private List voucherListMarketId = new ArrayList();
    /*多家超市购物车中选择代金券存放选择代金券的position*/
    private List voucherListPosition = new ArrayList();
    /*多家超市购物车中选择代金券存放选择代金券的优惠多少钱*/
    private List voucherListMoney = new ArrayList();

    private MoneyCallBackLister moneyCallBackLister;

    public interface MoneyCallBackLister{
        void voucherMonet(List<BigDecimal> list);
    }

    public AffirmOrderAdapter(Activity activity, List<AffirmOrderMarket> list, List voucherListId, List voucherListMarketId, List voucherListPosition,MoneyCallBackLister moneyCallBackLister) {
        super(activity, list);
        this.activity = activity;
        this.voucherListId = voucherListId;
        this.voucherListMarketId = voucherListMarketId;
        this.voucherListPosition = voucherListPosition;
        this.moneyCallBackLister=moneyCallBackLister;
    }

    //定义成员变量mTouchItemPosition,用来记录手指触摸的EditText的位置
   /* private int mTouchItemPosition = -1;*/


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        /*判断是否面配送费*/
        int dispatchFeeType = 0;
    /*订单出事需支付金额，不加配送费和代金券*/
        BigDecimal pay = null;
        AOViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.item_affirm_order, parent, false);
            viewHolder = new AOViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (AOViewHolder) convertView.getTag();
        }

        /*商品购买金钱，作用于判断是否可使用代金券*/
        final double money;

        //遍历计算单个商超的所有产品个数和总价格
        BigDecimal bigDecimal = new BigDecimal(Double.valueOf(0.00));
        int titleNum = 0;
        for (CartGoods cartGoods : getItem(position).getAppCartGoodsList()) {
            BigDecimal b = new BigDecimal(Byte.valueOf(cartGoods.getQty()));
            bigDecimal = ((cartGoods.getRetailPrice()).multiply(b)).add(bigDecimal);
            pay = bigDecimal;
            titleNum += cartGoods.getQty();
        }


        //设置单个商超的所有产品合计价格
        //String[] priceStr = bigDecimal.toString().split("\\.");

        //设置单个商超的所有产品个数
        viewHolder.tvAffirmGoodsNum.setText(String.valueOf(titleNum));
        //设置其他相关属性
        viewHolder.tvMarketName.setText(getItem(position).getMarketName());

        //购买额不足加运费
        if (bigDecimal.compareTo(getItem(position).getFreeDispatchLimit()) == -1) {
            LogUtils.error(AffirmOrderAdapter.class, "有配送费有配送费有配送费有配送费有配送费有配送费有配送费" + getItem(position).getAppCartGoodsList().get(0).getName() + "position=" + position);
            viewHolder.tvSendFee.setText(String.valueOf(getItem(position).getDispatchFee()) + "元");
            //affirmOrderInterface.sendFee(getItem(position).getDispatchFee());
            String[] priceStr = (bigDecimal.add(getItem(position).getDispatchFee())).toString().split("\\.");
            viewHolder.tvBigPrice.setText(priceStr[0]);
            viewHolder.tvSmallPrice.setText("." + priceStr[1]);
            double a = Double.parseDouble(viewHolder.tvBigPrice.getText().toString());
            double b = Double.parseDouble(getItem(position).getDispatchFee().toString());
            money = a - b;
            dispatchFeeType = 1;

        } else {
            //满额免运费
            LogUtils.error(AffirmOrderAdapter.class, "满额面配送费" + getItem(position).getAppCartGoodsList().get(0).getName() + "position=" + position);
            viewHolder.tvSendFee.setText("0.00元");
            String[] priceStr = bigDecimal.toString().split("\\.");
            viewHolder.tvBigPrice.setText(priceStr[0]);
            viewHolder.tvSmallPrice.setText("." + priceStr[1]);
            money = Integer.parseInt(viewHolder.tvBigPrice.getText().toString());
        }


        //LogUtils.error(AffirmOrderAdapter.class, "ama" + getItem(position).getMarketName());
        final AffirmGoodsAdapter affirmGoodsAdapter = new AffirmGoodsAdapter(activity, getItem(position).getAppCartGoodsList());
        viewHolder.lvItem.setAdapter(affirmGoodsAdapter);


        //给用户留言的editText设置获取焦点
        /*viewHolder.EtAffirmLeaveMsg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mTouchItemPosition = position;
                return false;
            }
        });


        //判断编辑的是那个item中的留言editText
        if (mTouchItemPosition != -1 && mTouchItemPosition == position) {
            viewHolder.EtAffirmLeaveMsg.requestFocus();
            //imm.hideSoftInputFromWindow(viewHolder.EtAffirmLeaveMsg.getWindowToken(), 0); //强制隐藏键盘
            // imm.showSoftInput(viewHolder.EtAffirmLeaveMsg, InputMethodManager.SHOW_FORCED);
        } else {
            viewHolder.EtAffirmLeaveMsg.clearFocus();
        }*/

        /*优惠券集合*/
        final List<AffirmOrderVouchers> vouchersChoList = new ArrayList<>();
        for (AffirmOrderVouchers voucher : getItem(position).getAppVouchers()) {
            vouchersChoList.add(voucher);
        }


        if (!vouchersChoList.isEmpty()) {
            viewHolder.rlAffirmCoupon.setVisibility(View.VISIBLE);
            LogUtils.error(AffirmOrderActivity.class, "代金券数量........................." + vouchersChoList.size());
        }
        final AOViewHolder finalViewHolder = viewHolder;
        final AOViewHolder finalViewHolder1 = viewHolder;
        final int finalDispatchFeeType = dispatchFeeType;
        final BigDecimal finalPay = pay;
        viewHolder.ivAffirmChoCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.pop_voucher, null);
                ListView lvPopVoucher = (ListView) view.findViewById(R.id.lv_pop_voucher);
                Button btnPopVoucherFinish = (Button) view.findViewById(R.id.btn_popVoucher_finish);

                /*设置优惠券列表的adapter*/
                if (vouchersChoList == null || vouchersChoList.size() == 0)
                    return;
                if (isSelected != null)
                    isSelected = null;
                /*存放checkBox选中的标示*/
                isSelected = new HashMap<Integer, Boolean>();
                for (int i = 0; i < vouchersChoList.size(); i++) {
                    isSelected.put(i, false);
                }
                // 清除已经选择的项
                if (beSelectedData.size() > 0) {
                    beSelectedData.clear();
                }

                final PopVoucherAdapter popVoucherAdapter = new PopVoucherAdapter(activity, vouchersChoList, isSelected, beSelectedData, money, voucherListId, voucherListMarketId, getItem(position).getMarketId(), voucherListPosition, voucherListMoney);
                lvPopVoucher.setAdapter(popVoucherAdapter);
                lvPopVoucher.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                popVoucherAdapter.notifyDataSetChanged();

                PopShowUtils.showPopwindow(view, activity.getWindow(), finalViewHolder.ivAffirmChoCoupon);

                btnPopVoucherFinish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopShowUtils.closePopWindowPage();
                        if (finalDispatchFeeType == 0) {
                            if(voucherListMoney.size()==0){
                                showToast("没有选择任何代金券");
                            }else {
                                BigDecimal practicalPay = finalPay.subtract((BigDecimal) voucherListMoney.get(voucherListMoney.size() - 1));
                                String[] str = practicalPay.toString().split("\\.");
                                LogUtils.error(AffirmOrderAdapter.class, str[0] + ".........." + str[1]);
                                finalViewHolder.tvBigPrice.setText(str[0]);
                                finalViewHolder.tvSmallPrice.setText("." + str[1]);
                                moneyCallBackLister.voucherMonet(voucherListMoney);
                            }

                        } else if (finalDispatchFeeType == 1) {
                            if(voucherListMoney.size()==0){
                                /*代表着没有代金券可以选择，点击消失对话框*/
                                ToastUtils.show(activity,"没有代金券可以选择");
                            }else {
                                BigDecimal practicalPay =finalPay.add(getItem(position).getDispatchFee()).subtract((BigDecimal) voucherListMoney.get(voucherListMoney.size() - 1));
                                String[] str = practicalPay.toString().split("\\.");
                                LogUtils.error(AffirmOrderAdapter.class, str[0] + ".........." + str[1]);
                                finalViewHolder.tvBigPrice.setText(str[0]);
                                finalViewHolder.tvSmallPrice.setText("." + str[1]);
                                moneyCallBackLister.voucherMonet(voucherListMoney);
                            }
                        }



                    }
                });
            }
        });


        return convertView;
    }


    //确认订单中的viewHolder
    public class AOViewHolder {
        TextView tvSendFee;
        TextView tvMarketName;
        ListView lvItem;
        /*EditText EtAffirmLeaveMsg;*/
        ImageView ivAffirmChoCoupon;
        /*LinearLayout llAffirmOrderMsg;*/
        TextView tvAffirmGoodsNum;
        TextView tvBigPrice;
        TextView tvSmallPrice;
        RelativeLayout rlAffirmCoupon;

        public AOViewHolder(View convertView) {
            //初始化填充布局中的控件
            tvMarketName = get(convertView, R.id.tv_affirmOrder_marketName);
            lvItem = get(convertView, R.id.lv_item_affirmOrder);
            /* 原先的留言，现在移植最下面 EtAffirmLeaveMsg = get(convertView, R.id.et_affirm_leaveMsg);*/
            ivAffirmChoCoupon = get(convertView, R.id.img_affirm_chooseCoupon);
            /* 原先的留言，现在移植最下面  llAffirmOrderMsg = get(convertView, R.id.ll_affirmOrder_Msg);*/
            tvAffirmGoodsNum = get(convertView, R.id.tv_affirm_goodsNum);
            tvBigPrice = get(convertView, R.id.tv_affirm_bigPrice);
            tvSmallPrice = get(convertView, R.id.tv_affirm_smallPrice);
            rlAffirmCoupon = get(convertView, R.id.rl_affirm_coupon);
            tvSendFee = get(convertView, R.id.tv_affirm_sendFee);

        }
    }


    public interface AffirmOrderInterface {
        void sendFee(BigDecimal sendCost);
    }
}
