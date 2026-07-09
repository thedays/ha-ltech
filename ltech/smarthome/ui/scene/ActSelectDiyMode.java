package com.ltech.smarthome.ui.scene;

import android.content.Intent;
import androidx.appcompat.widget.AppCompatTextView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.BaseSingleSelectActivity;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.ModeContent;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.singleton.ComboCmdHelper;
import com.ltech.smarthome.ui.device.aspanel.AsHelper;
import com.ltech.smarthome.ui.device.e6knob.E6Helper;
import com.ltech.smarthome.ui.device.eurpanel.EurHelper;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NameRepository;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.ModeAssistant;
import com.smart.product_agreement.param.ModeCmdParam;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectDiyMode extends BaseSingleSelectActivity<String> implements ISelectAction {
    private int lightType;
    private long deviceId = -1;
    private List<String> modeNameList = new ArrayList();

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
    public /* synthetic */ void lambda$initActionView$3(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.confirm));
        setTitle(getString(R.string.diy_mode));
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
        this.lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
        if (getIntent().getBooleanExtra(Constants.IS_E6, false) && !ProductId.ID_KNOB_PANEL_E6M.equals(getIntent().getStringExtra(Constants.PRODUCT_ID))) {
            ((ActSelectBinding) this.mViewBinding).tvTip.setText(getString(R.string.e6_mode_tip));
            ((ActSelectBinding) this.mViewBinding).tvTip.setVisibility(0);
        }
        updateModeList();
        super.initView();
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<String> getList() {
        return this.modeNameList;
    }

    private void updateModeList() {
        this.modeNameList = getDefaultList();
        Iterator<ModeContent> it = Injection.repo().mode().getModeListFromDb(this.lightType, 2).iterator();
        while (it.hasNext()) {
            this.modeNameList.set(r1.getModeIndex() - 1, it.next().getModeName());
        }
    }

    private List<String> getDefaultList() {
        int i = this.lightType;
        if (i == 1) {
            return Arrays.asList(NameRepository.getAdvancedDimModeName(this));
        }
        if (i == 2) {
            return Arrays.asList(NameRepository.getAdvancedCtModeName(this));
        }
        return Arrays.asList(NameRepository.getAdvancedModeName(this));
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity
    protected void save() {
        if (getIntent().getBooleanExtra(Constants.IS_GQ, false)) {
            lambda$initActionView$3(this);
            ComboCmdHelper.getInstance().selectAdvanceMode(this.selectPosition);
            return;
        }
        if (getIntent().getBooleanExtra(Constants.IS_E6, false)) {
            E6Helper.instance().selectAdvanceMode(this.selectPosition);
            return;
        }
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setZoneNum(1);
        modeCmdParam.setCmdType(14);
        modeCmdParam.setPlayModeList(Collections.singletonList(Integer.valueOf(this.selectPosition)));
        modeCmdParam.setPlayType(1);
        modeCmdParam.setPlayTimes(0);
        modeCmdParam.setListPlay(false);
        int i = this.lightType;
        if (i == 1) {
            modeCmdParam.addExtParam(SceneHelper.OPTION, "5");
        } else if (i == 2) {
            modeCmdParam.addExtParam(SceneHelper.OPTION, "6");
        } else {
            modeCmdParam.addExtParam(SceneHelper.OPTION, "3");
        }
        modeCmdParam.addExtParam(SceneHelper.OPTION_VALUE, this.modeNameList.get(this.selectPosition));
        SceneHelper.instance().cmdParam = modeCmdParam;
        boolean booleanExtra = getIntent().getBooleanExtra(Constants.IS_LOCAL_SCENE, false);
        if (SceneHelper.instance().isMultiZone) {
            SceneHelper.instance().goSelectZone(this, booleanExtra);
        } else {
            saveAction(this, booleanExtra);
        }
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
        getModeCmdHelper().previewDefaultAdvancedMode(this, position);
    }

    public ModeAssistant getModeCmdHelper() {
        if (AsHelper.isNewUb(SceneHelper.instance().controlObject) || EurHelper.isEb125(SceneHelper.instance().controlObject)) {
            return CmdAssistant.getModeCmdAssistant(SceneHelper.instance().controlObject, 15);
        }
        if (getIntent().getBooleanExtra(Constants.IS_E6, false)) {
            return E6Helper.instance().getModeCmdHelper();
        }
        return CmdAssistant.getModeCmdAssistant(SceneHelper.instance().controlObject, new int[0]);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            lambda$initActionView$3(this);
        }
    }
}