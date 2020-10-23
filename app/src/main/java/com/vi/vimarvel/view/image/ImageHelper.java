package com.vi.vimarvel.view.image;

import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.vi.vimarvel.store.api.images.ImageFileDownloader;

import androidx.databinding.BindingAdapter;

public class ImageHelper {

    @BindingAdapter("imagesrc")
    public static void loadGridImage(ImageView imageView, String imageUrl){
        if (imageUrl == null) {
            return;
        }

        imageView.setImageDrawable(null);
        ImageFileDownloader downloader = ImageFileDownloader.getInstance();
        downloader.downloadImage(imageUrl, (responseCode, imageBitmap) -> {
            new Handler(Looper.getMainLooper()).post(() -> {
                if (imageBitmap != null) {
                    imageView.setImageBitmap(imageBitmap);
                }
            });
        });
    }
}
