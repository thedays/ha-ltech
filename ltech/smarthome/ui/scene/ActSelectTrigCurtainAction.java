package com.ltech.smarthome.ui.scene;

import androidx.appcompat.widget.AppCompatTextView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.BaseSingleSelectActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.ui.device.trig.TrigRepository;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NameRepository;
import com.smart.product_agreement.param.TrigCmdParam;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectTrigCurtainAction extends BaseSingleSelectActivity<String> implements ISelectAction {
    Device device;
    private boolean isLocal;
    private int subType;

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$save$0(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.confirm));
        setTitle(getString(R.string.choose_action));
        this.isLocal = getIntent().getBooleanExtra(Constants.IS_LOCAL_SCENE, false);
        this.device = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<String> getList() {
        int intExtra = getIntent().getIntExtra(Constants.SUB_TYPE, 0);
        this.subType = intExtra;
        if (intExtra == 0) {
            return Arrays.asList(NameRepository.getTrigCurtainActionName(this));
        }
        return Arrays.asList(NameRepository.getTrigDreamCurtainActionName(this));
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity
    protected void save() {
        if (this.isLocal) {
            SceneHelper.instance().maskType = MaskType.LOCAL;
            setResult(3001);
            finishActivity();
            return;
        }
        SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectTrigCurtainAction$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectTrigCurtainAction.this.lambda$save$0((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name, item).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == this.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity
    protected void onItemClick(int position) {
        super.onItemClick(position);
        setParamOption(position);
    }

    private void setParamOption(int position) {
        TrigCmdParam trigCmdParam = new TrigCmdParam();
        trigCmdParam.setCmdType(1);
        if (this.subType == 0) {
            trigCmdParam.setOpenCloseVar(TrigRepository.getDefaultCurtainItemList().get(position).spanCount);
        } else {
            trigCmdParam.setOpenCloseVar(TrigRepository.getDefaultDreamCurtainItemList().get(position).spanCount);
        }
        trigCmdParam.addExtParam(SceneHelper.OPTION, String.valueOf(position + 1));
        trigCmdParam.addExtParam(SceneHelper.OPTION_VALUE, this.mAdapter.getData().get(position));
        SceneHelper.instance().cmdParam = trigCmdParam;
    }
}