package net.swa.util.geography;

public class VoSquare {
    private Double maxLat;
    private Double maxLng;
    private Double minLat;
    private Double minLng;

    public Double getMaxLat() {
        return this.maxLat;
    }

    public void setMaxLat(Double maxLat) {
        this.maxLat = maxLat;
    }

    public Double getMaxLng() {
        return this.maxLng;
    }

    public void setMaxLng(Double maxLng) {
        this.maxLng = maxLng;
    }

    public Double getMinLat() {
        return this.minLat;
    }

    public void setMinLat(Double minLat) {
        this.minLat = minLat;
    }

    public Double getMinLng() {
        return this.minLng;
    }

    public void setMinLng(Double minLng) {
        this.minLng = minLng;
    }

    public String toString() {
        return "VoSquare [maxLat=" + this.maxLat + ", maxLng=" + this.maxLng + ", minLat=" + this.minLat + ", minLng=" + this.minLng + "]";
    }
}
