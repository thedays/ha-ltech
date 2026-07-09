package com.ltech.smarthome.ui.device.super_panel;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.BaseSelectGroupActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.ResourceEmptyLiveData;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.SuperPanelInfo;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public class ActSuperPanelRelateGroup extends BaseSelectGroupActivity {
    private long deviceId;
    private List<Group> allGroupList = new ArrayList();
    public SingleLiveEvent<Void> showNoPermissionDialogEvent = new SingleLiveEvent<>();

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select_with_icon;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(getIntent().getLongExtra("device_id", -1L));
        return Gloading.from(new DefaultAdapter().emptyImageRes(deviceByDeviceId != null && deviceByDeviceId.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI) ? R.mipmap.pic_empty_device_mini : R.mipmap.pic_empty_device_superpanel).emptyTryStringRes(R.string.add).emptyStringRes(R.string.super_panel_empty_group));
    }

    @Override // com.ltech.smarthome.base.BaseSelectGroupActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.display_group));
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
                jArr[i] = ((Group) this.dataList.get(i)).getGroupId();
            }
            NavUtils.destination(ActSelectSuperPanelGroup.class).withLong(Constants.PLACE_ID, this.placeId).withLong("device_id", this.deviceId).withLongArray(Constants.GROUP_ID_ARRAY, jArr).navigation(this);
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
    public void convertView(BaseViewHolder helper, Group item) {
        helper.setText(R.id.tv_name, item.getGroupName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item));
        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseSelectGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
        handleData(Transformations.switchMap(this.mRequest.data(), new Function1() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelRelateGroup$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$startObserve$0;
                lambda$startObserve$0 = ActSuperPanelRelateGroup.this.lambda$startObserve$0((Resource) obj);
                return lambda$startObserve$0;
            }
        }), new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelRelateGroup$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSuperPanelRelateGroup.this.lambda$startObserve$1((List) obj);
            }
        });
        this.showNoPermissionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelRelateGroup$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSuperPanelRelateGroup.this.lambda$startObserve$2((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$startObserve$0(Resource resource) {
        if (resource == null) {
            return ResourceEmptyLiveData.create();
        }
        this.allGroupList.clear();
        if (resource.getData() != null) {
            this.allGroupList.addAll((Collection) resource.getData());
        }
        return Injection.repo().device().getSuperPanelInfo(this, this.deviceId).data();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(List list) {
        ArrayList arrayList = new ArrayList();
        if (list != null && list.size() > 0) {
            for (Group group : this.allGroupList) {
                if (((SuperPanelInfo) list.get(0)).getGroupIds().contains(Long.valueOf(group.getGroupId()))) {
                    arrayList.add(group);
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