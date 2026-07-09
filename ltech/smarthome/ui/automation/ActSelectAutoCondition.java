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
import com.ltech.smarthome.ui.scene.ActSelectDeviceFoStateCondition;
import com.ltech.smarthome.ui.scene.ActSelectDeviceForCondition;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectAutoCondition extends BaseListActivity<GoItem> implements ISelectCondition {
    private boolean isStateCondition;
    private long placeId;

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_go;
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
        setTitle(getString(R.string.choose_condition));
        setBackImage(R.mipmap.icon_back);
        this.placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.automation.ActSelectAutoCondition$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSelectAutoCondition.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        ((GoItem) this.mAdapter.getData().get(i)).getAction().execute();
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<GoItem> getList() {
        this.isStateCondition = getIntent().getBooleanExtra(Constants.STATE_CONDITION, false);
        ArrayList arrayList = new ArrayList(4);
        if (this.isStateCondition) {
            arrayList.add(new GoItem().setMainText(getString(R.string.status_condition_1)).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.automation.ActSelectAutoCondition$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActSelectAutoCondition.this.lambda$getList$1();
                }
            })));
            arrayList.add(new GoItem().setMainText(getString(R.string.status_condition_2)).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.automation.ActSelectAutoCondition$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActSelectAutoCondition.this.lambda$getList$2();
                }
            })));
            return arrayList;
        }
        if (!getIntent().getBooleanExtra(Constants.IS_LOCAL_AUTOMATION, false)) {
            arrayList.add(new GoItem().setMainText(getString(R.string.condition_address_tip)).setImageRes(R.mipmap.ic_auto_address).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.automation.ActSelectAutoCondition$$ExternalSyntheticLambda3
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActSelectAutoCondition.this.lambda$getList$3();
                }
            })));
        }
        arrayList.add(new GoItem().setMainText(getString(R.string.condition_timing_tip)).setImageRes(R.mipmap.ic_auto_timing).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.automation.ActSelectAutoCondition$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectAutoCondition.this.lambda$getList$4();
            }
        })));
        arrayList.add(new GoItem().setMainText(getString(R.string.sunrise_sunset)).setImageRes(R.mipmap.ic_auto_weather).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.automation.ActSelectAutoCondition$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectAutoCondition.this.lambda$getList$5();
            }
        })));
        arrayList.add(new GoItem().setMainText(getString(R.string.condition_device_tip)).setImageRes(R.mipmap.ic_auto_device).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.automation.ActSelectAutoCondition$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectAutoCondition.this.lambda$getList$6();
            }
        })));
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getList$1() {
        NavUtils.destination(ActSelectEnvStatusCondition.class).withLong(Constants.PLACE_ID, this.placeId).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getList$2() {
        NavUtils.destination(ActSelectDeviceFoStateCondition.class).withLong(Constants.PLACE_ID, this.placeId).withBoolean(Constants.CHOOSE_DEVICE, true).withBoolean(Constants.STATE_CONDITION, true).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getList$3() {
        NavUtils.destination(ActSelectAddress.class).withLong(Constants.PLACE_ID, this.placeId).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getList$4() {
        NavUtils.destination(ActSelectTime.class).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getList$5() {
        NavUtils.destination(ActSelectSunWeather.class).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getList$6() {
        NavUtils.destination(ActSelectDeviceForCondition.class).withLong(Constants.PLACE_ID, this.placeId).withBoolean(Constants.CHOOSE_DEVICE, true).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, GoItem item) {
        helper.setGone(R.id.iv_icon, false).setText(R.id.tv_main, item.getMainText()).setImageResource(R.id.iv_go, item.getGoRes());
        ((AppCompatTextView) helper.getView(R.id.tv_main)).getPaint().setFakeBoldText(true);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3003) {
            setSelectResult(this);
        } else if (resultCode == 5013) {
            setSelectStatusResult(this);
        }
    }
}