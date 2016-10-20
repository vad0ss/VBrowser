package ru.geekbrains.vbrowser;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.Map;

/**
 * Created by Prilepishev Vadim on 17.10.2016.
 */
public class VWebViewClient extends WebViewClient {

    public static String title;
    public static String address;

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
    {
        view.loadUrl(request.toString());
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        VBrowserActivity.progress.setVisibility(View.GONE);
        VBrowserActivity.progress.setProgress(100);
        title = view.getTitle();
        address = view.getOriginalUrl();
        super.onPageFinished(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        VBrowserActivity.progress.setVisibility(View.VISIBLE);
        VBrowserActivity.progress.setProgress(0);
        super.onPageStarted(view, url, favicon);
    }

}
