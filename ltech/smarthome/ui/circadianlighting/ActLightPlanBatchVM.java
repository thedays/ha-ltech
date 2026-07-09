package com.ltech.smarthome.ui.circadianlighting;

import android.text.TextUtils;
import android.view.View;
import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.SunInfo;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.mode.ListModeResponse;
import com.ltech.smarthome.ui.automation.ActSelectWeek;
import com.ltech.smarthome.ui.control.ActChangeEngineerMode;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.message.utils.StringUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ActLightPlanBatchVM extends BaseViewModel {
    public int mCurPlan;
    public List<ListModeResponse.RowsBean> mRhythmsList;
    public String weeks;
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
    public MutableLiveData<String> sunRiseText = new MutableLiveData<>("");
    public MutableLiveData<String> sunSetText = new MutableLiveData<>("");
    public SingleLiveEvent<Void> selectStarTimeDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> selectEndTimeDialogEvent = new SingleLiveEvent<>();
    public MutableLiveData<Boolean> rhythmsIsPlay = new MutableLiveData<>(false);
    public MutableLiveData<String> planText = new MutableLiveData<>("");
    public SingleLiveEvent<Void> showPlanListEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showKValueEvent = new SingleLiveEvent<>();
    public List<String> rhythmsNameList = new ArrayList(Arrays.asList(ActivityUtils.getTopActivity().getResources().getStringArray(R.array.light_rhythms_diy)));
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanBatchVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActLightPlanBatchVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.layout_end_time /* 2131297463 */:
                this.selectEndTimeDialogEvent.call();
                break;
            case R.id.layout_plan /* 2131297570 */:
            case R.id.tv_plan /* 2131298865 */:
                if (this.showRhythmsModel.getValue().intValue() != 3) {
                    this.showRhythmsModel.setValue(3);
                    if (TextUtils.isEmpty(this.planText.getValue())) {
                        this.showPlanListEvent.call();
                        break;
                    }
                } else {
                    this.showRhythmsModel.setValue(3);
                    this.showPlanListEvent.call();
                    break;
                }
                break;
            case R.id.layout_plan_edit /* 2131297571 */:
                navigation(NavUtils.destination(ActLightPlanList.class).withString(Constants.SELECTED_LIST, GsonUtils.toJson(this.mRhythmsList)).withBoolean(Constants.LIGHT_RHYTHMS_ON_OFF, Boolean.TRUE.equals(this.showRhythmsSetting.getValue())).withInt(Constants.LIGHT_RHYTHMS_REPEAT, getRepeatInt(this.weeks)).withBoolean(Constants.LIGHT_CIRCADIAN_LIGHTING_BATCH, true).withInt(Constants.LIGHT_RHYTHMS_PLAN_ID_CUR, this.mCurPlan).withRequestCode(5009));
                break;
            case R.id.layout_plan_time /* 2131297572 */:
                this.showRhythmsModel.setValue(2);
                break;
            case R.id.layout_repeat_date /* 2131297596 */:
                navigation(NavUtils.destination(ActSelectWeek.class).withString(Constants.WEEKS, StringUtils.toHigherWeek(this.weeks)).withDefaultRequestCode());
                break;
            case R.id.layout_start_time /* 2131297657 */:
                this.selectStarTimeDialogEvent.call();
                break;
            case R.id.layout_sunset /* 2131297666 */:
                this.showRhythmsModel.setValue(1);
                break;
            case R.id.tv_next /* 2131298810 */:
                if (Injection.repo().device().getExistGateway() == null) {
                    showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.no_super_panel));
                    break;
                } else {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        int intValue = this.showRhythmsModel.getValue() != null ? this.showRhythmsModel.getValue().intValue() : 1;
                        SharedPreferenceUtil.edit().keepShared(Constants.LIGHT_CIRCADIAN_LIGHTING_BATCH_MODE_TEMP, intValue);
                        jSONObject.put("mode", intValue);
                        jSONObject.put("repeat", getRepeatInt(this.weeks));
                        if (intValue == 2) {
                            jSONObject.put("startH", this.startH);
                            jSONObject.put("startM", this.startM);
                            jSONObject.put("endH", this.endH);
                            jSONObject.put("endM", this.endM);
                        } else if (intValue == 3) {
                            Iterator<ListModeResponse.RowsBean> it = this.mRhythmsList.iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    ListModeResponse.RowsBean next = it.next();
                                    if (next.getModeindex() == this.mCurPlan) {
                                        jSONObject.put("planData", next.getContent());
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    NavUtils.destination(ActChangeEngineerMode.class).withLong(Constants.PLACE_ID, getCurrentPlace().getPlaceId()).withInt(Constants.ENGINEER_SET_TYPE, 8).withString(Constants.CIRCADIAN_LIGHTING_DATA, jSONObject.toString()).navigation(ActivityUtils.getTopActivity());
                    break;
                }
        }
    }

    public void syncPlanList() {
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
        Place value = Injection.repo().home().getSelectPlace().getValue();
        if (value != null) {
            ((ObservableSubscribeProxy) Injection.net().getModeList(value.getPlaceId(), 2, 3).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanBatchVM$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActLightPlanBatchVM.this.lambda$syncPlanList$1((ListModeResponse) obj);
                }
            }, new SmartErrorComsumer());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncPlanList$1(ListModeResponse listModeResponse) throws Exception {
        if (listModeResponse.getTotal() > 0) {
            Iterator<ListModeResponse.RowsBean> it = listModeResponse.getRows().iterator();
            while (it.hasNext()) {
                this.mRhythmsList.set(r0.getModeindex() - 1, it.next());
            }
        }
        int i = this.mCurPlan;
        if (i > 0) {
            this.planText.setValue(this.mRhythmsList.get(i - 1).getModename());
        }
    }

    public void getSunTime() {
        ((ObservableSubscribeProxy) Injection.net().getSunTime(getCurrentPlace().getLongitude() + com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP + getCurrentPlace().getLatitude()).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<SunInfo.SunBean>() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanBatchVM.1
            @Override // io.reactivex.functions.Consumer
            public void accept(SunInfo.SunBean sunInfo) throws Exception {
                try {
                    if (sunInfo.getSunriseTime() == null && sunInfo.getSunsetTime() == null) {
                        return;
                    }
                    String str = sunInfo.getSunriseTime().split(ExifInterface.GPS_DIRECTION_TRUE)[1].split("\\+")[0];
                    String str2 = sunInfo.getSunsetTime().split(ExifInterface.GPS_DIRECTION_TRUE)[1].split("\\+")[0];
                    ActLightPlanBatchVM.this.sunRiseText.setValue(str);
                    ActLightPlanBatchVM.this.sunSetText.setValue(str2);
                    SharedPreferenceUtil.edit().keepShared(Constants.SUN_RISE, str);
                    SharedPreferenceUtil.edit().keepShared(Constants.SUN_SET, str2);
                    if (!"".equals(SharedPreferenceUtil.queryValue(Constants.LIGHT_CIRCADIAN_LIGHTING_BATCH_MODE_TEMP_TIME_START)) && !"".equals(SharedPreferenceUtil.queryValue(Constants.LIGHT_CIRCADIAN_LIGHTING_BATCH_MODE_TEMP_TIME_END))) {
                        return;
                    }
                    ActLightPlanBatchVM.this.initStartAndEndTime(str, str2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new SmartErrorComsumer());
    }

    public void setPlan(int i) {
        this.mCurPlan = i;
        this.planText.setValue(this.mRhythmsList.get(i - 1).getModename());
    }

    public void initStartAndEndTime(String start, String end) {
        if (!TextUtils.isEmpty(start)) {
            this.startH = Integer.parseInt(start.split(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR)[0]);
            this.startM = Integer.parseInt(start.split(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR)[1]);
        }
        if (TextUtils.isEmpty(end)) {
            return;
        }
        this.endH = Integer.parseInt(end.split(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR)[0]);
        this.endM = Integer.parseInt(end.split(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR)[1]);
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
        if (h == i && this.endM - m2 < 1) {
            SmartToast.showShort(String.format(ActivityUtils.getTopActivity().getString(R.string.app_str_interval_time_less_than), 1));
            return;
        }
        this.startH = h;
        this.startM = m2;
        this.starTimeText.setValue(String.format(Locale.CHINA, "%02d:%02d", Integer.valueOf(h), Integer.valueOf(m2)));
    }

    public void setEndTime(int h, int m2) {
        int i = this.startH;
        if (h < i || (h == m2 && m2 < this.startM)) {
            SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_end_less_than_the_start_tip));
            return;
        }
        if (h == i && m2 - this.startM < 1) {
            SmartToast.showShort(String.format(ActivityUtils.getTopActivity().getString(R.string.app_str_interval_time_less_than), 1));
            return;
        }
        this.endH = h;
        this.endM = m2;
        this.endTimeText.setValue(String.format(Locale.CHINA, "%02d:%02d", Integer.valueOf(h), Integer.valueOf(m2)));
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
        String lowerWeek = StringUtils.toLowerWeek(week);
        this.weeks = lowerWeek;
        this.repeatTimeLiveData.setValue(lowerWeek);
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }
}