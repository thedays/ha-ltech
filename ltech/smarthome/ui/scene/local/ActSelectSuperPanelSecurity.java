package com.ltech.smarthome.ui.scene.local;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseListActivity;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NameRepository;
import com.smart.product_agreement.param.SuperPanelSecurityCmdParam;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectSuperPanelSecurity extends BaseListActivity<String> {
    private int selectPosition;

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select;
    }

    @Override // com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        int i;
        int i2;
        super.initView();
        setTitle(getString(R.string.security_scene));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.confirm));
        String stringExtra = getIntent().getStringExtra(Constants.SCENE_INSTRUCT);
        if (stringExtra == null || stringExtra.isEmpty()) {
            i = 1;
            i2 = 0;
        } else {
            i2 = Integer.parseInt(stringExtra.substring(4, 6), 16);
            i = Integer.parseInt(stringExtra.substring(6, 8), 16);
        }
        this.selectPosition = i2 + (i == 1 ? 0 : 4);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectSuperPanelSecurity$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i3) {
                ActSelectSuperPanelSecurity.this.lambda$initView$0(baseQuickAdapter, view, i3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (this.selectPosition != i) {
            this.selectPosition = i;
            baseQuickAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<String> getList() {
        return Arrays.asList(NameRepository.getSecurityName(this));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, String item) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) helper.itemView.getLayoutParams();
        if (helper.getAdapterPosition() == 4) {
            marginLayoutParams.topMargin = (int) TypedValue.applyDimension(1, 15.0f, helper.itemView.getContext().getResources().getDisplayMetrics());
        } else {
            marginLayoutParams.topMargin = 0;
        }
        helper.setText(R.id.tv_name, item).setBackgroundRes(R.id.iv_select, this.selectPosition == helper.getAdapterPosition() ? R.mipmap.ic_tick_sel : R.color.transparent);
        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        SuperPanelSecurityCmdParam superPanelSecurityCmdParam = new SuperPanelSecurityCmdParam();
        int i = 1;
        superPanelSecurityCmdParam.setCmdType(1);
        superPanelSecurityCmdParam.addExtParam(SceneHelper.OPTION, "4");
        superPanelSecurityCmdParam.addExtParam(SceneHelper.OPTION_VALUE, this.dataList.get(this.selectPosition));
        switch (this.selectPosition) {
            case 0:
            case 4:
                superPanelSecurityCmdParam.setSecurityType(0);
                break;
            case 1:
            case 5:
                superPanelSecurityCmdParam.setSecurityType(1);
                break;
            case 2:
            case 6:
                superPanelSecurityCmdParam.setSecurityType(2);
                break;
            case 3:
            case 7:
                superPanelSecurityCmdParam.setSecurityType(3);
                break;
        }
        int i2 = this.selectPosition;
        if (i2 != 0 && i2 != 1 && i2 != 2 && i2 != 3) {
            i = 0;
        }
        superPanelSecurityCmdParam.setSecurityValue(i);
        SceneHelper.instance().cmdParam = superPanelSecurityCmdParam;
        SceneHelper.instance().maskType = MaskType.LOCAL;
        setResult(3001);
        finishActivity();
    }
}