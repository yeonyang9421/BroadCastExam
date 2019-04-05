package kr.co.woobi.imyeon.airvisualapinetwork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import kr.co.woobi.imyeon.airvisualapinetwork.model.Example;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FindCityActivity extends AppCompatActivity {
    private String mCountry;
    private String mState;
    private String mCity;
    private Retrofit mRetrofit;
    private Service mService;
    private final String MYKEY = "qn5N7AM3TKTLJ3x9b";
    private Example mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_dust_info);

        FindCountryFragment findCountryFragment=new FindCountryFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_container,findCountryFragment).commit();


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventCountryItem event) {
        mCountry = event.getCountry();
        FindStateFragment findStateFragement= FindStateFragment.newInstance(mCountry);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container2,findStateFragement).commit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventStateItem event) {
        mState = event.getState();
        FindCityFragment findCityFragment= FindCityFragment.newInstance(mCountry,mState);
        Toast.makeText(this, ""+mCountry + mState, Toast.LENGTH_SHORT).show();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container2,findCityFragment).commit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventCityItem event) {
        mCity = event.getCity();

        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.airvisual.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = mRetrofit.create(Service.class);
        mService.keyCityStateCountry(mCity, mState, mCountry, MYKEY).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
//                        Toast.makeText(LocationDustInfoActivity.this, "" + response.body(), Toast.LENGTH_SHORT).show();

                mData = new Example();
                mData.setData(response.body().getData());

                       /* for (int i = 0; i < data.size(); i++) {
                            listCity.add(data.get(i).getCity());
                        }*/
                Toast.makeText(FindCityActivity.this, "" + mCountry + mState + mCity, Toast.LENGTH_SHORT).show();



                Intent intent = new Intent(FindCityActivity.this, MainActivity.class);


                intent.putExtra("city", mData.getData().city);
                intent.putExtra("hu", mData.getData().getCurrent().getWeather().getHu());
                intent.putExtra("ws", mData.getData().getCurrent().getWeather().getWs());
                intent.putExtra("tp", mData.getData().getCurrent().getWeather().getTp());
                intent.putExtra("aqicn", mData.getData().getCurrent().getPollution().getAqicn());
                intent.putExtra("aqius", mData.getData().getCurrent().getPollution().getAqicn());
                startActivityForResult(intent, 1000);



                



            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(FindCityActivity.this, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }








    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onGotoMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);


        intent.putExtra("city", mData.getData().city);
        intent.putExtra("hu", mData.getData().getCurrent().getWeather().getHu());
        intent.putExtra("ws", mData.getData().getCurrent().getWeather().getWs());
        intent.putExtra("tp", mData.getData().getCurrent().getWeather().getTp());
        intent.putExtra("aqicn", mData.getData().getCurrent().getPollution().getAqicn());
        intent.putExtra("aqius", mData.getData().getCurrent().getPollution().getAqicn());
        startActivityForResult(intent, 1000);



        Toast.makeText(this, ""+mCountry + mState + mCity, Toast.LENGTH_SHORT).show();


    }
}
