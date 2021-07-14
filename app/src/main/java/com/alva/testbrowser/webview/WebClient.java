package com.alva.testbrowser.webview;

import android.graphics.Bitmap;
import android.net.Uri;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author Alva
 * @since 2021/7/11 20:59
 */
public class WebClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return false;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        view.loadUrl("javascript:(function(){" +
                "window.imageListener.clearImageUrl();" +
                "})()");
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        view.loadUrl("javascript:(function(){" +
                "var imgs=document.getElementsByTagName(\"img\");" +
                "for(var i=0;i<imgs.length;i++)" +
                "{" +
                "window.imageListener.getImageUrl(imgs[i].src);" +
                " imgs[i].onclick=function(e)" +
                " {" +
                " window.imageListener.openImage(this.src);" +
                " e.stopPropagation();" +
                " e.preventDefault();" +
                " }" +
                "}" +
                "})()");
    }
}
