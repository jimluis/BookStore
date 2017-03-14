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
import edu.stevens.cs522.bookstore.entities.Author;
import edu.stevens.cs522.bookstore.entities.Book;

public class AddBookActivity extends AppCompatActivity {
	
	// Use this as the key to return the book details as a Parcelable extra in the result intent.
	public static final String BOOK_RESULT_KEY = "book_result";
	private static final String TAG = AddBookActivity.class.getCanonicalName();

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
		//Author authors = (Author)authorTextView;

		//author = author.toString().split(",");
		Log.i(TAG, "Title: " + searchTitle.getText().toString());
		Log.i(TAG, "ISBN: " + searchIsbn.getText().toString());

		bookObj.setTitle(searchTitle.getText().toString());
		bookObj.setIsbn(searchIsbn.getText().toString());

		String aut = authorTextView.getText().toString();

		String[] authorName = aut.split(", ");
		//int numOfAuthors = authorName.length;
		ArrayList<Author> authorsList = new ArrayList<Author>();
		Author authorInfo = new Author();
		//Author [] authorArray = new Author[numOfAuthors];

		for (int i = 0; i < authorName.length; i++)
		{
			String[] authorSplit = authorName[i].split(" ");

			if(authorSplit.length == 1)
			{
				authorInfo.setFirstName(authorSplit[0]);
			}

			else if(authorSplit.length == 2)
			{
				authorInfo.setFirstName(authorSplit[0]);
				authorInfo.setLastName(authorSplit[1]);
			}

			else if(authorSplit.length == 3)
			{
				authorInfo.setFirstName(authorSplit[0]);
				authorInfo.setMiddleInitial(authorSplit[1]);
				authorInfo.setLastName(authorSplit[2]);
			}

			//authorArray[i] = authorInfo;
			authorsList.add(authorInfo);
			authorInfo = new Author();
		}

		//bookObj.setAuthors(authorArray);

		bookObj.setAuthorsAL(authorsList);


		return bookObj;
	}
}