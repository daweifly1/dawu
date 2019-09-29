package net.swa.util;

import java.math.BigDecimal;

public class Const {
    public static final String CURRENT_USER = "cuser";
    public static final short USER_TYPE_ADMIN = 0;
    public static final short USER_TYPE_FUWUZHAN = 1;
    public static final short USER_TYPE_EMP = 2;
    public static final int SHI_XIAO = -1;
    public static final int YOU_XIAO = 0;
    public static final String MENDIAN_ROLE = "门店用户";
    public static final String DINGDAN_STATES0 = "0";
    public static final String DINGDAN_STATES1 = "1";
    public static final String DINGDAN_STATES2 = "2";
    public static final String DINGDAN_STATES3 = "3";
    public static final String DINGDAN_STATES4 = "4";
    public static final String DINGDAN_STATES5 = "5";
    public static final String ROLE_FUWUZHAN = "食品公司";
    public static final String ROLE_EMP = "普通员工";
    public static final int MAX_RESULT = 60000;
    public static final String CURRENT_PROCESS = "prcocess";
    public static final int NUM_THREAD = 8;
    public static final int MINUTE_FIVE = 5;
    public static final int MINUTE_FIFTEN = 15;
    public static final String TOKEN = "access_token";
    public static final String HTTP_GETCLIENT_LOCATION = "http://java.softweare.net/wxapi/api/getUserRecentlyLocation.json";
    public static final String APPID = "wx924ac7d11803f9c1";
    public static final String SECRET = "f28f2ba41173c1407cb3a1dfedb4ae02";
    public static final String HTTP_WX__WEB_OAUTH_CHECK = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static final String HTTP_WX_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info";
    public static final String HTTP_WX_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";
    public static final String HTTP_WX_USER_LIST = "https://api.weixin.qq.com/cgi-bin/user/get";
    public static final String HTTP_STATIC_IMAGE_URL = "https://api.weixin.qq.com/cgi-bin/user/get";
    public static final String HTTP_BAIDU_CONVERT_URL = "http://api.map.baidu.com/ag/coord/convert?from=0&to=4";
    public static final String HTTP_BAIDU_CONVERT_URL2 = "http://api.map.baidu.com/ag/coord/convert?from=2&to=4";
    public static final String HTTP_BAIDU_CONVERT_URL3 = "http://map.yanue.net/gpsApi.php?";
    public static final Integer WX_GUANZHU = Integer.valueOf(1);
    public static final Integer WX_QUXIAO = Integer.valueOf(2);
    public static final int STATUS_YES = 1;
    public static final int STATUS_NO = 0;
    public static final int MAXNUM = 1000;
    public static final Object CONFIG_INFO = "configInfo";
    public static final Object CONFIG_SERVER_PRICE = "serverPrice";
    public static final int HOUR48 = 48;
    public static final String FORMAT_STRING1 = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_STRING2 = "yyyy年MM月dd日";
    public static final String CHEXING_STATE_SHOW = "1";
    public static final String CHEXING_STATE_HIDDEN = "0";
    public static final String DEWEI_NAME = "得威";
    public static final String HTTP_BACKUP_MYSQL = "http://localhost/mysql_backup.php";
    public static final String HTTP_MAP_GEO_GAODE = "http://restapi.amap.com/v3/geocode/regeo?key=e4e68a5cf6df32793a5c7f00e7d17253&radius=500";
    public static final String HTTP_MAP_GEO_GOOGLE = "https://maps.google.com/maps/api/geocode/json";
    public static final String DEFAULT_KEY = "dewei_default_key";
    public static final String CODE_UTF8 = "utf-8";
    public static final String ZTREE_AREA_NAME = "区域信息";
    public static final String ZTREE_OPEN = "open";
    public static final String PRICE_CHANGE_TYPE_PLUS = "1";
    public static final String PRICE_CHANGE_TYPE_MINUS = "-1";
    public static final String PRICE_CHANGE_ZONE_ALL = "all";
    public static final String PRICE_CHANGE_ZONE_PART = "part";
    public static final String FOODM2STATE1 = "1";
    public static final String FOODM2STATE2 = "2";
    public static final Double LIMIT_MONEY = Double.valueOf(30.0D);

    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        double v = b1.add(b2).doubleValue();

        return Math.round(v * 100.0D) / 100.0D;
    }

    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        double v = b1.subtract(b2).doubleValue();
        return Math.round(v * 100.0D) / 100.0D;
    }
}
