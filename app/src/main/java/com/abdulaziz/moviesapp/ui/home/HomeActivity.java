package com.abdulaziz.moviesapp.ui.home;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.abdulaziz.moviesapp.R;
import com.abdulaziz.moviesapp.ui.home.movies.MoviesFragment;
import com.abdulaziz.moviesapp.utils.PagerAdapter;

public class HomeActivity extends AppCompatActivity implements MoviesFragment.OnMoviesFragmentInteractionListener{

    private ViewPager viewPager;
    private PagerAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        init();

    }

    private void init(){
        //defining the view pager for fragments
        viewPager = (ViewPager) findViewById(R.id.home_view_pager);

        fragmentAdapter = new PagerAdapter(getSupportFragmentManager());

        fragmentAdapter.addFragment(MoviesFragment.newInstance());

        viewPager.setAdapter(fragmentAdapter);



    }

    @Override
    public void onMoviesFragmentInteraction(String action, String message) {

    }
}
