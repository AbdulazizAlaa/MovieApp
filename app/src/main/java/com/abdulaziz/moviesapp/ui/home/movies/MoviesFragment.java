package com.abdulaziz.moviesapp.ui.home.movies;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.abdulaziz.moviesapp.R;
import com.abdulaziz.moviesapp.data.model.Movie;
import com.abdulaziz.moviesapp.data.remote.Service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MoviesFragment.OnMoviesFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviesFragment extends Fragment {

    private final String api_key = "8f5f6df0585d0d2b95c786ab35858e78";
    public static final String TAG = "Movies_fragment";

    private ArrayList<Movie> movies;
    private RecyclerView moviesList;
    private MoviesAdapter adapter;
    private GridLayoutManager layoutManager;

    Callback<com.abdulaziz.moviesapp.data.model.Response> callbackHandler;//callback handler for movies requests

    private Toolbar toolbar;

    private OnMoviesFragmentInteractionListener mListener;

    public MoviesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MoviesFragment.
     */
    public static MoviesFragment newInstance() {
        MoviesFragment fragment = new MoviesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        init();
    }

    private void init(){
        Log.i(TAG, "init: ");

        //setting up toolbar
        toolbar = (Toolbar) getView().findViewById(R.id.movies_toolbar);
        toolbar.inflateMenu(R.menu.home_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_popular:
                        Log.i(TAG, "onMenuItemClick: popular");
                        Service.Creator.getService().getPopularMovies(api_key).enqueue(callbackHandler);
                        break;
                    case R.id.home_top_rated:
                        Log.i(TAG, "onMenuItemClick: top rated");
                        Service.Creator.getService().getTopRatedMovies(api_key).enqueue(callbackHandler);
                        break;
                    case R.id.home_fav:
                        Log.i(TAG, "onMenuItemClick: favourite");

                        break;
                }
                return false;
            }
        });

        //setting up movies adapter
        movies = new ArrayList<Movie>();
        adapter = new MoviesAdapter(getContext(), movies);
        moviesList = (RecyclerView) getView().findViewById(R.id.movies_recycler_view);

        layoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);

        moviesList.setLayoutManager(layoutManager);
        moviesList.setAdapter(adapter);

        adapter.setOnItemClickListener(new MoviesAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Movie movie) {
                Log.i(TAG, "onItemClick: "+movie.getTitle());
                mListener.onMoviesFragmentInteraction("show_movie", movie.getTitle());
            }
        });

        //making the popular movies request
       callbackHandler = new Callback<com.abdulaziz.moviesapp.data.model.Response>() {
            @Override
            public void onResponse(Call<com.abdulaziz.moviesapp.data.model.Response> call, Response<com.abdulaziz.moviesapp.data.model.Response> response) {
                Log.i(TAG, "onResponse: "+response.isSuccessful());
                Log.i(TAG, "onResponse: "+response.message());

                movies.clear();
                movies.addAll(response.body().getMovies());

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<com.abdulaziz.moviesapp.data.model.Response> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t.getMessage());
            }
        };

        Service.Creator.getService().getPopularMovies(api_key).enqueue(callbackHandler);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMoviesFragmentInteractionListener) {
            mListener = (OnMoviesFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMoviesFragmentInteractionListener {
        void onMoviesFragmentInteraction(String action, String message);
    }
}
