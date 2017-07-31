package yunju.com.huiqitian.vm.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;
import java.util.List;
import yunju.com.huiqitian.utils.ImageOptions;

/**
 * Created by 胡月 on 2016/8/9.
 * 轮播图,将不同的数据通过调用内部方法，传给方法图片的url，使用imageLoader进行自动加载
 */
public class DynamicViewPager extends ViewPager {
    /**
     * 标签
     */
    private String TAG = DynamicViewPager.class.getSimpleName();

    /**
     * 用来添加图片资源
     */
    private List<View> views;

    /**
     * 加载图片的工具
     */
    private ImageLoader imageLoader= ImageLoader.getInstance();

    /**
     * 图片数量，这个数量是处理后的数量
     */
    private int number=0;

    public DynamicViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DynamicViewPager(Context context) {
        super(context);
        init();
    }


    /**
     * 适配器
     */
    private PagerAdapter adapter = new PagerAdapter() {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = views.get(position%views.size());
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null !=parent){
                parent.removeView(view);
            }
            ((ViewPager)container).addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            ((ViewPager)container).removeView(views.get(position%views.size()));
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }
    };

    /**
     * 监听器
     */
    private OnPageChangeListener listener = new OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrolled(int position, float percent, int offset) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    /**
     * 初始化数据
     */
    private void init(){
//        setOffscreenPageLimit(2);
        setAdapter(adapter); // 适配器
        super.setOnPageChangeListener(listener); // 监听器
    }


    /**
     * 得到图片数量
     * @return
     */
    public int getImageNumber(){
        return number;
    }



    /**
     * 设置数据,此数据是图片的url集合，通过上面的加载图片工具，加载图片，然后添加到views中
     * @param url
     */
    public void setViews(ArrayList<String> url){
        this.views=new ArrayList<>();
        views.clear();

        if(url.size()==1){
            url.addAll(url);
            url.addAll(url);
        }
         else if(url.size()==2){
            url.addAll(url);
        }
        number=url.size();
        for (int i = 0; i < url.size(); i++) {
            ImageView image=new ImageView(getContext());
            imageLoader.displayImage(url.get(i).replace("#", "origin"),image, ImageOptions.getDefaultOptions());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
//            image.setScaleType(ImageView.ScaleType.CENTER);
            image.setLayoutParams(params);
            this.views.add(image);
        }
        setCurrentItem(0);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);//这句话的作用 告诉父view，我的单击事件我自行处理，不要阻碍我。
        return super.dispatchTouchEvent(ev);
    }


    /**
     * 设置当前页面
     * @param page
     */
    public void setItem(int page){
        if(page<0){
            setCurrentItem(views.size()-1);
        }else if(page>=views.size()){
            setCurrentItem(0);
        }else{
            setCurrentItem(page);
        }
    }

    /**
     * 当前页面
     * @return
     */
    public int getItem(){
        return getCurrentItem();
    }
}
