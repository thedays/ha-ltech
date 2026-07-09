package com.ltech.smarthome.view.dialog;

import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.databinding.DialogRc4sTipBinding;
import com.ltech.smarthome.view.pagergridlayoutmanager.PagerGridLayoutManager;
import java.util.List;

/* loaded from: classes4.dex */
public class Rc4sTipListDialog extends SmartDialog<DialogRc4sTipBinding> {
    private ImageView[] imgs;
    private List<Tip> list;
    private int position;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_rc4s_tip;
    }

    public static Rc4sTipListDialog as() {
        return (Rc4sTipListDialog) new Rc4sTipListDialog().setViewConverter(new SmartDialog.ViewConverter<DialogRc4sTipBinding, Rc4sTipListDialog>() { // from class: com.ltech.smarthome.view.dialog.Rc4sTipListDialog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(DialogRc4sTipBinding viewBinding, Rc4sTipListDialog dialog) {
                dialog.initRv(viewBinding);
            }
        }).setOutCancel(true).setMargin(0).setGravity(17);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRv(DialogRc4sTipBinding viewBinding) {
        int i = 0;
        this.imgs = new ImageView[]{viewBinding.iv1, viewBinding.iv2, viewBinding.iv3, viewBinding.iv4};
        RecyclerView recyclerView = viewBinding.rv;
        PagerGridLayoutManager pagerGridLayoutManager = new PagerGridLayoutManager(1, 1);
        recyclerView.setLayoutManager(pagerGridLayoutManager);
        viewBinding.rv.setAdapter(new BaseQuickAdapter<Tip, BaseViewHolder>(this, R.layout.item_rc4s_tip, this.list) { // from class: com.ltech.smarthome.view.dialog.Rc4sTipListDialog.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, Tip tip) {
                baseViewHolder.setText(R.id.tv_title, tip.getTitle());
                if (tip.array == null) {
                    baseViewHolder.setGone(R.id.group_tip, false);
                    baseViewHolder.setGone(R.id.group_empty, true);
                } else {
                    baseViewHolder.setGone(R.id.group_tip, true);
                    baseViewHolder.setGone(R.id.group_empty, false);
                    baseViewHolder.setImageResource(R.id.iv_rc4s, tip.pic);
                }
            }
        });
        pagerGridLayoutManager.scrollToPagerIndex(this.position);
        pagerGridLayoutManager.setPagerChangedListener(new PagerGridLayoutManager.PagerChangedListener() { // from class: com.ltech.smarthome.view.dialog.Rc4sTipListDialog.3
            @Override // com.ltech.smarthome.view.pagergridlayoutmanager.PagerGridLayoutManager.PagerChangedListener
            public void onPagerCountChanged(int pagerCount) {
            }

            @Override // com.ltech.smarthome.view.pagergridlayoutmanager.PagerGridLayoutManager.PagerChangedListener
            public void onPagerIndexSelected(int prePagerIndex, int currentPagerIndex) {
                Rc4sTipListDialog.this.imgs[prePagerIndex].setImageResource(R.drawable.gray_dot_a40);
                Rc4sTipListDialog.this.imgs[currentPagerIndex].setImageResource(R.drawable.gray_dot);
            }
        });
        while (true) {
            ImageView[] imageViewArr = this.imgs;
            if (i >= imageViewArr.length) {
                return;
            }
            ImageView imageView = imageViewArr[i];
            if (i == this.position) {
                imageView.setImageResource(R.drawable.gray_dot);
            } else {
                imageView.setImageResource(R.drawable.gray_dot_a40);
            }
            i++;
        }
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "list_dialog";
    }

    public Rc4sTipListDialog setItemList(List<Tip> list) {
        this.list = list;
        return this;
    }

    public Rc4sTipListDialog setPosition(int position) {
        this.position = position;
        return this;
    }

    public static class Tip {
        private String[] array;
        private int pic;
        private String title;

        public Tip(String title, String[] array, int pic) {
            this.array = array;
            this.title = title;
            this.pic = pic;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String[] getArray() {
            return this.array;
        }

        public void setArray(String[] array) {
            this.array = array;
        }
    }
}