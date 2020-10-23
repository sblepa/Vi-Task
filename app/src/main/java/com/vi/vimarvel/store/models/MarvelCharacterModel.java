package com.vi.vimarvel.store.models;

import androidx.annotation.VisibleForTesting;

public class MarvelCharacterModel {

    private int id;
    protected Images images;

    protected class Images {
        private String xs;
        private String sm;
        private String md;
        protected String lg;
    }

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return images != null ? images.md: null;
    }

    @VisibleForTesting
    public void addImage(String url) {
        if (images == null) {
            images = new Images();
            images.md = url;
        }
    }
}
