package com.ltech.smarthome.ui.share;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.BaseSelectGroupActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.placeuser.InvitationPlaceUserRequest;
import com.ltech.smarthome.net.request.role.UpdateGroupRoleRequest;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSelectGroupForPermission extends BaseSelectGroupActivity {
    private boolean[] selectArray;
    private long userId;

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select_with_icon;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, Group item) {
        helper.setText(R.id.tv_name, item.getGroupName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item)).setBackgroundRes(R.id.iv_select, this.selectArray[helper.getAdapterPosition()] ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
    }

    @Override // com.ltech.smarthome.base.BaseSelectGroupActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setEditString(getString(R.string.finish));
        this.userId = getIntent().getLongExtra(Constants.USER_ID, -1L);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.share.ActSelectGroupForPermission$$ExternalSyntheticLambda4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSelectGroupForPermission.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.selectArray[i] = !r3[i];
        baseQuickAdapter.notifyItemChanged(i);
    }

    @Override // com.ltech.smarthome.base.BaseSelectGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        handleData(this.mRequest, new IAction() { // from class: com.ltech.smarthome.ui.share.ActSelectGroupForPermission$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectGroupForPermission.this.lambda$startObserve$1((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Group group = (Group) it.next();
            if ("3".equalsIgnoreCase(group.getModuleType())) {
                arrayList.add(group);
            }
        }
        if (arrayList.isEmpty()) {
            showEmpty();
            return;
        }
        this.selectArray = new boolean[arrayList.size()];
        int i = 0;
        if (PlaceShareHelper.getInstance().enterType == 1002) {
            Arrays.fill(this.selectArray, false);
            while (i < this.selectArray.length) {
                Iterator<Long> it2 = PlaceShareHelper.getInstance().groupIds.iterator();
                while (it2.hasNext()) {
                    if (it2.next().longValue() == ((Group) arrayList.get(i)).getGroupId()) {
                        this.selectArray[i] = true;
                    }
                }
                i++;
            }
        } else if (PlaceShareHelper.getInstance().groupIds == null) {
            Arrays.fill(this.selectArray, true);
        } else {
            Arrays.fill(this.selectArray, false);
            while (i < this.selectArray.length) {
                Iterator<Long> it3 = PlaceShareHelper.getInstance().groupIds.iterator();
                while (it3.hasNext()) {
                    if (it3.next().longValue() == ((Group) arrayList.get(i)).getGroupId()) {
                        this.selectArray[i] = true;
                    }
                }
                i++;
            }
        }
        this.mAdapter.setNewData(arrayList);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyImageRes(R.mipmap.pic_empty_1).emptyStringRes(R.string.no_group));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        PlaceShareHelper.getInstance().groupIds = getSelectGroup();
        if (PlaceShareHelper.getInstance().enterType == 1002) {
            updateGroupPermission();
        } else {
            finishActivity();
        }
    }

    private void updateGroupPermission() {
        ((ObservableSubscribeProxy) Injection.net().updateGroupRole(generateRequest()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.share.ActSelectGroupForPermission$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectGroupForPermission.this.lambda$updateGroupPermission$2((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.share.ActSelectGroupForPermission$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActSelectGroupForPermission.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.share.ActSelectGroupForPermission$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectGroupForPermission.this.lambda$updateGroupPermission$3(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateGroupPermission$2(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.adding));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateGroupPermission$3(Object obj) throws Exception {
        LHomeLog.i(getClass(), "updateGroupRole: response enter");
        finishActivity();
    }

    private UpdateGroupRoleRequest generateRequest() {
        ArrayList arrayList = new ArrayList();
        Iterator<Long> it = PlaceShareHelper.getInstance().groupIds.iterator();
        while (it.hasNext()) {
            arrayList.add(new InvitationPlaceUserRequest.GroupId(it.next().longValue()));
        }
        return new UpdateGroupRoleRequest(this.placeId, this.userId, arrayList);
    }

    private List<Long> getSelectGroup() {
        ArrayList arrayList = new ArrayList();
        if (this.selectArray != null) {
            int i = 0;
            while (true) {
                boolean[] zArr = this.selectArray;
                if (i >= zArr.length) {
                    break;
                }
                if (zArr[i]) {
                    arrayList.add(Long.valueOf(((Group) this.mAdapter.getData().get(i)).getGroupId()));
                }
                i++;
            }
        }
        return arrayList;
    }
}