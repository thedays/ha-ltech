package com.ltech.smarthome.ui.device.super_panel;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.BaseSelectDeviceActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.ResourceEmptyLiveData;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.SuperPanelInfo;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public class ActSuperPanelRelateDevice extends BaseSelectDeviceActivity {
    private long deviceId;
    private List<Device> allDeviceList = new ArrayList();
    public SingleLiveEvent<Void> showNoPermissionDialogEvent = new SingleLiveEvent<>();

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_device_manage;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.ltech.smarthome.base.BaseSelectDeviceActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        char c2;
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(getIntent().getLongExtra("device_id", -1L));
        int i = R.mipmap.pic_empty_device_mini;
        if (deviceByDeviceId != null) {
            String productId = deviceByDeviceId.getProductId();
            switch (productId.hashCode()) {
                case -1309274422:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO)) {
                        c2 = 6;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1308265372:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_12S)) {
                        c2 = 3;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1265646206:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                        c2 = 4;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -324427448:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_6S)) {
                        c2 = 2;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 294483828:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_4S)) {
                        c2 = 1;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 811752507:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI)) {
                        c2 = 0;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 956710656:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL)) {
                        c2 = 5;
                        break;
                    }
                    c2 = 65535;
                    break;
                default:
                    c2 = 65535;
                    break;
            }
            if (c2 != 0 && c2 != 1) {
                i = c2 != 2 ? c2 != 3 ? c2 != 4 ? R.mipmap.pic_empty_device_superpanel : R.mipmap.pic_empty_device_g4max : R.mipmap.pic_empty_device_12s : R.mipmap.pic_empty_device_6s;
            }
        }
        return Gloading.from(new DefaultAdapter().emptyImageRes(i).emptyTryStringRes(R.string.add).emptyStringRes(R.string.super_panel_empty_device));
    }

    @Override // com.ltech.smarthome.base.BaseSelectDeviceActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.display_device));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showContent() {
        super.showContent();
        setEditImage(R.mipmap.ic_edit_black);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        super.showEmpty();
        setEditImage(R.mipmap.ic_edit_black);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onEmptyTry() {
        super.onEmptyTry();
        if (getCurrentPlace().isOwner() || getCurrentPlace().isManager()) {
            long[] jArr = new long[this.dataList.size()];
            int size = this.dataList.size();
            for (int i = 0; i < size; i++) {
                jArr[i] = ((Device) this.dataList.get(i)).getDeviceId();
            }
            NavUtils.destination(ActSelectSuperPanelDevice.class).withLong(Constants.PLACE_ID, this.placeId).withLong("device_id", this.deviceId).withLongArray(Constants.DEVICE_ID_ARRAY, jArr).navigation(this);
            return;
        }
        this.showNoPermissionDialogEvent.call();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        onEmptyTry();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, Device item) {
        helper.setText(R.id.tv_device_name, item.getDeviceName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item)).setVisible(R.id.iv_go, false).setText(R.id.tv_place_info, getPlaceInfo(item.getFloorId(), item.getRoomId()));
        ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseSelectDeviceActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
        handleData(Transformations.switchMap(this.deviceResult, new Function1() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelRelateDevice$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$startObserve$0;
                lambda$startObserve$0 = ActSuperPanelRelateDevice.this.lambda$startObserve$0((Resource) obj);
                return lambda$startObserve$0;
            }
        }), new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelRelateDevice$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSuperPanelRelateDevice.this.lambda$startObserve$1((List) obj);
            }
        });
        this.showNoPermissionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelRelateDevice$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSuperPanelRelateDevice.this.lambda$startObserve$2((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$startObserve$0(Resource resource) {
        if (resource == null) {
            return ResourceEmptyLiveData.create();
        }
        this.allDeviceList.clear();
        if (resource.getData() != null) {
            this.allDeviceList.addAll((Collection) resource.getData());
        }
        return Injection.repo().device().getSuperPanelInfo(this, this.deviceId).data();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(List list) {
        List<Device> list2;
        List<Long> deviceIds;
        ArrayList arrayList = new ArrayList();
        if (list != null && list.size() > 0 && (list2 = this.allDeviceList) != null) {
            for (Device device : list2) {
                SuperPanelInfo superPanelInfo = (SuperPanelInfo) list.get(0);
                if (superPanelInfo != null && (deviceIds = superPanelInfo.getDeviceIds()) != null) {
                    Iterator<Long> it = deviceIds.iterator();
                    while (it.hasNext()) {
                        if (it.next().longValue() == device.getDeviceId()) {
                            arrayList.add(device);
                        }
                    }
                }
            }
        }
        if (arrayList.isEmpty()) {
            showEmpty();
        }
        setDataList(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r1) {
        showNoPermissionDialog();
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }
}