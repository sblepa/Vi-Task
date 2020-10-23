package com.vi.vimarvel.store;

import com.vi.vimarvel.dispatcher.ActionType;
import com.vi.vimarvel.dispatcher.Dispatcher;
import com.vi.vimarvel.store.api.APIClient;
import com.vi.vimarvel.store.routes.BaseRoute;
import com.vi.vimarvel.store.routes.FetchCharacterInfoRoute;
import com.vi.vimarvel.store.routes.FetchMarvelCharactersRoute;

import java.util.ArrayList;

public class MarvelStore {

    private final Dispatcher dispatcher;
    private MarvelStoreState state;
    private final APIClient apiClient;
    private ArrayList<BaseRoute> routes = new ArrayList<>();

    @SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
    private static MarvelStore sharedInstance;

    private MarvelStore(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
        state = new MarvelStoreState();
        apiClient = new APIClient();

        initRoutes();
    }

    public static synchronized void initStore(Dispatcher dispatcher) {
        sharedInstance = new MarvelStore(dispatcher);
    }

    private void initRoutes() {
        FetchMarvelCharactersRoute fetchMarvelCharactersRoute = new FetchMarvelCharactersRoute(apiClient, dispatcher);
        dispatcher.addActionListener(ActionType.ACTION_TYPE_FETCH_MARVEL_CHARACTERS, fetchMarvelCharactersRoute);
        routes.add(fetchMarvelCharactersRoute);

        FetchCharacterInfoRoute fetchCharacterInfoRoute = new FetchCharacterInfoRoute(apiClient, dispatcher);
        dispatcher.addActionListener(ActionType.ACTION_TYPE_FETCH_CHARACTER_INFO, fetchCharacterInfoRoute);
        routes.add(fetchCharacterInfoRoute);
    }
}
