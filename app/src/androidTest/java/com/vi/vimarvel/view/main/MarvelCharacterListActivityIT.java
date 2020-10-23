package com.vi.vimarvel.view.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.vi.vimarvel.MockApplication;
import com.vi.vimarvel.R;
import com.vi.vimarvel.store.api.IAPIResponse;
import com.vi.vimarvel.store.models.MarvelCharacterModel;

import org.hamcrest.Matcher;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.SmallTest;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.vi.vimarvel.helpers.EspressoHelpers.recyclerViewHasItemsCount;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@SmallTest
public class MarvelCharacterListActivityIT {

    @Test
    public void testNoImages() {
        mockGetCharacterList(0);
        launchActivity();
        onView(withId(R.id.characters_recycler_view)).perform(waitFor(1000));
        onView(withId(R.id.characters_recycler_view)).check(recyclerViewHasItemsCount(0));
    }

    @Test
    public void testFewImages() {
        mockGetCharacterList(4);
        launchActivity();
        onView(withId(R.id.characters_recycler_view)).perform(waitForImages());
        onView(withId(R.id.characters_recycler_view)).check(recyclerViewHasItemsCount(4));
    }

    @Test
    public void testMultipleImages() {
        mockGetCharacterList(20);
        launchActivity();
        onView(withId(R.id.characters_recycler_view)).perform(waitForImages());
        onView(withId(R.id.characters_recycler_view)).check(recyclerViewHasItemsCount(20));

        // Scroll to last image
        onView(withId(R.id.characters_recycler_view)).perform(RecyclerViewActions.scrollToPosition(19));
    }

    /*
     * Mocks
     */

    private void mockGetCharacterList(int itemCount) {
        Context context = getInstrumentation().getTargetContext();

        ArrayList<MarvelCharacterModel> responseData = null;
        if (itemCount > 0) {
            responseData = new ArrayList<>();
            for (int i = 0; i < itemCount; i++) {
                MarvelCharacterModel character = new MarvelCharacterModel();
                character.addImage(String.format("http://app.vi.com/image%d", i));
                responseData.add(character);
            }
        }

        ArrayList<MarvelCharacterModel> finalResponseData = responseData;
        doAnswer(invocation -> {
            IAPIResponse<ArrayList<MarvelCharacterModel>> callback = invocation.getArgument(0);
            callback.onResponse(HttpURLConnection.HTTP_OK, finalResponseData);
            return null;
        }).when(MockApplication.getMockAPIClient()).fetchMarvelCharacters(any());

        Call mockCall = mock(Call.class);
        doAnswer(invocation -> {
            byte[] imageBytes = getBytesForImage(context, R.drawable.test_marvel);
            Response res = new Response.Builder().
                    request(new Request.Builder().url("http://app.vi.com/test").build()).
                    body(ResponseBody.create(
                            MediaType.get("image/jpeg"),
                            imageBytes)).
                    protocol(Protocol.HTTP_1_0).
                    code(HttpURLConnection.HTTP_OK).
                    message("OK").
                    build();

            Callback callback = invocation.getArgument(0);
            callback.onResponse(mockCall, res);
            return null;
        }).when(mockCall).enqueue(any());
        doReturn(mockCall).when(MockApplication.getMockHttpClient()).newCall(any());
    }

    /*
     * Internal
     */

    private ViewAction waitFor(long milliseconds) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isDisplayed();
            }

            @Override
            public String getDescription() {
                return "wait for character_recycler_view to ahve at list 1 item";
            }

            @Override
            public void perform(UiController uiController, View view) {

                long startTime = System.currentTimeMillis();
                long endTime = startTime + milliseconds;
                do {
                    uiController.loopMainThreadForAtLeast(100);
                } while (System.currentTimeMillis() < endTime);
            }
        };
    }

    private ViewAction waitForImages() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isDisplayed();
            }

            @Override
            public String getDescription() {
                return "wait for character_recycler_view to ahve at list 1 item";
            }

            @Override
            public void perform(UiController uiController, View view) {
                RecyclerView recyclerView = (RecyclerView)view;
                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                if (adapter == null) {
                    return;
                }

                // 5 seconds timeout
                long startTime = System.currentTimeMillis();
                long endTime = startTime + 1000;
                do {
                    uiController.loopMainThreadForAtLeast(100);
                    if (adapter.getItemCount() > 0) {
                        return;
                    }
                } while (System.currentTimeMillis() < endTime);
            }
        };
    }

    private byte[] getBytesForImage(Context context, int imageId) {
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), imageId);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private void launchActivity() {
        ActivityScenario.launch(MarvelCharacterListActivity.class);
    }
}
