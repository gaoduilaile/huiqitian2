package yunju.com.huiqitian.base;


import android.view.View;
import android.widget.ImageButton;

import cn.trinea.android.common.base.CommonActivity;


/**
 * 基础activity
 * Created by 高英祥 on 2016/7/11 0011.
 *
 */
public abstract class BaseActivity extends CommonActivity {

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

    /**
     * 设置返回按钮,并实现Finish()
     *
     * @param ibBack
     */
    public void setBackButton(ImageButton ibBack) {

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}

