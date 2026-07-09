package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.google.android.material.textfield.TextInputLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActCameraInfoBinding extends ViewDataBinding {
    public final AppCompatButton btNext;
    public final AppCompatEditText etSerialNum;
    public final AppCompatEditText etVerifyCode;
    public final AppCompatImageView ivCamera;
    public final LinearLayout layoutPic;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected String mSerialNum;

    @Bindable
    protected Boolean mShowInput;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected String mVerifyCode;
    public final RelativeLayout serialEditTextContainer;
    public final EditText seriesNumberEt;
    public final TextInputLayout tilSerialNum;
    public final TextInputLayout tilVerifyCode;
    public final TextView tvCameraSerial;
    public final AppCompatTextView tvQuestion;
    public final TextView tvScanTip;
    public final View vFocus;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setSerialNum(String serialNum);

    public abstract void setShowInput(Boolean showInput);

    public abstract void setTitle(TitleDefault title);

    public abstract void setVerifyCode(String verifyCode);

    protected ActCameraInfoBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatButton btNext, AppCompatEditText etSerialNum, AppCompatEditText etVerifyCode, AppCompatImageView ivCamera, LinearLayout layoutPic, RelativeLayout serialEditTextContainer, EditText seriesNumberEt, TextInputLayout tilSerialNum, TextInputLayout tilVerifyCode, TextView tvCameraSerial, AppCompatTextView tvQuestion, TextView tvScanTip, View vFocus) {
        super(_bindingComponent, _root, _localFieldCount);
        this.btNext = btNext;
        this.etSerialNum = etSerialNum;
        this.etVerifyCode = etVerifyCode;
        this.ivCamera = ivCamera;
        this.layoutPic = layoutPic;
        this.serialEditTextContainer = serialEditTextContainer;
        this.seriesNumberEt = seriesNumberEt;
        this.tilSerialNum = tilSerialNum;
        this.tilVerifyCode = tilVerifyCode;
        this.tvCameraSerial = tvCameraSerial;
        this.tvQuestion = tvQuestion;
        this.tvScanTip = tvScanTip;
        this.vFocus = vFocus;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public Boolean getShowInput() {
        return this.mShowInput;
    }

    public String getSerialNum() {
        return this.mSerialNum;
    }

    public String getVerifyCode() {
        return this.mVerifyCode;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActCameraInfoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCameraInfoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActCameraInfoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_camera_info, root, attachToRoot, component);
    }

    public static ActCameraInfoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCameraInfoBinding inflate(LayoutInflater inflater, Object component) {
        return (ActCameraInfoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_camera_info, null, false, component);
    }

    public static ActCameraInfoBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCameraInfoBinding bind(View view, Object component) {
        return (ActCameraInfoBinding) bind(component, view, R.layout.act_camera_info);
    }
}