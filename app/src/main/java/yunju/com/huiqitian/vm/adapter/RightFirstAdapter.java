package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.entity.LeftClassifyReq;
import yunju.com.huiqitian.utils.ImageOptions;

/**
 * 分类中已进入就加载第一条数据
 * Created by CCTV-1 on 2016/11/25 0025.
 */
public class RightFirstAdapter extends CommonAdapter {
    private final List<LeftClassifyReq.ObjBean.ChildsBean> childFirstList;

    /*图片加载类*/
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public RightFirstAdapter(Activity activity, List<LeftClassifyReq.ObjBean.ChildsBean> childFirstList) {
        super(activity, childFirstList);
        this.childFirstList = childFirstList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder = null;

        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.item_right_foods, parent, false);
            myViewHolder = new MyViewHolder();
            myViewHolder.tvRightClass = (TextView) convertView.findViewById(R.id.tv_right_class);
            myViewHolder.ivFoodsRight = (ImageView) convertView.findViewById(R.id.iv_foods_right);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        /*赋值*/
        myViewHolder.tvRightClass.setText(childFirstList.get(position).getName());

         /*加载网络图片 */
        imageLoader.displayImage((HttpConstant.ROOT_PATH + childFirstList.get(position).getPicUrl()), myViewHolder.ivFoodsRight,
                ImageOptions.getDefaultOptions());

        return convertView;
    }
    class MyViewHolder{
        private TextView tvRightClass;//分类名
        private ImageView ivFoodsRight;//分类的图标
    }
}
