package com.example.prudhvi.popularmovies.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prudhvi.popularmovies.Activities.DetailsActivity;
import com.example.prudhvi.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    ArrayList<Movie_model> list;
    Context mContext;
    public RecyclerViewAdapter(Context context,ArrayList<Movie_model> list) {
        this.list=list;
        this.mContext=context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewlayout,parent,false) ;
    return new ViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        final Movie_model temp_model = list.get(position);
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185"+temp_model.getMovie_poster_url()).into(holder.Movie_poster);
    holder.Movie_poster.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(mContext, DetailsActivity.class);
            i.putExtra("Movie_id", temp_model.getMovie_id());
            i.putExtra("Movie_title",temp_model.getMovie_title());
            i.putExtra("Movie_synopsis",temp_model.getMovie_synopsis() );
            i.putExtra("Movie_rating",temp_model.getMovie_rating());
            i.putExtra("Movie_release_date",temp_model.getMovie_release_date() );
            i.putExtra("Movie_poster_url",temp_model.getMovie_poster_url());
            mContext.startActivity(i);

        }
    });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public  void  refresh(ArrayList<Movie_model> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
       @BindView(R.id.model_imageview)  ImageView Movie_poster;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
