package com.ltech.smarthome.ui.automation;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSelectWeatherBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.auto.SunConditionParam;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.ui.automation.ISelectCondition;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.SmartToast;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActSelectSunWeather extends BaseNormalActivity<ActSelectWeatherBinding> implements ISelectCondition {
    protected BaseQuickAdapter<SunConditionParam.SunState, BaseViewHolder> mAdapter;
    private double x;
    private double y;
    protected int selectPosition = -1;
    private int lastSelectPosition = -1;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_weather;
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
        setTitle(getString(R.string.sunrise_sunset));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.finish));
        ArrayList arrayList = new ArrayList();
        arrayList.add(SunConditionParam.SunState.sunrise(this));
        arrayList.add(SunConditionParam.SunState.sunset(this));
        if (SceneHelper.instance().conditionParam != null) {
            SunConditionParam sunConditionParam = (SunConditionParam) SceneHelper.instance().conditionParam;
            this.x = sunConditionParam.x;
            this.y = sunConditionParam.y;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (((SunConditionParam.SunState) arrayList.get(i)).value == sunConditionParam.type) {
                    this.selectPosition = i;
                }
            }
        }
        BaseQuickAdapter<SunConditionParam.SunState, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<SunConditionParam.SunState, BaseViewHolder>(R.layout.item_select, arrayList) { // from class: com.ltech.smarthome.ui.automation.ActSelectSunWeather.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, SunConditionParam.SunState item) {
                helper.setText(R.id.tv_name, item.name).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == ActSelectSunWeather.this.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
                ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.automation.ActSelectSunWeather$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                ActSelectSunWeather.this.lambda$initView$0(baseQuickAdapter2, view, i2);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActSelectWeatherBinding) this.mViewBinding).rvContent);
        ((ActSelectWeatherBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectWeatherBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActSelectWeatherBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int i2 = this.selectPosition;
        if (i2 != i) {
            this.lastSelectPosition = i2;
            this.selectPosition = i;
            if (i != -1) {
                baseQuickAdapter.notifyItemChanged(i);
            }
            int i3 = this.lastSelectPosition;
            if (i3 != -1) {
                baseQuickAdapter.notifyItemChanged(i3);
            }
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (this.selectPosition == -1) {
            SmartToast.showShort(R.string.please_choose);
            return;
        }
        SunConditionParam sunConditionParam = new SunConditionParam();
        sunConditionParam.type = this.mAdapter.getData().get(this.selectPosition).value;
        Place value = Injection.repo().home().getSelectPlace().getValue();
        if (value != null) {
            sunConditionParam.x = value.getLongitude();
            sunConditionParam.y = value.getLatitude();
        }
        SceneHelper.instance().conditionParam = sunConditionParam;
        SceneHelper.instance().conditionParamType = 3;
        setSelectResult(this);
    }
}