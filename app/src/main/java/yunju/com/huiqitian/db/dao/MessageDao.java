package yunju.com.huiqitian.db.dao;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import yunju.com.huiqitian.AppApplication;
import yunju.com.huiqitian.db.entity.Message;

/**
 * Created by Administrator on 2016/11/10 0010.
 */
public class MessageDao {
    private DbManager db= x.getDb(AppApplication.getDaoConfig());
    private List<Message> messageList = new ArrayList<Message>();

    /**
     * 增加
     */
    public void save(Message message) throws DbException {
        db.save(message);
    }

    /**
     * 删除 (清空)
     */
    public void delete() throws DbException {
        messageList = db.findAll(Message.class);
        db.delete(messageList);
    }


    /**
     * 查询表
     */
    public List<Message> select() throws DbException {
        return db.selector(Message.class).findAll();

    }
}
