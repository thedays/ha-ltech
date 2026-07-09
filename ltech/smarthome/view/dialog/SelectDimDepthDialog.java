package com.ltech.smarthome.view.dialog;

import android.graphics.Rect;
import android.view.View;
import android.widget.SeekBar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogDimDepthSelectorBinding;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.view.dialog.SelectDimDepthDialog;
import com.smart.message.utils.LHomeLog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class SelectDimDepthDialog extends SmartDialog<DialogDimDepthSelectorBinding> {
    private static final int MAX = 21;
    private IAction<Integer> confirmAction;
    private String confirmString;
    private int progress;
    private int selectPosition = -1;
    private String title;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_dim_depth_selector;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectDimDepthDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogDimDepthSelectorBinding, SelectDimDepthDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogDimDepthSelectorBinding viewBinding, final SelectDimDepthDialog dialog) {
            final BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(this, R.layout.item_select_list, dialog.getContentList()) { // from class: com.ltech.smarthome.view.dialog.SelectDimDepthDialog.1.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, String item) {
                    helper.setText(R.id.tv_name, item).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == dialog.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
                }
            };
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.SelectDimDepthDialog$1$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    SelectDimDepthDialog.AnonymousClass1.lambda$convertView$0(SelectDimDepthDialog.this, baseQuickAdapter, viewBinding, baseQuickAdapter2, view, i);
                }
            });
            baseQuickAdapter.bindToRecyclerView(viewBinding.rvContent);
            viewBinding.rvContent.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
            viewBinding.rvContent.setHasFixedSize(true);
            ((DefaultItemAnimator) viewBinding.rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
            if (dialog.confirmString != null) {
                viewBinding.tvSave.setText(dialog.confirmString);
            }
            if (!LanguageUtils.isChinese(dialog.getContext())) {
                viewBinding.layoutDepthTip.setVisibility(8);
            }
            if (dialog.selectPosition != 0) {
                viewBinding.sbDimDepth.setProgress(dialog.selectPosition);
            } else {
                dialog.changeProgressDrawable(viewBinding, dialog.selectPosition);
            }
            if (dialog.progress >= 0 && dialog.progress < 21) {
                dialog.changeProgressView(viewBinding, dialog.progress, true);
                viewBinding.sbDimDepth.setProgress(dialog.progress);
            } else {
                dialog.setProgress(20);
                viewBinding.sbDimDepth.setProgress(20);
            }
            viewBinding.sbDimDepth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(this) { // from class: com.ltech.smarthome.view.dialog.SelectDimDepthDialog.1.2
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (dialog.selectPosition != 1) {
                        dialog.selectPosition = 1;
                        baseQuickAdapter.notifyDataSetChanged();
                        SelectDimDepthDialog selectDimDepthDialog = dialog;
                        selectDimDepthDialog.changeProgressDrawable(viewBinding, selectDimDepthDialog.selectPosition);
                    }
                    LHomeLog.i(getClass(), "progress:" + progress);
                    SelectDimDepthDialog selectDimDepthDialog2 = dialog;
                    selectDimDepthDialog2.changeProgressView(viewBinding, selectDimDepthDialog2.progress, false);
                    dialog.setProgress(progress);
                    SelectDimDepthDialog selectDimDepthDialog3 = dialog;
                    selectDimDepthDialog3.changeProgressView(viewBinding, selectDimDepthDialog3.progress, true);
                }
            });
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectDimDepthDialog$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SelectDimDepthDialog.AnonymousClass1.lambda$convertView$1(SelectDimDepthDialog.this, viewBinding, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(SelectDimDepthDialog selectDimDepthDialog, BaseQuickAdapter baseQuickAdapter, DialogDimDepthSelectorBinding dialogDimDepthSelectorBinding, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
            selectDimDepthDialog.setSelectPosition(i);
            baseQuickAdapter.notifyDataSetChanged();
            selectDimDepthDialog.changeProgressDrawable(dialogDimDepthSelectorBinding, i);
        }

        static /* synthetic */ void lambda$convertView$1(SelectDimDepthDialog selectDimDepthDialog, DialogDimDepthSelectorBinding dialogDimDepthSelectorBinding, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                selectDimDepthDialog.dismissDialog();
            } else if (id == R.id.tv_save && selectDimDepthDialog.selectPosition != -1) {
                if (selectDimDepthDialog.confirmAction != null) {
                    selectDimDepthDialog.confirmAction.act(Integer.valueOf(selectDimDepthDialog.selectPosition != 0 ? 21 - dialogDimDepthSelectorBinding.sbDimDepth.getProgress() : 0));
                }
                selectDimDepthDialog.dismissDialog();
            }
        }
    }

    public static SelectDimDepthDialog asDefault() {
        return (SelectDimDepthDialog) new SelectDimDepthDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setGravity(80);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<String> getContentList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.mode_default));
        arrayList.add(getString(R.string.mode_diy));
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeProgressDrawable(DialogDimDepthSelectorBinding viewBinding, int selectPosition) {
        Rect bounds = viewBinding.sbDimDepth.getProgressDrawable().getBounds();
        viewBinding.sbDimDepth.setProgressDrawable(ContextCompat.getDrawable(getContext(), selectPosition == 0 ? R.drawable.style_seekbar_gray : R.drawable.style_seekbar_red));
        viewBinding.sbDimDepth.getProgressDrawable().setBounds(bounds);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeProgressView(DialogDimDepthSelectorBinding viewBinding, int progress, boolean select) {
        View[] viewArr = {viewBinding.viewDepth1, viewBinding.viewDepth2, viewBinding.viewDepth3, viewBinding.viewDepth4, viewBinding.viewDepth5, viewBinding.viewDepth6, viewBinding.viewDepth7, viewBinding.viewDepth8, viewBinding.viewDepth9, viewBinding.viewDepth10, viewBinding.viewDepth11, viewBinding.viewDepth12, viewBinding.viewDepth13, viewBinding.viewDepth14, viewBinding.viewDepth15, viewBinding.viewDepth16, viewBinding.viewDepth17, viewBinding.viewDepth18, viewBinding.viewDepth19, viewBinding.viewDepth20, viewBinding.viewDepth21};
        if (select) {
            viewArr[progress].setBackgroundColor(getResources().getColor(R.color.color_text_red));
        } else {
            viewArr[progress].setBackgroundColor(getResources().getColor(R.color.color_light_gray));
        }
    }

    public SelectDimDepthDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public SelectDimDepthDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    public SelectDimDepthDialog setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        return this;
    }

    public SelectDimDepthDialog setProgress(int progress) {
        this.progress = progress;
        return this;
    }

    public SelectDimDepthDialog setConfirmAction(IAction<Integer> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "select_dim_depth_dialog";
    }
}