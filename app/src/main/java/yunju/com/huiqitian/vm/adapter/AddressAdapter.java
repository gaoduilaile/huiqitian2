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
 * Created by gao on 2016/8/4 0004.
 */
public class AddressAdapter extends CommonAdapter<PoiInfo> {

    public AddressAdapter(Activity activity, List<PoiInfo> list) {
        super(activity, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = getLayoutInflater().inflate(R.layout.item_address, parent, false);
            viewHolder.tvAddressItemName = findView(convertView, R.id.tv_address_item_name);
            viewHolder.tvAddressItemNum = findView(convertView, R.id.tv_address_item_num);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvAddressItemName.setText(getItem(position).name);
        viewHolder.tvAddressItemNum.setText(getItem(position).address);
        return convertView;
    }


    public class ViewHolder {
        private TextView tvAddressItemName;
        private TextView tvAddressItemNum;
    }
}
