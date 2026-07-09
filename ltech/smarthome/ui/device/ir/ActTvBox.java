package com.ltech.smarthome.ui.device.ir;

import android.view.View;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.hzy.tvmao.KKNonACManager;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActTvBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.IrParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActTvBox extends BaseActTv<ActTvBoxVM> {
    @Override // com.ltech.smarthome.ui.device.ir.BaseIrVMActivity
    protected int deviceType() {
        return 3;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.VMActivity
    public ActTvBoxVM getViewModel() {
        return (ActTvBoxVM) new ViewModelProvider(this).get(ActTvBoxVM.class);
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseActTv, com.ltech.smarthome.ui.device.ir.BaseIrVMActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((ActTvBinding) this.mViewBinding).ivHome.setImageResource(R.mipmap.icon_ir_tv_power);
        ((ActTvBoxVM) this.mViewModel).showRelateDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.ActTvBox$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActTvBox.this.lambda$initView$0((Void) obj);
            }
        });
        ((ActTvBinding) this.mViewBinding).ivHome.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.ltech.smarthome.ui.device.ir.ActTvBox$$ExternalSyntheticLambda2
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                boolean lambda$initView$1;
                lambda$initView$1 = ActTvBox.this.lambda$initView$1(view);
                return lambda$initView$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(Void r1) {
        showTvRelatedDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initView$1(View view) {
        if (SharedPreferenceUtil.queryByteArrayValue(Constants.TV_POWER_KEY + ((ActTvBoxVM) this.mViewModel).controlDevice.getValue().getDeviceId()) == null) {
            return true;
        }
        showTvRelatedDialog();
        return true;
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseActTv, com.ltech.smarthome.ui.device.ir.BaseIrVMActivity
    protected void getRcCodeSuccess(int ridPos) {
        super.getRcCodeSuccess(ridPos);
        ((ActTvBinding) this.mViewBinding).ivHome.setVisibility(8);
    }

    private void showTvRelatedDialog() {
        List<Device> value = Injection.repo().device().getDeviceListCache().getValue();
        final ArrayList arrayList = new ArrayList();
        for (Device device : value) {
            if (ProductId.ID_IR_TV.equals(device.getProductId())) {
                arrayList.add(device);
            }
        }
        if (arrayList.isEmpty()) {
            SmartToast.showShort(R.string.no_tv);
            return;
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(((Device) it.next()).getDeviceName());
        }
        SelectListDialog.asDefault(true).setTitle(getString(R.string.please_choose_tv)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.confirm)).setSelectPosition(-1).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.ir.ActTvBox$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActTvBox.this.lambda$showTvRelatedDialog$2(arrayList, (Integer) obj);
            }
        }).setSelectList(arrayList2).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showTvRelatedDialog$2(List list, Integer num) {
        if (num.intValue() == -1) {
            return;
        }
        KKNonACManager kKNonACManager = new KKNonACManager(((IrParam) ((Device) list.get(num.intValue())).getParam(IrParam.class)).getIrDatas().get(0));
        SharedPreferenceUtil.edit().keepShared(Constants.TV_POWER_KEY + ((ActTvBoxVM) this.mViewModel).controlDevice.getValue().getDeviceId(), kKNonACManager.getKeyIr(1));
        SmartToast.showShort(R.string.bind_success);
    }
}