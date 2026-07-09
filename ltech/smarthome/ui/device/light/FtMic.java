package com.ltech.smarthome.ui.device.light;

import android.os.Bundle;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.databinding.FtMicBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.EventBusUtils;
import com.ltech.smarthome.utils.LiveBusHelper;

/* loaded from: classes4.dex */
public class FtMic extends VMFragment<FtMicBinding, FtMicVM> {
    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_mic;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected boolean useEventBus() {
        return true;
    }

    public static FtMic newInstance(long controlId, boolean groupControl) {
        FtMic ftMic = new FtMic();
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.CONTROL_ID, controlId);
        bundle.putBoolean(Constants.GROUP_CONTROL, groupControl);
        ftMic.setArguments(bundle);
        return ftMic;
    }

    private FtMic() {
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initData() {
        super.initData();
        Bundle arguments = getArguments();
        if (arguments != null) {
            ((FtMicVM) this.mViewModel).controlId = arguments.getLong(Constants.CONTROL_ID);
            ((FtMicVM) this.mViewModel).groupControl = arguments.getBoolean(Constants.GROUP_CONTROL);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        TitleDefault titleDefault = new TitleDefault();
        titleDefault.setBackImageRes(R.mipmap.icon_back);
        titleDefault.setTitle(getString(R.string.mic));
        titleDefault.setEditImageRes(R.mipmap.icon_music);
        titleDefault.setBackAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.light.FtMic$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                FtMic.this.lambda$initView$0();
            }
        }));
        titleDefault.setEditAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.light.FtMic$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                FtMic.this.lambda$initView$1();
            }
        }));
        ((FtMicBinding) this.mViewBinding).setTitle(titleDefault);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0() {
        ((FtMicVM) this.mViewModel).finishActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1() {
        EventBusUtils.post(new LiveBusHelper(5, getClass().getName()));
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        ((FtMicVM) this.mViewModel).startRecordLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.FtMic$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtMic.this.lambda$startObserve$2((Boolean) obj);
            }
        });
        ((FtMicVM) this.mViewModel).voiceVolumeValue.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.FtMic$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtMic.this.lambda$startObserve$3((Float) obj);
            }
        });
        if (((FtMicVM) this.mViewModel).groupControl) {
            Injection.repo().group().getGroupFromDb(((FtMicVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.FtMic$$ExternalSyntheticLambda4
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    FtMic.this.lambda$startObserve$4((Group) obj);
                }
            });
        } else {
            Injection.repo().device().getDeviceFromDb(((FtMicVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.FtMic$$ExternalSyntheticLambda5
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    FtMic.this.lambda$startObserve$5((Device) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Boolean bool) {
        if (bool.booleanValue()) {
            ((FtMicBinding) this.mViewBinding).ivStartStop.setImageResource(R.mipmap.ic_speaking);
            ((FtMicBinding) this.mViewBinding).voiceview.setSpeaking(true);
            ((FtMicVM) this.mViewModel).startRecord();
            EventBusUtils.post(new LiveBusHelper(4));
            return;
        }
        ((FtMicBinding) this.mViewBinding).ivStartStop.setImageResource(R.mipmap.ic_speak);
        ((FtMicBinding) this.mViewBinding).voiceview.setSpeaking(false);
        ((FtMicVM) this.mViewModel).stopRecord();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Float f) {
        if (((FtMicBinding) this.mViewBinding).voiceview.isSpeaking()) {
            ((FtMicBinding) this.mViewBinding).voiceview.setVolume(f.floatValue());
            ((FtMicVM) this.mViewModel).setVolume(getActivity(), f.floatValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Group group) {
        ((FtMicVM) this.mViewModel).controlObject = group;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Device device) {
        ((FtMicVM) this.mViewModel).controlObject = device;
    }

    @Override // com.ltech.smarthome.base.VMFragment, com.ltech.smarthome.base.BaseNormalFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        ((FtMicVM) this.mViewModel).stopRecord();
        super.onDestroyView();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void handleBusEvent(LiveBusHelper helper) {
        if (3 == helper.getCode()) {
            ((FtMicVM) this.mViewModel).startRecordLiveData.setValue(false);
        }
    }
}