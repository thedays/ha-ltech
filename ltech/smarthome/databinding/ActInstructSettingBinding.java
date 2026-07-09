package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActInstructSettingBinding extends ViewDataBinding {
    public final AppCompatImageView ivBindGo;
    public final AppCompatImageView ivInstructGo;
    public final AppCompatImageView ivNameGo;
    public final ConstraintLayout layoutBind;
    public final LinearLayout layoutInstruct;
    public final LinearLayout layoutSetName;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Boolean mOwnerOrManager;

    @Bindable
    protected TitleDefault mTitle;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBindMain;
    public final AppCompatTextView tvBindSub;
    public final AppCompatTextView tvDelete;
    public final AppCompatTextView tvInstruct;
    public final AppCompatTextView tvName;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setOwnerOrManager(Boolean ownerOrManager);

    public abstract void setTitle(TitleDefault title);

    protected ActInstructSettingBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivBindGo, AppCompatImageView ivInstructGo, AppCompatImageView ivNameGo, ConstraintLayout layoutBind, LinearLayout layoutInstruct, LinearLayout layoutSetName, LayoutTitleDefaultBinding title, AppCompatTextView tvBindMain, AppCompatTextView tvBindSub, AppCompatTextView tvDelete, AppCompatTextView tvInstruct, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivBindGo = ivBindGo;
        this.ivInstructGo = ivInstructGo;
        this.ivNameGo = ivNameGo;
        this.layoutBind = layoutBind;
        this.layoutInstruct = layoutInstruct;
        this.layoutSetName = layoutSetName;
        this.title = title;
        this.tvBindMain = tvBindMain;
        this.tvBindSub = tvBindSub;
        this.tvDelete = tvDelete;
        this.tvInstruct = tvInstruct;
        this.tvName = tvName;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public Boolean getOwnerOrManager() {
        return this.mOwnerOrManager;
    }

    public static ActInstructSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActInstructSettingBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActInstructSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_instruct_setting, root, attachToRoot, component);
    }

    public static ActInstructSettingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActInstructSettingBinding inflate(LayoutInflater inflater, Object component) {
        return (ActInstructSettingBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_instruct_setting, null, false, component);
    }

    public static ActInstructSettingBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActInstructSettingBinding bind(View view, Object component) {
        return (ActInstructSettingBinding) bind(component, view, R.layout.act_instruct_setting);
    }
}