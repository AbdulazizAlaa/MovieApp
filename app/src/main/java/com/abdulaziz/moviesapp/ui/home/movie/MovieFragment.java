package com.abdulaziz.moviesapp.ui.home.movie;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abdulaziz.moviesapp.R;
import com.abdulaziz.moviesapp.data.local.DatabaseHandler;
import com.abdulaziz.moviesapp.data.model.Movie;
import com.abdulaziz.moviesapp.data.model.Response;
import com.abdulaziz.moviesapp.data.model.ReviewResponse;
import com.abdulaziz.moviesapp.data.model.VideoResponse;
import com.abdulaziz.moviesapp.data.remote.Service;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieFragment.OnMovieFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieFragment extends Fragment {

    private final String api_key = "8f5f6df0585d0d2b95c786ab35858e78";

    private TextView titleTV;
    private TextView voteCountTV;
    private TextView votePercentageTV;
    private TextView dateTV;
    private TextView genresTV;
    private TextView overviewTV;

    private TextView reviewAuthorTV;
    private TextView reviewContentTV;

    private ImageView posterIV;
    private ImageView backIV;
    private ImageView playIV;

    private FloatingActionButton fab;

    private String id;
    private Movie movie;

    private OnMovieFragmentInteractionListener mListener;

    public MovieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MovieFragment.
     */
    public static MovieFragment newInstance(String id) {
        MovieFragment fragment = new MovieFragment();
        Bundle bundle = new Bundle();
        bundle.putString("movie_id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            id = getArguments().getString("movie_id");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    private void init(){
        titleTV = (TextView) getView().findViewById(R.id.movie_title_tv);
        voteCountTV = (TextView) getView().findViewById(R.id.movie_vote_count_tv);
        votePercentageTV = (TextView) getView().findViewById(R.id.movie_vote_percentage_tv);
        dateTV = (TextView) getView().findViewById(R.id.movie_date_tv);
        genresTV = (TextView) getView().findViewById(R.id.movie_genres_tv);
        overviewTV = (TextView) getView().findViewById(R.id.movie_overview_tv);

        reviewAuthorTV = (TextView) getView().findViewById(R.id.movie_review_author_tv);
        reviewContentTV = (TextView) getView().findViewById(R.id.movie_review_content_tv);

        backIV = (ImageView) getView().findViewById(R.id.movie_back_iv);
        posterIV = (ImageView) getView().findViewById(R.id.movie_poster_iv);
        playIV = (ImageView) getView().findViewById(R.id.movie_play_iv);

        fab = (FloatingActionButton) getView().findViewById(R.id.movie_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("movie fragment", "onClick: fab");
                if(movie != null){
                    DatabaseHandler databaseHandler = new DatabaseHandler(getContext());

                    if(databaseHandler.getMovie(movie.getId()) == null){
                        databaseHandler.addMovie(movie);
                        Toast.makeText(getContext(), "You have added "+movie.getTitle()+" to Favourites.", Toast.LENGTH_LONG).show();
                    }else {
                        databaseHandler.deleteMovie(movie);
                        Toast.makeText(getContext(), "You have Removed "+movie.getTitle()+" from Favourites.", Toast.LENGTH_LONG).show();
                    }
                }


            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMovieFragmentInteractionListener) {
            mListener = (OnMovieFragmentInteractionListener) context;
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

    @Override
    public void onResume() {
        super.onResume();

        showMovie();
    }

    public void showMovie(){
        Service.Creator.getService().getMovie(id, api_key).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, retrofit2.Response<Movie> response) {
                movie = response.body();
                Log.i("movie_fragment", "onResponse: "+movie.getTitle());
                Log.i("movie_fragment", "onResponse: "+movie.getBackdrop());

                titleTV.setText(movie.getTitle());
                voteCountTV.setText(movie.getCount()+" votes");
                overviewTV.setText(movie.getDescription());
                dateTV.setText(movie.getDate());

                String genres = "";
                for(int i=0 ; i<movie.getGenres().size() ; i++){
                    genres += movie.getGenres().get(i).getName();
                    if(i != movie.getGenres().size()-1)
                        genres += ", ";
                }
                genresTV.setText(genres);

                Picasso.with(getContext()).load(movie.getBackdrop()).into(backIV);
                Picasso.with(getContext()).load(movie.getPoster()).into(posterIV);

                Service.Creator.getService().getVideos(id, api_key).enqueue(new Callback<VideoResponse>() {
                    @Override
                    public void onResponse(Call<VideoResponse> call, final retrofit2.Response<VideoResponse> response) {
                        Log.i("movie_fragment", "video onResponse: "+response.body().getVideos().size());

                        if(response.body().getVideos().size() >= 1){
                            playIV.setVisibility(View.VISIBLE);
                            playIV.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try{
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + response.body().getVideo(0).getUrl()));
                                        startActivity(intent);
                                    } catch (ActivityNotFoundException e) {
                                        // youtube is not installed.Will be opened in other available apps
                                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/watch?v=" + response.body().getVideo(0).getUrl()));
                                        startActivity(i);
                                    }

                                }
                            });

                        }else {
                            playIV.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onFailure(Call<VideoResponse> call, Throwable t) {
                        Log.i("movie_fragment", "video onFailure: "+t.getMessage());

                    }
                });

                Service.Creator.getService().getReviews(id, api_key).enqueue(new Callback<ReviewResponse>() {
                    @Override
                    public void onResponse(Call<ReviewResponse> call, retrofit2.Response<ReviewResponse> response) {
                        Log.i("movie_fragment", "review onResponse: "+response.body().getReviews().size());

                        if(response.body().getReviews().size() >= 1){
                            reviewAuthorTV.setText(response.body().getReview(0).getAuthor());
                            reviewContentTV.setText(response.body().getReview(0).getContent());

                            reviewAuthorTV.setVisibility(View.VISIBLE);
                            reviewContentTV.setVisibility(View.VISIBLE);
                        }else{
                            reviewAuthorTV.setVisibility(View.GONE);
                            reviewContentTV.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onFailure(Call<ReviewResponse> call, Throwable t) {
                        Log.i("movie_fragment", "review onFailure: "+t.getMessage());

                    }
                });

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.i("movie_fragment", "onFailure: "+t.getMessage());

            }
        });
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
    public interface OnMovieFragmentInteractionListener {
        void onMovieFragmentInteraction(String action, String message);
    }


}
