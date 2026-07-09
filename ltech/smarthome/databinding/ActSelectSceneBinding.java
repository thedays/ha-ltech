package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSelectSceneBinding extends ViewDataBinding {
    public final LinearLayout layoutFloor;
    public final FrameLayout layoutSearch;
    public final LinearLayout layoutSort;
    public final RelativeLayout layoutSortAndType;
    public final LinearLayout llShadow;

    @Bindable
    protected String mBottomTip;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected Boolean mTitleGone;
    public final RadioButton radioAll;
    public final RadioButton radioCloud;
    public final RadioButton radioDali;
    public final RadioButton radioLocal;
    public final RadioGroup radioSelectType;
    public final ItemSearchBarBinding searchBar;
    public final TabLayout tabs;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBottom;
    public final AppCompatTextView tvFloorContent;
    public final AppCompatTextView tvSort;
    public final AppCompatTextView tvSort1;
    public final ViewPager2 viewpager;

    public abstract void setBottomTip(String bottomTip);

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    public abstract void setTitleGone(Boolean titleGone);

    protected ActSelectSceneBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout layoutFloor, FrameLayout layoutSearch, LinearLayout layoutSort, RelativeLayout layoutSortAndType, LinearLayout llShadow, RadioButton radioAll, RadioButton radioCloud, RadioButton radioDali, RadioButton radioLocal, RadioGroup radioSelectType, ItemSearchBarBinding searchBar, TabLayout tabs, LayoutTitleDefaultBinding title, AppCompatTextView tvBottom, AppCompatTextView tvFloorContent, AppCompatTextView tvSort, AppCompatTextView tvSort1, ViewPager2 viewpager) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutFloor = layoutFloor;
        this.layoutSearch = layoutSearch;
        this.layoutSort = layoutSort;
        this.layoutSortAndType = layoutSortAndType;
        this.llShadow = llShadow;
        this.radioAll = radioAll;
        this.radioCloud = radioCloud;
        this.radioDali = radioDali;
        this.radioLocal = radioLocal;
        this.radioSelectType = radioSelectType;
        this.searchBar = searchBar;
        this.tabs = tabs;
        this.title = title;
        this.tvBottom = tvBottom;
        this.tvFloorContent = tvFloorContent;
        this.tvSort = tvSort;
        this.tvSort1 = tvSort1;
        this.viewpager = viewpager;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public Boolean getTitleGone() {
        return this.mTitleGone;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public String getBottomTip() {
        return this.mBottomTip;
    }

    public static ActSelectSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectSceneBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSelectSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_scene, root, attachToRoot, component);
    }

    public static ActSelectSceneBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectSceneBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSelectSceneBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_scene, null, false, component);
    }

    public static ActSelectSceneBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectSceneBinding bind(View view, Object component) {
        return (ActSelectSceneBinding) bind(component, view, R.layout.act_select_scene);
    }
}