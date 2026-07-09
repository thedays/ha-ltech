package com.ltech.smarthome.ui.device.microwave_sensor;

import android.view.View;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSelectBleCurtainActionBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.ui.device.curtain_motor.CurtainMotorInfoExtParam;
import com.ltech.smarthome.ui.device.curtain_motor.CurtainRepository;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.LightBrtBar;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.param.DeviceCmdParam;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActWaveSensorSelectBleCurtainAction extends BaseNormalActivity<ActSelectBleCurtainActionBinding> implements ISelectAction {
    private long controlId;
    private List<CurtainAction> dataList;
    private Device device;
    private boolean groupControl;
    private MultipleItemRvAdapter<CurtainAction, BaseViewHolder> mAdapter;
    private int progress;
    protected boolean[] selectArray;
    private int selectPosition = -1;
    private int lastSelectPosition = -1;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_ble_curtain_action;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$edit$2(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (this.selectPosition == -1) {
            SmartToast.showShort(R.string.please_choose);
            return;
        }
        WaveSensorHelper.instance().setSensorRelateBleParam();
        if (WaveSensorHelper.instance().isChangeAll) {
            WaveSensorHelper.instance().setDataType = 2;
            WaveSensorHelper.instance().setSensorRelateBleParam();
            WaveSensorHelper.instance().setDataType = 3;
            selectCurtainClose(this.selectPosition);
            WaveSensorHelper.instance().setSensorRelateBleParam();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_action));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        this.controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        boolean booleanExtra = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        this.groupControl = booleanExtra;
        if (booleanExtra) {
            Group groupById = Injection.repo().group().getGroupById(this.controlId);
            if (groupById.getDeviceIds() != null && groupById.getDeviceIds().size() > 0) {
                Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(groupById.getDeviceIds().get(0).longValue());
                this.device = deviceByDeviceId;
                if (deviceByDeviceId != null && deviceByDeviceId.getDeviceState().getCurtainMotorState() != null) {
                    this.progress = this.device.getDeviceState().getCurtainMotorState().getTravelPercent();
                }
            }
        } else {
            Device deviceById = Injection.repo().device().getDeviceById(this.controlId);
            this.device = deviceById;
            if (deviceById.getDeviceState().getCurtainMotorState() != null) {
                this.progress = this.device.getDeviceState().getCurtainMotorState().getTravelPercent();
            }
        }
        initRv();
    }

    private void initRv() {
        List<CurtainAction> contentList = getContentList();
        this.dataList = contentList;
        this.selectArray = new boolean[contentList.size()];
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.dataList);
        this.mAdapter = anonymousClass1;
        anonymousClass1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSelectBleCurtainAction$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActWaveSensorSelectBleCurtainAction.this.lambda$initRv$0(baseQuickAdapter, view, i);
            }
        });
        ((ActSelectBleCurtainActionBinding) this.mViewBinding).rvContent.addOnItemTouchListener(new OnItemChildClickListener(this) { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSelectBleCurtainAction.2
            @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
        this.mAdapter.finishInitialize();
        this.mAdapter.bindToRecyclerView(((ActSelectBleCurtainActionBinding) this.mViewBinding).rvContent);
        ((ActSelectBleCurtainActionBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectBleCurtainActionBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActSelectBleCurtainActionBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(true);
    }

    /* renamed from: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSelectBleCurtainAction$1, reason: invalid class name */
    class AnonymousClass1 extends MultipleItemRvAdapter<CurtainAction, BaseViewHolder> {
        AnonymousClass1(List data) {
            super(data);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
        public int getViewType(CurtainAction CurtainAction) {
            return CurtainAction.getType();
        }

        @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
        public void registerItemProvider() {
            this.mProviderDelegate.registerProvider(new BaseItemProvider<CurtainAction, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSelectBleCurtainAction.1.1
                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int layout() {
                    return R.layout.item_select;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int viewType() {
                    return CurtainAction.TYPE_DEFAULT;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void convert(BaseViewHolder helper, CurtainAction data, int position) {
                    ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                    if (helper.getAdapterPosition() == ActWaveSensorSelectBleCurtainAction.this.selectPosition) {
                        helper.setImageResource(R.id.iv_select, R.mipmap.ic_tick_sel).setGone(R.id.iv_select, true);
                    } else {
                        helper.setGone(R.id.iv_select, false);
                    }
                    helper.setText(R.id.tv_name, data.getTitleName());
                }
            });
            this.mProviderDelegate.registerProvider(new BaseItemProvider<CurtainAction, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSelectBleCurtainAction.1.2
                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int layout() {
                    return R.layout.item_select_diy;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int viewType() {
                    return CurtainAction.TYPE_DIY;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void convert(final BaseViewHolder helper, final CurtainAction data, final int position) {
                    helper.setText(R.id.tv_name, data.getTitleName());
                    ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                    if (helper.getAdapterPosition() == ActWaveSensorSelectBleCurtainAction.this.selectPosition) {
                        helper.setImageResource(R.id.iv_select, R.mipmap.ic_tick_sel).setGone(R.id.iv_select, true);
                        helper.setGone(R.id.layout_change_curtain_open_percent, true);
                        ((LightBrtBar) helper.getView(R.id.sb_brt)).setIncludeZero(true);
                        ((LightBrtBar) helper.getView(R.id.sb_brt)).setProgress(ActWaveSensorSelectBleCurtainAction.this.progress);
                        if (ActWaveSensorSelectBleCurtainAction.this.progress == 0) {
                            helper.setText(R.id.tv_brt, ActWaveSensorSelectBleCurtainAction.this.getString(R.string.app_str_all_close));
                        } else if (ActWaveSensorSelectBleCurtainAction.this.progress == 100) {
                            helper.setText(R.id.tv_brt, ActWaveSensorSelectBleCurtainAction.this.getString(R.string.app_str_all_open));
                        } else {
                            helper.setText(R.id.tv_brt, ActWaveSensorSelectBleCurtainAction.this.progress + "%");
                        }
                        data.setCurtainPercent(ActWaveSensorSelectBleCurtainAction.this.progress);
                        ((LightBrtBar) helper.getView(R.id.sb_brt)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSelectBleCurtainAction.1.2.1
                            @Override // android.widget.SeekBar.OnSeekBarChangeListener
                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }

                            @Override // android.widget.SeekBar.OnSeekBarChangeListener
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                LHomeLog.i(getClass(), "progress-->" + progress);
                                helper.setText(R.id.tv_brt, ((LightBrtBar) seekBar).getProgress() + "%");
                            }

                            @Override // android.widget.SeekBar.OnSeekBarChangeListener
                            public void onStopTrackingTouch(SeekBar seekBar) {
                                Class<?> cls = getClass();
                                StringBuilder sb = new StringBuilder("progress-->");
                                LightBrtBar lightBrtBar = (LightBrtBar) seekBar;
                                sb.append(lightBrtBar.getProgress());
                                LHomeLog.i(cls, sb.toString());
                                if (lightBrtBar.getProgress() == 0) {
                                    helper.setText(R.id.tv_brt, ActWaveSensorSelectBleCurtainAction.this.getString(R.string.app_str_all_close));
                                } else if (lightBrtBar.getProgress() == 100) {
                                    helper.setText(R.id.tv_brt, ActWaveSensorSelectBleCurtainAction.this.getString(R.string.app_str_all_open));
                                } else {
                                    helper.setText(R.id.tv_brt, lightBrtBar.getProgress() + "%");
                                }
                                data.setCurtainPercent(lightBrtBar.getProgress());
                                ActWaveSensorSelectBleCurtainAction.this.selectCurtainUserDefied(position);
                            }
                        });
                    } else {
                        helper.setGone(R.id.iv_select, false).setGone(R.id.iv_select, false);
                        helper.setGone(R.id.layout_change_curtain_open_percent, false);
                    }
                    helper.setText(R.id.tv_name, data.getTitleName());
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int i2 = this.selectPosition;
        if (i2 != i) {
            this.lastSelectPosition = i2;
            this.selectPosition = i;
            if (i != -1) {
                baseQuickAdapter.notifyItemChanged(i);
            }
            int i3 = this.lastSelectPosition;
            if (i3 != -1) {
                baseQuickAdapter.notifyItemChanged(i3);
            }
        }
        baseQuickAdapter.notifyDataSetChanged();
        onItemClick(baseQuickAdapter, i);
    }

    private void onItemClick(BaseQuickAdapter<CurtainAction, BaseViewHolder> adapter, int position) {
        switch (position) {
            case 0:
                selectCurtainOpen(position);
                break;
            case 1:
                selectCurtainClose(position);
                break;
            case 2:
                selectCurtainStop(position);
                break;
            case 3:
                if (!this.groupControl) {
                    selectCurtainWakeUp(position);
                    break;
                } else {
                    selectCurtainUserDefied(position);
                    break;
                }
            case 4:
                selectCurtainSleep(position);
                break;
            case 5:
                selectCurtainGlow(position);
                break;
            case 6:
                selectCurtainVisitor(position);
                break;
            case 7:
                selectCurtainUserDefied(position);
                break;
        }
    }

    private List<CurtainAction> getContentList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new CurtainAction(CurtainAction.TYPE_DEFAULT, CurtainRepository.getMotorKeyItem(CurtainRepository.BLE_MOTOR_KEY_NAME_UP).getName()));
        arrayList.add(new CurtainAction(CurtainAction.TYPE_DEFAULT, CurtainRepository.getMotorKeyItem(CurtainRepository.BLE_MOTOR_KEY_NAME_DOWN).getName()));
        arrayList.add(new CurtainAction(CurtainAction.TYPE_DEFAULT, CurtainRepository.getMotorKeyItem(CurtainRepository.BLE_MOTOR_KEY_NAME_STOP).getName()));
        if (!this.groupControl) {
            arrayList.add(new CurtainAction(CurtainAction.TYPE_DEFAULT, CurtainRepository.getMotorKeyModeItem(CurtainRepository.BLE_MODE_WAKE_UP).getName()));
            arrayList.add(new CurtainAction(CurtainAction.TYPE_DEFAULT, CurtainRepository.getMotorKeyModeItem(CurtainRepository.BLE_MODE_SLEEP).getName()));
            arrayList.add(new CurtainAction(CurtainAction.TYPE_DEFAULT, CurtainRepository.getMotorKeyModeItem(CurtainRepository.BLE_MODE_GLOW).getName()));
            arrayList.add(new CurtainAction(CurtainAction.TYPE_DEFAULT, CurtainRepository.getMotorKeyModeItem(CurtainRepository.BLE_MODE_RECEIVE_VISITOR).getName()));
        }
        arrayList.add(new CurtainAction(CurtainAction.TYPE_DIY, getString(R.string.app_str_curtain_locatedin), this.progress));
        Device device = this.device;
        if (device != null && device.getExtParam() != null) {
            CurtainMotorInfoExtParam curtainMotorInfoExtParam = new CurtainMotorInfoExtParam();
            curtainMotorInfoExtParam.fillMapWithString(this.device.getExtParam());
            for (int i = 0; i < 4; i++) {
                if (curtainMotorInfoExtParam.getModeInfo(i) != null) {
                    ((CurtainAction) arrayList.get(i + 3)).setTitleName(curtainMotorInfoExtParam.getModeInfo(i));
                }
            }
        }
        return arrayList;
    }

    public void selectCurtainOpen(int position) {
        DeviceCmdParam deviceCmdParam = new DeviceCmdParam();
        deviceCmdParam.setCmdType(1);
        deviceCmdParam.addExtParam(SceneHelper.OPTION, "1");
        WaveSensorHelper.instance().cmdParam = deviceCmdParam;
    }

    public void selectCurtainClose(int position) {
        DeviceCmdParam deviceCmdParam = new DeviceCmdParam();
        deviceCmdParam.setCmdType(4);
        deviceCmdParam.addExtParam(SceneHelper.OPTION, "2");
        WaveSensorHelper.instance().cmdParam = deviceCmdParam;
    }

    public void selectCurtainStop(int position) {
        DeviceCmdParam deviceCmdParam = new DeviceCmdParam();
        deviceCmdParam.setCmdType(3);
        deviceCmdParam.addExtParam(SceneHelper.OPTION, "3");
        WaveSensorHelper.instance().cmdParam = deviceCmdParam;
    }

    public void selectCurtainWakeUp(int position) {
        DeviceCmdParam deviceCmdParam = new DeviceCmdParam();
        deviceCmdParam.setCmdType(7);
        deviceCmdParam.setCurtainModeType(1);
        deviceCmdParam.addExtParam(SceneHelper.OPTION, "4");
        WaveSensorHelper.instance().cmdParam = deviceCmdParam;
    }

    public void selectCurtainSleep(int position) {
        DeviceCmdParam deviceCmdParam = new DeviceCmdParam();
        deviceCmdParam.setCmdType(7);
        deviceCmdParam.setCurtainModeType(2);
        deviceCmdParam.addExtParam(SceneHelper.OPTION, "5");
        WaveSensorHelper.instance().cmdParam = deviceCmdParam;
    }

    public void selectCurtainGlow(int position) {
        DeviceCmdParam deviceCmdParam = new DeviceCmdParam();
        deviceCmdParam.setCmdType(7);
        deviceCmdParam.setCurtainModeType(3);
        deviceCmdParam.addExtParam(SceneHelper.OPTION, "6");
        WaveSensorHelper.instance().cmdParam = deviceCmdParam;
    }

    public void selectCurtainVisitor(int position) {
        DeviceCmdParam deviceCmdParam = new DeviceCmdParam();
        deviceCmdParam.setCmdType(7);
        deviceCmdParam.setCurtainModeType(4);
        deviceCmdParam.addExtParam(SceneHelper.OPTION, "7");
        WaveSensorHelper.instance().cmdParam = deviceCmdParam;
    }

    public void selectCurtainUserDefied(int position) {
        DeviceCmdParam deviceCmdParam = new DeviceCmdParam();
        deviceCmdParam.setCmdType(6);
        deviceCmdParam.setCurtainProgress(this.mAdapter.getData().get(position).getCurtainPercent());
        deviceCmdParam.addExtParam(SceneHelper.OPTION, "8");
        deviceCmdParam.addExtParam(SceneHelper.OPTION_VALUE, Integer.valueOf(this.mAdapter.getData().get(position).getCurtainPercent()));
        WaveSensorHelper.instance().cmdParam = deviceCmdParam;
    }

    public static final class CurtainAction {
        private static int TYPE_DEFAULT = 1;
        private static int TYPE_DIY = 2;
        private int curtainPercent;
        private String name;
        private String subName;
        private String titleName;
        private int type;

        CurtainAction(int type, String titleName) {
            this.type = type;
            this.titleName = titleName;
        }

        CurtainAction(int type, String titleName, String name, String subName) {
            this.type = type;
            this.titleName = titleName;
            this.name = name;
            this.subName = subName;
        }

        CurtainAction(int type, String titleName, int curtainPercent) {
            this.type = type;
            this.titleName = titleName;
            this.curtainPercent = curtainPercent;
        }

        public int getCurtainPercent() {
            return this.curtainPercent;
        }

        public void setCurtainPercent(int curtainPercent) {
            this.curtainPercent = curtainPercent;
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