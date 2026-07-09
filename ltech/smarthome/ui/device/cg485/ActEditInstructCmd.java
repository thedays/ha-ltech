package com.ltech.smarthome.ui.device.cg485;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActEditInstructCmdBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.Rs485ExtParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.device.AddDeviceResponse;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.RadioImageTextView;
import com.ltech.smarthome.view.dialog.EditCmdCopyDialog;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.LearnInstructDialog;
import com.ltech.smarthome.view.dialog.SelectListWithIconDialog;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.StringUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActEditInstructCmd extends BaseNormalActivity<ActEditInstructCmdBinding> {
    private int commandType;
    private boolean isEdit;
    private String lastCmdStr;
    private String lastInstructStr;
    private Rs485ExtParam.Instruct mInstruct;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_edit_instruct_cmd;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        this.isEdit = getIntent().getBooleanExtra(Constants.IS_EDIT, false);
        int intExtra = getIntent().getIntExtra(Constants.COMMAND_TYPE, 1);
        this.commandType = intExtra;
        if (this.isEdit) {
            this.mInstruct = Cg485Helper.getInstruct(intExtra).getClone();
            setTitle(getString(R.string.modify_instruct));
            ((ActEditInstructCmdBinding) this.mViewBinding).etName.setText(this.mInstruct.getActionName());
            ((ActEditInstructCmdBinding) this.mViewBinding).tvBottom.setText(getString(R.string.save));
            ((ActEditInstructCmdBinding) this.mViewBinding).tvInstruct.setText(this.mInstruct.getCmd());
            ((ActEditInstructCmdBinding) this.mViewBinding).layoutDataFormat.setVisibility(this.mInstruct.getRecordType() != 1 ? 8 : 0);
        } else {
            this.mInstruct = new Rs485ExtParam.Instruct();
            setTitle(getString(R.string.add_instruct));
            if (this.commandType == 1) {
                ((ActEditInstructCmdBinding) this.mViewBinding).tvBottom.setText(getString(R.string.save));
            } else {
                ((ActEditInstructCmdBinding) this.mViewBinding).tvBottom.setText(getString(R.string.bind));
            }
            ((ActEditInstructCmdBinding) this.mViewBinding).layoutDataFormat.setVisibility(8);
        }
        this.lastInstructStr = GsonUtils.toJson(this.mInstruct);
        this.lastCmdStr = this.mInstruct.getCmd();
        ((ActEditInstructCmdBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.cg485.ActEditInstructCmd$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActEditInstructCmd.this.lambda$initView$5((View) obj);
            }
        }));
        initInputType();
        initDataFormat();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$5(View view) {
        int id = view.getId();
        if (id == R.id.layout_input) {
            if (this.mInstruct.getRecordType() == 2) {
                final List<String> asList = Arrays.asList(Cg485Helper.library);
                boolean[] zArr = new boolean[asList.size()];
                for (int i = 0; i < asList.size(); i++) {
                    zArr[i] = !Cg485Helper.isCmdRepeat(this.commandType, asList.get(i), 1);
                }
                SelectListWithIconDialog.asDefault(true).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.confirm)).setIconRes(R.mipmap.icon_copy).setSelectPosition(-1).setSelectList(asList).setEnableArray(zArr).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActEditInstructCmd$$ExternalSyntheticLambda3
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActEditInstructCmd.this.lambda$initView$1(asList, (Integer) obj);
                    }
                }).showDialog(this);
                return;
            }
            if (this.mInstruct.getRecordType() == 3) {
                LearnInstructDialog.asDefault().setConfirmString(getString(R.string.confirm)).setCancelString(getString(R.string.cancel)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActEditInstructCmd$$ExternalSyntheticLambda4
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActEditInstructCmd.this.lambda$initView$2((String) obj);
                    }
                }).showDialog(this);
                return;
            } else {
                EditCmdCopyDialog.asDefault().setTitle(getString(R.string.input_instruct)).setContent(this.mInstruct.getCmd()).setHexFormat(this.mInstruct.getDataFormat() == 1).setInputEmptyTip(this.mInstruct.getDataFormat() == 1 ? String.format("%s(%s)", getString(R.string.please_input_instruct), getString(R.string.input_hex_tip)) : getString(R.string.please_input_instruct)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.save)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActEditInstructCmd$$ExternalSyntheticLambda5
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActEditInstructCmd.this.lambda$initView$3((String) obj);
                    }
                }).showDialog(this);
                return;
            }
        }
        if (id == R.id.layout_name) {
            EditDialog.asDefault().setContent(this.mInstruct.getActionName()).setTitle(getString(R.string.edit_name)).setHint(getString(R.string.input_name)).setInputEmptyTip(getString(R.string.input_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActEditInstructCmd$$ExternalSyntheticLambda6
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActEditInstructCmd.this.lambda$initView$4((String) obj);
                }
            }).showDialog(this);
            return;
        }
        if (id != R.id.tv_bottom) {
            return;
        }
        if (TextUtils.isEmpty(this.mInstruct.getCmd())) {
            SmartToast.showShort(R.string.cmd_empty_tip);
            return;
        }
        if (!this.lastCmdStr.equals(this.mInstruct.getCmd()) && Cg485Helper.isCmdRepeat(this.commandType, this.mInstruct.getCmd(), this.mInstruct.getDataFormat())) {
            SmartToast.showShort(R.string.cmd_repeat_tip);
            return;
        }
        int i2 = this.commandType;
        if (i2 == 1) {
            if (this.isEdit) {
                Cg485Helper.setInstruct(i2, this.mInstruct);
            } else {
                Cg485Helper.getCategory(1).getAction().add(this.mInstruct);
            }
            this.lastInstructStr = GsonUtils.toJson(this.mInstruct);
            if (Cg485Helper.controlDevice.getDeviceId() == 0) {
                saveBleTo485Device();
                return;
            } else {
                Cg485Helper.updateParamExt(this, true, true);
                return;
            }
        }
        if (this.isEdit) {
            showLoadingDialog(getString(R.string.saving));
            Rs485ExtParam.RelateInfo bindAction = Cg485Helper.getInstruct(this.commandType).getBindAction();
            Cg485Helper.setInstruct(this, 2, this.mInstruct.getCmdIdx(), TextUtils.isEmpty(Cg485Helper.contentState.name) ? "" : StringUtils.addZeroForNum(Integer.toHexString(Cg485Helper.getInstructAddress(bindAction)), 4) + StringUtils.addZeroForNum(Integer.toHexString(bindAction.getZone()), 4) + bindAction.getInstruct(), this.mInstruct.getCmd(), this.mInstruct.getDataFormat(), new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActEditInstructCmd$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActEditInstructCmd.this.lambda$initView$0((ResponseMsg) obj);
                }
            });
            return;
        }
        Cg485Helper.showBindDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            this.lastInstructStr = GsonUtils.toJson(this.mInstruct);
            Cg485Helper.setInstruct(this.commandType, this.mInstruct);
            Cg485Helper.updateParamExt(this, true, new boolean[0]);
            return;
        }
        showErrorDialog(getString(R.string.save_fail));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(List list, Integer num) {
        ((ActEditInstructCmdBinding) this.mViewBinding).tvInstruct.setText((CharSequence) list.get(num.intValue()));
        this.mInstruct.setCmd((String) list.get(num.intValue()));
        this.mInstruct.setDataFormat(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(String str) {
        ((ActEditInstructCmdBinding) this.mViewBinding).tvInstruct.setText(str);
        this.mInstruct.setCmd(str);
        this.mInstruct.setDataFormat(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3(String str) {
        if (this.mInstruct.getDataFormat() == 1) {
            str = Cg485Helper.getInputCmd(str);
        }
        ((ActEditInstructCmdBinding) this.mViewBinding).tvInstruct.setText(str);
        this.mInstruct.setCmd(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$4(String str) {
        this.mInstruct.setActionName(str);
        ((ActEditInstructCmdBinding) this.mViewBinding).etName.setText(str);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        if (!GsonUtils.toJson(this.mInstruct).equals(this.lastInstructStr)) {
            showNeedSaveDialog();
        } else {
            super.back();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        Cg485Helper.isAddBleTo485 = false;
    }

    private void initInputType() {
        ((ActEditInstructCmdBinding) this.mViewBinding).radioInput.setEnable(true);
        ((ActEditInstructCmdBinding) this.mViewBinding).radioLibrary.setEnable(true);
        ((ActEditInstructCmdBinding) this.mViewBinding).radioLearn.setEnable(true);
        switchInputType(this.mInstruct.getRecordType());
        ((ActEditInstructCmdBinding) this.mViewBinding).radioInput.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActEditInstructCmd$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
            public final void onCheckChanged(RadioImageTextView radioImageTextView, boolean z) {
                ActEditInstructCmd.this.lambda$initInputType$6(radioImageTextView, z);
            }
        });
        ((ActEditInstructCmdBinding) this.mViewBinding).radioLibrary.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActEditInstructCmd$$ExternalSyntheticLambda12
            @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
            public final void onCheckChanged(RadioImageTextView radioImageTextView, boolean z) {
                ActEditInstructCmd.this.lambda$initInputType$7(radioImageTextView, z);
            }
        });
        ((ActEditInstructCmdBinding) this.mViewBinding).radioLearn.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActEditInstructCmd$$ExternalSyntheticLambda13
            @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
            public final void onCheckChanged(RadioImageTextView radioImageTextView, boolean z) {
                ActEditInstructCmd.this.lambda$initInputType$8(radioImageTextView, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initInputType$6(RadioImageTextView radioImageTextView, boolean z) {
        switchInputType(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initInputType$7(RadioImageTextView radioImageTextView, boolean z) {
        switchInputType(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initInputType$8(RadioImageTextView radioImageTextView, boolean z) {
        switchInputType(3);
    }

    private void switchInputType(int editType) {
        this.mInstruct.setRecordType(editType);
        ((ActEditInstructCmdBinding) this.mViewBinding).radioInput.setCheck(editType == 1);
        ((ActEditInstructCmdBinding) this.mViewBinding).radioLibrary.setCheck(editType == 2);
        ((ActEditInstructCmdBinding) this.mViewBinding).radioLearn.setCheck(editType == 3);
        ((ActEditInstructCmdBinding) this.mViewBinding).layoutDataFormat.setVisibility(this.mInstruct.getRecordType() == 1 ? 0 : 8);
        if (this.mInstruct.getRecordType() == 1) {
            ((ActEditInstructCmdBinding) this.mViewBinding).layoutDataFormat.setVisibility(0);
            switchDataFormat(this.mInstruct.getDataFormat());
        } else {
            ((ActEditInstructCmdBinding) this.mViewBinding).layoutDataFormat.setVisibility(8);
        }
        if (editType == 1) {
            ((ActEditInstructCmdBinding) this.mViewBinding).tvInput.setText(R.string.manual_input);
            ((ActEditInstructCmdBinding) this.mViewBinding).tvInstruct.setHint(R.string.please_input_instruct);
        } else if (editType == 2) {
            ((ActEditInstructCmdBinding) this.mViewBinding).tvInput.setText(R.string.choose_library);
            ((ActEditInstructCmdBinding) this.mViewBinding).tvInstruct.setHint(R.string.choose_library_tip);
        } else {
            ((ActEditInstructCmdBinding) this.mViewBinding).tvInput.setText(R.string.learn_instruct);
            ((ActEditInstructCmdBinding) this.mViewBinding).tvInstruct.setHint(R.string.learn_instruct_tip);
        }
    }

    private void initDataFormat() {
        ((ActEditInstructCmdBinding) this.mViewBinding).radioHex.setEnable(true);
        ((ActEditInstructCmdBinding) this.mViewBinding).radioAscii.setEnable(true);
        switchDataFormat(this.mInstruct.getDataFormat());
        ((ActEditInstructCmdBinding) this.mViewBinding).radioHex.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActEditInstructCmd$$ExternalSyntheticLambda15
            @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
            public final void onCheckChanged(RadioImageTextView radioImageTextView, boolean z) {
                ActEditInstructCmd.this.lambda$initDataFormat$9(radioImageTextView, z);
            }
        });
        ((ActEditInstructCmdBinding) this.mViewBinding).radioAscii.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActEditInstructCmd$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
            public final void onCheckChanged(RadioImageTextView radioImageTextView, boolean z) {
                ActEditInstructCmd.this.lambda$initDataFormat$10(radioImageTextView, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataFormat$9(RadioImageTextView radioImageTextView, boolean z) {
        switchDataFormat(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataFormat$10(RadioImageTextView radioImageTextView, boolean z) {
        switchDataFormat(2);
    }

    private void switchDataFormat(int dataFormat) {
        if (this.mInstruct.getDataFormat() != dataFormat) {
            ((ActEditInstructCmdBinding) this.mViewBinding).tvInstruct.setText("");
            this.mInstruct.setCmd("");
        }
        this.mInstruct.setDataFormat(dataFormat);
        ((ActEditInstructCmdBinding) this.mViewBinding).radioHex.setCheck(dataFormat == 1);
        ((ActEditInstructCmdBinding) this.mViewBinding).radioAscii.setCheck(dataFormat == 2);
    }

    public void saveBleTo485Device() {
        ((ObservableSubscribeProxy) Injection.net().addDevice(Cg485Helper.newBleTo485SubData()).delaySubscription(200L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.cg485.ActEditInstructCmd$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActEditInstructCmd.this.lambda$saveBleTo485Device$11((Disposable) obj);
            }
        }).observeOn(Schedulers.single()).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.cg485.ActEditInstructCmd$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActEditInstructCmd.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.cg485.ActEditInstructCmd$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActEditInstructCmd.this.lambda$saveBleTo485Device$12((AddDeviceResponse) obj);
            }
        }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.device.cg485.ActEditInstructCmd.1
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                ActEditInstructCmd actEditInstructCmd = ActEditInstructCmd.this;
                actEditInstructCmd.showErrorDialog(actEditInstructCmd.getString(R.string.save_fail));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveBleTo485Device$11(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveBleTo485Device$12(AddDeviceResponse addDeviceResponse) throws Exception {
        Injection.repo().device().saveDevice(Cg485Helper.addBleTo485Device(addDeviceResponse));
        showSuccessDialog(getString(R.string.save_success));
        finishActivity();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            List<Scene.SceneContent> convert2LocalSceneContents = SceneHelper.instance().convert2LocalSceneContents();
            if (convert2LocalSceneContents.isEmpty()) {
                return;
            }
            final Rs485ExtParam.RelateInfo convertSceneContent = Cg485Helper.convertSceneContent(convert2LocalSceneContents.get(0));
            final String str = StringUtils.addZeroForNum(Integer.toHexString(convertSceneContent.getAddress()), 4) + StringUtils.addZeroForNum(Integer.toHexString(convertSceneContent.getZone()), 4) + convertSceneContent.getInstruct();
            getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.cg485.ActEditInstructCmd$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    ActEditInstructCmd.this.lambda$onActivityResult$14(str, convertSceneContent);
                }
            }, 500L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onActivityResult$14(String str, final Rs485ExtParam.RelateInfo relateInfo) {
        showLoadingDialog(getString(R.string.saving));
        Cg485Helper.setInstruct(this.activity, 1, this.mInstruct.getCmdIdx(), str, this.mInstruct.getCmd(), this.mInstruct.getDataFormat(), new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActEditInstructCmd$$ExternalSyntheticLambda14
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEditInstructCmd.this.lambda$onActivityResult$13(relateInfo, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onActivityResult$13(Rs485ExtParam.RelateInfo relateInfo, ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            this.mInstruct.setCmdIdx(Integer.parseInt(responseMsg.getResData().substring(16, 18), 16));
            this.mInstruct.setBindAction(relateInfo);
            Cg485Helper.getCategory(2).getAction().add(this.mInstruct);
            this.lastInstructStr = GsonUtils.toJson(this.mInstruct);
            Cg485Helper.updateParamExt(this.activity, true, new boolean[0]);
            return;
        }
        showErrorDialog(getString(R.string.save_fail));
    }
}