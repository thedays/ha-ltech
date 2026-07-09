package com.ltech.smarthome.ui.device.ir;

import android.view.View;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzy.tvmao.KookongSDK;
import com.hzy.tvmao.interf.IRequestResult;
import com.kookong.app.data.StbList;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseListActivity;
import com.ltech.smarthome.ui.config.ConfigHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectIPTVBrand extends BaseListActivity<StbList.Stb> {
    private int spId;

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_go_1;
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<StbList.Stb> getList() {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, StbList.Stb item) {
        helper.setText(R.id.tv_main, item.bname).setImageResource(R.id.iv_go, R.mipmap.icon_more);
        ((TextView) helper.getView(R.id.tv_main)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.select_brand));
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.ir.ActSelectIPTVBrand$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSelectIPTVBrand.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        StbList.Stb stb = (StbList.Stb) this.mAdapter.getData().get(i);
        Iterator<StbList.Remote> it = stb.remotes.iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(it.next().rid));
        }
        NavUtils.Builder irNavBuilder = NavHelper.getIrNavBuilder(ConfigHelper.instance().productInfo.getProductId());
        if (irNavBuilder != null) {
            irNavBuilder.withIntegerArrayList(Constants.RID_LIST, arrayList).withString(Constants.BRAND_NAME, stb.bname);
            irNavBuilder.navigation(this);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        int intExtra = getIntent().getIntExtra(Constants.SP_ID, 0);
        this.spId = intExtra;
        getIPTV(intExtra);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        getIPTV(this.spId);
    }

    private void getIPTV(int spId) {
        showLoading();
        KookongSDK.getIPTV(spId, new IRequestResult<StbList>() { // from class: com.ltech.smarthome.ui.device.ir.ActSelectIPTVBrand.1
            @Override // com.hzy.tvmao.interf.IRequestResult
            public void onSuccess(String s, StbList stbList) {
                ActSelectIPTVBrand.this.mAdapter.setNewData(stbList.stbList);
                ActSelectIPTVBrand.this.showContent();
            }

            @Override // com.hzy.tvmao.interf.IRequestResult
            public void onFail(Integer integer, String s) {
                ActSelectIPTVBrand.this.showError();
            }
        });
    }
}