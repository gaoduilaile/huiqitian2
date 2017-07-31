package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;
import yunju.com.huiqitian.R;

/**
 * Created by Administrator on 2017/2/7 0007.
 */
public class AddressPoiAdapter extends CommonAdapter<PoiInfo>{
    public AddressPoiAdapter(Activity activity, List<PoiInfo> list) {
        super(activity, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=getLayoutInflater().inflate(R.layout.item_address_poi, parent, false);
            viewHolder.tvAddressName=findView(convertView, R.id.tv_address_name);
            viewHolder.tvAddress=findView(convertView,R.id.tv_address);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tvAddressName.setText(getItem(position).name);
        viewHolder.tvAddress.setText(getItem(position).address);

        return convertView;
    }
    public class ViewHolder {
        private TextView tvAddressName;
        private TextView tvAddress;
    }
}
