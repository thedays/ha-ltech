package com.ltech.smarthome.ui.device.light;

import android.graphics.Rect;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseSingleSelectActivity;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.IconRepository;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSelectLightIcon extends BaseSingleSelectActivity<IconContent> {
    private long deviceId;
    private int initPosition;

    public static class IconContent {
        public int iconRes;
        public String name;
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select_icon;
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_icon));
        setBackImage(R.mipmap.icon_back);
        ((ActSelectBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(this, 3));
        ((ActSelectBinding) this.mViewBinding).rvContent.setPadding(ConvertUtils.dp2px(8.0f), ConvertUtils.dp2px(16.0f), ConvertUtils.dp2px(8.0f), ConvertUtils.dp2px(16.0f));
        ((ActSelectBinding) this.mViewBinding).rvContent.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.light.ActSelectLightIcon.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, ConvertUtils.dp2px(8.0f), 0, ConvertUtils.dp2px(8.0f));
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected void setUpData() {
        this.initPosition = getIntent().getIntExtra(Constants.ICON_POSITION, -1);
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
        this.selectPosition = this.initPosition;
        super.setUpData();
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<IconContent> getList() {
        int[] lightIconSelect = IconRepository.getLightIconSelect(this);
        ArrayList arrayList = new ArrayList();
        for (int i : lightIconSelect) {
            IconContent iconContent = new IconContent();
            iconContent.iconRes = i;
            arrayList.add(iconContent);
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, IconContent item) {
        helper.setImageResource(R.id.iv_icon, item.iconRes).setImageResource(R.id.iv_select, R.mipmap.ic_tick_sel).setVisible(R.id.iv_select, helper.getAdapterPosition() == this.selectPosition);
        helper.getView(R.id.layout_item_bg).getLayoutParams().height = ((ActSelectBinding) this.mViewBinding).rvContent.getMeasuredWidth() / 3;
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity
    protected void save() {
        ((ObservableSubscribeProxy) Injection.net().updateDeviceImgIndex(this.deviceId, this.selectPosition).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.light.ActSelectLightIcon$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectLightIcon.this.lambda$save$0((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.light.ActSelectLightIcon$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActSelectLightIcon.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.light.ActSelectLightIcon$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectLightIcon.this.lambda$save$1(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$0(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$1(Object obj) throws Exception {
        SmartToast.showShort(getString(R.string.save_success));
        Injection.repo().device().changeDeviceIcon(this.deviceId, this.selectPosition);
        super.back();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        if (this.selectPosition != this.initPosition) {
            save();
        } else {
            super.back();
        }
    }
}