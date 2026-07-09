package com.ltech.smarthome.ui.device.central.tepanel;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogTePanelBinding;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.G4teBleParam;
import com.ltech.smarthome.ui.device.central.tepanel.TePanelQuickDialog;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.view.SwitchButton;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class TePanelQuickDialog extends SmartDialog<DialogTePanelBinding> {
    private long deviceId;
    private OnDialogCallback dialogCallback;
    private String title;
    private ActTePanelVM viewModel;
    private List<Fragment> fragmentList = new ArrayList();
    private MutableLiveData<Integer> chooseTabEvent = new MutableLiveData<>(0);
    private int subType = 1;

    public interface OnDialogCallback {
        void dismiss();

        void onMoreAction();
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_te_panel;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return getClass().getSimpleName();
    }

    @Override // com.ltech.smarthome.base.SmartDialog, com.ltech.smarthome.base.BaseDialog, androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = (ActTePanelVM) new ViewModelProvider((VMActivity) ActivityUtils.getTopActivity()).get(ActTePanelVM.class);
    }

    /* renamed from: com.ltech.smarthome.ui.device.central.tepanel.TePanelQuickDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogTePanelBinding, TePanelQuickDialog> {
        final /* synthetic */ Device val$device;

        AnonymousClass1(final Device val$device) {
            this.val$device = val$device;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogTePanelBinding viewBinding, final TePanelQuickDialog dialog) {
            dialog.deviceId = this.val$device.getDeviceId();
            dialog.initFragmentList(dialog.subType);
            dialog.startObserve();
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.sb.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.central.tepanel.TePanelQuickDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                    TePanelQuickDialog.AnonymousClass1.lambda$convertView$0(TePanelQuickDialog.this, switchButton, z);
                }
            });
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.central.tepanel.TePanelQuickDialog$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    TePanelQuickDialog.AnonymousClass1.lambda$convertView$1(TePanelQuickDialog.this, (View) obj);
                }
            }));
            dialog.viewModel.controlObject.setValue(this.val$device);
        }

        static /* synthetic */ void lambda$convertView$0(TePanelQuickDialog tePanelQuickDialog, SwitchButton switchButton, boolean z) {
            if (tePanelQuickDialog.viewModel.isAcControl) {
                tePanelQuickDialog.viewModel.openOrCloseAc();
            } else {
                tePanelQuickDialog.viewModel.openOrCloseAir();
            }
        }

        static /* synthetic */ void lambda$convertView$1(TePanelQuickDialog tePanelQuickDialog, View view) {
            int id = view.getId();
            if (id == R.id.iv_device_more) {
                tePanelQuickDialog.dismissDialog();
                if (tePanelQuickDialog.dialogCallback != null) {
                    tePanelQuickDialog.dialogCallback.onMoreAction();
                    return;
                }
                return;
            }
            if (id == R.id.tv_ac) {
                tePanelQuickDialog.viewModel.isAcControl = true;
                tePanelQuickDialog.chooseTabEvent.setValue(0);
                tePanelQuickDialog.viewModel.g4teParam.setValue(tePanelQuickDialog.viewModel.g4teParam.getValue());
            } else {
                if (id != R.id.tv_air) {
                    return;
                }
                tePanelQuickDialog.viewModel.isAcControl = false;
                tePanelQuickDialog.chooseTabEvent.setValue(1);
                tePanelQuickDialog.viewModel.g4teParam.setValue(tePanelQuickDialog.viewModel.g4teParam.getValue());
            }
        }
    }

    public static TePanelQuickDialog asDefault(Device device) {
        final TePanelQuickDialog tePanelQuickDialog = new TePanelQuickDialog();
        tePanelQuickDialog.setViewConverter(new AnonymousClass1(device)).setDismissRunnable(new Runnable() { // from class: com.ltech.smarthome.ui.device.central.tepanel.TePanelQuickDialog$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                TePanelQuickDialog.lambda$asDefault$0(TePanelQuickDialog.this);
            }
        }).setMargin(16).setGravity(17);
        return tePanelQuickDialog;
    }

    static /* synthetic */ void lambda$asDefault$0(TePanelQuickDialog tePanelQuickDialog) {
        OnDialogCallback onDialogCallback = tePanelQuickDialog.dialogCallback;
        if (onDialogCallback != null) {
            onDialogCallback.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startObserve() {
        this.viewModel.controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.tepanel.TePanelQuickDialog$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                TePanelQuickDialog.this.lambda$startObserve$1((Device) obj);
            }
        });
        this.viewModel.powerOn.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.tepanel.TePanelQuickDialog$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                TePanelQuickDialog.this.lambda$startObserve$2((Boolean) obj);
            }
        });
        this.viewModel.airPowerOn.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.tepanel.TePanelQuickDialog$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                TePanelQuickDialog.this.lambda$startObserve$3((Boolean) obj);
            }
        });
        this.chooseTabEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.tepanel.TePanelQuickDialog$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                TePanelQuickDialog.this.lambda$startObserve$4((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        if (device == null) {
            return;
        }
        G4teBleParam g4teBleParam = (G4teBleParam) device.getParam(G4teBleParam.class);
        if (g4teBleParam.getSubType() == 1) {
            ((DialogTePanelBinding) this.mViewBinding).layoutTab.setVisibility(8);
        } else if (g4teBleParam.getSubType() == 2) {
            this.viewModel.isAcControl = false;
            ((DialogTePanelBinding) this.mViewBinding).layoutTab.setVisibility(8);
        } else if (g4teBleParam.getSubType() == 3) {
            this.viewModel.isAcControl = SharedPreferenceUtil.queryBooleanValue(String.valueOf(device.getDeviceId()), true);
            this.chooseTabEvent.setValue(Integer.valueOf(!this.viewModel.isAcControl ? 1 : 0));
        }
        this.viewModel.g4teParam.setValue(g4teBleParam);
        this.viewModel.queryG4teState();
        this.viewModel.initDataListener(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Boolean bool) {
        if (this.viewModel.isAcControl) {
            ((DialogTePanelBinding) this.mViewBinding).sb.setCheckedNotByUser(bool.booleanValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Boolean bool) {
        if (this.viewModel.isAcControl) {
            return;
        }
        ((DialogTePanelBinding) this.mViewBinding).sb.setCheckedNotByUser(bool.booleanValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Integer num) {
        AppCompatTextView appCompatTextView = ((DialogTePanelBinding) this.mViewBinding).tvAc;
        int intValue = num.intValue();
        int i = R.drawable.shape_white_round_bg_10;
        appCompatTextView.setBackgroundResource(intValue == 0 ? R.drawable.shape_white_round_bg_10 : 0);
        ((DialogTePanelBinding) this.mViewBinding).tvAc.setTypeface(num.intValue() == 0 ? Typeface.defaultFromStyle(1) : Typeface.defaultFromStyle(0));
        AppCompatTextView appCompatTextView2 = ((DialogTePanelBinding) this.mViewBinding).tvAir;
        if (num.intValue() != 1) {
            i = 0;
        }
        appCompatTextView2.setBackgroundResource(i);
        ((DialogTePanelBinding) this.mViewBinding).tvAir.setTypeface(num.intValue() == 1 ? Typeface.defaultFromStyle(1) : Typeface.defaultFromStyle(0));
        FragmentUtils.showHide(num.intValue(), this.fragmentList);
        SharedPreferenceUtil.edit().keepShared(String.valueOf(this.deviceId), num.intValue() == 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initFragmentList(int subType) {
        this.fragmentList.clear();
        if (subType != 2) {
            this.fragmentList.add(new FtAcDialog());
        }
        if (subType != 1) {
            this.fragmentList.add(new FtAirDialog());
        }
        FragmentUtils.add(getChildFragmentManager(), this.fragmentList, R.id.fragment_container, 0);
    }

    public TePanelQuickDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public TePanelQuickDialog setSubType(int subType) {
        this.subType = subType;
        return this;
    }

    public TePanelQuickDialog setDialogCallback(OnDialogCallback dialogCallback) {
        this.dialogCallback = dialogCallback;
        return this;
    }
}