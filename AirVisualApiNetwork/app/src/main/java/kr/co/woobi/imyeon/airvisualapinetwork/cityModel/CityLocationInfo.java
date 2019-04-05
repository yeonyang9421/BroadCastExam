package kr.co.woobi.imyeon.airvisualapinetwork.cityModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityLocationInfo {
    private String status;

    @SerializedName("data")
    private List<City> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<City> getData() {
        return data;
    }

    public void setData(List<City> data) {
        this.data = data;
    }

}
