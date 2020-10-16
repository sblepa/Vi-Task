package com.vi.vimarvel.store.api;

import com.vi.vimarvel.BuildConfig;
import com.vi.vimarvel.store.models.MarvelCharacterModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class APIClient {

    private final int INTERNAL_ERROR_CODE = -1;
    private final int FETCH_MARVEL_CHARACTERS_LIMIT = 20;

    private final Retrofit retrofit;
    private String apikey;
    private final int ts;
    private final String hash;

    public APIClient() {

        // API Key should be stored more securely (obfuscation etc) that that and added to .gitignore to not be
        // uploaded to the git repository, its there for the task simplicity
        apikey = BuildConfig.MARVEL_API_KEY;
        ts = BuildConfig.MARVEL_API_TS;
        hash = BuildConfig.MARVEL_API_HASH;

        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(MarvelService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public void fetchMarvelCharacters(IAPIResponse<ArrayList<MarvelCharacterModel>> callback) {
        MarvelService serviceCall = retrofit.create(MarvelService.class);
        Call<MarvelRespose<ArrayList<MarvelCharacterModel>>> call = serviceCall.getMarvelCharacters(apikey, ts, hash, FETCH_MARVEL_CHARACTERS_LIMIT);

        call.enqueue(new Callback<MarvelRespose<ArrayList<MarvelCharacterModel>>>() {
            @Override
            public void onResponse(Call<MarvelRespose<ArrayList<MarvelCharacterModel>>> call,
                                   Response<MarvelRespose<ArrayList<MarvelCharacterModel>>> response) {
                ArrayList<MarvelCharacterModel> data = null;
                if (response.body() != null) {
                    data = response.body().getResults();
                }

                callback.onResponse(response.code(), data);
            }

            @Override
            public void onFailure(Call<MarvelRespose<ArrayList<MarvelCharacterModel>>> call, Throwable t) {
                callback.onResponse(INTERNAL_ERROR_CODE, null);
            }
        });
    }

    /*
     *  Internal Classes & Interfaces
     */

    // Marvel API Data response
    private class MarvelRespose<T> {
        private int code;
        private String status;
        private MarvelResponseData data;

        private class MarvelResponseData {
            private T results;
        }

        T getResults() {
            return data.results;
        }
    }

    // Marvel service
    private interface MarvelService {

        String BASE_URL = "https://gateway.marvel.com:443";

        @GET("/v1/public/characters")
        Call<MarvelRespose<ArrayList<MarvelCharacterModel>>> getMarvelCharacters(@Query("apikey") String apiKEy,
                                                                                 @Query("ts") int ts,
                                                                                 @Query("hash") String hash,
                                                                                 @Query("limit") int limit);
    }
}
