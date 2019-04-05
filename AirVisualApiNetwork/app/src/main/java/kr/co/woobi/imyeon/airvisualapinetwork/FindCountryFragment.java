package kr.co.woobi.imyeon.airvisualapinetwork;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kr.co.woobi.imyeon.airvisualapinetwork.countryMode.Country;
import kr.co.woobi.imyeon.airvisualapinetwork.countryMode.LocationDustInfo;
import kr.co.woobi.imyeon.airvisualapinetwork.model.Example;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FindCountryFragment extends Fragment {
    RecyclerView mRecyclerView;
    RecyclerViewAdapter mAdapter;

    private Service mService;
    private final String MYKEY = "qn5N7AM3TKTLJ3x9b";
    private String mCountry, mState, mCity;
    private Retrofit mRetrofit;
    private Example mData;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_country, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final List<String> listData = new ArrayList<>();
        mRecyclerView = view.findViewById(R.id.recycler);
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.airvisual.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = mRetrofit.create(Service.class);
        mService.keyCountry(MYKEY).enqueue(new Callback<LocationDustInfo>() {
            @Override
            public void onResponse(Call<LocationDustInfo> call, Response<LocationDustInfo> response) {

                List<Country> data=response.body().getData();
                for (int i = 0; i < data.size(); i++) {
                    listData.add(data.get(i).getCountry());
                }

                mAdapter = new RecyclerViewAdapter(listData);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<LocationDustInfo> call, Throwable t) {

            }
        });
    }
}