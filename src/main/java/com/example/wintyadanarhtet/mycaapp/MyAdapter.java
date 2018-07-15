package com.example.wintyadanarhtet.mycaapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends ArrayAdapter<String> {
    int resource;
    private List<String> items;

    public MyAdapter(@NonNull Context context, int resource, @NonNull List<String> items) {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(resource, null);
        String bookid = items.get(position);
        TextView e = (TextView) v.findViewById(R.id.textView);
        e.setText(bookid);

        final ImageView image = (ImageView) v.findViewById(R.id.imageView2);
        if (bookid != null) {
            new AsyncTask<String, Void, Bitmap>() {

                @Override
                protected Bitmap doInBackground(String... params) {
                    Book newB = Book.getBook(params[0]);
                    return Book.getPhoto(true,newB.get("ISBN"));
                }
                @Override
                protected void onPostExecute(Bitmap result) {

                    image.setImageBitmap(result);
                }
            }.execute(bookid);
        }
        return v;
    }


}

