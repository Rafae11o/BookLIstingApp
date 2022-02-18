package com.example.booklisting;

import androidx.annotation.Nullable;
import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    private static final String LOG_TAG = BookLoader.class.getName();

    private final String url;

    public BookLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Book> loadInBackground() {
        if (url == null) {
            return null;
        }
        return QueryUtils.fetchBookData(url);
    }
}
