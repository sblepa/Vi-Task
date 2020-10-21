package com.vi.vimarvel.store.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.vi.vimarvel.BuildConfig;
import com.vi.vimarvel.store.api.images.IImageResponse;
import com.vi.vimarvel.store.models.MarvelCharacterModel;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.HttpUrl;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class APIClient {

    public static final int INTERNAL_ERROR_CODE = -1;

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
        Call<JsonElement> call = serviceCall.getMarvelCharacters(apikey, ts, hash, FETCH_MARVEL_CHARACTERS_LIMIT);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call,
                                   Response<JsonElement> response) {
                if (response.body() != null) {
                    JsonElement body = response.body();

                    // Deserialize
                    try {
                        JsonArray results = getResults(body);
                        Type listType = new TypeToken<ArrayList<MarvelCharacterModel>>(){}.getType();
                        ArrayList<MarvelCharacterModel> res = (new Gson()).fromJson(results, listType);
                        callback.onResponse(response.code(), res);
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        callback.onResponse(INTERNAL_ERROR_CODE, null);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                callback.onResponse(INTERNAL_ERROR_CODE, null);
            }
        });
    }

    /*
     *  Internal Classes & Interfaces
     */

    private JsonArray getResults(JsonElement response) throws IOException, JSONException {
        JsonObject root = response.getAsJsonObject();
        return root.getAsJsonObject("data").getAsJsonArray("results");
    }

    // Marvel service
    private interface MarvelService {

        String BASE_URL = "https://gateway.marvel.com:443";

        @GET("/v1/public/characters")
        Call<JsonElement> getMarvelCharacters(@Query("apikey") String apiKEy,
                                              @Query("ts") int ts,
                                              @Query("hash") String hash,
                                              @Query("limit") int limit);
    }
}
