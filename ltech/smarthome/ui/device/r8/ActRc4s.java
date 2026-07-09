package com.ltech.smarthome.ui.device.r8;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActRc4sBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.singleton.ComboCmdHelper;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.eurpanel.EurHelper;
import com.ltech.smarthome.ui.device.gqx.ActGQSelectBleDeviceAndGroupNew;
import com.ltech.smarthome.ui.device.gqx.ActGqSelectScene;
import com.ltech.smarthome.ui.device.r8.ActRc4sVM;
import com.ltech.smarthome.ui.scene.ActGqSelectCgdPro;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActRc4s extends BaseControlActivity<ActRc4sBinding, ActRc4sVM> {
    private static final int TYPE_SCENE = 1;
    private static final int TYPE_SCENE_B8 = 3;
    private static final int TYPE_SCENE_OR_FUNCTION = 2;
    private BaseQuickAdapter<ActRc4sVM.BindContent, BaseViewHolder> bindZoneAdapter;
    private BaseQuickAdapter<ActRc4sVM.BindContent, BaseViewHolder> sceneAdapter;
    private int viewType;

    static /* synthetic */ boolean lambda$showFailBindDialog$6(BaseDialog baseDialog, View view) {
        return false;
    }

    static /* synthetic */ boolean lambda$showFailReSyncDialog$8(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.layout_content;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_rc4s;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        super.showContent();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        initBindZoneRv();
        initRelatedSceneView();
        ((ActRc4sBinding) this.mViewBinding).ivDoubt.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4s.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ActRc4s.this.showPopup();
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActRc4sVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActRc4sVM) this.mViewModel).device = Injection.repo().device().getDeviceById(((ActRc4sVM) this.mViewModel).controlId);
        ((ActRc4sVM) this.mViewModel).controlObject.setValue(((ActRc4sVM) this.mViewModel).device);
        ((ActRc4sVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActRc4sVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ((ActRc4sVM) this.mViewModel).initDefaultList();
        ((ActRc4sVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4s$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActRc4s.this.lambda$startObserve$0(obj);
            }
        });
        ((ActRc4sVM) this.mViewModel).deviceData.observe(this, new Observer<List<ActRc4sVM.BindContent>>() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4s.2
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<ActRc4sVM.BindContent> bindContents) {
                ActRc4s.this.bindZoneAdapter.replaceData(bindContents);
            }
        });
        ((ActRc4sVM) this.mViewModel).sceneData.observe(this, new Observer<List<ActRc4sVM.BindContent>>() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4s.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<ActRc4sVM.BindContent> bindContents) {
                ActRc4s.this.sceneAdapter.replaceData(bindContents);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Object obj) {
        if (obj instanceof Device) {
            setTitle(((Device) obj).getDeviceName());
            ((ActRc4sVM) this.mViewModel).refreshRelateInfoList();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((ActRc4sVM) this.mViewModel).controlObject.setValue(Injection.repo().device().getDeviceById(((ActRc4sVM) this.mViewModel).controlId));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        NavHelper.goSetting(((ActRc4sVM) this.mViewModel).controlObject.getValue());
    }

    private void initRelatedSceneView() {
        if (this.sceneAdapter == null) {
            BaseQuickAdapter<ActRc4sVM.BindContent, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ActRc4sVM.BindContent, BaseViewHolder>(this, R.layout.item_bind_eur_scene) { // from class: com.ltech.smarthome.ui.device.r8.ActRc4s.4
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder holder, ActRc4sVM.BindContent content) {
                    holder.setText(R.id.tv_name, content.getName()).setImageResource(R.id.iv_icon, content.getImg());
                    holder.setGone(R.id.iv_edit, true);
                }
            };
            this.sceneAdapter = baseQuickAdapter;
            baseQuickAdapter.bindToRecyclerView(((ActRc4sBinding) this.mViewBinding).rvScene);
            ((ActRc4sBinding) this.mViewBinding).rvScene.setLayoutManager(new GridLayoutManager(this, 3));
            ((ActRc4sBinding) this.mViewBinding).rvScene.setHasFixedSize(true);
            this.sceneAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4s.5
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    if (i == 0) {
                        ((ActRc4sBinding) ActRc4s.this.mViewBinding).ivClick.setImageResource(R.mipmap.img_lrc4s_click_scene1);
                    } else if (i == 1) {
                        ((ActRc4sBinding) ActRc4s.this.mViewBinding).ivClick.setImageResource(R.mipmap.img_lrc4s_click_scene2);
                    } else if (i == 2) {
                        ((ActRc4sBinding) ActRc4s.this.mViewBinding).ivClick.setImageResource(R.mipmap.img_lrc4s_click_scene3);
                    }
                    if (!ActRc4s.this.isOwnerOrManager()) {
                        ActRc4s.this.showNoPermissionDialog();
                        return;
                    }
                    ActRc4sVM.BindContent bindContent = (ActRc4sVM.BindContent) ActRc4s.this.sceneAdapter.getData().get(i);
                    ((ActRc4sVM) ActRc4s.this.mViewModel).index.setValue(Integer.valueOf(bindContent.getIndex()));
                    if (((ActRc4sVM) ActRc4s.this.mViewModel).isBind(bindContent.getIndex())) {
                        ActRc4s.this.showUnbindDialog(false, bindContent);
                    } else {
                        ActRc4s.this.showBindSceneDialog(bindContent);
                    }
                }
            });
            ((ActRc4sBinding) this.mViewBinding).rvScene.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.r8.ActRc4s.6
                @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.left = ConvertUtils.dp2px(4.5f);
                    outRect.right = ConvertUtils.dp2px(4.5f);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showBindSceneDialog(final ActRc4sVM.BindContent content) {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.local_scene));
        arrayList.add(getString(R.string.dali_scene));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4s$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActRc4s.this.lambda$showBindSceneDialog$1(content, (Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.setDismissRunnable(new Runnable() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4s.7
            @Override // java.lang.Runnable
            public void run() {
                ((ActRc4sBinding) ActRc4s.this.mViewBinding).ivClick.setImageResource(0);
            }
        });
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showBindSceneDialog$1(ActRc4sVM.BindContent bindContent, Integer num) {
        ((ActRc4sVM) this.mViewModel).index.setValue(Integer.valueOf(bindContent.getIndex()));
        int intValue = num.intValue();
        if (intValue == 0) {
            NavUtils.destination(ActGqSelectScene.class).withLong(Constants.PLACE_ID, ((ActRc4sVM) this.mViewModel).getCurPlace().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActRc4sVM) this.mViewModel).controlId).withInt(Constants.RELATED_POSITION, bindContent.getIndex()).withBoolean(Constants.IS_RC4S, true).withBoolean(Constants.GROUP_CONTROL, false).withInt(Constants.SELECT_POSITION, ((ActRc4sVM) this.mViewModel).getSelIndex()).withDefaultRequestCode().navigation(this);
        } else {
            if (intValue != 1) {
                return;
            }
            NavUtils.destination(ActGqSelectCgdPro.class).withLong(Constants.PLACE_ID, ((ActRc4sVM) this.mViewModel).getCurPlace().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActRc4sVM) this.mViewModel).controlId).withBoolean(Constants.GROUP_CONTROL, false).withBoolean(Constants.IS_RC4S, true).withInt(Constants.RELATED_POSITION, bindContent.getIndex()).withInt(Constants.SELECT_POSITION, ((ActRc4sVM) this.mViewModel).getSelIndex()).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showBindDialog(final ActRc4sVM.BindContent content) {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.device));
        arrayList.add(getString(R.string.reset_data));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4s$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActRc4s.this.lambda$showBindDialog$2(content, (Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.setDismissRunnable(new Runnable() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4s.8
            @Override // java.lang.Runnable
            public void run() {
                ((ActRc4sBinding) ActRc4s.this.mViewBinding).ivClick.setImageResource(0);
            }
        });
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showBindDialog$2(ActRc4sVM.BindContent bindContent, Integer num) {
        ((ActRc4sVM) this.mViewModel).index.setValue(Integer.valueOf(bindContent.getIndex()));
        int intValue = num.intValue();
        if (intValue == 0) {
            NavUtils.destination(ActGQSelectBleDeviceAndGroupNew.class).withLong(Constants.PLACE_ID, ((ActRc4sVM) this.mViewModel).getCurPlace().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActRc4sVM) this.mViewModel).controlId).withInt(Constants.RELATED_POSITION, bindContent.getIndex()).withString(Constants.PRODUCT_ID, ((ActRc4sVM) this.mViewModel).productId).withBoolean(Constants.GROUP_CONTROL, false).withBoolean(Constants.IS_RC4S, true).withInt(Constants.GROUP_RELATE, 1).withInt(Constants.LIGHT_TYPE, ((ActRc4sVM) this.mViewModel).controlType).withInt(Constants.SELECT_POSITION, ((ActRc4sVM) this.mViewModel).getSelIndex()).withDefaultRequestCode().navigation(this);
        } else {
            if (intValue != 1) {
                return;
            }
            EurHelper.clearPublishAddress(this.activity, ((ActRc4sVM) this.mViewModel).controlObject.getValue(), ((ActRc4sVM) this.mViewModel).getSelIndex());
            SmartToast.showCenterShort(getString(R.string.encrypt_password_open_success));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showUnbindDialog(boolean isZone, final ActRc4sVM.BindContent content) {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.reset_relate));
        if (isZone) {
            arrayList.add(getString(R.string.customize_name));
            arrayList.add(getString(R.string.app_str_sonos_sync));
        }
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4s$$ExternalSyntheticLambda10
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActRc4s.this.lambda$showUnbindDialog$4(content, (Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
        selectPosition.setDismissRunnable(new Runnable() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4s.10
            @Override // java.lang.Runnable
            public void run() {
                ((ActRc4sBinding) ActRc4s.this.mViewBinding).ivClick.setImageResource(0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnbindDialog$4(ActRc4sVM.BindContent bindContent, Integer num) {
        int intValue = num.intValue();
        if (intValue == 0) {
            ImageTipDialog.asDefault().setTitle(getString(R.string.app_str_rc4s_activate_tip)).setConfirmString(getString(R.string.get_it)).setImageRes(R.mipmap.rc4sble_2).setCallback(new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4s$$ExternalSyntheticLambda9
                @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                    ActRc4s.this.lambda$showUnbindDialog$3(imageTipDialog);
                }
            }).showDialog(this.activity);
        } else if (intValue == 1) {
            EditDialog.asDefault().setContent(bindContent.getName()).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction<String>() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4s.9
                @Override // com.ltech.smarthome.base.IAction
                public void act(String s) {
                    ((ActRc4sVM) ActRc4s.this.mViewModel).changeName(s);
                }
            }).showDialog(this.activity);
        } else {
            if (intValue != 2) {
                return;
            }
            reSyncData();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnbindDialog$3(ImageTipDialog imageTipDialog) {
        imageTipDialog.dismissDialog();
        unbindData();
    }

    private void unbindData() {
        showLoadingDialog("");
        ComboCmdHelper.getInstance().unSubscribeAllCmd(this, ((ActRc4sVM) this.mViewModel).controlObject.getValue(), ((ActRc4sVM) this.mViewModel).getSelIndex(), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4s.11
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                ActRc4s.this.dismissLoadingDialog();
                if (responseMsg != null && responseMsg.getStateCode() == 0) {
                    EurHelper.clearPublishAddress(ActRc4s.this.activity, ((ActRc4sVM) ActRc4s.this.mViewModel).controlObject.getValue(), ((ActRc4sVM) ActRc4s.this.mViewModel).getSelIndex());
                    ((ActRc4sVM) ActRc4s.this.mViewModel).unBindData();
                } else {
                    ActRc4s.this.showFailBindDialog(null, 2);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showFailBindDialog(final Intent data, final int type) {
        MessageDialog.show(this, getString(R.string.app_str_operation_failure), getString(R.string.app_str_binding_fail_tip)).setCancelable(false).setOkButton(getString(R.string.go_continue), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4s$$ExternalSyntheticLambda4
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showFailBindDialog$5;
                lambda$showFailBindDialog$5 = ActRc4s.this.lambda$showFailBindDialog$5(type, data, baseDialog, view);
                return lambda$showFailBindDialog$5;
            }
        }).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4s$$ExternalSyntheticLambda5
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return ActRc4s.lambda$showFailBindDialog$6(baseDialog, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showFailBindDialog$5(int i, Intent intent, BaseDialog baseDialog, View view) {
        if (i == 1) {
            bindData(intent);
            return false;
        }
        if (i != 2) {
            return false;
        }
        unbindData();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showFailReSyncDialog(final int type) {
        MessageDialog.show(this, getString(R.string.app_str_operation_failure), getString(R.string.app_str_binding_fail_tip)).setCancelable(false).setOkButton(getString(R.string.go_continue), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4s$$ExternalSyntheticLambda2
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showFailReSyncDialog$7;
                lambda$showFailReSyncDialog$7 = ActRc4s.this.lambda$showFailReSyncDialog$7(type, baseDialog, view);
                return lambda$showFailReSyncDialog$7;
            }
        }).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4s$$ExternalSyntheticLambda3
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return ActRc4s.lambda$showFailReSyncDialog$8(baseDialog, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showFailReSyncDialog$7(int i, BaseDialog baseDialog, View view) {
        if (i == 1) {
            reSyncData();
            return false;
        }
        if (i != 2) {
            return false;
        }
        unbindData();
        return false;
    }

    private void initBindZoneRv() {
        if (this.bindZoneAdapter == null) {
            BaseQuickAdapter<ActRc4sVM.BindContent, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<ActRc4sVM.BindContent, BaseViewHolder>(this, R.layout.item_bind_zone, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.r8.ActRc4s.12
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, ActRc4sVM.BindContent item) {
                    helper.setText(R.id.tv_name, item.getName());
                    helper.setGone(R.id.iv_edit, true).addOnClickListener(R.id.iv_edit).setGone(R.id.tv_state, false);
                }
            };
            this.bindZoneAdapter = baseQuickAdapter;
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4s.13
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    if (i == 0) {
                        ((ActRc4sBinding) ActRc4s.this.mViewBinding).ivClick.setImageResource(R.mipmap.img_lrc4s_click_zone1);
                    } else if (i == 1) {
                        ((ActRc4sBinding) ActRc4s.this.mViewBinding).ivClick.setImageResource(R.mipmap.img_lrc4s_click_zone2);
                    } else if (i == 2) {
                        ((ActRc4sBinding) ActRc4s.this.mViewBinding).ivClick.setImageResource(R.mipmap.img_lrc4s_click_zone3);
                    } else if (i == 3) {
                        ((ActRc4sBinding) ActRc4s.this.mViewBinding).ivClick.setImageResource(R.mipmap.img_lrc4s_click_zone4);
                    }
                    if (!ActRc4s.this.isOwnerOrManager()) {
                        ActRc4s.this.showNoPermissionDialog();
                        return;
                    }
                    ActRc4sVM.BindContent bindContent = (ActRc4sVM.BindContent) ActRc4s.this.bindZoneAdapter.getData().get(i);
                    ((ActRc4sVM) ActRc4s.this.mViewModel).index.setValue(Integer.valueOf(bindContent.getIndex()));
                    if (((ActRc4sVM) ActRc4s.this.mViewModel).isBind(bindContent.getIndex())) {
                        ActRc4s.this.showUnbindDialog(true, bindContent);
                    } else {
                        ActRc4s.this.showBindDialog(bindContent);
                    }
                }
            });
            this.bindZoneAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4s.14
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemLongClickListener
                public boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    ActRc4s.this.showRc4sTipDialog(i);
                    return false;
                }
            });
            this.bindZoneAdapter.bindToRecyclerView(((ActRc4sBinding) this.mViewBinding).rvMultiBind);
            ((ActRc4sBinding) this.mViewBinding).rvMultiBind.setLayoutManager(new GridLayoutManager(this, 4));
            ((ActRc4sBinding) this.mViewBinding).rvMultiBind.setHasFixedSize(true);
            ((ActRc4sBinding) this.mViewBinding).rvMultiBind.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.r8.ActRc4s.15
                @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.left = ConvertUtils.dp2px(4.5f);
                    outRect.right = ConvertUtils.dp2px(4.5f);
                }
            });
        }
    }

    private void bindData(final Intent data) {
        ImageTipDialog.asDefault().setTitle(getString(R.string.app_str_rc4s_activate_tip)).setConfirmString(getString(R.string.get_it)).setImageRes(R.mipmap.rc4sble_2).setCallback(new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4s$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
            public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                ActRc4s.this.lambda$bindData$9(data, imageTipDialog);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:6:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:9:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$bindData$9(final android.content.Intent r13, final com.ltech.smarthome.view.dialog.ImageTipDialog r14) {
        /*
            r12 = this;
            java.lang.String r0 = "RELATE_ID"
            r1 = -1
            long r0 = r13.getLongExtra(r0, r1)
            java.lang.String r2 = "group_relate"
            r3 = -1
            int r2 = r13.getIntExtra(r2, r3)
            r3 = 2
            java.lang.String r4 = ""
            if (r2 != r3) goto L32
            com.ltech.smarthome.model.Repository r2 = com.ltech.smarthome.model.Injection.repo()
            com.ltech.smarthome.model.repo.ifun.IGroup r2 = r2.group()
            com.ltech.smarthome.model.bean.Group r0 = r2.getGroupById(r0)
            r1 = r0
            com.ltech.smarthome.model.bean.Group r1 = (com.ltech.smarthome.model.bean.Group) r1
            java.lang.String r1 = r0.getName()
            com.ltech.smarthome.model.IComboCmd r2 = com.ltech.smarthome.singleton.ComboCmdHelper.getInstance()
            java.util.List r2 = r2.getDefaultSelfActions(r0)
        L2f:
            r7 = r0
            r9 = r1
            goto L78
        L32:
            r3 = 1
            if (r2 != r3) goto L51
            com.ltech.smarthome.model.Repository r2 = com.ltech.smarthome.model.Injection.repo()
            com.ltech.smarthome.model.repo.ifun.IDevice r2 = r2.device()
            com.ltech.smarthome.model.bean.Device r0 = r2.getDeviceById(r0)
            r1 = r0
            com.ltech.smarthome.model.bean.Device r1 = (com.ltech.smarthome.model.bean.Device) r1
            java.lang.String r1 = r0.getName()
            com.ltech.smarthome.model.IComboCmd r2 = com.ltech.smarthome.singleton.ComboCmdHelper.getInstance()
            java.util.List r2 = r2.getDefaultSelfActions(r0)
            goto L2f
        L51:
            r3 = 3
            if (r2 != r3) goto L74
            com.ltech.smarthome.model.Repository r2 = com.ltech.smarthome.model.Injection.repo()
            com.ltech.smarthome.model.repo.ifun.IScene r2 = r2.scene()
            com.ltech.smarthome.model.bean.Scene r0 = r2.getSceneBySceneId(r0)
            r1 = r0
            com.ltech.smarthome.model.bean.Scene r1 = (com.ltech.smarthome.model.bean.Scene) r1
            java.lang.String r1 = r0.getName()
            com.ltech.smarthome.model.IComboCmd r2 = com.ltech.smarthome.singleton.ComboCmdHelper.getInstance()
            int r3 = r0.getSceneNum()
            java.util.List r2 = r2.getSceneDefaultComboCmdActions(r0, r3)
            goto L2f
        L74:
            r0 = 0
            r2 = r0
            r7 = r2
            r9 = r4
        L78:
            if (r2 == 0) goto La4
            r12.showLoadingDialog(r4)
            com.ltech.smarthome.model.IComboCmd r0 = com.ltech.smarthome.singleton.ComboCmdHelper.getInstance()
            VM extends com.ltech.smarthome.base.BaseViewModel r1 = r12.mViewModel
            com.ltech.smarthome.ui.device.r8.ActRc4sVM r1 = (com.ltech.smarthome.ui.device.r8.ActRc4sVM) r1
            androidx.lifecycle.MutableLiveData<java.lang.Object> r1 = r1.controlObject
            java.lang.Object r1 = r1.getValue()
            VM extends com.ltech.smarthome.base.BaseViewModel r3 = r12.mViewModel
            com.ltech.smarthome.ui.device.r8.ActRc4sVM r3 = (com.ltech.smarthome.ui.device.r8.ActRc4sVM) r3
            int r3 = r3.getSelIndex()
            com.ltech.smarthome.ui.device.r8.ActRc4s$16 r5 = new com.ltech.smarthome.ui.device.r8.ActRc4s$16
            r6 = r12
            r10 = r13
            r8 = r14
            r5.<init>()
            r9 = r2
            r10 = r3
            r11 = r5
            r8 = r7
            r5 = r0
            r7 = r1
            r5.subscribeCmd(r6, r7, r8, r9, r10, r11)
        La4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.r8.ActRc4s.lambda$bindData$9(android.content.Intent, com.ltech.smarthome.view.dialog.ImageTipDialog):void");
    }

    private void reSyncData() {
        ImageTipDialog.asDefault().setTitle(getString(R.string.app_str_rc4s_activate_tip)).setConfirmString(getString(R.string.get_it)).setImageRes(R.mipmap.rc4sble_2).setCallback(new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.r8.ActRc4s$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
            public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                ActRc4s.this.lambda$reSyncData$10(imageTipDialog);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:6:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:9:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$reSyncData$10(final com.ltech.smarthome.view.dialog.ImageTipDialog r10) {
        /*
            r9 = this;
            com.ltech.smarthome.model.IComboCmd r0 = com.ltech.smarthome.singleton.ComboCmdHelper.getInstance()
            VM extends com.ltech.smarthome.base.BaseViewModel r1 = r9.mViewModel
            com.ltech.smarthome.ui.device.r8.ActRc4sVM r1 = (com.ltech.smarthome.ui.device.r8.ActRc4sVM) r1
            int r1 = r1.getSelIndex()
            int r1 = r1 + 1
            java.lang.Object r5 = r0.getRelateObject(r1)
            boolean r0 = r5 instanceof com.ltech.smarthome.model.bean.Group
            java.lang.String r1 = ""
            if (r0 == 0) goto L29
            r0 = r5
            com.ltech.smarthome.model.bean.Group r0 = (com.ltech.smarthome.model.bean.Group) r0
            java.lang.String r0 = r0.getName()
            com.ltech.smarthome.model.IComboCmd r2 = com.ltech.smarthome.singleton.ComboCmdHelper.getInstance()
            java.util.List r2 = r2.getDefaultSelfActions(r5)
        L27:
            r6 = r2
            goto L5a
        L29:
            boolean r0 = r5 instanceof com.ltech.smarthome.model.bean.Device
            if (r0 == 0) goto L3d
            r0 = r5
            com.ltech.smarthome.model.bean.Device r0 = (com.ltech.smarthome.model.bean.Device) r0
            java.lang.String r0 = r0.getName()
            com.ltech.smarthome.model.IComboCmd r2 = com.ltech.smarthome.singleton.ComboCmdHelper.getInstance()
            java.util.List r2 = r2.getDefaultSelfActions(r5)
            goto L27
        L3d:
            boolean r0 = r5 instanceof com.ltech.smarthome.model.bean.Scene
            if (r0 == 0) goto L57
            r0 = r5
            com.ltech.smarthome.model.bean.Scene r0 = (com.ltech.smarthome.model.bean.Scene) r0
            java.lang.String r2 = r0.getName()
            com.ltech.smarthome.model.IComboCmd r3 = com.ltech.smarthome.singleton.ComboCmdHelper.getInstance()
            int r0 = r0.getSceneNum()
            java.util.List r0 = r3.getSceneDefaultComboCmdActions(r5, r0)
            r6 = r0
            r0 = r2
            goto L5a
        L57:
            r2 = 0
            r0 = r1
            goto L27
        L5a:
            if (r6 == 0) goto L7e
            r9.showLoadingDialog(r1)
            com.ltech.smarthome.model.IComboCmd r2 = com.ltech.smarthome.singleton.ComboCmdHelper.getInstance()
            VM extends com.ltech.smarthome.base.BaseViewModel r1 = r9.mViewModel
            com.ltech.smarthome.ui.device.r8.ActRc4sVM r1 = (com.ltech.smarthome.ui.device.r8.ActRc4sVM) r1
            androidx.lifecycle.MutableLiveData<java.lang.Object> r1 = r1.controlObject
            java.lang.Object r4 = r1.getValue()
            VM extends com.ltech.smarthome.base.BaseViewModel r1 = r9.mViewModel
            com.ltech.smarthome.ui.device.r8.ActRc4sVM r1 = (com.ltech.smarthome.ui.device.r8.ActRc4sVM) r1
            int r7 = r1.getSelIndex()
            com.ltech.smarthome.ui.device.r8.ActRc4s$17 r8 = new com.ltech.smarthome.ui.device.r8.ActRc4s$17
            r8.<init>()
            r3 = r9
            r2.subscribeCmd(r3, r4, r5, r6, r7, r8)
        L7e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.r8.ActRc4s.lambda$reSyncData$10(com.ltech.smarthome.view.dialog.ImageTipDialog):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0453  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x047a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void showRc4sTipDialog(int r24) {
        /*
            Method dump skipped, instructions count: 1228
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.r8.ActRc4s.showRc4sTipDialog(int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPopup() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.view_eur_tip_function, (ViewGroup) null);
        ((AppCompatTextView) inflate.findViewById(R.id.tv)).setText(getString(R.string.app_str_rc4s_tip));
        PopupWindow popupWindow = new PopupWindow(inflate, Utils.dip2px(this, 235.0f), -2, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(((ActRc4sBinding) this.mViewBinding).ivDoubt, Utils.dip2px(this, -150.0f), 10);
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseControlActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String str;
        Role role;
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 3001 || data == null) {
            return;
        }
        long longExtra = data.getLongExtra(Constants.RELATE_ID, -1L);
        int intExtra = data.getIntExtra(Constants.GROUP_RELATE, -1);
        if (intExtra == 2) {
            role = Injection.repo().group().getGroupById(longExtra);
            str = role.getName();
        } else if (intExtra == 1) {
            role = Injection.repo().device().getDeviceById(longExtra);
            str = role.getName();
        } else if (intExtra == 3) {
            role = Injection.repo().scene().getSceneBySceneId(longExtra);
            str = role.getName();
        } else {
            str = "";
            role = null;
        }
        int selIndex = ((ActRc4sVM) this.mViewModel).getSelIndex();
        if (selIndex > 3) {
            int selIndex2 = ((ActRc4sVM) this.mViewModel).getSelIndex() - 4;
            List<ActRc4sVM.BindContent> value = ((ActRc4sVM) this.mViewModel).sceneData.getValue();
            if (value == null || value.size() <= selIndex2) {
                return;
            }
            ActRc4sVM.BindContent bindContent = value.get(selIndex2);
            bindContent.setName(str);
            if (role instanceof Role) {
                bindContent.setRole(role);
            }
            value.set(selIndex2, bindContent);
            ((ActRc4sVM) this.mViewModel).sceneData.setValue(value);
            return;
        }
        List<ActRc4sVM.BindContent> value2 = ((ActRc4sVM) this.mViewModel).deviceData.getValue();
        if (value2 == null || value2.size() <= selIndex) {
            return;
        }
        ActRc4sVM.BindContent bindContent2 = value2.get(selIndex);
        bindContent2.setName(str);
        if (role instanceof Role) {
            bindContent2.setRole(role);
        }
        value2.set(selIndex, bindContent2);
        ((ActRc4sVM) this.mViewModel).deviceData.setValue(value2);
    }
}