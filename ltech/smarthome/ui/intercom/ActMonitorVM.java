package com.ltech.smarthome.ui.intercom;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;

/* loaded from: classes4.dex */
public class ActMonitorVM extends BaseViewModel {
    public SingleLiveEvent<Void> hangupEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> answerEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> unlockEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> voiceSpeakEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> muteAudioEvent = new SingleLiveEvent<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.intercom.ActMonitorVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActMonitorVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.itv_answer /* 2131296908 */:
                this.answerEvent.call();
                break;
            case R.id.itv_hangup /* 2131296909 */:
                this.hangupEvent.call();
                break;
            case R.id.itv_unlock /* 2131296911 */:
                this.unlockEvent.call();
                break;
            case R.id.itv_voice_microphone /* 2131296912 */:
                this.muteAudioEvent.call();
                break;
            case R.id.itv_voice_speak /* 2131296913 */:
                this.voiceSpeakEvent.call();
                break;
        }
    }
}