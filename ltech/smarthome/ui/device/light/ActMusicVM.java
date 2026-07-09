package com.ltech.smarthome.ui.device.light;

import android.view.View;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.ItemFragment;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActMusicVM extends BaseViewModel {
    public long controlId;
    public boolean groupControl;
    public MutableLiveData<Integer> selectPosition = new MutableLiveData<>(0);
    public BindingCommand<View> clickCommand = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.light.ActMusicVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActMusicVM.this.lambda$new$0((View) obj);
        }
    });
    public List<ItemFragment> fragmentList = new ArrayList();

    public void initList() {
        this.fragmentList.clear();
        this.fragmentList.add(new ItemFragment(ActivityUtils.getTopActivity().getString(R.string.music), FtMusic.newInstance(this.controlId, this.groupControl)));
        this.fragmentList.add(new ItemFragment(ActivityUtils.getTopActivity().getString(R.string.mic), FtMic.newInstance(this.controlId, this.groupControl)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        if (view.getId() != R.id.iv_back) {
            return;
        }
        finishActivity();
    }
}