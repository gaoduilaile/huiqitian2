package yunju.com.huiqitian.vm.orders.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.trinea.android.common.util.LogUtils;
import cn.trinea.android.common.util.ToastUtils;
import yunju.com.huiqitian.AppApplication;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.entity.EvalTag;
import yunju.com.huiqitian.entity.OrderGoods;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.entity.EvaluationCommitReq;
import yunju.com.huiqitian.http.entity.EvaluationOrderResp;
import yunju.com.huiqitian.utils.FileUitl;
import yunju.com.huiqitian.utils.ImageOptions;
import yunju.com.huiqitian.utils.ImageUpLoadUtils;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.vm.adapter.EvaluationGridViewAdapter;
import yunju.com.huiqitian.vm.orders.model.EvaluationModel;
import yunju.com.huiqitian.vm.widget.MyGridView;
import yunju.com.huiqitian.vm.widget.MyToggleButton;
import yunju.com.huiqitian.vm.widget.PopShowUtils;
import yunju.com.huiqitian.vm.widget.TagCloudView;

/**
 * 评价订单页面
 */
public class EvaluationActivity extends BaseActivity implements EvaluationModel.EvaluationOrderInterface {

    /*title*/
    private TextView tvTitle;//标题
    private LinearLayout ivTitleBack;//返回键
    private ImageView ivTitleShare;//右边图片按钮

    /*商品相关*/
    private ImageView ivMyListGoodPic;//商品图片显示
    private TextView tvMyListGoodDetailsDiscr;//商品描述
    private List<OrderGoods> orderGoodsList;//总的商品
    private int orderGoodsIndex = 0;

    /*云标签相关*/
    private TagCloudView tcvEvaluationNormalTag;//云标签
    private List<String> allTagsNormal = new ArrayList<>();//整个标签存放集合
    private List<EvalTag> allTagsContent = new ArrayList<>();//存放所有标签信息内容集合
    private List<Integer>tagId=new ArrayList<>();//存放所有标签编号集合
    private HashMap<Integer,Integer> tagIds = new HashMap<>();//存放所有选中标签编号集合
    private HashMap<Integer, Boolean> isTagSelected;//存放所有的文字标签是否被选中

    private EditText etEvaluationContent;//评价内容
    /*照片选择*/
    private MyGridView mgvEvaluationGridView;//显示用户选择的照片
    private PopupWindow popupWindow;//显示popWindow提示用户选择打开照片方式
    private View viewHead;//要压缩的布局文件
    private TextView popFirstShow;//popWindow第一条显示
    private TextView popSecondShow;//popWindow第二条显示
    private TextView popCancelShow;//popWindow取消按钮
    private ArrayList<HashMap<String, String>> imageItem;//用户照片选择存放集合
    private EvaluationGridViewAdapter evaluationGridViewAdapter;//照片显示适配器
    private int picNum = 0;//记录照片的数量

    /*评价*/
    private LinearLayout llGoodEvaluation;//好评
    private ImageView ivGoodEvaluation;
    private int goodMark = 0;
    private LinearLayout llMediumEvaluation;//中评
    private ImageView ivMediumEvaluation;
    private int mediumMark = 0;
    private LinearLayout llBadEvaluation;//差评
    private ImageView ivBadEvaluation;
    private int badMark = 0;

    /*匿名评价*/
    private LinearLayout llEvaluationAnonymous;//匿名总布局
    private MyToggleButton mtbBtnEvaluationSelect;//是否匿名
    private int anonymousMark = 0;//匿名标志

    /*提交订单评价*/
    private TextView tvEvaluationCommit;

    /*订单评价model*/
    private EvaluationModel evaluationModel;

    /*图片加载类*/
    private ImageLoader imageLoader = ImageLoader.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_evaluation);
    }

    @Override
    public void initBoot() {
        evaluationModel = new EvaluationModel(activity);
        imageItem = new ArrayList<HashMap<String, String>>();
        orderGoodsList = new ArrayList<>();
        isTagSelected = new HashMap<>();
        evaluationGridViewAdapter = new EvaluationGridViewAdapter(activity, imageItem);
    }

    @Override
    public void initViews() {
        /*标题栏*/
        tvTitle = findView(R.id.tv_title);
        ivTitleBack = findView(R.id.iv_title_back);
        ivTitleShare = findView(R.id.iv_title_share);

        tcvEvaluationNormalTag = findView(R.id.tcv_evaluation_normalTag);
        etEvaluationContent = findView(R.id.et_evaluation_content);
        mgvEvaluationGridView = findView(R.id.mgv_evaluation_grid_view);
         /*评价*/
        llGoodEvaluation = findView(R.id.ll_good_evaluation);
        ivGoodEvaluation = findView(R.id.iv_good_evaluation);
        llMediumEvaluation = findView(R.id.ll_medium_evaluation);
        ivMediumEvaluation = findView(R.id.iv_medium_evaluation);
        llBadEvaluation = findView(R.id.ll_bad_evaluation);
        ivBadEvaluation = findView(R.id.iv_bad_evaluation);
          /*匿名评价*/
        llEvaluationAnonymous = findView(R.id.ll_evaluation_anonymous);
        mtbBtnEvaluationSelect = findView(R.id.mtb_btn_evaluation_select);

        /*提交评价*/
        tvEvaluationCommit = findView(R.id.tv_evaluation_commit);

        /*popWindow相关初始化*/
        viewHead = LayoutInflater.from(activity).inflate(R.layout.pop_show_select_sex, null);
        popFirstShow = (TextView) viewHead.findViewById(R.id.pop_first_show);
        popSecondShow = (TextView) viewHead.findViewById(R.id.pop_second_show);
        popCancelShow = (TextView) viewHead.findViewById(R.id.pop_cancel_show);

        /*商品图片及文字介绍*/
        ivMyListGoodPic = findView(R.id.iv_my_list_good_pic);
        tvMyListGoodDetailsDiscr = findView(R.id.tv_my_list_good_details_discr);
    }

    @Override
    public void initData(Bundle bundle) {
        //设置标题栏
        tvTitle.setText(R.string.evaluation_and_share_order);
        //标题栏右边的图标
        ivTitleShare.setVisibility(View.VISIBLE);
        ivTitleShare.setImageResource(R.mipmap.img_evaluation_order);

        /*像适配器中添加值*/
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("itemImage", " ");
        imageItem.add(map);
        mgvEvaluationGridView.setAdapter(evaluationGridViewAdapter);

        /*popWindow设置相关显示*/
        popFirstShow.setText(R.string.pop_open_cammer);//打开相机
        popSecondShow.setText(R.string.pop_open_album);//打开图库

        //拿到订单ID
        String orderId = bundle.getString(Constant.EVALUATION_ORDER);

        if (!TextUtils.isEmpty(orderId)) {
            //获取评价相关的内容
            evaluationModel.getOrders(orderId);
        }

    }

    @Override
    public void initEvents() {

        /*返回键*/
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*标签监听*/
        tcvEvaluationNormalTag.setOnTagClickListener(new TagCloudView.OnTagClickListener() {
            @Override
            public void onTagClick(int position) {
                //点击标签后设置背景色
                tcvEvaluationNormalTag.setTagsByPosition(getIsTagSelected(isTagSelected, position),
                        allTagsNormal);

            }
        });

        /*当点击某一项时*/
        mgvEvaluationGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == (imageItem.size() - 1)) {
                    if (evaluationGridViewAdapter.getCount() > 5) {//最多五张
                        ToastUtils.show(activity, "最多五张图片!");
                        return;
                    }
                    //显示pop
                    popupWindow = PopShowUtils.showPopwindow(viewHead, getWindow(), tvEvaluationCommit);
                }
            }
        });

        /*当点击删除某个照片时，对应从集合中删除*/
        evaluationGridViewAdapter.setOnItemClick(new EvaluationGridViewAdapter.OnDeleteItemClick() {
            @Override
            public void OnItemDeleteClickListeners(int position) {
                picNum--;
                imageItem.remove(position);
                evaluationGridViewAdapter.notifyDataSetChanged();
            }
        });

         /*popWindow打开相机*/
        popFirstShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                openCamera();
            }
        });

        /*popWindow打开图库*/
        popSecondShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                openGallery();
            }
        });

         /*popWindow取消*/
        popCancelShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        /*好评按钮*/
        llGoodEvaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodMark++;
                if (goodMark == 1) {
                    setEvaluationSelect();
                    mediumMark = 0;
                    badMark = 0;
                    ivGoodEvaluation.setBackgroundResource(R.mipmap.good_evaluation);
                } else {
                    setEvaluationSelect();
                    goodMark = 0;
                    mediumMark = 0;
                    badMark = 0;
                }

            }
        });

        /*中评*/
        llMediumEvaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediumMark++;
                if (mediumMark == 1) {
                    setEvaluationSelect();
                    goodMark = 0;
                    badMark = 0;
                    ivMediumEvaluation.setBackgroundResource(R.mipmap.middle_evaluation);
                } else {
                    setEvaluationSelect();
                    goodMark = 0;
                    mediumMark = 0;
                    badMark = 0;
                }

            }
        });

        /*差评*/
        llBadEvaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                badMark++;
                if (badMark == 1) {
                    setEvaluationSelect();
                    goodMark = 0;
                    mediumMark = 0;
                    ivBadEvaluation.setBackgroundResource(R.mipmap.bad_evaluation);
                } else {
                    setEvaluationSelect();
                    goodMark = 0;
                    mediumMark = 0;
                    badMark = 0;
                }
            }
        });

        /*匿名评价*/
        llEvaluationAnonymous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anonymousMark++;
                if (anonymousMark == 1) {
                    //匿名评价
                    mtbBtnEvaluationSelect.setToggleOn();
                } else {
                    //不匿名评价
                    mtbBtnEvaluationSelect.setToggleOff();
                    anonymousMark = 0;
                }
            }
        });


        /*是否是匿名评价*/
        mtbBtnEvaluationSelect.setOnToggleChanged(new MyToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on) {
                    anonymousMark = 1;
                } else {
                    anonymousMark = 0;
                }
            }
        });

        /*提交评价*/
        tvEvaluationCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EvaluationCommitReq evaluationCommitReq = new EvaluationCommitReq();
                /*获取输入框的内容*/
                String trim = etEvaluationContent.getText().toString().trim();
                if (!TextUtils.isEmpty(trim)) {
                    if (goodMark==1||mediumMark==1||badMark==1){
                        //判断如果用户的评价商品索引值大于
                        if (orderGoodsIndex <= orderGoodsList.size()) {
                            LogUtils.error(EvaluationActivity.class,"orderGoodsIndex......................="+orderGoodsIndex);
                            //是否匿名
                            evaluationCommitReq.setCryptonym((byte) anonymousMark);
                            //评价等级
                            if (goodMark == 1) {
                                evaluationCommitReq.setEvalLevelId(Constant.RIGHT);
                            }

                            if (mediumMark == 1) {
                                evaluationCommitReq.setEvalLevelId(Constant.NINE);
                            }

                            if (badMark == 1) {
                                evaluationCommitReq.setEvalLevelId(Constant.TEN);
                            }
                            //评价图片
                            //String[] fils = new String[imageItem.size() - 1];
                            List<String> fils = new ArrayList<String>();
                            for (int i = 0; i < imageItem.size() - 1; i++) {
                                fils.add(ImageUpLoadUtils.Bitmap2StrByBase64(ImageUpLoadUtils.getSendBitmap(imageItem.get(i).get("itemImage"))));
                            }
                            evaluationCommitReq.setFiles(fils);
                            //标签集合
                            List<Integer> tags = new ArrayList<Integer>();
                            for (Map.Entry<Integer, Integer> entry : tagIds.entrySet()) {
                                tags.add(Integer.valueOf(entry.getValue().toString()));
                            }
                            evaluationCommitReq.setTagIds(tags);
                            //评价内容
                            evaluationCommitReq.setEvalText(etEvaluationContent.getText().toString());
                            //订单Id
                            evaluationCommitReq.setOrderGoodsId(orderGoodsList.get(orderGoodsIndex - 1).getOrderGoodsId());
                            LogUtils.error(EvaluationActivity.class, "开始提交评价了。。。。。。。。。。。。。。。。。。。。。。。。");
                            evaluationModel.evaluationOrders(evaluationCommitReq);
                        }
                    }else {
                        ToastUtils.show(activity,"请选择评价点");
                    }
                }else {
                    ToastUtils.show(activity,"请输入评价内容");
                }


            }

        });

    }

    /**
     * 数据恢复原来的状态
     */
    public void restoreState() {
        LogUtils.error(EvaluationActivity.class,"第二次评价第二次评价第二次评价第二次评价第二次评价第二次评价第二次评价");
        orderGoodsIndex++;
        picNum=0;//照片数量
        imageItem.clear();//用户照片选择存放集合
         /*像适配器中添加值*/
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("itemImage", " ");
        imageItem.add(map);
        evaluationGridViewAdapter.notifyDataSetChanged();
        tagIds.clear();//存放所有标签编号集合
        setEvaluationSelect();//设置默认状态
        mtbBtnEvaluationSelect.setToggleOff();//把toggleButton设置为默认状态
        mediumMark = 0;//中评
        goodMark = 0;//好评
        badMark = 0;//差评
        anonymousMark = 0;//匿名标志
        etEvaluationContent.setText("");
        isTagSelected = getIsTagSelected(allTagsNormal);
        tcvEvaluationNormalTag.setTags(allTagsNormal);//设置标签


    }

    @Override
    public void evaluationDateSuccess(EvaluationOrderResp evaluationOrderResp) {

        if (evaluationOrderResp != null) {
             /*设置商品展示*/
            orderGoodsList = evaluationOrderResp.getObj().getAppOrderGoods();
             /*加载商品图片*/
            imageLoader.displayImage(HttpConstant.ROOT_PATH + orderGoodsList.get(orderGoodsIndex).getPicUrl(),
                    ivMyListGoodPic, ImageOptions.getDefaultOptions());
            /*加载商品文字显示*/
            tvMyListGoodDetailsDiscr.setText(orderGoodsList.get(orderGoodsIndex).getName());
            orderGoodsIndex++;

             /*把标签的值设置到云标签*/
            for (int i = 0; i < evaluationOrderResp.getObj().getEvaltag().size(); i++) {
                //添加云标签
                allTagsNormal.add(evaluationOrderResp.getObj().getEvaltag().get(i).getTagDesc());
                //把标签详细添加到集合中
                allTagsContent.add(evaluationOrderResp.getObj().getEvaltag().get(i));
                //把标签编号放到集合中
                tagId.add(evaluationOrderResp.getObj().getEvaltag().get(i).getId());
            }
            isTagSelected = getIsTagSelected(allTagsNormal);
            tcvEvaluationNormalTag.setTags(allTagsNormal);//设置标签
        }

    }

    @Override
    public void evaluationOrderSuccess(String msg) {
        //订单取消是我的页面刷新
        AppApplication.putBoolean("person_fragment_refresh", true);
        if (orderGoodsIndex == orderGoodsList.size()) {
            LogUtils.error(EvaluationActivity.class,"orderGoodsIndex="+orderGoodsIndex);
            finish();
        } else {
            restoreState();
             /*加载商品图片*/
            imageLoader.displayImage(HttpConstant.ROOT_PATH + orderGoodsList.get(orderGoodsIndex-1).getPicUrl(),
                    ivMyListGoodPic, ImageOptions.getDefaultOptions());
            /*加载商品文字显示*/
            tvMyListGoodDetailsDiscr.setText(orderGoodsList.get(orderGoodsIndex-1).getName());
        }
        //提交订单成功
        ToastUtils.show(activity, "评论成功");
    }

    @Override
    public void evaluationFailure(String msg) {
        ToastUtils.show(activity, "评论失败，请检查网络");
    }


    /**
     * 照片选择相关回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (picNum <= 5) {
            if (resultCode == 1)
                return;
            switch (requestCode) {
                case Constant.OPEN_CAMERA_CODE: //拍照
                    if (resultCode == Activity.RESULT_OK) {
                        final Bitmap photo = data.getParcelableExtra("data");
                        if (photo != null) {
                            String path = MyUtils.saveBitmap(photo, "yunju");
                            //打印base64
                            Log.e("base", "base64:" + ImageUpLoadUtils.Bitmap2StrByBase64(ImageUpLoadUtils.getSendBitmap(path)));

                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("itemImage", path);
                            imageItem.add(0, map);
                            evaluationGridViewAdapter.notifyDataSetChanged();
                            picNum++;

                        }
                    }
                    break;
                case Constant.OPEN_GALLERY_CODE://打开图库
                    if (resultCode == Activity.RESULT_OK) {
                        Uri uri = data.getData();
                        String ur = FileUitl.getRealFilePath(this, uri);

                        LogUtils.error(EvaluationActivity.class, "url:" + ur);
                        Log.e("EvaluationActivity", "url:" + ur);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("itemImage", ur);
                        imageItem.add(0, map);
                        evaluationGridViewAdapter.notifyDataSetChanged();
                        picNum++;

                    } else {
                        ToastUtils.show(activity, "获取图片失败!");
                    }

                    break;

                default:
                    break;
            }
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            ToastUtils.show(activity, "最多五张图片!");
        }
    }

    /**
     * 设置评价选中
     */
    public void setEvaluationSelect() {
        ivGoodEvaluation.setBackgroundResource(R.mipmap.default_evaluation);
        ivBadEvaluation.setBackgroundResource(R.mipmap.default_evaluation);
        ivMediumEvaluation.setBackgroundResource(R.mipmap.default_evaluation);
    }

    /**
     * 设置所有的tag都是未选中状态
     *
     * @param allTagsNormal 所有的tag标签
     * @return
     */
    public HashMap<Integer, Boolean> getIsTagSelected(List<String> allTagsNormal) {
        HashMap<Integer, Boolean> hashMap = new HashMap<>();
        for (int j = 0; j < allTagsNormal.size(); j++) {
            hashMap.put(j, false);
        }
        return hashMap;
    }


    /**
     * 设置所有的tag都是未选中状态
     *
     * @param isTagSelecte 所有的tag标签是否被选中标签
     * @param position     代表变化的位置
     * @return
     */
   /* public HashMap<Integer, Boolean> getIsTagSelected(HashMap<Integer, Boolean> isTagSelecte, int position) {
        LogUtils.error(EvaluationActivity.class,"000000000....................."+tagIds.size());
        for (int i = 0; i < isTagSelecte.size(); i++) {
            if (position == i) {
                if (isTagSelected.get(position)) {
                    isTagSelected.put(position, false);
                    //从集合中移除
                    tagIds.remove(allTagsContent.get(position).getId());
                } else {
                    isTagSelected.put(position, true);
                    //添加到集合中
                    tagIds.add(allTagsContent.get(position).getId());
                }
            } else {
                if (isTagSelected.get(i)) {
                    isTagSelected.put(i, true);
                    tagIds.add(allTagsContent.get(i).getId());
                } else {
                    isTagSelected.put(i, false);
                    tagIds.remove(allTagsContent.get(i).getId());
                }
            }
        }
        return isTagSelected;
    }*/
    public HashMap<Integer, Boolean> getIsTagSelected(HashMap<Integer, Boolean> isTagSelecte, int position) {
        LogUtils.error(EvaluationActivity.class, "000000000....................." + tagIds.size());
        for (int i = 0; i < isTagSelecte.size(); i++) {
            if (position == i) {
                if (isTagSelected.get(position)) {
                    isTagSelected.put(position, false);
                    LogUtils.error(EvaluationActivity.class,"11111111111111111111111");
                    //从集合中移除
                    tagIds.remove(i);
                } else {
                    LogUtils.error(EvaluationActivity.class,"2222222222222222222222222");
                    isTagSelected.put(position, true);
                    //添加到集合中
                    tagIds.put(i, tagId.get(i));
                }
            } else {
                LogUtils.error(EvaluationActivity.class,"3333333333333333333333333");
                if (isTagSelected.get(i)) {
                    LogUtils.error(EvaluationActivity.class, "44444444444444444444");
                    isTagSelected.put(i, true);
                    tagIds.put(i,tagId.get(i));
                } else {
                    LogUtils.error(EvaluationActivity.class, "5555555555555555555555555");
                    isTagSelected.put(i, false);
                }
            }
        }
        return isTagSelected;
    }


    /**
     * 打开相机
     */
    public void openCamera() {// 打开相机
        Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(getImageByCamera, Constant.OPEN_CAMERA_CODE);
    }

    /**
     * 打开相册
     */
    public void openGallery() {// 打开相册
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, Constant.OPEN_GALLERY_CODE);
    }
}
