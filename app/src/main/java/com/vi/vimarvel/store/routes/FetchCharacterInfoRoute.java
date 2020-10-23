package com.vi.vimarvel.store.routes;

import com.vi.vimarvel.dispatcher.Dispatcher;
import com.vi.vimarvel.dispatcher.EventErrorType;
import com.vi.vimarvel.dispatcher.EventType;
import com.vi.vimarvel.store.api.APIClient;

import java.net.HttpURLConnection;

public class FetchCharacterInfoRoute extends BaseRoute<Integer> {

    public FetchCharacterInfoRoute(APIClient apiClient, Dispatcher dispatcher) {
        super(apiClient, dispatcher);
    }

    @Override
    protected void handleRoute(Integer characterId) {
        apiClient.fetchMarvelCharacterInfo(characterId, (responseCode, data) -> {
            if (responseCode == APIClient.INTERNAL_ERROR_CODE) {
                dispatcher.dispatchEventFailure(EventType.EVENT_TYPE_MARVEL_CHARACTER_INFO_FETCHED,
                        EventErrorType.EVENT_ERROR_TYPE_INTERNAL,
                        null);
            } else if (responseCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
                dispatcher.dispatchEventFailure(EventType.EVENT_TYPE_MARVEL_CHARACTER_INFO_FETCHED,
                        EventErrorType.EVENT_ERROR_TYPE_GENERAL,
                        null);
            } else {
                dispatcher.dispatchEvent(EventType.EVENT_TYPE_MARVEL_CHARACTER_INFO_FETCHED, data);
            }
        });
    }
}
