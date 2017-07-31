package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.entity.GoldDetail;

/**
 * Created by 胡月 on 2016/8/18 0018.
 */
public class GoldDeatilAdapter extends CommonAdapter<GoldDetail>{
    public GoldDeatilAdapter(Activity activity, List<GoldDetail> list) {
        super(activity, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=new ViewHolder();
        if(convertView==null){
            convertView=getLayoutInflater().inflate(R.layout.item_gold_detail,parent,false);
            viewHolder.tvGoldAmountGoldDetail=get(convertView, R.id.tv_gold_amount_gold_detail);
            viewHolder.tvTimeGoldDetail=get(convertView, R.id.tv_time_gold_detail);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tvGoldAmountGoldDetail.setText("+"+getItem(position).getCoins());
        viewHolder.tvTimeGoldDetail.setText(getData(getItem(position).getSigninTime()));
        return convertView;
    }

    public class ViewHolder {
        private TextView tvGoldAmountGoldDetail;//显示金币数量
        private TextView tvTimeGoldDetail;//显示签到时间
    }

    public String getData(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String str=sdf.format(date);
        return str;
    }
}
