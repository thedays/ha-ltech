package com.ltech.smarthome.ui.voicecall.group;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActVoiceCallGropListBinding;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.zhuhai.ltech.lt_voice_call_api.bean.VoiceCallGroup;
import com.zhuhai.ltech.lt_voice_call_api.bean.VoiceCallMember;
import java.util.List;

/* loaded from: classes4.dex */
public class ActVoiceGroupList extends VMActivity<ActVoiceCallGropListBinding, ActVoiceGroupListVM> {
    private BaseQuickAdapter<VoiceCallGroup, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_voice_call_grop_list;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.voice_call_group_setting));
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_add);
        initList();
    }

    private void initList() {
        String stringExtra = getIntent().getStringExtra("voice_call_group");
        ((ActVoiceGroupListVM) this.mViewModel).panelId = getIntent().getLongExtra("panel_id", 0L);
        ((ActVoiceGroupListVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, 0L);
        if (stringExtra != null && !TextUtils.isEmpty(stringExtra)) {
            ((ActVoiceGroupListVM) this.mViewModel).setGroupList((List) GsonUtils.fromJson(stringExtra, new TypeToken<List<VoiceCallGroup>>(this) { // from class: com.ltech.smarthome.ui.voicecall.group.ActVoiceGroupList.1
            }.getType()));
        }
        ((ActVoiceCallGropListBinding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView = ((ActVoiceCallGropListBinding) this.mViewBinding).rv;
        BaseQuickAdapter<VoiceCallGroup, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<VoiceCallGroup, BaseViewHolder>(R.layout.item_voice_call_group_list) { // from class: com.ltech.smarthome.ui.voicecall.group.ActVoiceGroupList.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, VoiceCallGroup voiceCallGroup) {
                int i;
                int i2;
                baseViewHolder.setText(R.id.tv_title, voiceCallGroup.getName());
                if (voiceCallGroup.getUserinfoList() != null) {
                    i = 0;
                    i2 = 0;
                    for (VoiceCallMember voiceCallMember : voiceCallGroup.getUserinfoList()) {
                        if (voiceCallMember.getType() == 1) {
                            i2++;
                        }
                        if (voiceCallMember.getType() == 2) {
                            i++;
                        }
                    }
                } else {
                    i = 0;
                    i2 = 0;
                }
                baseViewHolder.setText(R.id.tv_sub, String.format(ActVoiceGroupList.this.getString(R.string.voice_call_add_group_sub), Integer.valueOf(i), Integer.valueOf(i2)));
                baseViewHolder.setImageResource(R.id.iv_header, R.mipmap.contacts_ic_group);
            }
        };
        this.mAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.voicecall.group.ActVoiceGroupList.3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int position) {
                VoiceCallGroup voiceCallGroup = (VoiceCallGroup) ActVoiceGroupList.this.mAdapter.getData().get(position);
                Intent intent = new Intent(ActVoiceGroupList.this.activity, (Class<?>) ActVoiceCallAddGroup.class);
                intent.putExtra("panel_id", ((ActVoiceGroupListVM) ActVoiceGroupList.this.mViewModel).panelId);
                intent.putExtra(Constants.PLACE_ID, ((ActVoiceGroupListVM) ActVoiceGroupList.this.mViewModel).placeId);
                intent.putExtra("voice_call_group", GsonUtils.toJson(voiceCallGroup));
                ActVoiceGroupList.this.startActivityForResult(intent, 1000);
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActVoiceGroupListVM) this.mViewModel).data.observe(this, new Observer<List<VoiceCallGroup>>() { // from class: com.ltech.smarthome.ui.voicecall.group.ActVoiceGroupList.4
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<VoiceCallGroup> voiceCallGroups) {
                ActVoiceGroupList.this.mAdapter.replaceData(voiceCallGroups);
                if (voiceCallGroups.size() > 0) {
                    ActVoiceGroupList.this.showContent();
                } else {
                    ActVoiceGroupList.this.showEmpty();
                }
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        addGroup();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onEmptyTry() {
        super.onEmptyTry();
        addGroup();
    }

    private void addGroup() {
        NavUtils.destination(ActVoiceCallAddGroup.class).withLong("panel_id", ((ActVoiceGroupListVM) this.mViewModel).panelId).withLong(Constants.PLACE_ID, ((ActVoiceGroupListVM) this.mViewModel).placeId).withInt(Constants.MAX, getSceneNameNum()).withDefaultRequestCode().navigation(this);
    }

    private int getSceneNameNum() {
        boolean z = true;
        int i = 0;
        while (z) {
            i++;
            z = ((ActVoiceGroupListVM) this.mViewModel).getGroupName(getString(R.string.voice_call_group_multi_group) + i);
        }
        return i;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(R.string.app_str_voice_call_no_group).emptyTryStringRes(R.string.app_str_voice_call_add_group));
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100 && data != null) {
            ((ActVoiceGroupListVM) this.mViewModel).refresh((VoiceCallGroup) GsonUtils.fromJson(data.getStringExtra("data"), new TypeToken<VoiceCallGroup>(this) { // from class: com.ltech.smarthome.ui.voicecall.group.ActVoiceGroupList.5
            }.getType()));
        } else {
            if (resultCode != 101 || data == null) {
                return;
            }
            ((ActVoiceGroupListVM) this.mViewModel).del(data.getLongExtra("id", 0L));
        }
    }
}