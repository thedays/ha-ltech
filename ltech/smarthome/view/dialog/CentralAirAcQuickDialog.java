package com.ltech.smarthome.view.dialog;

import android.graphics.Rect;
import android.view.View;
import android.view.animation.Animation;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogAcQuickBinding;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.CentralAirSubDeviceParam;
import com.ltech.smarthome.ui.device.central.air.ActAcCentralVM;
import com.ltech.smarthome.ui.device.ir.IrKeyItem;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.ltech.smarthome.view.dialog.CentralAirAcQuickDialog;
import com.ltech.smarthome.view.layoutmanager.GalleryLayoutManager;
import com.ltech.smarthome.view.layoutmanager.ScaleTransformer;
import com.smart.message.MessageManager;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.productBle.CmdBleFactory;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class CentralAirAcQuickDialog extends SmartDialog<DialogAcQuickBinding> {
    private Device device;
    private OnDialogCallback mDialogCallback;
    boolean openOrClose;
    private String title;
    private ActAcCentralVM viewModel;
    public MutableLiveData<CentralAirSubDeviceParam> currentState = new MutableLiveData<>();
    MutableLiveData<Boolean> powerOn = new MutableLiveData<>();

    public interface OnDialogCallback {
        void dismiss(String stateString);

        void onCmdSend(byte[] cmd);

        void onMoreAction(String stateString);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_ac_quick;
    }

    public static CentralAirAcQuickDialog asDefault(final Device device) {
        final CentralAirAcQuickDialog centralAirAcQuickDialog = new CentralAirAcQuickDialog();
        centralAirAcQuickDialog.initData(device);
        centralAirAcQuickDialog.setViewConverter(new AnonymousClass1(device, centralAirAcQuickDialog)).setDismissRunnable(new Runnable() { // from class: com.ltech.smarthome.view.dialog.CentralAirAcQuickDialog$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                CentralAirAcQuickDialog.lambda$asDefault$0(CentralAirAcQuickDialog.this, device);
            }
        }).setMargin(30).setGravity(17);
        return centralAirAcQuickDialog;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.CentralAirAcQuickDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogAcQuickBinding, CentralAirAcQuickDialog> {
        final /* synthetic */ CentralAirAcQuickDialog val$acQuickDialog;
        final /* synthetic */ Device val$device;

        AnonymousClass1(final Device val$device, final CentralAirAcQuickDialog val$acQuickDialog) {
            this.val$device = val$device;
            this.val$acQuickDialog = val$acQuickDialog;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogAcQuickBinding viewBinding, final CentralAirAcQuickDialog dialog) {
            final Animation.AnimationListener animationListener = new Animation.AnimationListener(this) { // from class: com.ltech.smarthome.view.dialog.CentralAirAcQuickDialog.1.1
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
            final BaseQuickAdapter<IrKeyItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<IrKeyItem, BaseViewHolder>(this, R.layout.item_ir_key, dialog.getKeyItemList()) { // from class: com.ltech.smarthome.view.dialog.CentralAirAcQuickDialog.1.2
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, IrKeyItem item) {
                    helper.getView(R.id.layout_bg).getLayoutParams().height = viewBinding.rvContent.getMeasuredHeight();
                    helper.setImageResource(R.id.iv_icon, item.getIconRes()).setText(R.id.tv_name, item.getName());
                    helper.setEnabled(R.id.tv_name, item.isEnable());
                }
            };
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.CentralAirAcQuickDialog$1$$ExternalSyntheticLambda1
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    CentralAirAcQuickDialog.AnonymousClass1.lambda$convertView$1(BaseQuickAdapter.this, animationListener, dialog, baseQuickAdapter2, view, i);
                }
            });
            baseQuickAdapter.bindToRecyclerView(viewBinding.rvContent);
            viewBinding.rvContent.setLayoutManager(new GridLayoutManager(dialog.getContext(), 3));
            viewBinding.rvContent.setHasFixedSize(true);
            ((DefaultItemAnimator) viewBinding.rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
            final BaseQuickAdapter<Integer, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<Integer, BaseViewHolder>(this, R.layout.item_temp, dialog.getTempList()) { // from class: com.ltech.smarthome.view.dialog.CentralAirAcQuickDialog.1.3
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, Integer item) {
                    helper.getView(R.id.layout_bg).getLayoutParams().height = viewBinding.rvContent.getMeasuredHeight();
                    helper.getView(R.id.layout_bg).getLayoutParams().width = viewBinding.rvContent.getMeasuredWidth() / 6;
                    helper.setText(R.id.tv_temp, item + "°");
                    ((AppCompatTextView) helper.getView(R.id.tv_temp)).getPaint().setFakeBoldText(true);
                    if (dialog.powerOn.getValue().booleanValue() && dialog.getParam().getTemperature() == item.intValue()) {
                        helper.setTextColor(R.id.tv_temp, ContextCompat.getColor(this.mContext, R.color.color_blue));
                    } else {
                        helper.setTextColor(R.id.tv_temp, ContextCompat.getColor(this.mContext, R.color.color_text_gray));
                    }
                }
            };
            baseQuickAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.CentralAirAcQuickDialog$1$$ExternalSyntheticLambda2
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    CentralAirAcQuickDialog.AnonymousClass1.lambda$convertView$2(CentralAirAcQuickDialog.this, viewBinding, baseQuickAdapter3, view, i);
                }
            });
            baseQuickAdapter2.bindToRecyclerView(viewBinding.rvTemp);
            viewBinding.rvTemp.setHasFixedSize(true);
            ((DefaultItemAnimator) viewBinding.rvTemp.getItemAnimator()).setSupportsChangeAnimations(false);
            GalleryLayoutManager galleryLayoutManager = new GalleryLayoutManager(0);
            int i = 0;
            for (int i2 = 0; i2 < dialog.getTempList().size(); i2++) {
                if (dialog.getParam().getTemperature() == ((Integer) dialog.getTempList().get(i2)).intValue()) {
                    i = i2;
                }
            }
            galleryLayoutManager.setItemTransformer(new ScaleTransformer());
            galleryLayoutManager.attach(viewBinding.rvTemp, i);
            viewBinding.rvTemp.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.view.dialog.CentralAirAcQuickDialog.1.4
                @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.left = 35;
                    outRect.right = 35;
                }
            });
            dialog.currentState.observe(dialog.getViewLifecycleOwner(), new Observer() { // from class: com.ltech.smarthome.view.dialog.CentralAirAcQuickDialog$1$$ExternalSyntheticLambda3
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    CentralAirAcQuickDialog.AnonymousClass1.lambda$convertView$3(CentralAirAcQuickDialog.this, (CentralAirSubDeviceParam) obj);
                }
            });
            dialog.powerOn.observe(dialog.getViewLifecycleOwner(), new Observer() { // from class: com.ltech.smarthome.view.dialog.CentralAirAcQuickDialog$1$$ExternalSyntheticLambda4
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    CentralAirAcQuickDialog.AnonymousClass1.this.lambda$convertView$6(baseQuickAdapter2, dialog, viewBinding, baseQuickAdapter, (Boolean) obj);
                }
            });
            viewBinding.tvTitle.setText(dialog.title);
            final Device device = this.val$device;
            final CentralAirAcQuickDialog centralAirAcQuickDialog = this.val$acQuickDialog;
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.CentralAirAcQuickDialog$1$$ExternalSyntheticLambda5
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    CentralAirAcQuickDialog.AnonymousClass1.lambda$convertView$7(CentralAirAcQuickDialog.this, device, centralAirAcQuickDialog, viewBinding, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$1(final BaseQuickAdapter baseQuickAdapter, Animation.AnimationListener animationListener, final CentralAirAcQuickDialog centralAirAcQuickDialog, BaseQuickAdapter baseQuickAdapter2, View view, final int i) {
            if (i <= 0 || ((IrKeyItem) baseQuickAdapter.getData().get(i)).isEnable()) {
                ViewHelpUtil.zoomInZoomOut(view, animationListener);
                ThreadUtils.runOnUiThreadDelayed(new Runnable() { // from class: com.ltech.smarthome.view.dialog.CentralAirAcQuickDialog$1$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        CentralAirAcQuickDialog.AnonymousClass1.lambda$convertView$0(i, centralAirAcQuickDialog, baseQuickAdapter);
                    }
                }, 200L);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        static /* synthetic */ void lambda$convertView$0(int i, CentralAirAcQuickDialog centralAirAcQuickDialog, BaseQuickAdapter baseQuickAdapter) {
            if (i == 0) {
                boolean z = (centralAirAcQuickDialog.getParam().getOn() > 0 ? (char) 1 : (char) 0) ^ 1;
                centralAirAcQuickDialog.getParam().setOn(z ? 1 : 0);
                centralAirAcQuickDialog.powerOn.setValue(Boolean.valueOf(centralAirAcQuickDialog.getParam().getOn() > 0));
                centralAirAcQuickDialog.viewModel.openOrClose(z);
                return;
            }
            if (i == 1) {
                centralAirAcQuickDialog.viewModel.changeMode();
                baseQuickAdapter.setNewData(centralAirAcQuickDialog.getKeyItemList());
            } else if (i == 2) {
                centralAirAcQuickDialog.viewModel.changeWindSpeed();
                baseQuickAdapter.setNewData(centralAirAcQuickDialog.getKeyItemList());
            }
        }

        static /* synthetic */ void lambda$convertView$2(CentralAirAcQuickDialog centralAirAcQuickDialog, DialogAcQuickBinding dialogAcQuickBinding, BaseQuickAdapter baseQuickAdapter, View view, int i) {
            if (i <= 0 || !centralAirAcQuickDialog.powerOn.getValue().booleanValue()) {
                return;
            }
            dialogAcQuickBinding.rvTemp.smoothScrollToPosition(i);
        }

        static /* synthetic */ void lambda$convertView$3(CentralAirAcQuickDialog centralAirAcQuickDialog, CentralAirSubDeviceParam centralAirSubDeviceParam) {
            centralAirAcQuickDialog.viewModel.currentOpen = centralAirSubDeviceParam.getOn();
            centralAirAcQuickDialog.viewModel.currentMode = centralAirSubDeviceParam.getMode();
            centralAirAcQuickDialog.viewModel.currentWindSpeed = centralAirSubDeviceParam.getSpeed();
            centralAirAcQuickDialog.viewModel.currentTempure = centralAirSubDeviceParam.getTemperature();
            centralAirAcQuickDialog.viewModel.param.setValue(centralAirSubDeviceParam);
            centralAirAcQuickDialog.powerOn.setValue(Boolean.valueOf(centralAirSubDeviceParam.getOn() > 0));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convertView$6(final BaseQuickAdapter baseQuickAdapter, final CentralAirAcQuickDialog centralAirAcQuickDialog, final DialogAcQuickBinding dialogAcQuickBinding, BaseQuickAdapter baseQuickAdapter2, Boolean bool) {
            LHomeLog.i(getClass(), "selectTempPos.111=" + bool);
            if (bool.booleanValue()) {
                baseQuickAdapter.setNewData(centralAirAcQuickDialog.getTempList());
                dialogAcQuickBinding.rvTemp.setOnFlingListener(null);
                dialogAcQuickBinding.rvTemp.clearOnScrollListeners();
                GalleryLayoutManager galleryLayoutManager = new GalleryLayoutManager(0);
                galleryLayoutManager.setOnItemSelectedListener(new GalleryLayoutManager.OnItemSelectedListener() { // from class: com.ltech.smarthome.view.dialog.CentralAirAcQuickDialog$1$$ExternalSyntheticLambda0
                    @Override // com.ltech.smarthome.view.layoutmanager.GalleryLayoutManager.OnItemSelectedListener
                    public final void onItemSelected(RecyclerView recyclerView, View view, int i, boolean z) {
                        CentralAirAcQuickDialog.AnonymousClass1.lambda$convertView$5(CentralAirAcQuickDialog.this, baseQuickAdapter, dialogAcQuickBinding, recyclerView, view, i, z);
                    }
                });
                int i = 0;
                for (int i2 = 0; i2 < centralAirAcQuickDialog.getTempList().size(); i2++) {
                    if (centralAirAcQuickDialog.getParam().getTemperature() == ((Integer) centralAirAcQuickDialog.getTempList().get(i2)).intValue()) {
                        i = i2;
                    }
                }
                LHomeLog.i(getClass(), "selectTempPos=" + i);
                galleryLayoutManager.setItemTransformer(new ScaleTransformer());
                galleryLayoutManager.attach(dialogAcQuickBinding.rvTemp, i);
            } else {
                if (dialogAcQuickBinding.rvTemp.getLayoutManager() != null) {
                    ((GalleryLayoutManager) dialogAcQuickBinding.rvTemp.getLayoutManager()).setCanScroll(false);
                }
                baseQuickAdapter.notifyDataSetChanged();
            }
            baseQuickAdapter2.setNewData(centralAirAcQuickDialog.getKeyItemList());
        }

        static /* synthetic */ void lambda$convertView$5(CentralAirAcQuickDialog centralAirAcQuickDialog, final BaseQuickAdapter baseQuickAdapter, final DialogAcQuickBinding dialogAcQuickBinding, RecyclerView recyclerView, View view, int i, boolean z) {
            if (z) {
                return;
            }
            centralAirAcQuickDialog.getParam().setTemperature(((Integer) baseQuickAdapter.getData().get(i)).intValue());
            centralAirAcQuickDialog.viewModel.changeTemperature(centralAirAcQuickDialog.getParam().getTemperature());
            dialogAcQuickBinding.ivSendTip.setBackgroundResource(R.drawable.shape_circle_red);
            ThreadUtils.runOnUiThreadDelayed(new Runnable() { // from class: com.ltech.smarthome.view.dialog.CentralAirAcQuickDialog$1$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    CentralAirAcQuickDialog.AnonymousClass1.lambda$convertView$4(BaseQuickAdapter.this, dialogAcQuickBinding);
                }
            }, 200L);
        }

        static /* synthetic */ void lambda$convertView$4(BaseQuickAdapter baseQuickAdapter, DialogAcQuickBinding dialogAcQuickBinding) {
            baseQuickAdapter.notifyDataSetChanged();
            dialogAcQuickBinding.ivSendTip.setBackgroundResource(R.drawable.shape_circle_gray);
        }

        static /* synthetic */ void lambda$convertView$7(CentralAirAcQuickDialog centralAirAcQuickDialog, Device device, CentralAirAcQuickDialog centralAirAcQuickDialog2, DialogAcQuickBinding dialogAcQuickBinding, View view) {
            switch (view.getId()) {
                case R.id.iv_device_more /* 2131297013 */:
                    centralAirAcQuickDialog.dismissDialog();
                    if (centralAirAcQuickDialog.mDialogCallback != null) {
                        device.setParam(centralAirAcQuickDialog2.getParam());
                        centralAirAcQuickDialog2.mDialogCallback.dismiss("");
                        centralAirAcQuickDialog.mDialogCallback.onMoreAction("");
                        break;
                    }
                    break;
                case R.id.layout_temp_left /* 2131297678 */:
                    if (centralAirAcQuickDialog.powerOn.getValue().booleanValue()) {
                        int max = Math.max(centralAirAcQuickDialog.getParam().getTemperature() - 1, ((Integer) centralAirAcQuickDialog.getTempList().get(0)).intValue());
                        dialogAcQuickBinding.rvTemp.smoothScrollToPosition(centralAirAcQuickDialog.getTempList().indexOf(Integer.valueOf(max)));
                        centralAirAcQuickDialog.viewModel.changeTemperature(max);
                        break;
                    }
                    break;
                case R.id.layout_temp_right /* 2131297679 */:
                    if (centralAirAcQuickDialog.powerOn.getValue().booleanValue()) {
                        int min = Math.min(centralAirAcQuickDialog.getParam().getTemperature() + 1, ((Integer) centralAirAcQuickDialog.getTempList().get(centralAirAcQuickDialog.getTempList().size() - 1)).intValue());
                        dialogAcQuickBinding.rvTemp.smoothScrollToPosition(centralAirAcQuickDialog.getTempList().indexOf(Integer.valueOf(min)));
                        centralAirAcQuickDialog.viewModel.changeTemperature(min);
                        break;
                    }
                    break;
            }
        }
    }

    static /* synthetic */ void lambda$asDefault$0(CentralAirAcQuickDialog centralAirAcQuickDialog, Device device) {
        if (centralAirAcQuickDialog.mDialogCallback != null) {
            device.setParam(centralAirAcQuickDialog.getParam());
            centralAirAcQuickDialog.mDialogCallback.dismiss("");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CentralAirSubDeviceParam getParam() {
        return this.viewModel.param.getValue();
    }

    public CentralAirAcQuickDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CentralAirAcQuickDialog initData(Device device) {
        this.device = device;
        ActAcCentralVM actAcCentralVM = (ActAcCentralVM) new ViewModelProvider((VMActivity) ActivityUtils.getTopActivity()).get(ActAcCentralVM.class);
        this.viewModel = actAcCentralVM;
        actAcCentralVM.initAcFeature(device);
        final CentralAirSubDeviceParam centralAirSubDeviceParam = (CentralAirSubDeviceParam) device.getParam(CentralAirSubDeviceParam.class);
        if (centralAirSubDeviceParam.getTemperature() < 16) {
            centralAirSubDeviceParam.setTemperature(16);
        }
        this.openOrClose = centralAirSubDeviceParam.getOn() > 0;
        MessageManager.getInstance().setCentralAirStatusCallback(new MessageManager.CentralAirStatusCallback() { // from class: com.ltech.smarthome.view.dialog.CentralAirAcQuickDialog$$ExternalSyntheticLambda0
            @Override // com.smart.message.MessageManager.CentralAirStatusCallback
            public final void onDataReceive(int i, int i2, int i3, String str) {
                CentralAirAcQuickDialog.this.lambda$initData$1(centralAirSubDeviceParam, i, i2, i3, str);
            }
        });
        this.currentState.setValue(centralAirSubDeviceParam);
        this.viewModel.param.setValue(centralAirSubDeviceParam);
        selectState();
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initData$1(CentralAirSubDeviceParam centralAirSubDeviceParam, int i, int i2, int i3, String str) {
        if (this.viewModel.canChange && i == centralAirSubDeviceParam.getUnicastAddress() && i2 == centralAirSubDeviceParam.outAddr && i3 == centralAirSubDeviceParam.inAddr) {
            if (str.length() >= 28) {
                this.viewModel.setRefreshDelay(((Long.parseLong(str.substring(0, 8), 16) * 1000) + 5200) - System.currentTimeMillis());
                setStatus(str.substring(8));
            } else if (str.length() >= 20) {
                setStatus(str);
            }
        }
    }

    protected List<IrKeyItem> getKeyItemList() {
        ArrayList arrayList = new ArrayList();
        if (getParam() != null && getParam().getOn() > 0) {
            arrayList.add(new IrKeyItem(R.mipmap.icon_ir_power_blue, R.string.ir_power, -1).setEnable(true));
        } else {
            arrayList.add(new IrKeyItem(R.mipmap.icon_ir_power, R.string.ir_power, -1).setEnable(true));
        }
        ActAcCentralVM actAcCentralVM = this.viewModel;
        arrayList.add(actAcCentralVM.getModeInfo(actAcCentralVM.currentMode).setEnable(getParam().getOn() > 0));
        ActAcCentralVM actAcCentralVM2 = this.viewModel;
        arrayList.add(actAcCentralVM2.getWindSpeedInfo(actAcCentralVM2.currentWindSpeed).setEnable(getParam().getOn() > 0));
        return arrayList;
    }

    public CentralAirAcQuickDialog selectState() {
        this.viewModel.sendQueryCmd(CmdBleFactory.queryCentralAirState(getParam().getOutAddr(), getParam().getInAddr()), getParam().getUnicastAddress());
        return this;
    }

    private void setStatus(String deviceData) {
        CentralAirSubDeviceParam param = getParam();
        CentralAirSubDeviceParam centralAirSubDeviceParam = new CentralAirSubDeviceParam(param.getUnicastAddress(), deviceData, param.getName(), param.getPosition());
        this.device.setParam(centralAirSubDeviceParam);
        this.currentState.setValue(centralAirSubDeviceParam);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<Integer> getTempList() {
        ArrayList arrayList = new ArrayList();
        for (int i = 16; i <= 30; i++) {
            arrayList.add(Integer.valueOf(i));
        }
        return arrayList;
    }

    public CentralAirAcQuickDialog setDialogCallback(OnDialogCallback mDialogCallback) {
        this.mDialogCallback = mDialogCallback;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "ac_quick_dialog";
    }
}