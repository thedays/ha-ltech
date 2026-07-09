package com.ltech.smarthome.ui.mode;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import anetwork.channel.util.RequestConstant;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.eurpanel.EurHelper;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.ModeAssistant;
import com.smart.message.utils.LHomeLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/* loaded from: classes4.dex */
public class ActChangeGeneralMode extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> {
    private TextView cancelTv;
    private ListIterator<Device> deviceListIterator;
    private int i;
    private int lightType;
    private int totalDevice = 0;
    public List<Device> addDeviceIdList = new ArrayList();
    private boolean isLast = false;
    private boolean isSendProcess = false;
    private List<Long> successDeviceIds = new ArrayList();
    public List<DeviceItem> selectData = new ArrayList();
    private Map<Long, DeviceItem> deviceItemMap = new HashMap();

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
                helper.setGone(R.id.iv_select, false);
                helper.setGone(R.id.send_loading, true);
            } else {
                helper.setGone(R.id.iv_select, !this.isSendProcess);
                helper.setGone(R.id.send_loading, false);
            }
            if (deviceItem.getSendModeNum() != 0) {
                helper.setGone(R.id.send_loading, false);
                helper.setGone(R.id.iv_select, true);
                helper.setBackgroundRes(R.id.iv_select, deviceItem.getSendModeNum() == 1 ? R.mipmap.light_mode_icon_succes : R.mipmap.light_mode_icon_fail);
            }
            helper.itemView.setEnabled(deviceItem.getSendModeNum() != 1);
        }
        ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
        helper.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.mode.ActChangeGeneralMode$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActChangeGeneralMode.this.lambda$convertView$0(item, helper, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertView$0(Role role, BaseViewHolder baseViewHolder, View view) {
        if (this.isSendProcess) {
            return;
        }
        Device device = (Device) role;
        if (this.selectRoleIds.contains(Long.valueOf(device.getDeviceId()))) {
            this.selectRoleIds.remove(Long.valueOf(device.getDeviceId()));
            baseViewHolder.getView(R.id.iv_select).setBackgroundResource(R.mipmap.ic_tick_default);
        } else {
            this.selectRoleIds.add(Long.valueOf(device.getDeviceId()));
            baseViewHolder.getView(R.id.iv_select).findViewById(R.id.iv_select).setBackgroundResource(R.mipmap.ic_tick_sel);
        }
        if (this.deviceItemMap.containsKey(Long.valueOf(role.getObjectId()))) {
            DeviceItem deviceItem = this.deviceItemMap.get(Long.valueOf(role.getObjectId()));
            if (deviceItem.getSendModeNum() != 0) {
                deviceItem.setSendModeNum(0);
            }
            if (deviceItem.getSendMode() != 0) {
                deviceItem.setSendMode(0);
            }
        }
        this.selectCountLiveData.setValue(Integer.valueOf(this.selectRoleIds.size()));
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
        TextView textView = new TextView(this);
        this.cancelTv = textView;
        textView.setTextColor(getResources().getColor(R.color.color_text_red));
        this.cancelTv.setTextSize(14.0f);
        this.cancelTv.setBackgroundColor(getResources().getColor(R.color.white));
        this.cancelTv.setGravity(17);
        this.cancelTv.setText(getString(R.string.application_with_number, new Object[]{0}));
        ((ActSelect3Binding) this.mViewBinding).footerView.addView(this.cancelTv, new RelativeLayout.LayoutParams(-1, ConvertUtils.dp2px(60.0f)));
        this.cancelTv.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.mode.ActChangeGeneralMode$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActChangeGeneralMode.this.lambda$initView$1(view);
            }
        });
        this.lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view) {
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
        if (FtAdvancedMode.getObj() == null || this.addDeviceIdList.isEmpty()) {
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
        getAdapter().notifyDataSetChanged();
        ((ActSelect3Binding) this.mViewBinding).title.tvEdit.setTextColor(ContextCompat.getColor(this, R.color.color_text_gray));
        this.cancelTv.setTextColor(ContextCompat.getColor(this, R.color.color_text_gray));
        this.isLast = false;
        ListIterator<Device> listIterator = this.addDeviceIdList.listIterator();
        this.deviceListIterator = listIterator;
        sendModeToList(listIterator.next());
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

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (this.isSendProcess) {
            return;
        }
        super.edit();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        if (this.isSendProcess) {
            SmartToast.showShort(getString(R.string.sending_data_and_wait));
        } else {
            super.back();
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void changeSelectCount(int selectCount) {
        this.cancelTv.setText(getString(R.string.application_with_number, new Object[]{Integer.valueOf(selectCount)}));
        this.cancelTv.setTextColor(ContextCompat.getColor(this, R.color.color_text_red));
    }

    public void sendModeToList(final Device device) {
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
                sendModeToList(this.deviceListIterator.next());
                return;
            }
            this.isSendProcess = false;
            getAdapter().notifyDataSetChanged();
            ((ActSelect3Binding) this.mViewBinding).title.tvEdit.setTextColor(ContextCompat.getColor(this, R.color.color_text_black));
            this.addDeviceIdList.clear();
            if (this.successDeviceIds.isEmpty()) {
                return;
            }
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.GENERAL_MODE, getModeCmdHelper(device, FtAdvancedMode.getObj().getViewModel().zoneNum).saveGeneralModeInfo(FtGeneralMode.getObj().getViewModel().modeNum, FtGeneralMode.getObj().getViewModel().getModeInfo()));
            ReplaceHelper.instance().backupIndexData(this, this.successDeviceIds, FtAdvancedMode.getObj().getViewModel().modeNum);
            return;
        }
        getModeCmdHelper(device, FtGeneralMode.getObj().getViewModel().zoneNum).saveGeneralModeInfoInterval(FtGeneralMode.getObj().getActivity(), FtGeneralMode.getObj().getViewModel().modeNum, FtGeneralMode.getObj().getViewModel().getModeInfo(), this.isLast, new IAction() { // from class: com.ltech.smarthome.ui.mode.ActChangeGeneralMode$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActChangeGeneralMode.this.lambda$sendModeToList$2(device, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendModeToList$2(Device device, Boolean bool) {
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
            sendModeToList(this.deviceListIterator.next());
            return;
        }
        this.isSendProcess = false;
        getAdapter().notifyDataSetChanged();
        ((ActSelect3Binding) this.mViewBinding).title.tvEdit.setTextColor(ContextCompat.getColor(this, R.color.color_text_black));
        this.addDeviceIdList.clear();
        if (this.successDeviceIds.isEmpty()) {
            return;
        }
        ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.GENERAL_MODE, getModeCmdHelper(device, FtAdvancedMode.getObj().getViewModel().zoneNum).saveGeneralModeInfo(FtGeneralMode.getObj().getViewModel().modeNum, FtGeneralMode.getObj().getViewModel().getModeInfo()));
        ReplaceHelper.instance().backupIndexData(this, this.successDeviceIds, FtAdvancedMode.getObj().getViewModel().modeNum);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        if (!ProductRepository.supportLightMode(device.getProductId())) {
            return false;
        }
        int lightColorType = ProductRepository.getLightColorType((Object) device);
        if (EurHelper.isEb125(device)) {
            lightColorType = EurHelper.convertType(device);
        }
        if (lightColorType == 20) {
            lightColorType = 5;
        } else if (lightColorType == 2 && !ProductRepository.isEbSupportLight(device)) {
            return false;
        }
        int i = this.lightType;
        return i == -1 || i == lightColorType;
    }

    private ModeAssistant getModeCmdHelper(Object object, int zoneNum) {
        return CmdAssistant.getModeCmdAssistant(object, zoneNum);
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