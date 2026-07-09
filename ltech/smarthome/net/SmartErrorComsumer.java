package com.ltech.smarthome.net;

import com.blankj.utilcode.util.ActivityUtils;
import com.google.gson.JsonSyntaxException;
import com.ltech.smarthome.R;
import com.ltech.smarthome.net.exception.ApiException;
import com.ltech.smarthome.net.response.ResultBean;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.message.utils.LHomeLog;
import io.reactivex.functions.Consumer;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import retrofit2.HttpException;

/* loaded from: classes4.dex */
public class SmartErrorComsumer implements Consumer<Throwable> {
    private CallBackMsg callBackMsg;
    protected int errorCode;
    protected String errorMessage;

    public interface CallBackMsg {
        void handleMsg(String msg);
    }

    @Override // io.reactivex.functions.Consumer
    public void accept(Throwable throwable) {
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
        throwable.getClass().getSimpleName();
        LHomeLog.i(getClass(), "errorMessage=" + this.errorMessage + "__throwable=" + throwable.getClass().getSimpleName());
        if (throwable instanceof UnknownHostException) {
            SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.no_network));
            return;
        }
        int i = this.errorCode;
        if (i == 25) {
            SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_error_25));
        } else if (i == 24) {
            SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_error_24));
        } else if (i == 5030) {
            SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_error_5030));
        } else if (i == 1018) {
            SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.app_str_error_1018));
        } else {
            String str = this.errorMessage;
            if (str != null && this.callBackMsg == null && i != 17) {
                SmartToast.showShort(str);
            }
        }
        CallBackMsg callBackMsg = this.callBackMsg;
        if (callBackMsg != null) {
            callBackMsg.handleMsg(this.errorMessage);
        }
    }

    public void setCallBackMsg(CallBackMsg callBackMsg) {
        this.callBackMsg = callBackMsg;
    }

    public static Throwable unifiedError(Throwable e) {
        if (e instanceof UnknownHostException) {
            return new Throwable("网络连接已经断开，请检查您的网络！", e.getCause());
        }
        if ((e instanceof SocketTimeoutException) || (e instanceof SocketException)) {
            return new Throwable("网络连接超时，请检查您的网络状态！", e.getCause());
        }
        if ((e instanceof IllegalArgumentException) || (e instanceof JsonSyntaxException)) {
            return new Throwable("未能请求到数据，攻城狮正在修复!", e.getCause());
        }
        if (e instanceof HttpException) {
            try {
                return new Throwable(((HttpException) e).response().errorBody().string(), e.getCause());
            } catch (IOException e2) {
                e2.printStackTrace();
                return new Throwable(e.getMessage(), e.getCause());
            }
        }
        return new Throwable(e.getMessage(), e.getCause());
    }
}