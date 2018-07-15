package com.example.wintyadanarhtet.mycaapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Book extends HashMap<String, String> {
    final static String baseURL = "http://172.23.194.97/BookService/Service.svc/";
    //final static String baseURL ="http://192.168.1.75:8080/Service.svc/";
    final static String imageURL ="http://172.23.194.97/BookService/images/";
    public Book() {

    }

    public Book(String bookID, String Title, String categoryID, String ISBN, String author, String stock, String price) {
        put("BookID", bookID);
        put("Title", Title);
        put("CategoryID", categoryID);
        put("ISBN", ISBN);
        put("Author", author);
        put("Stock", stock);
        put("Price", price);

    }

    public static List<String> list() {
        List<String> list = new ArrayList<String>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL + "Book");
//        JSONArray a = JSONParser.getJSONArrayFromUrl(baseURL + "BookIds");
        try {
            for (int i = 0; i < a.length(); i++)
                list.add(a.getString(i));
        } catch (Exception e) {
            Log.e("Book.list()", "JSONArray error");
        }
        return (list);
    }

    public static Book getBook(String bookid) {
        //JSONObject b = JSONParser.getJSONFromUrl(baseURL + "BookIds/" + bookid);
        JSONObject b = JSONParser.getJSONFromUrl(baseURL + "Book/" + bookid);
        try {
//            return new Book(b.getString("Id"), b.getString("Title"),
//                    b.getString("CategoryID"), b.getString("Isbn"),
//                    b.getString("Author"), b.getString("Stock"),
//                    b.getString("Price"));
            return new Book(b.getString("BookID"), b.getString("Title"),
                    b.getString("CategoryID"), b.getString("ISBN"),
                    b.getString("Author"), b.getString("Stock"),
                    b.getString("Price"));
            //return new Book("1", "1", "1","1", "1","1","1");
        } catch (Exception e) {
            Log.e("Book.getBook()", "JSONArray error");
        }
        return (null);
    }
    public static Bitmap getPhoto(boolean thumbnail, String id) {
        try {
            URL url = (new URL(String.format("%s/%s.jpg",imageURL, id)));
            URLConnection conn = url.openConnection();
            InputStream ins = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(ins);
            ins.close();
            return bitmap;
        } catch (Exception e) {
            Log.e("Book.getPhoto()", "Bitmap error");
        }
        return(null);
    }

    public static void updateBook(Book b) {
        JSONObject jbook = new JSONObject();
        try {
            jbook.put("BookID", Integer.parseInt(b.get("BookID")));
            jbook.put("Title", b.get("Title"));
            jbook.put("CategoryID", Integer.parseInt(b.get("CategoryID")));
            jbook.put("ISBN", b.get("ISBN"));
            jbook.put("Author", b.get("Author"));
            jbook.put("Stock", Integer.parseInt(b.get("Author")));
            jbook.put("Price", Float.parseFloat(b.get("Price")));
        } catch (Exception e) {
        }
        String result = JSONParser.postStream("http://172.23.194.97/BookService/Service.svc/Update", jbook.toString());
    }

/*    public static String getTitle(String id){
        try {
            URL url = (new URL(String.format("%sBook/%s",baseURL, id)));
            URLConnection conn = url.openConnection();
            InputStream ins = conn.getInputStream();
//            Bitmap bitmap = BitmapFactory.decodeStream(ins);
            //JSONObject b = JSONParser.getJSONFromUrl(baseURL + "BookIds/" + id);
            JSONObject b = JSONParser.getJSONFromUrl(baseURL + "Book/" + id);
            String title=b.getString("Title");
            ins.close();
            return title;
        } catch (Exception e) {
            Log.e("Book.getTitle()", "Title error");
        }
        return(null);
    }*/

    public static List<String> searchByTitle(String searchInput){
        List<String> searchResult=new ArrayList<>();
        for(String bookID:list()){
            Book book=getBook(bookID);
            if(book.get("Title".toString().toLowerCase()).contains(searchInput.toLowerCase())){
                searchResult.add(book.get("bookId"));
            }
        }
        return searchResult;
    }
}

