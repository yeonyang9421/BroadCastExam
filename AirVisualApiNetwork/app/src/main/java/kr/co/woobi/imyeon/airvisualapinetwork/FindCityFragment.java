package kr.co.woobi.imyeon.airvisualapinetwork;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kr.co.woobi.imyeon.airvisualapinetwork.cityModel.City;
import kr.co.woobi.imyeon.airvisualapinetwork.cityModel.CityLocationInfo;
import kr.co.woobi.imyeon.airvisualapinetwork.model.Example;
import kr.co.woobi.imyeon.airvisualapinetwork.stateModel.State;
import kr.co.woobi.imyeon.airvisualapinetwork.stateModel.StateLocationInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FindCityFragment extends Fragment {
    RecyclerView mRecyclerView;
    RecyclerViewAdapterForCity mAdapter;

    private Service mService;
    private final String MYKEY = "4Mn5Fqqsh4bfJoaBg";
    private String mCountry, mState, mCity, mInfo;
    private Retrofit mRetrofit;
    private Example mData;


    public static FindCityFragment newInstance(String country, String state) {
        FindCityFragment fragment = new FindCityFragment();
        Bundle args = new Bundle();
        args.putString("country", country);
        args.putString("state", state);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCountry = getArguments().getString("country");
            mState = getArguments().getString("state");
        }

    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_find_country, container, false);

        mRecyclerView = view.findViewById(R.id.recycler);
        final List<String> listData = new ArrayList<>();

        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.airvisual.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = mRetrofit.create(Service.class);
        mService.keyCity(mState,mCountry, MYKEY).enqueue(new Callback<CityLocationInfo>() {
            @Override
            public void onResponse(Call<CityLocationInfo> call, Response<CityLocationInfo> response) {

                List<City> data = response.body().getData();
                for (int i = 0; i < data.size(); i++) {
                    listData.add(data.get(i).getCity());
                }

                mAdapter = new RecyclerViewAdapterForCity(listData);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();


//                Toast.makeText(view.getContext(), ""+mCity+mState+mCountry, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<CityLocationInfo> call, Throwable t) {

            }
        });

        return view;
    }

}