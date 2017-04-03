package com.abdulaziz.moviesapp.ui.home;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.abdulaziz.moviesapp.R;
import com.abdulaziz.moviesapp.ui.home.movie.MovieFragment;
import com.abdulaziz.moviesapp.ui.home.movies.MoviesFragment;
import com.abdulaziz.moviesapp.utils.PagerAdapter;

public class HomeActivity extends AppCompatActivity implements
        MoviesFragment.OnMoviesFragmentInteractionListener,
        MovieFragment.OnMovieFragmentInteractionListener{

    private ViewPager viewPager;
    private PagerAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        init();

    }

    private void init(){
        //show movies fragment
        MoviesFragment moviesFragment = MoviesFragment.newInstance();

        getSupportFragmentManager().beginTransaction().add(R.id.activity_home, moviesFragment).commit();
    }

    @Override
    public void onMoviesFragmentInteraction(String action, String message) {
        if(action.equals("show_movie")){
            Log.i("home", "onMoviesFragmentInteraction: "+message);
            MovieFragment movieFragment = MovieFragment.newInstance(message);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.activity_home, movieFragment);
            transaction.addToBackStack(null);

            transaction.commit();
        }
    }

    @Override
    public void onMovieFragmentInteraction(String action, String message) {

    }
}
