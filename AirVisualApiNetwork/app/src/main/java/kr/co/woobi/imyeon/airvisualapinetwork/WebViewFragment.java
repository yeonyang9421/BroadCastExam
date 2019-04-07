package kr.co.woobi.imyeon.airvisualapinetwork;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewFragment extends Fragment {
    public static final int REQUEST_CODE = 1000;
    private WebView mWebView1,mWebView2;
    private WebSettings mWebSettings1;
    private WebSettings mWebSettings2;
    String mUrl, mUrl2;


    public static WebViewFragment newInstance(String url) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        fragment.setArguments(args);
        return fragment;
    }
    public static WebViewFragment newInstance(String url, String url2) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        args.putString("url2", url2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() !=null){
            mUrl=getArguments().getString("url");
            mUrl2=getArguments().getString("url2");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_nullschoolview,container,false);

        mWebView1 = (WebView) view.findViewById(R.id.webview1);
        mWebView2 = (WebView) view.findViewById(R.id.webview2);
        mWebView1.setWebViewClient(new WebViewClient());
        mWebView2.setWebViewClient(new WebViewClient());
        mWebSettings1 = mWebView1.getSettings();
        mWebSettings2 = mWebView2.getSettings();
        mWebSettings1.setJavaScriptEnabled(true);
        mWebSettings2.setJavaScriptEnabled(true);
        mWebView1.loadUrl(mUrl);
        mWebView2.loadUrl(mUrl2);


        return view;
    }

}
