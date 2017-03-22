package edu.stevens.cs522.bookstore.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import edu.stevens.cs522.bookstore.R;
import edu.stevens.cs522.bookstore.databases.CartDbAdapter;
import edu.stevens.cs522.bookstore.entities.Author;
import edu.stevens.cs522.bookstore.entities.Book;

public class AddBookActivity extends AppCompatActivity {
	
	// Use this as the key to return the book details as a Parcelable extra in the result intent.
	public static final String BOOK_RESULT_KEY = "book_result";
	private static final String TAG = AddBookActivity.class.getCanonicalName();
    CartDbAdapter db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_book);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// TODO provide ADD and CANCEL options
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.addbook_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		// TODO
		switch(item.getItemId()) {
			// ADD: return the book details to the BookStore activity
			case R.id.add:
			Book book = addBook();

			Intent addingABookIntent = new Intent();
			addingABookIntent.putExtra(BOOK_RESULT_KEY, book);
			setResult(MainActivity.ADD_REQUEST, addingABookIntent);

			finish();


			break;

			// CANCEL: cancel the request
			case R.id.cancel:
				//setResult(MainActivity.CHECKOUT_REQUEST);
			//	Toast.makeText(AddBookActivity.this, "Cancelling...", Toast.LENGTH_LONG).show();
				finish();
			break;
		}
		return false;
	}
	
	public Book addBook() {
		// TODO Just build a Book object with the search criteria and return that.
		Book bookObj = new Book();
		TextView searchTitle = (TextView) findViewById(R.id.search_title);
		TextView searchIsbn = (TextView) findViewById(R.id.search_isbn);
		TextView authorTextView = (TextView) findViewById(R.id.search_author);
        TextView price = (TextView) findViewById(R.id.price);

		Log.i(TAG, "Title: " + searchTitle.getText().toString());
		Log.i(TAG, "ISBN: " + searchIsbn.getText().toString());

		bookObj.setTitle(searchTitle.getText().toString());
		bookObj.setIsbn(searchIsbn.getText().toString());
        bookObj.setPrice(price.getText().toString());


		String aut = authorTextView.getText().toString();
		Author authorInfo = new Author(aut);

		bookObj.setAuthorsAL(authorInfo.getAuthorsList());

		return bookObj;
	}
}