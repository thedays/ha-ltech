package com.ltech.smarthome.ui.intercom;

import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.databinding.FtAccessControlBinding;
import com.ltech.smarthome.net.response.intercom.UserInfoResponse;
import java.util.List;

/* loaded from: classes4.dex */
public class FtAccessControl extends BaseNormalFragment<FtAccessControlBinding> {
    private List<UserInfoResponse.DevInfo> devInfoList;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_access_control;
    }

    public void setDevInfoList(List<UserInfoResponse.DevInfo> devInfoList) {
        this.devInfoList = devInfoList;
    }
}