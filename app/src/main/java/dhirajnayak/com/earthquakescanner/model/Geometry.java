package dhirajnayak.com.earthquakescanner.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dhirajnayak on 1/27/18.
 */

public class Geometry implements Serializable{
    String type;
    ArrayList<String> coordinates;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<String> coordinates) {
        this.coordinates = coordinates;
    }
}
