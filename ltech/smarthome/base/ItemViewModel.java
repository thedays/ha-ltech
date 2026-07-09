package com.ltech.smarthome.base;

import com.ltech.smarthome.base.BaseViewModel;

/* loaded from: classes3.dex */
public abstract class ItemViewModel<VM extends BaseViewModel> {
    protected VM viewModel;

    public ItemViewModel(VM viewModel) {
        this.viewModel = viewModel;
    }
}