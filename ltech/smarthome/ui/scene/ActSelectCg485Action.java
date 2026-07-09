package com.ltech.smarthome.ui.scene;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActSelectListBinding;
import com.ltech.smarthome.model.device_param.Rs485ExtParam;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.ui.device.cg485.Cg485Helper;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.product_agreement.param.Rs485CmdParam;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectCg485Action extends BaseNormalActivity<ActSelectListBinding> implements ISelectAction {
    private BaseQuickAdapter<Rs485ExtParam.Instruct, BaseViewHolder> adapter;
    private boolean isLocal;
    private List<Rs485ExtParam.Instruct> selectList = new ArrayList();
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
        this.selectList.addAll(Cg485Helper.getCategory(1).getAction());
        initAdapter();
        ((ActSelectListBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.scene.ActSelectCg485Action$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActSelectCg485Action.this.lambda$initView$1((View) obj);
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
        SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectCg485Action$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectCg485Action.this.lambda$initView$0((Boolean) obj);
            }
        });
    }

    private void initAdapter() {
        BaseQuickAdapter<Rs485ExtParam.Instruct, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Rs485ExtParam.Instruct, BaseViewHolder>(R.layout.item_select_with_sub, this.selectList) { // from class: com.ltech.smarthome.ui.scene.ActSelectCg485Action.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Rs485ExtParam.Instruct item) {
                helper.setText(R.id.tv_name, item.getActionName()).setText(R.id.tv_sub, item.getCmd()).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == ActSelectCg485Action.this.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
                ((AppCompatTextView) helper.getView(R.id.tv_name)).setTextSize(2, 15.0f);
                ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                ((AppCompatTextView) helper.getView(R.id.tv_sub)).setTextSize(2, 12.0f);
            }
        };
        this.adapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectCg485Action$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActSelectCg485Action.this.lambda$initAdapter$2(baseQuickAdapter2, view, i);
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
        Rs485CmdParam rs485CmdParam = new Rs485CmdParam();
        rs485CmdParam.setCmdType(4);
        rs485CmdParam.setRsData(this.adapter.getData().get(position).getCmd());
        rs485CmdParam.setDataFormat(this.adapter.getData().get(position).getDataFormat());
        SceneHelper.instance().cmdParam = rs485CmdParam;
        SceneHelper.instance().cmdParam.addExtParam(SceneHelper.OPTION, String.valueOf(position));
        SceneHelper.instance().cmdParam.addExtParam(SceneHelper.OPTION_VALUE, this.adapter.getData().get(position).getActionName());
    }
}