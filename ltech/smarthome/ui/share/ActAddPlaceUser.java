package com.ltech.smarthome.ui.share;

import android.view.View;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActAddPlaceUserBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.utils.Constants;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import java.util.List;

/* loaded from: classes4.dex */
public class ActAddPlaceUser extends VMActivity<ActAddPlaceUserBinding, ActAddPlaceUserVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_add_place_user;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.add_member));
        setBackImage(R.mipmap.icon_back);
        PlaceShareHelper.getInstance().enterType = 1001;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActAddPlaceUserVM) this.mViewModel).placeListing = Injection.repo().home().getPlace(this, getIntent().getLongExtra(Constants.PLACE_ID, -1L));
        handleData(((ActAddPlaceUserVM) this.mViewModel).placeListing, new IAction() { // from class: com.ltech.smarthome.ui.share.ActAddPlaceUser$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActAddPlaceUser.this.lambda$startObserve$0((List) obj);
            }
        });
        ((ActAddPlaceUserVM) this.mViewModel).place.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.share.ActAddPlaceUser$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddPlaceUser.this.lambda$startObserve$1((Place) obj);
            }
        });
        ((ActAddPlaceUserVM) this.mViewModel).showInvitationSendDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.share.ActAddPlaceUser$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddPlaceUser.this.lambda$startObserve$2((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(List list) {
        if (((ActAddPlaceUserVM) this.mViewModel).place.getValue() == null) {
            ((ActAddPlaceUserVM) this.mViewModel).place.setValue((Place) list.get(0));
            setEditString(getString(R.string.send_invitation));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Place place) {
        ((ActAddPlaceUserVM) this.mViewModel).shareManagerPermission.setValue(Boolean.valueOf(place.isOwner()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r1) {
        showInvitationSendDialog();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        ((ActAddPlaceUserVM) this.mViewModel).sendInvitation(this);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        PlaceShareHelper.getInstance().reset();
        super.back();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        ((ActAddPlaceUserVM) this.mViewModel).placeListing.retry();
    }

    private void showInvitationSendDialog() {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.app_str_invited_member_tip, new Object[]{((ActAddPlaceUserVM) this.mViewModel).account.getValue()})).setOkButton(getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.share.ActAddPlaceUser$$ExternalSyntheticLambda0
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showInvitationSendDialog$3;
                lambda$showInvitationSendDialog$3 = ActAddPlaceUser.this.lambda$showInvitationSendDialog$3(baseDialog, view);
                return lambda$showInvitationSendDialog$3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showInvitationSendDialog$3(BaseDialog baseDialog, View view) {
        PlaceShareHelper.getInstance().reset();
        finishActivity();
        return false;
    }

    @Override // com.ltech.smarthome.base.VMActivity
    public void refreshData() {
        Injection.limiter().reset(Injection.keyCreator().placeUserKey(((ActAddPlaceUserVM) this.mViewModel).place.getValue().getPlaceId()));
        Injection.limiter().reset(Injection.keyCreator().placeListKey());
    }
}