package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public abstract class ItemSearchBarNoEditMusicBinding extends ViewDataBinding {
    public final AppCompatTextView cancelBtn;
    public final AppCompatTextView searchEdtTxt;
    public final LinearLayout searchLayout;

    protected ItemSearchBarNoEditMusicBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView cancelBtn, AppCompatTextView searchEdtTxt, LinearLayout searchLayout) {
        super(_bindingComponent, _root, _localFieldCount);
        this.cancelBtn = cancelBtn;
        this.searchEdtTxt = searchEdtTxt;
        this.searchLayout = searchLayout;
    }

    public static ItemSearchBarNoEditMusicBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSearchBarNoEditMusicBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemSearchBarNoEditMusicBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_search_bar_no_edit_music, root, attachToRoot, component);
    }

    public static ItemSearchBarNoEditMusicBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSearchBarNoEditMusicBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemSearchBarNoEditMusicBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_search_bar_no_edit_music, null, false, component);
    }

    public static ItemSearchBarNoEditMusicBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemSearchBarNoEditMusicBinding bind(View view, Object component) {
        return (ItemSearchBarNoEditMusicBinding) bind(component, view, R.layout.item_search_bar_no_edit_music);
    }
}