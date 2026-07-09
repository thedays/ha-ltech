package com.ltech.smarthome.ui.device.aspanel;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseSingleSelectActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NameRepository;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSavePanelScene extends BaseSingleSelectActivity<String> {
    private long controlId;
    private Object controlObject;

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select;
    }

    @Override // com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select;
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        setTitle(getString(R.string.select_scene));
        ((ActSelectBinding) this.mViewBinding).tvTip.setText(getString(R.string.save_scene_tip));
        ((ActSelectBinding) this.mViewBinding).tvTip.setVisibility(0);
        this.controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        super.initView();
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity
    protected void save() {
        saveScene();
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<String> getList() {
        return Arrays.asList(NameRepository.getAsPanelSceneName(this));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name, item).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == this.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        this.controlObject = device;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        Injection.repo().device().getDeviceFromDb(this.controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.aspanel.ActSavePanelScene$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSavePanelScene.this.lambda$startObserve$0((Device) obj);
            }
        });
    }

    private void saveScene() {
        showLoadingDialog(getString(R.string.subscribing));
        CmdAssistant.getSceneCmdAssistant(this.controlObject, 65535).saveScene(this, this.selectPosition + 1, new IAction() { // from class: com.ltech.smarthome.ui.device.aspanel.ActSavePanelScene$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSavePanelScene.this.lambda$saveScene$1((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveScene$1(Boolean bool) {
        if (bool.booleanValue()) {
            finishActivity();
        }
    }
}