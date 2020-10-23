package com.vi.vimarvel.store.routes;

import com.vi.vimarvel.dispatcher.EventErrorType;
import com.vi.vimarvel.dispatcher.EventType;
import com.vi.vimarvel.store.api.IAPIResponse;
import com.vi.vimarvel.store.models.MarvelCharacterModel;

import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import static com.vi.vimarvel.store.api.APIClient.INTERNAL_ERROR_CODE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class FetchMarvelCharactersRouteTest extends BaseRouteTest {

    private FetchMarvelCharactersRoute fetchMarvelCharactersRoute;
    private ArrayList<MarvelCharacterModel> routeRes;

    @Before
    public void setup() {
        super.setup();

        fetchMarvelCharactersRoute = new FetchMarvelCharactersRoute(mockApiClient, mockDispatcher);
    }

    @Test
    public void testFetchMarvelCharactersRouteFail() {
        mockFetchMarvelCharacters(INTERNAL_ERROR_CODE);

        fetchMarvelCharactersRoute.handleRoute(null);
        verify(mockDispatcher, times(1)).dispatchEventFailure(
                eq(EventType.EVENT_TYPE_MARVEL_CHARACTERS_FETCHED),
                eq(EventErrorType.EVENT_ERROR_TYPE_INTERNAL),
                isNull());

        resetMocks();

        mockFetchMarvelCharacters(HttpURLConnection.HTTP_BAD_REQUEST);

        fetchMarvelCharactersRoute.handleRoute(null);
        verify(mockDispatcher, times(1)).dispatchEventFailure(
                eq(EventType.EVENT_TYPE_MARVEL_CHARACTERS_FETCHED),
                eq(EventErrorType.EVENT_ERROR_TYPE_GENERAL),
                isNull());
    }

    @Test
    public void testFetchMarvelCharactersRouteSuccess() {
        mockFetchMarvelCharacters(HttpURLConnection.HTTP_OK);

        fetchMarvelCharactersRoute.handleRoute(null);
        verify(mockDispatcher, times(0)).dispatchEventFailure(any(), any(), any());
        verify(mockDispatcher, times(1)).dispatchEvent(eq(EventType.EVENT_TYPE_MARVEL_CHARACTERS_FETCHED), eq(routeRes));
    }

    /*
     * Mocks
     */

    private void mockFetchMarvelCharacters(int statusCode) {
        if (statusCode == INTERNAL_ERROR_CODE || statusCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
            routeRes = null;
        } else {
            routeRes = new ArrayList<MarvelCharacterModel>(){{add(new MarvelCharacterModel());}};
        }

        doAnswer(invocation -> {
            IAPIResponse<ArrayList<MarvelCharacterModel>> callback = invocation.getArgument(0);
            callback.onResponse(statusCode, routeRes);
            return null;
        }
        ).when(mockApiClient).fetchMarvelCharacters(any());
    }
}
