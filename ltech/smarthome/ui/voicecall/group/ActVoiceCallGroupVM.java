package com.ltech.smarthome.ui.voicecall.group;

import android.os.Bundle;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.zhuhai.ltech.lt_voice_call_api.bean.VoiceCallGroup;
import com.zhuhai.ltech.lt_voice_call_api.bean.VoiceCallMember;
import com.zhuhai.ltech.lt_voice_call_api.bean.WhiteListUserBean;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActVoiceCallGroupVM extends BaseViewModel {
    public boolean edite;
    public VoiceCallGroup group;
    public ArrayList<Long> ids;
    private String name;
    public long panelId;
    public long placeId;
    public List<VoiceCallMember> users;
    public MutableLiveData<List<WhiteListUserBean>> data = new MutableLiveData<>();
    public MutableLiveData<Bundle> result = new MutableLiveData<>();

    public void loadMember() {
        boolean z;
        if (this.placeId == 0) {
            return;
        }
        if (!this.edite) {
            ArrayList arrayList = new ArrayList();
            List<VoiceCallMember> list = this.users;
            if (list != null) {
                Iterator<VoiceCallMember> it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(new WhiteListUserBean("", it.next(), 0, false, this.edite));
                }
            }
            this.data.setValue(arrayList);
            return;
        }
        ArrayList arrayList2 = new ArrayList();
        List<VoiceCallMember> whiteList = VoiceCallManager.getInstance().getWhiteList();
        if (whiteList != null) {
            for (VoiceCallMember voiceCallMember : whiteList) {
                List<VoiceCallMember> list2 = this.users;
                if (list2 != null) {
                    Iterator<VoiceCallMember> it2 = list2.iterator();
                    while (it2.hasNext()) {
                        if (it2.next().getUserid() == voiceCallMember.getUserid()) {
                            z = true;
                            break;
                        }
                    }
                }
                z = false;
                arrayList2.add(new WhiteListUserBean("", voiceCallMember, 0, z, this.edite));
            }
        }
        this.data.setValue(arrayList2);
    }

    public void save() {
        if (this.group == null) {
            ArrayList<Long> arrayList = this.ids;
            if (arrayList == null || arrayList.size() == 0) {
                SmartToast.showCenterShort(getContext().getString(R.string.voice_call_add_group_member_null));
                return;
            } else {
                ((ObservableSubscribeProxy) Injection.net().addVoiceGroup(this.panelId, this.name, this.ids).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<VoiceCallGroup>() { // from class: com.ltech.smarthome.ui.voicecall.group.ActVoiceCallGroupVM.1
                    @Override // io.reactivex.functions.Consumer
                    public void accept(VoiceCallGroup voiceCallGroup) throws Exception {
                        SmartToast.showCenterShort(ActVoiceCallGroupVM.this.getContext().getString(R.string.voice_call_add_white_list_success));
                        Bundle bundle = new Bundle();
                        bundle.putString("data", GsonUtils.toJson(voiceCallGroup));
                        ActVoiceCallGroupVM.this.finishActivity(100, bundle);
                    }
                }, new Consumer<Throwable>() { // from class: com.ltech.smarthome.ui.voicecall.group.ActVoiceCallGroupVM.2
                    @Override // io.reactivex.functions.Consumer
                    public void accept(Throwable throwable) throws Exception {
                        SmartToast.showCenterShort(ActVoiceCallGroupVM.this.getContext().getString(R.string.voice_call_add_white_list_failed));
                    }
                });
                return;
            }
        }
        ((ObservableSubscribeProxy) Injection.net().updateVoiceGroup(this.group.getPanelvoicegroupid(), this.name, this.ids).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<VoiceCallGroup>() { // from class: com.ltech.smarthome.ui.voicecall.group.ActVoiceCallGroupVM.3
            @Override // io.reactivex.functions.Consumer
            public void accept(VoiceCallGroup voiceCallGroup) throws Exception {
                SmartToast.showCenterShort(ActVoiceCallGroupVM.this.getContext().getString(R.string.voice_call_add_white_list_success));
                Bundle bundle = new Bundle();
                bundle.putString("data", GsonUtils.toJson(voiceCallGroup));
                ActVoiceCallGroupVM.this.result.setValue(bundle);
            }
        }, new Consumer<Throwable>() { // from class: com.ltech.smarthome.ui.voicecall.group.ActVoiceCallGroupVM.4
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                SmartToast.showCenterShort(ActVoiceCallGroupVM.this.getContext().getString(R.string.voice_call_add_white_list_failed));
            }
        });
    }

    public void changeName(String s) {
        this.name = s;
    }

    public void del() {
        ((ObservableSubscribeProxy) Injection.net().delVoiceGroup(this.group.getPanelvoicegroupid()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<Object>() { // from class: com.ltech.smarthome.ui.voicecall.group.ActVoiceCallGroupVM.5
            @Override // io.reactivex.functions.Consumer
            public void accept(Object o) throws Exception {
                SmartToast.showCenterShort(ActVoiceCallGroupVM.this.getContext().getString(R.string.voice_call_del_group_success));
                Bundle bundle = new Bundle();
                bundle.putLong("id", ActVoiceCallGroupVM.this.group.getPanelvoicegroupid());
                ActVoiceCallGroupVM.this.finishActivity(101, bundle);
            }
        }, new Consumer<Throwable>() { // from class: com.ltech.smarthome.ui.voicecall.group.ActVoiceCallGroupVM.6
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                SmartToast.showCenterShort(ActVoiceCallGroupVM.this.getContext().getString(R.string.voice_call_del_group_failed));
            }
        });
    }
}