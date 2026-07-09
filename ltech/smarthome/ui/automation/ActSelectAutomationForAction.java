package com.ltech.smarthome.ui.automation;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.BaseSelectAutomationActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.bean.Automation;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.SwitchButton;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectAutomationForAction extends BaseSelectAutomationActivity implements ISelectAction {
    private List<Automation> selectAutomationList = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select_automation;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void lambda$edit$3(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseSelectAutomationActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.finish));
        if (SceneHelper.instance().controlObject != null) {
            if (SceneHelper.instance().automationAction) {
                this.selectAutomationList.addAll((List) SceneHelper.instance().controlObject);
            } else {
                this.selectAutomationList.add((Automation) SceneHelper.instance().controlObject);
            }
        }
        ((ActSelectBinding) this.mViewBinding).tvTip.setVisibility(8);
        ((ActSelectBinding) this.mViewBinding).rvContent.setPadding(ConvertUtils.dp2px(8.0f), ConvertUtils.dp2px(8.0f), ConvertUtils.dp2px(8.0f), ConvertUtils.dp2px(16.0f));
        ((ActSelectBinding) this.mViewBinding).rvContent.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.automation.ActSelectAutomationForAction.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(8.0f), 0, ConvertUtils.dp2px(8.0f), ConvertUtils.dp2px(16.0f));
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.automation.ActSelectAutomationForAction$$ExternalSyntheticLambda3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSelectAutomationForAction.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (SceneHelper.instance().automationAction) {
            Automation automation = (Automation) this.mAdapter.getData().get(i);
            if (this.selectAutomationList.contains(automation)) {
                this.selectAutomationList.remove(automation);
            } else {
                this.selectAutomationList.add(automation);
            }
        } else {
            this.selectAutomationList.clear();
            this.selectAutomationList.add((Automation) this.mAdapter.getData().get(i));
        }
        this.mAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, Automation item) {
        helper.setText(R.id.tv_name, item.getName()).setBackgroundRes(R.id.iv_bg, SceneHelper.getAutomationPic(ActivityUtils.getTopActivity(), item.getPicIndex())).setImageResource(R.id.iv_select, this.selectAutomationList.contains(item) ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default).setGone(R.id.sb_state, this.selectAutomationList.contains(item)).setText(R.id.tv_type, getString(item.getAutomationType() == 2 ? R.string.type_local : R.string.type_cloud)).setBackgroundRes(R.id.tv_type, item.getAutomationType() == 2 ? R.drawable.shape_red_bt_5 : R.drawable.shape_blue_bt_5);
        if (this.selectAutomationList.contains(item)) {
            final Automation selectAutomation = getSelectAutomation(item.getAutomationId());
            SwitchButton switchButton = (SwitchButton) helper.getView(R.id.sb_state);
            switchButton.setChecked(selectAutomation.isEnable());
            switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.automation.ActSelectAutomationForAction$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                public final void onCheckedChanged(SwitchButton switchButton2, boolean z) {
                    Automation.this.setEnable(z);
                }
            });
        }
    }

    @Override // com.ltech.smarthome.base.BaseSelectAutomationActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        handleData(this.request, new IAction() { // from class: com.ltech.smarthome.ui.automation.ActSelectAutomationForAction$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectAutomationForAction.this.lambda$startObserve$2((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(List list) {
        this.allData.addAll(list);
        this.mAdapter.setNewData(list);
        if (list.isEmpty()) {
            showEmpty();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyImageRes(R.mipmap.pic_empty_1).emptyStringRes(R.string.no_automation));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (this.selectAutomationList.isEmpty()) {
            SmartToast.showShort(R.string.please_choose);
            return;
        }
        SceneHelper.instance().maskType = MaskType.AUTOMATION;
        SceneHelper.instance().controlObject = SceneHelper.instance().automationAction ? this.selectAutomationList : this.selectAutomationList.get(0);
        SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.automation.ActSelectAutomationForAction$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectAutomationForAction.this.lambda$edit$3((Boolean) obj);
            }
        });
    }

    private Automation getSelectAutomation(long automationId) {
        for (Automation automation : this.selectAutomationList) {
            if (automation.getAutomationId() == automationId) {
                return automation;
            }
        }
        return null;
    }
}