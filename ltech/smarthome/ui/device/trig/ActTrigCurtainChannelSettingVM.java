package com.ltech.smarthome.ui.device.trig;

import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.device_param.TrigExtParam;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.trig.TrigRepository;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.CodeLibraryUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.ResponseMsg;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActTrigCurtainChannelSettingVM extends BaseViewModel {
    public long controlId;
    ArrayList<Integer> currentChannels;
    public long deviceId;
    TrigExtParam extParam;
    public int mode;
    public long placeId;
    public String productId;
    public boolean samePlace;
    public MutableLiveData<Object> controlObject = new MutableLiveData<>();
    public MutableLiveData<List<TrigRepository.TrigItem>> items = new MutableLiveData<>();
    List<TrigRepository.TrigItem> keyItemList = new ArrayList();

    public void generateExtParam(Device device, int currentAction, int currentChannel) {
        this.extParam = new TrigExtParam();
        if (device.getExtParam() != null) {
            this.extParam.fillMapWithString(device.getExtParam());
            this.currentChannels = this.extParam.getBtnChannels();
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        if (this.currentChannels != null) {
            while (i < TrigRepository.getDefaultCurtainItemList().size()) {
                arrayList.add(this.currentChannels.get(i));
                if (i == currentAction) {
                    arrayList.set(i, Integer.valueOf(currentChannel));
                }
                i++;
            }
        } else {
            while (i < TrigRepository.getDefaultCurtainItemList().size()) {
                arrayList.add(Integer.valueOf(TrigRepository.getDefaultCurtainItemList().get(i).spanCount));
                if (i == currentAction) {
                    arrayList.set(i, Integer.valueOf(currentChannel));
                }
                i++;
            }
        }
        this.extParam.getInfoMap().put(TrigExtParam.BTN_CHANNELS, arrayList);
    }

    public void loadChanel() {
        CmdAssistant.getDeviceAssistant(this.controlObject.getValue(), new int[0]).queryChannelMapping(ActivityUtils.getTopActivity(), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigCurtainChannelSettingVM.1
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                if (responseMsg == null || responseMsg.getResData().length() < 16) {
                    return;
                }
                ActTrigCurtainChannelSettingVM.this.items.setValue(ActTrigCurtainChannelSettingVM.this.cover2ChannelItem(responseMsg.getResData().substring(16), ActTrigCurtainChannelSettingVM.this.keyItemList));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<TrigRepository.TrigItem> cover2ChannelItem(String data, List<TrigRepository.TrigItem> list) {
        for (int i = 0; i < data.length() / 4; i++) {
            int i2 = i * 4;
            int i3 = i2 + 2;
            int parseInt = Integer.parseInt(data.substring(i2, i3), 16);
            int parseInt2 = Integer.parseInt(data.substring(i3, i2 + 4), 16);
            for (TrigRepository.TrigItem trigItem : list) {
                if (trigItem.spanCount == parseInt) {
                    trigItem.channel = parseInt2;
                }
            }
        }
        return list;
    }

    public void updateExtParam(final int currentAction, final int currentChannel) {
        CmdAssistant.getDeviceAssistant(this.controlObject.getValue(), new int[0]).channelMapping(ActivityUtils.getTopActivity(), currentAction, currentChannel, new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigCurtainChannelSettingVM.2
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                ActTrigCurtainChannelSettingVM.this.loadChanel();
                if (responseMsg != null && responseMsg.getStateCode() == 0) {
                    SmartToast.showShort(R.string.save_success);
                    Object value = ActTrigCurtainChannelSettingVM.this.controlObject.getValue();
                    if (value instanceof Device) {
                        ReplaceHelper.instance().backupIndexData(ActTrigCurtainChannelSettingVM.this.getLifecycleOwner(), ((Device) value).getDeviceId(), UpdateBackDataRequest.DRY_CONTACT_CHANNEL, CmdAssistant.getDeviceAssistant(ActTrigCurtainChannelSettingVM.this.controlObject.getValue(), new int[0]).channelMapping(currentAction, currentChannel), currentAction);
                        return;
                    }
                    return;
                }
                SmartToast.showShort(R.string.save_fail);
            }
        });
    }

    public void updateCodeLibrary(int address, ArrayList<Integer> channels) {
        int[] iArr = new int[channels.size()];
        for (int i = 0; i < channels.size(); i++) {
            iArr[i] = channels.get(i).intValue();
        }
        if (this.controlObject.getValue() instanceof Device) {
            Injection.net().updateCodeLibrary(((Device) this.controlObject.getValue()).getDeviceId(), CodeLibraryUtil.generateTrigCurCodeLibrary(iArr, address));
        }
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }
}