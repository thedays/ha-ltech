package com.ltech.smarthome.ui.home;

import android.view.View;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActHomeTransferSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.PlaceUser;
import com.ltech.smarthome.utils.Constants;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public class ActHomeTransferSetting extends VMActivity<ActHomeTransferSettingBinding, ActHomeTransferSettingVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_home_transfer_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.transfer_home));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActHomeTransferSettingVM) this.mViewModel).placeListing = Injection.repo().home().getPlace(this, getIntent().getLongExtra(Constants.PLACE_ID, 0L));
        ((ActHomeTransferSettingVM) this.mViewModel).placeListing.data().observe(this, new Observer() { // from class: com.ltech.smarthome.ui.home.ActHomeTransferSetting$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActHomeTransferSetting.this.lambda$startObserve$1((Resource) obj);
            }
        });
        handleData(Transformations.switchMap(((ActHomeTransferSettingVM) this.mViewModel).place, new Function1() { // from class: com.ltech.smarthome.ui.home.ActHomeTransferSetting$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$startObserve$2;
                lambda$startObserve$2 = ActHomeTransferSetting.this.lambda$startObserve$2((Place) obj);
                return lambda$startObserve$2;
            }
        }), new IAction() { // from class: com.ltech.smarthome.ui.home.ActHomeTransferSetting$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActHomeTransferSetting.this.lambda$startObserve$3((List) obj);
            }
        });
        ((ActHomeTransferSettingVM) this.mViewModel).showTransferDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.home.ActHomeTransferSetting$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActHomeTransferSetting.this.lambda$startObserve$4((PlaceUser) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Resource resource) {
        handleResource(resource, new IAction() { // from class: com.ltech.smarthome.ui.home.ActHomeTransferSetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActHomeTransferSetting.this.lambda$startObserve$0((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(List list) {
        if (list.isEmpty()) {
            showEmpty();
        } else {
            ((ActHomeTransferSettingVM) this.mViewModel).place.setValue((Place) list.get(0));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$startObserve$2(Place place) {
        return Injection.repo().home().getPlaceUserList(this, place.getPlaceId(), -1L).data();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(List list) {
        ((ActHomeTransferSettingVM) this.mViewModel).placeUserObservableList.clear();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            PlaceUser placeUser = (PlaceUser) it.next();
            if (placeUser.getUserId() != ((ActHomeTransferSettingVM) this.mViewModel).place.getValue().getUserId()) {
                ((ActHomeTransferSettingVM) this.mViewModel).placeUserObservableList.add(placeUser);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        showContent();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
    }

    /* renamed from: showTransferMessageDialog, reason: merged with bridge method [inline-methods] */
    public void lambda$startObserve$4(final PlaceUser placeUser) {
        MessageDialog.show(this, getString(R.string.app_str_transfer_home_tip), getString(R.string.app_str_transfer_tome_desc)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.home.ActHomeTransferSetting$$ExternalSyntheticLambda1
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showTransferMessageDialog$5;
                lambda$showTransferMessageDialog$5 = ActHomeTransferSetting.this.lambda$showTransferMessageDialog$5(placeUser, baseDialog, view);
                return lambda$showTransferMessageDialog$5;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showTransferMessageDialog$5(PlaceUser placeUser, BaseDialog baseDialog, View view) {
        ((ActHomeTransferSettingVM) this.mViewModel).transferHome(this, ((ActHomeTransferSettingVM) this.mViewModel).place.getValue().getPlaceId(), placeUser.getUserId());
        return false;
    }
}