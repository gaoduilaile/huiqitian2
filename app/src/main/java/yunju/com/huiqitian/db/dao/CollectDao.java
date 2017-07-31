package yunju.com.huiqitian.db.dao;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import yunju.com.huiqitian.AppApplication;
import yunju.com.huiqitian.db.entity.Collect;

/**
 * Created by gao on 2016/8/16 0016.
 * 添加收藏的dao类
 */
public class CollectDao {
    private DbManager db = x.getDb(AppApplication.getDaoConfig());
    private List<Collect> parentList = new ArrayList<Collect>();

    /**
     * 增加
     */
    public void save(Collect collect) throws DbException {
        db.save(collect);
    }

    /**
     * 删除 (清空)
     */
    public void delete() throws DbException {
        parentList = db.findAll(Collect.class);
        if(parentList!=null&&parentList.size()>0){
            db.delete(parentList);
        }

    }


    /**
     * 查询表
     */
    public List<Collect> select() throws DbException {
        return db.selector(Collect.class).findAll();

    }

    /**
     * 判断本地数据库是否有数据
     */

    /**
     * 删除对应的一个
     */
    public void deleteOne(int a) {
        try {
            parentList = db.selector(Collect.class).findAll();
            if(a==0){
                db.delete(parentList.get(parentList.size() - 1));
            }else {
                db.delete(parentList.get(a));
            }


        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 增加一列
     */
    public  void addCollectOne() {
        try {
            db.addColumn(Collect.class, "freeDispatchLimit");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 增加一列
     */
    public  void addCollectTwo() {
        try {
            db.addColumn(Collect.class, "marketPrice");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 增加一列
     */
    public  void addCollectThree() {
        try {
            db.addColumn(Collect.class, "marketId");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}
