package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.entity.GoodsProp;

/**
 * Created by 胡月 on 2016/8/4 0004.
 */
public class GoodsDetailsAdapter extends CommonAdapter<GoodsProp>{
    public GoodsDetailsAdapter(Activity activity, List list) {
        super(activity, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=getLayoutInflater().inflate(R.layout.item_goods_details,parent,false);
            viewHolder.tvTitleGoodsDetails=get(convertView, R.id.tv_title_goods_details);
            viewHolder.tvGoodsDetails=get(convertView, R.id.tv_goods_details);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
            viewHolder.tvTitleGoodsDetails.setText(getItem(position).getName());
            viewHolder.tvGoodsDetails.setText(getItem(position).getPropValue()+getItem(position).getUnit());
        return convertView;
    }

    //自定义的ViewHolder,持有每个Item的所有界面元素
    public class ViewHolder {
        private TextView tvTitleGoodsDetails;
        private TextView tvGoodsDetails;
    }
}
