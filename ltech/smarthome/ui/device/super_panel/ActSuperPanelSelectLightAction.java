package com.ltech.smarthome.ui.device.super_panel;

import android.content.Intent;
import androidx.appcompat.widget.AppCompatTextView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.SuperPanelInfo;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectActionVM;
import com.ltech.smarthome.ui.device.smartpanel.BaseSmartPanelActionListActivity;
import com.ltech.smarthome.ui.device.smartpanel.BindValue;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.ui.device.spicontroller.ActSelectSpiForAction;
import com.ltech.smarthome.ui.scene.ActSelectDefaultMode;
import com.ltech.smarthome.ui.scene.ActSelectDiyMode;
import com.ltech.smarthome.ui.scene.ActSelectThemeMode;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.smart.message.base.BaseCmdParam;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.param.LightCmdParam;
import com.smart.product_agreement.param.ModeCmdParam;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSuperPanelSelectLightAction extends BaseSmartPanelActionListActivity<ActSelectBinding, ActSmartPanelSelectActionVM> {
    private int lightType;
    protected int selectPosition = -1;
    private SuperPanelInfo.PanelKeyLight panelKeyLight = new SuperPanelInfo.PanelKeyLight();

    @Override // com.ltech.smarthome.ui.device.smartpanel.BaseSmartPanelActionListActivity
    protected int itemLayout() {
        return R.layout.item_select;
    }

    @Override // com.ltech.smarthome.ui.device.smartpanel.BaseSmartPanelActionListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_action));
        setBackImage(R.mipmap.icon_back);
        ((ActSelectBinding) this.mViewBinding).tvTip.setText(getString(R.string.key_bind_tip));
        ((ActSelectBinding) this.mViewBinding).tvTip.setVisibility(0);
    }

    @Override // com.ltech.smarthome.ui.device.smartpanel.BaseSmartPanelActionListActivity
    protected List<BindValue> getList() {
        return new ArrayList();
    }

    @Override // com.ltech.smarthome.ui.device.smartpanel.BaseSmartPanelActionListActivity
    protected void convertView(BaseViewHolder helper, BindValue item) {
        helper.setText(R.id.tv_name, item.getNameRes());
        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.ui.device.smartpanel.BaseSmartPanelActionListActivity
    protected void onItemClick(int position) {
        if (this.selectPosition != position) {
            this.selectPosition = position;
            this.mAdapter.notifyDataSetChanged();
        }
        ((ActSmartPanelSelectActionVM) this.mViewModel).selectBindValue = this.dataList.get(position);
        this.panelKeyLight.actioncode = this.dataList.get(position).getKey();
        if (ProductRepository.isDaliLightGroup(SceneHelper.instance().controlObject)) {
            if (this.panelKeyLight.actioncode == 19) {
                this.panelKeyLight.actioncode = 34;
            } else if (this.panelKeyLight.actioncode == 20) {
                this.panelKeyLight.actioncode = 35;
            }
        }
        if (this.panelKeyLight.actioncode == 16) {
            NavUtils.destination(ActSelectThemeMode.class).withInt(Constants.LIGHT_TYPE, this.lightType).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode().navigation(this);
            return;
        }
        if (this.panelKeyLight.actioncode == 17) {
            NavUtils.destination(ActSelectDiyMode.class).withInt(Constants.LIGHT_TYPE, this.lightType).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode().navigation(this);
            return;
        }
        if (this.panelKeyLight.actioncode == 18) {
            NavUtils.destination(ActSelectDefaultMode.class).withInt(Constants.LIGHT_TYPE, this.lightType).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode().navigation(this);
            return;
        }
        if (this.panelKeyLight.actioncode == 256) {
            NavUtils.destination(ActSelectSpiForAction.class).withLong(Constants.CONTROL_ID, ProductRepository.getControlId(SceneHelper.instance().controlObject)).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode().navigation(this);
            return;
        }
        if (this.panelKeyLight.actioncode == 19 || this.panelKeyLight.actioncode == 20) {
            ((ActSmartPanelSelectActionVM) this.mViewModel).goSelection(this.lightType);
            return;
        }
        if (this.panelKeyLight.actioncode == 34 || this.panelKeyLight.actioncode == 35) {
            this.lightType = ((ActSmartPanelSelectActionVM) this.mViewModel).selectBindValue.getLightType();
            ((ActSmartPanelSelectActionVM) this.mViewModel).goDaliSelection(this.lightType);
        } else {
            SuperPanelInfo.PanelKeyLight panelKeyLight = this.panelKeyLight;
            panelKeyLight.wholeDataExtra = StringUtils.toHexString(panelKeyLight.actioncode);
            save();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        int intExtra = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
        this.lightType = intExtra;
        if (intExtra == 20) {
            this.lightType = 5;
        }
        if (ProductRepository.isDaliLightGroup(SceneHelper.instance().controlObject)) {
            int convertDaliType = DaliProHelper.convertDaliType(SceneHelper.instance().controlObject);
            this.lightType = convertDaliType;
            setDataList(ActSmartPanelSelectActionVM.getDaliActionValueList(convertDaliType, (Role) SceneHelper.instance().controlObject));
            return;
        }
        setDataList(ActSmartPanelSelectActionVM.getActionValueList(SceneHelper.instance().controlObject, this.lightType));
    }

    @Override // com.ltech.smarthome.ui.device.smartpanel.BaseSmartPanelActionListActivity
    protected void save() {
        if (this.panelKeyLight.actioncode == 19 || this.panelKeyLight.actioncode == 20) {
            SceneHelper.instance().cmdParam.addExtParam(SceneHelper.OPTION, "8");
            SceneHelper.instance().cmdParam.addExtParam(SceneHelper.OPTION_VALUE, RelateInfoUtils.getLightStaticInfo(this.lightType, this.panelKeyLight.actioncode, this.panelKeyLight.wholeDataExtra, SceneHelper.instance().controlObject));
        } else if (this.panelKeyLight.actioncode == 34 || this.panelKeyLight.actioncode == 35) {
            SceneHelper.instance().cmdParam.addExtParam(SceneHelper.OPTION, "8");
            SceneHelper.instance().cmdParam.addExtParam(SceneHelper.OPTION_VALUE, RelateInfoUtils.getLightStaticInfo(Integer.parseInt(this.panelKeyLight.wholeDataExtra.substring(0, 2), 16), this.panelKeyLight.actioncode, this.panelKeyLight.wholeDataExtra, SceneHelper.instance().controlObject));
        } else if (this.panelKeyLight.actioncode != 16 && this.panelKeyLight.actioncode != 17 && this.panelKeyLight.actioncode != 18) {
            SceneHelper.instance().cmdParam = new BaseCmdParam();
            SceneHelper.instance().cmdParam.addExtParam(SceneHelper.OPTION, "8");
            SceneHelper.instance().cmdParam.addExtParam(SceneHelper.OPTION_VALUE, getString(this.mAdapter.getData().get(this.selectPosition).getNameRes()));
        }
        this.panelKeyLight.address = getIntent().getIntExtra(Constants.ADDRESS, -1);
        if (ProductRepository.isDaliLightGroup(SceneHelper.instance().controlObject)) {
            this.panelKeyLight.devicetype = ((ActSmartPanelSelectActionVM) this.mViewModel).getRelationLightType(SceneHelper.instance().controlObject) + 100;
        } else {
            this.panelKeyLight.devicetype = this.lightType;
        }
        if (ProductRepository.isDaliLightGroup(SceneHelper.instance().controlObject)) {
            this.panelKeyLight.zone = DaliProHelper.getZoneNum(SceneHelper.instance().controlObject);
        } else if (ProductRepository.isRelaySeparationSub(SceneHelper.instance().controlObject)) {
            this.panelKeyLight.zone = RelaySeparationHelper.getZoneNum(SceneHelper.instance().controlObject);
        }
        SceneHelper.instance().panelKeyLight = this.panelKeyLight;
        SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSelectLightAction$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSuperPanelSelectLightAction.this.lambda$save$0((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$0(Boolean bool) {
        setResult(3001);
        finishActivity();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            if (SceneHelper.instance().cmdParam instanceof ModeCmdParam) {
                ModeCmdParam modeCmdParam = (ModeCmdParam) SceneHelper.instance().cmdParam;
                if (modeCmdParam.getCmdType() == 16) {
                    SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSelectLightAction$$ExternalSyntheticLambda0
                        @Override // com.ltech.smarthome.base.IAction
                        public final void act(Object obj) {
                            ActSuperPanelSelectLightAction.this.lambda$onActivityResult$1((Boolean) obj);
                        }
                    });
                    return;
                }
                int intValue = 1 << modeCmdParam.getPlayModeList().get(0).intValue();
                this.panelKeyLight.wholeDataExtra = StringUtils.toHexString((intValue >> 8) & 255) + StringUtils.toHexString(intValue & 255);
            } else if (SceneHelper.instance().cmdParam instanceof LightCmdParam) {
                LightCmdParam lightCmdParam = (LightCmdParam) SceneHelper.instance().cmdParam;
                if (this.panelKeyLight.actioncode == 34 || this.panelKeyLight.actioncode == 35) {
                    this.panelKeyLight.wholeDataExtra = SceneHelper.instance().getConvertCmd(lightCmdParam).substring(4);
                } else {
                    this.panelKeyLight.wholeDataExtra = StringUtils.toHexString(this.lightType) + SceneHelper.instance().getConvertCmd(lightCmdParam).substring(2);
                }
            }
            save();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onActivityResult$1(Boolean bool) {
        setResult(3001);
        finishActivity();
    }
}