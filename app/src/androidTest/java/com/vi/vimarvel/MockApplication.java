package com.vi.vimarvel;

import android.app.Application;

import com.vi.vimarvel.dispatcher.Dispatcher;
import com.vi.vimarvel.store.MarvelStore;
import com.vi.vimarvel.store.api.APIClient;
import com.vi.vimarvel.store.api.images.ImageFileDownloader;

import okhttp3.OkHttpClient;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class MockApplication extends Application {

    private static APIClient mockAPIClient;
    private static Dispatcher mockDispatcher;
    private static OkHttpClient mockHttpClient;

    @Override
    public void onCreate() {
        super.onCreate();

        Dispatcher dispatcher = Dispatcher.initSharedDispatcher();
        mockAPIClient = mock(APIClient.class);
        mockDispatcher = spy(dispatcher);
        mockHttpClient = mock(OkHttpClient.class);

        // Init the share store
        MarvelStore.initStore(mockDispatcher, mockAPIClient);

        // Init the image downloader
        ImageFileDownloader.initInstance(getApplicationContext(), mockHttpClient);
    }

    public static APIClient getMockAPIClient() {
        return mockAPIClient;
    }

    public static Dispatcher getMockDispatcher() {
        return mockDispatcher;
    }

    public static OkHttpClient getMockHttpClient() {
        return mockHttpClient;
    }
}
