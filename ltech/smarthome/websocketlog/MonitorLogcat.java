package com.ltech.smarthome.websocketlog;

/* loaded from: classes4.dex */
class MonitorLogcat {
    private static MonitorLogcat sLogcatRunner;
    public String LOG_NAME = "";
    private ShellProcessThread mLogcatThread;

    public interface LogcatOutputCallback {
        void onReaderLine(String line);
    }

    public static MonitorLogcat getInstance() {
        if (sLogcatRunner == null) {
            synchronized (MonitorLogcat.class) {
                if (sLogcatRunner == null) {
                    sLogcatRunner = new MonitorLogcat();
                }
            }
        }
        return sLogcatRunner;
    }

    public void start(LogcatOutputCallback logcatOutputCallback) {
        doStop();
        ShellProcessThread shellProcessThread = new ShellProcessThread();
        this.mLogcatThread = shellProcessThread;
        shellProcessThread.setOutputCallback(logcatOutputCallback);
        this.mLogcatThread.start();
    }

    public void stop() {
        doStop();
    }

    private MonitorLogcat() {
    }

    private void doStop() {
        try {
            ShellProcessThread shellProcessThread = this.mLogcatThread;
            if (shellProcessThread == null || !shellProcessThread.isAlive()) {
                return;
            }
            this.mLogcatThread.setOutputCallback(null);
            this.mLogcatThread.stopReader();
            this.mLogcatThread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class ShellProcessThread extends Thread {
        private LogcatOutputCallback mOutputCallback;
        private volatile boolean readerLogging;

        private ShellProcessThread() {
            this.readerLogging = true;
        }

        void setOutputCallback(LogcatOutputCallback outputCallback) {
            this.mOutputCallback = outputCallback;
        }

        /* JADX WARN: Removed duplicated region for block: B:60:0x00aa  */
        /* JADX WARN: Removed duplicated region for block: B:62:? A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:63:0x00a0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:68:0x0096 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                r8 = this;
                r0 = 0
                java.lang.Runtime r1 = java.lang.Runtime.getRuntime()     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L71
                java.lang.String r2 = "logcat -v threadtime"
                java.lang.Process r1 = r1.exec(r2)     // Catch: java.lang.Throwable -> L6b java.lang.Exception -> L71
                java.io.InputStream r2 = r1.getInputStream()     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L66
                java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L57 java.lang.Exception -> L5c
                java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L57 java.lang.Exception -> L5c
                r4.<init>(r2)     // Catch: java.lang.Throwable -> L57 java.lang.Exception -> L5c
                r3.<init>(r4)     // Catch: java.lang.Throwable -> L57 java.lang.Exception -> L5c
            L19:
                boolean r0 = r8.readerLogging     // Catch: java.lang.Exception -> L55 java.lang.Throwable -> L93
                if (r0 == 0) goto L40
                java.lang.String r0 = r3.readLine()     // Catch: java.lang.Exception -> L55 java.lang.Throwable -> L93
                com.ltech.smarthome.websocketlog.MonitorLogcat$LogcatOutputCallback r4 = r8.mOutputCallback     // Catch: java.lang.Exception -> L55 java.lang.Throwable -> L93
                if (r4 == 0) goto L19
                if (r0 == 0) goto L19
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L55 java.lang.Throwable -> L93
                r5.<init>()     // Catch: java.lang.Exception -> L55 java.lang.Throwable -> L93
                com.ltech.smarthome.websocketlog.MonitorLogcat r6 = com.ltech.smarthome.websocketlog.MonitorLogcat.getInstance()     // Catch: java.lang.Exception -> L55 java.lang.Throwable -> L93
                java.lang.String r6 = r6.LOG_NAME     // Catch: java.lang.Exception -> L55 java.lang.Throwable -> L93
                r5.append(r6)     // Catch: java.lang.Exception -> L55 java.lang.Throwable -> L93
                r5.append(r0)     // Catch: java.lang.Exception -> L55 java.lang.Throwable -> L93
                java.lang.String r0 = r5.toString()     // Catch: java.lang.Exception -> L55 java.lang.Throwable -> L93
                r4.onReaderLine(r0)     // Catch: java.lang.Exception -> L55 java.lang.Throwable -> L93
                goto L19
            L40:
                r3.close()     // Catch: java.io.IOException -> L44
                goto L48
            L44:
                r0 = move-exception
                r0.printStackTrace()
            L48:
                if (r2 == 0) goto L52
                r2.close()     // Catch: java.io.IOException -> L4e
                goto L52
            L4e:
                r0 = move-exception
                r0.printStackTrace()
            L52:
                if (r1 == 0) goto L92
                goto L8f
            L55:
                r0 = move-exception
                goto L76
            L57:
                r3 = move-exception
                r7 = r3
                r3 = r0
                r0 = r7
                goto L94
            L5c:
                r3 = move-exception
                r7 = r3
                r3 = r0
                r0 = r7
                goto L76
            L61:
                r2 = move-exception
                r3 = r0
                r0 = r2
                r2 = r3
                goto L94
            L66:
                r2 = move-exception
                r3 = r0
                r0 = r2
                r2 = r3
                goto L76
            L6b:
                r1 = move-exception
                r2 = r0
                r3 = r2
                r0 = r1
                r1 = r3
                goto L94
            L71:
                r1 = move-exception
                r2 = r0
                r3 = r2
                r0 = r1
                r1 = r3
            L76:
                r0.printStackTrace()     // Catch: java.lang.Throwable -> L93
                if (r3 == 0) goto L83
                r3.close()     // Catch: java.io.IOException -> L7f
                goto L83
            L7f:
                r0 = move-exception
                r0.printStackTrace()
            L83:
                if (r2 == 0) goto L8d
                r2.close()     // Catch: java.io.IOException -> L89
                goto L8d
            L89:
                r0 = move-exception
                r0.printStackTrace()
            L8d:
                if (r1 == 0) goto L92
            L8f:
                r1.destroy()
            L92:
                return
            L93:
                r0 = move-exception
            L94:
                if (r3 == 0) goto L9e
                r3.close()     // Catch: java.io.IOException -> L9a
                goto L9e
            L9a:
                r3 = move-exception
                r3.printStackTrace()
            L9e:
                if (r2 == 0) goto La8
                r2.close()     // Catch: java.io.IOException -> La4
                goto La8
            La4:
                r2 = move-exception
                r2.printStackTrace()
            La8:
                if (r1 == 0) goto Lad
                r1.destroy()
            Lad:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.websocketlog.MonitorLogcat.ShellProcessThread.run():void");
        }

        void stopReader() {
            this.readerLogging = false;
        }
    }
}