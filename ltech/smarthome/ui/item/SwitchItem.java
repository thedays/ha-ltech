package com.ltech.smarthome.ui.item;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes4.dex */
public class SwitchItem {
    private BindingCommand action;
    private LiveData<Boolean> checked;
    private MutableLiveData<Boolean> mChecked;
    private String name;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        setChecked(!isChecked().getValue().booleanValue());
    }

    public SwitchItem(String name) {
        MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>(false);
        this.mChecked = mutableLiveData;
        this.checked = mutableLiveData;
        this.action = new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.item.SwitchItem$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                SwitchItem.this.lambda$new$0();
            }
        });
        this.name = name;
    }

    public SwitchItem(String name, boolean checked) {
        MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>(false);
        this.mChecked = mutableLiveData;
        this.checked = mutableLiveData;
        this.action = new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.item.SwitchItem$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                SwitchItem.this.lambda$new$0();
            }
        });
        this.name = name;
        setChecked(checked);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LiveData<Boolean> isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.mChecked.setValue(Boolean.valueOf(checked));
    }

    public BindingCommand getCheckedAction() {
        return this.action;
    }

    public void setCheckedAction(BindingCommand action) {
        this.action = action;
    }
}