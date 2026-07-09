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
import com.ltech.smarthome.model.auto.Pm25ConditionParam;
import com.ltech.smarthome.ui.automation.ISelectCondition;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.SmartToast;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActSelectPmWeather extends BaseNormalActivity<ActSelectWeatherBinding> implements ISelectCondition {
    private String city;
    protected BaseQuickAdapter<Pm25ConditionParam.PmState, BaseViewHolder> mAdapter;
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
        setTitle(getString(R.string.pm_25));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.finish));
        ArrayList arrayList = new ArrayList();
        arrayList.add(Pm25ConditionParam.PmState.excellent(this));
        arrayList.add(Pm25ConditionParam.PmState.good(this));
        arrayList.add(Pm25ConditionParam.PmState.contaminated(this));
        if (SceneHelper.instance().conditionParam != null) {
            Pm25ConditionParam pm25ConditionParam = (Pm25ConditionParam) SceneHelper.instance().conditionParam;
            this.x = pm25ConditionParam.x;
            this.y = pm25ConditionParam.y;
            this.city = pm25ConditionParam.city;
            int size = arrayList.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                if (((Pm25ConditionParam.PmState) arrayList.get(i)).value == pm25ConditionParam.type) {
                    this.selectPosition = i;
                    break;
                }
                i++;
            }
        }
        BaseQuickAdapter<Pm25ConditionParam.PmState, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Pm25ConditionParam.PmState, BaseViewHolder>(R.layout.item_select, arrayList) { // from class: com.ltech.smarthome.ui.automation.ActSelectPmWeather.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Pm25ConditionParam.PmState item) {
                helper.setText(R.id.tv_name, item.name).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == ActSelectPmWeather.this.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
                ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.automation.ActSelectPmWeather$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                ActSelectPmWeather.this.lambda$initView$0(baseQuickAdapter2, view, i2);
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
        Pm25ConditionParam pm25ConditionParam = new Pm25ConditionParam();
        pm25ConditionParam.x = this.x;
        pm25ConditionParam.y = this.y;
        pm25ConditionParam.city = this.city;
        pm25ConditionParam.type = this.mAdapter.getData().get(this.selectPosition).value;
        SceneHelper.instance().conditionParam = pm25ConditionParam;
        SceneHelper.instance().conditionParamType = 7;
        setSelectResult(this);
    }
}