package kr.co.woobi.imyeon.airvisualapinetwork;

import kr.co.woobi.imyeon.airvisualapinetwork.cityModel.CityLocationInfo;
import kr.co.woobi.imyeon.airvisualapinetwork.stateModel.StateLocationInfo;
import kr.co.woobi.imyeon.airvisualapinetwork.model.Example;
import kr.co.woobi.imyeon.airvisualapinetwork.countryMode.LocationDustInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {

    @GET("v2/nearest_city")
    Call<Example> keyValue(@Query("key") String key);

    @GET("v2/countries")
    Call<LocationDustInfo> keyCountry (@Query("key") String key);

    @GET("v2/states")
    Call<StateLocationInfo> keyState (@Query("country") String country, @Query("key") String key);


    @GET("v2/cities")
    Call<CityLocationInfo> keyCity (@Query("state") String state, @Query("country") String country, @Query("key") String key );


    @GET("v2/city")
    Call<Example> keyCityStateCountry (@Query("city") String city, @Query("state") String state, @Query("country") String country, @Query("key") String key );


}
