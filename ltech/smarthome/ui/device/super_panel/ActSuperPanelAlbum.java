package com.ltech.smarthome.ui.device.super_panel;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActSuperPanelAlbumBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.photo.SortPhotoRequest;
import com.ltech.smarthome.net.response.photo.ListPhotoResponse;
import com.ltech.smarthome.singleton.PathManager;
import com.ltech.smarthome.ui.device.super_panel.ActSuperPanelAlbum;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.VersionUtils;
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
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageHelper;
import com.yuyh.library.imgsel.config.ISListConfig;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSuperPanelAlbum extends BaseNormalActivity<ActSuperPanelAlbumBinding> {
    private static final int REQUEST_CAPTURE = 100;
    private static final int REQUEST_WRITE_STORAGE = 3;
    private File camerafile;
    private boolean clickEnable;
    private long deviceId;
    private BaseItemDraggableAdapter<ListPhotoResponse.RowsBean, BaseViewHolder> draggableAdapter;
    private boolean isOnline;
    private int itemCount;
    private ItemTouchHelper itemTouchHelper;
    private String mac;
    private String productId;
    List<ListPhotoResponse.RowsBean> photoList = new ArrayList();
    private MutableLiveData<Integer> photoNumber = new MutableLiveData<>();
    private MutableLiveData<Boolean> editMode = new MutableLiveData<>(false);
    private List<Long> selectPictureIds = new ArrayList();
    private int maxLimit = 8;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected boolean isRootViewClickEnable() {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.content;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_super_panel_album;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditString("");
        setTitle(getString(R.string.family_album));
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
        this.mac = getIntent().getStringExtra(Constants.MAC_ADDRESS);
        this.isOnline = getIntent().getBooleanExtra(Constants.DEVICE_IS_ONLINE, false);
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ((ActSuperPanelAlbumBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelAlbum$$ExternalSyntheticLambda9
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActSuperPanelAlbum.this.lambda$initView$2((View) obj);
            }
        }));
        setWindowSize();
        initDraggableAdapter();
        showEmpty();
        changeDeleteBtn();
        getPhotoList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(View view) {
        if (view.getId() == R.id.tv_upload) {
            if (this.maxLimit == 0) {
                return;
            }
            if (this.isOnline) {
                showPhotoDialog();
                return;
            } else {
                SmartToast.showCenterShort(getString(R.string.device_offline));
                return;
            }
        }
        if (view.getId() == R.id.tv_delete) {
            MessageDialog.show(this, getString(R.string.str_delete_confirm), getString(R.string.delete_photo_tip)).setOkButton(R.string.delete, new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelAlbum$$ExternalSyntheticLambda6
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view2) {
                    boolean lambda$initView$1;
                    lambda$initView$1 = ActSuperPanelAlbum.this.lambda$initView$1(baseDialog, view2);
                    return lambda$initView$1;
                }
            }).setCancelButton(R.string.cancel);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initView$1(BaseDialog baseDialog, View view) {
        ((ObservableSubscribeProxy) Injection.net().deletePhoto(this.selectPictureIds).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelAlbum$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSuperPanelAlbum.this.lambda$initView$0(obj);
            }
        }, new SmartErrorComsumer());
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(Object obj) throws Exception {
        ArrayList arrayList = new ArrayList();
        for (ListPhotoResponse.RowsBean rowsBean : this.photoList) {
            if (this.selectPictureIds.contains(Long.valueOf(rowsBean.getPictureid()))) {
                arrayList.add(rowsBean);
            }
        }
        this.photoList.removeAll(arrayList);
        this.selectPictureIds.clear();
        this.photoNumber.setValue(Integer.valueOf(this.photoList.size()));
        if (this.photoList.size() == 0) {
            this.editMode.setValue(false);
        } else {
            changeDeleteBtn();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0048, code lost:
    
        if (r4.equals(com.ltech.smarthome.model.product.ProductId.ID_ANDROID_SUPER_PANEL_4S) == false) goto L6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void setWindowSize() {
        /*
            r8 = this;
            r0 = 2
            r8.itemCount = r0
            V extends androidx.databinding.ViewDataBinding r1 = r8.mViewBinding
            com.ltech.smarthome.databinding.ActSuperPanelAlbumBinding r1 = (com.ltech.smarthome.databinding.ActSuperPanelAlbumBinding) r1
            android.widget.FrameLayout r1 = r1.layoutBg
            android.view.ViewGroup$LayoutParams r1 = r1.getLayoutParams()
            int r2 = com.blankj.utilcode.util.ScreenUtils.getScreenWidth()
            r3 = 1096810496(0x41600000, float:14.0)
            int r3 = com.blankj.utilcode.util.ConvertUtils.dp2px(r3)
            int r2 = r2 - r3
            int r3 = r1.height
            java.lang.String r4 = r8.productId
            if (r4 == 0) goto L77
            r4.hashCode()
            int r5 = r4.hashCode()
            r6 = 3
            r7 = -1
            switch(r5) {
                case -1309274422: goto L56;
                case -324427448: goto L4b;
                case 294483828: goto L42;
                case 811752507: goto L37;
                case 956710656: goto L2c;
                default: goto L2a;
            }
        L2a:
            r0 = -1
            goto L60
        L2c:
            java.lang.String r0 = "120010615085201"
            boolean r0 = r4.equals(r0)
            if (r0 != 0) goto L35
            goto L2a
        L35:
            r0 = 4
            goto L60
        L37:
            java.lang.String r0 = "121052512023201"
            boolean r0 = r4.equals(r0)
            if (r0 != 0) goto L40
            goto L2a
        L40:
            r0 = 3
            goto L60
        L42:
            java.lang.String r5 = "122080911090801"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L60
            goto L2a
        L4b:
            java.lang.String r0 = "122042815485901"
            boolean r0 = r4.equals(r0)
            if (r0 != 0) goto L54
            goto L2a
        L54:
            r0 = 1
            goto L60
        L56:
            java.lang.String r0 = "123050811340901"
            boolean r0 = r4.equals(r0)
            if (r0 != 0) goto L5f
            goto L2a
        L5f:
            r0 = 0
        L60:
            switch(r0) {
                case 0: goto L6a;
                case 1: goto L65;
                case 2: goto L6a;
                case 3: goto L6a;
                case 4: goto L6a;
                default: goto L63;
            }
        L63:
            r2 = r3
            goto L6c
        L65:
            int r2 = r2 * 9
            int r2 = r2 / 16
            goto L6c
        L6a:
            r8.itemCount = r6
        L6c:
            r1.height = r2
            V extends androidx.databinding.ViewDataBinding r0 = r8.mViewBinding
            com.ltech.smarthome.databinding.ActSuperPanelAlbumBinding r0 = (com.ltech.smarthome.databinding.ActSuperPanelAlbumBinding) r0
            android.widget.FrameLayout r0 = r0.layoutBg
            r0.setLayoutParams(r1)
        L77:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelAlbum.setWindowSize():void");
    }

    private void getPhotoList() {
        ((ObservableSubscribeProxy) Injection.net().listPhoto(this.deviceId, this.mac).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelAlbum$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSuperPanelAlbum.this.lambda$getPhotoList$3((ListPhotoResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getPhotoList$3(ListPhotoResponse listPhotoResponse) throws Exception {
        this.photoList.clear();
        this.photoList.addAll(listPhotoResponse.getRows());
        this.photoNumber.setValue(Integer.valueOf(listPhotoResponse.getRows().size()));
    }

    private void initDraggableAdapter() {
        BaseItemDraggableAdapter<ListPhotoResponse.RowsBean, BaseViewHolder> baseItemDraggableAdapter = new BaseItemDraggableAdapter<ListPhotoResponse.RowsBean, BaseViewHolder>(R.layout.item_img_super_panel, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelAlbum.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, ListPhotoResponse.RowsBean item) {
                int i;
                int i2;
                if (ActSuperPanelAlbum.this.itemCount == 2) {
                    i = ((ScreenUtils.getScreenWidth() - ConvertUtils.dp2px(14.0f)) - ConvertUtils.dp2px(10.0f)) / 2;
                    if (ProductId.ID_ANDROID_SUPER_PANEL_6S.equals(ActSuperPanelAlbum.this.productId)) {
                        i2 = (i * 9) / 16;
                    }
                    i2 = 0;
                } else if (ActSuperPanelAlbum.this.itemCount == 3) {
                    i = ((ScreenUtils.getScreenWidth() - ConvertUtils.dp2px(14.0f)) - ConvertUtils.dp2px(20.0f)) / 3;
                    i2 = i;
                } else {
                    i = 0;
                    i2 = 0;
                }
                if (i != 0 && i2 != 0) {
                    helper.itemView.setLayoutParams(new RecyclerView.LayoutParams(-1, i2));
                }
                if (!((Boolean) ActSuperPanelAlbum.this.editMode.getValue()).booleanValue()) {
                    helper.setImageResource(R.id.iv_select, 0);
                } else {
                    helper.setImageResource(R.id.iv_select, ActSuperPanelAlbum.this.selectPictureIds.contains(Long.valueOf(item.getPictureid())) ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
                }
                Glide.with((FragmentActivity) ActSuperPanelAlbum.this.activity).load(item.getUrl()).apply((BaseRequestOptions<?>) RequestOptions.bitmapTransform(new RoundedCorners(SizeUtils.dp2px(5.0f)))).into((AppCompatImageView) helper.getView(R.id.iv_image));
            }
        };
        this.draggableAdapter = baseItemDraggableAdapter;
        baseItemDraggableAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelAlbum$$ExternalSyntheticLambda7
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSuperPanelAlbum.this.lambda$initDraggableAdapter$4(baseQuickAdapter, view, i);
            }
        });
        this.draggableAdapter.bindToRecyclerView(((ActSuperPanelAlbumBinding) this.mViewBinding).rvContent);
        ((ActSuperPanelAlbumBinding) this.mViewBinding).rvContent.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelAlbum.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (ActSuperPanelAlbum.this.itemCount == 2) {
                    if (parent.getChildLayoutPosition(view) % 2 == 0) {
                        outRect.set(0, ConvertUtils.dp2px(5.0f), ConvertUtils.dp2px(5.0f), ConvertUtils.dp2px(5.0f));
                        return;
                    } else {
                        outRect.set(ConvertUtils.dp2px(5.0f), ConvertUtils.dp2px(5.0f), 0, ConvertUtils.dp2px(5.0f));
                        return;
                    }
                }
                if (ActSuperPanelAlbum.this.itemCount == 3) {
                    if (parent.getChildLayoutPosition(view) % 3 == 0) {
                        outRect.set(0, ConvertUtils.dp2px(5.0f), ConvertUtils.dp2px(5.0f), ConvertUtils.dp2px(5.0f));
                    } else if (parent.getChildLayoutPosition(view) % 3 == 1) {
                        outRect.set(ConvertUtils.dp2px(5.0f), ConvertUtils.dp2px(5.0f), ConvertUtils.dp2px(5.0f), ConvertUtils.dp2px(5.0f));
                    } else {
                        outRect.set(ConvertUtils.dp2px(5.0f), ConvertUtils.dp2px(5.0f), 0, ConvertUtils.dp2px(5.0f));
                    }
                }
            }
        });
        ((ActSuperPanelAlbumBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(this, this.itemCount));
        ((ActSuperPanelAlbumBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        enableDrag();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDraggableAdapter$4(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (this.editMode.getValue().booleanValue()) {
            ListPhotoResponse.RowsBean rowsBean = this.photoList.get(i);
            if (this.selectPictureIds.contains(Long.valueOf(rowsBean.getPictureid()))) {
                this.selectPictureIds.remove(Long.valueOf(rowsBean.getPictureid()));
            } else {
                this.selectPictureIds.add(Long.valueOf(rowsBean.getPictureid()));
            }
            this.draggableAdapter.notifyItemChanged(i);
            changeDeleteBtn();
            return;
        }
        if (!this.clickEnable || this.photoList.size() <= 0) {
            return;
        }
        this.clickEnable = false;
        ImageHelper.imageList.clear();
        Iterator<ListPhotoResponse.RowsBean> it = this.photoList.iterator();
        while (it.hasNext()) {
            ImageHelper.imageList.add(it.next().getUrl());
        }
        ImageHelper.tempImageList.clear();
        ImageHelper.tempImageList.addAll(ImageHelper.imageList);
        NavUtils.destination(ActSuperPanelPreviewPhoto.class).withInt(Constants.SELECT_POSITION, i).navigation(this);
    }

    /* renamed from: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelAlbum$3, reason: invalid class name */
    class AnonymousClass3 extends ItemDragAndSwipeCallback {
        AnonymousClass3(BaseItemDraggableAdapter adapter) {
            super(adapter);
        }

        @Override // com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback, androidx.recyclerview.widget.ItemTouchHelper.Callback
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            ActSuperPanelAlbum actSuperPanelAlbum = ActSuperPanelAlbum.this;
            actSuperPanelAlbum.photoList = actSuperPanelAlbum.draggableAdapter.getData();
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < ActSuperPanelAlbum.this.photoList.size(); i++) {
                SortPhotoRequest.Bean bean = new SortPhotoRequest.Bean();
                bean.setPictureid(ActSuperPanelAlbum.this.photoList.get(i).getPictureid());
                bean.setSorting(i);
                arrayList.add(bean);
            }
            ((ObservableSubscribeProxy) Injection.net().sortPhoto(arrayList).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(ActSuperPanelAlbum.this.activity, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelAlbum$3$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSuperPanelAlbum.AnonymousClass3.this.lambda$clearView$0(obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$clearView$0(Object obj) throws Exception {
            ((ActSuperPanelAlbumBinding) ActSuperPanelAlbum.this.mViewBinding).banner.setDatas(ActSuperPanelAlbum.this.photoList);
        }
    }

    private void enableDrag() {
        if (this.itemTouchHelper == null) {
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new AnonymousClass3(this.draggableAdapter));
            this.itemTouchHelper = itemTouchHelper;
            itemTouchHelper.attachToRecyclerView(((ActSuperPanelAlbumBinding) this.mViewBinding).rvContent);
        }
        this.draggableAdapter.enableDragItem(this.itemTouchHelper);
    }

    private void initBanner(List<ListPhotoResponse.RowsBean> list) {
        int i = LanguageUtils.isChinese(this) ? R.mipmap.album_preview_img_cn : R.mipmap.album_preview_img_en;
        String str = this.productId;
        if (str != null) {
            str.hashCode();
            switch (str) {
                case "123050811340901":
                case "122080911090801":
                case "121052512023201":
                case "120010615085201":
                    if (!LanguageUtils.isChinese(this)) {
                        i = R.mipmap.album_preview_img_4s_en;
                        break;
                    } else {
                        i = R.mipmap.album_preview_img_4s_cn;
                        break;
                    }
                case "122042815485901":
                    if (!LanguageUtils.isChinese(this)) {
                        i = R.mipmap.album_preview_img_6s_en;
                        break;
                    } else {
                        i = R.mipmap.album_preview_img_6s_cn;
                        break;
                    }
            }
        }
        ((ActSuperPanelAlbumBinding) this.mViewBinding).ivPreview.setImageResource(i);
        ((ActSuperPanelAlbumBinding) this.mViewBinding).banner.setDatas(list).isAutoLoop(true).setLoopTime(10000L).setUserInputEnabled(true).setBannerRound(SizeUtils.dp2px(5.0f)).setAdapter(new BannerImageAdapter(this, list) { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelAlbum.4
            @Override // com.youth.banner.holder.IViewHolder
            public void onBindView(Object holder, Object data, int position, int size) {
                BannerImageHolder bannerImageHolder = (BannerImageHolder) holder;
                Glide.with(bannerImageHolder.itemView).load(((ListPhotoResponse.RowsBean) data).getUrl()).into(bannerImageHolder.imageView);
            }
        }).start();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        this.photoNumber.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelAlbum$$ExternalSyntheticLambda10
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSuperPanelAlbum.this.lambda$startObserve$6((Integer) obj);
            }
        });
        this.editMode.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelAlbum$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSuperPanelAlbum.this.lambda$startObserve$7((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(Integer num) {
        this.maxLimit = 8 - num.intValue();
        if (num.intValue() == 0) {
            ((ActSuperPanelAlbumBinding) this.mViewBinding).banner.stop();
            setEditString("");
            showEmpty();
        } else {
            setEditString(getString(this.editMode.getValue().booleanValue() ? R.string.finish : R.string.delete));
            showContent();
            Collections.sort(this.photoList, new Comparator() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelAlbum$$ExternalSyntheticLambda8
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ActSuperPanelAlbum.lambda$startObserve$5((ListPhotoResponse.RowsBean) obj, (ListPhotoResponse.RowsBean) obj2);
                }
            });
            this.draggableAdapter.replaceData(this.photoList);
            initBanner(this.photoList);
            ((ActSuperPanelAlbumBinding) this.mViewBinding).tvUpload.setBackgroundResource(this.maxLimit == 0 ? R.drawable.shape_red_bt_20_2 : R.drawable.shape_red_bt_20);
        }
    }

    static /* synthetic */ int lambda$startObserve$5(ListPhotoResponse.RowsBean rowsBean, ListPhotoResponse.RowsBean rowsBean2) {
        return rowsBean.getSorting() - rowsBean2.getSorting();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(Boolean bool) {
        if (bool.booleanValue()) {
            this.draggableAdapter.notifyDataSetChanged();
            ((ActSuperPanelAlbumBinding) this.mViewBinding).tvDelete.setVisibility(0);
            ((ActSuperPanelAlbumBinding) this.mViewBinding).tvUpload.setVisibility(8);
            setEditString(getString(R.string.finish));
            this.draggableAdapter.disableDragItem();
            return;
        }
        this.selectPictureIds.clear();
        this.photoNumber.setValue(Integer.valueOf(this.photoList.size()));
        ((ActSuperPanelAlbumBinding) this.mViewBinding).tvUpload.setVisibility(0);
        ((ActSuperPanelAlbumBinding) this.mViewBinding).tvDelete.setVisibility(8);
        enableDrag();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        this.clickEnable = true;
        super.onResume();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        this.editMode.setValue(Boolean.valueOf(!r0.getValue().booleanValue()));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        int i;
        String str = this.productId;
        if (str != null) {
            str.hashCode();
            switch (str) {
                case "123050811340901":
                    i = R.mipmap.pic_pro_albums_empty;
                    break;
                case "122042815485901":
                    i = R.mipmap.pic_6s_albums_empty;
                    break;
                case "122080911090801":
                case "121052512023201":
                    i = R.mipmap.pic_4s_albums_empty;
                    break;
                case "120010615085201":
                    i = R.mipmap.pic_superpanel_albums_empty;
                    break;
            }
            return Gloading.from(new DefaultAdapter().emptyImageRes(i).emptyStringRes(R.string.photo_empty_tip));
        }
        i = R.mipmap.pic_albums_empty;
        return Gloading.from(new DefaultAdapter().emptyImageRes(i).emptyStringRes(R.string.photo_empty_tip));
    }

    private void changeDeleteBtn() {
        ((ActSuperPanelAlbumBinding) this.mViewBinding).tvDelete.setText(String.format(getResources().getString(R.string.delete_with_num), Integer.valueOf(this.selectPictureIds.size())));
        ((ActSuperPanelAlbumBinding) this.mViewBinding).tvDelete.setEnabled(this.selectPictureIds.size() != 0);
    }

    private void showPhotoDialog() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.camera));
        arrayList.add(getString(R.string.select_pic));
        SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelAlbum$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSuperPanelAlbum.this.lambda$showPhotoDialog$8((Integer) obj);
            }
        }).setSelectList(arrayList).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPhotoDialog$8(Integer num) {
        if (num.intValue() == 1) {
            if (this.maxLimit <= 0 || !checkWriteStoragePermission(3)) {
                return;
            }
            goPhotoSelect();
            return;
        }
        goCamera();
    }

    private void goPhotoSelect() {
        ISNav.getInstance().setConfig(new ISListConfig.Builder().multiSelect(true).maxNum(this.maxLimit).rememberSelected(false).needCamera(false).checkRes(R.mipmap.ic_img_checked, R.mipmap.ic_img_uncheck).build());
        NavUtils.destination(ActSuperPanelSelectPhoto.class).withString(Constants.PRODUCT_ID, this.productId).withLong("device_id", getIntent().getLongExtra("device_id", -1L)).withString(Constants.MAC_ADDRESS, getIntent().getStringExtra(Constants.MAC_ADDRESS)).withDefaultRequestCode().navigation(this);
    }

    private void goCamera() {
        String[] strArr;
        if (VersionUtils.isAndroidQ()) {
            strArr = new String[]{Permission.CAMERA};
        } else {
            strArr = new String[]{Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE, Permission.CAMERA};
        }
        AndPermission.with((Activity) this).runtime().permission(strArr).onGranted(new Action() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelAlbum$$ExternalSyntheticLambda4
            @Override // com.yanzhenjie.permission.Action
            public final void onAction(Object obj) {
                ActSuperPanelAlbum.this.lambda$goCamera$9((List) obj);
            }
        }).onDenied(new Action() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelAlbum$$ExternalSyntheticLambda5
            @Override // com.yanzhenjie.permission.Action
            public final void onAction(Object obj) {
                ActSuperPanelAlbum.this.lambda$goCamera$10((List) obj);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$goCamera$9(List list) {
        this.camerafile = new File(PathManager.getCacheDir(this.activity), PathManager.getSuperPanelPicName(this.mac) + System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        Uri uriForFile = FileProvider.getUriForFile(this, "com.ltech.smarthome.provider", this.camerafile);
        intent.addFlags(1);
        intent.putExtra("output", uriForFile);
        startActivityForResult(intent, 100);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$goCamera$10(List list) {
        SmartToast.showShort(getString(R.string.permission_deny));
    }

    private void goClipPhoto() {
        NavUtils.destination(ActSuperPanelClipPhoto.class).withString(Constants.PRODUCT_ID, this.productId).withLong("device_id", getIntent().getLongExtra("device_id", -1L)).withString(Constants.MAC_ADDRESS, getIntent().getStringExtra(Constants.MAC_ADDRESS)).withDefaultRequestCode().navigation(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != 3) {
            return;
        }
        goPhotoSelect();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != -1 || 100 != requestCode) {
            if (resultCode == 3021) {
                getPhotoList();
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
}