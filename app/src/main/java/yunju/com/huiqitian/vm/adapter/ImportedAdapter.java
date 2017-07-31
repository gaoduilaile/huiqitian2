package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;
import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.entity.ClassifyInfo;
import yunju.com.huiqitian.entity.GoodsInfo;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.utils.ImageOptions;

/**
 * Created by 胡月 on 2016/8/10 0010.
 */
public class ImportedAdapter extends CommonAdapter<GoodsInfo>{
    private ImageLoader imageLoader = ImageLoader.getInstance();
    public ImportedAdapter(Activity activity, List<GoodsInfo> list) {
        super(activity, list);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView= getLayoutInflater().inflate(R.layout.item_gv_imported_home,parent,false);
            viewHolder.tvNameImported=get(convertView, R.id.tv_name_imported);
            viewHolder.tvPriceImported=get(convertView, R.id.tv_price_imported);
            viewHolder.imgGoodsImported=get(convertView, R.id.img_goods_imported);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tvNameImported.setText(getItem(position).getName());
        viewHolder.tvPriceImported.setText(getItem(position).getRetailPrice().toString());
        imageLoader.displayImage(HttpConstant.ROOT_PATH + getItem(position).getPicUrl(), viewHolder.imgGoodsImported, ImageOptions.getDefaultOptions());
        return convertView;
    }
    //自定义的ViewHolder,持有每个Item的所有界面元素
    public class ViewHolder {
        private TextView tvNameImported;//商品名称
        private TextView tvPriceImported;//商品价格
        private ImageView imgGoodsImported;//商品图片
    }
}
