package com.example.booklisting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=android";

    private static final int BOOK_LOADER_ID = 1;

    private BookAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView bookListView = (ListView) findViewById(R.id.list);

        mAdapter = new BookAdapter(this, new ArrayList<>());

        bookListView.setAdapter(mAdapter);

        bookListView.setOnItemClickListener((adapterView, view, i, l) -> {
            Book book = mAdapter.getItem(i);
            Uri bookUri = Uri.parse(book.getPreviewLink());
            Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);
            startActivity(websiteIntent);
        });

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(BOOK_LOADER_ID, null, this);
    }


    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return new BookLoader(this, API_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        mAdapter.clear();
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }
}