package com.abdulaziz.moviesapp.ui.home.movies;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abdulaziz.moviesapp.R;
import com.abdulaziz.moviesapp.data.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by abdulaziz on 3/3/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder>{

    private ArrayList<Movie> movies;
    private Context mContext;
    private onItemClickListener mListener;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView nameTV, ratingTV;
        public ImageView posterIV;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            this.nameTV = (TextView) view.findViewById(R.id.name_text);
            this.ratingTV = (TextView) view.findViewById(R.id.rating_text);
            this.posterIV = (ImageView) view.findViewById(R.id.imagView);

            this.cardView = (CardView) view.findViewById(R.id.card_view);
        }
    }

    public MoviesAdapter(Context mContext, ArrayList<Movie> movies) {
        this.movies = movies;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(onItemClickListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_row, parent, false);
        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Movie movie = movies.get(position);

        holder.nameTV.setText(movie.getTitle());
        holder.ratingTV.setText(movie.getRating());

        Picasso.with(mContext)
                .load("http://image.tmdb.org/t/p/w500"+movie.getPoster())
                .into(holder.posterIV);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(movie);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public interface onItemClickListener{
        void onItemClick(Movie movie);
    }

}
