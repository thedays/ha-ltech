package com.ltech.smarthome.ui.device.light;

import android.graphics.Rect;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseListActivity;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.IconRepository;
import com.ltech.smarthome.utils.NameRepository;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActDefaultMode extends BaseListActivity<ModeItem> {
    private Object controlObject;
    private int lightType;

    public static class ModeItem {
        public int iconRes;
        public String modeContent;
        public String modeName;
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_default_mode;
    }

    @Override // com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.theme));
        setBackImage(R.mipmap.icon_back);
        if (((ActSelectBinding) this.mViewBinding).tvTip.getText().toString().equals("")) {
            ((ActSelectBinding) this.mViewBinding).tvTip.setVisibility(8);
        }
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActDefaultMode$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActDefaultMode.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
        ((ActSelectBinding) this.mViewBinding).rvContent.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.light.ActDefaultMode.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(16.0f), ConvertUtils.dp2px(5.0f), ConvertUtils.dp2px(16.0f), ConvertUtils.dp2px(5.0f));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (view.getId() == R.id.iv_cover) {
            CmdAssistant.getModeCmdAssistant(this.controlObject, new int[0]).playDefaultMode(this, i);
        }
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<ModeItem> getList() {
        String[] dimDefaultModeName;
        int[] dimDefaultModePic;
        String[] dimDefaultModeContent;
        this.lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(this.controlObject));
        ArrayList arrayList = new ArrayList();
        int i = this.lightType;
        if (i == 1) {
            dimDefaultModeName = NameRepository.getDimDefaultModeName(this);
            dimDefaultModePic = IconRepository.getDimDefaultModePic(this);
            dimDefaultModeContent = NameRepository.getDimDefaultModeContent(this);
        } else if (i == 2) {
            dimDefaultModeName = NameRepository.getCtDefaultModeName(this);
            dimDefaultModePic = IconRepository.getCtDefaultModePic(this);
            dimDefaultModeContent = NameRepository.getCtDefaultModeContent(this);
        } else {
            dimDefaultModeName = NameRepository.getDefaultModeName(this);
            dimDefaultModePic = IconRepository.getDefaultModePic(this);
            dimDefaultModeContent = NameRepository.getDefaultModeContent(this);
        }
        int length = dimDefaultModeName.length;
        for (int i2 = 0; i2 < length; i2++) {
            ModeItem modeItem = new ModeItem();
            modeItem.modeName = dimDefaultModeName[i2];
            modeItem.modeContent = dimDefaultModeContent[i2];
            modeItem.iconRes = dimDefaultModePic[i2];
            arrayList.add(modeItem);
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, ModeItem item) {
        helper.setText(R.id.tv_mode_name, item.modeName).setText(R.id.tv_mode_content, item.modeContent).setBackgroundRes(R.id.iv_cover, item.iconRes).addOnClickListener(R.id.iv_cover);
        ((AppCompatTextView) helper.getView(R.id.tv_mode_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        if (getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false)) {
            Injection.repo().group().getGroupFromDb(getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActDefaultMode$$ExternalSyntheticLambda1
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActDefaultMode.this.lambda$startObserve$1((Group) obj);
                }
            });
        } else {
            Injection.repo().device().getDeviceFromDb(getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActDefaultMode$$ExternalSyntheticLambda2
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActDefaultMode.this.lambda$startObserve$2((Device) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Group group) {
        this.controlObject = group;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Device device) {
        this.controlObject = device;
        if (!ProductRepository.isE6Panel(device.getProductId()) || ProductId.ID_KNOB_PANEL_E6M.equals(device.getProductId())) {
            return;
        }
        ((ActSelectBinding) this.mViewBinding).tvTip.setText(getString(R.string.e6_mode_tip));
        ((ActSelectBinding) this.mViewBinding).tvTip.setVisibility(0);
    }
}