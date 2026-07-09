package com.ltech.smarthome.ui.device.dalipro;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import io.reactivex.ObservableEmitter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActDaliBatchModifyParamVM extends BaseViewModel {
    public List<Device> selectDevices = new ArrayList();
    public List<Integer> selectViews = new ArrayList();
    public List<Room> roomList = new ArrayList();
    public List<Floor> floorList = new ArrayList();
    public SingleLiveEvent<Void> selectModifyParamEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> selectExtendFadeTimeEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> saveEvent = new SingleLiveEvent<>();
    public BindingCommand<View> clickCommand = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParamVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActDaliBatchModifyParamVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        int id = view.getId();
        if (id == R.id.layout_extend_fade_time) {
            this.selectExtendFadeTimeEvent.call();
        } else if (id == R.id.layout_save) {
            this.saveEvent.call();
        } else {
            if (id != R.id.layout_select_modify_param) {
                return;
            }
            this.selectModifyParamEvent.call();
        }
    }

    public LightAssistant getLightCmdHelper(Device device) {
        return CmdAssistant.getLightCmdAssistant(device, new int[0]);
    }

    public static final class BatchSaveItem {
        private Device device;
        ObservableEmitter<Integer> emitter;
        private int id;
        public float progress;
        public int state = StateParam.STATE_PENDING;

        public BatchSaveItem(int id, Device device) {
            this.id = id;
            this.device = device;
        }

        public int getId() {
            return this.id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Device getDevice() {
            return this.device;
        }

        public void setDevice(Device device) {
            this.device = device;
        }

        public ObservableEmitter<Integer> getEmitter() {
            return this.emitter;
        }

        public void setEmitter(ObservableEmitter<Integer> emitter) {
            this.emitter = emitter;
        }
    }

    public static class StateParam {
        public static int STATE_ALL_COMPLETED = 5;
        public static int STATE_COMPLETED = 4;
        public static int STATE_ERROR = 3;
        public static int STATE_PENDING = 1;
        public static int STATE_WORKING = 2;
        public long id;
        public float progress;
        public int state;

        public StateParam(long id, int state, float progress) {
            this.id = id;
            this.state = state;
            this.progress = progress;
        }
    }
}