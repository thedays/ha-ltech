package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

/* loaded from: classes3.dex */
public abstract class ActPlaylistManageBinding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;
    public final SwipeRecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvTip;

    public abstract void setTitle(TitleDefault title);

    protected ActPlaylistManageBinding(Object _bindingComponent, View _root, int _localFieldCount, SwipeRecyclerView rvContent, LayoutTitleDefaultBinding title, AppCompatTextView tvTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.rvContent = rvContent;
        this.title = title;
        this.tvTip = tvTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActPlaylistManageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActPlaylistManageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActPlaylistManageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_playlist_manage, root, attachToRoot, component);
    }

    public static ActPlaylistManageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActPlaylistManageBinding inflate(LayoutInflater inflater, Object component) {
        return (ActPlaylistManageBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_playlist_manage, null, false, component);
    }

    public static ActPlaylistManageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActPlaylistManageBinding bind(View view, Object component) {
        return (ActPlaylistManageBinding) bind(component, view, R.layout.act_playlist_manage);
    }
}