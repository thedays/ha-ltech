package com.ltech.smarthome.ui.home;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ConvertUtils;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ActHideDeviceManagerNew extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> implements ISelectAction {
    private boolean alreadyHide;
    private TextView okTv;

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$edit$1(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((ActSelect3Binding) this.mViewBinding).setTitleGone(true);
        ((ActSelect3Binding) this.mViewBinding).layoutTab.setVisibility(0);
        ((ActSelect3Binding) this.mViewBinding).layoutSort.setVisibility(8);
        ((ActSelect3Binding) this.mViewBinding).layoutSortAndType.setVisibility(0);
        ((ActSelect3Binding) this.mViewBinding).ivBack.setImageResource(R.mipmap.icon_back);
        ((ActSelect3Binding) this.mViewBinding).tvEdit.setText(getString(R.string.app_str_select_all));
        ((FtDeviceGroupVM) this.mViewModel).needFloorRoomMemory = false;
        TabLayout.Tab text = ((ActSelect3Binding) this.mViewBinding).tabTitle.newTab().setText(R.string.app_str_not_hidden);
        ViewHelpUtil.createTabCustomView(this, text, getString(R.string.app_str_not_hidden), true);
        TabLayout.Tab text2 = ((ActSelect3Binding) this.mViewBinding).tabTitle.newTab().setText(R.string.app_str_already_hidden);
        ViewHelpUtil.createTabCustomView(this, text2, getString(R.string.app_str_already_hidden), false);
        ((ActSelect3Binding) this.mViewBinding).tabTitle.addTab(text);
        ((ActSelect3Binding) this.mViewBinding).tabTitle.addTab(text2);
        ((ActSelect3Binding) this.mViewBinding).tabTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.ui.home.ActHideDeviceManagerNew.1
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                ViewHelpUtil.createTabCustomView(ActHideDeviceManagerNew.this, tab, tab.getText().toString(), true);
                if (tab.getPosition() == 0) {
                    ActHideDeviceManagerNew.this.alreadyHide = false;
                } else {
                    ActHideDeviceManagerNew.this.alreadyHide = true;
                }
                if (ActHideDeviceManagerNew.this.selectFt != null) {
                    ActHideDeviceManagerNew.this.selectFt.getData();
                }
                ActHideDeviceManagerNew.this.selectRoleIds.clear();
                ActHideDeviceManagerNew.this.selectCountLiveData.setValue(0);
                if (ActHideDeviceManagerNew.this.alreadyHide) {
                    ActHideDeviceManagerNew.this.okTv.setText(String.format(ActHideDeviceManagerNew.this.getResources().getString(R.string.app_str_select_display_device), Integer.valueOf(ActHideDeviceManagerNew.this.selectRoleIds.size())));
                } else {
                    ActHideDeviceManagerNew.this.okTv.setText(String.format(ActHideDeviceManagerNew.this.getResources().getString(R.string.app_str_select_hide_device), Integer.valueOf(ActHideDeviceManagerNew.this.selectRoleIds.size())));
                }
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
                ViewHelpUtil.createTabCustomView(ActHideDeviceManagerNew.this, tab, tab.getText().toString(), false);
            }
        });
        TextView textView = new TextView(this);
        this.okTv = textView;
        textView.setTextColor(getResources().getColor(R.color.color_text_red));
        this.okTv.setTextSize(14.0f);
        this.okTv.setBackgroundColor(getResources().getColor(R.color.white));
        this.okTv.setGravity(17);
        this.okTv.setText(String.format(getResources().getString(R.string.app_str_select_hide_device), 0));
        ((ActSelect3Binding) this.mViewBinding).footerView.addView(this.okTv, new RelativeLayout.LayoutParams(-1, ConvertUtils.dp2px(60.0f)));
        this.okTv.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.home.ActHideDeviceManagerNew.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (ActHideDeviceManagerNew.this.selectRoleIds.size() == 0) {
                    SmartToast.showShort(R.string.app_str_select_no_device_hint);
                    return;
                }
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < ActHideDeviceManagerNew.this.allRoleData.size(); i++) {
                    if (ActHideDeviceManagerNew.this.selectRoleIds.contains(Long.valueOf(ActHideDeviceManagerNew.this.allRoleData.get(i).getObjectId()))) {
                        ActHideDeviceManagerNew actHideDeviceManagerNew = ActHideDeviceManagerNew.this;
                        arrayList.add(actHideDeviceManagerNew.updateExt(actHideDeviceManagerNew.allRoleData.get(i), i, ActHideDeviceManagerNew.this.alreadyHide));
                    }
                }
                if (arrayList.size() > 0) {
                    ActHideDeviceManagerNew actHideDeviceManagerNew2 = ActHideDeviceManagerNew.this;
                    actHideDeviceManagerNew2.showLoadingDialog(actHideDeviceManagerNew2.getString(R.string.saving));
                    ActHideDeviceManagerNew.this.batchControl(arrayList);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Observable<Integer> updateExt(final Role role, final int position, final boolean isShow) {
        return Observable.create(new ObservableOnSubscribe<Integer>(this) { // from class: com.ltech.smarthome.ui.home.ActHideDeviceManagerNew.3
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(final ObservableEmitter<Integer> observableEmitter) throws Exception {
                Role role2 = role;
                if (role2 instanceof Device) {
                    final Device device = (Device) role2;
                    try {
                        JSONObject jSONObject = device.getExtParam() != null ? new JSONObject(device.getExtParam()) : new JSONObject();
                        jSONObject.put("hideDevice", !isShow ? 1 : 0);
                        device.setExtParam(jSONObject.toString());
                        Injection.net().updateParamExt(device.getDeviceId(), jSONObject.toString()).compose(RxUtils.io_io()).subscribe(new DisposableObserver<Object>() { // from class: com.ltech.smarthome.ui.home.ActHideDeviceManagerNew.3.1
                            @Override // io.reactivex.Observer
                            public void onNext(Object o) {
                            }

                            @Override // io.reactivex.Observer
                            public void onError(Throwable e) {
                                observableEmitter.onNext(-1);
                                observableEmitter.onComplete();
                            }

                            @Override // io.reactivex.Observer
                            public void onComplete() {
                                Injection.repo().device().saveDevice(device);
                                observableEmitter.onNext(Integer.valueOf(position));
                                observableEmitter.onComplete();
                            }
                        });
                        return;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return;
                    }
                }
                if (role2 instanceof Group) {
                    final Group group = (Group) role2;
                    try {
                        JSONObject jSONObject2 = group.getExtParam() != null ? new JSONObject(group.getExtParam()) : new JSONObject();
                        jSONObject2.put("hideDevice", !isShow ? 1 : 0);
                        group.setExtParam(jSONObject2.toString());
                        Injection.net().updateGroupParamExt(group.getGroupId(), jSONObject2.toString()).compose(RxUtils.io_io()).subscribe(new DisposableObserver<Object>() { // from class: com.ltech.smarthome.ui.home.ActHideDeviceManagerNew.3.2
                            @Override // io.reactivex.Observer
                            public void onNext(Object o) {
                            }

                            @Override // io.reactivex.Observer
                            public void onError(Throwable e2) {
                                observableEmitter.onNext(-1);
                                observableEmitter.onComplete();
                            }

                            @Override // io.reactivex.Observer
                            public void onComplete() {
                                Injection.repo().group().saveGroup(group);
                                observableEmitter.onNext(Integer.valueOf(position));
                                observableEmitter.onComplete();
                            }
                        });
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void batchControl(List<Observable<Integer>> request) {
        ((ObservableSubscribeProxy) Observable.concat(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Observer<Integer>() { // from class: com.ltech.smarthome.ui.home.ActHideDeviceManagerNew.4
            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onNext(Integer i) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d2) {
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                ActHideDeviceManagerNew.this.selectRoleIds.clear();
                ActHideDeviceManagerNew.this.selectCountLiveData.setValue(Integer.valueOf(ActHideDeviceManagerNew.this.selectRoleIds.size()));
                ActHideDeviceManagerNew.this.selectFt.getData();
                ActHideDeviceManagerNew.this.dismissLoadingDialog();
            }
        });
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterGroup(Group group) {
        return this.alreadyHide ? group.getHideDevice() != 0 : group.getHideDevice() == 0;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        return this.alreadyHide ? device.getHideDevice() != 0 : device.getHideDevice() == 0;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void changeSelectCount(int integer) {
        if (this.selectAll) {
            ((ActSelect3Binding) this.mViewBinding).tvEdit.setText(getString(R.string.app_str_cancel_select_all));
        } else {
            ((ActSelect3Binding) this.mViewBinding).tvEdit.setText(getString(R.string.app_str_select_all));
        }
        if (this.alreadyHide) {
            this.okTv.setText(String.format(getResources().getString(R.string.app_str_select_display_device), Integer.valueOf(integer)));
        } else {
            this.okTv.setText(String.format(getResources().getString(R.string.app_str_select_hide_device), Integer.valueOf(integer)));
        }
    }
}