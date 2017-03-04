package com.alexoladele.testingshit;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Alex Oladele
 * BookyApp
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.BookViewHolder> {
    List<BookModel> books;

    public RecyclerViewAdapter(List<BookModel> books) {
        this.books = books;
    }

//    =====================     OVERRIDED METHODS     =====================
    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_row, parent, false);
        BookViewHolder bookViewHolder = new BookViewHolder(v);
        return bookViewHolder;
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        holder.bookTitle.setText("Title: " + books.get(position).getTitle());
        holder.authorName.setText("Author: " + books.get(position).getAuthor());
        holder.md5.setText("MD5: " + books.get(position).getMd5());
    }


    @Override
    public int getItemCount() {
        return books.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    //    =====================     INNER CLASS     =====================
    public static class BookViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView bookTitle;
        TextView authorName;
        TextView md5;

        BookViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_list);
            bookTitle = (TextView) itemView.findViewById(R.id.book_title);
            authorName = (TextView) itemView.findViewById(R.id.author_name);
            md5 = (TextView) itemView.findViewById(R.id.md5);
        }

    }
}
