package com.ltech.smarthome.ui.automation;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseMultiSelectActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectWeek extends BaseMultiSelectActivity<String> {
    private long minSele;

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select;
    }

    @Override // com.ltech.smarthome.base.BaseMultiSelectActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.repeat_date));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        this.minSele = getIntent().getIntExtra(Constants.MIN, 0);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.automation.ActSelectWeek$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSelectWeek.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.selectArray[i] = !this.selectArray[i];
        if (i == 0) {
            for (int i2 = 1; i2 < this.selectArray.length; i2++) {
                this.selectArray[i2] = this.selectArray[0];
            }
            baseQuickAdapter.notifyDataSetChanged();
            return;
        }
        this.selectArray[0] = isSelectAll();
        baseQuickAdapter.notifyItemChanged(i);
        baseQuickAdapter.notifyItemChanged(0);
    }

    @Override // com.ltech.smarthome.base.BaseMultiSelectActivity, com.ltech.smarthome.base.BaseListActivity
    protected void setUpData() {
        super.setUpData();
        String stringExtra = getIntent().getStringExtra(Constants.WEEKS);
        if (TextUtils.isEmpty(stringExtra)) {
            return;
        }
        try {
            for (String str : stringExtra.split(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP)) {
                this.selectArray[Integer.parseInt(str)] = true;
            }
            this.selectArray[0] = isSelectAll();
        } catch (Exception unused) {
            int length = this.selectArray.length;
            for (int i = 0; i < length; i++) {
                this.selectArray[i] = false;
            }
        }
    }

    private boolean isSelectAll() {
        boolean z = true;
        for (int i = 1; i < this.selectArray.length; i++) {
            z &= this.selectArray[i];
        }
        return z;
    }

    @Override // com.ltech.smarthome.base.BaseMultiSelectActivity
    protected void save() {
        StringBuilder sb = new StringBuilder();
        int length = this.selectArray.length;
        for (int i = 1; i < length; i++) {
            if (this.selectArray[i]) {
                sb.append(i);
                sb.append(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP);
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        if (this.minSele > 0 && sb.toString().length() < this.minSele) {
            SmartToast.showCenterShort(String.format(getString(R.string.app_str_select_at_least_n_day), Long.valueOf(this.minSele)));
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(Constants.WEEKS, sb.toString());
        setResult(3004, intent);
        finishActivity();
    }

    @Override // com.ltech.smarthome.base.BaseMultiSelectActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        save();
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<String> getList() {
        ArrayList arrayList = new ArrayList(8);
        arrayList.add(getString(R.string.every_day));
        arrayList.add(getString(R.string.every_sun));
        arrayList.add(getString(R.string.every_mon));
        arrayList.add(getString(R.string.every_tus));
        arrayList.add(getString(R.string.every_wed));
        arrayList.add(getString(R.string.every_thur));
        arrayList.add(getString(R.string.every_fri));
        arrayList.add(getString(R.string.every_sat));
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name, item).setBackgroundRes(R.id.iv_select, this.selectArray[helper.getAdapterPosition()] ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
    }
}