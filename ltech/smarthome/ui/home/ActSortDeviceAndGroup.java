package com.ltech.smarthome.ui.home;

import androidx.lifecycle.Lifecycle;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.device.SortDeviceAndGroupRequest;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSortDeviceAndGroup extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> {
    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(0);
        setBackString(getString(R.string.cancel));
        setEditString(getString(R.string.finish));
        setTitle(getString(R.string.sort_device));
        ((ActSelect3Binding) this.mViewBinding).layoutSort.setVisibility(8);
        ((ActSelect3Binding) this.mViewBinding).layoutSortAndType.setVisibility(8);
        this.listType = 6;
        ((FtDeviceGroupVM) this.mViewModel).needFloorRoomMemory = false;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        saveData();
    }

    protected void saveData() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        final ArrayList arrayList3 = new ArrayList();
        final ArrayList arrayList4 = new ArrayList();
        for (int i = 0; i < getRoleList().size(); i++) {
            Role role = getRoleList().get(i);
            long objectId = role.getObjectId();
            if (role instanceof Device) {
                Device device = (Device) role;
                int i2 = i + 1;
                device.setIndex(i2);
                arrayList3.add(device);
                arrayList.add(new SortDeviceAndGroupRequest.DeviceSortBean(objectId, i2));
            } else if (role instanceof Group) {
                Group group = (Group) role;
                int i3 = i + 1;
                group.setGroupIndex(i3);
                arrayList4.add(group);
                arrayList2.add(new SortDeviceAndGroupRequest.GroupSortBean(objectId, i3));
            }
        }
        ((ObservableSubscribeProxy) Injection.net().sortDeviceAndGroup(new SortDeviceAndGroupRequest(arrayList, arrayList2)).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActSortDeviceAndGroup$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSortDeviceAndGroup.this.lambda$saveData$0((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.home.ActSortDeviceAndGroup$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActSortDeviceAndGroup.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActSortDeviceAndGroup$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSortDeviceAndGroup.this.lambda$saveData$1(arrayList3, arrayList4, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveData$0(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveData$1(final List list, final List list2, Object obj) throws Exception {
        ((ObservableSubscribeProxy) Observable.create(new ObservableOnSubscribe<Boolean>(this) { // from class: com.ltech.smarthome.ui.home.ActSortDeviceAndGroup.2
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                for (int i = 0; i < list.size(); i++) {
                    Injection.repo().device().saveDevice((Device) list.get(i));
                }
                for (int i2 = 0; i2 < list2.size(); i2++) {
                    Injection.repo().group().saveGroup((Group) list2.get(i2));
                }
                emitter.onNext(true);
                emitter.onComplete();
            }
        }).compose(RxUtils.io_io()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<Boolean>() { // from class: com.ltech.smarthome.ui.home.ActSortDeviceAndGroup.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Boolean aBoolean) throws Exception {
                ActSortDeviceAndGroup.this.finishActivity();
            }
        });
        SmartToast.showShort(R.string.save_success);
    }
}