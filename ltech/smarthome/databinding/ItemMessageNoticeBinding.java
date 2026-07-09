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
import com.ltech.smarthome.push.AppNotice;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public abstract class ItemMessageNoticeBinding extends ViewDataBinding {
    public final CircleImageView circleImageView2;
    public final ConstraintLayout layoutItemBg;
    public final View lineBottom;

    @Bindable
    protected AppNotice mAppNotice;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected String mContent;

    @Bindable
    protected String mCreateTime;

    @Bindable
    protected Integer mIconRes;

    @Bindable
    protected Integer mTextColor;
    public final AppCompatTextView tvContent;
    public final AppCompatTextView tvCreateTime;

    public abstract void setAppNotice(AppNotice appNotice);

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setContent(String Content);

    public abstract void setCreateTime(String createTime);

    public abstract void setIconRes(Integer iconRes);

    public abstract void setTextColor(Integer textColor);

    protected ItemMessageNoticeBinding(Object _bindingComponent, View _root, int _localFieldCount, CircleImageView circleImageView2, ConstraintLayout layoutItemBg, View lineBottom, AppCompatTextView tvContent, AppCompatTextView tvCreateTime) {
        super(_bindingComponent, _root, _localFieldCount);
        this.circleImageView2 = circleImageView2;
        this.layoutItemBg = layoutItemBg;
        this.lineBottom = lineBottom;
        this.tvContent = tvContent;
        this.tvCreateTime = tvCreateTime;
    }

    public AppNotice getAppNotice() {
        return this.mAppNotice;
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

    public Integer getTextColor() {
        return this.mTextColor;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ItemMessageNoticeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMessageNoticeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemMessageNoticeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_message_notice, root, attachToRoot, component);
    }

    public static ItemMessageNoticeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMessageNoticeBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemMessageNoticeBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_message_notice, null, false, component);
    }

    public static ItemMessageNoticeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemMessageNoticeBinding bind(View view, Object component) {
        return (ItemMessageNoticeBinding) bind(component, view, R.layout.item_message_notice);
    }
}