package com.example.prudhvi.popularmovies.Activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.prudhvi.popularmovies.Adapters.Movie_model;
import com.example.prudhvi.popularmovies.Adapters.RecyclerViewAdapter;
import com.example.prudhvi.popularmovies.R;
import com.example.prudhvi.popularmovies.Utils.Connection;
import com.example.prudhvi.popularmovies.Utils.Constant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.main_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    RecyclerViewAdapter adapter;
    private ArrayList<Movie_model> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(getApplicationContext());
        ButterKnife.bind(this);
        Connection connection = new Connection(getApplicationContext());
        if (!connection.isInternet())
            Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();

        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        setSupportActionBar(toolbar);

        RecyclerView.LayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(linearLayoutManager);
        list=new ArrayList<>();
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

                if (item.toString().equals("Popular")) {
                    list.clear();
                    adapter.refresh(list);
                    fetchdata(Constant.POPULAR_URL);

                }
                if (item.toString().equals("Top Rated")) {
                    list.clear();
                    adapter.refresh(list);
                    fetchdata(Constant.TOP_RATED_URL);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });


        return true;

    }

    private void fetchdata(String url) {

        AndroidNetworking.get(url)
                .addQueryParameter("api_key", Constant.API_KEY)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {

                    @Override
                    public void onResponse(JSONObject response) {

                        JSONArray results = response.optJSONArray("results");
                        for(int i=0;i<results.length();i++) {
                            JSONObject result= results.optJSONObject(i);
                            Movie_model movie_model = new Movie_model();
                            movie_model.setMovie_rating(result.optString(Constant.MOVIE_RATING));
                            movie_model.setMovie_release_date(result.optString(Constant.MOVIE_RELEASE_DATE));
                            movie_model.setMovie_synopsis(result.optString(Constant.MOVIE_OVERVIEW));
                            movie_model.setMovie_title(result.optString(Constant.MOVIE_TITLE));

                            movie_model.setMovie_poster_url(result.optString(Constant.MOVIE_POSTER));
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

