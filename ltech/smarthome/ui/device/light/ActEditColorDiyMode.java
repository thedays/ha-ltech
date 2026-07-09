package com.ltech.smarthome.ui.device.light;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.SeekBar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActEditColorDiyModeBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.SelectColorDialog;
import com.smart.product_agreement.bean.GeneralModeInfo;
import java.nio.charset.Charset;
import java.util.Locale;

/* loaded from: classes4.dex */
public class ActEditColorDiyMode extends VMActivity<ActEditColorDiyModeBinding, ActEditColorDiyModeVM> {
    private BaseQuickAdapter<Integer, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_edit_color_diy_mode;
    }

    @Override // com.ltech.smarthome.base.VMActivity
    protected boolean useVMStateEvent() {
        return true;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.edit_mode));
        ((ActEditColorDiyModeBinding) this.mViewBinding).tabMode.addTab(((ActEditColorDiyModeBinding) this.mViewBinding).tabMode.newTab().setText(getString(R.string.gradual)));
        ((ActEditColorDiyModeBinding) this.mViewBinding).tabMode.addTab(((ActEditColorDiyModeBinding) this.mViewBinding).tabMode.newTab().setText(getString(R.string.jump)));
        ((ActEditColorDiyModeBinding) this.mViewBinding).tabMode.addTab(((ActEditColorDiyModeBinding) this.mViewBinding).tabMode.newTab().setText(getString(R.string.strobe)));
        ((ActEditColorDiyModeBinding) this.mViewBinding).tabMode.addTab(((ActEditColorDiyModeBinding) this.mViewBinding).tabMode.newTab().setText(getString(R.string.breath)));
        ((ActEditColorDiyModeBinding) this.mViewBinding).tabMode.addTab(((ActEditColorDiyModeBinding) this.mViewBinding).tabMode.newTab().setText(getString(R.string.static_color)));
        ((ActEditColorDiyModeBinding) this.mViewBinding).tabMode.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.ui.device.light.ActEditColorDiyMode.1
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                ((ActEditColorDiyModeVM) ActEditColorDiyMode.this.mViewModel).setModeType(tab.getPosition());
            }
        });
        ((ActEditColorDiyModeBinding) this.mViewBinding).sbSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.device.light.ActEditColorDiyMode.2
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    int i = progress + 1;
                    ((ActEditColorDiyModeVM) ActEditColorDiyMode.this.mViewModel).getModeInfo().setSpeed(i);
                    ((ActEditColorDiyModeBinding) ActEditColorDiyMode.this.mViewBinding).tvSpeed.setText(String.valueOf(i));
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((ActEditColorDiyModeVM) ActEditColorDiyMode.this.mViewModel).getModeInfo().setSpeed(seekBar.getProgress() + 1);
                ((ActEditColorDiyModeBinding) ActEditColorDiyMode.this.mViewBinding).tvSpeed.setText(String.valueOf(seekBar.getProgress() + 1));
            }
        });
        ((ActEditColorDiyModeBinding) this.mViewBinding).sbBrt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.device.light.ActEditColorDiyMode.3
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((ActEditColorDiyModeVM) ActEditColorDiyMode.this.mViewModel).getModeInfo().setRgbBrt(LightUtils.progress2Brt(progress));
                    ((ActEditColorDiyModeBinding) ActEditColorDiyMode.this.mViewBinding).tvBrt.setText(String.format(Locale.US, "%d%%", Integer.valueOf(progress)));
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((ActEditColorDiyModeVM) ActEditColorDiyMode.this.mViewModel).getModeInfo().setRgbBrt(LightUtils.progress2Brt(seekBar.getProgress()));
                ((ActEditColorDiyModeBinding) ActEditColorDiyMode.this.mViewBinding).tvBrt.setText(String.format(Locale.US, "%d%%", Integer.valueOf(seekBar.getProgress())));
            }
        });
        ((ActEditColorDiyModeBinding) this.mViewBinding).sbW.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.device.light.ActEditColorDiyMode.4
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ((ActEditColorDiyModeVM) ActEditColorDiyMode.this.mViewModel).getModeInfo().setWyBrt(LightUtils.progress2Brt(progress));
                    ((ActEditColorDiyModeBinding) ActEditColorDiyMode.this.mViewBinding).tvW.setText(String.format(Locale.US, "%d%%", Integer.valueOf(progress)));
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((ActEditColorDiyModeVM) ActEditColorDiyMode.this.mViewModel).getModeInfo().setWyBrt(LightUtils.progress2Brt(seekBar.getProgress()));
                ((ActEditColorDiyModeBinding) ActEditColorDiyMode.this.mViewBinding).tvW.setText(String.format(Locale.US, "%d%%", Integer.valueOf(seekBar.getProgress())));
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActEditColorDiyModeVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActEditColorDiyModeVM) this.mViewModel).modePosition = getIntent().getIntExtra(Constants.MODE_POSITION, -1);
        Injection.repo().device().getDeviceFromDb(((ActEditColorDiyModeVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActEditColorDiyMode$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEditColorDiyMode.this.lambda$startObserve$0((Device) obj);
            }
        });
        ((ActEditColorDiyModeVM) this.mViewModel).paramLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActEditColorDiyMode$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEditColorDiyMode.this.lambda$startObserve$1((GeneralModeInfo) obj);
            }
        });
        ((ActEditColorDiyModeVM) this.mViewModel).previewLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActEditColorDiyMode$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEditColorDiyMode.this.lambda$startObserve$2((Boolean) obj);
            }
        });
        ((ActEditColorDiyModeVM) this.mViewModel).editNameEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActEditColorDiyMode$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEditColorDiyMode.this.lambda$startObserve$3((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        ((ActEditColorDiyModeVM) this.mViewModel).controlDevice = device;
        ((ActEditColorDiyModeVM) this.mViewModel).queryInfo(this, ((ActEditColorDiyModeVM) this.mViewModel).modePosition);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(GeneralModeInfo generalModeInfo) {
        refreshColorList();
        ((ActEditColorDiyModeBinding) this.mViewBinding).tvModeName.setText(generalModeInfo.getModeName());
        ((ActEditColorDiyModeBinding) this.mViewBinding).tabMode.getTabAt(((ActEditColorDiyModeVM) this.mViewModel).getModeTypePosition(generalModeInfo.getModeType())).select();
        ((ActEditColorDiyModeBinding) this.mViewBinding).sbSpeed.setProgress(generalModeInfo.getSpeed() - 1);
        ((ActEditColorDiyModeBinding) this.mViewBinding).tvSpeed.setText(String.valueOf(generalModeInfo.getSpeed()));
        ((ActEditColorDiyModeBinding) this.mViewBinding).sbBrt.setProgress(LightUtils.brt2Progress(generalModeInfo.getRgbBrt()));
        ((ActEditColorDiyModeBinding) this.mViewBinding).tvBrt.setText(String.format(Locale.US, "%d%%", Integer.valueOf(((ActEditColorDiyModeBinding) this.mViewBinding).sbBrt.getProgress())));
        ((ActEditColorDiyModeBinding) this.mViewBinding).sbW.setProgress(LightUtils.brt2Progress(generalModeInfo.getwOrwy()));
        ((ActEditColorDiyModeBinding) this.mViewBinding).tvW.setText(String.format(Locale.US, "%d%%", Integer.valueOf(((ActEditColorDiyModeBinding) this.mViewBinding).sbW.getProgress())));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Boolean bool) {
        if (bool != null) {
            ((ActEditColorDiyModeVM) this.mViewModel).previewMode(this, bool.booleanValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r1) {
        showEditNameDialog();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        ((ActEditColorDiyModeVM) this.mViewModel).saveData(this);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        ((ActEditColorDiyModeVM) this.mViewModel).queryInfo(this, ((ActEditColorDiyModeVM) this.mViewModel).modePosition);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showContent() {
        super.showContent();
        setEditString(getString(R.string.save));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshColorList() {
        BaseQuickAdapter<Integer, BaseViewHolder> baseQuickAdapter = this.mAdapter;
        if (baseQuickAdapter == null) {
            BaseQuickAdapter<Integer, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<Integer, BaseViewHolder>(R.layout.item_color, ((ActEditColorDiyModeVM) this.mViewModel).getModeInfo().getColorList()) { // from class: com.ltech.smarthome.ui.device.light.ActEditColorDiyMode.5
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, Integer item) {
                    if (5 == ((ActEditColorDiyModeVM) ActEditColorDiyMode.this.mViewModel).getModeInfo().getModeType() && helper.getAdapterPosition() > 0) {
                        helper.setImageDrawable(R.id.civ_color, new ColorDrawable(-7829368));
                    } else {
                        helper.setImageDrawable(R.id.civ_color, new ColorDrawable(item.intValue()));
                    }
                }
            };
            this.mAdapter = baseQuickAdapter2;
            baseQuickAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActEditColorDiyMode$$ExternalSyntheticLambda4
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    ActEditColorDiyMode.this.lambda$refreshColorList$4(baseQuickAdapter3, view, i);
                }
            });
            this.mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActEditColorDiyMode$$ExternalSyntheticLambda5
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemLongClickListener
                public final boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    boolean lambda$refreshColorList$5;
                    lambda$refreshColorList$5 = ActEditColorDiyMode.this.lambda$refreshColorList$5(baseQuickAdapter3, view, i);
                    return lambda$refreshColorList$5;
                }
            });
            this.mAdapter.bindToRecyclerView(((ActEditColorDiyModeBinding) this.mViewBinding).rvColor);
            ((ActEditColorDiyModeBinding) this.mViewBinding).rvColor.setHasFixedSize(true);
            ((ActEditColorDiyModeBinding) this.mViewBinding).rvColor.setLayoutManager(new GridLayoutManager(this, 8));
            ((ActEditColorDiyModeBinding) this.mViewBinding).rvColor.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.light.ActEditColorDiyMode.6
                @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(ConvertUtils.dp2px(5.0f), 0, ConvertUtils.dp2px(5.0f), 0);
                }
            });
            return;
        }
        baseQuickAdapter.setNewData(((ActEditColorDiyModeVM) this.mViewModel).getModeInfo().getColorList());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshColorList$4(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        showSelectColorDialog(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$refreshColorList$5(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        ((ActEditColorDiyModeVM) this.mViewModel).getModeInfo().getColorList().set(i, -16777216);
        refreshColorList();
        return false;
    }

    private void showSelectColorDialog(final int position) {
        SelectColorDialog.rgb().setOnDialogCallback(new SelectColorDialog.OnDialogCallback() { // from class: com.ltech.smarthome.ui.device.light.ActEditColorDiyMode.7
            @Override // com.ltech.smarthome.view.dialog.SelectColorDialog.OnDialogCallback
            public void onColorChanged(int red, int green, int blue, int brt, int w, boolean finish) {
                ((ActEditColorDiyModeVM) ActEditColorDiyMode.this.mViewModel).sendColor(ActEditColorDiyMode.this, red, green, blue, finish);
            }

            @Override // com.ltech.smarthome.view.dialog.SelectColorDialog.OnDialogCallback
            public void onSaved(int red, int green, int blue, int brt, int w) {
                ((ActEditColorDiyModeVM) ActEditColorDiyMode.this.mViewModel).getModeInfo().getColorList().set(position, Integer.valueOf(Color.rgb(red, green, blue)));
                ActEditColorDiyMode.this.refreshColorList();
            }
        }).showDialog(this);
    }

    private void showEditNameDialog() {
        EditDialog.asDefault().setContent(((ActEditColorDiyModeVM) this.mViewModel).getModeInfo().getModeName()).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActEditColorDiyMode$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEditColorDiyMode.this.lambda$showEditNameDialog$6((String) obj);
            }
        }).showDialog(getSupportFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$6(String str) {
        if (str.getBytes(Charset.forName("GB2312")).length > 12) {
            SmartToast.showShort(getString(R.string.name_too_long));
        } else {
            ((ActEditColorDiyModeVM) this.mViewModel).getModeInfo().setModeName(str);
            ((ActEditColorDiyModeBinding) this.mViewBinding).tvModeName.setText(str);
        }
    }
}