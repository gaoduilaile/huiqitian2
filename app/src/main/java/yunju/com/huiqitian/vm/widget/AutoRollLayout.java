package yunju.com.huiqitian.vm.widget;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

import yunju.com.huiqitian.R;

/**
 * Created by Administrator on 2016/7/28 0028.
 */
public class AutoRollLayout extends FrameLayout {
    private  int ROLL_DELAY = 1000;
	/*
	 * 方法：
	 *
	 * 让外界指定数据（ 图片 和 文字） setItems(String[] 图片连接,String[] 文字);
	 * setItems(RollItem[] / List<RollItem>);
	 *
	 * class RollItem{ String imageUrl; String title; }
	 *
	 *
	 * 让外界指定是否自动滚动 setAllowAutoRoll(boolean)
	 *
	 * 方法 ，获得当前显示的角标：
	 * getCurrentIndex();
	 *
	 *
	 * 触摸事件
	 * A 手指放在上面时，不自动滚动，离开时恢复滚动
	 *  先得知触摸事件，在控制滚动（逻辑放到runnable）
	 *
	 * B 点击轮播图显示标题土司
	 *
	 *
	 * 在View内部：2方法可以获得触摸事件MotionEvent：dispatchTouchEvent 分发触摸事件 ，onTouchEvent
	 * 处理触摸事件， google工程师提供了一中手段,可以在外部获取到控件上的触摸事件 view.setOnTouchListener
	 *
	 * GestureDetector手势识别器，间谍
	 * 1 构造
	 * 2监视触摸事件
	 * 3通知上司
	 *
	 * performClick,此方法可以模拟一次点击事件
	 */

    ViewPager viewPager;
    /*TextView textView;*/
    LinearLayout dotContainer;
    private List<? extends IRollItem> items;
    private boolean allow;


    public AutoRollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public AutoRollLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // init();
    }

    public AutoRollLayout(Context context) {
        this(context, null);
        // init();
    }

    private void init() {
        // 从xml中加载布局
        // View rootView = View.inflate(getContext(),
        // R.layout.arl_auto_roll_layout, null);
        // addView(rootView);
        // 跟上面是等价的
        View.inflate(getContext(), R.layout.arl_auto_roll_layout, this);
        viewPager = (ViewPager) findViewById(R.id.arl_view_pager);
		/*textView = (TextView) findViewById(R.id.arl_text_view);*/
        dotContainer = (LinearLayout) findViewById(R.id.arl_dot_container);

        viewPager.setOnTouchListener(otl);

        gestureDetector = new GestureDetector(ogl);
    }

    static Handler hanlder = new Handler();

    public void setAllowAutoRoll(boolean allow) {
        this.allow = allow;
        hanlder.postDelayed(rollRunnable, 1000);

        // if(allow){
        // hanlder.postDelayed(rollRunnable, 1000);
        // }else{
        // hanlder.removeCallbacks(rollRunnable);
        // }
        Log.e("setAllowAutoRoll", "" + allow);
    }

    Runnable rollRunnable = new Runnable() {

        @Override
        public void run() {
            // 还没有执行的任务都移除掉
            hanlder.removeCallbacks(this);
            if (!allow) {
                return;
            }
            // 如果没有触摸，才动
            if (!isTouching) {
                showNextPager();
            }
            // 计划下一次任务
            hanlder.postDelayed(this, ROLL_DELAY);
        }
    };
    boolean toRight = true;

    public void showNextPager() {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem == 0) {
            toRight = true;
        } else if (currentItem == adapter.getCount() - 1) {
            toRight = false;
        }

		/*int nextindex = toRight ? currentItem + 1 : currentItem - 1;*/
        int nextindex = toRight ? currentItem + 1 : 0;
        viewPager.setCurrentItem(nextindex);

        //Log.e("showNextPager", "" + items.get(nextindex).getTitle());
    }

    /*让外部设置轮播的周期*/
    public void setCycle(int second){
        ROLL_DELAY=second;

    }

    public void setItems(List<? extends IRollItem> items) {
        this.items = items;

        // 先清空所有的点
        dotContainer.removeAllViews();
        // 把文字也清掉
		/*textView.setText(null);*/

        // 处理viewpager
        viewPager.setAdapter(adapter);

        // 处理textView
        viewPager.setOnPageChangeListener(opcl);

        // 发现传入数据没有就返回
        if (items == null || items.isEmpty()) {
            return;
        }

        // 处理点
        // 有多少数据，就有多少个点
        addDots();
        // 在页面切换的时候，当前页面对应的点是红色的

        // 搞定初始状态
        opcl.onPageSelected(0);
    }

    public int getCurrentIndex(){
        return viewPager.getCurrentItem();
    }

    private void addDots() {

        int dotWidth = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 10, getResources()
                        .getDisplayMetrics());
        for (IRollItem item : items) {
            View dot = new View(getContext());
            // android:layout_width="10dp"
            // android:layout_height="10dp"
            // android:layout_marginRight="10dp"
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    dotWidth, dotWidth);
            lp.setMargins(0, 0, dotWidth, 0);
            dot.setLayoutParams(lp);

            // android:background="@drawable/arl_dot_bg_selector"
            dot.setBackgroundResource(R.drawable.arl_dot_bg_selector);
            dotContainer.addView(dot);
        }

    }

    private PagerAdapter adapter = new PagerAdapter() {

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return items == null ? 0 : items.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = new ImageView(getContext());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            // TODO 往iv中加图片链接
            Picasso.with(getContext()).load(items.get(position).getImageUrl())
                    .into(iv);
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    };

    private ViewPager.OnPageChangeListener opcl = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            // 改变文字
			/*textView.setText(items.get(position).getTitle());*/

            int childCount = dotContainer.getChildCount();
            for (int i = 0; i < childCount; i++) {
                if (i == position) {
                    dotContainer.getChildAt(i).setEnabled(false);
                } else {
                    dotContainer.getChildAt(i).setEnabled(true);
                }
            }
            // dotContainer.getChildAt(position).setEnabled(false);

        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    boolean isTouching = false;

    private OnTouchListener otl = new OnTouchListener() {

        // 当view接受到触摸事件会调用此方法
        // v 正在被触摸的v
        // event 触摸事件
//		返回true,v的onTouchEvent方法就不会被调用，false 还会被调用
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.d("onTouch", "" + event.getAction());
            // 监视触摸事件
            gestureDetector.onTouchEvent(event);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // hanlder.removeCallbacks(rollRunnable);
                    isTouching = true;
                    break;
                case MotionEvent.ACTION_UP:
                    // hanlder.postDelayed(rollRunnable, ROLL_DELAY);
                    isTouching = false;

//				 在up时，判断down的事件和up的差
                    break;

            }

            // 返回true,v的onTouchEvent方法就不会被调用
//			v.onTouchEvent(event);
//			return true;

            return false;
        }
    };

    private GestureDetector gestureDetector;
    private GestureDetector.OnGestureListener ogl = new GestureDetector.OnGestureListener() {

        // 表示命中了手势
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
//			Toast.makeText(getContext(), "onSingleTapUp", 0).show();
            AutoRollLayout.this.performClick();
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }
    };
}

