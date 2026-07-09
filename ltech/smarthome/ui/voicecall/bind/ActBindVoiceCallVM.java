package com.ltech.smarthome.ui.voicecall.bind;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.alibaba.fastjson.JSONObject;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.zhuhai.ltech.lt_voice_call_api.bean.QuerySettingResponse;
import com.zhuhai.ltech.lt_voice_call_api.bean.VoiceCallGroup;
import com.zhuhai.ltech.lt_voice_call_api.bean.VoiceCallMember;
import com.zhuhai.ltech.lt_voice_call_api.bean.WhiteListUserBean;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActBindVoiceCallVM extends BaseViewModel {
    public MutableLiveData<List<WhiteListUserBean>> data = new MutableLiveData<>();
    public List<VoiceCallGroup> groupList;
    public long panelId;
    public JSONObject saveData;
    public List<VoiceCallMember> whiteList;

    public void loadData() {
        ((ObservableSubscribeProxy) VoiceCallManager.getInstance().syncVoiceCallSetting(this.panelId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<QuerySettingResponse>() { // from class: com.ltech.smarthome.ui.voicecall.bind.ActBindVoiceCallVM.1
            @Override // io.reactivex.functions.Consumer
            public void accept(QuerySettingResponse r) throws Exception {
                ActBindVoiceCallVM.this.showSuccessTipDialog("同步成功");
                if (r.getVoicewhitelist() != null) {
                    ActBindVoiceCallVM.this.whiteList = r.getVoicewhitelist().getUserinfoList();
                }
                if (r.getVoicegroup() != null) {
                    ActBindVoiceCallVM.this.groupList = r.getVoicegroup().getRows();
                }
                ArrayList arrayList = new ArrayList();
                if (ActBindVoiceCallVM.this.whiteList != null) {
                    ArrayList arrayList2 = new ArrayList();
                    for (VoiceCallMember voiceCallMember : ActBindVoiceCallVM.this.whiteList) {
                        if (voiceCallMember.getType() == 2) {
                            arrayList2.add(new WhiteListUserBean(voiceCallMember.getFloorname() + " " + voiceCallMember.getRoomname(), voiceCallMember, 1, false, false));
                        }
                    }
                    if (arrayList2.size() > 0) {
                        arrayList.add(new WhiteListUserBean(ActBindVoiceCallVM.this.getContext().getString(R.string.voice_call_str_smart_panel), 0, false, false));
                        arrayList.addAll(arrayList2);
                    }
                }
                if (ActBindVoiceCallVM.this.groupList != null && ActBindVoiceCallVM.this.groupList.size() > 0) {
                    arrayList.add(new WhiteListUserBean(ActBindVoiceCallVM.this.getContext().getString(R.string.voice_call_group_multi_group), 0, false, true));
                    for (VoiceCallGroup voiceCallGroup : ActBindVoiceCallVM.this.groupList) {
                        if (voiceCallGroup.getType() == 1) {
                            arrayList.add(new WhiteListUserBean(voiceCallGroup.getName(), voiceCallGroup, 1, false, false));
                        }
                    }
                }
                if (ActBindVoiceCallVM.this.whiteList != null) {
                    ArrayList arrayList3 = new ArrayList();
                    for (VoiceCallMember voiceCallMember2 : ActBindVoiceCallVM.this.whiteList) {
                        if (voiceCallMember2.getType() == 1) {
                            arrayList3.add(new WhiteListUserBean(voiceCallMember2.getUsername(), voiceCallMember2, 1, false, false));
                        }
                    }
                    if (arrayList3.size() > 0) {
                        arrayList.add(new WhiteListUserBean(ActBindVoiceCallVM.this.getContext().getString(R.string.voice_call_str_family_member), 0, false, true));
                        arrayList.addAll(arrayList3);
                    }
                }
                ActBindVoiceCallVM.this.data.setValue(arrayList);
            }
        }, new Consumer<Throwable>() { // from class: com.ltech.smarthome.ui.voicecall.bind.ActBindVoiceCallVM.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                ActBindVoiceCallVM.this.showSuccessTipDialog("同步失败" + throwable.getMessage());
            }
        });
    }
}