package com.ltech.smarthome.ui.device.kbs;

import android.graphics.Rect;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActKbsBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.KbsExtParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.IconRepository;
import com.ltech.smarthome.utils.NavHelper;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.bean.SwitchPanelState;
import com.smart.product_agreement.parser.IPanelParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActKbs extends BaseControlActivity<ActKbsBinding, ActKbsVM> {
    private BaseQuickAdapter<KbsKey, BaseViewHolder> adapter;
    List<Device> devices = null;
    private boolean[] selectArray;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_kbs;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        ((ActKbsVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActKbsVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActKbsVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        initListView();
    }

    private void startObjectObserve() {
        if (((ActKbsVM) this.mViewModel).groupControl) {
            Group groupById = Injection.repo().group().getGroupById(((ActKbsVM) this.mViewModel).controlId);
            ((ActKbsVM) this.mViewModel).controlObject.setValue(groupById);
            setTitle(groupById.getGroupName());
            return;
        }
        Device deviceById = Injection.repo().device().getDeviceById(((ActKbsVM) this.mViewModel).controlId);
        setTitle(deviceById.getDeviceName());
        LHomeLog.i(getClass(), "message_send (device)getDeviceFromDb enter");
        try {
            if (((ActKbsVM) this.mViewModel).controlObject.getValue() == null) {
                LHomeLog.i(getClass(), "message_send (device)getDeviceFromDb mViewModel.controlObject.getValue()==null");
                ((ActKbsVM) this.mViewModel).controlObject.setValue(deviceById);
            } else {
                if (HelpUtils.compareObject((Device) ((ActKbsVM) this.mViewModel).controlObject.getValue(), deviceById)) {
                    return;
                }
                LHomeLog.i(getClass(), "message_send device changed");
                ((ActKbsVM) this.mViewModel).controlObject.setValue(deviceById);
            }
        } catch (Exception e) {
            LHomeLog.i(getClass(), "message_send (device)getDeviceFromDb Exception" + e.toString());
            e.printStackTrace();
        }
    }

    private void initListView() {
        ((ActKbsBinding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(this));
        BaseQuickAdapter<KbsKey, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<KbsKey, BaseViewHolder>(R.layout.item_kbs_switch) { // from class: com.ltech.smarthome.ui.device.kbs.ActKbs.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, KbsKey key) {
                helper.setText(R.id.tv_name, key.getName());
                helper.setImageResource(R.id.iv_switch, ActKbs.this.selectArray[helper.getAdapterPosition()] ? R.mipmap.icon_ir_power_blue : R.mipmap.icon_ir_power);
                helper.setText(R.id.tv_floor, key.getFloor()).setGone(R.id.tv_floor, !StringUtils.isEmpty(key.getFloor()));
                helper.setImageResource(R.id.iv_icon, key.getIco());
            }
        };
        this.adapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbs$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActKbs.this.lambda$initListView$0(baseQuickAdapter2, view, i);
            }
        });
        this.adapter.bindToRecyclerView(((ActKbsBinding) this.mViewBinding).rv);
        ((ActKbsBinding) this.mViewBinding).rv.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActKbsBinding) this.mViewBinding).rv.getItemAnimator()).setSupportsChangeAnimations(false);
        ((ActKbsBinding) this.mViewBinding).rv.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.kbs.ActKbs.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.right = ConvertUtils.dp2px(15.0f);
                outRect.left = ConvertUtils.dp2px(15.0f);
                outRect.bottom = ConvertUtils.dp2px(15.0f);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.selectArray[i] = !r1[i];
        this.adapter.notifyItemChanged(i);
        ((ActKbsVM) this.mViewModel).sendSingleOnOff(i, this.selectArray[i]);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActKbsVM) this.mViewModel).controlObject.observe(this, new Observer<Object>() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbs.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(final Object o) {
                ActKbs.this.bindData();
                ((ActKbsVM) ActKbs.this.mViewModel).queryPanelState(o, 4, 2);
                MessageManager.getInstance().setPanelSwitchStatusCallBack(new MessageManager.SmartPanelSwitchStatusCallBack() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbs.3.1
                    @Override // com.smart.message.MessageManager.SmartPanelSwitchStatusCallBack
                    public void onDataReceive(ResponseMsg msg) {
                        int firstDevUniAddr;
                        Object obj = o;
                        if (obj instanceof Device) {
                            firstDevUniAddr = ((BleParam) ((Device) obj).getParam(BleParam.class)).getUnicastAddress();
                        } else {
                            firstDevUniAddr = obj instanceof Group ? ((Group) obj).getFirstDevUniAddr() : 0;
                        }
                        ((ActKbsVM) ActKbs.this.mViewModel).refreshPanelState(((IPanelParser) Injection.strategy().getParserStrategy(msg.getAgreementId())).parserSwitchPanelState(msg.getAgreementId(), firstDevUniAddr, 2, msg.getResData()));
                    }
                });
            }
        });
        ((ActKbsVM) this.mViewModel).switchChange.observe(this, new Observer<SwitchPanelState>() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbs.4
            @Override // androidx.lifecycle.Observer
            public void onChanged(SwitchPanelState switchPanelState) {
                if (switchPanelState == null || switchPanelState.getSwitchPanelZoneStateList() == null) {
                    return;
                }
                for (int i = 0; i < switchPanelState.getSwitchPanelZoneStateList().size(); i++) {
                    if (ActKbs.this.selectArray.length > i) {
                        ActKbs.this.selectArray[i] = switchPanelState.getSwitchPanelZoneStateList().get(i).isSwitchOnOff();
                        ActKbs.this.adapter.notifyItemChanged(i);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindData() {
        String string;
        String string2;
        Object value = ((ActKbsVM) this.mViewModel).controlObject.getValue();
        ArrayList arrayList = new ArrayList();
        if (value instanceof Device) {
            Device device = (Device) value;
            List<Device> subDevice = Injection.repo().device().getSubDevice(((ActKbsVM) this.mViewModel).placeId, device.getDeviceId());
            this.devices = subDevice;
            Collections.sort(subDevice, new Comparator<Device>(this) { // from class: com.ltech.smarthome.ui.device.kbs.ActKbs.5
                @Override // java.util.Comparator
                public int compare(Device o1, Device o2) {
                    return o1.getWifiMac().compareTo(o2.getWifiMac());
                }
            });
            boolean[] zArr = new boolean[this.devices.size()];
            this.selectArray = zArr;
            if (zArr.length > 1) {
                zArr[0] = this.devices.get(0).getDeviceState().isOn();
                this.selectArray[1] = this.devices.get(1).getDeviceState().isOn();
            }
            for (Device device2 : this.devices) {
                arrayList.add(new KbsKey(device2.getName(), device.getFloorName() + " " + device.getRoomName(), ProductRepository.getProductIcon(device2)));
            }
        } else if (value instanceof Group) {
            Group group = (Group) value;
            KbsExtParam kbsExtParam = (KbsExtParam) group.getExtParam(KbsExtParam.class);
            int[] lightGroupIconValue = IconRepository.getLightGroupIconValue(this);
            int imgindex1 = kbsExtParam == null ? 0 : kbsExtParam.getImgindex1();
            int i = R.mipmap.icon_light_group;
            int i2 = (imgindex1 < 0 || imgindex1 > lightGroupIconValue.length) ? R.mipmap.icon_light_group : lightGroupIconValue[imgindex1];
            int imgindex2 = kbsExtParam == null ? 0 : kbsExtParam.getImgindex2();
            if (imgindex2 >= 0 && imgindex2 <= lightGroupIconValue.length) {
                i = lightGroupIconValue[imgindex2];
            }
            if (kbsExtParam == null || StringUtils.isEmpty(kbsExtParam.getName1())) {
                string = getString(R.string.app_str_smart_panel_switch1);
            } else {
                string = kbsExtParam.getName1();
            }
            arrayList.add(new KbsKey(string, "", i2));
            if (kbsExtParam == null || StringUtils.isEmpty(kbsExtParam.getName2())) {
                string2 = getString(R.string.app_str_smart_panel_switch2);
            } else {
                string2 = kbsExtParam.getName2();
            }
            arrayList.add(new KbsKey(string2, "", i));
            this.selectArray = new boolean[]{group.getGroupState().getOnOffStates().get(0).booleanValue(), false};
            this.selectArray[1] = group.getGroupState().getOnOffStates().get(1).booleanValue();
        }
        this.adapter.replaceData(arrayList);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        startObjectObserve();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        NavHelper.goSetting(((ActKbsVM) this.mViewModel).controlObject.getValue());
    }

    private static class KbsKey {
        private String floor;
        private int ico;
        private String name;

        public KbsKey(String name, String floor, int ico) {
            this.name = name;
            this.floor = floor;
            this.ico = ico;
        }

        public int getIco() {
            return this.ico;
        }

        public void setIco(int ico) {
            this.ico = ico;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFloor() {
            return this.floor;
        }

        public void setFloor(String floor) {
            this.floor = floor;
        }
    }
}