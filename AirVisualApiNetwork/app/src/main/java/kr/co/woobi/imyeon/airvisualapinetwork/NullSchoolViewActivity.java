package kr.co.woobi.imyeon.airvisualapinetwork;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NullSchoolViewActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 1000;
    private WebView mWebView;
    private WebSettings mWebSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_null_school_view);

        mWebView = (WebView) findViewById(R.id.webview_nullSchool);
        mWebView.setWebViewClient(new WebViewClient());
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("https://earth.nullschool.net/#current/particulates/surface/level/overlay=pm2.5/orthographic=126.89,38.29,3000/loc=127.130,37.617");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }
}



