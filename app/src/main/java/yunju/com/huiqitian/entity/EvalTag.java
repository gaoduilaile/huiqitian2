package yunju.com.huiqitian.entity;

/**
 * Created by 张超群 on 2016-08-13.
 * <p/>
 * 云标签
 */
public class EvalTag {

    /**
     * 标签编号
     */
    private Integer id;

    /**
     * 标签名称
     */
    private String tag;

    /**
     * 标签描述
     */
    private String tagDesc;

    /**
     * 标签类型
     */
    private Byte tagType;

    /**
     * 排序号
     */
    private int sort;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTagDesc() {
        return tagDesc;
    }

    public void setTagDesc(String tagDesc) {
        this.tagDesc = tagDesc;
    }

    public Byte getTagType() {
        return tagType;
    }

    public void setTagType(Byte tagType) {
        this.tagType = tagType;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }


}
