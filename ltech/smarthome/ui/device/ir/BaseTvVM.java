package com.ltech.smarthome.ui.device.ir;

import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import com.blankj.utilcode.util.ActivityUtils;
import com.kookong.app.data.IrData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.utils.LanguageUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class BaseTvVM extends BaseIrVM {
    public static final String KEY_EXT_FUN = "key_ext_fun";
    public static final String KEY_FUN = "key_fun";
    public static final String KEY_NUM = "key_num";
    private Fragment fromFragment;
    private Fragment ftTvExtFun;
    private Fragment ftTvFun;
    private Fragment ftTvNum;
    private Fragment toFragment;
    public SingleLiveEvent<String> changeFragmentEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<String> currentFragmentKeyEvent = new SingleLiveEvent<>();
    public BindingCommand<View> viewClick = getViewClick();

    protected abstract Fragment createExtFunFragment();

    protected abstract Fragment createFunFragment();

    protected abstract Fragment createNumFragment();

    protected abstract int[] getExistFun();

    protected abstract BindingCommand<View> getViewClick();

    public void switchFragment(FragmentActivity activity, String key, int layoutId) {
        key.hashCode();
        switch (key) {
            case "key_fun":
                this.toFragment = getFunFragment();
                this.currentFragmentKeyEvent.setValue(KEY_FUN);
                break;
            case "key_num":
                if (this.fromFragment != null && KEY_NUM.equals(this.currentFragmentKeyEvent.getValue())) {
                    this.toFragment = getFunFragment();
                    this.currentFragmentKeyEvent.setValue(KEY_FUN);
                    break;
                } else {
                    this.toFragment = getNumFragment();
                    this.currentFragmentKeyEvent.setValue(KEY_NUM);
                    break;
                }
            case "key_ext_fun":
                if (this.fromFragment != null && KEY_EXT_FUN.equals(this.currentFragmentKeyEvent.getValue())) {
                    this.toFragment = getFunFragment();
                    this.currentFragmentKeyEvent.setValue(KEY_FUN);
                    break;
                } else {
                    this.toFragment = getExtFunFragment();
                    this.currentFragmentKeyEvent.setValue(KEY_EXT_FUN);
                    break;
                }
                break;
        }
        Fragment fragment = this.toFragment;
        if (fragment != null) {
            switchFragment(activity, this.fromFragment, fragment, layoutId, KEY_FUN.equals(this.currentFragmentKeyEvent.getValue()));
            this.fromFragment = this.toFragment;
        }
    }

    public List<IrKeyItem> getItemList() {
        ArrayList arrayList = new ArrayList();
        Iterator<IrData.IrKey> it = this.irData.keys.iterator();
        while (it.hasNext()) {
            IrData.IrKey next = it.next();
            int[] existFun = getExistFun();
            int length = existFun.length;
            int i = 0;
            while (true) {
                if (i < length) {
                    if (next.fid == existFun[i]) {
                        break;
                    }
                    i++;
                } else {
                    arrayList.add(new IrKeyItem(LanguageUtils.isChinese(ActivityUtils.getTopActivity()) ? next.fname : next.fkey, next.fid));
                }
            }
        }
        return arrayList;
    }

    private Fragment getFunFragment() {
        if (this.ftTvFun == null) {
            this.ftTvFun = createFunFragment();
        }
        return this.ftTvFun;
    }

    private Fragment getNumFragment() {
        if (this.ftTvNum == null) {
            this.ftTvNum = createNumFragment();
        }
        return this.ftTvNum;
    }

    private Fragment getExtFunFragment() {
        if (this.ftTvExtFun == null) {
            this.ftTvExtFun = createExtFunFragment();
        }
        return this.ftTvExtFun;
    }

    protected void switchFragment(FragmentActivity activity, Fragment from, Fragment to, int layoutId, boolean top) {
        if (to == null || from == to) {
            return;
        }
        FragmentTransaction beginTransaction = activity.getSupportFragmentManager().beginTransaction();
        if (from == null) {
            if (to.isAdded()) {
                return;
            }
            beginTransaction.add(layoutId, to, to.getClass().getName()).commitAllowingStateLoss();
            return;
        }
        if (top) {
            beginTransaction.setCustomAnimations(R.anim.anim_slide_top_in, R.anim.anim_slide_bottom_out);
        } else {
            beginTransaction.setCustomAnimations(R.anim.anim_slide_bottom_in, R.anim.anim_slide_top_out);
        }
        if (!to.isAdded()) {
            beginTransaction.hide(from).add(layoutId, to, to.getClass().getName()).commitAllowingStateLoss();
        } else {
            beginTransaction.hide(from).show(to).commitAllowingStateLoss();
        }
    }
}