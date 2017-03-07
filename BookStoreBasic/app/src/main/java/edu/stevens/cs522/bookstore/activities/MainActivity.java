package edu.stevens.cs522.bookstore.activities;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import edu.stevens.cs522.bookstore.entities.Book;
import edu.stevens.cs522.bookstore.R;
import edu.stevens.cs522.bookstore.util.BooksAdapter;
//homework3
public class MainActivity extends AppCompatActivity {
	
	// Use this when logging errors and warnings.
	private static final String TAG = MainActivity.class.getCanonicalName();
	
	// These are request codes for subactivity request calls
	static final public int ADD_REQUEST = 1;
	
	static final public int CHECKOUT_REQUEST = ADD_REQUEST + 1;

    static final private String STATE_BOOKS = "state_books";

	// There is a reason this must be an ArrayList instead of a List.
    public static ArrayList<Book> shoppingCart = new ArrayList<Book>();
    //BooksAdapter booksArrayAdapter;
    ArrayAdapter<Book> booksArrayAdapter;

    //TextView emptyText = (TextView)findViewById(android.R.id.empty);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO Set the layout (use cart.xml layout)
        setContentView(R.layout.cart);

        // TODO use an array adapter to display the cart  contents.
       booksArrayAdapter = new BooksAdapter(MainActivity.this, shoppingCart);//ArrayAdapter<Book>(this, android.R.layout.simple_list_item_1, shoppingCart);

        ListView listView = (ListView) findViewById(R.id.listView);
        //listView.setEmptyView(emptyText);
        if(shoppingCart.size() > 0)
        {
            TextView emptyText = (TextView)findViewById(android.R.id.empty);
            listView.setEmptyView(emptyText);

            for(int i = 0; i < shoppingCart.size(); i++)
            {
                listView.setAdapter(booksArrayAdapter);
                listView.setItemsCanFocus(false);
                registerForContextMenu(listView);// to add a context menu (menu)to each one of the items in the list
            }
        }

        // TODO check if there is saved UI state, and if so, restore it (i.e. the cart contents)
        if(savedInstanceState != null)
            shoppingCart = savedInstanceState.getParcelableArrayList(STATE_BOOKS);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                String book = String.valueOf(adapterView.getItemAtPosition(position));
                Toast.makeText(MainActivity.this, shoppingCart.get(position).getTitle(), Toast.LENGTH_LONG).show();

                Intent viewBookIntent = new Intent(MainActivity.this, ViewBookActivity.class);
                viewBookIntent.putExtra("bookInfo", shoppingCart.get(position));
                setResult(RESULT_OK, viewBookIntent);
                startActivity(viewBookIntent);
            }
        });

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// TODO inflate a menu with ADD and CHECKOUT options
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bookstore_menu, menu);
        return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
        switch(item.getItemId()) {

            // TODO ADD provide the UI for adding a book
            case R.id.add:
                 Intent addIntent = new Intent(this, AddBookActivity.class);
                 startActivityForResult(addIntent, ADD_REQUEST);
                break;

            // TODO CHECKOUT provide the UI for checking out
            case R.id.checkout:
                Intent checkoutIntent = new Intent(this, CheckoutActivity.class);
                checkoutIntent.putParcelableArrayListExtra("shoppingCart", shoppingCart);
                startActivityForResult(checkoutIntent, CHECKOUT_REQUEST);
                break;

            default:
        }
        return false;
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		// TODO Handle results from the Search and Checkout activities.

        // Use ADD_REQUEST and CHECKOUT_REQUEST codes to distinguish the cases.
        switch(resultCode) {
            case ADD_REQUEST:
                // ADD: add the book that is returned to the shopping cart.
                Book book =  (Book) intent.getParcelableExtra("bookInfo");
                if(book != null)
                    shoppingCart.add(book);

                booksArrayAdapter = new BooksAdapter(MainActivity.this, shoppingCart);//ArrayAdapter<Book>(this, android.R.layout.simple_list_item_1, shoppingCart);

                ListView listView = (ListView) findViewById(R.id.listView);

                //Shows a message if the listview is empty, else, the listview items will be displayed
                TextView emptyText = (TextView)findViewById(android.R.id.empty);
                listView.setEmptyView(emptyText);

                if(shoppingCart.size() > 0)
                {
                    for(int i = 0; i < shoppingCart.size(); i++)
                    {
                        listView.setAdapter(booksArrayAdapter);
                        listView.setItemsCanFocus(false);
                        registerForContextMenu(listView);// to add a context menu to each one of the items in the list
                    }
                }

                break;

            case CHECKOUT_REQUEST:
                // CHECKOUT: empty the shopping cart.
                Toast.makeText(this, "emptying shopping cart...", Toast.LENGTH_LONG).show();
                booksArrayAdapter.clear();

                break;
        }
	}

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // TODO save the shopping cart contents (which should be a list of parcelables).
        outState.putParcelableArrayList(STATE_BOOKS, shoppingCart);
    }

	//onCreateContextMenu is used to create a contextMenu (a pop out menu to choose Delete or See Datails)
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId()==R.id.listView) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle(shoppingCart.get(info.position).getTitle());

            String[] menuItems = getResources().getStringArray(R.array.menu);
            for (int i = 0; i < menuItems.length; i++) {
                     menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    //onContextItemSelected is the action that goes after contextMenu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String[] menuItems = getResources().getStringArray(R.array.menu);
        String menuItemName = menuItems[menuItemIndex];
        String listItemName = shoppingCart.get(info.position).getTitle();

        if (menuItemName.equalsIgnoreCase("Delete"))
            booksArrayAdapter.remove(booksArrayAdapter.getItem(info.position));

        else if (menuItemName.equalsIgnoreCase("See Details"))
        {
            Intent viewBookIntent = new Intent(MainActivity.this, ViewBookActivity.class);
            viewBookIntent.putExtra("bookInfo", shoppingCart.get(info.position));
            setResult(RESULT_OK, viewBookIntent);
            startActivity(viewBookIntent);
        }

        return true;

    }
}