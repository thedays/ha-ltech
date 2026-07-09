package com.ltech.smarthome.ui.device.trig;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActTrigCurtainOpenDirSetBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.TrigExtParam;
import com.ltech.smarthome.ui.device.trig.TrigRepository;
import com.ltech.smarthome.utils.Constants;
import java.util.List;

/* loaded from: classes4.dex */
public class ActTrigCurtainOpenDirSetting extends VMActivity<ActTrigCurtainOpenDirSetBinding, ActTrigCurtainOpenDirSettingVM> {
    Device device;
    private Group group;
    private boolean isGroupControl;
    BaseQuickAdapter<TrigRepository.TrigItem, BaseViewHolder> keyAdapter;
    List<TrigRepository.TrigItem> keyItemList;
    private int subType;
    protected int selectPosition = -1;
    private int lastSelectPosition = -1;
    int curtainDir = 0;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_trig_curtain_open_dir_set;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.app_str_curtain_open_dir));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        this.keyItemList = TrigRepository.getDefaultCurtainOpenDirItemList();
        int intExtra = getIntent().getIntExtra(Constants.SUB_TYPE, 0);
        this.subType = intExtra;
        if (intExtra == 3) {
            this.keyItemList.remove(2);
        }
        BaseQuickAdapter<TrigRepository.TrigItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<TrigRepository.TrigItem, BaseViewHolder>(R.layout.item_curtain_open_dir_select, this.keyItemList) { // from class: com.ltech.smarthome.ui.device.trig.ActTrigCurtainOpenDirSetting.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, TrigRepository.TrigItem item) {
                helper.setImageResource(R.id.iv_icon, item.bgRes).setText(R.id.tv_main, item.name).setImageResource(R.id.iv_select, ActTrigCurtainOpenDirSetting.this.selectPosition == helper.getAdapterPosition() ? R.mipmap.trig_sel_red : 0);
            }
        };
        this.keyAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.trig.ActTrigCurtainOpenDirSetting$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActTrigCurtainOpenDirSetting.this.lambda$initView$0(baseQuickAdapter2, view, i);
            }
        });
        this.keyAdapter.bindToRecyclerView(((ActTrigCurtainOpenDirSetBinding) this.mViewBinding).rvContent);
        ((ActTrigCurtainOpenDirSetBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this, 1, false));
        ((ActTrigCurtainOpenDirSetBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int i2 = this.selectPosition;
        if (i2 != i) {
            this.lastSelectPosition = i2;
            this.selectPosition = i;
            if (i != -1) {
                baseQuickAdapter.notifyItemChanged(i);
            }
            int i3 = this.lastSelectPosition;
            if (i3 != -1) {
                baseQuickAdapter.notifyItemChanged(i3);
            }
        }
        onItemClick(i);
    }

    private void onItemClick(int position) {
        this.curtainDir = this.keyItemList.get(position).spanCount;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        String extParam;
        super.startObserve();
        ((ActTrigCurtainOpenDirSettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActTrigCurtainOpenDirSettingVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActTrigCurtainOpenDirSettingVM) this.mViewModel).deviceId = getIntent().getLongExtra("device_id", -1L);
        ((ActTrigCurtainOpenDirSettingVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ((ActTrigCurtainOpenDirSettingVM) this.mViewModel).samePlace = getIntent().getBooleanExtra(Constants.SAME_PLACE, true);
        boolean booleanExtra = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        this.isGroupControl = booleanExtra;
        if (booleanExtra) {
            Group groupById = Injection.repo().group().getGroupById(((ActTrigCurtainOpenDirSettingVM) this.mViewModel).controlId);
            this.group = groupById;
            extParam = groupById.getExtParam();
        } else {
            Device deviceById = Injection.repo().device().getDeviceById(((ActTrigCurtainOpenDirSettingVM) this.mViewModel).controlId);
            this.device = deviceById;
            extParam = deviceById.getExtParam();
        }
        if (extParam != null) {
            TrigExtParam trigExtParam = new TrigExtParam();
            trigExtParam.fillMapWithString(extParam);
            int curtainType = trigExtParam.getCurtainType();
            if (curtainType == -1 || curtainType == 0) {
                int i = this.selectPosition;
                if (i != 0) {
                    this.lastSelectPosition = i;
                    this.selectPosition = 0;
                    this.keyAdapter.notifyItemChanged(0);
                    int i2 = this.lastSelectPosition;
                    if (i2 != -1) {
                        this.keyAdapter.notifyItemChanged(i2);
                        return;
                    }
                    return;
                }
                return;
            }
            if (curtainType == 1) {
                if (this.selectPosition != trigExtParam.getCurtainType()) {
                    this.lastSelectPosition = this.selectPosition;
                    this.selectPosition = 2;
                    this.keyAdapter.notifyItemChanged(2);
                    int i3 = this.lastSelectPosition;
                    if (i3 != -1) {
                        this.keyAdapter.notifyItemChanged(i3);
                        return;
                    }
                    return;
                }
                return;
            }
            if (curtainType == 2 && this.selectPosition != trigExtParam.getCurtainType()) {
                this.lastSelectPosition = this.selectPosition;
                this.selectPosition = 1;
                this.keyAdapter.notifyItemChanged(1);
                int i4 = this.lastSelectPosition;
                if (i4 != -1) {
                    this.keyAdapter.notifyItemChanged(i4);
                    return;
                }
                return;
            }
            return;
        }
        int i5 = this.selectPosition;
        if (i5 != 0) {
            this.lastSelectPosition = i5;
            this.selectPosition = 0;
            this.keyAdapter.notifyItemChanged(0);
            int i6 = this.lastSelectPosition;
            if (i6 != -1) {
                this.keyAdapter.notifyItemChanged(i6);
            }
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (this.isGroupControl) {
            ((ActTrigCurtainOpenDirSettingVM) this.mViewModel).updateExtParam(this.group, this.curtainDir);
        } else {
            ((ActTrigCurtainOpenDirSettingVM) this.mViewModel).updateExtParam(this.device, this.curtainDir);
        }
    }
}