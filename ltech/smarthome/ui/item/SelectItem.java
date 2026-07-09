package com.ltech.smarthome.ui.item;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes4.dex */
public class SelectItem {
    private BindingCommand action;
    private MutableLiveData<Boolean> mutSelect;
    private String name;
    private LiveData<Boolean> select;
    private String subName;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        setSelect(!isSelect().getValue().booleanValue());
    }

    public SelectItem(String name) {
        MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>(false);
        this.mutSelect = mutableLiveData;
        this.select = mutableLiveData;
        this.action = new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.item.SelectItem$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                SelectItem.this.lambda$new$0();
            }
        });
        this.name = name;
    }

    public SelectItem(String name, boolean select) {
        MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>(false);
        this.mutSelect = mutableLiveData;
        this.select = mutableLiveData;
        this.action = new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.item.SelectItem$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                SelectItem.this.lambda$new$0();
            }
        });
        this.name = name;
        setSelect(select);
    }

    public SelectItem(String name, String subName) {
        MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>(false);
        this.mutSelect = mutableLiveData;
        this.select = mutableLiveData;
        this.action = new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.item.SelectItem$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                SelectItem.this.lambda$new$0();
            }
        });
        this.name = name;
        this.subName = subName;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LiveData<Boolean> isSelect() {
        return this.select;
    }

    public BindingCommand getAction() {
        return this.action;
    }

    public void setAction(BindingCommand action) {
        this.action = action;
    }

    public void setSelect(boolean select) {
        this.mutSelect.setValue(Boolean.valueOf(select));
    }

    public String getSubName() {
        return this.subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }
}