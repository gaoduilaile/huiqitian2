package yunju.com.huiqitian.http.entity;

/**
 * Created by gaoyingxiang on 2016/7/27 0027.
 */
public class GoodsPropResp {
    /**
     * 属性名
     */
    private String name;

    /**
     * 属性值
     */
    private String propValue;

    /**
     * 属性单位
     */
    private String unit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPropValue() {
        return propValue;
    }

    public void setPropValue(String propValue) {
        this.propValue = propValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
