package com.ltech.smarthome.ui.device.spicontroller;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.PickerAdapter;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSpiLightSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.SpiLightExtParam;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.SettingAssistant;
import com.ltech.smarthome.view.layoutmanager.PickerLayoutManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSpiLightSetting extends VMActivity<ActSpiLightSettingBinding, BaseDeviceSetViewModel> {
    private boolean canSave;
    private SpiLightExtParam extParam;
    private List<String> icList = new ArrayList();
    private PickerLayoutManager mIcManager;
    private PickerLayoutManager mOrderManager;
    private int pixelValue;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_spi_light_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        getWindow().setSoftInputMode(32);
        setTitle(getString(R.string.spi_light_param));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        initRv();
        ((ActSpiLightSettingBinding) this.mViewBinding).editPixel.addTextChangedListener(new TextWatcher() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiLightSetting.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    return;
                }
                ActSpiLightSetting.this.pixelValue = Integer.parseInt(s.toString());
                if (ActSpiLightSetting.this.pixelValue < 8 || ActSpiLightSetting.this.pixelValue > 1020) {
                    ((ActSpiLightSettingBinding) ActSpiLightSetting.this.mViewBinding).editPixel.setTextColor(ActSpiLightSetting.this.getResources().getColor(R.color.color_text_red));
                    ((ActSpiLightSettingBinding) ActSpiLightSetting.this.mViewBinding).tvError.setVisibility(0);
                    ActSpiLightSetting.this.canSave = false;
                    ((ActSpiLightSettingBinding) ActSpiLightSetting.this.mViewBinding).title.tvEdit.setTextColor(ActSpiLightSetting.this.getResources().getColor(R.color.color_text_gray));
                    return;
                }
                ((ActSpiLightSettingBinding) ActSpiLightSetting.this.mViewBinding).editPixel.setTextColor(ActSpiLightSetting.this.getResources().getColor(R.color.color_text_black));
                ((ActSpiLightSettingBinding) ActSpiLightSetting.this.mViewBinding).tvError.setVisibility(8);
                ActSpiLightSetting.this.canSave = true;
                ((ActSpiLightSettingBinding) ActSpiLightSetting.this.mViewBinding).title.tvEdit.setTextColor(ActSpiLightSetting.this.getResources().getColor(R.color.color_text_black));
            }
        });
        ((BaseDeviceSetViewModel) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L)));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((BaseDeviceSetViewModel) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiLightSetting$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSpiLightSetting.this.lambda$startObserve$0((Device) obj);
            }
        });
        ((BaseDeviceSetViewModel) this.mViewModel).refreshEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiLightSetting$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSpiLightSetting.this.lambda$startObserve$1((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        if (device.getExtParam() != null) {
            SpiLightExtParam spiLightExtParam = (SpiLightExtParam) device.getExtParam(SpiLightExtParam.class);
            this.extParam = spiLightExtParam;
            if (spiLightExtParam == null || spiLightExtParam.getPixel() == 0) {
                return;
            }
            ((ActSpiLightSettingBinding) this.mViewBinding).pickViewIc.scrollToPosition(this.extParam.getIcType());
            ((ActSpiLightSettingBinding) this.mViewBinding).pickerViewOrder.scrollToPosition(this.extParam.getIcLine());
            ((ActSpiLightSettingBinding) this.mViewBinding).editPixel.setText(String.valueOf(this.extParam.getPixel()));
            return;
        }
        this.extParam = new SpiLightExtParam();
        ((ActSpiLightSettingBinding) this.mViewBinding).pickViewIc.scrollToPosition(1);
        ((ActSpiLightSettingBinding) this.mViewBinding).pickerViewOrder.scrollToPosition(0);
        ((ActSpiLightSettingBinding) this.mViewBinding).editPixel.setText(String.valueOf(100));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r1) {
        finishActivity();
    }

    private void initRv() {
        PickerAdapter pickerAdapter = new PickerAdapter(this);
        PickerAdapter pickerAdapter2 = new PickerAdapter(this);
        List<String> asList = Arrays.asList(getResources().getStringArray(R.array.spi_light_ic));
        this.icList = asList;
        pickerAdapter.setData(asList);
        pickerAdapter2.setData(Arrays.asList(getResources().getStringArray(R.array.spi_light_order)));
        this.mIcManager = new PickerLayoutManager.Builder(this).setAlpha(true).setMaxItem(5).build();
        this.mOrderManager = new PickerLayoutManager.Builder(this).setAlpha(true).setMaxItem(5).build();
        ((ActSpiLightSettingBinding) this.mViewBinding).pickViewIc.setLayoutManager(this.mIcManager);
        ((ActSpiLightSettingBinding) this.mViewBinding).pickerViewOrder.setLayoutManager(this.mOrderManager);
        ((ActSpiLightSettingBinding) this.mViewBinding).pickViewIc.setAdapter(pickerAdapter);
        ((ActSpiLightSettingBinding) this.mViewBinding).pickerViewOrder.setAdapter(pickerAdapter2);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (this.canSave) {
            showLoadingDialog(getString(R.string.saving));
            this.extParam.setIcType(this.mIcManager.getPickedPosition());
            this.extParam.setIcLine(this.mOrderManager.getPickedPosition());
            this.extParam.setPixel(this.pixelValue);
            getCmdHelper().setSpiLoadParam(this, this.mOrderManager.getPickedPosition() + 1, getIcNumber(this.icList.get(this.mIcManager.getPickedPosition())), this.pixelValue, new IAction() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiLightSetting$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSpiLightSetting.this.lambda$edit$2((Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$2(Boolean bool) {
        if (bool.booleanValue()) {
            showSuccessDialog(getString(R.string.save_success));
            if (((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue() != null) {
                ((BaseDeviceSetViewModel) this.mViewModel).updateParamExt(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue(), GsonUtils.toJson(this.extParam));
            }
            ReplaceHelper.instance().backupData(this, ((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue().getDeviceId(), UpdateBackDataRequest.SPI_PARAM, getCmdHelper().setSpiLoadParam(this.mOrderManager.getPickedPosition() + 1, getIcNumber(this.icList.get(this.mIcManager.getPickedPosition())), this.pixelValue));
            return;
        }
        showErrorDialog(getString(R.string.save_fail));
    }

    public int getIcNumber(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                sb.append(str.charAt(i));
            }
        }
        return Integer.parseInt(sb.toString(), 16);
    }

    private SettingAssistant getCmdHelper() {
        return CmdAssistant.getSettingCmdAssistant(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue(), new int[0]);
    }
}