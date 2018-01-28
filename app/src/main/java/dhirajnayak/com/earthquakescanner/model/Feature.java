package dhirajnayak.com.earthquakescanner.model;

import java.io.Serializable;

/**
 * Created by dhirajnayak on 1/27/18.
 */

public class Feature implements Serializable{
    String type;
    Property properties;
    Geometry geometry;
    String id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Property getProperties() {
        return properties;
    }

    public void setProperties(Property properties) {
        this.properties = properties;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
