package com.ltech.smarthome.ui.ifly;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.ViewDataBinding;
import com.aispeech.dca.DcaSdk;
import com.aispeech.dca.web.CustomWebview;
import com.aispeech.dca.web.WebType;
import com.aispeech.dca.web.WebViewFragment;
import com.aispeech.dca.web.WebViewParam;
import com.iflytek.home.sdk.callback.AuthResultCallback;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActDcaWebViewBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.utils.Constants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DcaWebViewActivity.kt */
@Metadata(d1 = {"\u0000W\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0001\r\u0018\u0000 $2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001$B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0012\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0014J\b\u0010\u000b\u001a\u00020\bH\u0014J\u0010\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0012\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u0011H\u0014J\b\u0010\u001d\u001a\u00020\bH\u0014J\b\u0010\u001e\u001a\u00020\bH\u0014J\b\u0010\u001f\u001a\u00020\bH\u0016J\u0018\u0010 \u001a\u00020\b2\u0006\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\u0014R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000e¨\u0006%"}, d2 = {"Lcom/ltech/smarthome/ui/ifly/DcaWebViewActivity;", "Lcom/ltech/smarthome/base/BaseNormalActivity;", "Lcom/ltech/smarthome/databinding/ActDcaWebViewBinding;", "<init>", "()V", "fragment", "Lcom/aispeech/dca/web/WebViewFragment;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onStart", "authResultCallback", "com/ltech/smarthome/ui/ifly/DcaWebViewActivity$authResultCallback$1", "Lcom/ltech/smarthome/ui/ifly/DcaWebViewActivity$authResultCallback$1;", "handleResult", "result", "", "showAlert", "message", "", "onCreateOptionsMenu", "", "menu", "Landroid/view/Menu;", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "provideLayoutId", "initView", "back", "onBackPressed", "openPDFInBrowser", "context", "Landroid/content/Context;", "url", "Companion", "app_yingyongbaoRelease"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class DcaWebViewActivity extends BaseNormalActivity<ActDcaWebViewBinding> {
    public static final String EXTRA_WEB_TYPE = "web_type";
    private final DcaWebViewActivity$authResultCallback$1 authResultCallback = new AuthResultCallback() { // from class: com.ltech.smarthome.ui.ifly.DcaWebViewActivity$authResultCallback$1
        @Override // com.iflytek.home.sdk.callback.AuthResultCallback
        public void onFailed(String message) {
            Intrinsics.checkNotNullParameter(message, "message");
            Toast.makeText(DcaWebViewActivity.this, message, 0).show();
        }

        @Override // com.iflytek.home.sdk.callback.AuthResultCallback
        public void onSuccess() {
            Toast.makeText(DcaWebViewActivity.this, "授权成功", 0).show();
        }
    };
    private WebViewFragment fragment;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_dca_web_view;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        WebType webType;
        super.onCreate(savedInstanceState);
        int intExtra = getIntent().getIntExtra(EXTRA_WEB_TYPE, 0);
        long longExtra = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        String stringExtra = getIntent().getStringExtra(Constants.DCA_ID);
        Injection.repo().device().getDeviceById(longExtra);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager != null ? fragmentManager.beginTransaction() : null;
        WebViewParam.Builder hideTitle = new WebViewParam.Builder().productId(stringExtra).productVersion("1").aliasKey("prod").deviceId(DcaSdk.getCurrentDeviceId()).hideTitle(true);
        if (intExtra != 0) {
            webType = intExtra != 1 ? WebType.SMARTHOME_DEVICE_LIST : WebType.SKILL_STORE;
        } else {
            webType = WebType.PRODUCT_SKILL;
        }
        WebViewFragment dcaWebViewFragment = DcaSdk.getDcaWebViewFragment(hideTitle.webType(webType).build());
        if (beginTransaction != null) {
            beginTransaction.add(R.id.web_view, dcaWebViewFragment);
        }
        if (beginTransaction != null) {
            beginTransaction.commit();
        }
        this.fragment = dcaWebViewFragment;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        Fragment findFragmentById;
        View view;
        CustomWebview customWebview;
        super.onStart();
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager == null || (findFragmentById = fragmentManager.findFragmentById(R.id.web_view)) == null || (view = findFragmentById.getView()) == null || (customWebview = (CustomWebview) view.findViewById(R.id.webview)) == null) {
            return;
        }
        customWebview.setWebViewClient(new WebViewClient() { // from class: com.ltech.smarthome.ui.ifly.DcaWebViewActivity$onStart$1
            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView view2, String url) {
                ViewDataBinding viewDataBinding;
                super.onPageFinished(view2, url);
                Log.i("WebViewFragment", "return onPageFinished : " + url);
                String title = view2 != null ? view2.getTitle() : null;
                if (TextUtils.isEmpty(title)) {
                    return;
                }
                viewDataBinding = ((BaseNormalActivity) DcaWebViewActivity.this).mViewBinding;
                if (viewDataBinding != null) {
                    DcaWebViewActivity.this.setTitle(title);
                }
            }
        });
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
        new AlertDialog.Builder(this).setMessage(message).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() { // from class: com.ltech.smarthome.ui.ifly.DcaWebViewActivity$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                DcaWebViewActivity.showAlert$lambda$0(DcaWebViewActivity.this, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showAlert$lambda$0(DcaWebViewActivity this$0, DialogInterface dialogInterface, int i) {
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
        WebViewFragment webViewFragment = this.fragment;
        if (webViewFragment == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fragment");
            webViewFragment = null;
        }
        if (webViewFragment.goBack()) {
            return;
        }
        finish();
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
}