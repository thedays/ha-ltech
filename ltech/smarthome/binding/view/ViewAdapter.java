package com.ltech.smarthome.binding.view;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.jakewharton.rxbinding4.view.RxView;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.CircleBar;
import com.ltech.smarthome.view.SignalView;
import com.ltech.smarthome.view.SmartSwitch;
import com.ltech.smarthome.view.SwitchButton;
import io.reactivex.rxjava3.functions.Consumer;
import java.util.concurrent.TimeUnit;
import kotlin.Unit;

/* loaded from: classes3.dex */
public class ViewAdapter {
    public static final int CLICK_INTERVAL = 300;

    public static void onClickCommand(final View view, final BindingCommand<View> clickCommand, final boolean isThrottleFirst) {
        if (isThrottleFirst) {
            RxView.clicks(view).subscribe(new Consumer<Unit>() { // from class: com.ltech.smarthome.binding.view.ViewAdapter.1
                @Override // io.reactivex.rxjava3.functions.Consumer
                public void accept(Unit unit) throws Throwable {
                    BindingCommand bindingCommand = BindingCommand.this;
                    if (bindingCommand != null) {
                        bindingCommand.execute(view);
                        BindingCommand.this.execute();
                    }
                }
            });
        } else {
            RxView.clicks(view).throttleFirst(300L, TimeUnit.MILLISECONDS).subscribe(new Consumer<Unit>() { // from class: com.ltech.smarthome.binding.view.ViewAdapter.2
                @Override // io.reactivex.rxjava3.functions.Consumer
                public void accept(Unit unit) throws Throwable {
                    BindingCommand bindingCommand = BindingCommand.this;
                    if (bindingCommand != null) {
                        bindingCommand.execute(view);
                        BindingCommand.this.execute();
                    }
                }
            });
        }
    }

    public static void setBackground(View view, int resId) {
        view.setBackgroundResource(resId);
    }

    public static void setSrc(ImageView view, int resId) {
        view.setImageResource(resId);
    }

    public static void onClickCommand(ImageView view, Object url, int placeHolderRes, int errorRes, boolean circle) {
        RequestBuilder<Drawable> load = Glide.with(view).load(url);
        if (placeHolderRes > 0) {
            load.placeholder(placeHolderRes);
        }
        if (errorRes > 0) {
            load.error(errorRes);
        }
        if (circle) {
            load.circleCrop();
        }
        load.into(view);
    }

    public static void setTextBold(TextView view, boolean bold) {
        view.getPaint().setFakeBoldText(bold);
    }

    public static void setCircleBarEnable(CircleBar circleBar, boolean enable) {
        circleBar.setEnable(enable);
    }

    public static void setTextGravity(TextView view, int gravity) {
        view.setGravity(gravity);
    }

    public static void setChecked(SwitchButton view, boolean checked) {
        view.setCheckedNotByUser(checked);
    }

    public static void setCheckdChangeListener(SwitchButton view, SwitchButton.OnCheckedChangeListener listener) {
        view.setOnCheckedChangeListener(listener);
    }

    public static void setOnCheckedChangeListener(CompoundButton buttonView, CompoundButton.OnCheckedChangeListener listener) {
        buttonView.setOnCheckedChangeListener(listener);
    }

    public static void setOnUserCheckedChangeListener(SmartSwitch buttonView, SmartSwitch.OnUserCheckedChangeListener listener) {
        buttonView.setOnUserCheckedChangeListener(listener);
    }

    public static void addItemDecoration(RecyclerView recyclerView, RecyclerView.ItemDecoration itemDecoration) {
        recyclerView.addItemDecoration(itemDecoration);
    }

    public static void setCurSignal(SignalView signalView, int signal) {
        signalView.setCurValue(signal);
    }
}