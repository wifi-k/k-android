package net.treebear.kwifimanager.activity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class WebsiteActivity extends BaseActivity {
    private static final String TAG = "WebsiteActivity";

    @BindView(R.id.toolbar)
    LinearLayout llTitle;
    @BindView(R.id.wv_website)
    WebView wvWebsite;
    private boolean isFullScreen;
    private String title;
    private String url;
    private String rightText;
    private boolean isAutoTitle;

    @Override
    public int layoutId() {
        return R.layout.activity_website;
    }

    @Override
    public void initParams(Bundle params) {
        if (params != null) {
            isFullScreen = params.getBoolean(FULL_SCREEN, false);
            isAutoTitle = params.getBoolean(AUTO_TITLE, false);
            title = params.getString(TITLE, getString(R.string.app_name));
            rightText = params.getString(RIGHT_TEXT, "");
            url = params.getString(URL, getString(R.string.app_name));
        }
    }

    @Override
    protected void initView() {
        if (isFullScreen) {
            llTitle.setVisibility(View.GONE);
            statusTransparentFontBlack();
        } else {
            setTitleBack(title, rightText);
            statusWhiteFontBlack();
        }
        initSettings();
        wvWebsite.loadUrl(url);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initSettings() {
        WebSettings settings = wvWebsite.getSettings();
        //水平不显示
        wvWebsite.setHorizontalScrollBarEnabled(false);
        //垂直不显示
        wvWebsite.setVerticalScrollBarEnabled(false);
        //支持JS
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAllowContentAccess(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAppCacheEnabled(true);
        // 设置可以支持缩放
        settings.setSupportZoom(false);
        // 设置出现缩放工具
        settings.setBuiltInZoomControls(false);
        //扩大比例的缩放
        settings.setUseWideViewPort(false);
        wvWebsite.setWebViewClient(new MyWebViewClient());
        wvWebsite.setWebChromeClient(new MyChromeClient());
        wvWebsite.addJavascriptInterface(new JavaScriptInterface(), "AppInterface");
        wvWebsite.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) ->
                Log.i("aaa", String.format("url = %s\n," +
                                "userAgent = %s\n," +
                                "contentDisposition = %s\n," +
                                "mimetype = %s\n," +
                                "contentLength = %s",
                        url, userAgent, contentDisposition, mimetype, contentLength)));
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_title_right)
    public void onTvTitleRightClicked() {
    }

    /**
     * 主动注入JS语句
     *
     * @param javaScript JavaScript语句
     */
    private void injectJavaScript(@NonNull String javaScript) {
        if (!javaScript.toLowerCase().startsWith("javascript:")) {
            javaScript = "javascript:" + javaScript;
        }
        wvWebsite.loadUrl(javaScript);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.i(TAG, request.getUrl().getPath() + "#####" + error.getDescription());
            }
        }
    }

    public class MyChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            Log.i(TAG, String.format("onProgressChanged:%s", newProgress));
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (isAutoTitle) {
                setTitleBack(title, rightText);
            }
            Log.i(TAG, title);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Log.i(TAG, String.format("onJsAlert: url->%s; message->%s", url, message));
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            Log.i(TAG, String.format("onJsConfirm: url->%s; message->%s", url, message));
            return super.onJsConfirm(view, url, message, result);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            Log.i(TAG, String.format("onJsPrompt: url->%s; message->%s; defaultValue->%s", url, message, defaultValue));
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            Log.i(TAG, String.format("level:%s; line->%s; sourceId->%s; message->%s",
                    consoleMessage.messageLevel(), consoleMessage.lineNumber(),
                    consoleMessage.sourceId(), consoleMessage.message()));
            return super.onConsoleMessage(consoleMessage);
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        }
    }

    @Keep
    private class JavaScriptInterface {
        @Keep
        @JavascriptInterface
        public void toApp(Object object) {
            Log.i(TAG, String.valueOf(object));
        }

        public String fromApp() {
            return TAG;
        }
    }
}
