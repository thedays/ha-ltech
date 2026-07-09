package com.ltech.smarthome.view.dialog;

import android.os.CountDownTimer;
import android.view.View;
import com.blankj.utilcode.util.ClipboardUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.StringUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.databinding.DialogMatterQrcodeBinding;
import com.ltech.smarthome.utils.DateUtils;
import com.ltech.smarthome.utils.QrcodeUtil;
import com.ltech.smarthome.utils.SmartToast;

/* loaded from: classes4.dex */
public class MatterQrDialog extends SmartDialog<DialogMatterQrcodeBinding> {
    private OnMatterCallBack callBack;
    private String code;
    private String qrcode;
    private int showType;

    public interface OnMatterCallBack {
        void onRefresh();
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_matter_qrcode;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "MatterQrDialog";
    }

    public static MatterQrDialog asDefault() {
        return (MatterQrDialog) new MatterQrDialog().setViewConverter(new SmartDialog.ViewConverter<DialogMatterQrcodeBinding, MatterQrDialog>() { // from class: com.ltech.smarthome.view.dialog.MatterQrDialog.1
            private CountDownTimer mCountDownTimer;

            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Type inference failed for: r2v2, types: [com.ltech.smarthome.view.dialog.MatterQrDialog$1$2] */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(DialogMatterQrcodeBinding viewBinding, MatterQrDialog dialog) {
                final DialogMatterQrcodeBinding dialogMatterQrcodeBinding;
                final MatterQrDialog matterQrDialog;
                dialog.setDismissRunnable(new Runnable() { // from class: com.ltech.smarthome.view.dialog.MatterQrDialog.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (AnonymousClass1.this.mCountDownTimer != null) {
                            AnonymousClass1.this.mCountDownTimer.cancel();
                            AnonymousClass1.this.mCountDownTimer = null;
                        }
                    }
                });
                if (dialog.showType == 0) {
                    viewBinding.groupLoading.setVisibility(0);
                    viewBinding.groupSuccess.setVisibility(4);
                    viewBinding.groupFail.setVisibility(4);
                    viewBinding.tvRefresh.setText(dialog.getString(R.string.loading));
                    viewBinding.ivRefresh.setVisibility(8);
                    viewBinding.progress.start();
                    CountDownTimer countDownTimer = this.mCountDownTimer;
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                        this.mCountDownTimer = null;
                    }
                    dialogMatterQrcodeBinding = viewBinding;
                    matterQrDialog = dialog;
                } else if (dialog.showType == 1) {
                    viewBinding.progress.stop();
                    viewBinding.groupLoading.setVisibility(4);
                    viewBinding.groupSuccess.setVisibility(0);
                    viewBinding.groupFail.setVisibility(4);
                    viewBinding.ivRefresh.setVisibility(8);
                    viewBinding.tvRefresh.setText(String.format(dialog.getString(R.string.app_str_effective_time), "10:00"));
                    dialogMatterQrcodeBinding = viewBinding;
                    matterQrDialog = dialog;
                    this.mCountDownTimer = new CountDownTimer(600000L, 1000L) { // from class: com.ltech.smarthome.view.dialog.MatterQrDialog.1.2
                        @Override // android.os.CountDownTimer
                        public void onTick(long millisUntilFinished) {
                            if (dialogMatterQrcodeBinding == null || matterQrDialog.getContext() == null) {
                                return;
                            }
                            dialogMatterQrcodeBinding.tvRefresh.setText(String.format(StringUtils.getString(R.string.app_str_effective_time), DateUtils.getStrTime2(Integer.valueOf((int) (millisUntilFinished / 1000)))));
                        }

                        @Override // android.os.CountDownTimer
                        public void onFinish() {
                            matterQrDialog.showTimeOut();
                            AnonymousClass1.this.mCountDownTimer.cancel();
                            AnonymousClass1.this.mCountDownTimer = null;
                        }
                    }.start();
                    if (!StringUtils.isEmpty(matterQrDialog.code)) {
                        dialogMatterQrcodeBinding.tvNum.setText(matterQrDialog.code);
                    }
                    if (!StringUtils.isEmpty(matterQrDialog.qrcode)) {
                        dialogMatterQrcodeBinding.ivQrCode.setImageBitmap(QrcodeUtil.createQRCodeBitmap(matterQrDialog.qrcode, ConvertUtils.dp2px(127.0f), ConvertUtils.dp2px(127.0f)));
                    }
                } else {
                    dialogMatterQrcodeBinding = viewBinding;
                    matterQrDialog = dialog;
                    if (matterQrDialog.showType == 2) {
                        dialogMatterQrcodeBinding.progress.stop();
                        dialogMatterQrcodeBinding.groupLoading.setVisibility(4);
                        dialogMatterQrcodeBinding.groupSuccess.setVisibility(4);
                        dialogMatterQrcodeBinding.groupFail.setVisibility(0);
                        dialogMatterQrcodeBinding.tvRefresh.setText(matterQrDialog.getString(R.string.app_str_matter_qrcode_fail));
                        dialogMatterQrcodeBinding.ivRefresh.setVisibility(0);
                        CountDownTimer countDownTimer2 = this.mCountDownTimer;
                        if (countDownTimer2 != null) {
                            countDownTimer2.cancel();
                            this.mCountDownTimer = null;
                        }
                    } else if (matterQrDialog.showType == 3) {
                        dialogMatterQrcodeBinding.progress.stop();
                        dialogMatterQrcodeBinding.groupLoading.setVisibility(4);
                        dialogMatterQrcodeBinding.groupSuccess.setVisibility(0);
                        dialogMatterQrcodeBinding.groupFail.setVisibility(8);
                        dialogMatterQrcodeBinding.ivRefresh.setVisibility(0);
                        dialogMatterQrcodeBinding.tvRefresh.setText(matterQrDialog.getString(R.string.app_str_matter_qrcode_lose));
                    }
                }
                dialogMatterQrcodeBinding.tvCopy.setOnClickListener(new View.OnClickListener(this) { // from class: com.ltech.smarthome.view.dialog.MatterQrDialog.1.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        ClipboardUtils.copyText(dialogMatterQrcodeBinding.tvNum.getText());
                        SmartToast.showCenterShort(matterQrDialog.getString(R.string.copy_success));
                    }
                });
                dialogMatterQrcodeBinding.viewClose.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.view.dialog.MatterQrDialog.1.4
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        if (AnonymousClass1.this.mCountDownTimer != null) {
                            AnonymousClass1.this.mCountDownTimer.cancel();
                            AnonymousClass1.this.mCountDownTimer = null;
                        }
                        matterQrDialog.dismissDialog();
                    }
                });
                dialogMatterQrcodeBinding.ivRefresh.setOnClickListener(new View.OnClickListener(this) { // from class: com.ltech.smarthome.view.dialog.MatterQrDialog.1.5
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        if (matterQrDialog.showType == 0 || matterQrDialog.showType == 1) {
                            return;
                        }
                        matterQrDialog.showLoading();
                        if (matterQrDialog.callBack != null) {
                            matterQrDialog.callBack.onRefresh();
                        }
                    }
                });
                dialogMatterQrcodeBinding.tvRefresh.setOnClickListener(new View.OnClickListener(this) { // from class: com.ltech.smarthome.view.dialog.MatterQrDialog.1.6
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        if (matterQrDialog.showType == 0 || matterQrDialog.showType == 1) {
                            return;
                        }
                        matterQrDialog.showLoading();
                        if (matterQrDialog.callBack != null) {
                            matterQrDialog.callBack.onRefresh();
                        }
                    }
                });
            }
        }).setOutCancel(false).setGravity(17);
    }

    public MatterQrDialog setOnMatterCallBack(OnMatterCallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    public MatterQrDialog showLoading() {
        this.showType = 0;
        notifyDialog();
        return this;
    }

    public MatterQrDialog showSuccess(String qrcode, String code) {
        this.code = code;
        this.qrcode = qrcode;
        this.showType = 1;
        notifyDialog();
        return this;
    }

    public MatterQrDialog showFail() {
        this.showType = 2;
        notifyDialog();
        return this;
    }

    public MatterQrDialog showTimeOut() {
        this.showType = 3;
        notifyDialog();
        return this;
    }
}