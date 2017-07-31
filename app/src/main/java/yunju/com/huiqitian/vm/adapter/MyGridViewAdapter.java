package yunju.com.huiqitian.vm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.entity.ClassifyInfo;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.utils.ImageOptions;

/**
 * Created by 胡月on 2016/7/28 0028.
 */
public class MyGridViewAdapter extends BaseAdapter {
    /*ListView的position*/
    private int p;

    private Context context;

    private LayoutInflater inflater;

    /*传过来右边的数据*/
    private List<ClassifyInfo> dataRight = new ArrayList<>();

    /*图片加载器*/
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public MyGridViewAdapter(Context context, List<ClassifyInfo> dataRight, int p) {
        this.context = context;
        this.dataRight = dataRight;
        this.p = p;


    }

    @Override
    public int getCount() {
        return dataRight.size();
    }

    @Override
    public Object getItem(int position) {
        return dataRight.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHodle holdle = null;
        if (convertView == null) {
            convertView = inflater.from(context).inflate(R.layout.item_gridview_class, null);
            holdle = new ViewHodle();
            holdle.tvGvClass = (TextView) convertView.findViewById(R.id.tv_gv_class);
            holdle.imgGoodsClass = (ImageView) convertView.findViewById(R.id.img_goods_class);
            convertView.setTag(holdle);
        } else {
            holdle = (ViewHodle) convertView.getTag();

        }
        /*LogUtils.error(MyGridViewAdapter.class,"p="+p + "......................position=" + position+"...."+dataRight.get(position).getName());*/
        holdle.tvGvClass.setText(dataRight.get(position).getName());
        imageLoader.displayImage(HttpConstant.ROOT_PATH + dataRight.get(position).getPicUrl(), holdle.imgGoodsClass, ImageOptions.getDefaultOptions());

        /*LogUtils.error(MyGridViewAdapter.class, "stop..................." );*/
        return convertView;
    }

    class ViewHodle {
        private TextView tvGvClass;
        private ImageView imgGoodsClass;
    }

}
