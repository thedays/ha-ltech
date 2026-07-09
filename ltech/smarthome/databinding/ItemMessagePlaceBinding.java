package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.push.PlaceMessage;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public abstract class ItemMessagePlaceBinding extends ViewDataBinding {
    public final CircleImageView circleImageView2;
    public final ConstraintLayout layoutItemBg;
    public final View lineBottom;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected String mContent;

    @Bindable
    protected String mCreateTime;

    @Bindable
    protected Integer mIconRes;

    @Bindable
    protected PlaceMessage mPlaceMessage;
    public final AppCompatTextView tvContent;
    public final AppCompatTextView tvCreateTime;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setContent(String Content);

    public abstract void setCreateTime(String createTime);

    public abstract void setIconRes(Integer iconRes);

    public abstract void setPlaceMessage(PlaceMessage placeMessage);

    protected ItemMessagePlaceBinding(Object _bindingComponent, View _root, int _localFieldCount, CircleImageView circleImageView2, ConstraintLayout layoutItemBg, View lineBottom, AppCompatTextView tvContent, AppCompatTextView tvCreateTime) {
        super(_bindingComponent, _root, _localFieldCount);
        this.circleImageView2 = circleImageView2;
        this.layoutItemBg = layoutItemBg;
        this.lineBottom = lineBottom;
        this.tvContent = tvContent;
        this.tvCreateTime = tvCreateTime;
    }

    public PlaceMessage getPlaceMessage() {
        return this.mPlaceMessage;
    }

    public String getCreateTime() {
        return this.mCreateTime;
    }

    public String getContent() {
        return this.mContent;
    }

    public Integer getIconRes() {
        return this.mIconRes;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ItemMessagePlaceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMessagePlaceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemMessagePlaceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_message_place, root, attachToRoot, component);
    }

    public static ItemMessagePlaceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMessagePlaceBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemMessagePlaceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_message_place, null, false, component);
    }

    public static ItemMessagePlaceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMessagePlaceBinding bind(View view, Object component) {
        return (ItemMessagePlaceBinding) bind(component, view, R.layout.item_message_place);
    }
}