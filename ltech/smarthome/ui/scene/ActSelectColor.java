package com.ltech.smarthome.ui.scene;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.common.primitives.Ints;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSelectColorBinding;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.mode.ColorArray;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.ColorEditText;
import com.ltech.smarthome.view.ColorPickerView;
import com.ltech.smarthome.view.ColorSeekBar2;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.smart.message.utils.LHomeLog;

/* loaded from: classes4.dex */
public class ActSelectColor extends VMActivity<ActSelectColorBinding, ActSelectColorVM> {
    private int ctChangeNum;
    private int number;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_color;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x00cc, code lost:
    
        if (r0 != 10009) goto L24;
     */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void initView() {
        /*
            Method dump skipped, instructions count: 360
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.scene.ActSelectColor.initView():void");
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        LHomeLog.i(getClass(), "选择颜色save ");
        if (ProductId.ID_SMART_PANEL_S6B.equals(((ActSelectColorVM) this.mViewModel).productId) && RelateInfoUtils.needShowTipDialog()) {
            RelateInfoUtils.showImageTipDialog(getString(R.string.s6b_click_tip), R.mipmap.pic_click_tip_s6b, this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.scene.ActSelectColor$$ExternalSyntheticLambda7
                @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                    ActSelectColor.this.lambda$edit$0(imageTipDialog);
                }
            });
        } else {
            ((ActSelectColorVM) this.mViewModel).saveData();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$0(ImageTipDialog imageTipDialog) {
        ((ActSelectColorVM) this.mViewModel).saveData();
        imageTipDialog.dismissDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBgColorView(int color) {
        ((ActSelectColorBinding) this.mViewBinding).vColor.setBackgroundColor(color);
        ((ActSelectColorBinding) this.mViewBinding).etRed.setText(String.valueOf(Color.red(color)));
        ((ActSelectColorBinding) this.mViewBinding).etGreen.setText(String.valueOf(Color.green(color)));
        ((ActSelectColorBinding) this.mViewBinding).etBlue.setText(String.valueOf(Color.blue(color)));
    }

    private void initRgb() {
        initRgbView();
        Glide.with((FragmentActivity) this).asBitmap().load(Integer.valueOf(R.mipmap.bg_rgbw1)).into((RequestBuilder<Bitmap>) new CustomViewTarget<ColorPickerView, Bitmap>(((ActSelectColorBinding) this.mViewBinding).cpv) { // from class: com.ltech.smarthome.ui.scene.ActSelectColor.1
            @Override // com.bumptech.glide.request.target.Target
            public void onLoadFailed(Drawable errorDrawable) {
            }

            @Override // com.bumptech.glide.request.target.CustomViewTarget
            protected void onResourceCleared(Drawable placeholder) {
            }

            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(Object resource, Transition transition) {
                onResourceReady((Bitmap) resource, (Transition<? super Bitmap>) transition);
            }

            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                ((ActSelectColorBinding) ActSelectColor.this.mViewBinding).cpv.setBitmapBack(resource);
            }
        });
    }

    private void initRgbw() {
        initRgbwView();
        Glide.with((FragmentActivity) this).asBitmap().load(Integer.valueOf(R.mipmap.bg_rgbw1)).into((RequestBuilder<Bitmap>) new CustomViewTarget<ColorPickerView, Bitmap>(((ActSelectColorBinding) this.mViewBinding).cpv) { // from class: com.ltech.smarthome.ui.scene.ActSelectColor.2
            @Override // com.bumptech.glide.request.target.Target
            public void onLoadFailed(Drawable errorDrawable) {
            }

            @Override // com.bumptech.glide.request.target.CustomViewTarget
            protected void onResourceCleared(Drawable placeholder) {
            }

            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(Object resource, Transition transition) {
                onResourceReady((Bitmap) resource, (Transition<? super Bitmap>) transition);
            }

            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                ((ActSelectColorBinding) ActSelectColor.this.mViewBinding).cpv.setBitmapBack(resource);
            }
        });
    }

    private void initRgbWy() {
        initRgbView();
        if (!ProductRepository.isDaliLightGroup(((ActSelectColorVM) this.mViewModel).getControlObject()) && !ProductRepository.isCgdPro(((ActSelectColorVM) this.mViewModel).getControlObject())) {
            ((ActSelectColorBinding) this.mViewBinding).groupRgbw.setVisibility(0);
        }
        ((ActSelectColorBinding) this.mViewBinding).groupRgbwy.setVisibility(0);
        ((ActSelectColorBinding) this.mViewBinding).tvWTip.setText(getString(R.string.wy));
        ((ActSelectColorBinding) this.mViewBinding).seekBarWy.setOnColorChangedListener(new ColorSeekBar2.onColorChangedListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectColor.3
            @Override // com.ltech.smarthome.view.ColorSeekBar2.onColorChangedListener
            public void onColorChangedStart() {
            }

            @Override // com.ltech.smarthome.view.ColorSeekBar2.onColorChangedListener
            public void onColorChanged(int xPercent, int color, boolean isFromUser) {
                if (isFromUser) {
                    ((ActSelectColorVM) ActSelectColor.this.mViewModel).setWy(255 - LightUtils.progress2Brt(xPercent));
                    LightAssistant lightCmdHelper = ((ActSelectColorVM) ActSelectColor.this.mViewModel).getLightCmdHelper();
                    ActSelectColor actSelectColor = ActSelectColor.this;
                    lightCmdHelper.sendWy(actSelectColor, ((ActSelectColorVM) actSelectColor.mViewModel).getWy(), false);
                }
            }

            @Override // com.ltech.smarthome.view.ColorSeekBar2.onColorChangedListener
            public void onColorChangedFinish(int xPercent, int color, boolean isFromUser) {
                if (isFromUser) {
                    ((ActSelectColorVM) ActSelectColor.this.mViewModel).setWy(255 - LightUtils.progress2Brt(xPercent));
                    LightAssistant lightCmdHelper = ((ActSelectColorVM) ActSelectColor.this.mViewModel).getLightCmdHelper();
                    ActSelectColor actSelectColor = ActSelectColor.this;
                    lightCmdHelper.sendWy(actSelectColor, ((ActSelectColorVM) actSelectColor.mViewModel).getWy(), true);
                }
            }
        });
        ((ActSelectColorBinding) this.mViewBinding).seekBarWy.setProgress(LightUtils.brt2Progress(255 - ((ActSelectColorVM) this.mViewModel).getWy()));
        ((ActSelectColorBinding) this.mViewBinding).sbW.setIncludeZero(true);
        ((ActSelectColorBinding) this.mViewBinding).sbW.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectColor.4
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((ActSelectColorVM) ActSelectColor.this.mViewModel).setWyBrt(LightUtils.progress2BrtIncludeZero(progress));
                    ((ActSelectColorBinding) ActSelectColor.this.mViewBinding).tvW.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                    ((ActSelectColorVM) ActSelectColor.this.mViewModel).getLightCmdHelper().sendWyBrtHas1to9(ActSelectColor.this, progress, false);
                } else {
                    ((ActSelectColorVM) ActSelectColor.this.mViewModel).setWyBrt(LightUtils.progress2BrtHasBelowOne(progress));
                    ((ActSelectColorBinding) ActSelectColor.this.mViewBinding).tvW.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((ActSelectColorVM) ActSelectColor.this.mViewModel).setWyBrt(LightUtils.progress2BrtIncludeZero(seekBar.getProgress()));
                ((ActSelectColorBinding) ActSelectColor.this.mViewBinding).tvW.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                ((ActSelectColorVM) ActSelectColor.this.mViewModel).getLightCmdHelper().sendWyBrtHas1to9(ActSelectColor.this, seekBar.getProgress(), true);
            }
        });
        ((ActSelectColorBinding) this.mViewBinding).tvW.setText(((ActSelectColorBinding) this.mViewBinding).sbW.getProgressHasBelowOne());
        Glide.with((FragmentActivity) this).asBitmap().load(Integer.valueOf(R.mipmap.bg_rgb)).into((RequestBuilder<Bitmap>) new CustomViewTarget<ColorPickerView, Bitmap>(((ActSelectColorBinding) this.mViewBinding).cpv) { // from class: com.ltech.smarthome.ui.scene.ActSelectColor.5
            @Override // com.bumptech.glide.request.target.Target
            public void onLoadFailed(Drawable errorDrawable) {
            }

            @Override // com.bumptech.glide.request.target.CustomViewTarget
            protected void onResourceCleared(Drawable placeholder) {
            }

            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(Object resource, Transition transition) {
                onResourceReady((Bitmap) resource, (Transition<? super Bitmap>) transition);
            }

            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                ((ActSelectColorBinding) ActSelectColor.this.mViewBinding).cpv.setBitmapBack(resource);
            }
        });
    }

    private void initRgbwView() {
        initRgbView();
        ((ActSelectColorBinding) this.mViewBinding).groupRgbw.setVisibility(0);
        ((ActSelectColorBinding) this.mViewBinding).sbW.setIncludeZero(true);
        ((ActSelectColorBinding) this.mViewBinding).sbW.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectColor.6
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((ActSelectColorVM) ActSelectColor.this.mViewModel).setWyBrt(LightUtils.progress2BrtIncludeZero(progress));
                    ((ActSelectColorBinding) ActSelectColor.this.mViewBinding).tvW.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                    ((ActSelectColorVM) ActSelectColor.this.mViewModel).getLightCmdHelper().sendWHas1to9(ActSelectColor.this, progress, false);
                } else {
                    ((ActSelectColorVM) ActSelectColor.this.mViewModel).setWyBrt(LightUtils.progress2BrtHasBelowOne(progress));
                    ((ActSelectColorBinding) ActSelectColor.this.mViewBinding).tvW.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((ActSelectColorVM) ActSelectColor.this.mViewModel).setWyBrt(LightUtils.progress2BrtIncludeZero(seekBar.getProgress()));
                ((ActSelectColorBinding) ActSelectColor.this.mViewBinding).tvW.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                ((ActSelectColorVM) ActSelectColor.this.mViewModel).getLightCmdHelper().sendWHas1to9(ActSelectColor.this, seekBar.getProgress(), true);
            }
        });
        ((ActSelectColorBinding) this.mViewBinding).tvW.setText(((ActSelectColorBinding) this.mViewBinding).sbW.getProgressHasBelowOne());
    }

    private void initRgbView() {
        ((ActSelectColorBinding) this.mViewBinding).groupRgb.setVisibility(0);
        ((ActSelectColorBinding) this.mViewBinding).vColor.setBackgroundColor(Color.rgb(((ActSelectColorVM) this.mViewModel).getRed(), ((ActSelectColorVM) this.mViewModel).getGreen(), ((ActSelectColorVM) this.mViewModel).getBlue()));
        final BaseQuickAdapter<Integer, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Integer, BaseViewHolder>(this, R.layout.item_color, Ints.asList(getResources().getIntArray(R.array.static_default_color))) { // from class: com.ltech.smarthome.ui.scene.ActSelectColor.7
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Integer item) {
                helper.setImageDrawable(R.id.civ_color, new ColorDrawable(item.intValue()));
            }
        };
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectColor$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActSelectColor.this.lambda$initRgbView$1(baseQuickAdapter, baseQuickAdapter2, view, i);
            }
        });
        baseQuickAdapter.bindToRecyclerView(((ActSelectColorBinding) this.mViewBinding).rvColor);
        ((ActSelectColorBinding) this.mViewBinding).rvColor.setLayoutManager(new GridLayoutManager(this, 8));
        ((ActSelectColorBinding) this.mViewBinding).rvColor.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.scene.ActSelectColor.8
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(5.0f), 0, ConvertUtils.dp2px(5.0f), 0);
            }
        });
        ((ActSelectColorBinding) this.mViewBinding).cpv.setOnColorChangedListener(new ColorPickerView.OnColorChangedListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectColor.9
            @Override // com.ltech.smarthome.view.ColorPickerView.OnColorChangedListener
            public void onColorChanged(int color, float xPercent, float yPercent) {
                ((ActSelectColorVM) ActSelectColor.this.mViewModel).setRed(Color.red(color));
                ((ActSelectColorVM) ActSelectColor.this.mViewModel).setGreen(Color.green(color));
                ((ActSelectColorVM) ActSelectColor.this.mViewModel).setBlue(Color.blue(color));
                ActSelectColor.this.setBgColorView(color);
                ActSelectColor.this.number++;
                ((ActSelectColorVM) ActSelectColor.this.mViewModel).getLightCmdHelper().sendRgb(ActSelectColor.this, color, false);
            }

            @Override // com.ltech.smarthome.view.ColorPickerView.OnColorChangedListener
            public void onChangedFinish(int color, float xPercent, float yPercent) {
                ((ActSelectColorVM) ActSelectColor.this.mViewModel).setRed(Color.red(color));
                ((ActSelectColorVM) ActSelectColor.this.mViewModel).setGreen(Color.green(color));
                ((ActSelectColorVM) ActSelectColor.this.mViewModel).setBlue(Color.blue(color));
                ActSelectColor.this.setBgColorView(color);
                if (ActSelectColor.this.number > 10) {
                    ((ActSelectColorVM) ActSelectColor.this.mViewModel).getLightCmdHelper().sendRgb(ActSelectColor.this, color, true);
                } else {
                    ((ActSelectColorVM) ActSelectColor.this.mViewModel).getLightCmdHelper().sendRgbDC(ActSelectColor.this, color, true);
                }
                ActSelectColor.this.number = 0;
            }
        });
        ((ActSelectColorBinding) this.mViewBinding).etRed.setListener(new ColorEditText.OnColorTextChangedListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectColor$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.ColorEditText.OnColorTextChangedListener
            public final void onTextChangedListener(String str) {
                ActSelectColor.this.lambda$initRgbView$2(str);
            }
        });
        ((ActSelectColorBinding) this.mViewBinding).etGreen.setListener(new ColorEditText.OnColorTextChangedListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectColor$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.view.ColorEditText.OnColorTextChangedListener
            public final void onTextChangedListener(String str) {
                ActSelectColor.this.lambda$initRgbView$3(str);
            }
        });
        ((ActSelectColorBinding) this.mViewBinding).etBlue.setListener(new ColorEditText.OnColorTextChangedListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectColor$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.view.ColorEditText.OnColorTextChangedListener
            public final void onTextChangedListener(String str) {
                ActSelectColor.this.lambda$initRgbView$4(str);
            }
        });
        ((ActSelectColorBinding) this.mViewBinding).sbBrt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectColor.10
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((ActSelectColorVM) ActSelectColor.this.mViewModel).setRgbBrt(LightUtils.progress2BrtHasBelowOne(progress));
                    ((ActSelectColorBinding) ActSelectColor.this.mViewBinding).tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                    ((ActSelectColorVM) ActSelectColor.this.mViewModel).getLightCmdHelper().sendRgbBrtHas1to9(ActSelectColor.this, progress, false);
                } else {
                    ((ActSelectColorVM) ActSelectColor.this.mViewModel).setRgbBrt(LightUtils.progress2Brt(progress));
                    ((ActSelectColorBinding) ActSelectColor.this.mViewBinding).tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((ActSelectColorVM) ActSelectColor.this.mViewModel).setRgbBrt(LightUtils.progress2BrtHasBelowOne(seekBar.getProgress()));
                ((ActSelectColorBinding) ActSelectColor.this.mViewBinding).tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                ((ActSelectColorVM) ActSelectColor.this.mViewModel).getLightCmdHelper().sendRgbBrtHas1to9(ActSelectColor.this, seekBar.getProgress(), true);
            }
        });
        ((ActSelectColorBinding) this.mViewBinding).tvBrt.setText(((ActSelectColorBinding) this.mViewBinding).sbBrt.getProgressHasBelowOne());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRgbView$1(BaseQuickAdapter baseQuickAdapter, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
        int intValue = ((Integer) baseQuickAdapter.getData().get(i)).intValue();
        ((ActSelectColorVM) this.mViewModel).setRed(Color.red(intValue));
        ((ActSelectColorVM) this.mViewModel).setGreen(Color.green(intValue));
        ((ActSelectColorVM) this.mViewModel).setBlue(Color.blue(intValue));
        setBgColorView(intValue);
        ((ActSelectColorVM) this.mViewModel).getLightCmdHelper().sendRgb(this, intValue, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRgbView$2(String str) {
        ((ActSelectColorVM) this.mViewModel).setRed(Integer.parseInt(str));
        setBgColorView(Color.rgb(((ActSelectColorVM) this.mViewModel).getRed(), ((ActSelectColorVM) this.mViewModel).getGreen(), ((ActSelectColorVM) this.mViewModel).getBlue()));
        ((ActSelectColorBinding) this.mViewBinding).etRed.selectEnd();
        ((ActSelectColorBinding) this.mViewBinding).cpv.clearPicker();
        ((ActSelectColorVM) this.mViewModel).getLightCmdHelper().sendRgb(this, Color.rgb(((ActSelectColorVM) this.mViewModel).getRed(), ((ActSelectColorVM) this.mViewModel).getGreen(), ((ActSelectColorVM) this.mViewModel).getBlue()), true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRgbView$3(String str) {
        ((ActSelectColorVM) this.mViewModel).setGreen(Integer.parseInt(str));
        setBgColorView(Color.rgb(((ActSelectColorVM) this.mViewModel).getRed(), ((ActSelectColorVM) this.mViewModel).getGreen(), ((ActSelectColorVM) this.mViewModel).getBlue()));
        ((ActSelectColorBinding) this.mViewBinding).etGreen.selectEnd();
        ((ActSelectColorBinding) this.mViewBinding).cpv.clearPicker();
        ((ActSelectColorVM) this.mViewModel).getLightCmdHelper().sendRgb(this, Color.rgb(((ActSelectColorVM) this.mViewModel).getRed(), ((ActSelectColorVM) this.mViewModel).getGreen(), ((ActSelectColorVM) this.mViewModel).getBlue()), true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRgbView$4(String str) {
        ((ActSelectColorVM) this.mViewModel).setBlue(Integer.parseInt(str));
        setBgColorView(Color.rgb(((ActSelectColorVM) this.mViewModel).getRed(), ((ActSelectColorVM) this.mViewModel).getGreen(), ((ActSelectColorVM) this.mViewModel).getBlue()));
        ((ActSelectColorBinding) this.mViewBinding).etBlue.selectEnd();
        ((ActSelectColorBinding) this.mViewBinding).cpv.clearPicker();
        ((ActSelectColorVM) this.mViewModel).getLightCmdHelper().sendRgb(this, Color.rgb(((ActSelectColorVM) this.mViewModel).getRed(), ((ActSelectColorVM) this.mViewModel).getGreen(), ((ActSelectColorVM) this.mViewModel).getBlue()), true);
    }

    private void initDimView() {
        ((ActSelectColorBinding) this.mViewBinding).etGreen.setVisibility(8);
        ((ActSelectColorBinding) this.mViewBinding).tvGreen.setVisibility(8);
        ((ActSelectColorBinding) this.mViewBinding).etBlue.setVisibility(8);
        ((ActSelectColorBinding) this.mViewBinding).tvBlue.setVisibility(8);
        ((ActSelectColorBinding) this.mViewBinding).etRed.setText(String.valueOf(((ActSelectColorVM) this.mViewModel).getWyBrt()));
        Glide.with((FragmentActivity) this).asBitmap().load(Integer.valueOf(R.mipmap.bg_dim)).into((RequestBuilder<Bitmap>) new CustomViewTarget<ColorPickerView, Bitmap>(((ActSelectColorBinding) this.mViewBinding).cpv) { // from class: com.ltech.smarthome.ui.scene.ActSelectColor.11
            @Override // com.bumptech.glide.request.target.Target
            public void onLoadFailed(Drawable errorDrawable) {
            }

            @Override // com.bumptech.glide.request.target.CustomViewTarget
            protected void onResourceCleared(Drawable placeholder) {
            }

            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(Object resource, Transition transition) {
                onResourceReady((Bitmap) resource, (Transition<? super Bitmap>) transition);
            }

            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                ((ActSelectColorBinding) ActSelectColor.this.mViewBinding).cpv.setBitmapBack(resource);
                ((ActSelectColorBinding) ActSelectColor.this.mViewBinding).cpv.setPickPosition(LightUtils.brt2PercentHasBelowZero(((ActSelectColorVM) ActSelectColor.this.mViewModel).getWyBrt()), 50.0f);
            }
        });
        ((ActSelectColorBinding) this.mViewBinding).sbBrt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectColor.12
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ActSelectColor.this.refreshDimView(progress, false, false);
                    ((ActSelectColorVM) ActSelectColor.this.mViewModel).getLightCmdHelper().sendDimBrtHas1to9(ActSelectColor.this, progress, false);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ActSelectColor.this.refreshDimView(seekBar.getProgress(), false, true);
                ((ActSelectColorVM) ActSelectColor.this.mViewModel).getLightCmdHelper().sendDimBrtHas1to9(ActSelectColor.this, seekBar.getProgress(), false);
            }
        });
        ((ActSelectColorBinding) this.mViewBinding).tvBrt.setText(((ActSelectColorBinding) this.mViewBinding).sbBrt.getProgressHasBelowOne());
        ((ActSelectColorBinding) this.mViewBinding).cpv.setOnColorChangedListener(new ColorPickerView.OnColorChangedListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectColor.13
            @Override // com.ltech.smarthome.view.ColorPickerView.OnColorChangedListener
            public void onColorChanged(int color, float xPercent, float yPercent) {
                Log.i(getClass().getSimpleName(), "onColorChanged:xPercent " + xPercent);
                int i = (int) xPercent;
                ActSelectColor.this.refreshDimView(LightUtils.progressNormal2progressMax(i), false, false);
                ((ActSelectColorVM) ActSelectColor.this.mViewModel).getLightCmdHelper().sendDimBrtHas1to9(ActSelectColor.this, i, false);
            }

            @Override // com.ltech.smarthome.view.ColorPickerView.OnColorChangedListener
            public void onChangedFinish(int color, float xPercent, float yPercent) {
                int i = (int) xPercent;
                ActSelectColor.this.refreshDimView(LightUtils.progressNormal2progressMax(i), false, false);
                ((ActSelectColorVM) ActSelectColor.this.mViewModel).getLightCmdHelper().sendDimBrtHas1to9(ActSelectColor.this, i, true);
            }
        });
        ((ActSelectColorBinding) this.mViewBinding).tvRed.setText(getString(R.string.w));
        ((ActSelectColorBinding) this.mViewBinding).etRed.setListener(new ColorEditText.OnColorTextChangedListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectColor$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.view.ColorEditText.OnColorTextChangedListener
            public final void onTextChangedListener(String str) {
                ActSelectColor.this.lambda$initDimView$5(str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDimView$5(String str) {
        int parseInt;
        if (Integer.parseInt(str) == 0) {
            ((ActSelectColorBinding) this.mViewBinding).etRed.setText(String.valueOf(1));
            parseInt = 1;
        } else {
            parseInt = Integer.parseInt(str);
        }
        refreshDimView(LightUtils.progressNormal2progressMax(LightUtils.brt2ProgressHasBelowZero(parseInt)), true, true);
        ((ActSelectColorBinding) this.mViewBinding).etRed.selectEnd();
        ((ActSelectColorVM) this.mViewModel).getLightCmdHelper().sendDimBrt(this, LightUtils.brt2ProgressHasBelowZero(parseInt), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshDimView(int progress, boolean inputText, boolean needSetPick) {
        ((ActSelectColorVM) this.mViewModel).setWyBrt(LightUtils.progress2BrtHasBelowOne(progress));
        ((ActSelectColorBinding) this.mViewBinding).sbBrt.setProgress(progress);
        ((ActSelectColorBinding) this.mViewBinding).tvBrt.setText(((ActSelectColorBinding) this.mViewBinding).sbBrt.getProgressHasBelowOne());
        int rgb = Color.rgb(((ActSelectColorVM) this.mViewModel).getWyBrt(), ((ActSelectColorVM) this.mViewModel).getWyBrt(), ((ActSelectColorVM) this.mViewModel).getWyBrt());
        ((ActSelectColorBinding) this.mViewBinding).vColor.setBackgroundColor(rgb);
        ((ActSelectColorBinding) this.mViewBinding).tvRed.setTextColor(LightUtils.getInvertedColor(rgb));
        if (!inputText) {
            ((ActSelectColorBinding) this.mViewBinding).etRed.setText(String.valueOf(((ActSelectColorVM) this.mViewModel).getWyBrt()));
        }
        if (needSetPick) {
            ((ActSelectColorBinding) this.mViewBinding).cpv.setPickPosition(LightUtils.progressMax2progressNormal(progress), ((ActSelectColorBinding) this.mViewBinding).cpv.getCurYPercent());
        }
    }

    private void initCtView() {
        ((ActSelectColorBinding) this.mViewBinding).etBlue.setVisibility(8);
        ((ActSelectColorBinding) this.mViewBinding).tvBlue.setVisibility(8);
        ((ActSelectColorBinding) this.mViewBinding).tvRed.setText(getString(R.string.ww));
        ((ActSelectColorBinding) this.mViewBinding).tvGreen.setText(getString(R.string.cw));
        ((ActSelectColorBinding) this.mViewBinding).etRed.setText(String.valueOf(((ActSelectColorVM) this.mViewModel).getWy()));
        ((ActSelectColorBinding) this.mViewBinding).etGreen.setText(String.valueOf(255 - ((ActSelectColorVM) this.mViewModel).getWy()));
        ((ActSelectColorBinding) this.mViewBinding).vColor.setBackgroundColor(Color.rgb(ColorArray.CT_COLOR_ARRAY_2[0][0], ColorArray.CT_COLOR_ARRAY_2[0][1], ColorArray.CT_COLOR_ARRAY_2[0][2]));
        Glide.with((FragmentActivity) this).asBitmap().load(Integer.valueOf(R.mipmap.bg_ct_color)).into((RequestBuilder<Bitmap>) new CustomViewTarget<ColorPickerView, Bitmap>(((ActSelectColorBinding) this.mViewBinding).cpv) { // from class: com.ltech.smarthome.ui.scene.ActSelectColor.14
            @Override // com.bumptech.glide.request.target.Target
            public void onLoadFailed(Drawable errorDrawable) {
            }

            @Override // com.bumptech.glide.request.target.CustomViewTarget
            protected void onResourceCleared(Drawable placeholder) {
            }

            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(Object resource, Transition transition) {
                onResourceReady((Bitmap) resource, (Transition<? super Bitmap>) transition);
            }

            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                ((ActSelectColorBinding) ActSelectColor.this.mViewBinding).cpv.setBitmapBack(resource);
                ((ActSelectColorBinding) ActSelectColor.this.mViewBinding).cpv.setPickPosition(((255 - ((ActSelectColorVM) ActSelectColor.this.mViewModel).getWy()) * 100.0f) / 255.0f, 50.0f);
            }
        });
        ((ActSelectColorBinding) this.mViewBinding).sbBrt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectColor.15
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((ActSelectColorVM) ActSelectColor.this.mViewModel).setWyBrt(LightUtils.progress2BrtHasBelowOne(progress));
                    ((ActSelectColorBinding) ActSelectColor.this.mViewBinding).tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                    ((ActSelectColorVM) ActSelectColor.this.mViewModel).getLightCmdHelper().sendCtBrtHas1to9(ActSelectColor.this, progress, false);
                } else {
                    ((ActSelectColorVM) ActSelectColor.this.mViewModel).setWyBrt(LightUtils.progress2BrtHasBelowOne(progress));
                    ((ActSelectColorBinding) ActSelectColor.this.mViewBinding).tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((ActSelectColorVM) ActSelectColor.this.mViewModel).setWyBrt(LightUtils.progress2BrtHasBelowOne(seekBar.getProgress()));
                ((ActSelectColorBinding) ActSelectColor.this.mViewBinding).tvW.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                ((ActSelectColorVM) ActSelectColor.this.mViewModel).getLightCmdHelper().sendCtBrtHas1to9(ActSelectColor.this, seekBar.getProgress(), true);
            }
        });
        ((ActSelectColorBinding) this.mViewBinding).tvBrt.setText(((ActSelectColorBinding) this.mViewBinding).sbBrt.getProgressHasBelowOne());
        ((ActSelectColorBinding) this.mViewBinding).cpv.setOnColorChangedListener(new ColorPickerView.OnColorChangedListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectColor.16
            @Override // com.ltech.smarthome.view.ColorPickerView.OnColorChangedListener
            public void onColorChanged(int color, float xPercent, float yPercent) {
                float f = xPercent / 100.0f;
                ((ActSelectColorVM) ActSelectColor.this.mViewModel).setWy(255 - LightUtils.percent2C(f));
                ((ActSelectColorBinding) ActSelectColor.this.mViewBinding).vColor.setBackgroundColor(color);
                ((ActSelectColorBinding) ActSelectColor.this.mViewBinding).etRed.setText(String.valueOf(((ActSelectColorVM) ActSelectColor.this.mViewModel).getWy()));
                ((ActSelectColorBinding) ActSelectColor.this.mViewBinding).etGreen.setText(String.valueOf(255 - ((ActSelectColorVM) ActSelectColor.this.mViewModel).getWy()));
                ((ActSelectColorVM) ActSelectColor.this.mViewModel).getLightCmdHelper().sendCTDE(ActSelectColor.this, 255 - LightUtils.percent2C(f), false);
            }

            @Override // com.ltech.smarthome.view.ColorPickerView.OnColorChangedListener
            public void onChangedFinish(int color, float xPercent, float yPercent) {
                LHomeLog.i(getClass(), "color onChangedFinish---> current thread" + Thread.currentThread());
                float f = xPercent / 100.0f;
                ((ActSelectColorVM) ActSelectColor.this.mViewModel).setWy(255 - LightUtils.percent2C(f));
                ((ActSelectColorBinding) ActSelectColor.this.mViewBinding).vColor.setBackgroundColor(color);
                ((ActSelectColorBinding) ActSelectColor.this.mViewBinding).etRed.setText(String.valueOf(((ActSelectColorVM) ActSelectColor.this.mViewModel).getWy()));
                ((ActSelectColorBinding) ActSelectColor.this.mViewBinding).etGreen.setText(String.valueOf(255 - ((ActSelectColorVM) ActSelectColor.this.mViewModel).getWy()));
                ((ActSelectColorVM) ActSelectColor.this.mViewModel).getLightCmdHelper().sendCTDE(ActSelectColor.this, 255 - LightUtils.percent2C(f), true);
                ActSelectColor.this.ctChangeNum = 0;
            }
        });
        ((ActSelectColorBinding) this.mViewBinding).etRed.setListener(new ColorEditText.OnColorTextChangedListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectColor$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.view.ColorEditText.OnColorTextChangedListener
            public final void onTextChangedListener(String str) {
                ActSelectColor.this.lambda$initCtView$6(str);
            }
        });
        ((ActSelectColorBinding) this.mViewBinding).etGreen.setListener(new ColorEditText.OnColorTextChangedListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectColor$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.view.ColorEditText.OnColorTextChangedListener
            public final void onTextChangedListener(String str) {
                ActSelectColor.this.lambda$initCtView$7(str);
            }
        });
        ((ActSelectColorBinding) this.mViewBinding).vColor.setBackgroundColor(Color.rgb(ColorArray.CT_COLOR_ARRAY_2[255 - ((ActSelectColorVM) this.mViewModel).getWy()][0], ColorArray.CT_COLOR_ARRAY_2[255 - ((ActSelectColorVM) this.mViewModel).getWy()][1], ColorArray.CT_COLOR_ARRAY_2[255 - ((ActSelectColorVM) this.mViewModel).getWy()][2]));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initCtView$6(String str) {
        LHomeLog.i(getClass(), "etRed.setListener---> current thread" + Thread.currentThread());
        int parseInt = Integer.parseInt(str);
        ((ActSelectColorVM) this.mViewModel).setWy(parseInt);
        int i = 255 - parseInt;
        ((ActSelectColorBinding) this.mViewBinding).etGreen.setText(String.valueOf(i));
        ((ActSelectColorBinding) this.mViewBinding).etRed.selectEnd();
        ((ActSelectColorBinding) this.mViewBinding).vColor.setBackgroundColor(Color.rgb(ColorArray.CT_COLOR_ARRAY_2[i][0], ColorArray.CT_COLOR_ARRAY_2[i][1], ColorArray.CT_COLOR_ARRAY_2[i][2]));
        ((ActSelectColorBinding) this.mViewBinding).cpv.setPickPosition(((255 - ((ActSelectColorVM) this.mViewModel).getWy()) * 100.0f) / 255.0f, ((ActSelectColorBinding) this.mViewBinding).cpv.getCurYPercent());
        runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.scene.ActSelectColor.17
            @Override // java.lang.Runnable
            public void run() {
                LightAssistant lightCmdHelper = ((ActSelectColorVM) ActSelectColor.this.mViewModel).getLightCmdHelper();
                ActSelectColor actSelectColor = ActSelectColor.this;
                lightCmdHelper.sendCTDE(actSelectColor, ((ActSelectColorVM) actSelectColor.mViewModel).getWy(), true);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initCtView$7(String str) {
        LHomeLog.i(getClass(), "etRed.setListener---> current thread" + Thread.currentThread());
        int parseInt = Integer.parseInt(str);
        int i = 255 - parseInt;
        ((ActSelectColorVM) this.mViewModel).setWy(i);
        ((ActSelectColorBinding) this.mViewBinding).etRed.setText(String.valueOf(i));
        ((ActSelectColorBinding) this.mViewBinding).etGreen.selectEnd();
        ((ActSelectColorBinding) this.mViewBinding).vColor.setBackgroundColor(Color.rgb(ColorArray.CT_COLOR_ARRAY_2[parseInt][0], ColorArray.CT_COLOR_ARRAY_2[parseInt][1], ColorArray.CT_COLOR_ARRAY_2[parseInt][2]));
        ((ActSelectColorBinding) this.mViewBinding).cpv.setPickPosition(((255 - ((ActSelectColorVM) this.mViewModel).getWy()) * 100.0f) / 255.0f, ((ActSelectColorBinding) this.mViewBinding).cpv.getCurYPercent());
        runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.scene.ActSelectColor.18
            @Override // java.lang.Runnable
            public void run() {
                LightAssistant lightCmdHelper = ((ActSelectColorVM) ActSelectColor.this.mViewModel).getLightCmdHelper();
                ActSelectColor actSelectColor = ActSelectColor.this;
                lightCmdHelper.sendCTDE(actSelectColor, ((ActSelectColorVM) actSelectColor.mViewModel).getWy(), true);
            }
        });
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            setResult(3001);
            finishActivity();
        }
    }
}