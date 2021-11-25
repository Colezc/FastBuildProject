package com.juice.baselibrary.view.areapickerview;

import android.graphics.Color;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.juice.baselibrary.R;
import com.juice.baselibrary.base.BaseAdapter;

import java.util.List;

public class CityAdapter extends BaseAdapter<AddressBean.CityBean> {
    public CityAdapter(int layoutResId, @Nullable List<AddressBean.CityBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressBean.CityBean item) {
        helper.setText(R.id.textview, item.getLabel());
        helper.setTextColor(R.id.textview, item.isStatus() ? Color.parseColor("#65C15C") : Color.parseColor("#444444"));
    }

    public void updateItemData(List<AddressBean.CityBean> data) {
        setNewData(data);
        notifyDataSetChanged();
    }
}
