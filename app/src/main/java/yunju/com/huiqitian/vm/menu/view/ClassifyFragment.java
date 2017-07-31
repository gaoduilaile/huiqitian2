package yunju.com.huiqitian.vm.menu.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseFragment;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.entity.LeftClassifyReq;
import yunju.com.huiqitian.vm.adapter.LeftAdapter;
import yunju.com.huiqitian.vm.adapter.RightAdapter;
import yunju.com.huiqitian.vm.adapter.RightFirstAdapter;
import yunju.com.huiqitian.vm.classify.view.ClassifyResultActivity;
import yunju.com.huiqitian.vm.find.view.FindActivity;
import yunju.com.huiqitian.vm.menu.model.ClassifyModel;

/**
 * 分类页面
 * Created by CCTV-1 on 2016/11/25 0025.
 */
public class ClassifyFragment extends BaseFragment {

    private ClassifyModel classifyModel;
    private ListView leftListView;

    private List<LeftClassifyReq.ObjBean> fuList;
    private GridView rightGridView;
    private EditText etClassContent;
    private TextView tvChildTtile;
    private List<LeftClassifyReq.ObjBean.ChildsBean> childList;
    private LeftClassifyReq leftClassifyReq;

    /*无网络重新加载页面*/
    private LinearLayout llNoNetwork;
    /*无网络重新加载按钮*/
    private Button btnReload;

    /*分类布局*/
    private LinearLayout llClassify;
    /*分类无数据*/
    private TextView tvNoMarket;


    @Override
    public View bootView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_menu_class, viewGroup, false);
    }

    @Override
    public void initBoot() {
        /*实例化MenuFragmentModel*/
        classifyModel = new ClassifyModel(activity);
    }

    @Override
    public void initViews() {
        /*左边listView*/
        leftListView = findView(R.id.lv_left_class);
        /*右边的listView*/
        rightGridView = findView(R.id.lv_right_class);
        /*搜索的输入框*/
        etClassContent = findView(R.id.et_class_content);
        /*标题头*/
        tvChildTtile = findView(R.id.tv_child_title);
         /*分类布局*/
        llClassify = findView(R.id.ll_classify);
         /*分类无数据*/
        tvNoMarket = findView(R.id.tv_no_market);
        /*无网络重新加载页面*/
        llNoNetwork=findView(R.id.ll_no_network);
        /*无网络重新加载按钮*/
        btnReload=findView(R.id.btn_reload);

    }

    @Override
    public void initData(Bundle bundle) {

        if(noNetWork()){
            showNetWorkError();
            /*无网络情况下数据的显示隐藏*/
            tvNoMarket.setVisibility(View.GONE);
            llClassify.setVisibility(View.GONE);
            llNoNetwork.setVisibility(View.VISIBLE);
        }else {
            classifyModel.leftListView();
        }

        /*调取暴漏的接口，实现成功的操作*/
        classifyModel.LeftInterface(new ClassifyModel.LeftModelInterface() {
            @Override
            public void addSuccess(String obj) {
                /*解析网络数据*/
                leftClassifyReq = parseObject(obj, LeftClassifyReq.class);
                /*得到父的集合*/
                fuList = leftClassifyReq.getObj();
                if (fuList.size() > 0) {
                    /*页面的显示隐藏*/
                    llClassify.setVisibility(View.VISIBLE);
                    tvNoMarket.setVisibility(View.GONE);
                    llNoNetwork.setVisibility(View.GONE);
                     /*设置adapter显示*/
                    leftListView.setAdapter(new LeftAdapter(activity, fuList));

                    //得到右边第一条数据集合
                    List<LeftClassifyReq.ObjBean.ChildsBean> childFirstList = fuList.get(0).getChilds();

                    //调取成功后默认选中第一条
                    rightGridView.setAdapter(new RightFirstAdapter(activity, childFirstList));
                    tvChildTtile.setText(fuList.get(0).getName());

                /*没有点击父控件是默认的集合.后期用来赋值使用*/
                    childList = fuList.get(0).getChilds();
                } else {
                    /*无超市数据情况下的显示隐藏*/
                    llNoNetwork.setVisibility(View.GONE);
                    llClassify.setVisibility(View.GONE);
                    tvNoMarket.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void addFailure(String msg) {

            }
        });

    }

    @Override
    public void initEvents() {

        /*父控件的点击事件*/
        leftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*得到右边子集合*/
                childList = fuList.get(position).getChilds();
                /*点击左边的条目，显示右边的数据，设置右边的适配器*/
                rightGridView.setAdapter(new RightAdapter(activity, childList, fuList));
                /*设置显示标题头*/
                tvChildTtile.setText(fuList.get(position).getName());
                /*点击条目变色效果实现*/
                for (int i = 0; i < leftListView.getCount(); i++) {
                    setItem(i, R.color.color_999999, R.color.white);
                }
                setItem(position, R.color.color_text_title, R.color.color_f5f5f5);


            }
        });

        /*子条目的条目点击事件*/
        rightGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*获取分类组的名称,携带商品名称跳转*/
                String name = childList.get(position).getName();
                /*获取分类组的id*/
                int goodsId = childList.get(position).getId();
                Intent intent = new Intent(getContext(), ClassifyResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.GOODS_NAME, name);
                bundle.putInt("goodsId", goodsId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        /*输入框的点击事件*/
        etClassContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(FindActivity.class);
            }
        });

        /*无网络时重新加载*/
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noNetWork()){
                    showNetWorkError();
                }else {
                    classifyModel.leftListView();
                }
            }
        });

    }

    /**
     * ListView点击文字背景色的变化  position:当前的位置  id:控件id获取该控件  textColor:改变字体的颜色   bgColor:改变背景色的颜色
     */
    public void setItem(int position, int textColor, int backgroundColor) {
        LinearLayout linearLayout = (LinearLayout) leftListView.getChildAt(position);
        TextView textView = (TextView) linearLayout.findViewById(R.id.tv_list_class);
        textView.setTextColor(getResources().getColor(textColor));
        textView.setBackgroundColor(getResources().getColor(backgroundColor));
    }
}
