package com.abdulaziz.moviesapp.data.remote;

import com.abdulaziz.moviesapp.data.model.Movie;
import com.abdulaziz.moviesapp.data.model.Response;
import com.abdulaziz.moviesapp.data.model.ReviewResponse;
import com.abdulaziz.moviesapp.data.model.VideoResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by abdulaziz on 3/3/17.
 */

public interface Service {


    @GET("/3/movie/popular")
    Call<Response> getPopularMovies(@Query("api_key") String api_key);

    @GET("/3/movie/top_rated")
    Call<Response> getTopRatedMovies(@Query("api_key") String api_key);

    @GET("/3/movie/{movie_id}")
    Call<Movie> getMovie(@Path("movie_id") String id, @Query("api_key") String api_key);

    @GET("/3/movie/{movie_id}/reviews")
    Call<ReviewResponse> getReviews(@Path("movie_id") String id, @Query("api_key") String api_key);

    @GET("/3/movie/{movie_id}/videos")
    Call<VideoResponse> getVideos(@Path("movie_id") String id, @Query("api_key") String api_key);

    class Creator{
        public static Retrofit retrofit;

        public static Service getService(){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();


            retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.themoviedb.org/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            return retrofit.create(Service.class);
        }
    }

}
