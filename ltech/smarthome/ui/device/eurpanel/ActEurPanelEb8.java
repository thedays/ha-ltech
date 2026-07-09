package com.ltech.smarthome.ui.device.eurpanel;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActRemoteBatteryBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb8;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.smart.message.ResponseMsg;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class ActEurPanelEb8 extends BaseControlActivity<ActRemoteBatteryBinding, ActEurPanelVM> {
    private static final int TYPE_SCENE_EB8 = 3;
    private int curPosition;
    private Device device;
    private long[] originalSceneIds;
    private MultipleItemRvAdapter<RelateInfoItem, BaseViewHolder> sceneAdapter;
    private long[] sceneIds;
    private int viewType;
    public SingleLiveEvent<Void> showNoPermissionDialogEvent = new SingleLiveEvent<>();
    private boolean editSceneMode = false;
    private List<Integer> diffPositionList = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.layout_content;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_remote_battery;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        super.showContent();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        if (((ActEurPanelVM) this.mViewModel).request != null) {
            ((ActEurPanelVM) this.mViewModel).request.retry();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        ((ActRemoteBatteryBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb8$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActEurPanelEb8.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        int id = view.getId();
        if (id == R.id.iv_doubt) {
            showPopup();
            return;
        }
        if (id != R.id.layout_edit_action) {
            return;
        }
        if (this.editSceneMode) {
            this.diffPositionList.clear();
            int i = 0;
            while (true) {
                long[] jArr = this.sceneIds;
                if (i >= jArr.length) {
                    break;
                }
                if (jArr[i] != this.originalSceneIds[i]) {
                    this.diffPositionList.add(Integer.valueOf(i));
                }
                i++;
            }
            if (this.diffPositionList.isEmpty()) {
                this.editSceneMode = false;
                inOrOutEditMode(false);
                return;
            } else {
                saveSceneData();
                return;
            }
        }
        this.editSceneMode = true;
        inOrOutEditMode(true);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActEurPanelVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActEurPanelVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActEurPanelVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ((ActEurPanelVM) this.mViewModel).groupList.setValue(Injection.repo().group().getGroupListByPlaceId(((ActEurPanelVM) this.mViewModel).placeId));
        ((ActEurPanelVM) this.mViewModel).deviceList.setValue(Injection.repo().device().getDeviceListByPlaceId(((ActEurPanelVM) this.mViewModel).placeId));
        ((ActEurPanelVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb8$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanelEb8.this.lambda$startObserve$1(obj);
            }
        });
        this.showNoPermissionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb8$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanelEb8.this.lambda$startObserve$2((Void) obj);
            }
        });
        ((ActEurPanelVM) this.mViewModel).batchRefreshScene.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb8$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanelEb8.this.lambda$startObserve$3((Void) obj);
            }
        });
        ((ActEurPanelVM) this.mViewModel).batchSceneFinish.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb8$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanelEb8.this.lambda$startObserve$4((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Object obj) {
        if (obj instanceof Device) {
            Device device = (Device) obj;
            this.device = device;
            setTitle(device.getDeviceName());
            ((ActEurPanelVM) this.mViewModel).lightType = ProductRepository.getLightColorType((Object) device);
            ((ActEurPanelVM) this.mViewModel).initRelateInfoList(device);
            String productId = device.getProductId();
            productId.hashCode();
            if (productId.equals(ProductId.ID_AS_PANEL_UB8) || productId.equals(ProductId.ID_EUR_PANEL_EB8)) {
                this.viewType = 3;
                ((ActRemoteBatteryBinding) this.mViewBinding).ivMode.setVisibility(8);
                ((ActRemoteBatteryBinding) this.mViewBinding).layoutPanel.setVisibility(8);
                if (ProductId.ID_EUR_PANEL_EB8.equals(device.getProductId())) {
                    ((ActRemoteBatteryBinding) this.mViewBinding).ivDoubt.setVisibility(8);
                    ((ActRemoteBatteryBinding) this.mViewBinding).ivDevicePic.setImageResource(R.mipmap.pic_add_eb8_bg);
                } else {
                    ((ActRemoteBatteryBinding) this.mViewBinding).ivDoubt.setVisibility(0);
                    ((ActRemoteBatteryBinding) this.mViewBinding).ivDevicePic.setImageResource(R.mipmap.pic_add_ub8);
                }
                ((ActRemoteBatteryBinding) this.mViewBinding).layoutZone.setVisibility(8);
                ((ActRemoteBatteryBinding) this.mViewBinding).tvScene.setText(R.string.bind_object);
                this.sceneIds = new long[8];
            }
            initRelatedSceneView();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r1) {
        showNoPermissionDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r4) {
        RelateInfoUtils.relatedSceneInfoList.set(this.curPosition, RelateInfoUtils.getRelateInfoItem(((ActEurPanelVM) this.mViewModel).relateInfoAssistant.getRelateSceneInfo(this.curPosition)));
        this.sceneAdapter.replaceData(RelateInfoUtils.relatedSceneInfoList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r4) {
        this.editSceneMode = false;
        inOrOutEditMode(false);
        ((ActEurPanelVM) this.mViewModel).uploadData(new IAction[0]);
        ReplaceHelper.instance().backupData(this.activity, this.device.getDeviceId());
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (this.editSceneMode) {
            return;
        }
        ((ActEurPanelVM) this.mViewModel).controlObject.setValue(Injection.repo().device().getDeviceById(((ActEurPanelVM) this.mViewModel).controlId));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        if (this.editSceneMode) {
            showNeedSaveDialog();
        } else {
            super.back();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        NavHelper.goSetting(((ActEurPanelVM) this.mViewModel).controlObject.getValue());
    }

    private void initRelatedSceneView() {
        if (this.sceneAdapter == null) {
            MultipleItemRvAdapter<RelateInfoItem, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<RelateInfoItem, BaseViewHolder>(new ArrayList()) { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb8.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
                public int getViewType(RelateInfoItem relateInfoItem) {
                    return ActEurPanelEb8.this.viewType;
                }

                /* renamed from: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb8$1$1, reason: invalid class name and collision with other inner class name */
                class C01211 extends BaseItemProvider<RelateInfoItem, BaseViewHolder> {
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_scene_eur;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 3;
                    }

                    C01211() {
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, RelateInfoItem item, final int position) {
                        String str;
                        if (!((ActEurPanelVM) ActEurPanelEb8.this.mViewModel).isSceneBind(item)) {
                            str = ActEurPanelEb8.this.getString(R.string.app_scene) + (position + 1);
                            helper.setVisible(R.id.tv_sub_name, true);
                            ActEurPanelEb8.this.sceneIds[position] = 0;
                        } else {
                            String str2 = item.infoName;
                            helper.setVisible(R.id.tv_sub_name, false);
                            ActEurPanelEb8.this.sceneIds[position] = item.relateInfo.objectId;
                            str = str2;
                        }
                        helper.setText(R.id.tv_name, str).setImageResource(R.id.appCompatImageView14, RelateInfoUtils.getRelateInfoIcon(((ActEurPanelVM) ActEurPanelEb8.this.mViewModel).relateInfoAssistant.getRelateSceneInfo(position))).setBackgroundRes(R.id.tv_type, R.drawable.shape_scene_bind_bg).setText(R.id.tv_type, String.valueOf(position + 1)).setTextColor(R.id.tv_type, ActEurPanelEb8.this.getResources().getColor(R.color.white)).setGone(R.id.iv_select, false).setVisible(R.id.iv_edit, ActEurPanelEb8.this.editSceneMode);
                        helper.getView(R.id.iv_edit).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb8$1$1$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                ActEurPanelEb8.AnonymousClass1.C01211.this.lambda$convert$0(position, view);
                            }
                        });
                    }

                    /* JADX INFO: Access modifiers changed from: private */
                    public /* synthetic */ void lambda$convert$0(int i, View view) {
                        ActEurPanelEb8.this.curPosition = i;
                        ActEurPanelEb8.this.bindScene(i);
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void onClick(BaseViewHolder helper, RelateInfoItem item, int position) {
                        if (item.infoName == null || item.type != 3) {
                            return;
                        }
                        ((ActEurPanelVM) ActEurPanelEb8.this.mViewModel).executeCurScene(ActEurPanelEb8.this.activity, position);
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public boolean onLongClick(BaseViewHolder helper, RelateInfoItem data, int position) {
                        if (!ActEurPanelEb8.this.editSceneMode) {
                            return true;
                        }
                        ActEurPanelEb8.this.curPosition = position;
                        ActEurPanelEb8.this.bindScene(position);
                        return true;
                    }
                }

                @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
                public void registerItemProvider() {
                    this.mProviderDelegate.registerProvider(new C01211());
                }
            };
            this.sceneAdapter = multipleItemRvAdapter;
            multipleItemRvAdapter.finishInitialize();
            this.sceneAdapter.bindToRecyclerView(((ActRemoteBatteryBinding) this.mViewBinding).rvScene);
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb8.2
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int position) {
                    if (ActEurPanelEb8.this.viewType == 3) {
                        return gridLayoutManager.getSpanCount() / 2;
                    }
                    return 1;
                }
            });
            ((ActRemoteBatteryBinding) this.mViewBinding).rvScene.setLayoutManager(gridLayoutManager);
            ((ActRemoteBatteryBinding) this.mViewBinding).rvScene.setHasFixedSize(true);
            ((ActRemoteBatteryBinding) this.mViewBinding).rvScene.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb8.3
                @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(ConvertUtils.dp2px(5.0f), 0, ConvertUtils.dp2px(5.0f), ConvertUtils.dp2px(8.0f));
                }
            });
            ((DefaultItemAnimator) ((ActRemoteBatteryBinding) this.mViewBinding).rvScene.getItemAnimator()).setSupportsChangeAnimations(false);
        }
        this.sceneAdapter.replaceData(RelateInfoUtils.relatedSceneInfoList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindScene(int position) {
        if (isOwnerOrManager()) {
            RelateInfoItem relateInfoItem = this.sceneAdapter.getData().get(position);
            if (this.editSceneMode) {
                ((ActEurPanelVM) this.mViewModel).batchSetScene = true;
            }
            ((ActEurPanelVM) this.mViewModel).showRelatedSceneDialog(position, ((ActEurPanelVM) this.mViewModel).isSceneBind(relateInfoItem));
            return;
        }
        this.showNoPermissionDialogEvent.call();
    }

    private void showPopup() {
        PopupWindow popupWindow = new PopupWindow(LayoutInflater.from(this).inflate(R.layout.view_eur_tip_scene, (ViewGroup) null), Utils.dip2px(this, 235.0f), -2, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(((ActRemoteBatteryBinding) this.mViewBinding).ivDoubt, Utils.dip2px(this, -150.0f), 10);
    }

    private void inOrOutEditMode(boolean editMode) {
        if (editMode) {
            long[] jArr = this.sceneIds;
            this.originalSceneIds = Arrays.copyOf(jArr, jArr.length);
        }
        ((ActRemoteBatteryBinding) this.mViewBinding).tvAction.setText(getString(editMode ? R.string.save_scene : R.string.edit_scene));
        ((ActRemoteBatteryBinding) this.mViewBinding).ivAction.setImageResource(editMode ? R.mipmap.ic_save_action : R.mipmap.ic_edit_action);
        this.sceneAdapter.notifyDataSetChanged();
    }

    public void saveSceneData() {
        ArrayList arrayList = new ArrayList();
        for (final int i = 0; i < this.diffPositionList.size(); i++) {
            final int intValue = this.diffPositionList.get(i).intValue();
            if (this.sceneIds[intValue] == 0) {
                final int unicastAddress = ((BleParam) this.device.getParam(BleParam.class)).getUnicastAddress();
                arrayList.add(Observable.create(new ObservableOnSubscribe() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb8$$ExternalSyntheticLambda1
                    @Override // io.reactivex.ObservableOnSubscribe
                    public final void subscribe(ObservableEmitter observableEmitter) {
                        ActEurPanelEb8.this.lambda$saveSceneData$6(unicastAddress, intValue, i, observableEmitter);
                    }
                }));
            } else {
                arrayList.add(Observable.create(new ObservableOnSubscribe() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb8$$ExternalSyntheticLambda2
                    @Override // io.reactivex.ObservableOnSubscribe
                    public final void subscribe(ObservableEmitter observableEmitter) {
                        ActEurPanelEb8.this.lambda$saveSceneData$8(intValue, i, observableEmitter);
                    }
                }));
            }
        }
        ((ActEurPanelVM) this.mViewModel).batchSaveScene(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveSceneData$6(int i, int i2, final int i3, final ObservableEmitter observableEmitter) throws Exception {
        CmdAssistant.getSettingCmdAssistant(this.device, new int[0]).unSubscribeInEurPanel(ActivityUtils.getTopActivity(), i, 1 << i2, 2, 2, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb8$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelEb8.this.lambda$saveSceneData$5(i3, observableEmitter, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveSceneData$5(int i, ObservableEmitter observableEmitter, Boolean bool) {
        if (bool.booleanValue()) {
            ReplaceHelper.instance().addBackupIndexData(UpdateBackDataRequest.SCENE_BIND, null, i + 1);
            observableEmitter.onNext(Integer.valueOf(i));
            observableEmitter.onComplete();
        } else {
            dismissLoadingDialog();
            observableEmitter.onError(new Throwable());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveSceneData$8(final int i, final int i2, final ObservableEmitter observableEmitter) throws Exception {
        int unicastAddress = ((BleParam) this.device.getParam(BleParam.class)).getUnicastAddress();
        final ArrayList arrayList = new ArrayList();
        arrayList.add(3);
        arrayList.add(Integer.valueOf(((ActEurPanelVM) this.mViewModel).relateInfoAssistant.getRelateSceneInfo(i).keyActionExtra));
        final int i3 = 65025;
        final int i4 = 1;
        CmdAssistant.getSettingCmdAssistant(this.device, new int[0]).subscribeInEurPanel(this.activity, 1 << i, unicastAddress, 65025, 2, 1, 2, 3, arrayList, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb8$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelEb8.this.lambda$saveSceneData$7(i, i3, i4, arrayList, observableEmitter, i2, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveSceneData$7(int i, int i2, int i3, List list, ObservableEmitter observableEmitter, int i4, ResponseMsg responseMsg) {
        if (responseMsg != null) {
            if (responseMsg.getStateCode() == 0) {
                ReplaceHelper.instance().addBackupIndexData(UpdateBackDataRequest.SCENE_BIND, CmdAssistant.getSettingCmdAssistant(this.device, new int[0]).subscribeInEurPanel(1 << i, i2, i3, 2, 3, list), i + 1);
            }
            observableEmitter.onNext(Integer.valueOf(i4));
            observableEmitter.onComplete();
            return;
        }
        dismissLoadingDialog();
        observableEmitter.onError(new Throwable());
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseControlActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 6004) {
            Scene sceneBySceneId = Injection.repo().scene().getSceneBySceneId(data.getLongExtra(Constants.SCENE_ID, 0L));
            RelatedInfoExtParam.RelateInfo RelatedSceneInfo = RelatedInfoExtParam.RelateInfo.RelatedSceneInfo(sceneBySceneId.getSceneId());
            RelatedSceneInfo.keyActionExtra = sceneBySceneId.getSceneNum();
            RelatedSceneInfo.action = sceneBySceneId.getSceneType() == 4 ? 2 : 3;
            ((ActEurPanelVM) this.mViewModel).relateInfoAssistant.setRelateSceneInfo(this.curPosition, RelatedSceneInfo);
            ((ActEurPanelVM) this.mViewModel).batchRefreshScene.call();
        }
    }
}