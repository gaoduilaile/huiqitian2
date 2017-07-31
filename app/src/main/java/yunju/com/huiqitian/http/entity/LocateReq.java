package yunju.com.huiqitian.http.entity;

/**
 * Created by 高英祥 on 2016/7/25 0025.
 */
public class LocateReq {
    /**
     * 经度
     */
    private double lng;

    /**
     * 维度
     */
    private double lat;

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
