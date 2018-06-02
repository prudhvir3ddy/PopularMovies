package com.example.prudhvi.popularmovies.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prudhvi.popularmovies.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {
String title,overview,poster,rating,release_date;
@BindView(R.id.Movie_title)   TextView T_title;
    @BindView(R.id.Movie_Overview)   TextView T_overview;
    @BindView(R.id.Movie_releasedate)   TextView T_releasedate;
    @BindView(R.id.Movie_rating)   TextView T_rating;
    @BindView(R.id.Movie_poster)    ImageView T_poster;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        ActionBar actionBar=getSupportActionBar();

       Intent i=getIntent();
               if(i!=null){

            title=i.getStringExtra("Movie_title");
                   overview=i.getStringExtra("Movie_synopsis");
                   poster=i.getStringExtra("Movie_poster_url");
                   rating=i.getStringExtra("Movie_rating");
                   release_date=i.getStringExtra("Movie_release_date");
                   actionBar.setTitle(title);
                    T_title.setText(title);
                    T_overview.setText(overview);
                    T_rating.setText(rating);
                    T_releasedate.setText(release_date);

                   Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w500"+poster).into(T_poster, new Callback() {
                       @Override
                       public void onSuccess() {
                           progressBar.setVisibility(View.GONE);
                       }

                       @Override
                       public void onError() {
                           progressBar.setVisibility(View.GONE);
                           Toast.makeText(getApplicationContext(), R.string.image_error,Toast.LENGTH_SHORT).show();

                       }
                   });
               }else{
                   Toast.makeText(getApplicationContext(),"No Data Available ",Toast.LENGTH_LONG).show();
               }

    }
}
