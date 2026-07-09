package com.ltech.smarthome.view.dialog;

import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogSpQuickBinding;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class SpQuickDialog extends SmartDialog<DialogSpQuickBinding> {
    public static DialogSpQuickBinding mViewBinding;
    private OnDialogCallback mCallback;
    private int mShowMax;
    private Boolean online;
    private List<RelateInfoItem> relatedInfoList;
    protected String deviceName = "";
    public boolean[] selectArray = null;

    public interface OnDialogCallback {
        void onItemClick(int position, boolean state);

        void onMoreAction();
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_sp_quick;
    }

    public static SpQuickDialog asDefault() {
        return (SpQuickDialog) new SpQuickDialog().setViewConverter(new SmartDialog.ViewConverter<DialogSpQuickBinding, SpQuickDialog>() { // from class: com.ltech.smarthome.view.dialog.SpQuickDialog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(DialogSpQuickBinding viewBinding, SpQuickDialog dialog) {
                SpQuickDialog.mViewBinding = viewBinding;
                dialog.initDefault(viewBinding, dialog);
            }
        }).setGravity(17).setMargin(30);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initDefault(final DialogSpQuickBinding viewBinding, final SpQuickDialog dialog) {
        String format;
        if (viewBinding == null || this.relatedInfoList == null) {
            return;
        }
        AppCompatTextView appCompatTextView = viewBinding.tvTitle;
        if (this.online == null) {
            format = this.deviceName;
        } else {
            format = String.format("%s|%s", this.deviceName, requireContext().getString(this.online.booleanValue() ? R.string.online : R.string.offline));
        }
        appCompatTextView.setText(format);
        viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SpQuickDialog$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                SpQuickDialog.this.lambda$initDefault$0((View) obj);
            }
        }));
        final BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<RelateInfoItem, BaseViewHolder>(R.layout.item_sp_btn_key, this.relatedInfoList.subList(0, this.mShowMax)) { // from class: com.ltech.smarthome.view.dialog.SpQuickDialog.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, RelateInfoItem item) {
                helper.getView(R.id.layout_bg).getLayoutParams().height = viewBinding.rvContent.getMeasuredHeight();
                if (item.infoName != null && !TextUtils.isEmpty(item.infoName)) {
                    helper.setText(R.id.tv_device_name, item.infoName);
                } else {
                    helper.setText(R.id.tv_device_name, ActivityUtils.getTopActivity().getResources().getStringArray(R.array.smart_panel_s4_key_select)[helper.getAdapterPosition()]);
                }
                RelatedInfoExtParam.RelateInfo relateInfo = item.relateInfo;
                int i = R.mipmap.icon_ir_power_blue;
                if (relateInfo != null && item.relateInfo.objectId != 0 && item.type != 0) {
                    helper.setImageResource(R.id.iv_switch, R.mipmap.icon_ir_power_blue);
                    return;
                }
                if (!SpQuickDialog.this.selectArray[helper.getAdapterPosition()]) {
                    i = R.mipmap.icon_ir_power;
                }
                helper.setImageResource(R.id.iv_switch, i);
            }
        };
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.SpQuickDialog$$ExternalSyntheticLambda1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                SpQuickDialog.this.lambda$initDefault$1(baseQuickAdapter, dialog, baseQuickAdapter2, view, i);
            }
        });
        baseQuickAdapter.bindToRecyclerView(viewBinding.rvContent);
        viewBinding.rvContent.setLayoutManager(new GridLayoutManager(dialog.getContext(), 4));
        viewBinding.rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) viewBinding.rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDefault$0(View view) {
        if (view.getId() != R.id.iv_device_more) {
            return;
        }
        OnDialogCallback onDialogCallback = this.mCallback;
        if (onDialogCallback != null) {
            onDialogCallback.onMoreAction();
        }
        dismissDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDefault$1(BaseQuickAdapter baseQuickAdapter, SpQuickDialog spQuickDialog, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
        this.selectArray[i] = !r3[i];
        baseQuickAdapter.notifyItemChanged(i);
        OnDialogCallback onDialogCallback = spQuickDialog.mCallback;
        if (onDialogCallback != null) {
            onDialogCallback.onItemClick(i, this.selectArray[i]);
        }
    }

    public SpQuickDialog setOnline(boolean online) {
        this.online = Boolean.valueOf(online);
        return this;
    }

    public SpQuickDialog setPanelStatus(boolean[] status) {
        this.selectArray = status;
        return this;
    }

    public SpQuickDialog setRelateInfoList(List<RelateInfoItem> relatedInfoList) {
        if (relatedInfoList == null) {
            this.relatedInfoList = new ArrayList();
        }
        this.relatedInfoList = relatedInfoList;
        if (this.selectArray == null) {
            this.selectArray = new boolean[relatedInfoList.size()];
        }
        if (this.mShowMax == 0) {
            this.mShowMax = this.relatedInfoList.size();
        }
        return this;
    }

    public SpQuickDialog setDeviceName(String deviceName) {
        this.deviceName = deviceName;
        return this;
    }

    public SpQuickDialog setCallback(OnDialogCallback callback) {
        this.mCallback = callback;
        return this;
    }

    public SpQuickDialog setShowMax(int max) {
        this.mShowMax = max;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return getClass().getSimpleName();
    }
}