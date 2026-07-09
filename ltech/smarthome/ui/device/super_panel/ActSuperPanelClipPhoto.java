package com.ltech.smarthome.ui.device.super_panel;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.imageclip.util.FileUtil;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActSuperPanelClipPhotoBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.photo.UploadPhotoRequest;
import com.ltech.smarthome.net.response.user.UploadImageFileResponse;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageHelper;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSuperPanelClipPhoto extends BaseNormalActivity<ActSuperPanelClipPhotoBinding> {
    private long deviceId;
    private boolean groupControl;
    private long groupId;
    private boolean isRoundCut;
    private int lastSelectPosition;
    private String mac;
    private BaseQuickAdapter<String, BaseViewHolder> photoAdapter;
    private String productId;
    private int selectPosition;
    private List<FtClipPhoto> fragments = new ArrayList();
    private List<UploadPhotoRequest.Photo> photoList = new ArrayList();
    private MutableLiveData<Integer> uploadIndex = new MutableLiveData<>();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_super_panel_clip_photo;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
        this.groupId = getIntent().getLongExtra(Constants.GROUP_ID, -1L);
        this.groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        this.mac = getIntent().getStringExtra(Constants.MAC_ADDRESS);
        this.isRoundCut = getIntent().getBooleanExtra(Constants.ROUND_CUT, false);
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        if (this.isRoundCut) {
            ((ActSuperPanelClipPhotoBinding) this.mViewBinding).ivCrop.setVisibility(8);
        } else {
            ((ActSuperPanelClipPhotoBinding) this.mViewBinding).ivCrop.setVisibility(0);
        }
        ((ActSuperPanelClipPhotoBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelClipPhoto$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActSuperPanelClipPhoto.this.lambda$initView$0((View) obj);
            }
        }));
        BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_img, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelClipPhoto.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, String image) {
                if (helper.getAdapterPosition() == ActSuperPanelClipPhoto.this.selectPosition) {
                    helper.itemView.setBackgroundResource(R.drawable.shape_red_stroke_no_radius);
                } else {
                    helper.itemView.setBackgroundResource(0);
                }
                ISNav.getInstance().displayImage(ActSuperPanelClipPhoto.this.activity, image, (ImageView) helper.getView(R.id.iv_image));
            }
        };
        this.photoAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelClipPhoto$$ExternalSyntheticLambda6
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActSuperPanelClipPhoto.this.lambda$initView$1(baseQuickAdapter2, view, i);
            }
        });
        this.photoAdapter.bindToRecyclerView(((ActSuperPanelClipPhotoBinding) this.mViewBinding).rvContent);
        ((ActSuperPanelClipPhotoBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this, 0, false));
        ((ActSuperPanelClipPhotoBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((ActSuperPanelClipPhotoBinding) this.mViewBinding).rvContent.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelClipPhoto.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(5.0f), 0, ConvertUtils.dp2px(5.0f), 0);
            }
        });
        this.photoAdapter.replaceData(ImageHelper.tempImageList);
        initViewPager();
        this.selectPosition = ImageHelper.currentPosition;
        ((ActSuperPanelClipPhotoBinding) this.mViewBinding).ivEdit.setImageResource(ImageHelper.imageList.contains(ImageHelper.tempImageList.get(this.selectPosition)) ? R.mipmap.ic_img_checked : R.mipmap.ic_img_uncheck);
        scrollToPosition(this.selectPosition);
        changeNumber(ImageHelper.imageList.size());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        int id = view.getId();
        if (id == R.id.iv_back) {
            finish();
            return;
        }
        if (id == R.id.iv_edit) {
            edit();
        } else if (id == R.id.tv_upload && ImageHelper.imageList.size() > 0) {
            this.photoList.clear();
            showLoadingDialog(getString(R.string.saving));
            this.uploadIndex.setValue(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int i2 = this.selectPosition;
        if (i2 != i) {
            this.lastSelectPosition = i2;
            this.selectPosition = i;
            this.photoAdapter.notifyItemChanged(i);
            this.photoAdapter.notifyItemChanged(this.lastSelectPosition);
            ((ActSuperPanelClipPhotoBinding) this.mViewBinding).ivEdit.setImageResource(ImageHelper.imageList.contains(ImageHelper.tempImageList.get(this.selectPosition)) ? R.mipmap.ic_img_checked : R.mipmap.ic_img_uncheck);
            scrollToPosition(this.selectPosition);
            ((ActSuperPanelClipPhotoBinding) this.mViewBinding).viewpager.setCurrentItem(this.selectPosition);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        this.uploadIndex.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelClipPhoto$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSuperPanelClipPhoto.this.lambda$startObserve$2((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Integer num) {
        if (num.intValue() < this.fragments.size()) {
            if (ImageHelper.imageList.contains(ImageHelper.tempImageList.get(num.intValue()))) {
                saveSuperPanelImage(this.fragments.get(num.intValue()).generateUri());
                return;
            } else {
                MutableLiveData<Integer> mutableLiveData = this.uploadIndex;
                mutableLiveData.setValue(Integer.valueOf(mutableLiveData.getValue().intValue() + 1));
                return;
            }
        }
        savePhotoList();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (ImageHelper.imageList.contains(ImageHelper.tempImageList.get(this.selectPosition))) {
            ImageHelper.imageList.remove(ImageHelper.tempImageList.get(this.selectPosition));
        } else {
            ImageHelper.imageList.add(ImageHelper.tempImageList.get(this.selectPosition));
        }
        changeNumber(ImageHelper.imageList.size());
        ((ActSuperPanelClipPhotoBinding) this.mViewBinding).ivEdit.setImageResource(ImageHelper.imageList.contains(ImageHelper.tempImageList.get(this.selectPosition)) ? R.mipmap.ic_img_checked : R.mipmap.ic_img_uncheck);
    }

    private void initViewPager() {
        int i = LanguageUtils.isChinese(this) ? R.mipmap.albums_box_crop_cn : R.mipmap.albums_box_crop_en;
        String str = this.productId;
        if (str != null) {
            str.hashCode();
            switch (str) {
                case "123050811340901":
                case "122080911090801":
                case "121052512023201":
                case "120010615085201":
                    if (LanguageUtils.isChinese(this)) {
                        i = R.mipmap.albums_box_crop_4s_cn;
                        break;
                    } else {
                        i = R.mipmap.albums_box_crop_4s_en;
                        break;
                    }
                case "122042815485901":
                    if (LanguageUtils.isChinese(this)) {
                        i = R.mipmap.albums_box_crop_6s_cn;
                        break;
                    } else {
                        i = R.mipmap.albums_box_crop_6s_en;
                        break;
                    }
            }
        }
        ((ActSuperPanelClipPhotoBinding) this.mViewBinding).ivCrop.setImageResource(i);
        for (int i2 = 0; i2 < ImageHelper.tempImageList.size(); i2++) {
            this.fragments.add(FtClipPhoto.newInstance(ImageHelper.tempImageList.get(i2), this.mac, this.isRoundCut, this.productId));
        }
        ((ActSuperPanelClipPhotoBinding) this.mViewBinding).viewpager.setAdapter(new FragmentStateAdapter(this) { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelClipPhoto.3
            @Override // androidx.viewpager2.adapter.FragmentStateAdapter
            public Fragment createFragment(int position) {
                return (Fragment) ActSuperPanelClipPhoto.this.fragments.get(position);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return ActSuperPanelClipPhoto.this.fragments.size();
            }
        });
        ((ActSuperPanelClipPhotoBinding) this.mViewBinding).viewpager.setCurrentItem(this.selectPosition);
        ((ActSuperPanelClipPhotoBinding) this.mViewBinding).viewpager.setOffscreenPageLimit(ISNav.getInstance().getConfig().maxNum);
        ((ActSuperPanelClipPhotoBinding) this.mViewBinding).viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelClipPhoto.4
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int position) {
                if (ActSuperPanelClipPhoto.this.selectPosition != position) {
                    ActSuperPanelClipPhoto actSuperPanelClipPhoto = ActSuperPanelClipPhoto.this;
                    actSuperPanelClipPhoto.lastSelectPosition = actSuperPanelClipPhoto.selectPosition;
                    ActSuperPanelClipPhoto.this.selectPosition = position;
                    ActSuperPanelClipPhoto.this.photoAdapter.notifyItemChanged(ActSuperPanelClipPhoto.this.selectPosition);
                    ActSuperPanelClipPhoto.this.photoAdapter.notifyItemChanged(ActSuperPanelClipPhoto.this.lastSelectPosition);
                    ((ActSuperPanelClipPhotoBinding) ActSuperPanelClipPhoto.this.mViewBinding).ivEdit.setImageResource(ImageHelper.imageList.contains(ImageHelper.tempImageList.get(ActSuperPanelClipPhoto.this.selectPosition)) ? R.mipmap.ic_img_checked : R.mipmap.ic_img_uncheck);
                    ActSuperPanelClipPhoto actSuperPanelClipPhoto2 = ActSuperPanelClipPhoto.this;
                    actSuperPanelClipPhoto2.scrollToPosition(actSuperPanelClipPhoto2.selectPosition);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scrollToPosition(int position) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) ((ActSuperPanelClipPhotoBinding) this.mViewBinding).rvContent.getLayoutManager();
        if (position < linearLayoutManager.findFirstVisibleItemPosition() || position > linearLayoutManager.findLastVisibleItemPosition()) {
            ((ActSuperPanelClipPhotoBinding) this.mViewBinding).rvContent.scrollToPosition(position);
        }
    }

    public void saveSuperPanelImage(Uri uri) {
        final File file = new File(FileUtil.getRealFilePathFromUri(getApplicationContext(), uri));
        ((ObservableSubscribeProxy) Injection.net().uploadHeaderFile(file).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelClipPhoto$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSuperPanelClipPhoto.this.lambda$saveSuperPanelImage$3(file, (UploadImageFileResponse) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveSuperPanelImage$3(File file, UploadImageFileResponse uploadImageFileResponse) throws Exception {
        UploadPhotoRequest.Photo photo = new UploadPhotoRequest.Photo();
        photo.setUrl(uploadImageFileResponse.getUrl());
        photo.setPath(uploadImageFileResponse.getPath());
        photo.setSize(file.length());
        this.photoList.add(photo);
        MutableLiveData<Integer> mutableLiveData = this.uploadIndex;
        mutableLiveData.setValue(Integer.valueOf(mutableLiveData.getValue().intValue() + 1));
    }

    public void savePhotoList() {
        if (this.groupControl) {
            ((ObservableSubscribeProxy) Injection.net().updateColorPalette(this.groupId, 3, this.photoList.get(0).getUrl()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelClipPhoto$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSuperPanelClipPhoto.this.lambda$savePhotoList$4(obj);
                }
            }, new SmartErrorComsumer());
        } else {
            ((ObservableSubscribeProxy) Injection.net().uploadPhotoList(this.deviceId, this.mac, this.photoList).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelClipPhoto$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActSuperPanelClipPhoto.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelClipPhoto$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSuperPanelClipPhoto.this.lambda$savePhotoList$5(obj);
                }
            }, new SmartErrorComsumer());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$savePhotoList$4(Object obj) throws Exception {
        Group groupByGroupId = Injection.repo().group().getGroupByGroupId(this.groupId);
        groupByGroupId.setColorPaletteUrl(this.photoList.get(0).getUrl());
        setColorPaletteType(3);
        Injection.repo().group().saveGroup(groupByGroupId);
        showSuccessDialog(getString(R.string.save_success));
        Intent intent = new Intent();
        intent.putExtra("data", this.photoList.get(0).getUrl());
        setResult(3021, intent);
        finishActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$savePhotoList$5(Object obj) throws Exception {
        showSuccessDialog(getString(R.string.save_success));
        setResult(3021);
        finishActivity();
    }

    private void changeNumber(int integer) {
        ((ActSuperPanelClipPhotoBinding) this.mViewBinding).tvUpload.setEnabled(integer > 0);
        ((ActSuperPanelClipPhotoBinding) this.mViewBinding).tvUpload.setText(String.format(getResources().getString(R.string.str_upload_with_num), Integer.valueOf(integer)));
    }

    public void setColorPaletteType(int colorPaletteType) {
        if (this.groupControl) {
            SPUtils.getInstance().put("colorpalettetype#" + this.groupId, colorPaletteType);
        }
    }
}