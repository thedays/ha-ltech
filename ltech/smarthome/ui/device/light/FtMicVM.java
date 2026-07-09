package com.ltech.smarthome.ui.device.light;

import android.content.Context;
import android.view.View;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.service.music.RecordThread;
import com.smart.product_agreement.param.LightCmdParam;

/* loaded from: classes4.dex */
public class FtMicVM extends BaseViewModel {
    public long controlId;
    public Object controlObject;
    public boolean groupControl;
    private LightCmdParam mLightCmdParam;
    private RecordThread mRecordThread;
    public MutableLiveData<Boolean> startRecordLiveData = new MutableLiveData<>(false);
    public MutableLiveData<Float> voiceVolumeValue = new MutableLiveData<>(Float.valueOf(0.0f));
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.light.FtMicVM$$ExternalSyntheticLambda1
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            FtMicVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        if (view.getId() != R.id.iv_start_stop) {
            return;
        }
        this.startRecordLiveData.setValue(Boolean.valueOf(!r2.getValue().booleanValue()));
    }

    public void startRecord() {
        if (this.mRecordThread == null) {
            this.mRecordThread = new RecordThread(new RecordThread.RecordCallback() { // from class: com.ltech.smarthome.ui.device.light.FtMicVM$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.service.music.RecordThread.RecordCallback
                public final void onVolumes(float f) {
                    FtMicVM.this.lambda$startRecord$1(f);
                }
            });
        }
        if (this.mRecordThread.isRecording()) {
            return;
        }
        this.mRecordThread.startRecord();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startRecord$1(float f) {
        this.voiceVolumeValue.postValue(Float.valueOf(f));
    }

    public void stopRecord() {
        RecordThread recordThread = this.mRecordThread;
        if (recordThread == null || !recordThread.isRecording()) {
            return;
        }
        this.mRecordThread.stopRecord();
        this.mRecordThread = null;
    }

    public void setVolume(Context context, float volumePercent) {
        getLightCmdParam().setCmdType(13);
        getLightCmdParam().setVolumePercent(volumePercent);
        getLightCmdParam().setPlayMusic(true);
        RecordThread recordThread = this.mRecordThread;
        if (recordThread == null || !recordThread.isRecording()) {
            return;
        }
        Injection.message().create(context).cmdParam(getLightCmdParam()).control(this.controlObject).intervalTime(0).enqueue();
    }

    private LightCmdParam getLightCmdParam() {
        if (this.mLightCmdParam == null) {
            this.mLightCmdParam = new LightCmdParam();
        }
        return this.mLightCmdParam;
    }
}