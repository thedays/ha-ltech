package com.ltech.smarthome.view.dialog;

import android.graphics.Color;
import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogSelectCgdActionBinding;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.ui.device.dalipro.CgdActionSelectView;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.RadioImageTextView;
import com.ltech.smarthome.view.dialog.SelectCgdActionDialog;
import com.smart.message.base.BaseCmdParam;
import com.smart.product_agreement.param.LightCmdParam;

/* loaded from: classes4.dex */
public class SelectCgdActionDialog extends SmartDialog<DialogSelectCgdActionBinding> {
    private String cancelString;
    private IAction<Boolean> confirmAction;
    private String confirmString;
    private CgdActionSelectView.OnDialogCallback mCallback;
    private Role role;
    private String title;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_select_cgd_action;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectCgdActionDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogSelectCgdActionBinding, SelectCgdActionDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogSelectCgdActionBinding viewBinding, final SelectCgdActionDialog dialog) {
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.tvCancel.setText(dialog.cancelString);
            viewBinding.tvConfirm.setText(dialog.confirmString);
            viewBinding.radioOn.setCheck(true);
            dialog.initListeners(viewBinding);
            viewBinding.cgdActionView.setCallback(dialog.mCallback);
            viewBinding.cgdActionView.setRole(dialog.role);
            viewBinding.cgdActionView.setProgress(110);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectCgdActionDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SelectCgdActionDialog.AnonymousClass1.lambda$convertView$0(SelectCgdActionDialog.this, viewBinding, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(SelectCgdActionDialog selectCgdActionDialog, DialogSelectCgdActionBinding dialogSelectCgdActionBinding, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                selectCgdActionDialog.dismissDialog();
                return;
            }
            if (id != R.id.tv_confirm) {
                return;
            }
            if (dialogSelectCgdActionBinding.radioOn.isChecked()) {
                DaliProHelper.cmdParam = selectCgdActionDialog.getActionCmd(dialogSelectCgdActionBinding, true);
            } else {
                DaliProHelper.cmdParam = selectCgdActionDialog.getActionCmd(dialogSelectCgdActionBinding, false);
            }
            if (selectCgdActionDialog.confirmAction != null) {
                selectCgdActionDialog.confirmAction.act(true);
            }
            selectCgdActionDialog.dismissDialog();
        }
    }

    public static SelectCgdActionDialog asDefault() {
        return (SelectCgdActionDialog) new SelectCgdActionDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setGravity(80);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initListeners(final DialogSelectCgdActionBinding viewBinding) {
        viewBinding.radioOn.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.view.dialog.SelectCgdActionDialog$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
            public final void onCheckChanged(RadioImageTextView radioImageTextView, boolean z) {
                SelectCgdActionDialog.this.lambda$initListeners$0(viewBinding, radioImageTextView, z);
            }
        });
        viewBinding.radioOff.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.view.dialog.SelectCgdActionDialog$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
            public final void onCheckChanged(RadioImageTextView radioImageTextView, boolean z) {
                SelectCgdActionDialog.this.lambda$initListeners$1(viewBinding, radioImageTextView, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListeners$0(DialogSelectCgdActionBinding dialogSelectCgdActionBinding, RadioImageTextView radioImageTextView, boolean z) {
        dialogSelectCgdActionBinding.radioOn.setCheck(true);
        dialogSelectCgdActionBinding.radioOff.setCheck(false);
        dialogSelectCgdActionBinding.cgdActionView.setEnabled(true);
        Role role = this.role;
        if (role != null) {
            CmdAssistant.getLightCmdAssistant(role, new int[0]).sendOnOff(getContext(), true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListeners$1(DialogSelectCgdActionBinding dialogSelectCgdActionBinding, RadioImageTextView radioImageTextView, boolean z) {
        dialogSelectCgdActionBinding.radioOn.setCheck(false);
        dialogSelectCgdActionBinding.radioOff.setCheck(true);
        dialogSelectCgdActionBinding.cgdActionView.setEnabled(false);
        Role role = this.role;
        if (role != null) {
            CmdAssistant.getLightCmdAssistant(role, new int[0]).sendOnOff(getContext(), false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BaseCmdParam getActionCmd(DialogSelectCgdActionBinding viewBinding, boolean onOff) {
        if (onOff) {
            int color = viewBinding.cgdActionView.getColor();
            LightCmdParam lightCmdParam = new LightCmdParam();
            lightCmdParam.setCmdType(43);
            lightCmdParam.setRed(Color.red(color));
            lightCmdParam.setGreen(Color.green(color));
            lightCmdParam.setBlue(Color.blue(color));
            lightCmdParam.setRgbBrt(viewBinding.cgdActionView.getRgbBrt());
            lightCmdParam.setWy(viewBinding.cgdActionView.getWy());
            lightCmdParam.setWyBrt(viewBinding.cgdActionView.getWyBrt());
            lightCmdParam.addExtParam(SceneHelper.OPTION, "0");
            return lightCmdParam;
        }
        LightCmdParam lightCmdParam2 = new LightCmdParam();
        lightCmdParam2.setCmdType(1);
        lightCmdParam2.setOn(false);
        lightCmdParam2.addExtParam(SceneHelper.OPTION, "4");
        return lightCmdParam2;
    }

    public SelectCgdActionDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public SelectCgdActionDialog setCancelString(String cancelString) {
        this.cancelString = cancelString;
        return this;
    }

    public SelectCgdActionDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    public SelectCgdActionDialog setConfirmAction(IAction<Boolean> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    public SelectCgdActionDialog setRole(Role role) {
        this.role = role;
        return this;
    }

    public SelectCgdActionDialog setCallback(CgdActionSelectView.OnDialogCallback mCallback) {
        this.mCallback = mCallback;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "select_cgd_action_dialog";
    }
}