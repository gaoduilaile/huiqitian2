package yunju.com.huiqitian.entity;

/**
 * Created by 胡月 on 2016/9/7 0007.
 */
public class CarouselImage {
    /**
     * 轮播图的序号
     */
    private int seq;

    /**
     * 轮播图图片Id
     */
    private int picId;

    /**
     * 轮播图图片Url
     */
        private String picUrl;

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
