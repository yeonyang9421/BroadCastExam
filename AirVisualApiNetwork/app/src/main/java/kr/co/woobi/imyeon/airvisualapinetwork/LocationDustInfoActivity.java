package kr.co.woobi.imyeon.airvisualapinetwork;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kr.co.woobi.imyeon.airvisualapinetwork.cityModel.CityLocationInfo;
import kr.co.woobi.imyeon.airvisualapinetwork.cityModel.City;
import kr.co.woobi.imyeon.airvisualapinetwork.dustInfoModel.Example;
import kr.co.woobi.imyeon.airvisualapinetwork.stateModel.StateLocationInfo;
import kr.co.woobi.imyeon.airvisualapinetwork.countryModel.Country;
import kr.co.woobi.imyeon.airvisualapinetwork.countryModel.LocationDustInfo;
import kr.co.woobi.imyeon.airvisualapinetwork.stateModel.State;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationDustInfoActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 1000;
    private Spinner mSpinnerCountry, mSpinnerState, mSpinnerCity;
    private Service mService;
    private final String MYKEY = "4Mn5Fqqsh4bfJoaBg";
    private Country mCountry;
    private State mState;
    private City mCity;
    private Retrofit mRetrofit;
    private Example mData;
    private ArrayAdapter<Country> mCountryAdapter;
    private ArrayAdapter<State> mStateAdpater;
    private ArrayAdapter<City> mCityAdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_dust_info);

        mSpinnerCountry = findViewById(R.id.spinner_country);
        mSpinnerState = findViewById(R.id.spinner_state);
        mSpinnerCity = findViewById(R.id.spinner_city);

        final List<Country> listCountry = new ArrayList<>();
        final List<State> listState = new ArrayList<>();
        final List<City> listCity = new ArrayList<>();

        mCountryAdapter = new ArrayAdapter<>(LocationDustInfoActivity.this, android.R.layout.simple_spinner_item, listCountry);
        mStateAdpater = new ArrayAdapter<>(LocationDustInfoActivity.this, android.R.layout.simple_spinner_item, listState);
        mCityAdater = new ArrayAdapter<>(LocationDustInfoActivity.this, android.R.layout.simple_spinner_item, listCity);
        mCountryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStateAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCityAdater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCountry.setAdapter(mCountryAdapter);
        mSpinnerState.setAdapter(mStateAdpater);
        mSpinnerCity.setAdapter(mCityAdater);

        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.airvisual.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = mRetrofit.create(Service.class);
        mService.keyCountry(MYKEY).enqueue(new Callback<LocationDustInfo>() {
            @Override
            public void onResponse(Call<LocationDustInfo> call, Response<LocationDustInfo> response) {

                LocationDustInfo data = response.body();
                mCountryAdapter.addAll(data.getData());
            }

            @Override
            public void onFailure(Call<LocationDustInfo> call, Throwable t) {
                Toast.makeText(LocationDustInfoActivity.this, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        mSpinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCountry =mCountryAdapter.getItem(position);
                mService.keyState(mCountry.getCountry(), MYKEY).enqueue(new Callback<StateLocationInfo>() {
                    @Override
                    public void onResponse(Call<StateLocationInfo> call, Response<StateLocationInfo> response) {
                        StateLocationInfo data = response.body();

                        if (data == null) {
                            mStateAdpater.clear();
                            mCityAdater.clear();
                            return;
                        }
                        mStateAdpater.clear();
                        mStateAdpater.addAll(data.getData());
                        mState = mStateAdpater.getItem(0);
                        mService.keyCity(mState.getState(),mCountry.getCountry(), MYKEY).enqueue(new Callback<CityLocationInfo>() {
                            @Override
                            public void onResponse(Call<CityLocationInfo> call, Response<CityLocationInfo> response) {
                                CityLocationInfo data = response.body();
                                mCityAdater.clear();
                                mCityAdater.addAll(data.getData());
                            }

                            @Override
                            public void onFailure(Call<CityLocationInfo> call, Throwable t) {

                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<StateLocationInfo> call, Throwable t) {
                        Toast.makeText(LocationDustInfoActivity.this, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mSpinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mState = mStateAdpater.getItem(position);
                mService.keyCity(mState.getState(), mCountry.getCountry(), MYKEY).enqueue(new Callback<CityLocationInfo>() {
                    @Override
                    public void onResponse(Call<CityLocationInfo> call, Response<CityLocationInfo> response) {
                        Toast.makeText(LocationDustInfoActivity.this, "" + response.body(), Toast.LENGTH_SHORT).show();
                        CityLocationInfo data = response.body();

                        if(data==null){
                            mCityAdater.clear();
                            return;
                        }

                          mCityAdater.clear();
                        mCityAdater.addAll(data.getData());

                    }


                    @Override
                    public void onFailure(Call<CityLocationInfo> call, Throwable t) {
                        Toast.makeText(LocationDustInfoActivity.this, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mSpinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mCity =mCityAdater.getItem(position);
                mService.keyCityStateCountry(mCity.getCity(), mState.getState(), mCountry.getCountry(), MYKEY).enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {

                        mData = new Example();
                        mData.setData(response.body().getData());

                        Toast.makeText(LocationDustInfoActivity.this, "" + mCountry + mState + mCity, Toast.LENGTH_SHORT).show();




                    }

                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {
                        Toast.makeText(LocationDustInfoActivity.this, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void onSendDustInfo(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra("city", mData.getData().city);
        intent.putExtra("hu", mData.getData().getCurrent().getWeather().getHu());
        intent.putExtra("ws", mData.getData().getCurrent().getWeather().getWs());
        intent.putExtra("tp", mData.getData().getCurrent().getWeather().getTp());
        intent.putExtra("aqicn", mData.getData().getCurrent().getPollution().getAqicn());
        intent.putExtra("aqius", mData.getData().getCurrent().getPollution().getAqicn());
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            finish();
        }
    }
}



