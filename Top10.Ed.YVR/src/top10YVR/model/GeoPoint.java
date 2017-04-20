package top10YVR.model;

/**
 * Created by jacobhsu on 2017-04-12.
 */
public class GeoPoint {
    private double lat;
    private double lon;

    // EFFECTS: constructs geo-point at given latitude (lat) and longitude (lon)
    public GeoPoint(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLatitude() {
        return lat;
    }

    public double getLongitude() {
        return lon;
    }
}
