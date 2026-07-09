package com.ltech.smarthome.model.extra;

import androidx.databinding.ObservableField;

/* loaded from: classes4.dex */
public class ProtocolDefault {
    private ObservableField<Boolean> checkedProtocol = new ObservableField<>(true);
    private ObservableField<CharSequence> protocolText = new ObservableField<>("");

    public ObservableField<Boolean> getCheckedProtocol() {
        return this.checkedProtocol;
    }

    public void setCheckedProtocol(boolean checked) {
        this.checkedProtocol.set(Boolean.valueOf(checked));
    }

    public ObservableField<CharSequence> getProtocolText() {
        return this.protocolText;
    }

    public void setProtocolText(CharSequence text) {
        this.protocolText.set(text);
    }
}