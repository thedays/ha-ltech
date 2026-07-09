package com.ltech.smarthome.upgrade;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.airoha.btdlib.core.AirohaLink;
import com.airoha.libfota.core.AirohaOtaMgr;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.ltech.smarthome.R;
import com.qw.curtain.lib.GuideView$$ExternalSyntheticApiModelOutline0;
import com.sun.jna.platform.win32.WinError;

/* loaded from: classes4.dex */
public class BluetoothLeService extends Service {
    private static final String TAG = "Airoha_BluetoothLeService";
    private AirohaLink mAirohaLink;
    private AirohaOtaMgr mAirohaOtaMgr;
    private final IBinder mBinder = new LocalBinder();

    public void setHandler(Handler handler) {
    }

    @Override // android.app.Service
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
        setForeground(getResources().getString(R.string.app_name));
        this.mAirohaLink = new AirohaLink(this);
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override // android.app.Service
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        this.mAirohaLink = null;
        super.onDestroy();
    }

    public class LocalBinder extends Binder {
        public LocalBinder() {
        }

        BluetoothLeService getService() {
            return BluetoothLeService.this;
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        close();
        return super.onUnbind(intent);
    }

    public boolean connect(final String address) {
        AirohaLink airohaLink = this.mAirohaLink;
        if (airohaLink == null) {
            return false;
        }
        return airohaLink.connect(address);
    }

    public void disconnect() {
        this.mAirohaLink.disconnect();
    }

    private void close() {
        this.mAirohaLink.close();
    }

    public void setForeground(String strUpate) {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel m2 = GuideView$$ExternalSyntheticApiModelOutline0.m(strUpate, strUpate, 2);
            NotificationManager notificationManager = (NotificationManager) getSystemService(RemoteMessageConst.NOTIFICATION);
            if (notificationManager == null) {
                return;
            }
            notificationManager.createNotificationChannel(m2);
            startForeground(WinError.ERROR_PORT_UNREACHABLE, new NotificationCompat.Builder(this, strUpate).setContentTitle(strUpate).setAutoCancel(true).setCategory("service").setOngoing(true).setPriority(2).build());
            return;
        }
        new Intent(this, (Class<?>) ActBtOta.class).setFlags(603979776);
        startForeground(WinError.ERROR_PORT_UNREACHABLE, new Notification.Builder(this).setContentTitle(getResources().getString(R.string.app_name)).setContentText(strUpate).setSmallIcon(R.mipmap.ic_lhome).build());
    }

    public AirohaLink getAirohaLink() {
        return this.mAirohaLink;
    }

    public AirohaOtaMgr getAirohaOtaMgr() {
        if (this.mAirohaOtaMgr == null) {
            this.mAirohaOtaMgr = new AirohaOtaMgr(this.mAirohaLink, 115);
        }
        return this.mAirohaOtaMgr;
    }
}