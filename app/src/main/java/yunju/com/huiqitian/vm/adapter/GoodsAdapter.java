package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.entity.Goods;
import yunju.com.huiqitian.utils.ImageOptions;

/**
 * Created by 张超群 on 2016-08-08.
 * <p/>
 * 商品的适配器
 */
public class GoodsAdapter extends CommonAdapter<Goods> {

    /*图片加载类*/
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public GoodsAdapter(Activity activity, List<Goods> list) {
        super(activity, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            convertView = getLayoutInflater().inflate(R.layout.my_list_item, parent, false);
            viewHolder.ivMyListGood = findView(convertView, R.id.iv_my_list_good);
            viewHolder.tvMyListGoodDetails = findView(convertView, R.id.tv_my_list_good_details);
            viewHolder.tvMyListGoodPrice = findView(convertView, R.id.tv_my_list_good_price);
            viewHolder.tvMyListGoodNumber = findView(convertView, R.id.tv_my_list_good_number);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /*加载商品图片*/
        imageLoader.displayImage(HttpConstant.ROOT_PATH + getItem(position).getPicUrl(),
                viewHolder.ivMyListGood, ImageOptions.getDefaultOptions());
        /*商品名称*/
        if (!TextUtils.isEmpty(getItem(position).getName()))
            viewHolder.tvMyListGoodDetails.setText(getItem(position).getName());
        /*商品价格*/
        if (getItem(position).getRetailPrice() != null)
            viewHolder.tvMyListGoodPrice.setText(getItem(position).getRetailPrice().toString());
        /*商品数量*/
        if (getItem(position).getQty() != null)
            viewHolder.tvMyListGoodNumber.setText(getItem(position).getQty().toString());
        return convertView;
    }

    /**
     * ViewHolder类防止加载紊乱
     */
    private class ViewHolder {
        private ImageView ivMyListGood;//商品图片
        private TextView tvMyListGoodDetails;//商品介绍
        private TextView tvMyListGoodPrice;//商品价格
        private TextView tvMyListGoodNumber;//商品数量
    }
}
