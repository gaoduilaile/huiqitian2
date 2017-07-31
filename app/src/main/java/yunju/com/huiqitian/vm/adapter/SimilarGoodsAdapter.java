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
import yunju.com.huiqitian.entity.GoodsInfo;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.utils.ImageOptions;

/**
 * Created by 胡月 on 2016/9/29 0029.
 */
public class SimilarGoodsAdapter extends CommonAdapter<GoodsInfo>{

    private ImageLoader imageLoader = ImageLoader.getInstance();
    public SimilarGoodsAdapter(Activity activity, List<GoodsInfo> list) {
        super(activity, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecommendViewHolder recommendViewHolder=null;
        if(convertView==null){
            recommendViewHolder=new RecommendViewHolder();
            convertView=getLayoutInflater().inflate(R.layout.item_gv_sales_home,parent,false);
            recommendViewHolder.ivGoods=get(convertView, R.id.img_sales_home);
            recommendViewHolder.tvGoodsName=get(convertView,R.id.tv_name_sales_home);
            recommendViewHolder.tvIntegralNum=get(convertView,R.id.tv_price_sales);
            convertView.setTag(recommendViewHolder);
        }else {
            recommendViewHolder= (RecommendViewHolder) convertView.getTag();
        }
        recommendViewHolder.tvGoodsName.setText(getItem(position).getName());
        recommendViewHolder.tvIntegralNum.setText(getItem(position).getRetailPrice().toString());
        imageLoader.displayImage(HttpConstant.ROOT_PATH + getItem(position).getPicUrl(), recommendViewHolder.ivGoods, ImageOptions.getDefaultOptions());
        return convertView;
    }

    /**
     * 推荐商品中的viewHandler类
     */
    public class RecommendViewHolder {
        LinearLayout llItemSales;
        LinearLayout llItemIntegral;
        ImageView ivGoods;
        TextView tvGoodsName;
        TextView tvIntegralNum;

    }
}
