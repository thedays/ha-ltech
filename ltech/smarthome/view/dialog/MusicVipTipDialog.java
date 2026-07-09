package com.ltech.smarthome.view.dialog;

import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogMusicVipTipBinding;

/* loaded from: classes4.dex */
public class MusicVipTipDialog extends SmartDialog<DialogMusicVipTipBinding> {
    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_music_vip_tip;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.MusicVipTipDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogMusicVipTipBinding, MusicVipTipDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogMusicVipTipBinding viewBinding, final MusicVipTipDialog dialog) {
            viewBinding.tvTip.setText(R.string.music_vip_tip);
            viewBinding.tvConfirm.setText(R.string.ok);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.MusicVipTipDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    MusicVipTipDialog.this.dismiss();
                }
            }));
        }
    }

    public static MusicVipTipDialog asDefault() {
        return (MusicVipTipDialog) new MusicVipTipDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setWidth(-1).setGravity(17);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "music_vip_dialog";
    }
}