package com.ltech.smarthome.base;

import com.ltech.smarthome.R;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.utils.Constants;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class BaseSelectGroupActivity extends BaseListActivity<Group> {
    protected Listing<Group> mRequest;
    protected long placeId;
    protected String productId;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.rv_content;
    }

    @Override // com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.select_group));
        setBackImage(R.mipmap.icon_back);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        this.placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        this.mRequest = Injection.repo().group().getGroupList(this, this.placeId);
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        Listing<Group> listing = this.mRequest;
        if (listing != null) {
            listing.retry();
        }
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<Group> getList() {
        return new ArrayList();
    }
}