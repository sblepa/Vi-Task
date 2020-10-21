package com.vi.vimarvel.dispatcher;

public interface IActionHandler <T> {
    void onAction(ActionType actionType, T arguments);
}
