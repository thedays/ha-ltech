package com.ltech.smarthome.ui.circadianlighting;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseSingleSelectActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectDeviceLightPlan extends BaseSingleSelectActivity<String> {
    private Device device;

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select;
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.select_light_plan));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.import_mode));
        this.device = Injection.repo().device().getDeviceByDeviceId(getIntent().getLongExtra("device_id", 1L));
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected void setUpData() {
        this.selectPosition = 0;
        super.setUpData();
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity
    protected void save() {
        final int i = this.selectPosition + 1;
        MessageDialog.show(this, getString(R.string.import_circadian_lighting_title), getString(R.string.import_circadian_lighting_tip)).setOkButton(getString(R.string.import_mode), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.circadianlighting.ActSelectDeviceLightPlan$$ExternalSyntheticLambda0
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$save$1;
                lambda$save$1 = ActSelectDeviceLightPlan.this.lambda$save$1(i, baseDialog, view);
                return lambda$save$1;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$save$1(int i, BaseDialog baseDialog, View view) {
        showLoadingDialog("");
        CmdAssistant.getLightRhythmsCmdAssistant(this.device, new int[0]).queryRhythmsMode(this, i, new IAction() { // from class: com.ltech.smarthome.ui.circadianlighting.ActSelectDeviceLightPlan$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectDeviceLightPlan.this.lambda$save$0((ResponseMsg) obj);
            }
        });
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$0(ResponseMsg responseMsg) {
        if (responseMsg == null) {
            showErrorDialog(getString(R.string.import_fail));
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(Constants.MODE_DATA, responseMsg.getResData().substring(20));
        setResult(6002, intent);
        SmartToast.showShort(R.string.import_success);
        finishActivity();
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<String> getList() {
        return Arrays.asList(getResources().getStringArray(R.array.circadian_light_array));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name, item).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == this.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
    }
}