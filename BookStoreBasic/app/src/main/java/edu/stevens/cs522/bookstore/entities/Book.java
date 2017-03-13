package edu.stevens.cs522.bookstore.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Book implements Parcelable{
	
	// TODO Modify this to implement the Parcelable interface.

	private static final String TAG = "Book";

	private int id;
	private String title;
	private String isbn;
	private String price;

	private List<Author> authorsAL;

	public Book(){
	}

	public Book(Book book)
	{
		this.title = book.getTitle();
		this.authorsAL = book.getAuthorsAL();
		this.isbn = book.getIsbn();
		this.price = book.getPrice();
	}


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
		if (authorsAL == null){
			authorsAL = new ArrayList<Author>();
		}

		parcel.readTypedList(authorsAL, Author.CREATOR);
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

}