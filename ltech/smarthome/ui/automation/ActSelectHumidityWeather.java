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
import com.ltech.smarthome.model.auto.HumidityConditionParam;
import com.ltech.smarthome.ui.automation.ISelectCondition;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.SmartToast;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActSelectHumidityWeather extends BaseNormalActivity<ActSelectWeatherBinding> implements ISelectCondition {
    private String city;
    protected BaseQuickAdapter<HumidityConditionParam.HumidityState, BaseViewHolder> mAdapter;
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
        setTitle(getString(R.string.humidity));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.finish));
        ((ActSelectWeatherBinding) this.mViewBinding).tvTip.setText(getString(R.string.humidity_tip));
        ArrayList arrayList = new ArrayList();
        arrayList.add(HumidityConditionParam.HumidityState.dry(this));
        arrayList.add(HumidityConditionParam.HumidityState.comfortable(this));
        arrayList.add(HumidityConditionParam.HumidityState.damp(this));
        if (SceneHelper.instance().conditionParam != null) {
            HumidityConditionParam humidityConditionParam = (HumidityConditionParam) SceneHelper.instance().conditionParam;
            this.x = humidityConditionParam.x;
            this.y = humidityConditionParam.y;
            this.city = humidityConditionParam.city;
            int size = arrayList.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                if (((HumidityConditionParam.HumidityState) arrayList.get(i)).value == humidityConditionParam.type) {
                    this.selectPosition = i;
                    break;
                }
                i++;
            }
        }
        BaseQuickAdapter<HumidityConditionParam.HumidityState, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<HumidityConditionParam.HumidityState, BaseViewHolder>(R.layout.item_select, arrayList) { // from class: com.ltech.smarthome.ui.automation.ActSelectHumidityWeather.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, HumidityConditionParam.HumidityState item) {
                helper.setText(R.id.tv_name, item.name).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == ActSelectHumidityWeather.this.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
                ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.automation.ActSelectHumidityWeather$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                ActSelectHumidityWeather.this.lambda$initView$0(baseQuickAdapter2, view, i2);
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
        HumidityConditionParam humidityConditionParam = new HumidityConditionParam();
        humidityConditionParam.x = this.x;
        humidityConditionParam.y = this.y;
        humidityConditionParam.city = this.city;
        humidityConditionParam.type = this.mAdapter.getData().get(this.selectPosition).value;
        SceneHelper.instance().conditionParam = humidityConditionParam;
        SceneHelper.instance().conditionParamType = 5;
        setSelectResult(this);
    }
}