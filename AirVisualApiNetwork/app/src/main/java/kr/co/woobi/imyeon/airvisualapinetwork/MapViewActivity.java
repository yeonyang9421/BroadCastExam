package kr.co.woobi.imyeon.airvisualapinetwork;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MapViewActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    public static final int REQUEST_CODE = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        WebViewFragment nullSchoolViewFragment = WebViewFragment.newInstance("https://earth.nullschool.net/#current/particulates/surface/level/overlay=pm2.5/orthographic=126.89,38.29,3000/loc=127.130,37.617",
                "https://earth.nullschool.net/#current/particulates/surface/level/overlay=pm10/orthographic=126.89,38.29,3000/loc=127.130,37.617");
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_web_container, nullSchoolViewFragment).commit();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_one:
                        WebViewFragment nullSchoolViewFragment = WebViewFragment.newInstance("https://earth.nullschool.net/#current/particulates/surface/level/overlay=pm2.5/orthographic=126.89,38.29,3000/loc=127.130,37.617",
                                "https://earth.nullschool.net/#current/particulates/surface/level/overlay=pm10/orthographic=126.89,38.29,3000/loc=127.130,37.617");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_web_container, nullSchoolViewFragment).commit();
                        break;
                    case R.id.action_two:
                        WebViewFragment anYangFragment = WebViewFragment.newInstance("http://www.webairwatch.com/kaq/modelimg_case4/PM2_5.27KM.Animation.gif",
                                "http://www.webairwatch.com/kaq/modelimg_case4/PM10.27KM.Animation.gif");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_web_container, anYangFragment).commit();
                        break;
                    case R.id.action_three:
                        WebViewFragment japanFragment = WebViewFragment.newInstance("https://tenki.jp/lite/pm25/precip.html",
                                "https://www.airkorea.or.kr/map");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_web_container, japanFragment).commit();
                        break;

                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }
}
