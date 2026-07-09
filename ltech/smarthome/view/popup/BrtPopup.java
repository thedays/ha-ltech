package com.ltech.smarthome.view.popup;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import com.blankj.utilcode.util.SizeUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.RectProgressBar2;
import com.smart.message.utils.LHomeLog;
import com.zyyoona7.popup.BasePopup;

/* loaded from: classes4.dex */
public class BrtPopup extends BasePopup<BrtPopup> {
    public static final int CW_BRT = 2;
    public static final int RGB_BRT = 1;
    private int brt;
    private Context context;
    private Object controlObject;
    private int flag;
    private boolean on;
    private OnBrtCallback onBrtCallback;
    private RectProgressBar2 rectProgressBar;
    private TextView tvBrt;

    public interface OnBrtCallback {
        void onBrtChange(int brt);
    }

    public static BrtPopup create(Context context) {
        return new BrtPopup(context);
    }

    protected BrtPopup(Context context) {
        this.context = context;
        setContext(context);
    }

    @Override // com.zyyoona7.popup.BasePopup
    protected void initAttributes() {
        setContentView(R.layout.popup_brt, SizeUtils.dp2px(100.0f), SizeUtils.dp2px(280.0f));
        setFocusAndOutsideEnable(false).setBackgroundDimEnable(true).setFocusAndOutsideEnable(true).setDimValue(0.5f);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.zyyoona7.popup.BasePopup
    public void initViews(View view, BrtPopup popup) {
        this.tvBrt = (TextView) findViewById(R.id.tv_brt);
        RectProgressBar2 rectProgressBar2 = (RectProgressBar2) findViewById(R.id.progressBar);
        this.rectProgressBar = rectProgressBar2;
        rectProgressBar2.setCanChangeProgress(true);
        this.rectProgressBar.setOnProgressChangeListener(new RectProgressBar2.OnProgressChangeListener() { // from class: com.ltech.smarthome.view.popup.BrtPopup.1
            @Override // com.ltech.smarthome.view.RectProgressBar2.OnProgressChangeListener
            public void onStartProgressChanged(RectProgressBar2 bar) {
            }

            @Override // com.ltech.smarthome.view.RectProgressBar2.OnProgressChangeListener
            public void onProgressChanged(RectProgressBar2 bar) {
                LHomeLog.i(getClass(), "onProgressChanged brt-->" + bar.getProgressPercent());
                BrtPopup.this.tvBrt.setText(bar.getProgressPercent());
                if (BrtPopup.this.flag == 1) {
                    CmdAssistant.getLightCmdAssistant(BrtPopup.this.controlObject, new int[0]).sendRgbBrtHas1to9(BrtPopup.this.context, bar.getProgress(), false);
                } else if (BrtPopup.this.flag == 2) {
                    CmdAssistant.getLightCmdAssistant(BrtPopup.this.controlObject, new int[0]).sendWyBrtHas1to9(BrtPopup.this.context, bar.getProgress(), false);
                }
            }

            @Override // com.ltech.smarthome.view.RectProgressBar2.OnProgressChangeListener
            public void onStopProgressChanged(RectProgressBar2 bar) {
                BrtPopup.this.tvBrt.setText(bar.getProgressPercent());
                if (BrtPopup.this.flag == 1) {
                    CmdAssistant.getLightCmdAssistant(BrtPopup.this.controlObject, new int[0]).sendRgbBrtHas1to9(BrtPopup.this.context, bar.getProgress(), true);
                } else if (BrtPopup.this.flag == 2) {
                    CmdAssistant.getLightCmdAssistant(BrtPopup.this.controlObject, new int[0]).sendWyBrtHas1to9(BrtPopup.this.context, bar.getProgress(), true);
                }
                if (BrtPopup.this.onBrtCallback != null) {
                    BrtPopup.this.onBrtCallback.onBrtChange(bar.getProgress());
                }
            }
        });
        final AppCompatImageView appCompatImageView = (AppCompatImageView) findViewById(R.id.iv_open);
        appCompatImageView.setBackgroundResource(this.on ? R.mipmap.ic_power_pressed : R.mipmap.ic_power_off);
        appCompatImageView.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.view.popup.BrtPopup$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                BrtPopup.this.lambda$initViews$0(appCompatImageView, view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(AppCompatImageView appCompatImageView, View view) {
        boolean z = !this.on;
        this.on = z;
        this.rectProgressBar.setCanChangeProgress(z);
        if (this.on) {
            int max = Math.max(this.brt, 1);
            this.brt = max;
            this.rectProgressBar.setAnimation(0, max);
            this.tvBrt.setText(this.brt + "%");
        } else {
            int progress = this.rectProgressBar.getProgress();
            this.brt = progress;
            this.rectProgressBar.setAnimation(progress, 0);
            this.tvBrt.setText(R.string.close);
        }
        appCompatImageView.setBackgroundResource(this.on ? R.mipmap.ic_power_pressed : R.mipmap.ic_power_off);
        int i = this.flag;
        if (i == 1) {
            CmdAssistant.getLightCmdAssistant(this.controlObject, new int[0]).sendRgbOnOff(this.context, this.on);
        } else if (i == 2) {
            CmdAssistant.getLightCmdAssistant(this.controlObject, new int[0]).sendWyOnOff(this.context, this.on);
        }
    }

    public BrtPopup setFlag(int flag) {
        this.flag = flag;
        return this;
    }

    public BrtPopup setOn(boolean on) {
        this.on = on;
        return this;
    }

    public BrtPopup setBrt(int brt) {
        this.brt = brt;
        if (this.on) {
            this.tvBrt.setText(brt + "%");
        } else {
            this.tvBrt.setText(R.string.close);
        }
        this.rectProgressBar.setCanChangeProgress(this.on);
        this.rectProgressBar.setCurrentProgress(brt);
        return this;
    }

    public BrtPopup setControlObject(Object object) {
        this.controlObject = object;
        return this;
    }

    public BrtPopup setOnBrtCallback(OnBrtCallback onTotalBrtCallback) {
        this.onBrtCallback = onTotalBrtCallback;
        return this;
    }
}