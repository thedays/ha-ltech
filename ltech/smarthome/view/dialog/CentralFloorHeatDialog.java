package com.ltech.smarthome.view.dialog;

import android.graphics.Rect;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogFloorHeatQuickBinding;
import com.ltech.smarthome.message.CtrlPackager;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.FloorHeatSubDeviceParam;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.CentralFloorHeatDialog;
import com.ltech.smarthome.view.layoutmanager.GalleryLayoutManager;
import com.ltech.smarthome.view.layoutmanager.ScaleTransformer;
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
public class CentralFloorHeatDialog extends SmartDialog<DialogFloorHeatQuickBinding> {
    private Device device;
    private FloorHeatSubDeviceParam deviceParam;
    private OnDialogCallback mDialogCallback;
    boolean openOrClose;
    private String title;
    public MutableLiveData<FloorHeatSubDeviceParam> currentState = new MutableLiveData<>();
    MutableLiveData<Boolean> powerOn = new MutableLiveData<>();
    int currentMode = 1;

    public interface OnDialogCallback {
        void dismiss(String stateString);

        void onCmdSend(byte[] cmd);

        void onMoreAction(String stateString);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_floor_heat_quick;
    }

    public static CentralFloorHeatDialog asDefault(Device device) {
        final CentralFloorHeatDialog centralFloorHeatDialog = new CentralFloorHeatDialog();
        centralFloorHeatDialog.initData(device);
        centralFloorHeatDialog.setViewConverter(new AnonymousClass1()).setDismissRunnable(new Runnable() { // from class: com.ltech.smarthome.view.dialog.CentralFloorHeatDialog$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                CentralFloorHeatDialog.lambda$asDefault$0(CentralFloorHeatDialog.this);
            }
        }).setMargin(30).setGravity(17);
        return centralFloorHeatDialog;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.CentralFloorHeatDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogFloorHeatQuickBinding, CentralFloorHeatDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogFloorHeatQuickBinding viewBinding, final CentralFloorHeatDialog dialog) {
            final BaseQuickAdapter<Integer, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Integer, BaseViewHolder>(this, R.layout.item_temp, dialog.getTempList()) { // from class: com.ltech.smarthome.view.dialog.CentralFloorHeatDialog.1.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, Integer item) {
                    helper.getView(R.id.layout_bg).getLayoutParams().height = viewBinding.rvTemp.getMeasuredHeight();
                    helper.getView(R.id.layout_bg).getLayoutParams().width = viewBinding.rvTemp.getMeasuredWidth() / 4;
                    helper.setText(R.id.tv_temp, item + "°");
                    ((AppCompatTextView) helper.getView(R.id.tv_temp)).getPaint().setFakeBoldText(true);
                    if (dialog.powerOn.getValue().booleanValue() && dialog.deviceParam.getTemperature() == item.intValue()) {
                        helper.setTextColor(R.id.tv_temp, ContextCompat.getColor(this.mContext, R.color.color_blue));
                    } else {
                        helper.setTextColor(R.id.tv_temp, ContextCompat.getColor(this.mContext, R.color.color_text_gray));
                    }
                }
            };
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.CentralFloorHeatDialog$1$$ExternalSyntheticLambda1
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    CentralFloorHeatDialog.AnonymousClass1.lambda$convertView$0(CentralFloorHeatDialog.this, viewBinding, baseQuickAdapter2, view, i);
                }
            });
            baseQuickAdapter.bindToRecyclerView(viewBinding.rvTemp);
            viewBinding.rvTemp.setHasFixedSize(true);
            ((DefaultItemAnimator) viewBinding.rvTemp.getItemAnimator()).setSupportsChangeAnimations(false);
            GalleryLayoutManager galleryLayoutManager = new GalleryLayoutManager(0);
            int i = 0;
            for (int i2 = 0; i2 < dialog.getTempList().size(); i2++) {
                if (dialog.deviceParam.getTemperature() == ((Integer) dialog.getTempList().get(i2)).intValue()) {
                    i = i2;
                }
            }
            galleryLayoutManager.setItemTransformer(new ScaleTransformer());
            galleryLayoutManager.attach(viewBinding.rvTemp, i);
            viewBinding.rvTemp.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.view.dialog.CentralFloorHeatDialog.1.2
                @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.left = 35;
                    outRect.right = 35;
                }
            });
            viewBinding.sb.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.view.dialog.CentralFloorHeatDialog$1$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                    CentralFloorHeatDialog.AnonymousClass1.lambda$convertView$1(CentralFloorHeatDialog.this, switchButton, z);
                }
            });
            dialog.currentState.observe(dialog.getViewLifecycleOwner(), new Observer() { // from class: com.ltech.smarthome.view.dialog.CentralFloorHeatDialog$1$$ExternalSyntheticLambda3
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    CentralFloorHeatDialog.this.powerOn.setValue(Boolean.valueOf(r1.getOn() > 0));
                }
            });
            dialog.powerOn.observe(dialog.getViewLifecycleOwner(), new Observer() { // from class: com.ltech.smarthome.view.dialog.CentralFloorHeatDialog$1$$ExternalSyntheticLambda4
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    CentralFloorHeatDialog.AnonymousClass1.this.lambda$convertView$5(viewBinding, baseQuickAdapter, dialog, (Boolean) obj);
                }
            });
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.CentralFloorHeatDialog$1$$ExternalSyntheticLambda5
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    CentralFloorHeatDialog.AnonymousClass1.lambda$convertView$6(CentralFloorHeatDialog.this, viewBinding, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(CentralFloorHeatDialog centralFloorHeatDialog, DialogFloorHeatQuickBinding dialogFloorHeatQuickBinding, BaseQuickAdapter baseQuickAdapter, View view, int i) {
            if (i <= 0 || !centralFloorHeatDialog.powerOn.getValue().booleanValue()) {
                return;
            }
            dialogFloorHeatQuickBinding.rvTemp.smoothScrollToPosition(i);
        }

        static /* synthetic */ void lambda$convertView$1(CentralFloorHeatDialog centralFloorHeatDialog, SwitchButton switchButton, boolean z) {
            centralFloorHeatDialog.openOrClose(z);
            centralFloorHeatDialog.powerOn.setValue(Boolean.valueOf(z));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convertView$5(final DialogFloorHeatQuickBinding dialogFloorHeatQuickBinding, final BaseQuickAdapter baseQuickAdapter, final CentralFloorHeatDialog centralFloorHeatDialog, Boolean bool) {
            LHomeLog.i(getClass(), "selectTempPos.111=" + bool);
            dialogFloorHeatQuickBinding.sb.setChecked(bool.booleanValue());
            if (bool.booleanValue()) {
                baseQuickAdapter.setNewData(centralFloorHeatDialog.getTempList());
                dialogFloorHeatQuickBinding.rvTemp.setOnFlingListener(null);
                dialogFloorHeatQuickBinding.rvTemp.clearOnScrollListeners();
                GalleryLayoutManager galleryLayoutManager = new GalleryLayoutManager(0);
                galleryLayoutManager.setOnItemSelectedListener(new GalleryLayoutManager.OnItemSelectedListener() { // from class: com.ltech.smarthome.view.dialog.CentralFloorHeatDialog$1$$ExternalSyntheticLambda0
                    @Override // com.ltech.smarthome.view.layoutmanager.GalleryLayoutManager.OnItemSelectedListener
                    public final void onItemSelected(RecyclerView recyclerView, View view, int i, boolean z) {
                        CentralFloorHeatDialog.AnonymousClass1.lambda$convertView$4(CentralFloorHeatDialog.this, baseQuickAdapter, dialogFloorHeatQuickBinding, recyclerView, view, i, z);
                    }
                });
                int i = 0;
                for (int i2 = 0; i2 < centralFloorHeatDialog.getTempList().size(); i2++) {
                    if (centralFloorHeatDialog.deviceParam.getTemperature() == ((Integer) centralFloorHeatDialog.getTempList().get(i2)).intValue()) {
                        i = i2;
                    }
                }
                LHomeLog.i(getClass(), "selectTempPos=" + i);
                galleryLayoutManager.setItemTransformer(new ScaleTransformer());
                galleryLayoutManager.attach(dialogFloorHeatQuickBinding.rvTemp, i);
                return;
            }
            if (dialogFloorHeatQuickBinding.rvTemp.getLayoutManager() != null) {
                ((GalleryLayoutManager) dialogFloorHeatQuickBinding.rvTemp.getLayoutManager()).setCanScroll(false);
            }
            baseQuickAdapter.notifyDataSetChanged();
        }

        static /* synthetic */ void lambda$convertView$4(CentralFloorHeatDialog centralFloorHeatDialog, final BaseQuickAdapter baseQuickAdapter, final DialogFloorHeatQuickBinding dialogFloorHeatQuickBinding, RecyclerView recyclerView, View view, int i, boolean z) {
            if (z) {
                return;
            }
            centralFloorHeatDialog.deviceParam.setTemperature(((Integer) baseQuickAdapter.getData().get(i)).intValue());
            centralFloorHeatDialog.changeTemperature(centralFloorHeatDialog.deviceParam.getTemperature());
            dialogFloorHeatQuickBinding.ivSendTip.setBackgroundResource(R.drawable.shape_circle_red);
            ThreadUtils.runOnUiThreadDelayed(new Runnable() { // from class: com.ltech.smarthome.view.dialog.CentralFloorHeatDialog$1$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    CentralFloorHeatDialog.AnonymousClass1.lambda$convertView$3(BaseQuickAdapter.this, dialogFloorHeatQuickBinding);
                }
            }, 200L);
        }

        static /* synthetic */ void lambda$convertView$3(BaseQuickAdapter baseQuickAdapter, DialogFloorHeatQuickBinding dialogFloorHeatQuickBinding) {
            baseQuickAdapter.notifyDataSetChanged();
            dialogFloorHeatQuickBinding.ivSendTip.setBackgroundResource(R.drawable.shape_circle_gray);
        }

        static /* synthetic */ void lambda$convertView$6(CentralFloorHeatDialog centralFloorHeatDialog, DialogFloorHeatQuickBinding dialogFloorHeatQuickBinding, View view) {
            switch (view.getId()) {
                case R.id.iv_device_more /* 2131297013 */:
                    centralFloorHeatDialog.dismissDialog();
                    if (centralFloorHeatDialog.mDialogCallback != null) {
                        centralFloorHeatDialog.mDialogCallback.onMoreAction("");
                        break;
                    }
                    break;
                case R.id.layout_temp_left /* 2131297678 */:
                    if (centralFloorHeatDialog.powerOn.getValue().booleanValue()) {
                        int max = Math.max(centralFloorHeatDialog.deviceParam.getTemperature() - 1, ((Integer) centralFloorHeatDialog.getTempList().get(0)).intValue());
                        dialogFloorHeatQuickBinding.rvTemp.smoothScrollToPosition(centralFloorHeatDialog.getTempList().indexOf(Integer.valueOf(max)));
                        centralFloorHeatDialog.changeTemperature(max);
                        break;
                    }
                    break;
                case R.id.layout_temp_right /* 2131297679 */:
                    if (centralFloorHeatDialog.powerOn.getValue().booleanValue()) {
                        int min = Math.min(centralFloorHeatDialog.deviceParam.getTemperature() + 1, ((Integer) centralFloorHeatDialog.getTempList().get(centralFloorHeatDialog.getTempList().size() - 1)).intValue());
                        dialogFloorHeatQuickBinding.rvTemp.smoothScrollToPosition(centralFloorHeatDialog.getTempList().indexOf(Integer.valueOf(min)));
                        centralFloorHeatDialog.changeTemperature(min);
                        break;
                    }
                    break;
            }
        }
    }

    static /* synthetic */ void lambda$asDefault$0(CentralFloorHeatDialog centralFloorHeatDialog) {
        OnDialogCallback onDialogCallback = centralFloorHeatDialog.mDialogCallback;
        if (onDialogCallback != null) {
            onDialogCallback.dismiss("");
        }
    }

    public CentralFloorHeatDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CentralFloorHeatDialog initData(Device device) {
        this.device = device;
        FloorHeatSubDeviceParam floorHeatSubDeviceParam = (FloorHeatSubDeviceParam) device.getParam(FloorHeatSubDeviceParam.class);
        this.deviceParam = floorHeatSubDeviceParam;
        if (floorHeatSubDeviceParam.getTemperature() < 5) {
            this.deviceParam.setTemperature(5);
        }
        this.currentMode = this.deviceParam.getMode();
        this.openOrClose = this.deviceParam.getOn() > 0;
        MessageManager.getInstance().setCentralAirStatusCallback(new MessageManager.CentralAirStatusCallback() { // from class: com.ltech.smarthome.view.dialog.CentralFloorHeatDialog$$ExternalSyntheticLambda0
            @Override // com.smart.message.MessageManager.CentralAirStatusCallback
            public final void onDataReceive(int i, int i2, int i3, String str) {
                CentralFloorHeatDialog.this.lambda$initData$1(i, i2, i3, str);
            }
        });
        this.currentState.setValue(this.deviceParam);
        selectState();
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initData$1(int i, int i2, int i3, String str) {
        if (i == this.deviceParam.getUnicastAddress() && i2 == this.deviceParam.outAddr && i3 == this.deviceParam.inAddr) {
            setStatus(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<Integer> getTempList() {
        ArrayList arrayList = new ArrayList();
        for (int i = 5; i <= 90; i++) {
            arrayList.add(Integer.valueOf(i));
        }
        return arrayList;
    }

    public CentralFloorHeatDialog setDialogCallback(OnDialogCallback mDialogCallback) {
        this.mDialogCallback = mDialogCallback;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "floor_heat_quick";
    }

    public void changeTemperature(int tempure) {
        this.deviceParam.setTemperature(tempure);
        this.device.setParam(this.deviceParam);
        sendFloorHeatCommonCmd(CmdBleFactory.floorHeatChangeTemp(tempure, this.deviceParam.getOutAddr(), this.deviceParam.getInAddr()));
    }

    private void sendFloorHeatCommonCmd(BaseCmd cmd) {
        sendCmdWithPackage(cmd, CtrlPackager.getBleForCentralAirDeviceCtrlPackage(this.deviceParam.getUnicastAddress()));
    }

    public void openOrClose(boolean z) {
        this.deviceParam.setOn(z ? 1 : 0);
        this.device.setParam(this.deviceParam);
        sendFloorHeatCommonCmd(CmdBleFactory.openOrCloseFloorHeat(z, this.deviceParam.getOutAddr(), this.deviceParam.getInAddr()));
    }

    public CentralFloorHeatDialog selectState() {
        sendFloorHeatCommonCmd(CmdBleFactory.queryFloorHeatState(this.deviceParam.getOutAddr(), this.deviceParam.getInAddr()));
        return this;
    }

    private void sendCmdWithPackage(BaseCmd cmd, BaseCtrlPackage ctrlPackage) {
        MessageManager.getInstance().create(ActivityUtils.getTopActivity()).cmd(cmd).control(ctrlPackage).sendTimes(1).resendTimes(0).timeOutTime(10000).intervalTime(0).receiveListener(new IReceiveListener(this) { // from class: com.ltech.smarthome.view.dialog.CentralFloorHeatDialog.2
            @Override // com.smart.message.base.IReceiveListener
            public void onSuccess(ResponseMsg msg) {
            }

            @Override // com.smart.message.base.IReceiveListener
            public void onTimeout() {
            }
        }).enqueue();
    }

    private void setStatus(String deviceData) {
        FloorHeatSubDeviceParam floorHeatSubDeviceParam = this.deviceParam;
        FloorHeatSubDeviceParam floorHeatSubDeviceParam2 = new FloorHeatSubDeviceParam(floorHeatSubDeviceParam.getUnicastAddress(), deviceData, floorHeatSubDeviceParam.getName(), floorHeatSubDeviceParam.getPosition());
        this.currentMode = floorHeatSubDeviceParam2.getMode();
        this.deviceParam = floorHeatSubDeviceParam2;
        this.device.setParam(floorHeatSubDeviceParam2);
        this.currentState.setValue(this.deviceParam);
    }
}