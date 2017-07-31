package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.entity.AppPic;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.utils.ImageOptions;

/**
 * Created by 胡月 on 2016/9/30 0030.
 */
public class EvalImageAdapter extends BaseAdapter{

    private final List<AppPic> list;
    private final Activity activity;
    /*图片加载类*/
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public EvalImageAdapter(Activity activity, List<AppPic> list) {
        this.activity = activity;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EvalViewHolder evalViewHolde=null;
        if(evalViewHolde==null){
            evalViewHolde=new EvalViewHolder();
            convertView=convertView.inflate(activity,R.layout.item_eval_image,null);
            /*evalViewHolde.evalImage=findView(convertView, R.id.eval_image);*/
            evalViewHolde.evalImage= (ImageView) convertView.findViewById(R.id.eval_image);
            convertView.setTag(evalViewHolde);
        }else {
            evalViewHolde= (EvalViewHolder) convertView.getTag();
        }

        if (list.get(position).getPicUrl()==null){
            evalViewHolde.evalImage.setVisibility(View.GONE);
        }else {
         /*加载商品图片*/
            imageLoader.displayImage(HttpConstant.ROOT_PATH + list.get(position).getPicUrl(),
                    evalViewHolde.evalImage, ImageOptions.getDefaultOptions());
        }
        return convertView ;
    }

    private class EvalViewHolder {
        private ImageView evalImage;

    }
}
