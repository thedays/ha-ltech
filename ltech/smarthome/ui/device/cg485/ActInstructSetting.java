package com.ltech.smarthome.ui.device.cg485;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActInstructSettingBinding;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.Rs485ExtParam;
import com.ltech.smarthome.ui.device.cg485.Cg485Helper;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.StringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class ActInstructSetting extends BaseNormalActivity<ActInstructSettingBinding> {
    private int commandType;
    private Cg485Helper.ContentState contentState = new Cg485Helper.ContentState();
    private Rs485ExtParam.Instruct mInstruct;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_instruct_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.instruct_set));
        setBackImage(R.mipmap.icon_back);
        int intExtra = getIntent().getIntExtra(Constants.COMMAND_TYPE, 1);
        this.commandType = intExtra;
        if (intExtra == 1) {
            ((ActInstructSettingBinding) this.mViewBinding).layoutBind.setVisibility(8);
        } else {
            ((ActInstructSettingBinding) this.mViewBinding).layoutBind.setVisibility(0);
        }
        ((ActInstructSettingBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.cg485.ActInstructSetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActInstructSetting.this.lambda$initView$2((View) obj);
            }
        }));
        ((ActInstructSettingBinding) this.mViewBinding).tvDelete.setVisibility(isOwnerOrManager() ? 0 : 8);
        ((ActInstructSettingBinding) this.mViewBinding).setOwnerOrManager(Boolean.valueOf(isOwnerOrManager()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(View view) {
        if (isOwnerOrManager()) {
            switch (view.getId()) {
                case R.id.layout_bind /* 2131297366 */:
                    if (TextUtils.isEmpty(this.contentState.name)) {
                        Cg485Helper.showBindDialog(this);
                        break;
                    } else {
                        showUnbindDialog();
                        break;
                    }
                case R.id.layout_instruct /* 2131297498 */:
                    Cg485Helper.contentState = this.contentState;
                    NavUtils.destination(ActEditInstructCmd.class).withBoolean(Constants.IS_EDIT, true).withInt(Constants.COMMAND_TYPE, this.commandType).navigation(this.activity);
                    break;
                case R.id.layout_set_name /* 2131297643 */:
                    EditDialog.asDefault().setContent(this.mInstruct.getActionName()).setTitle(getString(R.string.edit_name)).setHint(getString(R.string.input_name)).setInputEmptyTip(getString(R.string.input_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActInstructSetting$$ExternalSyntheticLambda2
                        @Override // com.ltech.smarthome.base.IAction
                        public final void act(Object obj) {
                            ActInstructSetting.this.lambda$initView$0((String) obj);
                        }
                    }).showDialog(this);
                    break;
                case R.id.tv_delete /* 2131298575 */:
                    MessageDialog.show(this, getString(R.string.delete_instruct), "").setOkButton(R.string.delete, new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.cg485.ActInstructSetting$$ExternalSyntheticLambda3
                        @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                        public final boolean onClick(BaseDialog baseDialog, View view2) {
                            boolean lambda$initView$1;
                            lambda$initView$1 = ActInstructSetting.this.lambda$initView$1(baseDialog, view2);
                            return lambda$initView$1;
                        }
                    }).setCancelButton(R.string.cancel);
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(String str) {
        this.mInstruct.setActionName(str);
        ((ActInstructSettingBinding) this.mViewBinding).tvName.setText(this.mInstruct.getActionName());
        Cg485Helper.updateParamExt(this, false, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initView$1(BaseDialog baseDialog, View view) {
        deleteInstruct();
        return false;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        showBindInfo();
    }

    private void showBindInfo() {
        this.mInstruct = Cg485Helper.getInstruct(this.commandType);
        ((ActInstructSettingBinding) this.mViewBinding).tvName.setText(this.mInstruct.getActionName());
        ((ActInstructSettingBinding) this.mViewBinding).tvInstruct.setText(this.mInstruct.getCmd());
        if (this.commandType == 2) {
            Cg485Helper.ContentState relateAction = Cg485Helper.getRelateAction(this, this.mInstruct.getBindAction());
            this.contentState = relateAction;
            if (TextUtils.isEmpty(relateAction.name)) {
                ((ActInstructSettingBinding) this.mViewBinding).tvBindMain.setText(R.string.no_bind_object);
                ((ActInstructSettingBinding) this.mViewBinding).tvBindSub.setVisibility(8);
            } else {
                ((ActInstructSettingBinding) this.mViewBinding).tvBindMain.setText(this.contentState.name);
                ((ActInstructSettingBinding) this.mViewBinding).tvBindSub.setVisibility(0);
                ((ActInstructSettingBinding) this.mViewBinding).tvBindSub.setText(String.format("%s %s", this.contentState.place, this.contentState.action));
            }
        }
    }

    private void deleteInstruct() {
        int i = this.commandType;
        if (i == 1) {
            Cg485Helper.getCategory(i).getAction().remove(Cg485Helper.instructPosition);
            Cg485Helper.updateParamExt(this, true, true);
        } else {
            Cg485Helper.deleteInstruct(this, Collections.singletonList(Integer.valueOf(this.mInstruct.getCmdIdx())), new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActInstructSetting$$ExternalSyntheticLambda6
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActInstructSetting.this.lambda$deleteInstruct$3((Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteInstruct$3(Boolean bool) {
        if (bool.booleanValue()) {
            Cg485Helper.getCategory(this.commandType).getAction().remove(Cg485Helper.instructPosition);
            Cg485Helper.updateParamExt(this, true, true);
        } else {
            showErrorDialog(getString(R.string.del_fail));
        }
    }

    private void showUnbindDialog() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.reset_relate));
        SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActInstructSetting$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActInstructSetting.this.lambda$showUnbindDialog$5((Integer) obj);
            }
        }).setSelectList(arrayList).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnbindDialog$5(Integer num) {
        showLoadingDialog(getString(R.string.unsubscribing));
        Cg485Helper.setInstruct(this, 2, this.mInstruct.getCmdIdx(), "", this.mInstruct.getCmd(), this.mInstruct.getDataFormat(), new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActInstructSetting$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActInstructSetting.this.lambda$showUnbindDialog$4((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnbindDialog$4(ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            this.mInstruct.setBindAction(new Rs485ExtParam.RelateInfo());
            Cg485Helper.updateParamExt(this, false, new boolean[0]);
            showBindInfo();
            return;
        }
        showErrorDialog(getString(R.string.save_fail));
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
            String str = StringUtils.addZeroForNum(Integer.toHexString(convertSceneContent.getAddress()), 4) + StringUtils.addZeroForNum(Integer.toHexString(convertSceneContent.getZone()), 4) + convertSceneContent.getInstruct();
            showLoadingDialog(getString(R.string.saving));
            Cg485Helper.setInstruct(this, 2, this.mInstruct.getCmdIdx(), str, this.mInstruct.getCmd(), this.mInstruct.getDataFormat(), new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActInstructSetting$$ExternalSyntheticLambda5
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActInstructSetting.this.lambda$onActivityResult$6(convertSceneContent, (ResponseMsg) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onActivityResult$6(Rs485ExtParam.RelateInfo relateInfo, ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            this.mInstruct.setActionName(Cg485Helper.getInstructName(relateInfo));
            this.mInstruct.setBindAction(relateInfo);
            Cg485Helper.updateParamExt(this, true, new boolean[0]);
            showBindInfo();
            return;
        }
        showErrorDialog(getString(R.string.save_fail));
    }
}