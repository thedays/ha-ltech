package com.ltech.smarthome.ui.scene;

import android.content.Intent;
import androidx.appcompat.widget.AppCompatTextView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.BaseSingleSelectActivity;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.smart.product_agreement.param.RhythmsCmdParam;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectLightRhythmAction extends BaseSingleSelectActivity<String> implements ISelectAction {
    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$initActionView$7(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        setTitle(getString(R.string.choose_action));
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<String> getList() {
        return Arrays.asList(getResources().getStringArray(R.array.light_rhythms_array));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name, item).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == this.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity
    protected void save() {
        RhythmsCmdParam rhythmsCmdParam = new RhythmsCmdParam();
        rhythmsCmdParam.setRhythmsCode(this.selectPosition < 2 ? 1 : 2);
        rhythmsCmdParam.setRhythmsData(Collections.singletonList(Integer.valueOf(this.selectPosition % 2 != 0 ? 0 : 1)));
        rhythmsCmdParam.setCmdType(2);
        rhythmsCmdParam.addExtParam(SceneHelper.OPTION, "9");
        rhythmsCmdParam.addExtParam(SceneHelper.OPTION_VALUE, ((String) this.dataList.get(this.selectPosition)) + getString(R.string.app_circadian_lighting));
        SceneHelper.instance().cmdParam = rhythmsCmdParam;
        setResult(3001);
        finishActivity();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3001) {
            setResult(3001);
            finishActivity();
        }
    }
}