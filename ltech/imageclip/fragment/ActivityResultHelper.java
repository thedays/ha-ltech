package com.ltech.imageclip.fragment;

import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

/* loaded from: classes3.dex */
public class ActivityResultHelper {
    private static final String TAG = "ActivityResultHelper";
    private Context mContext;
    private RouterFragment mRouterFragment;

    public interface Callback {
        void onActivityResult(int i, Intent intent);
    }

    public static ActivityResultHelper init(FragmentActivity fragmentActivity) {
        return new ActivityResultHelper(fragmentActivity);
    }

    private ActivityResultHelper(FragmentActivity fragmentActivity) {
        this.mContext = fragmentActivity;
        this.mRouterFragment = getRouterFragment(fragmentActivity);
    }

    private RouterFragment getRouterFragment(FragmentActivity fragmentActivity) {
        RouterFragment findRouterFragment = findRouterFragment(fragmentActivity);
        if (findRouterFragment != null) {
            return findRouterFragment;
        }
        RouterFragment newInstance = RouterFragment.newInstance();
        FragmentManager supportFragmentManager = fragmentActivity.getSupportFragmentManager();
        supportFragmentManager.beginTransaction().add(newInstance, TAG).commitAllowingStateLoss();
        supportFragmentManager.executePendingTransactions();
        return newInstance;
    }

    private RouterFragment findRouterFragment(FragmentActivity fragmentActivity) {
        return (RouterFragment) fragmentActivity.getSupportFragmentManager().findFragmentByTag(TAG);
    }

    public void startActivityForResult(Class<?> cls, Callback callback) {
        startActivityForResult(new Intent(this.mContext, cls), callback);
    }

    public void startActivityForResult(Intent intent, Callback callback) {
        this.mRouterFragment.startActivityForResult(intent, callback);
    }
}