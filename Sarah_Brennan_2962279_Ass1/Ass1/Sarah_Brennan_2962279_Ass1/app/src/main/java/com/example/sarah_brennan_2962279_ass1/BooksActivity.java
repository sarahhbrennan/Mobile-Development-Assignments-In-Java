package com.example.sarah_brennan_2962279_ass1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * @author sarahbrennan
 * @version 1.6
 */

public class BooksActivity extends AppCompatActivity{
    private RadioGroup booksRadioGroup;
    private RadioButton booksRadioButton;
    private Button borrowButton;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        Spinner genre_spinner = populateSpinner();
        ArrayList<Books> bookList = getListOfBooks();

        //populates listview from listview_row with the list of books in arraylist booklist
        CustomListAdapter cla = new CustomListAdapter(BooksActivity.this, bookList);
        listView = (ListView) findViewById(R.id.loanBookListView);
        listView.setAdapter(cla);

        // Button to view current borrowed books
        Button goToBooksActivityBtn = findViewById(R.id.goToLoanActivityButton);
        goToBooksActivityBtn.setOnClickListener((View.OnClickListener) v -> {
            ArrayList<Books> borrowedBooks = new ArrayList<Books>();
            for(Books b:bookList){
                if (b.getBorrowed() == true) {
                    borrowedBooks.add(b);
                }
            }
            //Used to bundle parameters being passed from activity to activity. As need 2 arraylists use this here
            Bundle bundle = new Bundle();
            bundle.putSerializable("borrowed",borrowedBooks);
            bundle.putSerializable("books",bookList);
            Intent intent = new Intent(BooksActivity.this, LoanActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        // Button to view filtered list based on genre
        Button selectGenreButton = findViewById(R.id.genreButton);
        selectGenreButton.setOnClickListener((View.OnClickListener) v -> {
            String genre = genre_spinner.getSelectedItem().toString();
            //create new list with just books from that genre
            ArrayList<Books> genreList = new ArrayList<Books>();
            //so long as genre is not the item selected in spinner, add each book with that genre to list
            if(!genre.equals("Genre")){
                for (Books b : bookList) {
                    if (b.getGenre().equals(genre)) {
                        genreList.add(b);
                    }
                }
                Intent intent = new Intent(BooksActivity.this, GenreFilterActivity.class);
                //pass list with just the list of books in genre to genreactivity
                intent.putExtra("key", genreList);
                Toast.makeText(getBaseContext(), genre + " genre has been chosen", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
            //If genre item selected, show error message below
            else{
                Toast.makeText(getBaseContext(), "Please select a Genre from dropdown list", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList<Books> getListOfBooks() {
        //list of books to be put in arraylist
        Books b1 = new Books("Life Of Pi","Yann Martel","Action", 2, true);
        Books b2 = new Books("The Three Musketeers","Alexandre Dumas","Action", 2, false);
        Books b3 = new Books("To Kill A Mockingbird","Harper Lee","Classic", 0, false);
        Books b4 = new Books("Little Women","Louisa May Alcott","Classic", 2, false);
        Books b5 = new Books("The Dresden Files","Jim Butcher","Fantasy", 2, true);
        Books b6 = new Books("The Lord Of The Rings","J.R.R. Tolkien","Fantasy", 2, false);
        Books b7 = new Books("Misery","Stephen King","Horror", 2, false);
        Books b8 = new Books("The Haunting Of Hill House","Shirley Jackson","Horror", 2, false);
        Books b9 = new Books("The Hunger Games","Suzanne Collins","Sci-Fi", 2, false);
        Books b10 = new Books("The Handmaids Tale","Margaret Atwood","Sci-Fi", 2, false);
        Books b11 = new Books("Gone Girl","Gillian Flynn","Thriller", 2, false);
        Books b12 = new Books("The Girl On The Train","Paula Hawkings","Thriller", 2, false);
        ArrayList<Books> bookList = new ArrayList<Books>();
        bookList.add(b1);
        bookList.add(b2);
        bookList.add(b3);
        bookList.add(b4);
        bookList.add(b5);
        bookList.add(b6);
        bookList.add(b7);
        bookList.add(b8);
        bookList.add(b9);
        bookList.add(b10);
        bookList.add(b11);
        bookList.add(b12);
        return bookList;
    }

    private Spinner populateSpinner() {
        //Genre Spinner, uses strings from string.xml to populate
        Spinner genre_spinner = (Spinner) findViewById(R.id.genreSpinner);
        ArrayAdapter<CharSequence> genreAdapter = ArrayAdapter.createFromResource(this,
                R.array.genre_array, android.R.layout.simple_spinner_item);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genre_spinner.setAdapter(genreAdapter);
        return genre_spinner;
    }
}