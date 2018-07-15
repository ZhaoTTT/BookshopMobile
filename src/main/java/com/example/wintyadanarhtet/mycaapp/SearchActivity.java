package com.example.wintyadanarhtet.mycaapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends ListActivity {

    EditText searchText;
    Button searchButton;
    ListView searchList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchText = (EditText) findViewById(R.id.et_search);
        searchButton = (Button) findViewById(R.id.btn_search);
        searchList = (ListView) findViewById(R.id.list);

        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER && keyEvent != null)) {
                    return true;
                }
                return false;
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {
                final String searchInput = searchText.getText().toString();

                new AsyncTask<String, Void, List<String>>() {
                    @Override
                    protected List<String> doInBackground(String... params) {
                        if (searchInput == null) {
                            return Book.list();
                        } else {
                            return Book.searchByTitle(searchInput);
                        }
                    }

                    @Override
                    protected void onPostExecute(List<String> result) {
                        Intent intent = new Intent(SearchActivity.this, BookListActivity.class);
                        intent.putExtra("ListOfBooks", (ArrayList<String>) result);
                        startActivity(intent);
                    }
                }.execute();

            }

        });
    }}
/*        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... params) {
                final String searchInput = searchText.getText().toString();
                List<String> result = Book.searchByTitle(searchInput);
                return result;
            }

            @Override
            protected void onPostExecute(List<String> result) {
                List<String> bookList = result;
                MyAdapter adapter = new MyAdapter(SearchActivity.this, R.layout.item_book, bookList);
                searchList.setAdapter(adapter);
            }
        }.execute();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Intent intent = new Intent(this, BookDetailsActivity.class);
        intent.putExtra("bookID", item);
        startActivity(intent);
    }*/

    //}




