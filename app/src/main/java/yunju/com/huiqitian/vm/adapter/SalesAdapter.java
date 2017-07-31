package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;
import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.entity.GoodsInfo;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.utils.ImageOptions;

/**
 * Created by 胡月 on 2016/8/12 0012.
 */
public class SalesAdapter extends CommonAdapter<GoodsInfo>{
    private ImageLoader imageLoader = ImageLoader.getInstance();
    public SalesAdapter(Activity activity, List<GoodsInfo> list) {
        super(activity, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView= getLayoutInflater().inflate(R.layout.item_gv_sales_home,parent,false);
            viewHolder.tvNameSalesHome=get(convertView, R.id.tv_name_sales_home);
            viewHolder.tvPriceSales=get(convertView, R.id.tv_price_sales);
            viewHolder.imgSalesHome=get(convertView, R.id.img_sales_home);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tvNameSalesHome.setText(getItem(position).getName());
        viewHolder.tvPriceSales.setText(getItem(position).getRetailPrice().toString());
        imageLoader.displayImage(HttpConstant.ROOT_PATH + getItem(position).getPicUrl(), viewHolder.imgSalesHome, ImageOptions.getDefaultOptions());
        return convertView;
    }
    //自定义的ViewHolder,持有每个Item的所有界面元素
    public class ViewHolder {
        private TextView tvNameSalesHome;//商品名称
        private TextView tvPriceSales;//商品价格
        private ImageView imgSalesHome;//商品图片
    }
}
