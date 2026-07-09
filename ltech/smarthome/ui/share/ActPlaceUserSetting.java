package com.ltech.smarthome.ui.share;

import android.content.Intent;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActPlaceUserSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.PlaceUser;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.view.SwitchButton;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public class ActPlaceUserSetting extends VMActivity<ActPlaceUserSettingBinding, ActPlaceUserSettingVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_place_user_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.member_setting));
        setBackImage(R.mipmap.icon_back);
        PlaceShareHelper.getInstance().enterType = 1002;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActPlaceUserSettingVM) this.mViewModel).placeListing = Injection.repo().home().getPlace(this, getIntent().getLongExtra(Constants.PLACE_ID, -1L));
        ((ActPlaceUserSettingVM) this.mViewModel).placeListing.data().observe(this, new Observer() { // from class: com.ltech.smarthome.ui.share.ActPlaceUserSetting$$ExternalSyntheticLambda11
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActPlaceUserSetting.this.lambda$startObserve$1((Resource) obj);
            }
        });
        handleData(Transformations.switchMap(((ActPlaceUserSettingVM) this.mViewModel).place, new Function1() { // from class: com.ltech.smarthome.ui.share.ActPlaceUserSetting$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$startObserve$2;
                lambda$startObserve$2 = ActPlaceUserSetting.this.lambda$startObserve$2((Place) obj);
                return lambda$startObserve$2;
            }
        }), new IAction() { // from class: com.ltech.smarthome.ui.share.ActPlaceUserSetting$$ExternalSyntheticLambda13
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActPlaceUserSetting.this.lambda$startObserve$3((List) obj);
            }
        });
        ((ActPlaceUserSettingVM) this.mViewModel).placeUser.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.share.ActPlaceUserSetting$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActPlaceUserSetting.this.lambda$startObserve$4((PlaceUser) obj);
            }
        });
        ((ActPlaceUserSettingVM) this.mViewModel).place.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.share.ActPlaceUserSetting$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActPlaceUserSetting.this.lambda$startObserve$5((Place) obj);
            }
        });
        ((ActPlaceUserSettingBinding) this.mViewBinding).sb.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.share.ActPlaceUserSetting$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                ActPlaceUserSetting.this.lambda$startObserve$6(switchButton, z);
            }
        });
        ((ActPlaceUserSettingVM) this.mViewModel).removeEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.share.ActPlaceUserSetting$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActPlaceUserSetting.this.lambda$startObserve$7((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Resource resource) {
        handleResource(resource, new IAction() { // from class: com.ltech.smarthome.ui.share.ActPlaceUserSetting$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActPlaceUserSetting.this.lambda$startObserve$0((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(List list) {
        if (list.isEmpty()) {
            showEmpty();
        } else {
            ((ActPlaceUserSettingVM) this.mViewModel).place.setValue((Place) list.get(0));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$startObserve$2(Place place) {
        return Injection.repo().home().getPlaceUserList(this, place.getPlaceId(), getIntent().getLongExtra(Constants.PLACE_USER_ID, -1L)).data();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(List list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        ((ActPlaceUserSettingVM) this.mViewModel).placeUser.setValue((PlaceUser) list.get(0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(PlaceUser placeUser) {
        ((ActPlaceUserSettingVM) this.mViewModel).bManager.setValue(Boolean.valueOf(placeUser.isManager()));
        ((ActPlaceUserSettingVM) this.mViewModel).bOwner.setValue(Boolean.valueOf(placeUser.isOwner()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Place place) {
        ((ActPlaceUserSettingVM) this.mViewModel).pOwner.setValue(Boolean.valueOf(place.isOwner()));
        if (place.isOwner() || place.isManager()) {
            ((ActPlaceUserSettingBinding) this.mViewBinding).ivGoName.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(SwitchButton switchButton, boolean z) {
        showTipDialog(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(Void r1) {
        showRemoveTipDialog();
    }

    private void showTipDialog(final boolean manager) {
        MessageDialog.show(this, getString(manager ? R.string.change_manager_tip : R.string.change_member_tip), getString(manager ? R.string.change_manager_content : R.string.change_member_content)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.share.ActPlaceUserSetting$$ExternalSyntheticLambda9
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showTipDialog$8;
                lambda$showTipDialog$8 = ActPlaceUserSetting.this.lambda$showTipDialog$8(manager, baseDialog, view);
                return lambda$showTipDialog$8;
            }
        }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.share.ActPlaceUserSetting$$ExternalSyntheticLambda10
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showTipDialog$9;
                lambda$showTipDialog$9 = ActPlaceUserSetting.this.lambda$showTipDialog$9(manager, baseDialog, view);
                return lambda$showTipDialog$9;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showTipDialog$8(boolean z, BaseDialog baseDialog, View view) {
        ((ActPlaceUserSettingVM) this.mViewModel).bManager.setValue(Boolean.valueOf(z));
        updatePlaceUser(z ? 2 : 3);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showTipDialog$9(boolean z, BaseDialog baseDialog, View view) {
        ((ActPlaceUserSettingBinding) this.mViewBinding).sb.setCheckedNotByUser(!z);
        return false;
    }

    private void showRemoveTipDialog() {
        MessageDialog.show(this, getString(R.string.remove_member_tip), getString(R.string.remove_member_content)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.share.ActPlaceUserSetting$$ExternalSyntheticLambda0
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showRemoveTipDialog$10;
                lambda$showRemoveTipDialog$10 = ActPlaceUserSetting.this.lambda$showRemoveTipDialog$10(baseDialog, view);
                return lambda$showRemoveTipDialog$10;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showRemoveTipDialog$10(BaseDialog baseDialog, View view) {
        ((ActPlaceUserSettingVM) this.mViewModel).removePlaceUser(this);
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        ((ActPlaceUserSettingVM) this.mViewModel).placeListing.retry();
    }

    public void updatePlaceUser(int roleType) {
        if (((ActPlaceUserSettingVM) this.mViewModel).placeUser.getValue() != null) {
            ((ObservableSubscribeProxy) Injection.net().updatePlaceUser(((ActPlaceUserSettingVM) this.mViewModel).placeUser.getValue().getPlaceUserId(), roleType, "").delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.share.ActPlaceUserSetting$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActPlaceUserSetting.this.lambda$updatePlaceUser$11((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.share.ActPlaceUserSetting$$ExternalSyntheticLambda6
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActPlaceUserSetting.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.share.ActPlaceUserSetting$$ExternalSyntheticLambda7
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActPlaceUserSetting.this.lambda$updatePlaceUser$12(obj);
                }
            }, new SmartErrorComsumer());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePlaceUser$11(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.requesting));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePlaceUser$12(Object obj) throws Exception {
        showSuccessDialog(getString(R.string.config_success));
        Injection.limiter().reset(Injection.keyCreator().placeUserKey(((ActPlaceUserSettingVM) this.mViewModel).placeUser.getValue().getPlaceId()));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        PlaceShareHelper.getInstance().reset();
        super.back();
    }

    @Override // com.ltech.smarthome.base.VMActivity
    public void refreshData() {
        Injection.limiter().reset(Injection.keyCreator().placeUserKey(((ActPlaceUserSettingVM) this.mViewModel).place.getValue().getPlaceId()));
        Injection.limiter().reset(Injection.keyCreator().placeListKey());
        ((ActPlaceUserSettingVM) this.mViewModel).placeListing.refresh();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (1002 == resultCode) {
            refreshData();
        }
    }
}