package com.example.sarah_brennan_2962279_ass1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author sarahbrennan
 * @version 1.6
 * @reference https://appsandbiscuits.com/listview-tutorial-android-12-ccef4ead27cc
 * @reference https://stackoverflow.com/questions/3208897/android-listview-viewholder-when-to-use-it-and-when-not-to
 */

//Referenced from https://appsandbiscuits.com/listview-tutorial-android-12-ccef4ead27cc
public class CustomListAdapter extends ArrayAdapter<Books> implements Filterable {

    //to reference the Activity
    private Activity activity;

    //to store the list of books
    private ArrayList<Books> booksList = new ArrayList<Books>();

    private static LayoutInflater inflater = null;

    public CustomListAdapter(Activity activity, ArrayList<Books> booksArr) {
        super(activity, R.layout.listview_row, booksArr);
        this.booksList = booksArr;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View view, ViewGroup parent) {
        View rowView= view;
        //this code gets references to objects in the listview_row.xml file
        try{
            //Referenced from https://stackoverflow.com/questions/3208897/android-listview-viewholder-when-to-use-it-and-when-not-to
            rowView = inflater.inflate(R.layout.listview_row, null);
            //2 textviews appearing in listview_row xml
            TextView nameAuthorTextField = (TextView) rowView.findViewById(R.id.bookNameAuthorTV);
            TextView genreTextField = (TextView) rowView.findViewById(R.id.bookGenreTV);

            //this code sets the values of the objects to values from the arraylist
            nameAuthorTextField.setText(booksList.get(position).toString());
            genreTextField.setText(booksList.get(position).getGenre());
        } catch(Exception e){

        }

        return rowView;

    };

}
