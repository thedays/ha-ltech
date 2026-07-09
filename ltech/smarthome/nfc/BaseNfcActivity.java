package com.ltech.smarthome.nfc;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcV;
import android.os.Build;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import androidx.databinding.ViewDataBinding;
import com.ltech.nfc.source.NetInfo;
import com.ltech.nfc.source.SourceInfo;
import com.ltech.nfc.utils.CardManager;
import com.ltech.nfc.utils.DataUtil;
import com.ltech.nfc.utils.NfcVUtil;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.nfc.WriteVirtualHelper;
import com.ltech.smarthome.nfc.dialog.ResultDialog;
import com.ltech.smarthome.nfc.dialog.ScanNfcDialog;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.utils.LHomeLog;
import java.io.IOException;

/* loaded from: classes4.dex */
public abstract class BaseNfcActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends VMActivity<V, VM> {
    protected static final String NFC_ACTION = "android.nfc.action.TECH_DISCOVERED";
    protected boolean batchWrite;
    protected boolean canRead;
    protected ScanNfcDialog dialog;
    protected NfcAdapter nfcAdapter;
    protected NfcVUtil nfcVUtil;
    protected PendingIntent pendingIntent;
    protected int[] readValue;
    protected OnNfcWriteCallback writeCallback;

    public interface OnNfcWriteCallback {
        void onFinish(boolean result);
    }

    static /* synthetic */ boolean lambda$checkNfcStatus$2(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        this.nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (Build.VERSION.SDK_INT < 31) {
            this.pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(536870912), 0);
        } else {
            this.pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(536870912), 33554432);
        }
        NfcAdapter nfcAdapter = this.nfcAdapter;
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, this.pendingIntent, CardManager.FILTERS, CardManager.TECHLISTS);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        NfcAdapter nfcAdapter = this.nfcAdapter;
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        Parcelable parcelableExtra;
        super.onNewIntent(intent);
        if (!NFC_ACTION.equals(intent.getAction()) || (parcelableExtra = intent.getParcelableExtra("android.nfc.extra.TAG")) == null) {
            return;
        }
        readNfcData((Tag) parcelableExtra);
    }

    public void showNfcDialog(OnNfcWriteCallback writeCallback) {
        this.writeCallback = writeCallback;
        if (checkNfcStatus()) {
            ScanNfcDialog onDialogCallback = ScanNfcDialog.asDefault().setOnDialogCallback(new ScanNfcDialog.OnDialogCallback() { // from class: com.ltech.smarthome.nfc.BaseNfcActivity$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.nfc.dialog.ScanNfcDialog.OnDialogCallback
                public final void onDismiss() {
                    BaseNfcActivity.this.lambda$showNfcDialog$0();
                }
            });
            this.dialog = onDialogCallback;
            onDialogCallback.showDialog(this);
            this.canRead = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showNfcDialog$0() {
        this.canRead = false;
    }

    public void showNfcDialog() {
        showNfcDialog(null);
    }

    public boolean checkNfcStatus() {
        NfcAdapter nfcAdapter = this.nfcAdapter;
        if (nfcAdapter == null) {
            SmartToast.showShort(R.string.tip_nfc_notfound);
            return false;
        }
        if (nfcAdapter.isEnabled()) {
            return true;
        }
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.open_nfc_tip)).setCancelable(false).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.nfc.BaseNfcActivity$$ExternalSyntheticLambda2
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$checkNfcStatus$1;
                lambda$checkNfcStatus$1 = BaseNfcActivity.this.lambda$checkNfcStatus$1(baseDialog, view);
                return lambda$checkNfcStatus$1;
            }
        }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.nfc.BaseNfcActivity$$ExternalSyntheticLambda3
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return BaseNfcActivity.lambda$checkNfcStatus$2(baseDialog, view);
            }
        });
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$checkNfcStatus$1(BaseDialog baseDialog, View view) {
        this.activity.startActivity(new Intent("android.settings.NFC_SETTINGS"));
        return false;
    }

    protected void readNfcData(Tag tag) {
        try {
            final NfcV nfcV = NfcV.get(tag);
            nfcV.connect();
            NfcVUtil nfcVUtil = new NfcVUtil(nfcV);
            this.nfcVUtil = nfcVUtil;
            String readBlocks = nfcVUtil.readBlocks(0, 30);
            String readBlocks2 = this.nfcVUtil.readBlocks(40, 14);
            if (TextUtils.isEmpty(readBlocks) || TextUtils.isEmpty(readBlocks2)) {
                return;
            }
            this.readValue = convertData(readBlocks2);
            SourceInfo sourceInfo = new SourceInfo(this.readValue, convertData(readBlocks));
            this.dialog.dismissDialog();
            int isDeviceMatch = WriteVirtualHelper.instance().isDeviceMatch(sourceInfo);
            String str = "";
            if (isDeviceMatch == 4) {
                str = getString(R.string.type_not_match);
            } else if (isDeviceMatch == 5) {
                str = getString(R.string.mac_not_match);
            } else if (isDeviceMatch == 6) {
                str = getString(R.string.mac_not_valid);
            } else if (!sourceInfo.isValid()) {
                str = getString(R.string.cmd_firmware_need_upgrade);
            } else if (WriteVirtualHelper.instance().isEnterNet() && sourceInfo.getNetFlag() == 1 && !this.batchWrite) {
                str = getString(R.string.device_in_net);
            }
            if (str.isEmpty()) {
                WriteVirtualHelper.instance().setCallBack(new WriteVirtualHelper.onWriteCallBack() { // from class: com.ltech.smarthome.nfc.BaseNfcActivity$$ExternalSyntheticLambda4
                    @Override // com.ltech.smarthome.nfc.WriteVirtualHelper.onWriteCallBack
                    public final void onFinish(int i) {
                        BaseNfcActivity.this.lambda$readNfcData$3(nfcV, i);
                    }
                });
                WriteVirtualHelper.instance().writeDevice(this.nfcVUtil, sourceInfo, new NetInfo(sourceInfo.getCountFlag()));
            } else {
                ResultDialog.asDefault().setResult(false).setTitle(getString(R.string.failed_to_write)).setTip(str).setAutoClose(true).showDialog(this);
                nfcV.close();
            }
        } catch (Exception e) {
            LHomeLog.e(getClass(), e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$readNfcData$3(NfcV nfcV, int i) {
        int i2;
        if (i == 3) {
            MessageDialog.show(this, getString(R.string.storage_not_enough), "").setOkButton(getString(R.string.i_know));
        } else {
            if (i == 1) {
                i2 = WriteVirtualHelper.instance().isEnterNet() ? R.string.succeed_to_write : R.string.restore_finish;
            } else {
                i2 = R.string.failed_to_write;
            }
            ResultDialog.asDefault().setResult(i == 1).setTitle(getString(i2)).setAutoClose(true).showDialog(this);
            OnNfcWriteCallback onNfcWriteCallback = this.writeCallback;
            if (onNfcWriteCallback != null) {
                onNfcWriteCallback.onFinish(i == 1);
            }
        }
        try {
            nfcV.close();
            if (this.batchWrite) {
                getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.nfc.BaseNfcActivity$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        BaseNfcActivity.this.showNfcDialog();
                    }
                }, 1000L);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int[] convertData(String data) {
        byte[] hexStringToBytes = DataUtil.hexStringToBytes(data);
        for (int i = 0; i < hexStringToBytes.length; i++) {
            if (i % 2 == 0) {
                hexStringToBytes[i] = (byte) (hexStringToBytes[i] ^ (-91));
            } else {
                hexStringToBytes[i] = (byte) (hexStringToBytes[i] ^ (-74));
            }
        }
        String formatHexString = DataUtil.formatHexString(hexStringToBytes);
        LHomeLog.i(getClass(), "NFC DATA:" + formatHexString);
        int[] iArr = new int[hexStringToBytes.length];
        for (int i2 = 0; i2 < hexStringToBytes.length; i2++) {
            iArr[i2] = hexStringToBytes[i2] & 255;
        }
        return iArr;
    }
}