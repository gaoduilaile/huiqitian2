package yunju.com.huiqitian.vm.opinion.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.http.entity.OpinionCommitReq;
import yunju.com.huiqitian.vm.opinion.model.OpinionModel;

/**
 * @author 张超群
 *         <p/>
 *         意见反馈界面
 */
public class OpinionActivity extends BaseActivity implements OpinionModel.OpinionCommitSuccessInterface {

    /*title*/
    private LinearLayout ivTitleBack;//返回键
    private TextView tvTitle;//标题
    private TextView tvTitleShare;//用来设置右边标题
    private RelativeLayout rlTitleShare;//设置点击事件

    private EditText tvOpinionContent;//评价内容
    private TextView tvOpinionCommit;//提交


    /*照片选择*/
    /*private GridView gvOpinionPic;//评价图片
    private PopupWindow popupWindow;//显示popWindow提示用户选择打开照片方式
    private View viewHead;//要压缩的布局文件
    private TextView popFirstShow;//popWindow第一条显示
    private TextView popSecondShow;//popWindow第二条显示
    private TextView popCancelShow;//popWindow取消按钮
    private ArrayList<HashMap<String, String>> imageItem;//用户照片选择存放集合
    private OpinionGridViewAdapter opinionGridViewAdapter;//照片显示适配器
    private int picNum = 0;//记录照片的数量*/

    /*意见反馈的model*/
    private OpinionModel opinionModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_opinion);

    }

    @Override
    public void initBoot() {
        /*imageItem = new ArrayList<HashMap<String, String>>();
        opinionGridViewAdapter = new OpinionGridViewAdapter(activity, imageItem);*/
        opinionModel = new OpinionModel(activity);
    }

    @Override
    public void initViews() {
        /*title*/
        ivTitleBack = findView(R.id.iv_title_back);
        tvTitle = findView(R.id.tv_title);
        //tvTitleShare = findView(R.id.tv_title_share);
        // rlTitleShare = findView(R.id.rl_title_share);
        tvOpinionContent = findView(R.id.tv_opinion_content);
       /* gvOpinionPic = findView(R.id.gv_opinion_pic);*/
        tvOpinionCommit = findView(R.id.tv_opinion_commit);

        /*popWindow相关初始化*/
        /*viewHead = LayoutInflater.from(activity).inflate(R.layout.pop_show_select_sex, null);
        popFirstShow = (TextView) viewHead.findViewById(R.id.pop_first_show);
        popSecondShow = (TextView) viewHead.findViewById(R.id.pop_second_show);
        popCancelShow = (TextView) viewHead.findViewById(R.id.pop_cancel_show);*/
    }

    @Override
    public void initData(Bundle bundle) {
        tvTitle.setText(R.string.opinion_title);
        //  tvTitleShare.setVisibility(View.VISIBLE);//用来设置右边标题
        // tvTitleShare.setText(R.string.opinion_commit);

         /*popWindow设置相关显示*/
        /*popFirstShow.setText(R.string.pop_open_cammer);//打开相机
        popSecondShow.setText(R.string.pop_open_album);//打开图库*/

         /*像适配器中添加值*/
        /*HashMap<String, String> map = new HashMap<String, String>();
        map.put("itemImage", " ");
        imageItem.add(map);
        gvOpinionPic.setAdapter(opinionGridViewAdapter);*/
    }

    @Override
    public void initEvents() {
       /*  *//*判断pop是否关闭*//*
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }*/

          /*返回键响应事件*/
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

         /*当点击某一项时*//*
        gvOpinionPic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == (imageItem.size() - 1)) {
                    if (opinionGridViewAdapter.getCount() > 5) {//最多五张
                        ToastUtils.show(activity, "最多五张图片!");
                        return;
                    }
                    //显示pop
                    popupWindow = PopShowUtils.showPopwindow(viewHead, getWindow(), gvOpinionPic);
                }
            }
        });

         *//*当点击删除某个照片时，对应从集合中删除*//*
        opinionGridViewAdapter.setOnItemClick(new OpinionGridViewAdapter.OnDeleteItemClick() {
            @Override
            public void OnItemDeleteClickListeners(int position) {
                picNum--;
                imageItem.remove(position);
                opinionGridViewAdapter.notifyDataSetChanged();
            }
        });

         *//*popWindow打开相机*//*
        popFirstShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                openCamera();
            }
        });

        *//*popWindow打开图库*//*
        popSecondShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                openGallery();
            }
        });

         *//*popWindow取消*//*
        popCancelShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
*/
//        /*提交意见反馈*/
//        rlTitleShare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO: 2016-08-13   提交意见反馈
//                opinionCommit();
//            }
//        });

        /*提交反馈按钮*/
        tvOpinionCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016-08-13 提交意见反馈
                /*opinionCommit();*/
                OpinionCommitReq opinionCommitReq = new OpinionCommitReq();
                if (tvOpinionContent.getText().toString() != null && !tvOpinionContent.getText().toString().equals("")) {
                    if (tvOpinionContent.length() <= 150) {
                        opinionCommitReq.setText(tvOpinionContent.getText().toString());
                        opinionModel.commitOpinion(opinionCommitReq);
                    } else {
                        showToast("反馈最多为150字");
                    }
                } else {
                    showToast("请输入评价类容");
                }

            }
        });

    }

    /**
     * 提交意见反馈
     */
   /* public void opinionCommit() {
        OpinionCommitReq opinionCommitReq = new OpinionCommitReq();
        //评价图片
        List<String> fils = new ArrayList<>();
//        String[] fils = new String[imageItem.size() - 1];
        for (int i = 0; i < imageItem.size() - 1; i++) {
//            Log.e("base", "base64:" +
//                    ImageUpLoadUtils.Bitmap2StrByBase64(ImageUpLoadUtils.getSendBitmap(imageItem.get(i).get("itemImage"))));
            fils.add(ImageUpLoadUtils.Bitmap2StrByBase64(ImageUpLoadUtils.getSendBitmap(imageItem.get(i).get("itemImage"))));
        }
        opinionCommitReq.setFiles(fils);
        LogUtils.error(OpinionActivity.class, "数组长度" + fils.size() + "第一条数据:" + fils.get(0));

        String text = tvOpinionContent.getText().toString();
        if (!TextUtils.isEmpty(text)) {
            if (text.length() > 240 && text.length() > 0) {
                ToastUtils.show(activity, "请按规定输入问题字数");
            } else {
                opinionCommitReq.setText(text);
                opinionModel.commitOpinion(opinionCommitReq);
            }
        } else {
            ToastUtils.show(activity, "请输入反馈内容！");
        }
    }

    *//**
     * 打开相机
     *//*
    public void openCamera() {// 打开相机
        Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(getImageByCamera, Constant.OPEN_CAMERA_CODE);
    }

    *//**
     * 打开相册
     *//*
    public void openGallery() {// 打开相册
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, Constant.OPEN_GALLERY_CODE);
    }

    */

    /**
     * 照片选择相关回调
     *//*
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
                            opinionGridViewAdapter.notifyDataSetChanged();
                            picNum++;

                        }
                    }
                    break;
                case Constant.OPEN_GALLERY_CODE://打开图库
                    if (resultCode == Activity.RESULT_OK) {
                        Uri uri = data.getData();
                        String ur = FileUitl.getRealFilePath(this, uri);

                        LogUtils.error(EvaluationActivity.class, "url...............:" + ur);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("itemImage", ur);
                        imageItem.add(0, map);
                        opinionGridViewAdapter.notifyDataSetChanged();
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
    }*/
    @Override
    public void opinionCommitSuccess(String obj) {
        /*LogUtils.error(OpinionActivity.class, "成功结果：" + obj);*/
        showToast("意见反馈成功");
        finish();
    }

    @Override
    public void opinionCommitFailure(String msg) {
        LogUtils.error(OpinionActivity.class, "失败结果：" + msg);
    }
}
