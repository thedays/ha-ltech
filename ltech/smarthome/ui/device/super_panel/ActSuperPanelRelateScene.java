package com.ltech.smarthome.ui.device.super_panel;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.recyclerview.widget.GridLayoutManager;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.BaseSelectSceneActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.ResourceEmptyLiveData;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.bean.SuperPanelInfo;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.net.response.super_panel.DetailSuperPanelResponse;
import com.ltech.smarthome.ui.device.gqpro.BleSyncHelper;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.view.popup.PanelSelectScenePopup;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public class ActSuperPanelRelateScene extends BaseSelectSceneActivity {
    private Device device;
    private long deviceId;
    private BleSyncHelper mBleSyncHelper;
    private List<Scene> allSceneList = new ArrayList();
    public SingleLiveEvent<Void> showNoPermissionDialogEvent = new SingleLiveEvent<>();
    private List<Long> sceneIds = new ArrayList();
    private List<SuperPanelInfo.SortInfo> sortSceneList = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_scene_new;
    }

    @Override // com.ltech.smarthome.base.BaseSelectSceneActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.scrollView;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        char c2;
        Device device = this.device;
        int i = R.mipmap.pic_empty_device_mini;
        if (device != null) {
            String productId = device.getProductId();
            switch (productId.hashCode()) {
                case -1309274422:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO)) {
                        c2 = 7;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1308265372:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_12S)) {
                        c2 = 4;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1265646206:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                        c2 = 5;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1201890867:
                    if (productId.equals(ProductId.ID_SMART_PANEL_GQ_PRO)) {
                        c2 = 0;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -324427448:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_6S)) {
                        c2 = 3;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 294483828:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_4S)) {
                        c2 = 2;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 811752507:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI)) {
                        c2 = 1;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 956710656:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL)) {
                        c2 = 6;
                        break;
                    }
                    c2 = 65535;
                    break;
                default:
                    c2 = 65535;
                    break;
            }
            if (c2 == 0) {
                i = R.mipmap.empty_gq_bg_pic;
            } else if (c2 != 1 && c2 != 2) {
                i = c2 != 3 ? c2 != 4 ? c2 != 5 ? R.mipmap.pic_empty_device_superpanel : R.mipmap.pic_empty_device_g4max : R.mipmap.pic_empty_device_12s : R.mipmap.pic_empty_device_6s;
            }
        }
        return Gloading.from(new DefaultAdapter().emptyImageRes(i).emptyTryStringRes(R.string.add).emptyStringRes(R.string.super_panel_empty_scene));
    }

    @Override // com.ltech.smarthome.base.BaseSelectSceneActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.display_scene));
        this.device = Injection.repo().device().getDeviceByDeviceId(getIntent().getLongExtra("device_id", -1L));
        ((ActSelectBinding) this.mViewBinding).layoutSearch.setVisibility(8);
        ((ActSelectBinding) this.mViewBinding).rvContent.setPadding(ConvertUtils.dp2px(5.0f), 0, ConvertUtils.dp2px(5.0f), ConvertUtils.dp2px(5.0f));
        ((ActSelectBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(this, 2));
        ((ActSelectBinding) this.mViewBinding).title.ivSearch.setVisibility(0);
        ((ActSelectBinding) this.mViewBinding).title.ivSearch.setImageResource(R.mipmap.ic_sort);
        ((ActSelectBinding) this.mViewBinding).title.ivSearch.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelRelateScene.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                long[] jArr = new long[ActSuperPanelRelateScene.this.sceneIds.size()];
                int size = ActSuperPanelRelateScene.this.sceneIds.size();
                for (int i = 0; i < size; i++) {
                    jArr[i] = ((Long) ActSuperPanelRelateScene.this.sceneIds.get(i)).longValue();
                }
                NavUtils.destination(ActSortScene.class).withLong("device_id", ActSuperPanelRelateScene.this.deviceId).withLongArray(Constants.SCENE_ID_ARRAY, jArr).withInt(Constants.MAX, ActSuperPanelRelateScene.this.getIntent().getIntExtra(Constants.MAX, -1)).withDefaultRequestCode().navigation(ActSuperPanelRelateScene.this);
            }
        });
        initSyncBtn();
    }

    private void initSyncBtn() {
        boolean booleanExtra = getIntent().getBooleanExtra(Constants.NEED_BLE_SYNC, false);
        ((ActSelectBinding) this.mViewBinding).groupSync.setVisibility(booleanExtra ? 0 : 8);
        ((ActSelectBinding) this.mViewBinding).viewSync.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelRelateScene.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ActSuperPanelRelateScene.this.showSyncDialog();
            }
        });
        if (booleanExtra) {
            this.mBleSyncHelper = new BleSyncHelper(this.activity, this.device, getMainHandler());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSyncDialog() {
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), this.activity.getString(R.string.app_str_gqpro_syning), this.activity.getString(R.string.app_str_need_gqpro_sync_immediately), this.activity.getString(R.string.app_str_gqpro_sync_now), this.activity.getString(R.string.cancel)).setOkButton(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelRelateScene.4
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                if (ActSuperPanelRelateScene.this.dataList.isEmpty()) {
                    if (ActSuperPanelRelateScene.this.mBleSyncHelper == null) {
                        return false;
                    }
                    ActSuperPanelRelateScene.this.mBleSyncHelper.delete(6);
                    return false;
                }
                if (ActSuperPanelRelateScene.this.mBleSyncHelper == null) {
                    return false;
                }
                ActSuperPanelRelateScene.this.mBleSyncHelper.startSync();
                return false;
            }
        }).setCancelButton(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelRelateScene.3
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                if (!ActSuperPanelRelateScene.this.isBlePermissionOk()) {
                    return false;
                }
                Injection.mesh().checkNearbyDevice(ActSuperPanelRelateScene.this.activity);
                return false;
            }
        }).setCancelable(false).show();
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
                jArr[i] = ((Scene) this.dataList.get(i)).getSceneId();
            }
            NavUtils.destination(ActSelectSuperPanelAllScene.class).withInt(Constants.MAX, getIntent().getIntExtra(Constants.MAX, -1)).withLong(Constants.PLACE_ID, this.placeId).withLong("device_id", this.deviceId).withBoolean(Constants.IS_GQ, getIntent().getBooleanExtra(Constants.IS_GQ, false)).withLongArray(Constants.SCENE_ID_ARRAY, jArr).navigation(this);
            return;
        }
        this.showNoPermissionDialogEvent.call();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.scenes));
        arrayList.add(getString(R.string.dali_scenes));
        final PanelSelectScenePopup apply = PanelSelectScenePopup.create(this).setData(arrayList).apply();
        apply.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelRelateScene$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSuperPanelRelateScene.this.lambda$edit$0(apply, baseQuickAdapter, view, i);
            }
        });
        apply.showAtAnchorView(((ActSelectBinding) this.mViewBinding).title.ivEdit, 2, 1, 130, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$0(PanelSelectScenePopup panelSelectScenePopup, BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (i == 0) {
            onEmptyTry();
        } else {
            long[] jArr = new long[this.dataList.size()];
            int size = this.dataList.size();
            for (int i2 = 0; i2 < size; i2++) {
                jArr[i2] = ((Scene) this.dataList.get(i2)).getSceneId();
            }
            NavUtils.destination(ActSuperPanelSelectCgdPro.class).withLong(Constants.PLACE_ID, this.placeId).withLong("device_id", this.deviceId).withLongArray(Constants.SCENE_ID_ARRAY, jArr).withInt(Constants.MAX, getIntent().getIntExtra(Constants.MAX, -1)).withDefaultRequestCode().navigation(this);
        }
        panelSelectScenePopup.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, Scene item) {
        int i;
        int i2;
        int i3;
        if (item.getSceneType() == 1) {
            i = R.drawable.shape_scene_cloud_bg;
            i2 = R.color.color_text_blue;
            i3 = R.string.type_cloud;
        } else if (item.getSceneType() == 2) {
            i = R.drawable.shape_scene_local_bg;
            i2 = R.color.color_text_red;
            i3 = R.string.type_local;
        } else {
            i = R.drawable.shape_scene_dali_bg;
            i2 = R.color.color_cgd_add;
            i3 = R.string.type_dali;
        }
        helper.setText(R.id.tv_name, item.getSceneName()).setImageResource(R.id.appCompatImageView14, SceneHelper.getSceneIcon(ActivityUtils.getTopActivity(), item.getIconPos())).setGone(R.id.iv_edit, false).setBackgroundRes(R.id.tv_type, i).setText(R.id.tv_type, i3).setTextColor(R.id.tv_type, getResources().getColor(i2));
    }

    @Override // com.ltech.smarthome.base.BaseSelectSceneActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(this.deviceId);
        if (deviceByDeviceId.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_6S) || deviceByDeviceId.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_12S)) {
            ((ActSelectBinding) this.mViewBinding).title.ivSearch.setVisibility(8);
        }
        handleData(Transformations.switchMap(this.request.data(), new Function1() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelRelateScene$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$startObserve$1;
                lambda$startObserve$1 = ActSuperPanelRelateScene.this.lambda$startObserve$1((Resource) obj);
                return lambda$startObserve$1;
            }
        }), new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelRelateScene$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSuperPanelRelateScene.this.lambda$startObserve$3((List) obj);
            }
        });
        this.showNoPermissionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelRelateScene$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSuperPanelRelateScene.this.lambda$startObserve$4((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$startObserve$1(Resource resource) {
        if (resource == null) {
            return ResourceEmptyLiveData.create();
        }
        this.allSceneList.clear();
        if (resource.getData() != null) {
            this.allSceneList.addAll((Collection) resource.getData());
        }
        Injection.limiter().reset(Injection.keyCreator().superPanelInfoKey(this.deviceId));
        return Injection.repo().device().getSuperPanelInfo(this, this.deviceId).data();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(List list) {
        ArrayList<Scene> arrayList = new ArrayList();
        if (list != null && list.size() > 0) {
            this.sceneIds.clear();
            SuperPanelInfo superPanelInfo = (SuperPanelInfo) list.get(0);
            List<SuperPanelInfo.SortInfo> sortSceneList = ((SuperPanelInfo) list.get(0)).getSortSceneList();
            this.sortSceneList = sortSceneList;
            Collections.sort(sortSceneList, new Comparator() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelRelateScene$$ExternalSyntheticLambda4
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ActSuperPanelRelateScene.lambda$startObserve$2((SuperPanelInfo.SortInfo) obj, (SuperPanelInfo.SortInfo) obj2);
                }
            });
            for (int i = 0; i < this.sortSceneList.size(); i++) {
                this.sceneIds.add(Long.valueOf(this.sortSceneList.get(i).getSortId()));
                arrayList.add(Injection.repo().scene().getSceneBySceneId(this.sortSceneList.get(i).getSortId()));
            }
            if (this.mBleSyncHelper != null && !arrayList.isEmpty()) {
                ArrayList arrayList2 = new ArrayList();
                for (Scene scene : arrayList) {
                    if (superPanelInfo.getScenes() != null) {
                        Iterator<DetailSuperPanelResponse.ScenesBean.RowsBean> it = superPanelInfo.getScenes().iterator();
                        while (true) {
                            if (it.hasNext()) {
                                DetailSuperPanelResponse.ScenesBean.RowsBean next = it.next();
                                if (next.getSceneid() == scene.getSceneId()) {
                                    arrayList2.add(new BleSyncHelper.BleSyncData(GsonUtils.toJson(next), 2, next.getScenename()));
                                    break;
                                }
                            }
                        }
                    }
                }
                this.mBleSyncHelper.setDownloadData(arrayList2, 2);
            }
        }
        if (arrayList.isEmpty()) {
            showEmpty();
        }
        setDataList(arrayList);
    }

    static /* synthetic */ int lambda$startObserve$2(SuperPanelInfo.SortInfo sortInfo, SuperPanelInfo.SortInfo sortInfo2) {
        return sortInfo.getSort() > sortInfo2.getSort() ? 1 : -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r1) {
        showNoPermissionDialog();
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            setResult(-1);
        }
    }
}