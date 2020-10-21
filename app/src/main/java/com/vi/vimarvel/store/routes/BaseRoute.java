package com.vi.vimarvel.store.routes;

import com.vi.vimarvel.dispatcher.ActionType;
import com.vi.vimarvel.dispatcher.Dispatcher;
import com.vi.vimarvel.dispatcher.IActionHandler;

public abstract class BaseRoute <T> implements IActionHandler<T> {

    protected final Dispatcher dispatcher;

    BaseRoute(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    protected abstract void handleRoute(T arguments);

    @Override
    public void onAction(ActionType actionType, T arguments) {
        handleRoute(arguments);
    }
}
