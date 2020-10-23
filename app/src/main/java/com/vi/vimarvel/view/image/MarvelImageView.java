package com.vi.vimarvel.view.image;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class MarvelImageView extends AppCompatImageView {

    private String url;

    public MarvelImageView(Context context) {
        super(context);
    }

    public MarvelImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarvelImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
