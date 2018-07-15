package com.example.wintyadanarhtet.mycaapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdateActivity extends AppCompatActivity {
    final static int[] views = {R.id.editText8, R.id.editText9, R.id.editText10, R.id.editText11, R.id.editText12, R.id.editText13, R.id.editText14};
    final static String[] key = {"BookID", "Title", "CategoryID", "ISBN", "Author", "Stock", "Price"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Intent i = getIntent();
        String bookid = i.getStringExtra("id");
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

        Button update = (Button) findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book b = new Book();
                for (int i = 0; i < views.length; i++) {
                    EditText t = (EditText) findViewById(views[i]);
                    b.put(key[i], t.getText().toString());
                }
                new AsyncTask<Book, Void, Void>() {
                    @Override
                    protected Void doInBackground(Book... params) {
                        Book.updateBook(params[0]);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        finish();
                        Intent i = new Intent(UpdateActivity.this, SearchActivity.class);
                        startActivity(i);
                    }
                }.execute(b);
            }
        });

    }

    void show(Book book) {
        int[] ids = {R.id.editText8, R.id.editText9, R.id.editText10, R.id.editText11, R.id.editText12, R.id.editText13, R.id.editText14};
        String[] keys = {"BookID", "Title", "CategoryID", "ISBN", "Author", "Stock", "Price"};

        Log.i("Title", "show: ");
        for (int i = 0; i < keys.length; i++) {
            EditText e = (EditText) findViewById(ids[i]);
            e.setText(book.get(keys[i]));
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
