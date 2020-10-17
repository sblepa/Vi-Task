package com.vi.vimarvel.view.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.vi.vimarvel.R;
import com.vi.vimarvel.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the ViewModel
        viewModel = new MainViewModel();

        // Bind layout to ViewModel
        ActivityMainBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_main, null, false);
        setContentView(binding.getRoot());
        binding.setViewModel(viewModel);
    }
}
