package com.ltech.smarthome.ui.scene.local;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.SeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSelectVoiceSpeakBinding;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.smart.product_agreement.param.SuperPanelCmdParam;
import java.util.Locale;

/* loaded from: classes4.dex */
public class ActSelectVoiceSpeak extends BaseNormalActivity<ActSelectVoiceSpeakBinding> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_voice_speak;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.voice_speak));
        setEditString(getString(R.string.save));
        ((ActSelectVoiceSpeakBinding) this.mViewBinding).setVoiceContent(getIntent().getStringExtra("content"));
        ((ActSelectVoiceSpeakBinding) this.mViewBinding).title.tvEdit.setTextColor(getResources().getColor(R.color.color_text_gray));
        ((ActSelectVoiceSpeakBinding) this.mViewBinding).title.tvEdit.addTextChangedListener(new TextWatcher() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectVoiceSpeak.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    ((ActSelectVoiceSpeakBinding) ActSelectVoiceSpeak.this.mViewBinding).title.tvEdit.setTextColor(ActSelectVoiceSpeak.this.getResources().getColor(R.color.color_text_black));
                } else {
                    ((ActSelectVoiceSpeakBinding) ActSelectVoiceSpeak.this.mViewBinding).title.tvEdit.setTextColor(ActSelectVoiceSpeak.this.getResources().getColor(R.color.color_text_gray));
                }
            }
        });
        ((ActSelectVoiceSpeakBinding) this.mViewBinding).sbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectVoiceSpeak.2
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ((ActSelectVoiceSpeakBinding) ActSelectVoiceSpeak.this.mViewBinding).tvVolume.setText(String.format(Locale.US, "%d%s", Integer.valueOf(seekBar.getProgress()), "%"));
            }
        });
        String stringExtra = getIntent().getStringExtra(Constants.SCENE_INSTRUCT);
        if (!TextUtils.isEmpty(stringExtra) && stringExtra.length() >= 6) {
            ((ActSelectVoiceSpeakBinding) this.mViewBinding).sbVolume.setProgress(Integer.parseInt(stringExtra.substring(4, 6), 16));
        }
        ((ActSelectVoiceSpeakBinding) this.mViewBinding).tvVolume.setText(String.format(Locale.US, "%d%s", Integer.valueOf(((ActSelectVoiceSpeakBinding) this.mViewBinding).sbVolume.getProgress()), "%"));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (TextUtils.isEmpty(((ActSelectVoiceSpeakBinding) this.mViewBinding).getVoiceContent())) {
            return;
        }
        SuperPanelCmdParam superPanelCmdParam = new SuperPanelCmdParam();
        superPanelCmdParam.setCmdType(1);
        superPanelCmdParam.setSound(((ActSelectVoiceSpeakBinding) this.mViewBinding).sbVolume.getProgress());
        superPanelCmdParam.addExtParam(SceneHelper.OPTION, "2");
        superPanelCmdParam.addExtParam(SceneHelper.OPTION_VALUE, ((ActSelectVoiceSpeakBinding) this.mViewBinding).getVoiceContent());
        SceneHelper.instance().cmdParam = superPanelCmdParam;
        SceneHelper.instance().maskType = MaskType.LOCAL;
        setResult(3001);
        finishActivity();
    }
}