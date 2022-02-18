package com.example.booklisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(@NonNull Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_item, parent, false
            );
        }

        Book book = getItem(position);

        ((TextView)listItemView.findViewById(R.id.title)).setText(book.getTitle());

        ((TextView)listItemView.findViewById(R.id.authors)).setText(book.getAuthors());

        ((TextView)listItemView.findViewById(R.id.language)).setText(book.getLanguage());

        ((RatingBar)listItemView.findViewById(R.id.rating)).setRating((float) book.getRating());

        return listItemView;

    }
}
