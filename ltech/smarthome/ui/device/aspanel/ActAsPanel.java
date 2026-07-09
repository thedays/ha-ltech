package com.ltech.smarthome.ui.device.aspanel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActAsPanelBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.ui.device.aspanel.ActAsPanelVM;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.NameRepository;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.view.AnnularColorPickView;
import com.ltech.smarthome.view.ColorSeekBar;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.dialog.PanelBrtDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.bean.PanelState;
import com.smart.product_agreement.parser.IPanelParser;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public class ActAsPanel extends BaseControlActivity<ActAsPanelBinding, ActAsPanelVM> {
    private BaseQuickAdapter<ActAsPanelVM.AsPanelRelateInfoItem, BaseViewHolder> infoAdapter;

    private void ub245RgbCtChangeMinBbt25() {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.layout_content;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_as_panel;
    }

    @Override // com.ltech.smarthome.base.VMActivity
    protected boolean useVMStateEvent() {
        return true;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        ((ActAsPanelVM) this.mViewModel).lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
        int i = ((ActAsPanelVM) this.mViewModel).lightType;
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    initColorRgb();
                } else {
                    if (i != 4) {
                        if (i != 5) {
                            switch (i) {
                            }
                        }
                        initColorRgbwy();
                    }
                    initColorRgbw();
                }
                initSceneView();
            }
            initColorCt();
            initSceneView();
        }
        initColorDim();
        initSceneView();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        NavHelper.goSetting(((ActAsPanelVM) this.mViewModel).controlObject.getValue());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActAsPanelVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActAsPanelVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActAsPanelVM) this.mViewModel).request = Injection.repo().group().getGroupList(this, ((ActAsPanelVM) this.mViewModel).placeId);
        Injection.repo().device().getDeviceList(this, ((ActAsPanelVM) this.mViewModel).placeId, -1L, -1L);
        ((ActAsPanelVM) this.mViewModel).groupList.addSource(((ActAsPanelVM) this.mViewModel).request.data(), new Observer() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel$$ExternalSyntheticLambda13
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAsPanel.this.lambda$startObserve$1((Resource) obj);
            }
        });
        handleData(Transformations.switchMap(((ActAsPanelVM) this.mViewModel).groupList, new Function1() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$startObserve$2;
                lambda$startObserve$2 = ActAsPanel.this.lambda$startObserve$2((List) obj);
                return lambda$startObserve$2;
            }
        }), new IAction() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel$$ExternalSyntheticLambda15
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActAsPanel.this.lambda$startObserve$3((List) obj);
            }
        });
        ((ActAsPanelVM) this.mViewModel).deviceList.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAsPanel.this.lambda$startObserve$4((List) obj);
            }
        });
        ((ActAsPanelVM) this.mViewModel).panelZoneStateLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAsPanel.this.lambda$startObserve$5((PanelState.PanelZoneState) obj);
            }
        });
        ((ActAsPanelVM) this.mViewModel).showWDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAsPanel.this.lambda$startObserve$6((Void) obj);
            }
        });
        ((ActAsPanelVM) this.mViewModel).showRgbBrtDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAsPanel.this.lambda$startObserve$7((Void) obj);
            }
        });
        MessageManager.getInstance().setWallSwitchStatusCallBack(new MessageManager.WallSwitchStatusCallBack() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel$$ExternalSyntheticLambda5
            @Override // com.smart.message.MessageManager.WallSwitchStatusCallBack
            public final void onDataReceive(ResponseMsg responseMsg) {
                ActAsPanel.this.lambda$startObserve$8(responseMsg);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(List list) {
        ((ActAsPanelVM) this.mViewModel).groupList.setValue(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Resource resource) {
        handleResource(resource, new IAction() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel$$ExternalSyntheticLambda9
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActAsPanel.this.lambda$startObserve$0((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$startObserve$2(List list) {
        return Injection.repo().device().getDeviceList(this, ((ActAsPanelVM) this.mViewModel).placeId, -1L, -1L).data();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(List list) {
        ((ActAsPanelVM) this.mViewModel).deviceList.setValue(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Device device = (Device) it.next();
            if (device.getId() == ((ActAsPanelVM) this.mViewModel).controlId) {
                if (((ActAsPanelVM) this.mViewModel).controlObject.getValue() == null) {
                    LHomeLog.i(getClass(), "mViewModel.controlObject.getValue() == null &&mViewModel.deviceList observe enter");
                    initTvBrtOrWY(device);
                    return;
                }
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(PanelState.PanelZoneState panelZoneState) {
        if (panelZoneState != null) {
            LHomeLog.i(getClass(), "get panelZoneStateLiveData=" + panelZoneState.getRgbBrt() + "__=" + ((panelZoneState.getRgbBrt() * 100) / 255));
            if (1 == ((ActAsPanelVM) this.mViewModel).lightType || 27 == ((ActAsPanelVM) this.mViewModel).lightType) {
                ((ActAsPanelBinding) this.mViewBinding).sbBrt.setProgress(LightUtils.brt2ProgressHasBelowZero(panelZoneState.getWyBrt()));
            }
            if (2 == ((ActAsPanelVM) this.mViewModel).lightType || 28 == ((ActAsPanelVM) this.mViewModel).lightType) {
                ((ActAsPanelBinding) this.mViewBinding).sbBrt.setProgress(LightUtils.brt2ProgressHasBelowZero(panelZoneState.getWyBrt()));
            }
            if (4 == ((ActAsPanelVM) this.mViewModel).lightType || 29 == ((ActAsPanelVM) this.mViewModel).lightType) {
                ((ActAsPanelBinding) this.mViewBinding).sbBrt.setProgress(LightUtils.brt2ProgressHasBelowZero(panelZoneState.getWyBrt()));
            }
            if (5 == ((ActAsPanelVM) this.mViewModel).lightType || 30 == ((ActAsPanelVM) this.mViewModel).lightType) {
                ((ActAsPanelBinding) this.mViewBinding).sbBrt.setProgress(LightUtils.brt2ProgressHasBelowZero(panelZoneState.getwOrWy()));
            }
            ((ActAsPanelBinding) this.mViewBinding).tvBrt.setText(((ActAsPanelBinding) this.mViewBinding).sbBrt.getProgressHasBelowOne());
            ((ActAsPanelBinding) this.mViewBinding).csbColorBar.setProgress(LightUtils.c2percent(panelZoneState.getwOrWy()));
            ((ActAsPanelBinding) this.mViewBinding).tvColor.setText(String.format(Locale.US, "%d", Integer.valueOf(Math.round(LightUtils.c2percent(panelZoneState.getwOrWy()) * 255.0f))));
            CircleImageView circleImageView = ((ActAsPanelBinding) this.mViewBinding).civRgb;
            boolean isRgbOnOff = panelZoneState.isRgbOnOff();
            int i = R.color.color_blue;
            circleImageView.setImageResource(isRgbOnOff ? R.color.color_blue : R.color.color_text_dark_gray);
            CircleImageView circleImageView2 = ((ActAsPanelBinding) this.mViewBinding).civW;
            if (!panelZoneState.iswOrWyOnOff()) {
                i = R.color.color_text_dark_gray;
            }
            circleImageView2.setImageResource(i);
            BaseQuickAdapter<ActAsPanelVM.AsPanelRelateInfoItem, BaseViewHolder> baseQuickAdapter = this.infoAdapter;
            if (baseQuickAdapter != null) {
                baseQuickAdapter.notifyDataSetChanged();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(Void r2) {
        if (((ActAsPanelVM) this.mViewModel).lightType == 5) {
            showWyBrtDialog();
        } else {
            showWBrtDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(Void r1) {
        showRgbBrtDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$8(ResponseMsg responseMsg) {
        ((ActAsPanelVM) this.mViewModel).refreshPanelState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserPanelState(responseMsg.getAgreementId(), responseMsg.getResData()));
    }

    private void startDeviceObserver() {
        ((ActAsPanelVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAsPanel.this.lambda$startDeviceObserver$9((Device) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startDeviceObserver$9(Device device) {
        LHomeLog.i(getClass(), "controlObject observe enter");
        setTitle(device.getDeviceName());
        ((ActAsPanelVM) this.mViewModel).stateOn.setValue(Boolean.valueOf(device.getDeviceState().isOn()));
        initRelatedInfoView(device);
        LHomeLog.i(getClass(), "pparms=" + device.getParam());
        ((ActAsPanelVM) this.mViewModel).queryPanelState(4);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        super.showContent();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        if (((ActAsPanelVM) this.mViewModel).request != null) {
            ((ActAsPanelVM) this.mViewModel).request.retry();
        }
    }

    private void initSceneView() {
        BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(this, R.layout.item_text, Arrays.asList(NameRepository.getAsPanelSceneName(this))) { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_name, item).setTextColor(R.id.tv_name, ContextCompat.getColor(this.mContext, R.color.color_text_black)).setBackgroundRes(R.id.tv_name, R.drawable.shape_light_gray_bt);
                ((AppCompatTextView) helper.getView(R.id.tv_name)).setGravity(17);
                ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
            }
        };
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel$$ExternalSyntheticLambda12
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActAsPanel.this.lambda$initSceneView$10(baseQuickAdapter2, view, i);
            }
        });
        baseQuickAdapter.bindToRecyclerView(((ActAsPanelBinding) this.mViewBinding).rvScene);
        ((ActAsPanelBinding) this.mViewBinding).rvScene.setLayoutManager(new GridLayoutManager(this, 4));
        ((ActAsPanelBinding) this.mViewBinding).rvScene.setHasFixedSize(true);
        ((ActAsPanelBinding) this.mViewBinding).rvScene.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(SizeUtils.dp2px(4.0f), 0, SizeUtils.dp2px(4.0f), 0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initSceneView$10(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        ((ActAsPanelVM) this.mViewModel).executeScene(this, i + 1);
    }

    private void initRelatedInfoView(Device device) {
        ((ActAsPanelVM) this.mViewModel).initPanelState(device);
        ((ActAsPanelVM) this.mViewModel).initRelateInfoList(device);
        BaseQuickAdapter<ActAsPanelVM.AsPanelRelateInfoItem, BaseViewHolder> baseQuickAdapter = this.infoAdapter;
        if (baseQuickAdapter == null) {
            BaseQuickAdapter<ActAsPanelVM.AsPanelRelateInfoItem, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<ActAsPanelVM.AsPanelRelateInfoItem, BaseViewHolder>(R.layout.item_as_panel_related_info, ((ActAsPanelVM) this.mViewModel).relatedInfoList) { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel.3
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(final BaseViewHolder helper, ActAsPanelVM.AsPanelRelateInfoItem item) {
                    if (((ActAsPanelVM) ActAsPanel.this.mViewModel).panelState == null || ((ActAsPanelVM) ActAsPanel.this.mViewModel).panelState.getPanelZoneStateList() == null || helper.getAdapterPosition() >= ((ActAsPanelVM) ActAsPanel.this.mViewModel).panelState.getPanelZoneStateList().size()) {
                        return;
                    }
                    boolean isOnOff = ((ActAsPanelVM) ActAsPanel.this.mViewModel).panelState.getPanelZoneStateList().get(helper.getAdapterPosition()).isOnOff();
                    LHomeLog.i(getClass(), "select.ison=" + isOnOff);
                    helper.setText(R.id.tv_on_off, isOnOff ? R.string.on : R.string.off).addOnClickListener(R.id.iv_more);
                    if (item.isSelect(((ActAsPanelVM) ActAsPanel.this.mViewModel).zoneNum, helper.getAdapterPosition())) {
                        BaseViewHolder backgroundRes = helper.setBackgroundRes(R.id.layout_item_bg, R.drawable.shape_red_bt_10);
                        Context context = this.mContext;
                        int i = R.color.white;
                        BaseViewHolder textColor = backgroundRes.setTextColor(R.id.tv_info, ContextCompat.getColor(context, isOnOff ? R.color.white : R.color.dark_red));
                        Context context2 = this.mContext;
                        if (!isOnOff) {
                            i = R.color.dark_red;
                        }
                        textColor.setTextColor(R.id.tv_on_off, ContextCompat.getColor(context2, i)).setImageResource(R.id.iv_more, isOnOff ? R.mipmap.icon_as_panel_bind_w : R.mipmap.icon_as_panel_bind_red);
                    } else {
                        helper.setBackgroundRes(R.id.layout_item_bg, R.drawable.shape_light_gray_bt_10).setTextColor(R.id.tv_info, ContextCompat.getColor(this.mContext, R.color.color_text_black)).setTextColor(R.id.tv_on_off, ContextCompat.getColor(this.mContext, R.color.color_text_gray)).setImageResource(R.id.iv_more, R.mipmap.icon_as_panel_bind_gray);
                    }
                    if (item.infoName != null) {
                        helper.setText(R.id.tv_info, item.infoName);
                    } else {
                        helper.setText(R.id.tv_info, String.format(Locale.US, "%s%d", this.mContext.getString(R.string.zone), Integer.valueOf(helper.getAdapterPosition() + 1)));
                    }
                    ((AppCompatTextView) helper.getView(R.id.tv_info)).getPaint().setFakeBoldText(true);
                    helper.getView(R.id.layout_item_bg).setOnLongClickListener(new View.OnLongClickListener() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel.3.1
                        @Override // android.view.View.OnLongClickListener
                        public boolean onLongClick(View view) {
                            boolean isOnOff2 = ((ActAsPanelVM) ActAsPanel.this.mViewModel).panelState.getPanelZoneStateList().get(helper.getAdapterPosition()).isOnOff();
                            LHomeLog.i(getClass(), "ison=" + isOnOff2);
                            ((ActAsPanelVM) ActAsPanel.this.mViewModel).sendSingleOnOff(helper.getAdapterPosition(), isOnOff2 ^ true);
                            ((ActAsPanelVM) ActAsPanel.this.mViewModel).panelState.getPanelZoneStateList().get(helper.getAdapterPosition()).setOnOff(isOnOff2 ^ true);
                            notifyDataSetChanged();
                            return true;
                        }
                    });
                }
            };
            this.infoAdapter = baseQuickAdapter2;
            baseQuickAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel$$ExternalSyntheticLambda10
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    ActAsPanel.this.lambda$initRelatedInfoView$11(baseQuickAdapter3, view, i);
                }
            });
            this.infoAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel$$ExternalSyntheticLambda11
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
                public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    ActAsPanel.this.lambda$initRelatedInfoView$12(baseQuickAdapter3, view, i);
                }
            });
            this.infoAdapter.bindToRecyclerView(((ActAsPanelBinding) this.mViewBinding).rvRelatedInfo);
            ((ActAsPanelBinding) this.mViewBinding).rvRelatedInfo.setLayoutManager(new GridLayoutManager(this, ((ActAsPanelVM) this.mViewModel).relatedInfoList.size()));
            ((ActAsPanelBinding) this.mViewBinding).rvRelatedInfo.setHasFixedSize(true);
            ((ActAsPanelBinding) this.mViewBinding).rvRelatedInfo.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel.4
                @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(SizeUtils.dp2px(4.0f), 0, SizeUtils.dp2px(4.0f), 0);
                }
            });
            ((DefaultItemAnimator) ((ActAsPanelBinding) this.mViewBinding).rvRelatedInfo.getItemAnimator()).setSupportsChangeAnimations(false);
            return;
        }
        baseQuickAdapter.setNewData(((ActAsPanelVM) this.mViewModel).relatedInfoList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRelatedInfoView$11(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (((ActAsPanelVM) this.mViewModel).clickSelect(i)) {
            baseQuickAdapter.notifyItemChanged(i);
            ((ActAsPanelVM) this.mViewModel).sendSingleOnOff(i, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRelatedInfoView$12(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (view.getId() == R.id.iv_more) {
            showRelatedDialog(i);
        }
    }

    private void initColorRgbwy() {
        ((ActAsPanelBinding) this.mViewBinding).groupWy.setVisibility(0);
        ((ActAsPanelBinding) this.mViewBinding).groupColorTop.setVisibility(0);
        ((ActAsPanelBinding) this.mViewBinding).ivRgb.setImageResource(R.mipmap.icon_panel_mode_rgb);
        ((ActAsPanelBinding) this.mViewBinding).ivW.setImageResource(R.mipmap.icon_panel_mode_ct);
        initRgbAnnular();
        ((ActAsPanelBinding) this.mViewBinding).csbColorBar.setOnColorChangedListener(new ColorSeekBar.OnColorChangedListener() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel.5
            @Override // com.ltech.smarthome.view.ColorSeekBar.OnColorChangedListener
            public void onColorChangedFinish(float xProgress, int color, boolean isFromUser) {
            }

            @Override // com.ltech.smarthome.view.ColorSeekBar.OnColorChangedListener
            public void onColorChangedStart() {
            }

            @Override // com.ltech.smarthome.view.ColorSeekBar.OnColorChangedListener
            public void onColorChanged(float xProgress, int color, boolean isFromUser) {
                LHomeLog.i(getClass(), isFromUser + "isFromUser");
                if (isFromUser) {
                    float f = xProgress * 255.0f;
                    ((ActAsPanelBinding) ActAsPanel.this.mViewBinding).tvColor.setText(String.format(Locale.US, "%d", Integer.valueOf(Math.round(f))));
                    ((ActAsPanelVM) ActAsPanel.this.mViewModel).getLightCmdHelper().sendWy(ActAsPanel.this, Math.round(f), false);
                }
            }
        });
        ((ActAsPanelBinding) this.mViewBinding).tvColor.setText(String.format(Locale.US, "%d", 0));
    }

    private void initColorRgbw() {
        ((ActAsPanelBinding) this.mViewBinding).groupBrt.setVisibility(0);
        ((ActAsPanelBinding) this.mViewBinding).groupColorTop.setVisibility(0);
        ((ActAsPanelBinding) this.mViewBinding).ivRgb.setImageResource(R.mipmap.icon_panel_mode_rgb);
        ((ActAsPanelBinding) this.mViewBinding).ivW.setImageResource(R.mipmap.icon_panel_mode_w);
        initRgbAnnular();
        ((ActAsPanelBinding) this.mViewBinding).sbBrt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel.6
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((ActAsPanelVM) ActAsPanel.this.mViewModel).getLightCmdHelper().sendRgbBrtHas1to9(ActAsPanel.this, progress, false);
                    ((ActAsPanelBinding) ActAsPanel.this.mViewBinding).tvBrt.setText(((ActAsPanelBinding) ActAsPanel.this.mViewBinding).sbBrt.getProgressHasBelowOne());
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((ActAsPanelVM) ActAsPanel.this.mViewModel).getLightCmdHelper().sendRgbBrtHas1to9(ActAsPanel.this, seekBar.getProgress(), false);
                ((ActAsPanelBinding) ActAsPanel.this.mViewBinding).tvBrt.setText(((ActAsPanelBinding) ActAsPanel.this.mViewBinding).sbBrt.getProgressHasBelowOne());
            }
        });
    }

    private void initColorRgb() {
        ((ActAsPanelBinding) this.mViewBinding).groupBrt.setVisibility(0);
        initRgbAnnular();
        ((ActAsPanelBinding) this.mViewBinding).sbBrt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel.7
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((ActAsPanelVM) ActAsPanel.this.mViewModel).getLightCmdHelper().sendRgbBrt(ActAsPanel.this, LightUtils.progress2Brt(seekBar.getProgress()), false);
                    ((ActAsPanelBinding) ActAsPanel.this.mViewBinding).tvBrt.setText(String.format(Locale.US, "%d%%", Integer.valueOf(seekBar.getProgress())));
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((ActAsPanelVM) ActAsPanel.this.mViewModel).getLightCmdHelper().sendRgbBrt(ActAsPanel.this, LightUtils.progress2Brt(seekBar.getProgress()), true);
                ((ActAsPanelBinding) ActAsPanel.this.mViewBinding).tvBrt.setText(String.format(Locale.US, "%d%%", Integer.valueOf(seekBar.getProgress())));
            }
        });
        ((ActAsPanelBinding) this.mViewBinding).tvBrt.setText(String.format(Locale.US, "%d", 0));
    }

    private void initColorCt() {
        ((ActAsPanelBinding) this.mViewBinding).groupBrt.setVisibility(0);
        initCtAnnular();
        ((ActAsPanelBinding) this.mViewBinding).sbBrt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel.8
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((ActAsPanelVM) ActAsPanel.this.mViewModel).getLightCmdHelper().sendCtBrtHas1to9(ActAsPanel.this, seekBar.getProgress(), false);
                    ((ActAsPanelBinding) ActAsPanel.this.mViewBinding).tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((ActAsPanelVM) ActAsPanel.this.mViewModel).getLightCmdHelper().sendCtBrtHas1to9(ActAsPanel.this, seekBar.getProgress(), true);
                ((ActAsPanelBinding) ActAsPanel.this.mViewBinding).tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
            }
        });
    }

    private void initColorDim() {
        ((ActAsPanelBinding) this.mViewBinding).groupBrt.setVisibility(0);
        ((ActAsPanelBinding) this.mViewBinding).sbBrt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel.9
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                LHomeLog.i(getClass(), "progress=" + progress);
                if (fromUser) {
                    ((ActAsPanelVM) ActAsPanel.this.mViewModel).getLightCmdHelper().sendDimBrtD1Has1to9(ActAsPanel.this, seekBar.getProgress(), false);
                    ((ActAsPanelBinding) ActAsPanel.this.mViewBinding).tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((ActAsPanelVM) ActAsPanel.this.mViewModel).getLightCmdHelper().sendDimBrtD1Has1to9(ActAsPanel.this, seekBar.getProgress(), true);
                ((ActAsPanelBinding) ActAsPanel.this.mViewBinding).tvBrt.setText(((LightBrtBar) seekBar).getProgressHasBelowOne());
            }
        });
        ((ActAsPanelBinding) this.mViewBinding).annularColorPickView.setOnColorChangedListener(new AnnularColorPickView.IColorChangeListener() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel.10
            @Override // com.ltech.smarthome.view.AnnularColorPickView.IColorChangeListener
            public void onColorChanged(int color, float progress) {
                int round;
                LHomeLog.i(getClass(), "progress=" + progress);
                if (progress < 10.0f) {
                    round = Math.round(progress);
                } else {
                    round = Math.round(progress) + 10;
                }
                ((ActAsPanelVM) ActAsPanel.this.mViewModel).getLightCmdHelper().sendDimBrtD1Has1to9(ActAsPanel.this, round, false);
                ((ActAsPanelBinding) ActAsPanel.this.mViewBinding).sbBrt.setProgress(round);
                ((ActAsPanelBinding) ActAsPanel.this.mViewBinding).tvBrt.setText(((ActAsPanelBinding) ActAsPanel.this.mViewBinding).sbBrt.getProgressHasBelowOne());
            }

            @Override // com.ltech.smarthome.view.AnnularColorPickView.IColorChangeListener
            public void onColorChangedFinish(int color, float progress) {
                int round;
                LHomeLog.i(getClass(), "progress1111=" + progress);
                if (progress < 10.0f) {
                    round = Math.round(progress);
                } else {
                    round = Math.round(progress) + 10;
                }
                ((ActAsPanelVM) ActAsPanel.this.mViewModel).getLightCmdHelper().sendDimBrtD1Has1to9(ActAsPanel.this, round, true);
                ((ActAsPanelBinding) ActAsPanel.this.mViewBinding).sbBrt.setProgress(round);
                ((ActAsPanelBinding) ActAsPanel.this.mViewBinding).tvBrt.setText(((ActAsPanelBinding) ActAsPanel.this.mViewBinding).sbBrt.getProgressHasBelowOne());
            }
        });
        Glide.with((FragmentActivity) this).asBitmap().load(Integer.valueOf(R.mipmap.pic_panel_dim)).into((RequestBuilder<Bitmap>) new CustomViewTarget<AnnularColorPickView, Bitmap>(((ActAsPanelBinding) this.mViewBinding).annularColorPickView) { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel.11
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
                ((ActAsPanelBinding) ActAsPanel.this.mViewBinding).annularColorPickView.setBitmapBack(resource);
            }
        });
    }

    private void initRgbAnnular() {
        ((ActAsPanelBinding) this.mViewBinding).annularColorPickView.setOnColorChangedListener(new AnnularColorPickView.IColorChangeListener() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel.12
            @Override // com.ltech.smarthome.view.AnnularColorPickView.IColorChangeListener
            public void onColorChanged(int color, float progress) {
                ((ActAsPanelVM) ActAsPanel.this.mViewModel).getLightCmdHelper().sendRgb(ActAsPanel.this, color, false);
            }

            @Override // com.ltech.smarthome.view.AnnularColorPickView.IColorChangeListener
            public void onColorChangedFinish(int color, float progress) {
                ((ActAsPanelVM) ActAsPanel.this.mViewModel).getLightCmdHelper().sendRgb(ActAsPanel.this, color, true);
            }
        });
        Glide.with((FragmentActivity) this).asBitmap().load(Integer.valueOf(R.mipmap.pic_panel_rgb)).into((RequestBuilder<Bitmap>) new CustomViewTarget<AnnularColorPickView, Bitmap>(((ActAsPanelBinding) this.mViewBinding).annularColorPickView) { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel.13
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
                ((ActAsPanelBinding) ActAsPanel.this.mViewBinding).annularColorPickView.setBitmapBack(resource);
            }
        });
    }

    private void initCtAnnular() {
        ((ActAsPanelBinding) this.mViewBinding).annularColorPickView.setOnColorChangedListener(new AnnularColorPickView.IColorChangeListener() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel.14
            @Override // com.ltech.smarthome.view.AnnularColorPickView.IColorChangeListener
            public void onColorChanged(int color, float progress) {
                ((ActAsPanelVM) ActAsPanel.this.mViewModel).getLightCmdHelper().sendCW(ActAsPanel.this, 255 - Math.round((progress / 100.0f) * 255.0f), false);
            }

            @Override // com.ltech.smarthome.view.AnnularColorPickView.IColorChangeListener
            public void onColorChangedFinish(int color, float progress) {
                ((ActAsPanelVM) ActAsPanel.this.mViewModel).getLightCmdHelper().sendCW(ActAsPanel.this, 255 - Math.round((progress / 100.0f) * 255.0f), true);
            }
        });
        Glide.with((FragmentActivity) this).asBitmap().load(Integer.valueOf(R.mipmap.pic_panel_ct)).into((RequestBuilder<Bitmap>) new CustomViewTarget<AnnularColorPickView, Bitmap>(((ActAsPanelBinding) this.mViewBinding).annularColorPickView) { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel.15
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
                ((ActAsPanelBinding) ActAsPanel.this.mViewBinding).annularColorPickView.setBitmapBack(resource);
            }
        });
    }

    private void showRelatedDialog(final int position) {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        if (this.infoAdapter.getData().get(position).infoName != null) {
            arrayList.add(getString(R.string.reset_relate));
            selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActAsPanel.this.lambda$showRelatedDialog$13(position, (Integer) obj);
                }
            }).setSelectList(arrayList);
        } else {
            arrayList.add(getString(R.string.relate_light));
            selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel$$ExternalSyntheticLambda7
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActAsPanel.this.lambda$showRelatedDialog$14(position, (Integer) obj);
                }
            }).setSelectList(arrayList);
        }
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showRelatedDialog$13(int i, Integer num) {
        showUnbindDialog(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showRelatedDialog$14(int i, Integer num) {
        if (num.intValue() == 1) {
            NavUtils.destination(ActRelatedBleGroup.class).withLong(Constants.PLACE_ID, ((ActAsPanelVM) this.mViewModel).controlObject.getValue().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActAsPanelVM) this.mViewModel).controlObject.getValue().getId()).withInt(Constants.RELATED_POSITION, i).withInt(Constants.LIGHT_TYPE, ((ActAsPanelVM) this.mViewModel).lightType).withBoolean(Constants.NEED_RESET, true).withString(Constants.PRODUCT_ID, ((ActAsPanelVM) this.mViewModel).controlObject.getValue().getProductId()).withDefaultRequestCode().navigation(this);
        } else {
            NavUtils.destination(ActRelatedBleLightDeviceAndGroup.class).withLong(Constants.PLACE_ID, ((ActAsPanelVM) this.mViewModel).controlObject.getValue().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActAsPanelVM) this.mViewModel).controlObject.getValue().getId()).withInt(Constants.RELATED_POSITION, i).withInt(Constants.LIGHT_TYPE, ((ActAsPanelVM) this.mViewModel).lightType).withBoolean(Constants.NEED_RESET, true).withString(Constants.PRODUCT_ID, ((ActAsPanelVM) this.mViewModel).controlObject.getValue().getProductId()).withDefaultRequestCode().navigation(this);
        }
    }

    private void showRgbBrtDialog() {
        PanelBrtDialog.asDefault().setBrtTip(getString(R.string.rgb_brt)).setOnOffTip(getString(R.string.rgb_light_tip)).setOnOff(((ActAsPanelVM) this.mViewModel).panelZoneState != null ? ((ActAsPanelVM) this.mViewModel).panelZoneState.isRgbOnOff() : false).setBrtProgress(((ActAsPanelVM) this.mViewModel).controlObject.getValue().getDeviceState() != null ? ((ActAsPanelVM) this.mViewModel).controlObject.getValue().getDeviceState().getRgbBrt() : 0).setStateChangedListener(new PanelBrtDialog.OnStateChangedListener() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel.16
            @Override // com.ltech.smarthome.view.dialog.PanelBrtDialog.OnStateChangedListener
            public void onBrtChanged(int brtProgress, boolean finish) {
                if (((ActAsPanelVM) ActAsPanel.this.mViewModel).panelZoneState != null) {
                    ((ActAsPanelVM) ActAsPanel.this.mViewModel).panelZoneState.setRgbBrt(brtProgress);
                }
                ((ActAsPanelVM) ActAsPanel.this.mViewModel).getLightCmdHelper().sendRgbBrtHas1to9(ActAsPanel.this, brtProgress, finish);
            }

            @Override // com.ltech.smarthome.view.dialog.PanelBrtDialog.OnStateChangedListener
            public void onOnOffChanged(boolean on) {
                ((ActAsPanelVM) ActAsPanel.this.mViewModel).getLightCmdHelper().sendRgbOnOff(ActAsPanel.this, on);
            }
        }).showDialog(this);
    }

    private void showWyBrtDialog() {
        PanelBrtDialog.asDefault().setBrtTip(getString(R.string.wy_brt)).setOnOffTip(getString(R.string.wy_light_tip)).setOnOff(((ActAsPanelVM) this.mViewModel).panelZoneState != null ? ((ActAsPanelVM) this.mViewModel).panelZoneState.iswOrWyOnOff() : false).setBrtProgress(((ActAsPanelVM) this.mViewModel).controlObject.getValue().getDeviceState() != null ? ((ActAsPanelVM) this.mViewModel).controlObject.getValue().getDeviceState().getWyBrt() : 0).setStateChangedListener(new PanelBrtDialog.OnStateChangedListener() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel.17
            @Override // com.ltech.smarthome.view.dialog.PanelBrtDialog.OnStateChangedListener
            public void onBrtChanged(int brtProgress, boolean finish) {
                if (((ActAsPanelVM) ActAsPanel.this.mViewModel).panelZoneState != null) {
                    ((ActAsPanelVM) ActAsPanel.this.mViewModel).panelZoneState.setWyBrt(brtProgress);
                }
                ((ActAsPanelVM) ActAsPanel.this.mViewModel).getLightCmdHelper().sendWyBrtHas1to9(ActAsPanel.this, brtProgress, finish);
            }

            @Override // com.ltech.smarthome.view.dialog.PanelBrtDialog.OnStateChangedListener
            public void onOnOffChanged(boolean on) {
                ((ActAsPanelVM) ActAsPanel.this.mViewModel).getLightCmdHelper().sendWyOnOff(ActAsPanel.this, on);
            }
        }).showDialog(this);
    }

    private void showWBrtDialog() {
        PanelBrtDialog.asDefault().setBrtTip(getString(R.string.w_value)).setOnOffTip(getString(R.string.w_light_tip)).setOnOff(((ActAsPanelVM) this.mViewModel).panelZoneState != null ? ((ActAsPanelVM) this.mViewModel).panelZoneState.iswOrWyOnOff() : false).setBrtProgress(((ActAsPanelVM) this.mViewModel).controlObject.getValue().getDeviceState() != null ? ((ActAsPanelVM) this.mViewModel).controlObject.getValue().getDeviceState().getWyBrt() : 0).setStateChangedListener(new PanelBrtDialog.OnStateChangedListener() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel.18
            @Override // com.ltech.smarthome.view.dialog.PanelBrtDialog.OnStateChangedListener
            public void onBrtChanged(int brtProgress, boolean finish) {
                ((ActAsPanelVM) ActAsPanel.this.mViewModel).getLightCmdHelper().sendWHas1to9(ActAsPanel.this, brtProgress, finish);
                if (((ActAsPanelVM) ActAsPanel.this.mViewModel).panelZoneState != null) {
                    ((ActAsPanelVM) ActAsPanel.this.mViewModel).panelZoneState.setwOrWy(brtProgress);
                }
            }

            @Override // com.ltech.smarthome.view.dialog.PanelBrtDialog.OnStateChangedListener
            public void onOnOffChanged(boolean on) {
                ((ActAsPanelVM) ActAsPanel.this.mViewModel).getLightCmdHelper().sendWOnOff(ActAsPanel.this, on);
            }
        }).showDialog(this);
    }

    private void showUnbindDialog(final int position) {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.unbind_tip)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.aspanel.ActAsPanel$$ExternalSyntheticLambda6
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showUnbindDialog$15;
                lambda$showUnbindDialog$15 = ActAsPanel.this.lambda$showUnbindDialog$15(position, baseDialog, view);
                return lambda$showUnbindDialog$15;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showUnbindDialog$15(int i, BaseDialog baseDialog, View view) {
        ((ActAsPanelVM) this.mViewModel).unBindRelateInfo(i);
        return false;
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseControlActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 5004 || resultCode == 5003) {
            ((ActAsPanelVM) this.mViewModel).controlObject.setValue(Injection.repo().device().getDeviceByDeviceId(((ActAsPanelVM) this.mViewModel).controlObject.getValue().getDeviceId()));
        }
    }

    private void initTvBrtOrWY(Device device) {
        int rgbBrt;
        try {
            if (((ActAsPanelVM) this.mViewModel).controlObject.getValue() == null) {
                LHomeLog.i(getClass(), "mViewModel.controlObject.getValue()==null");
                ((ActAsPanelVM) this.mViewModel).controlObject.setValue(device);
            } else if (!HelpUtils.compareObject(((ActAsPanelVM) this.mViewModel).controlObject.getValue(), device)) {
                LHomeLog.i(getClass(), "device changed");
                ((ActAsPanelVM) this.mViewModel).controlObject.setValue(device);
            }
        } catch (Exception e) {
            LHomeLog.i(getClass(), "getDeviceFromDb Exception" + e.toString());
            e.printStackTrace();
        }
        if (((ActAsPanelVM) this.mViewModel).lightType == 1 || 27 == ((ActAsPanelVM) this.mViewModel).lightType) {
            rgbBrt = ((ActAsPanelVM) this.mViewModel).controlObject.getValue().getDeviceState() != null ? ((ActAsPanelVM) this.mViewModel).controlObject.getValue().getDeviceState().getRgbBrt() : 0;
            LHomeLog.i(getClass(), "brtProgress=" + rgbBrt + "___device=" + device.getDeviceState().toString());
            ((ActAsPanelBinding) this.mViewBinding).sbBrt.setProgress(rgbBrt);
            ((ActAsPanelBinding) this.mViewBinding).tvBrt.setText(((ActAsPanelBinding) this.mViewBinding).sbBrt.getProgressHasBelowOne());
        } else if (((ActAsPanelVM) this.mViewModel).lightType == 2 || 28 == ((ActAsPanelVM) this.mViewModel).lightType) {
            rgbBrt = ((ActAsPanelVM) this.mViewModel).controlObject.getValue().getDeviceState() != null ? ((ActAsPanelVM) this.mViewModel).controlObject.getValue().getDeviceState().getWyBrt() : 0;
            LHomeLog.i(getClass(), "brtProgress=" + rgbBrt);
            ((ActAsPanelBinding) this.mViewBinding).sbBrt.setProgress(rgbBrt);
            ((ActAsPanelBinding) this.mViewBinding).tvBrt.setText(((ActAsPanelBinding) this.mViewBinding).sbBrt.getProgressHasBelowOne());
        } else if (((ActAsPanelVM) this.mViewModel).lightType == 4 || 29 == ((ActAsPanelVM) this.mViewModel).lightType) {
            rgbBrt = ((ActAsPanelVM) this.mViewModel).controlObject.getValue().getDeviceState() != null ? ((ActAsPanelVM) this.mViewModel).controlObject.getValue().getDeviceState().getRgbBrt() : 0;
            ((ActAsPanelBinding) this.mViewBinding).tvBrtTip.setText(R.string.rgb_brt);
            ((ActAsPanelBinding) this.mViewBinding).sbBrt.setProgress(rgbBrt);
            ((ActAsPanelBinding) this.mViewBinding).tvBrt.setText(((ActAsPanelBinding) this.mViewBinding).sbBrt.getProgressHasBelowOne());
        } else if (((ActAsPanelVM) this.mViewModel).lightType == 5 || 30 == ((ActAsPanelVM) this.mViewModel).lightType) {
            int wy = ((ActAsPanelVM) this.mViewModel).controlObject.getValue().getDeviceState() != null ? ((ActAsPanelVM) this.mViewModel).controlObject.getValue().getDeviceState().getWy() : 0;
            ((ActAsPanelBinding) this.mViewBinding).csbColorBar.setProgress(LightUtils.c2percent(wy));
            ((ActAsPanelBinding) this.mViewBinding).tvColor.setText(String.format(Locale.US, "%d", Integer.valueOf(wy)));
        }
        startDeviceObserver();
    }
}