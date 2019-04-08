package kr.co.woobi.imyeon.airvisualapinetwork.cityModel;

import com.google.gson.annotations.SerializedName;

public class City {
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {

        return city;
    }
}
