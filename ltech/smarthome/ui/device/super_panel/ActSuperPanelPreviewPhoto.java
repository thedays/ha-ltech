package com.ltech.smarthome.ui.device.super_panel;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.blankj.utilcode.util.ConvertUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.imageclip.ClipViewLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActSuperPanelPreviewPhotoBinding;
import com.ltech.smarthome.utils.Constants;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageHelper;
import java.io.File;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActSuperPanelPreviewPhoto extends BaseNormalActivity<ActSuperPanelPreviewPhotoBinding> {
    private int lastSelectPosition;
    private ImageAdapter pageAdapter;
    private BaseQuickAdapter<String, BaseViewHolder> photoAdapter;
    private boolean selectMode;
    private int selectPosition;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_super_panel_preview_photo;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        this.selectMode = getIntent().getBooleanExtra(Constants.SELECT_MODE, false);
        this.selectPosition = getIntent().getIntExtra(Constants.SELECT_POSITION, 0);
        if (!this.selectMode) {
            ((ActSuperPanelPreviewPhotoBinding) this.mViewBinding).ivEdit.setVisibility(8);
        }
        ((ActSuperPanelPreviewPhotoBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelPreviewPhoto$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActSuperPanelPreviewPhoto.this.lambda$initView$0((View) obj);
            }
        }));
        BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_img, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelPreviewPhoto.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, String image) {
                if (helper.getAdapterPosition() == ActSuperPanelPreviewPhoto.this.selectPosition) {
                    helper.itemView.setBackgroundResource(R.drawable.shape_red_stroke_no_radius);
                } else {
                    helper.itemView.setBackgroundResource(0);
                }
                ISNav.getInstance().displayImage(ActSuperPanelPreviewPhoto.this.activity, image, (ImageView) helper.getView(R.id.iv_image));
            }
        };
        this.photoAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelPreviewPhoto$$ExternalSyntheticLambda1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActSuperPanelPreviewPhoto.this.lambda$initView$1(baseQuickAdapter2, view, i);
            }
        });
        this.photoAdapter.bindToRecyclerView(((ActSuperPanelPreviewPhotoBinding) this.mViewBinding).rvContent);
        ((ActSuperPanelPreviewPhotoBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this, 0, false));
        ((ActSuperPanelPreviewPhotoBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((ActSuperPanelPreviewPhotoBinding) this.mViewBinding).rvContent.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelPreviewPhoto.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(5.0f), 0, ConvertUtils.dp2px(5.0f), 0);
            }
        });
        this.photoAdapter.replaceData(ImageHelper.tempImageList);
        initViewPager();
        this.selectPosition = ImageHelper.currentPosition;
        ((ActSuperPanelPreviewPhotoBinding) this.mViewBinding).ivEdit.setImageResource(ImageHelper.imageList.contains(ImageHelper.tempImageList.get(this.selectPosition)) ? R.mipmap.ic_img_checked : R.mipmap.ic_img_uncheck);
        scrollToPosition(this.selectPosition);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        int id = view.getId();
        if (id == R.id.iv_back) {
            finish();
        } else {
            if (id != R.id.iv_edit) {
                return;
            }
            edit();
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
            ((ActSuperPanelPreviewPhotoBinding) this.mViewBinding).ivEdit.setImageResource(ImageHelper.imageList.contains(ImageHelper.tempImageList.get(this.selectPosition)) ? R.mipmap.ic_img_checked : R.mipmap.ic_img_uncheck);
            scrollToPosition(this.selectPosition);
            ((ActSuperPanelPreviewPhotoBinding) this.mViewBinding).viewpager.setCurrentItem(this.selectPosition);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (ImageHelper.imageList.contains(ImageHelper.tempImageList.get(this.selectPosition))) {
            ImageHelper.imageList.remove(ImageHelper.tempImageList.get(this.selectPosition));
        } else {
            ImageHelper.imageList.add(ImageHelper.tempImageList.get(this.selectPosition));
        }
        ((ActSuperPanelPreviewPhotoBinding) this.mViewBinding).ivEdit.setImageResource(ImageHelper.imageList.contains(ImageHelper.tempImageList.get(this.selectPosition)) ? R.mipmap.ic_img_checked : R.mipmap.ic_img_uncheck);
    }

    private void initViewPager() {
        this.pageAdapter = new ImageAdapter();
        ((ActSuperPanelPreviewPhotoBinding) this.mViewBinding).viewpager.setAdapter(this.pageAdapter);
        ((ActSuperPanelPreviewPhotoBinding) this.mViewBinding).viewpager.setCurrentItem(this.selectPosition);
        ((ActSuperPanelPreviewPhotoBinding) this.mViewBinding).viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelPreviewPhoto.3
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int position) {
                if (ActSuperPanelPreviewPhoto.this.selectPosition != position) {
                    ActSuperPanelPreviewPhoto actSuperPanelPreviewPhoto = ActSuperPanelPreviewPhoto.this;
                    actSuperPanelPreviewPhoto.lastSelectPosition = actSuperPanelPreviewPhoto.selectPosition;
                    ActSuperPanelPreviewPhoto.this.selectPosition = position;
                    ActSuperPanelPreviewPhoto.this.photoAdapter.notifyItemChanged(ActSuperPanelPreviewPhoto.this.selectPosition);
                    ActSuperPanelPreviewPhoto.this.photoAdapter.notifyItemChanged(ActSuperPanelPreviewPhoto.this.lastSelectPosition);
                    ((ActSuperPanelPreviewPhotoBinding) ActSuperPanelPreviewPhoto.this.mViewBinding).ivEdit.setImageResource(ImageHelper.imageList.contains(ImageHelper.tempImageList.get(ActSuperPanelPreviewPhoto.this.selectPosition)) ? R.mipmap.ic_img_checked : R.mipmap.ic_img_uncheck);
                    ActSuperPanelPreviewPhoto actSuperPanelPreviewPhoto2 = ActSuperPanelPreviewPhoto.this;
                    actSuperPanelPreviewPhoto2.scrollToPosition(actSuperPanelPreviewPhoto2.selectPosition);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scrollToPosition(int position) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) ((ActSuperPanelPreviewPhotoBinding) this.mViewBinding).rvContent.getLayoutManager();
        if (position < linearLayoutManager.findFirstVisibleItemPosition() || position > linearLayoutManager.findLastVisibleItemPosition()) {
            ((ActSuperPanelPreviewPhotoBinding) this.mViewBinding).rvContent.scrollToPosition(position);
        }
    }

    private class ImageAdapter extends PagerAdapter {
        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        private ImageAdapter() {
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return ImageHelper.tempImageList.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public View instantiateItem(ViewGroup container, int position) {
            View inflate = View.inflate(ActSuperPanelPreviewPhoto.this.activity, R.layout.item_img_preview, null);
            final ClipViewLayout clipViewLayout = (ClipViewLayout) inflate.findViewById(R.id.clipViewLayout);
            if (!ActSuperPanelPreviewPhoto.this.selectMode) {
                Glide.with((FragmentActivity) ActSuperPanelPreviewPhoto.this.activity).asBitmap().load(ImageHelper.tempImageList.get(position)).into((RequestBuilder<Bitmap>) new CustomTarget<Bitmap>(this) { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelPreviewPhoto.ImageAdapter.1
                    @Override // com.bumptech.glide.request.target.Target
                    public void onLoadCleared(Drawable placeholder) {
                    }

                    @Override // com.bumptech.glide.request.target.Target
                    public /* bridge */ /* synthetic */ void onResourceReady(Object bitmap, Transition transition) {
                        onResourceReady((Bitmap) bitmap, (Transition<? super Bitmap>) transition);
                    }

                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        clipViewLayout.setImageSrc(bitmap);
                    }
                });
            } else {
                clipViewLayout.setImageSrc(Uri.fromFile(new File(ImageHelper.tempImageList.get(position))));
            }
            container.addView(inflate, -1, -1);
            return inflate;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}