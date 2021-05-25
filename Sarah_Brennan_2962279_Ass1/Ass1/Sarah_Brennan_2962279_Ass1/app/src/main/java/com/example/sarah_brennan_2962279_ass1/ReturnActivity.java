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

public class ReturnActivity extends AppCompatActivity implements Serializable {
    private ListView listView;
    private ArrayList<Books> bookList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);

        Intent i = getIntent();
        //Gets list from MainActivity of books
        Bundle bundle = getIntent().getExtras();
        //Split bundle into 2 arraylists needed
        bookList = (ArrayList<Books>) bundle.getSerializable("books");
        ArrayList<Books> borrowedList = (ArrayList<Books>) bundle.getSerializable("borrowed");
        //adds list of borrowed books to listview
        CustomListAdapter cla = new CustomListAdapter(ReturnActivity.this, borrowedList);
        listView = (ListView) findViewById(R.id.returnBookListView);
        listView.setAdapter(cla);

        // Button to go back to Booksactivity
        Button goBack = findViewById(R.id.returnBackButton);
        goBack.setOnClickListener((View.OnClickListener) v -> {

            //Used to bundle parameters being passed from activity to activity. As need 2 arraylists use this here
            Bundle bundleBooks = new Bundle();
            bundle.putSerializable("borrowed", borrowedList);
            bundle.putSerializable("books",bookList);
            //create intent to update list in loanactivity
            Intent intent = new Intent(ReturnActivity.this, LoanActivity.class);
            //makes sure list created with all borrowed books is sent to loanactivity
            intent.putExtras(bundle);
            //go to new activity
            startActivity(intent);
        });
    }
}