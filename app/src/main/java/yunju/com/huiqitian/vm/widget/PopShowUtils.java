package yunju.com.huiqitian.vm.widget;

import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import yunju.com.huiqitian.R;

/**
 * Created by 张超群 on 2016-08-04.
 *
 * popwindow显示帮助类
 */
public class PopShowUtils {

    private static PopupWindow popupWindow;

    private static void initpopwindowAt(PopupWindow popupWindow, View view,Window window,View attbottom) {
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        WindowManager.LayoutParams params = window.getAttributes();
        //	params.alpha = 0.5f;
        window.setAttributes(params);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        // 点击其他地方消失
        popupWindow.setContentView(view);

        popupWindow.showAsDropDown(attbottom);

    }
    public static void closePopWindowPage(){
        if(popupWindow!=null&&popupWindow.isShowing()){
            popupWindow.dismiss();

        }
    }
    private static void initpopwindowStyle(PopupWindow popupWindow, View view,Window window,View bottom,View body) {

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        WindowManager.LayoutParams params = window.getAttributes();
        window.setAttributes(params);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        // 点击其他地方消失
        body.setVisibility(View.VISIBLE);
        popupWindow.setContentView(view);
        popupWindow.showAsDropDown(bottom);
    }


    public static PopupWindow intPopwindowStyle(final Window window, View view, View bottom, final View bodyView) {
        if(popupWindow!=null&&popupWindow.isShowing()){
            popupWindow.dismiss();
        }
        popupWindow = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = window.getAttributes();
                window.setAttributes(params);
                bodyView.setVisibility(View.GONE);
            }
        });
        initpopwindowStyle(popupWindow, view, window, bottom, bodyView);
        return popupWindow;
    }

    /**
     *初始化popwindow
     *
     * @param popupWindow
     * @param view
     * @param window
     * @param bottom
     */
    private static  void initpopwindow(PopupWindow popupWindow, View view,Window window,View bottom) {
        popupWindow.setAnimationStyle(R.style.anim_popup_dir);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        WindowManager.LayoutParams params= window.getAttributes();
        params.alpha = 0.5f;
        window.setAttributes(params);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        // 点击其他地方消
        popupWindow.setContentView(view);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 显示popwindow
     *
     * @param view 要压缩的布局文件
     * @param window 当前窗体的window
     * @param bottom 根布局
     * @return
     */
    public static  PopupWindow showPopwindow(View view, final Window window, View bottom){
        popupWindow = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);// 初始化

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.alpha = 1f;
                window.setAttributes(attributes);

            }
        });
        initpopwindow(popupWindow, view, window, bottom);

        return popupWindow;
    }


    private static  void initpopwindow(PopupWindow popupWindow, View view,Window window,View bottom,Boolean isCenter) {
        popupWindow.setAnimationStyle(R.style.anim_popup_dir);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        WindowManager.LayoutParams params= window.getAttributes();
        params.alpha = 0.5f;
        window.setAttributes(params);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        // 点击其他地方消
        popupWindow.setContentView(view);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }


    public static  PopupWindow showPopwindow(View view, final Window window, View bottom,Boolean isCenter){
        popupWindow = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);// 初始化

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.alpha = 1f;
                window.setAttributes(attributes);

            }
        });
        initpopwindow(popupWindow, view, window, bottom,isCenter);
        return popupWindow;
    }

}
