package com.juice.baselibrary.view.areapickerview;

import android.graphics.Color;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.juice.baselibrary.R;

import java.util.List;

public class ProvinceAdapter extends BaseQuickAdapter<AddressBean, BaseViewHolder> {
    public ProvinceAdapter(int layoutResId, @Nullable List<AddressBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressBean item) {
        helper.setText(R.id.textview, item.getLabel());
        helper.setTextColor(R.id.textview, item.isStatus() ? Color.parseColor("#65C15C") : Color.parseColor("#444444"));
    }

    public void updateItemData(List<AddressBean> mData) {

        setNewData(mData);
        notifyDataSetChanged();
    }
}
