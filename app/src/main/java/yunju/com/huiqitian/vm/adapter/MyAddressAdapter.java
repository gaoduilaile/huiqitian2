package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.entity.ReceiveAddress;

/**
 * Created by gao on 2016/8/15 0015.
 */
public class MyAddressAdapter extends CommonAdapter<ReceiveAddress> {
    public MyAddressAdapter(Activity activity, List list) {
        super(activity, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getLayoutInflater().inflate(R.layout.item_my_address, parent, false);
            holder.tvMyAddress = findView(convertView, R.id.tv_my_address);
            holder.tvMyAddressName = findView(convertView, R.id.tv_my_address_name);
            holder.tvMyAddressSex = findView(convertView, R.id.tv_my_address_sex);
            holder.tvMyAddressPhone = findView(convertView, R.id.tv_my_address_phone);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvMyAddress.setText(getItem(position).getAddressInfo());
        holder.tvMyAddressName.setText(getItem(position).getName());
        if (getItem(position).getSex() == 1) {
            holder.tvMyAddressSex.setText("男");
        }else {
            holder.tvMyAddressSex.setText("女");
        }
        holder.tvMyAddressPhone.setText(getItem(position).getTel());
        return convertView;
    }


    public class ViewHolder {
        private TextView tvMyAddressName;//地址
        private TextView tvMyAddressSex;//性别
        private TextView tvMyAddressPhone;//手机号码
        private TextView tvMyAddress;//地址
    }
}
