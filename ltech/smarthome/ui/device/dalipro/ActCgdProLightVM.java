package com.ltech.smarthome.ui.device.dalipro;

import android.view.View;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Scene;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActCgdProLightVM extends BaseViewModel {
    public long controlId;
    protected Listing<Scene> request;
    public final int DALI_SCENE = 0;
    public final int GROUP_AND_ADDRESS = 1;
    public final int BROADCAST = 2;
    public List<BaseNormalFragment> fragmentList = new ArrayList();
    public MutableLiveData<Device> controlObject = new MutableLiveData<>();
    public MutableLiveData<Integer> chooseTabEvent = new MutableLiveData<>(0);
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActCgdProLightVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActCgdProLightVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        int id = view.getId();
        if (id == R.id.tv_broadcast) {
            this.chooseTabEvent.setValue(2);
        } else if (id == R.id.tv_dali_scene) {
            this.chooseTabEvent.setValue(0);
        } else {
            if (id != R.id.tv_group_and_address) {
                return;
            }
            this.chooseTabEvent.setValue(1);
        }
    }

    public void initFragmentList(long deviceId) {
        this.fragmentList.clear();
        this.fragmentList.add(FtDaliScene.newInstance(deviceId));
        this.fragmentList.add(FtDaliAdd.newInstance(deviceId));
        this.fragmentList.add(FtDaliPushrod.newInstance(deviceId));
    }
}