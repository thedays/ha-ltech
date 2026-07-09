package com.ltech.smarthome.ui.scene;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseMultiSelectActivity;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.ui.scene.ISelectAction;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectZone extends BaseMultiSelectActivity<String> implements ISelectAction {
    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void lambda$save$1(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseMultiSelectActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.select_zone));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.confirm));
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectZone$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSelectZone.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
        Arrays.fill(this.selectArray, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.selectArray[i] = !this.selectArray[i];
        baseQuickAdapter.notifyItemChanged(i);
        changeEditView();
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<String> getList() {
        return Arrays.asList(getString(R.string.zone_with_num, new Object[]{1}), getString(R.string.zone_with_num, new Object[]{2}), getString(R.string.zone_with_num, new Object[]{3}), getString(R.string.zone_with_num, new Object[]{4}));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name, item);
        helper.setTextColor(R.id.tv_name, getResources().getColor(R.color.color_text_black)).setBackgroundRes(R.id.iv_select, this.selectArray[helper.getAdapterPosition()] ? R.mipmap.ic_tick_sel : R.color.transparent);
        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseMultiSelectActivity
    protected void save() {
        if (SceneHelper.instance().cmdParam != null) {
            SceneHelper.instance().cmdParam.setZoneNum(getSelectZone());
        }
        SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectZone$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectZone.this.lambda$save$1((Boolean) obj);
            }
        });
    }

    private void changeEditView() {
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= this.selectArray.length) {
                break;
            }
            if (this.selectArray[i]) {
                z = true;
                break;
            }
            i++;
        }
        ((ActSelectBinding) this.mViewBinding).title.tvEdit.setEnabled(z);
        ((ActSelectBinding) this.mViewBinding).title.tvEdit.setTextColor(getResources().getColor(z ? R.color.color_text_black : R.color.color_text_gray));
    }

    private int getSelectZone() {
        int i = 0;
        for (int i2 = 0; i2 < this.selectArray.length; i2++) {
            if (this.selectArray[i2]) {
                i += 1 << i2;
            }
        }
        return i;
    }
}