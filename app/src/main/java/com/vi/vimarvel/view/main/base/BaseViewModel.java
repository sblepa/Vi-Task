package com.vi.vimarvel.view.main.base;

import com.vi.vimarvel.dispatcher.Dispatcher;

import androidx.databinding.BaseObservable;

public abstract class BaseViewModel extends BaseObservable {

    protected final Dispatcher dispatcher;

    protected BaseViewModel(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    protected abstract void onResume();
}
