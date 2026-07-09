package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSelect3Binding extends ViewDataBinding {
    public final ViewDeviceManageBottomBinding bottom;
    public final RelativeLayout footerView;
    public final RelativeLayout headView;
    public final AppCompatImageView ivBack;
    public final AppCompatImageView ivSearch;
    public final LinearLayout layoutDaliType;
    public final LinearLayout layoutFloor;
    public final LinearLayout layoutSceneSortAndType;
    public final FrameLayout layoutSearch;
    public final LinearLayout layoutSort;
    public final LinearLayout layoutSortAndType;
    public final ConstraintLayout layoutTab;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected Boolean mTitleGone;
    public final RadioButton radioAdd;
    public final RadioButton radioAll;
    public final RadioButton radioCloud;
    public final RadioButton radioDali;
    public final RadioGroup radioDaliType;
    public final RadioButton radioGroup;
    public final RadioButton radioLight;
    public final RadioButton radioLocal;
    public final RadioButton radioOther;
    public final RadioButton radioPanel;
    public final RadioButton radioScene;
    public final RadioButton radioSceneAll;
    public final RadioGroup radioSceneSelectType;
    public final RadioGroup radioSelectType;
    public final ItemSearchBarBinding searchBar;
    public final TabLayout tabTitle;
    public final TabLayout tabs;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvEdit;
    public final AppCompatTextView tvFloorContent;
    public final AppCompatTextView tvSceneSort1;
    public final AppCompatTextView tvSort;
    public final AppCompatTextView tvSort1;
    public final View vTitleBg;
    public final ViewPager2 viewpager;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    public abstract void setTitleGone(Boolean titleGone);

    protected ActSelect3Binding(Object _bindingComponent, View _root, int _localFieldCount, ViewDeviceManageBottomBinding bottom, RelativeLayout footerView, RelativeLayout headView, AppCompatImageView ivBack, AppCompatImageView ivSearch, LinearLayout layoutDaliType, LinearLayout layoutFloor, LinearLayout layoutSceneSortAndType, FrameLayout layoutSearch, LinearLayout layoutSort, LinearLayout layoutSortAndType, ConstraintLayout layoutTab, RadioButton radioAdd, RadioButton radioAll, RadioButton radioCloud, RadioButton radioDali, RadioGroup radioDaliType, RadioButton radioGroup, RadioButton radioLight, RadioButton radioLocal, RadioButton radioOther, RadioButton radioPanel, RadioButton radioScene, RadioButton radioSceneAll, RadioGroup radioSceneSelectType, RadioGroup radioSelectType, ItemSearchBarBinding searchBar, TabLayout tabTitle, TabLayout tabs, LayoutTitleDefaultBinding title, AppCompatTextView tvEdit, AppCompatTextView tvFloorContent, AppCompatTextView tvSceneSort1, AppCompatTextView tvSort, AppCompatTextView tvSort1, View vTitleBg, ViewPager2 viewpager) {
        super(_bindingComponent, _root, _localFieldCount);
        this.bottom = bottom;
        this.footerView = footerView;
        this.headView = headView;
        this.ivBack = ivBack;
        this.ivSearch = ivSearch;
        this.layoutDaliType = layoutDaliType;
        this.layoutFloor = layoutFloor;
        this.layoutSceneSortAndType = layoutSceneSortAndType;
        this.layoutSearch = layoutSearch;
        this.layoutSort = layoutSort;
        this.layoutSortAndType = layoutSortAndType;
        this.layoutTab = layoutTab;
        this.radioAdd = radioAdd;
        this.radioAll = radioAll;
        this.radioCloud = radioCloud;
        this.radioDali = radioDali;
        this.radioDaliType = radioDaliType;
        this.radioGroup = radioGroup;
        this.radioLight = radioLight;
        this.radioLocal = radioLocal;
        this.radioOther = radioOther;
        this.radioPanel = radioPanel;
        this.radioScene = radioScene;
        this.radioSceneAll = radioSceneAll;
        this.radioSceneSelectType = radioSceneSelectType;
        this.radioSelectType = radioSelectType;
        this.searchBar = searchBar;
        this.tabTitle = tabTitle;
        this.tabs = tabs;
        this.title = title;
        this.tvEdit = tvEdit;
        this.tvFloorContent = tvFloorContent;
        this.tvSceneSort1 = tvSceneSort1;
        this.tvSort = tvSort;
        this.tvSort1 = tvSort1;
        this.vTitleBg = vTitleBg;
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

    public static ActSelect3Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelect3Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSelect3Binding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select3, root, attachToRoot, component);
    }

    public static ActSelect3Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelect3Binding inflate(LayoutInflater inflater, Object component) {
        return (ActSelect3Binding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select3, null, false, component);
    }

    public static ActSelect3Binding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelect3Binding bind(View view, Object component) {
        return (ActSelect3Binding) bind(component, view, R.layout.act_select3);
    }
}