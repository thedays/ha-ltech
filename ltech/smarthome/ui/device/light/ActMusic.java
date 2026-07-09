package com.ltech.smarthome.ui.device.light;

import android.app.Activity;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActMusicBinding;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LiveBusHelper;
import com.ltech.smarthome.utils.VersionUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import java.util.List;

/* loaded from: classes4.dex */
public class ActMusic extends VMActivity<ActMusicBinding, ActMusicVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_music;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected boolean useEventBus() {
        return true;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        String[] strArr;
        super.initView();
        if (VersionUtils.isAndroidQ()) {
            strArr = new String[]{Permission.RECORD_AUDIO};
        } else {
            strArr = new String[]{Permission.RECORD_AUDIO, Permission.READ_EXTERNAL_STORAGE};
        }
        AndPermission.with((Activity) this).runtime().permission(strArr).onGranted(new Action() { // from class: com.ltech.smarthome.ui.device.light.ActMusic$$ExternalSyntheticLambda0
            @Override // com.yanzhenjie.permission.Action
            public final void onAction(Object obj) {
                ActMusic.this.lambda$initView$0((List) obj);
            }
        }).onDenied(new Action() { // from class: com.ltech.smarthome.ui.device.light.ActMusic$$ExternalSyntheticLambda1
            @Override // com.yanzhenjie.permission.Action
            public final void onAction(Object obj) {
                ActMusic.this.lambda$initView$1((List) obj);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(List list) {
        setView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(List list) {
        back();
    }

    private void setView() {
        ((ActMusicVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActMusicVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        ((ActMusicVM) this.mViewModel).initList();
        ((ActMusicBinding) this.mViewBinding).viewpager.setAdapter(new FragmentStateAdapter(this) { // from class: com.ltech.smarthome.ui.device.light.ActMusic.1
            @Override // androidx.viewpager2.adapter.FragmentStateAdapter
            public Fragment createFragment(int position) {
                return ((ActMusicVM) ActMusic.this.mViewModel).fragmentList.get(position).fragment;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return ((ActMusicVM) ActMusic.this.mViewModel).fragmentList.size();
            }
        });
        ((ActMusicBinding) this.mViewBinding).viewpager.setOffscreenPageLimit(((ActMusicVM) this.mViewModel).fragmentList.size() - 1);
        ((ActMusicBinding) this.mViewBinding).viewpager.setUserInputEnabled(false);
        ((ActMusicBinding) this.mViewBinding).viewpager.setPageTransformer(new ViewPager2.PageTransformer() { // from class: com.ltech.smarthome.ui.device.light.ActMusic$$ExternalSyntheticLambda2
            @Override // androidx.viewpager2.widget.ViewPager2.PageTransformer
            public final void transformPage(View view, float f) {
                ActMusic.lambda$setView$2(view, f);
            }
        });
    }

    static /* synthetic */ void lambda$setView$2(View view, float f) {
        view.setPivotX(view.getWidth() / 2.0f);
        view.setPivotY(view.getHeight());
        view.setRotation(f * 45.0f);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void handleBusEvent(LiveBusHelper helper) {
        if (5 == helper.getCode()) {
            if (FtMusic.class.getName().equals(helper.getData())) {
                ((ActMusicBinding) this.mViewBinding).viewpager.setCurrentItem(1);
            } else {
                ((ActMusicBinding) this.mViewBinding).viewpager.setCurrentItem(0);
            }
        }
    }
}