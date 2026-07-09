package com.ltech.smarthome.view.dialog;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogCenterDaliModifyParamBinding;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.device_param.CgdProLightExtParam;
import com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParamVM;
import com.ltech.smarthome.view.CircularProgressView;
import com.ltech.smarthome.view.dialog.CenterDaliBatchModifyParamDialog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class CenterDaliBatchModifyParamDialog extends SmartDialog<DialogCenterDaliModifyParamBinding> {
    private BaseQuickAdapter<Device, BaseViewHolder> adapter;
    private IAction<Integer> confirmAction;
    public List<Floor> floorList;
    public List<Room> roomList;
    private String title;
    private List<Device> deviceList = new ArrayList();
    public HashMap<Long, ActDaliBatchModifyParamVM.BatchSaveItem> batchSaveItemMap = new HashMap<>();
    private boolean isWorking = true;
    private boolean isIncludeError = false;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_center_dali_modify_param;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.CenterDaliBatchModifyParamDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogCenterDaliModifyParamBinding, CenterDaliBatchModifyParamDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogCenterDaliModifyParamBinding viewBinding, final CenterDaliBatchModifyParamDialog dialog) {
            dialog.initRv(dialog);
            viewBinding.tvCancel.setAlpha(0.5f);
            viewBinding.tvConfirm.setAlpha(0.5f);
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.CenterDaliBatchModifyParamDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    CenterDaliBatchModifyParamDialog.AnonymousClass1.lambda$convertView$0(CenterDaliBatchModifyParamDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(CenterDaliBatchModifyParamDialog centerDaliBatchModifyParamDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                if (centerDaliBatchModifyParamDialog.isWorking) {
                    return;
                }
                centerDaliBatchModifyParamDialog.dismissDialog();
            } else if (id == R.id.tv_confirm && !centerDaliBatchModifyParamDialog.isWorking && centerDaliBatchModifyParamDialog.confirmAction != null && centerDaliBatchModifyParamDialog.isIncludeError) {
                centerDaliBatchModifyParamDialog.isIncludeError = false;
                centerDaliBatchModifyParamDialog.confirmAction.act(0);
            }
        }
    }

    public static CenterDaliBatchModifyParamDialog asDefault() {
        return (CenterDaliBatchModifyParamDialog) new CenterDaliBatchModifyParamDialog().setViewConverter(new AnonymousClass1()).setWidth(280).setHeight(0).setGravity(17).setOutCancel(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRv(CenterDaliBatchModifyParamDialog dialog) {
        BaseQuickAdapter<Device, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Device, BaseViewHolder>(R.layout.item_dali_batch_modify_param, dialog.deviceList) { // from class: com.ltech.smarthome.view.dialog.CenterDaliBatchModifyParamDialog.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Device item) {
                helper.setText(R.id.tv_num, String.valueOf(((CgdProLightExtParam) item.getExtParam(CgdProLightExtParam.class)).getDaliAddr()));
                helper.setText(R.id.tv_place_info, CenterDaliBatchModifyParamDialog.this.getPlaceInfo(item.getFloorId(), item.getRoomId()));
                helper.setVisible(R.id.sync_tv, false);
                helper.setText(R.id.tv_device_name, item.getDeviceName());
                ActDaliBatchModifyParamVM.BatchSaveItem batchSaveItem = CenterDaliBatchModifyParamDialog.this.batchSaveItemMap.get(Long.valueOf(item.getId()));
                if (batchSaveItem != null) {
                    if (batchSaveItem.state == ActDaliBatchModifyParamVM.StateParam.STATE_WORKING || batchSaveItem.state == ActDaliBatchModifyParamVM.StateParam.STATE_PENDING) {
                        helper.setVisible(R.id.iv_upgrade_waiting, true);
                        helper.setVisible(R.id.iv_select, false);
                        ((CircularProgressView) helper.getView(R.id.iv_upgrade_waiting)).setProgress(batchSaveItem.progress);
                        return;
                    } else if (batchSaveItem.state == ActDaliBatchModifyParamVM.StateParam.STATE_COMPLETED) {
                        helper.setVisible(R.id.iv_select, true);
                        helper.setBackgroundRes(R.id.iv_select, R.mipmap.light_mode_icon_succes);
                        helper.setVisible(R.id.iv_upgrade_waiting, false);
                        return;
                    } else if (batchSaveItem.state == ActDaliBatchModifyParamVM.StateParam.STATE_ERROR) {
                        helper.setVisible(R.id.iv_select, true);
                        helper.setBackgroundRes(R.id.iv_select, R.mipmap.light_mode_icon_fail);
                        helper.setVisible(R.id.iv_upgrade_waiting, false);
                        return;
                    } else {
                        helper.setVisible(R.id.iv_select, true);
                        helper.setVisible(R.id.iv_upgrade_waiting, false);
                        return;
                    }
                }
                helper.setVisible(R.id.iv_select, true);
                helper.setVisible(R.id.iv_upgrade_waiting, false);
            }
        };
        this.adapter = baseQuickAdapter;
        baseQuickAdapter.bindToRecyclerView(((DialogCenterDaliModifyParamBinding) this.mViewBinding).rvContent);
        ((DialogCenterDaliModifyParamBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
        ((DialogCenterDaliModifyParamBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((DialogCenterDaliModifyParamBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
        ((DialogCenterDaliModifyParamBinding) this.mViewBinding).rvContent.post(new Runnable() { // from class: com.ltech.smarthome.view.dialog.CenterDaliBatchModifyParamDialog$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CenterDaliBatchModifyParamDialog.this.lambda$initRv$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$0() {
        View childAt;
        if (((DialogCenterDaliModifyParamBinding) this.mViewBinding).rvContent.getAdapter() == null || ((DialogCenterDaliModifyParamBinding) this.mViewBinding).rvContent.getAdapter().getItemCount() <= 3 || (childAt = ((DialogCenterDaliModifyParamBinding) this.mViewBinding).rvContent.getChildAt(0)) == null) {
            return;
        }
        int height = childAt.getHeight() * 3;
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) ((DialogCenterDaliModifyParamBinding) this.mViewBinding).rvContent.getLayoutParams();
        layoutParams.height = height;
        ((DialogCenterDaliModifyParamBinding) this.mViewBinding).rvContent.setLayoutParams(layoutParams);
    }

    public CenterDaliBatchModifyParamDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public void changeTitle(String title) {
        ((DialogCenterDaliModifyParamBinding) this.mViewBinding).tvTitle.setText(title);
    }

    public CenterDaliBatchModifyParamDialog setRoomList(List<Room> roomList) {
        this.roomList = roomList;
        return this;
    }

    public CenterDaliBatchModifyParamDialog setFloorList(List<Floor> floorList) {
        this.floorList = floorList;
        return this;
    }

    public CenterDaliBatchModifyParamDialog setDeviceList(List<Device> list) {
        this.deviceList.clear();
        this.deviceList.addAll(list);
        return this;
    }

    public CenterDaliBatchModifyParamDialog setBatchSaveItemList(List<ActDaliBatchModifyParamVM.BatchSaveItem> batchSaveItemList) {
        for (ActDaliBatchModifyParamVM.BatchSaveItem batchSaveItem : batchSaveItemList) {
            this.batchSaveItemMap.put(Long.valueOf(batchSaveItem.getDevice().getId()), batchSaveItem);
        }
        return this;
    }

    public CenterDaliBatchModifyParamDialog setConfirmAction(IAction<Integer> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    public void setProgress(ActDaliBatchModifyParamVM.StateParam stateParam) {
        ActDaliBatchModifyParamVM.BatchSaveItem batchSaveItem = this.batchSaveItemMap.get(Long.valueOf(stateParam.id));
        if (batchSaveItem != null) {
            batchSaveItem.progress = stateParam.progress;
            batchSaveItem.state = stateParam.state;
            this.isWorking = true;
            if (stateParam.state == ActDaliBatchModifyParamVM.StateParam.STATE_ALL_COMPLETED) {
                this.isWorking = false;
            }
            if (stateParam.state == ActDaliBatchModifyParamVM.StateParam.STATE_ERROR) {
                this.isIncludeError = true;
            }
            float f = 0.5f;
            ((DialogCenterDaliModifyParamBinding) this.mViewBinding).tvCancel.setAlpha(this.isWorking ? 0.5f : 1.0f);
            AppCompatTextView appCompatTextView = ((DialogCenterDaliModifyParamBinding) this.mViewBinding).tvConfirm;
            if (!this.isWorking && this.isIncludeError) {
                f = 1.0f;
            }
            appCompatTextView.setAlpha(f);
            ((DialogCenterDaliModifyParamBinding) this.mViewBinding).rvContent.smoothScrollToPosition(this.adapter.getData().indexOf(batchSaveItem.getDevice()));
            this.adapter.notifyDataSetChanged();
        }
    }

    public String getPlaceInfo(long floorId, long roomId) {
        StringBuilder sb = new StringBuilder();
        List<Floor> list = this.floorList;
        if (list != null) {
            Iterator<Floor> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Floor next = it.next();
                if (floorId == next.getFloorId()) {
                    sb.append(next.getFloorName());
                    break;
                }
            }
        }
        List<Room> list2 = this.roomList;
        if (list2 != null) {
            Iterator<Room> it2 = list2.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Room next2 = it2.next();
                if (roomId == next2.getRoomId()) {
                    sb.append(next2.getRoomName());
                    break;
                }
            }
        }
        return sb.toString();
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "center_dali_modify_param_dialog";
    }
}