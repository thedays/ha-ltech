package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class ActSuperPanelSelectPhotoBinding extends ViewDataBinding {
    public final FrameLayout fmImageList;
    public final RelativeLayout footerView;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final AppCompatTextView tvPreview;
    public final AppCompatTextView tvUpload;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected ActSuperPanelSelectPhotoBinding(Object _bindingComponent, View _root, int _localFieldCount, FrameLayout fmImageList, RelativeLayout footerView, AppCompatTextView tvPreview, AppCompatTextView tvUpload) {
        super(_bindingComponent, _root, _localFieldCount);
        this.fmImageList = fmImageList;
        this.footerView = footerView;
        this.tvPreview = tvPreview;
        this.tvUpload = tvUpload;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActSuperPanelSelectPhotoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelSelectPhotoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSuperPanelSelectPhotoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_super_panel_select_photo, root, attachToRoot, component);
    }

    public static ActSuperPanelSelectPhotoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelSelectPhotoBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSuperPanelSelectPhotoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_super_panel_select_photo, null, false, component);
    }

    public static ActSuperPanelSelectPhotoBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelSelectPhotoBinding bind(View view, Object component) {
        return (ActSuperPanelSelectPhotoBinding) bind(component, view, R.layout.act_super_panel_select_photo);
    }
}