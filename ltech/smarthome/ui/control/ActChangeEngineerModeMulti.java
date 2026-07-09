package com.ltech.smarthome.ui.control;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import anetwork.channel.util.RequestConstant;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.base.BaseCmdParam;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/* loaded from: classes4.dex */
public class ActChangeEngineerModeMulti extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> {
    private TextView applyTv;
    private ListIterator<Integer> cmdListIterator;
    private ListIterator<Device> deviceListIterator;
    private List<Integer> engineerSetType;
    private int i;
    private boolean isLast = false;
    private boolean isSendProcess = false;
    private List<Device> addDeviceIdList = new ArrayList();
    private List<DeviceItem> selectData = new ArrayList();
    private Map<Long, DeviceItem> deviceItemMap = new HashMap();
    private List<Long> successDeviceIds = new ArrayList();

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterGroup(Group group) {
        return false;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void convertView(final BaseViewHolder helper, final Role item) {
        helper.setText(R.id.tv_device_name, item.getName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item)).setText(R.id.tv_place_info, ((FtDeviceGroupVM) this.mViewModel).getPlaceInfo(item.getFloorId(), item.getRoomId())).setBackgroundRes(R.id.iv_select, this.selectRoleIds.contains(Long.valueOf(item.getObjectId())) ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
        if (this.deviceItemMap.containsKey(Long.valueOf(item.getObjectId()))) {
            DeviceItem deviceItem = this.deviceItemMap.get(Long.valueOf(item.getObjectId()));
            if (deviceItem.getSendMode() == 1) {
                helper.setVisible(R.id.iv_select, false);
                helper.setGone(R.id.send_loading, true);
            } else {
                helper.setVisible(R.id.iv_select, !this.isSendProcess);
                helper.setGone(R.id.send_loading, false);
            }
            if (deviceItem.getSendModeNum() != 0) {
                helper.setVisible(R.id.iv_select, true);
                helper.setGone(R.id.send_loading, false);
                helper.setBackgroundRes(R.id.iv_select, deviceItem.getSendModeNum() == 1 ? R.mipmap.light_mode_icon_succes : R.mipmap.light_mode_icon_fail);
            }
            helper.itemView.setEnabled(deviceItem.getSendModeNum() != 1);
        }
        ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
        helper.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.ActChangeEngineerModeMulti.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (ActChangeEngineerModeMulti.this.isSendProcess) {
                    return;
                }
                Device device = (Device) item;
                if (ActChangeEngineerModeMulti.this.selectRoleIds.contains(Long.valueOf(device.getDeviceId()))) {
                    ActChangeEngineerModeMulti.this.selectRoleIds.remove(Long.valueOf(device.getDeviceId()));
                    helper.getView(R.id.iv_select).setBackgroundResource(R.mipmap.ic_tick_default);
                } else {
                    ActChangeEngineerModeMulti.this.selectRoleIds.add(Long.valueOf(device.getDeviceId()));
                    helper.getView(R.id.iv_select).findViewById(R.id.iv_select).setBackgroundResource(R.mipmap.ic_tick_sel);
                }
                if (ActChangeEngineerModeMulti.this.deviceItemMap.containsKey(Long.valueOf(item.getObjectId()))) {
                    DeviceItem deviceItem2 = (DeviceItem) ActChangeEngineerModeMulti.this.deviceItemMap.get(Long.valueOf(item.getObjectId()));
                    if (deviceItem2.getSendModeNum() != 0) {
                        deviceItem2.setSendModeNum(0);
                    }
                    if (deviceItem2.getSendMode() != 0) {
                        deviceItem2.setSendMode(0);
                    }
                }
                ActChangeEngineerModeMulti.this.selectCountLiveData.setValue(Integer.valueOf(ActChangeEngineerModeMulti.this.selectRoleIds.size()));
            }
        });
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_device));
        setEditString(getString(R.string.app_str_select_all));
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.setVisibility(0);
        this.listType = 5;
        ((FtDeviceGroupVM) this.mViewModel).needFloorRoomMemory = false;
        setSortType(1);
        this.engineerSetType = getIntent().getIntegerArrayListExtra(Constants.ENGINEER_SET_TYPE);
        TextView textView = new TextView(this);
        this.applyTv = textView;
        textView.setTextColor(getResources().getColor(R.color.color_text_red));
        this.applyTv.setTextSize(14.0f);
        this.applyTv.setBackgroundColor(getResources().getColor(R.color.white));
        this.applyTv.setGravity(17);
        this.applyTv.setText(getString(R.string.application_with_number, new Object[]{0}));
        ((ActSelect3Binding) this.mViewBinding).footerView.addView(this.applyTv, new RelativeLayout.LayoutParams(-1, ConvertUtils.dp2px(60.0f)));
        this.applyTv.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.ActChangeEngineerModeMulti$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActChangeEngineerModeMulti.this.lambda$initView$0(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        applyTo();
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void initData() {
        if (this.isSendProcess) {
            return;
        }
        for (Role role : this.allRoleData) {
            if (!this.deviceItemMap.containsKey(Long.valueOf(role.getObjectId()))) {
                DeviceItem deviceItem = new DeviceItem();
                deviceItem.setDeviceId(role.getObjectId());
                this.selectData.add(deviceItem);
                this.deviceItemMap.put(Long.valueOf(deviceItem.getDeviceId()), deviceItem);
            }
        }
    }

    private void applyTo() {
        if (this.isSendProcess) {
            return;
        }
        this.addDeviceIdList.clear();
        this.successDeviceIds.clear();
        for (int i = 0; i < this.selectRoleIds.size(); i++) {
            Role roleByRoleId = ((FtDeviceGroupVM) this.mViewModel).getRoleByRoleId(this.selectRoleIds.get(i).longValue());
            if (roleByRoleId != null) {
                this.addDeviceIdList.add((Device) roleByRoleId);
            }
        }
        if (this.addDeviceIdList.isEmpty()) {
            return;
        }
        for (int i2 = 0; i2 < this.selectData.size(); i2++) {
            for (int i3 = 0; i3 < this.addDeviceIdList.size(); i3++) {
                if (this.selectData.get(i2).getDeviceId() == this.addDeviceIdList.get(i3).getDeviceId()) {
                    this.selectData.get(i2).setSendMode(1);
                    this.selectData.get(i2).setSendModeNum(0);
                    getAdapter().notifyItemChanged(i2);
                    LHomeLog.i(RequestConstant.ENV_TEST, getClass(), "第" + i2 + "个数据准备发送");
                    LHomeLog.i(RequestConstant.ENV_TEST, getClass(), "current Thread--->" + Thread.currentThread());
                }
            }
        }
        this.isSendProcess = true;
        changeViewState();
        this.isLast = false;
        ListIterator<Device> listIterator = this.addDeviceIdList.listIterator();
        this.deviceListIterator = listIterator;
        applySettings(listIterator.next());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applySettings(Device device) {
        ArrayList arrayList = new ArrayList(this.engineerSetType);
        arrayList.retainAll(getSupportSettings(device));
        this.cmdListIterator = arrayList.listIterator();
        this.i = 0;
        ListIterator<DeviceItem> listIterator = this.selectData.listIterator();
        while (listIterator.hasNext()) {
            this.i = listIterator.nextIndex();
            if (listIterator.next().deviceId == device.getDeviceId()) {
                break;
            }
        }
        LHomeLog.i(RequestConstant.ENV_TEST, getClass(), "第" + this.i + "个数据sendModeToList enter");
        applySingleSetting(device, this.cmdListIterator.next().intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applySingleSetting(Device device, int setType) {
        if (!this.deviceListIterator.hasNext() && !this.cmdListIterator.hasNext()) {
            this.isLast = true;
        }
        if (setType == 1) {
            int queryIntValue = SharedPreferenceUtil.queryIntValue(Constants.ON_OFF_STATE_MAIN_VALUE);
            int queryIntValue2 = SharedPreferenceUtil.queryIntValue(Constants.ON_OFF_STATE_SUB_VALUE);
            sendCmdListAndBackup(device, CmdAssistant.getLightCmdAssistant(device, new int[0]).setOnState(queryIntValue, LightUtils.progress2BrtHasBelowOne(queryIntValue2), LightUtils.progress2BrtHasBelowOne(queryIntValue2)), UpdateBackDataRequest.POWER_STATUS);
            return;
        }
        if (setType == 2) {
            sendCmdListAndBackup(device, CmdAssistant.getLightCmdAssistant(device, new int[0]).setPowerOnTime(SharedPreferenceUtil.queryIntValue(Constants.POWER_ON_TIME_VALUE)), UpdateBackDataRequest.POWER_FADE_TIME);
            return;
        }
        if (setType == 3) {
            sendCmdListAndBackup(device, CmdAssistant.getLightCmdAssistant(device, new int[0]).setOnOffTime(SharedPreferenceUtil.queryIntValue(Constants.ON_TIME_VALUE), SharedPreferenceUtil.queryIntValue(Constants.OFF_TIME_VALUE)), UpdateBackDataRequest.FADE_TIME);
        } else if (setType == 6) {
            sendCmdListAndBackup(device, CmdAssistant.getLightCmdAssistant(device, new int[0]).setDimDepth(SharedPreferenceUtil.queryIntValue(Constants.DIM_DEPTH_VALUE)), UpdateBackDataRequest.DIM_DEPTH);
        } else if (setType == 7) {
            sendCtRangeList(device, SharedPreferenceUtil.queryIntValue(Constants.MAX_K, 6500), SharedPreferenceUtil.queryIntValue(Constants.MIN_K, 2700));
        } else {
            if (setType != 13) {
                return;
            }
            sendCmdListAndBackup(device, CmdAssistant.getLightCmdAssistant(device, new int[0]).setSceneOnTime(SharedPreferenceUtil.queryIntValue(Constants.SCENE_ON_TIME_VALUE)), UpdateBackDataRequest.SCENE_FADE_TIME);
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void changeSelectCount(int selectCount) {
        this.applyTv.setText(getString(R.string.application_with_number, new Object[]{Integer.valueOf(selectCount)}));
        this.applyTv.setTextColor(ContextCompat.getColor(this, R.color.color_text_red));
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (this.isSendProcess) {
            return;
        }
        super.edit();
    }

    private void changeViewState() {
        getAdapter().notifyDataSetChanged();
        if (this.isSendProcess) {
            ((AppCompatTextView) ((ActSelect3Binding) this.mViewBinding).getRoot().findViewById(R.id.tv_edit)).setTextColor(ContextCompat.getColor(this, R.color.color_text_gray));
            ((ActSelect3Binding) this.mViewBinding).searchBar.searchEdtTxt.setFocusable(false);
            ((ActSelect3Binding) this.mViewBinding).searchBar.searchEdtTxt.setFocusableInTouchMode(false);
            ((ActSelect3Binding) this.mViewBinding).searchBar.ivClean.setEnabled(false);
            this.applyTv.setTextColor(ContextCompat.getColor(this, R.color.color_text_gray));
            return;
        }
        ((AppCompatTextView) ((ActSelect3Binding) this.mViewBinding).getRoot().findViewById(R.id.tv_edit)).setTextColor(ContextCompat.getColor(this, R.color.color_text_black));
        ((ActSelect3Binding) this.mViewBinding).searchBar.searchEdtTxt.setFocusable(true);
        ((ActSelect3Binding) this.mViewBinding).searchBar.searchEdtTxt.setFocusableInTouchMode(true);
        ((ActSelect3Binding) this.mViewBinding).searchBar.ivClean.setEnabled(true);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        if (this.isSendProcess) {
            SmartToast.showShort(getString(R.string.sending_data_and_wait));
        } else {
            super.back();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendProcessOver() {
        this.isSendProcess = false;
        changeViewState();
        this.addDeviceIdList.clear();
        if (this.successDeviceIds.size() > 0) {
            ReplaceHelper.instance().backupData(this, this.successDeviceIds);
        }
    }

    private void sendCmdListAndBackup(final Device device, final BaseCmdParam cmdParam, final String flag) {
        if (device.isVirtual()) {
            if (this.cmdListIterator.hasNext()) {
                applySingleSetting(device, this.cmdListIterator.next().intValue());
                return;
            }
            this.selectData.get(this.i).setSendModeNum(1);
            getAdapter().notifyItemChanged(this.i);
            LHomeLog.i(RequestConstant.ENV_TEST, getClass(), "第" + this.i + "个数据发送完成");
            this.successDeviceIds.add(Long.valueOf(device.getDeviceId()));
            if (this.deviceListIterator.hasNext()) {
                applySettings(this.deviceListIterator.next());
                return;
            } else {
                ReplaceHelper.instance().addBackupData(flag, cmdParam);
                sendProcessOver();
                return;
            }
        }
        CmdAssistant.getLightCmdAssistant(device, new int[0]).sendEngineerMode(this, cmdParam, this.isLast, new IAction() { // from class: com.ltech.smarthome.ui.control.ActChangeEngineerModeMulti$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActChangeEngineerModeMulti.this.lambda$sendCmdListAndBackup$1(device, flag, cmdParam, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendCmdListAndBackup$1(Device device, String str, BaseCmdParam baseCmdParam, Boolean bool) {
        if (bool.booleanValue()) {
            if (this.cmdListIterator.hasNext()) {
                applySingleSetting(device, this.cmdListIterator.next().intValue());
                return;
            }
            this.selectData.get(this.i).setSendModeNum(1);
            getAdapter().notifyItemChanged(this.i);
            LHomeLog.i(RequestConstant.ENV_TEST, getClass(), "第" + this.i + "个数据发送完成");
            this.successDeviceIds.add(Long.valueOf(device.getDeviceId()));
        } else {
            this.selectData.get(this.i).setSendModeNum(2);
            getAdapter().notifyItemChanged(this.i);
            LHomeLog.i(RequestConstant.ENV_TEST, getClass(), "第" + this.i + "个数据发送失败");
        }
        if (this.deviceListIterator.hasNext()) {
            applySettings(this.deviceListIterator.next());
        } else {
            ReplaceHelper.instance().addBackupData(str, baseCmdParam);
            sendProcessOver();
        }
    }

    private void sendCtRangeList(final Device device, final int max, final int min) {
        if (device.isVirtual()) {
            ((ObservableSubscribeProxy) Injection.net().updateCtRange(device.getDeviceId(), max, min).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycle(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.ActChangeEngineerModeMulti$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActChangeEngineerModeMulti.this.lambda$sendCtRangeList$2(device, min, max, obj);
                }
            }, new Consumer() { // from class: com.ltech.smarthome.ui.control.ActChangeEngineerModeMulti$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActChangeEngineerModeMulti.this.lambda$sendCtRangeList$3(device, min, max, (Throwable) obj);
                }
            });
        } else {
            CmdAssistant.getLightCmdAssistant(device, new int[0]).setKRange(this, min, max, this.isLast, new IAction() { // from class: com.ltech.smarthome.ui.control.ActChangeEngineerModeMulti$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActChangeEngineerModeMulti.this.lambda$sendCtRangeList$6(device, max, min, (Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendCtRangeList$2(Device device, int i, int i2, Object obj) throws Exception {
        device.setMinkelvin(i);
        device.setMaxkelvin(i2);
        Injection.repo().device().saveDevice(device);
        if (this.cmdListIterator.hasNext()) {
            applySingleSetting(device, this.cmdListIterator.next().intValue());
            return;
        }
        this.selectData.get(this.i).setSendModeNum(1);
        getAdapter().notifyItemChanged(this.i);
        this.successDeviceIds.add(Long.valueOf(device.getDeviceId()));
        if (this.deviceListIterator.hasNext()) {
            applySettings(this.deviceListIterator.next());
        } else {
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.CT_RANGE, CmdAssistant.getLightCmdAssistant(device, new int[0]).setKRange(i, i2));
            sendProcessOver();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendCtRangeList$3(Device device, int i, int i2, Throwable th) throws Exception {
        if (this.isLast) {
            SmartToast.showShort(R.string.no_network);
        }
        if (this.cmdListIterator.hasNext()) {
            applySingleSetting(device, this.cmdListIterator.next().intValue());
            return;
        }
        this.selectData.get(this.i).setSendModeNum(2);
        getAdapter().notifyItemChanged(this.i);
        if (this.deviceListIterator.hasNext()) {
            applySettings(this.deviceListIterator.next());
        } else {
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.CT_RANGE, CmdAssistant.getLightCmdAssistant(device, new int[0]).setKRange(i, i2));
            sendProcessOver();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendCtRangeList$6(final Device device, final int i, final int i2, Boolean bool) {
        if (bool.booleanValue()) {
            ((ObservableSubscribeProxy) Injection.net().updateCtRange(device.getDeviceId(), i, i2).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycle(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.ActChangeEngineerModeMulti$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActChangeEngineerModeMulti.this.lambda$sendCtRangeList$4(device, i2, i, obj);
                }
            }, new Consumer() { // from class: com.ltech.smarthome.ui.control.ActChangeEngineerModeMulti$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActChangeEngineerModeMulti.this.lambda$sendCtRangeList$5(device, i2, i, (Throwable) obj);
                }
            });
            return;
        }
        if (this.cmdListIterator.hasNext()) {
            applySingleSetting(device, this.cmdListIterator.next().intValue());
            return;
        }
        this.selectData.get(this.i).setSendModeNum(2);
        getAdapter().notifyItemChanged(this.i);
        if (this.deviceListIterator.hasNext()) {
            sendCtRangeList(this.deviceListIterator.next(), i, i2);
        } else {
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.CT_RANGE, CmdAssistant.getLightCmdAssistant(device, new int[0]).setKRange(i2, i));
            sendProcessOver();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendCtRangeList$4(Device device, int i, int i2, Object obj) throws Exception {
        device.setMinkelvin(i);
        device.setMaxkelvin(i2);
        Injection.repo().device().saveDevice(device);
        if (this.cmdListIterator.hasNext()) {
            applySingleSetting(device, this.cmdListIterator.next().intValue());
            return;
        }
        this.selectData.get(this.i).setSendModeNum(1);
        getAdapter().notifyItemChanged(this.i);
        this.successDeviceIds.add(Long.valueOf(device.getDeviceId()));
        if (this.deviceListIterator.hasNext()) {
            applySettings(this.deviceListIterator.next());
        } else {
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.CT_RANGE, CmdAssistant.getLightCmdAssistant(device, new int[0]).setKRange(i, i2));
            sendProcessOver();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendCtRangeList$5(final Device device, final int i, final int i2, Throwable th) throws Exception {
        if (this.isLast) {
            SmartToast.showShort(R.string.no_network);
        }
        CmdAssistant.getLightCmdAssistant(device, new int[0]).setKRange(this.activity, device.getMinkelvin(), device.getMaxkelvin(), false, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.control.ActChangeEngineerModeMulti.2
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                if (ActChangeEngineerModeMulti.this.cmdListIterator.hasNext()) {
                    ActChangeEngineerModeMulti actChangeEngineerModeMulti = ActChangeEngineerModeMulti.this;
                    actChangeEngineerModeMulti.applySingleSetting(device, ((Integer) actChangeEngineerModeMulti.cmdListIterator.next()).intValue());
                    return;
                }
                ((DeviceItem) ActChangeEngineerModeMulti.this.selectData.get(ActChangeEngineerModeMulti.this.i)).setSendModeNum(2);
                ActChangeEngineerModeMulti.this.getAdapter().notifyItemChanged(ActChangeEngineerModeMulti.this.i);
                if (ActChangeEngineerModeMulti.this.deviceListIterator.hasNext()) {
                    ActChangeEngineerModeMulti actChangeEngineerModeMulti2 = ActChangeEngineerModeMulti.this;
                    actChangeEngineerModeMulti2.applySettings((Device) actChangeEngineerModeMulti2.deviceListIterator.next());
                } else {
                    ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.CT_RANGE, CmdAssistant.getLightCmdAssistant(device, new int[0]).setKRange(i, i2));
                    ActChangeEngineerModeMulti.this.sendProcessOver();
                }
            }
        });
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        List<Integer> supportSettings = getSupportSettings(device);
        if (supportSettings.isEmpty()) {
            return false;
        }
        return containsAny(this.engineerSetType, supportSettings);
    }

    private List<Integer> getSupportSettings(Device device) {
        int lightColorType;
        ArrayList arrayList = new ArrayList();
        if (!ProductRepository.isDaliLightGroup(device)) {
            arrayList.add(1);
            arrayList.add(13);
            arrayList.add(2);
            arrayList.add(3);
            lightColorType = ProductRepository.getLightColorType((Object) device);
            String productId = device.getProductId();
            productId.hashCode();
            switch (productId) {
                case "3557654002353408":
                case "3486586935738368":
                case "120042616091901":
                case "120042616094101":
                case "120042616101901":
                case "120042616103901":
                case "3486587348451328":
                case "3486587769094144":
                    arrayList.remove((Object) 13);
                    return arrayList;
                case "120033108251501":
                    arrayList.add(6);
                    return arrayList;
                case "120033108255901":
                    arrayList.add(7);
                    arrayList.add(6);
                    return arrayList;
                case "3895993722014848":
                case "120122111301201":
                    if (!device.isSubDevice() && SharedPreferenceUtil.queryIntValue(Constants.ON_OFF_STATE_MAIN_VALUE) != 4) {
                        return Collections.singletonList(1);
                    }
                    break;
                case "25":
                    return lightColorType == 2 ? Arrays.asList(2, 3, 7) : Arrays.asList(2, 3);
                case "122112209430401":
                    return Collections.singletonList(13);
            }
            return Collections.EMPTY_LIST;
        }
        return arrayList;
    }

    private boolean containsAny(List<Integer> list1, List<Integer> list2) {
        return !Collections.disjoint(list1, list2);
    }

    public static class DeviceItem {
        private long deviceId;
        private int sendMode;
        private int sendModeNum;

        public long getDeviceId() {
            return this.deviceId;
        }

        public void setDeviceId(long deviceId) {
            this.deviceId = deviceId;
        }

        public int getSendModeNum() {
            return this.sendModeNum;
        }

        public void setSendModeNum(int sendModeNum) {
            this.sendModeNum = sendModeNum;
        }

        public int getSendMode() {
            return this.sendMode;
        }

        public void setSendMode(int sendMode) {
            this.sendMode = sendMode;
        }
    }
}