package dhirajnayak.com.earthquakeLocator.model;

/**
 * Created by dhirajnayak on 1/27/18.
 */

public class MarkerDetails {
    private float latitude,longitude;
    String magnitude;
    String tsunami;
    String eventTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String title;

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    public String getTsunami() {
        return tsunami;
    }

    public void setTsunami(String tsunami) {
        this.tsunami = tsunami;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }
}
