package com.ltech.smarthome.view.dialog;

import android.text.TextUtils;
import android.view.View;
import androidx.databinding.ObservableField;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogFrequencyIntervalBinding;
import com.ltech.smarthome.view.dialog.DeviceFrequencyAndIntervalDialog;

/* loaded from: classes4.dex */
public class DeviceFrequencyAndIntervalDialog extends SmartDialog<DialogFrequencyIntervalBinding> {
    private IAction<FrequencyAndIntervalBean> confirmAction;
    private ObservableField<String> content = new ObservableField<>();
    private ObservableField<String> content2 = new ObservableField<>();
    private String title;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_frequency_interval;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.DeviceFrequencyAndIntervalDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogFrequencyIntervalBinding, DeviceFrequencyAndIntervalDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogFrequencyIntervalBinding viewBinding, final DeviceFrequencyAndIntervalDialog dialog) {
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.setContent(dialog.content);
            viewBinding.setContent2(dialog.content2);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.DeviceFrequencyAndIntervalDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    DeviceFrequencyAndIntervalDialog.AnonymousClass1.lambda$convertView$0(DeviceFrequencyAndIntervalDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(DeviceFrequencyAndIntervalDialog deviceFrequencyAndIntervalDialog, View view) {
            switch (view.getId()) {
                case R.id.iv_delete /* 2131297006 */:
                    deviceFrequencyAndIntervalDialog.setContent("");
                    break;
                case R.id.iv_delete2 /* 2131297007 */:
                    deviceFrequencyAndIntervalDialog.setContent2("");
                    break;
                case R.id.tv_cancel /* 2131298504 */:
                    deviceFrequencyAndIntervalDialog.dismissDialog();
                    break;
                case R.id.tv_ok /* 2131298840 */:
                    deviceFrequencyAndIntervalDialog.dismissDialog();
                    if (deviceFrequencyAndIntervalDialog.confirmAction != null) {
                        FrequencyAndIntervalBean frequencyAndIntervalBean = new FrequencyAndIntervalBean();
                        String str = (String) deviceFrequencyAndIntervalDialog.content2.get();
                        int i = -1;
                        int parseInt = (TextUtils.isEmpty(str) || str == null) ? -1 : Integer.parseInt(str);
                        String str2 = (String) deviceFrequencyAndIntervalDialog.content.get();
                        if (!TextUtils.isEmpty(str2) && str2 != null) {
                            i = Integer.parseInt(str2);
                        }
                        frequencyAndIntervalBean.setFrequency(parseInt);
                        frequencyAndIntervalBean.setInterval(i);
                        deviceFrequencyAndIntervalDialog.confirmAction.act(frequencyAndIntervalBean);
                        break;
                    }
                    break;
            }
        }
    }

    public static DeviceFrequencyAndIntervalDialog asDefault() {
        return (DeviceFrequencyAndIntervalDialog) new DeviceFrequencyAndIntervalDialog().setViewConverter(new AnonymousClass1()).setGravity(17).setAnimStyle(R.style.iOSDialogAnimStyle).setOutCancel(false).setMargin(40);
    }

    public DeviceFrequencyAndIntervalDialog setContent(String content) {
        this.content.set(content);
        return this;
    }

    public DeviceFrequencyAndIntervalDialog setContent2(String content) {
        this.content2.set(content);
        return this;
    }

    public DeviceFrequencyAndIntervalDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public DeviceFrequencyAndIntervalDialog setConfirmAction(IAction<FrequencyAndIntervalBean> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "device_send_times_dialog";
    }

    public static class FrequencyAndIntervalBean {
        public int frequency;
        public int interval;

        public int getFrequency() {
            return this.frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        public int getInterval() {
            return this.interval;
        }

        public void setInterval(int interval) {
            this.interval = interval;
        }
    }
}