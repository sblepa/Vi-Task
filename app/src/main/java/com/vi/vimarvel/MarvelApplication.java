package com.vi.vimarvel;

import android.app.Application;

import com.vi.vimarvel.dispatcher.Dispatcher;
import com.vi.vimarvel.store.MarvelStore;
import com.vi.vimarvel.store.api.images.ImageFileDownloader;

public class MarvelApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Init the share store
        MarvelStore.initStore(Dispatcher.initSharedDispatcher());

        // Init the image downloader
        ImageFileDownloader.initInstance(getApplicationContext());
    }
}
