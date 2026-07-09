package com.ltech.smarthome.ui.voicecall.whitelist;

import android.os.Bundle;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.zhuhai.ltech.lt_voice_call_api.bean.QueryPlaceUserAndPanelResponse;
import com.zhuhai.ltech.lt_voice_call_api.bean.QuerySettingResponse;
import com.zhuhai.ltech.lt_voice_call_api.bean.VoiceCallGroup;
import com.zhuhai.ltech.lt_voice_call_api.bean.VoiceCallMember;
import com.zhuhai.ltech.lt_voice_call_api.bean.WhiteListUserBean;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActWhiteListVM extends BaseViewModel {
    public MutableLiveData<List<WhiteListUserBean>> data = new MutableLiveData<>();
    public long panelId;
    public long placeId;
    public List<Long> userIds;

    public void loadMember() {
        if (this.placeId == 0) {
            return;
        }
        ((ObservableSubscribeProxy) Injection.net().queryPlaceUserAndPanel(this.placeId, this.panelId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Observer<QueryPlaceUserAndPanelResponse>() { // from class: com.ltech.smarthome.ui.voicecall.whitelist.ActWhiteListVM.1
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d2) {
            }

            @Override // io.reactivex.Observer
            public void onNext(QueryPlaceUserAndPanelResponse response) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(new WhiteListUserBean(ActWhiteListVM.this.getContext().getString(R.string.voice_call_str_smart_panel), 0, false, true));
                if (response.getDevices() != null) {
                    for (VoiceCallMember voiceCallMember : response.getDevices()) {
                        if (voiceCallMember.getType() == 2 && voiceCallMember.getVoiceactive() == 1) {
                            arrayList.add(new WhiteListUserBean(voiceCallMember.getFloorname() + " " + voiceCallMember.getRoomname(), voiceCallMember, 1, ActWhiteListVM.this.userIds != null && ActWhiteListVM.this.userIds.contains(Long.valueOf(voiceCallMember.getUserid())), false));
                        }
                    }
                }
                arrayList.add(new WhiteListUserBean(ActWhiteListVM.this.getContext().getString(R.string.voice_call_str_family_member), 0, false, true));
                if (response.getUsers() != null) {
                    for (VoiceCallMember voiceCallMember2 : response.getUsers()) {
                        if (voiceCallMember2.getType() == 1) {
                            arrayList.add(new WhiteListUserBean(voiceCallMember2.getUsername(), voiceCallMember2, 1, ActWhiteListVM.this.userIds != null && ActWhiteListVM.this.userIds.contains(Long.valueOf(voiceCallMember2.getUserid())), true));
                        }
                    }
                }
                ActWhiteListVM.this.data.setValue(arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void del(List<Long> ids, final Bundle bundle) {
        ((ObservableSubscribeProxy) Injection.net().unbind(VoiceCallManager.getInstance().getSettingData().getVoicesetting().getPanelid(), ids).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<Object>() { // from class: com.ltech.smarthome.ui.voicecall.whitelist.ActWhiteListVM.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Object o) throws Exception {
                ActWhiteListVM actWhiteListVM = ActWhiteListVM.this;
                actWhiteListVM.showSuccessTipDialog(actWhiteListVM.getContext().getString(R.string.voice_call_add_white_list_success));
                ActWhiteListVM.this.finishActivity(-1, bundle);
            }
        }, new Consumer<Throwable>() { // from class: com.ltech.smarthome.ui.voicecall.whitelist.ActWhiteListVM.3
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                ActWhiteListVM actWhiteListVM = ActWhiteListVM.this;
                actWhiteListVM.showSuccessTipDialog(actWhiteListVM.getContext().getString(R.string.voice_call_add_white_list_success));
                ActWhiteListVM.this.finishActivity(-1, bundle);
            }
        });
    }

    public void saveWhiteList(List<Long> ids) {
        if (VoiceCallManager.getInstance().getSettingData().getVoicewhitelist().getPanelvoicewhitelistid() != 0) {
            final ArrayList arrayList = new ArrayList(CollectionUtils.subtract(this.userIds, ids));
            ((ObservableSubscribeProxy) Injection.net().updateWhiteList(VoiceCallManager.getInstance().getSettingData().getVoicewhitelist().getPanelvoicewhitelistid(), ids).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<QuerySettingResponse.VoiceWhiteList>() { // from class: com.ltech.smarthome.ui.voicecall.whitelist.ActWhiteListVM.4
                @Override // io.reactivex.functions.Consumer
                public void accept(QuerySettingResponse.VoiceWhiteList voiceWhiteList) throws Exception {
                    VoiceCallManager.getInstance().getSettingData().setVoicewhitelist(voiceWhiteList);
                    Bundle bundle = new Bundle();
                    bundle.putString("data", GsonUtils.toJson(voiceWhiteList));
                    ActWhiteListVM.this.del(arrayList, bundle);
                }
            }, new Consumer<Throwable>() { // from class: com.ltech.smarthome.ui.voicecall.whitelist.ActWhiteListVM.5
                @Override // io.reactivex.functions.Consumer
                public void accept(Throwable throwable) throws Exception {
                    ActWhiteListVM actWhiteListVM = ActWhiteListVM.this;
                    actWhiteListVM.showErrorTipDialog(actWhiteListVM.getContext().getString(R.string.voice_call_add_white_list_failed));
                }
            });
        } else {
            ((ObservableSubscribeProxy) Injection.net().addWhiteList(this.panelId, ids).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<QuerySettingResponse.VoiceWhiteList>() { // from class: com.ltech.smarthome.ui.voicecall.whitelist.ActWhiteListVM.6
                @Override // io.reactivex.functions.Consumer
                public void accept(QuerySettingResponse.VoiceWhiteList voiceWhiteList) throws Exception {
                    VoiceCallManager.getInstance().getSettingData().setVoicewhitelist(voiceWhiteList);
                    ActWhiteListVM actWhiteListVM = ActWhiteListVM.this;
                    actWhiteListVM.showSuccessTipDialog(actWhiteListVM.getContext().getString(R.string.voice_call_add_white_list_success));
                    Bundle bundle = new Bundle();
                    bundle.putString("data", GsonUtils.toJson(voiceWhiteList));
                    ActWhiteListVM.this.finishActivity(-1, bundle);
                }
            }, new Consumer<Throwable>() { // from class: com.ltech.smarthome.ui.voicecall.whitelist.ActWhiteListVM.7
                @Override // io.reactivex.functions.Consumer
                public void accept(Throwable throwable) throws Exception {
                    ActWhiteListVM actWhiteListVM = ActWhiteListVM.this;
                    actWhiteListVM.showErrorTipDialog(actWhiteListVM.getContext().getString(R.string.voice_call_add_white_list_failed));
                }
            });
        }
    }

    public int hasBind(long userid) {
        int i;
        Iterator<VoiceCallGroup> it = VoiceCallManager.getInstance().getGroupList().iterator();
        while (true) {
            if (!it.hasNext()) {
                i = 0;
                break;
            }
            if (it.next().getCalluserid().contains(userid + "")) {
                i = 1;
                break;
            }
        }
        return (VoiceCallManager.getInstance().getSetting() == null || !VoiceCallManager.getInstance().getSetting().getKeySet().contains(Long.valueOf(userid))) ? i : i == 1 ? 3 : 2;
    }
}