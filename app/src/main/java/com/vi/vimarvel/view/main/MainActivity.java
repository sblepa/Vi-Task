package com.vi.vimarvel.view.main;

import android.os.Bundle;

import com.vi.vimarvel.R;
import com.vi.vimarvel.databinding.ActivityMainBinding;
import com.vi.vimarvel.view.main.adapters.MarvelImageAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements MainViewModel.IMainViewModelViewEvents {

    private MainViewModel viewModel;
    private MarvelImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the ViewModel
        viewModel = new MainViewModel();
        viewModel.setViewEvents(this);

        // Bind layout to ViewModel
        ActivityMainBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_main, null, false);
        setContentView(binding.getRoot());
        binding.setViewModel(viewModel);

        imageAdapter = new MarvelImageAdapter(viewModel);
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setAdapter(imageAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // TODO: Add pull to refresh functionality
    }

    @Override
    public void onDataUpdate() {
        if (imageAdapter != null) {
            imageAdapter.notifyDataSetChanged();
        }
    }
}
