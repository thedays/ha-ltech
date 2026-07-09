package com.ltech.smarthome.ui.voicecall.bind;

import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.justalk.cloud.lemon.MtcUserConstants;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActVoiceCallBindBinding;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.push.PushContentParamKey;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.ui.voicecall.provider.WhiteListContentProvider;
import com.ltech.smarthome.ui.voicecall.provider.WhiteListHeaderProvider;
import com.zhuhai.ltech.lt_voice_call_api.bean.WhiteListUserBean;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActBindVoiceCall extends VMActivity<ActVoiceCallBindBinding, ActBindVoiceCallVM> implements ISelectAction {
    private MultipleItemRvAdapter<WhiteListUserBean, BaseViewHolder> mAdapter;
    private int mLastPos = 0;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.rv;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_voice_call_bind;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void lambda$edit$0(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.voice_call_selected_call_member));
        setBackImage(R.mipmap.icon_back);
        intList();
        ((ActBindVoiceCallVM) this.mViewModel).panelId = getIntent().getLongExtra("device_id", -1L);
        ((ActBindVoiceCallVM) this.mViewModel).loadData();
    }

    private void intList() {
        MultipleItemRvAdapter<WhiteListUserBean, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<WhiteListUserBean, BaseViewHolder>(this, new ArrayList()) { // from class: com.ltech.smarthome.ui.voicecall.bind.ActBindVoiceCall.1
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
        this.mAdapter.bindToRecyclerView(((ActVoiceCallBindBinding) this.mViewBinding).rv);
        ((ActVoiceCallBindBinding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(this));
        ((ActVoiceCallBindBinding) this.mViewBinding).rv.setAdapter(this.mAdapter);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.voicecall.bind.ActBindVoiceCall.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                int i;
                String str;
                String str2;
                String str3;
                String str4;
                int i2;
                long j;
                if (ActBindVoiceCall.this.mAdapter.getItemViewType(position) == 0 || ActBindVoiceCall.this.mAdapter.getData().size() <= position) {
                    return;
                }
                WhiteListUserBean whiteListUserBean = (WhiteListUserBean) ActBindVoiceCall.this.mAdapter.getData().get(ActBindVoiceCall.this.mLastPos);
                int i3 = 0;
                whiteListUserBean.setSel(false);
                ActBindVoiceCall.this.mAdapter.setData(ActBindVoiceCall.this.mLastPos, whiteListUserBean);
                ActBindVoiceCall.this.mLastPos = position;
                WhiteListUserBean whiteListUserBean2 = (WhiteListUserBean) ActBindVoiceCall.this.mAdapter.getData().get(position);
                whiteListUserBean2.setSel(true);
                ActBindVoiceCall.this.mAdapter.setData(position, whiteListUserBean2);
                long j2 = 0;
                if (whiteListUserBean2.getMember() != null) {
                    long userid = whiteListUserBean2.getMember().getUserid();
                    String headurl = whiteListUserBean2.getMember().getHeadurl();
                    String floorname = whiteListUserBean2.getMember().getFloorname();
                    str4 = whiteListUserBean2.getMember().getRoomname();
                    i2 = whiteListUserBean2.getMember().getType();
                    i = whiteListUserBean2.getMember().getPaneltype();
                    str = whiteListUserBean2.getMember().getUsername();
                    str3 = floorname;
                    str2 = headurl;
                    j = userid;
                    i3 = 1;
                } else {
                    i = 5;
                    if (whiteListUserBean2.getGroup() != null) {
                        long panelvoicegroupid = whiteListUserBean2.getGroup().getPanelvoicegroupid();
                        str = whiteListUserBean2.getGroup().getName();
                        str2 = "";
                        str3 = str2;
                        i3 = 2;
                        i2 = 0;
                        str4 = str3;
                        j = 0;
                        j2 = panelvoicegroupid;
                    } else {
                        str = "";
                        str2 = str;
                        str3 = str2;
                        str4 = str3;
                        i2 = 0;
                        j = 0;
                    }
                }
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(PushContentParamKey.VOICE_CALL_GROUP_ID, (Object) Long.valueOf(j2));
                    jSONObject.put("voicetype", (Object) Integer.valueOf(i3));
                    jSONObject.put(PushContentParamKey.USER_ID, (Object) Long.valueOf(j));
                    jSONObject.put("headurl", (Object) str2);
                    jSONObject.put("floorname", (Object) str3);
                    jSONObject.put("roomname", (Object) str4);
                    jSONObject.put("type", (Object) Integer.valueOf(i2));
                    jSONObject.put("paneltype", (Object) Integer.valueOf(i));
                    jSONObject.put(MtcUserConstants.MTC_USER_ID_USERNAME, (Object) str);
                    ((ActBindVoiceCallVM) ActBindVoiceCall.this.mViewModel).saveData = jSONObject;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(R.string.app_str_voice_call_bind_empty));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActBindVoiceCallVM) this.mViewModel).data.observe(this, new Observer<List<WhiteListUserBean>>() { // from class: com.ltech.smarthome.ui.voicecall.bind.ActBindVoiceCall.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<WhiteListUserBean> whiteListUserBeans) {
                if (whiteListUserBeans.size() > 0) {
                    ActBindVoiceCall.this.showContent();
                    ActBindVoiceCall actBindVoiceCall = ActBindVoiceCall.this;
                    actBindVoiceCall.setEditString(actBindVoiceCall.getString(R.string.voice_call_str_save));
                    ActBindVoiceCall.this.mAdapter.replaceData(whiteListUserBeans);
                    return;
                }
                ActBindVoiceCall.this.setEditString("");
                ActBindVoiceCall.this.showEmpty();
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (((ActBindVoiceCallVM) this.mViewModel).saveData != null) {
            SceneHelper.instance().maskType = MaskType.VOICE_CALL;
            SceneHelper.instance().controlObject = ((ActBindVoiceCallVM) this.mViewModel).saveData;
            SceneHelper.instance().panelid = ((ActBindVoiceCallVM) this.mViewModel).panelId;
            SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.voicecall.bind.ActBindVoiceCall$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActBindVoiceCall.this.lambda$edit$0((Boolean) obj);
                }
            });
        }
    }
}