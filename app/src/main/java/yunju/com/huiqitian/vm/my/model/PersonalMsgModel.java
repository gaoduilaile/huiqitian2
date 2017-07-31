package yunju.com.huiqitian.vm.my.model;

import android.content.Context;
import android.util.Log;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.http.CommonResponse;
import yunju.com.huiqitian.http.HttpConstant;
import yunju.com.huiqitian.http.HttpHandler;
import yunju.com.huiqitian.http.entity.PersonModifyMsgReq;
import yunju.com.huiqitian.http.entity.PersonMsgResp;
import yunju.com.huiqitian.utils.MyUtils;

/**
 * Created by 张超群 on 2016-08-02.
 * <p/>
 * 个人信息界面的Model
 */
public class PersonalMsgModel extends BaseModel {

    /*得到个人信息接口*/
    private GetPersonMsgInterface getPersonMsgInterface;

    /*修改性别接口*/
    private ModifyPersonSexInterface modifyPersonSexInterface;

    /*修改用户的头像*/
    private UploadPersonHeadInterface uploadPersonHeadInterface;

    public PersonalMsgModel(Context context) {
        super(context);
        getPersonMsgInterface = (GetPersonMsgInterface) context;
        modifyPersonSexInterface = (ModifyPersonSexInterface) context;
        uploadPersonHeadInterface = (UploadPersonHeadInterface) context;
    }


    /**
     * 张超群
     * <p/>
     * 获取个人信息
     */
    public void
    getPersonMsg() {
        sendGet(HttpConstant.PATH_PERSON_MSG, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);

                CommonResponse commonResponse = parseObject(response, CommonResponse.class);

                PersonMsgResp personMsgResp = parseObject(commonResponse.getObj(), PersonMsgResp.class);

                //保存个人信息
                MyUtils.savePersonMsg(personMsgResp.getName(), personMsgResp.getNickName(), personMsgResp.getSex().toString(),
                        personMsgResp.getMobile(), personMsgResp.getPicUrl());

                LogUtils.error(PersonalMsgModel.class, personMsgResp.getName());
                LogUtils.error(PersonalMsgModel.class, personMsgResp.getNickName());
                LogUtils.error(PersonalMsgModel.class, personMsgResp.getSex().toString());
                LogUtils.error(PersonalMsgModel.class, personMsgResp.getMobile());
                LogUtils.error(PersonalMsgModel.class,personMsgResp.getPicUrl());

                LogUtils.error(PersonalMsgModel.class, personMsgResp.getName());//手机号码
                Log.e("MenuFragmentModel", "绑定手机号：" + personMsgResp.getMobile());
                switch (commonResponse.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        getPersonMsgInterface.personMsg(personMsgResp);
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        getPersonMsgInterface.noPersonMsg(commonResponse.getMsg());
                        break;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpLoadingDialog.dismiss();
            }
        });
    }


    /**
     * 修改用户性别
     *
     * @param sex 性别
     */
    public void modifyNickName(final Byte sex) {
        PersonModifyMsgReq personModifyMsgReq = new PersonModifyMsgReq();
        personModifyMsgReq.setSex(sex);
        sendGet(HttpConstant.PATH_PERSON_CHANGE_NICKNAME, personModifyMsgReq, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                //把用户性别保存起来
                MyUtils.changePersonSex(sex.toString());
                CommonResponse commonResponse = parseObject(response, CommonResponse.class);
                LogUtils.error(ModifyModel.class, "修改后的信息：" + commonResponse);
                switch (commonResponse.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        modifyPersonSexInterface.modifySexSuccess(commonResponse.getMsg());
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        modifyPersonSexInterface.modifySexFailure(commonResponse.getMsg());
                        break;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpLoadingDialog.dismiss();
            }
        });
    }

    /**
     * 修改用户的头像
     *
     * @param base64 字符串
     */
    public void modifyHeadPic(String base64) {
        /*List<String> list = new ArrayList<>();
        list.add(base64);*/
        String list[]={base64};

        sendPost(HttpConstant.PATH_HEAD_PIC,list, new HttpHandler() {
            @Override
            public void onStart() {
                super.onStart();
                httpLoadingDialog.show();
            }

            @Override
            public void onSuccess(String response) {
                super.onSuccess(response);
                //把用户昵称保存起来
                CommonResponse commonResponse = parseObject(response, CommonResponse.class);
                LogUtils.error(ModifyModel.class, "修改后的信息：" + commonResponse);
                switch (commonResponse.getState()) {
                    case Constant.LOGIN_FAILURE://查询成功（1）
                        uploadPersonHeadInterface.uploadSuccess(commonResponse.getMsg());
                        break;
                    case Constant.LOGIN_SUCCESS://查询失败（0）
                        uploadPersonHeadInterface.uploadFailure(commonResponse.getMsg());
                        break;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                super.onFailure(statusCode, response, error);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpLoadingDialog.dismiss();
            }
        });
    }


    /*此接口是获取用户的信息*/
    public interface GetPersonMsgInterface {
        void personMsg(PersonMsgResp personMsgReq);

        void noPersonMsg(String msg);
    }

    /*此接口用来修改用户的性别*/
    public interface ModifyPersonSexInterface {
        void modifySexSuccess(String msg);

        void modifySexFailure(String msg);
    }

    /*用来上传用户的头像*/
    public interface UploadPersonHeadInterface {
        void uploadSuccess(String msg);

        void uploadFailure(String msg);
    }

}
