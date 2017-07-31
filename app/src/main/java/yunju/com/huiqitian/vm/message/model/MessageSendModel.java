package yunju.com.huiqitian.vm.message.model;

import android.content.Context;

import java.util.ArrayList;

import yunju.com.huiqitian.base.BaseModel;
import yunju.com.huiqitian.entity.MessageInfo;

/**
 * Created by 高琼 on 2016/9/2.
 */
public class MessageSendModel extends BaseModel {


    public MessageSendModel(Context context) {
        super(context);
    }


    /**
     * 从网络获取系统消息  测试数据
     * Create by gaoqiong 2016/9/2
     * */
    public ArrayList<MessageInfo> getSendItem() {
        ArrayList<MessageInfo> mList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MessageInfo messageInfo = new MessageInfo();
            messageInfo.time=44455555;
            messageInfo.content=i+"锄禾日当午旱，白日依山尽，黄河入海流，欲穷千里目，更上一层楼锄禾日当午旱，锄禾日当午旱，白日依山尽，黄河入海流，欲穷千里目，更上一层楼锄禾日当午旱，白日依山尽，黄河入海流，欲穷千里目，更上一层楼";
            messageInfo.iconUrl="http://ppt360.com/background/UploadFiles_6733/201012/2010122016023658.jpg";
            messageInfo.title=i+"安徽芸聚网络科技";
            mList.add(messageInfo);
        }
        return mList;
    }
}
