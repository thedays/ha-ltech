package com.ltech.smarthome.ui.automation;

import android.content.Intent;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.BaseSingleSelectActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.PlaceUser;
import com.ltech.smarthome.ui.automation.ISelectCondition;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public class ActSelectHomeMember extends BaseSingleSelectActivity<PlaceUser> implements ISelectCondition {
    private int initPosition;
    public Listing<Place> placeListing;
    public MutableLiveData<Place> place = new MutableLiveData<>();
    public ObservableList<PlaceUser> placeUserObservableList = new ObservableArrayList();

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select_home_member;
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity
    protected void save() {
    }

    @Override // com.ltech.smarthome.ui.automation.ISelectCondition
    public /* synthetic */ void setSelectResult(BaseNormalActivity baseNormalActivity) {
        ISelectCondition.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.ui.automation.ISelectCondition
    public /* synthetic */ void setSelectStatusResult(BaseNormalActivity baseNormalActivity) {
        ISelectCondition.CC.$default$setSelectStatusResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.app_str_select_home_member));
        setEditString(getString(R.string.next));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        Listing<Place> place = Injection.repo().home().getPlace(this, getIntent().getLongExtra(Constants.PLACE_ID, 0L));
        this.placeListing = place;
        place.data().observe(this, new Observer() { // from class: com.ltech.smarthome.ui.automation.ActSelectHomeMember$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSelectHomeMember.this.lambda$startObserve$1((Resource) obj);
            }
        });
        handleData(Transformations.switchMap(this.place, new Function1() { // from class: com.ltech.smarthome.ui.automation.ActSelectHomeMember$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$startObserve$2;
                lambda$startObserve$2 = ActSelectHomeMember.this.lambda$startObserve$2((Place) obj);
                return lambda$startObserve$2;
            }
        }), new IAction() { // from class: com.ltech.smarthome.ui.automation.ActSelectHomeMember$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectHomeMember.this.lambda$startObserve$4((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Resource resource) {
        handleResource(resource, new IAction() { // from class: com.ltech.smarthome.ui.automation.ActSelectHomeMember$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectHomeMember.this.lambda$startObserve$0((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(List list) {
        if (list.isEmpty()) {
            showEmpty();
        } else {
            this.place.setValue((Place) list.get(0));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$startObserve$2(Place place) {
        return Injection.repo().home().getPlaceUserList(this, place.getPlaceId(), -1L).data();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(List list) {
        this.placeUserObservableList.clear();
        Collections.sort(list, new Comparator() { // from class: com.ltech.smarthome.ui.automation.ActSelectHomeMember$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ActSelectHomeMember.lambda$startObserve$3((PlaceUser) obj, (PlaceUser) obj2);
            }
        });
        this.placeUserObservableList.addAll(list);
        setDataList(this.placeUserObservableList);
    }

    static /* synthetic */ int lambda$startObserve$3(PlaceUser placeUser, PlaceUser placeUser2) {
        return placeUser.getRoleType() > placeUser2.getRoleType() ? 1 : -1;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        showContent();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected void setUpData() {
        super.setUpData();
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected void setDataList(List<PlaceUser> list) {
        super.setDataList(list);
    }

    @Override // com.ltech.smarthome.base.BaseSingleSelectActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (this.selectPosition == -1) {
            SmartToast.showShort(R.string.please_choose);
        } else {
            goMap();
        }
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<PlaceUser> getList() {
        return this.placeUserObservableList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, PlaceUser item) {
        helper.setGone(R.id.iv_go, helper.getAdapterPosition() == this.selectPosition);
        helper.setText(R.id.appCompatTextView6, item.getUserName());
        helper.setText(R.id.appCompatTextView7, PlaceUser.getRoleString(this, item.getRoleType()));
        Glide.with((FragmentActivity) this).load(item.getHeadUrl()).error(R.mipmap.ic_my_photo_default).placeholder(R.mipmap.ic_my_photo_default).into((CircleImageView) helper.getView(R.id.iv_icon));
    }

    private void goMap() {
        NavUtils.destination(ActMap.class).withBoolean(Constants.LEAVE_SOMEWHERE, getIntent().getBooleanExtra(Constants.LEAVE_SOMEWHERE, false)).withLong(Constants.USER_ID, this.placeUserObservableList.get(this.selectPosition).getUserId()).withDefaultRequestCode().navigation(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3003) {
            setSelectResult(this);
        }
    }
}