package com.vi.vimarvel.store.routes;

import com.vi.vimarvel.store.api.APIClient;

public class FetchMarvelCharactersRoute {

    private APIClient apiClient;

    public FetchMarvelCharactersRoute(APIClient api) {
        apiClient = api;
    }

    public void handleRoute() {
        apiClient.fetchMarvelCharacters((responseCode, data) -> {
            if (responseCode == -1) {
                // TODO: Handle internal error
            } else if (responseCode >= 400) {
                // TODO: Handle server error
            } else {
                // TODO: return data
            }
        });
    }
}
