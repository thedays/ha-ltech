package com.ltech.smarthome.net.request.push;

import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes4.dex */
public class PushTestRequest {
    private String body;
    private int devicetype;
    private int messagetype;
    private int pushType;
    private String target;
    private String title;

    public PushTestRequest(int pushType, int devicetype, int messagetype, String target, String title, String body) {
        this.pushType = pushType;
        this.devicetype = devicetype;
        this.messagetype = messagetype;
        this.target = target;
        this.title = title;
        this.body = body;
    }

    public static final class PushTestRequestBuilder {
        private String body;
        private int devicetype;
        private int messagetype;
        private int pushType;
        private String target;
        private String title;

        private PushTestRequestBuilder() {
        }

        public static PushTestRequestBuilder aPushTestRequest() {
            return new PushTestRequestBuilder();
        }

        public PushTestRequestBuilder pushNotification() {
            this.pushType = 1;
            return this;
        }

        public PushTestRequestBuilder pushMessage() {
            this.pushType = 2;
            return this;
        }

        public PushTestRequestBuilder toIos() {
            this.devicetype = 1;
            return this;
        }

        public PushTestRequestBuilder toAndroid() {
            this.devicetype = 2;
            return this;
        }

        public PushTestRequestBuilder normalMessage() {
            this.messagetype = 1;
            return this;
        }

        public PushTestRequestBuilder shareMessage() {
            this.messagetype = 2;
            return this;
        }

        public PushTestRequestBuilder target(long... userIds) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < userIds.length; i++) {
                sb.append(userIds[i]);
                if (i < userIds.length - 1) {
                    sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                }
            }
            this.target = sb.toString();
            return this;
        }

        public PushTestRequestBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PushTestRequestBuilder content(String content) {
            this.body = content;
            return this;
        }

        public PushTestRequest build() {
            return new PushTestRequest(this.pushType, this.devicetype, this.messagetype, this.target, this.title, this.body);
        }
    }
}