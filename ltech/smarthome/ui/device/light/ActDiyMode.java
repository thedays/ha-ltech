package com.ltech.smarthome.ui.device.light;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseListActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.ModeAssistant;
import com.smart.message.ResponseMsg;
import com.smart.product_agreement.bean.GeneralModeList;
import com.smart.product_agreement.parser.IModeParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class ActDiyMode extends BaseListActivity<GeneralModeList.ModeItem> {
    private Device controlDevice;
    private long controlId;
    private GeneralModeList modeList;

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_diy_mode;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.rv_content;
    }

    @Override // com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.diy));
        setBackImage(R.mipmap.icon_back);
        ((ActSelectBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(this, 2));
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActDiyMode$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActDiyMode.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActDiyMode$$ExternalSyntheticLambda1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActDiyMode.this.lambda$initView$1(baseQuickAdapter, view, i);
            }
        });
        ((ActSelectBinding) this.mViewBinding).rvContent.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.light.ActDiyMode.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(SizeUtils.dp2px(10.0f), SizeUtils.dp2px(5.0f), SizeUtils.dp2px(10.0f), SizeUtils.dp2px(5.0f));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        playDiyMode(this, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (view.getId() == R.id.tv_edit) {
            NavUtils.destination(ActEditColorDiyMode.class).withLong(Constants.CONTROL_ID, this.controlId).withInt(Constants.MODE_POSITION, i).withDefaultRequestCode().navigation(this);
        }
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<GeneralModeList.ModeItem> getList() {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, GeneralModeList.ModeItem item) {
        helper.setText(R.id.tv_mode_name, TextUtils.isEmpty(item.modeName) ? LightModeManager.getGeneralModeDefaultName(this)[helper.getAdapterPosition()] : item.modeName).setBackgroundRes(R.id.iv_cover, LightModeManager.getGeneralModeDefaultIcon(this)[item.picIndex]).addOnClickListener(R.id.tv_edit);
        ((AppCompatTextView) helper.getView(R.id.tv_mode_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        this.controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        Injection.repo().device().getDeviceFromDb(this.controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActDiyMode$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDiyMode.this.lambda$startObserve$2((Device) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Device device) {
        if (this.controlDevice == null) {
            this.controlDevice = device;
            onRetry();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void queryDiyMode() {
        showLoading();
        getModeCmdHelper().queryGeneralModeList(this, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActDiyMode$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActDiyMode.this.lambda$queryDiyMode$3((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryDiyMode$3(ResponseMsg responseMsg) {
        if (responseMsg != null) {
            showContent();
            this.modeList = ((IModeParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parseGeneralModeList(responseMsg.getResData());
            this.mAdapter.setNewData(this.modeList.getModeItemList());
            return;
        }
        showError();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        queryDiyMode();
    }

    public void playDiyMode(Context context, int position) {
        getModeCmdHelper().playGeneralMode(context, Collections.singletonList(Integer.valueOf(position)), 0, false);
    }

    public ModeAssistant getModeCmdHelper() {
        return CmdAssistant.getModeCmdAssistant(this.controlDevice, new int[0]);
    }
}