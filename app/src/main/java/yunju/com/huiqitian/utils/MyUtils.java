package yunju.com.huiqitian.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.AppApplication;
import yunju.com.huiqitian.constant.Constant;
import yunju.com.huiqitian.vm.widget.PullToRefreshLayout;

/**
 * 工具类
 * Created by 高英祥 on 2016/7/11 0011.
 */
public class MyUtils {

    /**
     * 检查是否存储了用户
     *
     * @return true存在 false不存在
     */
    public static boolean checkUser() {

        return (!TextUtils.isEmpty(getCellPhone()) && !TextUtils.isEmpty(getPassword()));
    }

    /**
     * 保存用户选择的经纬度地址城市
     */
    public static void saveLag(String lag,String log,String city,String address) {

       AppApplication.getPreferenceHelper().putString(Constant.SHARED_USER_LAG, lag);
        AppApplication.getPreferenceHelper().putString(Constant.SHARED_USER_LOG, log);
        AppApplication.getPreferenceHelper().putString(Constant.SHARED_USER_CITY ,city);
        AppApplication.getPreferenceHelper().putString(Constant.SHARED_USER_ADDRESS, address);
    }


    /**
     * 保存用户是否选择经纬度地址的状态
     */
    public static void saveLagType(String type){
        AppApplication.getPreferenceHelper().putString(Constant.SHARED_USER_LOG_TYPE,type);
    }

    /**
     * 获取用户是否保存选择经纬度的状态
     */
    public static String getLogType(){
        return AppApplication.getPreferenceHelper().getString(Constant.SHARED_USER_LOG_TYPE,"");
    }

    /**
     * 得到用户选择的经纬度地址城市
     */
    public static String getLag(){
        return AppApplication.getPreferenceHelper().getString(Constant.SHARED_USER_LAG, "");
    }
    public static String getLog(){
        return AppApplication.getPreferenceHelper().getString(Constant.SHARED_USER_LOG, "");
    }
    public static String getAddresss(){
        return AppApplication.getPreferenceHelper().getString(Constant.SHARED_USER_ADDRESS, "");
    }
    public static String getCity(){
        return AppApplication.getPreferenceHelper().getString(Constant.SHARED_USER_CITY, "");
    }

    /**
     * 删除用户选择的经纬度地址城市
     */
    public static void clearLogLag(){
        AppApplication.getPreferenceHelper().putString(Constant.SHARED_USER_LAG, "");
        AppApplication.getPreferenceHelper().putString(Constant.SHARED_USER_LOG, "");
        AppApplication.getPreferenceHelper().putString(Constant.SHARED_USER_CITY ,"");
        AppApplication.getPreferenceHelper().putString(Constant.SHARED_USER_ADDRESS, "");
    }


    /**
     * 保存用户
     *
     * @param cellPhone
     * @param password
     */
    public static void saveUser(String cellPhone, String password) {

        if (!TextUtils.isEmpty(cellPhone)) {
            AppApplication.getPreferenceHelper().putString(Constant.SHARED_USER_CELL_PHONE, cellPhone);

        }
        if (!TextUtils.isEmpty(password)) {
            AppApplication.getPreferenceHelper().putString(Constant.SHARED_USER_PASSWORD, password);
        }
    }

    /**
     *判断用户是从首页定位还是自己选位置定位
     */
    public static void saveLocationType(String type){
        if (!TextUtils.isEmpty(type)) {
            AppApplication.getPreferenceHelper().putString(Constant.LOCATION_TYPE, type);

        }
    }


    /**
     * 保存用户CID
     *
     * @param cid
     */
    public static void saveCID(String cid){
        if(!TextUtils.isEmpty(cid)){
            AppApplication.getPreferenceHelper().putString(Constant.SHARED_USER_CID,cid);
        }
    }

    /**
     * 保存能否收到推送消息状态
     */
    public static void savePushType(String pushType){
        if (!TextUtils.isEmpty(pushType)){
            AppApplication.getPreferenceHelper().putString(Constant.SHARED_USER_PUSH,pushType);
        }
    }

    /**
     * 设置是否有声音
     */
    public static void savePushSound(String pushSound){
        if (!TextUtils.isEmpty(pushSound)){
            AppApplication.getPreferenceHelper().putString(Constant.SHARED_USER_PUSH_SOUND,pushSound);
        }
    }

    /**
     * 代金券id
     * @param id
     */
    public static void saveVoucherId(String id){
        if (!TextUtils.isEmpty(id)){
            AppApplication.getPreferenceHelper().putString(Constant.SHARED_VOUCHER_ID,id);
        }
    }

    /**
     * 获取代金券id
     */

    public static String getVoucherId() {

        return AppApplication.getPreferenceHelper().getString(Constant.SHARED_VOUCHER_ID, "");
    }

    /**
     * 清空代金券
     */
    public static void clearVoucher(){
        AppApplication.getPreferenceHelper().putString(Constant.SHARED_VOUCHER_ID, "");
    }


    /**
     * 清空用户
     */
    public static void clearUser() {

        AppApplication.getPreferenceHelper().putString(Constant.SHARED_USER_CELL_PHONE, "");
        AppApplication.getPreferenceHelper().putString(Constant.SHARED_USER_PASSWORD, "");
        AppApplication.getPreferenceHelper().putString(Constant.SHARED_USER_PUSH,"");
        AppApplication.getPreferenceHelper().putString(Constant.SHARED_USER_PUSH_SOUND,"");

    }

    /**
     * 获取手机号
     *
     * @return
     */
    public static String getCellPhone() {

        return AppApplication.getPreferenceHelper().getString(Constant.SHARED_USER_CELL_PHONE, "");
    }

    /**
     *判断用户是从首页定位还是自己选位置定位
     */
    public static String getLocationType(){
        return  AppApplication.getPreferenceHelper().getString(Constant.LOCATION_TYPE, "");

    }


    /**
     * 获取密码
     *
     * @return
     */
    public static String getPassword() {

        return AppApplication.getPreferenceHelper().getString(Constant.SHARED_USER_PASSWORD, "");
    }



    /**
     * 获取cid
     *
     * @return
     */
    public static String getCid() {

        return AppApplication.getPreferenceHelper().getString(Constant.SHARED_USER_CID, "");
    }

    /**
     * 获得能否接受推送状态
     * @return
     */
    public static String getPushType(){
        return AppApplication.getPreferenceHelper().getString(Constant.SHARED_USER_PUSH, "");
    }

    /**
     * 获得能否接受推送消息声音状态
     * @return
     */
    public static String getPushSound(){
        return AppApplication.getPreferenceHelper().getString(Constant.SHARED_USER_PUSH_SOUND, "");
    }

    public static boolean isMobileNum(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,1-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        System.out.println(m.matches() + "---");
        return m.matches();
    }

    /**
     * 拆分钱
     */
    public static String pointMoney(BigDecimal bd) {
        return bd.toString().substring(bd.toString().indexOf("."), bd.toString().length());
    }


    /**
     * 保存部分个人信息
     *
     * @param name     用户名
     * @param nickName 昵称
     * @param sex      性别
     * @param mobile   手机号
     * @param picUrl   图片路径
     */
    public static void savePersonMsg(String name, String nickName, String sex, String mobile, String picUrl) {

        if (!TextUtils.isEmpty(name)) {
            AppApplication.getPreferenceHelper().putString(Constant.SHARED_PERSON_NAME, name);
        }

        if (!TextUtils.isEmpty(nickName)) {
            AppApplication.getPreferenceHelper().putString(Constant.SHARED_PERSON_NICKNAME, nickName);
        }

        if (!TextUtils.isEmpty(sex)) {
            AppApplication.getPreferenceHelper().putString(Constant.SHARED_PERSON_SEX, sex.toString());
        }

        if (!TextUtils.isEmpty(mobile)) {
            AppApplication.getPreferenceHelper().putString(Constant.SHARED_PERSON_PHONE, mobile);
        }

        if (!TextUtils.isEmpty(picUrl)) {
            AppApplication.getPreferenceHelper().putString(Constant.SHARED_PERSON_PICURL, picUrl);
        }
    }

    /*得到用户名*/
    public static String getPersonName() {
        return AppApplication.getPreferenceHelper().getString(Constant.SHARED_PERSON_NAME, "");
    }

    /*得到用户昵称*/
    public static String getPersonNickname() {
        return AppApplication.getPreferenceHelper().getString(Constant.SHARED_PERSON_NICKNAME, "");
    }

    /*修改用户昵称*/
    public static void changePersonNickName(String nickName) {
        if (!TextUtils.isEmpty(nickName)) {
            AppApplication.getPreferenceHelper().putString(Constant.SHARED_PERSON_NICKNAME, nickName);
        }
    }

    /*得到用户性别*/
    public static String getPersonSex() {
        return AppApplication.getPreferenceHelper().getString(Constant.SHARED_PERSON_SEX, "");
    }

    /*修改用户性别*/
    public static void changePersonSex(String sex) {
        if (!TextUtils.isEmpty(sex)) {
            AppApplication.getPreferenceHelper().putString(Constant.SHARED_PERSON_SEX, sex);
        }
    }

    /*得到用户电话*/
    public static String getPersonPhone() {
        return AppApplication.getPreferenceHelper().getString(Constant.SHARED_PERSON_PHONE, "");
    }

    /*修改用户电话*/
    public static void changePersonPhone(String phone) {
        if (!TextUtils.isEmpty(phone)) {
            AppApplication.getPreferenceHelper().putString(Constant.SHARED_PERSON_PHONE, phone);
        }
    }
    /*保存支付之后的订单编号*/
    public static void savePayId(String strings){
        AppApplication.getPreferenceHelper().putString(Constant.PAY_ID,strings);
    }
    /*取出支付之后的订单编号*/
    public static String getPayId(){
        return AppApplication.getPreferenceHelper().getString(Constant.PAY_ID,"");
    }

    /*得到用户头像*/
    public static String getPersonPicUrl() {
        return AppApplication.getPreferenceHelper().getString(Constant.SHARED_PERSON_PICURL, "");
    }
    /*保存下单的时候用户的留言*/
    public static void saveMsgBody(String msg){
        AppApplication.getPreferenceHelper().putString(Constant.MSG_BODY,msg);
    }
    /*取出下单时候保存的用户留言*/
    public static String getMsgBody(){
        return AppApplication.getPreferenceHelper().getString(Constant.MSG_BODY,"");
    }

    /**
     * 清除个人信息
     */
    public static void clearPersonMsg() {
        AppApplication.getPreferenceHelper().putString(Constant.SHARED_PERSON_NAME, "");
        AppApplication.getPreferenceHelper().putString(Constant.SHARED_PERSON_NICKNAME, "");
        AppApplication.getPreferenceHelper().putString(Constant.SHARED_PERSON_SEX, "");
        AppApplication.getPreferenceHelper().putString(Constant.SHARED_PERSON_PHONE, "");
        AppApplication.getPreferenceHelper().putString(Constant.SHARED_PERSON_PICURL, "");
    }


    /**
     * 保存用户选择的照片
     *
     * @param bitmap 位图
     * @param files  文件路径
     * @return
     */
    public static String saveBitmap(Bitmap bitmap, String files) {
        long dates = System.currentTimeMillis();
        String out_file_path = FileUitl.getSDPath();// 路径
        String dates_image = dates + ".jpg";// 当前时间
        String path = out_file_path + "/" + files;
        File f = new File(path);// 创建文件
        if (!(f.exists())) {
            f.mkdirs();
        }
        File phoneFile = new File(f.getAbsolutePath() + "/" + dates_image);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(phoneFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return phoneFile.getAbsolutePath();
    }

    /**
     * 获取地址
     *
     * @return
     */
    public static String getAddress() {
        return AppApplication.getPreferenceHelper().getString(Constant.MY_LOCATION_TYPE_FOR_ADDRESS, "");
    }

    /**
     * 存放地址
     * @param address
     */
    public static void putAddress(String address) {
        AppApplication.getPreferenceHelper().putString(Constant.MY_LOCATION_TYPE_FOR_ADDRESS, address);
    }

    /**
     * 清空用户地址
     */
    public static void clearAddress() {
        AppApplication.getPreferenceHelper().putString(Constant.MY_LOCATION_TYPE_FOR_ADDRESS, "");
    }

    /*保存用户的头像*/
    public static void saveHeadPath(String path){
        AppApplication.getPreferenceHelper().putString(Constant.PIC_USER_HEAD, path);
    }

    /*得到用户的头像*/
    public static String getHeadPath(){
       return AppApplication.getPreferenceHelper().getString(Constant.PIC_USER_HEAD, "");
    }


  /**
  *Created by gaoqiong on 2016/9/8 0008 12:44
  *自定义土司
  */

    public static void toasShow(String des,Context context){
        Toast.makeText(context,des,Toast.LENGTH_SHORT).show();
    }

    /**
     * 下拉刷新的抽取
     */
    public static void setRefresh(final PullToRefreshLayout pullToRefreshLayout){
        // 下拉刷新操作
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // 千万别忘了告诉控件刷新完毕了哦！
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                LogUtils.error(MyUtils.class, "下拉刷新成功");
            }
        }.sendEmptyMessageDelayed(0, 2000);

    }

    /**
     * 上拉刷新的抽取
     */
    public static void setLoadMore(final PullToRefreshLayout pullToRefreshLayout){
        // 加载操作
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // 千万别忘了告诉控件加载完毕了哦！
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 2000);

    }
}
