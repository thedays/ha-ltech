package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.MySpinner;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.upgrade.ActBtOtaVM;

/* loaded from: classes3.dex */
public abstract class ActBtOtaBinding extends ViewDataBinding {
    public final Guideline guideline;
    public final FrameLayout layoutSearch;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActBtOtaVM mViewmodel;
    public final RecyclerView rvContent;
    public final ItemSearchBarBinding searchBar;
    public final MySpinner spinnerFloor;
    public final MySpinner spinnerRoom;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBottom;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActBtOtaVM viewmodel);

    protected ActBtOtaBinding(Object _bindingComponent, View _root, int _localFieldCount, Guideline guideline, FrameLayout layoutSearch, RecyclerView rvContent, ItemSearchBarBinding searchBar, MySpinner spinnerFloor, MySpinner spinnerRoom, LayoutTitleDefaultBinding title, AppCompatTextView tvBottom) {
        super(_bindingComponent, _root, _localFieldCount);
        this.guideline = guideline;
        this.layoutSearch = layoutSearch;
        this.rvContent = rvContent;
        this.searchBar = searchBar;
        this.spinnerFloor = spinnerFloor;
        this.spinnerRoom = spinnerRoom;
        this.title = title;
        this.tvBottom = tvBottom;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActBtOtaVM getViewmodel() {
        return this.mViewmodel;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActBtOtaBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBtOtaBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActBtOtaBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_bt_ota, root, attachToRoot, component);
    }

    public static ActBtOtaBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBtOtaBinding inflate(LayoutInflater inflater, Object component) {
        return (ActBtOtaBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_bt_ota, null, false, component);
    }

    public static ActBtOtaBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActBtOtaBinding bind(View view, Object component) {
        return (ActBtOtaBinding) bind(component, view, R.layout.act_bt_ota);
    }
}