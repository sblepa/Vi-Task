package com.vi.vimarvel.view.main.adapters;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vi.vimarvel.R;
import com.vi.vimarvel.store.api.images.ImageFileDownloader;
import com.vi.vimarvel.store.models.MarvelCharacterModel;
import com.vi.vimarvel.view.main.MainViewModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

public class MarvelImageAdapter extends RecyclerView.Adapter<MarvelImageViewHolder> {

    private final MainViewModel viewModel;

    public MarvelImageAdapter(MainViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public MarvelImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.layout_marvel_image, parent, false);
        return new MarvelImageViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MarvelImageViewHolder holder, int position) {
        MarvelCharacterModel character = viewModel.getMarvelCharacters().get(position);
        holder.getBindingView().setVariable(BR.url, character.getImageUrl());
    }

    @Override
    public int getItemCount() {
        ArrayList<MarvelCharacterModel> marvelCharacters = viewModel.getMarvelCharacters();
        if (marvelCharacters == null) {
            return 0;
        }

        return marvelCharacters.size();
    }
}
