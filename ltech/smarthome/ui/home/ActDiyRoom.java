package com.ltech.smarthome.ui.home;

import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActDiyRoomBinding;
import com.ltech.smarthome.utils.Constants;

/* loaded from: classes4.dex */
public class ActDiyRoom extends VMActivity<ActDiyRoomBinding, ActDiyRoomVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_diy_room;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        setEditString(getString(R.string.save));
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.add_room));
        ((ActDiyRoomVM) this.mViewModel).nameArray = getIntent().getStringArrayExtra(Constants.ROOM_NAME_ARRAY);
        ((ActDiyRoomVM) this.mViewModel).floorId = getIntent().getLongExtra(Constants.FLOOR_ID, -1L);
        ((ActDiyRoomVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        ((ActDiyRoomVM) this.mViewModel).createRoom(this);
    }
}