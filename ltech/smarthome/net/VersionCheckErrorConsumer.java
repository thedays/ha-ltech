package com.ltech.smarthome.net;

import android.util.Log;
import com.ltech.smarthome.net.exception.ApiException;
import com.ltech.smarthome.net.response.ResultBean;
import io.reactivex.functions.Consumer;

/* loaded from: classes4.dex */
public class VersionCheckErrorConsumer implements Consumer<Throwable> {
    private CallBackMsg callBackMsg;
    protected int errorCode;
    protected String errorMessage;

    public interface CallBackMsg {
        void handleElse(String msg);

        void handleMsg(String msg);
    }

    @Override // io.reactivex.functions.Consumer
    public void accept(Throwable throwable) throws Exception {
        if (throwable instanceof ApiException) {
            ResultBean resultBean = ((ApiException) throwable).getResultBean();
            resultBean.getRet();
            this.errorMessage = resultBean.getMessage();
            this.errorCode = resultBean.getRet();
        } else {
            this.errorMessage = throwable.getMessage();
        }
        action(throwable);
    }

    protected void action(Throwable throwable) {
        CallBackMsg callBackMsg;
        Log.i("errorMessage", "errorMessage=" + this.errorMessage);
        String str = this.errorMessage;
        if (str != null && (callBackMsg = this.callBackMsg) == null && this.errorCode != 17) {
            callBackMsg.handleElse(str);
        }
        CallBackMsg callBackMsg2 = this.callBackMsg;
        if (callBackMsg2 != null) {
            callBackMsg2.handleMsg(this.errorMessage);
        }
    }

    public void setCallBackMsg(CallBackMsg callBackMsg) {
        this.callBackMsg = callBackMsg;
    }
}