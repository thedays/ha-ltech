package com.ltech.smarthome.ui.device.ir;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Animation;
import android.widget.SeekBar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import anetwork.channel.util.RequestConstant;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActMotorBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.MotorParam;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.smart.message.utils.LHomeLog;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActMotor extends BaseControlActivity<ActMotorBinding, ActMotorVM> implements ISelectAction {
    private int DEFAULT_PROGRESS = 50;
    private Animation.AnimationListener animationListener = new Animation.AnimationListener() { // from class: com.ltech.smarthome.ui.device.ir.ActMotor.1
        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
            ((ActMotorBinding) ActMotor.this.mViewBinding).title.ivTip.setBackgroundResource(R.drawable.shape_circle_red);
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            ((ActMotorBinding) ActMotor.this.mViewBinding).title.ivTip.setBackgroundResource(R.drawable.shape_circle_gray);
        }
    };
    ValueAnimator animator;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_motor;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void lambda$edit$2(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        ((ActMotorBinding) this.mViewBinding).ivDevice.setImageResource(R.mipmap.cgcurtain_pic);
        ((ActMotorVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActMotorVM) this.mViewModel).selectAction = getIntent().getBooleanExtra(Constants.SELECT_ACTION, false);
        if (((ActMotorVM) this.mViewModel).selectAction) {
            setEditString(getString(R.string.save));
        } else {
            setEditImage(R.mipmap.ic_setting);
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(Device433Repository.getMotorKeyItem(Device433Repository.IR_MOTOR_KEY_NAME_UP));
        arrayList.add(Device433Repository.getMotorKeyItem(Device433Repository.IR_MOTOR_KEY_NAME_STOP));
        arrayList.add(Device433Repository.getMotorKeyItem(Device433Repository.IR_MOTOR_KEY_NAME_DOWN));
        final BaseQuickAdapter<MotorKeyItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<MotorKeyItem, BaseViewHolder>(R.layout.item_ir_key, arrayList) { // from class: com.ltech.smarthome.ui.device.ir.ActMotor.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, MotorKeyItem item) {
                helper.getView(R.id.layout_bg).getLayoutParams().height = ((ActMotorBinding) ActMotor.this.mViewBinding).rvContent.getMeasuredHeight() / 2;
                helper.setImageResource(R.id.iv_icon, item.getIconRes()).setText(R.id.tv_name, item.getName());
            }
        };
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.ir.ActMotor$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActMotor.this.lambda$initView$0(baseQuickAdapter, baseQuickAdapter2, view, i);
            }
        });
        baseQuickAdapter.bindToRecyclerView(((ActMotorBinding) this.mViewBinding).rvContent);
        ((ActMotorBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(this, 3));
        ((ActMotorBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        initCurtainProgress(this.DEFAULT_PROGRESS);
        ((ActMotorBinding) this.mViewBinding).curtainLeft.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.device.ir.ActMotor.3
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((ActMotorBinding) ActMotor.this.mViewBinding).curtainRight.setProgress(progress);
                }
            }
        });
        ((ActMotorBinding) this.mViewBinding).curtainRight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.device.ir.ActMotor.4
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((ActMotorBinding) ActMotor.this.mViewBinding).curtainLeft.setProgress(progress);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        if (i == 0) {
            setAnimation(((ActMotorBinding) this.mViewBinding).curtainLeft.getProgress(), this.DEFAULT_PROGRESS);
        }
        if (i != 1 && i == 2) {
            setAnimation(((ActMotorBinding) this.mViewBinding).curtainLeft.getProgress(), this.DEFAULT_PROGRESS + 100);
        }
        ((ActMotorVM) this.mViewModel).sendMotorCmd((MotorKeyItem) baseQuickAdapter.getData().get(i));
        ViewHelpUtil.zoomInZoomOut(view, this.animationListener);
    }

    private void initCurtainProgress(int progress) {
        ((ActMotorBinding) this.mViewBinding).curtainLeft.setMinProgressValue(progress);
        ((ActMotorBinding) this.mViewBinding).curtainRight.setMinProgressValue(progress);
        int i = progress + 100;
        ((ActMotorBinding) this.mViewBinding).curtainLeft.setMax(i);
        ((ActMotorBinding) this.mViewBinding).curtainRight.setMax(i);
        ((ActMotorBinding) this.mViewBinding).curtainLeft.setProgress(i);
        ((ActMotorBinding) this.mViewBinding).curtainRight.setProgress(i);
        ((ActMotorBinding) this.mViewBinding).curtainLeft.setEnabled(false);
        ((ActMotorBinding) this.mViewBinding).curtainRight.setEnabled(false);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActMotorVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.ActMotor$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActMotor.this.lambda$startObserve$1((Device) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        if (device != null) {
            setTitle(device.getDeviceName());
            MotorParam motorParam = (MotorParam) device.getParam(MotorParam.class);
            ((ActMotorVM) this.mViewModel).motorCodeLib = motorParam.getIrDatas();
        }
    }

    private void startObjectObserve() {
        if (((ActMotorVM) this.mViewModel).selectAction) {
            ((ActMotorVM) this.mViewModel).controlDevice.setValue((Device) SceneHelper.instance().controlObject);
        } else if (((ActMotorVM) this.mViewModel).controlId != -1) {
            ((ActMotorVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDeviceById(((ActMotorVM) this.mViewModel).controlId));
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        startObjectObserve();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (((ActMotorVM) this.mViewModel).selectAction) {
            if (((ActMotorVM) this.mViewModel).mMotorCmdParam == null) {
                SmartToast.showShort(R.string.please_choose);
                return;
            }
            ((ActMotorVM) this.mViewModel).mMotorCmdParam.addExtParam(SceneHelper.OPTION_VALUE, ((ActMotorVM) this.mViewModel).cmdName);
            ((ActMotorVM) this.mViewModel).mMotorCmdParam.addExtParam(SceneHelper.OPTION, "0");
            SceneHelper.instance().cmdParam = ((ActMotorVM) this.mViewModel).mMotorCmdParam;
            SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.device.ir.ActMotor$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActMotor.this.lambda$edit$2((Boolean) obj);
                }
            });
            return;
        }
        NavHelper.goSetting(((ActMotorVM) this.mViewModel).controlDevice.getValue());
    }

    private void setAnimation(final int startProgress, final int endProgress) {
        LHomeLog.i(RequestConstant.ENV_TEST, getClass(), "startprogress-->" + startProgress + "  endprogress-->" + endProgress);
        ValueAnimator duration = ValueAnimator.ofInt(startProgress, endProgress).setDuration((long) ((Math.abs(endProgress - startProgress) * 6500) / 100));
        this.animator = duration;
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.ltech.smarthome.ui.device.ir.ActMotor.5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                LHomeLog.i(RequestConstant.ENV_TEST, getClass(), valueAnimator.getAnimatedValue() + "");
                if (ActMotor.this.mViewBinding != null) {
                    ((ActMotorBinding) ActMotor.this.mViewBinding).curtainLeft.setProgress(((Integer) valueAnimator.getAnimatedValue()).intValue());
                    ((ActMotorBinding) ActMotor.this.mViewBinding).curtainRight.setProgress(((Integer) valueAnimator.getAnimatedValue()).intValue());
                }
            }
        });
        this.animator.start();
    }
}