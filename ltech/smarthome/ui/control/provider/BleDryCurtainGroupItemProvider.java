package com.ltech.smarthome.ui.control.provider;

import android.text.TextUtils;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.TrigExtParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.control.FtRoomVM;
import com.ltech.smarthome.ui.control.provider.BaseDeviceProvider;
import com.ltech.smarthome.ui.device.trig.ActTrigCurtain;
import com.ltech.smarthome.ui.device.trig.TrigRepository;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.IrQuickDialog;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class BleDryCurtainGroupItemProvider extends BaseDeviceProvider<Group> {
    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_device_ir;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 16;
    }

    public BleDryCurtainGroupItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel, BaseDeviceProvider.OnDataChangeListener onDataChangeListener) {
        super(activity, recyclerView, viewModel, onDataChangeListener);
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider, com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(final BaseViewHolder helper, final Group data, int position) {
        super.convert(helper, (BaseViewHolder) data, position);
        helper.setGone(R.id.appCompatTextView16, false);
        helper.getView(R.id.iv_device_more).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.provider.BleDryCurtainGroupItemProvider.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (((FtRoomVM) BleDryCurtainGroupItemProvider.this.viewModel).editRoleMode.getValue().booleanValue()) {
                    if (((FtRoomVM) BleDryCurtainGroupItemProvider.this.viewModel).selectRoleList.getValue().contains(data)) {
                        ((FtRoomVM) BleDryCurtainGroupItemProvider.this.viewModel).selectRoleList.getValue().remove(data);
                        helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_default);
                    } else {
                        ((FtRoomVM) BleDryCurtainGroupItemProvider.this.viewModel).selectRoleList.getValue().add(data);
                        helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_sel);
                    }
                    ((FtRoomVM) BleDryCurtainGroupItemProvider.this.viewModel).selectRoleList.setValue(((FtRoomVM) BleDryCurtainGroupItemProvider.this.viewModel).selectRoleList.getValue());
                    return;
                }
                BleDryCurtainGroupItemProvider bleDryCurtainGroupItemProvider = BleDryCurtainGroupItemProvider.this;
                bleDryCurtainGroupItemProvider.nav(data, bleDryCurtainGroupItemProvider.viewModel);
            }
        });
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider
    public void onItemClick(BaseViewHolder helper, Group data, int position) {
        showDryCurtainDialog(data, this.viewModel);
    }

    private void showDryCurtainDialog(final Group group, final BaseViewModel viewModel) {
        ArrayList arrayList = new ArrayList();
        if (ProductRepository.getLightColorType((Object) group) == 12) {
            if (!TextUtils.isEmpty(group.getExtParam())) {
                TrigExtParam trigExtParam = new TrigExtParam();
                trigExtParam.fillMapWithString(group.getExtParam());
                if (trigExtParam.getCurtainType() == 1) {
                    arrayList.add(new TrigRepository.TrigItem(R.mipmap.icon_open_2, ActivityUtils.getTopActivity().getString(R.string.open_curtain), 1));
                    arrayList.add(new TrigRepository.TrigItem(R.mipmap.cgcur_icon_stop, ActivityUtils.getTopActivity().getString(R.string.preview_stop), 2));
                    arrayList.add(new TrigRepository.TrigItem(R.mipmap.icon_close_2, ActivityUtils.getTopActivity().getString(R.string.close_curtain), 4));
                }
            }
            arrayList.add(new TrigRepository.TrigItem(R.mipmap.icon_open_3, ActivityUtils.getTopActivity().getString(R.string.open_curtain), 1));
            arrayList.add(new TrigRepository.TrigItem(R.mipmap.cgcur_icon_stop, ActivityUtils.getTopActivity().getString(R.string.preview_stop), 2));
            arrayList.add(new TrigRepository.TrigItem(R.mipmap.icon_close_4, ActivityUtils.getTopActivity().getString(R.string.close_curtain), 4));
        } else {
            arrayList.add(new TrigRepository.TrigItem(R.mipmap.icon_open_3, ActivityUtils.getTopActivity().getString(R.string.open_curtain), 1));
            arrayList.add(new TrigRepository.TrigItem(R.mipmap.cgcur_icon_stop, ActivityUtils.getTopActivity().getString(R.string.preview_stop), 3));
            arrayList.add(new TrigRepository.TrigItem(R.mipmap.icon_close_4, ActivityUtils.getTopActivity().getString(R.string.close_curtain), 2));
        }
        IrQuickDialog.dryCurtain(true).setTitle(group.getGroupName()).setList(arrayList).setDialogCallback(new IrQuickDialog.OnDialogCallback<TrigRepository.TrigItem>() { // from class: com.ltech.smarthome.ui.control.provider.BleDryCurtainGroupItemProvider.2
            @Override // com.ltech.smarthome.view.dialog.IrQuickDialog.OnDialogCallback
            public void onItemClick(TrigRepository.TrigItem item) {
                CmdAssistant.getDeviceAssistant(group, new int[0]).setOpenCloseVar(ActivityUtils.getTopActivity(), item.spanCount);
            }

            @Override // com.ltech.smarthome.view.dialog.IrQuickDialog.OnDialogCallback
            public void onMoreAction() {
                BleDryCurtainGroupItemProvider.this.nav(group, viewModel);
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
        checkGroupStatus(group);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void nav(Group group, BaseViewModel viewModel) {
        viewModel.navigation(NavUtils.destination(ActTrigCurtain.class).withLong(Constants.PLACE_ID, group.getPlaceId()).withLong(Constants.CONTROL_ID, group.getId()).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) group)).withBoolean(Constants.GROUP_CONTROL, true));
    }
}