package com.ltech.smarthome.ui.automation;

import android.content.Intent;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.common.primitives.Ints;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseSingleSelectActivity;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.IconRepository;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectCover extends BaseSingleSelectActivity<Integer> {
    private int initPosition;

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select_cover;
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.change_cover));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.finish));
        ((ActSelectBinding) this.mViewBinding).rvContent.setPadding(ConvertUtils.dp2px(8.0f), ConvertUtils.dp2px(16.0f), ConvertUtils.dp2px(8.0f), ConvertUtils.dp2px(16.0f));
        ((ActSelectBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(this, 2));
        ((ActSelectBinding) this.mViewBinding).rvContent.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.automation.ActSelectCover.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(8.0f), 0, ConvertUtils.dp2px(8.0f), ConvertUtils.dp2px(16.0f));
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected void setUpData() {
        int intExtra = getIntent().getIntExtra(Constants.ICON_POSITION, -1);
        this.initPosition = intExtra;
        this.selectPosition = intExtra;
        super.setUpData();
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity
    protected void save() {
        if (this.selectPosition != this.initPosition) {
            Intent intent = new Intent();
            intent.putExtra(Constants.ICON_POSITION, this.selectPosition);
            setResult(3002, intent);
        }
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        save();
        finishActivity();
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<Integer> getList() {
        return Ints.asList(IconRepository.getAutomationPic(this));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, Integer item) {
        helper.setBackgroundRes(R.id.v_pic, item.intValue()).setVisible(R.id.v_select, helper.getAdapterPosition() == this.selectPosition);
        helper.getView(R.id.layout_item_bg).getLayoutParams().height = ((ActSelectBinding) this.mViewBinding).rvContent.getMeasuredWidth() / 5;
    }
}