package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.trinea.android.common.adapter.CommonAdapter;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.entity.EvalList;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.vm.widget.CircleBitmapDisplayer;


/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class DetailEvaluateAdapter extends CommonAdapter<EvalList> {


    /*图片加载*/
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public DetailEvaluateAdapter(Activity activity, List<EvalList> list) {
        super(activity, list);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolde viewHolde = null;
        if (viewHolde == null) {
            viewHolde = new ViewHolde();
            convertView = getLayoutInflater().inflate(R.layout.detail_evaluate_adapter_item, null);
            viewHolde.ivPersonHead = (ImageView) convertView.findViewById(R.id.iv_person_head);
            viewHolde.tvNikeName = (TextView) convertView.findViewById(R.id.tv_nike_name);
            viewHolde.gridView = (GridView) convertView.findViewById(R.id.gv_gridView);
            viewHolde.tvEvaluation = (TextView) convertView.findViewById(R.id.tv_evaluation);
            viewHolde.tvEvalLevel = (TextView) convertView.findViewById(R.id.tv_evalLevel);
            viewHolde.tvEvaluateTime = (TextView) convertView.findViewById(R.id.tv_evaluate_time);
            convertView.setTag(viewHolde);
        } else {
            viewHolde = (ViewHolde) convertView.getTag();
        }

        viewHolde.tvNikeName.setText(getItem(position).getEvalUser());
        viewHolde.tvEvaluation.setText(getItem(position).getEvalText());
        viewHolde.tvEvalLevel.setText(getItem(position).getEvalLevelDesc());
        viewHolde.tvEvaluateTime.setText(getItem(position).getStCreateTime());
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new CircleBitmapDisplayer())
                .build();

        imageLoader.displayImage(HttpConstant.ROOT_PATH + getItem(position).getAvatarPicUrl(),
                viewHolde.ivPersonHead, options);
        EvalImageAdapter evalImageAdapter = new EvalImageAdapter((Activity) getContext(), getItem(position).getPics());
        viewHolde.gridView.setAdapter(evalImageAdapter);


        return convertView;
    }

    class ViewHolde {
        public ImageView ivPersonHead;
        public TextView tvNikeName;
        public GridView gridView;
        public TextView tvEvaluation;
        public TextView tvEvalLevel;
        public TextView tvEvaluateTime;
    }
}
