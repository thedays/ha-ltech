package com.ltech.smarthome.ui.mode;

import android.content.Intent;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.common.primitives.Ints;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseSingleSelectActivity;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.IconRepository;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectModeCover extends BaseSingleSelectActivity<Integer> {
    private long controlId;
    private Object controlObject;
    private int initPosition;
    private boolean isAdvancedMode;
    private int modeNum;
    private int zoneNum;

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select_mode_cover;
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.change_cover));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        ((ActSelectBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(this, 3));
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected void setUpData() {
        this.initPosition = getIntent().getIntExtra(Constants.ICON_POSITION, -1);
        this.modeNum = getIntent().getIntExtra(Constants.MODE_NUM, 0);
        this.zoneNum = getIntent().getIntExtra(Constants.ZONE_NUM, 1);
        this.controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        this.isAdvancedMode = getIntent().getBooleanExtra(Constants.ADVANCED_MODE, false);
        this.selectPosition = this.initPosition;
        super.setUpData();
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<Integer> getList() {
        return Ints.asList(IconRepository.getModePic(this));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, Integer item) {
        helper.setImageResource(R.id.iv_icon, item.intValue()).setImageResource(R.id.iv_select, R.drawable.shape_red_stroke).setVisible(R.id.iv_select, helper.getAdapterPosition() == this.selectPosition);
        helper.getView(R.id.layout_item_bg).getLayoutParams().height = ((ActSelectBinding) this.mViewBinding).rvContent.getMeasuredWidth() / 3;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        Injection.repo().device().getDeviceFromDb(this.controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.mode.ActSelectModeCover$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSelectModeCover.this.lambda$startObserve$0((Device) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        this.controlObject = device;
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity
    protected void save() {
        if (this.selectPosition != this.initPosition) {
            if (this.isAdvancedMode) {
                Intent intent = new Intent();
                intent.putExtra(Constants.ICON_POSITION, this.selectPosition);
                setResult(3002, intent);
                back();
                return;
            }
            Intent intent2 = new Intent();
            intent2.putExtra(Constants.ICON_POSITION, this.selectPosition);
            setResult(3002, intent2);
            back();
        }
    }
}