package com.ltech.smarthome.ui.mode;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import com.google.common.primitives.Ints;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.bean.ModeContent;
import com.ltech.smarthome.utils.IconRepository;
import com.ltech.smarthome.utils.NameRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class BaseFtAdvancedModeVM extends BaseViewModel implements IFtAdvancedMode {
    public int clickPosition;
    public long controlId;
    public Object controlObject;
    public int lightType;
    private List<String> mDefaultNameList;
    private List<Integer> mPicList;
    public long placeId;
    public int zoneNum;
    public List<Integer> playList = new ArrayList();
    public List<ModeContent> modeContentList = new ArrayList();
    public MutableLiveData<Integer> listPlayTime = new MutableLiveData<>();

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