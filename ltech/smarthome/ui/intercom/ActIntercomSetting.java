package com.ltech.smarthome.ui.intercom;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActIntercomSettingBinding;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.yanzhenjie.permission.runtime.Permission;
import java.util.ArrayList;
import no.nordicsemi.android.log.LogContract;

/* loaded from: classes4.dex */
public class ActIntercomSetting extends VMActivity<ActIntercomSettingBinding, ActIntercomSettingVM> {
    private final int CAMERA_PERMISSION_CODE = 6666;
    private final int CONTACTS_PERMISSION_CODE = 8888;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_intercom_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.setting));
        ((ActIntercomSettingVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        if (checkContactsPermission()) {
            if (isContactExists(this, Constants.XIAO_LEI_NAME, Constants.XIAO_LEI_PHONE)) {
                ((ActIntercomSettingVM) this.mViewModel).isExistContact.setValue(true);
                return;
            } else {
                ((ActIntercomSettingVM) this.mViewModel).isExistContact.setValue(false);
                return;
            }
        }
        ((ActIntercomSettingVM) this.mViewModel).isExistContact.setValue(false);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((ActIntercomSettingVM) this.mViewModel).getData();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActIntercomSettingVM) this.mViewModel).keyText.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSetting$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActIntercomSetting.this.lambda$startObserve$0((String) obj);
            }
        });
        ((ActIntercomSettingVM) this.mViewModel).faceStatusEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSetting$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActIntercomSetting.this.lambda$startObserve$1((Integer) obj);
            }
        });
        ((ActIntercomSettingVM) this.mViewModel).isExistContact.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSetting$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActIntercomSetting.this.lambda$startObserve$2((Boolean) obj);
            }
        });
        ((ActIntercomSettingVM) this.mViewModel).clickFaceLayoutEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSetting$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActIntercomSetting.this.lambda$startObserve$3((Void) obj);
            }
        });
        ((ActIntercomSettingVM) this.mViewModel).writeAddressEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercomSetting$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActIntercomSetting.this.lambda$startObserve$4((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(String str) {
        ((ActIntercomSettingBinding) this.mViewBinding).tvUnlock.setText(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Integer num) {
        if (num.intValue() == 0) {
            ((ActIntercomSettingBinding) this.mViewBinding).tvFace.setText(getString(R.string.super_key_no));
        } else {
            ((ActIntercomSettingBinding) this.mViewBinding).tvFace.setText(getString(R.string.already_set));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Boolean bool) {
        ((ActIntercomSettingBinding) this.mViewBinding).tvWriteAddressStatus.setText(getString(bool.booleanValue() ? R.string.already_write : R.string.not_write));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r3) {
        if (checkPhotoPermission()) {
            NavUtils.destination(ActIntercomSetFace.class).withInt(Constants.FACE_STATUS, ((ActIntercomSettingVM) this.mViewModel).faceStatus).navigation(ActivityUtils.getTopActivity());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r2) {
        if (checkContactsPermission()) {
            addContact(this, Constants.XIAO_LEI_NAME, Constants.XIAO_LEI_PHONE);
            ((ActIntercomSettingVM) this.mViewModel).isExistContact.setValue(true);
            SmartToast.showCenterShort(getString(R.string.write_success));
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String stringExtra;
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != -1 || data == null || (stringExtra = data.getStringExtra(Constants.OPEN_KEY)) == null || stringExtra.isEmpty()) {
            return;
        }
        ((ActIntercomSettingVM) this.mViewModel).key = stringExtra;
        ((ActIntercomSettingVM) this.mViewModel).keyText.setValue(stringExtra);
    }

    private boolean checkContactsPermission() {
        int checkSelfPermission = ContextCompat.checkSelfPermission(this, Permission.READ_CONTACTS);
        int checkSelfPermission2 = ContextCompat.checkSelfPermission(this, Permission.WRITE_CONTACTS);
        if (checkSelfPermission == 0 && checkSelfPermission2 == 0) {
            return true;
        }
        ActivityCompat.requestPermissions(this, new String[]{Permission.READ_CONTACTS, Permission.WRITE_CONTACTS}, 8888);
        return false;
    }

    private boolean checkPhotoPermission() {
        int checkSelfPermission;
        if (Build.VERSION.SDK_INT >= 23) {
            checkSelfPermission = checkSelfPermission(Permission.CAMERA);
            if (checkSelfPermission != 0) {
                ActivityCompat.requestPermissions(this, new String[]{Permission.CAMERA}, 6666);
                return false;
            }
        }
        return true;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 6666) {
            NavUtils.destination(ActIntercomSetFace.class).navigation(ActivityUtils.getTopActivity());
        } else if (requestCode == 8888) {
            ((ActIntercomSettingVM) this.mViewModel).isExistContact.setValue(Boolean.valueOf(isContactExists(this, Constants.XIAO_LEI_NAME, Constants.XIAO_LEI_PHONE)));
        }
    }

    public void addContact(Context context, String name, String phone) {
        ArrayList<ContentProviderOperation> arrayList = new ArrayList<>();
        int size = arrayList.size();
        arrayList.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI).withValue("account_type", null).withValue("account_name", null).build());
        arrayList.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference("raw_contact_id", size).withValue("mimetype", "vnd.android.cursor.item/name").withValue("data1", name).build());
        arrayList.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference("raw_contact_id", size).withValue("mimetype", "vnd.android.cursor.item/phone_v2").withValue("data1", phone).withValue("data2", 2).build());
        try {
            context.getContentResolver().applyBatch("com.android.contacts", arrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isContactExists(Context context, String name, String phone) {
        String string;
        Cursor query = context.getContentResolver().query(Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phone)), new String[]{"display_name", LogContract.SessionColumns.NUMBER}, null, null, null);
        if (query != null) {
            try {
                if (query.moveToFirst() && (string = query.getString(Math.max(query.getColumnIndex("display_name"), 0))) != null) {
                    if (string.equals(name)) {
                        if (query != null) {
                            query.close();
                        }
                        return true;
                    }
                }
            } finally {
            }
        }
        if (query != null) {
            query.close();
        }
        return false;
    }
}