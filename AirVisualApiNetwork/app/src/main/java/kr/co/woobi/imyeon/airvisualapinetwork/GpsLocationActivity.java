package kr.co.woobi.imyeon.airvisualapinetwork;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import kr.co.woobi.imyeon.airvisualapinetwork.dustInfoModel.Example;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GpsLocationActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 1000;
    private Retrofit mRetrofit;
    private Service mService;
    private final String MYKEY = "4Mn5Fqqsh4bfJoaBg";
    private Example mData;


    private Button btnShowLocation;
    private TextView txtLat;
    private TextView txtLon;
    private TextView tv_address;
    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;
    String add;

    // GPSTracker class
    private GpsInfo gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_location);

        btnShowLocation = (Button) findViewById(R.id.btn_start);
        txtLat = (TextView) findViewById(R.id.tv_latitude);
        txtLon = (TextView) findViewById(R.id.tv_longitude);
        tv_address = (TextView) findViewById(R.id.tv_address);

        // GPS 정보를 보여주기 위한 이벤트 클래스 등록
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // 권한 요청을 해야 함
                if (!isPermission) {
                    callPermission();
                    return;
                }

                gps = new GpsInfo(GpsLocationActivity.this);
                // GPS 사용유무 가져오기
                if (gps.isGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    txtLat.setText(String.valueOf(latitude));
                    txtLon.setText(String.valueOf(longitude));
                    add = getAddress(gps, latitude, longitude);

                    tv_address.setText(add);

                    mRetrofit = new Retrofit.Builder()
                            .baseUrl("https://api.airvisual.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    mService = mRetrofit.create(Service.class);
                    mService.keylatLon(latitude, longitude, MYKEY).enqueue(new Callback<Example>() {
                        @Override
                        public void onResponse(Call<Example> call, Response<Example> response) {

                            if (response.body().getData() != null) {
                                mData = new Example();
                                mData.setData(response.body().getData());
                                Intent intent = new Intent(GpsLocationActivity.this, MainActivity.class);
//                                    intent.putExtra("city", add);
                                intent.putExtra("city", mData.getData().city);
                                intent.putExtra("ti", mData.getData().getCurrent().getPollution().getTs());
                                intent.putExtra("hu", mData.getData().getCurrent().getWeather().getHu());
                                intent.putExtra("ws", mData.getData().getCurrent().getWeather().getWs());
                                intent.putExtra("hu", mData.getData().getCurrent().getWeather().getHu());
                                intent.putExtra("tp", mData.getData().getCurrent().getWeather().getTp());
                                intent.putExtra("aqicn", mData.getData().getCurrent().getPollution().getAqius());
                                intent.putExtra("aqius", mData.getData().getCurrent().getPollution().getAqicn());
                                startActivityForResult(intent, REQUEST_CODE);
                            }
                        }

                        @Override
                        public void onFailure(Call<Example> call, Throwable t) {
                            Toast.makeText(GpsLocationActivity.this, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                    Toast.makeText(
                            getApplicationContext(),
                            "당신의 위치 - \n위도: " + latitude + "\n경도: " + longitude + "\n주소: " + add,
                            Toast.LENGTH_LONG).show();
                } else {
                    // GPS 를 사용할수 없으므로
                    gps.showSettingsAlert();
                }
            }
        });

        callPermission();  // 권한 요청을 해야 함
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            finish();
        }
    }

    public static String getAddress(Context mContext, double lat, double lng) {
        String nowAddress = "현재 위치를 확인 할 수 없습니다.";
        Geocoder geocoder = new Geocoder(mContext, Locale.KOREA);
        List<Address> address;
        try {
            if (geocoder != null) {
                //세번째 파라미터는 좌표에 대해 주소를 리턴 받는 갯수로
                //한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 최대갯수 설정
                address = geocoder.getFromLocation(lat, lng, 1);

                if (address != null && address.size() > 0) {
                    // 주소 받아오기
                    String currentLocationAddress = address.get(0).getAddressLine(0).toString();
                    nowAddress = currentLocationAddress;
                }
            }

        } catch (IOException e) {
//            Toast.makeText(gps, "주소를 가져 올 수 없습니다.", Toast.LENGTH_LONG).show();

            e.printStackTrace();
        }
        return nowAddress;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocation = true;

        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessCoarseLocation = true;
        }

        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true;
        }
    }

    // 전화번호 권한 요청
    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }
}
