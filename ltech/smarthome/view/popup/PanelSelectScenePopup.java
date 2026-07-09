package com.ltech.smarthome.view.popup;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.zyyoona7.popup.BasePopup;
import java.util.List;

/* loaded from: classes4.dex */
public class PanelSelectScenePopup extends BasePopup<PanelSelectScenePopup> {
    private Context context;
    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;
    private RecyclerView recyclerView;

    public static PanelSelectScenePopup create(Context context) {
        return new PanelSelectScenePopup(context);
    }

    protected PanelSelectScenePopup(final Context context) {
        this.context = context;
        this.mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(this, R.layout.item_change_floor) { // from class: com.ltech.smarthome.view.popup.PanelSelectScenePopup.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, String name) {
                baseViewHolder.setText(R.id.floor_name, name);
                baseViewHolder.setTextColor(R.id.floor_name, context.getResources().getColor(R.color.color_text_light_black));
                baseViewHolder.setImageResource(R.id.floor_icon, R.mipmap.ic_gateway_scene);
                baseViewHolder.setGone(R.id.gray_line, false);
            }
        };
        setContext(context);
    }

    @Override // com.zyyoona7.popup.BasePopup
    protected void initAttributes() {
        setContentView(R.layout.dialog_select_scene, -2, -2);
        setFocusAndOutsideEnable(false).setBackgroundDimEnable(true).setFocusAndOutsideEnable(true).setDimValue(0.5f);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.zyyoona7.popup.BasePopup
    public void initViews(View view, PanelSelectScenePopup popup) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_scene);
        this.recyclerView = recyclerView;
        recyclerView.setAdapter(this.mAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        this.recyclerView.post(new Runnable() { // from class: com.ltech.smarthome.view.popup.PanelSelectScenePopup.2
            @Override // java.lang.Runnable
            public void run() {
                View childAt;
                if (PanelSelectScenePopup.this.recyclerView.getAdapter() == null || PanelSelectScenePopup.this.recyclerView.getAdapter().getItemCount() <= 5 || (childAt = PanelSelectScenePopup.this.recyclerView.getChildAt(0)) == null) {
                    return;
                }
                int height = childAt.getHeight() * 5;
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) PanelSelectScenePopup.this.recyclerView.getLayoutParams();
                layoutParams.height = height;
                PanelSelectScenePopup.this.recyclerView.setLayoutParams(layoutParams);
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.iv_arrow);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 5;
        layoutParams.rightMargin = ConvertUtils.dp2px(20.0f);
        imageView.setLayoutParams(layoutParams);
    }

    public PanelSelectScenePopup setData(List<String> list) {
        this.mAdapter.setNewData(list);
        return this;
    }

    public void setOnItemClickListener(BaseQuickAdapter.OnItemClickListener listener) {
        this.mAdapter.setOnItemClickListener(listener);
    }
}