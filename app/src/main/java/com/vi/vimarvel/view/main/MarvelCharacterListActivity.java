package com.vi.vimarvel.view.main;

import android.content.Intent;
import android.os.Bundle;

import com.vi.vimarvel.R;
import com.vi.vimarvel.databinding.ActivityMarvelCharacterListBinding;
import com.vi.vimarvel.view.main.adapters.MarvelImageAdapter;
import com.vi.vimarvel.view.main.base.BaseActivity;
import com.vi.vimarvel.view.main.character.MarvelCharacterInfoActivity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MarvelCharacterListActivity extends BaseActivity implements MarvelCharacterListViewModel.IMainViewModelViewEvents {

    private MarvelCharacterListViewModel viewModel;
    private MarvelImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the ViewModel
        viewModel = new MarvelCharacterListViewModel();
        viewModel.setViewEvents(this);

        // Bind layout to ViewModel
        ActivityMarvelCharacterListBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_marvel_character_list, null, false);
        setContentView(binding.getRoot());
        binding.setViewModel(viewModel);

        imageAdapter = new MarvelImageAdapter(viewModel);
        RecyclerView recyclerView = binding.charactersRecyclerView;
        recyclerView.setAdapter(imageAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // TODO: Add pull to refresh functionality
    }

    @Override
    public MarvelCharacterListViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void onDataUpdate() {
        if (imageAdapter != null) {
            imageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCharacterClicked(int characterId) {
        Intent intent = new Intent(this, MarvelCharacterInfoActivity.class);
        intent.putExtra("id", characterId);
        startActivity(intent);
    }
}
