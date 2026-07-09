package com.ltech.smarthome.ui.share;

import android.content.Intent;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.BaseDeviceManageActivity;
import com.ltech.smarthome.databinding.ActDeviceManageBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.placeuser.InvitationPlaceUserRequest;
import com.ltech.smarthome.net.request.role.UpdateDeviceRoleRequest;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSelectDeviceForPermission extends BaseDeviceManageActivity<ActDeviceManageBinding, ActSelectDeviceForPermissionVM> {
    @Override // com.ltech.smarthome.base.BaseDeviceManageActivity
    protected boolean filterDevice(Device device) {
        return true;
    }

    @Override // com.ltech.smarthome.base.BaseDeviceManageActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_device));
        ((ActSelectDeviceForPermissionVM) this.mViewModel).userId = getIntent().getLongExtra(Constants.USER_ID, -1L);
        ((ActDeviceManageBinding) this.mViewBinding).layoutSearch.setVisibility(0);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyImageRes(R.mipmap.pic_empty_1).emptyStringRes(R.string.device_choice_list_empty));
    }

    @Override // com.ltech.smarthome.base.BaseDeviceManageActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActSelectDeviceForPermissionVM) this.mViewModel).selectCountLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.share.ActSelectDeviceForPermission$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSelectDeviceForPermission.this.lambda$startObserve$0((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Integer num) {
        setEditString(String.format(Locale.US, "%s(%d)", getString(R.string.finish), num));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        PlaceShareHelper.getInstance().deviceIds = getSelectDevice();
        if (PlaceShareHelper.getInstance().enterType == 1002) {
            updateDevicePermission();
        } else {
            finishActivity();
        }
    }

    private void updateDevicePermission() {
        ((ObservableSubscribeProxy) Injection.net().updateDeviceRole(generateRequest()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.share.ActSelectDeviceForPermission$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDeviceForPermission.this.lambda$updateDevicePermission$1((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.share.ActSelectDeviceForPermission$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActSelectDeviceForPermission.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.share.ActSelectDeviceForPermission$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDeviceForPermission.this.lambda$updateDevicePermission$2(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDevicePermission$1(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.adding));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDevicePermission$2(Object obj) throws Exception {
        LHomeLog.i(getClass(), "updateDeviceRole: response enter");
        finishActivity();
    }

    private UpdateDeviceRoleRequest generateRequest() {
        ArrayList arrayList = new ArrayList();
        Iterator<Long> it = PlaceShareHelper.getInstance().deviceIds.iterator();
        while (it.hasNext()) {
            arrayList.add(new InvitationPlaceUserRequest.DeviceId(it.next().longValue()));
        }
        return new UpdateDeviceRoleRequest(((ActSelectDeviceForPermissionVM) this.mViewModel).placeId, ((ActSelectDeviceForPermissionVM) this.mViewModel).userId, arrayList);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            setResult(3001);
            finishActivity();
        }
    }

    @Override // com.ltech.smarthome.base.BaseDeviceManageActivity
    protected void getDevices() {
        ((ActSelectDeviceForPermissionVM) this.mViewModel).selectDeviceIdList.clear();
        if (PlaceShareHelper.getInstance().enterType == 1002) {
            for (Device device : ((ActSelectDeviceForPermissionVM) this.mViewModel).mDeviceList) {
                Iterator<Long> it = PlaceShareHelper.getInstance().deviceIds.iterator();
                while (it.hasNext()) {
                    if (device.getDeviceId() == it.next().longValue()) {
                        ((ActSelectDeviceForPermissionVM) this.mViewModel).selectDeviceIdList.add(Long.valueOf(device.getDeviceId()));
                    }
                }
            }
        } else if (PlaceShareHelper.getInstance().deviceIds == null) {
            for (int i = 0; i < ((ActSelectDeviceForPermissionVM) this.mViewModel).mDeviceList.size(); i++) {
                ((ActSelectDeviceForPermissionVM) this.mViewModel).selectDeviceIdList.add(Long.valueOf(((ActSelectDeviceForPermissionVM) this.mViewModel).mDeviceList.get(i).getDeviceId()));
            }
        } else {
            for (Device device2 : ((ActSelectDeviceForPermissionVM) this.mViewModel).mDeviceList) {
                Iterator<Long> it2 = PlaceShareHelper.getInstance().deviceIds.iterator();
                while (it2.hasNext()) {
                    if (device2.getDeviceId() == it2.next().longValue()) {
                        ((ActSelectDeviceForPermissionVM) this.mViewModel).selectDeviceIdList.add(Long.valueOf(device2.getDeviceId()));
                    }
                }
            }
        }
        ((ActSelectDeviceForPermissionVM) this.mViewModel).selectCountLiveData.setValue(Integer.valueOf(((ActSelectDeviceForPermissionVM) this.mViewModel).selectDeviceIdList.size()));
    }

    public List<Long> getSelectDevice() {
        return ((ActSelectDeviceForPermissionVM) this.mViewModel).selectDeviceIdList;
    }

    @Override // com.ltech.smarthome.base.BaseDeviceManageActivity
    protected void controlShowEmpty() {
        if (((ActSelectDeviceForPermissionVM) this.mViewModel).mDeviceList.isEmpty()) {
            showEmpty();
        }
    }
}