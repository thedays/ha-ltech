package com.ltech.smarthome.utils;

import androidx.fragment.app.Fragment;
import com.ltech.smarthome.model.bean.Step;
import com.ltech.smarthome.ui.config.FtStepsIntroduction;
import com.ltech.smarthome.ui.device.microwave_sensor.test.FtTestStep;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class DeviceAddStepsHelper {
    List<Step> steps = new ArrayList();

    public DeviceAddStepsHelper withNormalStep(String title, int img) {
        Step step = new Step(title, 0);
        step.setImg(img);
        this.steps.add(step);
        return this;
    }

    public DeviceAddStepsHelper withCountdownStep(String title, int time) {
        Step step = new Step(title, 1);
        step.setCountDown(time);
        this.steps.add(step);
        return this;
    }

    public DeviceAddStepsHelper withCountdownStep(String title, int img, int time) {
        Step step = new Step(title, 1);
        step.setImg(img);
        step.setCountDown(time);
        this.steps.add(step);
        return this;
    }

    public DeviceAddStepsHelper withScanStep(String title, int img) {
        Step step = new Step(title, 2);
        step.setImg(img);
        this.steps.add(step);
        return this;
    }

    public List<Fragment> getData() {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < this.steps.size()) {
            Step step = this.steps.get(i);
            i++;
            arrayList.add(FtStepsIntroduction.create(step, i, this.steps.size()));
        }
        return arrayList;
    }

    public List<Fragment> getSensorData() {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < this.steps.size()) {
            Step step = this.steps.get(i);
            i++;
            arrayList.add(FtTestStep.create(step, i, this.steps.size()));
        }
        return arrayList;
    }
}