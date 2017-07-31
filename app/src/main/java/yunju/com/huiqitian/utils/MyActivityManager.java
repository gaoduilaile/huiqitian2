package yunju.com.huiqitian.utils;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by gaoqiong on 2016/9/7 0007.
 * Activity管理类
 * 1、addActivity():保存Activity
 * 2、exit():关闭所有保存的Activity
 */
public class MyActivityManager extends Application {
    /**
     * Activity列表
     */
    private List<Activity> activityList = new LinkedList<Activity>();

    /**
     * 全局唯一实例
     */
    private static MyActivityManager instance;


    /**
     * 该类采用单例模式，不能实例化
     */
    private MyActivityManager() {
    }

    /**
     * 获取类实例对象
     *
     * @return MyActivityManager
     */
    public static MyActivityManager getInstance() {
        if (null == instance) {
            instance = new MyActivityManager();
        }
        return instance;
    }


    /**
     * 保存Activity到现有列表中
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }


    /**
     * 关闭保存的Activity
     */
    public void exit() {
        if (activityList != null) {
            Activity activity;

            for (int i = 0; i < activityList.size(); i++) {
                activity = activityList.get(i);

                if (activity != null) {
                    if (!activity.isFinishing()) {
                        activity.finish();
                    }

                    activity = null;
                }

                activityList.remove(i);
                i--;
            }
        }
    }
}
