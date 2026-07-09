package com.ltech.smarthome.ui.device.dalipro;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.SeekBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.databinding.FtRgbLightBinding;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.view.CircleColorPickerView;
import com.ltech.smarthome.view.LightBrtBar;
import java.util.Objects;

/* loaded from: classes4.dex */
public class FtRgbLight extends BaseNormalFragment<FtRgbLightBinding> {
    private BaseQuickAdapter<Integer, BaseViewHolder> colorAdapter;
    private ActDaliLightOrGroupVM mViewModel;
    private int number;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_rgb_light;
    }

    public static FtRgbLight newInstance(long deviceId, long controlId, boolean isGroup) {
        FtRgbLight ftRgbLight = new FtRgbLight();
        Bundle bundle = new Bundle();
        bundle.putLong("device_id", deviceId);
        bundle.putLong(Constants.CONTROL_ID, controlId);
        bundle.putBoolean(Constants.GROUP_CONTROL, isGroup);
        ftRgbLight.setArguments(bundle);
        return ftRgbLight;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (isAdded() && (ActivityUtils.getTopActivity() instanceof ActDaliLightOrGroup)) {
            ((ActDaliLightOrGroup) Objects.requireNonNull(getActivity())).onFragmentCreated();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initData() {
        this.mViewModel = (ActDaliLightOrGroupVM) new ViewModelProvider(requireActivity()).get(ActDaliLightOrGroupVM.class);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.mViewModel.deviceId = arguments.getLong("device_id");
            this.mViewModel.groupControl = arguments.getBoolean(Constants.GROUP_CONTROL, false);
            this.mViewModel.controlId = arguments.getLong(Constants.CONTROL_ID, -1L);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        setColorView();
        setRgbView();
        if (this.mViewModel.selectAction) {
            if (!TextUtils.isEmpty(SceneHelper.instance().selectInstruct)) {
                String lightCmdData = SceneHelper.getLightCmdData(SceneHelper.instance().selectInstruct);
                if (lightCmdData.length() >= 12) {
                    this.mViewModel.red = Integer.parseInt(lightCmdData.substring(0, 2), 16);
                    this.mViewModel.green = Integer.parseInt(lightCmdData.substring(2, 4), 16);
                    this.mViewModel.blue = Integer.parseInt(lightCmdData.substring(4, 6), 16);
                    this.mViewModel.brt = Integer.parseInt(lightCmdData.substring(10, 12), 16);
                    return;
                }
                if (lightCmdData.length() >= 4) {
                    this.mViewModel.brt = Integer.parseInt(lightCmdData.substring(2, 4), 16);
                    if (lightCmdData.length() >= 10) {
                        this.mViewModel.red = Integer.parseInt(lightCmdData.substring(4, 6), 16);
                        this.mViewModel.green = Integer.parseInt(lightCmdData.substring(6, 8), 16);
                        this.mViewModel.blue = Integer.parseInt(lightCmdData.substring(8, 10), 16);
                        return;
                    }
                    return;
                }
                this.mViewModel.brt = 255;
                return;
            }
            this.mViewModel.brt = 255;
        }
    }

    private void changeSelectView() {
        ((FtRgbLightBinding) this.mViewBinding).sbBrt.setProgress(LightUtils.brt2ProgressHasBelowZero(this.mViewModel.brt));
        ((FtRgbLightBinding) this.mViewBinding).tvBrt.setText(((FtRgbLightBinding) this.mViewBinding).sbBrt.getProgressHasBelowOne());
        ((FtRgbLightBinding) this.mViewBinding).ccpv.setEnabled(true);
        ((FtRgbLightBinding) this.mViewBinding).ccpv.setCanChangeColor(true);
        ((FtRgbLightBinding) this.mViewBinding).rvColor.setEnabled(true);
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        this.mViewModel.controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRgbLight$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtRgbLight.this.lambda$startObserve$0(obj);
            }
        });
        this.mViewModel.stateOn.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRgbLight$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtRgbLight.this.lambda$startObserve$1((Boolean) obj);
            }
        });
        this.mViewModel.brtProgress.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRgbLight$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtRgbLight.this.lambda$startObserve$2((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Object obj) {
        if (this.mViewModel.selectAction) {
            changeSelectView();
            return;
        }
        if (obj instanceof Group) {
            Group group = (Group) obj;
            this.mViewModel.brt = LightUtils.progress2BrtHasBelowOne(group.getGroupState().getRgbBrt());
            this.mViewModel.stateOn.setValue(Boolean.valueOf(group.getGroupState().isOn()));
        } else {
            Device device = (Device) obj;
            this.mViewModel.brt = LightUtils.progress2BrtHasBelowOne(device.getDeviceState().getRgbBrt());
            this.mViewModel.stateOn.setValue(Boolean.valueOf(device.getDeviceState().isOn()));
        }
        this.mViewModel.brtProgress.setValue(Integer.valueOf(LightUtils.brt2ProgressHasBelowZero(this.mViewModel.brt)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Boolean bool) {
        FragmentActivity activity;
        int i;
        ((FtRgbLightBinding) this.mViewBinding).ccpv.setEnabled(bool.booleanValue());
        ((FtRgbLightBinding) this.mViewBinding).ccpv.setAlpha(bool.booleanValue() ? 1.0f : 0.5f);
        ((FtRgbLightBinding) this.mViewBinding).ccpv.setCanChangeColor(bool.booleanValue());
        ((FtRgbLightBinding) this.mViewBinding).sbBrt.setEnabled(bool.booleanValue());
        ((FtRgbLightBinding) this.mViewBinding).sbBrt.setAlpha(bool.booleanValue() ? 1.0f : 0.5f);
        ((FtRgbLightBinding) this.mViewBinding).rvColor.setEnabled(bool.booleanValue());
        ((FtRgbLightBinding) this.mViewBinding).rvColor.setAlpha(bool.booleanValue() ? 1.0f : 0.5f);
        ((FtRgbLightBinding) this.mViewBinding).tvBrt.setAlpha(bool.booleanValue() ? 1.0f : 0.5f);
        ((FtRgbLightBinding) this.mViewBinding).tvBrtTip.setAlpha(bool.booleanValue() ? 1.0f : 0.5f);
        ConstraintLayout constraintLayout = ((FtRgbLightBinding) this.mViewBinding).layoutRoot;
        if (bool.booleanValue()) {
            activity = getActivity();
            i = R.color.white;
        } else {
            activity = getActivity();
            i = R.color.gray_round_music_background;
        }
        constraintLayout.setBackground(activity.getDrawable(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Integer num) {
        ((FtRgbLightBinding) this.mViewBinding).sbBrt.setProgress(num.intValue());
        ((FtRgbLightBinding) this.mViewBinding).tvBrt.setText(((FtRgbLightBinding) this.mViewBinding).sbBrt.getProgressHasBelowOne());
    }

    private void setColorView() {
        BaseQuickAdapter<Integer, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Integer, BaseViewHolder>(this, R.layout.item_color, this.mViewModel.getColorList(getActivity())) { // from class: com.ltech.smarthome.ui.device.dalipro.FtRgbLight.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Integer item) {
                helper.setImageDrawable(R.id.civ_color, new ColorDrawable(item.intValue()));
            }
        };
        this.colorAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRgbLight$$ExternalSyntheticLambda3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                FtRgbLight.this.lambda$setColorView$3(baseQuickAdapter2, view, i);
            }
        });
        this.colorAdapter.bindToRecyclerView(((FtRgbLightBinding) this.mViewBinding).rvColor);
        ((FtRgbLightBinding) this.mViewBinding).rvColor.setLayoutManager(new GridLayoutManager(getActivity(), 8));
        ((FtRgbLightBinding) this.mViewBinding).rvColor.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.dalipro.FtRgbLight.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(5.0f), 0, ConvertUtils.dp2px(5.0f), 0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setColorView$3(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int intValue = this.colorAdapter.getData().get(i).intValue();
        this.mViewModel.red = Color.red(intValue);
        this.mViewModel.green = Color.green(intValue);
        this.mViewModel.blue = Color.blue(intValue);
        this.mViewModel.getLightCmdHelper().sendRgbDC(getActivity(), intValue, true);
    }

    private void setRgbView() {
        ((FtRgbLightBinding) this.mViewBinding).ccpv.setOnColorChangedListener(new CircleColorPickerView.OnColorChangedListener() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRgbLight.3
            @Override // com.ltech.smarthome.view.CircleColorPickerView.OnColorChangedListener
            public void onColorChanged(int color, float xPercent, float yPercent) {
                FtRgbLight.this.number++;
                FtRgbLight.this.mViewModel.getLightCmdHelper().sendRgb(FtRgbLight.this.getActivity(), color, false);
            }

            @Override // com.ltech.smarthome.view.CircleColorPickerView.OnColorChangedListener
            public void onChangedFinish(int color, float xPercent, float yPercent) {
                FtRgbLight.this.mViewModel.red = Color.red(color);
                FtRgbLight.this.mViewModel.green = Color.green(color);
                FtRgbLight.this.mViewModel.blue = Color.blue(color);
                if (FtRgbLight.this.number > 10) {
                    FtRgbLight.this.mViewModel.getLightCmdHelper().sendRgb(FtRgbLight.this.getActivity(), color, true);
                } else {
                    FtRgbLight.this.mViewModel.getLightCmdHelper().sendRgbDC(FtRgbLight.this.getActivity(), color, true);
                }
                FtRgbLight.this.number = 0;
            }
        });
        ((FtRgbLightBinding) this.mViewBinding).sbBrt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.device.dalipro.FtRgbLight.4
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    FtRgbLight.this.mViewModel.getLightCmdHelper().sendDimBrtD1Has1to9(FtRgbLight.this.getActivity(), progress, false);
                    ((FtRgbLightBinding) FtRgbLight.this.mViewBinding).tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                FtRgbLight.this.mViewModel.brt = LightUtils.progress2BrtHasBelowOne(seekBar.getProgress());
                FtRgbLight.this.mViewModel.getLightCmdHelper().sendDimBrtD1Has1to9(FtRgbLight.this.getActivity(), seekBar.getProgress(), true);
                ((FtRgbLightBinding) FtRgbLight.this.mViewBinding).tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
            }
        });
        ((FtRgbLightBinding) this.mViewBinding).tvBrt.setText(((FtRgbLightBinding) this.mViewBinding).sbBrt.getProgressHasBelowOne());
    }
}