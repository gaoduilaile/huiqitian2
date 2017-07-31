package yunju.com.huiqitian.vm.adapter;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;
import yunju.com.huiqitian.R;

/**
 * Created by 胡月 on 2016/8/3 0003.
 */
public class MessageAdapter extends CommonAdapter{


    public MessageAdapter(Activity activity, List list) {
        super(activity, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=getLayoutInflater().inflate(R.layout.item_message,parent,false);
            viewHolder.tvTimeMessage=get(convertView, R.id.tv_time_message);
            viewHolder.tvTitleMessage=get(convertView, R.id.tv_time_message);
            viewHolder.imgMessage=get(convertView, R.id.img_message);
            viewHolder.tvContentMessage=get(convertView, R.id.tv_content_message);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        return convertView;
    }
    //自定义的ViewHolder,持有每个Item的所有界面元素
    public class ViewHolder{
        private TextView tvTimeMessage; //时间
        private TextView tvTitleMessage; //消息标题
        private ImageView imgMessage; //消息图片
        private TextView tvContentMessage; //消息内容
    }
}
