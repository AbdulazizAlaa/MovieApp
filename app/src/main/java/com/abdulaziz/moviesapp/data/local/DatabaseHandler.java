package com.abdulaziz.moviesapp.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.abdulaziz.moviesapp.data.model.Movie;

import java.util.ArrayList;

/**
 * Created by abdulaziz on 4/3/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper{

    private static final String name = "movie_app_db";

    private static final int version = 1;

    private static final String MOVIE_TABLE = "movie";

    private static final String MOVIE_ID = "id";
    private static final String MOVIE_NAME = "title";
    private static final String MOVIE_POSTER = "poster_path";
    private static final String MOVIE_RATING = "vote_average";

    public DatabaseHandler(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOVIE_TABLE = "CREATE TABLE " + MOVIE_TABLE + "( " +
                MOVIE_ID + " INTEGER PRIMARY KEY, " + MOVIE_NAME + " TEXT, " +
                MOVIE_POSTER + " TEXT, " + MOVIE_RATING + " TEXT" + ")";
        db.execSQL(CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MOVIE_TABLE);

        onCreate(db);
    }

    public void addMovie(Movie movie){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MOVIE_ID, movie.getId());
        values.put(MOVIE_NAME, movie.getTitle());
        values.put(MOVIE_POSTER, movie.getPosterURLName());
        values.put(MOVIE_RATING, movie.getRating());

        db.insert(MOVIE_TABLE, null, values);
        db.close();
    }

    public Movie getMovie(String id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(MOVIE_TABLE,
                                new String[]{MOVIE_ID, MOVIE_NAME, MOVIE_POSTER, MOVIE_RATING},
                                MOVIE_ID+"=?", new String[]{id}, null, null, null, null);

        Movie movie = null;
        if(cursor != null)
            cursor.moveToFirst();

        if(cursor.getCount() > 0){

            movie = new Movie(cursor.getString(cursor.getColumnIndex(MOVIE_ID)),
                    cursor.getString(cursor.getColumnIndex(MOVIE_NAME)),
                    cursor.getString(cursor.getColumnIndex(MOVIE_POSTER)),
                    cursor.getString(cursor.getColumnIndex(MOVIE_RATING)));
        }

        return movie;
    }


    public ArrayList<Movie> getMovies(){
        ArrayList<Movie> movies = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM " + MOVIE_TABLE;

        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do{
                Movie movie = new Movie(cursor.getString(cursor.getColumnIndex(MOVIE_ID)),
                        cursor.getString(cursor.getColumnIndex(MOVIE_NAME)),
                        cursor.getString(cursor.getColumnIndex(MOVIE_POSTER)),
                        cursor.getString(cursor.getColumnIndex(MOVIE_RATING)));

                movies.add(movie);
            }while (cursor.moveToNext());
        }


        return movies;
    }

    public void deleteMovie(Movie movie){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(MOVIE_TABLE, MOVIE_ID+"=?", new String[]{movie.getId()});
        db.close();
    }
}
