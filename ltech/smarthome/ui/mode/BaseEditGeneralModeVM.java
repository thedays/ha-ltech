package com.ltech.smarthome.ui.mode;

import android.content.Context;
import android.view.View;
import androidx.lifecycle.MutableLiveData;
import com.google.common.primitives.Ints;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.ModeContent;
import com.ltech.smarthome.utils.IconRepository;
import com.ltech.smarthome.utils.NameRepository;
import com.smart.product_agreement.bean.GeneralModeInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class BaseEditGeneralModeVM extends BaseViewModel implements IEditGeneralMode {
    private static int[] modeTypeArray = {2, 1, 3, 4, 5};
    public int clickPosition;
    public long controlId;
    public Object controlObject;
    private GeneralModeInfo generalModeInfo;
    public boolean groupControl;
    public int lightType;
    private List<String> mDefaultNameList;
    private List<Integer> mPicList;
    public String modeName;
    public int modeNum;
    public long placeId;
    public Listing<ModeContent> request;
    public int zoneNum = 1;
    public List<ModeContent> modeContentList = new ArrayList();
    public SingleLiveEvent<Void> showNameDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showResetDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showIconDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showTimeDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showPlayDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showStopDialogEvent = new SingleLiveEvent<>();
    public List<Integer> playList = new ArrayList();
    public MutableLiveData<Integer> listPlayTime = new MutableLiveData<>();
    public MutableLiveData<GeneralModeInfo> paramLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> previewLiveData = new MutableLiveData<>();
    public BindingCommand<View> clickCommand = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.mode.BaseEditGeneralModeVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            BaseEditGeneralModeVM.this.lambda$new$0((View) obj);
        }
    });

    public List<Integer> getPicList(Context context) {
        if (this.mPicList == null) {
            this.mPicList = Ints.asList(IconRepository.getModePic(context));
        }
        return this.mPicList;
    }

    public List<String> getNameList(Context context) {
        if (this.mDefaultNameList == null) {
            if (this.lightType == 2) {
                this.mDefaultNameList = Arrays.asList(NameRepository.getGeneralCtModeName(context));
            } else {
                this.mDefaultNameList = Arrays.asList(NameRepository.getGeneralModeName(context));
            }
        }
        return this.mDefaultNameList;
    }

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
            case R.id.v_preview1 /* 2131299155 */:
                this.showResetDialogEvent.call();
                break;
            case R.id.v_preview2 /* 2131299156 */:
                this.showPlayDialogEvent.call();
                break;
            case R.id.v_previewOff /* 2131299159 */:
                this.showStopDialogEvent.call();
                break;
        }
    }

    public void setModeInfo(GeneralModeInfo modeInfo) {
        this.generalModeInfo = modeInfo;
        this.paramLiveData.setValue(modeInfo);
    }

    public GeneralModeInfo getModeInfo() {
        return this.generalModeInfo;
    }

    public void setRgbBrt(int brt) {
        this.generalModeInfo.setRgbBrt(brt);
    }

    public void setSpeed(int speed) {
        this.generalModeInfo.setSpeed(speed);
    }

    public void setW(int w) {
        this.generalModeInfo.setwOrwy(w);
    }

    public void setWy(int wy) {
        this.generalModeInfo.setwOrwy(wy);
    }

    public void setWyBrt(int wyBrt) {
        this.generalModeInfo.setWyBrt(wyBrt);
    }

    public void setColor(int position, int color) {
        this.generalModeInfo.getColorList().set(position, Integer.valueOf(color));
    }

    public void getColor(int position, int color) {
        this.generalModeInfo.getColorList().get(position);
    }

    public void setModeType(int position) {
        this.generalModeInfo.setModeType(modeTypeArray[position]);
        MutableLiveData<GeneralModeInfo> mutableLiveData = this.paramLiveData;
        mutableLiveData.setValue(mutableLiveData.getValue());
    }

    public int getModeTypePosition(int modeType) {
        int i = 0;
        while (true) {
            int[] iArr = modeTypeArray;
            if (i >= iArr.length) {
                return 0;
            }
            if (modeType == iArr[i]) {
                return i;
            }
            i++;
        }
    }
}