package com.vi.vimarvel.store.routes;

import com.vi.vimarvel.dispatcher.Dispatcher;
import com.vi.vimarvel.store.api.APIClient;

import org.junit.Before;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

public class BaseRouteTest {

    protected APIClient mockApiClient;
    protected Dispatcher mockDispatcher;

    @Before
    public void setup() {
        mockApiClient = mock(APIClient.class);
        mockDispatcher = mock(Dispatcher.class);
    }

    protected void resetMocks() {
        reset(mockApiClient);
        reset(mockDispatcher);
    }
}
