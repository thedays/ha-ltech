package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.automation.ActAddAutomationVM;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

/* loaded from: classes3.dex */
public abstract class ActAddAutomationBinding extends ViewDataBinding {
    public final CardView cardView2;
    public final ItemGo1Binding goItem;
    public final AppCompatImageView ivChangePic;
    public final AppCompatImageView ivGo;
    public final AppCompatImageView ivGo1;
    public final AppCompatImageView ivGo2;
    public final AppCompatImageView ivStatusCondition;
    public final AppCompatImageView ivStatusConditionGo;
    public final RelativeLayout layoutBg;
    public final RelativeLayout layoutExpand;
    public final RelativeLayout layoutInterval;
    public final NestedScrollView layoutLoad;
    public final RelativeLayout layoutSelectGateway;
    public final ConstraintLayout layoutStatusConditionBg;
    public final RelativeLayout layoutTimes;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActAddAutomationVM mViewmodel;
    public final RadioButton radioCloud;
    public final RadioButton radioLocal;
    public final RadioGroup radioType;
    public final SwipeRecyclerView rvAction;
    public final SwipeRecyclerView rvCondition;
    public final SwipeRecyclerView rvExecCondition;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvConditionExecType;
    public final AppCompatTextView tvConditionType;
    public final AppCompatTextView tvGateway;
    public final AppCompatTextView tvInterval;
    public final AppCompatTextView tvName;
    public final AppCompatTextView tvOffline;
    public final AppCompatTextView tvRemove;
    public final AppCompatTextView tvStatusCondition;
    public final AppCompatTextView tvTimes;

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActAddAutomationVM viewmodel);

    protected ActAddAutomationBinding(Object _bindingComponent, View _root, int _localFieldCount, CardView cardView2, ItemGo1Binding goItem, AppCompatImageView ivChangePic, AppCompatImageView ivGo, AppCompatImageView ivGo1, AppCompatImageView ivGo2, AppCompatImageView ivStatusCondition, AppCompatImageView ivStatusConditionGo, RelativeLayout layoutBg, RelativeLayout layoutExpand, RelativeLayout layoutInterval, NestedScrollView layoutLoad, RelativeLayout layoutSelectGateway, ConstraintLayout layoutStatusConditionBg, RelativeLayout layoutTimes, RadioButton radioCloud, RadioButton radioLocal, RadioGroup radioType, SwipeRecyclerView rvAction, SwipeRecyclerView rvCondition, SwipeRecyclerView rvExecCondition, LayoutTitleDefaultBinding title, AppCompatTextView tvConditionExecType, AppCompatTextView tvConditionType, AppCompatTextView tvGateway, AppCompatTextView tvInterval, AppCompatTextView tvName, AppCompatTextView tvOffline, AppCompatTextView tvRemove, AppCompatTextView tvStatusCondition, AppCompatTextView tvTimes) {
        super(_bindingComponent, _root, _localFieldCount);
        this.cardView2 = cardView2;
        this.goItem = goItem;
        this.ivChangePic = ivChangePic;
        this.ivGo = ivGo;
        this.ivGo1 = ivGo1;
        this.ivGo2 = ivGo2;
        this.ivStatusCondition = ivStatusCondition;
        this.ivStatusConditionGo = ivStatusConditionGo;
        this.layoutBg = layoutBg;
        this.layoutExpand = layoutExpand;
        this.layoutInterval = layoutInterval;
        this.layoutLoad = layoutLoad;
        this.layoutSelectGateway = layoutSelectGateway;
        this.layoutStatusConditionBg = layoutStatusConditionBg;
        this.layoutTimes = layoutTimes;
        this.radioCloud = radioCloud;
        this.radioLocal = radioLocal;
        this.radioType = radioType;
        this.rvAction = rvAction;
        this.rvCondition = rvCondition;
        this.rvExecCondition = rvExecCondition;
        this.title = title;
        this.tvConditionExecType = tvConditionExecType;
        this.tvConditionType = tvConditionType;
        this.tvGateway = tvGateway;
        this.tvInterval = tvInterval;
        this.tvName = tvName;
        this.tvOffline = tvOffline;
        this.tvRemove = tvRemove;
        this.tvStatusCondition = tvStatusCondition;
        this.tvTimes = tvTimes;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActAddAutomationVM getViewmodel() {
        return this.mViewmodel;
    }

    public static ActAddAutomationBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddAutomationBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActAddAutomationBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_automation, root, attachToRoot, component);
    }

    public static ActAddAutomationBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddAutomationBinding inflate(LayoutInflater inflater, Object component) {
        return (ActAddAutomationBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_automation, null, false, component);
    }

    public static ActAddAutomationBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddAutomationBinding bind(View view, Object component) {
        return (ActAddAutomationBinding) bind(component, view, R.layout.act_add_automation);
    }
}