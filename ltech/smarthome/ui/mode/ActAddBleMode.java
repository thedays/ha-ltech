package com.ltech.smarthome.ui.mode;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseSelectDeviceActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.eurpanel.EurHelper;
import com.ltech.smarthome.utils.Constants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActAddBleMode extends BaseSelectDeviceActivity {
    private int lightType;

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select_device_with_place;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, Device item) {
        helper.setText(R.id.tv_device_name, item.getDeviceName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item)).setText(R.id.tv_place_info, getPlaceInfo(item.getFloorId(), item.getRoomId()));
        ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseSelectDeviceActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.mode.ActAddBleMode$$ExternalSyntheticLambda1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActAddBleMode.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        playDevice((Device) this.mAdapter.getData().get(i));
    }

    @Override // com.ltech.smarthome.base.BaseSelectDeviceActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        this.lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
        handleData(this.deviceResult, new IAction() { // from class: com.ltech.smarthome.ui.mode.ActAddBleMode$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActAddBleMode.this.lambda$startObserve$1((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Device device = (Device) it.next();
            if (ProductRepository.supportLightMode(device.getProductId())) {
                int lightColorType = ProductRepository.getLightColorType((Object) device);
                if (EurHelper.isEb125(device)) {
                    lightColorType = EurHelper.convertType(device);
                }
                if (lightColorType == 20) {
                    lightColorType = 5;
                }
                int i = this.lightType;
                if (i == -1 || i == lightColorType) {
                    arrayList.add(device);
                }
            }
        }
        if (arrayList.isEmpty()) {
            showEmpty();
        } else {
            setDataList(arrayList);
        }
    }

    public void playDevice(Device device) {
        Intent intent = new Intent();
        intent.putExtra("device_id", device.getId());
        setResult(3008, intent);
        finish();
    }
}