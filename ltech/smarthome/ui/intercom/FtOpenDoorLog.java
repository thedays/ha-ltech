package com.ltech.smarthome.ui.intercom;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.widget.ImageView;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.databinding.FtLogBinding;
import com.ltech.smarthome.ui.item.OpenDoorLogItem;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.view.TimeLineView;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class FtOpenDoorLog extends VMFragment<FtLogBinding, FtLogVM> {
    private MultipleItemRvAdapter<OpenDoorLogItem, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_log;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        initRv();
        initRefresh();
        ((FtLogVM) this.mViewModel).getOpenDoorData();
    }

    private void initRefresh() {
        ((FtLogBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((FtLogBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((FtLogBinding) this.mViewBinding).refreshLayout.setHeaderHeight(100.0f);
        ((FtLogBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.intercom.FtOpenDoorLog$$ExternalSyntheticLambda2
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                FtOpenDoorLog.this.lambda$initRefresh$0(refreshLayout);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRefresh$0(RefreshLayout refreshLayout) {
        refreshData();
        refreshLayout.finishRefresh();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyImageRes(R.mipmap.pic_empty_1).emptyTryStringRes(R.string.retry).emptyStringRes(R.string.no_data));
    }

    private void initRv() {
        MultipleItemRvAdapter<OpenDoorLogItem, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<OpenDoorLogItem, BaseViewHolder>(new ArrayList()) { // from class: com.ltech.smarthome.ui.intercom.FtOpenDoorLog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(OpenDoorLogItem entity) {
                return entity.getType();
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new BaseItemProvider<OpenDoorLogItem, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.intercom.FtOpenDoorLog.1.1
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_intercom_log_open_door;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 1;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, OpenDoorLogItem data, int position) {
                        helper.setText(R.id.tv_time, data.getItem().getCaptureTime().split(" ")[1]).setText(R.id.tv_name, data.getItem().getLocation());
                        TimeLineView timeLineView = (TimeLineView) helper.getView(R.id.line);
                        if (data.getPositionType() == FtLogVM.POSITION_TYPE_FIRST) {
                            timeLineView.setShowTopLine(false);
                            timeLineView.setShowBottomLine(true);
                        } else if (data.getPositionType() == FtLogVM.POSITION_TYPE_END) {
                            timeLineView.setShowTopLine(true);
                            timeLineView.setShowBottomLine(false);
                        } else {
                            timeLineView.setShowTopLine(true);
                            timeLineView.setShowBottomLine(true);
                        }
                        if (Integer.parseInt(data.getItem().getResponse()) == 1) {
                            helper.setTextColor(R.id.tv_time, FtOpenDoorLog.this.getResources().getColor(R.color.color_text_red)).setTextColor(R.id.tv_name, FtOpenDoorLog.this.getResources().getColor(R.color.color_text_red));
                            helper.setText(R.id.tv_status, R.string.not_open);
                            helper.setGone(R.id.tv_status, true);
                        } else {
                            helper.setTextColor(R.id.tv_time, FtOpenDoorLog.this.getResources().getColor(R.color.color_text_dark_gray)).setTextColor(R.id.tv_name, FtOpenDoorLog.this.getResources().getColor(R.color.item_music_time_color));
                            helper.setGone(R.id.tv_status, false);
                        }
                        Glide.with(FtOpenDoorLog.this).load(data.getItem().getSPicUrl()).override(SizeUtils.dp2px(50.0f), SizeUtils.dp2px(40.0f)).apply((BaseRequestOptions<?>) new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(10))).error(RoundedBitmapDrawableFactory.create(FtOpenDoorLog.this.getResources(), FtOpenDoorLog.getRoundedCornerBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(FtOpenDoorLog.this.getResources(), R.mipmap.pic_load_error), SizeUtils.dp2px(50.0f), SizeUtils.dp2px(40.0f), true), 10))).into((ImageView) helper.getView(R.id.iv_capture));
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<OpenDoorLogItem, BaseViewHolder>(this) { // from class: com.ltech.smarthome.ui.intercom.FtOpenDoorLog.1.2
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_intercom_log_title;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 0;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, OpenDoorLogItem data, int position) {
                        helper.setText(R.id.tv_title, data.getTitle());
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<OpenDoorLogItem, BaseViewHolder>(this) { // from class: com.ltech.smarthome.ui.intercom.FtOpenDoorLog.1.3
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, OpenDoorLogItem data, int position) {
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_empty_foot_15;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 2;
                    }
                });
            }
        };
        this.mAdapter = multipleItemRvAdapter;
        multipleItemRvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.intercom.FtOpenDoorLog$$ExternalSyntheticLambda1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                FtOpenDoorLog.this.lambda$initRv$1(baseQuickAdapter, view, i);
            }
        });
        this.mAdapter.finishInitialize();
        this.mAdapter.bindToRecyclerView(((FtLogBinding) this.mViewBinding).rv);
        ((FtLogBinding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$1(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        OpenDoorLogItem openDoorLogItem = this.mAdapter.getData().get(i);
        if (openDoorLogItem.getType() == 1) {
            NavUtils.destination(ActOpenDoorLogDetail.class).withString(Constants.INTERCOM_TIME, openDoorLogItem.getItem().getCaptureTime()).navigation(ActivityUtils.getTopActivity());
        }
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        float f = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        canvas.drawRoundRect(rectF, f, f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return createBitmap;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void onEmptyTry() {
        super.onEmptyTry();
        ((FtLogVM) this.mViewModel).getOpenDoorData();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        super.startObserve();
        ((FtLogVM) this.mViewModel).openDoorRefreshData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.FtOpenDoorLog$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtOpenDoorLog.this.lambda$startObserve$2((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(List list) {
        if (list.size() > 0) {
            this.mAdapter.replaceData(list);
        } else {
            showEmpty();
        }
    }

    @Override // com.ltech.smarthome.base.VMFragment, com.ltech.smarthome.base.BaseNormalFragment
    public void refreshData() {
        ((FtLogVM) this.mViewModel).getOpenDoorData();
    }
}