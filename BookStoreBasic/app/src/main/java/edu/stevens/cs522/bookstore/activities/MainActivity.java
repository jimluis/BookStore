package edu.stevens.cs522.bookstore.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import edu.stevens.cs522.bookstore.databases.CartDbAdapter;
import edu.stevens.cs522.bookstore.entities.Book;
import edu.stevens.cs522.bookstore.R;

//homework3
public class MainActivity extends AppCompatActivity {

	// Use this when logging errors and warnings.
	private static final String TAG = MainActivity.class.getCanonicalName();

	// These are request codes for subactivity request calls
	static final public int ADD_REQUEST = 1;

	static final public int CHECKOUT_REQUEST = ADD_REQUEST + 1;

    static final private String STATE_BOOKS = "state_books";

    public CartDbAdapter dba;

    public SimpleCursorAdapter simpleCursorAdapter;

    ListView listView;
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO Set the layout (use cart.xml layout)
        setContentView(R.layout.cart);

        // TODO use an array adapter to display the cart  contents.
       //booksArrayAdapter = new BooksAdapter(MainActivity.this, shoppingCart);

        listView = (ListView) findViewById(R.id.listView);


        // TODO check if there is saved UI state, and if so, restore it (i.e. the cart contents)
       // if(savedInstanceState != null)
           // shoppingCart = savedInstanceState.getParcelableArrayList(STATE_BOOKS);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                startManagingCursor(cursor);
                Book book = new Book(cursor);

                Intent viewBookIntent = new Intent(MainActivity.this, ViewBookActivity.class);
                viewBookIntent.putExtra(ViewBookActivity.BOOK_KEY, book);
                setResult(RESULT_OK, viewBookIntent);
                startActivity(viewBookIntent);
            }
        });

        // TODO open the database using the database adapter
        // THis is going to call the constructor of CartDbAdapter which is where the db and the tables are getting created
        dba = new CartDbAdapter(this);
        dba.open();
        // TODO query the database using the database adapter, and manage the cursor on the main thread
        Cursor cursor = dba.fetchAllBooks();
        // TODO use SimpleCursorAdapter to display the cart contents.

        TextView emptyText = (TextView)findViewById(android.R.id.empty);
        listView.setEmptyView(emptyText);

        if(cursor != null && cursor.getCount() > 0)
            generateList(cursor);

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
                Book book =  (Book) intent.getParcelableExtra(AddBookActivity.BOOK_RESULT_KEY);

                listView = (ListView) findViewById(R.id.listView);

                boolean isInserted = dba.persist(book);
                Cursor cursor = dba.fetchAllBooks();

                if(isInserted){
                    Toast.makeText(this,"The record was inserted",Toast.LENGTH_LONG).show();
                 //   booksArrayAdapter.notifyDataSetChanged();
                }else
                    Toast.makeText(this,"The record was NOT inserted",Toast.LENGTH_LONG).show();


                TextView emptyText = (TextView)findViewById(android.R.id.empty);
                listView.setEmptyView(emptyText);

                if(cursor != null)
                    generateList(cursor);

                break;

            case CHECKOUT_REQUEST:
                // CHECKOUT: empty the shopping cart.
                Toast.makeText(this, "emptying shopping cart...", Toast.LENGTH_LONG).show();
                Integer deletedRows = dba.deleteAll();

                if(deletedRows > 0) {
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                    refresh();
                }
                else
                    Toast.makeText(MainActivity.this,"Data was not Deleted", Toast.LENGTH_LONG).show();

                break;
        }
	}

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // TODO save the shopping cart contents (which should be a list of parcelables).
     //   outState.putParcelableArrayList(STATE_BOOKS, shoppingCart);
    }

	//onCreateContextMenu is used to create a contextMenu (a pop out menu to choose Delete or See Datails)
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId()==R.id.listView) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            //menu.setHeaderTitle(shoppingCart.get(info.position).getTitle());

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

        Cursor cursor = (Cursor)listView.getItemAtPosition(info.position);
        startManagingCursor(cursor);
        Book book = new Book(cursor);

        if (menuItemName.equalsIgnoreCase("Delete")) {

            Integer deletedRows = dba.delete(book);

            if(deletedRows > 0) {
                Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                refresh();
            }
            else
                Toast.makeText(MainActivity.this,"Data was not Deleted", Toast.LENGTH_LONG).show();
        }

        else if (menuItemName.equalsIgnoreCase("See Details"))
        {
            Intent viewBookIntent = new Intent(this, ViewBookActivity.class);
            viewBookIntent.putExtra(ViewBookActivity.BOOK_KEY, book);
            startActivity(viewBookIntent);
        }

        return true;

    }

    public void generateList(Cursor cursor)
    {

        startManagingCursor(cursor);
        String[] from = new String[]
                {
                    dba.TITLE,
                    dba.AUTHOR,
                   // dba.PRICE,
                    // dba._ID,
                    //dba.ISBN
                };

        int[] toViewIds = new int[] { R.id.cart_row_title, R.id.cart_row_author};//{ R.id.cart_row_title, R.id.cart_row_author};

        simpleCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.cart_row, cursor, from, toViewIds);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(simpleCursorAdapter);
        simpleCursorAdapter.notifyDataSetChanged();
        registerForContextMenu(listView);

    }

    public void refresh()
    {
        Cursor cursor = dba.fetchAllBooks();
        // TODO use SimpleCursorAdapter to display the cart contents.

        TextView emptyText = (TextView)findViewById(android.R.id.empty);
        listView.setEmptyView(emptyText);

        if(cursor != null)
            generateList(cursor);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dba.close();
    }
}