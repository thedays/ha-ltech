package com.ltech.smarthome.ui.device.gqpro;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActGqProThemeBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.response.photo.ListPhotoResponse;
import com.ltech.smarthome.singleton.PathManager;
import com.ltech.smarthome.ui.device.gqpro.ActGqProVM;
import com.ltech.smarthome.ui.device.gqpro.BleSyncHelper;
import com.ltech.smarthome.ui.device.super_panel.ActSuperPanelClipPhoto;
import com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSelectPhoto;
import com.ltech.smarthome.utils.BitmapUtils;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.VersionUtils;
import com.ltech.smarthome.view.dialog.SelectGqThemeDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageHelper;
import com.yuyh.library.imgsel.config.ISListConfig;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActGqProTheme extends VMActivity<ActGqProThemeBinding, ActGqProVM> {
    private static final int REQUEST_CAPTURE = 100;
    private static final int REQUEST_WRITE_STORAGE = 3;
    private File camerafile;
    private BleSegmentSyncHelper mBleSyncHelper;
    private MultipleItemRvAdapter<ActGqProVM.ThemeItem, BaseViewHolder> themeAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_gq_pro_theme;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.app_str_custom_system_theme));
        ((ActGqProVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActGqProVM) this.mViewModel).device = Injection.repo().device().getDeviceById(((ActGqProVM) this.mViewModel).controlId);
        ((ActGqProVM) this.mViewModel).controlObject.setValue(((ActGqProVM) this.mViewModel).device);
        String stringExtra = getIntent().getStringExtra("image");
        if (stringExtra != null) {
            ((ActGqProVM) this.mViewModel).photoList = (List) GsonUtils.fromJson(stringExtra, new TypeToken<List<ListPhotoResponse.RowsBean>>(this) { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProTheme.1
            }.getType());
        }
        ((ActGqProVM) this.mViewModel).initThemeData();
        initListView();
        ((ActGqProThemeBinding) this.mViewBinding).tvDel.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProTheme.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (Boolean.TRUE.equals(((ActGqProVM) ActGqProTheme.this.mViewModel).showCancelBtnEvent.getValue())) {
                    ((ActGqProVM) ActGqProTheme.this.mViewModel).edit(false);
                    ((ActGqProThemeBinding) ActGqProTheme.this.mViewBinding).tvDel.setVisibility(8);
                    ((ActGqProThemeBinding) ActGqProTheme.this.mViewBinding).groupSync.setVisibility(0);
                    return;
                }
                ((ActGqProVM) ActGqProTheme.this.mViewModel).delete();
            }
        });
        ((ActGqProThemeBinding) this.mViewBinding).viewSync.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProTheme.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ActGqProTheme.this.showSyncDialog();
            }
        });
        this.mBleSyncHelper = new BleSegmentSyncHelper(this.activity, ((ActGqProVM) this.mViewModel).device, getMainHandler());
    }

    private void initListView() {
        RecyclerView recyclerView = ((ActGqProThemeBinding) this.mViewBinding).rv;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.activity, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        MultipleItemRvAdapter<ActGqProVM.ThemeItem, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<ActGqProVM.ThemeItem, BaseViewHolder>(new ArrayList()) { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProTheme.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(ActGqProVM.ThemeItem themeItem) {
                return themeItem.getItemType();
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new BaseItemProvider<ActGqProVM.ThemeItem, BaseViewHolder>(this) { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProTheme.4.1
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_gq_pro_theme_title;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 0;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder baseViewHolder, ActGqProVM.ThemeItem themeItem, int i) {
                        baseViewHolder.setText(R.id.tv_title, themeItem.getTitle());
                        baseViewHolder.setText(R.id.tv_edit, StringUtils.isEmpty(themeItem.getEditName()) ? "" : themeItem.getEditName());
                        baseViewHolder.addOnClickListener(R.id.tv_edit);
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<ActGqProVM.ThemeItem, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProTheme.4.2
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_gq_pro_theme;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 1;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder baseViewHolder, ActGqProVM.ThemeItem themeItem, int i) {
                        if (themeItem.isCustom()) {
                            new RequestOptions();
                            Glide.with((FragmentActivity) ActGqProTheme.this.activity).load(themeItem.getUrl()).apply((BaseRequestOptions<?>) RequestOptions.circleCropTransform()).into((ImageView) baseViewHolder.getView(R.id.iv_theme));
                        } else {
                            baseViewHolder.setImageResource(R.id.iv_theme, themeItem.getImg());
                        }
                        baseViewHolder.setVisible(R.id.iv_cover, themeItem.isSel());
                        baseViewHolder.setGone(R.id.iv_del, themeItem.isEdit() && themeItem.isCanDel());
                        baseViewHolder.setImageResource(R.id.iv_del, themeItem.isNeedDel() ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
                        baseViewHolder.setGone(R.id.tv_name, !StringUtils.isEmpty(themeItem.getTitle())).setText(R.id.tv_name, StringUtils.isEmpty(themeItem.getTitle()) ? "" : themeItem.getTitle());
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<ActGqProVM.ThemeItem, BaseViewHolder>(this) { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProTheme.4.3
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_gq_pro_theme;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 2;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder baseViewHolder, ActGqProVM.ThemeItem themeItem, int i) {
                        baseViewHolder.setImageResource(R.id.iv_theme, R.mipmap.ic_gq_add);
                    }
                });
            }
        };
        this.themeAdapter = multipleItemRvAdapter;
        multipleItemRvAdapter.finishInitialize();
        this.themeAdapter.bindToRecyclerView(((ActGqProThemeBinding) this.mViewBinding).rv);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProTheme.5
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int position) {
                return ((ActGqProVM.ThemeItem) ActGqProTheme.this.themeAdapter.getData().get(position)).getItemType() == 0 ? 3 : 1;
            }
        });
        ((ActGqProThemeBinding) this.mViewBinding).rv.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProTheme.6
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = ConvertUtils.dp2px(24.0f);
            }
        });
        this.themeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProTheme.7
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (view.getId() == R.id.tv_edit) {
                    if (((ActGqProVM) ActGqProTheme.this.mViewModel).isEdit) {
                        ((ActGqProVM) ActGqProTheme.this.mViewModel).selectedAll();
                        return;
                    }
                    ((ActGqProVM) ActGqProTheme.this.mViewModel).edit(true);
                    ((ActGqProThemeBinding) ActGqProTheme.this.mViewBinding).tvDel.setVisibility(0);
                    ((ActGqProThemeBinding) ActGqProTheme.this.mViewBinding).groupSync.setVisibility(8);
                    ((ActGqProVM) ActGqProTheme.this.mViewModel).showCancelBtnEvent.setValue(true);
                }
            }
        });
        this.themeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProTheme.8
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActGqProVM.ThemeItem themeItem = (ActGqProVM.ThemeItem) ActGqProTheme.this.themeAdapter.getData().get(i);
                if (themeItem.getItemType() == 1) {
                    if (((ActGqProVM) ActGqProTheme.this.mViewModel).isEdit) {
                        ((ActGqProVM) ActGqProTheme.this.mViewModel).selected(themeItem.getNum());
                        return;
                    } else {
                        SelectGqThemeDialog.asDefault(true).selectedPos(themeItem.getSort()).setData(((ActGqProVM) ActGqProTheme.this.mViewModel).getPreviewData()).setConfirmAction(new IAction<Integer>() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProTheme.8.1
                            @Override // com.ltech.smarthome.base.IAction
                            public void act(Integer integer) {
                                ArrayList arrayList = new ArrayList();
                                for (ActGqProVM.ThemeItem themeItem2 : ((ActGqProVM) ActGqProTheme.this.mViewModel).themes.getValue()) {
                                    if (themeItem2.getItemType() == 1) {
                                        arrayList.add(themeItem2);
                                    }
                                }
                                ((ActGqProVM) ActGqProTheme.this.mViewModel).selected(((ActGqProVM.ThemeItem) arrayList.get(integer.intValue())).getNum());
                            }
                        }).showDialog(ActGqProTheme.this.activity);
                        return;
                    }
                }
                if (themeItem.getItemType() == 2) {
                    ActGqProTheme.this.showPhotoDialog();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPhotoDialog() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.camera));
        arrayList.add(getString(R.string.select_pic));
        SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProTheme$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActGqProTheme.this.lambda$showPhotoDialog$0((Integer) obj);
            }
        }).setSelectList(arrayList).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPhotoDialog$0(Integer num) {
        if (num.intValue() == 1) {
            if (((ActGqProVM) this.mViewModel).maxCustomPic <= 0 || !checkWriteStoragePermission(3)) {
                return;
            }
            goPhotoSelect();
            return;
        }
        goCamera();
    }

    private void goCamera() {
        String[] strArr;
        if (VersionUtils.isAndroidQ()) {
            strArr = new String[]{Permission.CAMERA};
        } else {
            strArr = new String[]{Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE, Permission.CAMERA};
        }
        AndPermission.with((Activity) this).runtime().permission(strArr).onGranted(new Action() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProTheme$$ExternalSyntheticLambda1
            @Override // com.yanzhenjie.permission.Action
            public final void onAction(Object obj) {
                ActGqProTheme.this.lambda$goCamera$1((List) obj);
            }
        }).onDenied(new Action() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProTheme$$ExternalSyntheticLambda2
            @Override // com.yanzhenjie.permission.Action
            public final void onAction(Object obj) {
                ActGqProTheme.this.lambda$goCamera$2((List) obj);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$goCamera$1(List list) {
        this.camerafile = new File(PathManager.getCacheDir(this.activity), PathManager.getSuperPanelPicName(((ActGqProVM) this.mViewModel).device.getMaccode()) + System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        Uri uriForFile = FileProvider.getUriForFile(this, "com.ltech.smarthome.provider", this.camerafile);
        intent.addFlags(1);
        intent.putExtra("output", uriForFile);
        startActivityForResult(intent, 100);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$goCamera$2(List list) {
        SmartToast.showShort(getString(R.string.permission_deny));
    }

    private void goClipPhoto() {
        NavUtils.destination(ActSuperPanelClipPhoto.class).withLong("device_id", ((ActGqProVM) this.mViewModel).device.getDeviceId()).withString(Constants.MAC_ADDRESS, ((ActGqProVM) this.mViewModel).device.getWifiMac()).withString(Constants.PRODUCT_ID, ((ActGqProVM) this.mViewModel).device.getProductId()).withBoolean(Constants.ROUND_CUT, true).withDefaultRequestCode().navigation(this);
    }

    private void goPhotoSelect() {
        ISNav.getInstance().setConfig(new ISListConfig.Builder().multiSelect(true).maxNum(((ActGqProVM) this.mViewModel).maxCustomPic).rememberSelected(false).needCamera(false).checkRes(R.mipmap.ic_img_checked, R.mipmap.ic_img_uncheck).build());
        NavUtils.destination(ActSuperPanelSelectPhoto.class).withLong("device_id", getIntent().getLongExtra("device_id", -1L)).withString(Constants.MAC_ADDRESS, ((ActGqProVM) this.mViewModel).device.getWifiMac()).withString(Constants.PRODUCT_ID, ((ActGqProVM) this.mViewModel).device.getProductId()).withBoolean(Constants.ROUND_CUT, true).withDefaultRequestCode().navigation(this);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActGqProVM) this.mViewModel).themes.observe(this, new Observer<List<ActGqProVM.ThemeItem>>() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProTheme.9
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<ActGqProVM.ThemeItem> themeItems) {
                ActGqProTheme.this.themeAdapter.replaceData(themeItems);
            }
        });
        ((ActGqProVM) this.mViewModel).showCancelBtnEvent.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProTheme.10
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    ((ActGqProThemeBinding) ActGqProTheme.this.mViewBinding).tvDel.setText(ActGqProTheme.this.getString(R.string.cancel));
                } else {
                    ((ActGqProThemeBinding) ActGqProTheme.this.mViewBinding).tvDel.setText(ActGqProTheme.this.getString(R.string.delete));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSyncDialog() {
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), this.activity.getString(R.string.app_str_gqpro_syning), this.activity.getString(R.string.app_str_need_gqpro_sync_immediately), this.activity.getString(R.string.app_str_gqpro_sync_now), this.activity.getString(R.string.cancel)).setOkButton(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProTheme.12
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                if (((ActGqProVM) ActGqProTheme.this.mViewModel).photoList.isEmpty()) {
                    if (ActGqProTheme.this.mBleSyncHelper == null) {
                        return false;
                    }
                    ActGqProTheme.this.mBleSyncHelper.delete(7);
                    return false;
                }
                ArrayList arrayList = new ArrayList();
                for (ListPhotoResponse.RowsBean rowsBean : ((ActGqProVM) ActGqProTheme.this.mViewModel).photoList) {
                    if (rowsBean.getUrl() != null) {
                        arrayList.add(ActGqProTheme.this.downloadUrl(rowsBean.getUrl()));
                    }
                }
                ActGqProTheme.this.batchControl(arrayList);
                return false;
            }
        }).setCancelButton(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProTheme.11
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                if (!ActGqProTheme.this.isBlePermissionOk()) {
                    return false;
                }
                Injection.mesh().checkNearbyDevice(ActGqProTheme.this.activity);
                return false;
            }
        }).setCancelable(false).show();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != -1 || 100 != requestCode) {
            if (resultCode == 3021) {
                ((ActGqProVM) this.mViewModel).refreshTheme();
                return;
            }
            return;
        }
        ImageHelper.imageList.clear();
        ImageHelper.imageList.add(this.camerafile.getAbsolutePath());
        ImageHelper.tempImageList.clear();
        ImageHelper.tempImageList.add(this.camerafile.getAbsolutePath());
        ISNav.getInstance().setConfig(new ISListConfig.Builder().multiSelect(false).maxNum(1).rememberSelected(false).needCamera(false).build());
        goClipPhoto();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Observable<BleSyncHelper.BleSyncData> downloadUrl(final String url) {
        return Observable.create(new ObservableOnSubscribe<BleSyncHelper.BleSyncData>() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProTheme.13
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(final ObservableEmitter<BleSyncHelper.BleSyncData> emitter) throws Exception {
                Glide.with((FragmentActivity) ActGqProTheme.this.activity).asBitmap().load(url).apply((BaseRequestOptions<?>) new RequestOptions().format(DecodeFormat.PREFER_ARGB_8888)).into((RequestBuilder<Bitmap>) new SimpleTarget<Bitmap>() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProTheme.13.1
                    @Override // com.bumptech.glide.request.target.Target
                    public /* bridge */ /* synthetic */ void onResourceReady(Object resource, Transition transition) {
                        onResourceReady((Bitmap) resource, (Transition<? super Bitmap>) transition);
                    }

                    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                    public void onLoadFailed(Drawable errorDrawable) {
                        emitter.onError(new Throwable());
                        emitter.onComplete();
                    }

                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        byte[] argb8888ToArgb5658 = BitmapUtils.argb8888ToArgb5658(resource);
                        resource.recycle();
                        emitter.onNext(new BleSyncHelper.BleSyncData(argb8888ToArgb5658, 4, url));
                        emitter.onComplete();
                    }
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void batchControl(List<Observable<BleSyncHelper.BleSyncData>> request) {
        final ArrayList arrayList = new ArrayList();
        showLoadingDialog("");
        ((ObservableSubscribeProxy) Observable.concat(request).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.activity, Lifecycle.Event.ON_DESTROY)))).subscribe(new io.reactivex.Observer<BleSyncHelper.BleSyncData>() { // from class: com.ltech.smarthome.ui.device.gqpro.ActGqProTheme.14
            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d2) {
            }

            @Override // io.reactivex.Observer
            public void onNext(BleSyncHelper.BleSyncData data) {
                arrayList.add(data);
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
                ActGqProTheme.this.dismissLoadingDialog();
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                ActGqProTheme.this.dismissLoadingDialog();
                if (ActGqProTheme.this.mBleSyncHelper != null) {
                    ActGqProTheme.this.mBleSyncHelper.setDownloadData(arrayList, 3);
                    ActGqProTheme.this.mBleSyncHelper.startSync();
                }
            }
        });
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        BleSegmentSyncHelper bleSegmentSyncHelper = this.mBleSyncHelper;
        if (bleSegmentSyncHelper != null) {
            bleSegmentSyncHelper.disconnect();
        }
    }
}