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
import com.ltech.smarthome.net.response.rs8.Rs8CodeLibResponse;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LanguageUtils;
import com.smart.product_agreement.param.Rs485CmdParam;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectRs8CurtainAction extends BaseSingleSelectActivity<String> implements ISelectAction {
    private List<Rs8CodeLibResponse.CodeLib.Action> actions;
    Device device;
    private boolean isLocal;

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult, reason: merged with bridge method [inline-methods] */
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
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<String> getList() {
        this.device = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
        ArrayList arrayList = new ArrayList();
        Rs8CodeLibResponse.CodeLib codeLib = (Rs8CodeLibResponse.CodeLib) this.device.getExtParam(Rs8CodeLibResponse.CodeLib.class);
        if (codeLib != null) {
            this.actions = codeLib.getActionlist();
            for (Rs8CodeLibResponse.CodeLib.Action action : codeLib.getActionlist()) {
                arrayList.add(LanguageUtils.isChinese(this) ? action.getCname() : action.getEname());
            }
        }
        return arrayList;
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity
    protected void save() {
        if (this.isLocal) {
            SceneHelper.instance().maskType = MaskType.LOCAL;
            setResult(3001);
            finishActivity();
            return;
        }
        SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectRs8CurtainAction$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectRs8CurtainAction.this.lambda$save$0((Boolean) obj);
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
        Rs485CmdParam rs485CmdParam = new Rs485CmdParam();
        rs485CmdParam.setCmdType(4);
        rs485CmdParam.setRsData(this.actions.get(position).getInstruct());
        rs485CmdParam.setRsReplyData(this.actions.get(position).getReplyinstruct());
        rs485CmdParam.setDataFormat(1);
        rs485CmdParam.addExtParam(SceneHelper.OPTION, String.valueOf(position + 1));
        rs485CmdParam.addExtParam(SceneHelper.OPTION_VALUE, this.mAdapter.getData().get(position));
        SceneHelper.instance().cmdParam = rs485CmdParam;
    }
}