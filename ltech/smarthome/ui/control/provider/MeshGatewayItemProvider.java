package com.ltech.smarthome.ui.control.provider;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.ui.control.provider.BaseDeviceProvider;
import com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGateway;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;

/* loaded from: classes4.dex */
public class MeshGatewayItemProvider extends BaseDeviceProvider<Device> {
    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_device_mesh_gateway;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 1;
    }

    public MeshGatewayItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel, BaseDeviceProvider.OnDataChangeListener onDataChangeListener) {
        super(activity, recyclerView, viewModel, onDataChangeListener);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0037  */
    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider, com.chad.library.adapter.base.provider.BaseItemProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void convert(com.chad.library.adapter.base.BaseViewHolder r4, com.ltech.smarthome.model.bean.Device r5, int r6) {
        /*
            r3 = this;
            super.convert(r4, r5, r6)
            boolean r6 = r5.hasIotFun()
            if (r6 != 0) goto L15
            android.app.Activity r6 = com.blankj.utilcode.util.ActivityUtils.getTopActivity()
            r0 = 2131822484(0x7f110794, float:1.927774E38)
        L10:
            java.lang.String r6 = r6.getString(r0)
            goto L26
        L15:
            boolean r6 = r5.isOnline()
            if (r6 == 0) goto L1e
            java.lang.String r6 = ""
            goto L26
        L1e:
            android.app.Activity r6 = com.blankj.utilcode.util.ActivityUtils.getTopActivity()
            r0 = 2131823657(0x7f110c29, float:1.928012E38)
            goto L10
        L26:
            r0 = 2131296382(0x7f09007e, float:1.821068E38)
            com.chad.library.adapter.base.BaseViewHolder r4 = r4.setText(r0, r6)
            android.app.Activity r1 = com.blankj.utilcode.util.ActivityUtils.getTopActivity()
            boolean r2 = r5.hasIotFun()
            if (r2 != 0) goto L3b
            r2 = 2131099772(0x7f06007c, float:1.7811907E38)
            goto L3e
        L3b:
            r2 = 2131099770(0x7f06007a, float:1.7811903E38)
        L3e:
            int r1 = androidx.core.content.ContextCompat.getColor(r1, r2)
            com.chad.library.adapter.base.BaseViewHolder r4 = r4.setTextColor(r0, r1)
            boolean r5 = r5.hasIotFun()
            if (r5 != 0) goto L50
            r5 = 2131231387(0x7f08029b, float:1.8078854E38)
            goto L53
        L50:
            r5 = 2131231300(0x7f080244, float:1.8078677E38)
        L53:
            com.chad.library.adapter.base.BaseViewHolder r4 = r4.setBackgroundRes(r0, r5)
            boolean r5 = android.text.TextUtils.isEmpty(r6)
            r5 = r5 ^ 1
            r4.setGone(r0, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.control.provider.MeshGatewayItemProvider.convert(com.chad.library.adapter.base.BaseViewHolder, com.ltech.smarthome.model.bean.Device, int):void");
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider
    public void onItemClick(BaseViewHolder helper, Device data, int position) {
        this.viewModel.navigation(NavUtils.destination(ActMeshGateway.class).withLong(Constants.PLACE_ID, data.getPlaceId()).withLong(Constants.CONTROL_ID, data.getId()));
    }
}