package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.RadioImageTextView;

/* loaded from: classes3.dex */
public abstract class ItemAddInstructContentBinding extends ViewDataBinding {
    public final AppCompatTextView etName;
    public final AppCompatImageView ivInstructGo;
    public final AppCompatImageView ivNameGo;
    public final RelativeLayout layoutDataFormat;
    public final RelativeLayout layoutInput;
    public final RelativeLayout layoutName;
    public final RadioImageTextView radioAscii;
    public final RadioImageTextView radioHex;
    public final RadioImageTextView radioInput;
    public final RadioImageTextView radioLearn;
    public final RadioImageTextView radioLibrary;
    public final AppCompatTextView tvInput;
    public final AppCompatTextView tvInstruct;
    public final AppCompatTextView tvName;

    protected ItemAddInstructContentBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView etName, AppCompatImageView ivInstructGo, AppCompatImageView ivNameGo, RelativeLayout layoutDataFormat, RelativeLayout layoutInput, RelativeLayout layoutName, RadioImageTextView radioAscii, RadioImageTextView radioHex, RadioImageTextView radioInput, RadioImageTextView radioLearn, RadioImageTextView radioLibrary, AppCompatTextView tvInput, AppCompatTextView tvInstruct, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.etName = etName;
        this.ivInstructGo = ivInstructGo;
        this.ivNameGo = ivNameGo;
        this.layoutDataFormat = layoutDataFormat;
        this.layoutInput = layoutInput;
        this.layoutName = layoutName;
        this.radioAscii = radioAscii;
        this.radioHex = radioHex;
        this.radioInput = radioInput;
        this.radioLearn = radioLearn;
        this.radioLibrary = radioLibrary;
        this.tvInput = tvInput;
        this.tvInstruct = tvInstruct;
        this.tvName = tvName;
    }

    public static ItemAddInstructContentBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAddInstructContentBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemAddInstructContentBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_add_instruct_content, root, attachToRoot, component);
    }

    public static ItemAddInstructContentBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAddInstructContentBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemAddInstructContentBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_add_instruct_content, null, false, component);
    }

    public static ItemAddInstructContentBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAddInstructContentBinding bind(View view, Object component) {
        return (ItemAddInstructContentBinding) bind(component, view, R.layout.item_add_instruct_content);
    }
}