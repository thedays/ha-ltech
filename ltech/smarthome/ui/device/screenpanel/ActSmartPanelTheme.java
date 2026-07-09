package com.ltech.smarthome.ui.device.screenpanel;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSmartPanelThemeBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelThemeVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;

/* loaded from: classes4.dex */
public class ActSmartPanelTheme extends VMActivity<ActSmartPanelThemeBinding, ActSmartPanelThemeVM> {
    private BaseQuickAdapter<ActSmartPanelThemeVM.Item, BaseViewHolder> mAdapter;
    private int mSelectTheme = -1;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_smart_panel_theme;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.app_str_custom_setting));
        setBackImage(R.mipmap.icon_back);
        ((ActSmartPanelThemeVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        ((ActSmartPanelThemeVM) this.mViewModel).themeNum = getIntent().getIntExtra(Constants.SELECT_THEME, 0);
        ((ActSmartPanelThemeVM) this.mViewModel).screensaverTime.setValue(Integer.valueOf(getIntent().getIntExtra(Constants.SELECT_SCREENSAVER_TIME, 0)));
        ((ActSmartPanelThemeVM) this.mViewModel).screensaverMode = getIntent().getIntExtra(Constants.SELECT_SCREENSAVER_MODE, 1);
        ((ActSmartPanelThemeVM) this.mViewModel).downloadThemes = getIntent().getIntegerArrayListExtra(Constants.SELECT_DOWNLOAD_SCREENSAVER);
        ((ActSmartPanelThemeVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActSmartPanelThemeVM) this.mViewModel).applyThemes = getIntent().getIntArrayExtra(Constants.SELECT_APPLY_SCREENSAVER);
        if (((ActSmartPanelThemeVM) this.mViewModel).groupControl) {
            ((ActSmartPanelThemeVM) this.mViewModel).controlObject.setValue(Injection.repo().group().getGroupById(((ActSmartPanelThemeVM) this.mViewModel).controlId));
            if (((ActSmartPanelThemeVM) this.mViewModel).applyThemes == null) {
                ((ActSmartPanelThemeVM) this.mViewModel).applyThemes = new int[]{1, 2, 3};
            }
        } else {
            ((ActSmartPanelThemeVM) this.mViewModel).controlObject.setValue(Injection.repo().device().getDeviceById(((ActSmartPanelThemeVM) this.mViewModel).controlId));
        }
        ((ActSmartPanelThemeVM) this.mViewModel).curScreen = getIntent().getIntExtra(Constants.SELECT_POSITION, 0);
        ((ActSmartPanelThemeVM) this.mViewModel).initData();
        initList();
        ((ActSmartPanelThemeBinding) this.mViewBinding).sb.setCheckedNotByUser(((ActSmartPanelThemeVM) this.mViewModel).screensaverMode == 2);
        ((ActSmartPanelThemeBinding) this.mViewBinding).sb.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelTheme.1
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                ((ActSmartPanelThemeVM) ActSmartPanelTheme.this.mViewModel).screenClose(isChecked);
            }
        });
        ((ActSmartPanelThemeBinding) this.mViewBinding).layoutTime.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelTheme.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                SelectListDialog.asDefault(true).setTitle(ActSmartPanelTheme.this.getString(R.string.app_str_screensaver_time_setting)).setConfirmString(ActSmartPanelTheme.this.getString(R.string.confirm)).setCancelString(ActSmartPanelTheme.this.getString(R.string.cancel)).setSelectPosition(((ActSmartPanelThemeVM) ActSmartPanelTheme.this.mViewModel).getTimePos()).setSelectList(((ActSmartPanelThemeVM) ActSmartPanelTheme.this.mViewModel).getTimeList()).setConfirmAction(new IAction<Integer>() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelTheme.2.1
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(Integer integer) {
                        ((ActSmartPanelThemeBinding) ActSmartPanelTheme.this.mViewBinding).tvStartTime.setText(((ActSmartPanelThemeVM) ActSmartPanelTheme.this.mViewModel).getTimeList().get(integer.intValue()));
                        ((ActSmartPanelThemeVM) ActSmartPanelTheme.this.mViewModel).changeScreenSaverTime(integer.intValue());
                    }
                }).showDialog(ActSmartPanelTheme.this.activity);
            }
        });
        ((ActSmartPanelThemeBinding) this.mViewBinding).tvStartTime.setText(((ActSmartPanelThemeVM) this.mViewModel).getTimeString());
        if (((ActSmartPanelThemeVM) this.mViewModel).groupControl) {
            return;
        }
        ((ActSmartPanelThemeVM) this.mViewModel).getTheme();
    }

    private void initList() {
        ((ActSmartPanelThemeBinding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(this));
        ((ActSmartPanelThemeBinding) this.mViewBinding).rv.setHasFixedSize(true);
        RecyclerView recyclerView = ((ActSmartPanelThemeBinding) this.mViewBinding).rv;
        BaseQuickAdapter<ActSmartPanelThemeVM.Item, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ActSmartPanelThemeVM.Item, BaseViewHolder>(R.layout.item_theme_selector, ((ActSmartPanelThemeVM) this.mViewModel).getThemeList()) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelTheme.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, ActSmartPanelThemeVM.Item item) {
                if (item.isApply()) {
                    baseViewHolder.setTextColor(R.id.tv_apply, -782275);
                    baseViewHolder.setGone(R.id.iv_sel, true);
                    baseViewHolder.setBackgroundRes(R.id.tv_apply, 0);
                    baseViewHolder.setText(R.id.tv_apply, ActSmartPanelTheme.this.getString(R.string.app_str_applying));
                } else {
                    baseViewHolder.setTextColor(R.id.tv_apply, ActSmartPanelTheme.this.getResources().getColor(R.color.white));
                    baseViewHolder.setBackgroundRes(R.id.tv_apply, R.drawable.shape_red_bt_60);
                    baseViewHolder.setGone(R.id.iv_sel, false);
                    baseViewHolder.setText(R.id.tv_apply, item.isDownload() ? ActSmartPanelTheme.this.getString(R.string.application) : ActSmartPanelTheme.this.getString(R.string.download));
                }
                baseViewHolder.setText(R.id.tv, item.getName());
                baseViewHolder.setBackgroundRes(R.id.iv, item.getImg());
                int adapterPosition = baseViewHolder.getAdapterPosition() + 1;
                baseViewHolder.setGone(R.id.iv_sel2, false);
                if (((ActSmartPanelThemeVM) ActSmartPanelTheme.this.mViewModel).groupControl) {
                    for (int i : ((ActSmartPanelThemeVM) ActSmartPanelTheme.this.mViewModel).applyThemes) {
                        if (i == adapterPosition) {
                            baseViewHolder.setGone(R.id.iv_sel2, true);
                            return;
                        }
                    }
                }
            }
        };
        this.mAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        ((ActSmartPanelThemeBinding) this.mViewBinding).rv.getItemAnimator().setChangeDuration(0L);
        ((ActSmartPanelThemeBinding) this.mViewBinding).rv.clearAnimation();
        ((ActSmartPanelThemeBinding) this.mViewBinding).rv.setItemAnimator(null);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelTheme.4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int pos) {
                if (ActSmartPanelTheme.this.isBlePermissionOk()) {
                    ActSmartPanelTheme.this.showApplyDialog(pos);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showApplyDialog(final int pos) {
        int identifier = getResources().getIdentifier("app_str_screen_" + (((ActSmartPanelThemeVM) this.mViewModel).curScreen + 1), "string", getPackageName());
        if (identifier <= 0) {
            identifier = R.string.app_str_screen_1;
        }
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), "", String.format(getString(R.string.app_str_apply_panel_theme_tip), getString(identifier))).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.cancel), new OnDialogButtonClickListener(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelTheme.5
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                return false;
            }
        }).setOkButton(ActivityUtils.getTopActivity().getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelTheme$$ExternalSyntheticLambda0
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showApplyDialog$0;
                lambda$showApplyDialog$0 = ActSmartPanelTheme.this.lambda$showApplyDialog$0(pos, baseDialog, view);
                return lambda$showApplyDialog$0;
            }
        }).setCancelable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showApplyDialog$0(int i, BaseDialog baseDialog, View view) {
        if (!((ActSmartPanelThemeVM) this.mViewModel).groupControl) {
            this.mSelectTheme = i;
        } else {
            ((ActSmartPanelThemeVM) this.mViewModel).applyThemes[((ActSmartPanelThemeVM) this.mViewModel).curScreen] = i + 1;
        }
        ((ActSmartPanelThemeVM) this.mViewModel).changeTheme(i + 1);
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActSmartPanelThemeVM) this.mViewModel).downloadEvent.observe(this, new Observer<Integer>() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelTheme.6
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer integer) {
                NavUtils.destination(ActSmartPanelDownloadTheme.class).withInt(Constants.SCENE_NUM, ((ActSmartPanelThemeVM) ActSmartPanelTheme.this.mViewModel).curScreen).withInt(Constants.SELECT_THEME, integer.intValue() - 1).withLong(Constants.CONTROL_ID, ((ActSmartPanelThemeVM) ActSmartPanelTheme.this.mViewModel).controlId).withBoolean(Constants.GROUP_CONTROL, ((ActSmartPanelThemeVM) ActSmartPanelTheme.this.mViewModel).groupControl).withDefaultRequestCode().navigation(ActSmartPanelTheme.this.activity);
            }
        });
        ((ActSmartPanelThemeVM) this.mViewModel).applyEvent.observe(this, new Observer<Integer>() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelTheme.7
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer pos) {
                if (((ActSmartPanelThemeVM) ActSmartPanelTheme.this.mViewModel).groupControl) {
                    ActSmartPanelTheme.this.mAdapter.notifyDataSetChanged();
                    return;
                }
                for (int i = 0; i < ActSmartPanelTheme.this.mAdapter.getData().size(); i++) {
                    ActSmartPanelThemeVM.Item item = (ActSmartPanelThemeVM.Item) ActSmartPanelTheme.this.mAdapter.getData().get(i);
                    if (i == pos.intValue()) {
                        item.setDownload(true);
                        item.setApply(true);
                    } else {
                        item.setApply(false);
                    }
                    ActSmartPanelTheme.this.mAdapter.setData(i, item);
                }
            }
        });
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            ((ActSmartPanelThemeVM) this.mViewModel).applyEvent.setValue(Integer.valueOf(this.mSelectTheme));
            if (!((ActSmartPanelThemeVM) this.mViewModel).groupControl) {
                ((ActSmartPanelThemeVM) this.mViewModel).getTheme();
            } else {
                ((ActSmartPanelThemeVM) this.mViewModel).applyThemes[((ActSmartPanelThemeVM) this.mViewModel).curScreen] = this.mSelectTheme + 1;
            }
        }
    }
}