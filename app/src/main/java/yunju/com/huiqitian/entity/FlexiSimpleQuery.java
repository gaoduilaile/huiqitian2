package yunju.com.huiqitian.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/12/13 0013.
 */
public class FlexiSimpleQuery {
    private String qtype;
    private List<String> query;
    private boolean fuzzy;

    public String getQtype() {
        return qtype;
    }

    public void setQtype(String qtype) {
        this.qtype = qtype;
    }

    public List<String> getQuery() {
        return query;
    }

    public void setQuery(List<String> query) {
        this.query = query;
    }

    public boolean isFuzzy() {
        return fuzzy;
    }

    public void setFuzzy(boolean fuzzy) {
        this.fuzzy = fuzzy;
    }
}
