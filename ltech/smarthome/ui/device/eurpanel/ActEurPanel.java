package com.ltech.smarthome.ui.device.eurpanel;

import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.databinding.ActEurPanelBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.aspanel.AsHelper;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.eurpanel.ActEurPanel;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.ui.mode.ActMode;
import com.ltech.smarthome.ui.scene.ActSelectCgdPro;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.AnnularColorPickView2;
import com.ltech.smarthome.view.TextSeekBarView;
import com.ltech.smarthome.view.dialog.EurFunctionAndRgbDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActEurPanel extends BaseControlActivity<ActEurPanelBinding, ActEurPanelVM> {
    private static final int TYPE_SCENE = 1;
    private static final int TYPE_SCENE_OR_FUNCTION = 2;
    private BaseQuickAdapter<RelateInfoItem, BaseViewHolder> bindZoneAdapter;
    private EurFunctionAndRgbDialog dialog;
    private MultipleItemRvAdapter<RelateInfoItem, BaseViewHolder> sceneFunctionAdapter;
    public SingleLiveEvent<Void> showNoPermissionDialogEvent = new SingleLiveEvent<>();
    private RelateInfoItem singleZoneInfoItem;
    private int viewType;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_eur_panel;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        getWindow().setSoftInputMode(32);
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        initBindZoneRv();
        initSceneFunctionRv();
        ((ActEurPanelVM) this.mViewModel).lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
        ((ActEurPanelVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ((ActEurPanelBinding) this.mViewBinding).annularColorPickView.setOnTouchListener(new View.OnTouchListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$$ExternalSyntheticLambda12
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                boolean lambda$initView$0;
                lambda$initView$0 = ActEurPanel.this.lambda$initView$0(view, motionEvent);
                return lambda$initView$0;
            }
        });
        int i = ((ActEurPanelVM) this.mViewModel).lightType;
        if (i == 1) {
            initDimView();
            return;
        }
        if (i == 2) {
            initCtView();
            return;
        }
        if (i == 3) {
            initRgbView();
            return;
        }
        if (i == 4) {
            initRgbView();
            initWView();
        } else {
            if (i != 5) {
                return;
            }
            initRgbView();
            initCwView();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initView$0(View view, MotionEvent motionEvent) {
        return !((ActEurPanelVM) this.mViewModel).isOpen.getValue().booleanValue();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActEurPanelVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActEurPanelVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActEurPanelVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        ((ActEurPanelVM) this.mViewModel).groupList.setValue(Injection.repo().group().getGroupListByPlaceId(((ActEurPanelVM) this.mViewModel).placeId));
        ((ActEurPanelVM) this.mViewModel).deviceList.setValue(Injection.repo().device().getDeviceListByPlaceId(((ActEurPanelVM) this.mViewModel).placeId));
        ((ActEurPanelVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$$ExternalSyntheticLambda21
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanel.this.lambda$startObserve$1(obj);
            }
        });
        MessageManager.getInstance().setEurPanelStatusCallBack(new MessageManager.EurPanelStatusCallBack() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$$ExternalSyntheticLambda1
            @Override // com.smart.message.MessageManager.EurPanelStatusCallBack
            public final void onDataReceive(ResponseMsg responseMsg) {
                ActEurPanel.this.lambda$startObserve$3(responseMsg);
            }
        });
        ((ActEurPanelVM) this.mViewModel).stateList.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanel.this.lambda$startObserve$4((List) obj);
            }
        });
        ((ActEurPanelVM) this.mViewModel).selectZoneList.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanel.this.lambda$startObserve$5((List) obj);
            }
        });
        ((ActEurPanelVM) this.mViewModel).functionStateList.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanel.this.lambda$startObserve$6((List) obj);
            }
        });
        ((ActEurPanelVM) this.mViewModel).isOpen.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanel.this.lambda$startObserve$7((Boolean) obj);
            }
        });
        ((ActEurPanelVM) this.mViewModel).refreshEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanel.this.lambda$startObserve$8((Void) obj);
            }
        });
        ((ActEurPanelVM) this.mViewModel).modeClickEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanel.this.lambda$startObserve$9((Void) obj);
            }
        });
        ((ActEurPanelVM) this.mViewModel).setLightEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanel.this.lambda$startObserve$10((Integer) obj);
            }
        });
        ((ActEurPanelVM) this.mViewModel).showBindDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$$ExternalSyntheticLambda9
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanel.this.lambda$startObserve$11((Void) obj);
            }
        });
        ((ActEurPanelVM) this.mViewModel).showDoubtDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$$ExternalSyntheticLambda22
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanel.this.lambda$startObserve$12((Void) obj);
            }
        });
        this.showNoPermissionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$$ExternalSyntheticLambda23
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanel.this.lambda$startObserve$13((Void) obj);
            }
        });
        ((ActEurPanelVM) this.mViewModel).showDialogEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActEurPanel.this.showPosition4Dialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Object obj) {
        if (obj instanceof Device) {
            Device device = (Device) obj;
            setTitle(device.getDeviceName());
            initRelatedInfoView(device);
        } else if (obj instanceof Group) {
            Group group = (Group) obj;
            setTitle(group.getGroupName());
            initRelatedInfoView(group);
        }
        if (this.isFirst) {
            CmdAssistant.getQueryCmdAssistant(obj, new int[0]).queryPanelState(this);
            this.isFirst = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(ResponseMsg responseMsg) {
        int parseInt = Integer.parseInt(responseMsg.getResData().substring(6, 10), 16);
        if (((ActEurPanelVM) this.mViewModel).groupControl) {
            Group group = (Group) ((ActEurPanelVM) this.mViewModel).controlObject.getValue();
            if (group == null) {
                return;
            }
            if (parseInt == group.getGroupAddress() || isAddressInGroup(group, parseInt)) {
                ((ActEurPanelVM) this.mViewModel).refreshPanelData(responseMsg);
            }
        } else {
            Device device = (Device) ((ActEurPanelVM) this.mViewModel).controlObject.getValue();
            if (device == null) {
                return;
            }
            if (parseInt == ((BleParam) device.getParam(BleParam.class)).getUnicastAddress()) {
                ((ActEurPanelVM) this.mViewModel).refreshPanelData(responseMsg);
            }
        }
        EurFunctionAndRgbDialog eurFunctionAndRgbDialog = this.dialog;
        if (eurFunctionAndRgbDialog == null || !eurFunctionAndRgbDialog.isVisible()) {
            return;
        }
        runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                ActEurPanel.this.lambda$startObserve$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2() {
        this.dialog.setR(((ActEurPanelVM) this.mViewModel).r).setG(((ActEurPanelVM) this.mViewModel).g).setB(((ActEurPanelVM) this.mViewModel).f6270b).setW(((ActEurPanelVM) this.mViewModel).w).setCwBrt(((ActEurPanelVM) this.mViewModel).cwBrt).setRgbBrt(((ActEurPanelVM) this.mViewModel).rgbBrt).setFunctionStateList(((ActEurPanelVM) this.mViewModel).functionStateList.getValue());
        this.dialog.notifyDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(List list) {
        BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = this.bindZoneAdapter;
        if (baseQuickAdapter != null) {
            baseQuickAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(List list) {
        BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = this.bindZoneAdapter;
        if (baseQuickAdapter != null) {
            baseQuickAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(List list) {
        MultipleItemRvAdapter<RelateInfoItem, BaseViewHolder> multipleItemRvAdapter = this.sceneFunctionAdapter;
        if (multipleItemRvAdapter != null) {
            multipleItemRvAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$8(Void r6) {
        MultipleItemRvAdapter<RelateInfoItem, BaseViewHolder> multipleItemRvAdapter = this.sceneFunctionAdapter;
        if (multipleItemRvAdapter != null) {
            multipleItemRvAdapter.notifyDataSetChanged();
        }
        BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = this.bindZoneAdapter;
        if (baseQuickAdapter != null) {
            baseQuickAdapter.notifyDataSetChanged();
        }
        if (((ActEurPanelVM) this.mViewModel).productId.equals(ProductId.ID_EUR_PANEL_EB2) || ((ActEurPanelVM) this.mViewModel).productId.equals(ProductId.ID_AS_PANEL_U2S)) {
            ((ActEurPanelBinding) this.mViewBinding).seekbarBrt.setProgress(((ActEurPanelVM) this.mViewModel).cwBrt);
        } else {
            if (((ActEurPanelVM) this.mViewModel).productId.equals(ProductId.ID_EUR_PANEL_EB5) || ProductId.ID_AS_PANEL_U4S.equals(((ActEurPanelVM) this.mViewModel).productId)) {
                ((ActEurPanelBinding) this.mViewBinding).seekbarBrt.setProgress(((ActEurPanelVM) this.mViewModel).rgbBrt);
                if (((ActEurPanelVM) this.mViewModel).lightType == 3) {
                    refreshRgbView(Boolean.valueOf(((ActEurPanelVM) this.mViewModel).rgbState == 1));
                } else if (((ActEurPanelVM) this.mViewModel).lightType == 4) {
                    ((ActEurPanelBinding) this.mViewBinding).seekbarW.setProgress(((ActEurPanelVM) this.mViewModel).w);
                    ((ActEurPanelBinding) this.mViewBinding).seekbarW.setValue(LightUtils.brt2ProgressHasBelowZero(((ActEurPanelVM) this.mViewModel).w) + "%");
                    refreshRgbView(Boolean.valueOf(((ActEurPanelVM) this.mViewModel).rgbState == 1));
                    TextSeekBarView textSeekBarView = ((ActEurPanelBinding) this.mViewBinding).seekbarW;
                    if (((ActEurPanelVM) this.mViewModel).cwState == 1 && ((ActEurPanelVM) this.mViewModel).isOpen.getValue().booleanValue()) {
                        r2 = true;
                    }
                    textSeekBarView.setEnabled(r2);
                } else if (((ActEurPanelVM) this.mViewModel).lightType == 5) {
                    ((ActEurPanelBinding) this.mViewBinding).seekbarCw.setProgress(((ActEurPanelVM) this.mViewModel).w);
                    refreshRgbView(Boolean.valueOf(((ActEurPanelVM) this.mViewModel).rgbState == 1 || ((ActEurPanelVM) this.mViewModel).cwState == 1));
                    TextSeekBarView textSeekBarView2 = ((ActEurPanelBinding) this.mViewBinding).seekbarCw;
                    if (((ActEurPanelVM) this.mViewModel).cwState == 1 && ((ActEurPanelVM) this.mViewModel).isOpen.getValue().booleanValue()) {
                        r2 = true;
                    }
                    textSeekBarView2.setEnabled(r2);
                }
            } else if (((ActEurPanelVM) this.mViewModel).productId.equals(ProductId.ID_AS_PANEL_U5S)) {
                ((ActEurPanelBinding) this.mViewBinding).seekbarCw.setValue(String.valueOf(((ActEurPanelVM) this.mViewModel).w));
                ((ActEurPanelBinding) this.mViewBinding).seekbarCw.setProgress(((ActEurPanelVM) this.mViewModel).w);
                refreshRgbView(Boolean.valueOf(((ActEurPanelVM) this.mViewModel).rgbState == 1 || ((ActEurPanelVM) this.mViewModel).cwState == 1));
                TextSeekBarView textSeekBarView3 = ((ActEurPanelBinding) this.mViewBinding).seekbarCw;
                if (((ActEurPanelVM) this.mViewModel).cwState == 1 && ((ActEurPanelVM) this.mViewModel).isOpen.getValue().booleanValue()) {
                    r2 = true;
                }
                textSeekBarView3.setEnabled(r2);
            }
        }
        if (((ActEurPanelVM) this.mViewModel).lightType == 5) {
            if (((ActEurPanelVM) this.mViewModel).rgbState == 1) {
                ((ActEurPanelBinding) this.mViewBinding).seekbarBrt.setProgress(((ActEurPanelVM) this.mViewModel).rgbBrt);
            } else if (((ActEurPanelVM) this.mViewModel).cwState == 1) {
                ((ActEurPanelBinding) this.mViewBinding).seekbarBrt.setProgress(((ActEurPanelVM) this.mViewModel).cwBrt);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$9(Void r4) {
        if (isEmptyGroup(((ActEurPanelVM) this.mViewModel).controlObject.getValue())) {
            return;
        }
        NavUtils.destination(ActMode.class).withInt(Constants.LIGHT_TYPE, ((ActEurPanelVM) this.mViewModel).lightType).withLong(Constants.CONTROL_ID, ((ActEurPanelVM) this.mViewModel).controlId).withBoolean(Constants.GROUP_CONTROL, ((ActEurPanelVM) this.mViewModel).groupControl).withInt(Constants.ZONE_NUM, ((ActEurPanelVM) this.mViewModel).getZoneNum()).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$10(Integer num) {
        if (isEmptyGroup(((ActEurPanelVM) this.mViewModel).controlObject.getValue())) {
            return;
        }
        int progress2BrtHasBelowOne = LightUtils.progress2BrtHasBelowOne(num.intValue());
        ((ActEurPanelBinding) this.mViewBinding).seekbarBrt.setProgress(progress2BrtHasBelowOne);
        ((ActEurPanelVM) this.mViewModel).setTotalBrt(progress2BrtHasBelowOne, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$11(Void r4) {
        if (isEmptyGroup(((ActEurPanelVM) this.mViewModel).controlObject.getValue())) {
            return;
        }
        if (isOwnerOrManager()) {
            ((ActEurPanelVM) this.mViewModel).showZoneBindDialog(0, this.singleZoneInfoItem, true);
        } else {
            this.showNoPermissionDialogEvent.call();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$12(Void r1) {
        showPopup();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$13(Void r1) {
        showNoPermissionDialog();
    }

    private void refreshRgbView(Boolean enable) {
        boolean z = false;
        ((ActEurPanelBinding) this.mViewBinding).annularColorPickViewTransparentView.setVisibility((enable.booleanValue() && ((ActEurPanelVM) this.mViewModel).isOpen.getValue().booleanValue()) ? 8 : 0);
        ((ActEurPanelBinding) this.mViewBinding).ivRgb.setImageResource(enable.booleanValue() ? R.mipmap.ic_rgbswitch_on : R.mipmap.ic_rgbswitch_off);
        if (((ActEurPanelVM) this.mViewModel).lightType == 3 || ((ActEurPanelVM) this.mViewModel).lightType == 5) {
            TextSeekBarView textSeekBarView = ((ActEurPanelBinding) this.mViewBinding).seekbarBrt;
            if (enable.booleanValue() && ((ActEurPanelVM) this.mViewModel).isOpen.getValue().booleanValue()) {
                z = true;
            }
            textSeekBarView.setEnabled(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: refreshView, reason: merged with bridge method [inline-methods] */
    public void lambda$startObserve$7(Boolean isOpen) {
        ((ActEurPanelBinding) this.mViewBinding).annularColorPickView.setAlpha(isOpen.booleanValue() ? 1.0f : 0.5f);
        ((ActEurPanelBinding) this.mViewBinding).ivRgb.setAlpha(isOpen.booleanValue() ? 1.0f : 0.5f);
        ((ActEurPanelBinding) this.mViewBinding).ivMode.setAlpha(isOpen.booleanValue() ? 1.0f : 0.5f);
        ((ActEurPanelBinding) this.mViewBinding).layoutBottom.setAlpha(isOpen.booleanValue() ? 1.0f : 0.5f);
        ((ActEurPanelBinding) this.mViewBinding).ivSwitch.setImageResource(isOpen.booleanValue() ? R.mipmap.ic_eur_on : R.mipmap.ic_eur_off);
        ((ActEurPanelBinding) this.mViewBinding).rvMultiTransparentView.setVisibility(isOpen.booleanValue() ? 8 : 0);
        ((ActEurPanelBinding) this.mViewBinding).rvSceneTransparentView.setVisibility(isOpen.booleanValue() ? 8 : 0);
        ((ActEurPanelBinding) this.mViewBinding).seekbarBrt.setEnabled(isOpen.booleanValue());
        ((ActEurPanelBinding) this.mViewBinding).seekbarW.setEnabled(isOpen.booleanValue());
        ((ActEurPanelBinding) this.mViewBinding).seekbarCw.setEnabled(isOpen.booleanValue());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        NavHelper.goSetting(((ActEurPanelVM) this.mViewModel).controlObject.getValue());
    }

    private void initRgbView() {
        ((ActEurPanelBinding) this.mViewBinding).seekbarBrt.setRange(1, 255, 1);
        ((ActEurPanelBinding) this.mViewBinding).seekbarBrt.setOnProgressChangeListener(new TextSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$$ExternalSyntheticLambda20
            @Override // com.ltech.smarthome.view.TextSeekBarView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z) {
                ActEurPanel.this.lambda$initRgbView$14(i, z);
            }
        });
        initRgbAnnular();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRgbView$14(int i, boolean z) {
        ((ActEurPanelVM) this.mViewModel).rgbBrt = i;
        ((ActEurPanelVM) this.mViewModel).setTotalBrt(i, z);
    }

    private void initWView() {
        ((ActEurPanelBinding) this.mViewBinding).seekbarW.setVisibility(0);
        ((ActEurPanelBinding) this.mViewBinding).seekbarW.setOnProgressChangeListener(new TextSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$$ExternalSyntheticLambda19
            @Override // com.ltech.smarthome.view.TextSeekBarView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z) {
                ActEurPanel.this.lambda$initWView$15(i, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initWView$15(int i, boolean z) {
        ((ActEurPanelVM) this.mViewModel).w = i;
        ((ActEurPanelVM) this.mViewModel).getLightCmdHelper().sendChannelValue(this, 4, i, z);
        this.sceneFunctionAdapter.notifyItemChanged(3);
    }

    private void initCwView() {
        ((ActEurPanelBinding) this.mViewBinding).seekbarCw.setVisibility(0);
        ((ActEurPanelBinding) this.mViewBinding).seekbarCw.setOnProgressChangeListener(new TextSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$$ExternalSyntheticLambda17
            @Override // com.ltech.smarthome.view.TextSeekBarView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z) {
                ActEurPanel.this.lambda$initCwView$16(i, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initCwView$16(int i, boolean z) {
        ((ActEurPanelVM) this.mViewModel).w = i;
        ((ActEurPanelVM) this.mViewModel).setCW(i, z);
        this.sceneFunctionAdapter.notifyItemChanged(3);
    }

    private void initDimView() {
        ((ActEurPanelBinding) this.mViewBinding).ivRgb.setVisibility(8);
        if (!ProductId.ID_AS_PANEL_U1S.equals(((ActEurPanelVM) this.mViewModel).productId)) {
            ((ActEurPanelBinding) this.mViewBinding).ivMode.setVisibility(8);
        }
        ((ActEurPanelBinding) this.mViewBinding).seekbarBrt.setRange(1, 255, 1);
        ((ActEurPanelBinding) this.mViewBinding).seekbarBrt.setOnProgressChangeListener(new TextSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$$ExternalSyntheticLambda15
            @Override // com.ltech.smarthome.view.TextSeekBarView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z) {
                ActEurPanel.this.lambda$initDimView$17(i, z);
            }
        });
        initDimAnnular();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDimView$17(int i, boolean z) {
        ((ActEurPanelVM) this.mViewModel).rgbBrt = i;
        ((ActEurPanelVM) this.mViewModel).getLightCmdHelper().sendDimBrt(this.activity, LightUtils.brt2ProgressHasBelowZero(i), z);
    }

    private void initCtView() {
        ((ActEurPanelBinding) this.mViewBinding).ivRgb.setVisibility(8);
        ((ActEurPanelBinding) this.mViewBinding).seekbarBrt.setRange(1, 255, 1);
        ((ActEurPanelBinding) this.mViewBinding).seekbarBrt.setOnProgressChangeListener(new TextSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$$ExternalSyntheticLambda18
            @Override // com.ltech.smarthome.view.TextSeekBarView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z) {
                ActEurPanel.this.lambda$initCtView$18(i, z);
            }
        });
        initCtAnnular();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initCtView$18(int i, boolean z) {
        ((ActEurPanelVM) this.mViewModel).rgbBrt = i;
        ((ActEurPanelVM) this.mViewModel).getLightCmdHelper().sendWyBrt(this.activity, LightUtils.brt2ProgressHasBelowZero(i), true);
    }

    private void initRgbAnnular() {
        ((ActEurPanelBinding) this.mViewBinding).annularColorPickView.setOnColorChangedListener(new AnnularColorPickView2.IColorChangeListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel.2
            @Override // com.ltech.smarthome.view.AnnularColorPickView2.IColorChangeListener
            public void onColorChanged(int color, float progress) {
                ((ActEurPanelVM) ActEurPanel.this.mViewModel).getLightCmdHelper().sendRgbDC(ActEurPanel.this.activity, color, false);
            }

            @Override // com.ltech.smarthome.view.AnnularColorPickView2.IColorChangeListener
            public void onColorChangedFinish(int color, float progress) {
                ((ActEurPanelVM) ActEurPanel.this.mViewModel).getLightCmdHelper().sendRgbDC(ActEurPanel.this.activity, color, true);
            }
        });
        ((ActEurPanelBinding) this.mViewBinding).annularColorPickViewTransparentView.setOnTouchListener(new View.OnTouchListener(this) { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel.3
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    private void initDimAnnular() {
        ((ActEurPanelBinding) this.mViewBinding).annularColorPickView.setOnColorChangedListener(new AnnularColorPickView2.IColorChangeListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel.4
            @Override // com.ltech.smarthome.view.AnnularColorPickView2.IColorChangeListener
            public void onColorChanged(int color, float progress) {
                ((ActEurPanelVM) ActEurPanel.this.mViewModel).getLightCmdHelper().sendWBrt(ActEurPanel.this.activity, (int) progress, false);
            }

            @Override // com.ltech.smarthome.view.AnnularColorPickView2.IColorChangeListener
            public void onColorChangedFinish(int color, float progress) {
                ((ActEurPanelVM) ActEurPanel.this.mViewModel).getLightCmdHelper().sendWBrt(ActEurPanel.this.activity, (int) progress, true);
            }
        });
        ((ActEurPanelBinding) this.mViewBinding).annularColorPickViewTransparentView.setOnTouchListener(new View.OnTouchListener(this) { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel.5
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        Glide.with((FragmentActivity) this).asBitmap().load(Integer.valueOf(R.mipmap.bg_eur_dim)).into((RequestBuilder<Bitmap>) new CustomViewTarget<AnnularColorPickView2, Bitmap>(((ActEurPanelBinding) this.mViewBinding).annularColorPickView) { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel.6
            @Override // com.bumptech.glide.request.target.Target
            public void onLoadFailed(Drawable errorDrawable) {
            }

            @Override // com.bumptech.glide.request.target.CustomViewTarget
            protected void onResourceCleared(Drawable placeholder) {
            }

            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(Object resource, Transition transition) {
                onResourceReady((Bitmap) resource, (Transition<? super Bitmap>) transition);
            }

            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                ((ActEurPanelBinding) ActEurPanel.this.mViewBinding).annularColorPickView.setBitmapBack(resource);
            }
        });
    }

    private void initCtAnnular() {
        ((ActEurPanelBinding) this.mViewBinding).annularColorPickView.setOnColorChangedListener(new AnnularColorPickView2.IColorChangeListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel.7
            @Override // com.ltech.smarthome.view.AnnularColorPickView2.IColorChangeListener
            public void onColorChanged(int color, float progress) {
                ((ActEurPanelVM) ActEurPanel.this.mViewModel).getLightCmdHelper().sendCW(ActEurPanel.this.activity, 255 - LightUtils.percent2C(progress / 100.0f), false);
            }

            @Override // com.ltech.smarthome.view.AnnularColorPickView2.IColorChangeListener
            public void onColorChangedFinish(int color, float progress) {
                ((ActEurPanelVM) ActEurPanel.this.mViewModel).getLightCmdHelper().sendCW(ActEurPanel.this.activity, 255 - LightUtils.percent2C(progress / 100.0f), true);
            }
        });
        ((ActEurPanelBinding) this.mViewBinding).annularColorPickViewTransparentView.setOnTouchListener(new View.OnTouchListener(this) { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel.8
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        Glide.with((FragmentActivity) this).asBitmap().load(Integer.valueOf(R.mipmap.bg_eur_ct)).into((RequestBuilder<Bitmap>) new CustomViewTarget<AnnularColorPickView2, Bitmap>(((ActEurPanelBinding) this.mViewBinding).annularColorPickView) { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel.9
            @Override // com.bumptech.glide.request.target.Target
            public void onLoadFailed(Drawable errorDrawable) {
            }

            @Override // com.bumptech.glide.request.target.CustomViewTarget
            protected void onResourceCleared(Drawable placeholder) {
            }

            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(Object resource, Transition transition) {
                onResourceReady((Bitmap) resource, (Transition<? super Bitmap>) transition);
            }

            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                ((ActEurPanelBinding) ActEurPanel.this.mViewBinding).annularColorPickView.setBitmapBack(resource);
            }
        });
    }

    private void initBindZoneRv() {
        if (this.bindZoneAdapter == null) {
            BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<RelateInfoItem, BaseViewHolder>(R.layout.item_bind_zone, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel.10
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, RelateInfoItem item) {
                    String str;
                    if (item.relateInfo != null) {
                        if (TextUtils.isEmpty(item.relateInfo.name)) {
                            if (((ActEurPanelVM) ActEurPanel.this.mViewModel).isZoneBind(item)) {
                                str = item.infoName;
                            } else {
                                str = ActEurPanel.this.getResources().getStringArray(R.array.eur_panel_zone_key)[helper.getAdapterPosition()];
                            }
                        } else {
                            str = item.relateInfo.name;
                        }
                    } else {
                        str = ActEurPanel.this.getResources().getStringArray(R.array.eur_panel_zone_key)[helper.getAdapterPosition()];
                    }
                    helper.setText(R.id.tv_name, str);
                    if (((ActEurPanelVM) ActEurPanel.this.mViewModel).stateList.getValue().get(helper.getAdapterPosition()).intValue() == 1) {
                        helper.setText(R.id.tv_state, "ON");
                    } else {
                        helper.setText(R.id.tv_state, "OFF");
                    }
                    if (((ActEurPanelVM) ActEurPanel.this.mViewModel).selectZoneList.getValue().get(helper.getAdapterPosition()).intValue() != 1) {
                        if (((ActEurPanelVM) ActEurPanel.this.mViewModel).stateList.getValue().get(helper.getAdapterPosition()).intValue() == 1) {
                            helper.setBackgroundRes(R.id.layout_item, R.drawable.selector_cradview_bg);
                        } else {
                            helper.setBackgroundRes(R.id.layout_item, R.drawable.shape_cccccc_bt_10);
                        }
                        helper.setTextColor(R.id.tv_name, ActEurPanel.this.getResources().getColor(R.color.color_text_black));
                        helper.setTextColor(R.id.tv_state, ActEurPanel.this.getResources().getColor(R.color.color_text_dark_gray));
                        helper.setImageResource(R.id.iv_edit, R.mipmap.icon_more_edit);
                    } else {
                        helper.setBackgroundRes(R.id.layout_item, R.drawable.selector_cardview_eur_bg);
                        helper.setTextColor(R.id.tv_name, ActEurPanel.this.getResources().getColor(R.color.white));
                        helper.setTextColor(R.id.tv_state, ActEurPanel.this.getResources().getColor(R.color.white));
                        helper.setImageResource(R.id.iv_edit, R.mipmap.icon_as_panel_bind_w);
                    }
                    helper.addOnClickListener(R.id.iv_edit);
                }
            };
            this.bindZoneAdapter = baseQuickAdapter;
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    ActEurPanel.this.lambda$initBindZoneRv$19(baseQuickAdapter2, view, i);
                }
            });
            this.bindZoneAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$$ExternalSyntheticLambda11
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemLongClickListener
                public final boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    boolean lambda$initBindZoneRv$20;
                    lambda$initBindZoneRv$20 = ActEurPanel.this.lambda$initBindZoneRv$20(baseQuickAdapter2, view, i);
                    return lambda$initBindZoneRv$20;
                }
            });
            this.bindZoneAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$$ExternalSyntheticLambda16
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
                public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    ActEurPanel.this.lambda$initBindZoneRv$21(baseQuickAdapter2, view, i);
                }
            });
            this.bindZoneAdapter.bindToRecyclerView(((ActEurPanelBinding) this.mViewBinding).rvMultiBind);
            ((ActEurPanelBinding) this.mViewBinding).rvMultiBind.setLayoutManager(new GridLayoutManager(this, 4));
            ((ActEurPanelBinding) this.mViewBinding).rvMultiBind.setHasFixedSize(true);
            ((ActEurPanelBinding) this.mViewBinding).rvMultiBind.addItemDecoration(((ActEurPanelVM) this.mViewModel).decoration);
            ((DefaultItemAnimator) ((ActEurPanelBinding) this.mViewBinding).rvMultiBind.getItemAnimator()).setSupportsChangeAnimations(false);
            ((ActEurPanelBinding) this.mViewBinding).rvMultiTransparentView.setOnTouchListener(new View.OnTouchListener(this) { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel.11
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initBindZoneRv$19(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (isEmptyGroup(((ActEurPanelVM) this.mViewModel).controlObject.getValue())) {
            return;
        }
        List<Integer> value = ((ActEurPanelVM) this.mViewModel).selectZoneList.getValue();
        if (hasOnlyOneOpenAndClickClose(value, i)) {
            return;
        }
        value.set(i, Integer.valueOf(value.get(i).intValue() == 1 ? 0 : 1));
        ((ActEurPanelVM) this.mViewModel).selectZoneList.setValue(value);
        List<Integer> value2 = ((ActEurPanelVM) this.mViewModel).stateList.getValue();
        if (value2.get(i).intValue() == 0) {
            value2.set(i, 1);
            ((ActEurPanelVM) this.mViewModel).stateList.setValue(value2);
        }
        ((ActEurPanelVM) this.mViewModel).setBindSwitch(1 << i, ((ActEurPanelVM) this.mViewModel).selectZoneList.getValue().get(i).intValue() != 1 ? 2 : 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initBindZoneRv$20(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (isEmptyGroup(((ActEurPanelVM) this.mViewModel).controlObject.getValue())) {
            return false;
        }
        List<Integer> value = ((ActEurPanelVM) this.mViewModel).selectZoneList.getValue();
        List<Integer> value2 = ((ActEurPanelVM) this.mViewModel).stateList.getValue();
        if (value2.get(i).intValue() == 1) {
            value2.set(i, 0);
            ((ActEurPanelVM) this.mViewModel).stateList.setValue(value2);
        }
        boolean hasOnlyOneOpenAndClickClose = hasOnlyOneOpenAndClickClose(value, i);
        if (value.get(i).intValue() == 1) {
            value.set(i, 0);
            ((ActEurPanelVM) this.mViewModel).selectZoneList.setValue(value);
        }
        if (hasOnlyOneOpenAndClickClose) {
            if (((ActEurPanelVM) this.mViewModel).getStateOnZoneNum() == 0) {
                ((ActEurPanelVM) this.mViewModel).setBindSwitch(0, 0);
            } else {
                ((ActEurPanelVM) this.mViewModel).setBindSwitch(1 << i, 0);
            }
        } else {
            ((ActEurPanelVM) this.mViewModel).setBindSwitch(1 << i, 0);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initBindZoneRv$21(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (isEmptyGroup(((ActEurPanelVM) this.mViewModel).controlObject.getValue())) {
            return;
        }
        RelateInfoItem item = this.bindZoneAdapter.getItem(i);
        if (isOwnerOrManager()) {
            ((ActEurPanelVM) this.mViewModel).showZoneBindDialog(i, item, false);
        } else {
            this.showNoPermissionDialogEvent.call();
        }
    }

    private void initSceneFunctionRv() {
        if (this.sceneFunctionAdapter == null) {
            MultipleItemRvAdapter<RelateInfoItem, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<RelateInfoItem, BaseViewHolder>(new ArrayList()) { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel.12
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
                public int getViewType(RelateInfoItem relateInfoItem) {
                    return ActEurPanel.this.viewType;
                }

                /* renamed from: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$12$1, reason: invalid class name */
                class AnonymousClass1 extends BaseItemProvider<RelateInfoItem, BaseViewHolder> {
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_bind_eur_scene;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 1;
                    }

                    AnonymousClass1() {
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, RelateInfoItem item, final int position) {
                        String str;
                        if (((ActEurPanelVM) ActEurPanel.this.mViewModel).isSceneBind(item)) {
                            str = item.infoName;
                        } else {
                            str = ActEurPanel.this.getString(R.string.app_scene) + (position + 1);
                            helper.setGone(R.id.tv_position, false);
                        }
                        if (ProductRepository.isAsPanel(((ActEurPanelVM) ActEurPanel.this.mViewModel).controlObject.getValue())) {
                            helper.setGone(R.id.tv_position, true).setText(R.id.tv_position, new String[]{ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, "B", "C", "D"}[position]);
                        }
                        helper.setText(R.id.tv_name, str).setImageResource(R.id.iv_icon, RelateInfoUtils.getRelateInfoIcon(((ActEurPanelVM) ActEurPanel.this.mViewModel).relateInfoAssistant.getRelateSceneInfo(position)));
                        helper.getView(R.id.iv_edit).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$12$1$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                ActEurPanel.AnonymousClass12.AnonymousClass1.this.lambda$convert$0(position, view);
                            }
                        });
                    }

                    /* JADX INFO: Access modifiers changed from: private */
                    public /* synthetic */ void lambda$convert$0(int i, View view) {
                        if (ActEurPanel.this.isEmptyGroup(((ActEurPanelVM) ActEurPanel.this.mViewModel).controlObject.getValue())) {
                            return;
                        }
                        if (ActEurPanel.this.isOwnerOrManager()) {
                            ((ActEurPanelVM) ActEurPanel.this.mViewModel).showRelateCurSceneDialog(i);
                        } else {
                            ActEurPanel.this.showNoPermissionDialogEvent.call();
                        }
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void onClick(BaseViewHolder helper, RelateInfoItem item, int position) {
                        if (!ActEurPanel.this.isEmptyGroup(((ActEurPanelVM) ActEurPanel.this.mViewModel).controlObject.getValue()) && ((ActEurPanelVM) ActEurPanel.this.mViewModel).isSceneBind(item)) {
                            if (item.relateInfo.objectId == position + 1) {
                                ((ActEurPanelVM) ActEurPanel.this.mViewModel).executeCurScene(ActEurPanel.this.activity, position);
                            } else if (item.infoName != null) {
                                ((ActEurPanelVM) ActEurPanel.this.mViewModel).executeCurScene(ActEurPanel.this.activity, position);
                            }
                        }
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public boolean onLongClick(BaseViewHolder helper, RelateInfoItem item, int position) {
                        if (ActEurPanel.this.isEmptyGroup(((ActEurPanelVM) ActEurPanel.this.mViewModel).controlObject.getValue())) {
                            return false;
                        }
                        if (!ProductId.ID_EUR_PANEL_EB5.equals(((ActEurPanelVM) ActEurPanel.this.mViewModel).productId) || position != 3) {
                            if (ActEurPanel.this.isOwnerOrManager()) {
                                ((ActEurPanelVM) ActEurPanel.this.mViewModel).showRelateCurSceneDialog(position);
                                return true;
                            }
                            ActEurPanel.this.showNoPermissionDialogEvent.call();
                            return true;
                        }
                        ActEurPanel.this.showPosition4Dialog();
                        return true;
                    }
                }

                @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
                public void registerItemProvider() {
                    this.mProviderDelegate.registerProvider(new AnonymousClass1());
                    this.mProviderDelegate.registerProvider(new AnonymousClass2());
                }

                /* renamed from: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$12$2, reason: invalid class name */
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
                        if (((ActEurPanelVM) ActEurPanel.this.mViewModel).isSceneBind(item)) {
                            helper.setText(R.id.tv_name, item.infoName).setText(R.id.tv_state, "").setTextColor(R.id.tv_name, ContextCompat.getColor(ActEurPanel.this.activity, R.color.color_text_black)).setBackgroundRes(R.id.layout_item, R.drawable.selector_cradview_bg).setImageResource(R.id.iv_icon, RelateInfoUtils.getRelateInfoIcon(((ActEurPanelVM) ActEurPanel.this.mViewModel).relateInfoAssistant.getRelateSceneInfo(position)));
                            if (ProductRepository.isAsPanel(((ActEurPanelVM) ActEurPanel.this.mViewModel).controlObject.getValue())) {
                                helper.setGone(R.id.tv_position, true).setText(R.id.tv_position, new String[]{ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, "B", "C", "D"}[position]);
                            }
                        } else {
                            helper.setText(R.id.tv_name, ActEurPanel.this.getResources().getStringArray(R.array.eur_rgb_text)[position]).setGone(R.id.tv_position, false);
                            helper.setImageResource(R.id.iv_icon, HelpUtils.getDrawableResourceArray(ActEurPanel.this.activity, R.array.eur_rgb_icon)[helper.getAdapterPosition()]);
                            if (position == 0) {
                                helper.setText(R.id.tv_state, String.valueOf(((ActEurPanelVM) ActEurPanel.this.mViewModel).r));
                            } else if (position == 1) {
                                helper.setText(R.id.tv_state, String.valueOf(((ActEurPanelVM) ActEurPanel.this.mViewModel).g));
                            } else if (position == 2) {
                                helper.setText(R.id.tv_state, String.valueOf(((ActEurPanelVM) ActEurPanel.this.mViewModel).f6270b));
                            } else if (position == 3) {
                                ActEurPanel.this.setPosition4Info(helper);
                            }
                        }
                        if (position != 3 || ((ActEurPanelVM) ActEurPanel.this.mViewModel).lightType != 3) {
                            if (!((ActEurPanelVM) ActEurPanel.this.mViewModel).isSceneBind(item)) {
                                if (((ActEurPanelVM) ActEurPanel.this.mViewModel).functionStateList.getValue().get(position).intValue() == 1) {
                                    if (((ActEurPanelVM) ActEurPanel.this.mViewModel).rgbState != 0 || position >= 3) {
                                        helper.setTextColor(R.id.tv_name, ContextCompat.getColor(ActEurPanel.this.activity, R.color.color_text_black));
                                        helper.setBackgroundRes(R.id.layout_item, R.drawable.selector_cradview_bg);
                                    } else {
                                        helper.setTextColor(R.id.tv_name, ContextCompat.getColor(ActEurPanel.this.activity, R.color.color_text_dark_gray));
                                        helper.setBackgroundRes(R.id.layout_item, R.drawable.selector_cardview_gray_bg);
                                        helper.setImageResource(R.id.iv_icon, R.mipmap.ic_red_off);
                                    }
                                } else {
                                    helper.setTextColor(R.id.tv_name, ContextCompat.getColor(ActEurPanel.this.activity, R.color.color_text_dark_gray));
                                    helper.setBackgroundRes(R.id.layout_item, R.drawable.selector_cardview_gray_bg);
                                    helper.setImageResource(R.id.iv_icon, R.mipmap.ic_red_off);
                                }
                            }
                        } else if (((ActEurPanelVM) ActEurPanel.this.mViewModel).rgbState == 0) {
                            helper.setTextColor(R.id.tv_name, ContextCompat.getColor(ActEurPanel.this.activity, R.color.color_text_dark_gray));
                            helper.setBackgroundRes(R.id.layout_item, R.drawable.selector_cardview_gray_bg);
                            helper.setImageResource(R.id.iv_icon, R.mipmap.ic_red_off);
                        } else {
                            helper.setTextColor(R.id.tv_name, ContextCompat.getColor(ActEurPanel.this.activity, R.color.color_text_black));
                            helper.setBackgroundRes(R.id.layout_item, R.drawable.selector_cradview_bg);
                        }
                        helper.getView(R.id.iv_edit).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$12$2$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                ActEurPanel.AnonymousClass12.AnonymousClass2.this.lambda$convert$0(position, view);
                            }
                        });
                    }

                    /* JADX INFO: Access modifiers changed from: private */
                    public /* synthetic */ void lambda$convert$0(int i, View view) {
                        if (ActEurPanel.this.isEmptyGroup(((ActEurPanelVM) ActEurPanel.this.mViewModel).controlObject.getValue())) {
                            return;
                        }
                        if (!ProductId.ID_EUR_PANEL_EB5.equals(((ActEurPanelVM) ActEurPanel.this.mViewModel).productId) || i != 3) {
                            if (ActEurPanel.this.isOwnerOrManager()) {
                                ((ActEurPanelVM) ActEurPanel.this.mViewModel).showRelateCurSceneDialog(i);
                                return;
                            } else {
                                ActEurPanel.this.showNoPermissionDialogEvent.call();
                                return;
                            }
                        }
                        ActEurPanel.this.showPosition4Dialog();
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void onClick(BaseViewHolder helper, RelateInfoItem item, int position) {
                        if (ActEurPanel.this.isEmptyGroup(((ActEurPanelVM) ActEurPanel.this.mViewModel).controlObject.getValue())) {
                            return;
                        }
                        if (((ActEurPanelVM) ActEurPanel.this.mViewModel).isSceneBind(item)) {
                            ((ActEurPanelVM) ActEurPanel.this.mViewModel).executeCurScene(ActEurPanel.this.activity, position);
                            return;
                        }
                        if (!ProductId.ID_EUR_PANEL_EB5.equals(((ActEurPanelVM) ActEurPanel.this.mViewModel).productId) || position != 3) {
                            if (ActEurPanel.this.isOwnerOrManager()) {
                                ((ActEurPanelVM) ActEurPanel.this.mViewModel).showRelateCurSceneDialog(position);
                                return;
                            } else {
                                ActEurPanel.this.showNoPermissionDialogEvent.call();
                                return;
                            }
                        }
                        ActEurPanel.this.showPosition4Dialog();
                    }
                }
            };
            this.sceneFunctionAdapter = multipleItemRvAdapter;
            multipleItemRvAdapter.finishInitialize();
            this.sceneFunctionAdapter.bindToRecyclerView(((ActEurPanelBinding) this.mViewBinding).rvScene);
            ((ActEurPanelBinding) this.mViewBinding).rvScene.setLayoutManager(new GridLayoutManager(this, 4));
            ((ActEurPanelBinding) this.mViewBinding).rvScene.setHasFixedSize(true);
            ((ActEurPanelBinding) this.mViewBinding).rvScene.addItemDecoration(((ActEurPanelVM) this.mViewModel).decoration);
            ((DefaultItemAnimator) ((ActEurPanelBinding) this.mViewBinding).rvScene.getItemAnimator()).setSupportsChangeAnimations(false);
            ((ActEurPanelBinding) this.mViewBinding).rvSceneTransparentView.setOnTouchListener(new View.OnTouchListener(this) { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel.13
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
    }

    private void showPopup() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.view_eur_tip_function, (ViewGroup) null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) inflate.findViewById(R.id.tv);
        if (ProductId.ID_AS_PANEL_U1S.equals(((ActEurPanelVM) this.mViewModel).productId) || ProductId.ID_AS_PANEL_U2S.equals(((ActEurPanelVM) this.mViewModel).productId)) {
            appCompatTextView.setText(String.format("%s\n\n%s", getString(R.string.str_eur_tip_zone), getString(R.string.ub12_scene_tip)));
        } else if (ProductId.ID_AS_PANEL_U4S.equals(((ActEurPanelVM) this.mViewModel).productId)) {
            if (((ActEurPanelVM) this.mViewModel).lightType == 3) {
                appCompatTextView.setText(String.format("%s\n\n%s", getString(R.string.str_eur_tip_zone), getString(R.string.ub45_scene_tip, new Object[]{getString(R.string.str_saturation)})));
            } else {
                appCompatTextView.setText(String.format("%s\n\n%s", getString(R.string.str_eur_tip_zone), getString(R.string.ub45_scene_tip, new Object[]{getString(R.string.w_value)})));
            }
        } else if (ProductId.ID_AS_PANEL_U5S.equals(((ActEurPanelVM) this.mViewModel).productId)) {
            appCompatTextView.setText(String.format("%s\n\n%s", getString(R.string.str_eur_tip_zone), getString(R.string.ub45_scene_tip, new Object[]{getString(R.string.wy_value)})));
        }
        PopupWindow popupWindow = new PopupWindow(inflate, Utils.dip2px(this, 235.0f), -2, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(((ActEurPanelBinding) this.mViewBinding).ivDoubt, Utils.dip2px(this, -150.0f), 10);
    }

    public boolean hasOnlyOneOpenAndClickClose(List<Integer> list, int position) {
        Iterator<Integer> it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().intValue() == 1) {
                i++;
            }
        }
        return i == 1 && list.get(position).intValue() == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPosition4Dialog() {
        int i = ((ActEurPanelVM) this.mViewModel).lightType;
        int i2 = 3;
        if (i != 3) {
            if (i == 4) {
                i2 = 2;
            } else if (i == 5) {
                i2 = 1;
            }
        } else if (((ActEurPanelVM) this.mViewModel).rgbState == 0) {
            return;
        }
        EurFunctionAndRgbDialog onDialogCallback = EurFunctionAndRgbDialog.asDefault().setDialogType(i2).setZoneNum(((ActEurPanelVM) this.mViewModel).getZoneNum()).setR(((ActEurPanelVM) this.mViewModel).r).setG(((ActEurPanelVM) this.mViewModel).g).setB(((ActEurPanelVM) this.mViewModel).f6270b).setW(((ActEurPanelVM) this.mViewModel).w).setCwBrt(((ActEurPanelVM) this.mViewModel).cwBrt).setRgbBrt(((ActEurPanelVM) this.mViewModel).rgbBrt).setFunctionStateList(((ActEurPanelVM) this.mViewModel).functionStateList.getValue()).setControlObject(((ActEurPanelVM) this.mViewModel).controlObject.getValue()).setOnDialogCallback(new EurFunctionAndRgbDialog.OnDialogCallback() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel.14
            @Override // com.ltech.smarthome.view.dialog.EurFunctionAndRgbDialog.OnDialogCallback
            public void dismiss(int r, int g, int b2, int w, int rgbBrt, int cwBrt, List<Integer> functionStateList) {
                ((ActEurPanelVM) ActEurPanel.this.mViewModel).r = r;
                ((ActEurPanelVM) ActEurPanel.this.mViewModel).g = g;
                ((ActEurPanelVM) ActEurPanel.this.mViewModel).f6270b = b2;
                ((ActEurPanelVM) ActEurPanel.this.mViewModel).w = w;
                ((ActEurPanelVM) ActEurPanel.this.mViewModel).rgbBrt = rgbBrt;
                ((ActEurPanelVM) ActEurPanel.this.mViewModel).cwBrt = cwBrt;
                ((ActEurPanelVM) ActEurPanel.this.mViewModel).functionStateList.setValue(functionStateList);
                ((ActEurPanelVM) ActEurPanel.this.mViewModel).cwState = functionStateList.get(3).intValue();
                ((ActEurPanelVM) ActEurPanel.this.mViewModel).rgbState = functionStateList.get(4).intValue();
                ((ActEurPanelVM) ActEurPanel.this.mViewModel).refreshEvent.call();
            }
        });
        this.dialog = onDialogCallback;
        onDialogCallback.showDialog(this);
        CmdAssistant.getQueryCmdAssistant(((ActEurPanelVM) this.mViewModel).controlObject.getValue(), new int[0]).queryPanelState(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPosition4Info(BaseViewHolder helper) {
        int i = ((ActEurPanelVM) this.mViewModel).lightType;
        if (i == 3) {
            helper.setText(R.id.tv_name, getString(R.string.str_saturation)).setImageResource(R.id.iv_icon, R.mipmap.ic_rgb_on);
            helper.setText(R.id.tv_state, LightUtils.brt2ProgressHasBelowZero(((ActEurPanelVM) this.mViewModel).w) + "%");
            return;
        }
        if (i != 4) {
            if (i != 5) {
                return;
            }
            helper.setText(R.id.tv_name, "CW").setImageResource(R.id.iv_icon, R.mipmap.ic_cw);
            helper.setText(R.id.tv_state, String.valueOf(((ActEurPanelVM) this.mViewModel).w));
            return;
        }
        helper.setText(R.id.tv_name, ExifInterface.LONGITUDE_WEST).setImageResource(R.id.iv_icon, R.mipmap.ic_w);
        helper.setText(R.id.tv_state, LightUtils.brt2ProgressHasBelowZero(((ActEurPanelVM) this.mViewModel).w) + "%");
    }

    private void initRelatedInfoView(Object object) {
        String str;
        ((ActEurPanelVM) this.mViewModel).initRelateInfoList(object);
        ((ActEurPanelBinding) this.mViewBinding).layoutSingleZone.setVisibility(((ActEurPanelVM) this.mViewModel).relateInfoAssistant.getZoneNumber() == 1 ? 0 : 8);
        ((ActEurPanelBinding) this.mViewBinding).layoutMultiZone.setVisibility(((ActEurPanelVM) this.mViewModel).relateInfoAssistant.getZoneNumber() == 4 ? 0 : 8);
        if (ProductId.ID_EUR_PANEL_EB5.equals(((ActEurPanelVM) this.mViewModel).productId) || ProductId.ID_AS_PANEL_U5S.equals(((ActEurPanelVM) this.mViewModel).productId) || ProductId.ID_AS_PANEL_U4S.equals(((ActEurPanelVM) this.mViewModel).productId)) {
            this.viewType = 2;
            ((ActEurPanelBinding) this.mViewBinding).tvScene.setText(R.string.scene_function);
        } else {
            this.viewType = 1;
            ((ActEurPanelBinding) this.mViewBinding).tvScene.setText(R.string.bind_scene);
            ((ActEurPanelBinding) this.mViewBinding).ivFunctionDoubt.setVisibility(8);
        }
        if (AsHelper.isNewUb(object)) {
            ((ActEurPanelBinding) this.mViewBinding).ivFunctionDoubt.setVisibility(8);
        }
        if (((ActEurPanelVM) this.mViewModel).relateInfoAssistant.getZoneNumber() == 4) {
            this.bindZoneAdapter.setNewData(RelateInfoUtils.relatedInfoList);
        } else {
            RelateInfoItem relateInfoItem = RelateInfoUtils.relatedInfoList.get(0);
            this.singleZoneInfoItem = relateInfoItem;
            if (relateInfoItem.relateInfo == null) {
                str = getString(R.string.wait_bind);
            } else if (TextUtils.isEmpty(this.singleZoneInfoItem.relateInfo.name)) {
                if (((ActEurPanelVM) this.mViewModel).isZoneBind(this.singleZoneInfoItem)) {
                    str = this.singleZoneInfoItem.infoName;
                } else {
                    str = getString(R.string.wait_bind);
                }
            } else {
                str = this.singleZoneInfoItem.relateInfo.name;
            }
            ((ActEurPanelBinding) this.mViewBinding).tvWaitBind.setText(str);
        }
        this.sceneFunctionAdapter.setNewData(RelateInfoUtils.relatedSceneInfoList);
    }

    private void showRelatedSceneDialog(final int position) {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        if (((ActEurPanelVM) this.mViewModel).isSceneBind(this.sceneFunctionAdapter.getData().get(position))) {
            arrayList.add(getString(R.string.reset_relate));
            selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$$ExternalSyntheticLambda13
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActEurPanel.this.lambda$showRelatedSceneDialog$22(position, (Integer) obj);
                }
            }).setSelectList(arrayList);
        } else {
            arrayList.add(getString(R.string.save_cur_scene));
            arrayList.add(getString(R.string.local_scene));
            arrayList.add(getString(R.string.dali_scene));
            selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanel$$ExternalSyntheticLambda14
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActEurPanel.this.lambda$showRelatedSceneDialog$23(position, (Integer) obj);
                }
            }).setSelectList(arrayList);
        }
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showRelatedSceneDialog$22(int i, Integer num) {
        ((ActEurPanelVM) this.mViewModel).unBindRelateInfo(i, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showRelatedSceneDialog$23(int i, Integer num) {
        int intValue = num.intValue();
        if (intValue == 0) {
            ((ActEurPanelVM) this.mViewModel).saveCurScene(i);
        } else if (intValue == 1) {
            NavUtils.destination(ActSmartPanelSelectScene.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActEurPanelVM) this.mViewModel).controlId).withBoolean(Constants.GROUP_CONTROL, ((ActEurPanelVM) this.mViewModel).groupControl).withInt(Constants.RELATED_POSITION, i).withDefaultRequestCode().navigation(this);
        } else {
            if (intValue != 2) {
                return;
            }
            NavUtils.destination(ActSelectCgdPro.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActEurPanelVM) this.mViewModel).controlId).withBoolean(Constants.GROUP_CONTROL, ((ActEurPanelVM) this.mViewModel).groupControl).withInt(Constants.RELATED_POSITION, i).withDefaultRequestCode().navigation(this);
        }
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (((ActEurPanelVM) this.mViewModel).groupControl) {
            ((ActEurPanelVM) this.mViewModel).controlObject.setValue(Injection.repo().group().getGroupById(((ActEurPanelVM) this.mViewModel).controlId));
        } else {
            ((ActEurPanelVM) this.mViewModel).controlObject.setValue(Injection.repo().device().getDeviceById(((ActEurPanelVM) this.mViewModel).controlId));
        }
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        MessageManager.getInstance().setEurPanelStatusCallBack(null);
    }
}