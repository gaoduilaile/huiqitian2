package yunju.com.huiqitian.vm.widget;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by liuGang on 2016/8/8 0008.
 */
public class ItemListView extends ListView{


    public ItemListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
