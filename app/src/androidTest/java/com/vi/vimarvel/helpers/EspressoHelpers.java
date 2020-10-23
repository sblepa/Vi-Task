package com.vi.vimarvel.helpers;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.ViewAssertion;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class EspressoHelpers {

    public static ViewAssertion recyclerViewHasItemsCount(final int count) {
        return (view, e) -> {
            if (!(view instanceof RecyclerView)) {
                throw e;
            }
            RecyclerView rv = (RecyclerView) view;
            assertNotNull(rv.getAdapter());
            assertEquals(count, rv.getAdapter().getItemCount());
        };
    }
}
