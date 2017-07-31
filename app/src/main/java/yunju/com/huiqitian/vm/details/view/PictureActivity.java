package yunju.com.huiqitian.vm.details.view;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.utils.ImageOptions;

/**
 * Created by Administrator on 2016/10/8 0008.
 */
public class PictureActivity extends BaseActivity {

    private ImageView imageView;
    private String picName;
    Bitmap icon = null;//原始图片
    /*图片加载类*/
    private ImageLoader imageLoader = ImageLoader.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState, R.layout.activity_picture);

    }

    @Override
    public void initBoot() {

    }

    @Override
    public void initViews() {
        imageView = findView(R.id.large_image);
    }

    @Override
    public void initData(Bundle bundle) {

       /* int width = icon.getWidth();
        int height = icon.getHeight();
        float scaleRate = width > height ? 300 / width : 300 / height;//缩小的比例
        while (width >= 300 || width >= 300) {
            Matrix matrix = new Matrix();
            matrix.postScale(width * scaleRate, height * scaleRate);
            // 得到新的图片
            Bitmap newIcon = Bitmap.createBitmap(icon, 0, 0, width, height, matrix, true);
            icon.recycle();
            icon = newIcon;
            width = icon.getWidth(); height = icon.getHeight();
        }

        if (width > 300 || width > 300) {
            Matrix matrix = new Matrix();
            matrix.postScale(width * scaleRate, height * scaleRate);
            // 得到新的图片
            Bitmap newIcon = Bitmap.createBitmap(icon, 0, 0, width, height, matrix, true);
            icon.recycle();
            icon = newIcon;
        }*/
        picName = bundle.getString("picName"); //图片名
        imageLoader.displayImage(HttpConstant.ROOT_PATH + picName,
                imageView, ImageOptions.getDefaultOptions());


    }

    @Override
    public void initEvents() {

    }
}
