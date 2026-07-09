package com.ltech.smarthome.ui.device.dalipro;

import android.os.Bundle;
import android.text.TextUtils;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.databinding.FtDimLightBinding;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.view.RectProgressBar2;
import java.util.Objects;

/* loaded from: classes4.dex */
public class FtDimLight extends BaseNormalFragment<FtDimLightBinding> {
    private boolean firstIn = true;
    private ActDaliLightOrGroupVM mViewModel;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_dim_light;
    }

    public static FtDimLight newInstance(long deviceId, long controlId, boolean isGroup) {
        FtDimLight ftDimLight = new FtDimLight();
        Bundle bundle = new Bundle();
        bundle.putLong("device_id", deviceId);
        bundle.putLong(Constants.CONTROL_ID, controlId);
        bundle.putBoolean(Constants.GROUP_CONTROL, isGroup);
        ftDimLight.setArguments(bundle);
        return ftDimLight;
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
        ((FtDimLightBinding) this.mViewBinding).progressBar.setOnProgressChangeListener(new RectProgressBar2.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDimLight.1
            @Override // com.ltech.smarthome.view.RectProgressBar2.OnProgressChangeListener
            public void onStartProgressChanged(RectProgressBar2 bar) {
            }

            @Override // com.ltech.smarthome.view.RectProgressBar2.OnProgressChangeListener
            public void onProgressChanged(RectProgressBar2 bar) {
                FtDimLight.this.mViewModel.getLightCmdHelper().sendDimBrtD1Has1to9(FtDimLight.this.getActivity(), bar.getProgress(), false);
                ((FtDimLightBinding) FtDimLight.this.mViewBinding).tvBrt.setText(bar.getProgressPercent());
            }

            @Override // com.ltech.smarthome.view.RectProgressBar2.OnProgressChangeListener
            public void onStopProgressChanged(RectProgressBar2 bar) {
                FtDimLight.this.mViewModel.brt = LightUtils.progress2BrtHasBelowOne(bar.getProgress());
                FtDimLight.this.mViewModel.getLightCmdHelper().sendDimBrtD1Has1to9(FtDimLight.this.getActivity(), bar.getProgress(), true);
                ((FtDimLightBinding) FtDimLight.this.mViewBinding).tvBrt.setText(bar.getProgressPercent());
            }
        });
        ((FtDimLightBinding) this.mViewBinding).tvBrt.setText(((FtDimLightBinding) this.mViewBinding).progressBar.getProgressPercent());
        if (this.mViewModel.selectAction) {
            if (!TextUtils.isEmpty(SceneHelper.instance().selectInstruct)) {
                String lightCmdData = SceneHelper.getLightCmdData(SceneHelper.instance().selectInstruct);
                if (lightCmdData.length() >= 12) {
                    this.mViewModel.brt = Integer.parseInt(lightCmdData.substring(6, 8), 16);
                    return;
                } else if (lightCmdData.length() >= 4) {
                    this.mViewModel.brt = Integer.parseInt(lightCmdData.substring(2, 4), 16);
                    return;
                } else {
                    this.mViewModel.brt = 255;
                    return;
                }
            }
            this.mViewModel.brt = 255;
        }
    }

    private void changeSelectView() {
        ((FtDimLightBinding) this.mViewBinding).progressBar.setCanChangeProgress(true);
        ((FtDimLightBinding) this.mViewBinding).progressBar.setCurrentProgress(LightUtils.brt2ProgressHasBelowZero(this.mViewModel.brt));
        ((FtDimLightBinding) this.mViewBinding).tvBrt.setText(((FtDimLightBinding) this.mViewBinding).progressBar.getProgressPercent());
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        this.mViewModel.controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDimLight$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDimLight.this.lambda$startObserve$0(obj);
            }
        });
        this.mViewModel.stateOn.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDimLight$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDimLight.this.lambda$startObserve$1((Boolean) obj);
            }
        });
        this.mViewModel.brtProgress.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDimLight$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDimLight.this.lambda$startObserve$2((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Object obj) {
        if (this.mViewModel.selectAction) {
            changeSelectView();
            return;
        }
        if (this.mViewModel.controlObject.getValue() instanceof Group) {
            Group group = (Group) this.mViewModel.controlObject.getValue();
            this.mViewModel.brt = LightUtils.progress2BrtHasBelowOne(group.getGroupState().getWyBrt());
            this.mViewModel.stateOn.setValue(Boolean.valueOf(group.getGroupState().isOn()));
        } else {
            Device device = (Device) this.mViewModel.controlObject.getValue();
            this.mViewModel.brt = LightUtils.progress2BrtHasBelowOne(device.getDeviceState().getWyBrt());
            this.mViewModel.stateOn.setValue(Boolean.valueOf(device.getDeviceState().isOn()));
        }
        int progress = ((FtDimLightBinding) this.mViewBinding).progressBar.getProgress();
        this.mViewModel.brtProgress.setValue(Integer.valueOf(LightUtils.brt2ProgressHasBelowZero(this.mViewModel.brt)));
        if (!this.firstIn) {
            ((FtDimLightBinding) this.mViewBinding).progressBar.setAnimation(progress, ((FtDimLightBinding) this.mViewBinding).progressBar.getProgress());
        } else {
            ((FtDimLightBinding) this.mViewBinding).progressBar.invalidate();
        }
        this.firstIn = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Boolean bool) {
        FragmentActivity activity;
        int i;
        ((FtDimLightBinding) this.mViewBinding).progressBar.setCanChangeProgress(bool.booleanValue());
        ((FtDimLightBinding) this.mViewBinding).progressBar.setAlpha(bool.booleanValue() ? 1.0f : 0.5f);
        ((FtDimLightBinding) this.mViewBinding).tvBrt.setAlpha(bool.booleanValue() ? 1.0f : 0.5f);
        ConstraintLayout constraintLayout = ((FtDimLightBinding) this.mViewBinding).layoutRoot;
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
        ((FtDimLightBinding) this.mViewBinding).progressBar.setCurrentProgress(num.intValue());
        ((FtDimLightBinding) this.mViewBinding).tvBrt.setText(((FtDimLightBinding) this.mViewBinding).progressBar.getProgressPercent());
    }
}