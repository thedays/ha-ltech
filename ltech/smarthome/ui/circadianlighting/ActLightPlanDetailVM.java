package com.ltech.smarthome.ui.circadianlighting;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.bean.RhythmsPlanInfo;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ActLightPlanDetailVM extends BaseViewModel {
    private long controlId;
    private int gradientTime;
    private boolean groupControl;
    private boolean isOn;
    private int mCurPlanId;
    private int mPlanId;
    private int repeat;
    public SingleLiveEvent<Void> delEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> timeEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> renameDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> changeGradientMode = new SingleLiveEvent<>();
    public MutableLiveData<String> nameLiveData = new MutableLiveData<>();
    public MutableLiveData<Integer> selectedGradientModeData = new MutableLiveData<>(0);
    public MutableLiveData<Integer> selectedGradientTime = new MutableLiveData<>(0);
    public MutableLiveData<Object> controlObject = new MutableLiveData<>();
    public MutableLiveData<List<RhythmsPlanInfo.Item>> factoryResetEvent = new MutableLiveData<>();
    private int mSelectedPos = -1;
    private List<RhythmsPlanInfo.Item> list = new ArrayList();
    private int mLastPos = -1;
    private boolean batch = false;
    public final List<String> defaultPlans = new ArrayList(Arrays.asList(Constants.LIGHT_PLAN_DEFAULT_LIST));
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetailVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActLightPlanDetailVM.this.lambda$new$0((View) obj);
        }
    });

    public int getLastPos() {
        return this.mLastPos;
    }

    public void setLastPos(int mLastPos) {
        this.mLastPos = mLastPos;
    }

    public int getSelectedPos() {
        return this.mSelectedPos;
    }

    public void setSelectedPos(int mSelectedPos) {
        this.mSelectedPos = mSelectedPos;
        if (getLastPos() != mSelectedPos) {
            setLastPos(mSelectedPos);
        }
    }

    public List<RhythmsPlanInfo.Item> getPlan(Map<Integer, String> onlinePlan) {
        this.list.clear();
        int i = 0;
        while (i < this.defaultPlans.size()) {
            int i2 = i + 1;
            if (onlinePlan.containsKey(Integer.valueOf(i2))) {
                this.defaultPlans.set(i, onlinePlan.get(Integer.valueOf(i2)));
            }
            i = i2;
        }
        int size = this.defaultPlans.size();
        int i3 = this.mPlanId;
        if (size > i3 - 1) {
            this.list.addAll(getPlanDetail(this.defaultPlans.get(i3 - 1)));
        }
        return this.list;
    }

    public List<RhythmsPlanInfo.Item> getPlanDetail(String detail) {
        ArrayList arrayList = new ArrayList();
        try {
            int parseInt = Integer.parseInt(detail.substring(4, 6), 16);
            int parseInt2 = Integer.parseInt(detail.substring(6, 8), 16);
            this.selectedGradientTime.setValue(Integer.valueOf(parseInt2));
            setGradientTime(parseInt2);
            this.selectedGradientModeData.setValue(Integer.valueOf(parseInt));
            String substring = detail.substring(8);
            if (substring.length() % 16 == 0) {
                int i = 0;
                while (i < substring.length() / 16) {
                    int i2 = i * 16;
                    i++;
                    String substring2 = substring.substring(i2, i * 16);
                    arrayList.add(new RhythmsPlanInfo.Item(Integer.parseInt(substring2.substring(6, 8), 16), Integer.parseInt(substring2.substring(8, 10), 16), Integer.parseInt(substring2.substring(12, 14), 16), Integer.parseInt(substring2.substring(14, 16), 16)));
                }
            }
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return arrayList;
        }
    }

    public List<RhythmsPlanInfo.Item> getData() {
        return this.list;
    }

    public String getEditData() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(getPlanId()));
        arrayList.add(Integer.valueOf(getData().size()));
        arrayList.add(this.selectedGradientModeData.getValue());
        arrayList.add(Integer.valueOf(this.gradientTime));
        for (RhythmsPlanInfo.Item item : getData()) {
            arrayList.add(Integer.valueOf(item.getRed()));
            arrayList.add(Integer.valueOf(item.getGreen()));
            arrayList.add(Integer.valueOf(item.getBlue()));
            arrayList.add(Integer.valueOf(item.getWyBrt()));
            arrayList.add(Integer.valueOf(item.getwOrWy()));
            arrayList.add(Integer.valueOf(item.getRgbBrt()));
            arrayList.add(Integer.valueOf(item.getH()));
            arrayList.add(Integer.valueOf(item.getM()));
        }
        byte[] bArr = new byte[arrayList.size()];
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            bArr[i] = ((Integer) arrayList.get(i)).byteValue();
        }
        return StringUtils.byte2Str(bArr);
    }

    public void setData(List<RhythmsPlanInfo.Item> plans) {
        this.list = plans;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.delete_tv /* 2131296627 */:
                this.delEvent.call();
                return;
            case R.id.factory_reset_tv /* 2131296735 */:
                this.defaultPlans.set(this.mPlanId - 1, Constants.LIGHT_PLAN_DEFAULT_LIST[this.mPlanId - 1]);
                this.list.clear();
                int size = this.defaultPlans.size();
                int i = this.mPlanId;
                if (size > i - 1) {
                    String substring = this.defaultPlans.get(i - 1).substring(8);
                    if (substring.length() % 16 == 0) {
                        int i2 = 0;
                        while (i2 < substring.length() / 16) {
                            int i3 = i2 * 16;
                            i2++;
                            String substring2 = substring.substring(i3, i2 * 16);
                            this.list.add(new RhythmsPlanInfo.Item(Integer.parseInt(substring2.substring(6, 8), 16), Integer.parseInt(substring2.substring(8, 10), 16), Integer.parseInt(substring2.substring(12, 14), 16), Integer.parseInt(substring2.substring(14, 16), 16)));
                        }
                    }
                }
                this.factoryResetEvent.setValue(this.list);
                return;
            case R.id.gradient_layout /* 2131296781 */:
                this.changeGradientMode.call();
                return;
            case R.id.name_layout /* 2131297811 */:
                this.renameDialogEvent.call();
                return;
            case R.id.review_tv /* 2131298025 */:
                if (isBatch()) {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("mode", 3);
                        jSONObject.put("repeat", getRepeat());
                        jSONObject.put("planData", getEditData());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (getCurrentPlace() != null) {
                        NavUtils.destination(ActLightPlanPreview.class).withLong(Constants.PLACE_ID, getCurrentPlace().getPlaceId()).withString(Constants.CIRCADIAN_LIGHTING_DATA, jSONObject.toString()).navigation(ActivityUtils.getTopActivity());
                        break;
                    }
                } else {
                    previewRhythmsPlanInfo(ActivityUtils.getTopActivity(), getPlanId(), this.isOn, this.repeat, this.selectedGradientModeData.getValue().intValue(), this.gradientTime, getData(), new IAction<Boolean>(this) { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetailVM.1
                        @Override // com.ltech.smarthome.base.IAction
                        public void act(Boolean aBoolean) {
                        }
                    });
                    return;
                }
                break;
            case R.id.time_layout /* 2131298375 */:
                this.timeEvent.call();
                return;
        }
    }

    public String getName() {
        return this.nameLiveData.getValue();
    }

    public void setName(String s) {
        this.nameLiveData.setValue(s);
    }

    public void save() {
        boolean z;
        if (getData() == null || getData().size() <= 0) {
            return;
        }
        Iterator<RhythmsPlanInfo.Item> it = getData().iterator();
        int i = -1;
        int i2 = -1;
        while (true) {
            z = true;
            if (!it.hasNext()) {
                break;
            }
            RhythmsPlanInfo.Item next = it.next();
            int h = next.getH();
            int m2 = next.getM();
            if (i <= h && ((i != h || i2 <= m2) && (i != h || m2 - i2 >= 1))) {
                i2 = m2;
                i = h;
            }
        }
        if (!checkTime()) {
            showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.app_circadian_lighting_save_error_tip));
            return;
        }
        if (z) {
            showLoadingDialog();
            if (getCurPlanId() == getPlanId()) {
                saveAndPlayRhythmsPlanInfo(ActivityUtils.getTopActivity(), getPlanId(), this.isOn, this.repeat, this.selectedGradientModeData.getValue().intValue(), this.gradientTime, getData(), new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetailVM.2
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(Boolean aBoolean) {
                        if (aBoolean.booleanValue()) {
                            ActLightPlanDetailVM.this.saveToServer(false);
                        }
                    }
                });
                return;
            } else {
                saveRhythmsPlanInfo(ActivityUtils.getTopActivity(), getPlanId(), this.isOn, this.repeat, this.selectedGradientModeData.getValue().intValue(), this.gradientTime, getData(), new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetailVM.3
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(Boolean aBoolean) {
                        if (aBoolean.booleanValue()) {
                            ActLightPlanDetailVM.this.saveToServer(false);
                        }
                    }
                });
                return;
            }
        }
        showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.app_circadian_lighting_save_node_error_tip));
    }

    public void saveToServer(final boolean isRhythmsBatch) {
        int i = -1;
        int i2 = -1;
        for (RhythmsPlanInfo.Item item : getData()) {
            int h = item.getH();
            int m2 = item.getM();
            if (i > h || ((i == h && i2 > m2) || (i == h && m2 - i2 < 1))) {
                showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.app_circadian_lighting_save_node_error_tip));
                return;
            } else {
                i2 = m2;
                i = h;
            }
        }
        ((ObservableSubscribeProxy) Injection.net().addMode(getName(), Injection.repo().home().getSelectPlace().getValue().getPlaceId(), 2, 3, getPlanId(), 0, getCmdString(), "", -1).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetailVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActLightPlanDetailVM.this.lambda$saveToServer$1((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetailVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActLightPlanDetailVM.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetailVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActLightPlanDetailVM.this.lambda$saveToServer$2(isRhythmsBatch, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveToServer$1(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveToServer$2(boolean z, Object obj) throws Exception {
        if (z) {
            Bundle bundle = new Bundle();
            bundle.putString("data", getEditData());
            bundle.putInt("index", getPlanId());
            finishActivity(-1, bundle);
        } else {
            setResult(-1);
            finishActivity();
        }
        showSuccessTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_success));
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    public boolean checkTime() {
        int i = 25;
        int i2 = 60;
        for (RhythmsPlanInfo.Item item : getData()) {
            int h = item.getH();
            int m2 = item.getM();
            if (!calcTime(i, i2, h, m2)) {
                return false;
            }
            i2 = m2;
            i = h;
        }
        return true;
    }

    private boolean calcTime(int lastH, int lastM, int curH, int cutM) {
        return Math.abs(((lastH * 60) + lastM) - ((curH * 60) + cutM)) >= this.gradientTime;
    }

    private String getCmdString() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(getPlanId()));
        arrayList.add(Integer.valueOf(getData().size()));
        arrayList.add(this.selectedGradientModeData.getValue());
        arrayList.add(Integer.valueOf(this.gradientTime));
        for (RhythmsPlanInfo.Item item : getData()) {
            arrayList.add(Integer.valueOf(item.getRed()));
            arrayList.add(Integer.valueOf(item.getGreen()));
            arrayList.add(Integer.valueOf(item.getBlue()));
            arrayList.add(Integer.valueOf(item.getWyBrt()));
            arrayList.add(Integer.valueOf(item.getwOrWy()));
            arrayList.add(Integer.valueOf(item.getRgbBrt()));
            arrayList.add(Integer.valueOf(item.getH()));
            arrayList.add(Integer.valueOf(item.getM()));
        }
        byte[] bArr = new byte[arrayList.size()];
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            bArr[i] = ((Integer) arrayList.get(i)).byteValue();
        }
        return StringUtils.byte2Str(bArr);
    }

    public void saveRhythmsPlanInfo(Context context, int modeNum, boolean isOn, int repeatTimes, int gradientMode, int playTime, List<RhythmsPlanInfo.Item> contentItemList, IAction<Boolean> result) {
        CmdAssistant.getLightRhythmsCmdAssistant(this.controlObject.getValue(), new int[0]).saveRhythmsPlanInfo(context, modeNum, isOn, repeatTimes, gradientMode, playTime, contentItemList, result);
    }

    public void saveAndPlayRhythmsPlanInfo(Context context, int modeNum, boolean isOn, int repeatTimes, int gradientMode, int playTime, List<RhythmsPlanInfo.Item> contentItemList, IAction<Boolean> result) {
        CmdAssistant.getLightRhythmsCmdAssistant(this.controlObject.getValue(), new int[0]).saveAndPlayRhythmsPlanInfo(context, modeNum, isOn, repeatTimes, gradientMode, playTime, contentItemList, result);
    }

    public void previewRhythmsPlanInfo(Context context, int modeNum, boolean isOn, int repeatTimes, int gradientMode, int playTime, List<RhythmsPlanInfo.Item> contentItemList, IAction<Boolean> result) {
        CmdAssistant.getLightRhythmsCmdAssistant(this.controlObject.getValue(), new int[0]).previewRhythmsPlanInfo(context, modeNum, isOn, repeatTimes, gradientMode, playTime, contentItemList, result);
    }

    public LightAssistant getLightCmdHelper() {
        return CmdAssistant.getLightCmdAssistant(this.controlObject.getValue(), new int[0]);
    }

    public void setPlanId(int id) {
        this.mPlanId = id;
    }

    public int getPlanId() {
        return this.mPlanId;
    }

    public void setCurPlanId(int mCurPlanId) {
        this.mCurPlanId = mCurPlanId;
    }

    public int getCurPlanId() {
        return this.mCurPlanId;
    }

    public int getGradientTime() {
        return this.gradientTime;
    }

    public void setGradientTime(int gradientTime) {
        this.gradientTime = gradientTime;
    }

    public void setControlId(long id) {
        this.controlId = id;
    }

    public void setGroupControl(boolean b2) {
        this.groupControl = b2;
    }

    public boolean isGroupControl() {
        return this.groupControl;
    }

    public long getControlId() {
        return this.controlId;
    }

    public void setIsOn(boolean isOn) {
        this.isOn = isOn;
    }

    public boolean getIsOn() {
        return this.isOn;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public int getRepeat() {
        return this.repeat;
    }

    public boolean isBatch() {
        return this.batch;
    }

    public void setBatch(boolean batch) {
        this.batch = batch;
    }
}