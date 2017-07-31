package yunju.com.huiqitian.http.entity;

import java.io.Serializable;
import java.util.List;

import yunju.com.huiqitian.base.BaseResp;

/**
 * 父ListView的javabean
 * Created by CCTV-1 on 2016/11/25 0025.
 */
public class LeftClassifyReq extends BaseResp implements Serializable{
    /**
     * childs : [{"childs":null,"name":"面条","id":394,"parentId":393,"pic":"4264","code":"0501","agentPercent":0,"marketPercent":100,"compPercent":0,"sort":1,"gcgname":null,"intoname":null,"stCreateTime":null,"origDeleteFlag":null,"stUpdateTime":null,"dataVersion":2,"createUser":156,"deleteFlag":0,"updateUser":1,"createUserType":0,"updateUserType":0,"createTime":1469098668000,"updateTime":1470034773000},{"childs":null,"name":"方便面","id":407,"parentId":393,"pic":"4270","code":"0502","agentPercent":100,"marketPercent":0,"compPercent":0,"sort":3,"gcgname":null,"intoname":null,"stCreateTime":null,"origDeleteFlag":null,"stUpdateTime":null,"dataVersion":0,"createUser":1,"deleteFlag":0,"updateUser":1,"createUserType":0,"updateUserType":0,"createTime":1470034875000,"updateTime":1470034875000},{"childs":null,"name":"米面油","id":396,"parentId":393,"pic":"4110","code":"0503","agentPercent":0,"marketPercent":100,"compPercent":0,"sort":3,"gcgname":null,"intoname":null,"stCreateTime":null,"origDeleteFlag":null,"stUpdateTime":null,"dataVersion":2,"createUser":156,"deleteFlag":0,"updateUser":1,"createUserType":0,"updateUserType":0,"createTime":1469098773000,"updateTime":1470031355000},{"childs":null,"name":"调味品","id":397,"parentId":393,"pic":"4108","code":"0504","agentPercent":0,"marketPercent":100,"compPercent":0,"sort":4,"gcgname":null,"intoname":null,"stCreateTime":null,"origDeleteFlag":null,"stUpdateTime":null,"dataVersion":2,"createUser":156,"deleteFlag":0,"updateUser":1,"createUserType":0,"updateUserType":0,"createTime":1469098799000,"updateTime":1470031335000},{"childs":null,"name":"包装熟食","id":398,"parentId":393,"pic":"4106","code":"0505","agentPercent":0,"marketPercent":100,"compPercent":0,"sort":5,"gcgname":null,"intoname":null,"stCreateTime":null,"origDeleteFlag":null,"stUpdateTime":null,"dataVersion":2,"createUser":156,"deleteFlag":0,"updateUser":1,"createUserType":0,"updateUserType":0,"createTime":1469098822000,"updateTime":1470031317000},{"childs":null,"name":"水饺汤圆鲜食","id":399,"parentId":393,"pic":"4104","code":"0506","agentPercent":0,"marketPercent":100,"compPercent":0,"sort":6,"gcgname":null,"intoname":null,"stCreateTime":null,"origDeleteFlag":null,"stUpdateTime":null,"dataVersion":2,"createUser":156,"deleteFlag":0,"updateUser":1,"createUserType":0,"updateUserType":0,"createTime":1469098853000,"updateTime":1470031301000},{"childs":null,"name":"包装干菜","id":400,"parentId":393,"pic":"4102","code":"0507","agentPercent":0,"marketPercent":100,"compPercent":0,"sort":7,"gcgname":null,"intoname":null,"stCreateTime":null,"origDeleteFlag":null,"stUpdateTime":null,"dataVersion":2,"createUser":156,"deleteFlag":0,"updateUser":1,"createUserType":0,"updateUserType":0,"createTime":1469098874000,"updateTime":1470031261000},{"childs":null,"name":"日配鲜奶","id":401,"parentId":393,"pic":"4100","code":"0508","agentPercent":0,"marketPercent":100,"compPercent":0,"sort":8,"gcgname":null,"intoname":null,"stCreateTime":null,"origDeleteFlag":null,"stUpdateTime":null,"dataVersion":2,"createUser":156,"deleteFlag":0,"updateUser":1,"createUserType":0,"updateUserType":0,"createTime":1469098900000,"updateTime":1470031248000},{"childs":null,"name":"散装类","id":402,"parentId":393,"pic":"4094","code":"0509","agentPercent":0,"marketPercent":100,"compPercent":0,"sort":9,"gcgname":null,"intoname":null,"stCreateTime":null,"origDeleteFlag":null,"stUpdateTime":null,"dataVersion":2,"createUser":156,"deleteFlag":0,"updateUser":1,"createUserType":0,"updateUserType":0,"createTime":1469098928000,"updateTime":1470031152000}]
     * name : 副食组
     * id : 393
     * picUrl : /data/upload/shop/goods/category/201608011547251855875.jpg
     * parentId : 0
     * pic : 4381
     * code : 06
     * agentPercent : 0.0
     * marketPercent : 100.0
     * compPercent : 0.0
     * sort : null
     * gcgname : null
     * intoname : null
     * stCreateTime : null
     * origDeleteFlag : null
     * stUpdateTime : null
     * dataVersion : 2
     * createUser : 156
     * deleteFlag : 0
     * updateUser : 1
     * createUserType : 0
     * updateUserType : 0
     * createTime : 1469098603000
     * updateTime : 1470037604000
     */

    private List<ObjBean> obj;

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class ObjBean implements Serializable{
        private String name;

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        private String picUrl;
        private int id;
        private int parentId;
        private String pic;
        private String code;
        private double agentPercent;
        private double marketPercent;
        private double compPercent;
        private Object sort;
        private Object gcgname;
        private Object intoname;
        private Object stCreateTime;
        private Object origDeleteFlag;
        private Object stUpdateTime;
        private int dataVersion;
        private int createUser;
        private int deleteFlag;
        private int updateUser;
        private int createUserType;
        private int updateUserType;
        private long createTime;
        private long updateTime;
        /**
         * picUrl : /data/upload/shop/goods/category/201608011500141547306.jpg
         * childs : null
         * name : 面条
         * id : 394
         * parentId : 393
         * pic : 4264
         * code : 0501
         * agentPercent : 0.0
         * marketPercent : 100.0
         * compPercent : 0.0
         * sort : 1
         * gcgname : null
         * intoname : null
         * stCreateTime : null
         * origDeleteFlag : null
         * stUpdateTime : null
         * dataVersion : 2
         * createUser : 156
         * deleteFlag : 0
         * updateUser : 1
         * createUserType : 0
         * updateUserType : 0
         * createTime : 1469098668000
         * updateTime : 1470034773000
         */

        private List<ChildsBean> childs;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public double getAgentPercent() {
            return agentPercent;
        }

        public void setAgentPercent(double agentPercent) {
            this.agentPercent = agentPercent;
        }

        public double getMarketPercent() {
            return marketPercent;
        }

        public void setMarketPercent(double marketPercent) {
            this.marketPercent = marketPercent;
        }

        public double getCompPercent() {
            return compPercent;
        }

        public void setCompPercent(double compPercent) {
            this.compPercent = compPercent;
        }

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }

        public Object getGcgname() {
            return gcgname;
        }

        public void setGcgname(Object gcgname) {
            this.gcgname = gcgname;
        }

        public Object getIntoname() {
            return intoname;
        }

        public void setIntoname(Object intoname) {
            this.intoname = intoname;
        }

        public Object getStCreateTime() {
            return stCreateTime;
        }

        public void setStCreateTime(Object stCreateTime) {
            this.stCreateTime = stCreateTime;
        }

        public Object getOrigDeleteFlag() {
            return origDeleteFlag;
        }

        public void setOrigDeleteFlag(Object origDeleteFlag) {
            this.origDeleteFlag = origDeleteFlag;
        }

        public Object getStUpdateTime() {
            return stUpdateTime;
        }

        public void setStUpdateTime(Object stUpdateTime) {
            this.stUpdateTime = stUpdateTime;
        }

        public int getDataVersion() {
            return dataVersion;
        }

        public void setDataVersion(int dataVersion) {
            this.dataVersion = dataVersion;
        }

        public int getCreateUser() {
            return createUser;
        }

        public void setCreateUser(int createUser) {
            this.createUser = createUser;
        }

        public int getDeleteFlag() {
            return deleteFlag;
        }

        public void setDeleteFlag(int deleteFlag) {
            this.deleteFlag = deleteFlag;
        }

        public int getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(int updateUser) {
            this.updateUser = updateUser;
        }

        public int getCreateUserType() {
            return createUserType;
        }

        public void setCreateUserType(int createUserType) {
            this.createUserType = createUserType;
        }

        public int getUpdateUserType() {
            return updateUserType;
        }

        public void setUpdateUserType(int updateUserType) {
            this.updateUserType = updateUserType;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public List<ChildsBean> getChilds() {
            return childs;
        }

        public void setChilds(List<ChildsBean> childs) {
            this.childs = childs;
        }

        public static class ChildsBean implements Serializable{
            private Object childs;
            private String name;
            private int id;
            private int parentId;
            private String pic;
            private String picUrl;
            private String code;
            private double agentPercent;
            private double marketPercent;
            private double compPercent;
            private int sort;
            private Object gcgname;
            private Object intoname;
            private Object stCreateTime;
            private Object origDeleteFlag;
            private Object stUpdateTime;
            private int dataVersion;
            private int createUser;
            private int deleteFlag;
            private int updateUser;
            private int createUserType;
            private int updateUserType;
            private long createTime;
            private long updateTime;

            public Object getChilds() {
                return childs;
            }

            public void setChilds(Object childs) {
                this.childs = childs;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public double getAgentPercent() {
                return agentPercent;
            }

            public void setAgentPercent(double agentPercent) {
                this.agentPercent = agentPercent;
            }

            public double getMarketPercent() {
                return marketPercent;
            }

            public void setMarketPercent(double marketPercent) {
                this.marketPercent = marketPercent;
            }

            public double getCompPercent() {
                return compPercent;
            }

            public void setCompPercent(double compPercent) {
                this.compPercent = compPercent;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public Object getGcgname() {
                return gcgname;
            }

            public void setGcgname(Object gcgname) {
                this.gcgname = gcgname;
            }

            public Object getIntoname() {
                return intoname;
            }

            public void setIntoname(Object intoname) {
                this.intoname = intoname;
            }

            public Object getStCreateTime() {
                return stCreateTime;
            }

            public void setStCreateTime(Object stCreateTime) {
                this.stCreateTime = stCreateTime;
            }

            public Object getOrigDeleteFlag() {
                return origDeleteFlag;
            }

            public void setOrigDeleteFlag(Object origDeleteFlag) {
                this.origDeleteFlag = origDeleteFlag;
            }

            public Object getStUpdateTime() {
                return stUpdateTime;
            }

            public void setStUpdateTime(Object stUpdateTime) {
                this.stUpdateTime = stUpdateTime;
            }

            public int getDataVersion() {
                return dataVersion;
            }

            public void setDataVersion(int dataVersion) {
                this.dataVersion = dataVersion;
            }

            public int getCreateUser() {
                return createUser;
            }

            public void setCreateUser(int createUser) {
                this.createUser = createUser;
            }

            public int getDeleteFlag() {
                return deleteFlag;
            }

            public void setDeleteFlag(int deleteFlag) {
                this.deleteFlag = deleteFlag;
            }

            public int getUpdateUser() {
                return updateUser;
            }

            public void setUpdateUser(int updateUser) {
                this.updateUser = updateUser;
            }

            public int getCreateUserType() {
                return createUserType;
            }

            public void setCreateUserType(int createUserType) {
                this.createUserType = createUserType;
            }

            public int getUpdateUserType() {
                return updateUserType;
            }

            public void setUpdateUserType(int updateUserType) {
                this.updateUserType = updateUserType;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }
        }
    }

   /*/**
     *
     * picUrl : /data/upload/shop/goods/category/201608011547251855875.jpg
     * storedName : 201608011547251855875.jpg
     * childs : [{"path":"\\upload\\shop\\goods\\category\\","picUrl":"/data/upload/shop/goods/category/201608011500141547306.jpg","storedName":"201608011500141547306.jpg","childs":null,"name":"面条","id":394,"code":"0501","agentPercent":0,"marketPercent":100,"compPercent":0,"parentId":393,"pic":4264,"sort":1,"stCreateTime":null,"origDeleteFlag":null,"stUpdateTime":null,"createUser":156,"deleteFlag":0,"updateUser":1,"createTime":1469098668000,"dataVersion":2,"createUserType":0,"updateUserType":0,"updateTime":1470034773000},{"path":"\\upload\\shop\\goods\\category\\","picUrl":"/data/upload/shop/goods/category/201608011501255150671.jpg","storedName":"201608011501255150671.jpg","childs":null,"name":"方便面","id":407,"code":"0502","agentPercent":100,"marketPercent":0,"compPercent":0,"parentId":393,"pic":4270,"sort":3,"stCreateTime":null,"origDeleteFlag":null,"stUpdateTime":null,"createUser":1,"deleteFlag":0,"updateUser":1,"createTime":1470034875000,"dataVersion":0,"createUserType":0,"updateUserType":0,"updateTime":1470034875000},{"path":"\\upload\\shop\\goods\\category\\","picUrl":"/data/upload/shop/goods/category/201608011403158831845.jpg","storedName":"201608011403158831845.jpg","childs":null,"name":"米面油","id":396,"code":"0503","agentPercent":0,"marketPercent":100,"compPercent":0,"parentId":393,"pic":4110,"sort":3,"stCreateTime":null,"origDeleteFlag":null,"stUpdateTime":null,"createUser":156,"deleteFlag":0,"updateUser":1,"createTime":1469098773000,"dataVersion":2,"createUserType":0,"updateUserType":0,"updateTime":1470031355000},{"path":"\\upload\\shop\\goods\\category\\","picUrl":"/data/upload/shop/goods/category/201608011402565198019.jpg","storedName":"201608011402565198019.jpg","childs":null,"name":"调味品","id":397,"code":"0504","agentPercent":0,"marketPercent":100,"compPercent":0,"parentId":393,"pic":4108,"sort":4,"stCreateTime":null,"origDeleteFlag":null,"stUpdateTime":null,"createUser":156,"deleteFlag":0,"updateUser":1,"createTime":1469098799000,"dataVersion":2,"createUserType":0,"updateUserType":0,"updateTime":1470031335000},{"path":"\\upload\\shop\\goods\\category\\","picUrl":"/data/upload/shop/goods/category/201608011402382837652.jpg","storedName":"201608011402382837652.jpg","childs":null,"name":"包装熟食","id":398,"code":"0505","agentPercent":0,"marketPercent":100,"compPercent":0,"parentId":393,"pic":4106,"sort":5,"stCreateTime":null,"origDeleteFlag":null,"stUpdateTime":null,"createUser":156,"deleteFlag":0,"updateUser":1,"createTime":1469098822000,"dataVersion":2,"createUserType":0,"updateUserType":0,"updateTime":1470031317000},{"path":"\\upload\\shop\\goods\\category\\","picUrl":"/data/upload/shop/goods/category/201608011402217020140.jpg","storedName":"201608011402217020140.jpg","childs":null,"name":"水饺汤圆鲜食","id":399,"code":"0506","agentPercent":0,"marketPercent":100,"compPercent":0,"parentId":393,"pic":4104,"sort":6,"stCreateTime":null,"origDeleteFlag":null,"stUpdateTime":null,"createUser":156,"deleteFlag":0,"updateUser":1,"createTime":1469098853000,"dataVersion":2,"createUserType":0,"updateUserType":0,"updateTime":1470031301000},{"path":"\\upload\\shop\\goods\\category\\","picUrl":"/data/upload/shop/goods/category/201608011401419127342.jpg","storedName":"201608011401419127342.jpg","childs":null,"name":"包装干菜","id":400,"code":"0507","agentPercent":0,"marketPercent":100,"compPercent":0,"parentId":393,"pic":4102,"sort":7,"stCreateTime":null,"origDeleteFlag":null,"stUpdateTime":null,"createUser":156,"deleteFlag":0,"updateUser":1,"createTime":1469098874000,"dataVersion":2,"createUserType":0,"updateUserType":0,"updateTime":1470031261000},{"path":"\\upload\\shop\\goods\\category\\","picUrl":"/data/upload/shop/goods/category/201608011401286559446.jpg","storedName":"201608011401286559446.jpg","childs":null,"name":"日配鲜奶","id":401,"code":"0508","agentPercent":0,"marketPercent":100,"compPercent":0,"parentId":393,"pic":4100,"sort":8,"stCreateTime":null,"origDeleteFlag":null,"stUpdateTime":null,"createUser":156,"deleteFlag":0,"updateUser":1,"createTime":1469098900000,"dataVersion":2,"createUserType":0,"updateUserType":0,"updateTime":1470031248000},{"path":"\\upload\\shop\\goods\\category\\","picUrl":"/data/upload/shop/goods/category/201608011359508006861.jpg","storedName":"201608011359508006861.jpg","childs":null,"name":"散装类","id":402,"code":"0509","agentPercent":0,"marketPercent":100,"compPercent":0,"parentId":393,"pic":4094,"sort":9,"stCreateTime":null,"origDeleteFlag":null,"stUpdateTime":null,"createUser":156,"deleteFlag":0,"updateUser":1,"createTime":1469098928000,"dataVersion":2,"createUserType":0,"updateUserType":0,"updateTime":1470031152000}]
     * name : 副食组
     * id : 393
     * code : 06
     * agentPercent : 0
     * marketPercent : 100
     * compPercent : 0
     * parentId : 0
     * pic : 4381
     * sort : null
     * stCreateTime : null
     * origDeleteFlag : null
     * stUpdateTime : null
     * createUser : 156
     * deleteFlag : 0
     * updateUser : 1
     * createTime : 1469098603000
     * dataVersion : 2
     * createUserType : 0
     * updateUserType : 0
     * updateTime : 1470037604000
     *//*

    private String path;
    private String picUrl; //图片的url
    private String storedName;
    private String name; //分类的名称
    private int id; //分类的id
    private String code; //分类的编码
    private int agentPercent;
    private int marketPercent;
    private int compPercent;
    private int parentId; //上级商品的id
    private int pic;
    private Object sort; //商品分类的排序
    private Object stCreateTime;
    private Object origDeleteFlag;
    private Object stUpdateTime;
    private int createUser;
    private int deleteFlag;
    private int updateUser;
    private long createTime;
    private int dataVersion;
    private int createUserType;
    private int updateUserType;
    private long updateTime;
    *//**
     *
     * picUrl : /data/upload/shop/goods/category/201608011500141547306.jpg
     * storedName : 201608011500141547306.jpg
     * childs : null
     * name : 面条
     * id : 394
     * code : 0501
     * agentPercent : 0
     * marketPercent : 100
     * compPercent : 0
     * parentId : 393
     * pic : 4264
     * sort : 1
     * stCreateTime : null
     * origDeleteFlag : null
     * stUpdateTime : null
     * createUser : 156
     * deleteFlag : 0
     * updateUser : 1
     * createTime : 1469098668000
     * dataVersion : 2
     * createUserType : 0
     * updateUserType : 0
     * updateTime : 1470034773000
     *//*

    private List<ChildsBean> childs;//z控件

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getStoredName() {
        return storedName;
    }

    public void setStoredName(String storedName) {
        this.storedName = storedName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getAgentPercent() {
        return agentPercent;
    }

    public void setAgentPercent(int agentPercent) {
        this.agentPercent = agentPercent;
    }

    public int getMarketPercent() {
        return marketPercent;
    }

    public void setMarketPercent(int marketPercent) {
        this.marketPercent = marketPercent;
    }

    public int getCompPercent() {
        return compPercent;
    }

    public void setCompPercent(int compPercent) {
        this.compPercent = compPercent;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public Object getSort() {
        return sort;
    }

    public void setSort(Object sort) {
        this.sort = sort;
    }

    public Object getStCreateTime() {
        return stCreateTime;
    }

    public void setStCreateTime(Object stCreateTime) {
        this.stCreateTime = stCreateTime;
    }

    public Object getOrigDeleteFlag() {
        return origDeleteFlag;
    }

    public void setOrigDeleteFlag(Object origDeleteFlag) {
        this.origDeleteFlag = origDeleteFlag;
    }

    public Object getStUpdateTime() {
        return stUpdateTime;
    }

    public void setStUpdateTime(Object stUpdateTime) {
        this.stUpdateTime = stUpdateTime;
    }

    public int getCreateUser() {
        return createUser;
    }

    public void setCreateUser(int createUser) {
        this.createUser = createUser;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public int getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(int updateUser) {
        this.updateUser = updateUser;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(int dataVersion) {
        this.dataVersion = dataVersion;
    }

    public int getCreateUserType() {
        return createUserType;
    }

    public void setCreateUserType(int createUserType) {
        this.createUserType = createUserType;
    }

    public int getUpdateUserType() {
        return updateUserType;
    }

    public void setUpdateUserType(int updateUserType) {
        this.updateUserType = updateUserType;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public List<ChildsBean> getChilds() {
        return childs;
    }

    public void setChilds(List<ChildsBean> childs) {
        this.childs = childs;
    }

    public static class ChildsBean {
        private String path;
        private String picUrl;
        private String storedName;
        private Object childs;
        private String name;
        private int id;
        private String code;
        private int agentPercent;
        private int marketPercent;
        private int compPercent;
        private int parentId;
        private int pic;
        private int sort;
        private Object stCreateTime;
        private Object origDeleteFlag;
        private Object stUpdateTime;
        private int createUser;
        private int deleteFlag;
        private int updateUser;
        private long createTime;
        private int dataVersion;
        private int createUserType;
        private int updateUserType;
        private long updateTime;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getStoredName() {
            return storedName;
        }

        public void setStoredName(String storedName) {
            this.storedName = storedName;
        }

        public Object getChilds() {
            return childs;
        }

        public void setChilds(Object childs) {
            this.childs = childs;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getAgentPercent() {
            return agentPercent;
        }

        public void setAgentPercent(int agentPercent) {
            this.agentPercent = agentPercent;
        }

        public int getMarketPercent() {
            return marketPercent;
        }

        public void setMarketPercent(int marketPercent) {
            this.marketPercent = marketPercent;
        }

        public int getCompPercent() {
            return compPercent;
        }

        public void setCompPercent(int compPercent) {
            this.compPercent = compPercent;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getPic() {
            return pic;
        }

        public void setPic(int pic) {
            this.pic = pic;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public Object getStCreateTime() {
            return stCreateTime;
        }

        public void setStCreateTime(Object stCreateTime) {
            this.stCreateTime = stCreateTime;
        }

        public Object getOrigDeleteFlag() {
            return origDeleteFlag;
        }

        public void setOrigDeleteFlag(Object origDeleteFlag) {
            this.origDeleteFlag = origDeleteFlag;
        }

        public Object getStUpdateTime() {
            return stUpdateTime;
        }

        public void setStUpdateTime(Object stUpdateTime) {
            this.stUpdateTime = stUpdateTime;
        }

        public int getCreateUser() {
            return createUser;
        }

        public void setCreateUser(int createUser) {
            this.createUser = createUser;
        }

        public int getDeleteFlag() {
            return deleteFlag;
        }

        public void setDeleteFlag(int deleteFlag) {
            this.deleteFlag = deleteFlag;
        }

        public int getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(int updateUser) {
            this.updateUser = updateUser;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getDataVersion() {
            return dataVersion;
        }

        public void setDataVersion(int dataVersion) {
            this.dataVersion = dataVersion;
        }

        public int getCreateUserType() {
            return createUserType;
        }

        public void setCreateUserType(int createUserType) {
            this.createUserType = createUserType;
        }

        public int getUpdateUserType() {
            return updateUserType;
        }

        public void setUpdateUserType(int updateUserType) {
            this.updateUserType = updateUserType;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }*/

}
