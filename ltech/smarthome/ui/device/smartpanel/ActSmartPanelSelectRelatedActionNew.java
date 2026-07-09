package com.ltech.smarthome.ui.device.smartpanel;

import android.content.Intent;
import androidx.appcompat.widget.AppCompatTextView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.scene.ActSelectDefaultMode;
import com.ltech.smarthome.ui.scene.ActSelectDiyMode;
import com.ltech.smarthome.ui.scene.ActSelectThemeMode;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.param.LightCmdParam;
import com.smart.product_agreement.param.ModeCmdParam;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSmartPanelSelectRelatedActionNew extends BaseSmartPanelActionListActivity<ActSelectBinding, ActSmartPanelSelectActionVM> {
    private int lightType;
    private RelatedInfoExtParam.RelateInfo relateInfo;
    protected int selectPosition = -1;

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
        this.relateInfo.action = this.dataList.get(position).getKey();
        if (ProductRepository.isDaliLightGroup(((ActSmartPanelSelectActionVM) this.mViewModel).relateObject)) {
            this.relateInfo.type = 8;
            if (this.relateInfo.action == 19) {
                this.relateInfo.action = 34;
            } else if (this.relateInfo.action == 20) {
                this.relateInfo.action = 35;
            }
        }
        SceneHelper.instance().controlObject = ((ActSmartPanelSelectActionVM) this.mViewModel).relateObject;
        if (this.relateInfo.action == 16) {
            NavUtils.destination(ActSelectThemeMode.class).withInt(Constants.LIGHT_TYPE, this.lightType).withString(Constants.PRODUCT_ID, ((ActSmartPanelSelectActionVM) this.mViewModel).productId).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode().navigation(this);
            return;
        }
        if (this.relateInfo.action == 17) {
            NavUtils.destination(ActSelectDiyMode.class).withInt(Constants.LIGHT_TYPE, this.lightType).withString(Constants.PRODUCT_ID, ((ActSmartPanelSelectActionVM) this.mViewModel).productId).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode().navigation(this);
            return;
        }
        if (this.relateInfo.action == 18) {
            NavUtils.destination(ActSelectDefaultMode.class).withInt(Constants.LIGHT_TYPE, this.lightType).withString(Constants.PRODUCT_ID, ((ActSmartPanelSelectActionVM) this.mViewModel).productId).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode().navigation(this);
            return;
        }
        if (this.relateInfo.action == 19 || this.relateInfo.action == 20) {
            ((ActSmartPanelSelectActionVM) this.mViewModel).goSelection(this.lightType);
        } else if (this.relateInfo.action == 34 || this.relateInfo.action == 35) {
            this.lightType = ((ActSmartPanelSelectActionVM) this.mViewModel).selectBindValue.getLightType();
            ((ActSmartPanelSelectActionVM) this.mViewModel).goDaliSelection(this.lightType);
        } else {
            save();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActSmartPanelSelectActionVM) this.mViewModel).relatePosition = getIntent().getIntExtra(Constants.RELATED_POSITION, -1);
        ((ActSmartPanelSelectActionVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        ((ActSmartPanelSelectActionVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ((ActSmartPanelSelectActionVM) this.mViewModel).relateType = getIntent().getIntExtra(Constants.GROUP_RELATE, -1);
        if (((ActSmartPanelSelectActionVM) this.mViewModel).relateType == 2) {
            ((ActSmartPanelSelectActionVM) this.mViewModel).relateObject = Injection.repo().group().getGroupById(getIntent().getLongExtra(Constants.RELATE_ID, -1L));
            this.relateInfo = RelatedInfoExtParam.RelateInfo.RelatedGroupInfo(((Group) ((ActSmartPanelSelectActionVM) this.mViewModel).relateObject).getGroupId());
        } else if (((ActSmartPanelSelectActionVM) this.mViewModel).relateType == 1) {
            ((ActSmartPanelSelectActionVM) this.mViewModel).relateObject = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.RELATE_ID, -1L));
            this.relateInfo = RelatedInfoExtParam.RelateInfo.RelatedDeviceInfo(((Device) ((ActSmartPanelSelectActionVM) this.mViewModel).relateObject).getDeviceId());
        }
        ((ActSmartPanelSelectActionVM) this.mViewModel).initRelateAssistant(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
        if (ProductRepository.isDaliLightGroup(((ActSmartPanelSelectActionVM) this.mViewModel).relateObject)) {
            int convertDaliType = DaliProHelper.convertDaliType(((ActSmartPanelSelectActionVM) this.mViewModel).relateObject);
            this.lightType = convertDaliType;
            setDataList(ActSmartPanelSelectActionVM.getDaliActionValueList(convertDaliType, (Role) ((ActSmartPanelSelectActionVM) this.mViewModel).relateObject));
        } else {
            this.lightType = ProductRepository.getLightColorType(((ActSmartPanelSelectActionVM) this.mViewModel).relateObject);
            setDataList(ActSmartPanelSelectActionVM.getActionValueList(((ActSmartPanelSelectActionVM) this.mViewModel).relateObject, this.lightType));
        }
    }

    @Override // com.ltech.smarthome.ui.device.smartpanel.BaseSmartPanelActionListActivity
    protected void save() {
        ((ActSmartPanelSelectActionVM) this.mViewModel).relateInfoAssistant.setRelateInfo(((ActSmartPanelSelectActionVM) this.mViewModel).relatePosition, this.relateInfo);
        if (Injection.state().isConnectOuterNet()) {
            if (((ActSmartPanelSelectActionVM) this.mViewModel).groupControl) {
                showLoadingDialog(getString(R.string.subscribing));
                ((ActSmartPanelSelectActionVM) this.mViewModel).subscribe();
                return;
            } else if (((ActSmartPanelSelectActionVM) this.mViewModel).controlDevice.getProductId().equals(ProductId.ID_SMART_PANEL_S6B) && RelateInfoUtils.needShowTipDialog()) {
                RelateInfoUtils.showImageTipDialog(getString(R.string.s6b_click_tip), R.mipmap.pic_click_tip_s6b, this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectRelatedActionNew$$ExternalSyntheticLambda0
                    @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                    public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                        ActSmartPanelSelectRelatedActionNew.this.lambda$save$0(imageTipDialog);
                    }
                });
                return;
            } else {
                showLoadingDialog(getString(R.string.subscribing));
                ((ActSmartPanelSelectActionVM) this.mViewModel).subscribe();
                return;
            }
        }
        SmartToast.showShort(R.string.no_network);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$0(ImageTipDialog imageTipDialog) {
        showLoadingDialog(getString(R.string.subscribing));
        ((ActSmartPanelSelectActionVM) this.mViewModel).subscribe();
        imageTipDialog.dismissDialog();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            if (SceneHelper.instance().cmdParam instanceof ModeCmdParam) {
                this.relateInfo.keyActionExtra = ((ModeCmdParam) SceneHelper.instance().cmdParam).getPlayModeList().get(0).intValue();
                int i = 1 << this.relateInfo.keyActionExtra;
                this.relateInfo.wholeDataExtra = StringUtils.toHexString((i >> 8) & 255) + StringUtils.toHexString(i & 255);
            } else if (SceneHelper.instance().cmdParam instanceof LightCmdParam) {
                LightCmdParam lightCmdParam = (LightCmdParam) SceneHelper.instance().cmdParam;
                if (this.relateInfo.action == 34 || this.relateInfo.action == 35) {
                    this.relateInfo.wholeDataExtra = SceneHelper.instance().getConvertCmd(lightCmdParam).substring(4);
                } else {
                    this.relateInfo.wholeDataExtra = StringUtils.toHexString(this.lightType) + SceneHelper.instance().getConvertCmd(lightCmdParam).substring(2);
                }
            }
            saveOnActivityResult();
        }
    }

    private void saveOnActivityResult() {
        ((ActSmartPanelSelectActionVM) this.mViewModel).relateInfoAssistant.setRelateInfo(((ActSmartPanelSelectActionVM) this.mViewModel).relatePosition, this.relateInfo);
        if (Injection.state().isConnectOuterNet()) {
            showLoadingDialog(getString(R.string.subscribing));
            ((ActSmartPanelSelectActionVM) this.mViewModel).subscribe();
        } else {
            SmartToast.showShort(R.string.no_network);
        }
    }
}