package kr.co.woobi.imyeon.airvisualapinetwork;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import kr.co.woobi.imyeon.airvisualapinetwork.model.Example;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Service mService;
    private final String MYKEY = "qn5N7AM3TKTLJ3x9b";
    private String TAG = MainActivity.class.getSimpleName();
    private LinearLayout mLinearLayout;
    TextView mTextCity, mTextTemp, mTextHum, mTextPm10, mTextPm2, mTextWs;
    Intent mIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLinearLayout = findViewById(R.id.back);
        mTextCity = findViewById(R.id.textCity);
        mTextTemp = findViewById(R.id.textTemp);
        mTextHum = findViewById(R.id.textHum);
        mTextPm10 = findViewById(R.id.textPm10);
        mTextPm2 = findViewById(R.id.textPm2);
        mTextWs = findViewById(R.id.textWs);
//        ImageView imageGood=findViewById(R.id.image_good);
//
//        GlideDrawableImageViewTarget good= new GlideDrawableImageViewTarget(imageGood);
//        Glide.with(this).load(R.drawable.good).into(good);

         mIntent = getIntent();
        String city = mIntent.getStringExtra("city");
        if (city == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.airvisual.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mService = retrofit.create(Service.class);


            mService.keyValue(MYKEY).enqueue(new Callback<Example>() {
                @Override
                public void onResponse(Call<Example> call, Response<Example> response) {
                    Drawable good = getResources().getDrawable(R.drawable.good);
                    Drawable normal = getResources().getDrawable(R.drawable.normal);
                    Drawable bad = getResources().getDrawable(R.drawable.bad);
                    Drawable verybad = getResources().getDrawable(R.drawable.verybad);
                    int air = response.body().getData().getCurrent().getPollution().aqicn;

                    ImageView image_gif = findViewById(R.id.image_gif);
                    if (response.body() != null) {
                        if (air <= 15) {
                            mLinearLayout.setBackground(good);
                            GlideDrawableImageViewTarget goodgif = new GlideDrawableImageViewTarget(image_gif);
                            Glide.with(MainActivity.this).load(R.drawable.gif1).into(goodgif);


                        } else if (air <= 30) {
                            mLinearLayout.setBackground(normal);

                            GlideDrawableImageViewTarget goodgif = new GlideDrawableImageViewTarget(image_gif);
                            Glide.with(MainActivity.this).load(R.drawable.gif2).into(goodgif);

                        } else if (air <= 50) {
                            mLinearLayout.setBackground(bad);
                            GlideDrawableImageViewTarget goodgif = new GlideDrawableImageViewTarget(image_gif);
                            Glide.with(MainActivity.this).load(R.drawable.gif3).into(goodgif);

                        } else {
                            mLinearLayout.setBackground(verybad);
                            GlideDrawableImageViewTarget goodgif = new GlideDrawableImageViewTarget(image_gif);
                            Glide.with(MainActivity.this).load(R.drawable.gif4).into(goodgif);

                        }

                        mTextCity.setText(response.body().getData().city);
                        mTextHum.setText(response.body().getData().getCurrent().getWeather().hu + " %");
                        mTextWs.setText(response.body().getData().getCurrent().getWeather().ws + "  m/sec");
                        mTextTemp.setText(response.body().getData().getCurrent().getWeather().tp + " °C");
                        mTextPm2.setText(response.body().getData().getCurrent().getPollution().aqicn + "  ㎍/m³");
                        mTextPm10.setText(response.body().getData().getCurrent().getPollution().aqius + "  ㎍/m³");
                        Toast.makeText(MainActivity.this, "" + response.body().getData().getCurrent().getPollution().aqicn, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                }
            });

        } else {

            Drawable good = getResources().getDrawable(R.drawable.good);
            Drawable normal = getResources().getDrawable(R.drawable.normal);
            Drawable bad = getResources().getDrawable(R.drawable.bad);
            Drawable verybad = getResources().getDrawable(R.drawable.verybad);


            int air = (int) mIntent.getIntExtra("aqicn",0);

            ImageView image_gif = findViewById(R.id.image_gif);
            if (air !=0) {
                if (air <= 15) {
                    mLinearLayout.setBackground(good);
                    GlideDrawableImageViewTarget goodgif = new GlideDrawableImageViewTarget(image_gif);
                    Glide.with(MainActivity.this).load(R.drawable.gif1).into(goodgif);


                } else if (air <= 30) {
                    mLinearLayout.setBackground(normal);

                    GlideDrawableImageViewTarget goodgif = new GlideDrawableImageViewTarget(image_gif);
                    Glide.with(MainActivity.this).load(R.drawable.gif2).into(goodgif);

                } else if (air <= 50) {
                    mLinearLayout.setBackground(bad);
                    GlideDrawableImageViewTarget goodgif = new GlideDrawableImageViewTarget(image_gif);
                    Glide.with(MainActivity.this).load(R.drawable.gif3).into(goodgif);

                } else {
                    mLinearLayout.setBackground(verybad);
                    GlideDrawableImageViewTarget goodgif = new GlideDrawableImageViewTarget(image_gif);
                    Glide.with(MainActivity.this).load(R.drawable.gif4).into(goodgif);

                }

                mTextCity.setText(mIntent.getStringExtra("city"));
                mTextHum.setText(mIntent.getDoubleExtra("hu",0)+ " %");
                mTextWs.setText(mIntent.getDoubleExtra("ws",0) + "  m/sec");
                mTextTemp.setText(mIntent.getIntExtra("tp",0) + " °C");
                mTextPm2.setText(mIntent.getIntExtra("aqicn",0) + "  ㎍/m³");
                mTextPm10.setText(mIntent.getIntExtra("aqius",0)+ "  ㎍/m³");
            }


        }


    }


}
