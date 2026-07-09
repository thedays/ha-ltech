package com.ltech.smarthome.ui.automation;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseListActivity;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.ui.automation.ISelectCondition;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.utils.NavUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectWeather extends BaseListActivity<GoItem> implements ISelectCondition {
    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_go_1;
    }

    @Override // com.ltech.smarthome.ui.automation.ISelectCondition
    public /* synthetic */ void setSelectResult(BaseNormalActivity baseNormalActivity) {
        ISelectCondition.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.ui.automation.ISelectCondition
    public /* synthetic */ void setSelectStatusResult(BaseNormalActivity baseNormalActivity) {
        ISelectCondition.CC.$default$setSelectStatusResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.weather));
        setBackImage(R.mipmap.icon_back);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.automation.ActSelectWeather$$ExternalSyntheticLambda4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSelectWeather.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        ((GoItem) this.mAdapter.getData().get(i)).getAction().execute();
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<GoItem> getList() {
        ArrayList arrayList = new ArrayList(4);
        arrayList.add(new GoItem().setMainText(getString(R.string.sunrise_sunset)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.automation.ActSelectWeather$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectWeather.this.lambda$getList$1();
            }
        })));
        arrayList.add(new GoItem().setMainText(getString(R.string.temperature)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.automation.ActSelectWeather$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectWeather.this.lambda$getList$2();
            }
        })));
        arrayList.add(new GoItem().setMainText(getString(R.string.humidity)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.automation.ActSelectWeather$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectWeather.this.lambda$getList$3();
            }
        })));
        arrayList.add(new GoItem().setMainText(getString(R.string.pm_25)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.automation.ActSelectWeather$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectWeather.this.lambda$getList$4();
            }
        })));
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getList$1() {
        NavUtils.destination(ActSelectSunWeather.class).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getList$2() {
        NavUtils.destination(ActSelectTemperatureWeather.class).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getList$3() {
        NavUtils.destination(ActSelectHumidityWeather.class).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getList$4() {
        NavUtils.destination(ActSelectPmWeather.class).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, GoItem item) {
        helper.setText(R.id.tv_main, item.getMainText());
        ((AppCompatTextView) helper.getView(R.id.tv_main)).getPaint().setFakeBoldText(true);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3003) {
            setSelectResult(this);
        }
    }
}