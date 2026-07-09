package com.ltech.smarthome.ui.device.light;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.FtColorCircleBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.light.ActColorLightVM;
import com.ltech.smarthome.ui.device.light.CtControlHelper;
import com.ltech.smarthome.ui.device.light.FtColorCircle;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.view.ColorDotView;
import com.ltech.smarthome.view.ColorPickerPointView;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.TextSeekBarView;
import com.ltech.smarthome.view.dialog.ColorBrtControlDialog;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.popup.BrtPopup;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class FtColorCircle extends BaseNormalFragment<FtColorCircleBinding> {
    private BaseQuickAdapter<Integer, BaseViewHolder> colorAdapter;
    private CtControlHelper ctHelper;
    private BaseQuickAdapter<ActColorLightVM.Item, BaseViewHolder> deviceAdpater;
    private Device mSeleDevice;
    private ActColorCCLightVM mViewModel;
    private int number;
    private boolean rgb_on_off;
    private int selPos;
    private boolean wy_on_off;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_color_circle;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        this.mViewModel = (ActColorCCLightVM) new ViewModelProvider(requireActivity()).get(ActColorCCLightVM.class);
        initDevicesRv();
        setRgbcwView();
        setColorView();
        ((FtColorCircleBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.light.FtColorCircle$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                FtColorCircle.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        switch (view.getId()) {
            case R.id.iv_change_brt /* 2131296972 */:
                showColorBrt();
                break;
            case R.id.iv_change_pic /* 2131296973 */:
                this.mViewModel.showPicBgDialog();
                break;
            case R.id.iv_cw_brt /* 2131297002 */:
                showCwBrtPopup();
                break;
            case R.id.iv_gradient /* 2131297081 */:
                this.mViewModel.gradientMode.setValue(true);
                break;
            case R.id.iv_normal /* 2131297158 */:
                this.mViewModel.gradientMode.setValue(false);
                break;
            case R.id.iv_rgb_brt /* 2131297214 */:
                showRgbBrtPopup();
                break;
            case R.id.iv_sort /* 2131297260 */:
                this.mViewModel.showSort();
                break;
            case R.id.tv_ct1 /* 2131298550 */:
                ((FtColorCircleBinding) this.mViewBinding).tvCt1.setTextColor(getResources().getColor(R.color.color_text_black));
                ((FtColorCircleBinding) this.mViewBinding).tvCt2.setTextColor(getResources().getColor(R.color.color_text_gray));
                ((FtColorCircleBinding) this.mViewBinding).ct1Seekbar.setVisibility(0);
                ((FtColorCircleBinding) this.mViewBinding).ct2Seekbar.setVisibility(8);
                ((FtColorCircleBinding) this.mViewBinding).ct1Seekbar.setProgress(this.mViewModel.totalK);
                ((FtColorCircleBinding) this.mViewBinding).tvValue.setText(this.mViewModel.totalK + "K");
                break;
            case R.id.tv_ct2 /* 2131298551 */:
                ((FtColorCircleBinding) this.mViewBinding).tvCt1.setTextColor(getResources().getColor(R.color.color_text_gray));
                ((FtColorCircleBinding) this.mViewBinding).tvCt2.setTextColor(getResources().getColor(R.color.color_text_black));
                ((FtColorCircleBinding) this.mViewBinding).ct1Seekbar.setVisibility(8);
                ((FtColorCircleBinding) this.mViewBinding).ct2Seekbar.setVisibility(0);
                this.ctHelper.setProgress(this.mViewModel.f6271c);
                break;
        }
    }

    private void setRgbcwView() {
        setGradientRgbView();
        ((FtColorCircleBinding) this.mViewBinding).ccpv.setOnColorChangedListener(new ColorPickerPointView.OnColorChangedListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorCircle.1
            @Override // com.ltech.smarthome.view.ColorPickerPointView.OnColorChangedListener
            public void onColorStarted(int color, ColorPickerPointView.PointInfo pointInfo, int selectedIndex) {
            }

            @Override // com.ltech.smarthome.view.ColorPickerPointView.OnColorChangedListener
            public void onSelectedPosition(ColorPickerPointView.PointInfo pointInfo, int selectedPointIndex) {
            }

            @Override // com.ltech.smarthome.view.ColorPickerPointView.OnColorChangedListener
            public void onColorChanged(int color, ColorPickerPointView.PointInfo pointInfo, int selectedPointIndex) {
                FtColorCircle.this.number++;
                FtColorCircle.this.mViewModel.getLightCmdHelper().sendRgb(FtColorCircle.this.getContext(), color, false);
            }

            @Override // com.ltech.smarthome.view.ColorPickerPointView.OnColorChangedListener
            public void onChangedFinish(int color, ColorPickerPointView.PointInfo pointInfo, int selectedPointIndex) {
                FtColorCircle.this.mViewModel.red = Color.red(color);
                FtColorCircle.this.mViewModel.green = Color.green(color);
                FtColorCircle.this.mViewModel.blue = Color.blue(color);
                if (FtColorCircle.this.number > 10) {
                    FtColorCircle.this.mViewModel.getLightCmdHelper().sendRgb(FtColorCircle.this.getContext(), color, true);
                } else {
                    FtColorCircle.this.mViewModel.getLightCmdHelper().sendRgbDC(FtColorCircle.this.getContext(), color, true);
                }
                FtColorCircle.this.number = 0;
            }
        });
        ((FtColorCircleBinding) this.mViewBinding).tvValue.setText(getString(R.string.min_ct));
        ((FtColorCircleBinding) this.mViewBinding).ct1Seekbar.setRange(1000, 20000, 50);
        ((FtColorCircleBinding) this.mViewBinding).ct1Seekbar.setTextGone();
        ((FtColorCircleBinding) this.mViewBinding).ct1Seekbar.setOnProgressChangeListener(new TextSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorCircle$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.TextSeekBarView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z) {
                FtColorCircle.this.lambda$setRgbcwView$1(i, z);
            }
        });
        ((FtColorCircleBinding) this.mViewBinding).brtSeekbar.setRange(1, 100, 0);
        ((FtColorCircleBinding) this.mViewBinding).brtSeekbar.setOnProgressChangeListener(new TextSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorCircle$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.view.TextSeekBarView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z) {
                FtColorCircle.this.lambda$setRgbcwView$2(i, z);
            }
        });
        ((FtColorCircleBinding) this.mViewBinding).brtSeekbar.setValue(getString(R.string.percent_1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setRgbcwView$1(int i, boolean z) {
        ((FtColorCircleBinding) this.mViewBinding).tvValue.setText(i + "K");
        this.mViewModel.getLightCmdHelper(true).sendTotalK(getContext(), i, z);
        this.mViewModel.totalK = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setRgbcwView$2(int i, boolean z) {
        ActColorCCLightVM actColorCCLightVM = this.mViewModel;
        actColorCCLightVM.getLightCmdHelper(actColorCCLightVM.changeK).sendTotalBrt(getContext(), i, z);
        ((FtColorCircleBinding) this.mViewBinding).brtSeekbar.setValue(LightUtils.getProgressHasBelowOne(i));
        this.mViewModel.setTotalBrt(LightUtils.progress2BrtHasBelowOne(i));
    }

    private void setGradientRgbView() {
        ((FtColorCircleBinding) this.mViewBinding).ccpv2.setOnColorChangedListener(new ColorPickerPointView.OnColorChangedListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorCircle.2
            @Override // com.ltech.smarthome.view.ColorPickerPointView.OnColorChangedListener
            public void onColorStarted(int color, ColorPickerPointView.PointInfo pointInfo, int selectedIndex) {
            }

            @Override // com.ltech.smarthome.view.ColorPickerPointView.OnColorChangedListener
            public void onColorChanged(int color, ColorPickerPointView.PointInfo pointInfo, int selectedPointIndex) {
                ActColorLightVM.Item item = (ActColorLightVM.Item) FtColorCircle.this.deviceAdpater.getData().get(selectedPointIndex);
                item.setColor(color);
                FtColorCircle.this.deviceAdpater.setData(selectedPointIndex, item);
                FtColorCircle.this.number++;
                FtColorCircle.this.mViewModel.getLightCmdHelper(FtColorCircle.this.mSeleDevice).sendRgb(FtColorCircle.this.getContext(), color, false);
            }

            @Override // com.ltech.smarthome.view.ColorPickerPointView.OnColorChangedListener
            public void onChangedFinish(int color, ColorPickerPointView.PointInfo pointInfo, int selectedPointIndex) {
                FtColorCircle.this.mViewModel.lightControlEvent.call();
                ActColorLightVM.Item item = (ActColorLightVM.Item) FtColorCircle.this.deviceAdpater.getData().get(selectedPointIndex);
                item.setColor(color);
                FtColorCircle.this.deviceAdpater.setData(selectedPointIndex, item);
                FtColorCircle.this.mViewModel.red = Color.red(color);
                FtColorCircle.this.mViewModel.green = Color.green(color);
                FtColorCircle.this.mViewModel.blue = Color.blue(color);
                if (FtColorCircle.this.number > 10) {
                    FtColorCircle.this.mViewModel.getLightCmdHelper(FtColorCircle.this.mSeleDevice).sendRgb(FtColorCircle.this.getContext(), color, true);
                } else {
                    FtColorCircle.this.mViewModel.getLightCmdHelper(FtColorCircle.this.mSeleDevice).sendRgbDC(FtColorCircle.this.getContext(), color, true);
                }
                FtColorCircle.this.number = 0;
            }

            @Override // com.ltech.smarthome.view.ColorPickerPointView.OnColorChangedListener
            public void onSelectedPosition(ColorPickerPointView.PointInfo pointInfo, int selectedPointIndex) {
                if (selectedPointIndex >= FtColorCircle.this.deviceAdpater.getData().size() || FtColorCircle.this.selPos == selectedPointIndex) {
                    return;
                }
                ActColorLightVM.Item item = (ActColorLightVM.Item) FtColorCircle.this.deviceAdpater.getData().get(selectedPointIndex);
                item.setSel(true);
                FtColorCircle.this.deviceAdpater.setData(selectedPointIndex, item);
                FtColorCircle.this.mSeleDevice = item.getDevice();
                ActColorLightVM.Item item2 = (ActColorLightVM.Item) FtColorCircle.this.deviceAdpater.getData().get(FtColorCircle.this.selPos);
                item2.setSel(false);
                FtColorCircle.this.deviceAdpater.setData(FtColorCircle.this.selPos, item2);
                FtColorCircle.this.selPos = selectedPointIndex;
                ((FtColorCircleBinding) FtColorCircle.this.mViewBinding).rvDevices.scrollToPosition(selectedPointIndex);
            }
        });
    }

    private void initDevicesRv() {
        ((FtColorCircleBinding) this.mViewBinding).rvDevices.setLayoutManager(new LinearLayoutManager(getActivity(), 0, false));
        RecyclerView recyclerView = ((FtColorCircleBinding) this.mViewBinding).rvDevices;
        BaseQuickAdapter<ActColorLightVM.Item, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ActColorLightVM.Item, BaseViewHolder>(R.layout.item_gradient_group_device) { // from class: com.ltech.smarthome.ui.device.light.FtColorCircle.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder holder, final ActColorLightVM.Item item) {
                holder.setImageResource(R.id.iv, ProductRepository.getProductIcon(item.getDevice()));
                ((ColorDotView) holder.getView(R.id.color_dot_view)).setDotColor(item.getColor());
                holder.setText(R.id.tv, item.getDevice().getName());
                SwitchButton switchButton = (SwitchButton) holder.getView(R.id.sb);
                switchButton.setCheckedNotByUser(item.isOn());
                holder.setBackgroundRes(R.id.bg, item.isSel() ? R.drawable.shape_blue_stroke_bt_sel : R.drawable.shape_white_round_bg_10);
                switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorCircle.3.1
                    @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                        item.setOn(isChecked);
                        FtColorCircle.this.mViewModel.getLightCmdHelper(item.getDevice()).sendOnOff(FtColorCircle.this.getActivity(), false);
                    }
                });
            }
        };
        this.deviceAdpater = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        ((FtColorCircleBinding) this.mViewBinding).rvDevices.setHasFixedSize(true);
        ((FtColorCircleBinding) this.mViewBinding).rvDevices.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.light.FtColorCircle.4
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.right = ConvertUtils.dp2px(5.0f);
                outRect.left = ConvertUtils.dp2px(5.0f);
            }
        });
        this.deviceAdpater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorCircle.5
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                if (FtColorCircle.this.selPos != i) {
                    ActColorLightVM.Item item = (ActColorLightVM.Item) FtColorCircle.this.deviceAdpater.getData().get(i);
                    item.setSel(true);
                    FtColorCircle.this.deviceAdpater.setData(i, item);
                    FtColorCircle.this.mSeleDevice = item.getDevice();
                    ActColorLightVM.Item item2 = (ActColorLightVM.Item) FtColorCircle.this.deviceAdpater.getData().get(FtColorCircle.this.selPos);
                    item2.setSel(false);
                    FtColorCircle.this.deviceAdpater.setData(FtColorCircle.this.selPos, item2);
                    FtColorCircle.this.selPos = i;
                    ((FtColorCircleBinding) FtColorCircle.this.mViewBinding).ccpv2.setSelectedPointIndex(i);
                }
            }
        });
        if (this.mViewModel.selectAction) {
            ((FtColorCircleBinding) this.mViewBinding).layoutGradient.setVisibility(8);
            ((FtColorCircleBinding) this.mViewBinding).layoutNormal.setVisibility(0);
            setSingleColorPickerMode();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        super.startObserve();
        this.mViewModel.refreshObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.FtColorCircle$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtColorCircle.this.lambda$startObserve$4((Boolean) obj);
            }
        });
        this.mViewModel.controlObject.observe(this, new Observer<Object>() { // from class: com.ltech.smarthome.ui.device.light.FtColorCircle.6
            @Override // androidx.lifecycle.Observer
            public void onChanged(Object o) {
                if (o instanceof Group) {
                    ((FtColorCircleBinding) FtColorCircle.this.mViewBinding).ivGradient.setVisibility(0);
                    FtColorCircle.this.setMultiColorPickerMode();
                } else {
                    ((FtColorCircleBinding) FtColorCircle.this.mViewBinding).ivGradient.setVisibility(8);
                }
                FtColorCircle.this.setSingleColorPickerMode();
            }
        });
        this.mViewModel.gradientMode.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.device.light.FtColorCircle.7
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    ((FtColorCircleBinding) FtColorCircle.this.mViewBinding).layoutNormal.setVisibility(8);
                    ((FtColorCircleBinding) FtColorCircle.this.mViewBinding).layoutGradient.setVisibility(0);
                } else {
                    ((FtColorCircleBinding) FtColorCircle.this.mViewBinding).layoutNormal.setVisibility(0);
                    ((FtColorCircleBinding) FtColorCircle.this.mViewBinding).layoutGradient.setVisibility(8);
                }
            }
        });
        this.mViewModel.paletteBitmap.observe(this, new Observer<Bitmap>() { // from class: com.ltech.smarthome.ui.device.light.FtColorCircle.8
            @Override // androidx.lifecycle.Observer
            public void onChanged(Bitmap bitmap) {
                if (bitmap != null) {
                    ((FtColorCircleBinding) FtColorCircle.this.mViewBinding).ccpv2.updateCircleBg(bitmap);
                }
            }
        });
        this.mViewModel.shortDevices.observe(this, new Observer<long[]>() { // from class: com.ltech.smarthome.ui.device.light.FtColorCircle.9
            @Override // androidx.lifecycle.Observer
            public void onChanged(long[] ids) {
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                for (long j : ids) {
                    Iterator it = FtColorCircle.this.deviceAdpater.getData().iterator();
                    while (true) {
                        if (it.hasNext()) {
                            ActColorLightVM.Item item = (ActColorLightVM.Item) it.next();
                            if (j == item.getDevice().getDeviceId()) {
                                arrayList2.add(item);
                                arrayList.add(new ColorPickerPointView.PointInfo(item.getX(), item.getY(), ProductRepository.getProductIcon(item.getDevice()), item.isSel()));
                                break;
                            }
                        }
                    }
                }
                FtColorCircle.this.deviceAdpater.replaceData(arrayList2);
                ((FtColorCircleBinding) FtColorCircle.this.mViewBinding).ccpv2.initData(arrayList);
            }
        });
        this.mViewModel.addGradientSceneEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.light.FtColorCircle.10
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                FtColorCircle.this.showCreateGradientSceneDialog();
            }
        });
        this.mViewModel.stateOn.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.device.light.FtColorCircle.11
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean aBoolean) {
                if (Boolean.TRUE.equals(FtColorCircle.this.mViewModel.gradientMode.getValue())) {
                    int i = 0;
                    for (ActColorLightVM.Item item : FtColorCircle.this.deviceAdpater.getData()) {
                        item.setOn(aBoolean.booleanValue());
                        FtColorCircle.this.deviceAdpater.setData(i, item);
                        i++;
                    }
                }
            }
        });
        this.mViewModel.lightControlEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.light.FtColorCircle.12
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                if (Boolean.TRUE.equals(FtColorCircle.this.mViewModel.gradientMode.getValue())) {
                    ActColorLightVM.Item item = (ActColorLightVM.Item) FtColorCircle.this.deviceAdpater.getData().get(FtColorCircle.this.selPos);
                    if (item.isOn()) {
                        return;
                    }
                    item.setOn(true);
                    FtColorCircle.this.deviceAdpater.setData(FtColorCircle.this.selPos, item);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Boolean bool) {
        if (!bool.booleanValue()) {
            this.wy_on_off = this.mViewModel.deviceState.isWOn();
            this.rgb_on_off = this.mViewModel.deviceState.isRGBOn();
            ActColorCCLightVM actColorCCLightVM = this.mViewModel;
            actColorCCLightVM.rgbBrt = LightUtils.progress2BrtIncludeZero(actColorCCLightVM.deviceState.getRgbBrt());
            ActColorCCLightVM actColorCCLightVM2 = this.mViewModel;
            actColorCCLightVM2.wyBrt = LightUtils.progress2BrtIncludeZero(actColorCCLightVM2.deviceState.getWyBrt());
            ActColorCCLightVM actColorCCLightVM3 = this.mViewModel;
            actColorCCLightVM3.totalK = actColorCCLightVM3.deviceState.getTotalK();
            ActColorCCLightVM actColorCCLightVM4 = this.mViewModel;
            actColorCCLightVM4.f6271c = actColorCCLightVM4.deviceState.getCold();
            ActColorCCLightVM actColorCCLightVM5 = this.mViewModel;
            actColorCCLightVM5.w = actColorCCLightVM5.deviceState.getWarm();
            ((FtColorCircleBinding) this.mViewBinding).brtSeekbar.setProgress(this.mViewModel.deviceState.getTotalBrt());
            ((FtColorCircleBinding) this.mViewBinding).brtSeekbar.setValue(LightUtils.getProgressHasBelowOne(this.mViewModel.deviceState.getTotalBrt()));
        } else {
            this.wy_on_off = this.mViewModel.wyBrt > 0;
            this.rgb_on_off = this.mViewModel.rgbBrt > 0;
            ((FtColorCircleBinding) this.mViewBinding).brtSeekbar.setProgress(LightUtils.brt2ProgressHasBelowZero(this.mViewModel.totalBrt));
            ((FtColorCircleBinding) this.mViewBinding).brtSeekbar.setValue(LightUtils.brt2ProgressHasBelowZero(this.mViewModel.totalBrt) + "%");
        }
        this.ctHelper = new CtControlHelper(((FtColorCircleBinding) this.mViewBinding).ct2Seekbar, ((FtColorCircleBinding) this.mViewBinding).tvValue, this.mViewModel.getControlObject(), new CtControlHelper.OnCtChangedListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorCircle$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.ui.device.light.CtControlHelper.OnCtChangedListener
            public final void onRangeChanged(int i, boolean z) {
                FtColorCircle.this.lambda$startObserve$3(i, z);
            }
        });
        ((FtColorCircleBinding) this.mViewBinding).ct1Seekbar.setProgress(this.mViewModel.totalK);
        ((FtColorCircleBinding) this.mViewBinding).tvValue.setText(this.mViewModel.totalK + "K");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(int i, boolean z) {
        this.mViewModel.f6271c = i;
        this.mViewModel.w = 255 - i;
        this.mViewModel.getLightCmdHelper().sendWy(getContext(), this.mViewModel.w, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCreateGradientSceneDialog() {
        EditDialog.asDefault().setContent("").setTitle(getString(R.string.app_str_gradient_scene_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.light.FtColorCircle$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtColorCircle.this.lambda$showCreateGradientSceneDialog$5((String) obj);
            }
        }).showDialog(getActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showCreateGradientSceneDialog$5(String str) {
        this.mViewModel.createGradientScene(str, this.deviceAdpater.getData());
    }

    private void showRgbBrtPopup() {
        BrtPopup.create(getContext()).setControlObject(this.mViewModel.getControlObject()).setFlag(1).setOn(this.rgb_on_off).setOnBrtCallback(new BrtPopup.OnBrtCallback() { // from class: com.ltech.smarthome.ui.device.light.FtColorCircle$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.view.popup.BrtPopup.OnBrtCallback
            public final void onBrtChange(int i) {
                FtColorCircle.this.lambda$showRgbBrtPopup$6(i);
            }
        }).apply().setBrt(LightUtils.brt2ProgressHasBelowZero(this.mViewModel.rgbBrt)).showAtAnchorView(((FtColorCircleBinding) this.mViewBinding).ivRgbBrt, 4, 3, -SizeUtils.dp2px(10.0f), 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showRgbBrtPopup$6(int i) {
        this.mViewModel.rgbBrt = LightUtils.progress2BrtHasBelowOne(i);
    }

    private void showCwBrtPopup() {
        BrtPopup.create(getContext()).setControlObject(this.mViewModel.getControlObject()).setFlag(2).setOn(this.wy_on_off).setOnBrtCallback(new BrtPopup.OnBrtCallback() { // from class: com.ltech.smarthome.ui.device.light.FtColorCircle$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.view.popup.BrtPopup.OnBrtCallback
            public final void onBrtChange(int i) {
                FtColorCircle.this.lambda$showCwBrtPopup$7(i);
            }
        }).apply().setBrt(LightUtils.brt2ProgressHasBelowZero(this.mViewModel.wyBrt)).showAtAnchorView(((FtColorCircleBinding) this.mViewBinding).ivCwBrt, 4, 4, SizeUtils.dp2px(10.0f), 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showCwBrtPopup$7(int i) {
        this.mViewModel.wyBrt = LightUtils.progress2BrtHasBelowOne(i);
    }

    /* renamed from: com.ltech.smarthome.ui.device.light.FtColorCircle$13, reason: invalid class name */
    class AnonymousClass13 extends BaseQuickAdapter<Integer, BaseViewHolder> {
        AnonymousClass13(int layoutResId, List data) {
            super(layoutResId, data);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(final BaseViewHolder helper, final Integer item) {
            helper.setImageDrawable(R.id.civ_color, new ColorDrawable(item.intValue()));
            helper.getView(R.id.civ_color).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.light.FtColorCircle$13$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FtColorCircle.AnonymousClass13.this.lambda$convert$0(item, helper, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(Integer num, final BaseViewHolder baseViewHolder, View view) {
            final ColorDrawable colorDrawable = new ColorDrawable(num.intValue());
            colorDrawable.setAlpha(77);
            baseViewHolder.setImageDrawable(R.id.civ_color, colorDrawable);
            FtColorCircle.this.mViewModel.red = Color.red(num.intValue());
            FtColorCircle.this.mViewModel.green = Color.green(num.intValue());
            FtColorCircle.this.mViewModel.blue = Color.blue(num.intValue());
            FtColorCircle.this.mViewModel.getLightCmdHelper().sendRgbDC(FtColorCircle.this.getContext(), num.intValue(), true);
            ThreadUtils.getMainHandler().postDelayed(new Runnable(this) { // from class: com.ltech.smarthome.ui.device.light.FtColorCircle.13.1
                @Override // java.lang.Runnable
                public void run() {
                    colorDrawable.setAlpha(255);
                    baseViewHolder.setImageDrawable(R.id.civ_color, colorDrawable);
                }
            }, 150L);
        }
    }

    private void setColorView() {
        AnonymousClass13 anonymousClass13 = new AnonymousClass13(R.layout.item_color, this.mViewModel.getColorList(requireContext()));
        this.colorAdapter = anonymousClass13;
        anonymousClass13.bindToRecyclerView(((FtColorCircleBinding) this.mViewBinding).rvColor);
        ((FtColorCircleBinding) this.mViewBinding).rvColor.setLayoutManager(new GridLayoutManager(getContext(), 8));
        ((FtColorCircleBinding) this.mViewBinding).rvColor.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.light.FtColorCircle.14
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(5.0f), 0, ConvertUtils.dp2px(5.0f), 0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSingleColorPickerMode() {
        if (((FtColorCircleBinding) this.mViewBinding).ccpv.getPoints().isEmpty()) {
            ArrayList arrayList = new ArrayList();
            ((FtColorCircleBinding) this.mViewBinding).ccpv.setTouchPointCircle(true);
            arrayList.add(new ColorPickerPointView.PointInfo(((FtColorCircleBinding) this.mViewBinding).ccpv.getWidth() / 2.0f, ((FtColorCircleBinding) this.mViewBinding).ccpv.getWidth() / 2.0f, 0, true));
            ((FtColorCircleBinding) this.mViewBinding).ccpv.initData(arrayList);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMultiColorPickerMode() {
        if (((FtColorCircleBinding) this.mViewBinding).ccpv2.getPoints().isEmpty()) {
            Object value = this.mViewModel.controlObject.getValue();
            if (value instanceof Group) {
                Group group = (Group) value;
                ArrayList arrayList = new ArrayList();
                if (group.getDeviceIds().isEmpty()) {
                    ((FtColorCircleBinding) this.mViewBinding).ivEmptyDevices.setVisibility(0);
                    ((FtColorCircleBinding) this.mViewBinding).tvEmptyDevices.setVisibility(0);
                } else {
                    ((FtColorCircleBinding) this.mViewBinding).ivEmptyDevices.setVisibility(8);
                    ((FtColorCircleBinding) this.mViewBinding).tvEmptyDevices.setVisibility(8);
                    ArrayList arrayList2 = new ArrayList();
                    Iterator<Long> it = group.getDeviceIds().iterator();
                    int i = 0;
                    while (it.hasNext()) {
                        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(it.next().longValue());
                        if (deviceByDeviceId != null) {
                            if (i == 0) {
                                this.mSeleDevice = deviceByDeviceId;
                            }
                            arrayList2.add(new ActColorLightVM.Item(deviceByDeviceId, i == 0, deviceByDeviceId.getDeviceState().isOn()));
                            arrayList.add(new ColorPickerPointView.PointInfo(0.0f, 0.0f, ProductRepository.getProductIcon(deviceByDeviceId), i == 0));
                        }
                        i++;
                    }
                    this.deviceAdpater.setNewData(arrayList2);
                }
                setPaletteBg(group, arrayList);
            }
        }
    }

    private void setPaletteBg(Group group, final List<ColorPickerPointView.PointInfo> infos) {
        if (this.mViewModel.getColorPaletteType() == 3 && group.getColorPaletteUrl() != null) {
            this.mViewModel.bgUrl = group.getColorPaletteUrl();
            Glide.with(getActivity()).asBitmap().load(this.mViewModel.bgUrl).into((RequestBuilder<Bitmap>) new CustomTarget<Bitmap>() { // from class: com.ltech.smarthome.ui.device.light.FtColorCircle.15
                @Override // com.bumptech.glide.request.target.Target
                public void onLoadCleared(Drawable placeholder) {
                }

                @Override // com.bumptech.glide.request.target.Target
                public /* bridge */ /* synthetic */ void onResourceReady(Object bitmap, Transition transition) {
                    onResourceReady((Bitmap) bitmap, (Transition<? super Bitmap>) transition);
                }

                public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                    ((FtColorCircleBinding) FtColorCircle.this.mViewBinding).ccpv2.initData(bitmap, infos);
                }
            });
        } else if (this.mViewModel.getColorPaletteType() == 2) {
            ((FtColorCircleBinding) this.mViewBinding).ccpv2.initData(BitmapFactory.decodeResource(getResources(), R.mipmap.bg_palette_2), infos);
        } else {
            ((FtColorCircleBinding) this.mViewBinding).ccpv2.initData(BitmapFactory.decodeResource(getResources(), R.mipmap.bg_palette_1), infos);
        }
    }

    private void showColorBrt() {
        Object value = this.mViewModel.controlObject.getValue();
        if (value instanceof Group) {
            Group group = (Group) value;
            ColorBrtControlDialog.asDefault(this.mViewModel.colorType).setRgbBrt(LightUtils.brt2ProgressHasBelowZero(group.getGroupState().getRgbBrt())).setCWBrt(LightUtils.brt2ProgressHasBelowZero(group.getGroupState().getWyBrt())).setCw(group.getGroupState().getWy()).setOnColorBrtCallBack(new ColorBrtControlDialog.OnColorBrtCallBack() { // from class: com.ltech.smarthome.ui.device.light.FtColorCircle.16
                @Override // com.ltech.smarthome.view.dialog.ColorBrtControlDialog.OnColorBrtCallBack
                public void onBrt1Change(int brt, boolean finish) {
                    FtColorCircle.this.mViewModel.lightControlEvent.call();
                    FtColorCircle.this.mViewModel.getLightCmdHelper(FtColorCircle.this.mSeleDevice).sendRgbBrtHas1to9(FtColorCircle.this.getActivity(), brt, finish);
                }

                @Override // com.ltech.smarthome.view.dialog.ColorBrtControlDialog.OnColorBrtCallBack
                public void onBrt2Change(int brt, boolean finish) {
                    FtColorCircle.this.mViewModel.lightControlEvent.call();
                    if (FtColorCircle.this.mViewModel.colorType == 4) {
                        FtColorCircle.this.mViewModel.getLightCmdHelper(FtColorCircle.this.mSeleDevice).sendWHas1to9(FtColorCircle.this.getActivity(), brt, finish);
                    } else {
                        FtColorCircle.this.mViewModel.getLightCmdHelper(FtColorCircle.this.mSeleDevice).sendWyBrtHas1to9(FtColorCircle.this.getActivity(), brt, finish);
                    }
                }

                @Override // com.ltech.smarthome.view.dialog.ColorBrtControlDialog.OnColorBrtCallBack
                public void onBrt3Change(int brt, boolean finish) {
                    FtColorCircle.this.mViewModel.lightControlEvent.call();
                    FtColorCircle.this.mViewModel.getLightCmdHelper(FtColorCircle.this.mSeleDevice).sendWy(FtColorCircle.this.getActivity(), brt, finish);
                }
            }).showDialog(getActivity());
        }
    }
}