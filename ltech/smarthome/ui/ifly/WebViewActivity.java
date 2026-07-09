package com.ltech.smarthome.ui.ifly;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebBackForwardList;
import android.webkit.WebHistoryItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.iflytek.home.sdk.IFlyHome;
import com.iflytek.home.sdk.callback.AuthResultCallback;
import com.iflytek.home.sdk.callback.IFlyHomeCallback;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActWebViewBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.push.PushManager;
import com.ltech.smarthome.ui.splash.ActSplash;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xiaomi.mipush.sdk.Constants;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import timber.log.Timber;

/* compiled from: WebViewActivity.kt */
@Metadata(d1 = {"\u0000Q\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006*\u0001\u0010\u0018\u0000 (2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002()B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0012\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0014J\u0010\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u0006H\u0002J\u0012\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0016J\u0010\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u0014H\u0014J\b\u0010\u001f\u001a\u00020\fH\u0014J\b\u0010 \u001a\u00020\fH\u0014J\b\u0010!\u001a\u00020\fH\u0016J\u0018\u0010\"\u001a\u00020\f2\u0006\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010\u0006J\b\u0010&\u001a\u00020\fH\u0002J\b\u0010'\u001a\u00020\fH\u0002R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0010\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0011¨\u0006*"}, d2 = {"Lcom/ltech/smarthome/ui/ifly/WebViewActivity;", "Lcom/ltech/smarthome/base/BaseNormalActivity;", "Lcom/ltech/smarthome/databinding/ActWebViewBinding;", "<init>", "()V", "requestUrl", "", "getRequestUrl", "()Ljava/lang/String;", "setRequestUrl", "(Ljava/lang/String;)V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "authResultCallback", "com/ltech/smarthome/ui/ifly/WebViewActivity$authResultCallback$1", "Lcom/ltech/smarthome/ui/ifly/WebViewActivity$authResultCallback$1;", "handleResult", "result", "", "showAlert", "message", "onCreateOptionsMenu", "", "menu", "Landroid/view/Menu;", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "provideLayoutId", "initView", "back", "onBackPressed", "openPDFInBrowser", "context", "Landroid/content/Context;", "url", "showCancelDialog", "removeUser", "Companion", "JsToJava", "app_yingyongbaoRelease"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class WebViewActivity extends BaseNormalActivity<ActWebViewBinding> {
    public static final String EXTRA_AUTH = "auth";
    public static final String EXTRA_AUTH_CODE = "auth_code";
    public static final String EXTRA_CUSTOM_URL = "custom_url";
    public static final String EXTRA_DEVICE_ID = "device_id";
    public static final String EXTRA_TAG = "tag";
    public static final String EXTRA_TARGET = "target";
    public static final String EXTRA_TYPE = "type";
    public static final String EXTRA_URL = "url";
    public static final String IFLY_REDIRECT_URL = "https://homev2.iflyos.cn/device/000d9d32-b027-410d-981d-fead0b8cbddd.LTECH2020091700013/smooth-music";
    private String requestUrl = "";
    private final WebViewActivity$authResultCallback$1 authResultCallback = new AuthResultCallback() { // from class: com.ltech.smarthome.ui.ifly.WebViewActivity$authResultCallback$1
        @Override // com.iflytek.home.sdk.callback.AuthResultCallback
        public void onFailed(String message) {
            Intrinsics.checkNotNullParameter(message, "message");
            Toast.makeText(WebViewActivity.this, message, 0).show();
        }

        @Override // com.iflytek.home.sdk.callback.AuthResultCallback
        public void onSuccess() {
            Toast.makeText(WebViewActivity.this, "授权成功", 0).show();
        }
    };

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_web_view;
    }

    public final String getRequestUrl() {
        return this.requestUrl;
    }

    public final void setRequestUrl(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.requestUrl = str;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility() | 8192);
        }
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeButtonEnabled(true);
        }
        ActionBar supportActionBar2 = getSupportActionBar();
        if (supportActionBar2 != null) {
            supportActionBar2.setDisplayHomeAsUpEnabled(true);
        }
        String stringExtra = getIntent().getStringExtra("tag");
        String stringExtra2 = getIntent().getStringExtra("device_id");
        Timber.d("onCreate deviceId: %s", stringExtra2);
        WebView webView = ((ActWebViewBinding) this.mViewBinding).webView;
        Intrinsics.checkNotNull(webView);
        WebSettings settings = webView.getSettings();
        Intrinsics.checkNotNullExpressionValue(settings, "getSettings(...)");
        settings.setJavaScriptEnabled(true);
        WebView webView2 = ((ActWebViewBinding) this.mViewBinding).webView;
        Intrinsics.checkNotNull(webView2);
        webView2.addJavascriptInterface(new JsToJava(), "androidFun");
        IFlyHome iFlyHome = IFlyHome.INSTANCE;
        WebView webView3 = ((ActWebViewBinding) this.mViewBinding).webView;
        Intrinsics.checkNotNullExpressionValue(webView3, "webView");
        String register$default = IFlyHome.register$default(iFlyHome, webView3, new IFlyHomeCallback() { // from class: com.ltech.smarthome.ui.ifly.WebViewActivity$onCreate$webViewTag$1
            @Override // com.iflytek.home.sdk.callback.IFlyHomeCallback
            public void updateHeaderColor(String color) {
                Intrinsics.checkNotNullParameter(color, "color");
                int parseColor = Color.parseColor(color);
                ActionBar supportActionBar3 = WebViewActivity.this.getSupportActionBar();
                if (supportActionBar3 != null) {
                    supportActionBar3.setBackgroundDrawable(new ColorDrawable(parseColor));
                }
                WebViewActivity.this.getWindow().setStatusBarColor(Color.rgb(Math.max((int) (Color.red(parseColor) * 0.8d), 0), Math.max((int) (Color.green(parseColor) * 0.8d), 0), Math.max((int) (Color.blue(parseColor) * 0.8d), 0)));
            }

            @Override // com.iflytek.home.sdk.callback.IFlyHomeCallback
            public void updateTitle(String title) {
                ViewDataBinding viewDataBinding;
                Intrinsics.checkNotNullParameter(title, "title");
                viewDataBinding = ((BaseNormalActivity) WebViewActivity.this).mViewBinding;
                if (viewDataBinding != null) {
                    WebViewActivity.this.setTitle(title);
                }
            }

            @Override // com.iflytek.home.sdk.callback.IFlyHomeCallback
            public void openNewPage(String tag, String params) {
                Intrinsics.checkNotNullParameter(tag, "tag");
                Intent intent = new Intent(WebViewActivity.this, (Class<?>) WebViewActivity.class);
                intent.putExtra("tag", tag);
                WebViewActivity.this.startActivity(intent);
            }

            @Override // com.iflytek.home.sdk.callback.IFlyHomeCallback
            public void closePage() {
                WebViewActivity.this.finish();
            }

            @Override // com.iflytek.home.sdk.callback.IFlyHomeCallback
            public WebViewClient getWebViewClient() {
                final WebViewActivity webViewActivity = WebViewActivity.this;
                return new WebViewClient() { // from class: com.ltech.smarthome.ui.ifly.WebViewActivity$onCreate$webViewTag$1$getWebViewClient$1
                    @Override // android.webkit.WebViewClient
                    public void onPageFinished(WebView view, String url) {
                        ViewDataBinding viewDataBinding;
                        ViewDataBinding viewDataBinding2;
                        ViewDataBinding viewDataBinding3;
                        ViewDataBinding viewDataBinding4;
                        ViewDataBinding viewDataBinding5;
                        ViewDataBinding viewDataBinding6;
                        ViewDataBinding viewDataBinding7;
                        ViewDataBinding viewDataBinding8;
                        ViewDataBinding viewDataBinding9;
                        super.onPageFinished(view, url);
                        viewDataBinding = ((BaseNormalActivity) WebViewActivity.this).mViewBinding;
                        if (viewDataBinding != null) {
                            viewDataBinding2 = ((BaseNormalActivity) WebViewActivity.this).mViewBinding;
                            ImageButton imageButton = ((ActWebViewBinding) viewDataBinding2).goBack;
                            viewDataBinding3 = ((BaseNormalActivity) WebViewActivity.this).mViewBinding;
                            imageButton.setEnabled(((ActWebViewBinding) viewDataBinding3).webView.canGoBack());
                            viewDataBinding4 = ((BaseNormalActivity) WebViewActivity.this).mViewBinding;
                            ImageButton imageButton2 = ((ActWebViewBinding) viewDataBinding4).goBack;
                            viewDataBinding5 = ((BaseNormalActivity) WebViewActivity.this).mViewBinding;
                            imageButton2.setImageTintList(ColorStateList.valueOf(((ActWebViewBinding) viewDataBinding5).webView.canGoBack() ? -16777216 : Color.parseColor("#A6A6A6")));
                            viewDataBinding6 = ((BaseNormalActivity) WebViewActivity.this).mViewBinding;
                            ImageButton imageButton3 = ((ActWebViewBinding) viewDataBinding6).goForward;
                            viewDataBinding7 = ((BaseNormalActivity) WebViewActivity.this).mViewBinding;
                            imageButton3.setEnabled(((ActWebViewBinding) viewDataBinding7).webView.canGoForward());
                            viewDataBinding8 = ((BaseNormalActivity) WebViewActivity.this).mViewBinding;
                            ImageButton imageButton4 = ((ActWebViewBinding) viewDataBinding8).goForward;
                            viewDataBinding9 = ((BaseNormalActivity) WebViewActivity.this).mViewBinding;
                            imageButton4.setImageTintList(ColorStateList.valueOf(((ActWebViewBinding) viewDataBinding9).webView.canGoForward() ? -16777216 : Color.parseColor("#A6A6A6")));
                        }
                    }

                    @Override // android.webkit.WebViewClient
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        WebViewActivity.this.setRequestUrl(String.valueOf(request != null ? request.getUrl() : null));
                        LHomeLog.i(WebViewActivity.this.getClass(), "load url-->" + (request != null ? request.getUrl() : null));
                        if (StringsKt.endsWith$default(String.valueOf(request != null ? request.getUrl() : null), "pdf", false, 2, (Object) null)) {
                            WebViewActivity webViewActivity2 = WebViewActivity.this;
                            webViewActivity2.openPDFInBrowser(webViewActivity2, String.valueOf(request != null ? request.getUrl() : null));
                            return true;
                        }
                        if (view == null) {
                            return true;
                        }
                        view.loadUrl(String.valueOf(request != null ? request.getUrl() : null));
                        return true;
                    }
                };
            }
        }, stringExtra, null, 8, null);
        String str = stringExtra;
        if (str == null || str.length() == 0) {
            if (getIntent().hasExtra("url")) {
                String stringExtra3 = getIntent().getStringExtra("url");
                if (stringExtra3 != null) {
                    handleResult(IFlyHome.openAuthorizePage$default(IFlyHome.INSTANCE, ((ActWebViewBinding) this.mViewBinding).webView, stringExtra3, (AuthResultCallback) null, 4, (Object) null));
                }
            } else if (getIntent().hasExtra("auth")) {
                String stringExtra4 = getIntent().getStringExtra("auth");
                if (stringExtra4 != null) {
                    handleResult(IFlyHome.INSTANCE.openAuthorizePage(((ActWebViewBinding) this.mViewBinding).webView, stringExtra4, this.authResultCallback));
                }
            } else if (getIntent().hasExtra(EXTRA_AUTH_CODE)) {
                int intExtra = getIntent().getIntExtra(EXTRA_AUTH_CODE, -1);
                if (intExtra != -1) {
                    handleResult(IFlyHome.INSTANCE.openAuthorizePage(((ActWebViewBinding) this.mViewBinding).webView, intExtra, this.authResultCallback));
                } else {
                    Toast.makeText(this, "授权码为空", 0).show();
                    finish();
                }
            } else if (getIntent().hasExtra("target")) {
                String stringExtra5 = getIntent().getStringExtra("target");
                String str2 = stringExtra2;
                if (str2 != null && str2.length() != 0) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("deviceId", stringExtra2);
                    if (stringExtra5 != null) {
                        handleResult(IFlyHome.INSTANCE.openWebPage(register$default, stringExtra5, hashMap));
                    }
                } else if (stringExtra5 != null) {
                    handleResult(IFlyHome.openWebPage$default(IFlyHome.INSTANCE, register$default, stringExtra5, null, 4, null));
                }
            } else if (getIntent().hasExtra(EXTRA_CUSTOM_URL)) {
                String stringExtra6 = getIntent().getStringExtra(EXTRA_CUSTOM_URL);
                if (stringExtra6 != null) {
                    ((ActWebViewBinding) this.mViewBinding).webView.loadUrl(stringExtra6);
                }
            } else if (getIntent().hasExtra("type")) {
                String stringExtra7 = getIntent().getStringExtra("type");
                if (stringExtra7 != null) {
                    IFlyHome.INSTANCE.openWebAccounts(register$default, stringExtra7);
                }
            } else {
                Toast.makeText(this, "无法识别意图", 0).show();
                finish();
            }
        }
        ((ActWebViewBinding) this.mViewBinding).goBack.setEnabled(false);
        ((ActWebViewBinding) this.mViewBinding).goBack.setImageTintList(ColorStateList.valueOf(Color.parseColor("#A6A6A6")));
        ((ActWebViewBinding) this.mViewBinding).goForward.setEnabled(false);
        ((ActWebViewBinding) this.mViewBinding).goForward.setImageTintList(ColorStateList.valueOf(Color.parseColor("#A6A6A6")));
        ((ActWebViewBinding) this.mViewBinding).goBack.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.ifly.WebViewActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                WebViewActivity.onCreate$lambda$1(WebViewActivity.this, view);
            }
        });
        ((ActWebViewBinding) this.mViewBinding).goForward.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.ifly.WebViewActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                WebViewActivity.onCreate$lambda$2(WebViewActivity.this, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(WebViewActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ((ActWebViewBinding) this$0.mViewBinding).webView.goBack();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2(WebViewActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ((ActWebViewBinding) this$0.mViewBinding).webView.goForward();
    }

    private final void handleResult(int result) {
        Log.d("WebView", "result: " + result);
        if (result == -3) {
            showAlert("未登录");
            return;
        }
        if (result == -2) {
            showAlert("参数不合法");
        } else if (result == -1) {
            showAlert("WebView 未注册成功");
        } else {
            if (result != 0) {
                return;
            }
            showAlert("SDK 未初始化");
        }
    }

    private final void showAlert(String message) {
        new AlertDialog.Builder(this).setMessage(message).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() { // from class: com.ltech.smarthome.ui.ifly.WebViewActivity$$ExternalSyntheticLambda7
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                WebViewActivity.showAlert$lambda$3(WebViewActivity.this, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showAlert$lambda$3(WebViewActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        WebHistoryItem currentItem = ((ActWebViewBinding) this.mViewBinding).webView.copyBackForwardList().getCurrentItem();
        String originalUrl = currentItem != null ? currentItem.getOriginalUrl() : null;
        LHomeLog.i(getClass(), "originalUrl -->" + originalUrl);
        if (((ActWebViewBinding) this.mViewBinding).webView.canGoBack()) {
            WebBackForwardList copyBackForwardList = ((ActWebViewBinding) this.mViewBinding).webView.copyBackForwardList();
            Intrinsics.checkNotNullExpressionValue(copyBackForwardList, "copyBackForwardList(...)");
            if (copyBackForwardList.getCurrentIndex() > 0) {
                String url = copyBackForwardList.getItemAtIndex(copyBackForwardList.getCurrentIndex() - 1).getUrl();
                LHomeLog.i(getClass(), "historyUrl -->" + url);
                if (!Intrinsics.areEqual(url, IFLY_REDIRECT_URL)) {
                    List split$default = originalUrl != null ? StringsKt.split$default((CharSequence) originalUrl, new String[]{Constants.COLON_SEPARATOR}, false, 0, 6, (Object) null) : null;
                    Intrinsics.checkNotNull(split$default);
                    Object obj = split$default.get(1);
                    List split$default2 = url != null ? StringsKt.split$default((CharSequence) url, new String[]{Constants.COLON_SEPARATOR}, false, 0, 6, (Object) null) : null;
                    Intrinsics.checkNotNull(split$default2);
                    if (!Intrinsics.areEqual(obj, split$default2.get(1)) && !Intrinsics.areEqual(originalUrl, url)) {
                        ((ActWebViewBinding) this.mViewBinding).webView.goBack();
                        return;
                    }
                }
                finish();
                return;
            }
            finish();
            return;
        }
        super.back();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        finish();
    }

    public final void openPDFInBrowser(Context context, String url) {
        Intrinsics.checkNotNullParameter(context, "context");
        Uri parse = Uri.parse(url);
        Intrinsics.checkNotNullExpressionValue(parse, "parse(...)");
        Intent intent = new Intent("android.intent.action.VIEW", parse);
        intent.putExtra("com.android.browser.application_id", context.getPackageName());
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            Log.w("error", "Activity was not found for intent, " + intent);
        }
    }

    /* compiled from: WebViewActivity.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u0080\u0004\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0007¨\u0006\u0006"}, d2 = {"Lcom/ltech/smarthome/ui/ifly/WebViewActivity$JsToJava;", "", "<init>", "(Lcom/ltech/smarthome/ui/ifly/WebViewActivity;)V", "cancelAccount", "", "app_yingyongbaoRelease"}, k = 1, mv = {2, 0, 0}, xi = 48)
    public final class JsToJava {
        public JsToJava() {
        }

        @JavascriptInterface
        public final void cancelAccount() {
            WebViewActivity.this.showCancelDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showCancelDialog() {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.remove_account_tip)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.ifly.WebViewActivity$$ExternalSyntheticLambda6
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean showCancelDialog$lambda$4;
                showCancelDialog$lambda$4 = WebViewActivity.showCancelDialog$lambda$4(WebViewActivity.this, baseDialog, view);
                return showCancelDialog$lambda$4;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean showCancelDialog$lambda$4(WebViewActivity this$0, BaseDialog baseDialog, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.removeUser();
        return false;
    }

    private final void removeUser() {
        PushManager push = Injection.push();
        Activity topActivity = ActivityUtils.getTopActivity();
        Intrinsics.checkNotNull(topActivity, "null cannot be cast to non-null type androidx.appcompat.app.AppCompatActivity");
        push.pushUnbind((AppCompatActivity) topActivity);
        Observable<Object> delaySubscription = Injection.net().removeUser().delaySubscription(500L, TimeUnit.MILLISECONDS);
        final Function1 function1 = new Function1() { // from class: com.ltech.smarthome.ui.ifly.WebViewActivity$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Unit removeUser$lambda$5;
                removeUser$lambda$5 = WebViewActivity.removeUser$lambda$5(WebViewActivity.this, (Disposable) obj);
                return removeUser$lambda$5;
            }
        };
        ((ObservableSubscribeProxy) delaySubscription.doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.ifly.WebViewActivity$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                WebViewActivity.removeUser$lambda$6(Function1.this, obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.ifly.WebViewActivity$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() {
                WebViewActivity.removeUser$lambda$7(WebViewActivity.this);
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_STOP)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.ifly.WebViewActivity$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                WebViewActivity.removeUser$lambda$8(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit removeUser$lambda$5(WebViewActivity this$0, Disposable disposable) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.removing));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void removeUser$lambda$6(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void removeUser$lambda$7(WebViewActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismissLoadingDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void removeUser$lambda$8(Object obj) {
        Injection.limiter().clear();
        Injection.repo().user().clear();
        Injection.stopLocationService();
        Injection.iot().deInit();
        NavUtils.destination(ActSplash.class).navigation(ActivityUtils.getTopActivity());
        SmartToast.showShort(R.string.remove_account_success);
    }
}