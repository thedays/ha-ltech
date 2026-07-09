package com.ltech.smarthome.ui.device.microwave_sensor;

import androidx.appcompat.widget.AppCompatTextView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.BaseSingleSelectActivity;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NameRepository;
import com.smart.product_agreement.param.LightCmdParam;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class ActWaveSensorSelectSpKeyAction extends BaseSingleSelectActivity<String> implements ISelectAction {
    private int controlType;
    private String[] datas = new String[0];
    private boolean keyAction;

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

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity
    protected void save() {
        WaveSensorHelper.instance().setSensorRelateBleParam();
        if (WaveSensorHelper.instance().isChangeAll) {
            WaveSensorHelper.instance().setDataType = 2;
            WaveSensorHelper.instance().setSensorRelateBleParam();
            WaveSensorHelper.instance().setDataType = 3;
            selectSwitchClose(this.selectPosition);
            WaveSensorHelper.instance().setSensorRelateBleParam();
        }
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_action));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        this.keyAction = getIntent().getBooleanExtra(Constants.KEY_ACTION_TYPE, false);
        this.controlType = getIntent().getIntExtra(Constants.LIGHT_TYPE, 0);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        initData(this.controlType);
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<String> getList() {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name, item).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == this.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity
    protected void onItemClick(int position) {
        if (this.keyAction) {
            selectSwitchOn(position);
        } else {
            selectSwitchClose(position);
        }
    }

    private void initData(int controlType) {
        if (controlType != 18) {
            switch (controlType) {
                case 8:
                    this.datas = NameRepository.getSmartPanelS1KeyName(this);
                    break;
                case 9:
                    this.datas = NameRepository.getSmartPanelS2KeyName(this);
                    break;
                case 10:
                    this.datas = NameRepository.getSmartPanelS3KeyName(this);
                    break;
                case 11:
                    break;
                default:
                    this.datas = NameRepository.getSmartPanelS8KeyName(this);
                    break;
            }
            this.mAdapter.setNewData(Arrays.asList(this.datas));
            ((ActSelectBinding) this.mViewBinding).tvTip.setVisibility(8);
        }
        this.datas = NameRepository.getSmartPanelS4KeyName(this);
        this.mAdapter.setNewData(Arrays.asList(this.datas));
        ((ActSelectBinding) this.mViewBinding).tvTip.setVisibility(8);
    }

    public void selectSwitchClose(int position) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(1 << position);
        lightCmdParam.setCmdType(1);
        lightCmdParam.setOn(false);
        lightCmdParam.addExtParam(SceneHelper.OPTION, "1");
        lightCmdParam.addExtParam(SceneHelper.OPTION_VALUE, Integer.valueOf(position));
        WaveSensorHelper.instance().cmdParam = lightCmdParam;
    }

    public void selectSwitchOn(int position) {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(1 << position);
        lightCmdParam.setCmdType(1);
        lightCmdParam.setOn(true);
        lightCmdParam.addExtParam(SceneHelper.OPTION, "0");
        lightCmdParam.addExtParam(SceneHelper.OPTION_VALUE, Integer.valueOf(position));
        WaveSensorHelper.instance().cmdParam = lightCmdParam;
    }
}