package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.http.entity.LeftClassifyReq;

/**
 * 左边父控件的适配器
 * Created by CCTV-1 on 2016/11/25 0025.
 */
public class LeftAdapter extends CommonAdapter {
    private final List<LeftClassifyReq.ObjBean> fuList;

    public LeftAdapter(Activity activity, List<LeftClassifyReq.ObjBean> fuList) {
        super(activity, fuList);
        this.fuList = fuList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder = null;
        if (convertView == null) {
            myViewHolder = new MyViewHolder();
            convertView = getLayoutInflater().inflate(R.layout.item_left_class, parent, false);
            myViewHolder.tvListClass = (TextView) convertView.findViewById(R.id.tv_list_class);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }

        /*赋值*/
        if(position==0){
            myViewHolder.tvListClass.setTextColor(getColor(R.color.color_text_title));
            myViewHolder.tvListClass.setBackgroundColor(getColor(R.color.color_f5f5f5));
        }
        myViewHolder.tvListClass.setText(fuList.get(position).getName());
        return convertView;

    }

    class MyViewHolder {
        private TextView tvListClass;
    }
}
