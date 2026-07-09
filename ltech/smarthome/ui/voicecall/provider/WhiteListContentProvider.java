package com.ltech.smarthome.ui.voicecall.provider;

import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.zhuhai.ltech.lt_voice_call_api.bean.VoiceCallMember;
import com.zhuhai.ltech.lt_voice_call_api.bean.WhiteListUserBean;

/* loaded from: classes4.dex */
public class WhiteListContentProvider extends BaseItemProvider<WhiteListUserBean, BaseViewHolder> {
    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_voice_call_white_list_content;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 1;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(BaseViewHolder baseViewHolder, WhiteListUserBean whiteListUserBean, int position) {
        int i;
        int i2;
        if (whiteListUserBean.getMember() != null) {
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
            } else {
                baseViewHolder.setText(R.id.tv_title, whiteListUserBean.getMember().getUsername());
                baseViewHolder.setGone(R.id.tv_sub, false);
                Glide.with(this.mContext).load(whiteListUserBean.getMember().getHeadurl()).placeholder(R.mipmap.ic_my_photo_default).into((ImageView) baseViewHolder.getView(R.id.iv));
            }
        } else if (whiteListUserBean.getGroup() != null) {
            baseViewHolder.setText(R.id.tv_title, whiteListUserBean.getGroup().getName());
            if (whiteListUserBean.getGroup().getUserinfoList() != null) {
                i = 0;
                i2 = 0;
                for (VoiceCallMember voiceCallMember : whiteListUserBean.getGroup().getUserinfoList()) {
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
            baseViewHolder.setText(R.id.tv_sub, String.format(this.mContext.getString(R.string.voice_call_add_group_sub), Integer.valueOf(i), Integer.valueOf(i2)));
            baseViewHolder.setImageResource(R.id.iv, R.mipmap.contacts_ic_group);
        }
        baseViewHolder.setImageResource(R.id.iv_selected, whiteListUserBean.isSel() ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
    }
}