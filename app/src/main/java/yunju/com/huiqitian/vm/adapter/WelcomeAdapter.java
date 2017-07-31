package yunju.com.huiqitian.vm.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.trinea.android.common.util.ListUtils;

/**
 * Created by 高英祥 on 2016/7/13 0013.
 */
public class WelcomeAdapter extends PagerAdapter {
    private List<View> viewList;

    public WelcomeAdapter(List<View> viewList) {
        this.viewList = viewList;
    }

    @Override
    public int getCount() {
        return ListUtils.getSize(viewList);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = viewList.get(position);
        container.addView(view);
        return view;
    }
}
