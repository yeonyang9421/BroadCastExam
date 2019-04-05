package kr.co.woobi.imyeon.airvisualapinetwork.stateModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class StateLocationInfo {
    private String status;

    @SerializedName("data")
    private List<State> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<State> getData() {
        return data;
    }

    public void setData(List<State> data) {
        this.data = data;
    }


}
