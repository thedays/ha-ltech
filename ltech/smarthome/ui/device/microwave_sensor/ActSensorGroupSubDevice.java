package com.ltech.smarthome.ui.device.microwave_sensor;

import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActLightGroupSubItemControlBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.ui.device.microwave_sensor.ActSensorGroupSubDevice;
import com.ltech.smarthome.ui.log.ActDeviceLog;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LiveBusHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.smart.message.ResponseMsg;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSensorGroupSubDevice extends VMActivity<ActLightGroupSubItemControlBinding, ActWaveSensorVM> {
    private List<Device> deviceList = new ArrayList();
    private boolean[] expandArray;
    private Group group;
    private BaseQuickAdapter<Device, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_light_group_sub_item_control;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected boolean useEventBus() {
        return true;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.group_device));
        ((ActWaveSensorVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        Group groupById = Injection.repo().group().getGroupById(((ActWaveSensorVM) this.mViewModel).controlId);
        this.group = groupById;
        Iterator<Long> it = groupById.getDeviceIds().iterator();
        while (it.hasNext()) {
            this.deviceList.add(Injection.repo().device().getDeviceByDeviceId(it.next().longValue()));
        }
        this.expandArray = new boolean[this.deviceList.size()];
        initAdapter();
        this.mAdapter.replaceData(this.deviceList);
    }

    /* renamed from: com.ltech.smarthome.ui.device.microwave_sensor.ActSensorGroupSubDevice$1, reason: invalid class name */
    class AnonymousClass1 extends BaseQuickAdapter<Device, BaseViewHolder> {
        AnonymousClass1(int layoutResId, List data) {
            super(layoutResId, data);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(final BaseViewHolder helper, Device item) {
            helper.setText(R.id.tv_name, item.getName()).setText(R.id.tv_lux, ActSensorGroupSubDevice.this.getString(R.string.lux_value, new Object[]{Integer.valueOf(item.getDeviceState().getWaveSensorState().getCurLux())})).setText(R.id.tv_ct, ActSensorGroupSubDevice.this.getString(R.string.ct_value, new Object[]{Integer.valueOf(item.getDeviceState().getWaveSensorState().getCurCt())})).addOnClickListener(R.id.layout_sense_record, R.id.layout_environment_log);
            helper.setTypeface(Typeface.defaultFromStyle(1), R.id.tv_lux, R.id.tv_ct);
            final LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.layout_content);
            linearLayout.setVisibility(ActSensorGroupSubDevice.this.expandArray[helper.getBindingAdapterPosition()] ? 0 : 8);
            final AppCompatImageView appCompatImageView = (AppCompatImageView) helper.getView(R.id.iv_name_show);
            helper.getView(R.id.layout_name).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActSensorGroupSubDevice$1$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ActSensorGroupSubDevice.AnonymousClass1.this.lambda$convert$0(linearLayout, helper, appCompatImageView, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(LinearLayout linearLayout, BaseViewHolder baseViewHolder, AppCompatImageView appCompatImageView, View view) {
            if (linearLayout.getVisibility() == 0) {
                ActSensorGroupSubDevice.this.expandArray[baseViewHolder.getBindingAdapterPosition()] = false;
                linearLayout.setVisibility(8);
                appCompatImageView.setImageResource(R.mipmap.ic_down_gray);
            } else {
                ActSensorGroupSubDevice.this.expandArray[baseViewHolder.getBindingAdapterPosition()] = true;
                linearLayout.setVisibility(0);
                appCompatImageView.setImageResource(R.mipmap.ic_up_gray);
            }
        }
    }

    private void initAdapter() {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(R.layout.item_sensor_sub_device, this.deviceList);
        this.mAdapter = anonymousClass1;
        anonymousClass1.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActSensorGroupSubDevice$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSensorGroupSubDevice.this.lambda$initAdapter$0(baseQuickAdapter, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActLightGroupSubItemControlBinding) this.mViewBinding).rv);
        ((ActLightGroupSubItemControlBinding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(this));
        ((ActLightGroupSubItemControlBinding) this.mViewBinding).rv.setHasFixedSize(true);
        ((ActLightGroupSubItemControlBinding) this.mViewBinding).rv.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActSensorGroupSubDevice.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 0, 0, ConvertUtils.dp2px(10.0f));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAdapter$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int id = view.getId();
        if (id == R.id.layout_environment_log) {
            NavUtils.destination(ActEnvironmentLog.class).withLong("device_id", this.mAdapter.getItem(i).getDeviceId()).navigation(this);
        } else {
            if (id != R.id.layout_sense_record) {
                return;
            }
            Device item = this.mAdapter.getItem(i);
            NavUtils.destination(ActDeviceLog.class).withLong("device_id", item.getDeviceId()).withString(Constants.PRODUCT_ID, item.getProductId()).navigation(this);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void handleBusEvent(LiveBusHelper helper) {
        if (17 != helper.getCode() || this.mViewBinding == 0) {
            return;
        }
        Object data = helper.getData();
        if (data instanceof ResponseMsg) {
            ResponseMsg responseMsg = (ResponseMsg) data;
            for (int i = 0; i < this.mAdapter.getData().size(); i++) {
                Device item = this.mAdapter.getItem(i);
                int parseInt = Integer.parseInt(responseMsg.getResData().substring(6, 10), 16);
                if (item != null && parseInt == ((BleParam) item.getParam(BleParam.class)).getUnicastAddress()) {
                    ((ActWaveSensorVM) this.mViewModel).convertState(responseMsg.getResData(), item.getDeviceState().getWaveSensorState());
                    Injection.repo().device().saveDevice(item);
                    this.mAdapter.notifyItemChanged(i);
                }
            }
        }
    }
}