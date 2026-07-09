package com.ltech.smarthome.ui.qrcode;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActShareDuvListBinding;
import com.ltech.smarthome.ui.qrcode.ShareHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.message.utils.LHomeLog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActShareDuvList extends BaseNormalActivity<ActShareDuvListBinding> {
    private BaseQuickAdapter<ShareHelper.ShareData, BaseViewHolder> mAdapter;
    private List<ShareHelper.ShareData> shareList = new ArrayList();
    private List<Integer> selectArray = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_share_duv_list;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        setTitle(getString(R.string.share_duv));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.app_str_select_all));
        this.shareList = ShareHelper.INSTANCE.getWholeDataList();
        initAdapter();
        changeShareNum();
        ((ActShareDuvListBinding) this.mViewBinding).tvShare.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.qrcode.ActShareDuvList$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActShareDuvList.this.lambda$initView$0(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (this.selectArray.size() == 0) {
            SmartToast.showShort(R.string.please_choose);
            return;
        }
        String createQrCode = ShareHelper.INSTANCE.createQrCode(this.shareList, this.selectArray);
        LHomeLog.i(getClass(), "codeData:" + createQrCode);
        NavUtils.destination(ActSaveQrCode.class).navigation(this);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (this.selectArray.size() != this.shareList.size()) {
            this.selectArray.clear();
            this.selectArray.addAll(ShareHelper.INSTANCE.getAllIndex(this.shareList));
            setEditString(getString(R.string.app_str_cancel_select_all));
        } else {
            this.selectArray.clear();
            setEditString(getString(R.string.app_str_select_all));
        }
        changeShareNum();
        this.mAdapter.notifyDataSetChanged();
    }

    private void initAdapter() {
        BaseQuickAdapter<ShareHelper.ShareData, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ShareHelper.ShareData, BaseViewHolder>(R.layout.item_share_data, this.shareList) { // from class: com.ltech.smarthome.ui.qrcode.ActShareDuvList.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, ShareHelper.ShareData item) {
                helper.setText(R.id.tv_main, item.getName()).setImageResource(R.id.iv_select, ActShareDuvList.this.selectArray.contains(Integer.valueOf(helper.getBindingAdapterPosition())) ? R.mipmap.ic_tick_sel : R.mipmap.icon_type_default).setGone(R.id.view_divider, helper.getBindingAdapterPosition() != getItemCount() - 1);
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.qrcode.ActShareDuvList$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActShareDuvList.this.lambda$initAdapter$1(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActShareDuvListBinding) this.mViewBinding).rvContent);
        ((ActShareDuvListBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActShareDuvListBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAdapter$1(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (this.selectArray.contains(Integer.valueOf(i))) {
            this.selectArray.remove(Integer.valueOf(i));
        } else {
            this.selectArray.add(Integer.valueOf(i));
        }
        this.mAdapter.notifyItemChanged(i);
        setEditString(getString(this.selectArray.size() != this.shareList.size() ? R.string.app_str_select_all : R.string.app_str_cancel_select_all));
        changeShareNum();
    }

    private void changeShareNum() {
        ((ActShareDuvListBinding) this.mViewBinding).tvShare.setText(getString(R.string.share_with_num, new Object[]{Integer.valueOf(this.selectArray.size())}));
    }
}