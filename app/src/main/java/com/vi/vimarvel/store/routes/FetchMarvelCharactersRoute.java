package com.vi.vimarvel.store.routes;

import com.vi.vimarvel.dispatcher.Dispatcher;
import com.vi.vimarvel.dispatcher.EventErrorType;
import com.vi.vimarvel.dispatcher.EventType;
import com.vi.vimarvel.store.api.APIClient;

import java.net.HttpURLConnection;

public class FetchMarvelCharactersRoute extends BaseRoute<Void> {

    private APIClient apiClient;

    public FetchMarvelCharactersRoute(APIClient apiClient, Dispatcher dispatcher) {
        super(dispatcher);
        this.apiClient = apiClient;
    }

    @Override
    protected void handleRoute(Void arguments) {
        apiClient.fetchMarvelCharacters((responseCode, data) -> {
            if (responseCode == APIClient.INTERNAL_ERROR_CODE) {
                dispatcher.dispatchEventFailure(EventType.EVENT_TYPES_MARVEL_CHARACTERS_FETCHED,
                        EventErrorType.EVENT_ERROR_TYPE_INTERNAL,
                        null);
            } else if (responseCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
                dispatcher.dispatchEventFailure(EventType.EVENT_TYPES_MARVEL_CHARACTERS_FETCHED,
                        EventErrorType.EVENT_ERROR_TYPE_GENERAL,
                        null);
            } else {
                dispatcher.dispatchEvent(EventType.EVENT_TYPES_MARVEL_CHARACTERS_FETCHED, data);
            }
        });
    }
}
