package dhirajnayak.com.earthquakeLocator.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dhirajnayak on 1/27/18.
 */

public class GeoPlace implements Serializable{

    private String type;
    private Metadata metadata;
    private ArrayList<Feature> features;
    private ArrayList<String> bbox;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public ArrayList<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<Feature> features) {
        this.features = features;
    }

    public ArrayList<String> getBbox() {
        return bbox;
    }

    public void setBbox(ArrayList<String> bbox) {
        this.bbox = bbox;
    }
}
