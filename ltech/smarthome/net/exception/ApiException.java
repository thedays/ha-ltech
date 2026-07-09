package com.ltech.smarthome.net.exception;

import com.ltech.smarthome.net.response.ResultBean;

/* loaded from: classes4.dex */
public class ApiException extends RuntimeException {
    private ResultBean resultBean;

    public ApiException(ResultBean resultBean) {
        this.resultBean = resultBean;
    }

    public ResultBean getResultBean() {
        return this.resultBean;
    }
}