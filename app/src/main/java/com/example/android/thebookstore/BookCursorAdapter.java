package com.example.android.thebookstore;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.thebookstore.database.BookContract.BookEntry;

public class BookCursorAdapter extends CursorAdapter {

    private Uri mCurrentBookUri;

    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.store_list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        Button saleButton = (Button) view.findViewById(R.id.sale);
        saleButton.setTag(cursor.getPosition());
        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();

                cursor.moveToPosition(position);

                int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
                int id = cursor.getInt(idColumnIndex);
                int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_QUANTITY);
                int quantity = cursor.getInt(quantityColumnIndex);

                if (quantity > 0) {
                    quantity--;
                } else {
                    Toast toast = Toast.makeText(context, R.string.no_more_books, Toast.LENGTH_LONG);
                    toast.show();
                }

                ContentValues values = new ContentValues();
                values.put(BookEntry.COLUMN_BOOK_QUANTITY, quantity);

                Uri uri = ContentUris.withAppendedId(BookEntry.CONTENT_URI, id);
                int result = context.getContentResolver().update(uri, values, null, null);

            }
        });

        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView authorTextView = (TextView) view.findViewById(R.id.author);
        TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);

        int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_NAME);
        int authorColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_AUTHOR);
        int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_QUANTITY);
        int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PRICE);

        String bookName = cursor.getString(nameColumnIndex);
        String bookAuthor = cursor.getString(authorColumnIndex);
        String bookQuantity = String.valueOf(cursor.getInt(quantityColumnIndex));
        String bookPrice = String.valueOf(cursor.getInt(priceColumnIndex));

        nameTextView.setText(bookName);
        authorTextView.setText(bookAuthor);
        quantityTextView.setText(bookQuantity);
        priceTextView.setText(bookPrice);
    }
}
