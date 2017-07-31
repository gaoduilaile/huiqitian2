package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.entity.IntegralGoodsDuihuan;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.utils.ImageOptions;
import yunju.com.huiqitian.vm.integral.view.TwoDimensionCodeActivity;

/**
 * Created by liuGang on 2016/8/16 0016.
 */
public class IntegralGoodsAdapter2 extends CommonAdapter<IntegralGoodsDuihuan.IntegralGoods2> {
    /*图片加载类*/
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public IntegralGoodsAdapter2(Activity activity, List<IntegralGoodsDuihuan.IntegralGoods2> list) {
        super(activity, list);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        IntegralViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.item_gv_sales_home2, parent, false);
            viewHolder = new IntegralViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (IntegralViewHolder) convertView.getTag();
        }
        /*设置商品名*/
        viewHolder.tvGoodsName.setText(getItem(position).getMotionGoodsName());
        //加载图片
        imageLoader.displayImage(HttpConstant.ROOT_PATH + getItem(position).getPicUrl(), viewHolder.ivGoods, ImageOptions.getDefaultOptions());
        if (getItem(position).getExchangeState() == 1) {
            /*设置有效时间至*/
            viewHolder.tvStExchangeExpireTime.setText(getItem(position).getStExchangeExpireTime());
            /* 立即领取*/
            viewHolder.tvSoonTakeGoods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getContext(),TwoDimensionCodeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(getItem(position).getId()));
                    intent.putExtras(bundle);
                    /*startActivity(TwoDimensionCodeActivity.class, bundle);*/
                    getActivity().startActivityForResult(intent,0);
                }
            });
        } else if (getItem(position).getExchangeState() == 2) {
            viewHolder.tvSoonTakeGoods.setVisibility(View.GONE);
            viewHolder.llTakeAddress.setVisibility(View.VISIBLE);
            viewHolder.tvTitleText.setText("领取时间：");
            /*领取时间*/
            viewHolder.tvStExchangeExpireTime.setText(getItem(position).getStExchangeTime());
            /*地址*/
            viewHolder.tvTakeAddress.setText(getItem(position).getMarketName());
        } else if (getItem(position).getExchangeState() == 3) {
            viewHolder.tvSoonTakeGoods.setVisibility(View.GONE);
        }

        return convertView;
    }

    /**
     * 积分商品中的viewHandler类
     */
    public class IntegralViewHolder {
        LinearLayout llItemSales;
        LinearLayout llItemIntegral;
        LinearLayout llTakeAddress;
        ImageView ivGoods;
        TextView tvGoodsName;
        TextView tvStExchangeExpireTime;
        TextView tvSoonTakeGoods;
        TextView tvTakeAddress;
        TextView tvTitleText;

        public IntegralViewHolder(View convertView) {
            llItemSales = get(convertView, R.id.ll_item_sales);
            llItemIntegral = get(convertView, R.id.ll_item_integral);
            llTakeAddress = get(convertView, R.id.ll_take_address);
            ivGoods = get(convertView, R.id.img_sales_home);
            tvGoodsName = get(convertView, R.id.tv_name_sales_home);
            tvStExchangeExpireTime = get(convertView, R.id.tv_stExchangeTime);
            tvSoonTakeGoods = get(convertView, R.id.tv_soon_take_goods);
            tvTakeAddress = get(convertView, R.id.tv_take_address);
            tvTitleText = findView(convertView, R.id.tv_title_text);
        }
    }
}
