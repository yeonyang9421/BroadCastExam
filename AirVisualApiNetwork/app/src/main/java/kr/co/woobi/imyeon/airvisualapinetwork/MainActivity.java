package kr.co.woobi.imyeon.airvisualapinetwork;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import kr.co.woobi.imyeon.airvisualapinetwork.dustInfoModel.Example;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final int REQUEST_CODE = 100;

    private Service mService;
    private final String MYKEY = "4Mn5Fqqsh4bfJoaBg";
    private String TAG = MainActivity.class.getSimpleName();
    private LinearLayout mLinearLayout;
    TextView mTextCity, mTextTemp, mTextHum, mTextPm10, mTextPm2, mTextWs, mTextTime;
    Intent mIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mLinearLayout = findViewById(R.id.back);
        mTextCity = findViewById(R.id.textCity);
        mTextTime=findViewById(R.id.text_time);
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
                        mTextTime.setText(response.body().getData().getCurrent().getPollution().getTs());
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

            int air = (int) mIntent.getIntExtra("aqicn", 0);

            ImageView image_gif = findViewById(R.id.image_gif);
            if (air != 0) {
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
                mTextHum.setText(mIntent.getIntExtra("hu", 0) + " %");
                mTextWs.setText(mIntent.getDoubleExtra("ws", 0) + "  m/sec");
                mTextTemp.setText(mIntent.getIntExtra("tp", 0) + " °C");
                mTextPm2.setText(mIntent.getIntExtra("aqicn", 0) + "  ㎍/m³");
                mTextPm10.setText(mIntent.getIntExtra("aqius", 0) + "  ㎍/m³");
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.first) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
            finish();
        } else if (id == R.id.second) {
            Intent intent = new Intent(this, FindCityActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
            finish();
        } else if (id == R.id.third) {
            Intent intent = new Intent(this, FirstSceneActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
            finish();
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

    public interface OnBackKeyPressedListener {
        public void onBackKey();
    }

    private OnBackKeyPressedListener mOnBackKeyPressedListener;

    public void setOnKeyBackPressedListener(OnBackKeyPressedListener listener) {
        mOnBackKeyPressedListener = listener;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START) || (mOnBackKeyPressedListener != null)) {
            drawer.closeDrawer(GravityCompat.START);
            mOnBackKeyPressedListener.onBackKey();
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}
