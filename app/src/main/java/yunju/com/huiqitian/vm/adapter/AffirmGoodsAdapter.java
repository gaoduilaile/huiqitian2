package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;
import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.entity.CartGoods;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.utils.ImageOptions;

/**
 * Created by liuGang on 2016/8/8 0008.
 */
public class AffirmGoodsAdapter extends CommonAdapter<CartGoods> {
    /*图片加载类*/
    private ImageLoader imageLoader = ImageLoader.getInstance();
    //构造函数传递过来的activity
    private Activity activity;

    public AffirmGoodsAdapter(Activity activity, List<CartGoods> list) {
        super(activity, list);
        this.activity=activity;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        GoodsViewHolder holder = null;
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.item_affirm_goods, parent, false);
            holder = new GoodsViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GoodsViewHolder) convertView.getTag();
        }

        //设置数据到控件上
        holder.tvAffirmGoodsName.setText(getItem(position).getName());
        holder.tvAffirmPrice.setText(getItem(position).getRetailPrice().toString());
        holder.tvAffirmNum.setText(getItem(position).getQty().toString());
        //加载图片
        imageLoader.displayImage(HttpConstant.ROOT_PATH + getItem(position).getPicUrl(), holder.ivAffirmGoods, ImageOptions.getDefaultOptions());


        return convertView;
    }

    //确认订单中ViewHolder
    public class GoodsViewHolder {
        ImageView ivAffirmGoods;
        TextView tvAffirmGoodsName;
        TextView tvAffirmPrice;
        TextView tvAffirmNum;

        public GoodsViewHolder(View convertView) {
            //初始化填充布局中的所有控件
            ivAffirmGoods = get(convertView, R.id.iv_affirm_item_goods);
            tvAffirmGoodsName = get(convertView, R.id.tv_affirm_item_goodsName);
            tvAffirmPrice = get(convertView, R.id.tv_affirm_item_price);
            tvAffirmNum = get(convertView, R.id.tv_affirm_item_goodsNum);
        }

    }


}
