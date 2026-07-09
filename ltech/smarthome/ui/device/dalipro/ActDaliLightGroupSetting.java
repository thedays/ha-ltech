package com.ltech.smarthome.ui.device.dalipro;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActDaliLightGroupSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.device_param.CgdProGroupExtParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.group.UpdateGroupResponse;
import com.ltech.smarthome.ui.device.dalipro.ActDaliLightGroupSetting;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.IconRepository;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RoomPickHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.RoomSelectDialog;
import com.ltech.smarthome.view.dialog.SelectDeviceIconDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActDaliLightGroupSetting extends VMActivity<ActDaliLightGroupSettingBinding, ActDaliLightGroupSettingVM> {
    private Group mGroup;
    public RoomPickHelper roomPickHelper = new RoomPickHelper();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_dali_light_group_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.setting));
        ((ActDaliLightGroupSettingBinding) this.mViewBinding).sbAddToSmart.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightGroupSetting$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                ActDaliLightGroupSetting.this.lambda$initView$0(switchButton, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(SwitchButton switchButton, boolean z) {
        ((ActDaliLightGroupSettingVM) this.mViewModel).setAddToSmart(z);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActDaliLightGroupSettingVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightGroupSetting$$ExternalSyntheticLambda9
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliLightGroupSetting.this.lambda$startObserve$1((Group) obj);
            }
        });
        ((ActDaliLightGroupSettingVM) this.mViewModel).parentDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightGroupSetting$$ExternalSyntheticLambda10
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliLightGroupSetting.this.lambda$startObserve$2((Device) obj);
            }
        });
        ((ActDaliLightGroupSettingVM) this.mViewModel).showEditNameDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightGroupSetting$$ExternalSyntheticLambda11
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliLightGroupSetting.this.lambda$startObserve$3((Void) obj);
            }
        });
        ((ActDaliLightGroupSettingVM) this.mViewModel).changeRoomEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightGroupSetting$$ExternalSyntheticLambda12
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliLightGroupSetting.this.lambda$startObserve$4((Void) obj);
            }
        });
        ((ActDaliLightGroupSettingVM) this.mViewModel).changeIconEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightGroupSetting$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliLightGroupSetting.this.lambda$startObserve$5((Void) obj);
            }
        });
        ((ActDaliLightGroupSettingVM) this.mViewModel).managerGroupEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightGroupSetting$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliLightGroupSetting.this.lambda$startObserve$6((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Group group) {
        this.mGroup = group;
        ((ActDaliLightGroupSettingBinding) this.mViewBinding).tvGroupName.setText(this.mGroup.getGroupName());
        Floor floor = Injection.repo().home().getFloor(this.mGroup.getFloorId());
        Room room = Injection.repo().home().getRoom(this.mGroup.getRoomId());
        String floorName = floor != null ? floor.getFloorName() : "";
        AppCompatTextView appCompatTextView = ((ActDaliLightGroupSettingBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            floorName = floorName + " " + room.getRoomName();
        }
        appCompatTextView.setText(floorName);
        ((ActDaliLightGroupSettingBinding) this.mViewBinding).tvManagerGroup.setText(this.mGroup.getDeviceIds().size() + "");
        this.roomPickHelper.startObserve(this, this.mGroup.getPlaceId(), this.mGroup.getRoomId());
        CgdProGroupExtParam cgdProGroupExtParam = (CgdProGroupExtParam) this.mGroup.getExtParam(CgdProGroupExtParam.class);
        ((ActDaliLightGroupSettingBinding) this.mViewBinding).tvGroupNumber.setText(cgdProGroupExtParam.getDaliAddr() + "");
        ((ActDaliLightGroupSettingVM) this.mViewModel).isAddToRoom.setValue(Boolean.valueOf(cgdProGroupExtParam.getDaliHidden() == 0));
        int imgindex = this.mGroup.getImgindex();
        int[] lightGroupIconValue = IconRepository.getLightGroupIconValue(this);
        if (imgindex < 0 || imgindex > lightGroupIconValue.length) {
            return;
        }
        ((ActDaliLightGroupSettingBinding) this.mViewBinding).ivIcon.setImageResource(lightGroupIconValue[imgindex]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Device device) {
        ((ActDaliLightGroupSettingBinding) this.mViewBinding).tvGatewayName.setText(device.getDeviceName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r1) {
        showEditNameDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r1) {
        showEditRoomDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Void r1) {
        showSelectDeviceIconDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(Void r4) {
        NavUtils.destination(ActSelectDaliLight.class).withLong(Constants.PLACE_ID, ((ActDaliLightGroupSettingVM) this.mViewModel).controlObject.getValue().getPlaceId()).withLong("device_id", ((ActDaliLightGroupSettingVM) this.mViewModel).controlObject.getValue().getMacdeviceid()).withLong(Constants.GROUP_ID, ((ActDaliLightGroupSettingVM) this.mViewModel).controlObject.getValue().getGroupId()).navigation(ActivityUtils.getTopActivity());
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((ActDaliLightGroupSettingVM) this.mViewModel).controlObject.setValue(Injection.repo().group().getGroupById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L)));
        ((ActDaliLightGroupSettingVM) this.mViewModel).parentDevice.setValue(Injection.repo().device().getDeviceByDeviceId(getIntent().getLongExtra("device_id", -1L)));
    }

    private void showSelectDeviceIconDialog() {
        SelectDeviceIconDialog.asDefault().setGroupTag(true).imageIndex(this.mGroup.getImgindex()).setOnSaveListener(new AnonymousClass1()).showDialog(this);
    }

    /* renamed from: com.ltech.smarthome.ui.device.dalipro.ActDaliLightGroupSetting$1, reason: invalid class name */
    class AnonymousClass1 implements SelectDeviceIconDialog.OnSaveListener {
        @Override // com.ltech.smarthome.view.dialog.SelectDeviceIconDialog.OnSaveListener
        public void cancel() {
        }

        AnonymousClass1() {
        }

        @Override // com.ltech.smarthome.view.dialog.SelectDeviceIconDialog.OnSaveListener
        public boolean onSave(final int selectPos) {
            ((ObservableSubscribeProxy) Injection.net().updateGroupDeviceIcon(ActDaliLightGroupSetting.this.mGroup.getGroupId(), selectPos).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightGroupSetting$1$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActDaliLightGroupSetting.AnonymousClass1.this.lambda$onSave$0((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new ActDaliLightGroupSetting$$ExternalSyntheticLambda5(ActDaliLightGroupSetting.this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(ActDaliLightGroupSetting.this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightGroupSetting$1$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActDaliLightGroupSetting.AnonymousClass1.this.lambda$onSave$1(selectPos, obj);
                }
            }, new SmartErrorComsumer());
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSave$0(Disposable disposable) throws Exception {
            ActDaliLightGroupSetting actDaliLightGroupSetting = ActDaliLightGroupSetting.this;
            actDaliLightGroupSetting.showLoadingDialog(actDaliLightGroupSetting.getString(R.string.saving));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSave$1(int i, Object obj) throws Exception {
            SmartToast.showShort(ActDaliLightGroupSetting.this.getString(R.string.save_success));
            ActDaliLightGroupSetting.this.mGroup.setImgindex(i);
            Injection.repo().group().saveGroup(ActDaliLightGroupSetting.this.mGroup);
            ((ActDaliLightGroupSettingVM) ActDaliLightGroupSetting.this.mViewModel).controlObject.setValue(ActDaliLightGroupSetting.this.mGroup);
        }
    }

    private void showEditNameDialog() {
        EditDialog.asDefault().setContent(this.mGroup.getGroupName()).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightGroupSetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDaliLightGroupSetting.this.lambda$showEditNameDialog$7((String) obj);
            }
        }).showDialog(getSupportFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$7(String str) {
        updateName(this.mGroup, str);
    }

    private void showEditRoomDialog() {
        if (this.roomPickHelper.getCanSetRoom() == null || !this.roomPickHelper.getCanSetRoom().getValue().booleanValue()) {
            return;
        }
        RoomSelectDialog.asDefault().setFloorList(this.roomPickHelper.getCurrentFloorNames()).setRoomList(this.roomPickHelper.getCurrentRoomNames()).setSelectFloorPosition(this.roomPickHelper.getSelectFloorPosition()).setSelectRoomPosition(this.roomPickHelper.getSelectRoomPosition()).setSelectListener(new RoomSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightGroupSetting.2
            @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
            public void confirm(int floorPosition, int roomPosition) {
                ActDaliLightGroupSetting.this.roomPickHelper.setSelectPosition(floorPosition, roomPosition);
                ActDaliLightGroupSetting actDaliLightGroupSetting = ActDaliLightGroupSetting.this;
                actDaliLightGroupSetting.changeGroupPlace(actDaliLightGroupSetting.roomPickHelper.getSelectFloorId(), ActDaliLightGroupSetting.this.roomPickHelper.getSelectRoomId());
            }

            @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
            public void onFloorSelect(RoomSelectDialog dialog, int floorPosition) {
                dialog.setRoomList(ActDaliLightGroupSetting.this.roomPickHelper.getRoomNames(floorPosition));
                dialog.notifyDialog();
            }
        }).showDialog(this);
    }

    public void changeGroupPlace(final long floorId, final long roomId) {
        ((ObservableSubscribeProxy) Injection.net().updateGroupLocation(this.mGroup.getGroupId(), floorId, roomId).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightGroupSetting$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDaliLightGroupSetting.this.lambda$changeGroupPlace$8((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActDaliLightGroupSetting$$ExternalSyntheticLambda5(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightGroupSetting$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDaliLightGroupSetting.this.lambda$changeGroupPlace$9(floorId, roomId, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeGroupPlace$8(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeGroupPlace$9(long j, long j2, Object obj) throws Exception {
        this.mGroup.setFloorId(j);
        this.mGroup.setRoomId(j2);
        Injection.repo().group().saveGroup(this.mGroup);
        ((ActDaliLightGroupSettingVM) this.mViewModel).controlObject.setValue(this.mGroup);
        SmartToast.showShort(R.string.save_success);
    }

    private void updateName(final Group group, final String name) {
        ((ObservableSubscribeProxy) Injection.net().updateGroupName(group.getGroupId(), name).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightGroupSetting$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDaliLightGroupSetting.this.lambda$updateName$10((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActDaliLightGroupSetting$$ExternalSyntheticLambda5(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightGroupSetting$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDaliLightGroupSetting.this.lambda$updateName$11(group, name, (UpdateGroupResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateName$10(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateName$11(Group group, String str, UpdateGroupResponse updateGroupResponse) throws Exception {
        group.setGroupName(str);
        Injection.repo().group().saveGroup(group);
        ((ActDaliLightGroupSettingVM) this.mViewModel).controlObject.setValue(group);
        SmartToast.showShort(getString(R.string.save_success));
    }
}