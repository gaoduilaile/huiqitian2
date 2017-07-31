package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.entity.GoodsInfo;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.utils.ImageOptions;

/**
 * Created by Administrator on 2017/1/18 0018.
 */
public class VoucherGoodsAdapter extends CommonAdapter<GoodsInfo>{

    /*图片加载类*/
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public VoucherGoodsAdapter(Activity activity, List<GoodsInfo> list) {
        super(activity, list);
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView = getLayoutInflater().inflate(R.layout.item_voucher_goods, parent, false);
            viewHolder.imgVoucherGoods=findView(convertView, R.id.img_voucher_goods);
            viewHolder.tvVoucherGoodsName = findView(convertView, R.id.tv_voucher_goods_name);
            viewHolder.tvVoucherGoodsMarketName = findView(convertView, R.id.tv_voucher_goods_market_name);
            viewHolder.tvVoucherGoodsMoney=findView(convertView,R.id.tv_voucher_goods_money);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tvVoucherGoodsName.setText(getItem(position).getName());
        viewHolder.tvVoucherGoodsMarketName.setText(getItem(position).getMarketName());
        viewHolder.tvVoucherGoodsMoney.setText(getItem(position).getRetailPrice().toString());
        imageLoader.displayImage(HttpConstant.ROOT_PATH + getItem(position).getPicUrl(), viewHolder.imgVoucherGoods, ImageOptions.getDefaultOptions());//加载图片
        return convertView;
    }

    public class ViewHolder {
        private ImageView imgVoucherGoods;
        private TextView tvVoucherGoodsName;
        private TextView tvVoucherGoodsMarketName;
        private TextView tvVoucherGoodsMoney;
    }
}
