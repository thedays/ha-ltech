package com.ltech.smarthome.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.google.android.material.badge.BadgeDrawable;
import com.ltech.smarthome.R;
import java.lang.reflect.Field;

/* loaded from: classes3.dex */
public abstract class BaseDialog<V extends ViewDataBinding> extends DialogFragment {
    private int animStyle;
    private Runnable dismissRunnable;
    private int height;
    protected V mViewBinding;
    private int margin;
    private int width;
    private int x;
    private int y;
    protected int theme = R.style.MyBaseDialog;
    private float dimAmount = 0.4f;
    private int gravity = 17;
    private boolean outCancel = true;

    protected abstract void convertView(V viewBinding, BaseDialog dialog);

    protected abstract int provideLayoutId();

    protected abstract String tag();

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(1, initTheme());
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (this.mViewBinding == null) {
            this.mViewBinding = (V) DataBindingUtil.inflate(layoutInflater, provideLayoutId(), viewGroup, false);
        }
        return this.mViewBinding.getRoot();
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.ltech.smarthome.base.BaseDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnKeyListener
            public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                boolean lambda$onActivityCreated$0;
                lambda$onActivityCreated$0 = BaseDialog.this.lambda$onActivityCreated$0(dialogInterface, i, keyEvent);
                return lambda$onActivityCreated$0;
            }
        });
        if (this.dismissRunnable != null) {
            getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.ltech.smarthome.base.BaseDialog$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    BaseDialog.this.lambda$onActivityCreated$1(dialogInterface);
                }
            });
        }
        convertView(this.mViewBinding, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onActivityCreated$0(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (!this.outCancel || i != 4) {
            return false;
        }
        dismissDialog();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onActivityCreated$1(DialogInterface dialogInterface) {
        this.dismissRunnable.run();
        dismissDialog();
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        getDialog().setOnKeyListener(null);
        V v = this.mViewBinding;
        if (v != null) {
            v.unbind();
            this.mViewBinding = null;
        }
        super.onDestroyView();
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        initParams();
    }

    private void initParams() {
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.dimAmount = this.dimAmount;
            int i = this.gravity;
            if (i != 0) {
                attributes.gravity = i;
            }
            switch (this.gravity) {
                case 17:
                    if (this.animStyle == 0) {
                        this.animStyle = R.style.CenterAnimation;
                        break;
                    }
                    break;
                case 48:
                    if (this.animStyle == 0) {
                        this.animStyle = R.style.TopAnimation;
                        break;
                    }
                    break;
                case 80:
                    if (this.animStyle == 0) {
                        this.animStyle = R.style.BottomAnimation;
                        break;
                    }
                    break;
                case GravityCompat.START /* 8388611 */:
                case BadgeDrawable.TOP_START /* 8388659 */:
                case BadgeDrawable.BOTTOM_START /* 8388691 */:
                    if (this.animStyle == 0) {
                        this.animStyle = R.style.LeftAnimation;
                        break;
                    }
                    break;
                case GravityCompat.END /* 8388613 */:
                case BadgeDrawable.TOP_END /* 8388661 */:
                case BadgeDrawable.BOTTOM_END /* 8388693 */:
                    if (this.animStyle == 0) {
                        this.animStyle = R.style.RightAnimation;
                        break;
                    }
                    break;
            }
            window.setWindowAnimations(this.animStyle);
            int i2 = this.width;
            if (i2 == 0) {
                attributes.width = ScreenUtils.getScreenWidth() - (SizeUtils.dp2px(this.margin) * 2);
            } else if (i2 == -1) {
                attributes.width = -2;
            } else {
                attributes.width = SizeUtils.dp2px(i2);
            }
            int i3 = this.height;
            if (i3 == 0) {
                attributes.height = -2;
            } else {
                attributes.height = SizeUtils.dp2px(i3);
            }
            attributes.x = SizeUtils.dp2px(this.x);
            attributes.y = SizeUtils.dp2px(this.y);
            window.setAttributes(attributes);
        }
        setCancelable(this.outCancel);
    }

    public int initTheme() {
        return this.theme;
    }

    public void showDialog(FragmentManager manager) {
        Fragment findFragmentByTag = manager.findFragmentByTag(tag());
        FragmentTransaction beginTransaction = manager.beginTransaction();
        if (findFragmentByTag != null) {
            beginTransaction.remove(findFragmentByTag);
        }
        if (manager.isDestroyed()) {
            return;
        }
        show(beginTransaction, tag());
    }

    public void showDialog(FragmentActivity activity) {
        if (activity != null) {
            activity.getSupportFragmentManager();
            showDialog(activity.getSupportFragmentManager());
        }
    }

    public void dismissDialog() {
        if (getActivity() == null || getActivity().isFinishing() || getChildFragmentManager().isDestroyed()) {
            return;
        }
        super.dismiss();
    }

    public BaseDialog<V> setDismissRunnable(Runnable dismissRunnable) {
        this.dismissRunnable = dismissRunnable;
        return this;
    }

    public BaseDialog<V> setDimAmount(float dimAmount) {
        this.dimAmount = dimAmount;
        return this;
    }

    public BaseDialog<V> setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public BaseDialog<V> setOutCancel(boolean outCancel) {
        this.outCancel = outCancel;
        return this;
    }

    public BaseDialog<V> setMargin(int margin) {
        this.margin = margin;
        return this;
    }

    public BaseDialog<V> setWidth(int width) {
        this.width = width;
        return this;
    }

    public BaseDialog<V> setHeight(int height) {
        this.height = height;
        return this;
    }

    public BaseDialog<V> setX(int x) {
        this.x = x;
        return this;
    }

    public BaseDialog<V> setY(int y) {
        this.y = y;
        return this;
    }

    public BaseDialog<V> setAnimStyle(int animStyle) {
        this.animStyle = animStyle;
        return this;
    }

    public BaseDialog<V> setTheme(int theme) {
        this.theme = theme;
        return this;
    }

    @Override // androidx.fragment.app.DialogFragment
    public int show(FragmentTransaction ft, String tag) {
        try {
            Class<?> cls = Class.forName("androidx.fragment.app.DialogFragment");
            Object newInstance = cls.getConstructor(null).newInstance(null);
            Field declaredField = cls.getDeclaredField("mDismissed");
            declaredField.setAccessible(true);
            declaredField.set(newInstance, false);
            Field declaredField2 = cls.getDeclaredField("mShownByMe");
            declaredField2.setAccessible(true);
            declaredField2.set(newInstance, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ft.add(this, tag);
        return ft.commitAllowingStateLoss();
    }
}