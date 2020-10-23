package com.vi.vimarvel.store.routes;

import com.vi.vimarvel.dispatcher.ActionType;
import com.vi.vimarvel.dispatcher.Dispatcher;
import com.vi.vimarvel.dispatcher.IActionHandler;
import com.vi.vimarvel.store.api.APIClient;

public abstract class BaseRoute <T> implements IActionHandler<T> {

    protected final Dispatcher dispatcher;
    protected final APIClient apiClient;

    BaseRoute(APIClient apiClient, Dispatcher dispatcher) {
        this.apiClient = apiClient;
        this.dispatcher = dispatcher;
    }

    protected abstract void handleRoute(T arguments);

    @Override
    public void onAction(ActionType actionType, T arguments) {
        handleRoute(arguments);
    }
}
