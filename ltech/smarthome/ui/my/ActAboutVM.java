package com.ltech.smarthome.ui.my;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.net.response.VersionInfo;
import com.ltech.smarthome.ui.item.SettingItem;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

/* loaded from: classes4.dex */
public class ActAboutVM extends BaseViewModel {
    public ObservableList<SettingItem> mObservableList = new ObservableArrayList();
    public SingleLiveEvent<Void> showUpgradeAppDialogEvent = new SingleLiveEvent<>();
    public ObservableField<VersionInfo> versionObservable = new ObservableField<>();
    public ItemBinding<SettingItem> itemBinding = ItemBinding.of(new OnItemBind() { // from class: com.ltech.smarthome.ui.my.ActAboutVM$$ExternalSyntheticLambda0
        @Override // me.tatarka.bindingcollectionadapter2.OnItemBind
        public final void onItemBind(ItemBinding itemBinding, int i, Object obj) {
            itemBinding.set(40, R.layout.item_setting);
        }
    });

    static /* synthetic */ void lambda$actionFindNewVersion$0() {
    }

    static /* synthetic */ void lambda$actionFindNotNewVersion$1() {
    }

    private void actionFindNewVersion() {
        this.mObservableList.set(0, new SettingItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.app_check_version)).setGoRes(R.mipmap.icon_more).setSubImageRes(R.drawable.dot_red).setSubText(ActivityUtils.getTopActivity().getString(R.string.app_find_new_version)).setMainResShow(false).setSubResShow(true).setSubTextShow(true).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.my.ActAboutVM$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActAboutVM.lambda$actionFindNewVersion$0();
            }
        })));
    }

    private void actionFindNotNewVersion() {
        this.mObservableList.set(0, new SettingItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.app_check_version)).setGoRes(R.mipmap.icon_more).setSubImageRes(R.drawable.dot_red).setSubText(ActivityUtils.getTopActivity().getString(R.string.app_find_new_version)).setMainResShow(false).setSubResShow(false).setSubTextShow(false).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.my.ActAboutVM$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActAboutVM.lambda$actionFindNotNewVersion$1();
            }
        })));
    }
}