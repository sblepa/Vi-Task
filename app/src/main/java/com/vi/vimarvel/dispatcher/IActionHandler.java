package com.vi.vimarvel.dispatcher;

public interface IActionHandler {
    void onAction(ActionType actionType, Object arguments);
}
