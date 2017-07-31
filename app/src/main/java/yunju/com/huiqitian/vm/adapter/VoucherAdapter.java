package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.entity.VoucherResp;

/**
 * Created by zcq on 2016-08-11.
 * <p>
 * 代金券适配器
 */
public class VoucherAdapter extends CommonAdapter<VoucherResp> {

    /*代表代金券状态*/
    private int status;

    public VoucherAdapter(Activity activity, List<VoucherResp> list, int status) {
        super(activity, list);
        this.status = status;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            convertView = getLayoutInflater().inflate(R.layout.list_voucher_item, parent, false);
            viewHolder.tvVoucherItemAddress = findView(convertView, R.id.tv_voucher_item_address);
            viewHolder.tvVoucherItemMoney = findView(convertView, R.id.tv_voucher_item_money);
            viewHolder.tvVoucherItemCanUse = findView(convertView, R.id.tv_voucher_item_can_use);
            viewHolder.tvVoucherItemUseTime = findView(convertView, R.id.tv_voucher_item_use_time);
            viewHolder.tvVoucherItemCanUseGood = findView(convertView, R.id.tv_voucher_item_can_use_good);
            viewHolder.ivVoucherItemYiguoqi = findView(convertView, R.id.iv_voucher_item_yiguoqi);
            viewHolder.ivVoucherItemYishiyong = findView(convertView, R.id.iv_voucher_item_yishiyong);
            viewHolder.tvVoucherRule=findView(convertView,R.id.tv_voucher_rule);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /*判断是对所有超市通用还是对指定超市使用*/
        if(getItem(position).getMarketName()!=null){
            viewHolder.tvVoucherItemAddress.setText("限"+getItem(position).getMarketName()+"使用");
        }else {
            viewHolder.tvVoucherItemAddress.setText("所有超市通用");
        }
        /*判断是对所有商品可用还是指定商品可用*/
        if(getItem(position).getAllGoods()==(byte)1){
            viewHolder.tvVoucherItemCanUseGood.setText("所有商品可用");
        }else {
            viewHolder.tvVoucherItemCanUseGood.setText("点击查看可使用券的商品");
        }

        //代金券数额
        viewHolder.tvVoucherItemMoney.setText(getItem(position).getAmount().toString());
        //使用条件
        viewHolder.tvVoucherItemCanUse.setText("满￥" + getItem(position).getAmountLimit() + "使用");
        //使用时间(起始到结束时间)
        viewHolder.tvVoucherItemUseTime.setText(getItem(position).getStrAffectDate() + " - " + getItem(position).getStrExpireDate());
        /*判断是否显示使用，过期和未使用*/
        switch (status) {
            case Constant.ONE:
                //未使用
                viewHolder.ivVoucherItemYiguoqi.setVisibility(View.GONE);
                viewHolder.ivVoucherItemYishiyong.setVisibility(View.GONE);
                break;
            case Constant.TWO:
                //已使用
                viewHolder.ivVoucherItemYiguoqi.setVisibility(View.GONE);
                viewHolder.ivVoucherItemYishiyong.setVisibility(View.VISIBLE);
                break;
            case Constant.THREE:
                //已过期
                viewHolder.ivVoucherItemYiguoqi.setVisibility(View.VISIBLE);
                viewHolder.ivVoucherItemYishiyong.setVisibility(View.GONE);
                break;
        }

        /*使用规则的点击查看*/
        viewHolder.tvVoucherRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                //设置对话框图标，可以使用自己的图片，Android本身也提供了一些图标供我们使用
                //设置对话框标题
                builder.setTitle("温馨提示：");
                //设置对话框内的文本
                builder.setMessage("每笔订单只能使用一张代金券，代金券所有解释权归总公司所有");
                //设置确定按钮，并给按钮设置一个点击侦听，注意这个OnClickListener使用的是DialogInterface类里的一个内部接口
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
        return convertView;
    }


    /**
     * 防止listview加载时紊乱
     */
    public class ViewHolder {
        private TextView tvVoucherItemAddress;//使用范围
        private TextView tvVoucherItemMoney;//代金券金额
        private TextView tvVoucherItemCanUse;//使用条件
        private TextView tvVoucherItemUseTime;//使用时间
        private TextView tvVoucherItemCanUseGood;//使用范围
        private ImageView ivVoucherItemYiguoqi;//显示已经过期
        private ImageView ivVoucherItemYishiyong;//显示已经使用
        private TextView tvVoucherRule;
    }
}
