package kr.co.woobi.imyeon.airvisualapinetwork;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {

    @GET("v2/nearest_city")
    Call<Example> keyValue(@Query("key") String key);

}
