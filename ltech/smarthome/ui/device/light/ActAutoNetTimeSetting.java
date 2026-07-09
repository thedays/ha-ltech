package com.ltech.smarthome.ui.device.light;

import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.PickerAdapter;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActAutoNetTimeSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.ui.control.ActChangeEngineerMode;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.layoutmanager.PickerLayoutManager;
import com.smart.message.ResponseMsg;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActAutoNetTimeSetting extends VMActivity<ActAutoNetTimeSettingBinding, ActAutoNetTimeSettingVM> {
    private long controlId;
    private boolean groupControl;
    PickerLayoutManager mMinuteManager;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_auto_net_time_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.auto_net_time));
        this.controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        this.groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        setEditString(getString(this.controlId != -1 ? R.string.confirm : R.string.next));
        PickerAdapter pickerAdapter = new PickerAdapter(this);
        ArrayList arrayList = new ArrayList();
        for (int i = 3; i <= 60; i++) {
            arrayList.add(String.valueOf(i));
        }
        pickerAdapter.setData(arrayList);
        this.mMinuteManager = new PickerLayoutManager.Builder(this).setAlpha(true).setMaxItem(5).build();
        ((ActAutoNetTimeSettingBinding) this.mViewBinding).pickViewMin.setLayoutManager(this.mMinuteManager);
        ((ActAutoNetTimeSettingBinding) this.mViewBinding).pickViewMin.setAdapter(pickerAdapter);
        if (this.controlId != -1) {
            final int queryIntValue = SharedPreferenceUtil.queryIntValue(Constants.AUTO_NET_TIME_DEVICE_VALUE, 7);
            if (this.groupControl) {
                CmdAssistant.getLightCmdAssistant(Injection.repo().group().getGroupById(this.controlId), new int[0]).getAutoNetTime(this, new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.light.ActAutoNetTimeSetting.1
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(ResponseMsg responseMsg) {
                        if (responseMsg == null || responseMsg.getStateCode() != 0) {
                            ((ActAutoNetTimeSettingBinding) ActAutoNetTimeSetting.this.mViewBinding).pickViewMin.scrollToPosition(queryIntValue);
                        } else {
                            if (responseMsg.getResData().length() <= 18) {
                                ((ActAutoNetTimeSettingBinding) ActAutoNetTimeSetting.this.mViewBinding).pickViewMin.scrollToPosition(queryIntValue);
                                return;
                            }
                            ((ActAutoNetTimeSettingBinding) ActAutoNetTimeSetting.this.mViewBinding).pickViewMin.scrollToPosition(Integer.parseInt(responseMsg.getResData().substring(18, 20), 16) - 3);
                            SharedPreferenceUtil.edit().keepShared(Constants.AUTO_NET_TIME_DEVICE_VALUE, ActAutoNetTimeSetting.this.mMinuteManager.getPickedPosition() + 3);
                        }
                    }
                });
                return;
            } else {
                CmdAssistant.getLightCmdAssistant(Injection.repo().device().getDeviceById(this.controlId), new int[0]).getAutoNetTime(this, new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.light.ActAutoNetTimeSetting.2
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(ResponseMsg responseMsg) {
                        if (responseMsg == null || responseMsg.getStateCode() != 0) {
                            ((ActAutoNetTimeSettingBinding) ActAutoNetTimeSetting.this.mViewBinding).pickViewMin.scrollToPosition(queryIntValue);
                        } else {
                            if (responseMsg.getResData().length() <= 18) {
                                ((ActAutoNetTimeSettingBinding) ActAutoNetTimeSetting.this.mViewBinding).pickViewMin.scrollToPosition(queryIntValue);
                                return;
                            }
                            ((ActAutoNetTimeSettingBinding) ActAutoNetTimeSetting.this.mViewBinding).pickViewMin.scrollToPosition(Integer.parseInt(responseMsg.getResData().substring(18, 20), 16) - 3);
                            SharedPreferenceUtil.edit().keepShared(Constants.AUTO_NET_TIME_DEVICE_VALUE, ActAutoNetTimeSetting.this.mMinuteManager.getPickedPosition() + 3);
                        }
                    }
                });
                return;
            }
        }
        ((ActAutoNetTimeSettingBinding) this.mViewBinding).pickViewMin.scrollToPosition(SharedPreferenceUtil.queryIntValue(Constants.AUTO_NET_TIME_VALUE, 7) - 3);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (this.controlId != -1) {
            if (this.groupControl) {
                CmdAssistant.getLightCmdAssistant(Injection.repo().group().getGroupById(this.controlId), new int[0]).setAutoNetTime(this, this.mMinuteManager.getPickedPosition() + 3, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.light.ActAutoNetTimeSetting.3
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(Boolean aBoolean) {
                        if (aBoolean.booleanValue()) {
                            ActAutoNetTimeSetting.this.finishActivity();
                        }
                    }
                });
                return;
            } else {
                CmdAssistant.getLightCmdAssistant(Injection.repo().device().getDeviceById(this.controlId), new int[0]).setAutoNetTime(this, this.mMinuteManager.getPickedPosition() + 3, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.light.ActAutoNetTimeSetting.4
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(Boolean aBoolean) {
                        if (aBoolean.booleanValue()) {
                            ActAutoNetTimeSetting.this.finishActivity();
                        }
                    }
                });
                return;
            }
        }
        SharedPreferenceUtil.edit().keepShared(Constants.AUTO_NET_TIME_VALUE, this.mMinuteManager.getPickedPosition() + 3);
        NavUtils.destination(ActChangeEngineerMode.class).withLong(Constants.PLACE_ID, getIntent().getLongExtra(Constants.PLACE_ID, 0L)).withInt(Constants.ENGINEER_SET_TYPE, 9).navigation(this);
    }
}