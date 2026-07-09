package com.ltech.smarthome.ui.device.hsd;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseListActivity;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.HsdExtParam;
import com.ltech.smarthome.ui.item.ValueItem;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActBattery extends BaseListActivity<ValueItem> {
    private HsdExtParam extParam = new HsdExtParam();

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_go_1;
    }

    @Override // com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.battery));
        setBackImage(R.mipmap.icon_back);
        Device deviceById = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
        if (deviceById != null || TextUtils.isEmpty(deviceById.getExtParam())) {
            return;
        }
        this.extParam = (HsdExtParam) deviceById.getExtParam(HsdExtParam.class);
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<ValueItem> getList() {
        String str;
        ArrayList arrayList = new ArrayList();
        String string = getString(R.string.display_battery);
        if (this.extParam.getBattery() < 0) {
            str = "100%";
        } else {
            str = this.extParam.getBattery() + "%";
        }
        arrayList.add(new ValueItem(string, str));
        arrayList.add(new ValueItem(getString(R.string.refresh_time), this.extParam.getUpdateBatteryTime()));
        arrayList.add(new ValueItem(getString(R.string.battery_model), getString(R.string.battery_123A)));
        arrayList.add(new ValueItem(getString(R.string.battery_guide), "", new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.hsd.ActBattery$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActBattery.this.lambda$getList$0(view);
            }
        }));
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getList$0(View view) {
        NavUtils.destination(ActBatteryGuide.class).navigation(this.activity);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, ValueItem item) {
        helper.setText(R.id.tv_main, item.getName());
        if (!TextUtils.isEmpty(item.getValue())) {
            helper.setText(R.id.tv_sub, item.getValue());
        }
        ((TextView) helper.getView(R.id.tv_main)).getPaint().setFakeBoldText(true);
        helper.itemView.setOnClickListener(item.getClickListener());
    }
}