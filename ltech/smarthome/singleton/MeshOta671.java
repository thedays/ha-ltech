package com.ltech.smarthome.singleton;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import com.ltech.smarthome.upgrade.ota671.DfuCallback;
import com.ltech.smarthome.upgrade.ota671.FileToByteCallBack;
import com.ltech.smarthome.upgrade.ota671.SplitByteArrayCallBack;
import com.ltech.smarthome.upgrade.ota671.UuidConsts;
import com.smart.message.utils.LHomeLog;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class MeshOta671 {
    private static final String TAG = "MeshOta671";
    private static volatile MeshOta671 instance;
    private byte[] appfile;
    private BluetoothDevice bluetoothDevice;
    private BluetoothGattCharacteristic charac_ota_control;
    BluetoothGattCharacteristic characteristic_ota_data;
    private BluetoothGatt gatt671;
    private BluetoothAdapter mBluetoothAdapter;
    private Context mContext;
    private DfuCallback mDfuCallback;
    BluetoothGattService bluetoothGattService = null;
    private boolean GATT_CONNECTED = false;
    private int reconnectNub = 0;
    private int inputFileCont = 0;
    private boolean OTA_MODLE = false;
    private int retry133Max = 3;
    private boolean isOtaEnd = false;
    Handler handler = new Handler(new Handler.Callback() { // from class: com.ltech.smarthome.singleton.MeshOta671.1
        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x01a5, code lost:
        
            return false;
         */
        @Override // android.os.Handler.Callback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean handleMessage(android.os.Message r13) {
            /*
                Method dump skipped, instructions count: 440
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.singleton.MeshOta671.AnonymousClass1.handleMessage(android.os.Message):boolean");
        }
    });
    private byte[][] resultFile = null;
    private final char[] HEX_DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private final char[] HEX_DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() { // from class: com.ltech.smarthome.singleton.MeshOta671.3
        @Override // android.bluetooth.BluetoothGattCallback
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            LHomeLog.e(getClass(), "======onConnectionStateChange======" + status + "     " + newState);
            if (newState == 2) {
                MeshOta671.this.OTA_MODLE = true;
                MeshOta671.this.GATT_CONNECTED = true;
                MeshOta671.this.handler.sendEmptyMessageDelayed(7, 600L);
                return;
            }
            if (newState == 0) {
                MeshOta671.this.GATT_CONNECTED = false;
                if (MeshOta671.this.OTA_MODLE) {
                    MeshOta671.this.handler.removeMessages(6);
                    MeshOta671.this.gatt671.close();
                    if (MeshOta671.this.mDfuCallback != null) {
                        MeshOta671.this.mDfuCallback.DfuFail("蓝牙异常断开");
                        return;
                    }
                    return;
                }
                if (status == 133) {
                    if (MeshOta671.this.retry133Max > 0) {
                        MeshOta671.this.retry133Max--;
                        MeshOta671.this.handler.removeMessages(4);
                        MeshOta671.this.handler.sendEmptyMessageDelayed(4, 2500L);
                        return;
                    }
                    MeshOta671.this.retry133Max = 3;
                    MeshOta671.this.handler.removeMessages(6);
                    MeshOta671.this.gatt671.close();
                    if (MeshOta671.this.mDfuCallback != null) {
                        MeshOta671.this.mDfuCallback.DfuFail("蓝牙异常断开");
                        return;
                    }
                    return;
                }
                if (MeshOta671.this.isOtaEnd) {
                    return;
                }
                MeshOta671.this.handler.removeMessages(4);
                MeshOta671.this.handler.sendEmptyMessageDelayed(4, 2500L);
            }
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            MeshOta671.this.handler.removeMessages(6);
            Class<?> cls = getClass();
            StringBuilder sb = new StringBuilder("=====接收=====");
            sb.append(status);
            sb.append("    ");
            sb.append(MeshOta671.this.resultFile.length);
            sb.append("    ");
            sb.append(MeshOta671.this.inputFileCont);
            sb.append("    ");
            sb.append(characteristic.getService().getUuid());
            sb.append("     ");
            sb.append(characteristic.getUuid());
            sb.append("        ");
            sb.append(MeshOta671.this.bytes2HexString(characteristic.getValue(), true));
            sb.append("   ");
            sb.append(characteristic.getValue() == null ? 0 : characteristic.getValue().length);
            LHomeLog.e(cls, sb.toString());
            if (status != 0) {
                if (status == 257) {
                    LHomeLog.e(getClass(), "onCharacteristicWrite中写入失败");
                    MeshOta671.this.OTA_MODLE = false;
                    if (MeshOta671.this.mDfuCallback != null) {
                        MeshOta671.this.mDfuCallback.DfuFail("onCharacteristicWrite中写入失败");
                        return;
                    }
                    return;
                }
                if (status != 3) {
                    if (status == 129) {
                        MeshOta671.this.handler.sendEmptyMessageDelayed(3, 3000L);
                        return;
                    } else {
                        MeshOta671.this.disconnect();
                        return;
                    }
                }
                LHomeLog.e(getClass(), "onCharacteristicWrite中没权限");
                MeshOta671.this.OTA_MODLE = false;
                if (MeshOta671.this.mDfuCallback != null) {
                    MeshOta671.this.mDfuCallback.DfuFail("onCharacteristicWrite中没权限");
                    return;
                }
                return;
            }
            if (characteristic.getUuid().equals(UuidConsts.OTA_CONTROL)) {
                if (characteristic.getValue().length == 1) {
                    if (characteristic.getValue()[0] == 0) {
                        MeshOta671.this.handler.removeMessages(2);
                        MeshOta671.this.handler.sendEmptyMessageDelayed(2, 500L);
                        if (MeshOta671.this.mDfuCallback != null) {
                            MeshOta671.this.mDfuCallback.Msg("===进入第一步====");
                            return;
                        }
                        return;
                    }
                    if (characteristic.getValue()[0] == 3) {
                        MeshOta671.this.handler.post(new Runnable() { // from class: com.ltech.smarthome.singleton.MeshOta671.3.1
                            @Override // java.lang.Runnable
                            public void run() {
                                MeshOta671.this.isOtaEnd = true;
                                MeshOta671.this.mDfuCallback.DfuSuccess();
                                MeshOta671.this.mDfuCallback.Msg("===升级结束====");
                                MeshOta671.this.inputFileCont = 0;
                                MeshOta671.this.OTA_MODLE = false;
                            }
                        });
                        return;
                    } else {
                        LHomeLog.e(getClass(), "========55555555============");
                        return;
                    }
                }
                return;
            }
            if (characteristic.getUuid().equals(UuidConsts.OTA_DATA)) {
                MeshOta671.this.handler.removeMessages(6);
                if (characteristic.getValue().equals(MeshOta671.this.resultFile[MeshOta671.this.inputFileCont])) {
                    MeshOta671.this.inputFileCont++;
                    if (MeshOta671.this.inputFileCont < MeshOta671.this.resultFile.length) {
                        MeshOta671.this.handler.sendEmptyMessage(2);
                        return;
                    } else {
                        MeshOta671.this.handler.sendEmptyMessageDelayed(3, 200L);
                        return;
                    }
                }
                MeshOta671.this.mDfuCallback.Msg("发送异常1");
                MeshOta671.this.mDfuCallback.DfuFail("升级失败1");
            }
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            LHomeLog.e(getClass(), "========mtu========" + mtu);
            MeshOta671.this.startOta();
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            MeshOta671.this.gatt671 = gatt;
            try {
                MeshOta671.this.bluetoothGattService = gatt.getService(UuidConsts.OTA_SERVICE);
                MeshOta671 meshOta671 = MeshOta671.this;
                meshOta671.charac_ota_control = meshOta671.bluetoothGattService.getCharacteristic(UuidConsts.OTA_CONTROL);
                MeshOta671 meshOta6712 = MeshOta671.this;
                meshOta6712.characteristic_ota_data = meshOta6712.bluetoothGattService.getCharacteristic(UuidConsts.OTA_DATA);
                if (MeshOta671.this.bluetoothGattService != null) {
                    LHomeLog.e(getClass(), "============广播服务=============");
                }
                if (MeshOta671.this.OTA_MODLE) {
                    MeshOta671.this.handler.sendEmptyMessageDelayed(1, 600L);
                }
            } catch (Exception e) {
                LHomeLog.e(getClass(), "============广播服务异常=============" + e.toString());
                if (MeshOta671.this.mDfuCallback != null) {
                    MeshOta671.this.mDfuCallback.DfuFail("============广播服务异常=============" + e.toString());
                }
            }
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            LHomeLog.e(getClass(), "========onCharacteristicChanged=========" + characteristic.getUuid() + "   " + MeshOta671.this.bytes2HexString(characteristic.getValue(), true));
        }
    };

    public static MeshOta671 getInstance() {
        if (instance == null) {
            synchronized (MeshOta671.class) {
                if (instance == null) {
                    instance = new MeshOta671();
                }
            }
        }
        return instance;
    }

    public void init(Context context, DfuCallback mDfuCallback) {
        this.mContext = context;
        this.mDfuCallback = mDfuCallback;
    }

    public void setFile(byte[] raw) {
        this.appfile = raw;
    }

    public void startOta() {
        this.isOtaEnd = false;
        this.inputFileCont = 0;
        this.reconnectNub = 0;
        byte[] bArr = this.appfile;
        if (bArr == null) {
            this.OTA_MODLE = false;
            DfuCallback dfuCallback = this.mDfuCallback;
            if (dfuCallback != null) {
                dfuCallback.Msg("文件不能等于null");
                return;
            }
            return;
        }
        if (this.gatt671 == null) {
            this.OTA_MODLE = false;
            DfuCallback dfuCallback2 = this.mDfuCallback;
            if (dfuCallback2 != null) {
                dfuCallback2.Msg("bluetoothGatt == null");
                return;
            }
            return;
        }
        SplitFile(bArr);
        if (this.GATT_CONNECTED) {
            DfuCallback dfuCallback3 = this.mDfuCallback;
            if (dfuCallback3 != null) {
                dfuCallback3.DfuStart();
            }
            this.handler.sendEmptyMessageDelayed(5, 2000L);
            return;
        }
        this.OTA_MODLE = false;
        DfuCallback dfuCallback4 = this.mDfuCallback;
        if (dfuCallback4 != null) {
            dfuCallback4.DfuFail("连接已经断开，请重试");
        }
    }

    public Handler getHandler() {
        return this.handler;
    }

    public void disconnect() {
        BluetoothGatt bluetoothGatt = this.gatt671;
        if (bluetoothGatt != null) {
            bluetoothGatt.disconnect();
        }
    }

    public boolean writeOtaControl(byte ctrl) {
        LHomeLog.e(getClass(), "========writeOtaControl========");
        if (this.gatt671.getService(UuidConsts.OTA_SERVICE) != null) {
            BluetoothGattCharacteristic characteristic = this.gatt671.getService(UuidConsts.OTA_SERVICE).getCharacteristic(UuidConsts.OTA_CONTROL);
            this.charac_ota_control = characteristic;
            if (characteristic != null) {
                characteristic.setWriteType(2);
                this.charac_ota_control.setValue(new byte[]{ctrl});
                this.gatt671.writeCharacteristic(this.charac_ota_control);
                return true;
            }
            LHomeLog.e(getClass(), "==========characteristic====null");
        } else {
            LHomeLog.e(getClass(), "==========OTA_SERVICE====null");
            this.handler.sendEmptyMessageDelayed(5, 1500L);
        }
        return false;
    }

    private void SplitFile(final byte[] data) {
        this.handler.post(new Runnable() { // from class: com.ltech.smarthome.singleton.MeshOta671.2
            @Override // java.lang.Runnable
            public void run() {
                MeshOta671.this.SplitByteArray(data, 244, new SplitByteArrayCallBack() { // from class: com.ltech.smarthome.singleton.MeshOta671.2.1
                    @Override // com.ltech.smarthome.upgrade.ota671.SplitByteArrayCallBack
                    public void callback(byte[][] result) {
                        MeshOta671.this.resultFile = result;
                    }
                });
            }
        });
    }

    public void fileToBytes(File file, FileToByteCallBack callBack) {
        byte[] bArr = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bArr);
            fileInputStream.close();
            callBack.filetobytecallback(bArr);
        } catch (Exception e) {
            LHomeLog.e(getClass(), "=========" + e.toString());
            callBack.filetobytecallback(null);
        }
    }

    public void SplitByteArray(byte[] data, int length, SplitByteArrayCallBack splitByteArrayCallBack) {
        int length2 = data.length;
        int ceil = (int) Math.ceil(length2 / length);
        byte[][] bArr = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, ceil, length);
        for (int i = 0; i < ceil; i++) {
            int i2 = i * length;
            bArr[i] = Arrays.copyOfRange(data, i2, Math.min(i2 + length, length2));
        }
        splitByteArrayCallBack.callback(bArr);
    }

    private boolean hasOtaFileCorrectExtension(String filename) {
        return filename.indexOf(".bbl") == -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String bytes2HexString(byte[] bytes, boolean isUpperCase) {
        if (bytes == null) {
            return "";
        }
        char[] cArr = isUpperCase ? this.HEX_DIGITS_UPPER : this.HEX_DIGITS_LOWER;
        int length = bytes.length;
        if (length <= 0) {
            return "";
        }
        char[] cArr2 = new char[length << 1];
        int i = 0;
        for (byte b2 : bytes) {
            int i2 = i + 1;
            cArr2[i] = cArr[(b2 >> 4) & 15];
            i += 2;
            cArr2[i2] = cArr[b2 & 15];
        }
        return new String(cArr2);
    }

    public boolean connect(final String address) {
        BluetoothGatt connectGatt;
        BluetoothGatt connectGatt2;
        this.retry133Max = 3;
        this.handler.removeMessages(4);
        try {
            LHomeLog.d(getClass(), "Start Connect");
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            this.mBluetoothAdapter = defaultAdapter;
            if (defaultAdapter == null) {
                Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
                return false;
            }
            BluetoothDevice remoteDevice = defaultAdapter.getRemoteDevice(address);
            this.bluetoothDevice = remoteDevice;
            if (remoteDevice == null) {
                Log.w(TAG, "Device not found.  Unable to connect.");
                return false;
            }
            LHomeLog.d(getClass(), "Trying to create a new connection. " + address);
            if (Build.VERSION.SDK_INT >= 26) {
                connectGatt2 = this.bluetoothDevice.connectGatt(this.mContext, false, this.mGattCallback, 2, 1);
                this.gatt671 = connectGatt2;
                return true;
            }
            if (Build.VERSION.SDK_INT >= 23) {
                connectGatt = this.bluetoothDevice.connectGatt(this.mContext, false, this.mGattCallback, 2);
                this.gatt671 = connectGatt;
                return true;
            }
            this.gatt671 = this.bluetoothDevice.connectGatt(this.mContext, false, this.mGattCallback);
            return true;
        } catch (Exception e) {
            LHomeLog.e(getClass(), e.toString());
            return false;
        }
    }
}