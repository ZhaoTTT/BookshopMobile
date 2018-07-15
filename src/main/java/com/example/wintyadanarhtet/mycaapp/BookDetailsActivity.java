package com.example.wintyadanarhtet.mycaapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.StrictMode;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class BookDetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        Intent i = getIntent();
        final String bookid = i.getStringExtra("bookid");
        new AsyncTask<String, Void, Book>() {
            @Override
            protected Book doInBackground(String... params) {

                return Book.getBook(params[0]);
            }

            @Override
            protected void onPostExecute(Book result) {

                show(result);
            }
        }.execute(bookid);

        Button edit = (Button) findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookDetailsActivity.this, UpdateActivity.class);
                intent.putExtra("id", bookid);
                startActivity(intent);
            }
        });
    }

    void show(Book book) {
        int[] ids = {R.id.editText1, R.id.editText2, R.id.editText3, R.id.editText4, R.id.editText5, R.id.editText6, R.id.editText7};
        String[] keys = {"BookID", "Title", "CategoryID", "ISBN", "Author", "Stock", "Price"};
        for (int i = 0; i < keys.length; i++) {
            EditText e = (EditText) findViewById(ids[i]);
            e.setText(book.get(keys[i]));
            e.setFocusable(false);
        }
        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                Book newB = Book.getBook(params[0]);
                return Book.getPhoto(false,params[0]);
            }
            @Override
            protected void onPostExecute(Bitmap result) {
                ImageView image = (ImageView) findViewById(R.id.imageView);
                image.setImageBitmap(result);
            }
        }.execute(book.get("ISBN"));
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}


