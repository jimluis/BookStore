package edu.stevens.cs522.bookstore.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.stevens.cs522.bookstore.contracts.BookContract;

public class Book implements Parcelable{
	
	// TODO Modify this to implement the Parcelable interface.

	private static final String TAG = "Book";

	private int id;
	private String title;
	//public Author[] authors;
	private String isbn;
	private String price;

	private List<Author> authorsAL;// = new ArrayList<Author>();

	public Book(){
	}

	public Book(Book book)
	{
		//this.id = Integer.parseInt(BookContract.getId());
		this.title = book.getTitle();
		//this.authors = book.getAuthors();
		this.authorsAL = book.getAuthorsAL();
		this.isbn = book.getIsbn();
		this.price = book.getPrice();
	}

	/*public Book(int id, String title, Author[] author, String isbn, String price) {
		this.id = id;
		this.title = title;
		this.authors = author;
		this.isbn = isbn;
		this.price = price;
	}*/


	public String getFirstAuthor() {
		if (authorsAL != null && authorsAL.size() > 0) {
			return authorsAL.get(0).getFirstName();
		} else {
			return "";
		}
	}

	/*public String getFirstAuthor() {
		if (authors != null && authors.length > 0) {
			return authors[0].toString();
		} else {
			return "";
		}
	}*/

//Returns the data saved in the parcel
	public Book(Parcel parcel){
		Log.i(TAG,"read Book");
		id = parcel.readInt();
		title = parcel.readString();
		if (authorsAL == null){
			authorsAL = new ArrayList<Author>();
		}

		parcel.readTypedList(authorsAL, Author.CREATOR);

	//	parcel.readParcelable(Author.class.getClassLoader());
		isbn = parcel.readString();
		price = parcel.readString();

	}

	@Override
	public int describeContents() {
		return 0;
	}

	//Saves data inside a parcel in case of a screen rotation that may delete the data if not saved
	@Override
	public void writeToParcel(Parcel parcel, int i) {
		Log.i(TAG,"writeToParcel Book");
		parcel.writeInt(this.id);
		parcel.writeString(this.title);

		parcel.writeTypedList(this.authorsAL);

		//parcel.writeTypedArray(this.authors, i);
		parcel.writeString(this.isbn);
		parcel.writeString(this.price);
	}

	//This is the part responsible for doing the read inside the parcelable
	public static final Creator<Book> CREATOR = new Creator<Book>() {

		//
		@Override
		public Book createFromParcel(Parcel parcel) {
			Log.i(TAG,"createFromParcel: Book");
			return new Book(parcel);
		}

		@Override
		public Book[] newArray(int i)
		{
			return new Book[i];
		}
	};

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/*public Author[] getAuthors() {
		return authors;
	}

	public void setAuthors(Author[] authors) {
		this.authors = authors;
	}*/

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public List<Author> getAuthorsAL() {
		return authorsAL;
	}

	public void setAuthorsAL(List<Author> authorsALIn) {
		this.authorsAL = authorsALIn;
	}


	public void writeToProvider(ContentValues values, String title) {
		BookContract.putTitle(values, title);
	 }

/*	public Author[] getAuthors() {
		return authors;
	}

	public void setAuthors(Author[] authors) {
		this.authors = authors;
	}*/

}