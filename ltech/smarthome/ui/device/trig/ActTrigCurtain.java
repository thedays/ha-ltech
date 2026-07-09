package com.ltech.smarthome.ui.device.trig;

import android.animation.ValueAnimator;
import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import anetwork.channel.util.RequestConstant;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActTrigCurtainBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.DryContactBleParam;
import com.ltech.smarthome.model.device_param.TrigExtParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.trig.TrigRepository;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.smart.message.utils.LHomeLog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActTrigCurtain extends BaseControlActivity<ActTrigCurtainBinding, ActTrigCurtainVM> implements ISelectAction {
    private static final int LEFT_ONLY = 3;
    private static final int LEFT_RIGHT = 1;
    private static final int UP_DOWN = 2;
    ValueAnimator animator;
    BaseQuickAdapter<TrigRepository.TrigItem, BaseViewHolder> keyAdapter;
    private int subType;
    List<TrigRepository.TrigItem> keyItemList = new ArrayList();
    private int type = -1;
    private int DEFAULT_PROGRESS = 50;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_trig_curtain;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$edit$1(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        ((ActTrigCurtainVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActTrigCurtainVM) this.mViewModel).selectAction = getIntent().getBooleanExtra(Constants.SELECT_ACTION, false);
        ((ActTrigCurtainVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        BaseQuickAdapter<TrigRepository.TrigItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<TrigRepository.TrigItem, BaseViewHolder>(R.layout.item_trig_key, this.keyItemList) { // from class: com.ltech.smarthome.ui.device.trig.ActTrigCurtain.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, TrigRepository.TrigItem item) {
                helper.getView(R.id.layout_bg).getLayoutParams().height = ((ActTrigCurtainBinding) ActTrigCurtain.this.mViewBinding).rvContent.getMeasuredHeight() / 2;
                helper.setImageResource(R.id.iv_icon, item.bgRes).setText(R.id.tv_name, item.name);
            }
        };
        this.keyAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigCurtain$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActTrigCurtain.this.lambda$initView$0(baseQuickAdapter2, view, i);
            }
        });
        this.DEFAULT_PROGRESS = 50;
        initCurtainProgress(50);
        initCurtainUpDownProgress(this.DEFAULT_PROGRESS);
        this.DEFAULT_PROGRESS = 30;
        initCurtainLeftOnlyProgress(30);
        this.keyAdapter.bindToRecyclerView(((ActTrigCurtainBinding) this.mViewBinding).rvContent);
        ((ActTrigCurtainBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(this, 3));
        ((ActTrigCurtainBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        if (i == 0) {
            int i2 = this.type;
            if (i2 == 1) {
                setAnimation(((ActTrigCurtainBinding) this.mViewBinding).curtainLeft.getProgress(), this.DEFAULT_PROGRESS);
            } else if (i2 == 3) {
                setAnimation(((ActTrigCurtainBinding) this.mViewBinding).curtainLeftOnly.getProgress(), this.DEFAULT_PROGRESS);
            } else if (i2 == 2) {
                setAnimation(((ActTrigCurtainBinding) this.mViewBinding).curtainUpDown.getProgress(), this.DEFAULT_PROGRESS);
            }
        }
        if (i != 1 && i == 2) {
            int i3 = this.type;
            if (i3 == 1) {
                setAnimation(((ActTrigCurtainBinding) this.mViewBinding).curtainLeft.getProgress(), this.DEFAULT_PROGRESS + 100);
            } else if (i3 == 3) {
                setAnimation(((ActTrigCurtainBinding) this.mViewBinding).curtainLeftOnly.getProgress(), this.DEFAULT_PROGRESS + 100);
            } else if (i3 == 2) {
                setAnimation(((ActTrigCurtainBinding) this.mViewBinding).curtainUpDown.getProgress(), this.DEFAULT_PROGRESS + 100);
            }
        }
        ((ActTrigCurtainVM) this.mViewModel).setTrigOpenCloseValue(this.keyItemList.get(i).spanCount);
    }

    private void initCurtainProgress(int progress) {
        ((ActTrigCurtainBinding) this.mViewBinding).curtainLeft.setMinProgressValue(progress);
        ((ActTrigCurtainBinding) this.mViewBinding).curtainRight.setMinProgressValue(progress);
        int i = progress + 100;
        ((ActTrigCurtainBinding) this.mViewBinding).curtainLeft.setMax(i);
        ((ActTrigCurtainBinding) this.mViewBinding).curtainRight.setMax(i);
        ((ActTrigCurtainBinding) this.mViewBinding).curtainLeft.setProgress(i);
        ((ActTrigCurtainBinding) this.mViewBinding).curtainRight.setProgress(i);
        ((ActTrigCurtainBinding) this.mViewBinding).curtainLeft.setEnabled(false);
        ((ActTrigCurtainBinding) this.mViewBinding).curtainRight.setEnabled(false);
    }

    private void initCurtainLeftOnlyProgress(int progress) {
        ((ActTrigCurtainBinding) this.mViewBinding).curtainLeftOnly.setMinProgressValue(progress);
        int i = progress + 100;
        ((ActTrigCurtainBinding) this.mViewBinding).curtainLeftOnly.setMax(i);
        ((ActTrigCurtainBinding) this.mViewBinding).curtainLeftOnly.setProgress(i);
        ((ActTrigCurtainBinding) this.mViewBinding).curtainLeftOnly.setEnabled(false);
    }

    private void initCurtainUpDownProgress(int progress) {
        ((ActTrigCurtainBinding) this.mViewBinding).curtainUpDown.setMinProgressValue(progress);
        int i = progress + 100;
        ((ActTrigCurtainBinding) this.mViewBinding).curtainUpDown.setMax(i);
        ((ActTrigCurtainBinding) this.mViewBinding).curtainUpDown.setProgress(i);
        ((ActTrigCurtainBinding) this.mViewBinding).curtainUpDown.setEnabled(false);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        if (((ActTrigCurtainVM) this.mViewModel).selectAction) {
            ((ActTrigCurtainVM) this.mViewModel).controlObject.setValue((Device) SceneHelper.instance().controlObject);
            ((ActTrigCurtainVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigCurtain$$ExternalSyntheticLambda1
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActTrigCurtain.this.lambda$startObserve$1(obj);
                }
            });
        } else if (((ActTrigCurtainVM) this.mViewModel).controlId != -1) {
            ((ActTrigCurtainVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigCurtain$$ExternalSyntheticLambda2
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActTrigCurtain.this.lambda$startObserve$2(obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Object obj) {
        if (obj instanceof Device) {
            setTitle(((Device) obj).getDeviceName());
            setEditString(getString(R.string.save));
            ((ActTrigCurtainBinding) this.mViewBinding).ivDevice.setImageResource(R.mipmap.pic_curtain);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Object obj) {
        String str;
        if (obj instanceof Device) {
            Device device = (Device) obj;
            setTitle(device.getDeviceName());
            setEditImage(R.mipmap.ic_setting);
            DryContactBleParam dryContactBleParam = (DryContactBleParam) device.getParam(DryContactBleParam.class);
            if (dryContactBleParam != null) {
                this.subType = dryContactBleParam.getSubType();
            }
            str = device.getExtParam();
            ((ActTrigCurtainVM) this.mViewModel).setTrigType(this.subType + 1);
        } else if (obj instanceof Group) {
            Group group = (Group) obj;
            this.subType = ProductRepository.getLightColorType((Object) group) == 16 ? 3 : 0;
            String extParam = group.getExtParam();
            setTitle(group.getGroupName());
            setEditImage(R.mipmap.ic_setting);
            str = extParam;
        } else {
            str = null;
        }
        if (str != null && !TextUtils.isEmpty(str)) {
            TrigExtParam trigExtParam = new TrigExtParam();
            trigExtParam.fillMapWithString(str);
            int curtainType = trigExtParam.getCurtainType();
            if (curtainType == -1 || curtainType == 0) {
                ((ActTrigCurtainBinding) this.mViewBinding).ivDevice.setImageResource(R.mipmap.cgcurtain_pic);
                ((ActTrigCurtainBinding) this.mViewBinding).curtainLeft.setVisibility(0);
                ((ActTrigCurtainBinding) this.mViewBinding).curtainRight.setVisibility(0);
                ((ActTrigCurtainBinding) this.mViewBinding).curtainUpDown.setVisibility(8);
                ((ActTrigCurtainBinding) this.mViewBinding).curtainLeftOnly.setVisibility(8);
                this.DEFAULT_PROGRESS = 50;
                this.type = 1;
                this.keyItemList.clear();
                this.keyItemList.addAll(TrigRepository.getDefaultCurtainItemList());
                this.keyAdapter.notifyDataSetChanged();
            } else if (curtainType == 1) {
                ((ActTrigCurtainBinding) this.mViewBinding).ivDevice.setImageResource(R.mipmap.cgcurtain_pic2);
                ((ActTrigCurtainBinding) this.mViewBinding).curtainUpDown.setVisibility(0);
                ((ActTrigCurtainBinding) this.mViewBinding).curtainLeft.setVisibility(8);
                ((ActTrigCurtainBinding) this.mViewBinding).curtainRight.setVisibility(8);
                ((ActTrigCurtainBinding) this.mViewBinding).curtainLeftOnly.setVisibility(8);
                this.DEFAULT_PROGRESS = 50;
                this.type = 2;
                this.keyItemList.clear();
                this.keyItemList.addAll(TrigRepository.getDefaultCurtainItemList());
                this.keyAdapter.notifyDataSetChanged();
            } else if (curtainType == 2) {
                ((ActTrigCurtainBinding) this.mViewBinding).ivDevice.setImageResource(R.mipmap.cgcurtain_pic);
                ((ActTrigCurtainBinding) this.mViewBinding).curtainLeft.setVisibility(8);
                ((ActTrigCurtainBinding) this.mViewBinding).curtainRight.setVisibility(8);
                ((ActTrigCurtainBinding) this.mViewBinding).curtainUpDown.setVisibility(8);
                ((ActTrigCurtainBinding) this.mViewBinding).curtainLeftOnly.setVisibility(0);
                this.DEFAULT_PROGRESS = 30;
                this.type = 3;
                this.keyItemList.clear();
                this.keyItemList.addAll(TrigRepository.getDefaultCurtainItemList());
                this.keyAdapter.notifyDataSetChanged();
            }
            if (trigExtParam.getBtnChannels() != null) {
                for (int i = 0; i < 3; i++) {
                    this.keyItemList.get(i).spanCount = trigExtParam.getBtnChannels().get(i).intValue();
                }
                return;
            }
            return;
        }
        ((ActTrigCurtainBinding) this.mViewBinding).ivDevice.setImageResource(R.mipmap.cgcurtain_pic);
        ((ActTrigCurtainBinding) this.mViewBinding).curtainLeft.setVisibility(0);
        ((ActTrigCurtainBinding) this.mViewBinding).curtainRight.setVisibility(0);
        ((ActTrigCurtainBinding) this.mViewBinding).curtainUpDown.setVisibility(8);
        ((ActTrigCurtainBinding) this.mViewBinding).curtainLeftOnly.setVisibility(8);
        this.DEFAULT_PROGRESS = 50;
        this.type = 1;
        this.keyItemList.clear();
        this.keyItemList.addAll(TrigRepository.getDefaultCurtainItemList());
        this.keyAdapter.notifyDataSetChanged();
    }

    private void startObjectObserve() {
        if (((ActTrigCurtainVM) this.mViewModel).groupControl) {
            ((ActTrigCurtainVM) this.mViewModel).controlObject.setValue(Injection.repo().group().getGroupById(((ActTrigCurtainVM) this.mViewModel).controlId));
        } else {
            ((ActTrigCurtainVM) this.mViewModel).controlObject.setValue(Injection.repo().device().getDeviceById(((ActTrigCurtainVM) this.mViewModel).controlId));
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        startObjectObserve();
        if (this.subType == 0) {
            if (this.type == 2) {
                this.keyItemList = TrigRepository.getUpDownCurtainItemList();
            } else {
                this.keyItemList = TrigRepository.getDefaultCurtainItemList();
            }
        } else {
            this.keyItemList = TrigRepository.getDefaultDreamCurtainItemList();
        }
        this.keyAdapter.setNewData(this.keyItemList);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (((ActTrigCurtainVM) this.mViewModel).selectAction) {
            return;
        }
        NavHelper.goSetting(((ActTrigCurtainVM) this.mViewModel).controlObject.getValue());
    }

    private void setAnimation(final int startProgress, final int endProgress) {
        LHomeLog.i(RequestConstant.ENV_TEST, getClass(), "startprogress-->" + startProgress + "  endprogress-->" + endProgress);
        ValueAnimator duration = ValueAnimator.ofInt(startProgress, endProgress).setDuration((long) ((Math.abs(endProgress - startProgress) * 6500) / 100));
        this.animator = duration;
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigCurtain.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (ActTrigCurtain.this.mViewBinding != null) {
                    if (ActTrigCurtain.this.type == 1) {
                        ((ActTrigCurtainBinding) ActTrigCurtain.this.mViewBinding).curtainLeft.setProgress(((Integer) valueAnimator.getAnimatedValue()).intValue());
                        ((ActTrigCurtainBinding) ActTrigCurtain.this.mViewBinding).curtainRight.setProgress(((Integer) valueAnimator.getAnimatedValue()).intValue());
                    } else if (ActTrigCurtain.this.type == 3) {
                        ((ActTrigCurtainBinding) ActTrigCurtain.this.mViewBinding).curtainLeftOnly.setProgress(((Integer) valueAnimator.getAnimatedValue()).intValue());
                    } else if (ActTrigCurtain.this.type == 2) {
                        ((ActTrigCurtainBinding) ActTrigCurtain.this.mViewBinding).curtainUpDown.setProgress(((Integer) valueAnimator.getAnimatedValue()).intValue());
                    }
                }
            }
        });
        this.animator.start();
    }
}