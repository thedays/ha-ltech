package com.ltech.smarthome.ui.scene;

import androidx.appcompat.widget.AppCompatTextView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.BaseSingleSelectActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NameRepository;
import com.smart.product_agreement.param.TrigCmdParam;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectTrigAction extends BaseSingleSelectActivity<String> implements ISelectAction {
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
        if (getIntent().getIntExtra(Constants.SUB_TYPE, 2) == 2) {
            return Arrays.asList(NameRepository.getTrigScene8ActionName(this));
        }
        return Arrays.asList(NameRepository.getTrigSceneActionName(this));
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity
    protected void save() {
        if (this.isLocal) {
            SceneHelper.instance().maskType = MaskType.LOCAL;
            setResult(3001);
            finishActivity();
            return;
        }
        SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectTrigAction$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectTrigAction.this.lambda$save$0((Boolean) obj);
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
        setSelectScene(position);
    }

    private void setSelectScene(int position) {
        TrigCmdParam trigCmdParam = new TrigCmdParam();
        trigCmdParam.setCmdType(1);
        int i = position + 1;
        trigCmdParam.setOpenCloseVar(i);
        trigCmdParam.addExtParam(SceneHelper.OPTION, String.valueOf(i));
        trigCmdParam.addExtParam(SceneHelper.OPTION_VALUE, this.mAdapter.getData().get(position));
        SceneHelper.instance().cmdParam = trigCmdParam;
    }
}