package com.ltech.smarthome.ui.device.light;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.ui.mode.ActMode;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;

/* loaded from: classes4.dex */
public class ActChoiceLightTypeVM extends BaseViewModel {
    public Device controlDevice;
    public long controlId;
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.light.ActChoiceLightTypeVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActChoiceLightTypeVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.light_type_Ct /* 2131297707 */:
                navigation(NavUtils.destination(ActMode.class).withInt(Constants.LIGHT_TYPE, 2));
                break;
            case R.id.light_type_Dim /* 2131297708 */:
                navigation(NavUtils.destination(ActMode.class).withInt(Constants.LIGHT_TYPE, 1));
                break;
            case R.id.light_type_Rgb /* 2131297709 */:
                navigation(NavUtils.destination(ActMode.class).withInt(Constants.LIGHT_TYPE, 3));
                break;
            case R.id.light_type_RgbW /* 2131297710 */:
                navigation(NavUtils.destination(ActMode.class).withInt(Constants.LIGHT_TYPE, 4));
                break;
            case R.id.light_type_RgbWy /* 2131297711 */:
                navigation(NavUtils.destination(ActMode.class).withInt(Constants.LIGHT_TYPE, 5));
                break;
        }
    }
}