package com.ltech.smarthome.ui.config;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.FtStepsIntroductionBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Step;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.CountDownProgressBar;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.yanzhenjie.permission.runtime.Permission;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class FtStepsIntroduction extends BaseNormalFragment<FtStepsIntroductionBinding> {
    private static final int REQUEST_ENABLE_BLUETOOTH = 1021;
    private static final int REQUEST_ENABLE_LOCATION = 1022;
    private int curStep;
    private int curType;
    private AnimationDrawable mLoadingAnimationDrawable;
    private OnStepsIntroductionListener mOnStepsIntroductionListener;
    private int totalSteps;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_steps_introduction;
    }

    public static FtStepsIntroduction create(Step step, int curStep, int totalSteps) {
        FtStepsIntroduction ftStepsIntroduction = new FtStepsIntroduction();
        Bundle bundle = new Bundle();
        bundle.putString("data", GsonUtils.toJson(step));
        bundle.putInt("curStep", curStep);
        bundle.putInt("totalSteps", totalSteps);
        ftStepsIntroduction.setArguments(bundle);
        return ftStepsIntroduction;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        ((FtStepsIntroductionBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.config.FtStepsIntroduction$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                FtStepsIntroduction.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        OnStepsIntroductionListener onStepsIntroductionListener;
        if (view.getId() == R.id.bt_next) {
            if (this.curStep == this.totalSteps) {
                int i = this.curType;
                if (i == 0) {
                    lastBtnClick();
                    return;
                } else {
                    if (i == 2 && checkPhotoPermission()) {
                        NavUtils.destination(ActQrCodeScan.class).navigation(getActivity());
                        return;
                    }
                    return;
                }
            }
            OnStepsIntroductionListener onStepsIntroductionListener2 = this.mOnStepsIntroductionListener;
            if (onStepsIntroductionListener2 != null) {
                onStepsIntroductionListener2.next();
                return;
            }
            return;
        }
        if (view.getId() != R.id.bt_previous || (onStepsIntroductionListener = this.mOnStepsIntroductionListener) == null) {
            return;
        }
        onStepsIntroductionListener.previous();
    }

    public void lastBtnClick() {
        check();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (getArguments() != null) {
            Step step = (Step) GsonUtils.fromJson(getArguments().getString("data", ""), new TypeToken<Step>(this) { // from class: com.ltech.smarthome.ui.config.FtStepsIntroduction.1
            }.getType());
            this.curStep = getArguments().getInt("curStep", 0);
            this.totalSteps = getArguments().getInt("totalSteps", 0);
            this.curType = step.getType();
            int i = this.totalSteps;
            if (i != 0) {
                if (step != null) {
                    bindView(step, this.curStep, i);
                }
            } else if (getActivity() != null) {
                SmartToast.showShort("请检查配置");
                getActivity().finish();
            }
        }
    }

    private void bindView(final Step step, int curStep, int totalSteps) {
        ((FtStepsIntroductionBinding) this.mViewBinding).tvSteps.setText(step.getTitle());
        ArrayList arrayList = new ArrayList();
        if (totalSteps == 2) {
            arrayList.add(Integer.valueOf(R.mipmap.pic_add_num_12_2));
            arrayList.add(Integer.valueOf(R.mipmap.pic_add_num_12_1));
        } else if (totalSteps == 3) {
            arrayList.add(Integer.valueOf(R.mipmap.pic_add_num_123_1));
            arrayList.add(Integer.valueOf(R.mipmap.pic_add_num_123_2));
            arrayList.add(Integer.valueOf(R.mipmap.pic_add_num_123_3));
        } else if (totalSteps == 4) {
            arrayList.add(Integer.valueOf(R.mipmap.pic_add_num_1234_1));
            arrayList.add(Integer.valueOf(R.mipmap.pic_add_num_1234_2));
            arrayList.add(Integer.valueOf(R.mipmap.pic_add_num_1234_3));
            arrayList.add(Integer.valueOf(R.mipmap.pic_add_num_1234_4));
        } else if (totalSteps == 5) {
            arrayList.add(Integer.valueOf(R.mipmap.pic_add_num_12345_1));
            arrayList.add(Integer.valueOf(R.mipmap.pic_add_num_12345_2));
            arrayList.add(Integer.valueOf(R.mipmap.pic_add_num_12345_3));
            arrayList.add(Integer.valueOf(R.mipmap.pic_add_num_12345_4));
            arrayList.add(Integer.valueOf(R.mipmap.pic_add_num_12345_5));
        }
        if (curStep == 1) {
            ((FtStepsIntroductionBinding) this.mViewBinding).btPrevious.setVisibility(8);
            ((FtStepsIntroductionBinding) this.mViewBinding).ivSteps.setImageResource(((Integer) arrayList.get(0)).intValue());
        } else if (curStep == 2) {
            ((FtStepsIntroductionBinding) this.mViewBinding).btPrevious.setVisibility(0);
            ((FtStepsIntroductionBinding) this.mViewBinding).ivSteps.setImageResource(((Integer) arrayList.get(1)).intValue());
        } else if (curStep == 3) {
            ((FtStepsIntroductionBinding) this.mViewBinding).btPrevious.setVisibility(0);
            ((FtStepsIntroductionBinding) this.mViewBinding).ivSteps.setImageResource(((Integer) arrayList.get(2)).intValue());
        } else if (curStep == 4) {
            ((FtStepsIntroductionBinding) this.mViewBinding).btPrevious.setVisibility(0);
            ((FtStepsIntroductionBinding) this.mViewBinding).ivSteps.setImageResource(((Integer) arrayList.get(3)).intValue());
        } else if (curStep == 5) {
            ((FtStepsIntroductionBinding) this.mViewBinding).btPrevious.setVisibility(0);
            ((FtStepsIntroductionBinding) this.mViewBinding).ivSteps.setImageResource(((Integer) arrayList.get(4)).intValue());
        }
        if (totalSteps == curStep) {
            ((FtStepsIntroductionBinding) this.mViewBinding).btNext.setText(getLastBtnName());
        }
        if (step.getType() != 1) {
            ((FtStepsIntroductionBinding) this.mViewBinding).barCountdown.setVisibility(8);
            ((FtStepsIntroductionBinding) this.mViewBinding).ivBg.setVisibility(0);
            ((FtStepsIntroductionBinding) this.mViewBinding).ivBg.setImageResource(step.getImg());
            if (((FtStepsIntroductionBinding) this.mViewBinding).ivBg.getDrawable() instanceof AnimationDrawable) {
                this.mLoadingAnimationDrawable = (AnimationDrawable) ((FtStepsIntroductionBinding) this.mViewBinding).ivBg.getDrawable();
                new Handler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.config.FtStepsIntroduction.2
                    @Override // java.lang.Runnable
                    public void run() {
                        FtStepsIntroduction.this.mLoadingAnimationDrawable.start();
                    }
                }, 1000L);
                return;
            }
            return;
        }
        ((FtStepsIntroductionBinding) this.mViewBinding).btNext.setEnabled(false);
        ((FtStepsIntroductionBinding) this.mViewBinding).btNext.setBackgroundResource(R.drawable.shape_gray_bt);
        if (step.getImg() == 0) {
            ((FtStepsIntroductionBinding) this.mViewBinding).barCountdown.setVisibility(0);
            ((FtStepsIntroductionBinding) this.mViewBinding).ivBg.setVisibility(8);
        } else {
            ((FtStepsIntroductionBinding) this.mViewBinding).barCountdown.setVisibility(8);
            ((FtStepsIntroductionBinding) this.mViewBinding).ivBg.setVisibility(0);
            ((FtStepsIntroductionBinding) this.mViewBinding).ivBg.setImageResource(step.getImg());
            ((FtStepsIntroductionBinding) this.mViewBinding).barCountdown.setOnUpdateListener(new CountDownProgressBar.OnUpdateListener() { // from class: com.ltech.smarthome.ui.config.FtStepsIntroduction$$ExternalSyntheticLambda4
                @Override // com.ltech.smarthome.view.CountDownProgressBar.OnUpdateListener
                public final void onUpdate(int i) {
                    FtStepsIntroduction.this.lambda$bindView$1(step, i);
                }
            });
        }
        ((FtStepsIntroductionBinding) this.mViewBinding).barCountdown.setDuration(step.getCountDown(), new CountDownProgressBar.OnFinishListener() { // from class: com.ltech.smarthome.ui.config.FtStepsIntroduction.3
            @Override // com.ltech.smarthome.view.CountDownProgressBar.OnFinishListener
            public void onFinish() {
                if (FtStepsIntroduction.this.mViewBinding != null) {
                    ((FtStepsIntroductionBinding) FtStepsIntroduction.this.mViewBinding).btNext.setEnabled(true);
                    ((FtStepsIntroductionBinding) FtStepsIntroduction.this.mViewBinding).btNext.setText(R.string.next);
                    ((FtStepsIntroductionBinding) FtStepsIntroduction.this.mViewBinding).btNext.setBackgroundResource(R.drawable.shape_red_bt);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindView$1(Step step, int i) {
        ((FtStepsIntroductionBinding) this.mViewBinding).btNext.setText(getString(R.string.next_with_time, Math.round(((step.getCountDown() / 100.0f) - i) / 10.0f) + getString(R.string.sec)));
    }

    protected String getLastBtnName() {
        return getString(this.curType == 0 ? R.string.ble_search : R.string.scan_qr_code);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        AnimationDrawable animationDrawable = this.mLoadingAnimationDrawable;
        if (animationDrawable != null) {
            animationDrawable.stop();
        }
    }

    private void check() {
        if (getActivity() != null && bleIsOk()) {
            if (!Injection.state().isLocationEnabled(getContext())) {
                MessageDialog.show((AppCompatActivity) getActivity(), getString(R.string.tips), getString(R.string.open_gps_tip)).setCancelable(false).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.config.FtStepsIntroduction$$ExternalSyntheticLambda2
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        boolean lambda$check$2;
                        lambda$check$2 = FtStepsIntroduction.this.lambda$check$2(baseDialog, view);
                        return lambda$check$2;
                    }
                }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.config.FtStepsIntroduction$$ExternalSyntheticLambda3
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        boolean lambda$check$3;
                        lambda$check$3 = FtStepsIntroduction.this.lambda$check$3(baseDialog, view);
                        return lambda$check$3;
                    }
                });
                return;
            }
            nav();
            if (getActivity() != null) {
                getActivity().setResult(1002);
                ActivityCompat.finishAfterTransition(getActivity());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$check$2(BaseDialog baseDialog, View view) {
        startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 1022);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$check$3(BaseDialog baseDialog, View view) {
        getPermissionFail();
        return false;
    }

    private boolean checkPhotoPermission() {
        int checkSelfPermission;
        if (Build.VERSION.SDK_INT >= 23) {
            checkSelfPermission = getActivity().checkSelfPermission(Permission.CAMERA);
            if (checkSelfPermission != 0) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Permission.CAMERA}, 6666);
                return false;
            }
        }
        return true;
    }

    private void getPermissionFail() {
        SmartToast.showShort(R.string.permission_deny);
        if (getActivity() != null) {
            getActivity().setResult(1003);
        }
        ActivityCompat.finishAfterTransition(getActivity());
    }

    private void nav() {
        NavUtils.destination(ActMeshNearDevice.class).navigation(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStepsIntroductionListener) {
            this.mOnStepsIntroductionListener = (OnStepsIntroductionListener) context;
        }
    }
}