package com.ltech.smarthome.ui.automation;

import androidx.core.content.ContextCompat;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSelectTemperatureWeatherBinding;
import com.ltech.smarthome.model.auto.TemperatureConditionParam;
import com.ltech.smarthome.ui.automation.ISelectCondition;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.view.MyTimePickerView;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActSelectTemperatureWeather extends BaseNormalActivity<ActSelectTemperatureWeatherBinding> implements ISelectCondition {
    private String city;
    private int symbolPosition = 0;
    private int temperature = 26;
    private double x;
    private double y;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_temperature_weather;
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
        setTitle(getString(R.string.temperature));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.finish));
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.less_than));
        arrayList.add(getString(R.string.equal));
        arrayList.add(getString(R.string.great_than));
        ((ActSelectTemperatureWeatherBinding) this.mViewBinding).pickViewSymbol.setData(arrayList);
        if (SceneHelper.instance().conditionParam != null) {
            TemperatureConditionParam temperatureConditionParam = (TemperatureConditionParam) SceneHelper.instance().conditionParam;
            this.x = temperatureConditionParam.x;
            this.y = temperatureConditionParam.y;
            this.city = temperatureConditionParam.city;
            this.symbolPosition = temperatureConditionParam.type - 1;
            this.temperature = temperatureConditionParam.temperature;
        }
        ArrayList arrayList2 = new ArrayList();
        int i = 0;
        for (int i2 = 0; i2 < 60; i2++) {
            String valueOf = i2 < 10 ? "0" + i2 : String.valueOf(i2);
            if (this.temperature == Integer.parseInt(valueOf)) {
                i = i2;
            }
            arrayList2.add(valueOf);
        }
        ((ActSelectTemperatureWeatherBinding) this.mViewBinding).pickViewTem.setData(arrayList2);
        ((ActSelectTemperatureWeatherBinding) this.mViewBinding).pickViewSymbol.setMainTextColor(ContextCompat.getColor(this, R.color.color_text_black));
        ((ActSelectTemperatureWeatherBinding) this.mViewBinding).pickViewTem.setMainTextColor(ContextCompat.getColor(this, R.color.color_text_black));
        ((ActSelectTemperatureWeatherBinding) this.mViewBinding).pickViewSymbol.setSelectedPosition(this.symbolPosition);
        ((ActSelectTemperatureWeatherBinding) this.mViewBinding).pickViewSymbol.setOnSelectListener(new MyTimePickerView.onSelectListener() { // from class: com.ltech.smarthome.ui.automation.ActSelectTemperatureWeather$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.MyTimePickerView.onSelectListener
            public final void onSelect(int i3, String str) {
                ActSelectTemperatureWeather.this.lambda$initView$0(i3, str);
            }
        });
        ((ActSelectTemperatureWeatherBinding) this.mViewBinding).pickViewTem.setSelectedPosition(i);
        ((ActSelectTemperatureWeatherBinding) this.mViewBinding).pickViewTem.setOnSelectListener(new MyTimePickerView.onSelectListener() { // from class: com.ltech.smarthome.ui.automation.ActSelectTemperatureWeather$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.MyTimePickerView.onSelectListener
            public final void onSelect(int i3, String str) {
                ActSelectTemperatureWeather.this.lambda$initView$1(i3, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(int i, String str) {
        this.symbolPosition = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(int i, String str) {
        this.temperature = Integer.parseInt(str);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        TemperatureConditionParam temperatureConditionParam = new TemperatureConditionParam();
        temperatureConditionParam.x = this.x;
        temperatureConditionParam.y = this.y;
        temperatureConditionParam.city = this.city;
        temperatureConditionParam.type = this.symbolPosition + 1;
        temperatureConditionParam.temperature = this.temperature;
        SceneHelper.instance().conditionParam = temperatureConditionParam;
        SceneHelper.instance().conditionParamType = 4;
        setSelectResult(this);
    }
}