package yunju.com.huiqitian.vm.widget.loopviewpager;

import android.content.Context;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.lang.ref.WeakReference;

/**
 * Created by tiansj on 15/8/7.
 * 继承LoopViewPager，增加handle触发自动循环，并对事件进行处理，按下停止播放，抬起开始播放
 */
public class AutoLoopViewPager extends LoopViewPager { /** 触摸时按下的点 **/

    /** 触摸时按下的点 **/
    PointF downP = new PointF();
    /** 触摸时当前的点 **/
    PointF curP = new PointF();

    public static final int DEFAULT_INTERVAL = 3000;
    public static final int SCROLL_WHAT = 0;
    private long interval = DEFAULT_INTERVAL;
    private Handler handler;

    public AutoLoopViewPager(Context context) {
        super(context);
        init();
    }

    public AutoLoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        handler = new MyHandler(this);
        startAutoScroll();
    }

    private static class MyHandler extends Handler {

        private final WeakReference<AutoLoopViewPager> autoScrollViewPager;

        public MyHandler(AutoLoopViewPager autoScrollViewPager) {
            this.autoScrollViewPager = new WeakReference<AutoLoopViewPager>(autoScrollViewPager);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case SCROLL_WHAT:
                    AutoLoopViewPager pager = this.autoScrollViewPager.get();
                    if (pager != null) {
                        pager.scrollOnce();
                        pager.sendScrollMessage(pager.interval);
                    }
                default:
                    break;
            }
        }
    }

    /**
     * <ul>
     * if stopScrollWhenTouch is true
     * <li>if event is down, stop auto scroll.</li>
     * <li>if event is up, start auto scroll again.</li>
     * </ul>
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_DOWN) {
            stopAutoScroll();
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            startAutoScroll();
        }
        getParent().requestDisallowInterceptTouchEvent(true);

        return super.dispatchTouchEvent(ev);

    }

    /**
     * scroll only once
     */
    public void scrollOnce() {
        PagerAdapter adapter = getAdapter();
        int currentItem = getCurrentItem();
        int totalCount;
        if (adapter == null || (totalCount = adapter.getCount()) <= 1) {
            return;
        }

        int nextItem = ++currentItem;
        if (nextItem < 0) {
            setCurrentItem(totalCount - 1);
        } else if (nextItem == totalCount) {
            setCurrentItem(0);
        } else {
            setCurrentItem(nextItem);
        }
    }

    /**
     * start auto scroll, first scroll delay time is 4s
     */
    public void startAutoScroll() {
        sendScrollMessage(interval);
    }

    /**
     * start auto scroll
     *
     * @param delayTimeInMills first scroll delay time
     */
    public void startAutoScroll(int delayTimeInMills) {
        sendScrollMessage(delayTimeInMills);
    }

    /**
     * stop auto scroll
     */
    public void stopAutoScroll() {
        handler.removeMessages(SCROLL_WHAT);
    }

    private void sendScrollMessage(long delayTimeInMills) {
        /** remove messages before, keeps one message is running at most **/
        handler.removeMessages(SCROLL_WHAT);
        handler.sendEmptyMessageDelayed(SCROLL_WHAT, delayTimeInMills);
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);//default
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //每次进行onTouch事件都记录当前的按下的坐标
        curP.x = ev.getX();
        curP.y = ev.getY();

        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            //记录按下时候的坐标
            //切记不可用 downP = curP ，这样在改变curP的时候，downP也会改变
            downP.x = ev.getX();
            downP.y = ev.getY();
            //此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        if(ev.getAction() == MotionEvent.ACTION_MOVE){
            float distanceX = curP.x - downP.x;
            float distanceY = curP.y - downP.y;
            //接近水平滑动，ViewPager控件捕获手势，水平滚动
            if(Math.abs(distanceX) > Math.abs(distanceY)){
                //此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
                return  true;
                //getParent().requestDisallowInterceptTouchEvent(true);
            }else if (Math.abs(distanceX) < Math.abs(distanceY)){
                //接近垂直滑动，交给父控件处理

                return  false;
               // getParent().requestDisallowInterceptTouchEvent(false);

            }
        }
        return super.onTouchEvent(ev);
    }
}
