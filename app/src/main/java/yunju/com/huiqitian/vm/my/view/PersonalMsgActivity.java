package yunju.com.huiqitian.vm.my.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import cn.trinea.android.common.util.LogUtils;
import cn.trinea.android.common.util.ToastUtils;
import yunju.com.huiqitian.AppApplication;
import yunju.com.huiqitian.R;
import yunju.com.huiqitian.base.BaseActivity;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.entity.PersonMsgResp;
import yunju.com.huiqitian.utils.FileUitl;
import yunju.com.huiqitian.utils.ImageOptions;
import yunju.com.huiqitian.utils.ImageUpLoadUtils;
import yunju.com.huiqitian.utils.MyUtils;
import yunju.com.huiqitian.vm.my.model.PersonalMsgModel;
import yunju.com.huiqitian.vm.widget.CircleBitmapDisplayer;
import yunju.com.huiqitian.vm.widget.PopShowUtils;

/**
 * @author 张超群
 *         <p/>
 *         个人信息界面
 */
public class PersonalMsgActivity extends BaseActivity implements PersonalMsgModel.GetPersonMsgInterface, PersonalMsgModel.ModifyPersonSexInterface
        ,PersonalMsgModel.UploadPersonHeadInterface{

    /*title*/
    private TextView tvTitle;//标题
    private LinearLayout ivTitleBack;//返回键

    /*控件*/
    private ImageView ivPersonHead;//用户的头像
    private RelativeLayout rlPersonHeadChange;//个人头像改变
    private RelativeLayout rlPersonNickname;//昵称布局
    private TextView tvPersonNickname;//昵称显示
    private RelativeLayout rlPersonSex;//性别布局
    private TextView tvPersonSex;//性别显示
    private RelativeLayout rlPersonAccout;//账号布局
    private TextView tvPersonAccout;//账号显示
    private RelativeLayout rlPersonBindPhone;//绑定手机号布局
    private TextView tvPersonBindPhone;//绑定手机号显示

    /*popWindow相关*/
    private PopupWindow popupWindow;//要显示的popWindow
    private View view;//要压缩的布局文件
    private TextView popFirstShow;//popWindow第一条显示
    private TextView popSecondShow;//popWindow第二条显示
    private TextView popCancelShow;//popWindow取消按钮
    private int popMark = 0;

    /*个人信息的model*/
    private PersonalMsgModel personalMsgModel;//个人信息的数据源

    /*图片加载类*/
    private ImageLoader imageLoader = ImageLoader.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_personal_msg);

    }

    @Override
    public void initBoot() {
        personalMsgModel = new PersonalMsgModel(activity);
    }

    @Override
    public void initViews() {
       /*初始化控件*/
        tvTitle = findView(R.id.tv_title);
        ivTitleBack = findView(R.id.iv_title_back);
        ivPersonHead = findView(R.id.iv_person_head);
        rlPersonHeadChange = findView(R.id.rl_person_head_change);
        rlPersonNickname = findView(R.id.rl_person_nickname);
        tvPersonNickname = findView(R.id.tv_person_nickname);
        rlPersonSex = findView(R.id.rl_person_sex);
        tvPersonSex = findView(R.id.tv_person_sex);
        rlPersonAccout = findView(R.id.rl_person_account);
        tvPersonAccout = findView(R.id.tv_person_account);
        rlPersonBindPhone = findView(R.id.rl_person_bind_phone);
        tvPersonBindPhone = findView(R.id.tv_person_bind_phone);
        /*popWindow相关初始化*/
        view = LayoutInflater.from(activity).inflate(R.layout.pop_show_select_sex, null);
        popFirstShow = (TextView) view.findViewById(R.id.pop_first_show);
        popSecondShow = (TextView) view.findViewById(R.id.pop_second_show);
        popCancelShow = (TextView) view.findViewById(R.id.pop_cancel_show);
    }

    /*自定义圆形头像搭配imageLoader使用*/
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .displayer(new CircleBitmapDisplayer())
            .build();

    @Override
    public void initData(Bundle bundle) {
        tvTitle.setText(R.string.person_title);
        //如果用户的用户名不存在则直接获取数据
        if (AppApplication.getPreferenceHelper().getString(Constant.SHARED_PERSON_NAME, "").equals("")) {
            personalMsgModel.getPersonMsg();
        } else {
            if (!MyUtils.getHeadPath().equals("")) {
                String str = MyUtils.getHeadPath();
                LogUtils.error(PersonalMsgActivity.class,"头像不是空，取出来的路径是"+str);
                imageLoader.displayImage("file://" + MyUtils.getHeadPath(), ivPersonHead, options);
            } else {
                 /*加载头像*/
                imageLoader.displayImage(HttpConstant.ROOT_PATH + MyUtils.getPersonPicUrl(),
                        ivPersonHead, options);
            }

            /*昵称*/
            if (!MyUtils.getPersonNickname().equals("")) {
                tvPersonNickname.setText(MyUtils.getPersonNickname());
            }

            /*性别*/
            if (!MyUtils.getPersonSex().equals("")) {
               /* int sex = -1;
                if (MyUtils.getPersonSex().equals(" ")) {
                    sex = 0;
                }
                showSex(sex);*/
                String personSex = MyUtils.getPersonSex();
                if ("1".equals(personSex)){
                    tvPersonSex.setText("男");
                }else if ("2".equals(personSex)){
                    tvPersonSex.setText("女");
                }

            }

            /*账号*/
            if (!MyUtils.getPersonName().equals("")) {
                tvPersonAccout.setText(MyUtils.getPersonName());
            }

             /*绑定手机号*/
            if (!MyUtils.getPersonPhone().equals("")) {
                tvPersonBindPhone.setText(MyUtils.getPersonPhone());
            }
        }

    }

    /**
     * 显示性别
     *
     * @param sex 性别
     */
    private void showSex(int sex) {
        Log.e("sex", "性别：" + sex);
        switch (sex) {
            case 1://男
                tvPersonSex.setText(R.string.pop_show_man);
                break;
            case 2://女
                tvPersonSex.setText(R.string.pop_show_woman);
                break;
            case 0://保密
                tvPersonSex.setText(R.string.sex_secrecy);
                break;
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

        /*性别选择*/
        rlPersonSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popMark = 1;
                popFirstShow.setText(R.string.pop_show_man);
                popSecondShow.setText(R.string.pop_show_woman);
                popupWindow = PopShowUtils.showPopwindow(view, getWindow(), tvPersonSex);
            }
        });

        /*popWindow显示男*/
        popFirstShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if (popMark == 1) {
                    personalMsgModel.modifyNickName((byte) Constant.SEX_MAN);
                    showSex(Constant.SEX_MAN);
                } else {
                    openCamera();
                }
            }
        });

        /*popWindow显示女*/
        popSecondShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if (popMark == 1) {
                    personalMsgModel.modifyNickName((byte) Constant.SEX_WOMAN);
                    showSex(Constant.SEX_WOMAN);
                } else {
                    openGallery();
                }

            }
        });

         /*popWindow取消*/
        popCancelShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        /*修改头像*/
        rlPersonHeadChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popMark = 2;
                  /*popWindow设置相关显示*/
                popFirstShow.setText(R.string.pop_open_cammer);//打开相机
                popSecondShow.setText(R.string.pop_open_album);//打开图库
                popupWindow = PopShowUtils.showPopwindow(view, getWindow(), tvPersonSex);
            }
        });
    }

    /**
     * 点击跳转修改页面
     *
     * @param view
     */
    public void changeClick(View view) {
        switch (view.getId()) {
            case R.id.rl_person_nickname:
                //昵称修改
                Intent intent = new Intent(activity, ModifyActivity.class);
                intent.putExtra(Constant.MODIFY_KEY, Constant.MODIFY_NICKNAME);
                startActivityForResult(intent, Constant.MODIFY_NICKNAME);
                break;
            case R.id.rl_person_account:
                //账号修改(没给效果图没写)
                break;
            case R.id.rl_person_bind_phone:
                //手机号修改
                Intent intent1 = new Intent(activity, ModifyActivity.class);
                intent1.putExtra(Constant.MODIFY_KEY, Constant.MODIFY_BIND_PHONE);
                startActivityForResult(intent1, Constant.MODIFY_BIND_PHONE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null) {
            Bundle b = data.getExtras();
            switch (requestCode) {
                case Constant.MODIFY_NICKNAME:
                    //修改用户昵称
                    if (resultCode == RESULT_OK) {
                        tvPersonNickname.setText(b.getString(Constant.MODIFY_KEY));
                    }
                    break;
                case Constant.MODIFY_BIND_PHONE:
                    //修改用户电话
                    if (resultCode == RESULT_OK) {
                        tvPersonBindPhone.setText(b.getString(Constant.MODIFY_KEY));
                    }
                    break;
                case Constant.OPEN_CAMERA_CODE:
                    //打开相机
                    if (resultCode == Activity.RESULT_OK) {
                        final Bitmap photo = data.getParcelableExtra("data");
                        if (photo != null) {
                            String path = MyUtils.saveBitmap(photo, "yunju");
                            String base64 = ImageUpLoadUtils.Bitmap2StrByBase64(ImageUpLoadUtils.getSendBitmap(path));
                            saveAndShowHead(path, base64);
                        }
                    }
                    break;
                case Constant.OPEN_GALLERY_CODE:
                    //打开图库
                    if (resultCode == Activity.RESULT_OK) {
                        Uri uri = data.getData();
                        String ur = FileUitl.getRealFilePath(this, uri);
                        LogUtils.error(PersonalMsgActivity.class, "url:" + ur);
                        String base64 = ImageUpLoadUtils.Bitmap2StrByBase64(ImageUpLoadUtils.getSendBitmap(ur));
                        saveAndShowHead(ur, base64);
                    } else {
                        ToastUtils.show(activity, "获取图片失败!");
                    }
                    break;
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void personMsg(PersonMsgResp personMsgReq) {
        LogUtils.error(PersonalMsgModel.class, "信息" + personMsgReq);
        /*加载头像*/
        imageLoader.displayImage(HttpConstant.ROOT_PATH + personMsgReq.getPicUrl(),
                ivPersonHead, options);
        /*昵称*/
        tvPersonNickname.setText(personMsgReq.getNickName());
        /*性别*/
        showSex(Integer.parseInt(personMsgReq.getSex().toString()));
        /*账号*/
        tvPersonAccout.setText(personMsgReq.getName());
        /*绑定手机号*/
        tvPersonBindPhone.setText(personMsgReq.getMobile());
    }

    @Override
    public void noPersonMsg(String msg) {
        LogUtils.error(PersonalMsgModel.class, "没有：" + msg);
    }

    @Override
    public void modifySexSuccess(String msg) {

    }

    @Override
    public void modifySexFailure(String msg) {

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

    /**
     * 保存和显示用户头像
     *
     * @param path   头像路径
     * @param base64
     */
    public void saveAndShowHead(String path, String base64) {
        imageLoader.displayImage("file://" + path, ivPersonHead, ImageOptions.getDefaultOptions());
        MyUtils.saveHeadPath(path);
        personalMsgModel.modifyHeadPic(base64);
    }

    @Override
    public void uploadSuccess(String msg) {
        LogUtils.error(PersonalMsgActivity.class, msg);
        ToastUtils.show(activity, "头像上传结果：" + msg);
        imageLoader.displayImage("file://" + MyUtils.getHeadPath(), ivPersonHead, options);
    }

    @Override
    public void uploadFailure(String msg) {
        ToastUtils.show(activity, msg);
    }
}
