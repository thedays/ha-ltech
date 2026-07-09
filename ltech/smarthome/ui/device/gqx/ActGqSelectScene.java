package com.ltech.smarthome.ui.device.gqx;

import android.content.Intent;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSelectSceneBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.singleton.ComboCmdHelper;
import com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity;
import com.ltech.smarthome.ui.newselect.FtRoomSceneVM;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
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
public class ActGqSelectScene extends BaseRoomSceneActivity<ActSelectSceneBinding, FtRoomSceneVM> implements ISelectAction {
    static /* synthetic */ boolean lambda$showFailBindDialog$3(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity
    protected int getSceneType() {
        return 2;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$initView$0(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setEditImage(R.mipmap.icon_search);
        this.listType = 2;
        ((ActSelectSceneBinding) this.mViewBinding).setBottomTip(getString(R.string.finish));
        ((ActSelectSceneBinding) this.mViewBinding).tvBottom.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqSelectScene$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActGqSelectScene.this.lambda$initView$0(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (this.selectSceneList.isEmpty()) {
            SmartToast.showShort(R.string.please_choose);
        } else {
            bindData(((FtRoomSceneVM) this.mViewModel).scene.getSceneId(), 3);
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity
    protected void sceneClick(Scene scene) {
        ((FtRoomSceneVM) this.mViewModel).scene = scene;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        ((ActSelectSceneBinding) this.mViewBinding).setTitleGone(true);
        ((ActSelectSceneBinding) this.mViewBinding).layoutSearch.setVisibility(0);
        ((ActSelectSceneBinding) this.mViewBinding).searchBar.cancelBtn.setVisibility(0);
    }

    private void bindData(final long relateId, final int relateType) {
        final int intExtra = getIntent().getIntExtra(Constants.SELECT_POSITION, 0);
        final Device deviceById = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
        ImageTipDialog.asDefault().setTitle(getString(R.string.app_str_rc4s_activate_tip)).setConfirmString(getString(R.string.get_it)).setImageRes(R.mipmap.rc4sble_2).setCallback(new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqSelectScene$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
            public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                ActGqSelectScene.this.lambda$bindData$1(relateType, relateId, deviceById, intExtra, imageTipDialog);
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
    public /* synthetic */ void lambda$bindData$1(final int r12, final long r13, final com.ltech.smarthome.model.bean.Device r15, final int r16, final com.ltech.smarthome.view.dialog.ImageTipDialog r17) {
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
            com.ltech.smarthome.ui.device.gqx.ActGqSelectScene$1 r1 = new com.ltech.smarthome.ui.device.gqx.ActGqSelectScene$1
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
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.gqx.ActGqSelectScene.lambda$bindData$1(int, long, com.ltech.smarthome.model.bean.Device, int, com.ltech.smarthome.view.dialog.ImageTipDialog):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showFailBindDialog(final long relateId, final int relateType) {
        MessageDialog.show(this, getString(R.string.app_str_operation_failure), getString(R.string.app_str_binding_fail_tip)).setCancelable(false).setOkButton(getString(R.string.go_continue), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqSelectScene$$ExternalSyntheticLambda2
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showFailBindDialog$2;
                lambda$showFailBindDialog$2 = ActGqSelectScene.this.lambda$showFailBindDialog$2(relateId, relateType, baseDialog, view);
                return lambda$showFailBindDialog$2;
            }
        }).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqSelectScene$$ExternalSyntheticLambda3
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return ActGqSelectScene.lambda$showFailBindDialog$3(baseDialog, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showFailBindDialog$2(long j, int i, BaseDialog baseDialog, View view) {
        bindData(j, i);
        return false;
    }

    public void uploadData(Device device, final int selIndex, final long relateId, final int relateType) {
        ((ObservableSubscribeProxy) Injection.net().bindKeyInfo(device.getDeviceId(), ComboCmdHelper.getInstance().getKeysByZone(selIndex + 1)).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqSelectScene$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActGqSelectScene.this.lambda$uploadData$4((Disposable) obj);
            }
        }).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqSelectScene$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActGqSelectScene.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqSelectScene$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActGqSelectScene.this.lambda$uploadData$5(selIndex, relateId, relateType, (List) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$4(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$5(int i, long j, int i2, List list) throws Exception {
        ComboCmdHelper.getInstance().setKeyData(i + 1, list);
        Intent intent = new Intent();
        intent.putExtra(Constants.RELATE_ID, j);
        intent.putExtra(Constants.GROUP_RELATE, i2);
        setResult(3001, intent);
        finishActivity();
    }
}