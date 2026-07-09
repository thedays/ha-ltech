package com.ltech.smarthome.ui.automation;

import android.content.Intent;
import android.view.View;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.PickerAdapter;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActSelectTimeBinding;
import com.ltech.smarthome.model.auto.TimerConditionParam;
import com.ltech.smarthome.ui.automation.ISelectCondition;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SelectorUtils;
import com.ltech.smarthome.view.layoutmanager.PickerLayoutManager;
import java.util.ArrayList;
import java.util.Calendar;

/* loaded from: classes4.dex */
public class ActSelectTime extends BaseNormalActivity<ActSelectTimeBinding> implements ISelectCondition {
    private int hour;
    PickerAdapter mHourAdapter;
    PickerLayoutManager mHourManager;
    PickerAdapter mMinuteAdapter;
    PickerLayoutManager mMinuteManager;
    private int min;
    public MutableLiveData<String> repeatTimeLiveData = new MutableLiveData<>();
    private String weeks;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_time;
    }

    @Override // com.ltech.smarthome.ui.automation.ISelectCondition
    public /* synthetic */ void setSelectResult(BaseNormalActivity baseNormalActivity) {
        ISelectCondition.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.ui.automation.ISelectCondition
    public /* synthetic */ void setSelectStatusResult(BaseNormalActivity baseNormalActivity) {
        ISelectCondition.CC.$default$setSelectStatusResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.timing));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.finish));
        Calendar calendar = Calendar.getInstance();
        this.hour = calendar.get(11);
        this.min = calendar.get(12);
        if (SceneHelper.instance().conditionParam != null) {
            TimerConditionParam timerConditionParam = (TimerConditionParam) SceneHelper.instance().conditionParam;
            this.hour = timerConditionParam.hour;
            this.min = timerConditionParam.min;
            this.weeks = timerConditionParam.weeks;
        }
        this.repeatTimeLiveData.setValue(this.weeks);
        initTimeView();
        ((ActSelectTimeBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.automation.ActSelectTime$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActSelectTime.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        NavUtils.destination(ActSelectWeek.class).withString(Constants.WEEKS, this.weeks).withDefaultRequestCode().navigation(this);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        this.repeatTimeLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.automation.ActSelectTime$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSelectTime.this.lambda$startObserve$1((String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(String str) {
        ((ActSelectTimeBinding) this.mViewBinding).tvRepeatTime.setText(HelpUtils.getWeeksString(this, str));
    }

    private void initTimeView() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 24; i++) {
            arrayList.add(i < 10 ? "0" + i : String.valueOf(i));
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i2 = 0; i2 < 60; i2++) {
            arrayList2.add(i2 < 10 ? "0" + i2 : String.valueOf(i2));
        }
        this.mHourAdapter = new PickerAdapter(this);
        this.mMinuteAdapter = new PickerAdapter(this);
        this.mHourAdapter.setData(arrayList);
        this.mMinuteAdapter.setData(arrayList2);
        this.mHourManager = new PickerLayoutManager.Builder(this).setAlpha(true).setMaxItem(5).build();
        this.mMinuteManager = new PickerLayoutManager.Builder(this).setAlpha(true).setMaxItem(5).build();
        ((ActSelectTimeBinding) this.mViewBinding).pickViewHour.setLayoutManager(this.mHourManager);
        ((ActSelectTimeBinding) this.mViewBinding).pickViewMin.setLayoutManager(this.mMinuteManager);
        ((ActSelectTimeBinding) this.mViewBinding).pickViewHour.setAdapter(this.mHourAdapter);
        ((ActSelectTimeBinding) this.mViewBinding).pickViewMin.setAdapter(this.mMinuteAdapter);
        SelectorUtils.updateListData(arrayList, this.hour, ((ActSelectTimeBinding) this.mViewBinding).pickViewHour);
        SelectorUtils.updateListData(arrayList2, this.min, ((ActSelectTimeBinding) this.mViewBinding).pickViewMin);
        this.mHourManager.setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.ui.automation.ActSelectTime$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
            public final void onPicked(RecyclerView recyclerView, int i3) {
                ActSelectTime.this.lambda$initTimeView$2(recyclerView, i3);
            }
        });
        this.mMinuteManager.setOnPickerListener(new PickerLayoutManager.OnPickerListener() { // from class: com.ltech.smarthome.ui.automation.ActSelectTime$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.layoutmanager.PickerLayoutManager.OnPickerListener
            public final void onPicked(RecyclerView recyclerView, int i3) {
                ActSelectTime.this.lambda$initTimeView$3(recyclerView, i3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTimeView$2(RecyclerView recyclerView, int i) {
        this.hour = this.mHourManager.getPickedPosition();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTimeView$3(RecyclerView recyclerView, int i) {
        this.min = this.mMinuteManager.getPickedPosition();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        TimerConditionParam timerConditionParam = new TimerConditionParam();
        timerConditionParam.hour = this.hour;
        timerConditionParam.min = this.min;
        timerConditionParam.weeks = this.weeks;
        SceneHelper.instance().conditionParam = timerConditionParam;
        SceneHelper.instance().conditionParamType = 2;
        setSelectResult(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 3004 || data == null) {
            return;
        }
        String stringExtra = data.getStringExtra(Constants.WEEKS);
        this.weeks = stringExtra;
        this.repeatTimeLiveData.setValue(stringExtra);
    }
}