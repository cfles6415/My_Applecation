package com.example.gameescalator;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class Search extends AppCompatActivity {

    ListView search;
    ArrayAdapter<String> adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = (ListView) findViewById(R.id.search);

        ArrayList<String> arrsearch = new ArrayList<>();
        arrsearch.addAll(Arrays.asList(getResources().getStringArray(R.array.my_games)));
        adapter = new ArrayAdapter<String>(
                Search.this,
                android.R.layout.simple_list_item_1,
                arrsearch
        );
        search.setAdapter(adapter);

    }

    public boolean onCreateOptionMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_game, menu);
        MenuItem item = menu.findItem(R.id.Search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String S) { return false; }

            @Override
            public boolean onQueryTextChange(String S) { adapter.getFilter().filter(S); return false; }});  {

            return super.onCreateOptionsMenu(menu);
        }
    }
}
