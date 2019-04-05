package kr.co.woobi.imyeon.airvisualapinetwork.model;

import java.util.List;

public class Location {

    public String type;
    public List<Double> coordinates = null;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }
}
