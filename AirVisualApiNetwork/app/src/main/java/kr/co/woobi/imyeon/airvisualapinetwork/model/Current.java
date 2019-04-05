package kr.co.woobi.imyeon.airvisualapinetwork.model;

import kr.co.woobi.imyeon.airvisualapinetwork.model.Pollution;
import kr.co.woobi.imyeon.airvisualapinetwork.model.Weather;

public class Current {

    public Weather weather;
    public Pollution pollution;


    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Pollution getPollution() {
        return pollution;
    }

    public void setPollution(Pollution pollution) {
        this.pollution = pollution;
    }
}
