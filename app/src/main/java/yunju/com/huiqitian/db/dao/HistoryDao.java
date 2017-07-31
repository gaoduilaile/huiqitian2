package yunju.com.huiqitian.db.dao;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.common.util.LogUtils;
import yunju.com.huiqitian.AppApplication;
import yunju.com.huiqitian.db.entity.History;

/**
 * Created by gao on 2016/8/16 0016.
 * 添加历史记录的dao类
 */
public class HistoryDao {

    private DbManager db = x.getDb(AppApplication.getDaoConfig());
    private List<History> historyList = new ArrayList<History>();

    /**
     * 增加
     */
    public void save(History history) throws DbException {
        db.save(history);
    }

    /**
     * 删除 (清空)
     */
    public void delete() throws DbException {
        historyList = db.findAll(History.class);
        if(historyList!=null&&historyList.size()>0){
            db.delete(historyList);
        }

    }

    /**
     * 查询表
     */
    public List<History> select() throws DbException {
        return db.selector(History.class).findAll();
    }

    public  void addColumn() {
        try {
            LogUtils.error(HistoryDao.class,"................888888888888...............");
            db.addColumn(History.class, "freeDispatchLimit");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
    public  void addColumnTwo() {
        try {
            LogUtils.error(HistoryDao.class,"................99999999999999999...............");
            db.addColumn(History.class, "marketPrice");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
    public  void addColumnThree() {
        try {
            LogUtils.error(HistoryDao.class,"................0000000000000000000000...............");
            db.addColumn(History.class, "marketId");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
