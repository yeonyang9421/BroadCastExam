package kr.co.woobi.imyeon.airvisualapinetwork.dustInfoModel;

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
