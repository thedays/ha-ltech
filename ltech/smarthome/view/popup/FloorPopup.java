package com.ltech.smarthome.view.popup;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.item.GoItem;
import com.zyyoona7.popup.BasePopup;
import java.util.List;

/* loaded from: classes4.dex */
public class FloorPopup extends BasePopup<FloorPopup> {
    private Context context;
    private List<GoItem> itemList;
    private BaseQuickAdapter<GoItem, BaseViewHolder> mAdapter;
    private RecyclerView recyclerView;

    public static FloorPopup create(Context context) {
        return new FloorPopup(context);
    }

    protected FloorPopup(final Context context) {
        this.context = context;
        this.mAdapter = new BaseQuickAdapter<GoItem, BaseViewHolder>(R.layout.item_change_floor) { // from class: com.ltech.smarthome.view.popup.FloorPopup.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, GoItem goItem) {
                Resources resources;
                int i;
                baseViewHolder.setText(R.id.floor_name, goItem.getMainText());
                if (goItem.isSelect()) {
                    resources = context.getResources();
                    i = R.color.auto_num_color;
                } else {
                    resources = context.getResources();
                    i = R.color.color_text_light_black;
                }
                baseViewHolder.setTextColor(R.id.floor_name, resources.getColor(i));
                baseViewHolder.setImageResource(R.id.floor_icon, goItem.isSelect() ? R.mipmap.ic_floor_selected : R.mipmap.ic_floor_not_selected);
                baseViewHolder.setImageResource(R.id.ic_select, goItem.isSelect() ? R.mipmap.ic_tick_sel : 0);
                if (((GoItem) FloorPopup.this.itemList.get(FloorPopup.this.itemList.size() - 1)).equals(goItem)) {
                    baseViewHolder.getView(R.id.gray_line).setVisibility(8);
                } else {
                    baseViewHolder.getView(R.id.gray_line).setVisibility(0);
                }
            }
        };
        setContext(context);
    }

    @Override // com.zyyoona7.popup.BasePopup
    protected void initAttributes() {
        setContentView(R.layout.dialog_change_floor, -2, -2);
        setFocusAndOutsideEnable(false).setBackgroundDimEnable(true).setFocusAndOutsideEnable(true).setDimValue(0.5f);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.zyyoona7.popup.BasePopup
    public void initViews(View view, FloorPopup popup) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_floor);
        this.recyclerView = recyclerView;
        recyclerView.setAdapter(this.mAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        this.recyclerView.post(new Runnable() { // from class: com.ltech.smarthome.view.popup.FloorPopup.2
            @Override // java.lang.Runnable
            public void run() {
                View childAt;
                if (FloorPopup.this.recyclerView.getAdapter() == null || FloorPopup.this.recyclerView.getAdapter().getItemCount() <= 5 || (childAt = FloorPopup.this.recyclerView.getChildAt(0)) == null) {
                    return;
                }
                int height = childAt.getHeight() * 5;
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) FloorPopup.this.recyclerView.getLayoutParams();
                layoutParams.height = height;
                FloorPopup.this.recyclerView.setLayoutParams(layoutParams);
            }
        });
    }

    public FloorPopup setData(List<GoItem> list) {
        this.itemList = list;
        this.mAdapter.setNewData(list);
        return this;
    }

    public void setOnItemClickListener(BaseQuickAdapter.OnItemClickListener listener) {
        this.mAdapter.setOnItemClickListener(listener);
    }

    public FloorPopup setArrowLeft() {
        ImageView imageView = (ImageView) findViewById(R.id.v_arrow);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 3;
        layoutParams.leftMargin = ConvertUtils.dp2px(20.0f);
        imageView.setLayoutParams(layoutParams);
        return this;
    }
}