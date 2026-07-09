package com.ltech.smarthome.ui.device.light;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.SunInfo;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.net.response.mode.ListModeResponse;
import com.ltech.smarthome.ui.circadianlighting.ActLightPlanBatchSet;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.RhythmsAssistant;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.message.utils.StringUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActRhythmsSettingVM extends BaseDeviceSetViewModel {
    public String weeks;
    public MutableLiveData<Object> controlObject = new MutableLiveData<>();
    public int mCurPlan = 1;
    private int startH = 6;
    private int startM = 0;
    private int endH = 18;
    private int endM = 0;
    public MutableLiveData<Boolean> showRhythmsSetting = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> showRhythms = new MutableLiveData<>(false);
    public MutableLiveData<Integer> showRhythmsModel = new MutableLiveData<>(1);
    public MutableLiveData<String> repeatTimeLiveData = new MutableLiveData<>("");
    public MutableLiveData<String> starTimeText = new MutableLiveData<>("06:00");
    public MutableLiveData<String> endTimeText = new MutableLiveData<>("18:00");
    public MutableLiveData<String> planTimeText = new MutableLiveData<>("06:00-18:00");
    public MutableLiveData<String> sunRiseText = new MutableLiveData<>("");
    public MutableLiveData<String> sunSetText = new MutableLiveData<>("");
    public SingleLiveEvent<Void> selectStarTimeDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> selectEndTimeDialogEvent = new SingleLiveEvent<>();
    public MutableLiveData<Boolean> rhythmsIsPlay = new MutableLiveData<>(false);
    public MutableLiveData<String> planText = new MutableLiveData<>("");
    public SingleLiveEvent<Void> showPlanListEvent = new SingleLiveEvent<>();
    private List<ListModeResponse.RowsBean> mRhythmsList = new ArrayList();
    public List<String> rhythmsNameList = new ArrayList(Arrays.asList(ActivityUtils.getTopActivity().getResources().getStringArray(R.array.light_rhythms_diy)));
    private final IAction<ResponseMsg> iAction = new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.light.ActRhythmsSettingVM.1
        @Override // com.ltech.smarthome.base.IAction
        public void act(ResponseMsg responseMsg) {
            if (responseMsg == null || responseMsg.getStateCode() != 0) {
                return;
            }
            if (responseMsg.getResData() != null && responseMsg.getResData().length() >= 22) {
                LHomeLog.i(getClass(), "queryState=" + responseMsg.getResData());
                ActRhythmsSettingVM.this.rhythmsIsPlay.setValue(Boolean.valueOf(Integer.parseInt(responseMsg.getResData().substring(18, 20), 16) == 1));
            }
            ActRhythmsSettingVM.this.backupRhythmsData();
            ActRhythmsSettingVM.this.showSuccessTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_success));
        }
    };
    public BindingCommand<View> rhythmViewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.light.ActRhythmsSettingVM$$ExternalSyntheticLambda2
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActRhythmsSettingVM.this.lambda$new$0((View) obj);
        }
    });

    public void saveTempControl() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        boolean z = getCurPlace().isOwner() || getCurPlace().isManager();
        switch (view.getId()) {
            case R.id.layout_end_time /* 2131297463 */:
                if (z) {
                    this.selectEndTimeDialogEvent.call();
                    break;
                }
                break;
            case R.id.layout_plan /* 2131297570 */:
            case R.id.tv_plan /* 2131298865 */:
                if (z) {
                    if (this.showRhythmsModel.getValue().intValue() != 3) {
                        this.showRhythmsModel.setValue(3);
                        if (!TextUtils.isEmpty(this.planText.getValue())) {
                            if (isVirtual()) {
                                backupRhythmsData();
                                break;
                            } else {
                                getRhythmsAssistant().setRhythmsPlanMode(ActivityUtils.getTopActivity(), 3, this.mCurPlan, this.iAction);
                                saveTempControl();
                                break;
                            }
                        } else {
                            this.showPlanListEvent.call();
                            break;
                        }
                    } else {
                        this.showPlanListEvent.call();
                        break;
                    }
                }
                break;
            case R.id.layout_plan_time /* 2131297572 */:
                if (z) {
                    this.showRhythmsModel.setValue(2);
                    if (isVirtual()) {
                        backupRhythmsData();
                        break;
                    } else {
                        getRhythmsAssistant().setRhythmsMode(ActivityUtils.getTopActivity(), 2, this.iAction);
                        getRhythmsAssistant().setRhythmsTime(ActivityUtils.getTopActivity(), this.startH, this.startM, this.endH, this.endM, this.iAction);
                        saveTempControl();
                        break;
                    }
                }
                break;
            case R.id.layout_rhythms /* 2131297600 */:
                goBatchSetting();
                break;
            case R.id.layout_start_time /* 2131297657 */:
                if (z) {
                    this.selectStarTimeDialogEvent.call();
                    break;
                }
                break;
            case R.id.layout_sunset /* 2131297666 */:
                if (z) {
                    this.showRhythmsModel.setValue(1);
                    if (isVirtual()) {
                        backupRhythmsData();
                        break;
                    } else {
                        getRhythmsAssistant().setRhythmsMode(ActivityUtils.getTopActivity(), 1, this.iAction);
                        saveTempControl();
                        break;
                    }
                }
                break;
            case R.id.play_iv /* 2131297884 */:
                if (!Boolean.FALSE.equals(this.showRhythmsSetting.getValue())) {
                    this.rhythmsIsPlay.setValue(Boolean.valueOf(Boolean.FALSE.equals(this.rhythmsIsPlay.getValue())));
                    if (isVirtual()) {
                        backupRhythmsData();
                        break;
                    } else {
                        getRhythmsAssistant().playRhythms(ActivityUtils.getTopActivity(), Boolean.TRUE.equals(this.rhythmsIsPlay.getValue()) ? 1 : 0, this.iAction);
                        saveTempControl();
                        break;
                    }
                }
                break;
        }
    }

    public void onOffRhythms(Context context, boolean z, boolean z2) {
        if (isVirtual()) {
            this.rhythmsIsPlay.setValue(Boolean.valueOf(z));
            backupRhythmsData();
            return;
        }
        if (!z2) {
            getRhythmsAssistant().onOffRhythms(context, z ? 1 : 0, this.iAction);
        } else {
            getRhythmsAssistant().onOffGroupRhythms(context, z ? 1 : 0);
        }
        saveTempControl();
    }

    public void syncRhythmsList() {
        this.mRhythmsList = new ArrayList();
        int i = 0;
        while (i < 4) {
            ListModeResponse.RowsBean rowsBean = new ListModeResponse.RowsBean();
            int i2 = i + 1;
            rowsBean.setModeindex(i2);
            rowsBean.setModename(this.rhythmsNameList.get(i));
            rowsBean.setContent(Constants.LIGHT_PLAN_DEFAULT_LIST[i]);
            this.mRhythmsList.add(rowsBean);
            i = i2;
        }
        ((ObservableSubscribeProxy) Injection.net().getModeList(Injection.repo().home().getSelectPlace().getValue().getPlaceId(), 2, 3).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.light.ActRhythmsSettingVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActRhythmsSettingVM.this.lambda$syncRhythmsList$1((ListModeResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncRhythmsList$1(ListModeResponse listModeResponse) throws Exception {
        if (listModeResponse.getTotal() > 0) {
            for (ListModeResponse.RowsBean rowsBean : listModeResponse.getRows()) {
                this.mRhythmsList.set(rowsBean.getModeindex() - 1, rowsBean);
                this.rhythmsNameList.set(rowsBean.getModeindex() - 1, rowsBean.getModename());
            }
        }
        int i = this.mCurPlan;
        if (i > 0) {
            this.planText.setValue(this.rhythmsNameList.get(i - 1));
        }
    }

    public void queryRhythmsSetting() {
        syncRhythmsList();
        getRhythmsQueryAssistant().queryRhythmsSetting(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActRhythmsSettingVM$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActRhythmsSettingVM.this.lambda$queryRhythmsSetting$2((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryRhythmsSetting$2(ResponseMsg responseMsg) {
        if (responseMsg != null) {
            if (responseMsg.getResData() != null && responseMsg.getResData().length() >= 24) {
                LHomeLog.i(getClass(), "queryState=" + responseMsg.getResData());
                parseRhythmsSetting(responseMsg.getResData().substring(16));
                return;
            }
            setRhythmsGone();
            return;
        }
        setRhythmsGone();
    }

    public void parseRhythmsSetting(String rhythmData) {
        int parseInt = Integer.parseInt(rhythmData.substring(0, 2), 16);
        int parseInt2 = Integer.parseInt(rhythmData.substring(2, 4), 16);
        int parseInt3 = Integer.parseInt(rhythmData.substring(4, 6), 16);
        int parseInt4 = Integer.parseInt(rhythmData.substring(6, 8), 16);
        int parseInt5 = Integer.parseInt(rhythmData.substring(8, 10), 16);
        this.mCurPlan = parseInt5;
        if (parseInt5 < 1 || parseInt5 > 4) {
            this.mCurPlan = 1;
        }
        this.showRhythms.setValue(true);
        this.showRhythmsSetting.setValue(Boolean.valueOf(parseInt == 1));
        this.rhythmsIsPlay.setValue(Boolean.valueOf(parseInt2 == 1));
        String repeatString = getRepeatString(parseInt3);
        this.weeks = repeatString;
        this.repeatTimeLiveData.setValue(repeatString);
        this.weeks = StringUtils.toHigherWeek(this.weeks);
        this.showRhythmsModel.setValue(Integer.valueOf(parseInt4));
        this.startH = Integer.parseInt(rhythmData.substring(10, 12), 16);
        this.startM = Integer.parseInt(rhythmData.substring(12, 14), 16);
        this.endH = Integer.parseInt(rhythmData.substring(14, 16), 16);
        int parseInt6 = Integer.parseInt(rhythmData.substring(16, 18), 16);
        this.endM = parseInt6;
        if (this.startH > 24) {
            this.startH = 6;
        }
        if (this.endH > 24) {
            this.startH = 18;
        }
        if (this.startM > 59) {
            this.startM = 0;
        }
        if (parseInt6 > 59) {
            this.endM = 0;
        }
        this.starTimeText.setValue(String.format(Locale.CHINA, "%02d:%02d", Integer.valueOf(this.startH), Integer.valueOf(this.startM)));
        this.endTimeText.setValue(String.format(Locale.CHINA, "%02d:%02d", Integer.valueOf(this.endH), Integer.valueOf(this.endM)));
        this.planTimeText.setValue(this.starTimeText.getValue() + com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SERVER + this.endTimeText.getValue());
        this.planText.setValue(this.rhythmsNameList.get(this.mCurPlan - 1));
    }

    public void setRhythmsGone() {
        this.showRhythms.setValue(false);
        this.showRhythmsSetting.setValue(false);
    }

    public void goBatchSetting() {
        SharedPreferenceUtil.edit().keepShared(Constants.LIGHT_CIRCADIAN_LIGHTING_BATCH_MODE_TEMP, this.showRhythmsModel.getValue().intValue());
        SharedPreferenceUtil.edit().keepShared(Constants.LIGHT_CIRCADIAN_LIGHTING_BATCH_MODE_TEMP_TIME_START, this.starTimeText.getValue());
        SharedPreferenceUtil.edit().keepShared(Constants.LIGHT_CIRCADIAN_LIGHTING_BATCH_MODE_TEMP_TIME_END, this.endTimeText.getValue());
        SharedPreferenceUtil.edit().keepShared(Constants.LIGHT_CIRCADIAN_LIGHTING_BATCH_MODE_TEMP_PLAN, this.mCurPlan);
        SharedPreferenceUtil.edit().keepShared(Constants.LIGHT_CIRCADIAN_LIGHTING_BATCH_MODE_TEMP_REPEAT, this.weeks);
        navigation(NavUtils.destination(ActLightPlanBatchSet.class));
    }

    public void queryRhythmsSettingFromSave() {
        String queryValue = SharedPreferenceUtil.queryValue(Constants.LIGHT_GROUP_CIRCADIAN_LIGHTING_TEMP_SAVE);
        if (queryValue == null || TextUtils.isEmpty(queryValue)) {
            return;
        }
        int parseInt = Integer.parseInt(queryValue.substring(0, 2), 16);
        int parseInt2 = Integer.parseInt(queryValue.substring(2, 4), 16);
        int parseInt3 = Integer.parseInt(queryValue.substring(4, 6), 16);
        int parseInt4 = Integer.parseInt(queryValue.substring(6, 8), 16);
        int parseInt5 = Integer.parseInt(queryValue.substring(8, 10), 16);
        this.mCurPlan = parseInt5;
        if (parseInt5 < 1 || parseInt5 > 4) {
            this.mCurPlan = 1;
        }
        this.showRhythmsSetting.setValue(Boolean.valueOf(parseInt == 1));
        this.rhythmsIsPlay.setValue(Boolean.valueOf(parseInt2 == 1));
        String repeatString = getRepeatString(parseInt3);
        this.weeks = repeatString;
        this.repeatTimeLiveData.setValue(repeatString);
        this.weeks = StringUtils.toHigherWeek(this.weeks);
        this.showRhythmsModel.setValue(Integer.valueOf(parseInt4));
        this.startH = Integer.parseInt(queryValue.substring(10, 12), 16);
        this.startM = Integer.parseInt(queryValue.substring(12, 14), 16);
        this.endH = Integer.parseInt(queryValue.substring(14, 16), 16);
        int parseInt6 = Integer.parseInt(queryValue.substring(16, 18), 16);
        this.endM = parseInt6;
        if (this.startH > 24) {
            this.startH = 6;
        }
        if (this.endH > 24) {
            this.startH = 18;
        }
        if (this.startM > 59) {
            this.startM = 0;
        }
        if (parseInt6 > 59) {
            this.endM = 0;
        }
        this.starTimeText.setValue(String.format(Locale.CHINA, "%02d:%02d", Integer.valueOf(this.startH), Integer.valueOf(this.startM)));
        this.endTimeText.setValue(String.format(Locale.CHINA, "%02d:%02d", Integer.valueOf(this.endH), Integer.valueOf(this.endM)));
        this.planText.setValue(this.rhythmsNameList.get(this.mCurPlan - 1));
    }

    public void setPlan(int i) {
        this.mCurPlan = i;
        this.planText.setValue(this.rhythmsNameList.get(i - 1));
        if (isVirtual()) {
            backupRhythmsData();
        } else {
            getRhythmsAssistant().setRhythmsPlanMode(ActivityUtils.getTopActivity(), 3, this.mCurPlan, this.iAction);
            saveTempControl();
        }
    }

    public int getRepeatInt(String week) {
        if (TextUtils.isEmpty(week)) {
            return 128;
        }
        String[] split = week.split(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP);
        if (split.length == 7) {
            return 127;
        }
        int i = 0;
        for (String str : split) {
            i += 1 << (Integer.parseInt(str) - 1);
        }
        return i;
    }

    public String getRepeatString(int repeat) {
        if ((repeat & 128) != 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            if (((1 << i) & repeat) != 0) {
                sb.append(i + 1);
                sb.append(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP);
            }
        }
        if (sb.length() != 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public void setStarTime(int h, int m2) {
        int i = this.endH;
        if (h > i || (h == i && m2 >= this.endM)) {
            SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_start_later_than_the_end_tip));
            return;
        }
        if (h == i && this.endM - m2 < 12) {
            SmartToast.showShort(String.format(ActivityUtils.getTopActivity().getString(R.string.app_time_span_tip), 12));
            return;
        }
        this.startH = h;
        this.startM = m2;
        this.starTimeText.setValue(String.format(Locale.CHINA, "%02d:%02d", Integer.valueOf(h), Integer.valueOf(m2)));
        if (isVirtual()) {
            backupRhythmsData();
        } else {
            getRhythmsAssistant().setRhythmsTime(ActivityUtils.getTopActivity(), this.startH, this.startM, this.endH, this.endM, this.iAction);
            saveTempControl();
        }
    }

    public void setEndTime(int h, int m2) {
        int i = this.startH;
        if (h < i || (h == m2 && m2 < this.startM)) {
            SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_end_less_than_the_start_tip));
            return;
        }
        if (h == i && m2 - this.startM < 12) {
            SmartToast.showShort(String.format(ActivityUtils.getTopActivity().getString(R.string.app_time_span_tip), 12));
            return;
        }
        this.endH = h;
        this.endM = m2;
        this.endTimeText.setValue(String.format(Locale.CHINA, "%02d:%02d", Integer.valueOf(h), Integer.valueOf(m2)));
        if (isVirtual()) {
            backupRhythmsData();
        } else {
            getRhythmsAssistant().setRhythmsTime(ActivityUtils.getTopActivity(), this.startH, this.startM, this.endH, this.endM, this.iAction);
            saveTempControl();
        }
    }

    public int getStarH() {
        return this.startH;
    }

    public int getStarM() {
        return this.startM;
    }

    public int getEndH() {
        return this.endH;
    }

    public int getEndM() {
        return this.endM;
    }

    public void setWeek(String week) {
        this.weeks = week;
        String lowerWeek = StringUtils.toLowerWeek(week);
        this.repeatTimeLiveData.setValue(lowerWeek);
        if (isVirtual()) {
            backupRhythmsData();
        } else {
            getRhythmsAssistant().setRhythmsWeek(ActivityUtils.getTopActivity(), getRepeatInt(lowerWeek), this.iAction);
            saveTempControl();
        }
    }

    public RhythmsAssistant getRhythmsAssistant() {
        if (this.controlObject.getValue() == null) {
            return CmdAssistant.getLightRhythmsCmdAssistant(this.controlDevice.getValue(), new int[0]);
        }
        return CmdAssistant.getLightRhythmsCmdAssistant(this.controlObject.getValue(), new int[0]);
    }

    public RhythmsAssistant getRhythmsQueryAssistant() {
        Group group = (Group) this.controlObject.getValue();
        if (group.getDeviceIds().size() > 0) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(group.getDeviceIds().get(0).longValue());
            if (deviceByDeviceId != null) {
                return CmdAssistant.getLightRhythmsCmdAssistant(deviceByDeviceId, new int[0]);
            }
        }
        return CmdAssistant.getLightRhythmsCmdAssistant(null, new int[0]);
    }

    public void getSunTime() {
        ((ObservableSubscribeProxy) Injection.net().getSunTime(getCurPlace().getLongitude() + com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP + getCurPlace().getLatitude()).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<SunInfo.SunBean>() { // from class: com.ltech.smarthome.ui.device.light.ActRhythmsSettingVM.2
            @Override // io.reactivex.functions.Consumer
            public void accept(SunInfo.SunBean sunInfo) throws Exception {
                try {
                    if (sunInfo.getSunriseTime() == null && sunInfo.getSunsetTime() == null) {
                        return;
                    }
                    String str = sunInfo.getSunriseTime().split(ExifInterface.GPS_DIRECTION_TRUE)[1].split("\\+")[0];
                    String str2 = sunInfo.getSunsetTime().split(ExifInterface.GPS_DIRECTION_TRUE)[1].split("\\+")[0];
                    ActRhythmsSettingVM.this.sunRiseText.setValue(str);
                    ActRhythmsSettingVM.this.sunSetText.setValue(str2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new SmartErrorComsumer());
    }

    public void backupRhythmsData() {
        String str;
        if (this.controlDevice.getValue() != null) {
            int intValue = this.showRhythmsModel.getValue().intValue();
            if (intValue == 2) {
                str = StringUtils.toHexString(this.startH) + StringUtils.toHexString(this.startM) + StringUtils.toHexString(this.endH) + StringUtils.toHexString(this.endM);
            } else if (intValue != 3) {
                str = "";
            } else {
                str = this.mRhythmsList.get(this.mCurPlan - 1).getContent();
            }
            ReplaceHelper.instance().backupData(getLifecycleOwner(), this.controlDevice.getValue().getDeviceId(), UpdateBackDataRequest.CIRCADIAN_PLAN, CmdAssistant.getLightRhythmsCmdAssistant(this.controlDevice.getValue(), new int[0]).saveRhythmsPlanInfo(intValue, this.showRhythmsSetting.getValue().booleanValue(), getRepeatInt(this.weeks), str));
        }
    }
}