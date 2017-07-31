package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.nio.channels.Pipe;
import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.db.entity.History;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.utils.ImageOptions;

/**
 * Created by gao on 2016/8/16 0016.
 */
public class HistoryAdapter extends CommonAdapter<History> {

    /*图片加载类*/
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public HistoryAdapter(Activity activity, List<History> list) {
        super(activity, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getLayoutInflater().inflate(R.layout.item_history_collect, parent, false);
            holder.ivHistoryImg = findView(convertView, R.id.iv_history_img);
            holder.tvHistoryName = findView(convertView, R.id.tv_history_name);
            holder.tvHistoryDis = findView(convertView, R.id.tv_history_dis);
            holder.tvHistoryMoney = findView(convertView,R.id.tv_history_money);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        imageLoader.displayImage(HttpConstant.ROOT_PATH + getItem(position).getImgUrl(),
                holder.ivHistoryImg,
                ImageOptions.getDefaultOptions());//加载图片
        holder.tvHistoryName.setText(getItem(position).getShopName());
        holder.tvHistoryDis.setText(getItem(position).getShopName()+"/"+getItem(position).getShopDistance()+"千米");
        holder.tvHistoryMoney.setText(getItem(position).getGoodsMoney());
        return convertView;
    }

    public class ViewHolder {
        private ImageView ivHistoryImg;//商品的图片
        private TextView tvHistoryName;//商品的名字
        private TextView tvHistoryDis;//商品的地址和距离
        private TextView tvHistoryMoney;//价格
    }
}
