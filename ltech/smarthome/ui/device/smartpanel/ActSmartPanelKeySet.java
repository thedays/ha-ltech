package com.ltech.smarthome.ui.device.smartpanel;

import android.content.Intent;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSmartPanelKeySetBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.ui.automation.ActAddAutomation;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActSmartPanelKeySet extends VMActivity<ActSmartPanelKeySetBinding, ActSmartPanelKeySetVM> implements ISelectAction {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_smart_panel_key_set;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$edit$1(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.key_set));
        setBackImage(R.mipmap.icon_back);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActSmartPanelKeySetVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActSmartPanelKeySetVM) this.mViewModel).position = getIntent().getIntExtra(Constants.KEY_POSITION, 0);
        ((ActSmartPanelKeySetVM) this.mViewModel).controlType = getIntent().getIntExtra(Constants.LIGHT_TYPE, 0);
        ((ActSmartPanelKeySetVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ((ActSmartPanelKeySetVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        ((ActSmartPanelKeySetVM) this.mViewModel).keyName.setValue(getIntent().getStringExtra(Constants.KEY_NAME));
        if (((ActSmartPanelKeySetVM) this.mViewModel).groupControl) {
            Injection.repo().group().getGroupFromDb(((ActSmartPanelKeySetVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelKeySet$$ExternalSyntheticLambda2
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActSmartPanelKeySet.this.lambda$startObserve$0((Group) obj);
                }
            });
        } else {
            Injection.repo().device().getDeviceFromDb(((ActSmartPanelKeySetVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelKeySet$$ExternalSyntheticLambda3
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActSmartPanelKeySet.this.lambda$startObserve$1((Device) obj);
                }
            });
        }
        ((ActSmartPanelKeySetVM) this.mViewModel).showEditNameDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelKeySet$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSmartPanelKeySet.this.lambda$startObserve$2((Void) obj);
            }
        });
        ((ActSmartPanelKeySetVM) this.mViewModel).showBindDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelKeySet$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSmartPanelKeySet.this.lambda$startObserve$3((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Group group) {
        ((ActSmartPanelKeySetVM) this.mViewModel).controlObject.setValue(group);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        ((ActSmartPanelKeySetVM) this.mViewModel).controlObject.setValue(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r1) {
        showEditNameDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r1) {
        showBindDialog();
    }

    private void showBindDialog() {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.device));
        arrayList.add(getString(R.string.local_scene));
        if (!((ActSmartPanelKeySetVM) this.mViewModel).groupControl) {
            arrayList.add(getString(R.string.link_automation));
        }
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelKeySet$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelKeySet.this.lambda$showBindDialog$4((Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showBindDialog$4(Integer num) {
        int intValue = num.intValue();
        if (intValue == 0) {
            NavUtils.destination(ActSmartPanelSelectBleDeviceAndGroup.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActSmartPanelKeySetVM) this.mViewModel).controlId).withInt(Constants.RELATED_POSITION, ((ActSmartPanelKeySetVM) this.mViewModel).position).withString(Constants.PRODUCT_ID, ((ActSmartPanelKeySetVM) this.mViewModel).productId).withBoolean(Constants.GROUP_CONTROL, ((ActSmartPanelKeySetVM) this.mViewModel).groupControl).withInt(Constants.GROUP_RELATE, 1).withInt(Constants.LIGHT_TYPE, ((ActSmartPanelKeySetVM) this.mViewModel).controlType).withDefaultRequestCode().navigation(this);
        } else if (intValue == 1) {
            NavUtils.destination(ActSmartPanelSelectScene.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActSmartPanelKeySetVM) this.mViewModel).controlId).withInt(Constants.RELATED_POSITION, ((ActSmartPanelKeySetVM) this.mViewModel).position).withBoolean(Constants.GROUP_CONTROL, ((ActSmartPanelKeySetVM) this.mViewModel).groupControl).withDefaultRequestCode().navigation(this);
        } else {
            if (intValue != 2) {
                return;
            }
            NavUtils.destination(ActAddAutomation.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withInt(Constants.RELATED_POSITION, ((ActSmartPanelKeySetVM) this.mViewModel).position).withLong(Constants.CONTROL_ID, ((ActSmartPanelKeySetVM) this.mViewModel).controlId).navigation(this);
        }
    }

    private void showEditNameDialog() {
        EditDialog.asDefault().setContent(((ActSmartPanelKeySetBinding) this.mViewBinding).tvKeyName.getText().toString()).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelKeySet$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelKeySet.this.lambda$showEditNameDialog$5((String) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$5(String str) {
        ((ActSmartPanelKeySetVM) this.mViewModel).changeKeyName(str, ((ActSmartPanelKeySetVM) this.mViewModel).position);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3001) {
            lambda$edit$1(this);
        }
    }
}