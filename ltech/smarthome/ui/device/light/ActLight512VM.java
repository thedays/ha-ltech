package com.ltech.smarthome.ui.device.light;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.device.microwave_sensor.WaveSensorHelper;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.ResponseMsg;
import com.smart.product_agreement.param.LightCmdParam;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ActLight512VM extends BaseViewModel {
    public int all;
    private List<Integer> channelData;
    private List<String> channelNames;
    private int channelNum;
    public long controlId;
    public MutableLiveData<Object> controlObject = new MutableLiveData<>();
    public MutableLiveData<List<Channel>> data = new MutableLiveData<>();
    private int firstAddress;
    public String instruct;
    public boolean isEdit;
    public boolean isLocal;
    public boolean isWaveSensorAction;

    public void queryChannel() {
        CmdAssistant.getQueryCmdAssistant(this.controlObject.getValue(), new int[0]).queryPanelState(getContext(), new IAction<ResponseMsg>(this) { // from class: com.ltech.smarthome.ui.device.light.ActLight512VM.1
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg msg) {
            }
        });
    }

    public void setChannelNum(int channelNum) {
        this.channelNum = channelNum;
    }

    public void setFirstAddress(int firstAddress) {
        this.firstAddress = firstAddress;
    }

    public void setChannelNames(List<String> channelNames) {
        this.channelNames = channelNames;
    }

    public List<String> getChannelNames() {
        return this.channelNames;
    }

    public int getChannelNum() {
        return this.channelNum;
    }

    public int getFirstAddress() {
        return this.firstAddress;
    }

    public void setChannelData(List<Integer> channelData) {
        this.channelData = channelData;
    }

    public List<Integer> getChannelData() {
        return this.channelData;
    }

    public void showData() {
        int i;
        int i2;
        ArrayList arrayList = new ArrayList();
        if (this.channelNames == null) {
            ArrayList arrayList2 = new ArrayList();
            this.channelNames = arrayList2;
            arrayList2.add("001");
            this.channelNames.add("002");
            this.channelNames.add("003");
            this.channelNames.add("004");
            this.channelNames.add("005");
            this.channelNames.add("006");
        }
        if (this.channelData == null) {
            this.channelData = new ArrayList();
            for (int i3 = 0; i3 < 6; i3++) {
                if ((this.isEdit || this.isWaveSensorAction) && !StringUtils.isEmpty(this.instruct) && this.instruct.length() >= (i2 = (i = i3 * 2) + 2)) {
                    this.channelData.add(Integer.valueOf(Integer.parseInt(this.instruct.substring(i, i2), 16)));
                } else {
                    this.channelData.add(255);
                }
            }
        }
        int i4 = this.firstAddress;
        int i5 = -1;
        for (int i6 = 0; i6 < this.channelNum; i6++) {
            int intValue = this.channelData.get(i6).intValue();
            if (i6 == 0) {
                i5 = intValue;
            } else if (i5 != -1 && i5 != intValue) {
                i5 = -1;
            }
            arrayList.add(new Channel(i4, this.channelNames.get(i6), intValue));
            i4++;
        }
        String string = getContext().getString(R.string.type_all);
        if (i5 == -1) {
            i5 = this.all;
        }
        arrayList.add(0, new Channel(0, string, i5));
        this.data.setValue(arrayList);
    }

    public void sendChannel(List<Integer> data, boolean finish) {
        CmdAssistant.getLightCmdAssistant(this.controlObject.getValue(), new int[0]).sendChannelValue(getContext(), data.size(), data, finish);
    }

    public void sendChannel(int pos, int progress, boolean finish) {
        CmdAssistant.getLightCmdAssistant(this.controlObject.getValue(), pos == 0 ? 65535 : 1 << (pos - 1)).sendSingleChannelValue(getContext(), progress, finish);
    }

    public void selectChannelState(List<Integer> data) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setCmdType(85);
        lightCmdParam.setChannelNum(data.size());
        lightCmdParam.setChannelValues(data);
        lightCmdParam.addExtParam(SceneHelper.OPTION, "0");
        if (this.isWaveSensorAction) {
            WaveSensorHelper.instance().cmdParam = lightCmdParam;
        } else {
            lightCmdParam.addExtParam(SceneHelper.OPTION_VALUE, ActivityUtils.getTopActivity().getString(R.string.app_zdy_status));
            SceneHelper.instance().cmdParam = lightCmdParam;
        }
    }

    public void updateName() {
        Object value = this.controlObject.getValue();
        if (value instanceof Device) {
            final Device device = (Device) value;
            try {
                JSONObject jSONObject = device.getExtParam() != null ? new JSONObject(device.getExtParam()) : new JSONObject();
                jSONObject.put("channelNames", new JSONArray((Collection) this.channelNames));
                device.setExtParam(jSONObject.toString());
                ((ObservableSubscribeProxy) Injection.net().updateParamExt(device.getDeviceId(), jSONObject.toString()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.light.ActLight512VM$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        Injection.repo().device().saveDevice(Device.this);
                    }
                }, new SmartErrorComsumer());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Channel {
        private int address;
        private String name;
        private int value;

        public Channel(int address, String name, int value) {
            this.address = address;
            this.name = name;
            this.value = value;
        }

        public int getAddress() {
            return this.address;
        }

        public void setAddress(int address) {
            this.address = address;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getValue() {
            return this.value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}