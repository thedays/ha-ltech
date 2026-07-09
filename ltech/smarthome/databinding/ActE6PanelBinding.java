package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public abstract class ActE6PanelBinding extends ViewDataBinding {
    public final CircleImageView civColor;
    public final Group groupGuide;
    public final AppCompatImageView ivControl;
    public final AppCompatImageView ivDoubt;
    public final AppCompatImageView ivE6d;
    public final AppCompatImageView ivGuide1;
    public final AppCompatImageView ivGuide2;
    public final RelativeLayout keyAction;
    public final RelativeLayout keyObject;
    public final RelativeLayout knobObject;
    public final ConstraintLayout layoutBg;
    public final ScrollView layoutInfo;
    public final LinearLayout layoutKnob;
    public final ConstraintLayout layoutKnobUseTip;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Boolean mManagerOrOwner;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvKey;
    public final RecyclerView rvKnobAction;
    public final TabLayout tabs;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvActionTitle;
    public final AppCompatTextView tvGuide1;
    public final AppCompatTextView tvGuide2;
    public final AppCompatTextView tvInstruction;
    public final AppCompatTextView tvKeyAction;
    public final AppCompatTextView tvKeyObject;
    public final AppCompatTextView tvKnow;
    public final AppCompatTextView tvObject;
    public final AppCompatTextView tvObjectTitle;
    public final AppCompatTextView tvTipMessage;
    public final AppCompatTextView tvTitle;
    public final AppCompatTextView tvType;
    public final View viewTip;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setManagerOrOwner(Boolean managerOrOwner);

    public abstract void setTitle(TitleDefault title);

    protected ActE6PanelBinding(Object _bindingComponent, View _root, int _localFieldCount, CircleImageView civColor, Group groupGuide, AppCompatImageView ivControl, AppCompatImageView ivDoubt, AppCompatImageView ivE6d, AppCompatImageView ivGuide1, AppCompatImageView ivGuide2, RelativeLayout keyAction, RelativeLayout keyObject, RelativeLayout knobObject, ConstraintLayout layoutBg, ScrollView layoutInfo, LinearLayout layoutKnob, ConstraintLayout layoutKnobUseTip, RecyclerView rvKey, RecyclerView rvKnobAction, TabLayout tabs, LayoutTitleDefaultBinding title, AppCompatTextView tvActionTitle, AppCompatTextView tvGuide1, AppCompatTextView tvGuide2, AppCompatTextView tvInstruction, AppCompatTextView tvKeyAction, AppCompatTextView tvKeyObject, AppCompatTextView tvKnow, AppCompatTextView tvObject, AppCompatTextView tvObjectTitle, AppCompatTextView tvTipMessage, AppCompatTextView tvTitle, AppCompatTextView tvType, View viewTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.civColor = civColor;
        this.groupGuide = groupGuide;
        this.ivControl = ivControl;
        this.ivDoubt = ivDoubt;
        this.ivE6d = ivE6d;
        this.ivGuide1 = ivGuide1;
        this.ivGuide2 = ivGuide2;
        this.keyAction = keyAction;
        this.keyObject = keyObject;
        this.knobObject = knobObject;
        this.layoutBg = layoutBg;
        this.layoutInfo = layoutInfo;
        this.layoutKnob = layoutKnob;
        this.layoutKnobUseTip = layoutKnobUseTip;
        this.rvKey = rvKey;
        this.rvKnobAction = rvKnobAction;
        this.tabs = tabs;
        this.title = title;
        this.tvActionTitle = tvActionTitle;
        this.tvGuide1 = tvGuide1;
        this.tvGuide2 = tvGuide2;
        this.tvInstruction = tvInstruction;
        this.tvKeyAction = tvKeyAction;
        this.tvKeyObject = tvKeyObject;
        this.tvKnow = tvKnow;
        this.tvObject = tvObject;
        this.tvObjectTitle = tvObjectTitle;
        this.tvTipMessage = tvTipMessage;
        this.tvTitle = tvTitle;
        this.tvType = tvType;
        this.viewTip = viewTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public Boolean getManagerOrOwner() {
        return this.mManagerOrOwner;
    }

    public static ActE6PanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActE6PanelBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActE6PanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_e6_panel, root, attachToRoot, component);
    }

    public static ActE6PanelBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActE6PanelBinding inflate(LayoutInflater inflater, Object component) {
        return (ActE6PanelBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_e6_panel, null, false, component);
    }

    public static ActE6PanelBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActE6PanelBinding bind(View view, Object component) {
        return (ActE6PanelBinding) bind(component, view, R.layout.act_e6_panel);
    }
}