package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActCommandCategoryBinding extends ViewDataBinding {
    public final CardView cardColor;
    public final AppCompatImageView ivColorGo;
    public final AppCompatImageView ivNameGo;
    public final LinearLayout layoutSetColor;
    public final LinearLayout layoutSetName;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected Boolean mOwnerOrManager;

    @Bindable
    protected TitleDefault mTitle;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvDelete;
    public final AppCompatTextView tvName;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setOwnerOrManager(Boolean ownerOrManager);

    public abstract void setTitle(TitleDefault title);

    protected ActCommandCategoryBinding(Object _bindingComponent, View _root, int _localFieldCount, CardView cardColor, AppCompatImageView ivColorGo, AppCompatImageView ivNameGo, LinearLayout layoutSetColor, LinearLayout layoutSetName, LayoutTitleDefaultBinding title, AppCompatTextView tvDelete, AppCompatTextView tvName) {
        super(_bindingComponent, _root, _localFieldCount);
        this.cardColor = cardColor;
        this.ivColorGo = ivColorGo;
        this.ivNameGo = ivNameGo;
        this.layoutSetColor = layoutSetColor;
        this.layoutSetName = layoutSetName;
        this.title = title;
        this.tvDelete = tvDelete;
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

    public static ActCommandCategoryBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCommandCategoryBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActCommandCategoryBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_command_category, root, attachToRoot, component);
    }

    public static ActCommandCategoryBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCommandCategoryBinding inflate(LayoutInflater inflater, Object component) {
        return (ActCommandCategoryBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_command_category, null, false, component);
    }

    public static ActCommandCategoryBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCommandCategoryBinding bind(View view, Object component) {
        return (ActCommandCategoryBinding) bind(component, view, R.layout.act_command_category);
    }
}