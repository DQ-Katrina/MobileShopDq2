package com.example.Katrina.mobileshopdq.fragment;

import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.Katrina.mobileshopdq.R;
import com.example.Katrina.mobileshopdq.commom.BaseFragment;
import com.example.Katrina.mobileshopdq.view.MyWebView;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.webView)
    MyWebView myWebView;

    @OnClick(R.id.home_title_search)
    void search(){
        toastShort("开发中...");
    }

    @Override
    public int getContentViewId(){
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view){
        super.initView(view);
        initWebView();
        initSwipeRefreshLayout();
    }

    private void initSwipeRefreshLayout(){
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,android.R.color.holo_green_light,android.R.color.holo_orange_light,android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myWebView.reload();
            }
        });
    }
    private void initWebView(){
        myWebView.setVerticalScrollBarEnabled(false);
        myWebView.setHorizontalFadingEdgeEnabled(false);

        WebSettings settings = myWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);

        myWebView.setOnCustomeScorllChanged(new MyWebView.IWebViewScroll() {
            @Override
            public void onTop() {
                mSwipeRefreshLayout.setEnabled(true);
            }

            @Override
            public void notonTop() {
                mSwipeRefreshLayout.setEnabled(false);
            }
        });
        myWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction()==KeyEvent.ACTION_DOWN){
                    if (keyCode==KeyEvent.KEYCODE_BACK && myWebView.canGoBack()){
                        myWebView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
        myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                mSwipeRefreshLayout.setRefreshing(true);
                Log.e(TAG, "onPageStarted");
            }

            @Override
            public void onPageFinished(WebView view, String url){
                mSwipeRefreshLayout.setRefreshing(false);
                Log.e(TAG, "onPageFinished");
            }
        });
        myWebView.loadUrl("https://www.ijq.tv/mingxing/15445050495617.html");
    }
}
