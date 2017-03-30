package com.longb.colordouban.adapter.common;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by longb on 2017/2/11.
 */

public class BindingViewHolder<DT> extends RecyclerView.ViewHolder {
    private ViewDataBinding binding;

    public BindingViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(DT data,int variableId) {
        binding.setVariable(variableId, data);
        binding.executePendingBindings();
    }
}
