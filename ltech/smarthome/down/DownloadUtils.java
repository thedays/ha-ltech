package com.ltech.smarthome.down;

import com.ltech.smarthome.model.bean.DownAppInfo;
import com.ltech.smarthome.net.api.UserApi;
import com.ltech.smarthome.net.response.VersionInfo;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.smart.message.utils.LHomeLog;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/* loaded from: classes3.dex */
public class DownloadUtils {
    private static final int DEFAULT_TIMEOUT = 30;
    private static final String TAG = "DownloadUtils";
    private String apkUrl;
    private File destination;
    private String downloadUrl;
    File file;
    OkHttpClient httpClient;
    private JsDownloadListener listener;
    private Retrofit retrofit;
    private VersionInfo versionInfo;
    FileOutputStream fos = null;
    private long startsPoint = 0;
    DownAppInfo downAppInfo = (DownAppInfo) SharedPreferenceUtil.getBean(Constants.DOWN_APP, DownAppInfo.class);

    public DownloadUtils(VersionInfo versionInfo, JsDownloadListener listener) {
        this.versionInfo = versionInfo;
        this.listener = listener;
    }

    public void download(final File file) {
        this.file = file;
        this.destination = file;
        JsDownloadInterceptor jsDownloadInterceptor = new JsDownloadInterceptor(this.listener);
        if (this.downAppInfo == null) {
            this.downAppInfo = new DownAppInfo();
        }
        this.httpClient = new OkHttpClient.Builder().addInterceptor(new RequestInterceptor(this.downAppInfo.getDownSize())).addInterceptor(jsDownloadInterceptor).retryOnConnectionFailure(true).readTimeout(360L, TimeUnit.SECONDS).connectTimeout(30L, TimeUnit.SECONDS).build();
        Retrofit build = new Retrofit.Builder().baseUrl("http://trytest.granwin.com:8088/").client(this.httpClient).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        this.retrofit = build;
        ((UserApi) build.create(UserApi.class)).download(this.versionInfo.getFileurl()).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).subscribe(new Consumer() { // from class: com.ltech.smarthome.down.DownloadUtils$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DownloadUtils.this.lambda$download$0((ResponseBody) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$download$0(final ResponseBody responseBody) throws Exception {
        new Thread(new Runnable() { // from class: com.ltech.smarthome.down.DownloadUtils.1
            @Override // java.lang.Runnable
            public void run() {
                DownloadUtils downloadUtils = DownloadUtils.this;
                downloadUtils.save(responseBody, downloadUtils.downAppInfo.getDownSize());
            }
        }).start();
    }

    private void writeFile(InputStream inputString, File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bArr = new byte[4096];
            while (true) {
                int read = inputString.read(bArr);
                if (read != -1) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    inputString.close();
                    fileOutputStream.close();
                    LHomeLog.i(getClass(), "down ok");
                    this.listener.onFinishDownload();
                    return;
                }
            }
        } catch (FileNotFoundException unused) {
            this.listener.onFail("FileNotFoundException");
        } catch (Exception e) {
            LHomeLog.i(getClass(), "down error=" + e.getMessage());
            this.listener.onFail("IOException");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void save(ResponseBody body, long startsPoint) {
        Throwable th;
        RandomAccessFile randomAccessFile;
        IOException iOException;
        InputStream byteStream = body.byteStream();
        FileChannel fileChannel = null;
        try {
            try {
                try {
                    randomAccessFile = new RandomAccessFile(this.destination, "rwd");
                    try {
                        FileChannel channel = randomAccessFile.getChannel();
                        try {
                            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, startsPoint, body.contentLength());
                            this.downAppInfo.apkSize = randomAccessFile.length();
                            byte[] bArr = new byte[1024];
                            long downSize = this.downAppInfo.getDownSize();
                            LHomeLog.i(getClass(), "down start size=" + downSize + "__apkfile.size=" + this.downAppInfo.apkSize);
                            while (true) {
                                int read = byteStream.read(bArr);
                                if (read == -1) {
                                    break;
                                }
                                map.put(bArr, 0, read);
                                downSize += read;
                                this.downAppInfo.setDownSize(downSize);
                            }
                            this.listener.onFinishDownload();
                            LHomeLog.i(getClass(), "down ok");
                            this.downAppInfo.versionCode = this.versionInfo.getAppversionnum();
                            this.downAppInfo.url = this.versionInfo.getFileurl();
                            SharedPreferenceUtil.edit().putBean(Constants.DOWN_APP, this.downAppInfo);
                            byteStream.close();
                            if (channel != null) {
                                channel.close();
                            }
                            randomAccessFile.close();
                        } catch (IOException e) {
                            iOException = e;
                            fileChannel = channel;
                            iOException.printStackTrace();
                            this.downAppInfo.versionCode = this.versionInfo.getAppversionnum();
                            this.downAppInfo.url = this.versionInfo.getFileurl();
                            SharedPreferenceUtil.edit().putBean(Constants.DOWN_APP, this.downAppInfo);
                            byteStream.close();
                            if (fileChannel != null) {
                                fileChannel.close();
                            }
                            if (randomAccessFile != null) {
                                randomAccessFile.close();
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            fileChannel = channel;
                            try {
                                this.downAppInfo.versionCode = this.versionInfo.getAppversionnum();
                                this.downAppInfo.url = this.versionInfo.getFileurl();
                                SharedPreferenceUtil.edit().putBean(Constants.DOWN_APP, this.downAppInfo);
                                byteStream.close();
                                if (fileChannel != null) {
                                    fileChannel.close();
                                }
                                if (randomAccessFile == null) {
                                    throw th;
                                }
                                randomAccessFile.close();
                                throw th;
                            } catch (IOException e2) {
                                e2.printStackTrace();
                                throw th;
                            }
                        }
                    } catch (IOException e3) {
                        iOException = e3;
                    }
                } catch (IOException e4) {
                    iOException = e4;
                    randomAccessFile = null;
                } catch (Throwable th3) {
                    th = th3;
                    randomAccessFile = null;
                }
            } catch (Throwable th4) {
                th = th4;
            }
        } catch (IOException e5) {
            e5.printStackTrace();
        }
    }
}