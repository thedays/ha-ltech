package com.ltech.smarthome.ui.device.curtain_motor;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.view.View;
import android.widget.SeekBar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import anetwork.channel.util.RequestConstant;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActBleCurtainMotorBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.ir.MotorKeyItem;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.IconRepository;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.bean.CurtainMotorState;
import com.smart.product_agreement.parser.IMotorParser;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActBleMotor extends BaseControlActivity<ActBleCurtainMotorBinding, ActBleMotorVM> implements ISelectAction {
    ValueAnimator animator;
    BaseQuickAdapter<MotorKeyItem, BaseViewHolder> keyAdapter;
    BaseQuickAdapter<MotorKeyItem, BaseViewHolder> modeAdapter;
    private int DEFAULT_PROGRESS = 50;
    List<MotorKeyItem> keyItemList = new ArrayList();
    List<MotorKeyItem> modeItemList = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_ble_curtain_motor;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$edit$7(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        CurtainMotorState generateCurtainMotorState;
        super.initView();
        setBackImage(R.mipmap.icon_back);
        ((ActBleCurtainMotorBinding) this.mViewBinding).ivDevice.setImageResource(R.mipmap.cgcurtain_pic);
        ((ActBleMotorVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActBleMotorVM) this.mViewModel).selectAction = getIntent().getBooleanExtra(Constants.SELECT_ACTION, false);
        ((ActBleMotorVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        this.keyItemList.add(CurtainRepository.getMotorKeyItem(CurtainRepository.BLE_MOTOR_KEY_NAME_UP));
        this.keyItemList.add(CurtainRepository.getMotorKeyItem(CurtainRepository.BLE_MOTOR_KEY_NAME_STOP));
        this.keyItemList.add(CurtainRepository.getMotorKeyItem(CurtainRepository.BLE_MOTOR_KEY_NAME_DOWN));
        List<MotorKeyItem> list = this.keyItemList;
        int i = R.layout.item_ir_key;
        BaseQuickAdapter<MotorKeyItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<MotorKeyItem, BaseViewHolder>(this, i, list) { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleMotor.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, MotorKeyItem item) {
                helper.setImageResource(R.id.iv_icon, item.getIconRes()).setText(R.id.tv_name, item.getName());
            }
        };
        this.keyAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleMotor$$ExternalSyntheticLambda6
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                ActBleMotor.this.lambda$initView$0(baseQuickAdapter2, view, i2);
            }
        });
        this.keyAdapter.bindToRecyclerView(((ActBleCurtainMotorBinding) this.mViewBinding).rvContent);
        ((ActBleCurtainMotorBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(this, 3));
        ((ActBleCurtainMotorBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActBleCurtainMotorBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
        this.modeItemList.add(CurtainRepository.getMotorKeyModeItem(CurtainRepository.BLE_MODE_WAKE_UP));
        this.modeItemList.add(CurtainRepository.getMotorKeyModeItem(CurtainRepository.BLE_MODE_SLEEP));
        this.modeItemList.add(CurtainRepository.getMotorKeyModeItem(CurtainRepository.BLE_MODE_GLOW));
        this.modeItemList.add(CurtainRepository.getMotorKeyModeItem(CurtainRepository.BLE_MODE_RECEIVE_VISITOR));
        Device deviceById = Injection.repo().device().getDeviceById(((ActBleMotorVM) this.mViewModel).controlId);
        if (deviceById != null && deviceById.getDeviceState() != null && deviceById.getDeviceState().getCurtainMotorState() == null && (generateCurtainMotorState = CurtainRepository.generateCurtainMotorState(((BleParam) deviceById.getParam(BleParam.class)).getUnicastAddress())) != null) {
            ((ActBleMotorVM) this.mViewModel).setCurtainState(generateCurtainMotorState);
            deviceById.getDeviceState().setCurtainMotorState(generateCurtainMotorState);
            Injection.repo().device().saveDevice(deviceById);
        }
        BaseQuickAdapter<MotorKeyItem, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<MotorKeyItem, BaseViewHolder>(this, i, this.modeItemList) { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleMotor.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, MotorKeyItem item) {
                helper.setImageResource(R.id.iv_icon, item.getIconRes()).setBackgroundRes(R.id.iv_icon, R.drawable.shape_item_40_white).setText(R.id.tv_name, item.getName());
            }
        };
        this.modeAdapter = baseQuickAdapter2;
        baseQuickAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleMotor$$ExternalSyntheticLambda7
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter3, View view, int i2) {
                ActBleMotor.this.lambda$initView$1(baseQuickAdapter3, view, i2);
            }
        });
        this.modeAdapter.bindToRecyclerView(((ActBleCurtainMotorBinding) this.mViewBinding).rvMode);
        ((ActBleCurtainMotorBinding) this.mViewBinding).rvMode.setLayoutManager(new GridLayoutManager(this, 4));
        ((ActBleCurtainMotorBinding) this.mViewBinding).rvMode.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActBleCurtainMotorBinding) this.mViewBinding).rvMode.getItemAnimator()).setSupportsChangeAnimations(false);
        initCurtainProgress(this.DEFAULT_PROGRESS);
        ((ActBleCurtainMotorBinding) this.mViewBinding).curtainLeft.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleMotor.3
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((ActBleCurtainMotorBinding) ActBleMotor.this.mViewBinding).curtainRight.setProgress(progress);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                LHomeLog.i(getClass(), "progress -->" + seekBar.getProgress());
                ((ActBleMotorVM) ActBleMotor.this.mViewModel).sendMotorPercentCmd(seekBar.getProgress());
            }
        });
        ((ActBleCurtainMotorBinding) this.mViewBinding).curtainRight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleMotor.4
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((ActBleCurtainMotorBinding) ActBleMotor.this.mViewBinding).curtainLeft.setProgress(progress);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                LHomeLog.i(getClass(), "progress -->" + seekBar.getProgress());
                ((ActBleMotorVM) ActBleMotor.this.mViewModel).sendMotorPercentCmd(seekBar.getProgress());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        if (i == 0) {
            setAnimation(((ActBleCurtainMotorBinding) this.mViewBinding).curtainLeft.getProgress(), this.DEFAULT_PROGRESS);
        }
        if (i != 1 && i == 2) {
            setAnimation(((ActBleCurtainMotorBinding) this.mViewBinding).curtainLeft.getProgress(), this.DEFAULT_PROGRESS + 100);
        }
        ((ActBleMotorVM) this.mViewModel).sendMotorCmd(this.keyAdapter.getData().get(i));
        ViewHelpUtil.zoomInZoomOut(view, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0076  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$initView$1(com.chad.library.adapter.base.BaseQuickAdapter r4, android.view.View r5, int r6) {
        /*
            r3 = this;
            VM extends com.ltech.smarthome.base.BaseViewModel r4 = r3.mViewModel
            com.ltech.smarthome.ui.device.curtain_motor.ActBleMotorVM r4 = (com.ltech.smarthome.ui.device.curtain_motor.ActBleMotorVM) r4
            androidx.lifecycle.MutableLiveData<com.smart.product_agreement.bean.CurtainMotorState> r4 = r4.stateMutableLiveData
            java.lang.Object r4 = r4.getValue()
            if (r4 == 0) goto L29
            VM extends com.ltech.smarthome.base.BaseViewModel r4 = r3.mViewModel
            com.ltech.smarthome.ui.device.curtain_motor.ActBleMotorVM r4 = (com.ltech.smarthome.ui.device.curtain_motor.ActBleMotorVM) r4
            androidx.lifecycle.MutableLiveData<com.smart.product_agreement.bean.CurtainMotorState> r4 = r4.stateMutableLiveData
            java.lang.Object r4 = r4.getValue()
            com.smart.product_agreement.bean.CurtainMotorState r4 = (com.smart.product_agreement.bean.CurtainMotorState) r4
            java.util.List r4 = r4.getModeInfoList()
            java.lang.Object r4 = r4.get(r6)
            com.smart.product_agreement.bean.CurtainMotorState$ModeInfo r4 = (com.smart.product_agreement.bean.CurtainMotorState.ModeInfo) r4
            int r4 = r4.getModePositionPercent()
        L26:
            int r4 = 100 - r4
            goto L55
        L29:
            VM extends com.ltech.smarthome.base.BaseViewModel r4 = r3.mViewModel
            com.ltech.smarthome.ui.device.curtain_motor.ActBleMotorVM r4 = (com.ltech.smarthome.ui.device.curtain_motor.ActBleMotorVM) r4
            boolean r4 = r4.groupControl
            if (r4 != 0) goto L54
            VM extends com.ltech.smarthome.base.BaseViewModel r4 = r3.mViewModel
            com.ltech.smarthome.ui.device.curtain_motor.ActBleMotorVM r4 = (com.ltech.smarthome.ui.device.curtain_motor.ActBleMotorVM) r4
            com.ltech.smarthome.base.SingleLiveEvent<java.lang.Object> r4 = r4.controlDevice
            java.lang.Object r4 = r4.getValue()
            com.ltech.smarthome.model.bean.Device r4 = (com.ltech.smarthome.model.bean.Device) r4
            com.ltech.smarthome.model.bean.DeviceState r4 = r4.getDeviceState()
            com.smart.product_agreement.bean.CurtainMotorState r4 = r4.getCurtainMotorState()
            java.util.List r4 = r4.getModeInfoList()
            java.lang.Object r4 = r4.get(r6)
            com.smart.product_agreement.bean.CurtainMotorState$ModeInfo r4 = (com.smart.product_agreement.bean.CurtainMotorState.ModeInfo) r4
            int r4 = r4.getModePositionPercent()
            goto L26
        L54:
            r4 = 0
        L55:
            java.lang.Class r0 = r3.getClass()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "position-->"
            r1.<init>(r2)
            r1.append(r6)
            java.lang.String r2 = " travelPercent -->"
            r1.append(r2)
            r1.append(r4)
            java.lang.String r1 = r1.toString()
            com.smart.message.utils.LHomeLog.i(r0, r1)
            android.animation.ValueAnimator r0 = r3.animator
            if (r0 == 0) goto L79
            r0.cancel()
        L79:
            V extends androidx.databinding.ViewDataBinding r0 = r3.mViewBinding
            com.ltech.smarthome.databinding.ActBleCurtainMotorBinding r0 = (com.ltech.smarthome.databinding.ActBleCurtainMotorBinding) r0
            com.ltech.smarthome.view.LightBrtBar r0 = r0.curtainLeft
            int r0 = r0.getProgress()
            int r1 = r3.DEFAULT_PROGRESS
            int r4 = 100 - r4
            int r1 = r1 + r4
            r3.setAnimation(r0, r1)
            VM extends com.ltech.smarthome.base.BaseViewModel r4 = r3.mViewModel
            com.ltech.smarthome.ui.device.curtain_motor.ActBleMotorVM r4 = (com.ltech.smarthome.ui.device.curtain_motor.ActBleMotorVM) r4
            com.chad.library.adapter.base.BaseQuickAdapter<com.ltech.smarthome.ui.device.ir.MotorKeyItem, com.chad.library.adapter.base.BaseViewHolder> r0 = r3.modeAdapter
            java.util.List r0 = r0.getData()
            java.lang.Object r6 = r0.get(r6)
            com.ltech.smarthome.ui.device.ir.MotorKeyItem r6 = (com.ltech.smarthome.ui.device.ir.MotorKeyItem) r6
            r4.sendMotorModeCmd(r6)
            r4 = 0
            com.ltech.smarthome.utils.ViewHelpUtil.zoomInZoomOut(r5, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.curtain_motor.ActBleMotor.lambda$initView$1(com.chad.library.adapter.base.BaseQuickAdapter, android.view.View, int):void");
    }

    private void initCurtainProgress(int progress) {
        ((ActBleCurtainMotorBinding) this.mViewBinding).curtainLeft.setMinProgressValue(progress);
        ((ActBleCurtainMotorBinding) this.mViewBinding).curtainRight.setMinProgressValue(progress);
        int i = progress + 100;
        ((ActBleCurtainMotorBinding) this.mViewBinding).curtainLeft.setMax(i);
        ((ActBleCurtainMotorBinding) this.mViewBinding).curtainRight.setMax(i);
        ((ActBleCurtainMotorBinding) this.mViewBinding).curtainLeft.setProgress(progress);
        ((ActBleCurtainMotorBinding) this.mViewBinding).curtainRight.setProgress(progress);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        if (((ActBleMotorVM) this.mViewModel).selectAction) {
            ((ActBleMotorVM) this.mViewModel).controlDevice.setValue((Device) SceneHelper.instance().controlObject);
            ((ActBleMotorVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleMotor$$ExternalSyntheticLambda2
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActBleMotor.this.lambda$startObserve$2(obj);
                }
            });
        } else if (((ActBleMotorVM) this.mViewModel).controlId != -1) {
            ((ActBleMotorVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleMotor$$ExternalSyntheticLambda3
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActBleMotor.this.lambda$startObserve$5(obj);
                }
            });
        }
        ((ActBleMotorVM) this.mViewModel).stateMutableLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleMotor$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActBleMotor.this.lambda$startObserve$6((CurtainMotorState) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Object obj) {
        if (obj instanceof Device) {
            setTitle(((Device) obj).getDeviceName());
            setEditString(getString(R.string.save));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Object obj) {
        if (obj instanceof Device) {
            final Device device = (Device) obj;
            setTitle(device.getDeviceName());
            setEditImage(R.mipmap.ic_setting);
            if (device.getExtParam() != null) {
                CurtainMotorInfoExtParam curtainMotorInfoExtParam = new CurtainMotorInfoExtParam();
                curtainMotorInfoExtParam.fillMapWithString(device.getExtParam());
                for (int i = 0; i < 4; i++) {
                    if (curtainMotorInfoExtParam.getModeInfo(i) != null) {
                        this.modeItemList.get(i).setName(curtainMotorInfoExtParam.getModeInfo(i));
                    }
                    if (device.getDeviceState().getCurtainMotorState().getModeInfoList().get(i) != null) {
                        this.modeItemList.get(i).setIconRes(IconRepository.getSceneIcons(this)[device.getDeviceState().getCurtainMotorState().getModeInfoList().get(i).getModeIconNum()]);
                        this.modeItemList.get(i).setIconIndex(device.getDeviceState().getCurtainMotorState().getModeInfoList().get(i).getModeIconNum());
                    }
                    this.modeAdapter.notifyItemChanged(i);
                }
                CurtainRepository.setModeList(this.modeItemList);
                if (curtainMotorInfoExtParam.getOpenType() == 1) {
                    ((ActBleCurtainMotorBinding) this.mViewBinding).curtainRight.setVisibility(8);
                    this.DEFAULT_PROGRESS = 30;
                } else {
                    ((ActBleCurtainMotorBinding) this.mViewBinding).curtainRight.setVisibility(0);
                    this.DEFAULT_PROGRESS = 50;
                }
            } else {
                CurtainRepository.setModeList(this.modeItemList);
                ((ActBleCurtainMotorBinding) this.mViewBinding).curtainRight.setVisibility(0);
                this.DEFAULT_PROGRESS = 50;
            }
            initCurtainProgress(this.DEFAULT_PROGRESS);
            ((ActBleMotorVM) this.mViewModel).queryBleMotor(device);
            MessageManager.getInstance().setCurtainBleStatusCallBack(new MessageManager.CurtainBleStatusCallBack() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleMotor$$ExternalSyntheticLambda0
                @Override // com.smart.message.MessageManager.CurtainBleStatusCallBack
                public final void onDataReceive(ResponseMsg responseMsg) {
                    ActBleMotor.this.lambda$startObserve$3(device, responseMsg);
                }
            });
            return;
        }
        if (obj instanceof Group) {
            final Group group = (Group) obj;
            setTitle(group.getGroupName());
            setEditImage(R.mipmap.ic_setting);
            if (group.getDeviceIds() != null && !group.getDeviceIds().isEmpty()) {
                if (group.getLeaderSup() == 1) {
                    ((ActBleMotorVM) this.mViewModel).queryBleMotor(group);
                } else {
                    ((ActBleMotorVM) this.mViewModel).queryBleMotor(Injection.repo().device().getDeviceByDeviceId(group.getDeviceIds().get(0).longValue()));
                }
            }
            if (group.getExtParam() != null) {
                CurtainMotorInfoExtParam curtainMotorInfoExtParam2 = new CurtainMotorInfoExtParam();
                curtainMotorInfoExtParam2.fillMapWithString(group.getExtParam());
                for (int i2 = 0; i2 < 4; i2++) {
                    if (curtainMotorInfoExtParam2.getModeInfo(i2) != null) {
                        this.modeItemList.get(i2).setName(curtainMotorInfoExtParam2.getModeInfo(i2));
                    }
                    if (group.getDeviceState().getCurtainMotorState() != null && group.getDeviceState().getCurtainMotorState().getModeInfoList() != null && group.getDeviceState().getCurtainMotorState().getModeInfoList().get(i2) != null) {
                        this.modeItemList.get(i2).setIconRes(IconRepository.getSceneIcons(this)[group.getDeviceState().getCurtainMotorState().getModeInfoList().get(i2).getModeIconNum()]);
                        this.modeItemList.get(i2).setIconIndex(group.getDeviceState().getCurtainMotorState().getModeInfoList().get(i2).getModeIconNum());
                    }
                    this.modeAdapter.notifyItemChanged(i2);
                }
                CurtainRepository.setModeList(this.modeItemList);
                if (curtainMotorInfoExtParam2.getOpenType() == 1) {
                    ((ActBleCurtainMotorBinding) this.mViewBinding).curtainRight.setVisibility(8);
                    this.DEFAULT_PROGRESS = 30;
                } else {
                    ((ActBleCurtainMotorBinding) this.mViewBinding).curtainRight.setVisibility(0);
                    this.DEFAULT_PROGRESS = 50;
                }
                initCurtainProgress(this.DEFAULT_PROGRESS);
            }
            MessageManager.getInstance().setCurtainBleStatusCallBack(new MessageManager.CurtainBleStatusCallBack() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleMotor$$ExternalSyntheticLambda1
                @Override // com.smart.message.MessageManager.CurtainBleStatusCallBack
                public final void onDataReceive(ResponseMsg responseMsg) {
                    ActBleMotor.this.lambda$startObserve$4(group, responseMsg);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Device device, ResponseMsg responseMsg) {
        BleParam bleParam = (BleParam) device.getParam(BleParam.class);
        CurtainMotorState parserCurtainMotorState = ((IMotorParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserCurtainMotorState(responseMsg.getAgreementId(), bleParam != null ? bleParam.getUnicastAddress() : 0, responseMsg.getResData());
        if (parserCurtainMotorState != null) {
            ((ActBleMotorVM) this.mViewModel).setCurtainState(parserCurtainMotorState);
            device.getDeviceState().setCurtainMotorState(parserCurtainMotorState);
            Injection.repo().device().saveDevice(device);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Group group, ResponseMsg responseMsg) {
        if (group.getDeviceIds() == null || group.getDeviceIds().isEmpty()) {
            return;
        }
        if (group.getLeaderSup() == 1) {
            CurtainMotorState parserCurtainMotorState = ((IMotorParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserCurtainMotorState(responseMsg.getAgreementId(), group.getGroupAddress(), responseMsg.getResData());
            if (parserCurtainMotorState != null) {
                ((ActBleMotorVM) this.mViewModel).setCurtainState(parserCurtainMotorState);
                return;
            }
            return;
        }
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(group.getDeviceIds().get(0).longValue());
        if (deviceByDeviceId != null) {
            BleParam bleParam = (BleParam) deviceByDeviceId.getParam(BleParam.class);
            CurtainMotorState parserCurtainMotorState2 = ((IMotorParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserCurtainMotorState(responseMsg.getAgreementId(), bleParam != null ? bleParam.getUnicastAddress() : 0, responseMsg.getResData());
            if (parserCurtainMotorState2 != null) {
                ((ActBleMotorVM) this.mViewModel).setCurtainState(parserCurtainMotorState2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(CurtainMotorState curtainMotorState) {
        ((ActBleCurtainMotorBinding) this.mViewBinding).curtainLeft.setProgress(this.DEFAULT_PROGRESS + (100 - curtainMotorState.getTravelPercent()));
        ((ActBleCurtainMotorBinding) this.mViewBinding).curtainRight.setProgress(this.DEFAULT_PROGRESS + (100 - curtainMotorState.getTravelPercent()));
    }

    private void startObjectObserve() {
        if (((ActBleMotorVM) this.mViewModel).groupControl) {
            Group groupById = Injection.repo().group().getGroupById(((ActBleMotorVM) this.mViewModel).controlId);
            if (groupById != null) {
                ((ActBleMotorVM) this.mViewModel).controlDevice.setValue(groupById);
            }
            ((ActBleCurtainMotorBinding) this.mViewBinding).bottomLayout.setVisibility(8);
            return;
        }
        Device deviceById = Injection.repo().device().getDeviceById(((ActBleMotorVM) this.mViewModel).controlId);
        if (deviceById != null) {
            ((ActBleMotorVM) this.mViewModel).controlDevice.setValue(deviceById);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        startObjectObserve();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        super.back();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        super.onBackPressed();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (((ActBleMotorVM) this.mViewModel).selectAction) {
            if (((ActBleMotorVM) this.mViewModel).mMotorCmdParam == null) {
                SmartToast.showShort(R.string.please_choose);
                return;
            }
            ((ActBleMotorVM) this.mViewModel).mMotorCmdParam.addExtParam(SceneHelper.OPTION_VALUE, ((ActBleMotorVM) this.mViewModel).cmdName);
            ((ActBleMotorVM) this.mViewModel).mMotorCmdParam.addExtParam(SceneHelper.OPTION, "0");
            SceneHelper.instance().cmdParam = ((ActBleMotorVM) this.mViewModel).mMotorCmdParam;
            SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleMotor$$ExternalSyntheticLambda5
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActBleMotor.this.lambda$edit$7((Boolean) obj);
                }
            });
            return;
        }
        NavHelper.goSetting(((ActBleMotorVM) this.mViewModel).controlDevice.getValue());
    }

    private void setAnimation(final int startProgress, final int endProgress) {
        LHomeLog.i(RequestConstant.ENV_TEST, getClass(), "startprogress-->" + startProgress + "  endprogress-->" + endProgress);
        ValueAnimator duration = ValueAnimator.ofInt(startProgress, endProgress).setDuration((long) ((Math.abs(endProgress - startProgress) * 6500) / 100));
        this.animator = duration;
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.ltech.smarthome.ui.device.curtain_motor.ActBleMotor.5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                LHomeLog.i(RequestConstant.ENV_TEST, getClass(), valueAnimator.getAnimatedValue() + "");
                if (ActBleMotor.this.mViewBinding != null) {
                    ((ActBleCurtainMotorBinding) ActBleMotor.this.mViewBinding).curtainLeft.setProgress(((Integer) valueAnimator.getAnimatedValue()).intValue());
                    ((ActBleCurtainMotorBinding) ActBleMotor.this.mViewBinding).curtainRight.setProgress(((Integer) valueAnimator.getAnimatedValue()).intValue());
                }
            }
        });
        this.animator.start();
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseControlActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 3005) {
            ((ActBleMotorVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDeviceById(((ActBleMotorVM) this.mViewModel).controlId));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}