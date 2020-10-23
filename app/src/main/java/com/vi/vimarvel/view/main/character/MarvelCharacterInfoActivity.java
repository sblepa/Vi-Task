package com.vi.vimarvel.view.main.character;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.vi.vimarvel.R;
import com.vi.vimarvel.databinding.ActivityMarvelCharacterInfoBinding;
import com.vi.vimarvel.view.main.base.BaseActivity;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

public class MarvelCharacterInfoActivity extends BaseActivity {

    private MarvelCharacterInfoViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the ViewModel
        int characterId = -1;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            characterId = getIntent().getExtras().getInt("id");
        }

        if (characterId == -1) {
            // TODO: Handle error
        }

        viewModel = new MarvelCharacterInfoViewModel(this, characterId);

        // Bind layout to ViewModel
        ActivityMarvelCharacterInfoBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_marvel_character_info, null, false);
        setContentView(binding.getRoot());
        binding.setViewModel(viewModel);
    }

    @Override
    public MarvelCharacterInfoViewModel getViewModel() {
        return viewModel;
    }
}
