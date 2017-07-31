package yunju.com.huiqitian.vm.widget.loopviewpager;

/**
 * Created by Administrator on 2016/11/1 0001.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.utils.ImageOptions;

public class MyViewPager extends ViewGroup {
    /*图片加载器*/
    private ImageLoader imageLoader = ImageLoader.getInstance();


    private List<String> image_id=new ArrayList<>();
    private GestureDetector mDetector;
    private Scroller mScroller;
    private float x1, x2, y1, y2;

    public MyViewPager(Context context,List<String> image_id) {
        super(context);
        this.image_id=image_id;
        initView();
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        for (int i = 0; i < image_id.size(); i++) {
            ImageView iv = new ImageView(getContext());
            //iv.setBackgroundResource(image_id.get(i));
            LogUtils.error(MyViewPager.class,"----iamgeview--"+image_id.get(i));
            imageLoader.displayImage(HttpConstant.ROOT_PATH + image_id.get(i), iv, ImageOptions.getDefaultOptions());//商品图片
            this.addView(iv);
        }

//        mDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
//            @Override
//            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//                //scrollBy：相对滑动，相对我们当前的控件多少距离，就滑动多少距离
//                //distanceX是我们手滑动的距离，即我们的手相对控件滑动了多少，所以X轴滑动这个距离，Y轴滑动0
//                scrollBy((int) distanceX, 0);
//                return super.onScroll(e1, e2, distanceX, distanceY);
//            }
//        });
       // mScroller = new Scroller(getContext());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < image_id.size(); i++) {
            this.getChildAt(i).layout(i * getWidth(), t, (i + 1) * getWidth(), b);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       // mDetector.onTouchEvent(event);
        //触摸事件处理
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getRawX();
                y1 = event.getRawY();

                break;
            case MotionEvent.ACTION_MOVE:
                x2 = event.getRawX();
                y2 = event.getRawY();

                if (Math.abs(x1-x2)>Math.abs(y2-y1)){
                    y2=y1;
                    return true;
                }
                if (Math.abs(x1-x2)<Math.abs(y2-y1)){
                    x2=x1;
                    return false;
                }


                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                //你滑动的距离加上屏幕的一半，除以屏幕宽度，如果你滑动距离超过了屏幕的一半，这个pos就加1
                int pos = (scrollX + getWidth() / 2) / getWidth();
                //滑到最后一张的时候，不能出边界
                if (pos >= image_id.size()) {
                    pos = image_id.size() - 1;
                }
                //绝对滑动，直接滑到指定的x值
                //scrollTo(pos * getWidth(), 0);
                //自然滑动,从手滑到的地方开始，滑动距离是页面宽度减去滑到的距离，时间由路程的大小来决定
                mScroller.startScroll(scrollX, 0, pos * getWidth() - scrollX, 0, Math.abs(pos * getWidth()));
               // invalidate();
                break;
        }
        return  super.onTouchEvent(event);
    }



//    @Override
//    public void computeScroll() {
//        if (mScroller.computeScrollOffset()) {
//            scrollTo(mScroller.getCurrX(), 0);
//            postInvalidate();
//        }
//    }
}