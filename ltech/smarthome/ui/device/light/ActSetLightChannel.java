package com.ltech.smarthome.ui.device.light;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSetLightChannelBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSetLightChannel extends BaseNormalActivity<ActSetLightChannelBinding> {
    private List<Integer> channelValues = new ArrayList();
    private Device controlDevice;
    private boolean isHb4;
    private int lightType;
    private int lineOrder;
    private BaseItemDraggableAdapter<Integer, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_set_light_channel;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.channel_set));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        this.controlDevice = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
        this.lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
        this.lineOrder = getIntent().getIntExtra(Constants.LINE_ORDER, -1);
        this.isHb4 = getIntent().getBooleanExtra(Constants.IS_HB4, false);
        initAdapter();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        setLineOrder();
    }

    private void initAdapter() {
        LineOrder.setHB4(this.isHb4);
        int length = (this.isHb4 ? Integer.toHexString(this.lineOrder) : Integer.toString(this.lineOrder)).length();
        int i = this.lineOrder;
        if (i > 0) {
            if (this.isHb4) {
                List<Integer> list = LineOrder.getList(i);
                this.channelValues = list;
                if (list.size() == 3) {
                    this.channelValues.add(0, 0);
                }
            } else if (length == LineOrder.getMap(this.lightType).size()) {
                this.channelValues = LineOrder.getList(this.lineOrder);
            }
        }
        if (this.channelValues.isEmpty()) {
            this.channelValues.addAll(LineOrder.getListByType(this.lightType));
        }
        BaseItemDraggableAdapter<Integer, BaseViewHolder> baseItemDraggableAdapter = new BaseItemDraggableAdapter<Integer, BaseViewHolder>(R.layout.item_light_order, this.channelValues) { // from class: com.ltech.smarthome.ui.device.light.ActSetLightChannel.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Integer item) {
                helper.setText(R.id.tv_port, String.valueOf(helper.getAdapterPosition() + 1)).setText(R.id.tv_output, LineOrder.getMap(ActSetLightChannel.this.lightType).get(ActSetLightChannel.this.channelValues.get(helper.getAdapterPosition())));
                ((AppCompatTextView) helper.getView(R.id.tv_port)).getPaint().setFakeBoldText(true);
                ((AppCompatTextView) helper.getView(R.id.tv_output)).getPaint().setFakeBoldText(true);
                helper.setVisible(R.id.divider, ActSetLightChannel.this.isHb4 && ActSetLightChannel.this.lightType == 2 && helper.getAdapterPosition() == 1);
            }
        };
        this.mAdapter = baseItemDraggableAdapter;
        baseItemDraggableAdapter.bindToRecyclerView(((ActSetLightChannelBinding) this.mViewBinding).rvContent);
        ((ActSetLightChannelBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSetLightChannelBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemDragAndSwipeCallback(this.mAdapter) { // from class: com.ltech.smarthome.ui.device.light.ActSetLightChannel.2
            private int groupOf(int pos) {
                if (pos == 0 || pos == 1) {
                    return 0;
                }
                if (pos == 2 || pos == 3) {
                    return 1;
                }
                return pos;
            }

            private boolean isHb4Ct() {
                return ActSetLightChannel.this.isHb4 && ActSetLightChannel.this.lightType == 2;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder current, RecyclerView.ViewHolder target) {
                if (isHb4Ct()) {
                    return groupOf(current.getAdapterPosition()) == groupOf(target.getAdapterPosition());
                }
                return true;
            }

            @Override // com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback, androidx.recyclerview.widget.ItemTouchHelper.Callback
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                if (isHb4Ct()) {
                    if (groupOf(viewHolder.getAdapterPosition()) != groupOf(target.getAdapterPosition())) {
                        return false;
                    }
                }
                return super.onMove(recyclerView, viewHolder, target);
            }

            @Override // com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback, androidx.recyclerview.widget.ItemTouchHelper.Callback
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                ActSetLightChannel.this.mAdapter.notifyDataSetChanged();
            }
        });
        itemTouchHelper.attachToRecyclerView(((ActSetLightChannelBinding) this.mViewBinding).rvContent);
        this.mAdapter.enableDragItem(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(((ActSetLightChannelBinding) this.mViewBinding).rvContent);
        this.mAdapter.enableDragItem(itemTouchHelper);
    }

    private void setLineOrder() {
        final int value = LineOrder.getValue(this.channelValues);
        if (this.isHb4) {
            final int i = (this.lightType << 4) + 4;
            getCmdHelper().setLineOrderMulti(this, i, value, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActSetLightChannel$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSetLightChannel.this.lambda$setLineOrder$0(i, value, (Boolean) obj);
                }
            });
        } else {
            final int i2 = this.lightType;
            if (i2 == 20) {
                i2 = 5;
            }
            getCmdHelper().setLineOrder(this, i2, value, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActSetLightChannel$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSetLightChannel.this.lambda$setLineOrder$1(i2, value, (Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setLineOrder$0(int i, int i2, Boolean bool) {
        if (bool.booleanValue()) {
            ReplaceHelper.instance().backupData(this, this.controlDevice.getDeviceId(), UpdateBackDataRequest.LIGHT_SEQUENCE, getCmdHelper().setLineOrderMulti(i, i2));
            finishActivity();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setLineOrder$1(int i, int i2, Boolean bool) {
        if (bool.booleanValue()) {
            ReplaceHelper.instance().backupData(this, this.controlDevice.getDeviceId(), UpdateBackDataRequest.LIGHT_SEQUENCE, getCmdHelper().setLineOrder(i, i2));
            finishActivity();
        }
    }

    protected LightAssistant getCmdHelper() {
        return CmdAssistant.getLightCmdAssistant(this.controlDevice, new int[0]);
    }
}