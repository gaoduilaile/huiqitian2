package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.entity.ShopInfo;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.utils.ImageOptions;

/**
 * Created by gao on 2016/8/11 0011.
 */
public class ShopAdapter extends CommonAdapter<ShopInfo> {

    /*图片加载类*/
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public ShopAdapter(Activity activity, List<ShopInfo> list) {
        super(activity, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getLayoutInflater().inflate(R.layout.item_shop, parent, false);
            holder.ivItemShop = get(convertView, R.id.iv_item_shop);
            holder.tvItemShopName = get(convertView, R.id.tv_item_shop_name);
            holder.rbShopStar = get(convertView, R.id.rb_shop_star);
            holder.tvItemShopStartCount = get(convertView, R.id.tv_item_shop_start_count);
            holder.tvShopDistance = get(convertView, R.id.tv_shop_distance);
            holder.tvItemMoneySend = get(convertView, R.id.tv_item_money_send);
            holder.tvItemShopSendMsg = get(convertView, R.id.tv_item_shop_send_msg);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        /*赋值*/
          /*加载商品图片*/
        imageLoader.displayImage(HttpConstant.ROOT_PATH + getItem(position).getPicUrl(),
                holder.ivItemShop, ImageOptions.getDefaultOptions());
        holder.tvItemShopName.setText(getItem(position).getName());
        holder.tvItemShopStartCount.setText(getItem(position).getScore() + "");
        holder.tvShopDistance.setText(getItem(position).getDistance() + "千米");
        holder.tvItemMoneySend.setText(getItem(position).getDispatchFee() + "");
        holder.tvItemShopSendMsg.setText("订单满" + getItem(position).getFreeDispatchLimit() + "免配送费");


        if (getItem(position).getScoreStar() != null) {
            float star = Float.parseFloat(getItem(position).getScoreStar().toString());
            holder.rbShopStar.setRating(star);
        } else {
            holder.rbShopStar.setRating(0);
        }

        return convertView;
    }

    public class ViewHolder {
        private ImageView ivItemShop;//店铺图片
        private TextView tvItemShopName;//店铺名称
        private RatingBar rbShopStar;//星级
        private TextView tvItemShopStartCount;//心级数
        private TextView tvShopDistance;//距离
        private TextView tvItemMoneySend;//配送费
        private TextView tvItemShopSendMsg;//配送信息
        private TextView tvItemMoneySendTwo;
    }
}
