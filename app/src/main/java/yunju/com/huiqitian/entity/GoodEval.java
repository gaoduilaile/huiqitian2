package yunju.com.huiqitian.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/25 0025.
 */
public class GoodEval implements Serializable{

    //评价类容
    private List<EvalList> evalList;

    //评价级别
    private List<LevelList>levelList;

    //评价标签
    private List<TagList>tagList;

    public List<EvalList> getEvalList() {
        return evalList;
    }

    public void setEvalList(List<EvalList> evalList) {
        this.evalList = evalList;
    }

    public List<LevelList> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<LevelList> levelList) {
        this.levelList = levelList;
    }

    public List<TagList> getTagList() {
        return tagList;
    }

    public void setTagList(List<TagList> tagList) {
        this.tagList = tagList;
    }
}
