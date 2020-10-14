package com.vi.vimarvel.store;

import android.content.Context;

public class MarvelStore {

    private MarvelStoreState state;

    private static MarvelStore sharedInstance;

    private MarvelStore() {
        state = new MarvelStoreState();
    }

    public static void initSharedStore(Context context) {
        sharedInstance = new MarvelStore();
    }
}
