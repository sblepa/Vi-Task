package com.vi.vimarvel;

import android.app.Application;

import com.vi.vimarvel.store.MarvelStore;

public class MarvelApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Init the share store
        MarvelStore.initSharedStore(getApplicationContext());
    }
}
