package com.vi.vimarvel.view.main.base;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();

        if (getViewModel() != null) {
            getViewModel().onResume();
        }
    }

    protected abstract BaseViewModel getViewModel();
}
