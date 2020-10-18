package com.vi.vimarvel.view.main.adapters;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class MarvelImageViewHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding bindingView;

    MarvelImageViewHolder(ViewDataBinding itemViewBindings) {
        super(itemViewBindings.getRoot());
        bindingView = itemViewBindings;
    }

    public ViewDataBinding getBindingView(){
        return bindingView;
    }
}
