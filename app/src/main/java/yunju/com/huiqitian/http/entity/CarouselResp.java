package yunju.com.huiqitian.http.entity;

import java.util.List;

import yunju.com.huiqitian.base.BaseResp;
import yunju.com.huiqitian.entity.CarouselImage;

/**
 * Created by 胡月 on 2016/9/7 0007.
 */
public class CarouselResp extends BaseResp{

    private List<CarouselImage>obj;

    public List<CarouselImage> getObj() {
        return obj;
    }

    public void setObj(List<CarouselImage> obj) {
        this.obj = obj;
    }
}
