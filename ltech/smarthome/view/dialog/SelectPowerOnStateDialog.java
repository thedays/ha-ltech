package com.ltech.smarthome.view.dialog;

import android.graphics.Rect;
import android.view.View;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogPowerStateSelectorBinding;
import com.ltech.smarthome.databinding.ViewPowerStateBleAllBinding;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.device.light.PowerState;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog;
import com.ltech.smarthome.view.helper.PowerStateViewHelper;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class SelectPowerOnStateDialog extends SmartDialog<DialogPowerStateSelectorBinding> {
    private Object controlObject;
    public List<OnOffState> dataList;
    private MultipleItemRvAdapter<OnOffState, BaseViewHolder> mAdapter;
    private OnSaveListener mOnSaveListener;
    private String saveText;
    private ViewPowerStateBleAllBinding stateBinding;
    private String title;
    private PowerStateViewHelper viewHelper;
    private int selectPosition = -1;
    private int lastSelectPosition = -1;
    private PowerState powerState = new PowerState();

    public interface OnSaveListener {
        void cancel();

        boolean onSave(OnOffState onOffState);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_power_state_selector;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogPowerStateSelectorBinding, SelectPowerOnStateDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogPowerStateSelectorBinding viewBinding, final SelectPowerOnStateDialog dialog) {
            dialog.initRv(viewBinding);
            if (dialog.saveText != null) {
                viewBinding.tvSave.setText(dialog.saveText);
            }
            if (dialog.title != null) {
                viewBinding.appCompatTextView33.setText(dialog.title);
            }
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SelectPowerOnStateDialog.AnonymousClass1.lambda$convertView$0(SelectPowerOnStateDialog.this, (View) obj);
                }
            }));
        }

        /* JADX WARN: Multi-variable type inference failed */
        static /* synthetic */ void lambda$convertView$0(SelectPowerOnStateDialog selectPowerOnStateDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                selectPowerOnStateDialog.dismissDialog();
                if (selectPowerOnStateDialog.mOnSaveListener != null) {
                    selectPowerOnStateDialog.mOnSaveListener.cancel();
                    return;
                }
                return;
            }
            if (id != R.id.tv_save) {
                return;
            }
            if (selectPowerOnStateDialog.selectPosition == -1) {
                SmartToast.showShort(R.string.please_choose);
                return;
            }
            if (selectPowerOnStateDialog.mOnSaveListener != null) {
                selectPowerOnStateDialog.powerState.state = selectPowerOnStateDialog.selectPosition + 1;
                ((OnOffState) selectPowerOnStateDialog.mAdapter.getItem(selectPowerOnStateDialog.selectPosition)).setState(selectPowerOnStateDialog.powerState);
                if (selectPowerOnStateDialog.mOnSaveListener.onSave((OnOffState) selectPowerOnStateDialog.mAdapter.getItem(selectPowerOnStateDialog.selectPosition))) {
                    selectPowerOnStateDialog.dismissDialog();
                    return;
                }
                return;
            }
            selectPowerOnStateDialog.dismissDialog();
        }
    }

    public static SelectPowerOnStateDialog asDefault() {
        return (SelectPowerOnStateDialog) new SelectPowerOnStateDialog().setViewConverter(new AnonymousClass1()).setGravity(80).setMargin(16).setY(16).setOutCancel(false);
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog$2, reason: invalid class name */
    class AnonymousClass2 extends MultipleItemRvAdapter<OnOffState, BaseViewHolder> {
        AnonymousClass2(List data) {
            super(data);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
        public int getViewType(OnOffState onOffState) {
            return onOffState.getType();
        }

        @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
        public void registerItemProvider() {
            this.mProviderDelegate.registerProvider(new BaseItemProvider<OnOffState, BaseViewHolder>() { // from class: com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog.2.1
                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int layout() {
                    return R.layout.item_select_subtext_ver;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int viewType() {
                    return OnOffState.TYPE_DEFAULT;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void convert(BaseViewHolder helper, OnOffState data, int position) {
                    helper.setText(R.id.tv_name, data.getName()).setText(R.id.tv_sub_name, data.getSubName());
                    ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                    if (helper.getAdapterPosition() == SelectPowerOnStateDialog.this.selectPosition) {
                        helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_tick_sel).setGone(R.id.iv_select, true);
                    } else {
                        helper.setGone(R.id.iv_select, false);
                    }
                }
            });
            this.mProviderDelegate.registerProvider(new BaseItemProvider<OnOffState, BaseViewHolder>() { // from class: com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog.2.2
                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int layout() {
                    return R.layout.item_select_subtext_ver_onoff_diy;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int viewType() {
                    return OnOffState.TYPE_DIY;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void convert(final BaseViewHolder helper, OnOffState data, int position) {
                    helper.setText(R.id.tv_name, data.getName()).setText(R.id.tv_sub_name, data.getSubName());
                    ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                    if (SelectPowerOnStateDialog.this.controlObject != null && (SelectPowerOnStateDialog.this.controlObject instanceof Device) && ((Device) SelectPowerOnStateDialog.this.controlObject).getProductId().equals(ProductId.ID_BLE_SWITCH)) {
                        helper.setGone(R.id.layout_brt, false).setGone(R.id.layout_item, false);
                        return;
                    }
                    if (position == SelectPowerOnStateDialog.this.selectPosition) {
                        helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_tick_sel).setGone(R.id.iv_select, true).setText(R.id.tv_brt, LightUtils.getProgressHasBelowOne(LightUtils.brt2ProgressHasBelowZero(SelectPowerOnStateDialog.this.powerState.brt))).setEnabled(R.id.sb_brt, true).setGone(R.id.layout_brt, true);
                        ((LightBrtBar) helper.getView(R.id.sb_brt)).setProgress(SelectPowerOnStateDialog.this.powerState.brt);
                        Rect bounds = ((LightBrtBar) helper.getView(R.id.sb_brt)).getProgressDrawable().getBounds();
                        ((LightBrtBar) helper.getView(R.id.sb_brt)).setProgressDrawable(ContextCompat.getDrawable(SelectPowerOnStateDialog.this.getContext(), R.drawable.style_seekbar_red));
                        ((LightBrtBar) helper.getView(R.id.sb_brt)).getProgressDrawable().setBounds(bounds);
                        ((LightBrtBar) helper.getView(R.id.sb_brt)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog.2.2.1
                            @Override // android.widget.SeekBar.OnSeekBarChangeListener
                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }

                            @Override // android.widget.SeekBar.OnSeekBarChangeListener
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                LHomeLog.i(getClass(), "progress-->" + progress);
                                helper.setText(R.id.tv_brt, ((LightBrtBar) seekBar).getProgressHasBelowOne());
                                SelectPowerOnStateDialog.this.powerState.brt = LightUtils.progress2BrtHasBelowOne(progress);
                                if (SelectPowerOnStateDialog.this.controlObject != null) {
                                    CmdAssistant.getLightCmdAssistant(SelectPowerOnStateDialog.this.controlObject, new int[0]).previewOnState(SelectPowerOnStateDialog.this.getContext(), SelectPowerOnStateDialog.this.powerState.brt, SelectPowerOnStateDialog.this.powerState.brt, false);
                                }
                            }

                            @Override // android.widget.SeekBar.OnSeekBarChangeListener
                            public void onStopTrackingTouch(SeekBar seekBar) {
                                Class<?> cls = getClass();
                                StringBuilder sb = new StringBuilder("progress-->");
                                LightBrtBar lightBrtBar = (LightBrtBar) seekBar;
                                sb.append(lightBrtBar.getProgress());
                                LHomeLog.i(cls, sb.toString());
                                helper.setText(R.id.tv_brt, lightBrtBar.getProgressHasBelowOne());
                                SelectPowerOnStateDialog.this.powerState.brt = LightUtils.progress2BrtHasBelowOne(seekBar.getProgress());
                                if (SelectPowerOnStateDialog.this.controlObject != null) {
                                    CmdAssistant.getLightCmdAssistant(SelectPowerOnStateDialog.this.controlObject, new int[0]).previewOnState(SelectPowerOnStateDialog.this.getContext(), SelectPowerOnStateDialog.this.powerState.brt, SelectPowerOnStateDialog.this.powerState.brt, false);
                                }
                            }
                        });
                        return;
                    }
                    helper.setGone(R.id.iv_select, false).setText(R.id.tv_brt, LightUtils.getProgressHasBelowOne(LightUtils.brt2ProgressHasBelowZero(SelectPowerOnStateDialog.this.powerState.brt))).setEnabled(R.id.sb_brt, false).setGone(R.id.layout_brt, true);
                    ((LightBrtBar) helper.getView(R.id.sb_brt)).setProgress(LightUtils.brt2ProgressHasBelowZero(SelectPowerOnStateDialog.this.powerState.brt));
                    Rect bounds2 = ((LightBrtBar) helper.getView(R.id.sb_brt)).getProgressDrawable().getBounds();
                    ((LightBrtBar) helper.getView(R.id.sb_brt)).setProgressDrawable(ContextCompat.getDrawable(SelectPowerOnStateDialog.this.getContext(), R.drawable.style_seekbar_gray));
                    ((LightBrtBar) helper.getView(R.id.sb_brt)).getProgressDrawable().setBounds(bounds2);
                }
            });
            this.mProviderDelegate.registerProvider(new BaseItemProvider<OnOffState, BaseViewHolder>() { // from class: com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog.2.3
                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int layout() {
                    return R.layout.item_select_subtext_ver_onoff_diy_all;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int viewType() {
                    return OnOffState.TYPE_DIY_ALL;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void convert(BaseViewHolder helper, OnOffState data, int position) {
                    helper.setText(R.id.tv_name, data.getName()).setText(R.id.tv_sub_name, data.getSubName());
                    ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                    ConstraintLayout constraintLayout = (ConstraintLayout) helper.getView(R.id.layout_state);
                    helper.setGone(R.id.layout_state, true);
                    if (SelectPowerOnStateDialog.this.stateBinding == null || SelectPowerOnStateDialog.this.viewHelper == null) {
                        SelectPowerOnStateDialog.this.stateBinding = (ViewPowerStateBleAllBinding) DataBindingUtil.bind(constraintLayout);
                        SelectPowerOnStateDialog.this.viewHelper = new PowerStateViewHelper(SelectPowerOnStateDialog.this.getActivity(), SelectPowerOnStateDialog.this.stateBinding, SelectPowerOnStateDialog.this.powerState, SelectPowerOnStateDialog.this.controlObject);
                        SelectPowerOnStateDialog.this.viewHelper.initPowerState();
                    }
                    if (position == SelectPowerOnStateDialog.this.selectPosition) {
                        helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_tick_sel).setGone(R.id.iv_select, true);
                        SelectPowerOnStateDialog.this.viewHelper.changePowerState(true);
                    } else {
                        helper.setGone(R.id.iv_select, false);
                        SelectPowerOnStateDialog.this.viewHelper.changePowerState(false);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRv(DialogPowerStateSelectorBinding viewBinding) {
        this.dataList = getContentList();
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(getContentList());
        this.mAdapter = anonymousClass2;
        anonymousClass2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog$$ExternalSyntheticLambda1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                SelectPowerOnStateDialog.this.lambda$initRv$0(baseQuickAdapter, view, i);
            }
        });
        this.mAdapter.finishInitialize();
        this.mAdapter.bindToRecyclerView(((DialogPowerStateSelectorBinding) this.mViewBinding).rvContent);
        ((DialogPowerStateSelectorBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        ((DialogPowerStateSelectorBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((DialogPowerStateSelectorBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
        if (this.selectPosition == -1) {
            queryState();
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
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0042, code lost:
    
        if (com.ltech.smarthome.model.repo.ProductRepository.getLightColorType(r1) == 26) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.util.List<com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog.OnOffState> getContentList() {
        /*
            r10 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.lang.Object r1 = r10.controlObject
            r2 = 3
            r3 = 2131821412(0x7f110364, float:1.9275566E38)
            r4 = 2131821970(0x7f110592, float:1.9276698E38)
            r5 = 2
            r6 = 1
            if (r1 == 0) goto L8d
            boolean r7 = r1 instanceof com.ltech.smarthome.model.bean.Device
            if (r7 == 0) goto L34
            com.ltech.smarthome.model.bean.Device r1 = (com.ltech.smarthome.model.bean.Device) r1
            java.lang.String r1 = r1.getProductId()
            java.lang.String r7 = "120122111301201"
            boolean r1 = r1.equals(r7)
            if (r1 != 0) goto L44
            java.lang.Object r1 = r10.controlObject
            com.ltech.smarthome.model.bean.Device r1 = (com.ltech.smarthome.model.bean.Device) r1
            java.lang.String r1 = r1.getProductId()
            java.lang.String r7 = "3895993722014848"
            boolean r1 = r1.equals(r7)
            if (r1 != 0) goto L44
        L34:
            java.lang.Object r1 = r10.controlObject
            boolean r7 = r1 instanceof com.ltech.smarthome.model.bean.Group
            if (r7 == 0) goto L8d
            com.ltech.smarthome.model.bean.Group r1 = (com.ltech.smarthome.model.bean.Group) r1
            int r1 = com.ltech.smarthome.model.repo.ProductRepository.getLightColorType(r1)
            r7 = 26
            if (r1 != r7) goto L8d
        L44:
            com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog$OnOffState r1 = new com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog$OnOffState
            int r7 = com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog.OnOffState.m4035$$Nest$sfgetTYPE_DEFAULT()
            r8 = 2131823284(0x7f110ab4, float:1.9279363E38)
            java.lang.String r8 = r10.getString(r8)
            r9 = 2131821725(0x7f11049d, float:1.9276201E38)
            java.lang.String r9 = r10.getString(r9)
            r1.<init>(r7, r8, r9, r6)
            r0.add(r1)
            com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog$OnOffState r1 = new com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog$OnOffState
            int r6 = com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog.OnOffState.m4035$$Nest$sfgetTYPE_DEFAULT()
            r7 = 2131823282(0x7f110ab2, float:1.927936E38)
            java.lang.String r7 = r10.getString(r7)
            r8 = 2131821724(0x7f11049c, float:1.92762E38)
            java.lang.String r8 = r10.getString(r8)
            r1.<init>(r6, r7, r8, r5)
            r0.add(r1)
            com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog$OnOffState r1 = new com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog$OnOffState
            int r5 = com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog.OnOffState.m4035$$Nest$sfgetTYPE_DEFAULT()
            java.lang.String r4 = r10.getString(r4)
            java.lang.String r3 = r10.getString(r3)
            r1.<init>(r5, r4, r3, r2)
            r0.add(r1)
            return r0
        L8d:
            com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog$OnOffState r1 = new com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog$OnOffState
            int r7 = com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog.OnOffState.m4035$$Nest$sfgetTYPE_DEFAULT()
            r8 = 2131821962(0x7f11058a, float:1.9276682E38)
            java.lang.String r8 = r10.getString(r8)
            r9 = 2131821410(0x7f110362, float:1.9275562E38)
            java.lang.String r9 = r10.getString(r9)
            r1.<init>(r7, r8, r9, r6)
            r0.add(r1)
            com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog$OnOffState r1 = new com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog$OnOffState
            int r6 = com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog.OnOffState.m4035$$Nest$sfgetTYPE_DEFAULT()
            r7 = 2131821960(0x7f110588, float:1.9276678E38)
            java.lang.String r7 = r10.getString(r7)
            r8 = 2131821409(0x7f110361, float:1.927556E38)
            java.lang.String r8 = r10.getString(r8)
            r1.<init>(r6, r7, r8, r5)
            r0.add(r1)
            com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog$OnOffState r1 = new com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog$OnOffState
            int r5 = com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog.OnOffState.m4035$$Nest$sfgetTYPE_DEFAULT()
            java.lang.String r4 = r10.getString(r4)
            java.lang.String r3 = r10.getString(r3)
            r1.<init>(r5, r4, r3, r2)
            r0.add(r1)
            com.ltech.smarthome.ui.device.light.PowerState r1 = r10.powerState
            boolean r1 = r1.supportAll
            r2 = 4
            r3 = 2131821969(0x7f110591, float:1.9276696E38)
            if (r1 == 0) goto Lf7
            com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog$OnOffState r1 = new com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog$OnOffState
            int r4 = com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog.OnOffState.m4037$$Nest$sfgetTYPE_DIY_ALL()
            java.lang.String r3 = r10.getString(r3)
            r5 = 2131821419(0x7f11036b, float:1.927558E38)
            java.lang.String r5 = r10.getString(r5)
            r1.<init>(r4, r3, r5, r2)
            r0.add(r1)
            return r0
        Lf7:
            com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog$OnOffState r1 = new com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog$OnOffState
            int r4 = com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog.OnOffState.m4036$$Nest$sfgetTYPE_DIY()
            java.lang.String r3 = r10.getString(r3)
            r5 = 2131821411(0x7f110363, float:1.9275564E38)
            java.lang.String r5 = r10.getString(r5)
            r1.<init>(r4, r3, r5, r2)
            r0.add(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog.getContentList():java.util.List");
    }

    public static List<OnOffState> generateSwitchList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new OnOffState(OnOffState.TYPE_DEFAULT, ActivityUtils.getTopActivity().getString(R.string.light_on), ActivityUtils.getTopActivity().getString(R.string.app_str_turn_light_on), 1));
        arrayList.add(new OnOffState(OnOffState.TYPE_DEFAULT, ActivityUtils.getTopActivity().getString(R.string.light_off), ActivityUtils.getTopActivity().getString(R.string.app_str_turn_light_off), 2));
        arrayList.add(new OnOffState(OnOffState.TYPE_DEFAULT, ActivityUtils.getTopActivity().getString(R.string.brt_remember), ActivityUtils.getTopActivity().getString(R.string.app_str_power_brt_remember), 3));
        return arrayList;
    }

    public static List<OnOffState> generateContentList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new OnOffState(OnOffState.TYPE_DEFAULT, ActivityUtils.getTopActivity().getString(R.string.brt_100), ActivityUtils.getTopActivity().getString(R.string.app_str_power_brt100), 1));
        arrayList.add(new OnOffState(OnOffState.TYPE_DEFAULT, ActivityUtils.getTopActivity().getString(R.string.brt_0), ActivityUtils.getTopActivity().getString(R.string.app_str_power_brt0), 2));
        arrayList.add(new OnOffState(OnOffState.TYPE_DEFAULT, ActivityUtils.getTopActivity().getString(R.string.brt_remember), ActivityUtils.getTopActivity().getString(R.string.app_str_power_brt_remember), 3));
        arrayList.add(new OnOffState(OnOffState.TYPE_DIY, ActivityUtils.getTopActivity().getString(R.string.brt_diy), ActivityUtils.getTopActivity().getString(R.string.app_str_power_brt_diy), 4));
        return arrayList;
    }

    private void queryState() {
        CmdAssistant.getLightCmdAssistant(this.controlObject, new int[0]).queryOnState(getContext(), new IAction() { // from class: com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                SelectPowerOnStateDialog.this.lambda$queryState$1((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryState$1(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getResData() == null || responseMsg.getResData().length() < 18) {
            return;
        }
        this.powerState = new PowerState(responseMsg.getResData().substring(16));
        int size = this.mAdapter.getData().size();
        for (int i = 0; i < size; i++) {
            if (this.powerState.state == this.mAdapter.getData().get(i).mainValue) {
                this.selectPosition = i;
                this.mAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    public static final class OnOffState {
        private static int TYPE_DEFAULT = 1;
        private static int TYPE_DIY = 2;
        private static int TYPE_DIY_ALL = 3;
        private int mainValue;
        private String name;
        private PowerState state;
        private String subName;
        private int type;

        OnOffState(int type, String name, String subName, int mainValue) {
            this.type = type;
            this.name = name;
            this.subName = subName;
            this.mainValue = mainValue;
        }

        int getType() {
            return this.type;
        }

        public String getName() {
            return this.name;
        }

        public int getMainValue() {
            return this.mainValue;
        }

        public String getSubName() {
            return this.subName;
        }

        public void setSubName(String subName) {
            this.subName = subName;
        }

        public PowerState getState() {
            return this.state;
        }

        public void setState(PowerState state) {
            this.state = state;
        }

        public int getSubValue() {
            return LightUtils.brt2ProgressHasBelowZero(this.state.brt);
        }
    }

    public void notifyDialog(ResponseMsg msg) {
        this.powerState = new PowerState(msg.getResData().substring(16));
        int size = this.mAdapter.getData().size();
        for (int i = 0; i < size; i++) {
            if (this.powerState.state == this.mAdapter.getData().get(i).mainValue) {
                this.selectPosition = i;
                this.mAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    public SelectPowerOnStateDialog controlObject(Object object) {
        this.controlObject = object;
        return this;
    }

    public SelectPowerOnStateDialog setOnSaveListener(OnSaveListener mOnSaveListener) {
        this.mOnSaveListener = mOnSaveListener;
        return this;
    }

    public SelectPowerOnStateDialog setSaveText(String saveText) {
        this.saveText = saveText;
        return this;
    }

    public SelectPowerOnStateDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public SelectPowerOnStateDialog setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        return this;
    }

    public SelectPowerOnStateDialog setSubValue(int subValue) {
        if (this.selectPosition == 3) {
            this.powerState.brt = subValue;
        }
        return this;
    }

    public SelectPowerOnStateDialog setPowerState(PowerState state) {
        if (state != null) {
            this.powerState = state;
            this.selectPosition = state.state - 1;
        }
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "dialog_power_state_selector";
    }
}