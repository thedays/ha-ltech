package com.ltech.smarthome.ui.scene;

import android.view.View;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelectSonosActionBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.ui.device.sonos.SonosResponse;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NameRepository;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.dialog.SelectListSubLineDialog;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.param.DeviceCmdParam;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSelectSonosForAction extends BaseNormalActivity<ActSelectSonosActionBinding> implements ISelectAction {
    private int actionCode;
    private long controlId;
    private List<CurtainAction> dataList;
    private Device device;
    private String keyActionExtra;
    private String mActionName;
    private MultipleItemRvAdapter<CurtainAction, BaseViewHolder> mAdapter;
    private int progress;
    protected boolean[] selectArray;
    private int selectPosition = -1;
    private int lastSelectPosition = -1;
    private boolean isLocal = false;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected boolean isNeedAddMarginTopEqualStatusBar() {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_sonos_action;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void lambda$edit$0(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectSonosForAction$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectSonosForAction.this.lambda$edit$0((Boolean) obj);
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_action));
        setEditString(getString(R.string.save));
        setBackString(getString(R.string.cancel));
        showAsDialog(0.0f);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        this.isLocal = getIntent().getBooleanExtra(Constants.IS_LOCAL_SCENE, false);
        this.controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        Device deviceById = Injection.repo().device().getDeviceById(this.controlId);
        this.device = deviceById;
        if (deviceById.getDeviceState().getCurtainMotorState() != null) {
            this.progress = this.device.getDeviceState().getCurtainMotorState().getTravelPercent();
        }
        initRv();
    }

    private void initRv() {
        List<CurtainAction> contentList = getContentList();
        this.dataList = contentList;
        this.selectArray = new boolean[contentList.size()];
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.dataList);
        this.mAdapter = anonymousClass1;
        anonymousClass1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectSonosForAction$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSelectSonosForAction.this.lambda$initRv$1(baseQuickAdapter, view, i);
            }
        });
        ((ActSelectSonosActionBinding) this.mViewBinding).rvContent.addOnItemTouchListener(new OnItemChildClickListener(this) { // from class: com.ltech.smarthome.ui.scene.ActSelectSonosForAction.2
            @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
        this.mAdapter.finishInitialize();
        this.mAdapter.bindToRecyclerView(((ActSelectSonosActionBinding) this.mViewBinding).rvContent);
        ((ActSelectSonosActionBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectSonosActionBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActSelectSonosActionBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(true);
    }

    /* renamed from: com.ltech.smarthome.ui.scene.ActSelectSonosForAction$1, reason: invalid class name */
    class AnonymousClass1 extends MultipleItemRvAdapter<CurtainAction, BaseViewHolder> {
        AnonymousClass1(List data) {
            super(data);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
        public int getViewType(CurtainAction CurtainAction) {
            return CurtainAction.getType();
        }

        @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
        public void registerItemProvider() {
            this.mProviderDelegate.registerProvider(new BaseItemProvider<CurtainAction, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.scene.ActSelectSonosForAction.1.1
                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int layout() {
                    return R.layout.item_select;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int viewType() {
                    return CurtainAction.TYPE_DEFAULT;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void convert(BaseViewHolder helper, CurtainAction data, int position) {
                    ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                    if (helper.getAdapterPosition() == ActSelectSonosForAction.this.selectPosition) {
                        helper.setImageResource(R.id.iv_select, R.mipmap.ic_tick_sel).setGone(R.id.iv_select, true);
                    } else {
                        helper.setGone(R.id.iv_select, false);
                    }
                    helper.setText(R.id.tv_name, data.getTitleName());
                }
            });
            this.mProviderDelegate.registerProvider(new BaseItemProvider<CurtainAction, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.scene.ActSelectSonosForAction.1.2
                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int layout() {
                    return R.layout.item_select_diy;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public int viewType() {
                    return CurtainAction.TYPE_DIY;
                }

                @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                public void convert(final BaseViewHolder helper, final CurtainAction data, final int position) {
                    helper.setText(R.id.tv_name, data.getTitleName());
                    ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                    if (helper.getAdapterPosition() == ActSelectSonosForAction.this.selectPosition) {
                        helper.setImageResource(R.id.iv_select, R.mipmap.ic_tick_sel).setGone(R.id.iv_select, true);
                        helper.setGone(R.id.layout_change_curtain_open_percent, true);
                        ((LightBrtBar) helper.getView(R.id.sb_brt)).setIncludeZero(true);
                        ((LightBrtBar) helper.getView(R.id.sb_brt)).setProgress(ActSelectSonosForAction.this.progress);
                        helper.setText(R.id.tv_brt, ActSelectSonosForAction.this.progress + "%");
                        data.setCurtainPercent(ActSelectSonosForAction.this.progress);
                        ((LightBrtBar) helper.getView(R.id.sb_brt)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectSonosForAction.1.2.1
                            @Override // android.widget.SeekBar.OnSeekBarChangeListener
                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }

                            @Override // android.widget.SeekBar.OnSeekBarChangeListener
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                LHomeLog.i(getClass(), "progress-->" + progress);
                                helper.setText(R.id.tv_brt, ((LightBrtBar) seekBar).getProgress() + "%");
                            }

                            @Override // android.widget.SeekBar.OnSeekBarChangeListener
                            public void onStopTrackingTouch(SeekBar seekBar) {
                                BaseViewHolder baseViewHolder = helper;
                                StringBuilder sb = new StringBuilder();
                                LightBrtBar lightBrtBar = (LightBrtBar) seekBar;
                                sb.append(lightBrtBar.getProgress());
                                sb.append("%");
                                baseViewHolder.setText(R.id.tv_brt, sb.toString());
                                data.setCurtainPercent(lightBrtBar.getProgress());
                                ActSelectSonosForAction.this.selectCurtainUserDefied(position);
                            }
                        });
                    } else {
                        helper.setGone(R.id.iv_select, false).setGone(R.id.iv_select, false);
                        helper.setGone(R.id.layout_change_curtain_open_percent, false);
                    }
                    helper.setText(R.id.tv_name, data.getTitleName());
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$1(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int i2 = this.selectPosition;
        if (i2 != i) {
            this.lastSelectPosition = i2;
            this.selectPosition = i;
            if (i != -1) {
                baseQuickAdapter.notifyItemChanged(i);
            }
            int i3 = this.lastSelectPosition;
            if (i3 != -1) {
                baseQuickAdapter.notifyItemChanged(i3);
            }
        }
        baseQuickAdapter.notifyDataSetChanged();
        onItemClick(baseQuickAdapter, i);
    }

    private void onItemClick(BaseQuickAdapter<CurtainAction, BaseViewHolder> adapter, int position) {
        this.mActionName = this.mAdapter.getData().get(this.selectPosition).getTitleName();
        switch (position) {
            case 0:
                showPlayList();
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                DeviceCmdParam deviceCmdParam = new DeviceCmdParam();
                deviceCmdParam.addExtParam(SceneHelper.OPTION, (position + 1) + "");
                deviceCmdParam.addExtParam(SceneHelper.OPTION_VALUE, this.mAdapter.getData().get(position).getTitleName());
                SceneHelper.instance().cmdParam = deviceCmdParam;
                break;
            case 10:
                selectCurtainUserDefied(position);
                break;
        }
    }

    private List<CurtainAction> getContentList() {
        ArrayList arrayList = new ArrayList();
        String[] sonosActionName = NameRepository.getSonosActionName(this);
        for (int i = 0; i < sonosActionName.length; i++) {
            String str = sonosActionName[i];
            if (i == 10) {
                arrayList.add(new CurtainAction(CurtainAction.TYPE_DIY, str, 20));
            } else {
                arrayList.add(new CurtainAction(CurtainAction.TYPE_DEFAULT, str));
            }
        }
        return arrayList;
    }

    public void selectCurtainUserDefied(int position) {
        DeviceCmdParam deviceCmdParam = new DeviceCmdParam();
        deviceCmdParam.setCurtainProgress(this.mAdapter.getData().get(position).getCurtainPercent());
        deviceCmdParam.addExtParam(SceneHelper.OPTION, (position + 1) + "");
        deviceCmdParam.addExtParam(SceneHelper.OPTION_VALUE, this.mAdapter.getData().get(position).getCurtainPercent() + "");
        SceneHelper.instance().cmdParam = deviceCmdParam;
    }

    public void showPlayList() {
        showLoadingDialog("");
        ((ObservableSubscribeProxy) Injection.net().sonosFavorites(Injection.repo().home().getSelPlace().getPlaceId()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.scene.ActSelectSonosForAction.3
            @Override // io.reactivex.functions.Action
            public void run() throws Exception {
                ActSelectSonosForAction.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.ActSelectSonosForAction$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectSonosForAction.this.lambda$showPlayList$2((List) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.scene.ActSelectSonosForAction$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectSonosForAction.this.lambda$showPlayList$3((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPlayList$2(final List list) throws Exception {
        dismissLoadingDialog();
        if (list != null && !list.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                SonosResponse.Favorites favorites = (SonosResponse.Favorites) it.next();
                arrayList.add(favorites.getName());
                arrayList2.add(favorites.getDescription());
            }
            SelectListSubLineDialog.asDefault(true).setSelectList(arrayList, arrayList2).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.confirm)).setSelectPosition(-1).setConfirmAction(new IAction<Integer>(this) { // from class: com.ltech.smarthome.ui.scene.ActSelectSonosForAction.4
                @Override // com.ltech.smarthome.base.IAction
                public void act(Integer integer) {
                    if (integer.intValue() < list.size()) {
                        DeviceCmdParam deviceCmdParam = new DeviceCmdParam();
                        deviceCmdParam.addExtParam(SceneHelper.OPTION, "1");
                        deviceCmdParam.addExtParam(SceneHelper.OPTION_VALUE, GsonUtils.toJson(list.get(integer.intValue())));
                        SceneHelper.instance().cmdParam = deviceCmdParam;
                    }
                }
            }).showDialog(this.activity);
            return;
        }
        SmartToast.showShort(getString(R.string.no_collect_song));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPlayList$3(Throwable th) throws Exception {
        SmartToast.showShort(getString(R.string.no_collect_song));
        dismissLoadingDialog();
    }

    public static final class CurtainAction {
        private static int TYPE_DEFAULT = 1;
        private static int TYPE_DIY = 2;
        private int curtainPercent;
        private String name;
        private String subName;
        private String titleName;
        private int type;

        CurtainAction(int type, String titleName) {
            this.type = type;
            this.titleName = titleName;
        }

        CurtainAction(int type, String titleName, String name, String subName) {
            this.type = type;
            this.titleName = titleName;
            this.name = name;
            this.subName = subName;
        }

        CurtainAction(int type, String titleName, int curtainPercent) {
            this.type = type;
            this.titleName = titleName;
            this.curtainPercent = curtainPercent;
        }

        public int getCurtainPercent() {
            return this.curtainPercent;
        }

        public void setCurtainPercent(int curtainPercent) {
            this.curtainPercent = curtainPercent;
        }

        int getType() {
            return this.type;
        }

        String getName() {
            return this.name;
        }

        public String getSubName() {
            return this.subName;
        }

        public void setSubName(String subName) {
            this.subName = subName;
        }

        public String getTitleName() {
            return this.titleName;
        }

        public void setTitleName(String titleName) {
            this.titleName = titleName;
        }
    }
}