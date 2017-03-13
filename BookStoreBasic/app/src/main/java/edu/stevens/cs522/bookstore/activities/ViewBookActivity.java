package edu.stevens.cs522.bookstore.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import edu.stevens.cs522.bookstore.R;
import edu.stevens.cs522.bookstore.entities.Author;
import edu.stevens.cs522.bookstore.entities.Book;

public class ViewBookActivity extends AppCompatActivity {
	
	// Use this as the key to return the book details as a Parcelable extra in the result intent.
	public static final String BOOK_KEY = "book";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_book);

		// TODO get book as parcelable intent extra and populate the UI with book details.
		Book book = (Book) getIntent().getParcelableExtra(BOOK_KEY);

		TextView viewTitle = (TextView) findViewById(R.id.view_title);
		TextView viewAuthor  = (TextView) findViewById(R.id.view_author);
		TextView viewIsbn = (TextView) findViewById(R.id.view_isbn);

		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < book.getAuthorsAL().size(); i++)
		{
			Author value = book.getAuthorsAL().get(i);

			if(i >= 1)
				builder.append(" , ");

			builder.append(value.toString());
		}
		String authors = builder.toString();

		viewTitle.setText(book.getTitle());
		viewAuthor.setText(authors);
		viewIsbn.setText(book.getIsbn());

	}

}