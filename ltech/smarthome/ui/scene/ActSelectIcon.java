package com.ltech.smarthome.ui.scene;

import android.content.Intent;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.GridLayoutManager;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.common.primitives.Ints;
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
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSelectIcon extends BaseSingleSelectActivity<Integer> {
    private int initPosition;
    private boolean isScene;
    private long sceneId;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected boolean isNeedAddMarginTopEqualStatusBar() {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select_icon;
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_icon));
        setBackString(getString(R.string.cancel));
        setEditString(getString(R.string.save));
        ((ActSelectBinding) this.mViewBinding).title.tvBack.setTextColor(getResources().getColor(R.color.gray));
        ((ActSelectBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(this, 4));
        ((ActSelectBinding) this.mViewBinding).layoutRoot.setBackground(ContextCompat.getDrawable(this, R.color.white));
        showAsDialog(0.4f);
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected void setUpData() {
        int intExtra = getIntent().getIntExtra(Constants.ICON_POSITION, -1);
        this.initPosition = intExtra;
        this.selectPosition = intExtra;
        this.sceneId = getIntent().getLongExtra(Constants.SCENE_ID, 0L);
        this.isScene = getIntent().getBooleanExtra(Constants.IS_SCENE, true);
        super.setUpData();
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<Integer> getList() {
        return Ints.asList(IconRepository.getSceneIcons(this));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, Integer item) {
        helper.setGone(R.id.iv_select, true);
        ImageView imageView = (ImageView) helper.getView(R.id.iv_icon);
        imageView.setImageResource(item.intValue());
        imageView.setColorFilter(helper.getAdapterPosition() == this.selectPosition ? 0 : ContextCompat.getColor(this, R.color.color_border_gray));
        helper.getView(R.id.layout_item_bg).getLayoutParams().height = ((ActSelectBinding) this.mViewBinding).rvContent.getMeasuredWidth() / 4;
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (this.selectPosition == -1) {
            SmartToast.showShort(R.string.please_choose);
        } else if (this.isScene) {
            ((ObservableSubscribeProxy) Injection.net().updateSceneicon(this.sceneId, this.selectPosition).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.ActSelectIcon$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSelectIcon.this.lambda$edit$0((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.scene.ActSelectIcon$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Action
                public final void run() {
                    ActSelectIcon.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.ActSelectIcon$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSelectIcon.this.lambda$edit$1(obj);
                }
            }, new SmartErrorComsumer());
        } else {
            save();
            finishActivity();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$0(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$1(Object obj) throws Exception {
        Injection.repo().scene().updateSceneIconPosition(this.sceneId, this.selectPosition);
        save();
        finishActivity();
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity
    protected void save() {
        if (this.selectPosition != this.initPosition) {
            Intent intent = new Intent();
            intent.putExtra(Constants.ICON_POSITION, this.selectPosition);
            setResult(3002, intent);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        super.back();
    }
}