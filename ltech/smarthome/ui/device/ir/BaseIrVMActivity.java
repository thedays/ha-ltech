package com.ltech.smarthome.ui.device.ir;

import android.text.TextUtils;
import android.view.View;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import com.hzy.tvmao.KookongSDK;
import com.kookong.app.data.IrData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.BaseIrParam;
import com.ltech.smarthome.model.device_param.IrParam;
import com.ltech.smarthome.ui.config.ConfigHelper;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.ir.BaseIrVM;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.IrFunDialog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class BaseIrVMActivity<V extends ViewDataBinding, VM extends BaseIrVM> extends BaseControlActivity<V, VM> implements ISelectAction {
    protected abstract int deviceType();

    protected abstract void getRcCodeSuccess(int ridPos);

    protected abstract void initControlView();

    protected abstract void initSelectActionView();

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void lambda$edit$4(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((BaseIrVM) this.mViewModel).brandId = getIntent().getIntExtra(Constants.BRAND_ID, 0);
        ((BaseIrVM) this.mViewModel).spId = getIntent().getIntExtra(Constants.SP_ID, 0);
        ((BaseIrVM) this.mViewModel).areaId = getIntent().getIntExtra(Constants.AREA_ID, 0);
        ((BaseIrVM) this.mViewModel).rids = getIntent().getIntegerArrayListExtra(Constants.RID_LIST);
        ((BaseIrVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((BaseIrVM) this.mViewModel).brandName = getIntent().getStringExtra(Constants.BRAND_NAME);
        ((BaseIrVM) this.mViewModel).selectAction = getIntent().getBooleanExtra(Constants.SELECT_ACTION, false);
        ((BaseIrVM) this.mViewModel).changeIr = getIntent().getBooleanExtra(Constants.CHANGE_IR, false);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        if (((BaseIrVM) this.mViewModel).rids != null || ((BaseIrVM) this.mViewModel).brandId > 0 || (((BaseIrVM) this.mViewModel).spId > 0 && ((BaseIrVM) this.mViewModel).areaId > 0)) {
            Device device = new Device();
            device.setProductId(ConfigHelper.instance().productInfo.getProductId());
            device.setMacdeviceid(ConfigHelper.instance().macdeviceid);
            device.setMacfalg(2);
            if (ConfigHelper.instance().unicastAddress != 0) {
                IrParam irParam = new IrParam();
                irParam.setUnicastAddress(ConfigHelper.instance().unicastAddress);
                device.setParam(irParam);
            }
            ((BaseIrVM) this.mViewModel).controlDevice.setValue(device);
            StringBuilder sb = new StringBuilder();
            sb.append(((BaseIrVM) this.mViewModel).brandName);
            sb.append(LanguageUtils.isChinese(this) ? "" : " ");
            sb.append(ConfigHelper.instance().productInfo.getDefaultName(this));
            setTitle(sb.toString());
            ((BaseIrVM) this.mViewModel).showEditNameDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.BaseIrVMActivity$$ExternalSyntheticLambda4
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    BaseIrVMActivity.this.lambda$startObserve$0((Void) obj);
                }
            });
            if (((BaseIrVM) this.mViewModel).rids != null) {
                ((BaseIrVM) this.mViewModel).currentRcCodePos.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.BaseIrVMActivity$$ExternalSyntheticLambda5
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        BaseIrVMActivity.this.getRcCode(((Integer) obj).intValue());
                    }
                });
                ((BaseIrVM) this.mViewModel).currentRcCodePos.setValue(0);
                return;
            } else {
                onRetry();
                ((BaseIrVM) this.mViewModel).currentRcCodePos.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.BaseIrVMActivity$$ExternalSyntheticLambda5
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        BaseIrVMActivity.this.getRcCode(((Integer) obj).intValue());
                    }
                });
                return;
            }
        }
        if (((BaseIrVM) this.mViewModel).changeIr) {
            List<Device> value = Injection.repo().device().getDeviceListCache().getValue();
            Iterator<Device> it = value.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Device next = it.next();
                if (next.getId() == ((BaseIrVM) this.mViewModel).controlId) {
                    ((BaseIrVM) this.mViewModel).controlDevice.setValue(next);
                    setTitle(next.getDeviceName());
                    ((BaseIrVM) this.mViewModel).brandId = ((IrParam) next.getParam(IrParam.class)).getBrandId();
                    break;
                }
            }
            Iterator<Device> it2 = value.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Device next2 = it2.next();
                if (next2.getDeviceId() == ((BaseIrVM) this.mViewModel).controlDevice.getValue().getMacdeviceid()) {
                    KookongSDK.init(getApplicationContext(), ActSelectBrand.INIT_KEY, next2.getWifiMac().replaceAll(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR, "").toUpperCase());
                    break;
                }
            }
            onRetry();
            ((BaseIrVM) this.mViewModel).currentRcCodePos.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.BaseIrVMActivity$$ExternalSyntheticLambda5
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    BaseIrVMActivity.this.getRcCode(((Integer) obj).intValue());
                }
            });
            ((BaseIrVM) this.mViewModel).showEditNameDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.BaseIrVMActivity$$ExternalSyntheticLambda6
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    BaseIrVMActivity.this.lambda$startObserve$1((Void) obj);
                }
            });
            return;
        }
        if (((BaseIrVM) this.mViewModel).selectAction) {
            ((BaseIrVM) this.mViewModel).controlDevice.setValue((Device) SceneHelper.instance().controlObject);
            ((BaseIrVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.BaseIrVMActivity$$ExternalSyntheticLambda7
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    BaseIrVMActivity.this.lambda$startObserve$2((Device) obj);
                }
            });
        } else if (((BaseIrVM) this.mViewModel).controlId != -1) {
            ((BaseIrVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.BaseIrVMActivity$$ExternalSyntheticLambda8
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    BaseIrVMActivity.this.lambda$startObserve$3((Device) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Void r1) {
        showEditDialog(getTitleBar().getTitle());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r1) {
        ((BaseIrVM) this.mViewModel).changeIr();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Device device) {
        if (device != null) {
            setTitle(device.getDeviceName());
            ((BaseIrVM) this.mViewModel).initManager(((IrParam) device.getParam(IrParam.class)).getIrDatas().get(0));
            initSelectActionView();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Device device) {
        if (device != null) {
            setTitle(device.getDeviceName());
            IrParam irParam = (IrParam) device.getParam(IrParam.class);
            if (irParam.getIrDatas() == null || irParam.getIrDatas().size() <= 0) {
                return;
            }
            ((BaseIrVM) this.mViewModel).initManager(irParam.getIrDatas().get(0));
            initControlView();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (((BaseIrVM) this.mViewModel).controlId != -1) {
            ((BaseIrVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDeviceById(((BaseIrVM) this.mViewModel).controlId));
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (((BaseIrVM) this.mViewModel).selectAction) {
            if (((BaseIrVM) this.mViewModel).mIrCmdParam == null) {
                SmartToast.showShort(R.string.please_choose);
                return;
            }
            ((BaseIrVM) this.mViewModel).mIrCmdParam.addExtParam(SceneHelper.OPTION_VALUE, ((BaseIrVM) this.mViewModel).cmdName);
            ((BaseIrVM) this.mViewModel).mIrCmdParam.addExtParam(SceneHelper.OPTION, "0");
            SceneHelper.instance().macCode = ((BaseIrVM) this.mViewModel).mParams;
            SceneHelper.instance().cmdParam = ((BaseIrVM) this.mViewModel).mIrCmdParam;
            SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.device.ir.BaseIrVMActivity$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    BaseIrVMActivity.this.lambda$edit$4((Boolean) obj);
                }
            });
            return;
        }
        NavHelper.goSetting(((BaseIrVM) this.mViewModel).controlDevice.getValue());
    }

    private void showEditDialog(String content) {
        EditDialog.asDefault().setContent(content).setTitle(getString(R.string.device_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.ir.BaseIrVMActivity$$ExternalSyntheticLambda10
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseIrVMActivity.this.lambda$showEditDialog$5((String) obj);
            }
        }).showDialog(getSupportFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditDialog$5(String str) {
        if (TextUtils.isEmpty(str)) {
            SmartToast.showShort(getString(R.string.input_name));
        } else {
            ((BaseIrVM) this.mViewModel).addIrDevice(str);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        if (((BaseIrVM) this.mViewModel).rids == null) {
            getRemoteId();
        } else {
            getRcCode(((BaseIrVM) this.mViewModel).currentRcCodePos.getValue().intValue());
        }
    }

    private void getRemoteId() {
        showLoading();
        ((BaseIrVM) this.mViewModel).getAllRemoteIds(deviceType(), new IAction() { // from class: com.ltech.smarthome.ui.device.ir.BaseIrVMActivity$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseIrVMActivity.this.lambda$getRemoteId$6((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getRemoteId$6(Boolean bool) {
        if (bool.booleanValue()) {
            ((BaseIrVM) this.mViewModel).currentRcCodePos.setValue(0);
        } else {
            showError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getRcCode(final int ridPos) {
        showLoading();
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.ir.BaseIrVMActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                BaseIrVMActivity.this.lambda$getRcCode$8(ridPos);
            }
        }, 250L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getRcCode$8(final int i) {
        ((BaseIrVM) this.mViewModel).getRcCode(i, deviceType(), new IAction() { // from class: com.ltech.smarthome.ui.device.ir.BaseIrVMActivity$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseIrVMActivity.this.lambda$getRcCode$7(i, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getRcCode$7(int i, Boolean bool) {
        if (bool.booleanValue()) {
            ((BaseIrVM) this.mViewModel).initManager(null);
            getRcCodeSuccess(i);
            showContent();
            return;
        }
        showError();
    }

    protected void showFunDialog() {
        List<IrKeyItem> keyItemList = ((BaseIrVM) this.mViewModel).getKeyItemList();
        ArrayList arrayList = new ArrayList();
        Iterator<IrData.IrKey> it = ((BaseIrVM) this.mViewModel).irData.keys.iterator();
        while (it.hasNext()) {
            IrData.IrKey next = it.next();
            Iterator<IrKeyItem> it2 = keyItemList.iterator();
            while (true) {
                if (it2.hasNext()) {
                    if (next.fid == it2.next().getFid()) {
                        break;
                    }
                } else {
                    arrayList.add(new IrKeyItem(LanguageUtils.isChinese(this) ? next.fname : next.fkey, next.fid));
                }
            }
        }
        IrFunDialog.asDefault().setList(arrayList).setDialogCallback(new IrFunDialog.OnDialogCallback() { // from class: com.ltech.smarthome.ui.device.ir.BaseIrVMActivity$$ExternalSyntheticLambda9
            @Override // com.ltech.smarthome.view.dialog.IrFunDialog.OnDialogCallback
            public final void onItemClick(View view, IrKeyItem irKeyItem) {
                BaseIrVMActivity.this.lambda$showFunDialog$9(view, irKeyItem);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showFunDialog$9(View view, IrKeyItem irKeyItem) {
        ((BaseIrVM) this.mViewModel).sendIrControl(irKeyItem.getFid());
        ((BaseIrVM) this.mViewModel).clickAnimate(view);
    }

    public int getUnicastAddress() {
        BaseIrParam baseIrParam;
        if (((BaseIrVM) this.mViewModel).controlDevice.getValue() == null || ((BaseIrVM) this.mViewModel).controlDevice.getValue().getParam() == null || (baseIrParam = (BaseIrParam) ((BaseIrVM) this.mViewModel).controlDevice.getValue().getParam(BaseIrParam.class)) == null || baseIrParam.getUnicastAddress() == 0) {
            return 0;
        }
        return baseIrParam.getUnicastAddress();
    }
}