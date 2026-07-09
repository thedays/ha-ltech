package com.ltech.smarthome.ui.scene;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActSelectListBinding;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.product_agreement.param.MicrowaveParam;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectWaveSensorAction extends BaseNormalActivity<ActSelectListBinding> implements ISelectAction {
    private BaseQuickAdapter<String, BaseViewHolder> adapter;
    private boolean isLocal;
    private List<String> selectList = new ArrayList();
    private int selectPosition = -1;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected boolean isNeedAddMarginTopEqualStatusBar() {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_list;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void lambda$initView$0(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((ActSelectListBinding) this.mViewBinding).tvTitle.setText(getString(R.string.choose_action));
        ((ActSelectListBinding) this.mViewBinding).tvConfirm.setText(getString(R.string.confirm));
        ((ActSelectListBinding) this.mViewBinding).tvCancel.setText(getString(R.string.cancel));
        this.isLocal = getIntent().getBooleanExtra(Constants.IS_LOCAL_SCENE, false);
        this.selectList = Arrays.asList(getResources().getStringArray(R.array.wave_sensor_scene_action));
        initAdapter();
        ((ActSelectListBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.scene.ActSelectWaveSensorAction$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActSelectWaveSensorAction.this.lambda$initView$1((View) obj);
            }
        }));
        showAsDialog(0.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view) {
        int id = view.getId();
        if (id == R.id.tv_cancel) {
            finishActivity();
            return;
        }
        if (id != R.id.tv_confirm) {
            return;
        }
        int i = this.selectPosition;
        if (i == -1) {
            SmartToast.showShort(R.string.please_choose);
            return;
        }
        setSelectAction(i);
        if (this.isLocal) {
            SceneHelper.instance().maskType = MaskType.LOCAL;
            setResult(3001);
            finishActivity();
            return;
        }
        SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectWaveSensorAction$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectWaveSensorAction.this.lambda$initView$0((Boolean) obj);
            }
        });
    }

    private void initAdapter() {
        BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_select_list, this.selectList) { // from class: com.ltech.smarthome.ui.scene.ActSelectWaveSensorAction.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_name, item).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == ActSelectWaveSensorAction.this.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
            }
        };
        this.adapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectWaveSensorAction$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActSelectWaveSensorAction.this.lambda$initAdapter$2(baseQuickAdapter2, view, i);
            }
        });
        this.adapter.bindToRecyclerView(((ActSelectListBinding) this.mViewBinding).rvContent);
        ((ActSelectListBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectListBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAdapter$2(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.selectPosition = i;
        this.adapter.notifyDataSetChanged();
    }

    private void setSelectAction(int position) {
        MicrowaveParam microwaveParam = new MicrowaveParam();
        if (position == 0 || position == 1) {
            microwaveParam.setEnable(position == 0);
            microwaveParam.setCmdType(1);
        } else {
            microwaveParam.setRelaySwitchOn(position == 2);
            microwaveParam.setCmdType(15);
        }
        SceneHelper.instance().cmdParam = microwaveParam;
        SceneHelper.instance().cmdParam.addExtParam(SceneHelper.OPTION, String.valueOf(position));
        SceneHelper.instance().cmdParam.addExtParam(SceneHelper.OPTION_VALUE, this.selectList.get(position));
    }
}