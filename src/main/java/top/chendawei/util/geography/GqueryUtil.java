package top.chendawei.util.geography;

public class GqueryUtil {
    public static final double EARTH_RADIUS = 6371.0D;

    public static VoSquare querySquarePoint(double lng, double lat, double distance) {
        double dlng = 2.0D * Math.asin(Math.sin(distance / 12742.0D) / Math.cos(deg2rad(lat)));
        dlng = rad2deg(dlng);
        double dlat = distance / 6371.0D;
        dlat = rad2deg(dlat);
        VoSquare v = new VoSquare();
        v.setMaxLat(Double.valueOf(lat + dlat));
        v.setMinLng(Double.valueOf(lng - dlng));
        v.setMaxLng(Double.valueOf(lng + dlng));
        v.setMinLat(Double.valueOf(lat - dlat));
        return v;
    }

    public static double deg2rad(double degree) {
        return degree / 180.0D * 3.141592653589793D;
    }

    public static double rad2deg(double radian) {
        return radian * 180.0D / 3.141592653589793D;
    }
}
