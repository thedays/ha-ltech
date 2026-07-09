package com.ltech.smarthome.view.dialog;

import android.graphics.Color;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
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
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.device.dalipro.CgdActionSelectView;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.SelectDaliPowerStateDialog;
import com.smart.message.ResponseMsg;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class SelectDaliPowerStateDialog extends SmartDialog<DialogPowerStateSelectorBinding> {
    public static final int TYPE_FAIL = 2;
    private int brt;
    private int color;
    private Object controlObject;
    public List<OnOffState> dataList;
    private MultipleItemRvAdapter<OnOffState, BaseViewHolder> mAdapter;
    private OnSaveListener mOnSaveListener;
    private String saveText;
    private int stateType;
    private String title;
    private int wy;
    private int zoneNum;
    private int selectPosition = -1;
    private int lastSelectPosition = -1;

    public interface OnSaveListener {
        void cancel();

        boolean onSave(OnOffState onOffState, int brt, int wy, int color);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_power_state_selector;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectDaliPowerStateDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogPowerStateSelectorBinding, SelectDaliPowerStateDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogPowerStateSelectorBinding viewBinding, final SelectDaliPowerStateDialog dialog) {
            dialog.initRv(viewBinding);
            if (dialog.saveText != null) {
                viewBinding.tvSave.setText(dialog.saveText);
            }
            if (dialog.title != null) {
                viewBinding.appCompatTextView33.setText(dialog.title);
            }
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectDaliPowerStateDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SelectDaliPowerStateDialog.AnonymousClass1.lambda$convertView$0(SelectDaliPowerStateDialog.this, (View) obj);
                }
            }));
        }

        /* JADX WARN: Multi-variable type inference failed */
        static /* synthetic */ void lambda$convertView$0(SelectDaliPowerStateDialog selectDaliPowerStateDialog, View view) {
            int id = view.getId();
            if (id != R.id.tv_cancel) {
                if (id == R.id.tv_save && selectDaliPowerStateDialog.selectPosition != -1) {
                    if (selectDaliPowerStateDialog.mOnSaveListener != null) {
                        if (selectDaliPowerStateDialog.mOnSaveListener.onSave((OnOffState) selectDaliPowerStateDialog.mAdapter.getItem(selectDaliPowerStateDialog.selectPosition), selectDaliPowerStateDialog.brt, selectDaliPowerStateDialog.wy, selectDaliPowerStateDialog.color)) {
                            selectDaliPowerStateDialog.dismissDialog();
                            return;
                        }
                        return;
                    }
                    selectDaliPowerStateDialog.dismissDialog();
                    return;
                }
                return;
            }
            selectDaliPowerStateDialog.dismissDialog();
            if (selectDaliPowerStateDialog.mOnSaveListener != null) {
                selectDaliPowerStateDialog.mOnSaveListener.cancel();
            }
        }
    }

    public static SelectDaliPowerStateDialog asDefault() {
        return (SelectDaliPowerStateDialog) new SelectDaliPowerStateDialog().setViewConverter(new AnonymousClass1()).setGravity(80).setMargin(16).setY(16).setOutCancel(false);
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectDaliPowerStateDialog$2, reason: invalid class name */
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
            this.mProviderDelegate.registerProvider(new BaseItemProvider<OnOffState, BaseViewHolder>() { // from class: com.ltech.smarthome.view.dialog.SelectDaliPowerStateDialog.2.1
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
                    if (helper.getAdapterPosition() == SelectDaliPowerStateDialog.this.selectPosition) {
                        helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_tick_sel).setGone(R.id.iv_select, true);
                    } else {
                        helper.setGone(R.id.iv_select, false);
                    }
                }
            });
            this.mProviderDelegate.registerProvider(new BaseItemProvider<OnOffState, BaseViewHolder>() { // from class: com.ltech.smarthome.view.dialog.SelectDaliPowerStateDialog.2.2
                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int layout() {
                    return R.layout.item_select_subtext_ver_onoff_diy_dali;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int viewType() {
                    return OnOffState.TYPE_DIY;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void convert(BaseViewHolder helper, OnOffState data, int position) {
                    helper.setText(R.id.tv_name, data.getName()).setText(R.id.tv_sub_name, data.getSubName());
                    ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                    if (SelectDaliPowerStateDialog.this.controlObject != null && ((Device) SelectDaliPowerStateDialog.this.controlObject).getProductId().equals(ProductId.ID_BLE_SWITCH)) {
                        helper.setGone(R.id.layout_brt, false).setGone(R.id.layout_item, false);
                        return;
                    }
                    CgdActionSelectView cgdActionSelectView = (CgdActionSelectView) helper.getView(R.id.cgd_action_view);
                    if (SelectDaliPowerStateDialog.this.controlObject != null) {
                        cgdActionSelectView.setRole((Role) SelectDaliPowerStateDialog.this.controlObject);
                        cgdActionSelectView.setBrt(SelectDaliPowerStateDialog.this.brt);
                        cgdActionSelectView.setCt(255 - SelectDaliPowerStateDialog.this.wy);
                        cgdActionSelectView.setColor(SelectDaliPowerStateDialog.this.color);
                    } else {
                        cgdActionSelectView.initAll(null);
                    }
                    if (helper.getAdapterPosition() == SelectDaliPowerStateDialog.this.selectPosition) {
                        helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_tick_sel).setGone(R.id.iv_select, true);
                        cgdActionSelectView.setEnabled(true);
                    } else {
                        helper.setGone(R.id.iv_select, false);
                        cgdActionSelectView.setEnabled(false);
                    }
                    cgdActionSelectView.setBrt(LightUtils.brt2ProgressHasBelowZero(SelectDaliPowerStateDialog.this.brt));
                    cgdActionSelectView.setCallback(new CgdActionSelectView.OnDialogCallback() { // from class: com.ltech.smarthome.view.dialog.SelectDaliPowerStateDialog.2.2.1
                        @Override // com.ltech.smarthome.ui.device.dalipro.CgdActionSelectView.OnDialogCallback
                        public void onColorChanged(float xProgress, int color, boolean finish) {
                            SelectDaliPowerStateDialog.this.setColor(color);
                            if (finish) {
                                CmdAssistant.getLightCmdAssistant(SelectDaliPowerStateDialog.this.controlObject, new int[0]).sendRgb(SelectDaliPowerStateDialog.this.getContext(), color, true);
                            }
                        }

                        @Override // com.ltech.smarthome.ui.device.dalipro.CgdActionSelectView.OnDialogCallback
                        public void onBrtChanged(int progress, boolean finish) {
                            SelectDaliPowerStateDialog.this.setBrt(LightUtils.progress2BrtHasBelowOne(progress));
                            if (finish) {
                                CmdAssistant.getLightCmdAssistant(SelectDaliPowerStateDialog.this.controlObject, new int[0]).sendDimBrtD1Has1to9(SelectDaliPowerStateDialog.this.getContext(), progress, true);
                            }
                        }

                        @Override // com.ltech.smarthome.ui.device.dalipro.CgdActionSelectView.OnDialogCallback
                        public void onCtChanged(int v, boolean finish) {
                            SelectDaliPowerStateDialog.this.setWy(255 - v);
                            if (finish) {
                                CmdAssistant.getLightCmdAssistant(SelectDaliPowerStateDialog.this.controlObject, new int[0]).sendCW(SelectDaliPowerStateDialog.this.getContext(), v, true);
                            }
                        }
                    });
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRv(DialogPowerStateSelectorBinding viewBinding) {
        this.dataList = getContentList();
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(getContentList());
        this.mAdapter = anonymousClass2;
        anonymousClass2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.SelectDaliPowerStateDialog$$ExternalSyntheticLambda2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                SelectDaliPowerStateDialog.this.lambda$initRv$0(baseQuickAdapter, view, i);
            }
        });
        this.mAdapter.finishInitialize();
        this.mAdapter.bindToRecyclerView(((DialogPowerStateSelectorBinding) this.mViewBinding).rvContent);
        ((DialogPowerStateSelectorBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        ((DialogPowerStateSelectorBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((DialogPowerStateSelectorBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
        queryState();
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

    public static List<OnOffState> getContentList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new OnOffState(OnOffState.TYPE_DEFAULT, ActivityUtils.getTopActivity().getString(R.string.brt_100), ActivityUtils.getTopActivity().getString(R.string.app_str_power_brt100), 1));
        arrayList.add(new OnOffState(OnOffState.TYPE_DEFAULT, ActivityUtils.getTopActivity().getString(R.string.brt_0), ActivityUtils.getTopActivity().getString(R.string.app_str_power_brt0), 2));
        arrayList.add(new OnOffState(OnOffState.TYPE_DEFAULT, ActivityUtils.getTopActivity().getString(R.string.state_memory), ActivityUtils.getTopActivity().getString(R.string.app_str_power_brt_remember), 3));
        arrayList.add(new OnOffState(OnOffState.TYPE_DIY, ActivityUtils.getTopActivity().getString(R.string.brt_diy), ActivityUtils.getTopActivity().getString(R.string.app_str_power_brt_diy), 4));
        return arrayList;
    }

    private void queryState() {
        if (this.stateType == 2) {
            CmdAssistant.getLightCmdAssistant(this.controlObject, this.zoneNum).queryFailState(getContext(), new IAction() { // from class: com.ltech.smarthome.view.dialog.SelectDaliPowerStateDialog$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    SelectDaliPowerStateDialog.this.lambda$queryState$1((ResponseMsg) obj);
                }
            });
        } else {
            CmdAssistant.getLightCmdAssistant(this.controlObject, this.zoneNum).queryOnState(getContext(), new IAction() { // from class: com.ltech.smarthome.view.dialog.SelectDaliPowerStateDialog$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    SelectDaliPowerStateDialog.this.lambda$queryState$2((ResponseMsg) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryState$1(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getResData() == null || responseMsg.getResData().length() < 18) {
            return;
        }
        int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 18), 16);
        int size = this.mAdapter.getData().size();
        for (int i = 0; i < size; i++) {
            if (parseInt == this.mAdapter.getData().get(i).mainValue) {
                this.selectPosition = i;
                if (responseMsg.getResData().length() > 18) {
                    setBrt(Integer.parseInt(responseMsg.getResData().substring(18, 20), 16));
                    if (responseMsg.getResData().length() > 22) {
                        int parseInt2 = Integer.parseInt(responseMsg.getResData().substring(22, 24), 16);
                        int parseInt3 = Integer.parseInt(responseMsg.getResData().substring(24, 26), 16);
                        int parseInt4 = Integer.parseInt(responseMsg.getResData().substring(26, 28), 16);
                        if (parseInt2 == 0 && parseInt3 == 0 && parseInt4 == 0) {
                            parseInt2 = 255;
                        }
                        setColor(Color.rgb(parseInt2, parseInt3, parseInt4));
                        setWy(Integer.parseInt(responseMsg.getResData().substring(30, 32), 16));
                    }
                }
                this.mAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryState$2(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getResData() == null || responseMsg.getResData().length() < 18) {
            return;
        }
        int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 18), 16);
        int size = this.mAdapter.getData().size();
        for (int i = 0; i < size; i++) {
            if (parseInt == this.mAdapter.getData().get(i).mainValue) {
                this.selectPosition = i;
                if (responseMsg.getResData().length() > 18) {
                    setBrt(Integer.parseInt(responseMsg.getResData().substring(18, 20), 16));
                    if (responseMsg.getResData().length() > 22) {
                        int parseInt2 = Integer.parseInt(responseMsg.getResData().substring(22, 24), 16);
                        int parseInt3 = Integer.parseInt(responseMsg.getResData().substring(24, 26), 16);
                        int parseInt4 = Integer.parseInt(responseMsg.getResData().substring(26, 28), 16);
                        if (parseInt2 == 0 && parseInt3 == 0 && parseInt4 == 0) {
                            parseInt2 = 255;
                        }
                        setColor(Color.rgb(parseInt2, parseInt3, parseInt4));
                        setWy(Integer.parseInt(responseMsg.getResData().substring(30, 32), 16));
                    }
                }
                this.mAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    public static final class OnOffState {
        private static int TYPE_DEFAULT = 1;
        private static int TYPE_DIY = 2;
        private int mainValue;
        private String name;
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
    }

    public SelectDaliPowerStateDialog controlObject(Object object) {
        this.controlObject = object;
        return this;
    }

    public SelectDaliPowerStateDialog setZoneNum(int zoneNum) {
        this.zoneNum = zoneNum;
        return this;
    }

    public SelectDaliPowerStateDialog setStateType(int stateType) {
        this.stateType = stateType;
        return this;
    }

    public SelectDaliPowerStateDialog setOnSaveListener(OnSaveListener mOnSaveListener) {
        this.mOnSaveListener = mOnSaveListener;
        return this;
    }

    public SelectDaliPowerStateDialog setSaveText(String saveText) {
        this.saveText = saveText;
        return this;
    }

    public SelectDaliPowerStateDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public SelectDaliPowerStateDialog setBrt(int brt) {
        this.brt = brt;
        return this;
    }

    public SelectDaliPowerStateDialog setWy(int wy) {
        this.wy = wy;
        return this;
    }

    public SelectDaliPowerStateDialog setColor(int color) {
        this.color = color;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "dialog_power_state_selector";
    }
}