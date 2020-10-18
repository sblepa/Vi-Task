package com.vi.vimarvel.store.models;

public class MarvelCharacterModel {
    private int id;
    private String name;
    private Thumbnail thumbnail;

    private class Thumbnail {
        private String path;
        private String extension;
    }

    public String getImageUrl() {
        return thumbnail.path;
    }
}
