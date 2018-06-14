package com.example.android.thebookstore;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.thebookstore.database.BookContract.BookEntry;
import com.example.android.thebookstore.database.BookDbHelper;

public class StoreListActivity extends AppCompatActivity {

    BookDbHelper mDbHelper = new BookDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);

        testGo();
    }

    private void testGo() {

        int rowsDeleted = getContentResolver().delete(BookEntry.CONTENT_URI, null, null);

        insertBook();

        Cursor cursor = queryData();

        try {
            if (cursor.moveToFirst()) {
                int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_NAME);
                String name = cursor.getString(nameColumnIndex);
                int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PRICE);
                String price = cursor.getString(priceColumnIndex);


                TextView mNameText = findViewById(R.id.book_name);
                mNameText.setText(name);
                TextView mPriceText = findViewById(R.id.book_price);
                mPriceText.setText(price);
            }
        } finally {
            cursor.close();
        }
    }

    private void insertBook() {
        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_BOOK_NAME, "Book Name 1");
        values.put(BookEntry.COLUMN_BOOK_AUTHOR, "Book Name 1");
        values.put(BookEntry.COLUMN_BOOK_PRICE, 7);
        values.put(BookEntry.COLUMN_BOOK_QUANTITY, 15);
        values.put(BookEntry.COLUMN_BOOK_SUPPLIER_NAME, "John Doe");
        values.put(BookEntry.COLUMN_BOOK_SUPPLIER_PHONE_NUMBER, "555-5555-5555");

        Uri newUri = getContentResolver().insert(BookEntry.CONTENT_URI, values);
    }

    private Cursor queryData() {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                BookEntry.COLUMN_BOOK_NAME,
                BookEntry.COLUMN_BOOK_PRICE
        };
        Cursor cursor = db.query(BookEntry.TABLE_NAME, projection, null, null, null, null, null);
        return cursor;
    }

}
