package yunju.com.huiqitian.utils;

/**
 * Created by Administrator on 2016/7/26 0026.
 */

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import yunju.com.huiqitian.R;

/**
 * ImageLoader显示选项
 */
public class ImageOptions {

    private static DisplayImageOptions defaultOptions;
    private static DisplayImageOptions defaultAppOptions;
    private static DisplayImageOptions defaultRecommendOptions;
    private static DisplayImageOptions defaultAppInfoOptions;
    private static DisplayImageOptions defaultAppMaxOptions;

    /**
     * 默认图片加载选项
     *
     * @return
     */
    public static DisplayImageOptions getDefaultOptions() {
        if (defaultOptions == null) {
            defaultOptions = getDisplayOptions(R.mipmap.img_goods_default);
        }
        return defaultOptions;
    }
    /**
     * 默认应用icon
     *
     * @return
     */
    public static DisplayImageOptions getAppIconOptions() {
        if (defaultAppOptions == null) {
            defaultAppOptions = getDisplayOptions(R.mipmap.img_goods_default);
        }
        return defaultAppOptions;
    }
//    /**
//     * 默认推荐icon
//     *
//     * @return
//     */
//    public static DisplayImageOptions getRecommendIconOptions() {
//        if (defaultRecommendOptions == null) {
//            defaultRecommendOptions = getDisplayOptions(R.mipmap.icon_recommend_default);
//        }
//        return defaultRecommendOptions;
//    }
//    /**
//     * 应用截图默认icon
//     *
//     * @return
//     */
//    public static DisplayImageOptions getAppInfoIconOptions() {
//        if (defaultAppInfoOptions == null) {
//            defaultAppInfoOptions = getDisplayOptions(R.mipmap.icon_app_info_default);
//        }
//        return defaultAppInfoOptions;
//    }
//    /**
//     * 应用截图浏览大图默认icon
//     *
//     * @return
//     */
//    public static DisplayImageOptions getAppMaxIconOptions() {
//        if (defaultAppMaxOptions == null) {
//            defaultAppMaxOptions = getDisplayOptions(R.mipmap.app_image_max);
//        }
//        return defaultAppMaxOptions;
//    }

    /**
     * 获取图片加载选项
     *
     * @param res 加载默认图片
     * @return DisplayImageOptions
     * @see DisplayImageOptions
     */
    private static DisplayImageOptions getDisplayOptions(@DrawableRes Integer res) {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        // 设置默认图片
        if (res != null) {
            builder.showImageForEmptyUri(res)       // url为空显示该图片
                    .showImageOnLoading(res)        // 正在加载中显示图片
                    .showImageOnFail(res);          // 加载失败显示图片
        }
        // 设置其它属性
        builder.resetViewBeforeLoading(false)           // 设置图片在下载前是否重置，复位
                .imageScaleType(ImageScaleType.EXACTLY) // 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.ARGB_8888)  // 设置图片的解码类型
                .considerExifParams(true)
                .cacheInMemory(true)                    //启用内存缓存
                .cacheOnDisk(true)                    //启用外存缓存
                .displayer(new RoundedBitmapDisplayer(1));
        return builder.build();
    }
}
