package com.sjtu.charles.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.sjtu.charles.adapter.RecyclerViewLoadMoreAdapter;
import com.sjtu.charles.nestedwebview.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements RecyclerViewLoadMoreAdapter.OnLoadMoreListener, RecyclerViewLoadMoreAdapter.OnRecyclerViewItemClickListener {

    private WebView webView;
    private RecyclerView recyclerView;

    private TestAdapter mAdapter;

    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initData();

        initWebView();

        initRecyclerView();

    }

    private void initData() {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        for (int i = 1; i < 20; i++) {
            mDatas.add("this is test data " + (mDatas.size() + 1));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new TestAdapter(mDatas);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setRecyclerView(recyclerView);
    }

    @Override
    public void OnLoadMore() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
                mAdapter.notifyDataSetChanged();
            }
        }, 3000);
    }

    private void initWebView() {
        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDatabasePath("/data/data/com.daimajia.gold/databases/");
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
        //网址添加 https 后，显示 http 图片,KITKAT 及以下版本默认为 MIXED_CONTENT_ALWAYS_ALLOW
        //see http://developer.android.com/intl/zh-cn/reference/android/webkit/WebSettings.html#setMixedContentMode(int)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.setScrollContainer(false);
        webView.loadUrl("http://www.jianshu.com/p/a00f5f2ab2f5");
    }


    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "click position " + position, Toast.LENGTH_SHORT).show();
    }
}
