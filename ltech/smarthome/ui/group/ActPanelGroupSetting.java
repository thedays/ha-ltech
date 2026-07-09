package com.ltech.smarthome.ui.group;

import android.content.Intent;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActPanelGroupSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.control.ActIntelligence;
import com.ltech.smarthome.ui.device.eurpanel.ActBrtButtonSetting;
import com.ltech.smarthome.ui.device.light.ActLightOnOffTime;
import com.ltech.smarthome.ui.device.light.ActSetLightOnState;
import com.ltech.smarthome.ui.device.screenpanel.ActScreenPanelElderlyMode;
import com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchPositionSet;
import com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelSwitchSetting;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectBleDeviceAndGroupNew;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.ui.group.ActPanelGroupSetting;
import com.ltech.smarthome.ui.my.ActLanguageSelect;
import com.ltech.smarthome.ui.scene.ActSelectCgdPro;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RoomPickHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.SettingAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.RoomSelectDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.ltech.smarthome.view.dialog.SelectSwitchPatternDialog;
import com.ltech.smarthome.view.dialog.SensingDistanceDialog;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.bean.SmartPanelSettingState;
import com.smart.product_agreement.param.SettingCmdParam;
import com.smart.product_agreement.parser.IPanelParser;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActPanelGroupSetting extends VMActivity<ActPanelGroupSettingBinding, BaseGroupSettingVM> {
    public long controlId;
    protected List<RelateInfoItem> dataList;
    protected BaseQuickAdapter<RelateInfoItem, BaseViewHolder> keyAdapter;
    private SettingAssistant mCmdAssistant;
    private Group mGroup;
    private int mSelectHour;
    private int mSelectMin;
    private boolean needBrtButton;
    public long placeId;
    public String productId;
    public RelateInfoAssistant relateInfoAssistant;
    public Listing<Group> request;
    private SmartPanelSettingState state;
    public List<RelateInfoItem> relatedInfoList = new ArrayList();
    public MediatorLiveData<List<Device>> deviceList = new MediatorLiveData<>();
    public MediatorLiveData<List<Group>> groupList = new MediatorLiveData<>();
    public MediatorLiveData<List<Scene>> sceneList = new MediatorLiveData<>();
    public RoomPickHelper roomPickHelper = new RoomPickHelper();
    private boolean isFirst = true;
    public MutableLiveData<Integer> distanceValue = new MutableLiveData<>(25);
    public MutableLiveData<Boolean> isProPanel = new MutableLiveData<>(false);
    final int COUNTS = 10;
    final long DURATION = 5000;
    long[] mHits = new long[10];

    static /* synthetic */ boolean lambda$initData$10(BaseDialog baseDialog, View view) {
        return false;
    }

    static /* synthetic */ boolean lambda$showDelFailDialog$6(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_panel_group_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.setting));
        setEditString("             ");
        this.controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        this.placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        this.request = Injection.repo().group().getGroupList(this, this.placeId);
        if (this.mViewBinding != 0) {
            ((ActPanelGroupSettingBinding) this.mViewBinding).setManagerOrOwner(Boolean.valueOf(isOwnerOrManager()));
            boolean z = true;
            if (((ActPanelGroupSettingBinding) this.mViewBinding).getManagerOrOwner().booleanValue()) {
                ((ActPanelGroupSettingBinding) this.mViewBinding).layoutGroupName.setEnabled(true);
                ((ActPanelGroupSettingBinding) this.mViewBinding).layoutEditGroup.setEnabled(true);
                ((ActPanelGroupSettingBinding) this.mViewBinding).tvDelete.setVisibility(0);
                ((ActPanelGroupSettingBinding) this.mViewBinding).ivGroupNameGo.setVisibility(0);
                ((ActPanelGroupSettingBinding) this.mViewBinding).ivDeviceCountGo.setVisibility(0);
                ((ActPanelGroupSettingBinding) this.mViewBinding).layoutChangeRoom.setEnabled(true);
                ((ActPanelGroupSettingBinding) this.mViewBinding).ivRoomNameGo.setVisibility(0);
                ((ActPanelGroupSettingBinding) this.mViewBinding).sbEngravedText.setEnabled(true);
            } else {
                ((ActPanelGroupSettingBinding) this.mViewBinding).layoutGroupName.setEnabled(false);
                ((ActPanelGroupSettingBinding) this.mViewBinding).layoutEditGroup.setEnabled(false);
                ((ActPanelGroupSettingBinding) this.mViewBinding).tvDelete.setVisibility(8);
                ((ActPanelGroupSettingBinding) this.mViewBinding).ivGroupNameGo.setVisibility(8);
                ((ActPanelGroupSettingBinding) this.mViewBinding).ivDeviceCountGo.setVisibility(8);
                ((ActPanelGroupSettingBinding) this.mViewBinding).layoutChangeRoom.setEnabled(false);
                ((ActPanelGroupSettingBinding) this.mViewBinding).ivRoomNameGo.setVisibility(8);
                ((ActPanelGroupSettingBinding) this.mViewBinding).sbEngravedText.setEnabled(false);
            }
            ((ActPanelGroupSettingBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda20
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    ActPanelGroupSetting.this.lambda$initView$0((View) obj);
                }
            }));
            ((ActPanelGroupSettingBinding) this.mViewBinding).sbEngravedText.setButtonEnable(getCurrentPlace().isOwner() || getCurrentPlace().isManager());
            ((ActPanelGroupSettingBinding) this.mViewBinding).sbEngravedText.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting.1
                @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                    ActPanelGroupSetting.this.setEngravedText(isChecked);
                }
            });
            ((ActPanelGroupSettingBinding) this.mViewBinding).sbMemorizePowerOnTip.setButtonEnable(getCurrentPlace().isOwner() || getCurrentPlace().isManager());
            ((ActPanelGroupSettingBinding) this.mViewBinding).sbMemorizePowerOnTip.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting.2
                @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                    ActPanelGroupSetting.this.setpowerOffStatus(isChecked);
                }
            });
            SwitchButton switchButton = ((ActPanelGroupSettingBinding) this.mViewBinding).sbNightMode;
            if (!getCurrentPlace().isOwner() && !getCurrentPlace().isManager()) {
                z = false;
            }
            switchButton.setButtonEnable(z);
            ((ActPanelGroupSettingBinding) this.mViewBinding).sbNightMode.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting.3
                @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                    if (Injection.repo().device().getSuperPanel() == null) {
                        ((ActPanelGroupSettingBinding) ActPanelGroupSetting.this.mViewBinding).sbNightMode.setCheckedNotByUser(false);
                        ActPanelGroupSetting.this.showNoSuperPanelDialog();
                    } else {
                        ActPanelGroupSetting.this.setNightMode(isChecked);
                    }
                }
            });
            ((ActPanelGroupSettingBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
            ((ActPanelGroupSettingBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
            ((ActPanelGroupSettingBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
            ((ActPanelGroupSettingBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda21
                @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
                public final void onRefresh(RefreshLayout refreshLayout) {
                    ActPanelGroupSetting.this.lambda$initView$1(refreshLayout);
                }
            });
            ((ActPanelGroupSettingBinding) this.mViewBinding).sbDistance.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting.4
                @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                    ActPanelGroupSetting.this.setSensingDistanceOn(isChecked);
                    if (isChecked) {
                        ((ActPanelGroupSettingBinding) ActPanelGroupSetting.this.mViewBinding).tvDistance.setText(ActPanelGroupSetting.this.distanceValue.getValue() + "%");
                        return;
                    }
                    ((ActPanelGroupSettingBinding) ActPanelGroupSetting.this.mViewBinding).tvDistance.setText(ActPanelGroupSetting.this.getString(R.string.sensing_distance_tip));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        switch (view.getId()) {
            case R.id.btn_adjust /* 2131296487 */:
                clickAdjustKRange(this.relateInfoAssistant);
                break;
            case R.id.layout_brt_button /* 2131297380 */:
                NavUtils.destination(ActBrtButtonSetting.class).withBoolean(Constants.GROUP_CONTROL, true).withLong("device_id", this.mGroup.getGroupId()).withRequestCode(5010).navigation(ActivityUtils.getTopActivity());
                break;
            case R.id.layout_change_language /* 2131297390 */:
                NavUtils.destination(ActLanguageSelect.class).withLong(Constants.CONTROL_ID, this.controlId).withInt(Constants.LANGUAGE_TPYE, this.relateInfoAssistant.getSwitchScreenLanguage()).withBoolean(Constants.GROUP_CONTROL, true).navigation(this);
                break;
            case R.id.layout_change_pattern /* 2131297391 */:
                showPatternDialog();
                break;
            case R.id.layout_change_room /* 2131297392 */:
                showEditRoomDialog();
                break;
            case R.id.layout_distance /* 2131297447 */:
                showSensingDistanceDialog();
                break;
            case R.id.layout_edit_group /* 2131297457 */:
                NavUtils.destination(ProductRepository.isBleGroup(this.mGroup.getModuleType()) ? ActSelectDeviceNew.class : ActAddWiFiGroup.class).withLong(Constants.PLACE_ID, this.mGroup.getPlaceId()).withLong(Constants.GROUP_ID, this.mGroup.getId()).withLong(Constants.FLOOR_ID, this.mGroup.getFloorId()).withLong(Constants.ROOM_ID, this.mGroup.getRoomId()).withRequestCode(5005).navigation(this);
                break;
            case R.id.layout_elderly_mode /* 2131297459 */:
                NavUtils.destination(ActScreenPanelElderlyMode.class).withLong(Constants.CONTROL_ID, this.controlId).withInt(Constants.ELDERLY_MODE, this.relateInfoAssistant.getSwitchScreenBigIcon()).withBoolean(Constants.GROUP_CONTROL, true).navigation(this);
                break;
            case R.id.layout_end_time /* 2131297463 */:
                showTimeDialog(false);
                break;
            case R.id.layout_group_name /* 2131297487 */:
                showEditNameDialog();
                break;
            case R.id.layout_on_off_time /* 2131297559 */:
                NavUtils.destination(ActLightOnOffTime.class).withLong(Constants.CONTROL_ID, this.mGroup.getId()).withBoolean(Constants.GROUP_CONTROL, true).withDefaultRequestCode().navigation(this);
                break;
            case R.id.layout_scene_and_automation /* 2131297612 */:
                NavUtils.destination(ActIntelligence.class).withLong("device_id", this.mGroup.getGroupId()).withBoolean(Constants.GROUP_CONTROL, true).navigation(this);
                break;
            case R.id.layout_sensitivity /* 2131297628 */:
                if (((ActPanelGroupSettingBinding) this.mViewBinding).sbDistance.isChecked()) {
                    showSensingDistanceDialog();
                    break;
                }
                break;
            case R.id.layout_set_on_state /* 2131297646 */:
                NavUtils.destination(ActSetLightOnState.class).withLong(Constants.CONTROL_ID, this.mGroup.getId()).withBoolean(Constants.GROUP_CONTROL, true).withDefaultRequestCode().navigation(this);
                break;
            case R.id.layout_start_time /* 2131297657 */:
                showTimeDialog(true);
                break;
            case R.id.layout_switch_position_setting /* 2131297672 */:
                NavUtils.destination(ActSmartPanelSwitchPositionSet.class).withLong(Constants.PLACE_ID, getCurrentPlace().getPlaceId()).withLong(Constants.CONTROL_ID, this.controlId).withBoolean(Constants.GROUP_CONTROL, true).navigation(this.activity);
                break;
            case R.id.layout_switch_setting /* 2131297673 */:
                NavUtils.destination(ActSmartPanelSwitchSetting.class).withLong("device_id", this.mGroup.getGroupId()).withBoolean(Constants.GROUP_CONTROL, true).withLong(Constants.PLACE_ID, this.mGroup.getPlaceId()).withBoolean(Constants.ICON_UPGRADE, true).navigation(this.activity);
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

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(RefreshLayout refreshLayout) {
        if (((BaseGroupSettingVM) this.mViewModel).controlObject.getValue() != null) {
            loadGroupStatus(((BaseGroupSettingVM) this.mViewModel).controlObject.getValue());
            ((BaseGroupSettingVM) this.mViewModel).queryScene(((BaseGroupSettingVM) this.mViewModel).controlObject.getValue().getGroupId());
        }
        ((ActPanelGroupSettingBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        List<Group> groupListByPlaceId = Injection.repo().group().getGroupListByPlaceId(this.placeId);
        List<Device> deviceListByPlaceId = Injection.repo().device().getDeviceListByPlaceId(this.placeId);
        List<Scene> sceneListByPlaceId = Injection.repo().scene().getSceneListByPlaceId(this.placeId, true);
        this.deviceList.setValue(deviceListByPlaceId);
        this.groupList.setValue(groupListByPlaceId);
        this.sceneList.setValue(sceneListByPlaceId);
        ((BaseGroupSettingVM) this.mViewModel).controlObject.setValue(Injection.repo().group().getGroupById(this.controlId));
        ((BaseGroupSettingVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActPanelGroupSetting.this.lambda$startObserve$2((Group) obj);
            }
        });
        ((BaseGroupSettingVM) this.mViewModel).getSceneAutomationOver.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActPanelGroupSetting.this.lambda$startObserve$3((Integer) obj);
            }
        });
        this.distanceValue.observe(this, new Observer<Integer>() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting.5
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer integer) {
                if (integer != null) {
                    ((ActPanelGroupSettingBinding) ActPanelGroupSetting.this.mViewBinding).tvDistance.setText(integer + "%");
                    if (Boolean.TRUE.equals(Boolean.valueOf(((ActPanelGroupSettingBinding) ActPanelGroupSetting.this.mViewBinding).sbDistance.isChecked()))) {
                        ((ActPanelGroupSettingBinding) ActPanelGroupSetting.this.mViewBinding).tvDistance.setText(integer + "%");
                        return;
                    }
                    ((ActPanelGroupSettingBinding) ActPanelGroupSetting.this.mViewBinding).tvDistance.setText(ActPanelGroupSetting.this.getString(R.string.sensing_distance_tip));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Group group) {
        String floorName;
        if (group != null) {
            this.mGroup = group;
            initRelateInfoList(group);
            ((ActPanelGroupSettingBinding) this.mViewBinding).tvGroupName.setText(group.getGroupName());
            ((ActPanelGroupSettingBinding) this.mViewBinding).tvDeviceCount.setText(String.format(Locale.US, "%d", Integer.valueOf(group.getDeviceIds() != null ? group.getDeviceIds().size() : 0)));
            this.roomPickHelper.startObserve(this, this.mGroup.getPlaceId(), this.mGroup.getRoomId());
            Floor floor = Injection.repo().home().getFloor(this.mGroup.getFloorId());
            Room room = Injection.repo().home().getRoom(this.mGroup.getRoomId());
            AppCompatTextView appCompatTextView = ((ActPanelGroupSettingBinding) this.mViewBinding).tvRoomName;
            if (room != null) {
                floorName = floor.getFloorName() + " " + room.getRoomName();
            } else {
                floorName = floor != null ? floor.getFloorName() : "";
            }
            appCompatTextView.setText(floorName);
            initData(group);
            loadGroupStatus(group);
            ((BaseGroupSettingVM) this.mViewModel).queryScene(this.mGroup.getGroupId());
            int i = 8;
            if (ProductRepository.getLightColorType((Object) this.mGroup) == 21 || ProductRepository.getLightColorType((Object) this.mGroup) == 19) {
                RelativeLayout relativeLayout = ((ActPanelGroupSettingBinding) this.mViewBinding).layoutDistance;
                if (ProductRepository.getLightColorType((Object) this.mGroup) == 19 && this.isProPanel.getValue().booleanValue()) {
                    i = 0;
                }
                relativeLayout.setVisibility(i);
                ((ActPanelGroupSettingBinding) this.mViewBinding).setSwitchSettingShow(true);
                ((ActPanelGroupSettingBinding) this.mViewBinding).setSwitchPositionSetting(true);
                if (this.needBrtButton) {
                    ((ActPanelGroupSettingBinding) this.mViewBinding).layoutBrtButton.setVisibility(0);
                    return;
                }
                return;
            }
            if (RelaySeparationHelper.isRelaySeparationDevice(this.mGroup)) {
                if (ProductRepository.getLightColorType((Object) this.mGroup) == 8) {
                    ((ActPanelGroupSettingBinding) this.mViewBinding).setSwitchPositionSetting(false);
                } else {
                    ((ActPanelGroupSettingBinding) this.mViewBinding).setSwitchPositionSetting(true);
                }
                if (this.needBrtButton) {
                    ((ActPanelGroupSettingBinding) this.mViewBinding).layoutBrtButton.setVisibility(0);
                }
                ((ActPanelGroupSettingBinding) this.mViewBinding).setSwitchSettingShow(true);
                return;
            }
            ((ActPanelGroupSettingBinding) this.mViewBinding).layoutDistance.setVisibility(8);
            ((ActPanelGroupSettingBinding) this.mViewBinding).setSwitchSettingShow(false);
            ((ActPanelGroupSettingBinding) this.mViewBinding).setSwitchPositionSetting(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Integer num) {
        ((ActPanelGroupSettingBinding) this.mViewBinding).tvRelatedNum.setText(String.valueOf(num));
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((BaseGroupSettingVM) this.mViewModel).controlObject.setValue(Injection.repo().group().getGroupById(this.controlId));
    }

    private void showEditNameDialog() {
        EditDialog.asDefault().setContent(this.mGroup.getGroupName()).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActPanelGroupSetting.this.lambda$showEditNameDialog$4((String) obj);
            }
        }).showDialog(getSupportFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$4(String str) {
        ((BaseGroupSettingVM) this.mViewModel).lambda$showEditNameDialog$5(str);
    }

    private void showDelDialog() {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.tip_del_group)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda10
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showDelDialog$5;
                lambda$showDelDialog$5 = ActPanelGroupSetting.this.lambda$showDelDialog$5(baseDialog, view);
                return lambda$showDelDialog$5;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showDelDialog$5(BaseDialog baseDialog, View view) {
        delGroupFromNet(this.mGroup);
        return false;
    }

    private void showDelFailDialog() {
        MessageDialog.show(this, getString(R.string.del_fail), getString(R.string.del_fail_tip)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda14
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return ActPanelGroupSetting.lambda$showDelFailDialog$6(baseDialog, view);
            }
        });
    }

    private void showSensingDistanceDialog() {
        SensingDistanceDialog.asDefault().setCallback(new SensingDistanceDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting.6
            @Override // com.ltech.smarthome.view.dialog.SensingDistanceDialog.OnConfirmCallback
            public void onConfirmClick(SensingDistanceDialog dialog) {
                ActPanelGroupSetting.this.distanceValue.setValue(Integer.valueOf(dialog.getProgress()));
                ActPanelGroupSetting.this.changeSensingDistance();
            }
        }).setProgress(this.distanceValue.getValue() != null ? this.distanceValue.getValue().intValue() : 25).showDialog(this);
    }

    public void changeSensingDistance() {
        int i;
        int intValue;
        if (this.distanceValue.getValue() != null && (intValue = this.distanceValue.getValue().intValue()) != 25) {
            if (intValue == 50) {
                i = 1;
            } else if (intValue == 75) {
                i = 2;
            } else if (intValue == 100) {
                i = 3;
            }
            showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
            CmdAssistant.getDeviceAssistant(this.mGroup, new int[0]).setSensitivity(ActivityUtils.getTopActivity(), i, new IAction() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda13
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActPanelGroupSetting.this.lambda$changeSensingDistance$7((Boolean) obj);
                }
            });
        }
        i = 0;
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
        CmdAssistant.getDeviceAssistant(this.mGroup, new int[0]).setSensitivity(ActivityUtils.getTopActivity(), i, new IAction() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda13
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActPanelGroupSetting.this.lambda$changeSensingDistance$7((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeSensingDistance$7(Boolean bool) {
        dismissLoadingDialog();
        if (bool.booleanValue()) {
            SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_setting_success));
        } else {
            SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_setting_failed));
        }
    }

    private void delGroupFromNet(final Group group) {
        ((ObservableSubscribeProxy) Injection.net().deleteGroup(group.getGroupId()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda16
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActPanelGroupSetting.this.lambda$delGroupFromNet$8((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActPanelGroupSetting$$ExternalSyntheticLambda23(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda17
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActPanelGroupSetting.this.lambda$delGroupFromNet$9(group, obj);
            }
        }, new SmartErrorComsumer());
        unSubscribePublicationAddress(group);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delGroupFromNet$8(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.removing));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delGroupFromNet$9(Group group, Object obj) throws Exception {
        RelaySeparationHelper.removeSub(group);
        Injection.repo().group().removeGroupFromDb(group.getId());
        setResult(5002);
        finishActivity();
    }

    private void unSubscribePublicationAddress(Group group) {
        LHomeLog.i(getClass(), "delete device--->" + group);
        CmdAssistant.getSettingCmdAssistant(null, new int[0]).unSubscribePublishAddress(ActivityUtils.getTopActivity(), group.getGroupAddress(), ProductRepository.getGroupAgreementId(group.getModuleType(), group.getControlType()), new int[0]);
    }

    public Place getCurrentPlace() {
        Place value = Injection.repo().home().getSelectPlace().getValue();
        return value == null ? new Place() : value;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3001) {
            refreshRelateInfoList(Injection.repo().group().getGroupById(this.mGroup.getId()));
            this.keyAdapter.notifyDataSetChanged();
        } else if (requestCode == 5005 && resultCode == -1) {
            ((BaseGroupSettingVM) this.mViewModel).controlObject.setValue(Injection.repo().group().getGroupById(this.controlId));
        } else if (requestCode == 5010 && resultCode == -1) {
            ((BaseGroupSettingVM) this.mViewModel).controlObject.setValue(Injection.repo().group().getGroupById(this.controlId));
        }
    }

    private void initData(final Group group) {
        refreshRelateInfoList(group);
        int lightColorType = ProductRepository.getLightColorType((Object) group);
        int i = R.string.pattern_1;
        if (lightColorType == 21) {
            this.isProPanel.setValue(true);
            showScreenSettings();
            ((ActPanelGroupSettingBinding) this.mViewBinding).layoutChangePattern.setVisibility(0);
            AppCompatTextView appCompatTextView = ((ActPanelGroupSettingBinding) this.mViewBinding).tvPattern;
            if (this.relateInfoAssistant.getSwitchShowType() != 1) {
                i = R.string.pattern_2;
            }
            appCompatTextView.setText(i);
            int proPanelCount = getProPanelCount(group);
            if (proPanelCount != 0 && proPanelCount != group.getDeviceIds().size()) {
                ((ActPanelGroupSettingBinding) this.mViewBinding).layoutChangePattern.setVisibility(0);
            } else {
                ((ActPanelGroupSettingBinding) this.mViewBinding).layoutChangePattern.setVisibility(8);
            }
        } else if (group.getDeviceIds().size() > 0) {
            Iterator<Long> it = group.getDeviceIds().iterator();
            int i2 = 0;
            while (it.hasNext()) {
                Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(it.next().longValue());
                if (deviceByDeviceId != null && (deviceByDeviceId.getProductId().equals(ProductId.ID_SMART_SWITCH_S1_PRO) || deviceByDeviceId.getProductId().equals(ProductId.ID_SMART_SWITCH_S2_PRO) || deviceByDeviceId.getProductId().equals(ProductId.ID_SMART_SWITCH_S3_PRO) || deviceByDeviceId.getProductId().equals(ProductId.ID_SMART_SWITCH_S6_PRO))) {
                    i2++;
                }
            }
            if (group.getDeviceIds().size() < 2) {
                ((ActPanelGroupSettingBinding) this.mViewBinding).layoutChangePattern.setVisibility(8);
            }
            if (i2 != 0 && i2 != group.getDeviceIds().size()) {
                ((ActPanelGroupSettingBinding) this.mViewBinding).layoutChangePattern.setVisibility(0);
                AppCompatTextView appCompatTextView2 = ((ActPanelGroupSettingBinding) this.mViewBinding).tvPattern;
                if (this.relateInfoAssistant.getSwitchShowType() != 1) {
                    i = R.string.pattern_2;
                }
                appCompatTextView2.setText(i);
                if (this.relateInfoAssistant.getSwitchShowType() == 2) {
                    showScreenSettings();
                } else {
                    setScreenSettingsGone();
                }
            } else if (i2 == 0) {
                setScreenSettingsGone();
            } else {
                showScreenSettings();
            }
            this.isProPanel.setValue(Boolean.valueOf(i2 > 0));
            if (i2 == 0 && ProductRepository.getLightColorType((Object) this.mGroup) == 19) {
                ((ActPanelGroupSettingBinding) this.mViewBinding).layoutEngraved.setVisibility(0);
            }
        }
        BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = this.keyAdapter;
        if (baseQuickAdapter == null) {
            BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<RelateInfoItem, BaseViewHolder>(R.layout.item_smart_panel_key_set, this.relatedInfoList) { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting.7
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, RelateInfoItem item) {
                    String[] stringArray;
                    int lightColorType2 = ProductRepository.getLightColorType((Object) group);
                    if (lightColorType2 == 19) {
                        stringArray = ActPanelGroupSetting.this.getResources().getStringArray(R.array.smart_panel_s6_pro_key_select);
                    } else {
                        stringArray = ActPanelGroupSetting.this.getResources().getStringArray(R.array.smart_panel_s4_key_select);
                    }
                    if (item.infoName != null && !TextUtils.isEmpty(item.infoName)) {
                        helper.setText(R.id.tv_main, item.infoName);
                    } else if (ActPanelGroupSetting.this.relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()) != null) {
                        if (ActPanelGroupSetting.this.relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()).name == null || ActPanelGroupSetting.this.relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()).name.equals("")) {
                            helper.setText(R.id.tv_main, stringArray[helper.getAdapterPosition()]);
                        } else {
                            helper.setText(R.id.tv_main, ActPanelGroupSetting.this.relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()).name);
                        }
                    } else {
                        helper.setText(R.id.tv_main, stringArray[helper.getAdapterPosition()]);
                    }
                    if (item.infoName == null || item.type == 0) {
                        helper.setText(R.id.tv_sub_text, ActPanelGroupSetting.this.getString(R.string.no_bind_object));
                    } else {
                        helper.setText(R.id.tv_sub_text, item.actionInfo);
                    }
                    boolean z = true;
                    helper.setGone(R.id.iv_go, ActPanelGroupSetting.this.getCurrentPlace().isOwner() || ActPanelGroupSetting.this.getCurrentPlace().isManager());
                    View view = helper.itemView;
                    if (!ActPanelGroupSetting.this.getCurrentPlace().isOwner() && !ActPanelGroupSetting.this.getCurrentPlace().isManager()) {
                        z = false;
                    }
                    view.setEnabled(z);
                    helper.addOnClickListener(R.id.tv_main);
                    TextView textView = (TextView) helper.getView(R.id.tv_main);
                    if ((lightColorType2 != 19 || helper.getAdapterPosition() <= 2) && (lightColorType2 != 21 || helper.getAdapterPosition() <= 3)) {
                        textView.setCompoundDrawablesWithIntrinsicBounds(ActPanelGroupSetting.this.isOwnerOrManager() ? R.mipmap.ic_edit_black : 0, 0, 0, 0);
                    } else {
                        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    }
                }
            };
            this.keyAdapter = baseQuickAdapter2;
            baseQuickAdapter2.setOnItemChildClickListener(new AnonymousClass8(group));
            this.keyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda11
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter3, View view, int i3) {
                    ActPanelGroupSetting.this.lambda$initData$11(baseQuickAdapter3, view, i3);
                }
            });
            this.keyAdapter.bindToRecyclerView(((ActPanelGroupSettingBinding) this.mViewBinding).rvKeys);
            ((ActPanelGroupSettingBinding) this.mViewBinding).rvKeys.setLayoutManager(new LinearLayoutManager(this));
        } else {
            baseQuickAdapter.setNewData(this.relatedInfoList);
        }
        this.keyAdapter.notifyDataSetChanged();
    }

    /* renamed from: com.ltech.smarthome.ui.group.ActPanelGroupSetting$8, reason: invalid class name */
    class AnonymousClass8 implements BaseQuickAdapter.OnItemChildClickListener {
        final /* synthetic */ Group val$group;

        static /* synthetic */ boolean lambda$onItemChildClick$0(BaseDialog baseDialog, View view) {
            return false;
        }

        AnonymousClass8(final Group val$group) {
            this.val$group = val$group;
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            if (ActPanelGroupSetting.this.isOwnerOrManager()) {
                int lightColorType = ProductRepository.getLightColorType((Object) this.val$group);
                if (lightColorType != 19 || position <= 2) {
                    if (lightColorType != 21 || position <= 3) {
                        if (ActPanelGroupSetting.this.keyAdapter.getData().get(position).infoName == null || ActPanelGroupSetting.this.keyAdapter.getData().get(position).type == 0) {
                            RelateInfoItem relateInfoItem = ActPanelGroupSetting.this.keyAdapter.getData().get(position);
                            if (relateInfoItem.infoName == null || TextUtils.isEmpty(relateInfoItem.infoName)) {
                                ActPanelGroupSetting actPanelGroupSetting = ActPanelGroupSetting.this;
                                actPanelGroupSetting.showEditKeyNameDialog(actPanelGroupSetting.getResources().getStringArray(R.array.smart_scene_panel_s8_key_select)[position], position);
                                return;
                            } else {
                                ActPanelGroupSetting.this.showEditKeyNameDialog(relateInfoItem.infoName, position);
                                return;
                            }
                        }
                        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), ActPanelGroupSetting.this.getString(R.string.app_str_operation_failure), ActPanelGroupSetting.this.getString(R.string.app_str_smart_panel_rename_tip)).setCancelable(false).setOkButton(ActivityUtils.getTopActivity().getString(R.string.app_str_accept), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$8$$ExternalSyntheticLambda0
                            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                            public final boolean onClick(BaseDialog baseDialog, View view2) {
                                return ActPanelGroupSetting.AnonymousClass8.lambda$onItemChildClick$0(baseDialog, view2);
                            }
                        });
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initData$11(BaseQuickAdapter baseQuickAdapter, View view, final int i) {
        if (this.keyAdapter.getData().get(i).infoName == null || this.keyAdapter.getData().get(i).type == 0) {
            MessageDialog.show(this, getString(R.string.tips), getString(R.string.app_str_smart_panel_bind_tip)).setCancelable(false).setOkButton(getString(R.string.app_str_continue_to_bind), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting.9
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public boolean onClick(BaseDialog baseDialog, View v) {
                    ActPanelGroupSetting.this.showBindDialog(i);
                    return false;
                }
            }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda3
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view2) {
                    return ActPanelGroupSetting.lambda$initData$10(baseDialog, view2);
                }
            });
        } else {
            showUnbindDialog(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEditKeyNameDialog(String name, final int position) {
        EditDialog.asDefault().setContent(name).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction<String>() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting.10
            @Override // com.ltech.smarthome.base.IAction
            public void act(String s) {
                ActPanelGroupSetting.this.changeKeyName(s, position);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeKeyName(String s, int position) {
        RelatedInfoExtParam.RelateInfo relateInfo = new RelatedInfoExtParam.RelateInfo();
        relateInfo.name = s;
        if (this.relateInfoAssistant.getRelateInfo(position) != null) {
            relateInfo.type = this.relateInfoAssistant.getRelateInfo(position).type;
            relateInfo.action = this.relateInfoAssistant.getRelateInfo(position).action;
            relateInfo.objectId = this.relateInfoAssistant.getRelateInfo(position).objectId;
            relateInfo.bindingZone = this.relateInfoAssistant.getRelateInfo(position).bindingZone;
        }
        this.relateInfoAssistant.setRelateInfo(position, relateInfo);
        uploadData(s, position);
    }

    private void showScreenSettings() {
        ((ActPanelGroupSettingBinding) this.mViewBinding).tvNightModeTip.setText(R.string.app_str_pro_night_mode_tip);
        if (ProductRepository.getLightColorType((Object) this.mGroup) == 19) {
            ((ActPanelGroupSettingBinding) this.mViewBinding).layoutElderlyMode.setVisibility(8);
        } else {
            ((ActPanelGroupSettingBinding) this.mViewBinding).layoutElderlyMode.setVisibility(0);
        }
        ((ActPanelGroupSettingBinding) this.mViewBinding).layoutChangeLanguage.setVisibility(0);
        ((ActPanelGroupSettingBinding) this.mViewBinding).tvElderlyModeTip.setText(this.relateInfoAssistant.getSwitchScreenBigIcon() == 2 ? getString(R.string.app_str_turned_on) : getString(R.string.app_str_turned_off));
        ((ActPanelGroupSettingBinding) this.mViewBinding).tvLanguageTip.setText(this.relateInfoAssistant.getSwitchScreenLanguage() == 2 ? getString(R.string.language_english) : getString(R.string.language_chinese));
    }

    private void setScreenSettingsGone() {
        ((ActPanelGroupSettingBinding) this.mViewBinding).tvNightModeTip.setText(R.string.app_str_night_mode_tip);
        ((ActPanelGroupSettingBinding) this.mViewBinding).layoutElderlyMode.setVisibility(8);
        ((ActPanelGroupSettingBinding) this.mViewBinding).layoutChangeLanguage.setVisibility(8);
    }

    private void showUnbindDialog(final int position) {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.reset_relate));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActPanelGroupSetting.this.lambda$showUnbindDialog$12(position, (Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnbindDialog$12(int i, Integer num) {
        unBindRelateInfo(i);
    }

    public void unBindRelateInfo(final int position) {
        int i;
        int i2;
        if (Injection.state().isConnectOuterNet()) {
            int groupAddress = this.mGroup.getGroupAddress();
            RelatedInfoExtParam.RelateInfo relateInfo = this.relateInfoAssistant.getRelateInfo(position);
            if (relateInfo != null) {
                if (relateInfo.isRelateGroupInfo()) {
                    if (this.groupList.getValue() != null) {
                        i2 = 0;
                        for (Group group : this.groupList.getValue()) {
                            if (group.getGroupId() == relateInfo.objectId) {
                                i2 = ProductRepository.getGroupAgreementId(group.getModuleType(), group.getControlType());
                                group.getGroupAddress();
                            }
                        }
                        i = i2;
                    }
                } else if (!relateInfo.isRelateDeviceInfo()) {
                    i = 2;
                } else if (this.deviceList.getValue() != null) {
                    i2 = 0;
                    for (Device device : this.deviceList.getValue()) {
                        if (device.getDeviceId() == relateInfo.objectId) {
                            i2 = ProductRepository.getAgreementIdByPid(device.getProductId());
                            ((BleParam) device.getParam(BleParam.class)).getUnicastAddress();
                        }
                    }
                    i = i2;
                }
                showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.unsubscribing));
                CmdAssistant.getSettingCmdAssistant(this.mGroup, new int[0]).unSubscribeInSwitchPanel(ActivityUtils.getTopActivity(), groupAddress, 1 << position, i, new IAction() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda19
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActPanelGroupSetting.this.lambda$unBindRelateInfo$13(position, (Boolean) obj);
                    }
                });
                return;
            }
            i = 0;
            showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.unsubscribing));
            CmdAssistant.getSettingCmdAssistant(this.mGroup, new int[0]).unSubscribeInSwitchPanel(ActivityUtils.getTopActivity(), groupAddress, 1 << position, i, new IAction() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda19
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActPanelGroupSetting.this.lambda$unBindRelateInfo$13(position, (Boolean) obj);
                }
            });
            return;
        }
        SmartToast.showShort(R.string.no_network);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unBindRelateInfo$13(int i, Boolean bool) {
        if (bool.booleanValue()) {
            this.relateInfoAssistant.resetSmartPanelRelateInfo(i);
            uploadData(i);
        } else {
            dismissLoadingDialog();
            ViewHelpUtil.showUnBindFailDialog((FragmentActivity) ActivityUtils.getTopActivity());
        }
    }

    private void uploadData(final int position) {
        ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(this.mGroup.getGroupId(), this.relateInfoAssistant.getExtParamString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActPanelGroupSetting.this.lambda$uploadData$14((Disposable) obj);
            }
        }).doFinally(new ActPanelGroupSetting$$ExternalSyntheticLambda23(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActPanelGroupSetting.this.lambda$uploadData$15(position, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$14(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$15(int i, Object obj) throws Exception {
        this.mGroup.setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().group().saveGroup(this.mGroup);
        SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.save_success));
        refreshRelateInfoList(this.mGroup);
        this.keyAdapter.notifyItemChanged(i);
    }

    private void uploadData(final String s, final int position) {
        ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(this.mGroup.getGroupId(), this.relateInfoAssistant.getExtParamString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda22
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActPanelGroupSetting.this.lambda$uploadData$16((Disposable) obj);
            }
        }).doFinally(new ActPanelGroupSetting$$ExternalSyntheticLambda23(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda24
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActPanelGroupSetting.this.lambda$uploadData$17(position, s, obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda25
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActPanelGroupSetting.this.lambda$uploadData$18((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$16(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$17(int i, String str, Object obj) throws Exception {
        this.mGroup.setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().group().saveGroup(this.mGroup);
        ((BaseGroupSettingVM) this.mViewModel).controlObject.setValue(this.mGroup);
        showSuccessDialog(ActivityUtils.getTopActivity().getString(R.string.save_success));
        this.keyAdapter.getData().get(i).infoName = str;
        this.keyAdapter.notifyItemChanged(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$18(Throwable th) throws Exception {
        showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
    }

    public void refreshRelateInfoList(Group group) {
        RelateInfoUtils.initRelateInfoList(group);
        this.relateInfoAssistant = RelateInfoUtils.relateInfoAssistant;
        this.relatedInfoList.clear();
        this.relatedInfoList.addAll(RelateInfoUtils.relatedInfoList);
        ((ActPanelGroupSettingBinding) this.mViewBinding).layoutSetKRange.setVisibility(this.relateInfoAssistant.isShowKRange() ? 0 : 8);
    }

    protected void showEditRoomDialog() {
        if (this.roomPickHelper.getCanSetRoom() == null || !this.roomPickHelper.getCanSetRoom().getValue().booleanValue()) {
            return;
        }
        RoomSelectDialog.asDefault().setFloorList(this.roomPickHelper.getCurrentFloorNames()).setRoomList(this.roomPickHelper.getCurrentRoomNames()).setSelectFloorPosition(this.roomPickHelper.getSelectFloorPosition()).setSelectRoomPosition(this.roomPickHelper.getSelectRoomPosition()).setSelectListener(new RoomSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting.11
            @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
            public void confirm(int floorPosition, int roomPosition) {
                ActPanelGroupSetting.this.roomPickHelper.setSelectPosition(floorPosition, roomPosition);
                ((BaseGroupSettingVM) ActPanelGroupSetting.this.mViewModel).changeGroupPlace(ActPanelGroupSetting.this.roomPickHelper.getSelectFloorId(), ActPanelGroupSetting.this.roomPickHelper.getSelectRoomId());
            }

            @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
            public void onFloorSelect(RoomSelectDialog dialog, int floorPosition) {
                dialog.setRoomList(ActPanelGroupSetting.this.roomPickHelper.getRoomNames(floorPosition));
                dialog.notifyDialog();
            }
        }).showDialog(this);
    }

    public void loadGroupStatus(final Group group) {
        if (this.isFirst) {
            this.isFirst = false;
            this.mCmdAssistant = CmdAssistant.getSettingCmdAssistant(group, new int[0]);
            CmdAssistant.getQueryCmdAssistant(group, new int[0]).querySmartPanelSetting(ActivityUtils.getTopActivity(), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting.12
                @Override // com.ltech.smarthome.base.IAction
                public void act(ResponseMsg msg) {
                    if (msg == null) {
                        return;
                    }
                    ActPanelGroupSetting.this.state = ((IPanelParser) Injection.strategy().getParserStrategy(msg.getAgreementId())).parserSmartPanelSettingState(msg.getAgreementId(), ActPanelGroupSetting.this.mGroup.getGroupAddress(), msg.getResData());
                    if (ActPanelGroupSetting.this.state != null) {
                        group.setSetting(GsonUtils.toJson(ActPanelGroupSetting.this.state));
                        Injection.repo().group().saveGroup(group);
                        if (ActPanelGroupSetting.this.mViewBinding != null) {
                            ((ActPanelGroupSettingBinding) ActPanelGroupSetting.this.mViewBinding).sbEngravedText.setCheckedNotByUser(ActPanelGroupSetting.this.state.getEngravedTextMode() == 1);
                            boolean z = ActPanelGroupSetting.this.state.getNightMode() != 0;
                            if (ActPanelGroupSetting.this.isProPanel.getValue().booleanValue()) {
                                z = ActPanelGroupSetting.this.state.getNightMode() == 2;
                            }
                            ((ActPanelGroupSettingBinding) ActPanelGroupSetting.this.mViewBinding).sbNightMode.setCheckedNotByUser(z);
                            ((ActPanelGroupSettingBinding) ActPanelGroupSetting.this.mViewBinding).layoutMore.setVisibility(0);
                            ((ActPanelGroupSettingBinding) ActPanelGroupSetting.this.mViewBinding).ivStartTimeGo.setVisibility(ActPanelGroupSetting.this.state.isOld() ? 8 : 0);
                            ((ActPanelGroupSettingBinding) ActPanelGroupSetting.this.mViewBinding).ivEndTimeGo.setVisibility(ActPanelGroupSetting.this.state.isOld() ? 8 : 0);
                            ((ActPanelGroupSettingBinding) ActPanelGroupSetting.this.mViewBinding).tvStartTime.setText(String.format(Locale.CHINA, "%02d:%02d", Integer.valueOf(ActPanelGroupSetting.this.state.getStartH()), Integer.valueOf(ActPanelGroupSetting.this.state.getStartM())));
                            ((ActPanelGroupSettingBinding) ActPanelGroupSetting.this.mViewBinding).tvEndTime.setText(String.format(Locale.CHINA, "%02d:%02d", Integer.valueOf(ActPanelGroupSetting.this.state.getEndH()), Integer.valueOf(ActPanelGroupSetting.this.state.getEndM())));
                            ((ActPanelGroupSettingBinding) ActPanelGroupSetting.this.mViewBinding).layoutStartTime.setEnabled(!ActPanelGroupSetting.this.state.isOld());
                            ((ActPanelGroupSettingBinding) ActPanelGroupSetting.this.mViewBinding).layoutEndTime.setEnabled(!ActPanelGroupSetting.this.state.isOld());
                            ((ActPanelGroupSettingBinding) ActPanelGroupSetting.this.mViewBinding).layoutStartTime.setVisibility(z ? 0 : 8);
                            ((ActPanelGroupSettingBinding) ActPanelGroupSetting.this.mViewBinding).layoutEndTime.setVisibility(z ? 0 : 8);
                            ((ActPanelGroupSettingBinding) ActPanelGroupSetting.this.mViewBinding).sbDistance.setCheckedNotByUser(ActPanelGroupSetting.this.state.getSensingDistanceSwitch() == 1);
                            int sensingDistance = ActPanelGroupSetting.this.state.getSensingDistance();
                            if (sensingDistance == 0) {
                                ActPanelGroupSetting.this.distanceValue.setValue(25);
                            } else if (sensingDistance == 1) {
                                ActPanelGroupSetting.this.distanceValue.setValue(50);
                            } else if (sensingDistance == 2) {
                                ActPanelGroupSetting.this.distanceValue.setValue(75);
                            } else if (sensingDistance == 3) {
                                ActPanelGroupSetting.this.distanceValue.setValue(100);
                            }
                        }
                        ActPanelGroupSetting.this.queryState();
                    }
                }
            });
            refreshRelateInfoList(group);
        }
    }

    public void queryState() {
        CmdAssistant.getLightCmdAssistant(this.mGroup, new int[0]).queryOnState(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda18
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActPanelGroupSetting.this.lambda$queryState$19((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryState$19(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getResData() == null || responseMsg.getResData().length() < 18) {
            return;
        }
        int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 18), 16);
        this.mGroup.setMemorizePowerOff(parseInt);
        Injection.repo().group().saveGroup(this.mGroup);
        if (this.mViewBinding != 0) {
            ((ActPanelGroupSettingBinding) this.mViewBinding).layoutMore.setVisibility(0);
            if (parseInt == 1 || parseInt == 2) {
                ((ActPanelGroupSettingBinding) this.mViewBinding).sbMemorizePowerOnTip.setCheckedNotByUser(false);
            } else {
                if (parseInt != 3) {
                    return;
                }
                ((ActPanelGroupSettingBinding) this.mViewBinding).sbMemorizePowerOnTip.setCheckedNotByUser(true);
            }
        }
    }

    public void setNightMode(boolean b2) {
        if (this.state != null) {
            ((ActPanelGroupSettingBinding) this.mViewBinding).layoutStartTime.setVisibility(b2 ? 0 : 8);
            ((ActPanelGroupSettingBinding) this.mViewBinding).layoutEndTime.setVisibility(b2 ? 0 : 8);
            this.state.setNightMode(b2 ? this.isProPanel.getValue().booleanValue() ? 2 : 1 : 0);
            setting();
        }
    }

    public void setAutoTurnOff(boolean b2) {
        SmartPanelSettingState smartPanelSettingState = this.state;
        if (smartPanelSettingState != null) {
            if (smartPanelSettingState.getNightMode() != 0) {
                this.state.setNightMode(b2 ? 2 : 1);
            } else {
                this.state.setNightMode(b2 ? 2 : 0);
            }
            setting();
        }
    }

    public void setEngravedText(boolean z) {
        SmartPanelSettingState smartPanelSettingState = this.state;
        if (smartPanelSettingState != null) {
            smartPanelSettingState.setEngravedTextMode(z ? 1 : 0);
            setting();
        }
    }

    public void setpowerOffStatus(boolean b2) {
        if (this.state != null) {
            CmdAssistant.getLightCmdAssistant(this.mGroup, new int[0]).setOnState(ActivityUtils.getTopActivity(), b2 ? 3 : 2, 0, 0, new IAction() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda15
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ((Boolean) obj).booleanValue();
                }
            });
        }
    }

    public void setStarTime(int h, int m2) {
        int endH = ((this.state.getEndH() * 60) + this.state.getEndM()) - ((h * 60) + m2);
        if (endH > 0 && endH < 30) {
            SmartToast.showShort(String.format(ActivityUtils.getTopActivity().getString(R.string.app_str_interval_time_less_than), 30));
            return;
        }
        ((ActPanelGroupSettingBinding) this.mViewBinding).tvStartTime.setText(String.format(Locale.CHINA, "%02d:%02d", Integer.valueOf(h), Integer.valueOf(m2)));
        this.state.setStartH(h);
        this.state.setStartM(m2);
        setting();
    }

    public void setEndTime(int h, int m2) {
        SmartPanelSettingState smartPanelSettingState = this.state;
        if (smartPanelSettingState != null) {
            int startH = ((h * 60) + m2) - ((smartPanelSettingState.getStartH() * 60) + this.state.getStartM());
            if (startH > 0 && startH < 30) {
                SmartToast.showShort(String.format(ActivityUtils.getTopActivity().getString(R.string.app_str_interval_time_less_than), 30));
                return;
            }
            ((ActPanelGroupSettingBinding) this.mViewBinding).tvEndTime.setText(String.format(Locale.CHINA, "%02d:%02d", Integer.valueOf(h), Integer.valueOf(m2)));
            this.state.setEndH(h);
            this.state.setEndM(m2);
            setting();
        }
    }

    private void setting() {
        this.mCmdAssistant.setSmartPanelMode(ActivityUtils.getTopActivity(), this.state.getDoubleLight(), this.state.getReverseLight(), this.state.getNightMode(), this.state.getEngravedTextMode(), this.state.getStartH(), this.state.getStartM(), this.state.getEndH(), this.state.getEndM(), new IAction<Boolean>(this) { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting.13
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                aBoolean.booleanValue();
            }
        });
    }

    public void showTimeDialog(final boolean isStart) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 24; i++) {
            arrayList.add(i < 10 ? "0" + i : String.valueOf(i));
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i2 = 0; i2 < 60; i2++) {
            arrayList2.add(i2 < 10 ? "0" + i2 : String.valueOf(i2));
        }
        SmartPanelSettingState smartPanelSettingState = this.state;
        if (smartPanelSettingState != null) {
            this.mSelectHour = isStart ? smartPanelSettingState.getStartH() : smartPanelSettingState.getEndH();
            SmartPanelSettingState smartPanelSettingState2 = this.state;
            this.mSelectMin = isStart ? smartPanelSettingState2.getStartM() : smartPanelSettingState2.getEndM();
            TimeSelectDialog.asDefault().setTitle(getString(isStart ? R.string.start_time : R.string.end_time)).setMinList(arrayList).setSecList(arrayList2).withUnit(false).setMinUnit(getString(R.string.hour)).setSecUnit(getString(R.string.min)).setSelectMinPosition(this.mSelectHour).setSelectSecPosition(this.mSelectMin).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda9
                @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
                public final void confirm(int i3, int i4) {
                    ActPanelGroupSetting.this.lambda$showTimeDialog$21(isStart, i3, i4);
                }
            }).showDialog(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showTimeDialog$21(boolean z, int i, int i2) {
        if (this.mSelectHour == i && this.mSelectMin == i2) {
            return;
        }
        this.mSelectHour = i;
        this.mSelectMin = i2;
        if (z) {
            setStarTime(i, i2);
        } else {
            setEndTime(i, i2);
        }
    }

    public void showPatternDialog() {
        SelectSwitchPatternDialog onSaveListener = SelectSwitchPatternDialog.asDefault().setSelectPosition(this.relateInfoAssistant.getSwitchShowType() - 1).setOnSaveListener(new SelectSwitchPatternDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda27
            @Override // com.ltech.smarthome.view.dialog.SelectSwitchPatternDialog.OnSaveListener
            public final boolean onSave(int i) {
                boolean lambda$showPatternDialog$23;
                lambda$showPatternDialog$23 = ActPanelGroupSetting.this.lambda$showPatternDialog$23(i);
                return lambda$showPatternDialog$23;
            }
        });
        if (21 == ProductRepository.getLightColorType((Object) this.mGroup)) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(Integer.valueOf(R.mipmap.g4_sel));
            arrayList.add(Integer.valueOf(R.mipmap.g4_nor));
            arrayList.add(Integer.valueOf(R.mipmap.g4pro_sel));
            arrayList.add(Integer.valueOf(R.mipmap.g4pro_nor));
            onSaveListener.setCustomPic(arrayList);
        } else if (19 == ProductRepository.getLightColorType((Object) this.mGroup)) {
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(Integer.valueOf(R.mipmap.panel6_1_sel));
            arrayList2.add(Integer.valueOf(R.mipmap.panel6_1_nor));
            arrayList2.add(Integer.valueOf(R.mipmap.panel6_2_sel));
            arrayList2.add(Integer.valueOf(R.mipmap.panel6_2_nor));
            onSaveListener.setCustomPic(arrayList2);
        } else {
            onSaveListener.setZoneCount(this.relatedInfoList.size());
        }
        onSaveListener.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showPatternDialog$23(int i) {
        int i2 = i + 1;
        if (this.relateInfoAssistant.getSwitchShowType() != i2) {
            this.relateInfoAssistant.setSwitchShowType(i2);
            ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(this.mGroup.getGroupId(), this.relateInfoAssistant.getExtParamString()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda26
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActPanelGroupSetting.this.lambda$showPatternDialog$22(obj);
                }
            });
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPatternDialog$22(Object obj) throws Exception {
        this.mGroup.setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().group().saveGroup(this.mGroup);
        ((BaseGroupSettingVM) this.mViewModel).controlObject.setValue(this.mGroup);
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
                    showSettingDialog();
                }
            }
        }
    }

    private void showSettingDialog() {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.setting)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.app_str_change_device_send_times));
        arrayList.add(getString(R.string.app_str_change_device_ttl));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActPanelGroupSetting.this.lambda$showSettingDialog$24((Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.setOutCancel(false);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSettingDialog$24(Integer num) {
        if (num.intValue() == 0) {
            ((BaseGroupSettingVM) this.mViewModel).showEditTimesDialog(this);
        } else if (num.intValue() == 1) {
            ((BaseGroupSettingVM) this.mViewModel).showEditTTLDialog(this);
        }
    }

    public Place getCurPlace() {
        return Injection.repo().home().getSelPlace();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showBindDialog(final int position) {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.device));
        arrayList.add(getString(R.string.local_scene));
        arrayList.add(getString(R.string.dali_scene));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActPanelGroupSetting.this.lambda$showBindDialog$25(position, (Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showBindDialog$25(int i, Integer num) {
        int intValue = num.intValue();
        if (intValue == 0) {
            NavUtils.destination(ActSmartPanelSelectBleDeviceAndGroupNew.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, this.controlId).withInt(Constants.RELATED_POSITION, i).withString(Constants.PRODUCT_ID, this.productId).withBoolean(Constants.GROUP_CONTROL, true).withInt(Constants.GROUP_RELATE, 1).withInt(Constants.LIGHT_TYPE, getIntent().getIntExtra(Constants.LIGHT_TYPE, 0)).withDefaultRequestCode().navigation(this);
        } else if (intValue == 1) {
            NavUtils.destination(ActSmartPanelSelectScene.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, this.controlId).withInt(Constants.RELATED_POSITION, i).withBoolean(Constants.GROUP_CONTROL, true).withDefaultRequestCode().navigation(this);
        } else {
            if (intValue != 2) {
                return;
            }
            NavUtils.destination(ActSelectCgdPro.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, this.controlId).withBoolean(Constants.GROUP_CONTROL, true).withInt(Constants.RELATED_POSITION, i).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
        }
    }

    public void clickAdjustKRange(RelateInfoAssistant relateInfoAssistant) {
        Group groupByGroupId;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < relateInfoAssistant.getZoneNumber(); i++) {
            RelatedInfoExtParam.RelateInfo relateInfo = relateInfoAssistant.getRelateInfo(i);
            SettingCmdParam.KInfo kInfo = new SettingCmdParam.KInfo();
            if (relateInfo != null && relateInfo.action > 9) {
                if (relateInfo.type == 1) {
                    Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(relateInfo.objectId);
                    if (deviceByDeviceId != null) {
                        kInfo.setMinK(deviceByDeviceId.getMinkelvin());
                        kInfo.setMaxK(deviceByDeviceId.getMaxkelvin());
                    }
                } else if (relateInfo.type == 2 && (groupByGroupId = Injection.repo().group().getGroupByGroupId(relateInfo.objectId)) != null) {
                    kInfo.setMinK(groupByGroupId.getMinkelvin());
                    kInfo.setMaxK(groupByGroupId.getMaxkelvin());
                }
            }
            arrayList.add(kInfo);
        }
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.app_str_process));
        CmdAssistant.getSettingCmdAssistant(this.mGroup, new int[0]).setKInfo(this, (1 << relateInfoAssistant.getZoneNumber()) - 1, arrayList, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting.14
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    ActPanelGroupSetting.this.dismissLoadingDialog();
                    SmartToast.showCenterShort(ActivityUtils.getTopActivity().getString(R.string.calibration_succee));
                } else {
                    ActPanelGroupSetting.this.dismissLoadingDialog();
                    SmartToast.showCenterShort(ActivityUtils.getTopActivity().getString(R.string.calibration_fail));
                }
            }
        });
    }

    private int getProPanelCount(Group group) {
        int i = 0;
        if (group.getDeviceIds().size() > 0) {
            Iterator<Long> it = group.getDeviceIds().iterator();
            while (it.hasNext()) {
                Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(it.next().longValue());
                if (deviceByDeviceId != null && (deviceByDeviceId.getProductId().equals(ProductId.ID_SMART_SWITCH_S1_PRO) || deviceByDeviceId.getProductId().equals(ProductId.ID_SMART_SWITCH_S2_PRO) || deviceByDeviceId.getProductId().equals(ProductId.ID_SMART_SWITCH_S3_PRO) || deviceByDeviceId.getProductId().equals(ProductId.ID_SMART_PANEL_G4_PRO))) {
                    i++;
                }
            }
        }
        return i;
    }

    public void setSensingDistanceOn(boolean isChecked) {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
        CmdAssistant.getDeviceAssistant(this.mGroup, new int[0]).setWaveEnable(ActivityUtils.getTopActivity(), isChecked, new IAction() { // from class: com.ltech.smarthome.ui.group.ActPanelGroupSetting$$ExternalSyntheticLambda12
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActPanelGroupSetting.this.lambda$setSensingDistanceOn$26((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSensingDistanceOn$26(ResponseMsg responseMsg) {
        dismissLoadingDialog();
        if (responseMsg == null || responseMsg.getStateCode() == 3) {
            SmartToast.showShort(StringUtils.getString(R.string.save_fail));
        }
    }

    public void initRelateInfoList(Object object) {
        RelateInfoUtils.initRelateInfoList(object);
        RelateInfoAssistant relateInfoAssistant = RelateInfoUtils.relateInfoAssistant;
        for (int i = 0; i < RelateInfoUtils.relatedInfoList.size(); i++) {
            RelatedInfoExtParam.RelateInfo relateLongClickInfo = relateInfoAssistant.getRelateLongClickInfo(i);
            if (relateLongClickInfo != null) {
                boolean z = true;
                if (relateLongClickInfo.action != 1 && relateLongClickInfo.action != 2 && relateLongClickInfo.action != 3) {
                    z = false;
                }
                this.needBrtButton = z;
                if (z) {
                    return;
                }
            }
        }
    }
}