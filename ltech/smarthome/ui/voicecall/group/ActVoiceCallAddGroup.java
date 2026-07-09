package com.ltech.smarthome.ui.voicecall.group;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActVoiceCallGroupAddBinding;
import com.ltech.smarthome.ui.voicecall.group.ActVoiceCallAddGroup;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.zhuhai.ltech.lt_voice_call_api.bean.VoiceCallGroup;
import com.zhuhai.ltech.lt_voice_call_api.bean.VoiceCallMember;
import com.zhuhai.ltech.lt_voice_call_api.bean.WhiteListUserBean;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActVoiceCallAddGroup extends VMActivity<ActVoiceCallGroupAddBinding, ActVoiceCallGroupVM> {
    private BaseQuickAdapter<WhiteListUserBean, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_voice_call_group_add;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        initList();
        ((ActVoiceCallGroupAddBinding) this.mViewBinding).tvEdit.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.voicecall.group.ActVoiceCallAddGroup.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ((ActVoiceCallGroupVM) ActVoiceCallAddGroup.this.mViewModel).edite = !((ActVoiceCallGroupVM) ActVoiceCallAddGroup.this.mViewModel).edite;
                if (((ActVoiceCallGroupVM) ActVoiceCallAddGroup.this.mViewModel).edite) {
                    ActVoiceCallAddGroup.this.setEditString("");
                    ((ActVoiceCallGroupAddBinding) ActVoiceCallAddGroup.this.mViewBinding).tvEdit.setText(String.format(ActVoiceCallAddGroup.this.getString(R.string.voice_call_group_edite_finish_num), Integer.valueOf(((ActVoiceCallGroupVM) ActVoiceCallAddGroup.this.mViewModel).users != null ? ((ActVoiceCallGroupVM) ActVoiceCallAddGroup.this.mViewModel).users.size() : 0), Integer.valueOf(Math.min(ActVoiceCallAddGroup.this.mAdapter.getData().size(), 31))));
                    ((ActVoiceCallGroupVM) ActVoiceCallAddGroup.this.mViewModel).loadMember();
                    ((ActVoiceCallGroupAddBinding) ActVoiceCallAddGroup.this.mViewBinding).tvDel.setVisibility(8);
                    return;
                }
                ((ActVoiceCallGroupAddBinding) ActVoiceCallAddGroup.this.mViewBinding).tvEdit.setEnabled(true);
                if (((ActVoiceCallGroupVM) ActVoiceCallAddGroup.this.mViewModel).group == null) {
                    ActVoiceCallAddGroup actVoiceCallAddGroup = ActVoiceCallAddGroup.this;
                    actVoiceCallAddGroup.setEditString(actVoiceCallAddGroup.getString(R.string.voice_call_str_save));
                }
                ((ActVoiceCallGroupAddBinding) ActVoiceCallAddGroup.this.mViewBinding).tvEdit.setText(ActVoiceCallAddGroup.this.getString(R.string.voice_call_group_edite));
                ((ActVoiceCallGroupVM) ActVoiceCallAddGroup.this.mViewModel).ids = new ArrayList<>();
                ((ActVoiceCallGroupVM) ActVoiceCallAddGroup.this.mViewModel).users = new ArrayList();
                for (WhiteListUserBean whiteListUserBean : ActVoiceCallAddGroup.this.mAdapter.getData()) {
                    if (whiteListUserBean.isSel() && whiteListUserBean.getMember() != null) {
                        ((ActVoiceCallGroupVM) ActVoiceCallAddGroup.this.mViewModel).ids.add(Long.valueOf(whiteListUserBean.getMember().getUserid()));
                        ((ActVoiceCallGroupVM) ActVoiceCallAddGroup.this.mViewModel).users.add(whiteListUserBean.getMember());
                    }
                }
                ((ActVoiceCallGroupVM) ActVoiceCallAddGroup.this.mViewModel).loadMember();
                if (((ActVoiceCallGroupVM) ActVoiceCallAddGroup.this.mViewModel).group != null) {
                    ((ActVoiceCallGroupAddBinding) ActVoiceCallAddGroup.this.mViewBinding).tvDel.setVisibility(0);
                    ((ActVoiceCallGroupVM) ActVoiceCallAddGroup.this.mViewModel).save();
                }
            }
        });
        ((ActVoiceCallGroupAddBinding) this.mViewBinding).tvSub.setOnClickListener(new AnonymousClass2());
        ((ActVoiceCallGroupAddBinding) this.mViewBinding).tvDel.setOnClickListener(new AnonymousClass3());
    }

    /* renamed from: com.ltech.smarthome.ui.voicecall.group.ActVoiceCallAddGroup$2, reason: invalid class name */
    class AnonymousClass2 implements View.OnClickListener {
        AnonymousClass2() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            EditDialog.asDefault().setContent(((ActVoiceCallGroupAddBinding) ActVoiceCallAddGroup.this.mViewBinding).tvSub.getText().toString()).setTitle(ActVoiceCallAddGroup.this.getString(R.string.voice_call_group_edite)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.voicecall.group.ActVoiceCallAddGroup$2$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActVoiceCallAddGroup.AnonymousClass2.this.lambda$onClick$0((String) obj);
                }
            }).showDialog(ActVoiceCallAddGroup.this.activity);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClick$0(String str) {
            ((ActVoiceCallGroupVM) ActVoiceCallAddGroup.this.mViewModel).changeName(str);
            ((ActVoiceCallGroupAddBinding) ActVoiceCallAddGroup.this.mViewBinding).tvSub.setText(str);
            if (((ActVoiceCallGroupVM) ActVoiceCallAddGroup.this.mViewModel).group != null) {
                ((ActVoiceCallGroupVM) ActVoiceCallAddGroup.this.mViewModel).save();
                ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.voicecall.group.ActVoiceCallAddGroup.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        KeyboardUtils.hideSoftInput(ActVoiceCallAddGroup.this.getWindow());
                    }
                }, 1000L);
            }
        }
    }

    /* renamed from: com.ltech.smarthome.ui.voicecall.group.ActVoiceCallAddGroup$3, reason: invalid class name */
    class AnonymousClass3 implements View.OnClickListener {
        AnonymousClass3() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            MessageDialog.show(ActVoiceCallAddGroup.this.activity, ActVoiceCallAddGroup.this.getString(R.string.tips), ActVoiceCallAddGroup.this.getString(R.string.voice_call_group_del_tip)).setOkButton(ActVoiceCallAddGroup.this.getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.voicecall.group.ActVoiceCallAddGroup$3$$ExternalSyntheticLambda0
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$onClick$0;
                    lambda$onClick$0 = ActVoiceCallAddGroup.AnonymousClass3.this.lambda$onClick$0(baseDialog, view);
                    return lambda$onClick$0;
                }
            }).setCancelButton(ActVoiceCallAddGroup.this.getString(R.string.cancel));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$onClick$0(BaseDialog baseDialog, View view) {
            ((ActVoiceCallGroupVM) ActVoiceCallAddGroup.this.mViewModel).del();
            return false;
        }
    }

    private void initList() {
        ((ActVoiceCallGroupVM) this.mViewModel).panelId = getIntent().getLongExtra("panel_id", 0L);
        ((ActVoiceCallGroupVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, 0L);
        int intExtra = getIntent().getIntExtra(Constants.MAX, 1);
        String stringExtra = getIntent().getStringExtra("voice_call_group");
        VoiceCallGroup voiceCallGroup = (stringExtra == null || TextUtils.isEmpty(stringExtra)) ? null : (VoiceCallGroup) GsonUtils.fromJson(stringExtra, new TypeToken<VoiceCallGroup>(this) { // from class: com.ltech.smarthome.ui.voicecall.group.ActVoiceCallAddGroup.4
        }.getType());
        if (voiceCallGroup != null) {
            setTitle(getString(R.string.voice_call_group_eidt));
            ((ActVoiceCallGroupVM) this.mViewModel).group = voiceCallGroup;
            ((ActVoiceCallGroupVM) this.mViewModel).users = voiceCallGroup.getUserinfoList();
            ((ActVoiceCallGroupVM) this.mViewModel).ids = new ArrayList<>();
            Iterator<VoiceCallMember> it = ((ActVoiceCallGroupVM) this.mViewModel).users.iterator();
            while (it.hasNext()) {
                ((ActVoiceCallGroupVM) this.mViewModel).ids.add(Long.valueOf(it.next().getUserid()));
            }
            ((ActVoiceCallGroupAddBinding) this.mViewBinding).tvSub.setText(voiceCallGroup.getName());
            ((ActVoiceCallGroupAddBinding) this.mViewBinding).tvDel.setVisibility(0);
        } else {
            setTitle(getString(R.string.voice_call_group_add));
            ((ActVoiceCallGroupAddBinding) this.mViewBinding).tvSub.setText(getString(R.string.voice_call_group_multi_group) + intExtra);
            ((ActVoiceCallGroupAddBinding) this.mViewBinding).tvDel.setVisibility(8);
            setEditString(getString(R.string.voice_call_str_save));
        }
        ((ActVoiceCallGroupVM) this.mViewModel).changeName(((ActVoiceCallGroupAddBinding) this.mViewBinding).tvSub.getText().toString());
        ((ActVoiceCallGroupAddBinding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView = ((ActVoiceCallGroupAddBinding) this.mViewBinding).rv;
        BaseQuickAdapter<WhiteListUserBean, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<WhiteListUserBean, BaseViewHolder>(this, R.layout.item_voice_call_white_list_content) { // from class: com.ltech.smarthome.ui.voicecall.group.ActVoiceCallAddGroup.5
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, WhiteListUserBean whiteListUserBean) {
                baseViewHolder.setGone(R.id.iv_selected, whiteListUserBean.isEdit());
                baseViewHolder.setImageResource(R.id.iv_selected, whiteListUserBean.isSel() ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
                if (whiteListUserBean.getMember().getType() == 2) {
                    baseViewHolder.setText(R.id.tv_title, whiteListUserBean.getMember().getFloorname() + " " + whiteListUserBean.getMember().getRoomname());
                    baseViewHolder.setText(R.id.tv_sub, whiteListUserBean.getMember().getUsername()).setGone(R.id.tv_sub, true);
                    switch (whiteListUserBean.getMember().getPaneltype()) {
                        case 2:
                            baseViewHolder.setImageResource(R.id.iv, R.mipmap.contacts_ic_mini);
                            break;
                        case 3:
                            baseViewHolder.setImageResource(R.id.iv, R.mipmap.contacts_ic_4s);
                            break;
                        case 4:
                            baseViewHolder.setImageResource(R.id.iv, R.mipmap.contacts_ic_6s);
                            break;
                        case 5:
                            baseViewHolder.setImageResource(R.id.iv, R.mipmap.contacts_ic_12s);
                            break;
                        case 6:
                            baseViewHolder.setImageResource(R.id.iv, R.mipmap.contacts_ic_pro);
                            break;
                        case 7:
                            baseViewHolder.setImageResource(R.id.iv, R.mipmap.contacts_ic_g4max);
                            break;
                        default:
                            baseViewHolder.setImageResource(R.id.iv, R.mipmap.ic_my_photo_default);
                            break;
                    }
                    return;
                }
                baseViewHolder.setText(R.id.tv_title, whiteListUserBean.getMember().getUsername());
                baseViewHolder.setGone(R.id.tv_sub, false);
                Glide.with(this.mContext).load(whiteListUserBean.getMember().getHeadurl()).placeholder(R.mipmap.ic_my_photo_default).into((ImageView) baseViewHolder.getView(R.id.iv));
            }
        };
        this.mAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.voicecall.group.ActVoiceCallAddGroup.6
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int position) {
                WhiteListUserBean whiteListUserBean = (WhiteListUserBean) ActVoiceCallAddGroup.this.mAdapter.getData().get(position);
                whiteListUserBean.setSel(!whiteListUserBean.isSel());
                ActVoiceCallAddGroup.this.mAdapter.setData(position, whiteListUserBean);
                if (!((ActVoiceCallGroupVM) ActVoiceCallAddGroup.this.mViewModel).edite) {
                    ((ActVoiceCallGroupAddBinding) ActVoiceCallAddGroup.this.mViewBinding).tvEdit.setText(R.string.voice_call_group_edite);
                    ((ActVoiceCallGroupAddBinding) ActVoiceCallAddGroup.this.mViewBinding).tvEdit.setEnabled(true);
                    return;
                }
                Iterator it2 = ActVoiceCallAddGroup.this.mAdapter.getData().iterator();
                int i = 0;
                while (it2.hasNext()) {
                    if (((WhiteListUserBean) it2.next()).isSel()) {
                        i++;
                    }
                }
                if (i > 31) {
                    whiteListUserBean.setSel(false);
                    SmartToast.showCenterShort(ActVoiceCallAddGroup.this.getString(R.string.up_to_32));
                    i = 31;
                }
                ((ActVoiceCallGroupAddBinding) ActVoiceCallAddGroup.this.mViewBinding).tvEdit.setText(String.format(ActVoiceCallAddGroup.this.getString(R.string.voice_call_group_edite_finish_num), Integer.valueOf(i), Integer.valueOf(Math.min(ActVoiceCallAddGroup.this.mAdapter.getData().size(), 31))));
                ((ActVoiceCallGroupAddBinding) ActVoiceCallAddGroup.this.mViewBinding).tvEdit.setEnabled(i >= 2);
            }
        });
        ((ActVoiceCallGroupVM) this.mViewModel).loadMember();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActVoiceCallGroupVM) this.mViewModel).data.observe(this, new Observer<List<WhiteListUserBean>>() { // from class: com.ltech.smarthome.ui.voicecall.group.ActVoiceCallAddGroup.7
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<WhiteListUserBean> whiteListUserBeans) {
                ActVoiceCallAddGroup.this.mAdapter.replaceData(whiteListUserBeans);
                if (!((ActVoiceCallGroupVM) ActVoiceCallAddGroup.this.mViewModel).edite) {
                    ((ActVoiceCallGroupAddBinding) ActVoiceCallAddGroup.this.mViewBinding).tvEdit.setText(R.string.voice_call_group_edite);
                    return;
                }
                Iterator it = ActVoiceCallAddGroup.this.mAdapter.getData().iterator();
                int i = 0;
                while (it.hasNext()) {
                    if (((WhiteListUserBean) it.next()).isSel()) {
                        i++;
                    }
                }
                ((ActVoiceCallGroupAddBinding) ActVoiceCallAddGroup.this.mViewBinding).tvEdit.setText(String.format(ActVoiceCallAddGroup.this.getString(R.string.voice_call_group_edite_finish_num), Integer.valueOf(i), Integer.valueOf(Math.min(ActVoiceCallAddGroup.this.mAdapter.getData().size(), 31))));
                ((ActVoiceCallGroupAddBinding) ActVoiceCallAddGroup.this.mViewBinding).tvEdit.setEnabled(i >= 2);
            }
        });
        ((ActVoiceCallGroupVM) this.mViewModel).result.observe(this, new Observer<Bundle>() { // from class: com.ltech.smarthome.ui.voicecall.group.ActVoiceCallAddGroup.8
            @Override // androidx.lifecycle.Observer
            public void onChanged(Bundle bundle) {
                ActVoiceCallAddGroup.this.setResult(100, new Intent().putExtras(bundle));
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        super.back();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (((ActVoiceCallGroupVM) this.mViewModel).edite) {
            return;
        }
        ((ActVoiceCallGroupVM) this.mViewModel).save();
    }
}