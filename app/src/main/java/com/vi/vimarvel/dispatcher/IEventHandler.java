package com.vi.vimarvel.dispatcher;

public interface IEventHandler <T> {
    void onEvent(EventType eventType, T data);
    void onEventFailure(EventType eventType, EventErrorType errorType, T data);
}
