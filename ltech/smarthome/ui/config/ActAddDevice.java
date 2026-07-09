package com.ltech.smarthome.ui.config;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.kookong.app.data.AppConst;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.IActionWithReturn;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.blemesh.BleMeshManager;
import com.ltech.smarthome.blemesh.IScanCallback;
import com.ltech.smarthome.blemesh.bean.ExtendedBluetoothDevice;
import com.ltech.smarthome.databinding.ActAddDeviceBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.DeviceState;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.EurPanelGroupParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.product.ProductInfo;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.group.AddGroupResponse;
import com.ltech.smarthome.ui.config.ActAddDeviceVM;
import com.ltech.smarthome.ui.device.eurpanel.EurHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.SelectItemDialog;
import com.ltech.smarthome.view.dialog.SetBleTypeDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.yanzhenjie.permission.runtime.Permission;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActAddDevice extends VMActivity<ActAddDeviceBinding, ActAddDeviceVM> {
    private static final int REQUEST_ENABLE_BLUETOOTH = 1021;
    BlueToothValueReceiver blueToothValueReceiver;
    private BaseQuickAdapter<Integer, BaseViewHolder> categoryAdapter;
    private MultipleItemRvAdapter<ActAddDeviceVM.ProductItem, BaseViewHolder> deviceAdapter;
    private SetBleTypeDialog dialog;
    private BaseQuickAdapter scanTypeQuickAdapter;
    private int selectPosition = 0;
    private final int TYPE_TITLE = 1;
    private final int TYPE_PRODUCT = 2;
    private final int CAMERA_PERMISSION_CODE = 6666;
    private boolean bSearchDevice = false;
    public List<ActAddDeviceVM.ScanTypeItem> showScanTypeList = new ArrayList();

    static /* synthetic */ void lambda$delGroup$15(Object obj) throws Exception {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected boolean hideGloading() {
        return true;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_add_device;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.add_device));
        ((ActAddDeviceVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        LHomeLog.e("PID", getClass(), ((ActAddDeviceVM) this.mViewModel).placeId + "");
        ((ActAddDeviceVM) this.mViewModel).floorId = getIntent().getLongExtra(Constants.FLOOR_ID, -1L);
        ((ActAddDeviceVM) this.mViewModel).roomId = getIntent().getLongExtra(Constants.ROOM_ID, -1L);
        this.selectPosition = getIntent().getIntExtra(Constants.ADD_ID, 0);
        ConfigHelper.instance().reset();
        initCategoryRv();
        initDeviceRv(((ActAddDeviceVM) this.mViewModel).getProduct(this.categoryAdapter.getData().get(this.selectPosition).intValue()));
        initScrollView();
    }

    private void initScrollView() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        int i = displayMetrics.heightPixels;
        TypedArray obtainStyledAttributes = getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        int dimension = (int) obtainStyledAttributes.getDimension(0, 0.0f);
        obtainStyledAttributes.recycle();
        ((ActAddDeviceBinding) this.mViewBinding).layoutLoad.setMinHeight((i - dimension) - getStatusBarHeight(this));
        ((ActAddDeviceBinding) this.mViewBinding).title.searchSpreadView.setAnimate(true);
        long j = AppConst.KOOKONG_BRANDID_MIX_STB;
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 1000 * 360.0f, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setDuration(j);
        rotateAnimation.setRepeatCount(-1);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setFillAfter(true);
        ((ActAddDeviceBinding) this.mViewBinding).title.searchImage.setAnimation(rotateAnimation);
        if (Build.VERSION.SDK_INT >= 23) {
            ((ActAddDeviceBinding) this.mViewBinding).actAddDeviceScroll.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.ltech.smarthome.ui.config.ActAddDevice.1
                @Override // android.view.View.OnScrollChangeListener
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    int px2dp = ActAddDevice.px2dp(ActAddDevice.this, scrollY);
                    ActAddDevice actAddDevice = ActAddDevice.this;
                    float px2dp2 = px2dp - ((ActAddDevice.px2dp(actAddDevice, ((ActAddDeviceBinding) actAddDevice.mViewBinding).layoutFoundDevice.getHeight()) + 10) / 4);
                    ActAddDevice actAddDevice2 = ActAddDevice.this;
                    float px2dp3 = px2dp2 / ((ActAddDevice.px2dp(actAddDevice2, ((ActAddDeviceBinding) actAddDevice2.mViewBinding).layoutFoundDevice.getHeight()) + 10) / 4);
                    int px2dp4 = ActAddDevice.px2dp(ActAddDevice.this, scrollY);
                    ActAddDevice actAddDevice3 = ActAddDevice.this;
                    if (px2dp4 > (ActAddDevice.px2dp(actAddDevice3, ((ActAddDeviceBinding) actAddDevice3.mViewBinding).layoutFoundDevice.getHeight()) + 10) / 4) {
                        ((ActAddDeviceBinding) ActAddDevice.this.mViewBinding).title.layoutSearchView.setVisibility(0);
                    } else {
                        ((ActAddDeviceBinding) ActAddDevice.this.mViewBinding).title.layoutSearchView.setVisibility(8);
                    }
                    ((ActAddDeviceBinding) ActAddDevice.this.mViewBinding).title.layoutSearchView.setAlpha(px2dp3);
                }
            });
            ((ActAddDeviceBinding) this.mViewBinding).title.layoutSearchView.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.config.ActAddDevice.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    ConfigHelper.instance().setScanListCache(((ActAddDeviceVM) ActAddDevice.this.mViewModel).deviceList);
                    NavUtils.destination(ActMeshNearDevice.class).withLong(Constants.PLACE_ID, ((ActAddDeviceVM) ActAddDevice.this.mViewModel).placeId).withLong(Constants.FLOOR_ID, ((ActAddDeviceVM) ActAddDevice.this.mViewModel).floorId).withLong(Constants.ROOM_ID, ((ActAddDeviceVM) ActAddDevice.this.mViewModel).roomId).navigation(ActAddDevice.this);
                }
            });
        }
    }

    private int getStatusBarHeight(Activity activity) {
        int identifier = activity.getResources().getIdentifier("status_bar_height", "dimen", DispatchConstants.ANDROID);
        if (identifier > 0) {
            return activity.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static int px2dp(Context context, int pxValue) {
        return (int) ((pxValue / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    private void initCategoryRv() {
        BaseQuickAdapter<Integer, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Integer, BaseViewHolder>(R.layout.item_category, ((ActAddDeviceVM) this.mViewModel).categoryList) { // from class: com.ltech.smarthome.ui.config.ActAddDevice.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Integer item) {
                helper.setText(R.id.tv_content, item.intValue()).setTextColor(R.id.tv_content, ContextCompat.getColor(this.mContext, helper.getAdapterPosition() == ActAddDevice.this.selectPosition ? R.color.color_text_red : R.color.color_text_black)).setBackgroundColor(R.id.layout_bg, ContextCompat.getColor(this.mContext, helper.getAdapterPosition() == ActAddDevice.this.selectPosition ? android.R.color.white : 17170445));
            }
        };
        this.categoryAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.config.ActAddDevice$$ExternalSyntheticLambda4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActAddDevice.this.lambda$initCategoryRv$0(baseQuickAdapter2, view, i);
            }
        });
        this.categoryAdapter.bindToRecyclerView(((ActAddDeviceBinding) this.mViewBinding).rvCategory);
        ((ActAddDeviceBinding) this.mViewBinding).rvCategory.setLayoutManager(new LinearLayoutManager(this));
        ((ActAddDeviceBinding) this.mViewBinding).rvCategory.setHasFixedSize(true);
        ((ActAddDeviceBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.config.ActAddDevice$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActAddDevice.this.lambda$initCategoryRv$1((View) obj);
            }
        }));
        ((ActAddDeviceBinding) this.mViewBinding).searchSpreadView.setAnimate(true);
        long j = AppConst.KOOKONG_BRANDID_MIX_STB;
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 1000 * 360.0f, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setDuration(j);
        rotateAnimation.setRepeatCount(-1);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setFillAfter(true);
        ((ActAddDeviceBinding) this.mViewBinding).searchImage.setAnimation(rotateAnimation);
        initScanAdapter();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initCategoryRv$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.selectPosition = i;
        this.categoryAdapter.notifyDataSetChanged();
        this.deviceAdapter.setNewData(((ActAddDeviceVM) this.mViewModel).getProduct(this.categoryAdapter.getData().get(i).intValue()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initCategoryRv$1(View view) {
        if (view.getId() != R.id.act_search_relayout) {
            return;
        }
        ConfigHelper.instance().setScanListCache(((ActAddDeviceVM) this.mViewModel).deviceList);
        NavUtils.destination(ActMeshNearDevice.class).withLong(Constants.PLACE_ID, ((ActAddDeviceVM) this.mViewModel).placeId).withLong(Constants.FLOOR_ID, ((ActAddDeviceVM) this.mViewModel).floorId).withLong(Constants.ROOM_ID, ((ActAddDeviceVM) this.mViewModel).roomId).navigation(this);
    }

    private void initScanAdapter() {
        this.scanTypeQuickAdapter = new BaseQuickAdapter<ActAddDeviceVM.ScanTypeItem, BaseViewHolder>(this, R.layout.item_mesh_search_scan, this.showScanTypeList) { // from class: com.ltech.smarthome.ui.config.ActAddDevice.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, ActAddDeviceVM.ScanTypeItem item) {
                helper.setText(R.id.tv_num, item.number + "");
                helper.setImageResource(R.id.iv_icon, item.iconRes);
            }
        };
        ((ActAddDeviceBinding) this.mViewBinding).rvSearchDevice.setAdapter(this.scanTypeQuickAdapter);
        this.scanTypeQuickAdapter.notifyDataSetChanged();
        this.scanTypeQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.config.ActAddDevice.5
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ConfigHelper.instance().setScanListCache(((ActAddDeviceVM) ActAddDevice.this.mViewModel).deviceList);
                NavUtils.destination(ActMeshNearDevice.class).withLong(Constants.PLACE_ID, ((ActAddDeviceVM) ActAddDevice.this.mViewModel).placeId).withLong(Constants.FLOOR_ID, ((ActAddDeviceVM) ActAddDevice.this.mViewModel).floorId).withLong(Constants.ROOM_ID, ((ActAddDeviceVM) ActAddDevice.this.mViewModel).roomId).navigation(ActAddDevice.this);
            }
        });
    }

    private void initDeviceRv(List<ActAddDeviceVM.ProductItem> productItemList) {
        MultipleItemRvAdapter<ActAddDeviceVM.ProductItem, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<ActAddDeviceVM.ProductItem, BaseViewHolder>(productItemList) { // from class: com.ltech.smarthome.ui.config.ActAddDevice.6
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(ActAddDeviceVM.ProductItem productItem) {
                return productItem.productInfo != null ? 2 : 1;
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new BaseItemProvider<ActAddDeviceVM.ProductItem, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.config.ActAddDevice.6.1
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_text_middle;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 1;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, ActAddDeviceVM.ProductItem item, int position) {
                        helper.setText(R.id.tv_name, item.title).setTextColor(R.id.tv_name, ContextCompat.getColor(ActAddDevice.this, R.color.color_text_gray));
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<ActAddDeviceVM.ProductItem, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.config.ActAddDevice.6.2
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_select_product;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 2;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, ActAddDeviceVM.ProductItem item, int position) {
                        helper.getView(R.id.layout_item_bg).getLayoutParams().height = ((ActAddDeviceBinding) ActAddDevice.this.mViewBinding).rvDevice.getMeasuredHeight() / 6;
                        helper.setImageResource(R.id.iv_product_icon, item.productInfo.getDefaultIconRes());
                        helper.setText(R.id.tv_product_name, item.productInfo.getDefaultName(this.mContext));
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void onClick(BaseViewHolder helper, ActAddDeviceVM.ProductItem data, int position) {
                        ActAddDeviceVM.ProductItem productItem = (ActAddDeviceVM.ProductItem) ActAddDevice.this.deviceAdapter.getItem(position);
                        if ("2".equals(productItem.productInfo.getProductId())) {
                            ActAddDevice.this.showAddGroupDialog(productItem, false);
                            return;
                        }
                        if ("3".equals(productItem.productInfo.getProductId())) {
                            ActAddDevice.this.showAddGroupDialog(productItem, true);
                            return;
                        }
                        if (ProductRepository.getInfraredType(productItem.productInfo.getProductId()) == 6) {
                            ActAddDevice.this.showSelectGatewayDialog(productItem.productInfo, productItem.navClz);
                            return;
                        }
                        if (ProductRepository.getInfraredType(productItem.productInfo.getProductId()) == 7) {
                            ActAddDevice.this.showModuleDialog(productItem.productInfo, productItem.navClz);
                        } else if (data.isVirtual()) {
                            ((ActAddDeviceVM) ActAddDevice.this.mViewModel).showAddVirtualDialog(data);
                        } else {
                            ActAddDevice.this.nav(productItem.productInfo, productItem.navClz, new String[0]);
                        }
                    }
                });
            }
        };
        this.deviceAdapter = multipleItemRvAdapter;
        multipleItemRvAdapter.finishInitialize();
        this.deviceAdapter.bindToRecyclerView(((ActAddDeviceBinding) this.mViewBinding).rvDevice);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.ltech.smarthome.ui.config.ActAddDevice.7
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int position) {
                if (((ActAddDeviceVM.ProductItem) ActAddDevice.this.deviceAdapter.getData().get(position)).productInfo == null) {
                    return gridLayoutManager.getSpanCount();
                }
                return ((ActAddDeviceVM.ProductItem) ActAddDevice.this.deviceAdapter.getData().get(position)).isVirtual() ? 3 : 2;
            }
        });
        ((ActAddDeviceBinding) this.mViewBinding).rvDevice.setLayoutManager(gridLayoutManager);
        ((ActAddDeviceBinding) this.mViewBinding).rvDevice.setHasFixedSize(true);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injection.mesh().stopCheckNearbyDevice();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((ActAddDeviceVM) this.mViewModel).deviceList.clear();
        ((ActAddDeviceVM) this.mViewModel).deviceList.addAll(ConfigHelper.instance().scanListCache);
        if (((ActAddDeviceVM) this.mViewModel).deviceList.size() > 0) {
            ((ActAddDeviceVM) this.mViewModel).convertScanItemList(((ActAddDeviceVM) this.mViewModel).deviceList);
            ((ActAddDeviceVM) this.mViewModel).refreshTypeItemEvent.call();
        } else {
            this.showScanTypeList.clear();
            ((ActAddDeviceBinding) this.mViewBinding).title.tvNum.setText(String.valueOf(0));
            this.scanTypeQuickAdapter.notifyDataSetChanged();
        }
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActAddDevice.8
            @Override // java.lang.Runnable
            public void run() {
                ActAddDevice.this.scanDevice();
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scanDevice() {
        Injection.mesh().startScan(BleMeshManager.MESH_PROVISIONING_UUID, new IScanCallback() { // from class: com.ltech.smarthome.ui.config.ActAddDevice$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.blemesh.IScanCallback
            public final void onScanResult(ExtendedBluetoothDevice extendedBluetoothDevice) {
                ActAddDevice.this.lambda$scanDevice$2(extendedBluetoothDevice);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scanDevice$2(ExtendedBluetoothDevice extendedBluetoothDevice) {
        LHomeLog.i(ActAddDevice.class, "scanDevice: bluetoothDevice-->" + extendedBluetoothDevice);
        if (((ActAddDeviceVM) this.mViewModel).refreshRssi(extendedBluetoothDevice) || !((ActAddDeviceVM) this.mViewModel).addDevice(extendedBluetoothDevice) || this.bSearchDevice) {
            return;
        }
        this.bSearchDevice = true;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActAddDeviceVM) this.mViewModel).floorList.setValue(Injection.repo().home().getFloorListByPlaceId(((ActAddDeviceVM) this.mViewModel).placeId));
        ((ActAddDeviceVM) this.mViewModel).roomList.setValue(Injection.repo().home().getRoomListByFloorId(((ActAddDeviceVM) this.mViewModel).placeId, -1L));
        List<Device> deviceListByPlaceId = Injection.repo().device().getDeviceListByPlaceId(((ActAddDeviceVM) this.mViewModel).placeId);
        ArrayList arrayList = new ArrayList();
        for (Device device : deviceListByPlaceId) {
            if (ProductId.ID_MESH_GATEWAY.equals(device.getProductId()) || ProductId.ID_BLE_HAM.equals(device.getProductId()) || ((ProductId.ID_ANDROID_SUPER_PANEL_4S.equals(device.getProductId()) && device.getUnicastAddress() != 0) || ((ProductId.ID_ANDROID_SUPER_PANEL_6S.equals(device.getProductId()) && device.getUnicastAddress() != 0) || ((ProductId.ID_ANDROID_SUPER_PANEL_PRO.equals(device.getProductId()) && device.getUnicastAddress() != 0) || ((ProductId.ID_ANDROID_SUPER_PANEL_12S.equals(device.getProductId()) && device.getUnicastAddress() != 0) || (ProductId.ID_ANDROID_SUPER_PANEL_G4MAX.equals(device.getProductId()) && device.getUnicastAddress() != 0)))))) {
                arrayList.add(device);
            }
            if (ProductId.ID_MESH_GATEWAY.equals(device.getProductId()) || ProductId.ID_BLE_HAM.equals(device.getProductId())) {
                ((ActAddDeviceVM) this.mViewModel).moduleDeviceList.add(device);
            }
        }
        ((ActAddDeviceVM) this.mViewModel).gatewayList.setValue(arrayList);
        ((ActAddDeviceVM) this.mViewModel).gatewayList.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.config.ActAddDevice$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddDevice.this.lambda$startObserve$3((List) obj);
            }
        });
        ((ActAddDeviceVM) this.mViewModel).roomPickHelper.startObserve(this, ((ActAddDeviceVM) this.mViewModel).placeId, -1L);
        ((ActAddDeviceVM) this.mViewModel).refreshTypeItemEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.config.ActAddDevice$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActAddDevice.this.lambda$startObserve$4((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(List list) {
        showContent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r4) {
        this.showScanTypeList.clear();
        int i = 0;
        for (ActAddDeviceVM.ScanTypeItem scanTypeItem : ((ActAddDeviceVM) this.mViewModel).scanTypeList) {
            if (scanTypeItem.number != 0) {
                this.showScanTypeList.add(scanTypeItem);
                i += scanTypeItem.number;
            }
        }
        ((ActAddDeviceBinding) this.mViewBinding).title.tvNum.setText(i + "");
        this.scanTypeQuickAdapter.notifyDataSetChanged();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void showContent() {
        super.showContent();
        setEditImage(R.mipmap.icon_scan);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        if (((ActAddDeviceVM) this.mViewModel).request != null) {
            ((ActAddDeviceVM) this.mViewModel).request.retry();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (checkPhotoPermission()) {
            ConfigHelper.instance().placeId = ((ActAddDeviceVM) this.mViewModel).placeId;
            ConfigHelper.instance().roomId = getIntent().getLongExtra(Constants.ROOM_ID, -1L);
            NavUtils.destination(ActQrCodeScan.class).navigation(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void nav(ProductInfo info, Class clazz, String... deviceName) {
        if (clazz == null) {
            return;
        }
        ConfigHelper.instance().reset();
        ConfigHelper.instance().placeId = ((ActAddDeviceVM) this.mViewModel).placeId;
        ConfigHelper.instance().floorId = ((ActAddDeviceVM) this.mViewModel).floorId;
        ConfigHelper.instance().roomId = getIntent().getLongExtra(Constants.ROOM_ID, -1L);
        ConfigHelper.instance().productInfo = info;
        if (deviceName.length > 0) {
            ConfigHelper.instance().deviceName = deviceName[0];
        }
        NavUtils.destination(clazz).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSelectGatewayDialog(final ProductInfo info, final Class clazz) {
        if (clazz == null) {
            return;
        }
        if (((ActAddDeviceVM) this.mViewModel).gatewayList.getValue() == null || ((ActAddDeviceVM) this.mViewModel).gatewayList.getValue().isEmpty()) {
            MessageDialog.show(this, getString(R.string.can_not_add_device), getString(R.string.can_not_add_device_tip)).setOkButton(getString(R.string.add_gateway), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.config.ActAddDevice$$ExternalSyntheticLambda15
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$showSelectGatewayDialog$5;
                    lambda$showSelectGatewayDialog$5 = ActAddDevice.this.lambda$showSelectGatewayDialog$5(baseDialog, view);
                    return lambda$showSelectGatewayDialog$5;
                }
            }).setCancelButton(getString(R.string.cancel));
            return;
        }
        if (((ActAddDeviceVM) this.mViewModel).gatewayList.getValue().size() == 1) {
            Device device = ((ActAddDeviceVM) this.mViewModel).gatewayList.getValue().get(0);
            ConfigHelper.instance().reset();
            if ((ProductRepository.isBLeDevice(device.getProductId()) || ProductRepository.isWifiBleDevice(device.getProductId())) && device.getParam() != null && device.getParam(BleParam.class) != null) {
                BleParam bleParam = (BleParam) device.getParam(BleParam.class);
                ConfigHelper.instance().unicastAddress = bleParam.getUnicastAddress();
            }
            ConfigHelper.instance().placeId = device.getPlaceId();
            ConfigHelper.instance().floorId = device.getFloorId();
            ConfigHelper.instance().roomId = device.getRoomId();
            ConfigHelper.instance().macdeviceid = device.getDeviceId();
            ConfigHelper.instance().mac = device.getWifiMac();
            ConfigHelper.instance().productInfo = info;
            NavUtils.destination(clazz).navigation(this);
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (Device device2 : ((ActAddDeviceVM) this.mViewModel).gatewayList.getValue()) {
            SelectItemDialog.ListItem listItem = new SelectItemDialog.ListItem();
            listItem.iconRes = ProductRepository.getProductIcon(device2);
            listItem.name = device2.getDeviceName();
            listItem.content = ((ActAddDeviceVM) this.mViewModel).getLocationName(device2.getDeviceId());
            arrayList.add(listItem);
        }
        SelectItemDialog.asDefault().setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.next)).setSelectList(arrayList).setTitle(getString(R.string.please_choose_gateway)).setItemClickAction(new IActionWithReturn() { // from class: com.ltech.smarthome.ui.config.ActAddDevice$$ExternalSyntheticLambda16
            @Override // com.ltech.smarthome.base.IActionWithReturn
            public final Object act(Object obj) {
                Boolean lambda$showSelectGatewayDialog$6;
                lambda$showSelectGatewayDialog$6 = ActAddDevice.this.lambda$showSelectGatewayDialog$6((Integer) obj);
                return lambda$showSelectGatewayDialog$6;
            }
        }).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.config.ActAddDevice$$ExternalSyntheticLambda17
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActAddDevice.this.lambda$showSelectGatewayDialog$7(info, clazz, (Integer) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showSelectGatewayDialog$5(BaseDialog baseDialog, View view) {
        ActAddDeviceVM.ProductItem irGateway = ((ActAddDeviceVM) this.mViewModel).irGateway();
        nav(irGateway.productInfo, irGateway.navClz, new String[0]);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$showSelectGatewayDialog$6(Integer num) {
        Device device = ((ActAddDeviceVM) this.mViewModel).gatewayList.getValue().get(num.intValue());
        if (!ProductRepository.isBLeDevice(device.getProductId()) && !device.hasIotFun()) {
            SmartToast.showShort(R.string.set_network_tip);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectGatewayDialog$7(ProductInfo productInfo, Class cls, Integer num) {
        Device device = ((ActAddDeviceVM) this.mViewModel).gatewayList.getValue().get(num.intValue());
        ConfigHelper.instance().reset();
        if ((ProductRepository.isBLeDevice(device.getProductId()) || ProductRepository.isWifiBleDevice(device.getProductId())) && device.getParam() != null && device.getParam(BleParam.class) != null) {
            BleParam bleParam = (BleParam) device.getParam(BleParam.class);
            ConfigHelper.instance().unicastAddress = bleParam.getUnicastAddress();
        }
        ConfigHelper.instance().placeId = device.getPlaceId();
        ConfigHelper.instance().floorId = device.getFloorId();
        ConfigHelper.instance().roomId = device.getRoomId();
        ConfigHelper.instance().macdeviceid = device.getDeviceId();
        ConfigHelper.instance().mac = device.getWifiMac();
        ConfigHelper.instance().productInfo = productInfo;
        NavUtils.destination(cls).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showModuleDialog(final ProductInfo info, final Class clazz) {
        if (clazz == null) {
            return;
        }
        if (((ActAddDeviceVM) this.mViewModel).moduleDeviceList == null || ((ActAddDeviceVM) this.mViewModel).moduleDeviceList.isEmpty()) {
            MessageDialog.show(this, getString(R.string.can_not_add_device), getString(R.string.can_not_add_device_tip2));
            return;
        }
        if (((ActAddDeviceVM) this.mViewModel).moduleDeviceList.size() == 1) {
            Device device = ((ActAddDeviceVM) this.mViewModel).moduleDeviceList.get(0);
            ConfigHelper.instance().reset();
            if ((ProductRepository.isBLeDevice(device.getProductId()) || ProductRepository.isWifiBleDevice(device.getProductId())) && device.getParam() != null && device.getParam(BleParam.class) != null) {
                BleParam bleParam = (BleParam) device.getParam(BleParam.class);
                ConfigHelper.instance().unicastAddress = bleParam.getUnicastAddress();
            }
            ConfigHelper.instance().placeId = device.getPlaceId();
            ConfigHelper.instance().floorId = device.getFloorId();
            ConfigHelper.instance().roomId = device.getRoomId();
            ConfigHelper.instance().macdeviceid = device.getDeviceId();
            ConfigHelper.instance().mac = device.getWifiMac();
            ConfigHelper.instance().productInfo = info;
            NavUtils.destination(clazz).navigation(this);
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (Device device2 : ((ActAddDeviceVM) this.mViewModel).moduleDeviceList) {
            SelectItemDialog.ListItem listItem = new SelectItemDialog.ListItem();
            listItem.iconRes = ProductRepository.getProductIcon(device2);
            listItem.name = device2.getDeviceName();
            listItem.content = ((ActAddDeviceVM) this.mViewModel).getLocationName(device2.getDeviceId());
            arrayList.add(listItem);
        }
        SelectItemDialog.asDefault().setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.next)).setSelectList(arrayList).setTitle(getString(R.string.please_choose_gateway)).setItemClickAction(new IActionWithReturn() { // from class: com.ltech.smarthome.ui.config.ActAddDevice$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IActionWithReturn
            public final Object act(Object obj) {
                Boolean lambda$showModuleDialog$8;
                lambda$showModuleDialog$8 = ActAddDevice.this.lambda$showModuleDialog$8((Integer) obj);
                return lambda$showModuleDialog$8;
            }
        }).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.config.ActAddDevice$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActAddDevice.this.lambda$showModuleDialog$9(info, clazz, (Integer) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$showModuleDialog$8(Integer num) {
        Device device = ((ActAddDeviceVM) this.mViewModel).moduleDeviceList.get(num.intValue());
        if (!ProductRepository.isBLeDevice(device.getProductId()) && !device.hasIotFun()) {
            SmartToast.showShort(R.string.set_network_tip);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showModuleDialog$9(ProductInfo productInfo, Class cls, Integer num) {
        Device device = ((ActAddDeviceVM) this.mViewModel).moduleDeviceList.get(num.intValue());
        ConfigHelper.instance().reset();
        if ((ProductRepository.isBLeDevice(device.getProductId()) || ProductRepository.isWifiBleDevice(device.getProductId())) && device.getParam() != null && device.getParam(BleParam.class) != null) {
            BleParam bleParam = (BleParam) device.getParam(BleParam.class);
            ConfigHelper.instance().unicastAddress = bleParam.getUnicastAddress();
        }
        ConfigHelper.instance().placeId = device.getPlaceId();
        ConfigHelper.instance().floorId = device.getFloorId();
        ConfigHelper.instance().roomId = device.getRoomId();
        ConfigHelper.instance().macdeviceid = device.getDeviceId();
        ConfigHelper.instance().mac = device.getWifiMac();
        ConfigHelper.instance().productInfo = productInfo;
        NavUtils.destination(cls).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAddGroupDialog(final ActAddDeviceVM.ProductItem item, final boolean bleGroup) {
        ((ActAddDeviceVM) this.mViewModel).roomPickHelper.resetObserve();
        ((ActAddDeviceVM) this.mViewModel).roomPickHelper.startObserve(this, ((ActAddDeviceVM) this.mViewModel).placeId, SPUtils.getInstance().getLong(Constants.USER_CUR_ROOM_FOR_GROUP, -1L));
        this.dialog = SetBleTypeDialog.asDefault().setTitle(getString(R.string.add_group)).setLabel(getString(R.string.group_name)).setContent(getString(item.productInfo.getAddNameRes()).replace("\n", "")).setOnSaveListener(new SetBleTypeDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.config.ActAddDevice.9
            @Override // com.ltech.smarthome.view.dialog.SetBleTypeDialog.OnSaveListener
            public void cancel() {
            }

            @Override // com.ltech.smarthome.view.dialog.SetBleTypeDialog.OnSaveListener
            public boolean onSave(String name, boolean changeType, int type, int outputType, int floorPos, int roomPos, int zoneControl, int controlType) {
                if (TextUtils.isEmpty(name.trim())) {
                    SmartToast.showShort(R.string.input_name);
                    return false;
                }
                if (item.productInfo.getHardwareId().equals(Integer.toString(22)) || item.productInfo.getHardwareId().equals(Integer.toString(23)) || item.productInfo.getHardwareId().equals(Integer.toString(24)) || item.productInfo.getHardwareId().equals(Integer.toString(28)) || item.productInfo.getHardwareId().equals(Integer.toString(27)) || item.productInfo.getHardwareId().equals(Integer.toString(29)) || item.productInfo.getHardwareId().equals(Integer.toString(30))) {
                    EurHelper.paramExt = RelateInfoUtils.initEurPanel(zoneControl, controlType).getExtParamString();
                }
                ((ActAddDeviceVM) ActAddDevice.this.mViewModel).roomPickHelper.setSelectPosition(floorPos, roomPos);
                long selectFloorId = ((ActAddDeviceVM) ActAddDevice.this.mViewModel).roomPickHelper.getSelectFloorId();
                long selectRoomId = ((ActAddDeviceVM) ActAddDevice.this.mViewModel).roomPickHelper.getSelectRoomId();
                if (bleGroup) {
                    ActAddDevice.this.createBleGroup(item, selectFloorId, selectRoomId, name);
                    return true;
                }
                ActAddDevice.this.dismissGroupDialog();
                ActAddDevice.this.nav(item.productInfo, item.navClz, name);
                return true;
            }
        });
        String hardwareId = item.productInfo.getHardwareId();
        if (hardwareId.equals(Integer.toString(22))) {
            this.dialog.eurPanelType(1);
        } else if (hardwareId.equals(Integer.toString(23))) {
            this.dialog.eurPanelType(2);
        } else if (hardwareId.equals(Integer.toString(24))) {
            this.dialog.eurPanelType(5);
        } else if (hardwareId.equals(Integer.toString(29))) {
            this.dialog.setAsPanelType(4);
        } else if (hardwareId.equals(Integer.toString(30))) {
            this.dialog.setAsPanelType(5);
        } else if (hardwareId.equals(Integer.toString(27))) {
            this.dialog.setAsPanelType(1);
        } else if (hardwareId.equals(Integer.toString(28))) {
            this.dialog.setAsPanelType(2);
        }
        ((ActAddDeviceVM) this.mViewModel).roomPickHelper.setCurrentFloorIdPos(((ActAddDeviceVM) this.mViewModel).floorId);
        ((ActAddDeviceVM) this.mViewModel).roomPickHelper.setCurrentRoomIdPos(((ActAddDeviceVM) this.mViewModel).floorId, ((ActAddDeviceVM) this.mViewModel).roomId);
        this.dialog.setSelectRoom(((ActAddDeviceVM) this.mViewModel).roomPickHelper.canSetRoom()).setFloorList(((ActAddDeviceVM) this.mViewModel).roomPickHelper.getCurrentFloorNames()).setRoomList(((ActAddDeviceVM) this.mViewModel).roomPickHelper.getCurrentRoomNames()).setSelectFloorPosition(((ActAddDeviceVM) this.mViewModel).roomPickHelper.getSelectFloorPosition()).setSelectRoomPosition(((ActAddDeviceVM) this.mViewModel).roomPickHelper.getSelectRoomPosition()).setOnSelectFloorListener(new SetBleTypeDialog.OnSelectFloorListener() { // from class: com.ltech.smarthome.ui.config.ActAddDevice$$ExternalSyntheticLambda14
            @Override // com.ltech.smarthome.view.dialog.SetBleTypeDialog.OnSelectFloorListener
            public final void selectFloor(SetBleTypeDialog setBleTypeDialog, int i, String str) {
                ActAddDevice.this.lambda$showAddGroupDialog$10(setBleTypeDialog, i, str);
            }
        }).setAddGroup(true).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAddGroupDialog$10(SetBleTypeDialog setBleTypeDialog, int i, String str) {
        this.dialog.setRoomList(((ActAddDeviceVM) this.mViewModel).roomPickHelper.getRoomNames(i));
        this.dialog.initRoomData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createBleGroup(ActAddDeviceVM.ProductItem item, long floorId, long roomId, String groupName) {
        if (item.productInfo.getHardwareId().equals(Integer.toString(22)) || item.productInfo.getHardwareId().equals(Integer.toString(23)) || item.productInfo.getHardwareId().equals(Integer.toString(24)) || item.productInfo.getHardwareId().equals(Integer.toString(28)) || item.productInfo.getHardwareId().equals(Integer.toString(27)) || item.productInfo.getHardwareId().equals(Integer.toString(29)) || item.productInfo.getHardwareId().equals(Integer.toString(30))) {
            setGroupPublishAddress(item, floorId, roomId, groupName);
        } else {
            addGroup(item, floorId, roomId, groupName);
        }
    }

    private void addGroup(final ActAddDeviceVM.ProductItem item, final long floorId, final long roomId, final String groupName) {
        getMainHandler().post(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActAddDevice$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                ActAddDevice.this.lambda$addGroup$11();
            }
        });
        ((ObservableSubscribeProxy) Injection.net().addGroup(((ActAddDeviceVM) this.mViewModel).placeId, floorId, roomId, groupName, item.productInfo.getProductId(), item.productInfo.getHardwareId(), new ArrayList()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).doFinally(new ActAddDevice$$ExternalSyntheticLambda12(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActAddDevice$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddDevice.this.lambda$addGroup$12(groupName, floorId, roomId, item, (AddGroupResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addGroup$11() {
        showLoadingDialog(getString(R.string.creating));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addGroup$12(String str, long j, long j2, ActAddDeviceVM.ProductItem productItem, AddGroupResponse addGroupResponse) throws Exception {
        Group group = new Group();
        group.setGroupName(str);
        group.setGroupAddress(Integer.parseInt(addGroupResponse.getIndex(), 16));
        group.setPlaceId(addGroupResponse.getPlaceid());
        group.setGroupId(addGroupResponse.getGroupid());
        group.setModuleType(addGroupResponse.getType());
        group.setControlType(addGroupResponse.getColortype());
        group.setDeviceIds(new ArrayList());
        group.setGroupState(new DeviceState());
        int zoneCount = ProductRepository.getZoneCount(ProductRepository.getLightColorType((Object) group));
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < zoneCount; i++) {
            arrayList.add(false);
        }
        group.getGroupState().setOnOffStates(arrayList);
        group.setFloorId(j);
        group.setRoomId(j2);
        group.setGroupIndex(Integer.parseInt(addGroupResponse.getIndex(), 16));
        group.setCreatetime(addGroupResponse.getCreatetime());
        group.setParam(addGroupResponse.getParam());
        group.setExtParam(addGroupResponse.getParamext());
        group.setEditTime(System.currentTimeMillis());
        NavUtils.destination(productItem.navClz).withBoolean(Constants.CREATE, true).withLong(Constants.PLACE_ID, ((ActAddDeviceVM) this.mViewModel).placeId).withLong(Constants.GROUP_ID, Injection.repo().group().lambda$saveGroup$1(group)).navigation(this);
        SPUtils.getInstance().put(Constants.USER_CUR_ROOM_FOR_GROUP, j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setGroupPublishAddress$13() {
        showLoadingDialog(getString(R.string.creating));
    }

    private void setGroupPublishAddress(final ActAddDeviceVM.ProductItem item, final long floorId, final long roomId, final String groupName) {
        getMainHandler().post(new Runnable() { // from class: com.ltech.smarthome.ui.config.ActAddDevice$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                ActAddDevice.this.lambda$setGroupPublishAddress$13();
            }
        });
        ((ObservableSubscribeProxy) Injection.net().addGroup(((ActAddDeviceVM) this.mViewModel).placeId, floorId, roomId, "", ProductId.CC.getModuleType(ProductId.BLE_GROUP_REMOTE_CONTROLLER), ProductId.CC.getControlType(ProductId.BLE_GROUP_REMOTE_CONTROLLER), new ArrayList()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).doFinally(new ActAddDevice$$ExternalSyntheticLambda12(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActAddDevice$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddDevice.this.lambda$setGroupPublishAddress$14(item, floorId, roomId, groupName, (AddGroupResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setGroupPublishAddress$14(ActAddDeviceVM.ProductItem productItem, long j, long j2, String str, AddGroupResponse addGroupResponse) throws Exception {
        EurHelper.eurGroupParam = new EurPanelGroupParam();
        EurHelper.eurGroupParam.setPublicationAddress(Integer.parseInt(addGroupResponse.getIndex(), 16));
        EurHelper.eurGroupParam.setPublicationId(addGroupResponse.getGroupid());
        addGroup(productItem, j, j2, str);
    }

    private void delGroup(long groupId) {
        ((ObservableSubscribeProxy) Injection.net().deleteGroup(groupId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActAddDevice$$ExternalSyntheticLambda18
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddDevice.lambda$delGroup$15(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissGroupDialog() {
        SetBleTypeDialog setBleTypeDialog = this.dialog;
        if (setBleTypeDialog != null) {
            setBleTypeDialog.dismissDialog();
            this.dialog = null;
        }
    }

    private boolean checkPhotoPermission() {
        int checkSelfPermission;
        if (Build.VERSION.SDK_INT >= 23) {
            checkSelfPermission = checkSelfPermission(Permission.CAMERA);
            if (checkSelfPermission != 0) {
                ActivityCompat.requestPermissions(this, new String[]{Permission.CAMERA}, 6666);
                return false;
            }
        }
        return true;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 6666) {
            ConfigHelper.instance().placeId = ((ActAddDeviceVM) this.mViewModel).placeId;
            ConfigHelper.instance().roomId = getIntent().getLongExtra(Constants.ROOM_ID, -1L);
            NavUtils.destination(ActQrCodeScan.class).navigation(this);
        }
    }

    public class BlueToothValueReceiver extends BroadcastReceiver {
        public static final int DEFAULT_VALUE_BULUETOOTH = 1000;

        public BlueToothValueReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(intent.getAction())) {
                switch (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 1000)) {
                    case 10:
                        LogUtils.e("蓝牙已关闭");
                        break;
                    case 11:
                        LogUtils.e("正在打开蓝牙");
                        break;
                    case 12:
                        LogUtils.e("蓝牙已打开");
                        ActAddDevice.this.initListData();
                        break;
                    case 13:
                        LogUtils.e("正在关闭蓝牙");
                        Injection.mesh().stopScan();
                        break;
                    default:
                        LogUtils.e("未知状态");
                        break;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initListData() {
        ((ActAddDeviceVM) this.mViewModel).deviceList.clear();
        ((ActAddDeviceVM) this.mViewModel).addList.getValue().clear();
        ((ActAddDeviceVM) this.mViewModel).startScanLiveData.setValue(true);
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        Injection.mesh().stopScan();
    }
}