package com.ltech.smarthome.ui.group;

import android.os.SystemClock;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActTrigCurtainGroupSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.device_param.TrigExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.group.UpdateGroupResponse;
import com.ltech.smarthome.ui.device.light.ActDiyLightName;
import com.ltech.smarthome.ui.device.trig.ActTrigCurtainOpenDirSetting;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RoomPickHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.RoomSelectDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActBleTrigCurtainGroupSetting extends VMActivity<ActTrigCurtainGroupSettingBinding, BaseGroupSettingVM> {
    private int lightType;
    private Group mGroup;
    public RoomPickHelper roomPickHelper = new RoomPickHelper();
    final int COUNTS = 10;
    final long DURATION = 5000;
    long[] mHits = new long[10];

    static /* synthetic */ boolean lambda$showDelFailDialog$4(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_trig_curtain_group_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.setting));
        setEditString("             ");
        this.lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
        if (this.mViewBinding != 0) {
            if (getCurrentPlace() != null && (getCurrentPlace().isOwner() || getCurrentPlace().isManager())) {
                ((ActTrigCurtainGroupSettingBinding) this.mViewBinding).layoutGroupName.setEnabled(true);
                ((ActTrigCurtainGroupSettingBinding) this.mViewBinding).layoutEditGroup.setEnabled(true);
                ((ActTrigCurtainGroupSettingBinding) this.mViewBinding).tvDelete.setVisibility(0);
                ((ActTrigCurtainGroupSettingBinding) this.mViewBinding).ivGroupNameGo.setVisibility(0);
                ((ActTrigCurtainGroupSettingBinding) this.mViewBinding).ivDeviceCountGo.setVisibility(0);
                ((ActTrigCurtainGroupSettingBinding) this.mViewBinding).layoutChangeRoom.setEnabled(true);
                ((ActTrigCurtainGroupSettingBinding) this.mViewBinding).ivRoomNameGo.setVisibility(0);
                ((ActTrigCurtainGroupSettingBinding) this.mViewBinding).layoutCurtainOpenDir.setEnabled(true);
                ((ActTrigCurtainGroupSettingBinding) this.mViewBinding).ivCurtainOpenDirGo.setVisibility(0);
            } else {
                ((ActTrigCurtainGroupSettingBinding) this.mViewBinding).layoutGroupName.setEnabled(false);
                ((ActTrigCurtainGroupSettingBinding) this.mViewBinding).layoutEditGroup.setEnabled(false);
                ((ActTrigCurtainGroupSettingBinding) this.mViewBinding).tvDelete.setVisibility(8);
                ((ActTrigCurtainGroupSettingBinding) this.mViewBinding).ivGroupNameGo.setVisibility(8);
                ((ActTrigCurtainGroupSettingBinding) this.mViewBinding).ivDeviceCountGo.setVisibility(8);
                ((ActTrigCurtainGroupSettingBinding) this.mViewBinding).layoutChangeRoom.setEnabled(false);
                ((ActTrigCurtainGroupSettingBinding) this.mViewBinding).ivRoomNameGo.setVisibility(8);
                ((ActTrigCurtainGroupSettingBinding) this.mViewBinding).layoutCurtainOpenDir.setEnabled(false);
                ((ActTrigCurtainGroupSettingBinding) this.mViewBinding).ivCurtainOpenDirGo.setVisibility(8);
            }
            ((ActTrigCurtainGroupSettingBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.group.ActBleTrigCurtainGroupSetting$$ExternalSyntheticLambda9
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    ActBleTrigCurtainGroupSetting.this.lambda$initView$0((View) obj);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        switch (view.getId()) {
            case R.id.layout_change_room /* 2131297392 */:
                showEditRoomDialog();
                break;
            case R.id.layout_curtain_open_dir /* 2131297418 */:
                NavUtils.destination(ActTrigCurtainOpenDirSetting.class).withBoolean(Constants.GROUP_CONTROL, true).withLong(Constants.CONTROL_ID, this.mGroup.getId()).withInt(Constants.SUB_TYPE, this.lightType == 16 ? 3 : 0).navigation(this);
                break;
            case R.id.layout_edit_group /* 2131297457 */:
                NavUtils.destination(ProductRepository.isBleGroup(this.mGroup.getModuleType()) ? ActSelectDeviceNew.class : ActAddWiFiGroup.class).withLong(Constants.PLACE_ID, this.mGroup.getPlaceId()).withLong(Constants.FLOOR_ID, this.mGroup.getFloorId()).withLong(Constants.ROOM_ID, this.mGroup.getRoomId()).withLong(Constants.GROUP_ID, this.mGroup.getId()).navigation(this);
                break;
            case R.id.layout_group_name /* 2131297487 */:
                String groupKey = this.mGroup.getGroupKey();
                if (groupKey.equals(ProductId.BLE_GROUP_DIM_LIGHT) || groupKey.equals(ProductId.BLE_GROUP_CT_LIGHT) || groupKey.equals(ProductId.BLE_GROUP_RGB_LIGHT) || groupKey.equals(ProductId.BLE_GROUP_RGBW_LIGHT) || groupKey.equals(ProductId.BLE_GROUP_RGBWY_LIGHT)) {
                    NavUtils.destination(ActDiyLightName.class).withLong(Constants.CONTROL_ID, this.mGroup.getId()).withBoolean(Constants.GROUP_CONTROL, true).navigation(ActivityUtils.getTopActivity());
                    break;
                } else {
                    showEditNameDialog();
                    break;
                }
                break;
            case R.id.tv_delete /* 2131298575 */:
                if (ProductRepository.isBleGroup(this.mGroup.getModuleType()) && !this.mGroup.getDeviceIds().isEmpty()) {
                    showDelFailDialog();
                    break;
                } else {
                    showDelDialog();
                    break;
                }
                break;
        }
    }

    private void showEditNameDialog() {
        EditDialog.asDefault().setContent(this.mGroup.getGroupName()).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.group.ActBleTrigCurtainGroupSetting$$ExternalSyntheticLambda10
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActBleTrigCurtainGroupSetting.this.lambda$showEditNameDialog$1((String) obj);
            }
        }).showDialog(getSupportFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$1(String str) {
        updateName(this.mGroup, str);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        Injection.repo().group().getGroupFromDb(getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.group.ActBleTrigCurtainGroupSetting$$ExternalSyntheticLambda11
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActBleTrigCurtainGroupSetting.this.lambda$startObserve$2((Group) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Group group) {
        this.mGroup = group;
        ((ActTrigCurtainGroupSettingBinding) this.mViewBinding).tvGroupName.setText(group.getGroupName());
        ((ActTrigCurtainGroupSettingBinding) this.mViewBinding).tvDeviceCount.setText(String.format(Locale.US, "%d", Integer.valueOf(group.getDeviceIds() != null ? group.getDeviceIds().size() : 0)));
        Floor floor = Injection.repo().home().getFloor(this.mGroup.getFloorId());
        Room room = Injection.repo().home().getRoom(this.mGroup.getRoomId());
        String floorName = floor != null ? floor.getFloorName() : "";
        AppCompatTextView appCompatTextView = ((ActTrigCurtainGroupSettingBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            floorName = floorName + " " + room.getRoomName();
        }
        appCompatTextView.setText(floorName);
        this.roomPickHelper.startObserve(this, this.mGroup.getPlaceId(), this.mGroup.getRoomId());
        if (group.getExtParam() != null) {
            TrigExtParam trigExtParam = new TrigExtParam();
            trigExtParam.fillMapWithString(group.getExtParam());
            int curtainType = trigExtParam.getCurtainType();
            if (curtainType == -1 || curtainType == 0) {
                ((ActTrigCurtainGroupSettingBinding) this.mViewBinding).tvCurtainOpenDirName.setText(getString(R.string.app_str_curtain_run_dir_left_to_right));
                return;
            } else if (curtainType == 1) {
                ((ActTrigCurtainGroupSettingBinding) this.mViewBinding).tvCurtainOpenDirName.setText(getString(R.string.app_str_curtain_run_up_to_down));
                return;
            } else {
                if (curtainType != 2) {
                    return;
                }
                ((ActTrigCurtainGroupSettingBinding) this.mViewBinding).tvCurtainOpenDirName.setText(getString(R.string.app_str_curtain_run_left));
                return;
            }
        }
        ((ActTrigCurtainGroupSettingBinding) this.mViewBinding).tvCurtainOpenDirName.setText(getString(R.string.app_str_curtain_run_dir_left_to_right));
    }

    private void showDelDialog() {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.tip_del_group)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActBleTrigCurtainGroupSetting$$ExternalSyntheticLambda8
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showDelDialog$3;
                lambda$showDelDialog$3 = ActBleTrigCurtainGroupSetting.this.lambda$showDelDialog$3(baseDialog, view);
                return lambda$showDelDialog$3;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showDelDialog$3(BaseDialog baseDialog, View view) {
        delGroupFromNet(this.mGroup);
        return false;
    }

    private void showDelFailDialog() {
        MessageDialog.show(this, getString(R.string.del_fail), getString(R.string.del_fail_tip)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActBleTrigCurtainGroupSetting$$ExternalSyntheticLambda7
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return ActBleTrigCurtainGroupSetting.lambda$showDelFailDialog$4(baseDialog, view);
            }
        });
    }

    private void delGroupFromNet(final Group group) {
        ((ObservableSubscribeProxy) Injection.net().deleteGroup(group.getGroupId()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActBleTrigCurtainGroupSetting$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActBleTrigCurtainGroupSetting.this.lambda$delGroupFromNet$5((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActBleTrigCurtainGroupSetting$$ExternalSyntheticLambda3(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActBleTrigCurtainGroupSetting$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActBleTrigCurtainGroupSetting.this.lambda$delGroupFromNet$6(group, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delGroupFromNet$5(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.removing));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delGroupFromNet$6(Group group, Object obj) throws Exception {
        Injection.repo().group().removeGroupFromDb(group.getId());
        setResult(5002);
        finishActivity();
    }

    private void updateName(final Group group, final String name) {
        ((ObservableSubscribeProxy) Injection.net().updateGroupName(group.getGroupId(), name).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActBleTrigCurtainGroupSetting$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActBleTrigCurtainGroupSetting.this.lambda$updateName$7((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActBleTrigCurtainGroupSetting$$ExternalSyntheticLambda3(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActBleTrigCurtainGroupSetting$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActBleTrigCurtainGroupSetting.this.lambda$updateName$8(group, name, (UpdateGroupResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateName$7(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateName$8(Group group, String str, UpdateGroupResponse updateGroupResponse) throws Exception {
        group.setGroupName(str);
        Injection.repo().group().saveGroup(group);
        SmartToast.showShort(getString(R.string.save_success));
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    protected void showEditRoomDialog() {
        if (this.roomPickHelper.getCanSetRoom() == null || !this.roomPickHelper.getCanSetRoom().getValue().booleanValue()) {
            return;
        }
        RoomSelectDialog.asDefault().setFloorList(this.roomPickHelper.getCurrentFloorNames()).setRoomList(this.roomPickHelper.getCurrentRoomNames()).setSelectFloorPosition(this.roomPickHelper.getSelectFloorPosition()).setSelectRoomPosition(this.roomPickHelper.getSelectRoomPosition()).setSelectListener(new RoomSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.group.ActBleTrigCurtainGroupSetting.1
            @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
            public void confirm(int floorPosition, int roomPosition) {
                ActBleTrigCurtainGroupSetting.this.roomPickHelper.setSelectPosition(floorPosition, roomPosition);
                ActBleTrigCurtainGroupSetting actBleTrigCurtainGroupSetting = ActBleTrigCurtainGroupSetting.this;
                actBleTrigCurtainGroupSetting.changeGroupPlace(actBleTrigCurtainGroupSetting.roomPickHelper.getSelectFloorId(), ActBleTrigCurtainGroupSetting.this.roomPickHelper.getSelectRoomId());
            }

            @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
            public void onFloorSelect(RoomSelectDialog dialog, int floorPosition) {
                dialog.setRoomList(ActBleTrigCurtainGroupSetting.this.roomPickHelper.getRoomNames(floorPosition));
                dialog.notifyDialog();
            }
        }).showDialog(this);
    }

    public void changeGroupPlace(final long floorId, final long roomId) {
        ((ObservableSubscribeProxy) Injection.net().updateGroupLocation(this.mGroup.getGroupId(), floorId, roomId).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActBleTrigCurtainGroupSetting$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActBleTrigCurtainGroupSetting.this.lambda$changeGroupPlace$9((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActBleTrigCurtainGroupSetting$$ExternalSyntheticLambda3(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActBleTrigCurtainGroupSetting$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActBleTrigCurtainGroupSetting.this.lambda$changeGroupPlace$10(floorId, roomId, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeGroupPlace$9(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeGroupPlace$10(long j, long j2, Object obj) throws Exception {
        this.mGroup.setFloorId(j);
        this.mGroup.setRoomId(j2);
        Injection.repo().group().saveGroup(this.mGroup);
        SmartToast.showShort(R.string.save_success);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (getCurPlace() != null) {
            if (getCurPlace().isManager() || getCurPlace().isOwner()) {
                long[] jArr = this.mHits;
                System.arraycopy(jArr, 1, jArr, 0, jArr.length - 1);
                long[] jArr2 = this.mHits;
                jArr2[jArr2.length - 1] = SystemClock.uptimeMillis();
                if (this.mHits[0] >= SystemClock.uptimeMillis() - 5000) {
                    ((BaseGroupSettingVM) this.mViewModel).showEditTTLDialog(this);
                }
            }
        }
    }

    public Place getCurPlace() {
        return Injection.repo().home().getSelPlace();
    }
}