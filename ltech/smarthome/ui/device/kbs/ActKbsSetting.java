package com.ltech.smarthome.ui.device.kbs;

import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActKbsSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.IconRepository;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.ltech.smarthome.view.dialog.SelectListWheelDialog;
import com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.message.ResponseMsg;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActKbsSetting extends BaseDeviceSetActivity<ActKbsSettingBinding, ActKbsSettingVM> {
    public List<LightSettingState> dataList;
    private BaseQuickAdapter<Device, BaseViewHolder> mAdapter;
    protected boolean[] selectArray;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_kbs_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.setting));
        setEditString("             ");
        setBackImage(R.mipmap.icon_back);
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActKbsSettingVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActKbsSettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActKbsSettingVM) this.mViewModel).lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
        ((ActKbsSettingVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSetting$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActKbsSetting.this.lambda$startObserve$0((Device) obj);
            }
        });
        initRv();
        ((ActKbsSettingBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
        ((ActKbsSettingBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((ActKbsSettingBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((ActKbsSettingBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSetting$$ExternalSyntheticLambda2
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ActKbsSetting.this.lambda$startObserve$1(refreshLayout);
            }
        });
        ((ActKbsSettingVM) this.mViewModel).showNoPermissionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSetting$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActKbsSetting.this.lambda$startObserve$2((Void) obj);
            }
        });
        ((ActKbsSettingVM) this.mViewModel).refreshRoleItem.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSetting$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActKbsSetting.this.lambda$startObserve$3((Void) obj);
            }
        });
        ((ActKbsSettingVM) this.mViewModel).selectSceneDialogEvent.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSetting.2
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean aBoolean) {
                if ((!aBoolean.booleanValue() || TextUtils.isEmpty(((ActKbsSettingVM) ActKbsSetting.this.mViewModel).showPowerOnScene.getValue())) && (aBoolean.booleanValue() || TextUtils.isEmpty(((ActKbsSettingVM) ActKbsSetting.this.mViewModel).showPowerOffScene.getValue()))) {
                    NavUtils.destination(ActSmartPanelSelectScene.class).withLong(Constants.PLACE_ID, ((ActKbsSettingVM) ActKbsSetting.this.mViewModel).getCurPlace().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActKbsSettingVM) ActKbsSetting.this.mViewModel).controlId).withBoolean(Constants.IS_POWER_SCENE, true).withBoolean(Constants.GROUP_CONTROL, false).withBoolean(Constants.IS_EXC, aBoolean.booleanValue()).withInt(Constants.ZONE_NUM, 1).withDefaultRequestCode().navigation(ActKbsSetting.this.activity);
                } else {
                    ActKbsSetting.this.showUnbindDialog(aBoolean.booleanValue());
                }
            }
        });
        ((ActKbsSettingVM) this.mViewModel).selectSceneDelayDialogEvent.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSetting.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean aBoolean) {
                ActKbsSetting.this.showSceneDelayTime(aBoolean);
            }
        });
        ((ActKbsSettingVM) this.mViewModel).showControlTypeEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSetting.4
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(ActKbsSetting.this.getString(R.string.app_dry_to_ble_type1));
                arrayList.add(ActKbsSetting.this.getString(R.string.app_dry_to_ble_type2));
                SelectListDialog.asDefault(true).setSelectPosition(((ActKbsSettingVM) ActKbsSetting.this.mViewModel).controlType.getValue().intValue() != 0 ? 0 : 1).setSelectList(arrayList).setTitle(ActKbsSetting.this.getString(R.string.app_str_switch_control_type)).setCancelString(ActKbsSetting.this.getString(R.string.cancel)).setConfirmString(ActKbsSetting.this.getString(R.string.confirm)).setConfirmAction(new IAction<Integer>() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSetting.4.1
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(Integer integer) {
                        ((ActKbsSettingVM) ActKbsSetting.this.mViewModel).changeControlType(integer);
                    }
                }).showDialog(ActKbsSetting.this.activity);
            }
        });
        ((ActKbsSettingVM) this.mViewModel).controlType.observe(this, new Observer<Integer>() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSetting.5
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer integer) {
                if (integer.intValue() == 1) {
                    ((ActKbsSettingVM) ActKbsSetting.this.mViewModel).showPowerOnOffScene.setValue(true);
                    ((ActKbsSettingVM) ActKbsSetting.this.mViewModel).queryPowerOnOffScene();
                }
            }
        });
        ((ActKbsSettingVM) this.mViewModel).showSelectDeviceIconDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSetting$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActKbsSetting.this.lambda$startObserve$4((Void) obj);
            }
        });
        ((ActKbsSettingVM) this.mViewModel).backDataResult.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSetting$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActKbsSetting.this.showBackSettingData((JSONObject) obj);
            }
        });
        if (isOwnerOrManager()) {
            ((ActKbsSettingVM) this.mViewModel).showLog.setValue(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        if (device == null) {
            return;
        }
        ((ActKbsSettingVM) this.mViewModel).imageIndex = device.getImageIndex();
        ((ActKbsSettingVM) this.mViewModel).deviceId = device.getDeviceId();
        ((ActKbsSettingBinding) this.mViewBinding).tvRoomName.setText(getPlaceInfo(device));
        ((ActKbsSettingVM) this.mViewModel).roomPickHelper.startObserve(this, device.getPlaceId(), device.getRoomId());
        int i = 0;
        ((ActKbsSettingBinding) this.mViewBinding).layoutDeviceReplace.setVisibility((!isOwnerOrManager() || isVirtual()) ? 8 : 0);
        int imageIndex = device.getImageIndex();
        int[] lightIconValue = IconRepository.getLightIconValue(this);
        while (true) {
            if (i >= lightIconValue.length) {
                break;
            }
            if (i == imageIndex) {
                ((ActKbsSettingBinding) this.mViewBinding).ivIcon.setImageResource(lightIconValue[i]);
                break;
            }
            i++;
        }
        queryState();
        ((ActKbsSettingVM) this.mViewModel).refreshRoleItem.call();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(RefreshLayout refreshLayout) {
        if (((ActKbsSettingVM) this.mViewModel).controlDevice.getValue() != null) {
            queryState();
        }
        ((ActKbsSettingBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r1) {
        showNoPermissionDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r5) {
        List<Device> subDevice = Injection.repo().device().getSubDevice(((ActKbsSettingVM) this.mViewModel).placeId, ((ActKbsSettingVM) this.mViewModel).deviceId);
        Collections.sort(subDevice, new Comparator<Device>(this) { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSetting.1
            @Override // java.util.Comparator
            public int compare(Device o1, Device o2) {
                return o1.getWifiMac().compareTo(o2.getWifiMac());
            }
        });
        this.mAdapter.setNewData(subDevice);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r1) {
        showSelectDeviceIconDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showBackSettingData(JSONObject jsonObject) {
        showData(jsonObject, UpdateBackDataRequest.POWER_STATUS);
        showData(jsonObject, UpdateBackDataRequest.CONTROL_INPUT_TYPE);
        showData(jsonObject, "trigger_scene_1_1");
        showData(jsonObject, "trigger_scene_1_2");
        showData(jsonObject, "trigger_delay_1_1");
        showData(jsonObject, "trigger_delay_1_2");
    }

    private void showData(JSONObject jsonObject, String key) {
        int[] parseBackupData;
        List<SelectPowerOnStateDialog.OnOffState> generateContentList;
        parseBackupData = ReplaceHelper.instance().parseBackupData(jsonObject, key);
        key.hashCode();
        switch (key) {
            case "trigger_delay_1_1":
                ((ActKbsSettingVM) this.mViewModel).showPowerOnSceneDelayStr.setValue(parseBackupData[0] + getString(R.string.sec));
                ((ActKbsSettingVM) this.mViewModel).showPowerOnSceneDelay = parseBackupData[0];
                break;
            case "trigger_delay_1_2":
                ((ActKbsSettingVM) this.mViewModel).showPowerOffSceneDelayStr.setValue(parseBackupData[0] + getString(R.string.sec));
                ((ActKbsSettingVM) this.mViewModel).showPowerOffSceneDelay = parseBackupData[0];
                break;
            case "trigger_scene_1_1":
                Scene localSceneBySceneNum = Injection.repo().scene().getLocalSceneBySceneNum(parseBackupData[0]);
                if (localSceneBySceneNum != null) {
                    ((ActKbsSettingVM) this.mViewModel).showPowerOnScene.setValue(localSceneBySceneNum.getName());
                    break;
                }
                break;
            case "trigger_scene_1_2":
                Scene localSceneBySceneNum2 = Injection.repo().scene().getLocalSceneBySceneNum(parseBackupData[0]);
                if (localSceneBySceneNum2 != null) {
                    ((ActKbsSettingVM) this.mViewModel).showPowerOffScene.setValue(localSceneBySceneNum2.getName());
                    break;
                }
                break;
            case "powerStatus":
                if (((ActKbsSettingVM) this.mViewModel).controlDevice.getValue() != null && (((ActKbsSettingVM) this.mViewModel).controlDevice.getValue().getProductId().equals(ProductId.ID_BLE_SWITCH) || ((ActKbsSettingVM) this.mViewModel).controlDevice.getValue().getProductId().equals(ProductId.ID_BLE_KBS))) {
                    generateContentList = SelectPowerOnStateDialog.generateSwitchList();
                } else {
                    generateContentList = SelectPowerOnStateDialog.generateContentList();
                }
                int size = generateContentList.size();
                for (int i = 0; i < size; i++) {
                    if (parseBackupData[0] == generateContentList.get(i).getMainValue()) {
                        if (parseBackupData[0] == 4) {
                            ((ActKbsSettingBinding) this.mViewBinding).tvState.setText(LightUtils.getProgressHasBelowOne(LightUtils.brt2ProgressHasBelowZero(parseBackupData[1])) + getString(R.string.brt));
                            break;
                        } else {
                            ((ActKbsSettingBinding) this.mViewBinding).tvState.setText(generateContentList.get(i).getName());
                            break;
                        }
                    }
                }
                break;
            case "control_input_type":
                ((ActKbsSettingVM) this.mViewModel).controlType.setValue(Integer.valueOf(parseBackupData[0]));
                break;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((ActKbsSettingVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDeviceById(((ActKbsSettingVM) this.mViewModel).controlId));
    }

    private void initRv() {
        ((ActKbsSettingBinding) this.mViewBinding).rvLightSetting.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView = ((ActKbsSettingBinding) this.mViewBinding).rvLightSetting;
        BaseQuickAdapter<Device, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Device, BaseViewHolder>(R.layout.item_smart_panel_switch) { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSetting.6
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, final Device device) {
                baseViewHolder.setImageResource(R.id.iv, ProductRepository.getProductIcon(device));
                baseViewHolder.setText(R.id.tv_sub, device.getFloorName() + " " + device.getRoomName());
                baseViewHolder.addOnClickListener(R.id.tv_setting);
                baseViewHolder.setText(R.id.tv, device.getName());
                SwitchButton switchButton = (SwitchButton) baseViewHolder.getView(R.id.sb);
                switchButton.setCheckedNotByUser(device.getSubhide() == 0);
                switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSetting.6.1
                    @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                        ((ActKbsSettingVM) ActKbsSetting.this.mViewModel).changeHide(device, isChecked);
                    }
                });
            }
        };
        this.mAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSetting.7
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ((ActKbsSettingVM) ActKbsSetting.this.mViewModel).showEditDialog((Role) ActKbsSetting.this.mAdapter.getItem(i));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showUnbindDialog(final boolean isExc) {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.reset_relate));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActKbsSetting.this.lambda$showUnbindDialog$5(isExc, (Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnbindDialog$5(boolean z, Integer num) {
        ((ActKbsSettingVM) this.mViewModel).unbindScene(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSceneDelayTime(final Boolean b2) {
        int i = b2.booleanValue() ? ((ActKbsSettingVM) this.mViewModel).showPowerOnSceneDelay : ((ActKbsSettingVM) this.mViewModel).showPowerOffSceneDelay;
        final ArrayList arrayList = new ArrayList();
        int i2 = 0;
        for (int i3 = 0; i3 < 61; i3++) {
            arrayList.add(i3 + "");
            if (i == i3) {
                i2 = i3;
            }
        }
        SelectListWheelDialog.asDefault(true).setTitle(getString(R.string.delay_time)).setSelectList(arrayList).setSpecify(getString(R.string.sec)).setSelectPosition(i2).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.save)).setConfirmAction(new IAction<Integer>() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSetting.8
            @Override // com.ltech.smarthome.base.IAction
            public void act(Integer integer) {
                ((ActKbsSettingVM) ActKbsSetting.this.mViewModel).bindSceneDelayTime(b2.booleanValue(), Integer.parseInt((String) arrayList.get(integer.intValue())));
            }
        }).showDialog(this.activity);
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity
    protected void updatePowerStateDialogData(SelectPowerOnStateDialog.OnOffState state) {
        if (state.getMainValue() == 4) {
            ((ActKbsSettingBinding) this.mViewBinding).tvState.setText(LightUtils.getProgressHasBelowOne(state.getSubValue()) + getString(R.string.brt));
            return;
        }
        ((ActKbsSettingBinding) this.mViewBinding).tvState.setText(state.getName());
    }

    private void queryState() {
        ((ActKbsSettingVM) this.mViewModel).queryBackData(((ActKbsSettingVM) this.mViewModel).deviceId);
        getCmdHelper().queryOnState(this, new IAction() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsSetting$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActKbsSetting.this.lambda$queryState$6((ResponseMsg) obj);
            }
        });
        ((ActKbsSettingVM) this.mViewModel).queryControlType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryState$6(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getResData() == null || responseMsg.getResData().length() < 18) {
            return;
        }
        List<SelectPowerOnStateDialog.OnOffState> generateSwitchList = SelectPowerOnStateDialog.generateSwitchList();
        int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 18), 16);
        int size = generateSwitchList.size();
        for (int i = 0; i < size; i++) {
            if (parseInt == generateSwitchList.get(i).getMainValue()) {
                ((ActKbsSettingBinding) this.mViewBinding).tvState.setText(generateSwitchList.get(i).getName());
                return;
            }
        }
    }

    public static final class LightSettingState {
        private static int TYPE_DEFAULT = 1;
        private static int TYPE_DIY = 2;
        private String name;
        private String subName;
        private String titleName;
        private int type;

        LightSettingState(int type, String titleName) {
            this.type = type;
            this.titleName = titleName;
        }

        LightSettingState(int type, String titleName, String name, String subName) {
            this.type = type;
            this.titleName = titleName;
            this.name = name;
            this.subName = subName;
        }

        int getType() {
            return this.type;
        }

        String getName() {
            return this.name;
        }

        public String getSubName() {
            return this.subName;
        }

        public void setSubName(String subName) {
            this.subName = subName;
        }

        public String getTitleName() {
            return this.titleName;
        }

        public void setTitleName(String titleName) {
            this.titleName = titleName;
        }
    }
}