package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.entity.IntegralGoods;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.utils.ImageOptions;

/**
 * Created by liuGang on 2016/8/16 0016.
 */
public class IntegralGoodsAdapter extends CommonAdapter<IntegralGoods> {
    /*图片加载类*/
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public IntegralGoodsAdapter(Activity activity, List<IntegralGoods> list) {
        super(activity, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IntegralViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.item_gv_sales_home, parent, false);
            viewHolder = new IntegralViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (IntegralViewHolder) convertView.getTag();
        }

        /*复用控件 隐藏和显示*/
        viewHolder.llItemSales.setVisibility(View.GONE);
        viewHolder.llItemIntegral.setVisibility(View.VISIBLE);

        /*设置商品名*/
        viewHolder.tvGoodsName.setText(getItem(position).getGoodsName());
        /*设置积分数*/
        viewHolder.tvIntegralNum.setText(getItem(position).getConsumption().toString());
        //加载图片
        imageLoader.displayImage(HttpConstant.ROOT_PATH + getItem(position).getPicUrl(), viewHolder.ivGoods, ImageOptions.getDefaultOptions());

        return convertView;
    }


    /**
     * 积分商品中的viewHandler类
     */
    public class IntegralViewHolder {
        LinearLayout llItemSales;
        LinearLayout llItemIntegral;
        ImageView ivGoods;
        TextView tvGoodsName;
        TextView tvIntegralNum;

        public IntegralViewHolder(View convertView) {
            llItemSales = get(convertView, R.id.ll_item_sales);
            llItemIntegral = get(convertView, R.id.ll_item_integral);
            ivGoods = get(convertView, R.id.img_sales_home);
            tvGoodsName = get(convertView, R.id.tv_name_sales_home);
            tvIntegralNum = get(convertView, R.id.tv_item_integral);
        }
    }
}
