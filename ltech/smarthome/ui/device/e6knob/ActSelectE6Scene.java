package com.ltech.smarthome.ui.device.e6knob;

import android.text.TextUtils;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.ParamMap;
import com.ltech.smarthome.view.NumberSetView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectE6Scene extends BaseNormalActivity<ActSelectBinding> {
    private MultipleItemRvAdapter<ParamMap, BaseViewHolder> mAdapter;
    private NumberSetView numberSetView;
    private int sceneNum;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        setTitle(getString(R.string.call_scene));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.confirm));
        initData();
        initAdapter();
    }

    private void initData() {
        if (!getIntent().getBooleanExtra(Constants.IS_E6, false) || TextUtils.isEmpty(E6Helper.instance().getActionInstruct())) {
            return;
        }
        String actionInstruct = E6Helper.instance().getActionInstruct();
        if (Integer.parseInt(actionInstruct.substring(0, 2), 16) == 12) {
            this.sceneNum = Integer.parseInt(actionInstruct.substring(2, 4), 16);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (this.numberSetView.getNumber() > 15) {
            this.numberSetView.setNumber(15);
        }
        E6Helper.instance().selectScene(this.numberSetView.getNumber());
    }

    private List<ParamMap> getList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ParamMap(getString(R.string.app_scene), 0));
        return arrayList;
    }

    private void initAdapter() {
        MultipleItemRvAdapter<ParamMap, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<ParamMap, BaseViewHolder>(getList()) { // from class: com.ltech.smarthome.ui.device.e6knob.ActSelectE6Scene.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(ParamMap item) {
                return 0;
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new BaseItemProvider<ParamMap, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.e6knob.ActSelectE6Scene.1.1
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_e6d_address;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void onClick(BaseViewHolder helper, ParamMap data, int position) {
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 0;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, ParamMap item, int position) {
                        helper.setText(R.id.tv_name, item.getName()).setGone(R.id.layout_name, false).setText(R.id.tv_title, String.format("%s(%s)", ActSelectE6Scene.this.getString(R.string.scene_number), "0-15"));
                        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                        NumberSetView numberSetView = (NumberSetView) helper.getView(R.id.add_set_view);
                        numberSetView.setRange(0, 15);
                        numberSetView.setNumber(Math.min(ActSelectE6Scene.this.sceneNum, 15));
                        numberSetView.setEditable(true);
                        if (ActSelectE6Scene.this.numberSetView == null) {
                            ActSelectE6Scene.this.numberSetView = numberSetView;
                        }
                    }
                });
            }
        };
        this.mAdapter = multipleItemRvAdapter;
        multipleItemRvAdapter.finishInitialize();
        this.mAdapter.bindToRecyclerView(((ActSelectBinding) this.mViewBinding).rvContent);
        ((ActSelectBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
    }
}