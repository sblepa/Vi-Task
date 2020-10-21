package com.vi.vimarvel.store.models;

public class MarvelCharacterModel {

    private static final String IMAGE_SIZE = "portrait_xlarge";

    private int id;
    private String name;
    private Thumbnail thumbnail;

    private class Thumbnail {
        private String path;
        private String extension;
    }

    public String getImageUrl() {
        return String.format("%s/%s.%s",thumbnail.path, IMAGE_SIZE,  thumbnail.extension);
    }
}
