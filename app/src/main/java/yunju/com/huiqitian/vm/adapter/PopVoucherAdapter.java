package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.trinea.android.common.adapter.CommonAdapter;
import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.entity.AffirmOrderVouchers;
import yunju.com.huiqitian.utils.MyUtils;

/**
 * Created by Administrator on 2016/8/17 0017.
 */
public class PopVoucherAdapter extends CommonAdapter<AffirmOrderVouchers> {

    private Map<Integer, Boolean> isSelected;
    private final List beSelectedData;
    /*商品总价钱*/
    private double money;

    /*多家超市购物车中选择代金券存放选择的代金券id*/
    private List voucherListId = new ArrayList();
    /*多家超市购物车中选择代金券存放选择的代金券的marketId*/
    private List voucherListMarketId = new ArrayList();
    /*多家超市购物车中选择代金券存放选择代金券的position*/
    private List voucherListPosition=new ArrayList();
    /*多家超市购物车中选择代金券存放选择代金券的优惠多少钱*/
    private List voucherListMoney=new ArrayList();
    /*超市marketId*/
    private int marketId;
    /*第一次进来voucherListId集合的长度*/
    private int length=0;
    /*判断是否是重新选择代金券*/
    private boolean overType=true;
    /*第二次进来重选代金券时，用户之前选择的代金券在存放集合的位置*/
    private int savePosition=100000;
    /*第二次进来重新选代金券，用户之前选择代金券是该代金券的位置position*/
    private int voucherPosition=10000;
    private int a=1;


    public PopVoucherAdapter(Activity activity, List<AffirmOrderVouchers> list, Map<Integer, Boolean> isSelected, List beSelectedData, double money, List voucherListId, List voucherListMarketId, int marketId,List voucherListPosition,List voucherListMoney) {
        super(activity, list);
        this.isSelected = isSelected;
        this.beSelectedData = beSelectedData;
        this.money = money;
        this.voucherListId = voucherListId;
        this.voucherListMarketId = voucherListMarketId;
        this.marketId = marketId;
        length=voucherListId.size();
        LogUtils.error(PopVoucherAdapter.class,"金额金额金额金额金额金额金额金额金额金额金额金额"+money);
        this.voucherListPosition=voucherListPosition;
        this.voucherListMoney=voucherListMoney;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        PopViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.item_pop_voucher, parent, false);
            viewHolder = new PopViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PopViewHolder) convertView.getTag();
        }

        //优惠券设置属性值
        String[] priceStr = getItem(position).getAmount().toString().split("\\.");
        viewHolder.tvBigPrice.setText(priceStr[0]);
        viewHolder.tvSmallPrice.setText("." + priceStr[1] + "元");
        viewHolder.tvFulfilPrice.setText("订单满" + getItem(position).getAmountLimit().toString() + "元使用（不包含配送费）");
        viewHolder.tvUseDate.setText(getItem(position).getStrAffectDate() + "-" + getItem(position).getStrExpireDate());

        /*判断是否是对指定商品使用，还是所有商品使用*/
        if (getItem(position).getUseable().equals((byte) 1)) {
            viewHolder.cbVoucher.setEnabled(true);
            /*判断是否达到代金券使用金额*/
            if (money >= Double.parseDouble(getItem(position).getAmountLimit().toString())) {
                LogUtils.error(PopVoucherAdapter.class,"111111111111111111");
                viewHolder.cbVoucher.setEnabled(true);
            } else {
                LogUtils.error(PopVoucherAdapter.class,"222222222222222222222222222222");
                viewHolder.cbVoucher.setEnabled(false);
            }
        } else if (getItem(position).getUseable() == (byte) 0) {
            viewHolder.cbVoucher.setEnabled(false);
            LogUtils.error(PopVoucherAdapter.class, "333333333333333333333333");
        }

        if(a==1){
            /*判断是不是第二次进来重新选择代金券*/
            if(voucherListMarketId!=null){
                for(int i=0;i<voucherListMarketId.size();i++){
                    if((int)voucherListMarketId.get(i)==marketId){
                        LogUtils.error(PopVoucherAdapter.class,"较深的非农死就发生的风景货款纠纷和深刻和开发和水库将更好看精神科");
                        overType=false;
                        savePosition=i;
                        voucherPosition= (int) voucherListPosition.get(i);

                    }
                }
            }
            if(position==voucherPosition){
                LogUtils.error(PopVoucherAdapter.class,"走了么嘛走了么嘛走了么嘛走了么嘛走了么嘛走了么嘛走了么嘛");
                viewHolder.cbVoucher.setChecked(true);
            }
           a++;
        }

        if(overType){
            /*第一次进来选代金券*/
            if (voucherListId != null) {
                for (int i = 0; i < voucherListId.size(); i++) {
                    if ((int) voucherListId.get(i) == getItem(position).getId()) {
                        LogUtils.error(PopVoucherAdapter.class,"第一次进代金券第一次进代金券第一次进代金券第一次进代金券第一次进代金券");
                        viewHolder.cbVoucher.setEnabled(false);
                    }
                }
            }
        }else {
            /*第二次重新选代金券*/
            LogUtils.error(PopVoucherAdapter.class,"................................................");
            for (int i = 0; i < voucherListId.size(); i++) {
                if ((int) voucherListId.get(i) == getItem(position).getId()) {
                    if(position!=voucherPosition){
                        viewHolder.cbVoucher.setEnabled(false);
                        LogUtils.error(PopVoucherAdapter.class, "第二次进代金券111第二次进代金券111第二次进代金券111第二次进代金券111");
                    }else {
                        viewHolder.cbVoucher.setEnabled(true);
                        isSelected.put(voucherPosition,true);
                        LogUtils.error(PopVoucherAdapter.class, "第二次进代金券222第二次进代金券111第二次进代金券222第二次进代金券222");
                    }
                }
            }
        }



        viewHolder.cbVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getList() == null || getList().size() == 0)
                    return;
                if (isSelected != null)
                    isSelected = null;
                isSelected = new HashMap<Integer, Boolean>();
                for (int i = 0; i < getList().size(); i++) {
                    isSelected.put(i, false);
                }
                // 清除已经选择的项
                if (beSelectedData.size() > 0) {
                    beSelectedData.clear();
                }
                // 当前点击的CB
                boolean cu = !isSelected.get(position);
                // 先将所有的置为FALSE
                for (Integer p : isSelected.keySet()) {
                    isSelected.put(p, false);
                }
                // 再将当前选择CB的实际状态
                isSelected.put(position, cu);
                beSelectedData.clear();
                a=1;
                /*overType=true;*/
                notifyDataSetChanged();
                if (cu) {
                    beSelectedData.add(getItem(position));
                    MyUtils.saveVoucherId(getItem(position).getId() + "");
                    if (overType) {
                        LogUtils.error(PopVoucherAdapter.class,"第一次进来代金券点击");
                        /*进来的时候判断代金券集合长度，防止每次点击都添加*/
                        if (voucherListId.size() == length) {
                            /*用户进来选代金券第一次选择*/
                            voucherListId.add(getItem(position).getId());
                            voucherListMarketId.add(marketId);
                            voucherListPosition.add(position);
                            voucherListMoney.add(getItem(position).getAmount());
                        } else {
                            /*用户选择代金券在没有退出的情况下再次换张代金券*/

                            voucherListId.remove(length-1);
                            voucherListMarketId.remove(length-1);
                            voucherListPosition.remove(length-1);
                            voucherListMoney.remove(length-1);

                            voucherListId.add(getItem(position).getId());
                            voucherListMarketId.add(marketId);
                            voucherListPosition.add(position);
                            voucherListMoney.add(getItem(position).getAmount());

                        }
                    } else {
                        LogUtils.error(PopVoucherAdapter.class,"第二次进来代金券点击");
                        /*用户第二系重新选择代金券*/
                        voucherListId.remove(savePosition);
                        voucherListMarketId.remove(savePosition);
                        voucherListPosition.remove(savePosition);
                        voucherListMoney.remove(savePosition);

                        if (voucherListId.size() + 1 == length) {
                            voucherListId.add(getItem(position).getId());
                            voucherListMarketId.add(marketId);
                            voucherListPosition.add(position);
                            voucherListMoney.add(getItem(position).getAmount());
                        } else {
                            if(voucherListId.size()>0){
                                voucherListId.remove(voucherListId.size() - 1);
                                voucherListMarketId.remove(voucherListId.size() - 1);
                                voucherListPosition.remove(voucherListId.size() - 1);
                                voucherListMoney.remove(voucherListId.size() - 1);

                                voucherListId.add(getItem(position).getId());
                                voucherListMarketId.add(marketId);
                                voucherListPosition.add(position);
                                voucherListMoney.add(getItem(position).getAmount());
                            }else {
                                voucherListId.add(getItem(position).getId());
                                voucherListMarketId.add(marketId);
                                voucherListPosition.add(position);
                                voucherListMoney.add(getItem(position).getAmount());
                            }


                        }

                    }


                }
                LogUtils.error(PopVoucherAdapter.class,"888888888888888888888888888888888888888888888888");


            }
        });
            viewHolder.cbVoucher.setChecked(isSelected.get(position));

        return convertView;
    }


   /* //初始化优惠券布局中的控件
    TextView tvBigPrice=(TextView)view.findViewById(R.id.tv_voucherPop_bigPrice);
    TextView tvSmallPrice=(TextView)view.findViewById(R.id.tv_voucherPop_smallPrice);
    TextView tvFulfilPrice=(TextView)view.findViewById(R.id.tv_voucherPop_fulfilPrice);
    TextView tvUseDate=(TextView)view.findViewById(R.id.tv_voucherPop_useDate);

    if (!vouchersList.isEmpty()){
        //优惠券设置属性值
        String[] priceStr=vouchersList.get(0).getAmount().toString().split("\\.");
        tvBigPrice.setText(priceStr[0]);
        tvSmallPrice.setText("." +priceStr[1]+"元");
        tvFulfilPrice.setText("订单满"+vouchersList.get(0).getAmountLimit().toString()+"元使用（不包含配送费）");
        tvUseDate.setText(vouchersList.get(0).getStrAffectDate()+"-"+vouchersList.get(0).getStrExpireDate());
    }*/

    public class PopViewHolder {

        TextView tvBigPrice;
        TextView tvSmallPrice;
        TextView tvFulfilPrice;
        TextView tvUseDate;
        CheckBox cbVoucher;

        public PopViewHolder(View convertView) {
            tvBigPrice = get(convertView, R.id.tv_voucherPop_bigPrice);
            tvSmallPrice = get(convertView, R.id.tv_voucherPop_smallPrice);
            tvFulfilPrice = get(convertView, R.id.tv_voucherPop_fulfilPrice);
            tvUseDate = get(convertView, R.id.tv_voucherPop_useDate);
            cbVoucher = get(convertView, R.id.cb_voucher_item);

        }
    }


}
