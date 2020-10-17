package com.vi.vimarvel.store.routes;

import com.vi.vimarvel.dispatcher.ActionType;
import com.vi.vimarvel.dispatcher.Dispatcher;
import com.vi.vimarvel.dispatcher.EventErrorType;
import com.vi.vimarvel.dispatcher.EventType;
import com.vi.vimarvel.store.api.APIClient;

public class FetchMarvelCharactersRoute extends BaseRoute {

    private final Dispatcher dispatcher;
    private APIClient apiClient;

    public FetchMarvelCharactersRoute(APIClient apiClient, Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
        this.apiClient = apiClient;
    }

    @Override
    public void onAction(ActionType actionType, Object arguments) {
        apiClient.fetchMarvelCharacters((responseCode, data) -> {
            if (responseCode == -1) {
                dispatcher.dispatchEventFailure(EventType.EVENT_TYPES_MARVEL_CHARACTERS_FETCHED,
                        EventErrorType.EVENT_ERROR_TYPE_INTERNAL,
                        null);
            } else if (responseCode >= 400) {
                dispatcher.dispatchEventFailure(EventType.EVENT_TYPES_MARVEL_CHARACTERS_FETCHED,
                        EventErrorType.EVENT_ERROR_TYPE_GENERAL,
                        null);
            } else {
                dispatcher.dispatchEvent(EventType.EVENT_TYPES_MARVEL_CHARACTERS_FETCHED, data);
            }
        });
    }
}
