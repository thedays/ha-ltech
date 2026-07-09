package com.ltech.smarthome.ui.device.screenpanel;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSetScreenDisplayBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.ScreenIconUtils;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.SetScreenDisplayDialog;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.message.utils.StringUtils;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class ActNewSetScreenDisplay extends VMActivity<ActSetScreenDisplayBinding, ActSmartPanelVM> {
    private int[] backgroundResArray;
    private int controlType;
    protected BaseQuickAdapter<String, BaseViewHolder> displayAdapter;
    protected BaseQuickAdapter<RelateInfoItem, BaseViewHolder> infoAdapter;
    private GridLayoutManager mGridLayoutManager;
    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mScreenGridLayoutManager;
    private LinearLayoutManager mScreenLinearLayoutManager;
    private RelatedInfoExtParam.RelateInfo relateInfo;
    protected BaseQuickAdapter<RelateInfoItem, BaseViewHolder> screenAdapter;
    private int[] screenBgRes;
    private int spanCount;
    private int zoneCount;
    private int selectPosition = -1;
    private String name = ".";

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_set_screen_display;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.set_display));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        LHomeLog.i(getClass(), "message_send startObserve() enter");
        ((ActSmartPanelVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActSmartPanelVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActSmartPanelVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ((ActSmartPanelVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        if (((ActSmartPanelVM) this.mViewModel).groupControl) {
            startGroupObserve();
        } else {
            startDeviceObserve();
        }
        ((ActSmartPanelVM) this.mViewModel).updateUIEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewSetScreenDisplay$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActNewSetScreenDisplay.this.lambda$startObserve$0(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Object obj) {
        ((ActSmartPanelVM) this.mViewModel).controlObject.setValue(obj);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        startObjectObserve();
    }

    private void startObjectObserve() {
        if (((ActSmartPanelVM) this.mViewModel).groupControl) {
            ((ActSmartPanelVM) this.mViewModel).controlObject.setValue(Injection.repo().group().getGroupById(((ActSmartPanelVM) this.mViewModel).controlId));
            return;
        }
        Device deviceById = Injection.repo().device().getDeviceById(((ActSmartPanelVM) this.mViewModel).controlId);
        try {
            if (((ActSmartPanelVM) this.mViewModel).controlObject.getValue() == null) {
                ((ActSmartPanelVM) this.mViewModel).controlObject.setValue(deviceById);
            } else {
                if (HelpUtils.compareObject((Device) ((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), deviceById)) {
                    return;
                }
                ((ActSmartPanelVM) this.mViewModel).controlObject.setValue(deviceById);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startDeviceObserve() {
        ((ActSmartPanelVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewSetScreenDisplay$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActNewSetScreenDisplay.this.lambda$startDeviceObserve$1(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startDeviceObserve$1(Object obj) {
        LHomeLog.i(getClass(), "message_send (device)mViewModel.controlObject enter");
        if (obj instanceof Device) {
            Device device = (Device) obj;
            String productId = device.getProductId();
            productId.hashCode();
            if (productId.equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                this.spanCount = 3;
                this.zoneCount = 6;
                this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s6pro);
            }
            initRelatedInfoView(device);
        }
    }

    private void startGroupObserve() {
        ((ActSmartPanelVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewSetScreenDisplay$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActNewSetScreenDisplay.this.lambda$startGroupObserve$2(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startGroupObserve$2(Object obj) {
        if (obj instanceof Group) {
            Group group = (Group) obj;
            if (ProductRepository.getLightColorType((Object) group) == 19) {
                this.spanCount = 3;
                this.zoneCount = 6;
                this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s6pro);
            } else {
                this.spanCount = 1;
                this.zoneCount = 1;
                this.backgroundResArray = HelpUtils.getDrawableResourceArray(this, R.array.smart_panel_bg_res_s1pro);
            }
            initRelatedInfoView(group);
        }
    }

    private void initRelatedInfoView(Device device) {
        ((ActSmartPanelVM) this.mViewModel).initPanelState(device);
        ((ActSmartPanelVM) this.mViewModel).initRelateInfoList(device);
        initInfoAdapter();
        initScreenAdapter();
        this.screenAdapter.replaceData(((ActSmartPanelVM) this.mViewModel).relatedInfoList);
        initDisplayAdapter();
    }

    private void initRelatedInfoView(Group group) {
        ((ActSmartPanelVM) this.mViewModel).initPanelState(group);
        ((ActSmartPanelVM) this.mViewModel).initRelateInfoList(group);
        initInfoAdapter();
        initScreenAdapter();
        this.screenAdapter.replaceData(((ActSmartPanelVM) this.mViewModel).relatedInfoList);
        initDisplayAdapter();
    }

    private void initInfoAdapter() {
        BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = this.infoAdapter;
        if (baseQuickAdapter == null) {
            BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<RelateInfoItem, BaseViewHolder>(R.layout.item_smart_panel_key, ((ActSmartPanelVM) this.mViewModel).relatedInfoList) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewSetScreenDisplay.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, RelateInfoItem item) {
                    LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.layout_content);
                    ViewGroup.LayoutParams layoutParams = helper.itemView.getLayoutParams();
                    if (ActNewSetScreenDisplay.this.spanCount == 3) {
                        linearLayout.setPadding(0, 0, 0, Utils.dip2px(this.mContext, 30.0f));
                        layoutParams.height = ActNewSetScreenDisplay.this.mGridLayoutManager.getHeight() / 2;
                        linearLayout.setGravity(17);
                    } else {
                        linearLayout.setGravity(81);
                        linearLayout.setPadding(0, 0, 0, Utils.dip2px(this.mContext, 30.0f));
                        layoutParams.height = ActNewSetScreenDisplay.this.mLinearLayoutManager.getHeight();
                    }
                    helper.itemView.setBackgroundResource(ActNewSetScreenDisplay.this.backgroundResArray[helper.getBindingAdapterPosition()]);
                    helper.setText(R.id.tv_device_name, ActNewSetScreenDisplay.this.name);
                    helper.setTextColor(R.id.tv_device_name, ContextCompat.getColor(ActNewSetScreenDisplay.this, R.color.colorPrimary));
                    ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
                }
            };
            this.infoAdapter = baseQuickAdapter2;
            baseQuickAdapter2.bindToRecyclerView(((ActSetScreenDisplayBinding) this.mViewBinding).rvKeyInfo);
            if (this.spanCount == 3) {
                RecyclerView recyclerView = ((ActSetScreenDisplayBinding) this.mViewBinding).rvKeyInfo;
                GridLayoutManager gridLayoutManager = new GridLayoutManager((Context) this, this.spanCount, 1, true);
                this.mGridLayoutManager = gridLayoutManager;
                recyclerView.setLayoutManager(gridLayoutManager);
            } else {
                RecyclerView recyclerView2 = ((ActSetScreenDisplayBinding) this.mViewBinding).rvKeyInfo;
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                this.mLinearLayoutManager = linearLayoutManager;
                recyclerView2.setLayoutManager(linearLayoutManager);
                ((ActSetScreenDisplayBinding) this.mViewBinding).rvKeyInfo.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewSetScreenDisplay.2
                    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        super.getItemOffsets(outRect, view, parent, state);
                        outRect.set(0, 0, 0, 0);
                    }
                });
            }
            ((ActSetScreenDisplayBinding) this.mViewBinding).rvKeyInfo.setHasFixedSize(true);
            ((DefaultItemAnimator) ((ActSetScreenDisplayBinding) this.mViewBinding).rvKeyInfo.getItemAnimator()).setSupportsChangeAnimations(false);
            return;
        }
        baseQuickAdapter.replaceData(((ActSmartPanelVM) this.mViewModel).relatedInfoList);
    }

    private void initScreenAdapter() {
        if (this.screenAdapter != null) {
            return;
        }
        BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<RelateInfoItem, BaseViewHolder>(R.layout.item_screen_panel_mult_line, ((ActSmartPanelVM) this.mViewModel).relatedInfoList) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewSetScreenDisplay.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, RelateInfoItem item) {
                RelatedInfoExtParam.RelateInfo relateInfo;
                ViewGroup.LayoutParams layoutParams = helper.itemView.getLayoutParams();
                if (ActNewSetScreenDisplay.this.spanCount == 3) {
                    layoutParams.height = ActNewSetScreenDisplay.this.mScreenGridLayoutManager.getHeight() / 2;
                } else {
                    layoutParams.height = ActNewSetScreenDisplay.this.mScreenLinearLayoutManager.getHeight();
                }
                helper.setGone(R.id.imaginary_line, helper.getAdapterPosition() >= ActNewSetScreenDisplay.this.zoneCount / 2);
                ((LinearLayout) helper.getView(R.id.layout_item_bg)).setGravity(helper.getAdapterPosition() < ActNewSetScreenDisplay.this.zoneCount / 2 ? 49 : 81);
                boolean z = ((ActSmartPanelVM) ActNewSetScreenDisplay.this.mViewModel).relateInfoAssistant.getSwitchScreenBigIcon() == 2;
                ViewGroup.LayoutParams layoutParams2 = ((ImageView) helper.getView(R.id.iv_screen)).getLayoutParams();
                TextView textView = (TextView) helper.getView(R.id.tv_screen);
                if (z) {
                    layoutParams2.width = SizeUtils.dp2px(35.0f);
                    layoutParams2.height = SizeUtils.dp2px(35.0f);
                    textView.setTextSize(18.0f);
                } else {
                    layoutParams2.width = SizeUtils.dp2px(25.0f);
                    layoutParams2.height = SizeUtils.dp2px(25.0f);
                    textView.setTextSize(13.0f);
                }
                if (ActNewSetScreenDisplay.this.selectPosition == -1 || ActNewSetScreenDisplay.this.relateInfo == null) {
                    relateInfo = item.relateInfo;
                } else {
                    relateInfo = ActNewSetScreenDisplay.this.relateInfo;
                }
                if (ActNewSetScreenDisplay.this.selectPosition == -1 || ActNewSetScreenDisplay.this.selectPosition == helper.getBindingAdapterPosition()) {
                    if (relateInfo == null || relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeNone.getValue()) {
                        helper.setGone(R.id.iv_screen, false);
                        helper.setGone(R.id.tv_screen, true);
                        helper.setText(R.id.tv_screen, "");
                        if (((ActSmartPanelVM) ActNewSetScreenDisplay.this.mViewModel).relayObjectMap == null || !((ActSmartPanelVM) ActNewSetScreenDisplay.this.mViewModel).relayObjectMap.containsKey(Integer.valueOf(helper.getAdapterPosition() + 1))) {
                            return;
                        }
                        Object obj = ((ActSmartPanelVM) ActNewSetScreenDisplay.this.mViewModel).relayObjectMap.get(Integer.valueOf(helper.getAdapterPosition() + 1));
                        if (obj instanceof Device) {
                            helper.setText(R.id.tv_screen, ((Device) obj).getName());
                            return;
                        } else {
                            if (obj instanceof Group) {
                                helper.setText(R.id.tv_screen, ((Group) obj).getName());
                                return;
                            }
                            return;
                        }
                    }
                    if (relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeIcon.getValue()) {
                        helper.setGone(R.id.iv_screen, true);
                        helper.setGone(R.id.tv_screen, false);
                        if (ScreenIconUtils.getScreenIconRes(relateInfo.iconIndex) != 0) {
                            helper.setImageResource(R.id.iv_screen, ScreenIconUtils.getScreenIconRes(relateInfo.iconIndex));
                            return;
                        }
                        return;
                    }
                    if (relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeIconWord.getValue()) {
                        helper.setGone(R.id.iv_screen, true);
                        helper.setGone(R.id.tv_screen, true);
                        helper.setText(R.id.tv_screen, relateInfo.screenStr);
                        if (z) {
                            helper.setGone(R.id.iv_screen, false);
                            helper.setText(R.id.tv_screen, StringUtils.replaceString(relateInfo.screenStr, true));
                        } else {
                            helper.setText(R.id.tv_screen, relateInfo.screenStr);
                        }
                        if (ScreenIconUtils.getScreenIconRes(relateInfo.iconIndex) != 0) {
                            helper.setImageResource(R.id.iv_screen, ScreenIconUtils.getScreenIconRes(relateInfo.iconIndex));
                            return;
                        }
                        return;
                    }
                    helper.setGone(R.id.iv_screen, false);
                    helper.setGone(R.id.tv_screen, true);
                    if (z) {
                        helper.setText(R.id.tv_screen, StringUtils.replaceString(relateInfo.screenStr, false));
                        return;
                    } else {
                        helper.setText(R.id.tv_screen, relateInfo.screenStr);
                        return;
                    }
                }
                helper.setGone(R.id.iv_screen, false);
                helper.setGone(R.id.tv_screen, false);
            }
        };
        this.screenAdapter = baseQuickAdapter;
        baseQuickAdapter.bindToRecyclerView(((ActSetScreenDisplayBinding) this.mViewBinding).rvScreenInfo);
        if (this.spanCount == 3) {
            RecyclerView recyclerView = ((ActSetScreenDisplayBinding) this.mViewBinding).rvScreenInfo;
            GridLayoutManager gridLayoutManager = new GridLayoutManager((Context) this, this.spanCount, 1, true);
            this.mScreenGridLayoutManager = gridLayoutManager;
            recyclerView.setLayoutManager(gridLayoutManager);
        } else {
            RecyclerView recyclerView2 = ((ActSetScreenDisplayBinding) this.mViewBinding).rvScreenInfo;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            this.mScreenLinearLayoutManager = linearLayoutManager;
            recyclerView2.setLayoutManager(linearLayoutManager);
            ((ActSetScreenDisplayBinding) this.mViewBinding).rvScreenInfo.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewSetScreenDisplay.4
                @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(0, 0, 0, 0);
                }
            });
        }
        ((ActSetScreenDisplayBinding) this.mViewBinding).rvScreenInfo.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActSetScreenDisplayBinding) this.mViewBinding).rvScreenInfo.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    private void initDisplayAdapter() {
        final boolean z = ((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getSwitchScreenBigIcon() == 2;
        if (this.displayAdapter != null) {
            return;
        }
        String[] stringArray = getResources().getStringArray(R.array.screen_display_s6pro);
        int i = this.spanCount;
        if (i == 1) {
            stringArray = new String[]{getString(R.string.screen_dislay_1)};
        } else if (i == 2) {
            stringArray = new String[]{getString(R.string.screen_dislay_1), getString(R.string.screen_dislay_2)};
        }
        BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(this, R.layout.item_screen_display, Arrays.asList(stringArray)) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewSetScreenDisplay.5
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_name, item);
            }
        };
        this.displayAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewSetScreenDisplay$$ExternalSyntheticLambda4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                ActNewSetScreenDisplay.this.lambda$initDisplayAdapter$3(z, baseQuickAdapter2, view, i2);
            }
        });
        this.displayAdapter.bindToRecyclerView(((ActSetScreenDisplayBinding) this.mViewBinding).rvDisplay);
        ((ActSetScreenDisplayBinding) this.mViewBinding).rvDisplay.setLayoutManager(new LinearLayoutManager(this));
        ((ActSetScreenDisplayBinding) this.mViewBinding).rvDisplay.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewSetScreenDisplay.7
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(5.0f), ConvertUtils.dp2px(5.0f), ConvertUtils.dp2px(5.0f), ConvertUtils.dp2px(5.0f));
            }
        });
        ((ActSetScreenDisplayBinding) this.mViewBinding).rvDisplay.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDisplayAdapter$3(boolean z, BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.selectPosition = i;
        this.relateInfo = null;
        this.screenAdapter.notifyDataSetChanged();
        int i2 = 0;
        SetScreenDisplayDialog.setIconIndex(((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getRelateInfo(i) == null ? 0 : ((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getRelateInfo(i).iconIndex);
        SetScreenDisplayDialog showTab = SetScreenDisplayDialog.asDefault(z).setScreenText(((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getRelateInfo(i) == null ? "" : ((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getRelateInfo(i).screenStr).setShowTab(ProductRepository.getLightColorType(((ActSmartPanelVM) this.mViewModel).controlObject.getValue()) != 19);
        if (ProductRepository.getLightColorType(((ActSmartPanelVM) this.mViewModel).controlObject.getValue()) != 19 && ((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getRelateInfo(i) != null && (((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getRelateInfo(i).screenType != 3 || !z)) {
            i2 = ((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getRelateInfo(i).screenType;
        }
        showTab.setScreenType(i2).setOnDialogCallback(new SetScreenDisplayDialog.OnDialogCallback() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewSetScreenDisplay.6
            @Override // com.ltech.smarthome.view.dialog.SetScreenDisplayDialog.OnDialogCallback
            public boolean onSave() {
                ActNewSetScreenDisplay actNewSetScreenDisplay = ActNewSetScreenDisplay.this;
                actNewSetScreenDisplay.setScreenData(actNewSetScreenDisplay.selectPosition);
                return false;
            }

            @Override // com.ltech.smarthome.view.dialog.SetScreenDisplayDialog.OnDialogCallback
            public void onCancel() {
                ActNewSetScreenDisplay.this.selectPosition = -1;
                ActNewSetScreenDisplay.this.screenAdapter.notifyDataSetChanged();
            }

            @Override // com.ltech.smarthome.view.dialog.SetScreenDisplayDialog.OnDialogCallback
            public void onScreenChanged(int screenType, String screenStr, int iconIndex) {
                ActNewSetScreenDisplay.this.relateInfo = new RelatedInfoExtParam.RelateInfo();
                ActNewSetScreenDisplay.this.relateInfo.screenType = screenType;
                ActNewSetScreenDisplay.this.relateInfo.screenStr = screenStr;
                ActNewSetScreenDisplay.this.relateInfo.iconIndex = iconIndex;
                ActNewSetScreenDisplay.this.screenAdapter.notifyDataSetChanged();
            }
        }).setHeight((int) (getWindowManager().getDefaultDisplay().getHeight() * 0.17f)).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setScreenData(final int position) {
        byte[] bArr;
        int i;
        byte[] bArr2;
        int i2;
        byte[] bArr3;
        int i3;
        RelatedInfoExtParam.RelateInfo relateInfo = this.relateInfo;
        if (relateInfo == null) {
            return;
        }
        final int i4 = 1;
        try {
        } catch (UnsupportedEncodingException e) {
            e = e;
            bArr = null;
        }
        if (relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeWord.getValue()) {
            String[] split = this.relateInfo.screenStr.split("\n");
            byte[] bytes = split[0].getBytes("gb2312");
            try {
                bArr3 = split.length > 1 ? split[1].getBytes("gb2312") : null;
                r4 = bytes;
            } catch (UnsupportedEncodingException e2) {
                e = e2;
                bArr = bytes;
                i = 1;
                e.printStackTrace();
                bArr2 = null;
                i2 = i;
                r4 = bArr;
                showLoadingDialog("");
                final int i5 = i2;
                final byte[] bArr4 = bArr2;
                CmdAssistant.getDeviceAssistant(((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), new int[0]).setPanelScreenData(this.activity, position, i4, r4, i2, bArr2, new IAction() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewSetScreenDisplay$$ExternalSyntheticLambda0
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActNewSetScreenDisplay.this.lambda$setScreenData$4(position, i4, r4, i5, bArr4, (ResponseMsg) obj);
                    }
                });
            }
        } else {
            if (this.relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeIcon.getValue()) {
                try {
                    int sendIconIndex = ScreenIconUtils.getSendIconIndex(this.relateInfo.iconIndex);
                    bArr3 = null;
                    r4 = new byte[]{(byte) ((sendIconIndex >> 8) & 255), (byte) (sendIconIndex & 255)};
                    i3 = 0;
                } catch (UnsupportedEncodingException e3) {
                    e = e3;
                    bArr = null;
                    i4 = 2;
                    i = 0;
                    e.printStackTrace();
                    bArr2 = null;
                    i2 = i;
                    r4 = bArr;
                    showLoadingDialog("");
                    final int i52 = i2;
                    final byte[] bArr42 = bArr2;
                    CmdAssistant.getDeviceAssistant(((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), new int[0]).setPanelScreenData(this.activity, position, i4, r4, i2, bArr2, new IAction() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewSetScreenDisplay$$ExternalSyntheticLambda0
                        @Override // com.ltech.smarthome.base.IAction
                        public final void act(Object obj) {
                            ActNewSetScreenDisplay.this.lambda$setScreenData$4(position, i4, r4, i52, bArr42, (ResponseMsg) obj);
                        }
                    });
                }
            } else if (this.relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeIconWord.getValue()) {
                try {
                    int sendIconIndex2 = ScreenIconUtils.getSendIconIndex(this.relateInfo.iconIndex);
                    bArr = new byte[]{(byte) ((sendIconIndex2 >> 8) & 255), (byte) (sendIconIndex2 & 255)};
                } catch (UnsupportedEncodingException e4) {
                    e = e4;
                    bArr = null;
                }
                try {
                    bArr3 = this.relateInfo.screenStr.getBytes("gb2312");
                    r4 = bArr;
                    i3 = 1;
                } catch (UnsupportedEncodingException e5) {
                    e = e5;
                    i4 = 2;
                    i = 1;
                    e.printStackTrace();
                    bArr2 = null;
                    i2 = i;
                    r4 = bArr;
                    showLoadingDialog("");
                    final int i522 = i2;
                    final byte[] bArr422 = bArr2;
                    CmdAssistant.getDeviceAssistant(((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), new int[0]).setPanelScreenData(this.activity, position, i4, r4, i2, bArr2, new IAction() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewSetScreenDisplay$$ExternalSyntheticLambda0
                        @Override // com.ltech.smarthome.base.IAction
                        public final void act(Object obj) {
                            ActNewSetScreenDisplay.this.lambda$setScreenData$4(position, i4, r4, i522, bArr422, (ResponseMsg) obj);
                        }
                    });
                }
            } else {
                bArr3 = null;
            }
            i4 = 2;
            i2 = i3;
            bArr2 = bArr3;
            showLoadingDialog("");
            final int i5222 = i2;
            final byte[] bArr4222 = bArr2;
            CmdAssistant.getDeviceAssistant(((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), new int[0]).setPanelScreenData(this.activity, position, i4, r4, i2, bArr2, new IAction() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewSetScreenDisplay$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActNewSetScreenDisplay.this.lambda$setScreenData$4(position, i4, r4, i5222, bArr4222, (ResponseMsg) obj);
                }
            });
        }
        i3 = 1;
        i2 = i3;
        bArr2 = bArr3;
        showLoadingDialog("");
        final int i52222 = i2;
        final byte[] bArr42222 = bArr2;
        CmdAssistant.getDeviceAssistant(((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), new int[0]).setPanelScreenData(this.activity, position, i4, r4, i2, bArr2, new IAction() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActNewSetScreenDisplay$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActNewSetScreenDisplay.this.lambda$setScreenData$4(position, i4, r4, i52222, bArr42222, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setScreenData$4(int i, int i2, byte[] bArr, int i3, byte[] bArr2, ResponseMsg responseMsg) {
        dismissLoadingDialog();
        if (responseMsg != null && (responseMsg.getStateCode() == 0 || responseMsg.getStateCode() == 153)) {
            RelatedInfoExtParam.RelateInfo relateInfo = ((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.getRelateInfo(i);
            if (relateInfo == null) {
                relateInfo = new RelatedInfoExtParam.RelateInfo();
            }
            relateInfo.screenType = this.relateInfo.screenType;
            relateInfo.screenStr = this.relateInfo.screenStr;
            relateInfo.iconIndex = this.relateInfo.iconIndex;
            ((ActSmartPanelVM) this.mViewModel).relateInfoAssistant.setRelateInfo(i, relateInfo);
            ((ActSmartPanelVM) this.mViewModel).uploadData();
            if (((ActSmartPanelVM) this.mViewModel).groupControl) {
                return;
            }
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.TEXT, CmdAssistant.getDeviceAssistant(((ActSmartPanelVM) this.mViewModel).controlObject.getValue(), new int[0]).setPanelScreenData(i, i2, bArr, i3, bArr2));
            ReplaceHelper.instance().backupIndexData(this.activity, ((Device) ((ActSmartPanelVM) this.mViewModel).controlObject.getValue()).getDeviceId(), i + 1);
            return;
        }
        showErrorDialog(getString(R.string.save_fail));
    }
}