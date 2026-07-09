package com.ltech.smarthome.ui.voicecall.setting;

import android.text.TextUtils;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.net.response.super_panel.QuerySuperPanelKeywordInfoResponse;
import com.ltech.smarthome.push.PushContentParamKey;
import com.ltech.smarthome.ui.automation.ActSelectWeek;
import com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.zhuhai.ltech.lt_voice_call_api.bean.QuerySettingResponse;
import com.zhuhai.ltech.lt_voice_call_api.bean.VoiceCallGroup;
import com.zhuhai.ltech.lt_voice_call_api.bean.VoiceCallMember;
import com.zhuhai.ltech.lt_voice_call_api.bean.VoiceCallSetting;
import io.reactivex.functions.Consumer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActVoiceCallSettingVM extends BaseViewModel {
    public long panelId;
    public MutableLiveData<String> whiteListNum = new MutableLiveData<>();
    public MutableLiveData<Boolean> auto = new MutableLiveData<>();
    public MutableLiveData<String> starTimeText = new MutableLiveData<>();
    public MutableLiveData<String> endTimeText = new MutableLiveData<>();
    public MutableLiveData<String> repeat = new MutableLiveData<>();
    public MutableLiveData<Boolean> disturb = new MutableLiveData<>();
    public SingleLiveEvent<Void> timeStartEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> timeEndEvent = new SingleLiveEvent<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.voicecall.setting.ActVoiceCallSettingVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActVoiceCallSettingVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        int id = view.getId();
        if (id == R.id.layout_white_list) {
            ArrayList arrayList = new ArrayList();
            if (VoiceCallManager.getInstance().getWhiteList() != null) {
                Iterator<VoiceCallMember> it = VoiceCallManager.getInstance().getWhiteList().iterator();
                while (it.hasNext()) {
                    arrayList.add(Long.valueOf(it.next().getUserid()));
                }
            }
            VoiceCallManager.getInstance().showAddWhiteListView((FragmentActivity) ActivityUtils.getTopActivity(), Injection.repo().home().getSelectPlace().getValue().getPlaceId(), this.panelId, arrayList, 101);
            return;
        }
        if (id == R.id.layout_group) {
            List<VoiceCallGroup> arrayList2 = new ArrayList<>();
            if (VoiceCallManager.getInstance().getGroupList() != null) {
                arrayList2 = VoiceCallManager.getInstance().getGroupList();
            }
            VoiceCallManager.getInstance().showAddGroupListView((FragmentActivity) ActivityUtils.getTopActivity(), Injection.repo().home().getSelectPlace().getValue().getPlaceId(), this.panelId, arrayList2, 102);
            return;
        }
        if (id == R.id.layout_repeat) {
            navigation(NavUtils.destination(ActSelectWeek.class).withInt(Constants.MIN, 1).withString(Constants.WEEKS, VoiceCallManager.getInstance().getSetting().getBusymodeweeks()).withDefaultRequestCode());
        } else if (id == R.id.layout_end_time) {
            this.timeEndEvent.call();
        } else if (id == R.id.layout_start_time) {
            this.timeStartEvent.call();
        }
    }

    public void loadData() {
        ((ObservableSubscribeProxy) VoiceCallManager.getInstance().syncVoiceCallSetting(this.panelId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<QuerySettingResponse>() { // from class: com.ltech.smarthome.ui.voicecall.setting.ActVoiceCallSettingVM.1
            @Override // io.reactivex.functions.Consumer
            public void accept(QuerySettingResponse r) throws Exception {
                ActVoiceCallSettingVM.this.bindData(r);
                ActVoiceCallSettingVM.this.getPanelKeySet();
            }
        }, new Consumer<Throwable>() { // from class: com.ltech.smarthome.ui.voicecall.setting.ActVoiceCallSettingVM.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                ActVoiceCallSettingVM.this.showSuccessTipDialog("同步失败" + throwable.getMessage());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getPanelKeySet() {
        ((ObservableSubscribeProxy) Injection.net().querySuperPanelKeywordsInfo(this.panelId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<QuerySuperPanelKeywordInfoResponse>(this) { // from class: com.ltech.smarthome.ui.voicecall.setting.ActVoiceCallSettingVM.3
            @Override // io.reactivex.functions.Consumer
            public void accept(QuerySuperPanelKeywordInfoResponse r) throws Exception {
                ArrayList arrayList = new ArrayList();
                for (QuerySuperPanelKeywordInfoResponse.ContentBean.RowsBean rowsBean : r.getContent().getRows()) {
                    if (MaskType.isVoiceCallAction(rowsBean.getActiontype())) {
                        try {
                            JSONObject parseObject = JSONObject.parseObject(rowsBean.getExecutecommand());
                            if (parseObject.getIntValue("voicetype") == 1) {
                                arrayList.add(Long.valueOf(parseObject.getLongValue(PushContentParamKey.USER_ID)));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                VoiceCallManager.getInstance().getSetting().setKeySet(arrayList);
            }
        }, new Consumer<Throwable>() { // from class: com.ltech.smarthome.ui.voicecall.setting.ActVoiceCallSettingVM.4
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                ActVoiceCallSettingVM.this.showSuccessTipDialog("同步失败" + throwable.getMessage());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindData(QuerySettingResponse r) {
        VoiceCallManager.getInstance().setSettingData(r);
        if (r != null && r.getVoicewhitelist() != null) {
            this.whiteListNum.postValue(r.getVoicewhitelist().getUserinfoList().size() + "");
        }
        if (r == null || r.getVoicesetting() == null) {
            return;
        }
        this.disturb.postValue(Boolean.valueOf(r.getVoicesetting().getIsbusymode() == 1));
        this.auto.postValue(Boolean.valueOf(r.getVoicesetting().getIsautoanswer() == 1));
        getRepeatWeekStr(r.getVoicesetting().getBusymodeweeks());
        String busymodestart = r.getVoicesetting().getBusymodestart();
        String busymodeend = r.getVoicesetting().getBusymodeend();
        if (busymodestart == null || TextUtils.isEmpty(busymodestart)) {
            busymodestart = "2200";
        }
        if (busymodeend == null || TextUtils.isEmpty(busymodeend)) {
            busymodeend = "800";
        }
        getRepeatTimeString(busymodestart + com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP + busymodeend);
    }

    public void enableAutoAnswer(boolean isChecked) {
        VoiceCallManager.getInstance().enableAutoAnswer(isChecked);
        update();
    }

    public void doNotDisturb(boolean isChecked) {
        VoiceCallManager.getInstance().doNotDisturb(isChecked);
        update();
    }

    public void update() {
        ((ObservableSubscribeProxy) VoiceCallManager.getInstance().updateSetting(this.panelId).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<VoiceCallSetting>() { // from class: com.ltech.smarthome.ui.voicecall.setting.ActVoiceCallSettingVM.5
            @Override // io.reactivex.functions.Consumer
            public void accept(VoiceCallSetting setting) throws Exception {
                QuerySettingResponse settingData = VoiceCallManager.getInstance().getSettingData();
                settingData.setVoicesetting(setting);
                ActVoiceCallSettingVM.this.bindData(settingData);
                SmartToast.showShort(ActVoiceCallSettingVM.this.getContext().getString(R.string.voice_call_add_white_list_success));
            }
        }, new Consumer<Throwable>() { // from class: com.ltech.smarthome.ui.voicecall.setting.ActVoiceCallSettingVM.6
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                SmartToast.showShort(ActVoiceCallSettingVM.this.getContext().getString(R.string.voice_call_add_white_list_failed));
            }
        });
    }

    private void getRepeatTimeString(String time) {
        try {
            if (!TextUtils.isEmpty(time) && !time.equals(TmpConstant.GROUP_ROLE_UNKNOWN)) {
                String[] split = time.split(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP);
                int parseInt = Integer.parseInt(split[0]) / 100;
                int parseInt2 = Integer.parseInt(split[0]) % 100;
                int parseInt3 = Integer.parseInt(split[1]) / 100;
                int parseInt4 = Integer.parseInt(split[1]) % 100;
                DecimalFormat decimalFormat = new DecimalFormat("00");
                this.starTimeText.postValue(decimalFormat.format(parseInt) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + decimalFormat.format(parseInt2));
                this.endTimeText.postValue(decimalFormat.format((long) parseInt3) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + decimalFormat.format(parseInt4));
                return;
            }
            this.starTimeText.postValue("22:00");
            this.endTimeText.postValue("08:00");
        } catch (Exception e) {
            e.printStackTrace();
            this.starTimeText.postValue("22:00");
            this.endTimeText.postValue("08:00");
        }
    }

    private void getRepeatWeekStr(String week) {
        if (StringUtils.isEmpty(week) || week.equals(TmpConstant.GROUP_ROLE_UNKNOWN)) {
            this.repeat.postValue(getContext().getString(R.string.app_str_never_repeat));
            return;
        }
        if (week.equals("1,2,3,4,5,6,7")) {
            this.repeat.postValue(getContext().getString(R.string.app_str_time_every_day));
            return;
        }
        String[] split = week.split(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP);
        if (week.contains("1") && week.contains("7") && split.length == 2) {
            this.repeat.postValue(getContext().getString(R.string.weekend));
            return;
        }
        if (week.contains("2") && week.contains("3") && week.contains("4") && week.contains("5") && week.contains("6") && split.length == 5) {
            this.repeat.postValue(getContext().getString(R.string.workday));
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            String str = split[i];
            if (i != 0) {
                sb.append(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP);
            }
            str.hashCode();
            switch (str) {
                case "1":
                    sb.append(getContext().getString(R.string.sun));
                    break;
                case "2":
                    sb.append(getContext().getString(R.string.mon));
                    break;
                case "3":
                    sb.append(getContext().getString(R.string.tue));
                    break;
                case "4":
                    sb.append(getContext().getString(R.string.wed));
                    break;
                case "5":
                    sb.append(getContext().getString(R.string.thur));
                    break;
                case "6":
                    sb.append(getContext().getString(R.string.fri));
                    break;
                case "7":
                    sb.append(getContext().getString(R.string.sat));
                    break;
            }
        }
        this.repeat.postValue(sb.toString());
    }

    public void setWeek(String repeat) {
        VoiceCallManager.getInstance().setRepeatWeek(repeat);
        update();
    }

    public void setStarTime(int startHour, int startMinutes) {
        DecimalFormat decimalFormat = new DecimalFormat("00");
        VoiceCallSetting setting = VoiceCallManager.getInstance().getSetting();
        StringBuilder sb = new StringBuilder();
        sb.append(startHour);
        long j = startMinutes;
        sb.append(decimalFormat.format(j));
        setting.setBusymodestart(sb.toString());
        this.starTimeText.postValue(decimalFormat.format(startHour) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + decimalFormat.format(j));
        update();
    }

    public void setEndTime(int startHour, int startMinutes) {
        DecimalFormat decimalFormat = new DecimalFormat("00");
        VoiceCallSetting setting = VoiceCallManager.getInstance().getSetting();
        StringBuilder sb = new StringBuilder();
        sb.append(startHour);
        long j = startMinutes;
        sb.append(decimalFormat.format(j));
        setting.setBusymodeend(sb.toString());
        this.endTimeText.postValue(decimalFormat.format(startHour) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + decimalFormat.format(j));
        update();
    }
}