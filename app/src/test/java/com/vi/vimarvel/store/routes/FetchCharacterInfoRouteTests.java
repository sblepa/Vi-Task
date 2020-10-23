package com.vi.vimarvel.store.routes;

import com.vi.vimarvel.dispatcher.EventType;
import com.vi.vimarvel.store.api.IAPIResponse;
import com.vi.vimarvel.store.models.MarvelCharacterInfoModel;

import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;

import static com.vi.vimarvel.dispatcher.EventErrorType.EVENT_ERROR_TYPE_GENERAL;
import static com.vi.vimarvel.dispatcher.EventErrorType.EVENT_ERROR_TYPE_INTERNAL;
import static com.vi.vimarvel.store.api.APIClient.INTERNAL_ERROR_CODE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class FetchCharacterInfoRouteTests extends BaseRouteTest {

    private FetchCharacterInfoRoute fetchCharacterInfoRoute;
    private MarvelCharacterInfoModel routeRes;

    @Before
    public void setup() {
        super.setup();

        fetchCharacterInfoRoute = new FetchCharacterInfoRoute(mockApiClient, mockDispatcher);
    }

    @Test
    public void testFetchCharacterInfoRouteFail() {
        mockFetchMarvelCharacterInfo(INTERNAL_ERROR_CODE);

        fetchCharacterInfoRoute.handleRoute(1);
        verify(mockDispatcher, times(1)).
                dispatchEventFailure(eq(EventType.EVENT_TYPE_MARVEL_CHARACTER_INFO_FETCHED),
                        eq(EVENT_ERROR_TYPE_INTERNAL),
                        isNull());

        resetMocks();

        mockFetchMarvelCharacterInfo(HttpURLConnection.HTTP_BAD_REQUEST);
        fetchCharacterInfoRoute.handleRoute(1);
        verify(mockDispatcher, times(1)).
                dispatchEventFailure(eq(EventType.EVENT_TYPE_MARVEL_CHARACTER_INFO_FETCHED),
                        eq(EVENT_ERROR_TYPE_GENERAL),
                        isNull());
    }

    @Test
    public void testFetchCharacterInfoRouteSuccess() {
        mockFetchMarvelCharacterInfo(HttpURLConnection.HTTP_OK);

        fetchCharacterInfoRoute.handleRoute(1);
        verify(mockDispatcher, times(0)).dispatchEventFailure(eq(EventType.EVENT_TYPE_MARVEL_CHARACTER_INFO_FETCHED), any(), any());
        verify(mockDispatcher, times(1)).dispatchEvent(eq(EventType.EVENT_TYPE_MARVEL_CHARACTER_INFO_FETCHED), eq(routeRes));
    }

    /*
     * Mocks
     */

    private void mockFetchMarvelCharacterInfo(int statusCode) {
        if (statusCode == INTERNAL_ERROR_CODE || statusCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
            routeRes = null;
        } else {
            routeRes = new MarvelCharacterInfoModel();
        }

        doAnswer(invocation -> {
            IAPIResponse<MarvelCharacterInfoModel> callback = invocation.getArgument(1);
            callback.onResponse(statusCode, routeRes);
            return null;
        }).when(mockApiClient).fetchMarvelCharacterInfo(anyInt(), any());
    }
}
