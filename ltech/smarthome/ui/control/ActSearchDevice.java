package com.ltech.smarthome.ui.control;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActSearchDeviceBinding;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.nfc.BaseNfcActivity;
import com.ltech.smarthome.ui.control.provider.AsPanelItemProvider;
import com.ltech.smarthome.ui.control.provider.BaseDeviceProvider;
import com.ltech.smarthome.ui.control.provider.BleCurtainGroupItemProvider;
import com.ltech.smarthome.ui.control.provider.BleDeviceItemProvider;
import com.ltech.smarthome.ui.control.provider.BleDryCurtainGroupItemProvider;
import com.ltech.smarthome.ui.control.provider.BleHamItemProvider;
import com.ltech.smarthome.ui.control.provider.CameraItemProvider;
import com.ltech.smarthome.ui.control.provider.CentralAirItemProvider;
import com.ltech.smarthome.ui.control.provider.CentralAirSubDeviceItemProvider;
import com.ltech.smarthome.ui.control.provider.DefaultGroupItemProvider;
import com.ltech.smarthome.ui.control.provider.DefaultItemProvider;
import com.ltech.smarthome.ui.control.provider.DryToBleItemProvider;
import com.ltech.smarthome.ui.control.provider.EurPanelProItemProvider;
import com.ltech.smarthome.ui.control.provider.IrDeviceItemProvider;
import com.ltech.smarthome.ui.control.provider.KbsGroupItemProvider;
import com.ltech.smarthome.ui.control.provider.KbsItemProvider;
import com.ltech.smarthome.ui.control.provider.KeySwitchItemProvider;
import com.ltech.smarthome.ui.control.provider.KnobPanelBatteryItemProvider;
import com.ltech.smarthome.ui.control.provider.KnobPanelItemProvider;
import com.ltech.smarthome.ui.control.provider.KnobPanelProItemProvider;
import com.ltech.smarthome.ui.control.provider.LightGroupItemProvider;
import com.ltech.smarthome.ui.control.provider.LightItemProvider;
import com.ltech.smarthome.ui.control.provider.MeshGatewayItemProvider;
import com.ltech.smarthome.ui.control.provider.MusicPlayerItemProvider;
import com.ltech.smarthome.ui.control.provider.Rs8ItemProvider;
import com.ltech.smarthome.ui.control.provider.Scene8ItemProvider;
import com.ltech.smarthome.ui.control.provider.SceneItemProvider;
import com.ltech.smarthome.ui.control.provider.SensorGroupItemProvider;
import com.ltech.smarthome.ui.control.provider.SensorItemProvider;
import com.ltech.smarthome.ui.control.provider.SmartPanelGroupItemProvider;
import com.ltech.smarthome.ui.control.provider.SmartPanelItemProvider;
import com.ltech.smarthome.ui.control.provider.SuperPanelItemProvider;
import com.ltech.smarthome.utils.Constants;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSearchDevice extends BaseNfcActivity<ActSearchDeviceBinding, FtRoomVM> implements TextWatcher, BaseDeviceProvider.OnDataChangeListener {
    private MultipleItemRvAdapter<Role, BaseViewHolder> deviceAdapter;

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.OnDataChangeListener
    public void onDataChange(Group sub) {
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.OnDataChangeListener
    public void onDataDelete(Role role) {
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.OnDataChangeListener
    public void onDataOfflineChange(int position) {
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.OnDataChangeListener
    public void onDeviceHide(Role role) {
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.OnDataChangeListener
    public void onGroupOfflineChange(int position) {
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_search_device;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((ActSearchDeviceBinding) this.mViewBinding).searchBar.cancelBtn.setVisibility(0);
        ((ActSearchDeviceBinding) this.mViewBinding).searchBar.cancelBtn.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.ActSearchDevice.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ActSearchDevice.this.finishActivity();
            }
        });
        ((ActSearchDeviceBinding) this.mViewBinding).searchBar.ivClean.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.ActSearchDevice.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).searchBar.searchEdtTxt.setText("");
            }
        });
        MultipleItemRvAdapter<Role, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<Role, BaseViewHolder>(new ArrayList()) { // from class: com.ltech.smarthome.ui.control.ActSearchDevice.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(Role role) {
                return ((FtRoomVM) ActSearchDevice.this.mViewModel).getViewType(role);
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new AsPanelItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new MeshGatewayItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new LightItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new IrDeviceItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new BleDeviceItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new SceneItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new Scene8ItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new SuperPanelItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new KeySwitchItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new CentralAirItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new MusicPlayerItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new SmartPanelItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new SensorItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new CentralAirSubDeviceItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new SmartPanelGroupItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new LightGroupItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new BleCurtainGroupItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new BleDryCurtainGroupItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new DryToBleItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new BleHamItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new KnobPanelItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new SensorGroupItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new CameraItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new KnobPanelBatteryItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new KnobPanelProItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new DefaultItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new EurPanelProItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new DefaultGroupItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new KbsItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new KbsGroupItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
                this.mProviderDelegate.registerProvider(new Rs8ItemProvider(ActSearchDevice.this.activity, ((ActSearchDeviceBinding) ActSearchDevice.this.mViewBinding).rvDevice, ActSearchDevice.this.mViewModel, ActSearchDevice.this));
            }
        };
        this.deviceAdapter = multipleItemRvAdapter;
        multipleItemRvAdapter.finishInitialize();
        this.deviceAdapter.bindToRecyclerView(((ActSearchDeviceBinding) this.mViewBinding).rvDevice);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2) { // from class: com.ltech.smarthome.ui.control.ActSearchDevice.4
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean canScrollVertically() {
                return ((FtRoomVM) ActSearchDevice.this.mViewModel).canScroll.getValue().booleanValue();
            }
        };
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.ltech.smarthome.ui.control.ActSearchDevice.5
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int position) {
                if (ActSearchDevice.this.deviceAdapter.getItemViewType(position) == 21) {
                    return gridLayoutManager.getSpanCount();
                }
                return 1;
            }
        });
        ((ActSearchDeviceBinding) this.mViewBinding).rvDevice.setLayoutManager(gridLayoutManager);
        ((ActSearchDeviceBinding) this.mViewBinding).rvDevice.setHasFixedSize(true);
        ((ActSearchDeviceBinding) this.mViewBinding).searchBar.searchEdtTxt.addTextChangedListener(this);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((FtRoomVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((FtRoomVM) this.mViewModel).roleResult = new MutableLiveData<>();
        ((FtRoomVM) this.mViewModel).roleResult.observe(this, new Observer<Resource<List<Role>>>() { // from class: com.ltech.smarthome.ui.control.ActSearchDevice.6
            @Override // androidx.lifecycle.Observer
            public void onChanged(Resource<List<Role>> listResource) {
                ActSearchDevice.this.deviceAdapter.replaceData(listResource.getData());
            }
        });
    }

    @Override // com.ltech.smarthome.nfc.BaseNfcActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((FtRoomVM) this.mViewModel).search(((ActSearchDeviceBinding) this.mViewBinding).searchBar.searchEdtTxt.getText().toString());
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
        ((FtRoomVM) this.mViewModel).search(editable.toString());
    }
}