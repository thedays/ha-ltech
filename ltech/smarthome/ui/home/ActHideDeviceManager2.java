package com.ltech.smarthome.ui.home;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseMultiSelectDeviceGroupActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelect2Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.utils.RxUtils;
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
import java.util.Arrays;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ActHideDeviceManager2 extends BaseMultiSelectDeviceGroupActivity {
    private TextView cancelTv;

    @Override // com.ltech.smarthome.base.BaseList2Activity
    protected int itemLayout() {
        return R.layout.item_select_device_with_place;
    }

    @Override // com.ltech.smarthome.base.BaseMultiSelectDeviceGroupActivity, com.ltech.smarthome.base.BaseMultiSelect2Activity, com.ltech.smarthome.base.BaseList2Activity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setEditString(getString(R.string.app_str_select_all));
        TextView textView = new TextView(this);
        this.cancelTv = textView;
        textView.setTextColor(getResources().getColor(R.color.color_text_red));
        this.cancelTv.setTextSize(14.0f);
        this.cancelTv.setBackgroundColor(getResources().getColor(R.color.white));
        this.cancelTv.setGravity(17);
        this.cancelTv.setText(String.format(getResources().getString(R.string.app_str_unhide_devices), 0));
        ((ActSelect2Binding) this.mViewBinding).footerView.addView(this.cancelTv, new RelativeLayout.LayoutParams(-1, ConvertUtils.dp2px(60.0f)));
        this.cancelTv.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.home.ActHideDeviceManager2.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Role role;
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < ActHideDeviceManager2.this.selectArray.length; i++) {
                    if (ActHideDeviceManager2.this.selectArray[i] && ActHideDeviceManager2.this.mAdapter.getItemCount() > i && (role = (Role) ActHideDeviceManager2.this.mAdapter.getItem(i)) != null) {
                        arrayList.add(ActHideDeviceManager2.this.updateExt(role, i));
                    }
                }
                if (arrayList.size() > 0) {
                    ActHideDeviceManager2 actHideDeviceManager2 = ActHideDeviceManager2.this;
                    actHideDeviceManager2.showLoadingDialog(actHideDeviceManager2.getString(R.string.saving));
                    ActHideDeviceManager2.this.batchControl(arrayList);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void batchControl(List<Observable<Integer>> request) {
        ((ObservableSubscribeProxy) Observable.concat(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Observer<Integer>() { // from class: com.ltech.smarthome.ui.home.ActHideDeviceManager2.2
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
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < ActHideDeviceManager2.this.mAdapter.getData().size(); i++) {
                    Role role = (Role) ActHideDeviceManager2.this.mAdapter.getItem(i);
                    if (role != null && role.getHideDevice() != 0) {
                        arrayList.add(role);
                    }
                }
                ActHideDeviceManager2.this.setDataList(arrayList);
                ActHideDeviceManager2.this.cancelTv.setText(String.format(ActHideDeviceManager2.this.getResources().getString(R.string.app_str_unhide_devices), 0));
                ActHideDeviceManager2.this.cancelTv.setText(String.format(ActHideDeviceManager2.this.getResources().getString(R.string.app_str_unhide_devices), 0));
                ActHideDeviceManager2.this.dismissLoadingDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseList2Activity
    public void convertView(BaseViewHolder helper, Role item) {
        helper.setText(R.id.tv_device_name, item.getName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item)).setText(R.id.tv_place_info, getPlaceInfo(item.getFloorId(), item.getRoomId())).setBackgroundRes(R.id.iv_select, this.selectArray[helper.getAdapterPosition()] ? R.mipmap.ic_tick_sel : R.color.transparent);
        ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseMultiSelectDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        handleData(this.roleList, new IAction<List<Role>>() { // from class: com.ltech.smarthome.ui.home.ActHideDeviceManager2.3
            @Override // com.ltech.smarthome.base.IAction
            public void act(List<Role> roles) {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < roles.size(); i++) {
                    Role role = roles.get(i);
                    if (role.getHideDevice() != 0) {
                        arrayList.add(role);
                    }
                }
                ActHideDeviceManager2.this.setDataList(arrayList);
                ActHideDeviceManager2.this.cancelTv.setText(String.format(ActHideDeviceManager2.this.getResources().getString(R.string.app_str_unhide_devices), 0));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        super.showEmpty();
        this.cancelTv.setText(String.format(getResources().getString(R.string.app_str_unhide_devices), 0));
    }

    @Override // com.ltech.smarthome.base.BaseMultiSelectDeviceGroupActivity, com.ltech.smarthome.base.BaseMultiSelect2Activity
    protected void save() {
        selectAll();
    }

    @Override // com.ltech.smarthome.base.BaseMultiSelect2Activity
    protected void onItemClick(int position) {
        super.onItemClick(position);
        boolean z = true;
        int i = 0;
        for (int i2 = 0; i2 < this.selectArray.length; i2++) {
            if (this.selectArray[i2]) {
                i++;
            } else {
                z = false;
            }
        }
        if (z) {
            setEditString(getString(R.string.app_str_cancel_select_all));
        } else {
            setEditString(getString(R.string.app_str_select_all));
        }
        this.cancelTv.setText(String.format(getResources().getString(R.string.app_str_unhide_devices), Integer.valueOf(i)));
    }

    private void selectAll() {
        if (getTitleBar().getEditString().equals(getResources().getString(R.string.app_str_select_all))) {
            Arrays.fill(this.selectArray, true);
            setEditString(getString(R.string.app_str_cancel_select_all));
            this.cancelTv.setText(String.format(getResources().getString(R.string.app_str_unhide_devices), Integer.valueOf(this.selectArray.length)));
        } else {
            Arrays.fill(this.selectArray, false);
            setEditString(getString(R.string.app_str_select_all));
            this.cancelTv.setText(String.format(getResources().getString(R.string.app_str_unhide_devices), 0));
        }
        this.mAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Observable<Integer> updateExt(final Role role, final int position) {
        return Observable.create(new ObservableOnSubscribe<Integer>(this) { // from class: com.ltech.smarthome.ui.home.ActHideDeviceManager2.4
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(final ObservableEmitter<Integer> emitter) throws Exception {
                Role role2 = role;
                if (role2 instanceof Device) {
                    final Device device = (Device) role2;
                    try {
                        JSONObject jSONObject = device.getExtParam() != null ? new JSONObject(device.getExtParam()) : new JSONObject();
                        jSONObject.put("hideDevice", 0);
                        device.setExtParam(jSONObject.toString());
                        Injection.net().updateParamExt(device.getDeviceId(), jSONObject.toString()).compose(RxUtils.io_io()).subscribe(new DisposableObserver<Object>() { // from class: com.ltech.smarthome.ui.home.ActHideDeviceManager2.4.1
                            @Override // io.reactivex.Observer
                            public void onNext(Object o) {
                            }

                            @Override // io.reactivex.Observer
                            public void onError(Throwable e) {
                                emitter.onNext(-1);
                                emitter.onComplete();
                            }

                            @Override // io.reactivex.Observer
                            public void onComplete() {
                                Injection.repo().device().saveDevice(device);
                                emitter.onNext(Integer.valueOf(position));
                                emitter.onComplete();
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
                        if (jSONObject2.has("hideDevice")) {
                            jSONObject2.put("hideDevice", 0);
                        }
                        group.setExtParam(jSONObject2.toString());
                        Injection.net().updateGroupParamExt(group.getGroupId(), jSONObject2.toString()).compose(RxUtils.io_io()).subscribe(new DisposableObserver<Object>() { // from class: com.ltech.smarthome.ui.home.ActHideDeviceManager2.4.2
                            @Override // io.reactivex.Observer
                            public void onNext(Object o) {
                            }

                            @Override // io.reactivex.Observer
                            public void onError(Throwable e2) {
                                emitter.onNext(-1);
                                emitter.onComplete();
                            }

                            @Override // io.reactivex.Observer
                            public void onComplete() {
                                Injection.repo().group().saveGroup(group);
                                emitter.onNext(Integer.valueOf(position));
                                emitter.onComplete();
                            }
                        });
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        });
    }
}