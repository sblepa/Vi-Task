package com.vi.vimarvel.view.image;

import android.os.Handler;
import android.os.Looper;

import com.vi.vimarvel.store.api.images.ImageFileDownloader;

import androidx.databinding.BindingAdapter;

public class ImageHelper {

    @BindingAdapter("imagesrc")
    public static void loadGridImage(MarvelImageView imageView, String imageUrl){
        if (imageUrl == null) {
            return;
        }

        imageView.setImageDrawable(null);
        imageView.setUrl(imageUrl);
        ImageFileDownloader downloader = ImageFileDownloader.getInstance();
        downloader.downloadImage(imageUrl, (responseCode, imageBitmap) -> {
            new Handler(Looper.getMainLooper()).post(() -> {
                if (imageBitmap != null && imageView.getUrl().equals(imageUrl)) {
                    imageView.setImageBitmap(imageBitmap);
                }
            });
        });
    }
}
