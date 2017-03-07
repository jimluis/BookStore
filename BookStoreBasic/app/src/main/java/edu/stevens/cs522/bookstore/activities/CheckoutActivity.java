package edu.stevens.cs522.bookstore.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import edu.stevens.cs522.bookstore.R;
import edu.stevens.cs522.bookstore.entities.Book;

public class CheckoutActivity extends AppCompatActivity {

	ArrayList<Book> shoppingCart;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkout);

		//shoppingCart1 = (ArrayList<Book>) getIntent().getParcelableExtra("test1");
		shoppingCart = getIntent().getParcelableArrayListExtra("shoppingCart");
		//shoppingCart = savedInstanceState.getParcelableArrayList("shoppingCart");
//		Toast.makeText(CheckoutActivity.this, "onCreate", Toast.LENGTH_LONG).show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// TODO display ORDER and CANCEL options.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.checkout_menu, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		// TODO
		switch(item.getItemId()) {
			case R.id.order:
			// ORDER: display a toast message of how many books have been ordered and return
			Toast.makeText(CheckoutActivity.this, "Your order has "+shoppingCart.size()+" book", Toast.LENGTH_LONG).show();
			finish();
			break;

			case R.id.checkout:
			// CANCEL: just return with REQUEST_CANCELED as the result code

				Intent checkOutIntent = new Intent();
				//addingABookIntent.putExtra("bookInfo", book);
				//setResult(MainActivity.ADD_REQUEST, addingABookIntent);
				setResult(MainActivity.CHECKOUT_REQUEST);
				finish();

			break;

		}
		return false;
	}
	
}