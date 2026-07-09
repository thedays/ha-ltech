package com.ltech.smarthome.ui.control.provider;

import android.graphics.Bitmap;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.imageclip.util.FileUtil;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.CameraParam;
import com.ltech.smarthome.singleton.PathManager;
import com.ltech.smarthome.ui.control.provider.BaseDeviceProvider;
import com.ltech.smarthome.ui.device.camera.EzConstants;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.Utils;

/* loaded from: classes4.dex */
public class CameraItemProvider extends BaseDeviceProvider<Device> {
    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_device_camera;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 21;
    }

    public CameraItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel, BaseDeviceProvider.OnDataChangeListener onDataChangeListener) {
        super(activity, recyclerView, viewModel, onDataChangeListener);
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider, com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(BaseViewHolder helper, Device data, int position) {
        super.convert(helper, (BaseViewHolder) data, position);
        CameraParam cameraParam = (CameraParam) data.getParam(CameraParam.class);
        helper.setGone(R.id.iv_camera2, cameraParam.getCameraNum() > 1);
        Bitmap cameraBitmap = FileUtil.getCameraBitmap(PathManager.getCacheDir(ActivityUtils.getTopActivity()) + "/" + PathManager.getCameraPicName(data.getDevicesn()));
        ((AppCompatImageView) helper.getView(R.id.iv_camera)).setImageBitmap(cameraBitmap);
        if (cameraParam.getCameraNum() == 2) {
            StringBuilder sb = new StringBuilder();
            sb.append(PathManager.getCacheDir(ActivityUtils.getTopActivity()));
            sb.append("/");
            sb.append(PathManager.getCameraPicName(data.getDevicesn() + "_2"));
            ((AppCompatImageView) helper.getView(R.id.iv_camera2)).setImageBitmap(FileUtil.getCameraBitmap(sb.toString()));
        }
        helper.setGone(R.id.iv_empty, cameraBitmap == null).setGone(R.id.tv_empty, cameraBitmap == null);
        if (isNormalMode()) {
            helper.setVisible(R.id.iv_device_more, false);
        }
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider
    public void onItemClick(BaseViewHolder helper, Device data, int position) {
        if (Utils.isYYBFlavor(ActivityUtils.getTopActivity())) {
            this.viewModel.navigation(NavUtils.destination("com.ltech.smarthome.ui.camera.play.ActCameraPlay").withLong(Constants.PLACE_ID, data.getPlaceId()).withLong(Constants.CONTROL_ID, data.getId()).withInt(EzConstants.PLAY_TYPE, 1));
        } else {
            SmartToast.showShort(R.string.cmd_no_function);
        }
    }
}