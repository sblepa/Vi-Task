package com.vi.vimarvel.store.models;

import java.util.ArrayList;
import java.util.Objects;

public class MarvelCharacterInfoModel extends MarvelCharacterModel {

    private static final String EMPTY_ELEMENT = "-";

    private Biography biography;

    private class Biography {
        private String fullName;
        private ArrayList<String> aliases;
        private String placeOfBirth;

    }

    @Override
    public String getImageUrl() {
        return this.images != null ? this.images.lg: null;
    }

    public String getFullName() {
        if (biography == null) {
            return "";
        }

        return biography.fullName.equals(EMPTY_ELEMENT)? "" : biography.fullName;
    }

    public String getAlias() {
        if (biography == null) {
            return "";
        }

        ArrayList<String> filteredAliases = filterEmptyValues(biography.aliases);

        if (filteredAliases == null || filteredAliases.size() == 0) {
            return "";
        }

        // Return the first alias
        return filteredAliases.get(0);
    }

    public String getPlaceOfBirth() {
        if (biography == null) {
            return "";
        }

        return biography.placeOfBirth.equals(EMPTY_ELEMENT)? "" : biography.placeOfBirth;
    }

    /*
     * Internal
     */

    private ArrayList<String> filterEmptyValues(ArrayList<String> list) {
        if (list == null) {
            return null;
        }

        ArrayList<String> res = new ArrayList<>();
        for (String val: list) {
            if (!Objects.equals(val, EMPTY_ELEMENT)) {
                res.add(val);
            }
        }

        return res;
    }
}
