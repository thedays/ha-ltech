package com.ltech.smarthome.ui.mode;

import android.view.View;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.ItemFragment;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.repo.ProductRepository;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActModeVM extends BaseViewModel {
    public long controlId;
    public boolean groupControl;
    public int lightType;
    public int zoneNum;
    public MutableLiveData<Integer> selectPosition = new MutableLiveData<>(0);
    public BindingCommand<View> clickCommand = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.mode.ActModeVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActModeVM.this.lambda$new$0((View) obj);
        }
    });
    public List<ItemFragment> fragmentList = new ArrayList();

    public void initList() {
        this.fragmentList.clear();
        int i = this.lightType;
        if (i == 1) {
            this.fragmentList.add(new ItemFragment(ActivityUtils.getTopActivity().getString(R.string.advanced_mode), FtAdvancedMode.newInstance(this.controlId, this.groupControl, this.zoneNum, this.lightType)));
            return;
        }
        if (i == 2) {
            if (this.controlId == -1 || supportCtGeneralMode()) {
                this.fragmentList.add(new ItemFragment(ActivityUtils.getTopActivity().getString(R.string.general_mode), FtGeneralMode.newInstance(this.controlId, this.groupControl, this.zoneNum, this.lightType)));
            }
            this.fragmentList.add(new ItemFragment(ActivityUtils.getTopActivity().getString(R.string.advanced_mode), FtAdvancedMode.newInstance(this.controlId, this.groupControl, this.zoneNum, this.lightType)));
            return;
        }
        this.fragmentList.add(new ItemFragment(ActivityUtils.getTopActivity().getString(R.string.general_mode), FtGeneralMode.newInstance(this.controlId, this.groupControl, this.zoneNum, this.lightType)));
        this.fragmentList.add(new ItemFragment(ActivityUtils.getTopActivity().getString(R.string.advanced_mode), FtAdvancedMode.newInstance(this.controlId, this.groupControl, this.zoneNum, this.lightType)));
    }

    private boolean supportCtGeneralMode() {
        return ProductRepository.supportCtGeneralMode(Injection.repo().role().getRoleById(this.controlId, this.groupControl));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        if (view.getId() != R.id.iv_back) {
            return;
        }
        finishActivity();
    }
}