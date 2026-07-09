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
public abstract class BaseFtGeneralModeVM extends BaseViewModel implements IFtGeneralMode {
    public int clickPosition;
    public long controlId;
    public Object controlObject;
    public int lightType;
    private List<String> mDefaultNameList;
    private List<Integer> mPicList;
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
        if (this.mDefaultNameList == null) {
            this.mDefaultNameList = Arrays.asList(NameRepository.getGeneralModeName(context));
        }
        return this.mDefaultNameList;
    }
}