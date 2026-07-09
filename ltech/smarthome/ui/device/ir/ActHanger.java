package com.ltech.smarthome.ui.device.ir;

import android.view.View;
import android.view.animation.Animation;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
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
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActHanger extends BaseControlActivity<ActMotorBinding, ActMotorVM> implements ISelectAction {
    private Animation.AnimationListener animationListener = new Animation.AnimationListener() { // from class: com.ltech.smarthome.ui.device.ir.ActHanger.1
        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
            ((ActMotorBinding) ActHanger.this.mViewBinding).title.ivTip.setBackgroundResource(R.drawable.shape_circle_red);
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            ((ActMotorBinding) ActHanger.this.mViewBinding).title.ivTip.setBackgroundResource(R.drawable.shape_circle_gray);
        }
    };

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_motor;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$edit$4(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        ((ActMotorBinding) this.mViewBinding).ivDevice.setImageResource(R.mipmap.pic_hanger);
        ((ActMotorVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActMotorVM) this.mViewModel).selectAction = getIntent().getBooleanExtra(Constants.SELECT_ACTION, false);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Device433Repository.getMotorKeyItem(Device433Repository.IR_AIRER_KEY_NAME_UP));
        arrayList.add(Device433Repository.getMotorKeyItem(Device433Repository.IR_AIRER_KEY_NAME_STOP));
        arrayList.add(Device433Repository.getMotorKeyItem(Device433Repository.IR_AIRER_KEY_NAME_DOWN));
        arrayList.add(Device433Repository.getMotorKeyItem(Device433Repository.IR_AIRER_KEY_NAME_LIGHTING));
        arrayList.add(Device433Repository.getMotorKeyItem(Device433Repository.IR_AIRER_KEY_NAME_WIND_DRY));
        arrayList.add(Device433Repository.getMotorKeyItem(Device433Repository.IR_AIRER_KEY_NAME_FIRE_DRY));
        arrayList.add(Device433Repository.getMotorKeyItem(Device433Repository.IR_AIRER_KEY_NAME_DISINFECT));
        final BaseQuickAdapter<MotorKeyItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<MotorKeyItem, BaseViewHolder>(R.layout.item_ir_key, arrayList) { // from class: com.ltech.smarthome.ui.device.ir.ActHanger.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, MotorKeyItem item) {
                helper.getView(R.id.layout_bg).getLayoutParams().height = ((ActMotorBinding) ActHanger.this.mViewBinding).rvContent.getMeasuredHeight() / 2;
                helper.setImageResource(R.id.iv_icon, item.getIconRes()).setText(R.id.tv_name, item.getName());
            }
        };
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.ir.ActHanger$$ExternalSyntheticLambda1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActHanger.this.lambda$initView$0(baseQuickAdapter, baseQuickAdapter2, view, i);
            }
        });
        baseQuickAdapter.bindToRecyclerView(((ActMotorBinding) this.mViewBinding).rvContent);
        ((ActMotorBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(this, 4));
        ((ActMotorBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((ActMotorBinding) this.mViewBinding).curtainLeft.setVisibility(8);
        ((ActMotorBinding) this.mViewBinding).curtainRight.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
        ((ActMotorVM) this.mViewModel).sendMotorCmd((MotorKeyItem) baseQuickAdapter.getData().get(i));
        ViewHelpUtil.zoomInZoomOut(view, this.animationListener);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        if (((ActMotorVM) this.mViewModel).selectAction) {
            ((ActMotorVM) this.mViewModel).controlDevice.setValue((Device) SceneHelper.instance().controlObject);
            ((ActMotorVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.ActHanger$$ExternalSyntheticLambda2
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActHanger.this.lambda$startObserve$1((Device) obj);
                }
            });
        } else if (((ActMotorVM) this.mViewModel).controlId != -1) {
            Injection.repo().device().getDeviceFromDb(((ActMotorVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.ActHanger$$ExternalSyntheticLambda3
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActHanger.this.lambda$startObserve$2((Device) obj);
                }
            });
            ((ActMotorVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.ir.ActHanger$$ExternalSyntheticLambda4
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActHanger.this.lambda$startObserve$3((Device) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        if (device != null) {
            setTitle(device.getDeviceName());
            setEditString(getString(R.string.save));
            MotorParam motorParam = (MotorParam) device.getParam(MotorParam.class);
            ((ActMotorVM) this.mViewModel).motorCodeLib = motorParam.getIrDatas();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Device device) {
        ((ActMotorVM) this.mViewModel).controlDevice.setValue(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Device device) {
        if (device != null) {
            setTitle(device.getDeviceName());
            setEditImage(R.mipmap.ic_setting);
            MotorParam motorParam = (MotorParam) device.getParam(MotorParam.class);
            ((ActMotorVM) this.mViewModel).motorCodeLib = motorParam.getIrDatas();
        }
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
            SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.device.ir.ActHanger$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActHanger.this.lambda$edit$4((Boolean) obj);
                }
            });
            return;
        }
        NavHelper.goSetting(((ActMotorVM) this.mViewModel).controlDevice.getValue());
    }
}