package com.ltech.smarthome.view.dialog;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.SeekBar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.common.primitives.Ints;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogSelectColorBinding;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.view.ColorEditText;
import com.ltech.smarthome.view.ColorPickerView;

/* loaded from: classes4.dex */
public class SelectColorDialog extends SmartDialog<DialogSelectColorBinding> {
    private int blue;
    private int brt;
    private int green;
    private OnDialogCallback mOnDialogCallback;
    private int red;
    private int w;

    public interface OnDialogCallback {
        void onColorChanged(int red, int green, int blue, int brt, int w, boolean finish);

        void onSaved(int red, int green, int blue, int brt, int w);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_select_color;
    }

    public static SelectColorDialog rgb() {
        return (SelectColorDialog) new SelectColorDialog().setViewConverter(new SmartDialog.ViewConverter<DialogSelectColorBinding, SelectColorDialog>() { // from class: com.ltech.smarthome.view.dialog.SelectColorDialog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(DialogSelectColorBinding viewBinding, SelectColorDialog dialog) {
                dialog.init2Rgb(viewBinding);
                dialog.initDefaultRv(viewBinding);
                dialog.initCallback(viewBinding);
            }
        }).setMargin(16).setY(16).setOutCancel(false).setGravity(80);
    }

    public static SelectColorDialog rgbB() {
        return (SelectColorDialog) new SelectColorDialog().setViewConverter(new SmartDialog.ViewConverter<DialogSelectColorBinding, SelectColorDialog>() { // from class: com.ltech.smarthome.view.dialog.SelectColorDialog.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(DialogSelectColorBinding viewBinding, SelectColorDialog dialog) {
                dialog.init2Rgb(viewBinding);
                dialog.initDefaultRv(viewBinding);
                dialog.initBrt(viewBinding);
                dialog.initCallback(viewBinding);
            }
        }).setMargin(16).setY(16).setOutCancel(false).setGravity(80);
    }

    public static SelectColorDialog rgbwB() {
        return (SelectColorDialog) new SelectColorDialog().setViewConverter(new SmartDialog.ViewConverter<DialogSelectColorBinding, SelectColorDialog>() { // from class: com.ltech.smarthome.view.dialog.SelectColorDialog.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(DialogSelectColorBinding viewBinding, SelectColorDialog dialog) {
                dialog.init2Rgb(viewBinding);
                dialog.initDefaultRv(viewBinding);
                dialog.initW(viewBinding);
                dialog.initBrt(viewBinding);
                dialog.initCallback(viewBinding);
            }
        }).setMargin(16).setY(16).setOutCancel(false).setGravity(80);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initDefaultRv(final DialogSelectColorBinding viewBinding) {
        final BaseQuickAdapter<Integer, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Integer, BaseViewHolder>(this, R.layout.item_color, Ints.asList(getContext().getResources().getIntArray(R.array.color_dialog_color))) { // from class: com.ltech.smarthome.view.dialog.SelectColorDialog.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Integer item) {
                helper.setImageDrawable(R.id.civ_color, new ColorDrawable(item.intValue()));
            }
        };
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.SelectColorDialog$$ExternalSyntheticLambda4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                SelectColorDialog.this.lambda$initDefaultRv$0(baseQuickAdapter, viewBinding, baseQuickAdapter2, view, i);
            }
        });
        baseQuickAdapter.bindToRecyclerView(viewBinding.rvColor);
        viewBinding.rvColor.setHasFixedSize(true);
        viewBinding.rvColor.setLayoutManager(new GridLayoutManager(getContext(), 8));
        viewBinding.rvColor.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.view.dialog.SelectColorDialog.5
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(5.0f), 0, ConvertUtils.dp2px(5.0f), 0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDefaultRv$0(BaseQuickAdapter baseQuickAdapter, DialogSelectColorBinding dialogSelectColorBinding, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
        this.red = Color.red(((Integer) baseQuickAdapter.getData().get(i)).intValue());
        this.green = Color.green(((Integer) baseQuickAdapter.getData().get(i)).intValue());
        this.blue = Color.blue(((Integer) baseQuickAdapter.getData().get(i)).intValue());
        dialogSelectColorBinding.etColor1.setText(String.valueOf(this.red));
        dialogSelectColorBinding.etColor2.setText(String.valueOf(this.green));
        dialogSelectColorBinding.etColor3.setText(String.valueOf(this.blue));
        setColorView(dialogSelectColorBinding);
        OnDialogCallback onDialogCallback = this.mOnDialogCallback;
        if (onDialogCallback != null) {
            onDialogCallback.onColorChanged(this.red, this.green, this.blue, this.brt, this.w, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void init2Rgb(final DialogSelectColorBinding viewBinding) {
        viewBinding.tvColor1.setText(R.string.r);
        viewBinding.tvColor2.setText(R.string.g);
        viewBinding.tvColor3.setText(R.string.f6266b);
        viewBinding.cpv.setOnColorChangedListener(new ColorPickerView.OnColorChangedListener() { // from class: com.ltech.smarthome.view.dialog.SelectColorDialog.6
            @Override // com.ltech.smarthome.view.ColorPickerView.OnColorChangedListener
            public void onColorChanged(int color, float xPercent, float yPercent) {
                SelectColorDialog.this.red = Color.red(color);
                SelectColorDialog.this.green = Color.green(color);
                SelectColorDialog.this.blue = Color.blue(color);
                viewBinding.etColor1.setText(String.valueOf(SelectColorDialog.this.red));
                viewBinding.etColor2.setText(String.valueOf(SelectColorDialog.this.green));
                viewBinding.etColor3.setText(String.valueOf(SelectColorDialog.this.blue));
                SelectColorDialog.this.setColorView(viewBinding);
                if (SelectColorDialog.this.mOnDialogCallback != null) {
                    SelectColorDialog.this.mOnDialogCallback.onColorChanged(SelectColorDialog.this.red, SelectColorDialog.this.green, SelectColorDialog.this.blue, SelectColorDialog.this.brt, SelectColorDialog.this.w, false);
                }
            }

            @Override // com.ltech.smarthome.view.ColorPickerView.OnColorChangedListener
            public void onChangedFinish(int color, float xPercent, float yPercent) {
                SelectColorDialog.this.red = Color.red(color);
                SelectColorDialog.this.green = Color.green(color);
                SelectColorDialog.this.blue = Color.blue(color);
                viewBinding.etColor1.setText(String.valueOf(SelectColorDialog.this.red));
                viewBinding.etColor2.setText(String.valueOf(SelectColorDialog.this.green));
                viewBinding.etColor3.setText(String.valueOf(SelectColorDialog.this.blue));
                SelectColorDialog.this.setColorView(viewBinding);
                if (SelectColorDialog.this.mOnDialogCallback != null) {
                    SelectColorDialog.this.mOnDialogCallback.onColorChanged(SelectColorDialog.this.red, SelectColorDialog.this.green, SelectColorDialog.this.blue, SelectColorDialog.this.brt, SelectColorDialog.this.w, true);
                }
            }
        });
        viewBinding.etColor1.setListener(new ColorEditText.OnColorTextChangedListener() { // from class: com.ltech.smarthome.view.dialog.SelectColorDialog$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.ColorEditText.OnColorTextChangedListener
            public final void onTextChangedListener(String str) {
                SelectColorDialog.this.lambda$init2Rgb$1(viewBinding, str);
            }
        });
        viewBinding.etColor2.setListener(new ColorEditText.OnColorTextChangedListener() { // from class: com.ltech.smarthome.view.dialog.SelectColorDialog$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.ColorEditText.OnColorTextChangedListener
            public final void onTextChangedListener(String str) {
                SelectColorDialog.this.lambda$init2Rgb$2(viewBinding, str);
            }
        });
        viewBinding.etColor3.setListener(new ColorEditText.OnColorTextChangedListener() { // from class: com.ltech.smarthome.view.dialog.SelectColorDialog$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.view.ColorEditText.OnColorTextChangedListener
            public final void onTextChangedListener(String str) {
                SelectColorDialog.this.lambda$init2Rgb$3(viewBinding, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init2Rgb$1(DialogSelectColorBinding dialogSelectColorBinding, String str) {
        this.red = Integer.parseInt(str);
        setColorView(dialogSelectColorBinding);
        dialogSelectColorBinding.cpv.clearPicker();
        OnDialogCallback onDialogCallback = this.mOnDialogCallback;
        if (onDialogCallback != null) {
            onDialogCallback.onColorChanged(this.red, this.green, this.blue, this.brt, this.w, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init2Rgb$2(DialogSelectColorBinding dialogSelectColorBinding, String str) {
        this.green = Integer.parseInt(str);
        setColorView(dialogSelectColorBinding);
        dialogSelectColorBinding.cpv.clearPicker();
        OnDialogCallback onDialogCallback = this.mOnDialogCallback;
        if (onDialogCallback != null) {
            onDialogCallback.onColorChanged(this.red, this.green, this.blue, this.brt, this.w, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init2Rgb$3(DialogSelectColorBinding dialogSelectColorBinding, String str) {
        this.blue = Integer.parseInt(str);
        setColorView(dialogSelectColorBinding);
        dialogSelectColorBinding.cpv.clearPicker();
        OnDialogCallback onDialogCallback = this.mOnDialogCallback;
        if (onDialogCallback != null) {
            onDialogCallback.onColorChanged(this.red, this.green, this.blue, this.brt, this.w, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setColorView(DialogSelectColorBinding viewBinding) {
        viewBinding.vColor.setBackgroundColor(Color.rgb(this.red, this.green, this.blue));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initBrt(DialogSelectColorBinding viewBinding) {
        viewBinding.tvBrtTip.setVisibility(0);
        viewBinding.sbBrt.setVisibility(0);
        viewBinding.sbBrt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.view.dialog.SelectColorDialog.7
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    SelectColorDialog.this.brt = LightUtils.progress2Brt(progress);
                    if (SelectColorDialog.this.mOnDialogCallback != null) {
                        SelectColorDialog.this.mOnDialogCallback.onColorChanged(SelectColorDialog.this.red, SelectColorDialog.this.green, SelectColorDialog.this.blue, SelectColorDialog.this.brt, SelectColorDialog.this.w, false);
                    }
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                SelectColorDialog.this.brt = LightUtils.progress2Brt(seekBar.getProgress());
                if (SelectColorDialog.this.mOnDialogCallback != null) {
                    SelectColorDialog.this.mOnDialogCallback.onColorChanged(SelectColorDialog.this.red, SelectColorDialog.this.green, SelectColorDialog.this.blue, SelectColorDialog.this.brt, SelectColorDialog.this.w, true);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initW(DialogSelectColorBinding viewBinding) {
        viewBinding.tvWTip.setVisibility(0);
        viewBinding.sbW.setVisibility(0);
        viewBinding.sbW.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.view.dialog.SelectColorDialog.8
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    SelectColorDialog.this.w = LightUtils.progress2Brt(progress);
                    if (SelectColorDialog.this.mOnDialogCallback != null) {
                        SelectColorDialog.this.mOnDialogCallback.onColorChanged(SelectColorDialog.this.red, SelectColorDialog.this.green, SelectColorDialog.this.blue, SelectColorDialog.this.brt, SelectColorDialog.this.w, false);
                    }
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                SelectColorDialog.this.w = LightUtils.progress2Brt(seekBar.getProgress());
                if (SelectColorDialog.this.mOnDialogCallback != null) {
                    SelectColorDialog.this.mOnDialogCallback.onColorChanged(SelectColorDialog.this.red, SelectColorDialog.this.green, SelectColorDialog.this.blue, SelectColorDialog.this.brt, SelectColorDialog.this.w, true);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initCallback(DialogSelectColorBinding viewBinding) {
        viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectColorDialog$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                SelectColorDialog.this.lambda$initCallback$4((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initCallback$4(View view) {
        int id = view.getId();
        if (id == R.id.tv_cancel) {
            dismissDialog();
        } else {
            if (id != R.id.tv_confirm) {
                return;
            }
            OnDialogCallback onDialogCallback = this.mOnDialogCallback;
            if (onDialogCallback != null) {
                onDialogCallback.onSaved(this.red, this.green, this.blue, this.brt, this.w);
            }
            dismissDialog();
        }
    }

    public SelectColorDialog setOnDialogCallback(OnDialogCallback onDialogCallback) {
        this.mOnDialogCallback = onDialogCallback;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "select_color_dialog";
    }
}