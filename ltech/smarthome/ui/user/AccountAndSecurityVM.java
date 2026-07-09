package com.ltech.smarthome.ui.user;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.ui.ifly.WebViewActivity;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.login.ActChangePwd;
import com.ltech.smarthome.utils.NavUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/* loaded from: classes4.dex */
public class AccountAndSecurityVM extends BaseViewModel {
    public ObservableList<GoItem> mObservableList = new ObservableArrayList();
    public SingleLiveEvent<Void> showNoPermissionDialogEvent = new SingleLiveEvent<>();
    public ItemBinding<GoItem> itemBinding = ItemBinding.of(40, R.layout.item_go_1);

    public AccountAndSecurityVM() {
        initMyList();
    }

    public void initMyList() {
        this.mObservableList.add(new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.change_pwd)).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.user.AccountAndSecurityVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                AccountAndSecurityVM.this.lambda$initMyList$0();
            }
        })));
        this.mObservableList.add(new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.disable_account)).setSubText(ActivityUtils.getTopActivity().getString(R.string.disable_account_tip)).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.user.AccountAndSecurityVM$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                NavUtils.destination(WebViewActivity.class).withString(WebViewActivity.EXTRA_CUSTOM_URL, ActivityUtils.getTopActivity().getString(R.string.user_cancellation_account_url)).navigation(ActivityUtils.getTopActivity());
            }
        })));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initMyList$0() {
        navigation(NavUtils.destination(ActChangePwd.class));
    }
}