package com.ltech.smarthome.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import com.ltech.imageclip.ClipImageActivity;
import com.ltech.imageclip.fragment.ActivityResultHelper;
import com.ltech.imageclip.util.FileUtil;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.databinding.ActUserInfoBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.User;
import com.ltech.smarthome.net.api.ApiConstants;
import com.ltech.smarthome.singleton.PathManager;
import com.ltech.smarthome.ui.login.ActChangeEmail;
import com.ltech.smarthome.ui.login.ActChangePhone;
import com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.VersionUtils;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActUserInfo extends VMActivity<ActUserInfoBinding, ActUserInfoVM> {
    private static final int REQUEST_CAPTURE = 100;
    private static final int REQUEST_PICK = 101;
    private File file;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_user_info;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.user_info));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        User userByDb = Injection.repo().user().getUserByDb();
        if (userByDb != null) {
            bindUser(userByDb);
        }
        ((ActUserInfoVM) this.mViewModel).userLising = Injection.repo().user().getUserInfo(this);
        handleData(((ActUserInfoVM) this.mViewModel).userLising, new IAction() { // from class: com.ltech.smarthome.ui.user.ActUserInfo$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActUserInfo.this.lambda$startObserve$0((List) obj);
            }
        });
        ((ActUserInfoVM) this.mViewModel).showEditDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.user.ActUserInfo$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActUserInfo.this.showEditDialog((String) obj);
            }
        });
        ((ActUserInfoVM) this.mViewModel).showRemoveTipDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.user.ActUserInfo$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActUserInfo.this.lambda$startObserve$1((Void) obj);
            }
        });
        ((ActUserInfoVM) this.mViewModel).showPicDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.user.ActUserInfo$$ExternalSyntheticLambda9
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActUserInfo.this.lambda$startObserve$2((Void) obj);
            }
        });
        ((ActUserInfoVM) this.mViewModel).showLogoutDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.user.ActUserInfo$$ExternalSyntheticLambda10
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActUserInfo.this.lambda$startObserve$3((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(List list) {
        if (list.size() > 0) {
            bindUser((User) list.get(0));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r1) {
        showRemoveTipDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r1) {
        showHeadDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r1) {
        showLogoutTipDialog();
    }

    private void bindUser(final User user) {
        if (((ActUserInfoVM) this.mViewModel).goItemObservableList.size() > 0) {
            ((ActUserInfoVM) this.mViewModel).goItemObservableList.set(((ActUserInfoVM) this.mViewModel).posUserHead, ((ActUserInfoVM) this.mViewModel).goItemObservableList.get(((ActUserInfoVM) this.mViewModel).posUserHead).setImageUrl(user.getHeadUrl()));
        }
        if (((ActUserInfoVM) this.mViewModel).goItemObservableList.size() > 1) {
            ((ActUserInfoVM) this.mViewModel).goItemObservableList.set(((ActUserInfoVM) this.mViewModel).posUserName, ((ActUserInfoVM) this.mViewModel).goItemObservableList.get(((ActUserInfoVM) this.mViewModel).posUserName).setSubText(user.getUserName()));
        }
        if (((ActUserInfoVM) this.mViewModel).goItemObservableList.size() > 2) {
            ((ActUserInfoVM) this.mViewModel).goItemObservableList.set(((ActUserInfoVM) this.mViewModel).posPhone, ((ActUserInfoVM) this.mViewModel).goItemObservableList.get(((ActUserInfoVM) this.mViewModel).posPhone).setSubText(TextUtils.isEmpty(user.getMobilePhone()) ? getString(R.string.no_bind) : user.getMobilePhone()));
            if (ApiConstants.isChinaNode()) {
                ((ActUserInfoVM) this.mViewModel).goItemObservableList.set(((ActUserInfoVM) this.mViewModel).posPhone, ((ActUserInfoVM) this.mViewModel).goItemObservableList.get(((ActUserInfoVM) this.mViewModel).posPhone).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.user.ActUserInfo$$ExternalSyntheticLambda4
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActUserInfo.this.lambda$bindUser$4(user);
                    }
                })));
            } else {
                ((ActUserInfoVM) this.mViewModel).goItemObservableList.set(((ActUserInfoVM) this.mViewModel).posPhone, ((ActUserInfoVM) this.mViewModel).goItemObservableList.get(((ActUserInfoVM) this.mViewModel).posPhone).setColor(-3355444).setGoRes(0));
            }
        }
        if (((ActUserInfoVM) this.mViewModel).goItemObservableList.size() > 3) {
            ((ActUserInfoVM) this.mViewModel).goItemObservableList.set(((ActUserInfoVM) this.mViewModel).posEmail, ((ActUserInfoVM) this.mViewModel).goItemObservableList.get(((ActUserInfoVM) this.mViewModel).posEmail).setSubText(TextUtils.isEmpty(user.getEmail()) ? getString(R.string.no_bind) : user.getEmail())).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.user.ActUserInfo$$ExternalSyntheticLambda5
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActUserInfo.this.lambda$bindUser$5(user);
                }
            }));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindUser$4(User user) {
        ((ActUserInfoVM) this.mViewModel).navigation(NavUtils.destination(ActChangePhone.class).withString(Constants.USER_ACCOUNT, user.getMobilePhone()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindUser$5(User user) {
        ((ActUserInfoVM) this.mViewModel).navigation(NavUtils.destination(ActChangeEmail.class).withString(Constants.USER_ACCOUNT, user.getEmail()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEditDialog(String content) {
        EditDialog.asDefault().setContent(content).setTitle(getString(R.string.user_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.user.ActUserInfo$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActUserInfo.this.lambda$showEditDialog$6((String) obj);
            }
        }).showDialog(getSupportFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditDialog$6(String str) {
        if (TextUtils.isEmpty(str)) {
            SmartToast.showShort(getString(R.string.input_name));
        } else {
            ((ActUserInfoVM) this.mViewModel).updateName(str);
        }
    }

    private void showRemoveTipDialog() {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.remove_account_tip)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.user.ActUserInfo$$ExternalSyntheticLambda13
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showRemoveTipDialog$7;
                lambda$showRemoveTipDialog$7 = ActUserInfo.this.lambda$showRemoveTipDialog$7(baseDialog, view);
                return lambda$showRemoveTipDialog$7;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showRemoveTipDialog$7(BaseDialog baseDialog, View view) {
        ((ActUserInfoVM) this.mViewModel).removeUser();
        return false;
    }

    private void showLogoutTipDialog() {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.logout_tip)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.user.ActUserInfo$$ExternalSyntheticLambda15
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showLogoutTipDialog$8;
                lambda$showLogoutTipDialog$8 = ActUserInfo.this.lambda$showLogoutTipDialog$8(baseDialog, view);
                return lambda$showLogoutTipDialog$8;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showLogoutTipDialog$8(BaseDialog baseDialog, View view) {
        showLoadingDialog("");
        Injection.logout();
        VoiceCallManager.getInstance().logout();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        showContent();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        ((ActUserInfoVM) this.mViewModel).userLising.retry();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (101 == requestCode) {
            if (resultCode != -1 || data == null) {
                return;
            }
            gotoClipActivity(data.getData());
            return;
        }
        if (100 == requestCode && resultCode == -1) {
            gotoClipActivity(Uri.fromFile(this.file));
        }
    }

    private void showHeadDialog() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.camera));
        arrayList.add(getString(R.string.select_pic));
        SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.user.ActUserInfo$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActUserInfo.this.lambda$showHeadDialog$9((Integer) obj);
            }
        }).setSelectList(arrayList).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showHeadDialog$9(Integer num) {
        if (num.intValue() == 1) {
            goPic();
        } else {
            goCamera();
        }
    }

    private void goPic() {
        if (VersionUtils.isAndroidQ()) {
            pickPic();
        } else {
            AndPermission.with((Activity) this).runtime().permission(Permission.READ_EXTERNAL_STORAGE).onGranted(new Action() { // from class: com.ltech.smarthome.ui.user.ActUserInfo$$ExternalSyntheticLambda1
                @Override // com.yanzhenjie.permission.Action
                public final void onAction(Object obj) {
                    ActUserInfo.this.lambda$goPic$10((List) obj);
                }
            }).onDenied(new Action() { // from class: com.ltech.smarthome.ui.user.ActUserInfo$$ExternalSyntheticLambda2
                @Override // com.yanzhenjie.permission.Action
                public final void onAction(Object obj) {
                    ActUserInfo.this.lambda$goPic$11((List) obj);
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$goPic$10(List list) {
        pickPic();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$goPic$11(List list) {
        SmartToast.showShort(getString(R.string.permission_deny));
    }

    private void pickPic() {
        startActivityForResult(Intent.createChooser(new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI), getString(R.string.choose_pic)), 101);
    }

    private void goCamera() {
        String[] strArr;
        if (VersionUtils.isAndroidQ()) {
            strArr = new String[]{Permission.CAMERA};
        } else {
            strArr = new String[]{Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE, Permission.CAMERA};
        }
        AndPermission.with((Activity) this).runtime().permission(strArr).onGranted(new Action() { // from class: com.ltech.smarthome.ui.user.ActUserInfo$$ExternalSyntheticLambda11
            @Override // com.yanzhenjie.permission.Action
            public final void onAction(Object obj) {
                ActUserInfo.this.lambda$goCamera$12((List) obj);
            }
        }).onDenied(new Action() { // from class: com.ltech.smarthome.ui.user.ActUserInfo$$ExternalSyntheticLambda12
            @Override // com.yanzhenjie.permission.Action
            public final void onAction(Object obj) {
                ActUserInfo.this.lambda$goCamera$13((List) obj);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$goCamera$12(List list) {
        this.file = new File(PathManager.getCacheDir(this), PathManager.getHeadPicName(String.valueOf(Injection.repo().user().getUserId())) + ".jpg");
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        Uri uriForFile = FileProvider.getUriForFile(this, "com.ltech.smarthome.provider", this.file);
        intent.addFlags(1);
        intent.putExtra("output", uriForFile);
        startActivityForResult(intent, 100);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$goCamera$13(List list) {
        SmartToast.showShort(getString(R.string.permission_deny));
    }

    private void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        ClipImageActivity.goToClipActivity(this, uri, PathManager.getHeadPicName(String.valueOf(Injection.repo().user().getUserId())), new ActivityResultHelper.Callback() { // from class: com.ltech.smarthome.ui.user.ActUserInfo$$ExternalSyntheticLambda14
            @Override // com.ltech.imageclip.fragment.ActivityResultHelper.Callback
            public final void onActivityResult(int i, Intent intent) {
                ActUserInfo.this.lambda$gotoClipActivity$14(i, intent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$gotoClipActivity$14(int i, Intent intent) {
        Uri data;
        if (i != -1 || (data = intent.getData()) == null) {
            return;
        }
        ((ActUserInfoVM) this.mViewModel).updateUserHead(FileUtil.getRealFilePathFromUri(getApplicationContext(), data));
    }
}