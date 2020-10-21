package com.vi.vimarvel.store.api.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.util.Map;

import androidx.collection.LruCache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ImageFileDownloader {

    public static final int INTERNAL_ERROR_CODE = -1;

    private final int CACHE_SIZE = 20 * 1024 * 1024; // Set cache size to 20MiB
    private final String CACHE_THREAD_NAME = "cacheHandler";

    private final OkHttpClient imageDownloadClient;
    private LruCache<String, byte[]> memoryCache;
    private File imageCache;
    private Handler cacheHandler;

    private static ImageFileDownloader sharedInstance;

    public static void initInstance(Context context) {
        sharedInstance = new ImageFileDownloader(context);
    }

    public static ImageFileDownloader getInstance() {
        return sharedInstance;
    }

    private ImageFileDownloader(Context context) {
        imageDownloadClient = new OkHttpClient();

        initCache(context);
    }

    public void downloadImage(String url, IImageResponse callback) {
        HttpUrl httpUrl = HttpUrl.parse(url);
        if (httpUrl == null) {
            callback.onImageDownloaded(INTERNAL_ERROR_CODE, null);
            return;
        }

        synchronized (CACHE_THREAD_NAME) {
            byte[] imageBytes = memoryCache.get(url);
            if (imageBytes != null) {
                callback.onImageDownloaded(HttpURLConnection.HTTP_OK, BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));
                return;
            }
        }

        Request request = new Request.Builder()
                .url(httpUrl.url())
                .get()
                .build();

        imageDownloadClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onImageDownloaded(INTERNAL_ERROR_CODE, null);
                }
            }

            @Override
            public void onResponse(Call call, Response response) {
                Bitmap imageBitmap = null;
                ResponseBody body = response.body();
                if (body != null) {
                    imageBitmap = BitmapFactory.decodeStream(body.byteStream());
                    if (imageBitmap != null) {
                        saveToCache(url, imageBitmap);
                    }
                }

                if (callback != null) {
                    callback.onImageDownloaded(response.code(), imageBitmap);
                }
            }
        });
    }

    /*
     * Internal
     */

    @SuppressWarnings("unchecked")
    private void initCache(Context context) {
        HandlerThread cacheThread = new HandlerThread(CACHE_THREAD_NAME);
        cacheThread.start();
        Looper cacheLooper = cacheThread.getLooper();
        cacheHandler = new Handler(cacheLooper);

        memoryCache = new LruCache<String, byte[]>(CACHE_SIZE) {
            protected int sizeOf(String key, byte[] value) {
                return value.length;
            }
        };

        // Handle disc cache on a dedicated thread
        cacheHandler.post(() -> {
            File cacheDir = context.getCacheDir();
            imageCache = new File(cacheDir, "images.bin");
            if (!imageCache.exists()) {
                try {
                    boolean res = imageCache.createNewFile();
                    if (!res) {
                        // TODO: Handle failed allocating an image cache directory
                    }
                } catch (IOException e) {
                    // TODO: Handle error creating file for cache
                    e.printStackTrace();
                }
            } else {
                // Recreate cache from disk
                try (FileInputStream in = new FileInputStream(imageCache)) {
                    ObjectInputStream objIn = new ObjectInputStream(in);
                    Map<String, byte[]> cacheMap = (Map<String, byte[]>) objIn.readObject();
                    for (Map.Entry<String, byte[]> entry : cacheMap.entrySet()) {
                        memoryCache.put(entry.getKey(), entry.getValue());
                    }

                    objIn.close();
                } catch (IOException | ClassNotFoundException e) {
                    // TODO: Handle error fetching cache
                    e.printStackTrace();
                }
            }
        });
    }

    private void saveToCache(String url, Bitmap imageBitmap) {
        synchronized (CACHE_THREAD_NAME) {
            if (memoryCache.get(url) == null) {
                memoryCache.put(url, bitmapToByteArray(imageBitmap));
            }
        }
        cacheHandler.post(() -> {
                try (FileOutputStream out = new FileOutputStream(imageCache)) {
                    ObjectOutputStream objOut = new ObjectOutputStream(out);
                    objOut.writeObject(memoryCache.snapshot());
                    objOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        });
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
