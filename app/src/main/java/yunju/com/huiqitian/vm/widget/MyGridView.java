package yunju.com.huiqitian.vm.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Administrator on 2016/7/28 0028.
 */
public class MyGridView extends GridView{
    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*重写onMeasure，取消GridView的滑动*/
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
