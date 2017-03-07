package edu.stevens.cs522.bookstore.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class Book implements Parcelable{
	
	// TODO Modify this to implement the Parcelable interface.

	private static final String TAG = "Book";

	private int id;
	private String title;
	//private ArrayList<Author> authors;
	private String isbn;
	private String price;

	private ArrayList<Author> authorsAL;// = new ArrayList<Author>();

	public Book(){}

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

//Returns the data saved in the parcel
	public Book(Parcel parcel){
		Log.i(TAG,"read Book");
		id = parcel.readInt();
		title = parcel.readString();
		parcel.readTypedList(authorsAL, Author.CREATOR);
		isbn = parcel.readString();
		price = parcel.readString();

	}

	@Override
	public int describeContents() {
		return 0;
	}

	//Saves data inside a parcel incase of a screen rotation that may delete the data if not saved
	@Override
	public void writeToParcel(Parcel parcel, int i) {
		Log.i(TAG,"writeToParcel Book");
		parcel.writeInt(this.id);
		parcel.writeString(this.title);

//		Object[] objectsAuthors= this.authors;
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
		public Book[] newArray(int i) {
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

	public ArrayList<Author> getAuthorsAL() {
		return authorsAL;
	}

	public void setAuthorsAL(ArrayList<Author> authorsALIn) {
		this.authorsAL = authorsALIn;
	}
}