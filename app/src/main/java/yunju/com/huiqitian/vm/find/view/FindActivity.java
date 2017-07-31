package yunju.com.huiqitian.vm.find.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import cn.trinea.android.common.util.ToastUtils;
import yunju.com.huiqitian.AppApplication;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.vm.find.model.FindModel;
import yunju.com.huiqitian.vm.menu.model.MenuModel;
import yunju.com.huiqitian.vm.widget.DeleteDialog;
import yunju.com.huiqitian.vm.widget.TagCloudView;

public class FindActivity extends BaseActivity implements FindModel.GoodsInterface {

    /*云标签相关*/
    private TagCloudView normalTagView;//标准
    private LinkedHashSet<String> allTagsNormalSet = new LinkedHashSet<>();//整个标签存放集合 set
    private List<String> allTagsNormal = new ArrayList<>();//整个标签存放集合  list

    /*控件实例化*/
    private LinearLayout tvFindSearch;
    private EditText etFindContent;
    private ImageView ivFindDelete;
    private LinearLayout ivFindBack;

    /*数据的model*/
    private FindModel findModel;

    /*提示*/
    private DeleteDialog deleteDialog;

    /*删除和显示*/
    private LinearLayout lyFindDelete;

    private MenuModel menuModel;

    /*点击云标签的位置*/
    private int positions;
    /*判断是输入搜索还是云标签点击的标识*/
    private int type;//初始值为0，1表示云标签点击，2表示输入搜索；
    public static FindActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_find);
        instance = this;
    }

    @Override
    public void initBoot() {
        findModel = new FindModel(activity);
        menuModel=new MenuModel(activity);
    }

    @Override
    public void initViews() {
        normalTagView = findView(R.id.normalTag);//云标签

       /*控件实例化*/
        tvFindSearch = findView(R.id.tv_find_search);
        etFindContent = findView(R.id.et_find_content);
        ivFindDelete = findView(R.id.iv_find_delete);
        ivFindBack = findView(R.id.iv_find_back);

        lyFindDelete = findView(R.id.ly_find_delete);
    }

    @Override
    public void initData(Bundle bundle) {
        /*如果缓存中的数据不是空，则设置显示*/
        if (!AppApplication.getAllTagsNormal().isEmpty()||AppApplication.getAllTagsNormal().size()!=0) {

            allTagsNormal.clear();
            allTagsNormal = AppApplication.getAllTagsNormal();

            /*将list集合添加到set集合中去重*/
            allTagsNormalSet.clear();
            allTagsNormalSet.addAll(allTagsNormal);

            allTagsNormal.clear();
            allTagsNormal.addAll(allTagsNormalSet);
            normalTagView.setTags(allTagsNormal);//设置标签

            lyFindDelete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initEvents() {

        /*标签点击事件*/
        normalTagView.setOnTagClickListener(new TagCloudView.OnTagClickListener() {
            @Override
            public void onTagClick(int position) {
                positions=position;
                type=1;
                findModel.searchGoods(1, 20, allTagsNormal.get(position), "qty", "desc");//销量倒叙

            }
        });

        /*删除标签的按钮*/
        ivFindDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        /*搜索按钮*/
        tvFindSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty(etFindContent)) {
                    type=2;
                    findModel.searchGoods(1, 20, etFindContent.getText().toString(), "qty", "desc");//销量倒叙
                } else {
                    ToastUtils.show(getApplicationContext(), "搜索内容为空");
                }
            }
        });

        /*返回按钮*/
        ivFindBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        /*软键盘的搜索*/
        etFindContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    type=2;
                    findModel.searchGoods(1, 20, etFindContent.getText().toString(), "qty", "desc");//销量倒叙
                }
                return false;
            }
        });

        /*获取不到当前位置，从新调取经纬度接口重新加载*/
        menuModel.setLocateInterface(new MenuModel.LocateInterface() {
            @Override
            public void LocateSuccess(String mes) {
                if(type==1){
                    findModel.searchGoods(1, 20, allTagsNormal.get(positions), "qty", "desc");//销量倒叙
                }else if(type==2){
                    findModel.searchGoods(1, 20, etFindContent.getText().toString(), "qty", "desc");//销量倒叙
                }
            }

            @Override
            public void LocateFailure(String mes) {

            }
        });
    }


    @Override
    public void goodsInfo(String resp,String name) {
        allTagsNormalSet.add(name);
        allTagsNormal.clear();
        allTagsNormal.addAll(allTagsNormalSet);
        normalTagView.setTags(allTagsNormal);//设置标签

        if (allTagsNormal == null) {
            lyFindDelete.setVisibility(View.GONE);
        } else {
            lyFindDelete.setVisibility(View.VISIBLE);
        }

        /*加入缓存*/
        AppApplication.setAllTagsNormal(allTagsNormal);

        /*跳到收索结果界面*/
        Bundle bundle = new Bundle();
        bundle.putString(Constant.GOODS_INFO, resp);
        bundle.putString(Constant.GOODS_NAME, etFindContent.getText().toString());
        /*1标示从主页跳转过去的*/
        bundle.putInt("tag",1);
        startActivity(FindResultActivity.class, bundle);
    }

    @Override
    public void noGoods(String msg) {
        showToast(msg);
    }

    @Override
    public void addSuccess() {

    }


    @Override
    public void addFailure(String msg) {

    }

    //获取不到当前位置
    @Override
    public void LocateError(String mes) {
        menuModel.buyerLocate(Double.parseDouble(MyUtils.getLog()), Double.parseDouble(MyUtils.getLag()));
    }

    /*删除标签*/
    public void delete() {
        allTagsNormal.clear();//清空列表

        normalTagView.setTags(allTagsNormal);//重新设置标签

        /*将空集合加入缓存*/
        AppApplication.setAllTagsNormal(allTagsNormal);

        lyFindDelete.setVisibility(View.GONE);
    }

    /*弹窗 提示*/
    private void showAlertDialog() {
        LayoutInflater inflaterDl = LayoutInflater.from(FindActivity.this);
        View mView = inflaterDl.inflate(R.layout.dialog_normal_layout, null);
        //对话框
        final Dialog dialog = new AlertDialog.Builder(FindActivity.this).create();
        dialog.show();
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setContentView(mView);
                /*设置dialog出现在屏幕的位置*/
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        lp.y = 600; // 新位置Y坐标
        dialogWindow.setAttributes(lp);

        TextView positiveBtn = (TextView) dialog.findViewById(R.id.btn_ok);
        TextView negativeBtn = (TextView) dialog.findViewById(R.id.btn_cancle);
        TextView tvContent = (TextView) dialog.findViewById(R.id.tv_content);
        tvContent.setText("你确定要删除搜索记录吗?");

        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
                dialog.dismiss();
            }
        });
    }
}
