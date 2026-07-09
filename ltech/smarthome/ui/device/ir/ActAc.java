package com.ltech.smarthome.ui.device.ir;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.hzy.tvmao.ir.ac.ACStateV2;
import com.ltech.nfc.utils.DataUtil;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActAcBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.device.ir.ActAc;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.CircleBar;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class ActAc extends BaseIrVMActivity<ActAcBinding, ActAcVM> {
    public static final int REPORT_STATE = 1;
    private ReportHandler handler;
    private long lastReportTime;
    private BaseQuickAdapter<IrKeyItem, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.ui.device.ir.BaseIrVMActivity
    protected int deviceType() {
        return 5;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_ac;
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseIrVMActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        BaseQuickAdapter<IrKeyItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<IrKeyItem, BaseViewHolder>(this, R.layout.item_ir_key, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.ir.ActAc.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, IrKeyItem item) {
                helper.setImageResource(R.id.iv_icon, item.getIconRes()).setText(R.id.tv_name, item.getName());
                helper.setEnabled(R.id.tv_name, item.isEnable());
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.ir.ActAc$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActAc.this.lambda$initView$0(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActAcBinding) this.mViewBinding).rvContent);
        ((ActAcBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(this, 4));
        ((ActAcBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((ActAcBinding) this.mViewBinding).circleBar.setProgressChangeListener(new CircleBar.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.ir.ActAc$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.CircleBar.OnProgressChangeListener
            public final void onProgressChanged(float f, int i, boolean z) {
                ActAc.this.lambda$initView$1(f, i, z);
            }
        });
        this.handler = new ReportHandler();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (this.mAdapter.getData().get(i).isEnable()) {
            if (i == 0) {
                ((ActAcVM) this.mViewModel).changeMode();
            } else if (i == 1) {
                ((ActAcVM) this.mViewModel).changeWind();
            } else if (i == 2) {
                ((ActAcVM) this.mViewModel).changeDirect(true);
            } else {
                ((ActAcVM) this.mViewModel).changeDirect(false);
            }
            ((ActAcVM) this.mViewModel).setSendState();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(float f, int i, boolean z) {
        ((ActAcBinding) this.mViewBinding).tvTemp.setText(String.valueOf(i));
        if (z) {
            ((ActAcVM) this.mViewModel).changeTemperature(i);
        }
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseIrVMActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActAcVM) this.mViewModel).currentState.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.ActAc$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAc.this.lambda$startObserve$2((ACStateV2) obj);
            }
        });
        ((ActAcVM) this.mViewModel).sendStateLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.ActAc$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAc.this.lambda$startObserve$3((Boolean) obj);
            }
        });
        ((ActAcVM) this.mViewModel).reportState.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.ActAc$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAc.this.lambda$startObserve$4((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(ACStateV2 aCStateV2) {
        String str;
        if (1 == aCStateV2.getCurPowerState()) {
            ((ActAcVM) this.mViewModel).powerOn.setValue(false);
            ((ActAcVM) this.mViewModel).tempControl.setValue(false);
            ((ActAcBinding) this.mViewBinding).rvContent.setAlpha(0.5f);
        } else {
            ((ActAcBinding) this.mViewBinding).rvContent.setAlpha(1.0f);
            ((ActAcVM) this.mViewModel).powerOn.setValue(true);
            ((ActAcVM) this.mViewModel).tempControl.setValue(Boolean.valueOf(aCStateV2.tempIsCanControl()));
            AppCompatTextView appCompatTextView = ((ActAcBinding) this.mViewBinding).tvTemp;
            if (aCStateV2.tempIsCanControl()) {
                str = aCStateV2.getCurTemp() + "";
            } else {
                str = "NA";
            }
            appCompatTextView.setText(str);
            if (aCStateV2.tempIsCanControl()) {
                if (1 == aCStateV2.getCurModelType()) {
                    ((ActAcBinding) this.mViewBinding).circleBar.setBarColor(ContextCompat.getColor(this, R.color.bar_blue_1), ContextCompat.getColor(this, R.color.bar_red));
                } else {
                    ((ActAcBinding) this.mViewBinding).circleBar.setBarColor(ContextCompat.getColor(this, R.color.bar_green), ContextCompat.getColor(this, R.color.bar_blue));
                }
                ((ActAcBinding) this.mViewBinding).circleBar.setMax(aCStateV2.modelMaxTemp());
                ((ActAcBinding) this.mViewBinding).circleBar.setMin(aCStateV2.modelMinTemp());
                ((ActAcBinding) this.mViewBinding).circleBar.setProgress(aCStateV2.getCurTemp());
            }
            ((ActAcBinding) this.mViewBinding).tvState.setText(((ActAcVM) this.mViewModel).getStateString());
        }
        this.mAdapter.setNewData(((ActAcVM) this.mViewModel).getKeyItemList());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Boolean bool) {
        if (bool.booleanValue()) {
            ((ActAcBinding) this.mViewBinding).title.ivTip.setBackgroundResource(R.drawable.shape_circle_red);
        } else {
            ((ActAcBinding) this.mViewBinding).title.ivTip.setBackgroundResource(R.drawable.shape_circle_gray);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r6) {
        if (getUnicastAddress() != 0) {
            if (System.currentTimeMillis() - this.lastReportTime > 3000) {
                this.handler.sendEmptyMessage(1);
                return;
            }
            if (this.handler.hasMessages(1)) {
                this.handler.removeMessages(1);
            }
            this.handler.sendEmptyMessageDelayed(1, Math.max(System.currentTimeMillis() - this.lastReportTime, 1000L));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ReportHandler extends Handler {
        private ReportHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                ActAc.this.lastReportTime = System.currentTimeMillis();
                final String reportString = ActAc.this.getReportString();
                if (((ActAcVM) ActAc.this.mViewModel).controlDevice.getValue().getDeviceId() > 0) {
                    ((ObservableSubscribeProxy) Injection.net().updateReportInstruct(((ActAcVM) ActAc.this.mViewModel).controlDevice.getValue().getDeviceId(), reportString).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(ActAc.this.activity, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.ir.ActAc$ReportHandler$$ExternalSyntheticLambda0
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) {
                            ActAc.ReportHandler.this.lambda$handleMessage$0(reportString, obj);
                        }
                    }, new SmartErrorComsumer());
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$0(String str, Object obj) throws Exception {
            ((ActAcVM) ActAc.this.mViewModel).controlDevice.getValue().setReportinstruct(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getReportString() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(193);
        arrayList.add(0);
        arrayList.add(1);
        int unicastAddress = getUnicastAddress();
        arrayList.add(Integer.valueOf((unicastAddress >> 8) & 255));
        arrayList.add(Integer.valueOf(unicastAddress & 255));
        arrayList.add(205);
        arrayList.add(1);
        ACStateV2 value = ((ActAcVM) this.mViewModel).currentState.getValue();
        arrayList.add(Integer.valueOf(value.getCurPowerState()));
        arrayList.add(Integer.valueOf(value.getCurTemp()));
        arrayList.add(Integer.valueOf(value.getCurModelType()));
        arrayList.add(Integer.valueOf(value.getCurWindSpeed()));
        arrayList.add(Integer.valueOf(value.getCurUDDirect()));
        return DataUtil.formatHexString(arrayList);
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseIrVMActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (((ActAcVM) this.mViewModel).selectAction) {
            if (((ActAcVM) this.mViewModel).mIrCmdParam == null) {
                SmartToast.showShort(R.string.please_choose);
                return;
            }
            ArrayList arrayList = new ArrayList();
            ACStateV2 value = ((ActAcVM) this.mViewModel).currentState.getValue();
            arrayList.add(Integer.valueOf(value.getCurModelType()));
            arrayList.add(Integer.valueOf(value.getCurPowerState()));
            arrayList.add(Integer.valueOf(value.getCurTemp()));
            arrayList.add(Integer.valueOf(value.getCurWindSpeed()));
            arrayList.add(Integer.valueOf(value.getCurUDDirect()));
            ((ActAcVM) this.mViewModel).mIrCmdParam.addExtParam(SceneHelper.SCENE_PARAM_EXT, GsonUtils.toJson(arrayList));
            ((ActAcVM) this.mViewModel).mIrCmdParam.addExtParam(SceneHelper.SCENE_REPORT_INSTRUCT, getReportString());
        }
        super.edit();
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseIrVMActivity
    protected void getRcCodeSuccess(int ridPos) {
        ((ActAcBinding) this.mViewBinding).cardviewAdd.setVisibility(0);
        ((ActAcBinding) this.mViewBinding).tvIndex.setText(String.format(Locale.US, "%d/%d", Integer.valueOf(ridPos + 1), Integer.valueOf(((ActAcVM) this.mViewModel).rids.size())));
        ((ActAcVM) this.mViewModel).initAcState("");
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseIrVMActivity
    protected void initControlView() {
        setEditImage(R.mipmap.ic_setting);
        Device value = ((ActAcVM) this.mViewModel).controlDevice.getValue();
        if (value.getReportinstruct().isEmpty()) {
            ((ActAcVM) this.mViewModel).initAcState(value.getDeviceState().getAcState());
        } else {
            ((ActAcVM) this.mViewModel).initAcState("");
            refreshData(value.getDeviceId(), value.getReportinstruct());
        }
    }

    @Override // com.ltech.smarthome.ui.device.ir.BaseIrVMActivity
    protected void initSelectActionView() {
        setEditString(getString(R.string.save));
        ((ActAcVM) this.mViewModel).initAcState(((ActAcVM) this.mViewModel).controlDevice.getValue().getDeviceState().getAcState());
        String stringExtra = getIntent().getStringExtra(SceneHelper.SCENE_PARAM_EXT);
        if (TextUtils.isEmpty(stringExtra)) {
            return;
        }
        List list = (List) GsonUtils.fromJson(stringExtra, new TypeToken<List<Integer>>(this) { // from class: com.ltech.smarthome.ui.device.ir.ActAc.2
        }.getType());
        setAcManagerData(((Integer) list.get(1)).intValue(), ((Integer) list.get(0)).intValue(), ((Integer) list.get(2)).intValue(), ((Integer) list.get(3)).intValue(), ((Integer) list.get(4)).intValue());
    }

    public void refreshData(long deviceId, String reportInstruct) {
        if (!TextUtils.isEmpty(reportInstruct) && deviceId == ((ActAcVM) this.mViewModel).controlDevice.getValue().getDeviceId() && reportInstruct.length() >= 24) {
            setAcManagerData(Integer.parseInt(reportInstruct.substring(14, 16), 16), Integer.parseInt(reportInstruct.substring(18, 20), 16), Integer.parseInt(reportInstruct.substring(16, 18), 16), Integer.parseInt(reportInstruct.substring(20, 22), 16), Integer.parseInt(reportInstruct.substring(22, 24), 16));
            ((ActAcVM) this.mViewModel).controlDevice.getValue().setReportinstruct(reportInstruct);
            Injection.repo().device().saveDevice(((ActAcVM) this.mViewModel).controlDevice.getValue());
        }
    }

    private void setAcManagerData(int isOpen, int mode, int temp, int windSpeed, int direction) {
        if (isOpen != ((ActAcVM) this.mViewModel).mKKacZipManagerV2.getPowerState()) {
            ((ActAcVM) this.mViewModel).mKKacZipManagerV2.changePowerState();
        }
        ((ActAcVM) this.mViewModel).mKKacZipManagerV2.changeACTargetModel(mode);
        ((ActAcVM) this.mViewModel).mKKacZipManagerV2.getACCurModel().setCurTmp(temp);
        ((ActAcVM) this.mViewModel).mKKacZipManagerV2.getACCurModel().setCurWindSpeed(windSpeed);
        if (direction != ((ActAcVM) this.mViewModel).mKKacZipManagerV2.getCurUDDirect()) {
            ((ActAcVM) this.mViewModel).mKKacZipManagerV2.changeUDWindDirect(direction == 0 ? ACStateV2.UDWindDirectKey.UDDIRECT_KEY_SWING : ACStateV2.UDWindDirectKey.UDDIRECT_KEY_FIX);
        }
        ((ActAcVM) this.mViewModel).currentState.setValue((ACStateV2) GsonUtils.fromJson(((ActAcVM) this.mViewModel).mKKacZipManagerV2.getACStateV2InString(), ACStateV2.class));
    }
}