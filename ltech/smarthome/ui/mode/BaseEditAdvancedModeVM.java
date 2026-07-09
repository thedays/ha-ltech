package com.ltech.smarthome.ui.mode;

import android.content.Context;
import android.view.View;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.google.common.primitives.Ints;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.ModeContent;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.IconRepository;
import com.ltech.smarthome.utils.NameRepository;
import com.ltech.smarthome.utils.NavUtils;
import com.smart.product_agreement.bean.AdvancedModeInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class BaseEditAdvancedModeVM extends BaseViewModel implements IEditAdvancedMode {
    private AdvancedModeInfo advancedModeInfo;
    public int clickPosition;
    public long controlId;
    public Object controlObject;
    public boolean groupControl;
    public int lightType;
    private List<String> mDefaultNameList;
    private List<Integer> mPicList;
    public String modeName;
    public int modeNum;
    public long placeId;
    public int playtime;
    public Listing<ModeContent> request;
    public int zoneNum;
    public List<ModeContent> modeContentList = new ArrayList();
    public List<Integer> playList = new ArrayList();
    public MutableLiveData<Integer> listPlayTime = new MutableLiveData<>();
    public MutableLiveData<AdvancedModeInfo> paramLiveData = new MutableLiveData<>();
    public SingleLiveEvent<Void> showNameDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showResetDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showIconDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showTimeDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showPlayDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showStopDialogEvent = new SingleLiveEvent<>();
    public MutableLiveData<Boolean> previewLiveData = new MutableLiveData<>();
    public BindingCommand<View> clickCommand = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.mode.BaseEditAdvancedModeVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            BaseEditAdvancedModeVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.item_icon /* 2131296902 */:
                this.showIconDialogEvent.call();
                break;
            case R.id.item_name /* 2131296904 */:
                this.showNameDialogEvent.call();
                break;
            case R.id.item_times /* 2131296906 */:
                this.showTimeDialogEvent.call();
                break;
            case R.id.tv_import_mode /* 2131298699 */:
                NavUtils.destination(ActSelectDeviceForMode.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withInt(Constants.LIGHT_TYPE, this.lightType).withInt(Constants.MODE_NUM, this.modeNum).withInt(Constants.MODE_TYPE, 2).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
                break;
            case R.id.v_preview1 /* 2131299155 */:
                this.showResetDialogEvent.call();
                break;
            case R.id.v_preview3 /* 2131299157 */:
                this.showPlayDialogEvent.call();
                break;
            case R.id.v_preview3Off /* 2131299158 */:
                this.showStopDialogEvent.call();
                break;
        }
    }

    public void setModeInfo(AdvancedModeInfo modeInfo) {
        this.advancedModeInfo = modeInfo;
        this.paramLiveData.setValue(modeInfo);
    }

    public AdvancedModeInfo getModeInfo() {
        return this.advancedModeInfo;
    }

    public List<Integer> getPicList(Context context) {
        if (this.mPicList == null) {
            this.mPicList = Ints.asList(IconRepository.getModePic(context));
        }
        return this.mPicList;
    }

    public List<String> getNameList(Context context) {
        int i = this.lightType;
        if (i == 1) {
            List<String> asList = Arrays.asList(NameRepository.getAdvancedDimModeName(context));
            this.mDefaultNameList = asList;
            return asList;
        }
        if (i == 2) {
            List<String> asList2 = Arrays.asList(NameRepository.getAdvancedCtModeName(context));
            this.mDefaultNameList = asList2;
            return asList2;
        }
        if (this.mDefaultNameList == null) {
            this.mDefaultNameList = Arrays.asList(NameRepository.getAdvancedModeName(context));
        }
        return this.mDefaultNameList;
    }
}