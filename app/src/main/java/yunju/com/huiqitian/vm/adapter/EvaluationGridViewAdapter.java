package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;
import yunju.com.huiqitian.R;

/**
 * Created by 张超群 on 2016-08-09.
 * <p/>
 * 展示用户图片选择
 */
public class EvaluationGridViewAdapter extends CommonAdapter<HashMap<String, String>> {

//    private ArrayList<HashMap<String, String>> imageItem;
//    private Activity aty;
//
//    public EvaluationGridViewAdapter(Activity accessorySelectActivity,
//                                     ArrayList<HashMap<String, String>> imageItem) {
//        this.imageItem = imageItem;
//        this.aty = accessorySelectActivity;
//    }

    public EvaluationGridViewAdapter(Activity activity, List<HashMap<String, String>> list) {
        super(activity, list);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.gridview_addpic, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.img_item_auth_top);
            viewHolder.deleteImg = (ImageView) convertView.findViewById(R.id.img_delete_top);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /*删除按钮*/
        viewHolder.deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ondeleteitemclick.OnItemDeleteClickListeners(position);
            }
        });

        /*如果位置等于最后一个就隐藏*/
        if (position == (getList().size() - 1)) {
            viewHolder.deleteImg.setVisibility(View.GONE);
            ImageLoader.getInstance().displayImage("drawable://" + R.mipmap.icon_addpic_unfocused,
                    viewHolder.image);
        } else {
            viewHolder.deleteImg.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage("file://" + getList().get(position).get("itemImage"),
                    viewHolder.image);

        }
        return convertView;
    }

    public final class ViewHolder {
        public ImageView deleteImg;//删除操作
        public ImageView image;//图片

    }

    public OnDeleteItemClick ondeleteitemclick;

    /**
     * 图片删除选择按钮
     */
    public interface OnDeleteItemClick {
        void OnItemDeleteClickListeners(int position);
    }

    public void setOnItemClick(OnDeleteItemClick ondeleteitemclick) {
        this.ondeleteitemclick = ondeleteitemclick;
    }

}
