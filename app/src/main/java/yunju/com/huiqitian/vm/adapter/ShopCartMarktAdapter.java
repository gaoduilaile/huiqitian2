package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.entity.CartMarket;

/**
 * Created by 胡月 on 2017/1/9 0009.
 */
public class ShopCartMarktAdapter extends CommonAdapter<CartMarket> {
    public ShopCartMarktAdapter(Activity activity, List<CartMarket> list) {
        super(activity, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=getLayoutInflater().inflate(R.layout.layout_title_position, parent, false);
            viewHolder.cbMarket=findView(convertView, R.id.choose_position);
            viewHolder.tvShopName=findView(convertView, R.id.indent_tv_shop);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tvShopName.setText(getItem(position).getMarketName());
        return convertView;
    }

    //自定义的ViewHolder,持有每个Item的所有界面元素
    public class ViewHolder{
        private CheckBox cbMarket;//选择超市
        private TextView tvShopName;//超市名称
    }
}
