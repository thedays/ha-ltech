package com.ltech.smarthome.ui.automation;

import android.view.View;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectEffectPeriodVM extends BaseViewModel {
    public List<String> minList;
    public String weeks;
    public MutableLiveData<String> repeatTimeLiveData = new MutableLiveData<>();
    public MutableLiveData<String> startTime = new MutableLiveData<>();
    public MutableLiveData<String> endTime = new MutableLiveData<>();
    public MutableLiveData<Boolean> showNextDay = new MutableLiveData<>();
    public SingleLiveEvent<Void> showEditStartTimeDialog = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showEditEndTimeDialog = new SingleLiveEvent<>();
    public int startHourPos = 0;
    public int endHourPos = 0;
    public int startMinPos = 0;
    public int endMinPos = 0;
    public BindingCommand<View> clickCommand = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.automation.ActSelectEffectPeriodVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActSelectEffectPeriodVM.this.lambda$new$0((View) obj);
        }
    });
    public List<String> hourList = new ArrayList();

    public ActSelectEffectPeriodVM() {
        for (int i = 0; i < 24; i++) {
            this.hourList.add(i < 10 ? "0" + i : String.valueOf(i));
        }
        this.minList = new ArrayList();
        for (int i2 = 0; i2 < 60; i2++) {
            this.minList.add(i2 < 10 ? "0" + i2 : String.valueOf(i2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        int id = view.getId();
        if (id == R.id.tv_end_time) {
            this.showEditEndTimeDialog.call();
        } else if (id == R.id.tv_start_time) {
            this.showEditStartTimeDialog.call();
        } else {
            if (id != R.id.v_select_time) {
                return;
            }
            navigation(NavUtils.destination(ActSelectWeek.class).withString(Constants.WEEKS, this.weeks).withDefaultRequestCode());
        }
    }

    public boolean isNextDay() {
        return (Integer.parseInt(this.hourList.get(this.startHourPos)) * 60) + Integer.parseInt(this.minList.get(this.startMinPos)) >= (Integer.parseInt(this.hourList.get(this.endHourPos)) * 60) + Integer.parseInt(this.minList.get(this.endMinPos));
    }

    public String getTimeString(int hourPos, int minPos) {
        return String.format("%s:%s", this.hourList.get(hourPos), this.minList.get(minPos));
    }
}