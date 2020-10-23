package com.vi.vimarvel.store.models;

public class MarvelCharacterModel {

    private static final String IMAGE_SIZE = "portrait_xlarge";

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
}
