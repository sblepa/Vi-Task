package com.vi.vimarvel.store.api;

import com.vi.vimarvel.store.models.MarvelCharacterInfoModel;
import com.vi.vimarvel.store.models.MarvelCharacterModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class APIClient {

    public static final int INTERNAL_ERROR_CODE = -1;

    private final Retrofit retrofit;

    public APIClient() {

        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(MarvelService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public void fetchMarvelCharacters(IAPIResponse<ArrayList<MarvelCharacterModel>> callback) {
        MarvelService serviceCall = retrofit.create(MarvelService.class);
        Call<ArrayList<MarvelCharacterModel>> call = serviceCall.getMarvelCharacters();

        call.enqueue(new Callback<ArrayList<MarvelCharacterModel>>() {
            @Override
            public void onResponse(Call<ArrayList<MarvelCharacterModel>> call,
                                   Response<ArrayList<MarvelCharacterModel>> response) {
                callback.onResponse(response.code(), response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<MarvelCharacterModel>> call, Throwable t) {
                callback.onResponse(INTERNAL_ERROR_CODE, null);
            }
        });
    }

    public void fetchMarvelCharacterInfo(Integer id, IAPIResponse<MarvelCharacterInfoModel> callback) {
        MarvelService serviceCall = retrofit.create(MarvelService.class);
        Call<MarvelCharacterInfoModel> call = serviceCall.getMarvelCharacterInfo(id);

        call.enqueue(new Callback<MarvelCharacterInfoModel>() {
            @Override
            public void onResponse(Call<MarvelCharacterInfoModel> call,
                                   Response<MarvelCharacterInfoModel> response) {
                callback.onResponse(response.code(), response.body());
            }

            @Override
            public void onFailure(Call<MarvelCharacterInfoModel> call, Throwable t) {
                callback.onResponse(INTERNAL_ERROR_CODE, null);
            }
        });
    }

    /*
     *  Internal Classes & Interfaces
     */

    // Marvel service
    private interface MarvelService {

        String BASE_URL = "https://akabab.github.io/superhero-api/api/";

        @GET("all.json")
        Call<ArrayList<MarvelCharacterModel>> getMarvelCharacters();

        @GET("id/{id}.json")
        Call<MarvelCharacterInfoModel> getMarvelCharacterInfo(@Path(value = "id", encoded = true) Integer characterId);
    }
}
