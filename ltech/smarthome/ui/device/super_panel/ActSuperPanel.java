package com.ltech.smarthome.ui.device.super_panel;

import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import com.aispeech.dca.DcaSdk;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.iflytek.home.sdk.IFlyHome;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.databinding.ActSuperPanelBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.device_param.SuperPanelExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.IFlyRepository;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.api.ApiConstants;
import com.ltech.smarthome.ui.config.ActMeshScan;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicHome;
import com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicList;
import com.ltech.smarthome.ui.device.super_panel.sp485.ActSp485;
import com.ltech.smarthome.ui.device.super_panel.talk.ActVoiceTalkMain;
import com.ltech.smarthome.ui.ifly.DcaWebViewActivity;
import com.ltech.smarthome.ui.ifly.WebViewActivity;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.utils.CodeLibraryUtil;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.NameRepository;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.rich.czlylibary.sdk.MiguCzlySDK;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.util.TextInfo;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.product_agreement.bean.SwitchPanelState;
import com.smart.product_agreement.parser.IPanelParser;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSuperPanel extends BaseControlActivity<ActSuperPanelBinding, ActSuperPanelVM> {
    private MultipleItemRvAdapter<FunItem, BaseViewHolder> mAdapter;
    public SingleLiveEvent<Void> showNoPermissionDialogEvent = new SingleLiveEvent<>();
    protected boolean[] selectArray = new boolean[4];
    private List<FunItem> list = new ArrayList();

    static /* synthetic */ boolean lambda$showVersionNotSupportDialog$13(BaseDialog baseDialog, View view) {
        return false;
    }

    static /* synthetic */ void lambda$startObserve$18(ResponseMsg responseMsg) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_super_panel;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        final String str;
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        ((ActSuperPanelVM) this.mViewModel).controlDevice = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
        final Device device = ((ActSuperPanelVM) this.mViewModel).controlDevice;
        if (device != null) {
            String productId = device.getProductId();
            productId.hashCode();
            switch (productId) {
                case "123050811340901":
                    ((ActSuperPanelBinding) this.mViewBinding).ivSuperPanel.setImageResource(LanguageUtils.isChinese(this) ? R.mipmap.bg_sqpro_cn : R.mipmap.bg_sqpro_en);
                    ((ActSuperPanelBinding) this.mViewBinding).tvSuperPanelTip.setText(R.string.super_panel_6S_pro);
                    ((ActSuperPanelBinding) this.mViewBinding).tvSuperPanelTip2.setText(R.string.super_panel_6S_pro_tip);
                    ((ActSuperPanelBinding) this.mViewBinding).tvSuperPanelTip2.setVisibility(0);
                    break;
                case "123050811353501":
                    ((ActSuperPanelBinding) this.mViewBinding).ivSuperPanel.setImageResource(LanguageUtils.isChinese(this) ? R.mipmap.pic_banner_super_panel_12s_cn : R.mipmap.pic_banner_super_panel_12s_en);
                    ((ActSuperPanelBinding) this.mViewBinding).tvSuperPanelTip.setText(R.string.super_panel_12S_tip);
                    ((ActSuperPanelBinding) this.mViewBinding).tvSuperPanelTip2.setText(R.string.super_panel_12S_tip_2);
                    ((ActSuperPanelBinding) this.mViewBinding).tvSuperPanelTip2.setVisibility(0);
                    break;
                case "3683388245101248":
                    ((ActSuperPanelBinding) this.mViewBinding).ivSuperPanel.setImageResource(R.mipmap.pic_banner_super_panel_g4_bg);
                    ((ActSuperPanelBinding) this.mViewBinding).tvSuperPanelTip.setText(R.string.super_panel_g4_max_tip);
                    ((ActSuperPanelBinding) this.mViewBinding).tvSuperPanelTip2.setText(R.string.super_panel_g4_max_tip_2);
                    ((ActSuperPanelBinding) this.mViewBinding).tvSuperPanelTip2.setVisibility(0);
                    break;
                case "122042815485901":
                    ((ActSuperPanelBinding) this.mViewBinding).ivSuperPanel.setImageResource(R.mipmap.pic_banner_super_panel_6s);
                    ((ActSuperPanelBinding) this.mViewBinding).tvSuperPanelTip.setText(R.string.super_panel_6S_tip);
                    ((ActSuperPanelBinding) this.mViewBinding).tvSuperPanelTip2.setText(R.string.super_panel_6S_tip_2);
                    ((ActSuperPanelBinding) this.mViewBinding).tvSuperPanelTip2.setVisibility(0);
                    break;
                case "122080911090801":
                case "121052512023201":
                    ((ActSuperPanelBinding) this.mViewBinding).ivSuperPanel.setImageResource(LanguageUtils.isChinese(this) ? R.mipmap.pic_banner_super_panel_mini_cn : R.mipmap.pic_banner_super_panel_mini_en);
                    ((ActSuperPanelBinding) this.mViewBinding).tvSuperPanelTip.setText(device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI) ? R.string.super_panel_mini_tip : R.string.super_panel_mini_4S_tip);
                    ((ActSuperPanelBinding) this.mViewBinding).tvSuperPanelTip2.setText(R.string.super_panel_mini_tip_2);
                    ((ActSuperPanelBinding) this.mViewBinding).tvSuperPanelTip2.setVisibility(0);
                    break;
                default:
                    ((ActSuperPanelBinding) this.mViewBinding).ivSuperPanel.setImageResource(R.mipmap.pic_banner_super_panel);
                    ((ActSuperPanelBinding) this.mViewBinding).tvSuperPanelTip.setText(R.string.super_panel_tip);
                    ((ActSuperPanelBinding) this.mViewBinding).tvSuperPanelTip2.setVisibility(8);
                    break;
            }
            if (device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_4S)) {
                str = Constants.DCA_PRODUCT_ID_MINI;
            } else if (device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_6S)) {
                str = Constants.DCA_PRODUCT_ID_6S;
            } else if (device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO)) {
                str = Constants.DCA_PRODUCT_ID_PRO;
            } else if (device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_12S)) {
                str = Constants.DCA_PRODUCT_ID_12S;
            } else if (!device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                str = "";
            } else {
                str = Constants.DCA_PRODUCT_ID_G4_MAX;
            }
            this.list = new ArrayList();
            final boolean isDcaDevice = isDcaDevice(device);
            if (isDcaDevice) {
                this.list.add(FunItem.title(new GoItem().setGoRes(0).setMainText(getString(R.string.switch_state))));
                String[] keyNameArray = getKeyNameArray(device);
                if (device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                    int i = 0;
                    for (int i2 = 4; i < i2; i2 = 4) {
                        this.list.add(FunItem.key(new GoItem().setMainText(keyNameArray[i]).setImageRes(R.mipmap.sp_ic_switch_off)));
                        i++;
                    }
                } else {
                    int i3 = 0;
                    for (int i4 = 2; i3 < i4; i4 = 2) {
                        this.list.add(FunItem.key(new GoItem().setMainText(keyNameArray[i3]).setImageRes(R.mipmap.sp_ic_switch_off)));
                        i3++;
                    }
                }
                setNewParam(device);
            }
            this.list.add(FunItem.title(new GoItem().setGoRes(0).setMainText(getString(R.string.panel_setting))));
            if (isSupportInfrared(device)) {
                this.list.add(FunItem.content(new GoItem().setMainText(getString(R.string.infrared_appliance)).setSubText(getString(R.string.infrared_appliance_tip)).setImageRes(R.mipmap.ic_super_panel_ir).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanel$$ExternalSyntheticLambda15
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSuperPanel.this.lambda$initView$0();
                    }
                }))));
            }
            this.list.add(FunItem.content(new GoItem().setMainText(getString(R.string.set_device)).setSubText(getString(R.string.set_device_tip)).setImageRes(R.mipmap.ic_super_panel_device).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanel$$ExternalSyntheticLambda18
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActSuperPanel.this.lambda$initView$1();
                }
            }))));
            this.list.add(FunItem.content(new GoItem().setMainText(getString(R.string.set_scene)).setSubText(getString(R.string.set_scene_tip)).setImageRes(R.mipmap.ic_super_panel_scene).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanel$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActSuperPanel.this.lambda$initView$2();
                }
            }))));
            if (!device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                this.list.add(FunItem.content(new GoItem().setMainText(getString(R.string.family_album)).setSubText(getString(R.string.family_album_tip)).setImageRes(R.mipmap.ic_super_panel_album).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanel$$ExternalSyntheticLambda2
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSuperPanel.this.lambda$initView$3(device);
                    }
                }))));
            }
            if (device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_12S) && SuperPanelVersionHelper.canAdd485(device)) {
                this.list.add(FunItem.content(new GoItem().setMainText(getString(R.string.app_str_sp_485)).setSubText(getString(R.string.app_str_sp_485_tip)).setImageRes(R.mipmap.panel_icon_485).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanel$$ExternalSyntheticLambda3
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSuperPanel.this.lambda$initView$4(device);
                    }
                }))));
            }
            if (device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO)) {
                this.list.add(FunItem.content(new GoItem().setMainText(getString(R.string.set_key)).setSubText(getString(R.string.set_key_tip)).setImageRes(R.mipmap.ic_super_panel_key).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanel$$ExternalSyntheticLambda4
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSuperPanel.this.lambda$initView$5(device);
                    }
                }))));
            } else if (device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                this.list.add(FunItem.content(new GoItem().setMainText(getString(R.string.set_key)).setSubText(getString(R.string.set_key_tip)).setImageRes(R.mipmap.ic_super_panel_key).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanel$$ExternalSyntheticLambda5
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSuperPanel.this.lambda$initView$6(device);
                    }
                }))));
            } else if (device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_6S)) {
                this.list.add(FunItem.content(new GoItem().setMainText(getString(R.string.set_key)).setSubText(getString(R.string.set_key_tip)).setImageRes(R.mipmap.ic_super_panel_key).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanel$$ExternalSyntheticLambda6
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSuperPanel.this.lambda$initView$7(device);
                    }
                }))));
            }
            this.list.add(FunItem.content(new GoItem().setMainText(getString(R.string.set_bluetooth)).setSubText(getString(R.string.set_bluetooth_tip)).setImageRes(R.mipmap.ic_super_panel_bluetooth).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanel$$ExternalSyntheticLambda7
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActSuperPanel.this.lambda$initView$8();
                }
            }))));
            this.list.add(FunItem.title(new GoItem().setGoRes(0).setMainText(getString(R.string.application_service))));
            if (device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_12S) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_6S) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_4S) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                this.list.add(FunItem.content(new GoItem().setMainText(getString(R.string.voice_call_str)).setSubText(getString(R.string.voice_talk_tip)).setImageRes(R.mipmap.ic_panel_intercom).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanel$$ExternalSyntheticLambda8
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSuperPanel.this.lambda$initView$9(device);
                    }
                }))));
            }
            if (ApiConstants.isChinaNode()) {
                this.list.add(FunItem.content(new GoItem().setMainText(getString(R.string.fun_voice)).setSubText(getString(R.string.fun_voice_tip)).setImageRes(R.mipmap.ic_super_panel_voice).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanel$$ExternalSyntheticLambda16
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSuperPanel.this.lambda$initView$10(isDcaDevice, device, str);
                    }
                }))));
            }
            if (!ProductId.ID_ANDROID_SUPER_PANEL_G4MAX.equals(device.getProductId())) {
                this.list.add(FunItem.content(new GoItem().setMainText(getString(R.string.fun_music)).setSubText(getString(R.string.fun_music_tip)).setImageRes(R.mipmap.ic_super_panel_music).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanel$$ExternalSyntheticLambda17
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSuperPanel.this.lambda$initView$11(isDcaDevice, device, str);
                    }
                }))));
            }
        }
        MultipleItemRvAdapter<FunItem, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<FunItem, BaseViewHolder>(this.list) { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanel.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(FunItem content) {
                return content.type;
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new BaseItemProvider<FunItem, BaseViewHolder>(this) { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanel.1.1
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_go_1;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 1;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, FunItem content, int position) {
                        helper.setText(R.id.tv_main, content.goItem.getMainText()).setBackgroundRes(R.id.layout_item_bg, R.color.transparent);
                        helper.setImageResource(R.id.iv_go, 0);
                        ((AppCompatTextView) helper.getView(R.id.tv_main)).getPaint().setFakeBoldText(true);
                        ((AppCompatTextView) helper.getView(R.id.tv_main)).setTextSize(16.0f);
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<FunItem, BaseViewHolder>(this) { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanel.1.2
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_super_panel_fun;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 2;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, FunItem content, int position) {
                        helper.setText(R.id.tv_fun_name, content.goItem.getMainText()).setText(R.id.tv_fun_tip, content.goItem.getSubText()).setImageResource(R.id.iv_icon, content.goItem.getImageRes());
                        ((AppCompatTextView) helper.getView(R.id.tv_fun_name)).getPaint().setFakeBoldText(true);
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void onClick(BaseViewHolder helper, FunItem data, int position) {
                        data.goItem.getAction().execute();
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<FunItem, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanel.1.3
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_super_panel_switch;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 3;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, FunItem content, int position) {
                        helper.setText(R.id.tv_name, content.goItem.getMainText()).setImageResource(R.id.iv_icon, ActSuperPanel.this.selectArray[position - 1] ? R.mipmap.sp_ic_switch_on : R.mipmap.sp_ic_switch_off);
                        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void onClick(BaseViewHolder helper, FunItem data, int position) {
                        int i5 = position - 1;
                        ActSuperPanel.this.selectArray[i5] = !ActSuperPanel.this.selectArray[i5];
                        notifyItemChanged(position);
                        ((ActSuperPanelVM) ActSuperPanel.this.mViewModel).sendSingleOnOff(i5, ActSuperPanel.this.selectArray[i5]);
                    }
                });
            }
        };
        this.mAdapter = multipleItemRvAdapter;
        multipleItemRvAdapter.finishInitialize();
        this.mAdapter.bindToRecyclerView(((ActSuperPanelBinding) this.mViewBinding).rvContent);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanel.2
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int position) {
                int i5 = ((FunItem) ActSuperPanel.this.mAdapter.getData().get(position)).type;
                if (i5 == 2) {
                    return gridLayoutManager.getSpanCount() / 3;
                }
                if (i5 == 3) {
                    return gridLayoutManager.getSpanCount() / 2;
                }
                return gridLayoutManager.getSpanCount();
            }
        });
        ((ActSuperPanelBinding) this.mViewBinding).rvContent.setLayoutManager(gridLayoutManager);
        ((ActSuperPanelBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        if (device != null) {
            if (device.getParam() == null || device.getParam(BleParam.class) == null || ((BleParam) device.getParam(BleParam.class)).getUnicastAddress() == 0) {
                showAddMeshDialog(device.getDeviceId());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0() {
        if (getCurrentPlace().isOwner() || getCurrentPlace().isManager()) {
            if (((ActSuperPanelVM) this.mViewModel).controlDevice != null) {
                addPlaceIdDeviceId(ActSuperPanelIrRemoteControl.class).navigation(this);
                return;
            }
            return;
        }
        this.showNoPermissionDialogEvent.call();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1() {
        addPlaceIdDeviceId(ActSuperPanelRelateDeviceAndGroup.class).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2() {
        addPlaceIdDeviceId(ActSuperPanelRelateScene.class).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3(Device device) {
        NavUtils.destination(ActSuperPanelAlbum.class).withString(Constants.PRODUCT_ID, device.getProductId()).withLong("device_id", device.getDeviceId()).withString(Constants.MAC_ADDRESS, device.getMaccode()).withBoolean(Constants.DEVICE_IS_ONLINE, device.isOnline()).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$4(Device device) {
        NavUtils.destination(ActSp485.class).withString(Constants.PRODUCT_ID, device.getProductId()).withLong(Constants.CONTROL_ID, device.getId()).withString(Constants.MAC_ADDRESS, device.getMaccode()).withBoolean(Constants.DEVICE_IS_ONLINE, device.isOnline()).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$5(Device device) {
        addPlaceIdDeviceId(ActSuperPanelKeySet.class).withString(Constants.PRODUCT_ID, device.getProductId()).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$6(Device device) {
        addPlaceIdDeviceId(ActG4MaxKeySet.class).withString(Constants.PRODUCT_ID, device.getProductId()).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$7(Device device) {
        addPlaceIdDeviceId(ActSuperPanelKeySet6S.class).withString(Constants.PRODUCT_ID, device.getProductId()).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$8() {
        if (getCurrentPlace().isOwner() || getCurrentPlace().isManager()) {
            if (((ActSuperPanelVM) this.mViewModel).controlDevice != null) {
                NavUtils.destination(ActMeshScan.class).withLong(Constants.CONTROL_ID, ((ActSuperPanelVM) this.mViewModel).controlDevice.getId()).navigation(this);
                return;
            }
            return;
        }
        this.showNoPermissionDialogEvent.call();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$9(Device device) {
        NavUtils.destination(ActVoiceTalkMain.class).withLong("device_id", device.getDeviceId()).withString(Constants.MAC_ADDRESS, device.getMaccode()).withBoolean(Constants.DEVICE_IS_ONLINE, device.isOnline()).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$10(boolean z, Device device, String str) {
        if (!z) {
            NavUtils.destination(WebViewActivity.class).withString("target", IFlyHome.SKILLS).withString("device_id", "000d9d32-b027-410d-981d-fead0b8cbddd." + ((ActSuperPanelVM) this.mViewModel).controlDevice.getDevicesn()).navigation(this);
            return;
        }
        DcaSdk.setCurrentDeviceId(device.getDevicesn());
        NavUtils.destination(DcaWebViewActivity.class).withLong(Constants.CONTROL_ID, device.getId()).withString(Constants.DCA_ID, str).withInt(DcaWebViewActivity.EXTRA_WEB_TYPE, 0).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$11(boolean z, Device device, String str) {
        if (!z) {
            new IFlyRepository().getDeviceDetail("000d9d32-b027-410d-981d-fead0b8cbddd." + ((ActSuperPanelVM) this.mViewModel).controlDevice.getDevicesn());
            NavUtils.destination(WebViewActivity.class).withString(WebViewActivity.EXTRA_CUSTOM_URL, "https://homev2.iflyos.cn/device/000d9d32-b027-410d-981d-fead0b8cbddd." + ((ActSuperPanelVM) this.mViewModel).controlDevice.getDevicesn() + Constants.IFLY_SUF).navigation(this);
            return;
        }
        DcaSdk.setCurrentDeviceId(device.getDevicesn());
        MiguCzlySDK.getInstance().setSmartDeviceId(device.getDevicesn());
        MiguCzlySDK.getInstance().setUid(device.getDevicesn());
        if (((ActSuperPanelVM) this.mViewModel).isSupportNewMusic()) {
            if (ApiConstants.isChinaNode()) {
                NavUtils.destination(ActDcaMusicHome.class).withLong(Constants.CONTROL_ID, device.getId()).withString(Constants.MAC_ADDRESS, device.getMaccode()).withBoolean(Constants.DEVICE_IS_ONLINE, device.isOnline()).withString(Constants.DCA_ID, str).navigation(this);
                return;
            } else {
                NavUtils.destination(ActDcaMusicList.class).withString(Constants.MUSIC_AREA, getString(R.string.music_local_short)).withLong(Constants.CONTROL_ID, ((ActSuperPanelVM) this.mViewModel).controlId).withString(Constants.MAC_ADDRESS, device.getMaccode()).navigation(this);
                return;
            }
        }
        showVersionNotSupportDialog();
    }

    private /* synthetic */ void lambda$initView$12(boolean z, Device device, String str) {
        if (!z) {
            NavUtils.destination(WebViewActivity.class).withString("target", IFlyHome.CONTROLLED_DEVICES).navigation(this);
        } else {
            DcaSdk.setCurrentDeviceId(device.getDevicesn());
            NavUtils.destination(DcaWebViewActivity.class).withLong(Constants.CONTROL_ID, device.getId()).withString(Constants.DCA_ID, str).withInt(DcaWebViewActivity.EXTRA_WEB_TYPE, 2).navigation(this);
        }
    }

    private void showVersionNotSupportDialog() {
        TextInfo textInfo = new TextInfo();
        textInfo.setGravity(GravityCompat.START);
        MessageDialog.show(this, "", getString(R.string.app_music_need_update_device)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanel$$ExternalSyntheticLambda9
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return ActSuperPanel.lambda$showVersionNotSupportDialog$13(baseDialog, view);
            }
        }).setMessageTextInfo(textInfo);
    }

    private String[] getKeyNameArray(Device device) {
        String[] smartPanelS4KeyName = NameRepository.getSmartPanelS4KeyName(this);
        if (device.getExtParam() != null) {
            SuperPanelExtParam superPanelExtParam = (SuperPanelExtParam) device.getExtParam(SuperPanelExtParam.class);
            if (superPanelExtParam != null && !TextUtils.isEmpty(superPanelExtParam.getSwitch1_name())) {
                smartPanelS4KeyName[0] = superPanelExtParam.getSwitch1_name();
            }
            if (superPanelExtParam != null && !TextUtils.isEmpty(superPanelExtParam.getSwitch2_name())) {
                smartPanelS4KeyName[1] = superPanelExtParam.getSwitch2_name();
            }
            if (superPanelExtParam != null && !TextUtils.isEmpty(superPanelExtParam.getSwitch3_name())) {
                smartPanelS4KeyName[2] = superPanelExtParam.getSwitch3_name();
            }
            if (superPanelExtParam != null && !TextUtils.isEmpty(superPanelExtParam.getSwitch4_name())) {
                smartPanelS4KeyName[3] = superPanelExtParam.getSwitch4_name();
            }
        }
        return smartPanelS4KeyName;
    }

    private void setNewParam(Device device) {
        SuperPanelExtParam superPanelExtParam = new SuperPanelExtParam();
        if (device.getExtParam() != null) {
            superPanelExtParam.fillMapWithString(device.getExtParam());
            if (superPanelExtParam.getZoneNumber() == 0) {
                setExistDeviceNewParam(superPanelExtParam, device);
                return;
            }
            return;
        }
        setExistDeviceNewParam(superPanelExtParam, device);
    }

    private void setExistDeviceNewParam(final SuperPanelExtParam extParam, final Device device) {
        int i = 2;
        int i2 = 0;
        if (device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
            extParam.setZoneNumber(4);
            while (i2 < 4) {
                RelatedInfoExtParam.RelateInfo relateInfo = new RelatedInfoExtParam.RelateInfo();
                if (i2 == 0) {
                    relateInfo.name = extParam.getSwitch1_name();
                } else if (i2 == 1) {
                    relateInfo.name = extParam.getSwitch2_name();
                } else if (i2 == 2) {
                    relateInfo.name = extParam.getSwitch3_name();
                } else {
                    relateInfo.name = extParam.getSwitch4_name();
                }
                i2++;
                extParam.setRelateInfo(i2, relateInfo);
            }
            i = 4;
        } else {
            extParam.setZoneNumber(2);
            while (i2 < 2) {
                RelatedInfoExtParam.RelateInfo relateInfo2 = new RelatedInfoExtParam.RelateInfo();
                if (i2 == 0) {
                    relateInfo2.name = extParam.getSwitch1_name();
                } else {
                    relateInfo2.name = extParam.getSwitch2_name();
                }
                i2++;
                extParam.setRelateInfo(i2, relateInfo2);
            }
        }
        ((ObservableSubscribeProxy) Injection.net().updateParamExtAndCodeLibrary(device.getDeviceId(), extParam.getParamString(), CodeLibraryUtil.generateDefaultSwitchPanelCodeLibrary(device.getUnicastAddress(), i), device.getLatitude(), device.getLongitude()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_io()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanel$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSuperPanel.lambda$setExistDeviceNewParam$14(Device.this, extParam, obj);
            }
        });
    }

    static /* synthetic */ void lambda$setExistDeviceNewParam$14(Device device, SuperPanelExtParam superPanelExtParam, Object obj) throws Exception {
        device.setExtParam(superPanelExtParam.getParamString());
        Injection.repo().device().saveDevice(device);
    }

    private boolean isDcaDevice(Device device) {
        return device != null && ProductRepository.isDcaSuperPanel(device.getProductId());
    }

    private boolean isSupportInfrared(Device device) {
        if (device != null) {
            return device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_4S) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_6S) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_12S) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX);
        }
        return false;
    }

    private void showAddMeshDialog(final long deviceId) {
        MessageDialog.show(this, getString(R.string.add_success), getString(R.string.add_super_panel_success_tip)).setOkButton(getString(R.string.configure), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanel$$ExternalSyntheticLambda14
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showAddMeshDialog$15;
                lambda$showAddMeshDialog$15 = ActSuperPanel.this.lambda$showAddMeshDialog$15(deviceId, baseDialog, view);
                return lambda$showAddMeshDialog$15;
            }
        }).setCancelButton(R.string.cancel).setCancelable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showAddMeshDialog$15(long j, BaseDialog baseDialog, View view) {
        NavUtils.destination(ActMeshScan.class).withLong("device_id", j).navigation(this);
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        NavHelper.goSetting(((ActSuperPanelVM) this.mViewModel).controlDevice);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActSuperPanelVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        Injection.repo().device().getDeviceFromDb(((ActSuperPanelVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanel$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSuperPanel.this.lambda$startObserve$16((Device) obj);
            }
        });
        if (isDcaDevice(((ActSuperPanelVM) this.mViewModel).controlDevice)) {
            final boolean equals = ((ActSuperPanelVM) this.mViewModel).controlDevice.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO);
            final boolean equals2 = ((ActSuperPanelVM) this.mViewModel).controlDevice.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX);
            MessageManager.getInstance().setPanelSwitchStatusCallBack(new MessageManager.SmartPanelSwitchStatusCallBack() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanel$$ExternalSyntheticLambda10
                @Override // com.smart.message.MessageManager.SmartPanelSwitchStatusCallBack
                public final void onDataReceive(ResponseMsg responseMsg) {
                    ActSuperPanel.this.lambda$startObserve$17(equals, equals2, responseMsg);
                }
            });
            CmdAssistant.getQueryCmdAssistant(((ActSuperPanelVM) this.mViewModel).controlDevice, new int[0]).queryPanelState(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanel$$ExternalSyntheticLambda11
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSuperPanel.lambda$startObserve$18((ResponseMsg) obj);
                }
            }, 4);
        }
        this.showNoPermissionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanel$$ExternalSyntheticLambda12
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSuperPanel.this.lambda$startObserve$19((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$16(Device device) {
        setTitle(device.getDeviceName());
        ((ActSuperPanelVM) this.mViewModel).controlDevice = device;
        if (isDcaDevice(device)) {
            String[] keyNameArray = getKeyNameArray(device);
            if (device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO) || device.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                if (this.list.size() > 4) {
                    this.list.get(1).goItem.setMainText(keyNameArray[0]);
                    this.list.get(2).goItem.setMainText(keyNameArray[1]);
                    this.list.get(3).goItem.setMainText(keyNameArray[2]);
                    this.list.get(4).goItem.setMainText(keyNameArray[3]);
                }
            } else if (this.list.size() > 2) {
                this.list.get(1).goItem.setMainText(keyNameArray[0]);
                this.list.get(2).goItem.setMainText(keyNameArray[1]);
            }
            this.mAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$17(boolean z, boolean z2, ResponseMsg responseMsg) {
        int unicastAddress;
        if (((ActSuperPanelVM) this.mViewModel).controlDevice.getParam(BleParam.class) != null && (unicastAddress = ((BleParam) ((ActSuperPanelVM) this.mViewModel).controlDevice.getParam(BleParam.class)).getUnicastAddress()) == Integer.parseInt(responseMsg.getResData().substring(6, 10), 16)) {
            SwitchPanelState parserSwitchPanelState = ((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserSwitchPanelState(responseMsg.getAgreementId(), unicastAddress, (z || z2) ? 4 : 2, responseMsg.getResData());
            if (parserSwitchPanelState != null) {
                int i = 0;
                if (z || z2) {
                    while (i < 4) {
                        this.selectArray[i] = parserSwitchPanelState.getSwitchPanelZoneStateList().get(i).isSwitchOnOff();
                        i++;
                    }
                } else {
                    while (i < 2) {
                        this.selectArray[i] = parserSwitchPanelState.getSwitchPanelZoneStateList().get(i).isSwitchOnOff();
                        i++;
                    }
                }
                if (this.mViewBinding != 0) {
                    ((ActSuperPanelBinding) this.mViewBinding).rvContent.getAdapter().notifyDataSetChanged();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$19(Void r1) {
        showNoPermissionDialog();
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    private static class FunItem {
        public static final int TYPE_CONTENT = 2;
        public static final int TYPE_KEY = 3;
        public static final int TYPE_TITLE = 1;
        public GoItem goItem;
        public int type;

        private FunItem(int type) {
            this.type = type;
        }

        public static FunItem content(GoItem goItem) {
            FunItem funItem = new FunItem(2);
            funItem.goItem = goItem;
            return funItem;
        }

        public static FunItem title(GoItem goItem) {
            FunItem funItem = new FunItem(1);
            funItem.goItem = goItem;
            return funItem;
        }

        public static FunItem key(GoItem goItem) {
            FunItem funItem = new FunItem(3);
            funItem.goItem = goItem;
            return funItem;
        }
    }

    private NavUtils.Builder addPlaceIdDeviceId(Class clazz) {
        NavUtils.Builder destination = NavUtils.destination(clazz);
        if (((ActSuperPanelVM) this.mViewModel).controlDevice != null) {
            Injection.limiter().reset(Injection.keyCreator().superPanelInfoKey(((ActSuperPanelVM) this.mViewModel).controlDevice.getDeviceId()));
            destination.withLong(Constants.PLACE_ID, ((ActSuperPanelVM) this.mViewModel).controlDevice.getPlaceId()).withLong("device_id", ((ActSuperPanelVM) this.mViewModel).controlDevice.getDeviceId()).withLong(Constants.CONTROL_ID, ((ActSuperPanelVM) this.mViewModel).controlDevice.getId());
        }
        return destination;
    }
}