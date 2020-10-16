package com.vi.vimarvel.store.api;

public interface IAPIResponse <T> {
    void onResponse(int responseCode, T data);
}
