package com.ltech.smarthome.ui.scene;

import android.content.Intent;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.singleton.ComboCmdHelper;
import com.ltech.smarthome.ui.device.gqx.ActGqSelectDaliScene;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.List;

/* loaded from: classes4.dex */
public class ActGqSelectCgdPro extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> {
    static /* synthetic */ boolean lambda$showFailBindDialog$2(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterGroup(Group group) {
        return false;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_device));
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.setVisibility(0);
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.getLayoutParams().width = Utils.dip2px(this, 30.0f);
        this.listType = 4;
        setSortType(1);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void deviceClick(Device device) {
        NavUtils.destination(ActGqSelectDaliScene.class).withLong("device_id", device.getDeviceId()).withLong(Constants.RELATE_ID, device.getId()).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withBoolean(Constants.GROUP_CONTROL, getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false)).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withBoolean(Constants.IS_GQ, true).withLong(Constants.PLACE_ID, this.placeId).withInt(Constants.SELECT_POSITION, getIntent().getIntExtra(Constants.SELECT_POSITION, 0)).withDefaultRequestCode().navigation(this);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        return device.getProductId().equals(ProductId.ID_BLE_LIGHT_CGD_PRO);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3001) {
            long longExtra = data.getLongExtra(Constants.RELATE_ID, -1L);
            int intExtra = data.getIntExtra(Constants.GROUP_RELATE, -1);
            Intent intent = new Intent();
            intent.putExtra(Constants.RELATE_ID, longExtra);
            intent.putExtra(Constants.GROUP_RELATE, intExtra);
            setResult(3001, intent);
            finishActivity();
        }
    }

    private void bindData(final long relateId, final int relateType) {
        final int intExtra = getIntent().getIntExtra(Constants.SELECT_POSITION, 0);
        final Device deviceById = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
        ImageTipDialog.asDefault().setTitle(getString(R.string.app_str_rc4s_activate_tip)).setConfirmString(getString(R.string.get_it)).setImageRes(R.mipmap.rc4sble_2).setCallback(new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.scene.ActGqSelectCgdPro$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
            public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                ActGqSelectCgdPro.this.lambda$bindData$0(relateType, relateId, deviceById, intExtra, imageTipDialog);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:9:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$bindData$0(final int r12, final long r13, final com.ltech.smarthome.model.bean.Device r15, final int r16, final com.ltech.smarthome.view.dialog.ImageTipDialog r17) {
        /*
            r11 = this;
            r0 = 2
            if (r12 != r0) goto L20
            com.ltech.smarthome.model.Repository r0 = com.ltech.smarthome.model.Injection.repo()
            com.ltech.smarthome.model.repo.ifun.IGroup r0 = r0.group()
            com.ltech.smarthome.model.bean.Group r0 = r0.getGroupById(r13)
            r1 = r0
            com.ltech.smarthome.model.bean.Group r1 = (com.ltech.smarthome.model.bean.Group) r1
            r0.getName()
            com.ltech.smarthome.model.IComboCmd r1 = com.ltech.smarthome.singleton.ComboCmdHelper.getInstance()
            java.util.List r1 = r1.getDefaultSelfActions(r0)
        L1d:
            r4 = r0
            r0 = r1
            goto L62
        L20:
            r0 = 1
            if (r12 != r0) goto L3e
            com.ltech.smarthome.model.Repository r0 = com.ltech.smarthome.model.Injection.repo()
            com.ltech.smarthome.model.repo.ifun.IDevice r0 = r0.device()
            com.ltech.smarthome.model.bean.Device r0 = r0.getDeviceById(r13)
            r1 = r0
            com.ltech.smarthome.model.bean.Device r1 = (com.ltech.smarthome.model.bean.Device) r1
            r0.getName()
            com.ltech.smarthome.model.IComboCmd r1 = com.ltech.smarthome.singleton.ComboCmdHelper.getInstance()
            java.util.List r1 = r1.getDefaultSelfActions(r0)
            goto L1d
        L3e:
            r0 = 3
            if (r12 != r0) goto L60
            com.ltech.smarthome.model.Repository r0 = com.ltech.smarthome.model.Injection.repo()
            com.ltech.smarthome.model.repo.ifun.IScene r0 = r0.scene()
            com.ltech.smarthome.model.bean.Scene r0 = r0.getSceneBySceneId(r13)
            r1 = r0
            com.ltech.smarthome.model.bean.Scene r1 = (com.ltech.smarthome.model.bean.Scene) r1
            r0.getName()
            com.ltech.smarthome.model.IComboCmd r1 = com.ltech.smarthome.singleton.ComboCmdHelper.getInstance()
            int r2 = r0.getSceneNum()
            java.util.List r1 = r1.getSceneDefaultComboCmdActions(r0, r2)
            goto L1d
        L60:
            r0 = 0
            r4 = r0
        L62:
            if (r0 == 0) goto L81
            java.lang.String r1 = ""
            r11.showLoadingDialog(r1)
            com.ltech.smarthome.model.IComboCmd r10 = com.ltech.smarthome.singleton.ComboCmdHelper.getInstance()
            com.ltech.smarthome.ui.scene.ActGqSelectCgdPro$1 r1 = new com.ltech.smarthome.ui.scene.ActGqSelectCgdPro$1
            r2 = r11
            r9 = r12
            r7 = r13
            r3 = r15
            r5 = r16
            r6 = r17
            r1.<init>()
            r7 = r1
            r6 = r5
            r1 = r10
            r5 = r0
            r1.subscribeCmd(r2, r3, r4, r5, r6, r7)
        L81:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.scene.ActGqSelectCgdPro.lambda$bindData$0(int, long, com.ltech.smarthome.model.bean.Device, int, com.ltech.smarthome.view.dialog.ImageTipDialog):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showFailBindDialog(final long relateId, final int relateType) {
        MessageDialog.show(this, getString(R.string.app_str_operation_failure), getString(R.string.app_str_binding_fail_tip)).setCancelable(false).setOkButton(getString(R.string.go_continue), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.scene.ActGqSelectCgdPro$$ExternalSyntheticLambda1
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showFailBindDialog$1;
                lambda$showFailBindDialog$1 = ActGqSelectCgdPro.this.lambda$showFailBindDialog$1(relateId, relateType, baseDialog, view);
                return lambda$showFailBindDialog$1;
            }
        }).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.scene.ActGqSelectCgdPro$$ExternalSyntheticLambda2
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return ActGqSelectCgdPro.lambda$showFailBindDialog$2(baseDialog, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showFailBindDialog$1(long j, int i, BaseDialog baseDialog, View view) {
        bindData(j, i);
        return false;
    }

    public void uploadData(Device device, final int selIndex, final long relateId, final int relateType) {
        ((ObservableSubscribeProxy) Injection.net().bindKeyInfo(device.getDeviceId(), ComboCmdHelper.getInstance().getKeysByZone(selIndex + 1)).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.ActGqSelectCgdPro$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActGqSelectCgdPro.this.lambda$uploadData$3((Disposable) obj);
            }
        }).doFinally(new Action() { // from class: com.ltech.smarthome.ui.scene.ActGqSelectCgdPro$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActGqSelectCgdPro.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.ActGqSelectCgdPro$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActGqSelectCgdPro.this.lambda$uploadData$4(selIndex, relateId, relateType, (List) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$3(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$4(int i, long j, int i2, List list) throws Exception {
        ComboCmdHelper.getInstance().setKeyData(i + 1, list);
        Intent intent = new Intent();
        intent.putExtra(Constants.RELATE_ID, j);
        intent.putExtra(Constants.GROUP_RELATE, i2);
        setResult(3001, intent);
        finishActivity();
    }
}