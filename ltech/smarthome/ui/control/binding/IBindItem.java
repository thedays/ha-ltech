package com.ltech.smarthome.ui.control.binding;

import com.ltech.smarthome.base.BaseViewModel;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/* loaded from: classes4.dex */
public interface IBindItem<T> {
    void bindItem(BaseViewModel viewModel, ItemBinding itemBinding, int position, T item);
}