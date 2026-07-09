package com.ltech.smarthome.ui.device.smartpanel;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActPanelColorSetBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.device.setting.ActSmartPanelSettingVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.message.utils.LHomeLog;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActSmartPanelColorSet extends VMActivity<ActPanelColorSetBinding, ActSmartPanelSettingVM> {
    private int[] colors;
    private Device device;
    private int[] imgs;
    private BaseQuickAdapter<PanelColor, BaseViewHolder> mAdapter;
    private String[] names;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_panel_color_set;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.app_light_plan));
        setEditString(getString(R.string.save));
        ((ActSmartPanelSettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        initRv();
    }

    private void initRv() {
        RecyclerView recyclerView = ((ActPanelColorSetBinding) this.mViewBinding).rv;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6);
        recyclerView.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(this) { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelColorSet.1
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int position) {
                return (position == 6 || position == 7) ? 3 : 2;
            }
        });
        ((ActPanelColorSetBinding) this.mViewBinding).rv.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelColorSet.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = ConvertUtils.dp2px(12.0f);
            }
        });
        RecyclerView recyclerView2 = ((ActPanelColorSetBinding) this.mViewBinding).rv;
        BaseQuickAdapter<PanelColor, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<PanelColor, BaseViewHolder>(this, R.layout.item_panel_color) { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelColorSet.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, PanelColor panelColor) {
                baseViewHolder.setText(R.id.tv, panelColor.getName());
                baseViewHolder.setImageResource(R.id.iv, panelColor.getColor());
                baseViewHolder.setVisible(R.id.iv_sel, panelColor.isSel());
            }
        };
        this.mAdapter = baseQuickAdapter;
        recyclerView2.setAdapter(baseQuickAdapter);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelColorSet.4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int pos) {
                int i = 0;
                while (true) {
                    if (i >= ActSmartPanelColorSet.this.mAdapter.getData().size()) {
                        break;
                    }
                    PanelColor panelColor = (PanelColor) ActSmartPanelColorSet.this.mAdapter.getData().get(i);
                    if (panelColor.isSel()) {
                        panelColor.setSel(false);
                        ActSmartPanelColorSet.this.mAdapter.setData(i, panelColor);
                        break;
                    }
                    i++;
                }
                PanelColor panelColor2 = (PanelColor) ActSmartPanelColorSet.this.mAdapter.getData().get(pos);
                panelColor2.setSel(true);
                ((ActPanelColorSetBinding) ActSmartPanelColorSet.this.mViewBinding).tvColorName.setText(panelColor2.getName());
                ActSmartPanelColorSet.this.mAdapter.setData(pos, panelColor2);
                ActSmartPanelColorSet.this.setBg(pos);
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        Device deviceById = Injection.repo().device().getDeviceById(((ActSmartPanelSettingVM) this.mViewModel).controlId);
        this.device = deviceById;
        if (deviceById == null) {
            SmartToast.showShort(getString(R.string.app_str_error_18));
            finishActivity();
            return;
        }
        ((ActSmartPanelSettingVM) this.mViewModel).controlDevice.setValue(this.device);
        ((ActPanelColorSetBinding) this.mViewBinding).ivScreen.setImageResource(R.mipmap.g4_img_4);
        if (this.device.getProductId().equalsIgnoreCase(ProductId.ID_SMART_PANEL_G4)) {
            this.names = getResources().getStringArray(R.array.g4_bg_panel_color_name);
            this.colors = HelpUtils.getDrawableResourceArray(this, R.array.g4_bg_panel_color);
            this.imgs = HelpUtils.getDrawableResourceArray(this, R.array.g4_bg_panel_bg);
        } else if (this.device.getProductId().equalsIgnoreCase(ProductId.ID_SMART_PANEL_G4_PRO)) {
            this.names = getResources().getStringArray(R.array.g4_bg_panel_color_name);
            this.colors = HelpUtils.getDrawableResourceArray(this, R.array.g4_bg_panel_color);
            this.imgs = HelpUtils.getDrawableResourceArray(this, R.array.g4_pro_bg_panel_bg);
        }
        String[] strArr = this.names;
        if (strArr != null) {
            int length = strArr.length;
            int[] iArr = this.colors;
            if (length == iArr.length && this.imgs.length == iArr.length) {
                ArrayList arrayList = new ArrayList();
                int i = 0;
                while (i < this.names.length) {
                    String panelColor = this.device.getPanelColor();
                    StringBuilder sb = new StringBuilder();
                    int i2 = i + 1;
                    sb.append(i2);
                    sb.append("");
                    if (panelColor.equals(sb.toString())) {
                        arrayList.add(new PanelColor(i2, true, this.colors[i], this.names[i]));
                    } else {
                        arrayList.add(new PanelColor(i2, false, this.colors[i], this.names[i]));
                    }
                    i = i2;
                }
                this.mAdapter.setNewData(arrayList);
                setBg(Integer.parseInt(this.device.getPanelColor()) - 1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBg(int i) {
        try {
            int[] iArr = this.imgs;
            if (iArr == null || i >= iArr.length) {
                return;
            }
            ((ActPanelColorSetBinding) this.mViewBinding).ivS4.setImageResource(this.imgs[i]);
        } catch (Exception e) {
            LHomeLog.e(getClass(), e.toString());
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        for (PanelColor panelColor : this.mAdapter.getData()) {
            if (panelColor.isSel()) {
                ((ActSmartPanelSettingVM) this.mViewModel).uploadPanelColor(panelColor.getNum());
            }
        }
    }

    static class PanelColor {
        private int color;
        private String name;
        private int num;
        private boolean sel;

        public PanelColor(int num, boolean sel, int color, String name) {
            this.num = num;
            this.sel = sel;
            this.color = color;
            this.name = name;
        }

        public int getNum() {
            return this.num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public void setSel(boolean sel) {
            this.sel = sel;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public boolean isSel() {
            return this.sel;
        }

        public int getColor() {
            return this.color;
        }
    }
}