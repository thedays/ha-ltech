package com.ltech.smarthome.ui.device.cg485;

import android.graphics.Rect;
import android.view.View;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.SizeUtils;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.SuperPanelExtParam;

/* loaded from: classes4.dex */
public class ActCg485VM extends BaseViewModel {
    public long controlId;
    public MutableLiveData<Device> controlObject = new MutableLiveData<>();
    public RecyclerView.ItemDecoration decoration = new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485VM.1
        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(SizeUtils.dp2px(5.0f), SizeUtils.dp2px(5.0f), SizeUtils.dp2px(5.0f), SizeUtils.dp2px(5.0f));
        }
    };
    public SuperPanelExtParam extParam;
    public long placeId;
}