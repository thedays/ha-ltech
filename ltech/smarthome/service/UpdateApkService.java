package com.ltech.smarthome.service;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.down.DownloadUtils;
import com.ltech.smarthome.down.JsDownloadListener;
import com.ltech.smarthome.model.bean.DownAppInfo;
import com.ltech.smarthome.net.response.VersionInfo;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.ProgressDialog;
import com.smart.message.utils.LHomeLog;
import java.io.File;

/* loaded from: classes4.dex */
public class UpdateApkService extends Service {
    DownloadUtils downloadUtils;
    ProgressDialog progressDialog;
    VersionInfo versionInfo;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        LHomeLog.i(getClass(), "UpdateApkService onStartCommand run thread -->" + Thread.currentThread().getName());
        ProgressDialog asDefault = ProgressDialog.asDefault();
        this.progressDialog = asDefault;
        asDefault.setTitle(getString(R.string.app_str_downloading));
        this.progressDialog.setOnSelectListener(new ProgressDialog.OnSelectListener() { // from class: com.ltech.smarthome.service.UpdateApkService.1
            @Override // com.ltech.smarthome.view.dialog.ProgressDialog.OnSelectListener
            public void cancel() {
                SmartToast.showShort(UpdateApkService.this.getString(R.string.app_back_down));
            }
        });
        this.progressDialog.showDialog((FragmentActivity) ActivityUtils.getTopActivity());
        if (intent != null && intent.hasExtra("versionInfo")) {
            VersionInfo versionInfo = (VersionInfo) intent.getSerializableExtra("versionInfo");
            this.versionInfo = versionInfo;
            if (versionInfo != null) {
                File file = new File(getExternalFilesDir(null).getAbsolutePath(), "ltech.apk");
                if (file.exists()) {
                    DownAppInfo downAppInfo = (DownAppInfo) SharedPreferenceUtil.getBean(Constants.DOWN_APP, DownAppInfo.class);
                    LHomeLog.i(getClass(), "has down.size=" + downAppInfo.toString());
                    if (downAppInfo.versionCode < this.versionInfo.appversionnum || downAppInfo.downSize > downAppInfo.apkSize || file.length() <= 0) {
                        LHomeLog.i(getClass(), "down error");
                        file.delete();
                        SharedPreferenceUtil.edit().putBean(Constants.DOWN_APP, new DownAppInfo());
                        downFile(this.versionInfo.getFileurl());
                    } else if (downAppInfo.downSize == downAppInfo.apkSize && downAppInfo.versionCode == this.versionInfo.appversionnum && downAppInfo.downSize > 0) {
                        LHomeLog.i(getClass(), "has down. ok");
                        this.progressDialog.dismissDialog();
                        goInstallApk(ActivityUtils.getTopActivity(), file);
                    } else {
                        LHomeLog.i(getClass(), "has down.go on");
                        downFile(this.versionInfo.getFileurl());
                    }
                } else {
                    LHomeLog.i(getClass(), "first down");
                    SharedPreferenceUtil.edit().putBean(Constants.DOWN_APP, new DownAppInfo());
                    downFile(this.versionInfo.getFileurl());
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void downFile(String url) {
        final File file = new File(getExternalFilesDir(null).getAbsolutePath(), "ltech.apk");
        LHomeLog.i(getClass(), "file.path=" + file.getAbsolutePath() + "downUrl=" + url);
        DownloadUtils downloadUtils = new DownloadUtils(this.versionInfo, new JsDownloadListener() { // from class: com.ltech.smarthome.service.UpdateApkService.2
            @Override // com.ltech.smarthome.down.JsDownloadListener
            public void onFail(String errorInfo) {
            }

            @Override // com.ltech.smarthome.down.JsDownloadListener
            public void onStartDownload(long length) {
            }

            @Override // com.ltech.smarthome.down.JsDownloadListener
            public void onProgress(final int progress) {
                if (ActivityUtils.getTopActivity() == null || UpdateApkService.this.progressDialog == null) {
                    return;
                }
                ActivityUtils.getTopActivity().runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.service.UpdateApkService.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        UpdateApkService.this.progressDialog.setProgress(progress);
                    }
                });
            }

            @Override // com.ltech.smarthome.down.JsDownloadListener
            public void onFinishDownload() {
                LHomeLog.i(getClass(), "go to install");
                ActivityUtils.getTopActivity().runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.service.UpdateApkService.2.2
                    @Override // java.lang.Runnable
                    public void run() {
                        UpdateApkService.this.progressDialog.dismissDialog();
                    }
                });
                UpdateApkService.this.goInstallApk(ActivityUtils.getTopActivity(), file);
            }
        });
        this.downloadUtils = downloadUtils;
        downloadUtils.download(file);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void goInstallApk(Activity mContext, File file) {
        boolean canRequestPackageInstalls;
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setFlags(1);
        intent.addFlags(268435456);
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setDataAndType(FileProvider.getUriForFile(mContext, "com.ltech.smarthome.provider", file), "application/vnd.android.package-archive");
            if (Build.VERSION.SDK_INT >= 26) {
                canRequestPackageInstalls = mContext.getPackageManager().canRequestPackageInstalls();
                if (!canRequestPackageInstalls) {
                    ActivityCompat.requestPermissions(mContext, new String[]{"android.permission.REQUEST_INSTALL_PACKAGES"}, 6666);
                }
            }
        } else {
            intent.setDataAndType(Uri.parse("file://" + file.toString()), "application/vnd.android.package-archive");
        }
        if (mContext.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
            mContext.startActivity(intent);
        }
    }

    private boolean isHasInstallPermissionWithO(Context context) {
        boolean canRequestPackageInstalls;
        if (context == null) {
            return false;
        }
        canRequestPackageInstalls = context.getPackageManager().canRequestPackageInstalls();
        return canRequestPackageInstalls;
    }

    private void startInstallPermissionSettingActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        if (Build.VERSION.SDK_INT >= 26) {
            intent.setAction("android.settings.MANAGE_UNKNOWN_APP_SOURCES");
        } else {
            intent.setAction("android.settings.SECURITY_SETTINGS");
        }
        ((Activity) context).startActivity(intent);
        Toast.makeText(context, "请打开未知应用安装权限", 0).show();
    }
}