package yunju.com.huiqitian.vm.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import yunju.com.huiqitian.utils.ImageOptions;

/**
 * Created by zcq on 2016-08-16.
 * <p/>
 * 轮播图适配器
 */
public class GalleryPagerAdapter extends PagerAdapter {

    private List<String> imageViewIds;
    private Activity activity;

    /*图片加载器*/
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public GalleryPagerAdapter(List<String> imageViewIds, Activity activity) {
        this.imageViewIds = imageViewIds;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return imageViewIds.size();
    }



    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView item = new ImageView(activity);
        final ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        item.setLayoutParams(params);
        item.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(item);
        imageLoader.displayImage(imageViewIds.get(position), item, ImageOptions.getDefaultOptions());

        final int pos = position;
        /*点击跳转事件*/
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Intent intent = new Intent(ZhiFuBaoActivity.this, ImageGalleryActivity.class);
//                    intent.putStringArrayListExtra("images", (ArrayList<String>) imageList);
//                    intent.putExtra("position", pos);
//                    startActivity(intent);
//                ToastUtils.show(activity, pos);
                Toast.makeText(activity, "pos" + pos, Toast.LENGTH_SHORT).show();
            }

        });

        item.setOnTouchListener(new View.OnTouchListener() {
            int firstX,firstY;
            int SecondX,SecondY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        firstX= (int) event.getRawX();
                        firstY= (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        SecondX= (int) event.getRawX();
                        SecondY= (int) event.getRawY();

                        int dx= Math.abs(SecondX-firstX);
                        int dy= Math.abs(SecondY-firstY);
                        if (dx>dy){
                            return true;
                        }else if (dx<dy){
                            return false;
                        }

                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                }
                return false;
            }
        });

        return item;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }


}