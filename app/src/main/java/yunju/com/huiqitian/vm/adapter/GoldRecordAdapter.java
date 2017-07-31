package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;
import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.http.entity.GoldRecord;

/**
 * Created by 胡月 on 2016/8/16 0016.
 */
public class GoldRecordAdapter extends CommonAdapter<GoldRecord>{
    public GoldRecordAdapter(Activity activity, List<GoldRecord> list) {
        super(activity, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView= getLayoutInflater().inflate(R.layout.item_gold_record_sign_in,parent,false);
            viewHolder.tvNameGoldRecord=get(convertView, R.id.tv_name_gold_record);
            viewHolder.tvTimeGoldRecord=get(convertView, R.id.tv_time_gold_record);
            viewHolder.imgGoldRecord=get(convertView, R.id.img_gold_record);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        LogUtils.error(GoldRecordAdapter.class,getItem(position).toString());
        viewHolder.tvNameGoldRecord.setText(getItem(position).getShakeGoodName());
        viewHolder.tvTimeGoldRecord.setText(timeConvert(getItem(position).getLuckyTime()));
        return convertView;
    }
    //自定义的ViewHolder,持有每个Item的所有界面元素
    public class ViewHolder {
        private TextView tvNameGoldRecord;//中奖商品名称
        private TextView tvTimeGoldRecord;//中奖商品时间
        private ImageView imgGoldRecord;//中奖商品图片
    }

    /**
     * 时间类型转换Timestamp___String
     */
    public String timeConvert(Timestamp ts){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tsStr;
        tsStr = sdf.format(ts);
        return tsStr;
    }

}
