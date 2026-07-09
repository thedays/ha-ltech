package com.ltech.smarthome.ui.device.sonos;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ThreadUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSonosWebViewBinding;
import com.ltech.smarthome.net.response.device.ListDeviceResponse;
import com.ltech.smarthome.utils.Constants;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSonosAuth extends VMActivity<ActSonosWebViewBinding, ActSonosPlayControlVM> {
    private BaseQuickAdapter<ListDeviceResponse.RowsBean, BaseViewHolder> mAdapter;
    private String url;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_sonos_web_view;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle("Sonos");
        String stringExtra = getIntent().getStringExtra(Constants.WS_URL);
        this.url = stringExtra;
        if (stringExtra != null) {
            ((ActSonosPlayControlVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, 0L);
            ((ActSonosWebViewBinding) this.mViewBinding).webView.getSettings().setJavaScriptEnabled(true);
            ((ActSonosWebViewBinding) this.mViewBinding).webView.loadUrl(this.url);
            ((ActSonosWebViewBinding) this.mViewBinding).webView.setWebViewClient(new WebViewClient() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosAuth.1
                @Override // android.webkit.WebViewClient
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    if (url.startsWith("https://ltsit.ltechcloud.cn/devicerouter/sonos/oauth/redirect/code?") || url.startsWith("https://apic.ltsys.com.cn/devicerouter/sonos/oauth/redirect/code?") || url.startsWith("https://apie.ltsys.com.cn/devicerouter/sonos/oauth/redirect/code?")) {
                        return;
                    }
                    super.onPageStarted(view, url, favicon);
                }

                @Override // android.webkit.WebViewClient
                public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                    if (url.startsWith("https://ltsit.ltechcloud.cn/devicerouter/sonos/oauth/redirect/code?") || url.startsWith("https://apic.ltsys.com.cn/devicerouter/sonos/oauth/redirect/code?") || url.startsWith("https://apie.ltsys.com.cn/devicerouter/sonos/oauth/redirect/code?")) {
                        ActSonosAuth.this.showAuthorizing();
                        ((ActSonosWebViewBinding) ActSonosAuth.this.mViewBinding).webView.loadUrl(url);
                        return false;
                    }
                    webView.loadUrl(url);
                    return true;
                }

                @Override // android.webkit.WebViewClient
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    if (url.startsWith("https://ltsit.ltechcloud.cn/devicerouter/sonos/oauth/redirect/code?") || url.startsWith("https://apic.ltsys.com.cn/devicerouter/sonos/oauth/redirect/code?") || url.startsWith("https://apie.ltsys.com.cn/devicerouter/sonos/oauth/redirect/code?")) {
                        view.evaluateJavascript("document.getElementsByTagName('body')[0].innerText", new ValueCallback<String>() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosAuth.1.1
                            @Override // android.webkit.ValueCallback
                            public void onReceiveValue(String value) {
                                ((ActSonosPlayControlVM) ActSonosAuth.this.mViewModel).login(value);
                            }
                        });
                    }
                }
            });
        } else {
            ((ActSonosPlayControlVM) this.mViewModel).sync();
            showAuthorizedSuccess();
        }
        ((ActSonosWebViewBinding) this.mViewBinding).btn.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosAuth.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (((ActSonosWebViewBinding) ActSonosAuth.this.mViewBinding).btn.getText().toString().equals(ActSonosAuth.this.getString(R.string.app_str_sonos_sync))) {
                    ((ActSonosPlayControlVM) ActSonosAuth.this.mViewModel).sync();
                } else {
                    ((ActSonosWebViewBinding) ActSonosAuth.this.mViewBinding).webView.loadUrl(ActSonosAuth.this.url);
                    ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosAuth.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            ((ActSonosWebViewBinding) ActSonosAuth.this.mViewBinding).bgView.setVisibility(8);
                            ((ActSonosWebViewBinding) ActSonosAuth.this.mViewBinding).group.setVisibility(8);
                        }
                    }, 1500L);
                }
            }
        });
        ((ActSonosWebViewBinding) this.mViewBinding).btnRemove.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosAuth.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ((ActSonosPlayControlVM) ActSonosAuth.this.mViewModel).remove();
            }
        });
        initList();
    }

    private void initList() {
        ((ActSonosWebViewBinding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView = ((ActSonosWebViewBinding) this.mViewBinding).rv;
        BaseQuickAdapter<ListDeviceResponse.RowsBean, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ListDeviceResponse.RowsBean, BaseViewHolder>(this, R.layout.item_sonons_devices) { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosAuth.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, ListDeviceResponse.RowsBean device) {
                baseViewHolder.setText(R.id.tv, device.getDevicename());
            }
        };
        this.mAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActSonosPlayControlVM) this.mViewModel).authEvent.observe(this, new Observer<Integer>() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosAuth.5
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer integer) {
                if (integer.intValue() == 0) {
                    ActSonosAuth.this.showAuthorizedSuccess();
                    return;
                }
                if (integer.intValue() == 1) {
                    ActSonosAuth.this.showAuthorizedFailed();
                } else if (integer.intValue() == 3) {
                    ActSonosAuth.this.showSyncNoDevice();
                } else {
                    ActSonosAuth.this.showSyncFailed();
                }
            }
        });
        ((ActSonosPlayControlVM) this.mViewModel).data.observe(this, new Observer<List<ListDeviceResponse.RowsBean>>() { // from class: com.ltech.smarthome.ui.device.sonos.ActSonosAuth.6
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<ListDeviceResponse.RowsBean> devices) {
                if (ActSonosAuth.this.mAdapter != null) {
                    ((ActSonosWebViewBinding) ActSonosAuth.this.mViewBinding).tvDeviceCount.setText(String.format(ActSonosAuth.this.getString(R.string.app_str_sonos_has_sync_devices_num), Integer.valueOf(devices.size())));
                    ActSonosAuth.this.mAdapter.setNewData(devices);
                }
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        super.back();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAuthorizing() {
        ((ActSonosWebViewBinding) this.mViewBinding).tvDeviceCount.setVisibility(8);
        ((ActSonosWebViewBinding) this.mViewBinding).bgView.setVisibility(0);
        ((ActSonosWebViewBinding) this.mViewBinding).group.setVisibility(0);
        ((ActSonosWebViewBinding) this.mViewBinding).iv.setImageResource(R.mipmap.pic_authorize_succes);
        ((ActSonosWebViewBinding) this.mViewBinding).tvStatus.setText("");
        ((ActSonosWebViewBinding) this.mViewBinding).btnRemove.setVisibility(8);
        ((ActSonosWebViewBinding) this.mViewBinding).btn.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAuthorizedFailed() {
        ((ActSonosWebViewBinding) this.mViewBinding).tvDeviceCount.setVisibility(8);
        ((ActSonosWebViewBinding) this.mViewBinding).bgView.setVisibility(0);
        ((ActSonosWebViewBinding) this.mViewBinding).group.setVisibility(0);
        ((ActSonosWebViewBinding) this.mViewBinding).rv.setVisibility(8);
        ((ActSonosWebViewBinding) this.mViewBinding).tvDeviceCount.setVisibility(8);
        ((ActSonosWebViewBinding) this.mViewBinding).iv.setImageResource(R.mipmap.pic_authorize_failed);
        ((ActSonosWebViewBinding) this.mViewBinding).tvStatus.setText(R.string.app_str_sonos_authorized_failed);
        ((ActSonosWebViewBinding) this.mViewBinding).btn.setText(R.string.app_str_sonos_re_authorize);
        ((ActSonosWebViewBinding) this.mViewBinding).btnRemove.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSyncNoDevice() {
        ((ActSonosWebViewBinding) this.mViewBinding).tvDeviceCount.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSyncFailed() {
        ((ActSonosWebViewBinding) this.mViewBinding).tvDeviceCount.setVisibility(8);
        ((ActSonosWebViewBinding) this.mViewBinding).bgView.setVisibility(0);
        ((ActSonosWebViewBinding) this.mViewBinding).group.setVisibility(0);
        ((ActSonosWebViewBinding) this.mViewBinding).rv.setVisibility(0);
        ((ActSonosWebViewBinding) this.mViewBinding).tvDeviceCount.setVisibility(0);
        ((ActSonosWebViewBinding) this.mViewBinding).iv.setImageResource(R.mipmap.pic_authorize_succes);
        ((ActSonosWebViewBinding) this.mViewBinding).tvStatus.setText(R.string.local_scene_sync_fail);
        ((ActSonosWebViewBinding) this.mViewBinding).btn.setText(R.string.app_str_sonos_sync);
        ((ActSonosWebViewBinding) this.mViewBinding).btnRemove.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAuthorizedSuccess() {
        ((ActSonosWebViewBinding) this.mViewBinding).tvDeviceCount.setVisibility(0);
        ((ActSonosWebViewBinding) this.mViewBinding).bgView.setVisibility(0);
        ((ActSonosWebViewBinding) this.mViewBinding).group.setVisibility(0);
        ((ActSonosWebViewBinding) this.mViewBinding).rv.setVisibility(0);
        ((ActSonosWebViewBinding) this.mViewBinding).tvDeviceCount.setVisibility(0);
        ((ActSonosWebViewBinding) this.mViewBinding).iv.setImageResource(R.mipmap.pic_authorize_succes);
        ((ActSonosWebViewBinding) this.mViewBinding).tvStatus.setText(R.string.app_str_sonos_authorized_success);
        ((ActSonosWebViewBinding) this.mViewBinding).btn.setText(R.string.app_str_sonos_sync);
        ((ActSonosWebViewBinding) this.mViewBinding).btnRemove.setVisibility(0);
    }
}