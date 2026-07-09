package com.ltech.smarthome.ui.device.eurpanel;

import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActBrtButtonSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.smart.message.utils.StringUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/* loaded from: classes4.dex */
public class ActBrtButtonSetting extends BaseNormalActivity<ActBrtButtonSettingBinding> {
    private int brtValue;
    private Device device;
    private Group group;
    private boolean groupControl;
    private RelateInfoAssistant relateInfoAssistant;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_brt_button_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.brt_button_setting));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        boolean booleanExtra = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        this.groupControl = booleanExtra;
        if (booleanExtra) {
            this.group = Injection.repo().group().getGroupByGroupId(getIntent().getLongExtra("device_id", -1L));
            this.relateInfoAssistant = new RelateInfoAssistant(this.group);
        } else {
            this.device = Injection.repo().device().getDeviceByDeviceId(getIntent().getLongExtra("device_id", -1L));
            this.relateInfoAssistant = new RelateInfoAssistant(this.device);
        }
        if (!TextUtils.isEmpty(this.relateInfoAssistant.getSceneDimmerBrt())) {
            changeBrtValue(Math.round((Integer.parseInt(this.relateInfoAssistant.getSceneDimmerBrt().substring(4, 6), 16) * 100.0f) / 255.0f));
        } else {
            changeBrtValue(1);
        }
        ((ActBrtButtonSettingBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActBrtButtonSetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActBrtButtonSetting.this.lambda$initView$0((View) obj);
            }
        }));
        ((ActBrtButtonSettingBinding) this.mViewBinding).brtStep.setOnRangeChangedListener(new OnRangeChangedListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActBrtButtonSetting.1
            @Override // com.jaygoo.widget.OnRangeChangedListener
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {
            }

            @Override // com.jaygoo.widget.OnRangeChangedListener
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {
            }

            @Override // com.jaygoo.widget.OnRangeChangedListener
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                if (ActBrtButtonSetting.this.brtValue != 0) {
                    ActBrtButtonSetting.this.brtValue = (int) leftValue;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        int id = view.getId();
        if (id != R.id.layout_diff) {
            if (id != R.id.layout_same) {
                return;
            }
            changeBrtValue(0);
        } else if (this.brtValue == 0) {
            changeBrtValue(1);
        }
    }

    private void changeBrtValue(int value) {
        this.brtValue = value;
        if (value == 0) {
            ((ActBrtButtonSettingBinding) this.mViewBinding).ivBrt1.setImageResource(R.mipmap.ic_tick_default);
            ((ActBrtButtonSettingBinding) this.mViewBinding).ivBrt2.setImageResource(R.mipmap.ic_tick_sel);
            ((ActBrtButtonSettingBinding) this.mViewBinding).brtStep.setProgress(1.0f);
            ((ActBrtButtonSettingBinding) this.mViewBinding).brtStep.setEnabled(false);
            ((ActBrtButtonSettingBinding) this.mViewBinding).brtStep.getLeftSeekBar().setThumbDrawableId(R.mipmap.icon_gray_thumb);
            ((ActBrtButtonSettingBinding) this.mViewBinding).brtStep.setTickMarkTextColor(getResources().getColor(R.color.color_text_gray));
            return;
        }
        ((ActBrtButtonSettingBinding) this.mViewBinding).ivBrt1.setImageResource(R.mipmap.ic_tick_sel);
        ((ActBrtButtonSettingBinding) this.mViewBinding).ivBrt2.setImageResource(R.mipmap.ic_tick_default);
        ((ActBrtButtonSettingBinding) this.mViewBinding).brtStep.setEnabled(true);
        ((ActBrtButtonSettingBinding) this.mViewBinding).brtStep.setProgress(value);
        ((ActBrtButtonSettingBinding) this.mViewBinding).brtStep.getLeftSeekBar().setThumbDrawableId(R.mipmap.icon_red_thumb);
        ((ActBrtButtonSettingBinding) this.mViewBinding).brtStep.setTickMarkTextColor(getResources().getColor(R.color.color_text_black));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        Device device = this.device;
        if (device != null && ProductId.ID_RC_B8.equals(device.getProductId())) {
            RelateInfoUtils.showImageTipDialog(this.device, this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActBrtButtonSetting$$ExternalSyntheticLambda5
                @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                    ActBrtButtonSetting.this.lambda$edit$1(imageTipDialog);
                }
            });
        } else {
            save();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$1(ImageTipDialog imageTipDialog) {
        imageTipDialog.dismiss();
        save();
    }

    private void save() {
        final int round = Math.round((this.brtValue * 255.0f) / 100.0f);
        showLoadingDialog(getString(R.string.saving));
        CmdAssistant.getSettingCmdAssistant(this.groupControl ? this.group : this.device, new int[0]).setBrtButton(this, round, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActBrtButtonSetting$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActBrtButtonSetting.this.lambda$save$4(round, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$4(int i, Boolean bool) {
        String sb;
        if (bool.booleanValue()) {
            if (i == 0) {
                sb = "010000";
            } else {
                StringBuilder sb2 = new StringBuilder("02");
                long j = i;
                sb2.append(StringUtils.demToHex(j, 2));
                sb2.append(StringUtils.demToHex(j, 2));
                sb = sb2.toString();
            }
            this.relateInfoAssistant.setSceneDimmerBrt(sb);
            if (this.groupControl) {
                ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(this.group.getGroupId(), this.relateInfoAssistant.getExtParamString()).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActBrtButtonSetting$$ExternalSyntheticLambda2
                    @Override // io.reactivex.functions.Action
                    public final void run() {
                        ActBrtButtonSetting.this.dismissLoadingDialog();
                    }
                }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActBrtButtonSetting$$ExternalSyntheticLambda3
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActBrtButtonSetting.this.lambda$save$2(obj);
                    }
                }, new SmartErrorComsumer());
                return;
            } else {
                ReplaceHelper.instance().backupData(this.activity, this.device.getDeviceId(), UpdateBackDataRequest.BRIGHTNESS_BUTTON, CmdAssistant.getSettingCmdAssistant(this.device, new int[0]).setBrtButton(i));
                ((ObservableSubscribeProxy) Injection.net().updateParamExt(this.device.getDeviceId(), this.relateInfoAssistant.getExtParamString()).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActBrtButtonSetting$$ExternalSyntheticLambda2
                    @Override // io.reactivex.functions.Action
                    public final void run() {
                        ActBrtButtonSetting.this.dismissLoadingDialog();
                    }
                }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActBrtButtonSetting$$ExternalSyntheticLambda4
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActBrtButtonSetting.this.lambda$save$3(obj);
                    }
                }, new SmartErrorComsumer());
                return;
            }
        }
        showErrorDialog(getString(R.string.save_fail));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$2(Object obj) throws Exception {
        setResult(-1);
        this.group.setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().group().saveGroup(this.group);
        SmartToast.showShort(R.string.save_success);
        finishActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$3(Object obj) throws Exception {
        setResult(-1);
        this.device.setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().device().saveDevice(this.device);
        SmartToast.showShort(R.string.save_success);
        finishActivity();
    }
}