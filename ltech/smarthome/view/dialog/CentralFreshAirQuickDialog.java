package com.ltech.smarthome.view.dialog;

import android.view.View;
import android.view.animation.Animation;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogFreshAirQuickBinding;
import com.ltech.smarthome.message.CtrlPackager;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.FreshAirSubDeviceParam;
import com.ltech.smarthome.ui.device.ir.IrKeyItem;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.CentralFreshAirQuickDialog;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.message.base.BaseCmd;
import com.smart.message.base.BaseCtrlPackage;
import com.smart.message.base.IReceiveListener;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.productBle.CmdBleFactory;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class CentralFreshAirQuickDialog extends SmartDialog<DialogFreshAirQuickBinding> {
    private Device device;
    private FreshAirSubDeviceParam freshAirParam;
    private OnDialogCallback mDialogCallback;
    boolean openOrClose;
    private String title;
    public MutableLiveData<FreshAirSubDeviceParam> currentState = new MutableLiveData<>();
    MutableLiveData<Boolean> powerOn = new MutableLiveData<>();
    int currentMode = 1;
    int currentWindSpeed = 1;

    public interface OnDialogCallback {
        void dismiss(String stateString);

        void onCmdSend(byte[] cmd);

        void onMoreAction(String stateString);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_fresh_air_quick;
    }

    public static CentralFreshAirQuickDialog asDefault(Device device) {
        final CentralFreshAirQuickDialog centralFreshAirQuickDialog = new CentralFreshAirQuickDialog();
        centralFreshAirQuickDialog.initData(device);
        centralFreshAirQuickDialog.setViewConverter(new AnonymousClass1()).setDismissRunnable(new Runnable() { // from class: com.ltech.smarthome.view.dialog.CentralFreshAirQuickDialog$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                CentralFreshAirQuickDialog.lambda$asDefault$0(CentralFreshAirQuickDialog.this);
            }
        }).setMargin(30).setGravity(17);
        return centralFreshAirQuickDialog;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.CentralFreshAirQuickDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogFreshAirQuickBinding, CentralFreshAirQuickDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogFreshAirQuickBinding viewBinding, final CentralFreshAirQuickDialog dialog) {
            final Animation.AnimationListener animationListener = new Animation.AnimationListener(this) { // from class: com.ltech.smarthome.view.dialog.CentralFreshAirQuickDialog.1.1
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                    viewBinding.ivSendTip.setBackgroundResource(R.drawable.shape_circle_red);
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    viewBinding.ivSendTip.setBackgroundResource(R.drawable.shape_circle_gray);
                }
            };
            final BaseQuickAdapter<IrKeyItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<IrKeyItem, BaseViewHolder>(this, R.layout.item_ir_key, dialog.getKeyItemList(-1)) { // from class: com.ltech.smarthome.view.dialog.CentralFreshAirQuickDialog.1.2
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, IrKeyItem item) {
                    helper.getView(R.id.layout_bg).getLayoutParams().height = viewBinding.rvContent.getMeasuredHeight();
                    helper.setImageResource(R.id.iv_icon, item.getIconRes()).setText(R.id.tv_name, item.getName());
                    helper.setEnabled(R.id.tv_name, item.isEnable());
                }
            };
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.CentralFreshAirQuickDialog$1$$ExternalSyntheticLambda1
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    CentralFreshAirQuickDialog.AnonymousClass1.lambda$convertView$1(CentralFreshAirQuickDialog.this, animationListener, baseQuickAdapter, baseQuickAdapter2, view, i);
                }
            });
            baseQuickAdapter.bindToRecyclerView(viewBinding.rvContent);
            viewBinding.rvContent.setLayoutManager(new GridLayoutManager(dialog.getContext(), 4));
            viewBinding.rvContent.setHasFixedSize(true);
            ((DefaultItemAnimator) viewBinding.rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
            viewBinding.sb.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.view.dialog.CentralFreshAirQuickDialog$1$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                    CentralFreshAirQuickDialog.AnonymousClass1.lambda$convertView$2(CentralFreshAirQuickDialog.this, switchButton, z);
                }
            });
            dialog.currentState.observe(dialog.getViewLifecycleOwner(), new Observer() { // from class: com.ltech.smarthome.view.dialog.CentralFreshAirQuickDialog$1$$ExternalSyntheticLambda3
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    CentralFreshAirQuickDialog.AnonymousClass1.lambda$convertView$3(CentralFreshAirQuickDialog.this, viewBinding, (FreshAirSubDeviceParam) obj);
                }
            });
            dialog.powerOn.observe(dialog.getViewLifecycleOwner(), new Observer() { // from class: com.ltech.smarthome.view.dialog.CentralFreshAirQuickDialog$1$$ExternalSyntheticLambda4
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    CentralFreshAirQuickDialog.AnonymousClass1.this.lambda$convertView$4(viewBinding, baseQuickAdapter, dialog, (Boolean) obj);
                }
            });
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.CentralFreshAirQuickDialog$1$$ExternalSyntheticLambda5
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    CentralFreshAirQuickDialog.AnonymousClass1.lambda$convertView$5(CentralFreshAirQuickDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$1(final CentralFreshAirQuickDialog centralFreshAirQuickDialog, Animation.AnimationListener animationListener, final BaseQuickAdapter baseQuickAdapter, BaseQuickAdapter baseQuickAdapter2, View view, final int i) {
            if (Boolean.TRUE.equals(centralFreshAirQuickDialog.powerOn.getValue())) {
                ViewHelpUtil.zoomInZoomOut(view, animationListener);
                ThreadUtils.runOnUiThreadDelayed(new Runnable() { // from class: com.ltech.smarthome.view.dialog.CentralFreshAirQuickDialog$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        CentralFreshAirQuickDialog.AnonymousClass1.lambda$convertView$0(CentralFreshAirQuickDialog.this, i, baseQuickAdapter);
                    }
                }, 200L);
            }
        }

        static /* synthetic */ void lambda$convertView$0(CentralFreshAirQuickDialog centralFreshAirQuickDialog, int i, BaseQuickAdapter baseQuickAdapter) {
            centralFreshAirQuickDialog.changeWindSpeed(i);
            centralFreshAirQuickDialog.freshAirParam.setSpeed(centralFreshAirQuickDialog.currentWindSpeed);
            baseQuickAdapter.setNewData(centralFreshAirQuickDialog.getKeyItemList(centralFreshAirQuickDialog.currentWindSpeed));
        }

        static /* synthetic */ void lambda$convertView$2(CentralFreshAirQuickDialog centralFreshAirQuickDialog, SwitchButton switchButton, boolean z) {
            centralFreshAirQuickDialog.openOrClose(z);
            centralFreshAirQuickDialog.powerOn.setValue(Boolean.valueOf(z));
        }

        static /* synthetic */ void lambda$convertView$3(CentralFreshAirQuickDialog centralFreshAirQuickDialog, DialogFreshAirQuickBinding dialogFreshAirQuickBinding, FreshAirSubDeviceParam freshAirSubDeviceParam) {
            centralFreshAirQuickDialog.powerOn.setValue(Boolean.valueOf(freshAirSubDeviceParam.getOn() > 0));
            dialogFreshAirQuickBinding.tvPmValue.setText(freshAirSubDeviceParam.getPmValue() + "");
            dialogFreshAirQuickBinding.tvTempValue.setText(freshAirSubDeviceParam.getRoomTemp() + "");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convertView$4(DialogFreshAirQuickBinding dialogFreshAirQuickBinding, BaseQuickAdapter baseQuickAdapter, CentralFreshAirQuickDialog centralFreshAirQuickDialog, Boolean bool) {
            LHomeLog.i(getClass(), "selectTempPos.111=" + bool);
            dialogFreshAirQuickBinding.sb.setChecked(bool.booleanValue());
            if (bool.booleanValue()) {
                baseQuickAdapter.setNewData(centralFreshAirQuickDialog.getKeyItemList(centralFreshAirQuickDialog.freshAirParam.getSpeed()));
            } else {
                baseQuickAdapter.setNewData(centralFreshAirQuickDialog.getKeyItemList(-1));
            }
        }

        static /* synthetic */ void lambda$convertView$5(CentralFreshAirQuickDialog centralFreshAirQuickDialog, View view) {
            if (view.getId() != R.id.iv_device_more) {
                return;
            }
            centralFreshAirQuickDialog.dismissDialog();
            if (centralFreshAirQuickDialog.mDialogCallback != null) {
                centralFreshAirQuickDialog.mDialogCallback.onMoreAction("");
            }
        }
    }

    static /* synthetic */ void lambda$asDefault$0(CentralFreshAirQuickDialog centralFreshAirQuickDialog) {
        OnDialogCallback onDialogCallback = centralFreshAirQuickDialog.mDialogCallback;
        if (onDialogCallback != null) {
            onDialogCallback.dismiss("");
        }
    }

    public CentralFreshAirQuickDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CentralFreshAirQuickDialog initData(Device device) {
        this.device = device;
        FreshAirSubDeviceParam freshAirSubDeviceParam = (FreshAirSubDeviceParam) device.getParam(FreshAirSubDeviceParam.class);
        this.freshAirParam = freshAirSubDeviceParam;
        this.currentWindSpeed = freshAirSubDeviceParam.getSpeed();
        this.openOrClose = this.freshAirParam.getOn() > 0;
        MessageManager.getInstance().setCentralAirStatusCallback(new MessageManager.CentralAirStatusCallback() { // from class: com.ltech.smarthome.view.dialog.CentralFreshAirQuickDialog$$ExternalSyntheticLambda0
            @Override // com.smart.message.MessageManager.CentralAirStatusCallback
            public final void onDataReceive(int i, int i2, int i3, String str) {
                CentralFreshAirQuickDialog.this.lambda$initData$1(i, i2, i3, str);
            }
        });
        this.currentState.setValue(this.freshAirParam);
        selectState();
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initData$1(int i, int i2, int i3, String str) {
        if (i == this.freshAirParam.getUnicastAddress() && i2 == this.freshAirParam.outAddr && i3 == this.freshAirParam.inAddr) {
            setStatus(str);
        }
    }

    public List<IrKeyItem> getKeyItemList(int windSpeed) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new IrKeyItem(R.mipmap.icon_ir_autowind, ActivityUtils.getTopActivity().getString(R.string.fan_speed_1), -1, windSpeed != -1));
        arrayList.add(new IrKeyItem(R.mipmap.icon_ir_lowwind, ActivityUtils.getTopActivity().getString(R.string.fan_speed_2), -1, windSpeed != -1));
        arrayList.add(new IrKeyItem(R.mipmap.icon_ir_midwind, ActivityUtils.getTopActivity().getString(R.string.fan_speed_3), -1, windSpeed != -1));
        arrayList.add(new IrKeyItem(R.mipmap.icon_ir_highwind, ActivityUtils.getTopActivity().getString(R.string.fan_speed_4), -1, windSpeed != -1));
        if (windSpeed == 0) {
            ((IrKeyItem) arrayList.get(0)).setIconRes(R.mipmap.ic_autowind_sel);
            return arrayList;
        }
        if (windSpeed == 1) {
            ((IrKeyItem) arrayList.get(3)).setIconRes(R.mipmap.ic_highwind_sel);
            return arrayList;
        }
        if (windSpeed == 2) {
            ((IrKeyItem) arrayList.get(2)).setIconRes(R.mipmap.ic_midwind_sel);
            return arrayList;
        }
        if (windSpeed != 4) {
            return arrayList;
        }
        ((IrKeyItem) arrayList.get(1)).setIconRes(R.mipmap.ic_lowwind_sel);
        return arrayList;
    }

    public CentralFreshAirQuickDialog setDialogCallback(OnDialogCallback mDialogCallback) {
        this.mDialogCallback = mDialogCallback;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "fresh_air_quick";
    }

    public void openOrClose(boolean z) {
        this.freshAirParam.setOn(z ? 1 : 0);
        this.device.setParam(this.freshAirParam);
        sendFreshAirCommonCmd(CmdBleFactory.openOrCloseFreshAir(z, this.freshAirParam.getOutAddr(), this.freshAirParam.getInAddr()));
    }

    public CentralFreshAirQuickDialog selectState() {
        sendFreshAirCommonCmd(CmdBleFactory.queryFreshAirState(this.freshAirParam.getOutAddr(), this.freshAirParam.getInAddr()));
        return this;
    }

    public void changeWindSpeed(int position) {
        if (position == 0) {
            this.currentWindSpeed = 0;
        } else if (position == 1) {
            this.currentWindSpeed = 4;
        } else if (position == 2) {
            this.currentWindSpeed = 2;
        } else if (position == 3) {
            this.currentWindSpeed = 1;
        }
        sendFreshAirCommonCmd(CmdBleFactory.freshAirChangeWindSpeed(this.currentWindSpeed, this.freshAirParam.getOutAddr(), this.freshAirParam.getInAddr()));
    }

    private void sendFreshAirCommonCmd(BaseCmd cmd) {
        sendCmdWithPackage(cmd, CtrlPackager.getBleForCentralAirDeviceCtrlPackage(this.freshAirParam.getUnicastAddress()));
    }

    private void sendCmdWithPackage(BaseCmd cmd, BaseCtrlPackage ctrlPackage) {
        MessageManager.getInstance().create(ActivityUtils.getTopActivity()).cmd(cmd).control(ctrlPackage).sendTimes(1).resendTimes(0).timeOutTime(10000).intervalTime(0).receiveListener(new IReceiveListener(this) { // from class: com.ltech.smarthome.view.dialog.CentralFreshAirQuickDialog.2
            @Override // com.smart.message.base.IReceiveListener
            public void onSuccess(ResponseMsg msg) {
            }

            @Override // com.smart.message.base.IReceiveListener
            public void onTimeout() {
            }
        }).enqueue();
    }

    private void setStatus(String deviceData) {
        FreshAirSubDeviceParam freshAirSubDeviceParam = this.freshAirParam;
        FreshAirSubDeviceParam freshAirSubDeviceParam2 = new FreshAirSubDeviceParam(freshAirSubDeviceParam.getUnicastAddress(), deviceData, freshAirSubDeviceParam.getName(), freshAirSubDeviceParam.getPosition());
        this.currentMode = freshAirSubDeviceParam2.getMode();
        this.currentWindSpeed = freshAirSubDeviceParam2.getSpeed();
        this.freshAirParam = freshAirSubDeviceParam2;
        this.device.setParam(freshAirSubDeviceParam2);
        this.currentState.setValue(this.freshAirParam);
    }
}