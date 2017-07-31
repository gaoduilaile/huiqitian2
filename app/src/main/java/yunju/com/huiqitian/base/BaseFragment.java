package yunju.com.huiqitian.base;



import cn.trinea.android.common.base.CommonFragment;

/**
 * 基础Fragment
 * Created by 高英祥 on 2016/7/11 0011.
 */
public abstract class BaseFragment extends CommonFragment {

    /**
     * 判断没有网络
     *
     * @return
     */
    public boolean noNetWork() {
        return !hasNetWork();
    }

    /**
     * 无网络的提醒
     */
    public void showNetWorkError() {
        showToast("请检查您的网络!");
    }
}