package com.ltech.smarthome.ui.device.rs8;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActRs8CurtainBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.response.rs8.Rs8CodeLibResponse;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.NavHelper;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActRs8Curtain extends BaseControlActivity<ActRs8CurtainBinding, ActRs8VM> {
    private int DEFAULT_PROGRESS = 30;
    private BaseQuickAdapter<Rs8CodeLibResponse.CodeLib.Action, BaseViewHolder> keyAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_rs8_curtain;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        initCurtainUpDownProgress(this.DEFAULT_PROGRESS);
        initCurtainProgress(this.DEFAULT_PROGRESS);
        initList();
    }

    private void initList() {
        BaseQuickAdapter<Rs8CodeLibResponse.CodeLib.Action, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Rs8CodeLibResponse.CodeLib.Action, BaseViewHolder>(R.layout.item_rs8_key) { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8Curtain.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Rs8CodeLibResponse.CodeLib.Action item) {
                helper.setImageResource(R.id.iv_icon, ((ActRs8VM) ActRs8Curtain.this.mViewModel).getImg(item.getIcon())).setText(R.id.tv_name, LanguageUtils.isChinese(ActivityUtils.getTopActivity()) ? item.getCname() : item.getEname());
            }
        };
        this.keyAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8Curtain$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActRs8Curtain.this.lambda$initList$0(baseQuickAdapter2, view, i);
            }
        });
        this.keyAdapter.bindToRecyclerView(((ActRs8CurtainBinding) this.mViewBinding).rvContent);
        ((ActRs8CurtainBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(this, 3));
        ((ActRs8CurtainBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((ActRs8CurtainBinding) this.mViewBinding).rvContent.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8Curtain.2
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = ConvertUtils.dp2px(32.0f);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initList$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        Rs8CodeLibResponse.CodeLib.Action action = this.keyAdapter.getData().get(i);
        ((ActRs8VM) this.mViewModel).sendCmd(action.getInstruct(), action.getReplyinstruct());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActRs8VM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActRs8VM) this.mViewModel).placeId = Injection.repo().home().getSelPlace().getPlaceId();
        ((ActRs8VM) this.mViewModel).isSelected = getIntent().getBooleanExtra(Constants.SELECT_ACTION, false);
        ((ActRs8VM) this.mViewModel).device = Injection.repo().device().getDeviceById(((ActRs8VM) this.mViewModel).controlId);
        if (((ActRs8VM) this.mViewModel).isSelected) {
            ((ActRs8VM) this.mViewModel).categoryId = getIntent().getLongExtra(Constants.CATEGORY_ID, -1L);
            ((ActRs8VM) this.mViewModel).brandId = getIntent().getLongExtra(Constants.BRAND_ID, -1L);
            ((ActRs8VM) this.mViewModel).img = getIntent().getStringExtra("image");
            ((ActRs8VM) this.mViewModel).deviceName = getIntent().getStringExtra("device_name");
            ((ActRs8VM) this.mViewModel).address.setValue(getIntent().getStringExtra(Constants.ADDRESS));
        } else {
            setEditImage(R.mipmap.ic_setting);
            ((ActRs8VM) this.mViewModel).loadDeviceAction();
        }
        setTitle(((ActRs8VM) this.mViewModel).deviceName);
        if (((ActRs8VM) this.mViewModel).img.equals("3")) {
            ((ActRs8CurtainBinding) this.mViewBinding).ivDevice.setImageResource(R.mipmap.cgcurtain_pic2);
            ((ActRs8CurtainBinding) this.mViewBinding).curtainUpDown.setVisibility(0);
            ((ActRs8CurtainBinding) this.mViewBinding).curtainLeft.setVisibility(8);
            ((ActRs8CurtainBinding) this.mViewBinding).curtainRight.setVisibility(8);
            ((ActRs8CurtainBinding) this.mViewBinding).curtainLeftOnly.setVisibility(8);
        } else {
            ((ActRs8CurtainBinding) this.mViewBinding).ivDevice.setImageResource(R.mipmap.cgcurtain_pic);
            ((ActRs8CurtainBinding) this.mViewBinding).curtainLeft.setVisibility(0);
            ((ActRs8CurtainBinding) this.mViewBinding).curtainRight.setVisibility(0);
            ((ActRs8CurtainBinding) this.mViewBinding).curtainUpDown.setVisibility(8);
            ((ActRs8CurtainBinding) this.mViewBinding).curtainLeftOnly.setVisibility(8);
        }
        if (((ActRs8VM) this.mViewModel).isSelected) {
            ((ActRs8CurtainBinding) this.mViewBinding).cardviewAdd.setVisibility(0);
            ((ActRs8CurtainBinding) this.mViewBinding).tvIndex.setText("0/0");
            ((ActRs8VM) this.mViewModel).initCodeLib();
        }
        ((ActRs8VM) this.mViewModel).codeLibData.observe(this, new Observer<Rs8CodeLibResponse.CodeLib>() { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8Curtain.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(Rs8CodeLibResponse.CodeLib codeLib) {
                List<Rs8CodeLibResponse.CodeLib.Action> arrayList = new ArrayList<>();
                if (codeLib.getActionlist() != null) {
                    if (((ActRs8VM) ActRs8Curtain.this.mViewModel).isSelected) {
                        for (Rs8CodeLibResponse.CodeLib.Action action : codeLib.getActionlist()) {
                            if (action.getPosition() == 1) {
                                arrayList.add(action);
                            }
                        }
                        ((ActRs8VM) ActRs8Curtain.this.mViewModel).waitSaveList = arrayList;
                    } else {
                        arrayList = codeLib.getActionlist();
                    }
                }
                ActRs8Curtain.this.keyAdapter.replaceData(arrayList);
                ((ActRs8CurtainBinding) ActRs8Curtain.this.mViewBinding).tvIndex.setText(String.format("%s/%s", Integer.valueOf(((ActRs8VM) ActRs8Curtain.this.mViewModel).curLib), Integer.valueOf(((ActRs8VM) ActRs8Curtain.this.mViewModel).maxLib)));
            }
        });
        ((ActRs8VM) this.mViewModel).addFinishEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.rs8.ActRs8Curtain$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActRs8Curtain.this.lambda$startObserve$1((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r4) {
        ((ActRs8VM) this.mViewModel).device = Injection.repo().device().getDeviceByDeviceId(((ActRs8VM) this.mViewModel).device.getDeviceId());
        ((ActRs8VM) this.mViewModel).isSelected = false;
        ((ActRs8CurtainBinding) this.mViewBinding).cardviewAdd.setVisibility(8);
        setEditImage(R.mipmap.ic_setting);
        ((ActRs8VM) this.mViewModel).loadDeviceAction();
        ActivityUtils.finishActivity((Class<? extends Activity>) ActRs8AddressWrite.class);
    }

    private void initCurtainProgress(int progress) {
        ((ActRs8CurtainBinding) this.mViewBinding).curtainLeft.setMinProgressValue(progress);
        ((ActRs8CurtainBinding) this.mViewBinding).curtainRight.setMinProgressValue(progress);
        int i = progress + 100;
        ((ActRs8CurtainBinding) this.mViewBinding).curtainLeft.setMax(i);
        ((ActRs8CurtainBinding) this.mViewBinding).curtainRight.setMax(i);
        ((ActRs8CurtainBinding) this.mViewBinding).curtainLeft.setProgress(progress);
        ((ActRs8CurtainBinding) this.mViewBinding).curtainRight.setProgress(progress);
        ((ActRs8CurtainBinding) this.mViewBinding).curtainLeft.setEnabled(false);
        ((ActRs8CurtainBinding) this.mViewBinding).curtainRight.setEnabled(false);
    }

    private void initCurtainUpDownProgress(int progress) {
        ((ActRs8CurtainBinding) this.mViewBinding).curtainUpDown.setMinProgressValue(progress);
        ((ActRs8CurtainBinding) this.mViewBinding).curtainUpDown.setMax(progress + 100);
        ((ActRs8CurtainBinding) this.mViewBinding).curtainUpDown.setProgress(progress);
        ((ActRs8CurtainBinding) this.mViewBinding).curtainUpDown.setEnabled(false);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        NavHelper.goSetting(((ActRs8VM) this.mViewModel).device);
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseControlActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            ((ActRs8VM) this.mViewModel).device = Injection.repo().device().getDeviceById(((ActRs8VM) this.mViewModel).controlId);
            ((ActRs8VM) this.mViewModel).loadDeviceAction();
        }
    }
}