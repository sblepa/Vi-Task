package com.vi.vimarvel.store.api.images;

import android.graphics.Bitmap;

public interface IImageResponse {
    void onImageDownloaded(int responseCode, Bitmap imageBitmap);
}
