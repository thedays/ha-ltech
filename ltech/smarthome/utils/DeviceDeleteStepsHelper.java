package com.ltech.smarthome.utils;

import androidx.fragment.app.Fragment;
import com.ltech.smarthome.model.bean.Step;
import com.ltech.smarthome.ui.config.FtStepsDelete;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class DeviceDeleteStepsHelper {
    List<Step> steps = new ArrayList();

    public DeviceDeleteStepsHelper withNormalStep(String title, int img) {
        Step step = new Step(title, 0);
        step.setImg(img);
        this.steps.add(step);
        return this;
    }

    public DeviceDeleteStepsHelper withCountdownStep(String title, int time) {
        Step step = new Step(title, 1);
        step.setCountDown(time);
        this.steps.add(step);
        return this;
    }

    public List<Fragment> getData() {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < this.steps.size()) {
            Step step = this.steps.get(i);
            i++;
            arrayList.add(FtStepsDelete.create(step, i, this.steps.size()));
        }
        return arrayList;
    }
}