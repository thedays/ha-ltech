package com.ltech.smarthome.ui.scene;

import android.text.TextUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActDmxChannelSelectBinding;
import com.ltech.smarthome.ui.device.e6knob.E6Helper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.view.StepSetView;
import java.util.Locale;

/* loaded from: classes4.dex */
public class ActDmxChannelSelect extends BaseNormalActivity<ActDmxChannelSelectBinding> {
    private boolean isE6;
    private int lightType;
    public int[] channel = {255, 255, 255, 255, 255};
    public int brt = 255;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_dmx_channel_select;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.str_channel));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.confirm));
        ((ActDmxChannelSelectBinding) this.mViewBinding).seekbarCh1.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.scene.ActDmxChannelSelect$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z, boolean z2) {
                ActDmxChannelSelect.this.lambda$initView$0(i, z, z2);
            }
        });
        ((ActDmxChannelSelectBinding) this.mViewBinding).seekbarCh2.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.scene.ActDmxChannelSelect$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z, boolean z2) {
                ActDmxChannelSelect.this.lambda$initView$1(i, z, z2);
            }
        });
        ((ActDmxChannelSelectBinding) this.mViewBinding).seekbarCh3.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.scene.ActDmxChannelSelect$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z, boolean z2) {
                ActDmxChannelSelect.this.lambda$initView$2(i, z, z2);
            }
        });
        ((ActDmxChannelSelectBinding) this.mViewBinding).seekbarCh4.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.scene.ActDmxChannelSelect$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z, boolean z2) {
                ActDmxChannelSelect.this.lambda$initView$3(i, z, z2);
            }
        });
        ((ActDmxChannelSelectBinding) this.mViewBinding).seekbarCh5.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.scene.ActDmxChannelSelect$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z, boolean z2) {
                ActDmxChannelSelect.this.lambda$initView$4(i, z, z2);
            }
        });
        initData();
        changeViewByType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(int i, boolean z, boolean z2) {
        this.channel[0] = i;
        ((ActDmxChannelSelectBinding) this.mViewBinding).seekbarCh1.setValue(getValue(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(int i, boolean z, boolean z2) {
        this.channel[1] = i;
        ((ActDmxChannelSelectBinding) this.mViewBinding).seekbarCh2.setValue(getValue(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(int i, boolean z, boolean z2) {
        this.channel[2] = i;
        ((ActDmxChannelSelectBinding) this.mViewBinding).seekbarCh3.setValue(getValue(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3(int i, boolean z, boolean z2) {
        this.channel[3] = i;
        ((ActDmxChannelSelectBinding) this.mViewBinding).seekbarCh4.setValue(getValue(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$4(int i, boolean z, boolean z2) {
        this.channel[4] = i;
        ((ActDmxChannelSelectBinding) this.mViewBinding).seekbarCh5.setValue(getValue(i));
    }

    private void initData() {
        this.isE6 = getIntent().getBooleanExtra(Constants.IS_E6, false);
        this.lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, 5);
        if (!this.isE6 || TextUtils.isEmpty(E6Helper.instance().getActionInstruct())) {
            return;
        }
        String actionInstruct = E6Helper.instance().getActionInstruct();
        if (Integer.parseInt(actionInstruct.substring(0, 2), 16) == 34) {
            this.channel[0] = Integer.parseInt(actionInstruct.substring(2, 4), 16);
            this.channel[1] = Integer.parseInt(actionInstruct.substring(4, 6), 16);
            this.channel[2] = Integer.parseInt(actionInstruct.substring(6, 8), 16);
            this.channel[3] = Integer.parseInt(actionInstruct.substring(8, 10), 16);
            this.channel[4] = Integer.parseInt(actionInstruct.substring(10, 12), 16);
        }
    }

    private void changeViewByType() {
        ((ActDmxChannelSelectBinding) this.mViewBinding).seekbarCh1.setProgress(this.channel[0]);
        ((ActDmxChannelSelectBinding) this.mViewBinding).seekbarCh1.setValue(getValue(this.channel[0]));
        ((ActDmxChannelSelectBinding) this.mViewBinding).seekbarCh2.setVisibility(this.lightType >= 2 ? 0 : 8);
        ((ActDmxChannelSelectBinding) this.mViewBinding).seekbarCh3.setVisibility(this.lightType >= 3 ? 0 : 8);
        ((ActDmxChannelSelectBinding) this.mViewBinding).seekbarCh4.setVisibility(this.lightType >= 4 ? 0 : 8);
        ((ActDmxChannelSelectBinding) this.mViewBinding).seekbarCh5.setVisibility(this.lightType < 5 ? 8 : 0);
        if (this.lightType >= 2) {
            ((ActDmxChannelSelectBinding) this.mViewBinding).seekbarCh2.setProgress(this.channel[1]);
            ((ActDmxChannelSelectBinding) this.mViewBinding).seekbarCh2.setValue(getValue(this.channel[1]));
        }
        if (this.lightType >= 3) {
            ((ActDmxChannelSelectBinding) this.mViewBinding).seekbarCh3.setProgress(this.channel[2]);
            ((ActDmxChannelSelectBinding) this.mViewBinding).seekbarCh3.setValue(getValue(this.channel[2]));
        }
        if (this.lightType >= 4) {
            ((ActDmxChannelSelectBinding) this.mViewBinding).seekbarCh4.setProgress(this.channel[3]);
            ((ActDmxChannelSelectBinding) this.mViewBinding).seekbarCh4.setValue(getValue(this.channel[3]));
        }
        if (this.lightType >= 5) {
            ((ActDmxChannelSelectBinding) this.mViewBinding).seekbarCh5.setProgress(this.channel[4]);
            ((ActDmxChannelSelectBinding) this.mViewBinding).seekbarCh5.setValue(getValue(this.channel[4]));
        }
    }

    private String getValue(int brt) {
        return String.format(Locale.US, "%s[%d]", LightUtils.brt2PercentHasBelowZero(brt) + "%", Integer.valueOf(brt));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        E6Helper.instance().selectDmxChannel(this.channel);
    }
}