package yunju.com.huiqitian.entity;

/**
 * Created by 胡月 on 2016/7/27 0027.
 */
public class ClassifyInfo {
    /**
     * 商品分类编号
    */
    private int id;

    /**
     * 商品分类名称
     */
    private String name;

    /**
     * 商品分类图片的ID
     */
    private int picId;

    /**
     * 文件路径
     */
    private String picPath;

    /**
     * 重命名后的文件名
     */
    private String picStoredName;

    /**
     * 商品分类图片的url
     */
    private String picUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getPicStoredName() {
        return picStoredName;
    }

    public void setPicStoredName(String picStoredName) {
        this.picStoredName = picStoredName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
