package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.entity.MessageInfo;
import yunju.com.huiqitian.utils.ImageOptions;

/**
 * Created by Administrator on 2016/9/2.
 */
public class MessageSendAdapter extends CommonAdapter<MessageInfo> {
    /*图片加载类*/
    private ImageLoader imageLoader = ImageLoader.getInstance();

    private List<MessageInfo> mList;


    public MessageSendAdapter(Activity activity, List<MessageInfo> list) {
        super(activity, list);
        this.mList=list;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getLayoutInflater().inflate(R.layout.message_system_item, parent, false);
            holder.tvTitle = findView(convertView, R.id.tv_title);
            holder.tvContentMessage = findView(convertView, R.id.tv_content_message);
            holder.ivIcon = findView(convertView, R.id.iv_icon);
            holder.tvTime=findView(convertView,R.id.tv_time);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        imageLoader.displayImage(getItem(position).iconUrl,
                holder.ivIcon,
                ImageOptions.getDefaultOptions());//加载图片
        holder.tvTitle.setText(getItem(position).title);
        holder.tvContentMessage.setText(getItem(position).content);

        String s = transferLongToDate("yyyy.MM.dd    HH:mm:ss", getItem(position).time);
        holder.tvTime.setText(s);


        /*listView的条目的点击事件*/

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

    public class ViewHolder {
        private ImageView ivIcon;//消息的图片
        private TextView tvTitle;//标题
        private TextView tvContentMessage;//内容
        private TextView tvTime;//时间
    }

    /**
     * 把毫秒转化成日期
     * @param dateFormat(日期格式，例如：MM/ dd/yyyy HH:mm:ss)
     * @param millSec(毫秒数)
     * @return
     */

    private String transferLongToDate(String dateFormat,Long millSec){

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        Date date= new Date(millSec);

        return sdf.format(date);
    }
}
