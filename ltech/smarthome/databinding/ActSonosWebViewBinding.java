package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSonosWebViewBinding extends ViewDataBinding {
    public final View bg;
    public final View bgView;
    public final AppCompatTextView btn;
    public final AppCompatTextView btnRemove;
    public final LinearLayout controlBar;
    public final ImageButton goBack;
    public final ImageButton goForward;
    public final Group group;
    public final AppCompatImageView iv;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rv;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvDeviceCount;
    public final AppCompatTextView tvStatus;
    public final WebView webView;

    public abstract void setTitle(TitleDefault title);

    protected ActSonosWebViewBinding(Object _bindingComponent, View _root, int _localFieldCount, View bg, View bgView, AppCompatTextView btn, AppCompatTextView btnRemove, LinearLayout controlBar, ImageButton goBack, ImageButton goForward, Group group, AppCompatImageView iv, RecyclerView rv, LayoutTitleDefaultBinding title, AppCompatTextView tvDeviceCount, AppCompatTextView tvStatus, WebView webView) {
        super(_bindingComponent, _root, _localFieldCount);
        this.bg = bg;
        this.bgView = bgView;
        this.btn = btn;
        this.btnRemove = btnRemove;
        this.controlBar = controlBar;
        this.goBack = goBack;
        this.goForward = goForward;
        this.group = group;
        this.iv = iv;
        this.rv = rv;
        this.title = title;
        this.tvDeviceCount = tvDeviceCount;
        this.tvStatus = tvStatus;
        this.webView = webView;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActSonosWebViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSonosWebViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSonosWebViewBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_sonos_web_view, root, attachToRoot, component);
    }

    public static ActSonosWebViewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSonosWebViewBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSonosWebViewBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_sonos_web_view, null, false, component);
    }

    public static ActSonosWebViewBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSonosWebViewBinding bind(View view, Object component) {
        return (ActSonosWebViewBinding) bind(component, view, R.layout.act_sonos_web_view);
    }
}