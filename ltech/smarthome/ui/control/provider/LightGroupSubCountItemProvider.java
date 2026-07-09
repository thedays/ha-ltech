package com.ltech.smarthome.ui.control.provider;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.expand.LightGroupSubCountExpandableItem;
import java.util.Locale;

/* loaded from: classes4.dex */
public class LightGroupSubCountItemProvider extends BaseItemProvider<LightGroupSubCountExpandableItem, BaseViewHolder> {
    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_light_group_control_sub_device_count;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 2;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(BaseViewHolder helper, LightGroupSubCountExpandableItem data, int position) {
        helper.setText(R.id.tv_count, String.format(Locale.getDefault(), "%s:%d", this.mContext.getString(R.string.group_device), Integer.valueOf(data.getCount())));
    }
}