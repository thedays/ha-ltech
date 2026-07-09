package com.ltech.smarthome.ui.device.super_panel;

import android.view.View;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSuperPanelVoiceControlRangeVM extends BaseDeviceSetViewModel {
    public List<FtDeviceAndScene> deviceAndSceneFtList = new ArrayList();
    public MutableLiveData<Integer> selectPosition = new MutableLiveData<>(0);
    public SingleLiveEvent<Void> backEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> editEvent = new SingleLiveEvent<>();
    public MutableLiveData<Integer> diyCount = new MutableLiveData<>(0);
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelVoiceControlRangeVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActSuperPanelVoiceControlRangeVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.iv_back /* 2131296947 */:
                this.backEvent.call();
                break;
            case R.id.iv_edit /* 2131297032 */:
                this.editEvent.call();
                break;
            case R.id.layout_all /* 2131297353 */:
                this.selectPosition.setValue(1);
                break;
            case R.id.layout_diy /* 2131297449 */:
                this.selectPosition.setValue(3);
                break;
            case R.id.layout_part /* 2131297568 */:
                this.selectPosition.setValue(2);
                break;
        }
    }

    public void initDeviceAndSceneFtList(long placeId) {
        this.deviceAndSceneFtList.add(FtDeviceAndScene.newInstance(1, placeId));
        this.deviceAndSceneFtList.add(FtDeviceAndScene.newInstance(2, placeId));
    }
}