package com.ltech.smarthome.ui.voicecall.provider;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.zhuhai.ltech.lt_voice_call_api.bean.WhiteListUserBean;

/* loaded from: classes4.dex */
public class WhiteListHeaderProvider extends BaseItemProvider<WhiteListUserBean, BaseViewHolder> {
    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_voice_call_white_list_header;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 0;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(BaseViewHolder baseViewHolder, WhiteListUserBean whiteListUserBean, int i) {
        baseViewHolder.setText(R.id.tv_title, whiteListUserBean.getTitle());
    }
}