package com.example.sarah_brennan_2962279_ass1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author sarahbrennan
 * @version 1.6
 */

public class GenreFilterActivity extends AppCompatActivity implements Serializable {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_filter);

        Intent i = getIntent();
        ArrayList<Books> bookList = (ArrayList<Books>) i.getSerializableExtra("key");
        //prints out list of books based on genre to listview
        CustomListAdapter cla = new CustomListAdapter(GenreFilterActivity.this, bookList);
        listView = (ListView) findViewById(R.id.fullBookFilteredListView);
        listView.setAdapter(cla);

        // Button to go back to BooksActivity
        Button goBack = findViewById(R.id.genreBackButton);
        goBack.setOnClickListener((View.OnClickListener) v -> {
            Intent intent = new Intent(GenreFilterActivity.this, BooksActivity.class);
            startActivity(intent);
        });
    }
}