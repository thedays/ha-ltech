package com.ltech.smarthome.ui.home;

import android.content.Intent;
import android.view.View;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.imageclip.fragment.ActivityResultHelper;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActHomeSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.PlaceUser;
import com.ltech.smarthome.ui.permission.ActGetLocationPermission;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public class ActHomeSetting extends VMActivity<ActHomeSettingBinding, ActHomeSettingVM> {
    private long placeid;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_home_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.home_setting));
        ((ActHomeSettingBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((ActHomeSettingBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((ActHomeSettingBinding) this.mViewBinding).refreshLayout.setHeaderHeight(100.0f);
        ((ActHomeSettingBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.home.ActHomeSetting$$ExternalSyntheticLambda5
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ActHomeSetting.this.lambda$initView$0(refreshLayout);
            }
        });
        this.placeid = getIntent().getLongExtra(Constants.PLACE_ID, 0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(RefreshLayout refreshLayout) {
        refreshData();
        refreshLayout.finishRefresh();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        initDataObserve();
        ((ActHomeSettingVM) this.mViewModel).showDeleteHomeDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.home.ActHomeSetting$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActHomeSetting.this.lambda$startObserve$1((Void) obj);
            }
        });
        ((ActHomeSettingVM) this.mViewModel).showExitHomeDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.home.ActHomeSetting$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActHomeSetting.this.lambda$startObserve$2((Void) obj);
            }
        });
        ((ActHomeSettingVM) this.mViewModel).goMapEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.home.ActHomeSetting$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActHomeSetting.this.lambda$startObserve$4((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r1) {
        showDeleteHomeDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r1) {
        showExitHomeDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r3) {
        ActivityResultHelper.init(this).startActivityForResult(ActGetLocationPermission.class, new ActivityResultHelper.Callback() { // from class: com.ltech.smarthome.ui.home.ActHomeSetting$$ExternalSyntheticLambda10
            @Override // com.ltech.imageclip.fragment.ActivityResultHelper.Callback
            public final void onActivityResult(int i, Intent intent) {
                ActHomeSetting.this.lambda$startObserve$3(i, intent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(int i, Intent intent) {
        if (100 == i) {
            NavUtils.destination(ActHomePosition.class).withLong(Constants.PLACE_ID, ((ActHomeSettingVM) this.mViewModel).place.getValue().getPlaceId()).navigation(this);
        }
    }

    private void initDataObserve() {
        ((ActHomeSettingVM) this.mViewModel).placeListing = Injection.repo().home().getPlace(this, this.placeid);
        ((ActHomeSettingVM) this.mViewModel).placeListing.data().observe(this, new Observer() { // from class: com.ltech.smarthome.ui.home.ActHomeSetting$$ExternalSyntheticLambda11
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActHomeSetting.this.lambda$initDataObserve$6((Resource) obj);
            }
        });
        ((ActHomeSettingVM) this.mViewModel).place.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.home.ActHomeSetting$$ExternalSyntheticLambda12
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActHomeSetting.this.lambda$initDataObserve$7((Place) obj);
            }
        });
        handleData(Transformations.switchMap(((ActHomeSettingVM) this.mViewModel).place, new Function1() { // from class: com.ltech.smarthome.ui.home.ActHomeSetting$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$initDataObserve$8;
                lambda$initDataObserve$8 = ActHomeSetting.this.lambda$initDataObserve$8((Place) obj);
                return lambda$initDataObserve$8;
            }
        }), new IAction() { // from class: com.ltech.smarthome.ui.home.ActHomeSetting$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActHomeSetting.this.lambda$initDataObserve$9((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataObserve$6(Resource resource) {
        handleResource(resource, new IAction() { // from class: com.ltech.smarthome.ui.home.ActHomeSetting$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActHomeSetting.this.lambda$initDataObserve$5((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataObserve$5(List list) {
        if (list.isEmpty()) {
            showEmpty();
        } else {
            ((ActHomeSettingVM) this.mViewModel).place.setValue((Place) list.get(0));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataObserve$7(Place place) {
        ((ActHomeSettingVM) this.mViewModel).initList(place);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$initDataObserve$8(Place place) {
        return Injection.repo().home().getPlaceUserList(this, place.getPlaceId(), -1L).data();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataObserve$9(List list) {
        ((ActHomeSettingVM) this.mViewModel).placeUserObservableList.clear();
        if (((ActHomeSettingVM) this.mViewModel).headerObservableList.size() == 0) {
            ((ActHomeSettingVM) this.mViewModel).headerObservableList.add(ActivityUtils.getTopActivity().getString(R.string.home_member));
        }
        Collections.sort(list, new Comparator<PlaceUser>(this) { // from class: com.ltech.smarthome.ui.home.ActHomeSetting.1
            @Override // java.util.Comparator
            public int compare(PlaceUser o1, PlaceUser o2) {
                return o1.getRoleType() > o2.getRoleType() ? 1 : -1;
            }
        });
        ((ActHomeSettingVM) this.mViewModel).placeUserObservableList.addAll(list);
        if (((ActHomeSettingVM) this.mViewModel).place.getValue().isOwner() && ((ActHomeSettingVM) this.mViewModel).placeUserObservableList != null && ((ActHomeSettingVM) this.mViewModel).placeUserObservableList.size() > 1) {
            setEditString(ActivityUtils.getTopActivity().getString(R.string.transfer_home));
        } else {
            setEditString("");
        }
    }

    private void showDeleteHomeDialog() {
        MessageDialog.show(this, getString(R.string.sure_remove_home), getString(R.string.tip_remove_home)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.home.ActHomeSetting$$ExternalSyntheticLambda0
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showDeleteHomeDialog$10;
                lambda$showDeleteHomeDialog$10 = ActHomeSetting.this.lambda$showDeleteHomeDialog$10(baseDialog, view);
                return lambda$showDeleteHomeDialog$10;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showDeleteHomeDialog$10(BaseDialog baseDialog, View view) {
        showCheckDeleteHomeDialog();
        return false;
    }

    private void showCheckDeleteHomeDialog() {
        MessageDialog.show(this, getString(R.string.check_remove_home), "").setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.home.ActHomeSetting$$ExternalSyntheticLambda9
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showCheckDeleteHomeDialog$11;
                lambda$showCheckDeleteHomeDialog$11 = ActHomeSetting.this.lambda$showCheckDeleteHomeDialog$11(baseDialog, view);
                return lambda$showCheckDeleteHomeDialog$11;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showCheckDeleteHomeDialog$11(BaseDialog baseDialog, View view) {
        ((ActHomeSettingVM) this.mViewModel).deletePlace(((ActHomeSettingVM) this.mViewModel).place.getValue().getPlaceId());
        return false;
    }

    private void showExitHomeDialog() {
        MessageDialog.show(this, getString(R.string.sure_exit_home), getString(R.string.tip_exit_home)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.home.ActHomeSetting$$ExternalSyntheticLambda4
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showExitHomeDialog$12;
                lambda$showExitHomeDialog$12 = ActHomeSetting.this.lambda$showExitHomeDialog$12(baseDialog, view);
                return lambda$showExitHomeDialog$12;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showExitHomeDialog$12(BaseDialog baseDialog, View view) {
        ((ActHomeSettingVM) this.mViewModel).quitPlace(((ActHomeSettingVM) this.mViewModel).place.getValue().getPlaceId());
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        showContent();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        ((ActHomeSettingVM) this.mViewModel).placeListing.retry();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        NavUtils.destination(ActHomeTransferSetting.class).withLong(Constants.PLACE_ID, ((ActHomeSettingVM) this.mViewModel).place.getValue().getPlaceId()).withDefaultRequestCode().navigation(this);
    }

    @Override // com.ltech.smarthome.base.VMActivity
    public void refreshData() {
        Injection.limiter().clear();
        ((ActHomeSettingVM) this.mViewModel).goItemObservableList.clear();
        ((ActHomeSettingVM) this.mViewModel).placeUserObservableList.clear();
        ((ActHomeSettingVM) this.mViewModel).footerObservableList.clear();
        initDataObserve();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (16001 == resultCode) {
            refreshData();
        }
    }
}