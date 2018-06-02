package com.example.prudhvi.popularmovies.Activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.prudhvi.popularmovies.Adapters.Movie_model;
import com.example.prudhvi.popularmovies.Adapters.RecyclerViewAdapter;
import com.example.prudhvi.popularmovies.Utils.Constant;
import com.example.prudhvi.popularmovies.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
  ArrayList<Movie_model> list;
  @BindView(R.id.main_recycler_view)RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(getApplicationContext());
        ButterKnife.bind(this);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        setSupportActionBar(toolbar);

        RecyclerView.LayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(linearLayoutManager);
        list=new ArrayList<>();
       //  fetchdata(Constant.popular_url);
       adapter = new RecyclerViewAdapter(getApplicationContext(),list);
        recyclerView.setAdapter(adapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.drawer_menu, menu);



        MenuItem spinnerMenuItem = menu.findItem(R.id.miSpinner);
        final Spinner spinner = (Spinner) MenuItemCompat.getActionView(spinnerMenuItem);
        spinner.getBackground().setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);




        final ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.choice, android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);

                if (item.toString().equals( "popular")) {
                    list.clear();
                    adapter.refresh(list);
                    fetchdata(Constant.popular_url);

                }
                if (item.toString().equals( "top_rated")) {
                    list.clear();
                    adapter.refresh(list);
                    fetchdata(Constant.top_rated_url);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });


        return true;

    }

    private void fetchdata(String url) {

        AndroidNetworking.post(url)

                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.v("response",""+response);
                        JSONArray results = response.optJSONArray("results");
                        for(int i=0;i<results.length();i++) {
                            JSONObject result= results.optJSONObject(i);
                            Movie_model movie_model = new Movie_model();
                            Log.v("immm",result.optString("id")+"http://image.tmdb.org/t/p/w185"+result.optString("poster_path"));
                           movie_model.setMovie_rating(result.optString("vote_average"));
                           movie_model.setMovie_release_date(result.optString("release_date"));
                           movie_model.setMovie_synopsis(result.optString("overview"));
                           movie_model.setMovie_title(result.optString("title"));
                            movie_model.setMovie_id(result.optString("id"));
                            movie_model.setMovie_poster_url(result.optString("poster_path"));
                        list.add(i,movie_model);
                        }
adapter.refresh(list);
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.v("response",""+error);
                    }
                });

    }

}

