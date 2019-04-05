package kr.co.woobi.imyeon.airvisualapinetwork.countryModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationDustInfo {
    private String status;

    @SerializedName("data")
    private List<Country> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Country> getData() {
        return data;
    }

    public void setData(List<Country> data) {
        this.data = data;
    }
}
