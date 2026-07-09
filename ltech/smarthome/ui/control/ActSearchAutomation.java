package com.ltech.smarthome.ui.control;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSearchAutomationBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Automation;
import com.ltech.smarthome.ui.automation.ActAddAutomation;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.view.SwitchButton;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSearchAutomation extends VMActivity<ActSearchAutomationBinding, FtAutomationVM> implements TextWatcher {
    private BaseQuickAdapter<Automation, BaseViewHolder> mAdapter;

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_search_automation;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        initListView();
        ((ActSearchAutomationBinding) this.mViewBinding).searchBar.searchEdtTxt.setHint(R.string.app_str_search_automation_name);
        ((ActSearchAutomationBinding) this.mViewBinding).searchBar.searchEdtTxt.addTextChangedListener(this);
        ((ActSearchAutomationBinding) this.mViewBinding).searchBar.cancelBtn.setVisibility(0);
        ((ActSearchAutomationBinding) this.mViewBinding).searchBar.cancelBtn.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.ActSearchAutomation.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ActSearchAutomation.this.finishActivity();
            }
        });
        ((ActSearchAutomationBinding) this.mViewBinding).searchBar.ivClean.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.ActSearchAutomation.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((ActSearchAutomationBinding) ActSearchAutomation.this.mViewBinding).searchBar.searchEdtTxt.setText("");
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((FtAutomationVM) this.mViewModel).place = getIntent().getLongExtra(Constants.PLACE_ID, 0L);
        ((FtAutomationVM) this.mViewModel).refreshData.observe(this, new Observer<List<Automation>>() { // from class: com.ltech.smarthome.ui.control.ActSearchAutomation.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<Automation> scenes) {
                ActSearchAutomation.this.mAdapter.replaceData(scenes);
            }
        });
    }

    private void initListView() {
        ((ActSearchAutomationBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView = ((ActSearchAutomationBinding) this.mViewBinding).rvContent;
        BaseQuickAdapter<Automation, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Automation, BaseViewHolder>(R.layout.item_automation) { // from class: com.ltech.smarthome.ui.control.ActSearchAutomation.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(final BaseViewHolder helper, final Automation item) {
                helper.setText(R.id.tv_mode_name, item.getName()).setBackgroundRes(R.id.layout_item_bg, SceneHelper.getAutomationPic(ActivityUtils.getTopActivity(), item.getPicIndex())).setText(R.id.tv_mode_type, ActSearchAutomation.this.getString(item.getAutomationType() == 2 ? R.string.type_local : R.string.type_cloud)).setBackgroundRes(R.id.tv_mode_type, item.getAutomationType() == 2 ? R.drawable.shape_red_bt_5 : R.drawable.shape_blue_bt_5).setChecked(R.id.sb, item.isEnable());
                ((SwitchButton) helper.getView(R.id.sb)).setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.control.ActSearchAutomation.4.1
                    @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                        ((FtAutomationVM) ActSearchAutomation.this.mViewModel).setAutomationEnable(helper.getLayoutPosition(), item, isChecked);
                    }
                });
            }
        };
        this.mAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.control.ActSearchAutomation.5
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Automation automation = (Automation) ActSearchAutomation.this.mAdapter.getItem(position);
                if (!((FtAutomationVM) ActSearchAutomation.this.mViewModel).getCurrentPlace().isOwner() && !((FtAutomationVM) ActSearchAutomation.this.mViewModel).getCurrentPlace().isManager()) {
                    ActSearchAutomation.this.showNoPermissionDialog();
                } else {
                    Injection.limiter().reset(Injection.keyCreator().automationKey(automation.getAutomationId()));
                    NavUtils.destination(ActAddAutomation.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withInt(Constants.ICON_POSITION, automation.getPicIndex()).withString(Constants.AUTOMATION_NAME, automation.getName()).withLong(Constants.AUTOMATION_ID, automation.getAutomationId()).navigation(ActSearchAutomation.this.activity);
                }
            }
        });
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
        ((FtAutomationVM) this.mViewModel).search(editable.toString());
    }
}