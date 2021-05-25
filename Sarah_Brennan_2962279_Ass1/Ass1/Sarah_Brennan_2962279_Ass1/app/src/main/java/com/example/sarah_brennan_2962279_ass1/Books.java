package com.example.sarah_brennan_2962279_ass1;

import java.io.Serializable;

/**
 * @author sarahbrennan
 * @version 1.6
 */

//Created books object to be able to create Arraylist from it
public class Books implements Serializable {

    private String title, author, genre;
    //Amount of books available to loan
    private int amount;
    //true if book already borrowed, false if not borrowed currently
    private boolean borrowed;
    public Books(String title, String author, String genre, int amount, boolean borrowed){
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.amount = amount;
        this.borrowed = borrowed;
    }

    /**
     * @return returns title of book
     */
    public String getTitle(){
        return this.title;
    }
    /**
     * @return returns author of book
     */
    public String getAuthor(){
        return this.author;
    }
    /**
     * @return returns genre of book
     */
    public String getGenre(){
        return this.genre;
    }
    /**
     * @return returns amount of books available to borrow
     */
    public int getAmount(){
        return this.amount;
    }
    /**
     * @return returns boolean for if book borrowed or not
     */
    public boolean getBorrowed(){
        return this.borrowed;
    }

    /**
     * @param title updates title of book
     */
    public void setTitle(String title){
        this.title = title;
    }
    /**
     * @param author updates author of book
     */
    public void setAuthor(String author){
        this.author = author;
    }
    /**
     * @param genre updates genre of book
     */
    public void setGenre(String genre){
        this.genre = genre;
    }
    /**
     * @param amount updates amount of books available
     */
    public void setAmount(int amount){
        this.amount = amount;
    }
    /**
     * @param borrowed updates if book borrowed or not
     */
    public void setBorrowed(boolean borrowed){
        this.borrowed = borrowed;
    }

    /**
     *
     * @return  The string of title and author together
     */
    //This is for the list of radio buttons and list in books and loan activities
    public String toString() {
        return this.title + " by " + this.author;
    }
}
