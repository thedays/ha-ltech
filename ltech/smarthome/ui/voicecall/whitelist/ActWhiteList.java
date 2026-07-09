package com.ltech.smarthome.ui.voicecall.whitelist;

import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActVoiceCallWhitelistAddBinding;
import com.ltech.smarthome.ui.voicecall.provider.WhiteListContentProvider;
import com.ltech.smarthome.ui.voicecall.provider.WhiteListHeaderProvider;
import com.ltech.smarthome.utils.Constants;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.zhuhai.ltech.lt_voice_call_api.bean.WhiteListUserBean;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActWhiteList extends VMActivity<ActVoiceCallWhitelistAddBinding, ActWhiteListVM> {
    private MultipleItemRvAdapter<WhiteListUserBean, BaseViewHolder> mAdapter;

    static /* synthetic */ boolean lambda$showDelTipDialog$0(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_voice_call_whitelist_add;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.voice_call_str_allow));
        setBackImage(R.mipmap.icon_back);
        ((ActVoiceCallWhitelistAddBinding) this.mViewBinding).tvSave.setText(String.format(getString(R.string.voice_call_str_save_num), 0));
        ((ActWhiteListVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, 0L);
        ((ActWhiteListVM) this.mViewModel).panelId = getIntent().getLongExtra("panel_id", 0L);
        String stringExtra = getIntent().getStringExtra("ids");
        if (stringExtra != null && !TextUtils.isEmpty(stringExtra)) {
            ((ActWhiteListVM) this.mViewModel).userIds = (List) GsonUtils.fromJson(stringExtra, new TypeToken<List<Long>>(this) { // from class: com.ltech.smarthome.ui.voicecall.whitelist.ActWhiteList.1
            }.getType());
        }
        ((ActWhiteListVM) this.mViewModel).loadMember();
        intList();
        ((ActVoiceCallWhitelistAddBinding) this.mViewBinding).tvSave.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.voicecall.whitelist.ActWhiteList.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ArrayList arrayList = new ArrayList();
                for (T t : ActWhiteList.this.mAdapter.getData()) {
                    if (t.getViewType() != 0 && t.isSel()) {
                        arrayList.add(Long.valueOf(t.getMember().getUserid()));
                    }
                }
                ((ActWhiteListVM) ActWhiteList.this.mViewModel).saveWhiteList(arrayList);
            }
        });
    }

    private void intList() {
        MultipleItemRvAdapter<WhiteListUserBean, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<WhiteListUserBean, BaseViewHolder>(this, new ArrayList()) { // from class: com.ltech.smarthome.ui.voicecall.whitelist.ActWhiteList.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(WhiteListUserBean whiteListUserBean) {
                return whiteListUserBean.getViewType();
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new WhiteListContentProvider());
                this.mProviderDelegate.registerProvider(new WhiteListHeaderProvider());
            }
        };
        this.mAdapter = multipleItemRvAdapter;
        multipleItemRvAdapter.finishInitialize();
        this.mAdapter.bindToRecyclerView(((ActVoiceCallWhitelistAddBinding) this.mViewBinding).rv);
        ((ActVoiceCallWhitelistAddBinding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(this));
        ((ActVoiceCallWhitelistAddBinding) this.mViewBinding).rv.setAdapter(this.mAdapter);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.voicecall.whitelist.ActWhiteList.4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                if (ActWhiteList.this.mAdapter.getItemViewType(position) == 0 || ActWhiteList.this.mAdapter.getData().size() <= position) {
                    return;
                }
                WhiteListUserBean whiteListUserBean = (WhiteListUserBean) ActWhiteList.this.mAdapter.getData().get(position);
                int hasBind = ((ActWhiteListVM) ActWhiteList.this.mViewModel).hasBind(whiteListUserBean.getMember().getUserid());
                if (whiteListUserBean.isSel() && hasBind != 0) {
                    ActWhiteList.this.showDelTipDialog(whiteListUserBean.getTitle(), hasBind);
                }
                whiteListUserBean.setSel(!whiteListUserBean.isSel());
                ActWhiteList.this.mAdapter.setData(position, whiteListUserBean);
                ActWhiteList.this.refreshNum();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDelTipDialog(String title, int bindType) {
        String string = getString(R.string.app_str_voice_call_member_has_bind1_tip);
        if (bindType == 2) {
            string = getString(R.string.app_str_voice_call_member_has_bind2_tip);
        } else if (bindType == 3) {
            string = getString(R.string.app_str_voice_call_member_has_bind3_tip);
        }
        MessageDialog.show(this, "", String.format(string, title)).setOkButton(getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.voicecall.whitelist.ActWhiteList$$ExternalSyntheticLambda0
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return ActWhiteList.lambda$showDelTipDialog$0(baseDialog, view);
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActWhiteListVM) this.mViewModel).data.observe(this, new Observer<List<WhiteListUserBean>>() { // from class: com.ltech.smarthome.ui.voicecall.whitelist.ActWhiteList.5
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<WhiteListUserBean> whiteListUserBeans) {
                ActWhiteList.this.mAdapter.replaceData(whiteListUserBeans);
                ActWhiteList.this.refreshNum();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshNum() {
        Iterator<WhiteListUserBean> it = this.mAdapter.getData().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().isSel()) {
                i++;
            }
        }
        ((ActVoiceCallWhitelistAddBinding) this.mViewBinding).tvSave.setText(String.format(getString(R.string.voice_call_str_save_num), Integer.valueOf(i)));
    }
}