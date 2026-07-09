package com.ltech.smarthome.nfc.dialog;

import android.text.TextUtils;
import androidx.core.content.ContextCompat;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.databinding.DialogResultBinding;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes4.dex */
public class ResultDialog extends SmartDialog<DialogResultBinding> {
    private boolean result;
    private String title = "";
    private String tip = "";
    private boolean autoClose = false;
    private int tipColor = R.color.color_text_light_black;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_result;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return null;
    }

    public static ResultDialog asDefault() {
        return (ResultDialog) new ResultDialog().setViewConverter(new SmartDialog.ViewConverter<DialogResultBinding, ResultDialog>() { // from class: com.ltech.smarthome.nfc.dialog.ResultDialog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(DialogResultBinding viewBinding, final ResultDialog dialog) {
                if (dialog.result) {
                    viewBinding.ivResult.setImageResource(R.mipmap.pic_nfc_succeeded);
                } else {
                    viewBinding.ivResult.setImageResource(R.mipmap.pic_nfc_failed);
                }
                viewBinding.tvTitle.setText(dialog.title);
                if (TextUtils.isEmpty(dialog.tip)) {
                    viewBinding.tvTip.setVisibility(8);
                } else {
                    viewBinding.tvTip.setText(dialog.tip);
                    viewBinding.tvTip.setTextColor(ContextCompat.getColor(ActivityUtils.getTopActivity(), dialog.tipColor));
                }
                if (dialog.autoClose) {
                    new Timer().schedule(new TimerTask(this) { // from class: com.ltech.smarthome.nfc.dialog.ResultDialog.1.1
                        @Override // java.util.TimerTask, java.lang.Runnable
                        public void run() {
                            dialog.dismissAllowingStateLoss();
                        }
                    }, 1500L);
                }
            }
        }).setGravity(80).setY(16).setMargin(16).setOutCancel(true);
    }

    public ResultDialog setResult(boolean result) {
        this.result = result;
        return this;
    }

    public ResultDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public ResultDialog setTip(String tip) {
        this.tip = tip;
        return this;
    }

    public ResultDialog setAutoClose(boolean autoClose) {
        this.autoClose = autoClose;
        return this;
    }

    public ResultDialog setTipColor(int tipColor) {
        this.tipColor = tipColor;
        return this;
    }
}