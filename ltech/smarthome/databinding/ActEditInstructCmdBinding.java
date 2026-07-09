package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.RadioImageTextView;

/* loaded from: classes3.dex */
public abstract class ActEditInstructCmdBinding extends ViewDataBinding {
    public final AppCompatTextView etName;
    public final AppCompatImageView ivInstructGo;
    public final AppCompatImageView ivNameGo;
    public final LinearLayout layoutBottom;
    public final RelativeLayout layoutChoose;
    public final RelativeLayout layoutDataFormat;
    public final RelativeLayout layoutInput;
    public final RelativeLayout layoutName;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final RadioImageTextView radioAscii;
    public final RadioImageTextView radioHex;
    public final RadioImageTextView radioInput;
    public final RadioImageTextView radioLearn;
    public final RadioImageTextView radioLibrary;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBottom;
    public final AppCompatTextView tvInput;
    public final AppCompatTextView tvInputType;
    public final AppCompatTextView tvInstruct;
    public final AppCompatTextView tvName;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActEditInstructCmdBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView etName, AppCompatImageView ivInstructGo, AppCompatImageView ivNameGo, LinearLayout layoutBottom, RelativeLayout layoutChoose, RelativeLayout layoutDataFormat, RelativeLayout layoutInput, RelativeLayout layoutName, RadioImageTextView radioAscii, RadioImageTextView radioHex, RadioImageTextView radioInput, RadioImageTextView radioLearn, RadioImageTextView radioLibrary, LayoutTitleDefaultBinding title, AppCompatTextView tvBottom, AppCompatTextView tvInput, AppCompatTextView tvInputType, AppCompatTextView tvInstruct, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.etName = etName;
        this.ivInstructGo = ivInstructGo;
        this.ivNameGo = ivNameGo;
        this.layoutBottom = layoutBottom;
        this.layoutChoose = layoutChoose;
        this.layoutDataFormat = layoutDataFormat;
        this.layoutInput = layoutInput;
        this.layoutName = layoutName;
        this.radioAscii = radioAscii;
        this.radioHex = radioHex;
        this.radioInput = radioInput;
        this.radioLearn = radioLearn;
        this.radioLibrary = radioLibrary;
        this.title = title;
        this.tvBottom = tvBottom;
        this.tvInput = tvInput;
        this.tvInputType = tvInputType;
        this.tvInstruct = tvInstruct;
        this.tvName = tvName;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActEditInstructCmdBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEditInstructCmdBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActEditInstructCmdBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_edit_instruct_cmd, root, attachToRoot, component);
    }

    public static ActEditInstructCmdBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEditInstructCmdBinding inflate(LayoutInflater inflater, Object component) {
        return (ActEditInstructCmdBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_edit_instruct_cmd, null, false, component);
    }

    public static ActEditInstructCmdBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActEditInstructCmdBinding bind(View view, Object component) {
        return (ActEditInstructCmdBinding) bind(component, view, R.layout.act_edit_instruct_cmd);
    }
}