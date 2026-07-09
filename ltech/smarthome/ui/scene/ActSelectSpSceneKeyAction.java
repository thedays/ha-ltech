package com.ltech.smarthome.ui.scene;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseMultiSelectActivity;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.SuperPanelExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.microwave_sensor.WaveSensorHelper;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NameRepository;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.smart.product_agreement.param.LightCmdParam;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectSpSceneKeyAction extends BaseMultiSelectActivity<String> implements ISelectAction {
    private int controlType;
    private String[] datas = new String[0];
    private boolean isLocal;
    private boolean isSept;
    private boolean isWaveSensorAction;
    private boolean keyAction;
    private String productId;

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void lambda$save$0(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseMultiSelectActivity
    protected void save() {
        for (boolean z : this.selectArray) {
            if (z) {
                if (this.isWaveSensorAction) {
                    WaveSensorHelper.instance().setSensorRelateBleParam();
                    if (WaveSensorHelper.instance().isChangeAll) {
                        WaveSensorHelper.instance().setDataType = 2;
                        WaveSensorHelper.instance().setSensorRelateBleParam();
                        WaveSensorHelper.instance().setDataType = 3;
                        selectSwitchClose();
                        WaveSensorHelper.instance().setSensorRelateBleParam();
                        return;
                    }
                    return;
                }
                SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectSpSceneKeyAction$$ExternalSyntheticLambda1
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActSelectSpSceneKeyAction.this.lambda$save$0((Boolean) obj);
                    }
                });
                return;
            }
        }
        SmartToast.showShort(R.string.please_choose);
    }

    @Override // com.ltech.smarthome.base.BaseMultiSelectActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_action));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        this.keyAction = getIntent().getBooleanExtra(Constants.KEY_ACTION_TYPE, false);
        this.controlType = getIntent().getIntExtra(Constants.LIGHT_TYPE, 0);
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        this.isLocal = getIntent().getBooleanExtra(Constants.IS_LOCAL_SCENE, false);
        this.isWaveSensorAction = getIntent().getBooleanExtra(Constants.WAVE_SENSOR_ACTION, false);
        this.isSept = getIntent().getBooleanExtra(Constants.IS_SEPT, false);
        if (this.isWaveSensorAction) {
            SceneHelper.instance().controlObject = WaveSensorHelper.instance().relateObject;
        }
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectSpSceneKeyAction$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSelectSpSceneKeyAction.this.lambda$initView$1(baseQuickAdapter, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (this.isSept || !this.isLocal || SceneHelper.instance().controlObject == null || isEurPanel() || !RelateInfoUtils.isPositionBind(SceneHelper.instance().controlObject, i)) {
            this.selectArray[i] = !this.selectArray[i];
            baseQuickAdapter.notifyItemChanged(i);
            onItemClick(i);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        initData();
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<String> getList() {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, String item) {
        boolean z = this.isSept || !this.isLocal || SceneHelper.instance().controlObject == null || isEurPanel() || !RelateInfoUtils.isPositionBind(SceneHelper.instance().controlObject, helper.getBindingAdapterPosition());
        helper.setText(R.id.tv_name, item);
        if (z) {
            helper.setTextColor(R.id.tv_name, getResources().getColor(R.color.color_text_black)).setBackgroundRes(R.id.iv_select, this.selectArray[helper.getAdapterPosition()] ? R.mipmap.ic_tick_sel : R.color.transparent);
        } else {
            helper.setTextColor(R.id.tv_name, getResources().getColor(R.color.color_text_dark_gray));
        }
        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseMultiSelectActivity
    protected void onItemClick(int position) {
        if (this.keyAction) {
            selectSwitchOn();
        } else {
            selectSwitchClose();
        }
    }

    private void initData() {
        SuperPanelExtParam superPanelExtParam;
        SuperPanelExtParam superPanelExtParam2;
        if (ProductId.ID_ANDROID_SUPER_PANEL_MINI.equals(this.productId) || ProductId.ID_ANDROID_SUPER_PANEL_4S.equals(this.productId) || ProductId.ID_ANDROID_SUPER_PANEL_6S.equals(this.productId) || ProductId.ID_ANDROID_SUPER_PANEL_12S.equals(this.productId)) {
            this.datas = NameRepository.getSmartPanelS2KeyName(this);
            Device device = (Device) SceneHelper.instance().controlObject;
            if (device.getExtParam() != null && (superPanelExtParam = (SuperPanelExtParam) device.getExtParam(SuperPanelExtParam.class)) != null) {
                this.datas = superPanelExtParam.getKeyNameArray(this.datas);
            }
        } else if (ProductId.ID_ANDROID_SUPER_PANEL_PRO.equals(this.productId) || ProductId.ID_ANDROID_SUPER_PANEL_G4MAX.equals(this.productId)) {
            this.datas = NameRepository.getSmartPanelS4KeyName(this);
            Device device2 = (Device) SceneHelper.instance().controlObject;
            if (device2.getExtParam() != null && (superPanelExtParam2 = (SuperPanelExtParam) device2.getExtParam(SuperPanelExtParam.class)) != null) {
                this.datas = superPanelExtParam2.getKeyNameArray(this.datas);
            }
        } else if (isEurPanel()) {
            if (this.keyAction) {
                this.datas = new String[]{getString(R.string.open_zone_with_num, new Object[]{1}), getString(R.string.open_zone_with_num, new Object[]{2}), getString(R.string.open_zone_with_num, new Object[]{3}), getString(R.string.open_zone_with_num, new Object[]{4})};
            } else {
                this.datas = new String[]{getString(R.string.close_zone_with_num, new Object[]{1}), getString(R.string.close_zone_with_num, new Object[]{2}), getString(R.string.close_zone_with_num, new Object[]{3}), getString(R.string.close_zone_with_num, new Object[]{4})};
            }
        } else if (SceneHelper.instance().controlObject == null) {
            int i = this.controlType;
            if (i != 18) {
                if (i != 19) {
                    if (i != 26) {
                        switch (i) {
                            case 8:
                                this.datas = NameRepository.getSmartPanelS1KeyName(this);
                                break;
                            case 9:
                                break;
                            case 10:
                                break;
                            case 11:
                                break;
                            default:
                                this.datas = NameRepository.getSmartPanelS8KeyName(this);
                                break;
                        }
                    }
                    this.datas = NameRepository.getSmartPanelS2KeyName(this);
                }
                this.datas = NameRepository.getSmartPanelS3KeyName(this);
            }
            this.datas = NameRepository.getSmartPanelS4KeyName(this);
        } else {
            int i2 = this.controlType;
            if (i2 == 19 || i2 == 21 || i2 == 8 || i2 == 9 || i2 == 10 || i2 == 18 || i2 == 11) {
                String[] relaySubNameArray = RelaySeparationHelper.getRelaySubNameArray(SceneHelper.instance().controlObject);
                this.datas = relaySubNameArray;
                if (relaySubNameArray == null || relaySubNameArray.length == 0) {
                    this.datas = RelateInfoUtils.getSwitchNameArray(SceneHelper.instance().controlObject);
                }
            } else if (i2 == 26) {
                Object obj = SceneHelper.instance().controlObject;
                if (obj instanceof Device) {
                    Device device3 = (Device) obj;
                    List<Device> subDevice = Injection.repo().device().getSubDevice(device3.getPlaceId(), device3.getDeviceId());
                    Collections.sort(subDevice, new Comparator<Device>(this) { // from class: com.ltech.smarthome.ui.scene.ActSelectSpSceneKeyAction.1
                        @Override // java.util.Comparator
                        public int compare(Device o1, Device o2) {
                            return o1.getWifiMac().compareTo(o2.getWifiMac());
                        }
                    });
                    this.datas = new String[subDevice.size()];
                    for (int i3 = 0; i3 < this.datas.length; i3++) {
                        this.datas[i3] = subDevice.get(i3).getDeviceName();
                    }
                } else {
                    this.datas = NameRepository.getSmartPanelS2KeyName(this);
                }
            } else {
                this.datas = RelateInfoUtils.getSwitchNameArray(SceneHelper.instance().controlObject);
            }
        }
        setDataList(Arrays.asList(this.datas));
        ((ActSelectBinding) this.mViewBinding).tvTip.setVisibility(8);
        if (isEurPanel()) {
            if (SceneHelper.instance().selectDeviceParam == null) {
                Arrays.fill(this.selectArray, true);
            } else if ((SceneHelper.instance().selectDeviceParam.option.equals("11") && this.keyAction) || (SceneHelper.instance().selectDeviceParam.option.equals("12") && !this.keyAction)) {
                int i4 = SceneHelper.instance().selectDeviceParam.sceneZone;
                for (int i5 = 0; i5 < this.selectArray.length; i5++) {
                    this.selectArray[i5] = ((i4 >> i5) & 1) == 1;
                }
            } else {
                Arrays.fill(this.selectArray, true);
            }
            if (this.keyAction) {
                selectSwitchOn();
            } else {
                selectSwitchClose();
            }
        }
    }

    public void selectSwitchClose() {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setCmdType(1);
        lightCmdParam.setOn(false);
        if (isEurPanel()) {
            lightCmdParam.addExtParam(SceneHelper.OPTION, "12");
        } else {
            lightCmdParam.addExtParam(SceneHelper.OPTION, "1");
        }
        lightCmdParam.setZoneNum(getSelectZone());
        if (!this.isWaveSensorAction) {
            lightCmdParam.addExtParam(SceneHelper.OPTION_VALUE, String.valueOf(getSelectZone()));
            SceneHelper.instance().cmdParam = lightCmdParam;
        } else {
            lightCmdParam.addExtParam(SceneHelper.OPTION_VALUE, Integer.valueOf(getSelectZone()));
            WaveSensorHelper.instance().cmdParam = lightCmdParam;
        }
    }

    public void selectSwitchOn() {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setCmdType(1);
        lightCmdParam.setOn(true);
        if (isEurPanel()) {
            lightCmdParam.addExtParam(SceneHelper.OPTION, "11");
        } else {
            lightCmdParam.addExtParam(SceneHelper.OPTION, "0");
        }
        lightCmdParam.setZoneNum(getSelectZone());
        if (this.isWaveSensorAction) {
            lightCmdParam.addExtParam(SceneHelper.OPTION_VALUE, Integer.valueOf(getSelectZone()));
            WaveSensorHelper.instance().cmdParam = lightCmdParam;
        } else {
            lightCmdParam.addExtParam(SceneHelper.OPTION_VALUE, String.valueOf(getSelectZone()));
            SceneHelper.instance().cmdParam = lightCmdParam;
        }
    }

    private int getSelectZone() {
        int i = 0;
        for (int i2 = 0; i2 < this.selectArray.length; i2++) {
            if (this.selectArray[i2]) {
                i += 1 << i2;
            }
        }
        return (this.isWaveSensorAction || isEurPanel()) ? i : i + 4;
    }

    private boolean isEurPanel() {
        String str = this.productId;
        if (str != null) {
            return ProductRepository.isEurPanel(str) || ProductRepository.isAsPanel(this.productId);
        }
        return false;
    }
}