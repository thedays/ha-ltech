package com.ltech.smarthome.view.dialog;

import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzy.tvmao.KKACZipManagerV2;
import com.hzy.tvmao.ir.ac.ACStateV2;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogAcQuickBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.ui.device.ir.AcRepository;
import com.ltech.smarthome.ui.device.ir.IrKeyItem;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.ltech.smarthome.view.dialog.AcQuickDialog;
import com.ltech.smarthome.view.layoutmanager.GalleryLayoutManager;
import com.ltech.smarthome.view.layoutmanager.ScaleTransformer;
import com.smart.message.MessageManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class AcQuickDialog extends SmartDialog<DialogAcQuickBinding> {
    public MutableLiveData<ACStateV2> currentState = new MutableLiveData<>();
    private Device device;
    private KKACZipManagerV2 kkacZipManagerV2;
    private OnDialogCallback mDialogCallback;
    private String title;

    public interface OnDialogCallback {
        void dismiss(String stateString);

        void onCmdSend(byte[] cmd);

        void onMoreAction(String stateString);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_ac_quick;
    }

    public static AcQuickDialog asDefault() {
        final AcQuickDialog acQuickDialog = new AcQuickDialog();
        acQuickDialog.setViewConverter(acQuickDialog.new AnonymousClass1()).setDismissRunnable(new Runnable() { // from class: com.ltech.smarthome.view.dialog.AcQuickDialog$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AcQuickDialog.lambda$asDefault$0(AcQuickDialog.this);
            }
        }).setMargin(30).setGravity(17);
        return acQuickDialog;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.AcQuickDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogAcQuickBinding, AcQuickDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogAcQuickBinding viewBinding, final AcQuickDialog dialog) {
            final Animation.AnimationListener animationListener = new Animation.AnimationListener(this) { // from class: com.ltech.smarthome.view.dialog.AcQuickDialog.1.1
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
            final BaseQuickAdapter<IrKeyItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<IrKeyItem, BaseViewHolder>(this, R.layout.item_ir_key, dialog.getItemList()) { // from class: com.ltech.smarthome.view.dialog.AcQuickDialog.1.2
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, IrKeyItem item) {
                    helper.getView(R.id.layout_bg).getLayoutParams().height = viewBinding.rvContent.getMeasuredHeight();
                    helper.setImageResource(R.id.iv_icon, item.getIconRes()).setText(R.id.tv_name, item.getName());
                    helper.setEnabled(R.id.tv_name, item.isEnable());
                }
            };
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.AcQuickDialog$1$$ExternalSyntheticLambda1
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    AcQuickDialog.AnonymousClass1.lambda$convertView$1(BaseQuickAdapter.this, animationListener, dialog, baseQuickAdapter2, view, i);
                }
            });
            baseQuickAdapter.bindToRecyclerView(viewBinding.rvContent);
            viewBinding.rvContent.setLayoutManager(new GridLayoutManager(dialog.getContext(), 4));
            viewBinding.rvContent.setHasFixedSize(true);
            ((DefaultItemAnimator) viewBinding.rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
            final BaseQuickAdapter<Integer, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<Integer, BaseViewHolder>(this, R.layout.item_temp, dialog.getTempList()) { // from class: com.ltech.smarthome.view.dialog.AcQuickDialog.1.3
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, Integer item) {
                    helper.getView(R.id.layout_bg).getLayoutParams().height = viewBinding.rvContent.getMeasuredHeight();
                    helper.getView(R.id.layout_bg).getLayoutParams().width = viewBinding.rvContent.getMeasuredWidth() / 6;
                    helper.setText(R.id.tv_temp, item + "°");
                    ((AppCompatTextView) helper.getView(R.id.tv_temp)).getPaint().setFakeBoldText(true);
                    if (dialog.kkacZipManagerV2.getPowerState() == 0 && dialog.kkacZipManagerV2.isTempCanControl() && item.intValue() == dialog.kkacZipManagerV2.getCurTemp()) {
                        helper.setTextColor(R.id.tv_temp, ContextCompat.getColor(this.mContext, R.color.color_blue));
                    } else {
                        helper.setTextColor(R.id.tv_temp, ContextCompat.getColor(this.mContext, R.color.color_text_gray));
                    }
                }
            };
            baseQuickAdapter2.bindToRecyclerView(viewBinding.rvTemp);
            viewBinding.rvTemp.setHasFixedSize(true);
            ((DefaultItemAnimator) viewBinding.rvTemp.getItemAnimator()).setSupportsChangeAnimations(false);
            GalleryLayoutManager galleryLayoutManager = new GalleryLayoutManager(0);
            galleryLayoutManager.setItemTransformer(new ScaleTransformer());
            galleryLayoutManager.attach(viewBinding.rvTemp, dialog.kkacZipManagerV2.getCurTemp() - dialog.kkacZipManagerV2.getMinTemp());
            viewBinding.rvTemp.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.view.dialog.AcQuickDialog.1.4
                @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.left = 35;
                    outRect.right = 35;
                }
            });
            dialog.currentState.observe(dialog.getViewLifecycleOwner(), new Observer() { // from class: com.ltech.smarthome.view.dialog.AcQuickDialog$1$$ExternalSyntheticLambda2
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    AcQuickDialog.AnonymousClass1.lambda$convertView$4(BaseQuickAdapter.this, dialog, viewBinding, baseQuickAdapter, (ACStateV2) obj);
                }
            });
            viewBinding.tvTitle.setText(dialog.title);
            final AcQuickDialog acQuickDialog = AcQuickDialog.this;
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.AcQuickDialog$1$$ExternalSyntheticLambda3
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    AcQuickDialog.AnonymousClass1.lambda$convertView$5(AcQuickDialog.this, acQuickDialog, baseQuickAdapter2, viewBinding, (View) obj);
                }
            }));
            if (!dialog.device.getReportinstruct().isEmpty()) {
                dialog.refreshData(dialog.device.getReportinstruct());
            }
            MessageManager.getInstance().setReceiveDataCallBack(new MessageManager.ReceiveDataCallBack() { // from class: com.ltech.smarthome.view.dialog.AcQuickDialog$1$$ExternalSyntheticLambda4
                @Override // com.smart.message.MessageManager.ReceiveDataCallBack
                public final void onDataReceive(long j, String str) {
                    AcQuickDialog.AnonymousClass1.lambda$convertView$6(AcQuickDialog.this, j, str);
                }
            });
        }

        static /* synthetic */ void lambda$convertView$1(BaseQuickAdapter baseQuickAdapter, Animation.AnimationListener animationListener, final AcQuickDialog acQuickDialog, BaseQuickAdapter baseQuickAdapter2, View view, final int i) {
            if (((IrKeyItem) baseQuickAdapter.getData().get(i)).isEnable()) {
                ViewHelpUtil.zoomInZoomOut(view, animationListener);
                ThreadUtils.runOnUiThreadDelayed(new Runnable() { // from class: com.ltech.smarthome.view.dialog.AcQuickDialog$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AcQuickDialog.AnonymousClass1.lambda$convertView$0(i, acQuickDialog);
                    }
                }, 250L);
            }
        }

        static /* synthetic */ void lambda$convertView$0(int i, AcQuickDialog acQuickDialog) {
            if (i == 0) {
                acQuickDialog.changePower();
                return;
            }
            if (i == 1) {
                acQuickDialog.changeMode();
            } else if (i == 2) {
                acQuickDialog.changeWind();
            } else {
                acQuickDialog.changeDirect();
            }
        }

        static /* synthetic */ void lambda$convertView$4(final BaseQuickAdapter baseQuickAdapter, final AcQuickDialog acQuickDialog, final DialogAcQuickBinding dialogAcQuickBinding, BaseQuickAdapter baseQuickAdapter2, ACStateV2 aCStateV2) {
            if (1 != aCStateV2.getCurPowerState() && aCStateV2.tempIsCanControl()) {
                baseQuickAdapter.setNewData(acQuickDialog.getTempList());
                dialogAcQuickBinding.rvTemp.setOnFlingListener(null);
                dialogAcQuickBinding.rvTemp.clearOnScrollListeners();
                GalleryLayoutManager galleryLayoutManager = new GalleryLayoutManager(0);
                galleryLayoutManager.setOnItemSelectedListener(new GalleryLayoutManager.OnItemSelectedListener() { // from class: com.ltech.smarthome.view.dialog.AcQuickDialog$1$$ExternalSyntheticLambda6
                    @Override // com.ltech.smarthome.view.layoutmanager.GalleryLayoutManager.OnItemSelectedListener
                    public final void onItemSelected(RecyclerView recyclerView, View view, int i, boolean z) {
                        AcQuickDialog.AnonymousClass1.lambda$convertView$3(AcQuickDialog.this, baseQuickAdapter, dialogAcQuickBinding, recyclerView, view, i, z);
                    }
                });
                galleryLayoutManager.setItemTransformer(new ScaleTransformer());
                galleryLayoutManager.attach(dialogAcQuickBinding.rvTemp, aCStateV2.getCurTemp() - aCStateV2.modelMinTemp());
            } else {
                if (dialogAcQuickBinding.rvTemp.getLayoutManager() != null) {
                    ((GalleryLayoutManager) dialogAcQuickBinding.rvTemp.getLayoutManager()).setCanScroll(false);
                }
                baseQuickAdapter.notifyDataSetChanged();
            }
            baseQuickAdapter2.setNewData(acQuickDialog.getItemList());
        }

        static /* synthetic */ void lambda$convertView$3(AcQuickDialog acQuickDialog, final BaseQuickAdapter baseQuickAdapter, final DialogAcQuickBinding dialogAcQuickBinding, RecyclerView recyclerView, View view, int i, boolean z) {
            if (z) {
                return;
            }
            acQuickDialog.changeTemperature(((Integer) baseQuickAdapter.getData().get(i)).intValue());
            dialogAcQuickBinding.ivSendTip.setBackgroundResource(R.drawable.shape_circle_red);
            ThreadUtils.runOnUiThreadDelayed(new Runnable() { // from class: com.ltech.smarthome.view.dialog.AcQuickDialog$1$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    AcQuickDialog.AnonymousClass1.lambda$convertView$2(BaseQuickAdapter.this, dialogAcQuickBinding);
                }
            }, 200L);
        }

        static /* synthetic */ void lambda$convertView$2(BaseQuickAdapter baseQuickAdapter, DialogAcQuickBinding dialogAcQuickBinding) {
            baseQuickAdapter.notifyDataSetChanged();
            dialogAcQuickBinding.ivSendTip.setBackgroundResource(R.drawable.shape_circle_gray);
        }

        static /* synthetic */ void lambda$convertView$5(AcQuickDialog acQuickDialog, AcQuickDialog acQuickDialog2, BaseQuickAdapter baseQuickAdapter, DialogAcQuickBinding dialogAcQuickBinding, View view) {
            switch (view.getId()) {
                case R.id.iv_device_more /* 2131297013 */:
                    acQuickDialog.dismissDialog();
                    if (acQuickDialog.mDialogCallback != null) {
                        acQuickDialog.mDialogCallback.onMoreAction(acQuickDialog2.kkacZipManagerV2.getACStateV2InString());
                        break;
                    }
                    break;
                case R.id.layout_temp_left /* 2131297678 */:
                    if (acQuickDialog.kkacZipManagerV2.getPowerState() == 0) {
                        int max = Math.max(acQuickDialog.kkacZipManagerV2.getCurTemp() - 1, ((Integer) acQuickDialog.getTempList().get(0)).intValue());
                        int indexOf = acQuickDialog.getTempList().indexOf(Integer.valueOf(max));
                        if (indexOf < baseQuickAdapter.getData().size()) {
                            dialogAcQuickBinding.rvTemp.smoothScrollToPosition(indexOf);
                        }
                        acQuickDialog.changeTemperature(max);
                        break;
                    }
                    break;
                case R.id.layout_temp_right /* 2131297679 */:
                    if (acQuickDialog.kkacZipManagerV2.getPowerState() == 0) {
                        int min = Math.min(acQuickDialog.kkacZipManagerV2.getCurTemp() + 1, ((Integer) acQuickDialog.getTempList().get(acQuickDialog.getTempList().size() - 1)).intValue());
                        int indexOf2 = acQuickDialog.getTempList().indexOf(Integer.valueOf(min));
                        if (indexOf2 < baseQuickAdapter.getData().size()) {
                            dialogAcQuickBinding.rvTemp.smoothScrollToPosition(indexOf2);
                        }
                        acQuickDialog.changeTemperature(min);
                        break;
                    }
                    break;
            }
        }

        static /* synthetic */ void lambda$convertView$6(AcQuickDialog acQuickDialog, long j, String str) {
            if (!TextUtils.isEmpty(str) && acQuickDialog.device.getDeviceId() == j && str.length() >= 24) {
                acQuickDialog.refreshData(str);
            }
        }
    }

    static /* synthetic */ void lambda$asDefault$0(AcQuickDialog acQuickDialog) {
        OnDialogCallback onDialogCallback = acQuickDialog.mDialogCallback;
        if (onDialogCallback != null) {
            onDialogCallback.dismiss(acQuickDialog.kkacZipManagerV2.getACStateV2InString());
        }
    }

    public void refreshData(String reportInstruct) {
        if (!TextUtils.isEmpty(reportInstruct) && reportInstruct.length() >= 24) {
            setAcManagerData(Integer.parseInt(reportInstruct.substring(14, 16), 16), Integer.parseInt(reportInstruct.substring(18, 20), 16), Integer.parseInt(reportInstruct.substring(16, 18), 16), Integer.parseInt(reportInstruct.substring(20, 22), 16), Integer.parseInt(reportInstruct.substring(22, 24), 16));
            this.device.setReportinstruct(reportInstruct);
            Injection.repo().device().saveDevice(this.device);
        }
    }

    private void setAcManagerData(int isOpen, int mode, int temp, int windSpeed, int direction) {
        if (isOpen != this.kkacZipManagerV2.getPowerState()) {
            this.kkacZipManagerV2.changePowerState();
        }
        this.kkacZipManagerV2.changeACTargetModel(mode);
        this.kkacZipManagerV2.getACCurModel().setCurTmp(temp);
        this.kkacZipManagerV2.getACCurModel().setCurWindSpeed(windSpeed);
        if (direction != this.kkacZipManagerV2.getCurUDDirect()) {
            this.kkacZipManagerV2.changeUDWindDirect(direction == 0 ? ACStateV2.UDWindDirectKey.UDDIRECT_KEY_SWING : ACStateV2.UDWindDirectKey.UDDIRECT_KEY_FIX);
        }
        this.currentState.setValue((ACStateV2) GsonUtils.fromJson(this.kkacZipManagerV2.getACStateV2InString(), ACStateV2.class));
    }

    public AcQuickDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public AcQuickDialog initManager(KKACZipManagerV2 kkacZipManagerV2) {
        this.kkacZipManagerV2 = kkacZipManagerV2;
        refreshState();
        return this;
    }

    public AcQuickDialog setDevice(Device device) {
        this.device = device;
        return this;
    }

    private void refreshState() {
        this.currentState.setValue((ACStateV2) GsonUtils.fromJson(this.kkacZipManagerV2.getACStateV2InString(), ACStateV2.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<IrKeyItem> getItemList() {
        ArrayList arrayList = new ArrayList();
        if (this.kkacZipManagerV2.getPowerState() == 0) {
            arrayList.add(new IrKeyItem(R.mipmap.icon_ir_power_blue, R.string.ir_power, -1).setEnable(true));
        } else {
            arrayList.add(new IrKeyItem(R.mipmap.icon_ir_power, R.string.ir_power, -1).setEnable(true));
        }
        arrayList.add(AcRepository.getAcModeInfo(this.kkacZipManagerV2.getCurModelType()).setEnable(this.kkacZipManagerV2.getPowerState() == 0));
        arrayList.add(AcRepository.getWindSpeedInfo(this.kkacZipManagerV2.getCurWindSpeed()).setEnable(this.kkacZipManagerV2.getPowerState() == 0 && this.kkacZipManagerV2.isWindSpeedCanControl()));
        arrayList.add(AcRepository.getDirectTypeInfo(true).setEnable(this.kkacZipManagerV2.getPowerState() == 0));
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<Integer> getTempList() {
        ArrayList arrayList = new ArrayList();
        if (this.kkacZipManagerV2.getMinTemp() < 0 || this.kkacZipManagerV2.getMaxTemp() < 0) {
            for (int i = 16; i <= 30; i++) {
                arrayList.add(Integer.valueOf(i));
            }
        } else {
            for (int minTemp = this.kkacZipManagerV2.getMinTemp(); minTemp <= this.kkacZipManagerV2.getMaxTemp(); minTemp++) {
                arrayList.add(Integer.valueOf(minTemp));
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeMode() {
        this.kkacZipManagerV2.changeACModel();
        sendIrControl();
        refreshState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeWind() {
        this.kkacZipManagerV2.changeWindSpeed();
        sendIrControl();
        refreshState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeDirect() {
        this.kkacZipManagerV2.changeUDWindDirect(ACStateV2.UDWindDirectKey.UDDIRECT_KEY_SWING);
        sendIrControl();
        refreshState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeTemperature(int temp) {
        this.kkacZipManagerV2.setTargetTemp(temp);
        sendIrControl();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changePower() {
        this.kkacZipManagerV2.changePowerState();
        sendIrControl();
        refreshState();
    }

    private void sendIrControl() {
        OnDialogCallback onDialogCallback = this.mDialogCallback;
        if (onDialogCallback != null) {
            onDialogCallback.onCmdSend(this.kkacZipManagerV2.getACKeyIr());
        }
    }

    public AcQuickDialog setDialogCallback(OnDialogCallback mDialogCallback) {
        this.mDialogCallback = mDialogCallback;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "ac_quick_dialog";
    }
}