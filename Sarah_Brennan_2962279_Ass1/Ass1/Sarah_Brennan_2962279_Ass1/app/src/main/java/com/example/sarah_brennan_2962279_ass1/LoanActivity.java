package com.example.sarah_brennan_2962279_ass1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * @author sarahbrennan
 * @version 1.6
 */

public class LoanActivity extends AppCompatActivity {
    private RadioGroup booksRadioGroup;
    private RadioButton booksRadioButton;
    private Button borrowButton;
    private ListView listView;
    private ArrayList<Books> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);

        Intent i = getIntent();
        //Gets list from MainActivity of books
        Bundle bundle = getIntent().getExtras();
        //Split bundle into 2 arraylists needed
        bookList = (ArrayList<Books>) bundle.getSerializable("books");
        ArrayList<Books> borrowedLst = (ArrayList<Books>) bundle.getSerializable("borrowed");


        //populates listview from listview_row with the list of books in arraylist booklist
        CustomListAdapter cla = new CustomListAdapter(LoanActivity.this, borrowedLst);
        listView = (ListView) findViewById(R.id.loanBookListView);
        listView.setAdapter(cla);

        populateRadioButtons();

        //Create new list of books already borrowed
        ArrayList<Books> borrowedList = new ArrayList<Books>();
        for (Books b : bookList) {
            if (b.getBorrowed() == true) {
                borrowedList.add(b);
            }
        }

        // Button listener to go from books to loans activity
        booksRadioGroup=(RadioGroup)findViewById(R.id.loanRadioGroup);
        borrowButton = findViewById(R.id.borrowBookButton);
        borrowButton.setOnClickListener((View.OnClickListener) v -> {
            //Found example here: https://www.tutorialspoint.com/how-to-get-selected-index-of-the-radio-group-in-android
            int selectedId = booksRadioGroup.getCheckedRadioButtonId();
            booksRadioButton = (RadioButton) findViewById(selectedId);
            //Split string into title and author so can check if already borrowed
            boolean alreadyBorrowed = checkBookBorrowed(borrowedList);
            borrowBook(bundle, borrowedList, selectedId, alreadyBorrowed);
        });

        // Button listener to go from books to return activity
        Button returnButton = findViewById(R.id.returnBookButton);
        returnButton.setOnClickListener((View.OnClickListener) v -> {
            int selectedId = booksRadioGroup.getCheckedRadioButtonId();
            booksRadioButton = (RadioButton) findViewById(selectedId);
            //split string so can get title and author of book and check if already loaned
            boolean alreadyBorrowed = checkBookBorrowed(borrowedList);
            returnBook(bundle, borrowedList, selectedId, alreadyBorrowed);
        });

        // Button to go back to Booksactivity
        Button goBack = findViewById(R.id.loanBackButton);
        goBack.setOnClickListener((View.OnClickListener) v -> {
            Intent intent = new Intent(LoanActivity.this, BooksActivity.class);
            startActivity(intent);
        });
    }

    /**
     * @param borrowedList arraylist of books already borrowed by user
     * @return returns if book already borrowed or not
     */
    private boolean checkBookBorrowed(ArrayList<Books> borrowedList) {
        String titleAuthor = (String) booksRadioButton.getText();
        String[] titleAuthorArr = titleAuthor.split(" by ");
        String title = titleAuthorArr[0];
        boolean alreadyBorrowed = false;
        //checks if already borrowed, if it is don't want to borrow again
        for (Books b : borrowedList) {
            if (b.getTitle().equals(title)) {
                alreadyBorrowed = true;
            }
        }
        return alreadyBorrowed;
    }

    /**
     *
     * @param bundle            bundle of ArrayList<Books>
     * @param borrowedList      arrayList of already borrowed books by user
     * @param selectedId        integer ID of radiobutton selected
     * @param alreadyBorrowed   boolean to check if book is already borrowed
     */
    private void borrowBook(Bundle bundle, ArrayList<Books> borrowedList, int selectedId, boolean alreadyBorrowed) {
        //so long as selected radio button is not borrowed before and no more than 4 books are already borrowed
        boolean bookAvailable = true;
        if(borrowedList.size()<4 && !alreadyBorrowed){
            switch (selectedId) {
                case R.id.radioButton1:
                    //Will check and see if the book is available to loan
                    if (bookList.get(0).getAmount() > 0) {
                        bookList.get(0).setBorrowed(true);
                        bookList.get(0).setAmount((bookList.get(0).getAmount() - 1));
                        borrowedList.add(bookList.get(0));
                    }
                    else{
                        bookAvailable = false;
                        break;
                    }
                    break;
                case R.id.radioButton2:
                    if (bookList.get(1).getAmount() > 0) {
                        bookList.get(1).setBorrowed(true);
                        bookList.get(1).setAmount((bookList.get(1).getAmount() - 1));
                        borrowedList.add(bookList.get(1));
                    }
                    else{
                        bookAvailable = false;
                        break;
                    }
                    break;
                case R.id.radioButton3:
                    if (bookList.get(2).getAmount() > 0) {
                        bookList.get(2).setBorrowed(true);
                        bookList.get(2).setAmount((bookList.get(2).getAmount() - 1));
                        borrowedList.add(bookList.get(2));
                    }
                    else{
                        bookAvailable = false;
                        break;
                    }
                    break;
                case R.id.radioButton4:
                    if (bookList.get(3).getAmount() > 0) {
                        bookList.get(3).setBorrowed(true);
                        bookList.get(3).setAmount((bookList.get(3).getAmount() - 1));
                        borrowedList.add(bookList.get(3));
                    }
                    else{
                        bookAvailable = false;
                        break;
                    }
                    break;
                case R.id.radioButton5:
                    if (bookList.get(4).getAmount() > 0) {
                        bookList.get(4).setBorrowed(true);
                        bookList.get(4).setAmount((bookList.get(4).getAmount() - 1));
                        borrowedList.add(bookList.get(4));
                    }
                    else{
                        bookAvailable = false;
                        break;
                    }
                    break;
                case R.id.radioButton6:
                    if (bookList.get(5).getAmount() > 0) {
                        bookList.get(5).setBorrowed(true);
                        bookList.get(5).setAmount((bookList.get(5).getAmount() - 1));
                        borrowedList.add(bookList.get(5));
                    }
                    else{
                        bookAvailable = false;
                        break;
                    }
                    break;
                case R.id.radioButton7:
                    if (bookList.get(6).getAmount() > 0) {
                        bookList.get(6).setBorrowed(true);
                        bookList.get(6).setAmount((bookList.get(6).getAmount() - 1));
                        borrowedList.add(bookList.get(6));
                    }
                    else{
                        bookAvailable = false;
                        break;
                    }
                    break;
                case R.id.radioButton8:
                    if (bookList.get(7).getAmount() > 0) {
                        bookList.get(7).setBorrowed(true);
                        bookList.get(7).setAmount((bookList.get(7).getAmount() - 1));
                        borrowedList.add(bookList.get(7));
                    }
                    else{
                        bookAvailable = false;
                        break;
                    }
                    break;
                case R.id.radioButton9:
                    if (bookList.get(8).getAmount() > 0) {
                        bookList.get(8).setBorrowed(true);
                        bookList.get(8).setAmount((bookList.get(8).getAmount() - 1));
                        borrowedList.add(bookList.get(8));
                    }
                    else{
                        bookAvailable = false;
                        break;
                    }
                    break;
                case R.id.radioButton10:
                    if (bookList.get(9).getAmount() > 0) {
                        bookList.get(9).setBorrowed(true);
                        bookList.get(9).setAmount((bookList.get(9).getAmount() - 1));
                        borrowedList.add(bookList.get(9));
                    }
                    else{
                        bookAvailable = false;
                        break;
                    }
                    break;
                case R.id.radioButton11:
                    if (bookList.get(10).getAmount() > 0) {
                        bookList.get(10).setBorrowed(true);
                        bookList.get(10).setAmount((bookList.get(10).getAmount() - 1));
                        borrowedList.add(bookList.get(10));
                    }
                    else{
                        bookAvailable = false;
                        break;
                    }
                    break;
                case R.id.radioButton12:
                    if (bookList.get(11).getAmount() > 0) {
                        bookList.get(11).setBorrowed(true);
                        bookList.get(11).setAmount((bookList.get(11).getAmount() - 1));
                        borrowedList.add(bookList.get(11));
                    }
                    else{
                        bookAvailable = false;
                        break;
                    }
                    break;
            }
            if(bookAvailable) {
                //Used to bundle parameters being passed from activity to activity. As need 2 arraylists use this here
                Bundle bundleBooks = new Bundle();
                bundle.putSerializable("borrowed", borrowedList);
                bundle.putSerializable("books", bookList);
                //create intent to update list in loanactivity
                Intent intent = new Intent(LoanActivity.this, LoanActivity.class);
                //makes sure list created with all borrowed books is sent to loanactivity
                intent.putExtras(bundle);
                //prints pop up to advise that book successfully has been borrowed
                Toast.makeText(getBaseContext(), "Book Borrowed Successfully", Toast.LENGTH_SHORT).show();
                //go to new activity
                startActivity(intent);
            }
            else{
                Toast.makeText(getBaseContext(), "Book not available in library currently", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            //Pop up that shows that the user already has too many books borrowed or already have the book they are looking to borrow
            Toast.makeText(getBaseContext(), "You have reached the maximum amount of books to be borrowed or book is already borrowed by you", Toast.LENGTH_SHORT).show();

        }
    }

    /**
     *
     * @param bundle            bundle of ArrayList<Books>
     * @param borrowedList      arrayList of books already borrowed
     * @param selectedId        integer ID of radiobutton selected
     * @param alreadyBorrowed   boolean to check if book already borrowed
     */
    private void returnBook(Bundle bundle, ArrayList<Books> borrowedList, int selectedId, boolean alreadyBorrowed) {
        //Make sure that there are books already borrowed and one selected is one borrowed
        if(borrowedList.size()>0 && alreadyBorrowed) {
            switch (selectedId) {
                case R.id.radioButton1:
                    bookList.get(0).setBorrowed(false);
                    bookList.get(0).setAmount((bookList.get(0).getAmount() + 1));
                    borrowedList.remove(bookList.get(0));
                    break;
                case R.id.radioButton2:
                        bookList.get(1).setBorrowed(false);
                        bookList.get(1).setAmount((bookList.get(1).getAmount() + 1));
                        borrowedList.remove(bookList.get(1));
                    break;
                case R.id.radioButton3:
                        bookList.get(2).setBorrowed(false);
                        bookList.get(2).setAmount((bookList.get(2).getAmount() + 1));
                        borrowedList.remove(bookList.get(2));
                    break;
                case R.id.radioButton4:
                        bookList.get(3).setBorrowed(false);
                        bookList.get(3).setAmount((bookList.get(3).getAmount() + 1));
                        borrowedList.remove(bookList.get(3));
                    break;
                case R.id.radioButton5:
                        bookList.get(4).setBorrowed(false);
                        bookList.get(4).setAmount((bookList.get(4).getAmount() + 1));
                        borrowedList.remove(bookList.get(4));
                    break;
                case R.id.radioButton6:
                        bookList.get(5).setBorrowed(false);
                        bookList.get(5).setAmount((bookList.get(5).getAmount() + 1));
                        borrowedList.remove(bookList.get(5));
                    break;
                case R.id.radioButton7:
                        bookList.get(6).setBorrowed(false);
                        bookList.get(6).setAmount((bookList.get(6).getAmount() + 1));
                        borrowedList.remove(bookList.get(6));
                    break;
                case R.id.radioButton8:
                        bookList.get(7).setBorrowed(false);
                        bookList.get(7).setAmount((bookList.get(7).getAmount() + 1));
                        borrowedList.remove(bookList.get(7));
                    break;
                case R.id.radioButton9:
                        bookList.get(8).setBorrowed(false);
                        bookList.get(8).setAmount((bookList.get(8).getAmount() + 1));
                        borrowedList.remove(bookList.get(8));
                    break;
                case R.id.radioButton10:
                        bookList.get(9).setBorrowed(false);
                        bookList.get(9).setAmount((bookList.get(9).getAmount() + 1));
                        borrowedList.remove(bookList.get(9));
                    break;
                case R.id.radioButton11:
                        bookList.get(10).setBorrowed(false);
                        bookList.get(10).setAmount((bookList.get(10).getAmount() + 1));
                        borrowedList.remove(bookList.get(10));
                    break;
                case R.id.radioButton12:
                        bookList.get(11).setBorrowed(false);
                        bookList.get(11).setAmount((bookList.get(11).getAmount() + 1));
                        borrowedList.remove(bookList.get(11));
                    break;
            }
            //Used to bundle parameters being passed from activity to activity. As need 2 arraylists use this here
            Bundle bundleBooks = new Bundle();
            bundle.putSerializable("borrowed",borrowedList);
            bundle.putSerializable("books",bookList);
            //create intent to update list in loanactivity
            Intent intent = new Intent(LoanActivity.this, ReturnActivity.class);
            //makes sure list created with all borrowed books is sent to loanactivity
            intent.putExtras(bundle);
            //prints pop up to advise book has been returned successfully
            Toast.makeText(getBaseContext(), "Book Returned Successfully", Toast.LENGTH_SHORT).show();
            //go to new activity
            startActivity(intent);
        }
        else{
            //prints out pop up to tell used they have no more books to return or that they don't have the book on loan they wish to return
            Toast.makeText(getBaseContext(), "This book is not currently borrowed by you", Toast.LENGTH_SHORT).show();

        }
    }

    private void populateRadioButtons() {
        // Set the radio buttons with title and author of books
        RadioButton rb1 = findViewById(R.id.radioButton1);
        rb1.setText(bookList.get(0).toString());
        RadioButton rb2 = findViewById(R.id.radioButton2);
        rb2.setText(bookList.get(1).toString());
        RadioButton rb3 = findViewById(R.id.radioButton3);
        rb3.setText(bookList.get(2).toString());
        RadioButton rb4 = findViewById(R.id.radioButton4);
        rb4.setText(bookList.get(3).toString());
        RadioButton rb5 = findViewById(R.id.radioButton5);
        rb5.setText(bookList.get(4).toString());
        RadioButton rb6 = findViewById(R.id.radioButton6);
        rb6.setText(bookList.get(5).toString());
        RadioButton rb7 = findViewById(R.id.radioButton7);
        rb7.setText(bookList.get(6).toString());
        RadioButton rb8 = findViewById(R.id.radioButton8);
        rb8.setText(bookList.get(7).toString());
        RadioButton rb9 = findViewById(R.id.radioButton9);
        rb9.setText(bookList.get(8).toString());
        RadioButton rb10 = findViewById(R.id.radioButton10);
        rb10.setText(bookList.get(9).toString());
        RadioButton rb11 = findViewById(R.id.radioButton11);
        rb11.setText(bookList.get(10).toString());
        RadioButton rb12 = findViewById(R.id.radioButton12);
        rb12.setText(bookList.get(11).toString());
    }
}