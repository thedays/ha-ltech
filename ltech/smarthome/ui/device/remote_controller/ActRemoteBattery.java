package com.ltech.smarthome.ui.device.remote_controller;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
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
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSelectDeviceAndGroup;
import com.ltech.smarthome.ui.device.eurpanel.ActEurPanelVM;
import com.ltech.smarthome.ui.device.eurpanel.EurHelper;
import com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.ui.mode.ActMode;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class ActRemoteBattery extends BaseControlActivity<ActRemoteBatteryBinding, ActEurPanelVM> {
    private static final int TYPE_SCENE = 1;
    private static final int TYPE_SCENE_B8 = 3;
    private static final int TYPE_SCENE_OR_FUNCTION = 2;
    private Object bindPanel;
    private BaseQuickAdapter<RelateInfoItem, BaseViewHolder> bindZoneAdapter;
    private int curPosition;
    private Device device;
    private long[] originalSceneIds;
    private BaseQuickAdapter<Long, BaseViewHolder> panelAdapter;
    private RelateInfoAssistant panelAssistant;
    private MultipleItemRvAdapter<RelateInfoItem, BaseViewHolder> sceneAdapter;
    private long[] sceneIds;
    private int viewType;
    private SingleLiveEvent<Void> showNoPermissionDialogEvent = new SingleLiveEvent<>();
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
        ((ActRemoteBatteryBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActRemoteBattery.this.lambda$initView$2((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(View view) {
        switch (view.getId()) {
            case R.id.add_panel /* 2131296341 */:
                MessageDialog.show(this, R.string.tips, R.string.bind_panel_tip).setOkButton(R.string.app_str_continue_to_bind, new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery$$ExternalSyntheticLambda12
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view2) {
                        boolean lambda$initView$0;
                        lambda$initView$0 = ActRemoteBattery.this.lambda$initView$0(baseDialog, view2);
                        return lambda$initView$0;
                    }
                }).setCancelButton(R.string.cancel);
                break;
            case R.id.iv_doubt /* 2131297028 */:
                ((ActEurPanelVM) this.mViewModel).showDoubtDialogEvent.call();
                break;
            case R.id.iv_mode /* 2131297137 */:
                NavUtils.Builder withInt = NavUtils.destination(ActMode.class).withInt(Constants.LIGHT_TYPE, ((ActEurPanelVM) this.mViewModel).lightType);
                Object obj = this.bindPanel;
                withInt.withLong(Constants.CONTROL_ID, obj == null ? ((ActEurPanelVM) this.mViewModel).controlId : ((Role) obj).getId()).navigation(this);
                break;
            case R.id.layout_edit_action /* 2131297456 */:
                if (this.editSceneMode) {
                    this.diffPositionList.clear();
                    int i = 0;
                    while (true) {
                        long[] jArr = this.sceneIds;
                        if (i >= jArr.length) {
                            if (this.diffPositionList.isEmpty()) {
                                this.editSceneMode = false;
                                inOrOutEditMode(false);
                                break;
                            } else {
                                RelateInfoUtils.showImageTipDialog(this.device, this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery$$ExternalSyntheticLambda13
                                    @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                                    public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                                        ActRemoteBattery.this.lambda$initView$1(imageTipDialog);
                                    }
                                });
                                break;
                            }
                        } else {
                            if (jArr[i] != this.originalSceneIds[i]) {
                                this.diffPositionList.add(Integer.valueOf(i));
                            }
                            i++;
                        }
                    }
                } else {
                    this.editSceneMode = true;
                    inOrOutEditMode(true);
                    break;
                }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initView$0(BaseDialog baseDialog, View view) {
        NavUtils.destination(ActEurPanelSelectDeviceAndGroup.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActEurPanelVM) this.mViewModel).controlId).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(((ActEurPanelVM) this.mViewModel).controlObject.getValue())).withBoolean(Constants.IS_BIND_EB, true).withDefaultRequestCode().navigation(this);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(ImageTipDialog imageTipDialog) {
        imageTipDialog.dismiss();
        saveSceneData();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActEurPanelVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActEurPanelVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActEurPanelVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ((ActEurPanelVM) this.mViewModel).groupList.setValue(Injection.repo().group().getGroupListByPlaceId(((ActEurPanelVM) this.mViewModel).placeId));
        ((ActEurPanelVM) this.mViewModel).deviceList.setValue(Injection.repo().device().getDeviceListByPlaceId(((ActEurPanelVM) this.mViewModel).placeId));
        ((ActEurPanelVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery$$ExternalSyntheticLambda19
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActRemoteBattery.this.lambda$startObserve$4(obj);
            }
        });
        ((ActEurPanelVM) this.mViewModel).showDoubtDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActRemoteBattery.this.lambda$startObserve$5((Void) obj);
            }
        });
        this.showNoPermissionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActRemoteBattery.this.lambda$startObserve$6((Void) obj);
            }
        });
        ((ActEurPanelVM) this.mViewModel).batchRefreshScene.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActRemoteBattery.this.lambda$startObserve$7((Void) obj);
            }
        });
        ((ActEurPanelVM) this.mViewModel).batchSceneFinish.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActRemoteBattery.this.lambda$startObserve$8((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Object obj) {
        if (obj instanceof Device) {
            Device device = (Device) obj;
            this.device = device;
            setTitle(device.getDeviceName());
            ((ActEurPanelVM) this.mViewModel).lightType = ProductRepository.getLightColorType((Object) device);
            ((ActEurPanelVM) this.mViewModel).initRelateInfoList(device);
            String productId = device.getProductId();
            productId.hashCode();
            switch (productId) {
                case "3503908278750336":
                    this.viewType = 1;
                    ((ActRemoteBatteryBinding) this.mViewBinding).ivDevicePic.setImageResource(R.mipmap.b2_pic);
                    break;
                case "3508084028410496":
                    this.viewType = 3;
                    ((ActRemoteBatteryBinding) this.mViewBinding).ivDevicePic.setImageResource(R.mipmap.b8_pic);
                    ((ActRemoteBatteryBinding) this.mViewBinding).ivDevicePic.getLayoutParams().height = SizeUtils.dp2px(280.0f);
                    ((ActRemoteBatteryBinding) this.mViewBinding).layoutZone.setVisibility(8);
                    ((ActRemoteBatteryBinding) this.mViewBinding).ivDoubt.setVisibility(8);
                    ((ActRemoteBatteryBinding) this.mViewBinding).layoutPanel.setVisibility(8);
                    this.sceneIds = new long[8];
                    break;
                case "3503908725640320":
                    this.viewType = 2;
                    ((ActRemoteBatteryBinding) this.mViewBinding).ivDevicePic.setImageResource(R.mipmap.b5_pic);
                    ((ActRemoteBatteryBinding) this.mViewBinding).tvScene.setText(R.string.scene_function);
                    break;
                case "3503907950824576":
                    this.viewType = 1;
                    ((ActRemoteBatteryBinding) this.mViewBinding).ivDevicePic.setImageResource(R.mipmap.b1_pic);
                    break;
            }
            ((ActRemoteBatteryBinding) this.mViewBinding).layoutEditAction.setVisibility(this.viewType == 3 ? 0 : 8);
            this.bindPanel = null;
            if (((ActEurPanelVM) this.mViewModel).relateInfoAssistant.getPanelId() != 0) {
                this.bindPanel = getRoleByRoleId(((ActEurPanelVM) this.mViewModel).relateInfoAssistant.getPanelId());
            }
            if (this.bindPanel != null) {
                ((ActRemoteBatteryBinding) this.mViewBinding).addPanel.setVisibility(8);
                ((ActRemoteBatteryBinding) this.mViewBinding).rvPanel.setVisibility(0);
                initBindPanelRv(((ActEurPanelVM) this.mViewModel).relateInfoAssistant.getPanelId());
                Object obj2 = this.bindPanel;
                if (obj2 instanceof Device) {
                    RelateInfoAssistant relateInfoAssistant = new RelateInfoAssistant((Device) this.bindPanel);
                    this.panelAssistant = relateInfoAssistant;
                    RelateInfoUtils.initEurPanelInfoList(relateInfoAssistant);
                } else if (obj2 instanceof Group) {
                    RelateInfoAssistant relateInfoAssistant2 = new RelateInfoAssistant((Group) this.bindPanel);
                    this.panelAssistant = relateInfoAssistant2;
                    RelateInfoUtils.initEurPanelInfoList(relateInfoAssistant2);
                }
                if (this.isFirst) {
                    CmdAssistant.getQueryCmdAssistant(this.bindPanel, new int[0]).queryPanelState(this);
                    this.isFirst = false;
                }
                MessageManager.getInstance().setEurPanelStatusCallBack(new MessageManager.EurPanelStatusCallBack() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery$$ExternalSyntheticLambda18
                    @Override // com.smart.message.MessageManager.EurPanelStatusCallBack
                    public final void onDataReceive(ResponseMsg responseMsg) {
                        ActRemoteBattery.this.lambda$startObserve$3(responseMsg);
                    }
                });
            } else {
                ((ActRemoteBatteryBinding) this.mViewBinding).addPanel.setVisibility(0);
                ((ActRemoteBatteryBinding) this.mViewBinding).rvPanel.setVisibility(8);
            }
            initBindZoneRv();
            initRelatedSceneView();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(ResponseMsg responseMsg) {
        int parseInt = Integer.parseInt(responseMsg.getResData().substring(6, 10), 16);
        Object obj = this.bindPanel;
        if (obj instanceof Group) {
            Group group = (Group) obj;
            if (parseInt == group.getGroupAddress() || isAddressInGroup(group, parseInt)) {
                ((ActEurPanelVM) this.mViewModel).refreshBindPanelData(this.bindPanel, this.panelAssistant, responseMsg);
                return;
            }
            return;
        }
        if (parseInt == ((BleParam) ((Device) obj).getParam(BleParam.class)).getUnicastAddress()) {
            ((ActEurPanelVM) this.mViewModel).refreshBindPanelData(this.bindPanel, this.panelAssistant, responseMsg);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Void r1) {
        showPopup();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(Void r1) {
        showNoPermissionDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(Void r4) {
        RelateInfoUtils.relatedSceneInfoList.set(this.curPosition, RelateInfoUtils.getRelateInfoItem(((ActEurPanelVM) this.mViewModel).relateInfoAssistant.getRelateSceneInfo(this.curPosition)));
        this.sceneAdapter.replaceData(RelateInfoUtils.relatedSceneInfoList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$8(Void r4) {
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
            MultipleItemRvAdapter<RelateInfoItem, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<RelateInfoItem, BaseViewHolder>(new ArrayList()) { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
                public int getViewType(RelateInfoItem relateInfoItem) {
                    return ActRemoteBattery.this.viewType;
                }

                /* renamed from: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery$1$1, reason: invalid class name and collision with other inner class name */
                class C01431 extends BaseItemProvider<RelateInfoItem, BaseViewHolder> {
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_bind_eur_scene;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 1;
                    }

                    C01431() {
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, RelateInfoItem item, final int position) {
                        String str;
                        if (!((ActEurPanelVM) ActRemoteBattery.this.mViewModel).isSceneBind(item)) {
                            str = ActRemoteBattery.this.getString(R.string.app_scene) + (position + 1);
                        } else {
                            str = item.infoName;
                        }
                        helper.setText(R.id.tv_name, str).setImageResource(R.id.iv_icon, RelateInfoUtils.getRelateInfoIcon(item.relateInfo));
                        helper.setGone(R.id.iv_edit, ActRemoteBattery.this.bindPanel == null);
                        helper.getView(R.id.iv_edit).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery$1$1$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                ActRemoteBattery.AnonymousClass1.C01431.this.lambda$convert$0(position, view);
                            }
                        });
                        if (position == 3) {
                            ActRemoteBattery.this.setPosition4Info(helper);
                        }
                    }

                    /* JADX INFO: Access modifiers changed from: private */
                    public /* synthetic */ void lambda$convert$0(int i, View view) {
                        ActRemoteBattery.this.bindScene(i);
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public boolean onLongClick(BaseViewHolder helper, RelateInfoItem data, int position) {
                        if (ActRemoteBattery.this.bindPanel != null) {
                            return false;
                        }
                        if (position == 3 && ProductId.ID_RC_B5.equals(((ActEurPanelVM) ActRemoteBattery.this.mViewModel).productId)) {
                            return false;
                        }
                        ActRemoteBattery.this.bindScene(position);
                        return true;
                    }
                }

                @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
                public void registerItemProvider() {
                    this.mProviderDelegate.registerProvider(new C01431());
                    this.mProviderDelegate.registerProvider(new AnonymousClass2());
                    this.mProviderDelegate.registerProvider(new AnonymousClass3());
                }

                /* renamed from: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery$1$2, reason: invalid class name */
                class AnonymousClass2 extends BaseItemProvider<RelateInfoItem, BaseViewHolder> {
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_bind_eur_scene;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 2;
                    }

                    AnonymousClass2() {
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, RelateInfoItem item, final int position) {
                        if (((ActEurPanelVM) ActRemoteBattery.this.mViewModel).isSceneBind(item)) {
                            helper.setText(R.id.tv_name, item.infoName).setText(R.id.tv_state, "").setImageResource(R.id.iv_icon, RelateInfoUtils.getRelateInfoIcon(((ActEurPanelVM) ActRemoteBattery.this.mViewModel).relateInfoAssistant.getRelateSceneInfo(position))).setGone(R.id.iv_edit, ActRemoteBattery.this.bindPanel == null);
                        } else {
                            helper.setText(R.id.tv_name, ActRemoteBattery.this.getResources().getStringArray(R.array.eur_rgb_text)[helper.getAdapterPosition()]);
                            helper.setImageResource(R.id.iv_icon, HelpUtils.getDrawableResourceArray(ActRemoteBattery.this.activity, R.array.eur_rgb_icon)[helper.getAdapterPosition()]);
                            helper.setText(R.id.tv_state, "");
                            if (helper.getAdapterPosition() == 3) {
                                ActRemoteBattery.this.setPosition4Info(helper);
                            }
                            if (ActRemoteBattery.this.bindPanel != null) {
                                helper.setText(R.id.tv_state, "").setGone(R.id.iv_edit, false);
                            }
                        }
                        helper.getView(R.id.iv_edit).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery$1$2$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                ActRemoteBattery.AnonymousClass1.AnonymousClass2.this.lambda$convert$0(position, view);
                            }
                        });
                    }

                    /* JADX INFO: Access modifiers changed from: private */
                    public /* synthetic */ void lambda$convert$0(int i, View view) {
                        ActRemoteBattery.this.bindScene(i);
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public boolean onLongClick(BaseViewHolder helper, RelateInfoItem data, int position) {
                        if (ActRemoteBattery.this.bindPanel != null) {
                            return false;
                        }
                        if (position == 3 && ProductId.ID_RC_B5.equals(((ActEurPanelVM) ActRemoteBattery.this.mViewModel).productId)) {
                            return false;
                        }
                        ActRemoteBattery.this.bindScene(position);
                        return true;
                    }
                }

                /* renamed from: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery$1$3, reason: invalid class name */
                class AnonymousClass3 extends BaseItemProvider<RelateInfoItem, BaseViewHolder> {
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_scene_eur;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 3;
                    }

                    AnonymousClass3() {
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, RelateInfoItem item, final int position) {
                        String str;
                        if (!((ActEurPanelVM) ActRemoteBattery.this.mViewModel).isSceneBind(item)) {
                            str = ActRemoteBattery.this.getString(R.string.app_scene) + (position + 1);
                            helper.setVisible(R.id.tv_sub_name, true);
                            ActRemoteBattery.this.sceneIds[position] = 0;
                        } else {
                            String str2 = item.infoName;
                            helper.setVisible(R.id.tv_sub_name, false);
                            ActRemoteBattery.this.sceneIds[position] = item.relateInfo.objectId;
                            str = str2;
                        }
                        helper.setText(R.id.tv_name, str).setImageResource(R.id.appCompatImageView14, RelateInfoUtils.getRelateInfoIcon(((ActEurPanelVM) ActRemoteBattery.this.mViewModel).relateInfoAssistant.getRelateSceneInfo(position))).setBackgroundRes(R.id.tv_type, R.drawable.shape_scene_bind_bg).setText(R.id.tv_type, String.valueOf(position + 1)).setTextColor(R.id.tv_type, ActRemoteBattery.this.getResources().getColor(R.color.white)).setGone(R.id.iv_select, false).setVisible(R.id.iv_edit, ActRemoteBattery.this.editSceneMode);
                        helper.getView(R.id.iv_edit).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery$1$3$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                ActRemoteBattery.AnonymousClass1.AnonymousClass3.this.lambda$convert$0(position, view);
                            }
                        });
                    }

                    /* JADX INFO: Access modifiers changed from: private */
                    public /* synthetic */ void lambda$convert$0(int i, View view) {
                        ActRemoteBattery.this.curPosition = i;
                        ActRemoteBattery.this.bindScene(i);
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public boolean onLongClick(BaseViewHolder helper, RelateInfoItem data, int position) {
                        if (!ActRemoteBattery.this.editSceneMode) {
                            return true;
                        }
                        ActRemoteBattery.this.curPosition = position;
                        ActRemoteBattery.this.bindScene(position);
                        return true;
                    }
                }
            };
            this.sceneAdapter = multipleItemRvAdapter;
            multipleItemRvAdapter.finishInitialize();
            this.sceneAdapter.bindToRecyclerView(((ActRemoteBatteryBinding) this.mViewBinding).rvScene);
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery.2
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int position) {
                    if (ActRemoteBattery.this.viewType == 3) {
                        return gridLayoutManager.getSpanCount() / 2;
                    }
                    return 1;
                }
            });
            ((ActRemoteBatteryBinding) this.mViewBinding).rvScene.setLayoutManager(gridLayoutManager);
            ((ActRemoteBatteryBinding) this.mViewBinding).rvScene.setHasFixedSize(true);
            ((ActRemoteBatteryBinding) this.mViewBinding).rvScene.addItemDecoration(((ActEurPanelVM) this.mViewModel).decoration);
            if (this.viewType == 3) {
                ((ActRemoteBatteryBinding) this.mViewBinding).rvScene.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery.3
                    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        super.getItemOffsets(outRect, view, parent, state);
                        outRect.set(ConvertUtils.dp2px(5.0f), 0, ConvertUtils.dp2px(5.0f), ConvertUtils.dp2px(8.0f));
                    }
                });
            }
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

    private void initBindZoneRv() {
        if (this.bindZoneAdapter == null) {
            BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<RelateInfoItem, BaseViewHolder>(R.layout.item_bind_zone, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery.4
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, RelateInfoItem item) {
                    String str;
                    if (item.relateInfo != null) {
                        if (TextUtils.isEmpty(item.relateInfo.name)) {
                            if (((ActEurPanelVM) ActRemoteBattery.this.mViewModel).isZoneBind(item)) {
                                str = item.infoName;
                            } else {
                                str = ActRemoteBattery.this.getResources().getStringArray(R.array.eur_panel_zone_key)[helper.getAdapterPosition()];
                            }
                        } else {
                            str = item.relateInfo.name;
                        }
                    } else {
                        str = ActRemoteBattery.this.getResources().getStringArray(R.array.eur_panel_zone_key)[helper.getAdapterPosition()];
                    }
                    helper.setText(R.id.tv_name, str);
                    helper.setGone(R.id.tv_state, false).setTextColor(R.id.tv_name, ActRemoteBattery.this.getResources().getColor(R.color.color_text_black));
                    if (ActRemoteBattery.this.bindPanel == null) {
                        helper.setGone(R.id.iv_edit, true).addOnClickListener(R.id.iv_edit);
                    } else {
                        helper.setGone(R.id.iv_edit, false);
                    }
                }
            };
            this.bindZoneAdapter = baseQuickAdapter;
            baseQuickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery$$ExternalSyntheticLambda7
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
                public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    ActRemoteBattery.this.lambda$initBindZoneRv$9(baseQuickAdapter2, view, i);
                }
            });
            this.bindZoneAdapter.bindToRecyclerView(((ActRemoteBatteryBinding) this.mViewBinding).rvMultiBind);
            ((ActRemoteBatteryBinding) this.mViewBinding).rvMultiBind.setLayoutManager(new GridLayoutManager(this, 4));
            ((ActRemoteBatteryBinding) this.mViewBinding).rvMultiBind.setHasFixedSize(true);
            ((ActRemoteBatteryBinding) this.mViewBinding).rvMultiBind.addItemDecoration(((ActEurPanelVM) this.mViewModel).decoration);
            ((DefaultItemAnimator) ((ActRemoteBatteryBinding) this.mViewBinding).rvMultiBind.getItemAnimator()).setSupportsChangeAnimations(false);
        }
        this.bindZoneAdapter.replaceData(RelateInfoUtils.relatedInfoList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initBindZoneRv$9(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        RelateInfoItem item = this.bindZoneAdapter.getItem(i);
        if (isOwnerOrManager()) {
            ((ActEurPanelVM) this.mViewModel).showZoneBindDialog(i, item, false);
        } else {
            this.showNoPermissionDialogEvent.call();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPosition4Info(BaseViewHolder helper) {
        if (ProductId.ID_RC_B5.equals(((ActEurPanelVM) this.mViewModel).productId)) {
            helper.setGone(R.id.iv_edit, false);
            int lightColorType = ProductRepository.getLightColorType(((ActEurPanelVM) this.mViewModel).controlObject.getValue());
            if (lightColorType == 3) {
                helper.setText(R.id.tv_name, getString(R.string.str_saturation)).setImageResource(R.id.iv_icon, R.mipmap.ic_rgb_on);
            } else if (lightColorType == 4) {
                helper.setText(R.id.tv_name, ExifInterface.LONGITUDE_WEST).setImageResource(R.id.iv_icon, R.mipmap.ic_w);
            } else {
                helper.setText(R.id.tv_name, "CW").setImageResource(R.id.iv_icon, R.mipmap.ic_cw);
            }
        }
    }

    private void initBindPanelRv(long panelId) {
        if (this.panelAdapter == null) {
            ((ActRemoteBatteryBinding) this.mViewBinding).rvPanel.setSwipeMenuCreator(new SwipeMenuCreator() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery$$ExternalSyntheticLambda14
                @Override // com.yanzhenjie.recyclerview.SwipeMenuCreator
                public final void onCreateMenu(SwipeMenu swipeMenu, SwipeMenu swipeMenu2, int i) {
                    ActRemoteBattery.this.lambda$initBindPanelRv$10(swipeMenu, swipeMenu2, i);
                }
            });
            ((ActRemoteBatteryBinding) this.mViewBinding).rvPanel.setOnItemMenuClickListener(new OnItemMenuClickListener() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery$$ExternalSyntheticLambda15
                @Override // com.yanzhenjie.recyclerview.OnItemMenuClickListener
                public final void onItemClick(SwipeMenuBridge swipeMenuBridge, int i) {
                    ActRemoteBattery.this.lambda$initBindPanelRv$13(swipeMenuBridge, i);
                }
            });
            BaseQuickAdapter<Long, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Long, BaseViewHolder>(R.layout.item_panel_bind, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery.5
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, Long item) {
                    Role roleByRoleId = ActRemoteBattery.this.getRoleByRoleId(item.longValue());
                    if (roleByRoleId instanceof Device) {
                        Device device = (Device) roleByRoleId;
                        helper.setText(R.id.tv_name, device.getDeviceName()).setText(R.id.tv_place_info, ActRemoteBattery.this.getPlaceInfo(device));
                    } else if (roleByRoleId instanceof Group) {
                        Group group = (Group) roleByRoleId;
                        helper.setText(R.id.tv_name, group.getGroupName()).setText(R.id.tv_place_info, ActRemoteBattery.this.getPlaceInfo(group));
                    }
                }
            };
            this.panelAdapter = baseQuickAdapter;
            baseQuickAdapter.bindToRecyclerView(((ActRemoteBatteryBinding) this.mViewBinding).rvPanel);
            ((ActRemoteBatteryBinding) this.mViewBinding).rvPanel.setLayoutManager(new LinearLayoutManager(this));
            ((ActRemoteBatteryBinding) this.mViewBinding).rvPanel.setHasFixedSize(true);
        }
        this.panelAdapter.replaceData(Collections.singletonList(Long.valueOf(panelId)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initBindPanelRv$10(SwipeMenu swipeMenu, SwipeMenu swipeMenu2, int i) {
        swipeMenu2.addMenuItem(new SwipeMenuItem(this).setTextSize(14).setWidth(SizeUtils.dp2px(100.0f)).setHeight(-1).setText(R.string.reset_relate).setTextColor(-1).setBackgroundColorResource(R.color.color_text_red));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initBindPanelRv$13(SwipeMenuBridge swipeMenuBridge, int i) {
        swipeMenuBridge.closeMenu();
        if (-1 == swipeMenuBridge.getDirection()) {
            MessageDialog.show(this, getString(R.string.unbind_eb_tip), "").setOkButton(R.string.confirm, new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery$$ExternalSyntheticLambda16
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$initBindPanelRv$12;
                    lambda$initBindPanelRv$12 = ActRemoteBattery.this.lambda$initBindPanelRv$12(baseDialog, view);
                    return lambda$initBindPanelRv$12;
                }
            }).setCancelButton(R.string.cancel);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initBindPanelRv$12(BaseDialog baseDialog, View view) {
        RelateInfoUtils.showImageTipDialog((Device) ((ActEurPanelVM) this.mViewModel).controlObject.getValue(), this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery$$ExternalSyntheticLambda9
            @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
            public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                ActRemoteBattery.this.lambda$initBindPanelRv$11(imageTipDialog);
            }
        });
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initBindPanelRv$11(ImageTipDialog imageTipDialog) {
        imageTipDialog.dismiss();
        unbindPanel();
    }

    private void unbindPanel() {
        showLoadingDialog(getString(R.string.unsubscribing));
        CmdAssistant.getSettingCmdAssistant(((ActEurPanelVM) this.mViewModel).controlObject.getValue(), new int[0]).unBindEurPanel(this, new IAction() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery$$ExternalSyntheticLambda17
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActRemoteBattery.this.lambda$unbindPanel$15((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unbindPanel$15(Boolean bool) {
        if (bool.booleanValue()) {
            Device device = (Device) ((ActEurPanelVM) this.mViewModel).controlObject.getValue();
            EurHelper.relateInfoAssistant = new RelateInfoAssistant(device);
            EurHelper.setRcbType(device, new IAction() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery$$ExternalSyntheticLambda8
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActRemoteBattery.this.lambda$unbindPanel$14((Boolean) obj);
                }
            });
        } else {
            dismissLoadingDialog();
            ActivityUtils.getTopActivity().runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery.7
                @Override // java.lang.Runnable
                public void run() {
                    ViewHelpUtil.showUnBindFailDialog(ActRemoteBattery.this.activity);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unbindPanel$14(Boolean bool) {
        if (bool.booleanValue()) {
            ((ActEurPanelVM) this.mViewModel).relateInfoAssistant.getExtParam().getRelateParamMap().clear();
            ((ActEurPanelVM) this.mViewModel).relateInfoAssistant.setPanelId(0L);
            ((ActEurPanelVM) this.mViewModel).uploadData(new IAction[0]);
        } else {
            dismissLoadingDialog();
            ActivityUtils.getTopActivity().runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery.6
                @Override // java.lang.Runnable
                public void run() {
                    ViewHelpUtil.showUnBindFailDialog(ActRemoteBattery.this.activity);
                }
            });
        }
    }

    private void showPopup() {
        PopupWindow popupWindow = new PopupWindow(LayoutInflater.from(this).inflate(R.layout.view_remote_tip, (ViewGroup) null), Utils.dip2px(this, 230.0f), -2, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(((ActRemoteBatteryBinding) this.mViewBinding).ivDoubt, Utils.dip2px(this, -215.0f), 10);
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
                arrayList.add(Observable.create(new ObservableOnSubscribe() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery$$ExternalSyntheticLambda5
                    @Override // io.reactivex.ObservableOnSubscribe
                    public final void subscribe(ObservableEmitter observableEmitter) {
                        ActRemoteBattery.this.lambda$saveSceneData$17(unicastAddress, intValue, i, observableEmitter);
                    }
                }));
            } else {
                arrayList.add(Observable.create(new ObservableOnSubscribe() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery$$ExternalSyntheticLambda6
                    @Override // io.reactivex.ObservableOnSubscribe
                    public final void subscribe(ObservableEmitter observableEmitter) {
                        ActRemoteBattery.this.lambda$saveSceneData$19(intValue, i, observableEmitter);
                    }
                }));
            }
        }
        ((ActEurPanelVM) this.mViewModel).batchSaveScene(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveSceneData$17(int i, int i2, final int i3, final ObservableEmitter observableEmitter) throws Exception {
        CmdAssistant.getSettingCmdAssistant(this.device, new int[0]).unSubscribeInEurPanel(ActivityUtils.getTopActivity(), i, 1 << i2, 2, 2, new IAction() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActRemoteBattery.this.lambda$saveSceneData$16(i3, observableEmitter, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveSceneData$16(int i, ObservableEmitter observableEmitter, Boolean bool) {
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
    public /* synthetic */ void lambda$saveSceneData$19(final int i, final int i2, final ObservableEmitter observableEmitter) throws Exception {
        int unicastAddress = ((BleParam) this.device.getParam(BleParam.class)).getUnicastAddress();
        final ArrayList arrayList = new ArrayList();
        arrayList.add(3);
        arrayList.add(Integer.valueOf(((ActEurPanelVM) this.mViewModel).relateInfoAssistant.getRelateSceneInfo(i).keyActionExtra));
        final int i3 = 65025;
        final int i4 = 1;
        CmdAssistant.getSettingCmdAssistant(this.device, new int[0]).subscribeInEurPanel(this.activity, 1 << i, unicastAddress, 65025, 2, 1, 2, 3, arrayList, new IAction() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery$$ExternalSyntheticLambda10
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActRemoteBattery.this.lambda$saveSceneData$18(i, i3, i4, arrayList, observableEmitter, i2, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveSceneData$18(int i, int i2, int i3, List list, ObservableEmitter observableEmitter, int i4, ResponseMsg responseMsg) {
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