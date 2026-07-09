package com.ltech.smarthome.ui.control;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import anetwork.channel.util.RequestConstant;
import com.alibaba.fastjson.parser.JSONLexer;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.device_param.SuperPanelExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.light.PowerState;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.base.BaseCmdParam;
import com.smart.message.utils.LHomeLog;
import com.smart.message.utils.StringUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ActChangeEngineerMode extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> {
    private TextView applyTv;
    private TextView cancelTv;
    private ListIterator<Device> deviceListIterator;
    private int engineerSetType;
    private int i;
    private boolean isLast = false;
    private boolean isSendProcess = false;
    private List<Device> addDeviceIdList = new ArrayList();
    private List<DeviceItem> selectData = new ArrayList();
    private Map<Long, DeviceItem> deviceItemMap = new HashMap();
    private List<Long> successDeviceIds = new ArrayList();
    private boolean isRhythmsOpen = false;

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
        helper.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.ActChangeEngineerMode.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (ActChangeEngineerMode.this.isSendProcess) {
                    return;
                }
                Device device = (Device) item;
                if (ActChangeEngineerMode.this.selectRoleIds.contains(Long.valueOf(device.getDeviceId()))) {
                    ActChangeEngineerMode.this.selectRoleIds.remove(Long.valueOf(device.getDeviceId()));
                    helper.getView(R.id.iv_select).setBackgroundResource(R.mipmap.ic_tick_default);
                } else {
                    ActChangeEngineerMode.this.selectRoleIds.add(Long.valueOf(device.getDeviceId()));
                    helper.getView(R.id.iv_select).findViewById(R.id.iv_select).setBackgroundResource(R.mipmap.ic_tick_sel);
                }
                if (ActChangeEngineerMode.this.deviceItemMap.containsKey(Long.valueOf(item.getObjectId()))) {
                    DeviceItem deviceItem2 = (DeviceItem) ActChangeEngineerMode.this.deviceItemMap.get(Long.valueOf(item.getObjectId()));
                    if (deviceItem2.getSendModeNum() != 0) {
                        deviceItem2.setSendModeNum(0);
                    }
                    if (deviceItem2.getSendMode() != 0) {
                        deviceItem2.setSendMode(0);
                    }
                }
                ActChangeEngineerMode.this.selectCountLiveData.setValue(Integer.valueOf(ActChangeEngineerMode.this.selectRoleIds.size()));
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
        int intExtra = getIntent().getIntExtra(Constants.ENGINEER_SET_TYPE, -1);
        this.engineerSetType = intExtra;
        if (intExtra == 8) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(0);
            TextView textView = new TextView(this);
            this.cancelTv = textView;
            textView.setTextColor(getResources().getColor(R.color.color_text_black));
            this.cancelTv.setTextSize(14.0f);
            this.cancelTv.setBackgroundColor(getResources().getColor(R.color.white));
            this.cancelTv.setGravity(17);
            this.cancelTv.setText(String.format(getResources().getString(R.string.app_str_apply_to_dev), 0));
            TextView textView2 = new TextView(this);
            this.applyTv = textView2;
            textView2.setTextColor(getResources().getColor(R.color.color_text_red));
            this.applyTv.setTextSize(14.0f);
            this.applyTv.setBackgroundColor(getResources().getColor(R.color.white));
            this.applyTv.setGravity(17);
            this.applyTv.setText(String.format(getResources().getString(R.string.app_str_apply_and_open_rhythms), 0));
            linearLayout.setWeightSum(2.0f);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
            layoutParams.weight = 1.0f;
            linearLayout.addView(this.cancelTv, layoutParams);
            linearLayout.addView(this.applyTv, layoutParams);
            ((ActSelect3Binding) this.mViewBinding).footerView.removeAllViews();
            ((ActSelect3Binding) this.mViewBinding).footerView.addView(linearLayout, new RelativeLayout.LayoutParams(-1, ConvertUtils.dp2px(60.0f)));
        } else {
            TextView textView3 = new TextView(this);
            this.cancelTv = textView3;
            textView3.setTextColor(getResources().getColor(R.color.color_text_red));
            this.cancelTv.setTextSize(14.0f);
            this.cancelTv.setBackgroundColor(getResources().getColor(R.color.white));
            this.cancelTv.setGravity(17);
            this.cancelTv.setText(getString(R.string.application_with_number, new Object[]{0}));
            ((ActSelect3Binding) this.mViewBinding).footerView.addView(this.cancelTv, new RelativeLayout.LayoutParams(-1, ConvertUtils.dp2px(60.0f)));
        }
        if (this.engineerSetType == 8) {
            this.applyTv.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.ActChangeEngineerMode.2
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    ActChangeEngineerMode.this.isRhythmsOpen = true;
                    ActChangeEngineerMode.this.applyTo();
                }
            });
        }
        this.cancelTv.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.ActChangeEngineerMode$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActChangeEngineerMode.this.lambda$initView$0(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        this.isRhythmsOpen = false;
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

    /* JADX INFO: Access modifiers changed from: private */
    public void applyTo() {
        int i;
        int i2;
        int i3;
        int i4;
        if (this.isSendProcess) {
            return;
        }
        this.addDeviceIdList.clear();
        this.successDeviceIds.clear();
        for (int i5 = 0; i5 < this.selectRoleIds.size(); i5++) {
            Role roleByRoleId = ((FtDeviceGroupVM) this.mViewModel).getRoleByRoleId(this.selectRoleIds.get(i5).longValue());
            if (roleByRoleId != null) {
                this.addDeviceIdList.add((Device) roleByRoleId);
            }
        }
        if (this.addDeviceIdList.isEmpty()) {
            return;
        }
        for (int i6 = 0; i6 < this.selectData.size(); i6++) {
            for (int i7 = 0; i7 < this.addDeviceIdList.size(); i7++) {
                if (this.selectData.get(i6).getDeviceId() == this.addDeviceIdList.get(i7).getDeviceId()) {
                    this.selectData.get(i6).setSendMode(1);
                    this.selectData.get(i6).setSendModeNum(0);
                    getAdapter().notifyItemChanged(i6);
                    LHomeLog.i(RequestConstant.ENV_TEST, getClass(), "第" + i6 + "个数据准备发送");
                    LHomeLog.i(RequestConstant.ENV_TEST, getClass(), "current Thread--->" + Thread.currentThread());
                }
            }
        }
        this.isSendProcess = true;
        changeViewState();
        this.isLast = false;
        ListIterator<Device> listIterator = this.addDeviceIdList.listIterator();
        this.deviceListIterator = listIterator;
        Device next = listIterator.next();
        switch (this.engineerSetType) {
            case 1:
                sendCmdListAndBackup(next, CmdAssistant.getLightCmdAssistant(next, new int[0]).setOnState((PowerState) GsonUtils.fromJson(SharedPreferenceUtil.queryValue(Constants.ON_OFF_STATE_MAIN_VALUE), PowerState.class)), UpdateBackDataRequest.POWER_STATUS);
                break;
            case 2:
                sendCmdListAndBackup(next, CmdAssistant.getLightCmdAssistant(next, new int[0]).setPowerOnTime(SharedPreferenceUtil.queryIntValue(Constants.POWER_ON_TIME_VALUE)), UpdateBackDataRequest.POWER_FADE_TIME);
                break;
            case 3:
                sendCmdListAndBackup(next, CmdAssistant.getLightCmdAssistant(next, new int[0]).setOnOffTime(SharedPreferenceUtil.queryIntValue(Constants.ON_TIME_VALUE), SharedPreferenceUtil.queryIntValue(Constants.OFF_TIME_VALUE)), UpdateBackDataRequest.FADE_TIME);
                break;
            case 4:
                sendCmdList(next, CmdAssistant.getSettingCmdAssistant(next, new int[0]).setDeviceTTl(SharedPreferenceUtil.queryIntValue(Constants.DEVICE_TTL_VALUE)));
                break;
            case 5:
                sendCmdList(next, CmdAssistant.getSettingCmdAssistant(next, new int[0]).setDeviceFrequency(SharedPreferenceUtil.queryIntValue(Constants.SEND_FREQUENCY_VALUE), SharedPreferenceUtil.queryIntValue(Constants.SEND_INTERVAL_VALUE)));
                break;
            case 6:
                sendCmdListAndBackup(next, CmdAssistant.getLightCmdAssistant(next, new int[0]).setDimDepth(SharedPreferenceUtil.queryIntValue(Constants.DIM_DEPTH_VALUE)), UpdateBackDataRequest.DIM_DEPTH);
                break;
            case 7:
                sendCtRangeList(next, SharedPreferenceUtil.queryIntValue(Constants.MAX_K, 6500), SharedPreferenceUtil.queryIntValue(Constants.MIN_K, 2700));
                break;
            case 8:
                try {
                    JSONObject jSONObject = new JSONObject(getIntent().getStringExtra(Constants.CIRCADIAN_LIGHTING_DATA));
                    int optInt = jSONObject.optInt("mode");
                    int optInt2 = jSONObject.optInt("repeat");
                    String str = "";
                    if (optInt == 2) {
                        str = StringUtils.toHexString(jSONObject.optInt("startH", 0)) + StringUtils.toHexString(jSONObject.optInt("startM", 0)) + StringUtils.toHexString(jSONObject.optInt("endH", 0)) + StringUtils.toHexString(jSONObject.optInt("endM", 0));
                    } else if (optInt == 3) {
                        str = jSONObject.optString("planData");
                    }
                    sendCmdListAndBackup(next, CmdAssistant.getLightRhythmsCmdAssistant(next, new int[0]).saveRhythmsPlanInfo(optInt, this.isRhythmsOpen, optInt2, str), UpdateBackDataRequest.CIRCADIAN_PLAN);
                    break;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            case 9:
                sendCmdList(next, CmdAssistant.getLightCmdAssistant(next, new int[0]).setAutoNetTime(SharedPreferenceUtil.queryIntValue(Constants.AUTO_NET_TIME_VALUE)));
                break;
            case 10:
                try {
                    JSONObject jSONObject2 = new JSONObject(getIntent().getStringExtra(Constants.CIRCADIAN_LIGHTING_DATA));
                    sendCmdList(next, CmdAssistant.getLightRhythmsCmdAssistant(next, new int[0]).previewRhythmsPlanInfo(jSONObject2.optInt("mode"), false, jSONObject2.optInt("week"), jSONObject2.optString("planData")));
                    break;
                } catch (JSONException e2) {
                    e2.printStackTrace();
                    return;
                }
            case 11:
                sendCmdList(next, CmdAssistant.getLightCmdAssistant(next, new int[0]).setDuvCalibration(getIntent().getStringExtra(Constants.DUV_CALIBRATION_DATA)));
                break;
            case 12:
                int[] intArrayExtra = getIntent().getIntArrayExtra(Constants.NIGHT_MODE_TIME_VALUE);
                sendCmdListAndBackup(next, CmdAssistant.getSettingCmdAssistant(next, new int[0]).setSmartPanelMode(0, 0, intArrayExtra != null ? 2 : 0, 0, intArrayExtra != null ? intArrayExtra[0] : 0, intArrayExtra != null ? intArrayExtra[1] : 0, intArrayExtra != null ? intArrayExtra[2] : 0, intArrayExtra != null ? intArrayExtra[3] : 0), UpdateBackDataRequest.NIGHT_MODE);
                break;
            case 13:
                sendCmdListAndBackup(next, CmdAssistant.getLightCmdAssistant(next, new int[0]).setSceneOnTime(SharedPreferenceUtil.queryIntValue(Constants.SCENE_ON_TIME_VALUE)), UpdateBackDataRequest.SCENE_FADE_TIME);
                break;
            case 14:
                int[] intArrayExtra2 = getIntent().getIntArrayExtra(Constants.NIGHT_MODE_TIME_VALUE);
                int i8 = intArrayExtra2 != null ? 1 : 0;
                int i9 = intArrayExtra2 != null ? intArrayExtra2[0] : 0;
                int i10 = intArrayExtra2 != null ? intArrayExtra2[1] : 0;
                int i11 = intArrayExtra2 != null ? intArrayExtra2[2] : 0;
                if (intArrayExtra2 != null) {
                    i = i10;
                    i2 = i9;
                    i3 = i11;
                    i4 = intArrayExtra2[3];
                } else {
                    i = i10;
                    i2 = i9;
                    i3 = i11;
                    i4 = 0;
                }
                sendGatewayNightMode(next, i8, i2, i, i3, i4);
                break;
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void changeSelectCount(int selectCount) {
        if (this.engineerSetType == 8) {
            this.applyTv.setText(getString(R.string.app_str_apply_and_open_rhythms, new Object[]{Integer.valueOf(selectCount)}));
            this.cancelTv.setText(getString(R.string.app_str_apply_to_dev, new Object[]{Integer.valueOf(selectCount)}));
        } else {
            this.cancelTv.setText(getString(R.string.application_with_number, new Object[]{Integer.valueOf(selectCount)}));
            this.cancelTv.setTextColor(ContextCompat.getColor(this, R.color.color_text_red));
        }
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
            this.cancelTv.setTextColor(ContextCompat.getColor(this, R.color.color_text_gray));
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
        this.i = 0;
        ListIterator<DeviceItem> listIterator = this.selectData.listIterator();
        while (listIterator.hasNext()) {
            this.i = listIterator.nextIndex();
            if (listIterator.next().deviceId == device.getDeviceId()) {
                break;
            }
        }
        LHomeLog.i(RequestConstant.ENV_TEST, getClass(), "第" + this.i + "个数据sendModeToList enter");
        if (!this.deviceListIterator.hasNext()) {
            this.isLast = true;
        }
        if (device.isVirtual()) {
            this.selectData.get(this.i).setSendModeNum(1);
            getAdapter().notifyItemChanged(this.i);
            LHomeLog.i(RequestConstant.ENV_TEST, getClass(), "第" + this.i + "个数据发送完成");
            this.successDeviceIds.add(Long.valueOf(device.getDeviceId()));
            if (this.deviceListIterator.hasNext()) {
                sendCmdListAndBackup(this.deviceListIterator.next(), cmdParam, flag);
                return;
            } else {
                ReplaceHelper.instance().addBackupData(flag, cmdParam);
                sendProcessOver();
                return;
            }
        }
        CmdAssistant.getLightCmdAssistant(device, new int[0]).sendEngineerMode(this, cmdParam, this.isLast, new IAction() { // from class: com.ltech.smarthome.ui.control.ActChangeEngineerMode$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActChangeEngineerMode.this.lambda$sendCmdListAndBackup$1(device, cmdParam, flag, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendCmdListAndBackup$1(Device device, BaseCmdParam baseCmdParam, String str, Boolean bool) {
        if (bool.booleanValue()) {
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
            sendCmdListAndBackup(this.deviceListIterator.next(), baseCmdParam, str);
        } else {
            ReplaceHelper.instance().addBackupData(str, baseCmdParam);
            sendProcessOver();
        }
    }

    private void sendCmdList(Device device, final BaseCmdParam cmdParam) {
        this.i = 0;
        ListIterator<DeviceItem> listIterator = this.selectData.listIterator();
        while (listIterator.hasNext()) {
            this.i = listIterator.nextIndex();
            if (listIterator.next().deviceId == device.getDeviceId()) {
                break;
            }
        }
        LHomeLog.i(RequestConstant.ENV_TEST, getClass(), "第" + this.i + "个数据sendModeToList enter");
        if (!this.deviceListIterator.hasNext()) {
            this.isLast = true;
        }
        if (device.isVirtual()) {
            this.selectData.get(this.i).setSendModeNum(1);
            getAdapter().notifyItemChanged(this.i);
            LHomeLog.i(RequestConstant.ENV_TEST, getClass(), "第" + this.i + "个数据发送完成");
            if (this.deviceListIterator.hasNext()) {
                sendCmdList(this.deviceListIterator.next(), cmdParam);
                return;
            }
            this.isSendProcess = false;
            changeViewState();
            this.addDeviceIdList.clear();
            return;
        }
        CmdAssistant.getSettingCmdAssistant(device, new int[0]).sendEngineerMode(this, cmdParam, this.isLast, new IAction() { // from class: com.ltech.smarthome.ui.control.ActChangeEngineerMode$$ExternalSyntheticLambda9
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActChangeEngineerMode.this.lambda$sendCmdList$2(cmdParam, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendCmdList$2(BaseCmdParam baseCmdParam, Boolean bool) {
        if (bool.booleanValue()) {
            this.selectData.get(this.i).setSendModeNum(1);
            getAdapter().notifyItemChanged(this.i);
            LHomeLog.i(RequestConstant.ENV_TEST, getClass(), "第" + this.i + "个数据发送完成");
        } else {
            this.selectData.get(this.i).setSendModeNum(2);
            getAdapter().notifyItemChanged(this.i);
            LHomeLog.i(RequestConstant.ENV_TEST, getClass(), "第" + this.i + "个数据发送失败");
        }
        if (this.deviceListIterator.hasNext()) {
            sendCmdList(this.deviceListIterator.next(), baseCmdParam);
            return;
        }
        this.isSendProcess = false;
        changeViewState();
        this.addDeviceIdList.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendCtRangeList(final Device device, final int max, final int min) {
        this.i = 0;
        ListIterator<DeviceItem> listIterator = this.selectData.listIterator();
        while (listIterator.hasNext()) {
            this.i = listIterator.nextIndex();
            if (listIterator.next().deviceId == device.getDeviceId()) {
                break;
            }
        }
        LHomeLog.i(RequestConstant.ENV_TEST, getClass(), "第" + this.i + "个数据sendModeToList enter");
        if (!this.deviceListIterator.hasNext()) {
            this.isLast = true;
        }
        if (device.isVirtual()) {
            ((ObservableSubscribeProxy) Injection.net().updateCtRange(device.getDeviceId(), max, min).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycle(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.ActChangeEngineerMode$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActChangeEngineerMode.this.lambda$sendCtRangeList$3(device, min, max, obj);
                }
            }, new Consumer() { // from class: com.ltech.smarthome.ui.control.ActChangeEngineerMode$$ExternalSyntheticLambda6
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActChangeEngineerMode.this.lambda$sendCtRangeList$4(max, min, device, (Throwable) obj);
                }
            });
        } else {
            CmdAssistant.getLightCmdAssistant(device, new int[0]).setKRange(this, min, max, this.isLast, new IAction() { // from class: com.ltech.smarthome.ui.control.ActChangeEngineerMode$$ExternalSyntheticLambda7
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActChangeEngineerMode.this.lambda$sendCtRangeList$7(device, max, min, (Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendCtRangeList$3(Device device, int i, int i2, Object obj) throws Exception {
        device.setMinkelvin(i);
        device.setMaxkelvin(i2);
        Injection.repo().device().saveDevice(device);
        this.selectData.get(this.i).setSendModeNum(1);
        getAdapter().notifyItemChanged(this.i);
        this.successDeviceIds.add(Long.valueOf(device.getDeviceId()));
        if (this.deviceListIterator.hasNext()) {
            sendCtRangeList(this.deviceListIterator.next(), i2, i);
        } else {
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.CT_RANGE, CmdAssistant.getLightCmdAssistant(device, new int[0]).setKRange(i, i2));
            sendProcessOver();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendCtRangeList$4(int i, int i2, Device device, Throwable th) throws Exception {
        if (this.isLast) {
            SmartToast.showShort(R.string.no_network);
        }
        this.selectData.get(this.i).setSendModeNum(2);
        getAdapter().notifyItemChanged(this.i);
        if (this.deviceListIterator.hasNext()) {
            sendCtRangeList(this.deviceListIterator.next(), i, i2);
        } else {
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.CT_RANGE, CmdAssistant.getLightCmdAssistant(device, new int[0]).setKRange(device.getMinkelvin(), device.getMaxkelvin()));
            sendProcessOver();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendCtRangeList$7(final Device device, final int i, final int i2, Boolean bool) {
        if (bool.booleanValue()) {
            ((ObservableSubscribeProxy) Injection.net().updateCtRange(device.getDeviceId(), i, i2).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycle(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.ActChangeEngineerMode$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActChangeEngineerMode.this.lambda$sendCtRangeList$5(device, i2, i, obj);
                }
            }, new Consumer() { // from class: com.ltech.smarthome.ui.control.ActChangeEngineerMode$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActChangeEngineerMode.this.lambda$sendCtRangeList$6(device, i, i2, (Throwable) obj);
                }
            });
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
    public /* synthetic */ void lambda$sendCtRangeList$5(Device device, int i, int i2, Object obj) throws Exception {
        device.setMinkelvin(i);
        device.setMaxkelvin(i2);
        Injection.repo().device().saveDevice(device);
        this.selectData.get(this.i).setSendModeNum(1);
        getAdapter().notifyItemChanged(this.i);
        this.successDeviceIds.add(Long.valueOf(device.getDeviceId()));
        if (this.deviceListIterator.hasNext()) {
            sendCtRangeList(this.deviceListIterator.next(), i2, i);
        } else {
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.CT_RANGE, CmdAssistant.getLightCmdAssistant(device, new int[0]).setKRange(i, i2));
            sendProcessOver();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendCtRangeList$6(final Device device, final int i, final int i2, Throwable th) throws Exception {
        if (this.isLast) {
            SmartToast.showShort(R.string.no_network);
        }
        CmdAssistant.getLightCmdAssistant(device, new int[0]).setKRange(this.activity, device.getMinkelvin(), device.getMaxkelvin(), false, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.control.ActChangeEngineerMode.3
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                ((DeviceItem) ActChangeEngineerMode.this.selectData.get(ActChangeEngineerMode.this.i)).setSendModeNum(2);
                ActChangeEngineerMode.this.getAdapter().notifyItemChanged(ActChangeEngineerMode.this.i);
                if (ActChangeEngineerMode.this.deviceListIterator.hasNext()) {
                    ActChangeEngineerMode actChangeEngineerMode = ActChangeEngineerMode.this;
                    actChangeEngineerMode.sendCtRangeList((Device) actChangeEngineerMode.deviceListIterator.next(), i, i2);
                } else {
                    ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.CT_RANGE, CmdAssistant.getLightCmdAssistant(device, new int[0]).setKRange(i2, i));
                    ActChangeEngineerMode.this.sendProcessOver();
                }
            }
        });
    }

    private void sendGatewayNightMode(final Device device, final int nightMode, final int startH, final int startM, final int endH, final int endM) {
        int i;
        int i2;
        this.i = 0;
        ListIterator<DeviceItem> listIterator = this.selectData.listIterator();
        while (listIterator.hasNext()) {
            this.i = listIterator.nextIndex();
            if (listIterator.next().deviceId == device.getDeviceId()) {
                break;
            }
        }
        if (!this.deviceListIterator.hasNext()) {
            this.isLast = true;
        }
        final SuperPanelExtParam superPanelExtParam = new SuperPanelExtParam();
        if (device.getExtParam() != null) {
            superPanelExtParam.fillMapWithString(device.getExtParam());
        }
        superPanelExtParam.setSwitchNightMode(nightMode);
        if (nightMode == 1) {
            superPanelExtParam.setSwitchNightModeStartH(startH);
            superPanelExtParam.setSwitchNightModeStartM(startM);
            i = endH;
            superPanelExtParam.setSwitchNightModeEndH(i);
            i2 = endM;
            superPanelExtParam.setSwitchNightModeEndM(i2);
        } else {
            i = endH;
            i2 = endM;
        }
        final int i3 = i;
        final int i4 = i2;
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(device.getDeviceId(), superPanelExtParam.getParamString()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycle(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.ActChangeEngineerMode$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActChangeEngineerMode.this.lambda$sendGatewayNightMode$8(device, superPanelExtParam, nightMode, startH, startM, i3, i4, obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.control.ActChangeEngineerMode$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActChangeEngineerMode.this.lambda$sendGatewayNightMode$9(nightMode, startH, startM, endH, endM, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendGatewayNightMode$8(Device device, SuperPanelExtParam superPanelExtParam, int i, int i2, int i3, int i4, int i5, Object obj) throws Exception {
        device.setExtParam(superPanelExtParam.getParamString());
        Injection.repo().device().saveDevice(device);
        this.selectData.get(this.i).setSendModeNum(1);
        getAdapter().notifyItemChanged(this.i);
        if (this.deviceListIterator.hasNext()) {
            sendGatewayNightMode(this.deviceListIterator.next(), i, i2, i3, i4, i5);
            return;
        }
        this.isSendProcess = false;
        changeViewState();
        this.addDeviceIdList.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendGatewayNightMode$9(int i, int i2, int i3, int i4, int i5, Throwable th) throws Exception {
        this.selectData.get(this.i).setSendModeNum(2);
        getAdapter().notifyItemChanged(this.i);
        if (this.deviceListIterator.hasNext()) {
            sendGatewayNightMode(this.deviceListIterator.next(), i, i2, i3, i4, i5);
            return;
        }
        this.isSendProcess = false;
        changeViewState();
        this.addDeviceIdList.clear();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        char c2;
        int i = this.engineerSetType;
        if ((i == 4 || i == 5 || i == 9 || i == 12 || i == 11) && device.isVirtual()) {
            return false;
        }
        int i2 = this.engineerSetType;
        if (i2 == 12) {
            if (device.isSubDevice()) {
                return false;
            }
            int lightColorType = ProductRepository.getLightColorType((Object) device);
            return 8 == lightColorType || 9 == lightColorType || 10 == lightColorType || 18 == lightColorType || 11 == lightColorType || 19 == lightColorType || 21 == lightColorType;
        }
        if (i2 == 14) {
            return ProductRepository.isSuperPanel(device.getProductId());
        }
        if (i2 == 9) {
            if (device.isSubDevice()) {
                return false;
            }
            return ProductRepository.isBLeDevice(device.getProductId()) || ProductRepository.isWifiBleDevice(device.getProductId());
        }
        if (ProductRepository.isDaliLightGroup(device)) {
            return false;
        }
        int lightColorType2 = ProductRepository.getLightColorType((Object) device);
        if (this.engineerSetType == 11) {
            return lightColorType2 == 20;
        }
        if (lightColorType2 == 2 && device.getMaxkelvin() > 0) {
            return true;
        }
        String productId = device.getProductId();
        productId.hashCode();
        switch (productId.hashCode()) {
            case -1822884084:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB6)) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case -1819630261:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1_PRO)) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case -1817691924:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2_PRO)) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case -1805322688:
                if (productId.equals(ProductId.ID_BLE_LIGHT_DIM)) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case -1805199680:
                if (productId.equals(ProductId.ID_BLE_LIGHT_CT)) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case -1804340546:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGB)) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            case -1804278081:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBW)) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case -1803448738:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBWY)) {
                    c2 = 7;
                    break;
                }
                c2 = 65535;
                break;
            case -1796419228:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                    c2 = '\b';
                    break;
                }
                c2 = 65535;
                break;
            case -1710907378:
                if (productId.equals(ProductId.ID_BLE_KBS)) {
                    c2 = '\t';
                    break;
                }
                c2 = 65535;
                break;
            case -1550133760:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB1)) {
                    c2 = '\n';
                    break;
                }
                c2 = 65535;
                break;
            case -1309274422:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO)) {
                    c2 = 11;
                    break;
                }
                c2 = 65535;
                break;
            case -1308265372:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_12S)) {
                    c2 = '\f';
                    break;
                }
                c2 = 65535;
                break;
            case -1265646206:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                    c2 = '\r';
                    break;
                }
                c2 = 65535;
                break;
            case -835060954:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                    c2 = 14;
                    break;
                }
                c2 = 65535;
                break;
            case -732569219:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S4)) {
                    c2 = 15;
                    break;
                }
                c2 = 65535;
                break;
            case -728269602:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6T)) {
                    c2 = 16;
                    break;
                }
                c2 = 65535;
                break;
            case -324427448:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_6S)) {
                    c2 = 17;
                    break;
                }
                c2 = 65535;
                break;
            case 166485422:
                if (productId.equals(ProductId.ID_BLE_SWITCH)) {
                    c2 = 18;
                    break;
                }
                c2 = 65535;
                break;
            case 225641606:
                if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                    c2 = 19;
                    break;
                }
                c2 = 65535;
                break;
            case 294483828:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_4S)) {
                    c2 = 20;
                    break;
                }
                c2 = 65535;
                break;
            case 312618751:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6M)) {
                    c2 = 21;
                    break;
                }
                c2 = 65535;
                break;
            case 356111630:
                if (productId.equals(ProductId.ID_AS_PANEL_U1S)) {
                    c2 = 22;
                    break;
                }
                c2 = 65535;
                break;
            case 356193315:
                if (productId.equals(ProductId.ID_AS_PANEL_U2S)) {
                    c2 = 23;
                    break;
                }
                c2 = 65535;
                break;
            case 376429092:
                if (productId.equals(ProductId.ID_AS_PANEL_U4S)) {
                    c2 = 24;
                    break;
                }
                c2 = 65535;
                break;
            case 376488674:
                if (productId.equals(ProductId.ID_AS_PANEL_U5S)) {
                    c2 = 25;
                    break;
                }
                c2 = 65535;
                break;
            case 439998223:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6D)) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                c2 = 65535;
                break;
            case 534249931:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB2)) {
                    c2 = 27;
                    break;
                }
                c2 = 65535;
                break;
            case 811752507:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI)) {
                    c2 = 28;
                    break;
                }
                c2 = 65535;
                break;
            case 956710656:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL)) {
                    c2 = 29;
                    break;
                }
                c2 = 65535;
                break;
            case 1647983530:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6A)) {
                    c2 = 30;
                    break;
                }
                c2 = 65535;
                break;
            case 1861788715:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB5)) {
                    c2 = 31;
                    break;
                }
                c2 = 65535;
                break;
            case 1951402182:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                    c2 = ' ';
                    break;
                }
                c2 = 65535;
                break;
            case 1951547293:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                    c2 = '!';
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        switch (c2) {
            case 0:
            case '\n':
            case 22:
            case 23:
            case 24:
            case 25:
            case 27:
            case 31:
                int i3 = this.engineerSetType;
                if (i3 == 1 || i3 == 2 || i3 == 3) {
                }
                break;
            case 1:
            case 2:
            case '\b':
            case 11:
            case '\f':
            case '\r':
            case 14:
            case 15:
            case 17:
            case 19:
            case 20:
            case 28:
            case 29:
            case ' ':
            case '!':
                int i4 = this.engineerSetType;
                if (i4 == 4 || i4 == 5 || i4 == 9 || i4 == 12) {
                }
                break;
            case 3:
            case 4:
                int i5 = this.engineerSetType;
                if (i5 != 5) {
                    if ((i5 == 7 || i5 == 8 || i5 == 10) && lightColorType2 != 2) {
                    }
                }
                break;
            case 5:
            case 6:
            case 7:
                int i6 = this.engineerSetType;
                if (i6 == 8 || i6 == 10) {
                    if (lightColorType2 == 20) {
                    }
                } else if (i6 == 5 || i6 == 6 || i6 == 7) {
                }
                break;
            case '\t':
            case 18:
                if (!device.isSubDevice() && this.engineerSetType == 1 && SharedPreferenceUtil.queryIntValue(Constants.ON_OFF_STATE_MAIN_VALUE) != 4) {
                }
                break;
            case 16:
            case 21:
            case 26:
            case 30:
                int i7 = this.engineerSetType;
                if (i7 == 1 || i7 == 2 || i7 == 3 || i7 == 13) {
                }
                break;
        }
        return false;
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