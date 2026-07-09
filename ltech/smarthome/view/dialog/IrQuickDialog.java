package com.ltech.smarthome.view.dialog;

import android.view.View;
import android.view.animation.Animation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogIrQuickBinding;
import com.ltech.smarthome.model.device_param.DiyIrParam;
import com.ltech.smarthome.ui.device.ir.IrKeyItem;
import com.ltech.smarthome.ui.device.ir.MotorKeyItem;
import com.ltech.smarthome.ui.device.trig.TrigRepository;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.ltech.smarthome.view.dialog.IrQuickDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class IrQuickDialog<T> extends SmartDialog<DialogIrQuickBinding> {
    private List<T> itemList = new ArrayList();
    private OnDialogCallback<T> mDialogCallback;
    private String title;

    public interface OnDialogCallback<T> {
        void onItemClick(T item);

        void onMoreAction();
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_ir_quick;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.IrQuickDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogIrQuickBinding, IrQuickDialog<IrKeyItem>> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogIrQuickBinding viewBinding, final IrQuickDialog<IrKeyItem> dialog) {
            final Animation.AnimationListener animationListener = new Animation.AnimationListener(this) { // from class: com.ltech.smarthome.view.dialog.IrQuickDialog.1.1
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                    viewBinding.ivSendTip.setBackgroundResource(R.drawable.shape_circle_red);
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    viewBinding.ivSendTip.setBackgroundResource(R.drawable.shape_circle_gray);
                }
            };
            final BaseQuickAdapter<IrKeyItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<IrKeyItem, BaseViewHolder>(this, R.layout.item_ir_key, ((IrQuickDialog) dialog).itemList) { // from class: com.ltech.smarthome.view.dialog.IrQuickDialog.1.2
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, IrKeyItem item) {
                    helper.getView(R.id.layout_bg).getLayoutParams().height = viewBinding.rvContent.getMeasuredHeight();
                    helper.setImageResource(R.id.iv_icon, item.getIconRes()).setText(R.id.tv_name, item.getName());
                    helper.setEnabled(R.id.tv_name, item.isEnable());
                }
            };
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.IrQuickDialog$1$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    IrQuickDialog.AnonymousClass1.lambda$convertView$0(BaseQuickAdapter.this, dialog, animationListener, baseQuickAdapter2, view, i);
                }
            });
            baseQuickAdapter.bindToRecyclerView(viewBinding.rvContent);
            viewBinding.rvContent.setLayoutManager(new GridLayoutManager(dialog.getContext(), ((IrQuickDialog) dialog).itemList.size() > 0 ? ((IrQuickDialog) dialog).itemList.size() : 1));
            viewBinding.rvContent.setHasFixedSize(true);
            ((DefaultItemAnimator) viewBinding.rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
            viewBinding.tvTitle.setText(((IrQuickDialog) dialog).title);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.IrQuickDialog$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    IrQuickDialog.AnonymousClass1.lambda$convertView$1(IrQuickDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(BaseQuickAdapter baseQuickAdapter, IrQuickDialog irQuickDialog, Animation.AnimationListener animationListener, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
            if (((IrKeyItem) baseQuickAdapter.getData().get(i)).isEnable()) {
                if (irQuickDialog.mDialogCallback != null) {
                    irQuickDialog.mDialogCallback.onItemClick((IrKeyItem) baseQuickAdapter.getData().get(i));
                }
                ViewHelpUtil.zoomInZoomOut(view, animationListener);
            }
        }

        static /* synthetic */ void lambda$convertView$1(IrQuickDialog irQuickDialog, View view) {
            if (view.getId() != R.id.iv_device_more) {
                return;
            }
            if (irQuickDialog.mDialogCallback != null) {
                irQuickDialog.mDialogCallback.onMoreAction();
            }
            irQuickDialog.dismissDialog();
        }
    }

    public static IrQuickDialog<IrKeyItem> ir() {
        IrQuickDialog<IrKeyItem> irQuickDialog = new IrQuickDialog<>();
        irQuickDialog.setViewConverter(new AnonymousClass1()).setMargin(30).setGravity(17);
        return irQuickDialog;
    }

    public static IrQuickDialog<MotorKeyItem> motor(boolean isBle) {
        IrQuickDialog<MotorKeyItem> irQuickDialog = new IrQuickDialog<>();
        irQuickDialog.setViewConverter(new AnonymousClass2(isBle)).setMargin(30).setGravity(17);
        return irQuickDialog;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.IrQuickDialog$2, reason: invalid class name */
    class AnonymousClass2 extends SmartDialog.ViewConverter<DialogIrQuickBinding, IrQuickDialog<MotorKeyItem>> {
        final /* synthetic */ boolean val$isBle;

        AnonymousClass2(final boolean val$isBle) {
            this.val$isBle = val$isBle;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogIrQuickBinding viewBinding, final IrQuickDialog<MotorKeyItem> dialog) {
            viewBinding.ivSendTip.setVisibility(this.val$isBle ? 8 : 0);
            final Animation.AnimationListener animationListener = new Animation.AnimationListener(this) { // from class: com.ltech.smarthome.view.dialog.IrQuickDialog.2.1
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                    viewBinding.ivSendTip.setBackgroundResource(R.drawable.shape_circle_red);
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    viewBinding.ivSendTip.setBackgroundResource(R.drawable.shape_circle_gray);
                }
            };
            final BaseQuickAdapter<MotorKeyItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<MotorKeyItem, BaseViewHolder>(this, R.layout.item_ir_key, ((IrQuickDialog) dialog).itemList) { // from class: com.ltech.smarthome.view.dialog.IrQuickDialog.2.2
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, MotorKeyItem item) {
                    helper.getView(R.id.layout_bg).getLayoutParams().height = viewBinding.rvContent.getMeasuredHeight();
                    helper.setImageResource(R.id.iv_icon, item.getIconRes()).setText(R.id.tv_name, item.getName());
                }
            };
            final boolean z = this.val$isBle;
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.IrQuickDialog$2$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    IrQuickDialog.AnonymousClass2.lambda$convertView$0(IrQuickDialog.this, baseQuickAdapter, z, animationListener, baseQuickAdapter2, view, i);
                }
            });
            baseQuickAdapter.bindToRecyclerView(viewBinding.rvContent);
            viewBinding.rvContent.setLayoutManager(new GridLayoutManager(dialog.getContext(), ((IrQuickDialog) dialog).itemList.size() > 0 ? ((IrQuickDialog) dialog).itemList.size() : 1));
            viewBinding.rvContent.setHasFixedSize(true);
            ((DefaultItemAnimator) viewBinding.rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
            viewBinding.tvTitle.setText(((IrQuickDialog) dialog).title);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.IrQuickDialog$2$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    IrQuickDialog.AnonymousClass2.lambda$convertView$1(IrQuickDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(IrQuickDialog irQuickDialog, BaseQuickAdapter baseQuickAdapter, boolean z, Animation.AnimationListener animationListener, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
            if (irQuickDialog.mDialogCallback != null) {
                irQuickDialog.mDialogCallback.onItemClick((MotorKeyItem) baseQuickAdapter.getData().get(i));
            }
            if (z) {
                animationListener = null;
            }
            ViewHelpUtil.zoomInZoomOut(view, animationListener);
        }

        static /* synthetic */ void lambda$convertView$1(IrQuickDialog irQuickDialog, View view) {
            if (view.getId() != R.id.iv_device_more) {
                return;
            }
            if (irQuickDialog.mDialogCallback != null) {
                irQuickDialog.mDialogCallback.onMoreAction();
            }
            irQuickDialog.dismissDialog();
        }
    }

    public static IrQuickDialog<TrigRepository.TrigItem> dryCurtain(boolean isBle) {
        IrQuickDialog<TrigRepository.TrigItem> irQuickDialog = new IrQuickDialog<>();
        irQuickDialog.setViewConverter(new AnonymousClass3(isBle)).setMargin(30).setGravity(17);
        return irQuickDialog;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.IrQuickDialog$3, reason: invalid class name */
    class AnonymousClass3 extends SmartDialog.ViewConverter<DialogIrQuickBinding, IrQuickDialog<TrigRepository.TrigItem>> {
        final /* synthetic */ boolean val$isBle;

        AnonymousClass3(final boolean val$isBle) {
            this.val$isBle = val$isBle;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogIrQuickBinding viewBinding, final IrQuickDialog<TrigRepository.TrigItem> dialog) {
            viewBinding.ivSendTip.setVisibility(this.val$isBle ? 8 : 0);
            final Animation.AnimationListener animationListener = new Animation.AnimationListener(this) { // from class: com.ltech.smarthome.view.dialog.IrQuickDialog.3.1
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                    viewBinding.ivSendTip.setBackgroundResource(R.drawable.shape_circle_red);
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    viewBinding.ivSendTip.setBackgroundResource(R.drawable.shape_circle_gray);
                }
            };
            final BaseQuickAdapter<TrigRepository.TrigItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<TrigRepository.TrigItem, BaseViewHolder>(this, R.layout.item_ir_key, ((IrQuickDialog) dialog).itemList) { // from class: com.ltech.smarthome.view.dialog.IrQuickDialog.3.2
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, TrigRepository.TrigItem item) {
                    helper.getView(R.id.layout_bg).getLayoutParams().height = viewBinding.rvContent.getMeasuredHeight();
                    helper.setImageResource(R.id.iv_icon, item.bgRes).setText(R.id.tv_name, item.name);
                }
            };
            final boolean z = this.val$isBle;
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.IrQuickDialog$3$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    IrQuickDialog.AnonymousClass3.lambda$convertView$0(IrQuickDialog.this, baseQuickAdapter, z, animationListener, baseQuickAdapter2, view, i);
                }
            });
            baseQuickAdapter.bindToRecyclerView(viewBinding.rvContent);
            viewBinding.rvContent.setLayoutManager(new GridLayoutManager(dialog.getContext(), ((IrQuickDialog) dialog).itemList.size() > 0 ? ((IrQuickDialog) dialog).itemList.size() : 1));
            viewBinding.rvContent.setHasFixedSize(true);
            ((DefaultItemAnimator) viewBinding.rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
            viewBinding.tvTitle.setText(((IrQuickDialog) dialog).title);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.IrQuickDialog$3$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    IrQuickDialog.AnonymousClass3.lambda$convertView$1(IrQuickDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(IrQuickDialog irQuickDialog, BaseQuickAdapter baseQuickAdapter, boolean z, Animation.AnimationListener animationListener, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
            if (irQuickDialog.mDialogCallback != null) {
                irQuickDialog.mDialogCallback.onItemClick((TrigRepository.TrigItem) baseQuickAdapter.getData().get(i));
            }
            if (z) {
                animationListener = null;
            }
            ViewHelpUtil.zoomInZoomOut(view, animationListener);
        }

        static /* synthetic */ void lambda$convertView$1(IrQuickDialog irQuickDialog, View view) {
            if (view.getId() != R.id.iv_device_more) {
                return;
            }
            if (irQuickDialog.mDialogCallback != null) {
                irQuickDialog.mDialogCallback.onMoreAction();
            }
            irQuickDialog.dismissDialog();
        }
    }

    public static IrQuickDialog<DiyIrParam.DiyIrKey> diy() {
        IrQuickDialog<DiyIrParam.DiyIrKey> irQuickDialog = new IrQuickDialog<>();
        irQuickDialog.setViewConverter(new AnonymousClass4()).setMargin(30).setGravity(17);
        return irQuickDialog;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.IrQuickDialog$4, reason: invalid class name */
    class AnonymousClass4 extends SmartDialog.ViewConverter<DialogIrQuickBinding, IrQuickDialog<DiyIrParam.DiyIrKey>> {
        AnonymousClass4() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogIrQuickBinding viewBinding, final IrQuickDialog<DiyIrParam.DiyIrKey> dialog) {
            final Animation.AnimationListener animationListener = new Animation.AnimationListener(this) { // from class: com.ltech.smarthome.view.dialog.IrQuickDialog.4.1
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                    viewBinding.ivSendTip.setBackgroundResource(R.drawable.shape_circle_red);
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    viewBinding.ivSendTip.setBackgroundResource(R.drawable.shape_circle_gray);
                }
            };
            final BaseQuickAdapter<DiyIrParam.DiyIrKey, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<DiyIrParam.DiyIrKey, BaseViewHolder>(this, R.layout.item_ir_diy_key, ((IrQuickDialog) dialog).itemList) { // from class: com.ltech.smarthome.view.dialog.IrQuickDialog.4.2
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, DiyIrParam.DiyIrKey item) {
                    helper.getView(R.id.layout_bg).getLayoutParams().height = viewBinding.rvContent.getMeasuredHeight();
                    helper.setText(R.id.tv_name, item.getKeyName());
                }
            };
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.view.dialog.IrQuickDialog$4$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    IrQuickDialog.AnonymousClass4.lambda$convertView$0(IrQuickDialog.this, baseQuickAdapter, animationListener, baseQuickAdapter2, view, i);
                }
            });
            baseQuickAdapter.bindToRecyclerView(viewBinding.rvContent);
            viewBinding.rvContent.setLayoutManager(new GridLayoutManager(dialog.getContext(), ((IrQuickDialog) dialog).itemList.size() < 3 ? ((IrQuickDialog) dialog).itemList.size() : 3));
            viewBinding.rvContent.setHasFixedSize(true);
            ((DefaultItemAnimator) viewBinding.rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
            viewBinding.tvTitle.setText(((IrQuickDialog) dialog).title);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.IrQuickDialog$4$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    IrQuickDialog.AnonymousClass4.lambda$convertView$1(IrQuickDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(IrQuickDialog irQuickDialog, BaseQuickAdapter baseQuickAdapter, Animation.AnimationListener animationListener, BaseQuickAdapter baseQuickAdapter2, View view, int i) {
            if (irQuickDialog.mDialogCallback != null) {
                irQuickDialog.mDialogCallback.onItemClick((DiyIrParam.DiyIrKey) baseQuickAdapter.getData().get(i));
            }
            ViewHelpUtil.zoomInZoomOut(view, animationListener);
        }

        static /* synthetic */ void lambda$convertView$1(IrQuickDialog irQuickDialog, View view) {
            if (view.getId() != R.id.iv_device_more) {
                return;
            }
            if (irQuickDialog.mDialogCallback != null) {
                irQuickDialog.mDialogCallback.onMoreAction();
            }
            irQuickDialog.dismissDialog();
        }
    }

    public IrQuickDialog<T> setTitle(String title) {
        this.title = title;
        return this;
    }

    public IrQuickDialog<T> setList(List<T> list) {
        this.itemList.clear();
        this.itemList.addAll(list);
        return this;
    }

    public IrQuickDialog<T> setDialogCallback(OnDialogCallback<T> mDialogCallback) {
        this.mDialogCallback = mDialogCallback;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "ir_quick_dialog";
    }
}