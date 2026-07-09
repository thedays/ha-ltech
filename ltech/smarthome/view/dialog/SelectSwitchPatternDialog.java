package com.ltech.smarthome.view.dialog;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogSwitchPatternSelectorBinding;
import com.ltech.smarthome.view.dialog.SelectSwitchPatternDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class SelectSwitchPatternDialog extends SmartDialog<DialogSwitchPatternSelectorBinding> {
    public List<String> dataList;
    private List<Integer> imgs;
    public BaseQuickAdapter<String, BaseViewHolder> mAdapter;
    private OnSaveListener mOnSaveListener;
    private int selectPosition = -1;
    private int zoneCount;

    public static class IconContent {
        public int iconRes;
        public String name;
    }

    public interface OnSaveListener {
        boolean onSave(int selectPos);
    }

    private int itemLayout() {
        return R.layout.item_group_switch_pattern;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_switch_pattern_selector;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectSwitchPatternDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogSwitchPatternSelectorBinding, SelectSwitchPatternDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogSwitchPatternSelectorBinding viewBinding, final SelectSwitchPatternDialog dialog) {
            dialog.initRv(viewBinding);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectSwitchPatternDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SelectSwitchPatternDialog.AnonymousClass1.lambda$convertView$0(SelectSwitchPatternDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(SelectSwitchPatternDialog selectSwitchPatternDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                selectSwitchPatternDialog.dismissDialog();
                return;
            }
            if (id != R.id.tv_save) {
                return;
            }
            if (selectSwitchPatternDialog.mOnSaveListener != null) {
                if (selectSwitchPatternDialog.selectPosition != -1 && selectSwitchPatternDialog.mOnSaveListener.onSave(selectSwitchPatternDialog.selectPosition)) {
                    selectSwitchPatternDialog.dismissDialog();
                    return;
                }
                return;
            }
            selectSwitchPatternDialog.dismissDialog();
        }
    }

    public static SelectSwitchPatternDialog asDefault() {
        return (SelectSwitchPatternDialog) new SelectSwitchPatternDialog().setViewConverter(new AnonymousClass1()).setGravity(80).setMargin(16).setY(16).setOutCancel(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRv(DialogSwitchPatternSelectorBinding viewBinding) {
        ArrayList arrayList = new ArrayList();
        this.dataList = arrayList;
        arrayList.add(getString(R.string.pattern_1));
        this.dataList.add(getString(R.string.pattern_2));
        BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(itemLayout(), this.dataList) { // from class: com.ltech.smarthome.view.dialog.SelectSwitchPatternDialog.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, String item) {
                List list;
                int i;
                helper.setText(R.id.tv_pattern, item);
                if (SelectSwitchPatternDialog.this.imgs != null && SelectSwitchPatternDialog.this.imgs.size() >= 4) {
                    if (helper.getBindingAdapterPosition() == 0) {
                        if (SelectSwitchPatternDialog.this.selectPosition == helper.getBindingAdapterPosition()) {
                            list = SelectSwitchPatternDialog.this.imgs;
                            i = 0;
                        } else {
                            list = SelectSwitchPatternDialog.this.imgs;
                            i = 1;
                        }
                        helper.setImageResource(R.id.iv_pattern, ((Integer) list.get(i)).intValue());
                        return;
                    }
                    helper.setImageResource(R.id.iv_pattern, ((Integer) (SelectSwitchPatternDialog.this.selectPosition == helper.getBindingAdapterPosition() ? SelectSwitchPatternDialog.this.imgs.get(2) : SelectSwitchPatternDialog.this.imgs.get(3))).intValue());
                    return;
                }
                int i2 = SelectSwitchPatternDialog.this.zoneCount;
                if (i2 == 2) {
                    if (helper.getBindingAdapterPosition() == 0) {
                        helper.setImageResource(R.id.iv_pattern, SelectSwitchPatternDialog.this.selectPosition == helper.getBindingAdapterPosition() ? R.mipmap.panel2_1_sel : R.mipmap.panel2_1_nor);
                        return;
                    } else {
                        helper.setImageResource(R.id.iv_pattern, SelectSwitchPatternDialog.this.selectPosition == helper.getBindingAdapterPosition() ? R.mipmap.panel2_2_sel : R.mipmap.panel2_2_nor);
                        return;
                    }
                }
                if (i2 == 3) {
                    if (helper.getBindingAdapterPosition() == 0) {
                        helper.setImageResource(R.id.iv_pattern, SelectSwitchPatternDialog.this.selectPosition == helper.getBindingAdapterPosition() ? R.mipmap.panel3_1_sel : R.mipmap.panel3_1_nor);
                        return;
                    } else {
                        helper.setImageResource(R.id.iv_pattern, SelectSwitchPatternDialog.this.selectPosition == helper.getBindingAdapterPosition() ? R.mipmap.panel3_2_sel : R.mipmap.panel3_2_nor);
                        return;
                    }
                }
                if (helper.getBindingAdapterPosition() == 0) {
                    helper.setImageResource(R.id.iv_pattern, SelectSwitchPatternDialog.this.selectPosition == helper.getBindingAdapterPosition() ? R.mipmap.panel1_1_sel : R.mipmap.panel1_1_nor);
                } else {
                    helper.setImageResource(R.id.iv_pattern, SelectSwitchPatternDialog.this.selectPosition == helper.getBindingAdapterPosition() ? R.mipmap.panel1_2_sel : R.mipmap.panel1_2_nor);
                }
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.SelectSwitchPatternDialog$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                SelectSwitchPatternDialog.this.lambda$initRv$0(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((DialogSwitchPatternSelectorBinding) this.mViewBinding).rvContent);
        viewBinding.rvContent.setLayoutManager(new GridLayoutManager(getContext(), 2));
        viewBinding.rvContent.setPadding(ConvertUtils.dp2px(8.0f), ConvertUtils.dp2px(16.0f), ConvertUtils.dp2px(8.0f), ConvertUtils.dp2px(16.0f));
        viewBinding.rvContent.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.view.dialog.SelectSwitchPatternDialog.3
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, ConvertUtils.dp2px(8.0f), 0, ConvertUtils.dp2px(8.0f));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (this.selectPosition != i) {
            this.selectPosition = i;
            baseQuickAdapter.notifyDataSetChanged();
        }
    }

    public SelectSwitchPatternDialog setZoneCount(int zoneCount) {
        this.zoneCount = zoneCount;
        return this;
    }

    public SelectSwitchPatternDialog setCustomPic(List<Integer> imgs) {
        this.imgs = imgs;
        return this;
    }

    public SelectSwitchPatternDialog setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        return this;
    }

    public SelectSwitchPatternDialog setOnSaveListener(OnSaveListener mOnSaveListener) {
        this.mOnSaveListener = mOnSaveListener;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "dialog_switch_pattern_selector";
    }
}