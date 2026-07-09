package com.ltech.smarthome.ui.voicecall.group;

import android.os.Bundle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager;
import com.zhuhai.ltech.lt_voice_call_api.bean.VoiceCallGroup;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActVoiceGroupListVM extends BaseViewModel {
    public MutableLiveData<List<VoiceCallGroup>> data = new MutableLiveData<>();
    public List<VoiceCallGroup> groupList;
    private ArrayList<String> names;
    public long panelId;
    public long placeId;

    public void refresh(VoiceCallGroup voiceCallGroup) {
        if (this.groupList == null) {
            this.groupList = new ArrayList();
        }
        int i = 0;
        while (true) {
            if (i >= this.groupList.size()) {
                this.groupList.add(voiceCallGroup);
                break;
            } else {
                if (this.groupList.get(i).getPanelvoicegroupid() == voiceCallGroup.getPanelvoicegroupid()) {
                    this.groupList.set(i, voiceCallGroup);
                    break;
                }
                i++;
            }
        }
        setGroupName();
        if (VoiceCallManager.getInstance().getSettingData().getVoicegroup() != null) {
            VoiceCallManager.getInstance().getSettingData().getVoicegroup().setRows(this.groupList);
        }
        Bundle bundle = new Bundle();
        bundle.putString("data", GsonUtils.toJson(this.groupList));
        setResultBundle(bundle);
        this.data.setValue(this.groupList);
    }

    public boolean getGroupName(String name) {
        ArrayList<String> arrayList = this.names;
        if (arrayList == null || arrayList.size() == 0) {
            return false;
        }
        return this.names.contains(name);
    }

    public void setGroupList(List<VoiceCallGroup> list) {
        this.groupList = list;
        if (list == null) {
            this.groupList = new ArrayList();
        }
        setGroupName();
    }

    private void setGroupName() {
        this.names = new ArrayList<>();
        Iterator<VoiceCallGroup> it = this.groupList.iterator();
        while (it.hasNext()) {
            this.names.add(it.next().getName());
        }
        this.data.setValue(this.groupList);
    }

    public void del(long id) {
        int i = 0;
        while (true) {
            if (i >= this.groupList.size()) {
                break;
            }
            VoiceCallGroup voiceCallGroup = this.groupList.get(i);
            if (voiceCallGroup.getPanelvoicegroupid() == id) {
                this.groupList.remove(voiceCallGroup);
                break;
            }
            i++;
        }
        VoiceCallManager.getInstance().getSettingData().getVoicegroup().setRows(this.groupList);
        setGroupName();
        this.data.setValue(this.groupList);
    }
}