package com.ltech.smarthome.ui.device.dalipro;

import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.common.primitives.Ints;
import com.loc.at;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.databinding.FtCtLightBinding;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.ui.device.light.CtControlHelper;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.view.RectProgressBar2;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class FtCtLight extends BaseNormalFragment<FtCtLightBinding> {
    private CtControlHelper ctControlHelper;
    private boolean firstIn = true;
    private BaseQuickAdapter<Integer, BaseViewHolder> mQuickSelAdapter;
    private ActDaliLightOrGroupVM mViewModel;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_ct_light;
    }

    public static FtCtLight newInstance(long deviceId, long controlId, boolean isGroup) {
        FtCtLight ftCtLight = new FtCtLight();
        Bundle bundle = new Bundle();
        bundle.putLong("device_id", deviceId);
        bundle.putLong(Constants.CONTROL_ID, controlId);
        bundle.putBoolean(Constants.GROUP_CONTROL, isGroup);
        ftCtLight.setArguments(bundle);
        return ftCtLight;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (isAdded() && (getActivity() instanceof ActDaliLightOrGroup)) {
            ((ActDaliLightOrGroup) getActivity()).onFragmentCreated();
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
        ((FtCtLightBinding) this.mViewBinding).sbBrt.setOnProgressChangeListener(new RectProgressBar2.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.dalipro.FtCtLight.1
            @Override // com.ltech.smarthome.view.RectProgressBar2.OnProgressChangeListener
            public void onStartProgressChanged(RectProgressBar2 bar) {
            }

            @Override // com.ltech.smarthome.view.RectProgressBar2.OnProgressChangeListener
            public void onProgressChanged(RectProgressBar2 bar) {
                FtCtLight.this.mViewModel.brt = LightUtils.progress2BrtHasBelowOne(bar.getProgress());
                FtCtLight.this.mViewModel.getLightCmdHelper().sendDimBrtD1Has1to9(FtCtLight.this.getActivity(), bar.getProgress(), false);
                ((FtCtLightBinding) FtCtLight.this.mViewBinding).tvBrt.setText(bar.getProgressPercent());
            }

            @Override // com.ltech.smarthome.view.RectProgressBar2.OnProgressChangeListener
            public void onStopProgressChanged(RectProgressBar2 bar) {
                FtCtLight.this.mViewModel.brt = LightUtils.progress2BrtHasBelowOne(bar.getProgress());
                FtCtLight.this.mViewModel.getLightCmdHelper().sendDimBrtD1Has1to9(FtCtLight.this.getActivity(), bar.getProgress(), false);
                ((FtCtLightBinding) FtCtLight.this.mViewBinding).tvBrt.setText(bar.getProgressPercent());
            }
        });
        ((FtCtLightBinding) this.mViewBinding).tvBrt.setText(((FtCtLightBinding) this.mViewBinding).sbBrt.getProgressPercent());
        ((FtCtLightBinding) this.mViewBinding).rvCt.setLayoutManager(new LinearLayoutManager(getActivity(), 0, false));
        RecyclerView recyclerView = ((FtCtLightBinding) this.mViewBinding).rvCt;
        BaseQuickAdapter<Integer, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Integer, BaseViewHolder>(this, R.layout.item_ct_color_selector) { // from class: com.ltech.smarthome.ui.device.dalipro.FtCtLight.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder holder, Integer k) {
                holder.setText(R.id.sel_tv, k + at.k);
                holder.setImageDrawable(R.id.iv, new ColorDrawable(Ints.asList(this.mContext.getResources().getIntArray(R.array.k_value_color)).get(holder.getLayoutPosition()).intValue()));
                holder.itemView.setEnabled(true);
            }
        };
        this.mQuickSelAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        ((FtCtLightBinding) this.mViewBinding).rvCt.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.dalipro.FtCtLight.3
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(10.0f), 0, ConvertUtils.dp2px(10.0f), 0);
            }
        });
        this.mQuickSelAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.dalipro.FtCtLight.4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                int ctK2Y = 255 - LightUtils.ctK2Y(((Integer) FtCtLight.this.mQuickSelAdapter.getData().get(i)).intValue(), FtCtLight.this.ctControlHelper.getKMax(), FtCtLight.this.ctControlHelper.getkMin());
                FtCtLight.this.mViewModel.wy = 255 - ctK2Y;
                FtCtLight.this.ctControlHelper.setProgress(ctK2Y);
                FtCtLight.this.mViewModel.getLightCmdHelper().sendCW(FtCtLight.this.getActivity(), ctK2Y, true);
            }
        });
        if (this.mViewModel.selectAction) {
            if (!TextUtils.isEmpty(SceneHelper.instance().selectInstruct)) {
                String lightCmdData = SceneHelper.getLightCmdData(SceneHelper.instance().selectInstruct);
                if (lightCmdData.length() >= 12) {
                    this.mViewModel.brt = Integer.parseInt(lightCmdData.substring(6, 8), 16);
                    this.mViewModel.wy = Integer.parseInt(lightCmdData.substring(8, 10), 16);
                    return;
                }
                if (lightCmdData.length() >= 4) {
                    this.mViewModel.brt = Integer.parseInt(lightCmdData.substring(2, 4), 16);
                    if (lightCmdData.length() >= 6) {
                        this.mViewModel.wy = Integer.parseInt(lightCmdData.substring(4, 6), 16);
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
        ((FtCtLightBinding) this.mViewBinding).ctsb.setEnabled(true);
        ((FtCtLightBinding) this.mViewBinding).sbBrt.setCanChangeProgress(true);
        ((FtCtLightBinding) this.mViewBinding).rvCt.setEnabled(true);
        ((FtCtLightBinding) this.mViewBinding).sbBrt.setCurrentProgress(LightUtils.brt2ProgressHasBelowZero(this.mViewModel.brt));
        ((FtCtLightBinding) this.mViewBinding).tvBrt.setText(((FtCtLightBinding) this.mViewBinding).sbBrt.getProgressPercent());
        this.ctControlHelper.setProgress(255 - this.mViewModel.wy);
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        this.mViewModel.controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtCtLight$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtCtLight.this.lambda$startObserve$0(obj);
            }
        });
        this.mViewModel.stateOn.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtCtLight$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtCtLight.this.lambda$startObserve$1((Boolean) obj);
            }
        });
        this.mViewModel.brtProgress.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtCtLight$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtCtLight.this.lambda$startObserve$2((Integer) obj);
            }
        });
        ArrayList arrayList = new ArrayList();
        arrayList.add(2700);
        arrayList.add(3500);
        arrayList.add(4000);
        arrayList.add(5000);
        arrayList.add(6000);
        arrayList.add(6500);
        this.mQuickSelAdapter.replaceData(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Object obj) {
        this.ctControlHelper = new CtControlHelper(((FtCtLightBinding) this.mViewBinding).ctsb, ((FtCtLightBinding) this.mViewBinding).tvWy, obj, new CtControlHelper.OnCtChangedListener() { // from class: com.ltech.smarthome.ui.device.dalipro.FtCtLight.5
            @Override // com.ltech.smarthome.ui.device.light.CtControlHelper.OnCtChangedListener
            public void onRangeChanged(int v, boolean finish) {
                FtCtLight.this.mViewModel.wy = 255 - v;
                FtCtLight.this.mViewModel.getLightCmdHelper().sendCW(FtCtLight.this.getActivity(), v, finish);
            }
        });
        if (this.mViewModel.selectAction) {
            changeSelectView();
            return;
        }
        if (obj instanceof Group) {
            Group group = (Group) obj;
            this.mViewModel.brt = LightUtils.progress2BrtHasBelowOne(group.getGroupState().getWyBrt());
            this.mViewModel.stateOn.setValue(Boolean.valueOf(group.getGroupState().isOn()));
            return;
        }
        Device device = (Device) obj;
        this.mViewModel.brt = LightUtils.progress2BrtHasBelowOne(device.getDeviceState().getWyBrt());
        this.mViewModel.stateOn.setValue(Boolean.valueOf(device.getDeviceState().isOn()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Boolean bool) {
        FragmentActivity activity;
        int i;
        ((FtCtLightBinding) this.mViewBinding).ctsb.setEnabled(bool.booleanValue());
        ((FtCtLightBinding) this.mViewBinding).ctsb.setAlpha(bool.booleanValue() ? 1.0f : 0.5f);
        ((FtCtLightBinding) this.mViewBinding).sbBrt.setCanChangeProgress(bool.booleanValue());
        ((FtCtLightBinding) this.mViewBinding).sbBrt.setAlpha(bool.booleanValue() ? 1.0f : 0.5f);
        ((FtCtLightBinding) this.mViewBinding).rvCt.setEnabled(bool.booleanValue());
        ((FtCtLightBinding) this.mViewBinding).rvCt.setAlpha(bool.booleanValue() ? 1.0f : 0.5f);
        ((FtCtLightBinding) this.mViewBinding).tvBrt.setAlpha(bool.booleanValue() ? 1.0f : 0.5f);
        ((FtCtLightBinding) this.mViewBinding).tvWy.setAlpha(bool.booleanValue() ? 1.0f : 0.5f);
        ConstraintLayout constraintLayout = ((FtCtLightBinding) this.mViewBinding).layoutRoot;
        if (bool.booleanValue()) {
            activity = getActivity();
            i = R.color.white;
        } else {
            activity = getActivity();
            i = R.color.gray_round_music_background;
        }
        constraintLayout.setBackground(activity.getDrawable(i));
        int progress = ((FtCtLightBinding) this.mViewBinding).sbBrt.getProgress();
        this.mViewModel.brtProgress.setValue(Integer.valueOf(LightUtils.brt2ProgressHasBelowZero(this.mViewModel.brt)));
        if (!this.firstIn) {
            ((FtCtLightBinding) this.mViewBinding).sbBrt.setAnimation(progress, ((FtCtLightBinding) this.mViewBinding).sbBrt.getProgress());
        } else {
            ((FtCtLightBinding) this.mViewBinding).sbBrt.invalidate();
        }
        this.firstIn = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Integer num) {
        ((FtCtLightBinding) this.mViewBinding).sbBrt.setCurrentProgress(this.mViewModel.stateOn.getValue().booleanValue() ? num.intValue() : 0);
        ((FtCtLightBinding) this.mViewBinding).tvBrt.setText(((FtCtLightBinding) this.mViewBinding).sbBrt.getProgressPercent());
    }
}