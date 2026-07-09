package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.MySpinner;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public abstract class ActChildMcuUpgradeBinding extends ViewDataBinding {
    public final Group groupTop;
    public final View line;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rv;
    public final SwitchButton sbAuto;
    public final MySpinner spinnerFloor;
    public final MySpinner spinnerRoom;
    public final LayoutTitleDefaultBinding title;
    public final View topBg;
    public final AppCompatTextView tvAutoTime;
    public final TextView tvLabel;
    public final AppCompatTextView tvLabel1;
    public final AppCompatTextView tvLabel2;
    public final AppCompatTextView tvNum;
    public final AppCompatTextView tvOk;
    public final AppCompatTextView tvSelect;
    public final AppCompatTextView tvStop;
    public final View view;
    public final View view2;

    public abstract void setTitle(TitleDefault title);

    protected ActChildMcuUpgradeBinding(Object _bindingComponent, View _root, int _localFieldCount, Group groupTop, View line, RecyclerView rv, SwitchButton sbAuto, MySpinner spinnerFloor, MySpinner spinnerRoom, LayoutTitleDefaultBinding title, View topBg, AppCompatTextView tvAutoTime, TextView tvLabel, AppCompatTextView tvLabel1, AppCompatTextView tvLabel2, AppCompatTextView tvNum, AppCompatTextView tvOk, AppCompatTextView tvSelect, AppCompatTextView tvStop, View view, View view2) {
        super(_bindingComponent, _root, _localFieldCount);
        this.groupTop = groupTop;
        this.line = line;
        this.rv = rv;
        this.sbAuto = sbAuto;
        this.spinnerFloor = spinnerFloor;
        this.spinnerRoom = spinnerRoom;
        this.title = title;
        this.topBg = topBg;
        this.tvAutoTime = tvAutoTime;
        this.tvLabel = tvLabel;
        this.tvLabel1 = tvLabel1;
        this.tvLabel2 = tvLabel2;
        this.tvNum = tvNum;
        this.tvOk = tvOk;
        this.tvSelect = tvSelect;
        this.tvStop = tvStop;
        this.view = view;
        this.view2 = view2;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActChildMcuUpgradeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActChildMcuUpgradeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActChildMcuUpgradeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_child_mcu_upgrade, root, attachToRoot, component);
    }

    public static ActChildMcuUpgradeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActChildMcuUpgradeBinding inflate(LayoutInflater inflater, Object component) {
        return (ActChildMcuUpgradeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_child_mcu_upgrade, null, false, component);
    }

    public static ActChildMcuUpgradeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActChildMcuUpgradeBinding bind(View view, Object component) {
        return (ActChildMcuUpgradeBinding) bind(component, view, R.layout.act_child_mcu_upgrade);
    }
}