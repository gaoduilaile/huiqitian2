package yunju.com.huiqitian;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.text.TextUtils;

import com.baidu.mapapi.SDKInitializer;
import com.http.session.HttpSession;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.common.util.LogUtils;
import cn.trinea.android.common.util.PreferenceHelper;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.GoodsInfo;
import yunju.com.huiqitian.http.HttpClient;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.vm.login.view.LoginActivity;

/**
 * Created by 高英祥 on 2016/7/11 0011.
 */
public class AppApplication extends Application {
    private static Context context;

    private static PreferenceHelper preferenceHelper;
    private static SharedPreferences sharedPreferences;
    private static HttpClient httpClient;

    private static List<String> AllTagsNormal = new ArrayList<>();//整个标签存放集合
    private static List<GoodsInfo> goodsInfos = new ArrayList<>();//商品列表

    /*数据库相关*/
    private static DbManager.DaoConfig daoConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        context = getApplicationContext();
        initImageLoader(context);
        /*数据库相关*/
        x.Ext.init(this);
        x.Ext.setDebug(true);

        daoConfig = new DbManager.DaoConfig()
                .setDbName("huiqitian")    //设置数据库名称
                .setDbDir(new File(Environment.getExternalStorageDirectory().getAbsolutePath()))  //数据库路径
                .setDbVersion(1)  //数据库版本
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                    }
                });
        sharedPreferences = AppApplication.getSharedPreferences();

    }


    /**
     * 获取上下文对象
     *
     * @return
     */
    public static Context getContext() {
        return context;
    }

    /**
     * imageLoader 加载
     *
     * @param context
     */
    private static void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getCacheDirectory(context);
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        //ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator()).discCache(new UnlimitedDiscCache(cacheDir)).tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs()
        //.build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(5)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(10 * 1024 * 1024))
                .memoryCacheSize(10 * 1024 * 1024)
                .diskCacheSize(100 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .build();

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    public static DbManager.DaoConfig getDaoConfig() {
        return daoConfig;
    }

    /**
     * 获取数据存储
     *
     * @return
     */
    public static PreferenceHelper getPreferenceHelper() {
        synchronized (AppApplication.class) {
            if (preferenceHelper == null) {
                PreferenceHelper.PREFERENCE_NAME = Constant.SHARED_PREFERENCE_NAME;
                preferenceHelper = new PreferenceHelper(context);
            }
        }
        return preferenceHelper;
    }

    /**
     * 获取网络连接
     *
     * @return
     */
    public static HttpClient getHttpClient() {
        synchronized (AppApplication.class) {
            if (httpClient == null) {
                HttpSession.initialize(context);
                httpClient = new HttpClient();
            }
        }
        return httpClient;
    }

    /**
     * 用来缓存云标签的
     */
    public static List<String> getAllTagsNormal() {
        return AllTagsNormal;
    }

    public static void setAllTagsNormal(List<String> allTagsNormal) {
        AllTagsNormal = allTagsNormal;
    }

    /**
     * 缓存商品列表
     */
    public static List<GoodsInfo> getGoodsInfos() {
        return goodsInfos;
    }

    public static void setGoodsInfos(List<GoodsInfo> goodsInfos) {
        AppApplication.goodsInfos = goodsInfos;
    }


    /**
     * 获取数据存储
     *
     * @return
     */
    public static SharedPreferences getSharedPreferences() {
        synchronized (AppApplication.class) {
            if (AppApplication.sharedPreferences == null) {
                sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
            }
        }
        return sharedPreferences;
    }

    /**
     * 保存购物车刷新状态
     */
   /* public static void putShopBoolean(String key,boolean value){
        synchronized (AppApplication.class) {
            if (sharedPreferences == null) {
                sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);

            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(key, value);

            editor.commit();
        }
    }*/

    /**
     * 获取购物车刷新状态
     */
   /* public static boolean getShopBoolean(String key) {
        synchronized (AppApplication.class) {
            if (sharedPreferences == null) {
                sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
            }

            return sharedPreferences.getBoolean(key, true);
        }
    }*/


    /**
     * 设置boolean
     */
    public static void putBoolean(String key, boolean value) {
        synchronized (AppApplication.class) {
            if (sharedPreferences == null) {
                sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);

            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(key, value);

            editor.commit();
        }
    }



    /**
     * 获得boolean
     */
    public static boolean getBoolean(String key) {
        synchronized (AppApplication.class) {
            if (sharedPreferences == null) {
                sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
            }

            return sharedPreferences.getBoolean(key, true);
        }
    }

    /**
     * 保存数据string
     */
    public static void putString(String key, String value) {

        LogUtils.error(LoginActivity.class, "------进入application--------" + MyUtils.getCellPhone() + ";" + MyUtils.getPassword());
        synchronized (AppApplication.class) {
            if (AppApplication.sharedPreferences == null) {
                sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
            }

            LogUtils.error(LoginActivity.class, "------进入application--context------：" + context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, value);
            editor.commit();
        }

    }


    /**
     * 得到数据string
     */
    public static String getString(String key) {
        synchronized (AppApplication.class) {
            if (sharedPreferences == null) {
                sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);

            }

            if (!TextUtils.isEmpty(key)) {
                String value = sharedPreferences.getString(key, "");
                return value;
            }
        }
        return "";
    }

    /**
     * 保存数据string
     */
    public static void putInt(String key, int value) {

        LogUtils.error(LoginActivity.class, "------进入application--------" + MyUtils.getCellPhone() + ";" + MyUtils.getPassword());
        synchronized (AppApplication.class) {
            if (AppApplication.sharedPreferences == null) {
                sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
            }

            LogUtils.error(LoginActivity.class, "------进入application--context------：" + context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(key, value);
            editor.commit();
        }

    }


    /**
     * 得到数据string
     */
    public static int getInt(String key) {
        synchronized (AppApplication.class) {
            if (sharedPreferences == null) {
                sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
            }

            if (!TextUtils.isEmpty(key)) {
                int value = sharedPreferences.getInt(key, -1);
                return value;
            }
        }
        return -1;
    }

}

