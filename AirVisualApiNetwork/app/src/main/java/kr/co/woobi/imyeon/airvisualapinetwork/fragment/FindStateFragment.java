package kr.co.woobi.imyeon.airvisualapinetwork.fragment;

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

import kr.co.woobi.imyeon.airvisualapinetwork.R;
import kr.co.woobi.imyeon.airvisualapinetwork.Service;
import kr.co.woobi.imyeon.airvisualapinetwork.adapter.RecyclerViewAdapterForState;
import kr.co.woobi.imyeon.airvisualapinetwork.dustInfoModel.Example;
import kr.co.woobi.imyeon.airvisualapinetwork.stateModel.State;
import kr.co.woobi.imyeon.airvisualapinetwork.stateModel.StateLocationInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FindStateFragment extends Fragment {
    RecyclerView mRecyclerView;
    RecyclerViewAdapterForState mAdapter;

    private Service mService;
    private final String MYKEY = "4Mn5Fqqsh4bfJoaBg";
    private String mCountry, mState, mCity, mInfo;
    private Retrofit mRetrofit;
    private Example mData;

    public static FindStateFragment newInstance(String country) {
        FindStateFragment fragment = new FindStateFragment();
        Bundle args = new Bundle();
        args.putString("country", country);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCountry = getArguments().getString("country");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_country, container, false);

        mRecyclerView = view.findViewById(R.id.recycler);
        final List<String> listData = new ArrayList<>();


        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.airvisual.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = mRetrofit.create(Service.class);
        mService.keyState(mCountry, MYKEY).enqueue(new Callback<StateLocationInfo>() {
            @Override
            public void onResponse(Call<StateLocationInfo> call, Response<StateLocationInfo> response) {

                if (response.body() != null) {


                    if (response.body().getData() != null) {
                        List<State> data = response.body().getData();
                        for (int i = 0; i < data.size(); i++) {
                            listData.add(data.get(i).getState());
                        }

                        mAdapter = new RecyclerViewAdapterForState(listData);
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<StateLocationInfo> call, Throwable t) {

            }
        });

        return view;
    }

}