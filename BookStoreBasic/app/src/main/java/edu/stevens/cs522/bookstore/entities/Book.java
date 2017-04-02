package edu.stevens.cs522.bookstore.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.stevens.cs522.bookstore.contracts.BookContract;

public class Book implements Parcelable{
	
	// TODO Modify this to implement the Parcelable interface.

	private static final String TAG = "Book";

	private String id;
	private String title;
	private String isbn;
	private String price;
    private String authors;

	private ArrayList<Author> authorsAL;

	public Book(){
	}

	public Book(Cursor cursor)
	{
		id = BookContract.getId(cursor);
		title = BookContract.getTitle(cursor);
		authorsAL = Author.castingString(BookContract.getAuthors(cursor));
		isbn = BookContract.getIsbn(cursor);
		price = BookContract.getPrice(cursor);

	}

	/*public Book(Book book)
	{
		this.title = book.getTitle();
		this.authorsAL = book.getAuthorsAL();
		this.isbn = book.getIsbn();
		this.price = book.getPrice();
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
		id = parcel.readString();
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
		parcel.writeString(this.id);
		parcel.writeString(this.title);
		//parcel.writeString(this.authors);
		parcel.writeTypedList(this.authorsAL);
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public ArrayList<Author> getAuthorsAL() {
		return authorsAL;
	}

	public void setAuthorsAL(ArrayList<Author> authorsALIn) {
		this.authorsAL = authorsALIn;
	}

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void writeToProvider(ContentValues values)
	{
		BookContract.putTitle(values, title);
		BookContract.putIsbn(values, isbn);
		BookContract.putPrice(values, price);
		BookContract.putAuthors(values, authorNamesString(authorsAL));
	}

	public String authorNamesString(ArrayList<Author> authorsAL)
	{
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < authorsAL.size(); i++)
		{
			Author value = authorsAL.get(i);

			if(i >= 1)
				builder.append(" , ");

			builder.append(value.toString());
		}
		String authors = builder.toString();

		return authors;
	}

}