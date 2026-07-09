package com.ltech.smarthome.ui.device.spicontroller;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSpiEditPlayListBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.SpiLightExtParam;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSpiEditPlayList extends VMActivity<ActSpiEditPlayListBinding, BaseDeviceSetViewModel> {
    private SpiLightExtParam extParam;
    private String lastListString;
    private Device mDevice;
    private String[] modeNameArray;
    private BaseItemDraggableAdapter<Integer, BaseViewHolder> notSelectAdapter;
    private int position;
    private BaseItemDraggableAdapter<Integer, BaseViewHolder> selectAdapter;
    public List<Integer> selectList = new ArrayList();
    public List<Integer> notSelectList = new ArrayList();

    private int itemLayout() {
        return R.layout.item_spi_edit_play_list;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_spi_edit_play_list;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.spi_edit_play_list));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        this.mDevice = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
        this.position = getIntent().getIntExtra(Constants.LIST_POSITION, 0);
        ((BaseDeviceSetViewModel) this.mViewModel).controlDevice.setValue(this.mDevice);
        this.extParam = new SpiLightExtParam();
        Device value = ((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue();
        if (value.getExtParam() != null) {
            this.extParam = (SpiLightExtParam) value.getExtParam(SpiLightExtParam.class);
        }
        ((ActSpiEditPlayListBinding) this.mViewBinding).tvSelect.setText(R.string.spi_in_play_list);
        ((ActSpiEditPlayListBinding) this.mViewBinding).tvNotSelect.setText(R.string.spi_not_in_play_list);
        this.modeNameArray = getResources().getStringArray(R.array.spi_mode_array);
        initSelectAdapter();
        initNotSelectAdapter();
        String stringExtra = getIntent().getStringExtra(Constants.MODE_LIST);
        this.lastListString = stringExtra;
        this.selectList = (List) GsonUtils.fromJson(stringExtra, new TypeToken<List<Integer>>(this) { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiEditPlayList.1
        }.getType());
        changeList();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((BaseDeviceSetViewModel) this.mViewModel).refreshEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiEditPlayList$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSpiEditPlayList.this.lambda$startObserve$0((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Void r1) {
        finish();
    }

    private void changeList() {
        this.notSelectList.clear();
        for (int i = 1; i <= 16; i++) {
            if (!this.selectList.contains(Integer.valueOf(i))) {
                this.notSelectList.add(Integer.valueOf(i));
            }
        }
        this.selectAdapter.replaceData(this.selectList);
        this.notSelectAdapter.replaceData(this.notSelectList);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        if (!GsonUtils.toJson(this.selectList).equals(this.lastListString)) {
            showListChangeDialog();
        } else {
            super.back();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        CmdAssistant.getModeCmdAssistant(this.mDevice, new int[0]).setSpiModeList(this, this.selectList, 1, 0, new IAction() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiEditPlayList$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSpiEditPlayList.this.lambda$edit$1((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$1(Boolean bool) {
        if (bool.booleanValue()) {
            showSuccessDialog(getString(R.string.save_success));
            this.lastListString = GsonUtils.toJson(this.selectList);
            List<SpiLightExtParam.PlayList> playList = this.extParam.getPlayList();
            playList.get(this.position).setModes(this.selectList);
            this.extParam.setPlayList(playList);
            if (((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue() != null) {
                ((BaseDeviceSetViewModel) this.mViewModel).updateParamExt(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue(), GsonUtils.toJson(this.extParam));
            }
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.SPI_PLAY_LIST, CmdAssistant.getModeCmdAssistant(this.mDevice, new int[0]).setSpiModeList(this.selectList, 1, 0));
            ReplaceHelper.instance().backupIndexData(this, Collections.singletonList(Long.valueOf(this.mDevice.getDeviceId())), 1);
            return;
        }
        showErrorDialog(getString(R.string.save_fail));
    }

    private void initSelectAdapter() {
        BaseItemDraggableAdapter<Integer, BaseViewHolder> baseItemDraggableAdapter = new BaseItemDraggableAdapter<Integer, BaseViewHolder>(itemLayout(), new ArrayList()) { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiEditPlayList.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder holder, Integer item) {
                holder.setText(R.id.tv_name, ActSpiEditPlayList.this.modeNameArray[item.intValue() - 1]).setBackgroundRes(R.id.iv_select, R.mipmap.spgroup_edit_delete).addOnClickListener(R.id.iv_select);
                ((AppCompatTextView) holder.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
            }
        };
        this.selectAdapter = baseItemDraggableAdapter;
        baseItemDraggableAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiEditPlayList$$ExternalSyntheticLambda3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSpiEditPlayList.this.lambda$initSelectAdapter$2(baseQuickAdapter, view, i);
            }
        });
        this.selectAdapter.bindToRecyclerView(((ActSpiEditPlayListBinding) this.mViewBinding).rvSelectContent);
        ((ActSpiEditPlayListBinding) this.mViewBinding).rvSelectContent.setHasFixedSize(true);
        ((ActSpiEditPlayListBinding) this.mViewBinding).rvSelectContent.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemDragAndSwipeCallback(this.selectAdapter) { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiEditPlayList.3
            @Override // com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback, androidx.recyclerview.widget.ItemTouchHelper.Callback
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                ActSpiEditPlayList actSpiEditPlayList = ActSpiEditPlayList.this;
                actSpiEditPlayList.selectList = actSpiEditPlayList.selectAdapter.getData();
            }
        });
        itemTouchHelper.attachToRecyclerView(((ActSpiEditPlayListBinding) this.mViewBinding).rvSelectContent);
        this.selectAdapter.enableDragItem(itemTouchHelper);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initSelectAdapter$2(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        Integer num = this.selectList.get(i);
        this.selectList.remove(num);
        this.selectAdapter.replaceData(this.selectList);
        this.notSelectList.add(num);
        this.notSelectAdapter.replaceData(this.notSelectList);
    }

    private void initNotSelectAdapter() {
        BaseItemDraggableAdapter<Integer, BaseViewHolder> baseItemDraggableAdapter = new BaseItemDraggableAdapter<Integer, BaseViewHolder>(itemLayout(), new ArrayList()) { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiEditPlayList.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder holder, Integer item) {
                holder.setText(R.id.tv_name, ActSpiEditPlayList.this.modeNameArray[item.intValue() - 1]).setBackgroundRes(R.id.iv_select, R.mipmap.spgroup_edit_add).setGone(R.id.iv_sort, false).addOnClickListener(R.id.iv_select);
                ((AppCompatTextView) holder.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
            }
        };
        this.notSelectAdapter = baseItemDraggableAdapter;
        baseItemDraggableAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiEditPlayList$$ExternalSyntheticLambda4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSpiEditPlayList.this.lambda$initNotSelectAdapter$3(baseQuickAdapter, view, i);
            }
        });
        this.notSelectAdapter.bindToRecyclerView(((ActSpiEditPlayListBinding) this.mViewBinding).rvNotSelectContent);
        ((ActSpiEditPlayListBinding) this.mViewBinding).rvNotSelectContent.setHasFixedSize(true);
        ((ActSpiEditPlayListBinding) this.mViewBinding).rvNotSelectContent.setLayoutManager(new LinearLayoutManager(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initNotSelectAdapter$3(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        Integer num = this.notSelectList.get(i);
        this.notSelectList.remove(num);
        this.notSelectAdapter.replaceData(this.notSelectList);
        this.selectList.add(num);
        this.selectAdapter.replaceData(this.selectList);
    }

    private void showListChangeDialog() {
        MessageDialog.show(this, "", getString(R.string.spi_play_list_change_tip)).setOkButton(getString(R.string.save), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiEditPlayList$$ExternalSyntheticLambda0
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showListChangeDialog$4;
                lambda$showListChangeDialog$4 = ActSpiEditPlayList.this.lambda$showListChangeDialog$4(baseDialog, view);
                return lambda$showListChangeDialog$4;
            }
        }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.spicontroller.ActSpiEditPlayList$$ExternalSyntheticLambda1
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showListChangeDialog$5;
                lambda$showListChangeDialog$5 = ActSpiEditPlayList.this.lambda$showListChangeDialog$5(baseDialog, view);
                return lambda$showListChangeDialog$5;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showListChangeDialog$4(BaseDialog baseDialog, View view) {
        edit();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showListChangeDialog$5(BaseDialog baseDialog, View view) {
        finish();
        return false;
    }
}