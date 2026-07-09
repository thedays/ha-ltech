package com.ltech.smarthome.ui.device.light;

import android.content.Context;
import android.view.View;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.preference_bean.MusicBean;
import com.ltech.smarthome.ui.device.light.ActAddMusicVM;
import java.util.ArrayList;
import java.util.Iterator;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

/* loaded from: classes4.dex */
public class ActAddMusicVM extends BaseViewModel {
    public ObservableList<ItemMusic> musicList = new ObservableArrayList();
    public ItemBinding<ItemMusic> itemBinding = ItemBinding.of(new OnItemBind() { // from class: com.ltech.smarthome.ui.device.light.ActAddMusicVM$$ExternalSyntheticLambda1
        @Override // me.tatarka.bindingcollectionadapter2.OnItemBind
        public final void onItemBind(ItemBinding itemBinding, int i, Object obj) {
            itemBinding.clearExtras().set(40, R.layout.item_music).bindExtra(68, Integer.valueOf(R.mipmap.ic_tick_sel)).bindExtra(21, Integer.valueOf(R.mipmap.ic_tick_default)).bindExtra(10, new BindingCommand(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.light.ActAddMusicVM$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj2) {
                    ActAddMusicVM.lambda$new$0(ActAddMusicVM.ItemMusic.this, (View) obj2);
                }
            }));
        }
    });

    public static final class ItemMusic {
        public MusicBean musicBean;
        public MutableLiveData<Boolean> select = new MutableLiveData<>(false);
    }

    static /* synthetic */ void lambda$new$0(ItemMusic itemMusic, View view) {
        if (view.getId() != R.id.layout_bg) {
            return;
        }
        itemMusic.select.setValue(Boolean.valueOf(!itemMusic.select.getValue().booleanValue()));
    }

    public void initMusicList(Context context) {
        for (MusicBean musicBean : Injection.player().queryMusic(context)) {
            ItemMusic itemMusic = new ItemMusic();
            itemMusic.musicBean = musicBean;
            this.musicList.add(itemMusic);
        }
        for (MusicBean musicBean2 : Injection.player().getSavePlayList()) {
            Iterator<ItemMusic> it = this.musicList.iterator();
            while (true) {
                if (it.hasNext()) {
                    ItemMusic next = it.next();
                    if (musicBean2.equals(next.musicBean)) {
                        next.select.setValue(true);
                        break;
                    }
                }
            }
        }
    }

    public void saveData() {
        ArrayList arrayList = new ArrayList();
        for (MusicBean musicBean : Injection.player().getSavePlayList()) {
            Iterator<ItemMusic> it = this.musicList.iterator();
            while (true) {
                if (it.hasNext()) {
                    ItemMusic next = it.next();
                    if (musicBean.equals(next.musicBean) && next.select.getValue().booleanValue()) {
                        arrayList.add(musicBean);
                        break;
                    }
                }
            }
        }
        for (ItemMusic itemMusic : this.musicList) {
            if (itemMusic.select.getValue().booleanValue() && !arrayList.contains(itemMusic.musicBean)) {
                arrayList.add(itemMusic.musicBean);
            }
        }
        Injection.player().savePlayList(arrayList);
        finishActivity();
    }
}