package com.vi.vimarvel.store;

import android.content.Context;

import com.vi.vimarvel.store.api.APIClient;
import com.vi.vimarvel.store.routes.FetchMarvelCharactersRoute;

public class MarvelStore {

    private MarvelStoreState state;
    private final APIClient apiClient;

    @SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
    private static MarvelStore sharedInstance;

    private MarvelStore() {
        state = new MarvelStoreState();
        apiClient = new APIClient();

        initRoutes();
    }

    public static synchronized void initSharedStore(Context context) {
        sharedInstance = new MarvelStore();
    }

    private void initRoutes() {
    }
}
