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
import com.example.prudhvi.popularmovies.Utils.Connection;
import com.example.prudhvi.popularmovies.Utils.Constant;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {
    @BindView(R.id.Movie_rating)
    TextView T_rating;
@BindView(R.id.Movie_title)   TextView T_title;
    @BindView(R.id.Movie_Overview)   TextView T_overview;
    @BindView(R.id.Movie_releasedate)   TextView T_releasedate;
    private String title, overview, poster, rating, release_date;
    @BindView(R.id.Movie_poster)    ImageView T_poster;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        ActionBar actionBar=getSupportActionBar();
        Connection connection = new Connection(getApplicationContext());

       Intent i=getIntent();
               if(i!=null){

                   title = i.getStringExtra(Constant.MOVIE_TITLE);
                   overview = i.getStringExtra(Constant.MOVIE_OVERVIEW);
                   poster = i.getStringExtra(Constant.MOVIE_POSTER);
                   rating = i.getStringExtra(Constant.MOVIE_RATING);
                   release_date = i.getStringExtra(Constant.MOVIE_RELEASE_DATE);
                   actionBar.setTitle(title);
                    T_title.setText(title);
                    T_overview.setText(overview);
                    T_rating.setText(rating);
                    T_releasedate.setText(release_date);

                   if (!connection.isInternet()) {
                       Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
                       progressBar.setVisibility(View.GONE);
                   } else {
                       Picasso.with(getApplicationContext()).load(Constant.BIG_IMAGE_URL + poster).into(T_poster, new Callback() {
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
                   }
               } else {
                   Toast.makeText(getApplicationContext(), R.string.no_data_error, Toast.LENGTH_LONG).show();
               }

    }
}
