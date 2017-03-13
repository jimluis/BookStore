package edu.stevens.cs522.bookstore.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.stevens.cs522.bookstore.entities.Book;

/**
 * Created by luisfelipejimenez on 3/8/17.
 *
 * Database adapter, provides application specific operations
 * Encapsulates db interactions
 * Handle queries
 */

public class CartDbAdapter {

    private static final String DATABASE_NAME = "books.db";

    private static final String BOOK_TABLE = "books";

    private static final String AUTHOR_TABLE = "authors";

    private static final int DATABASE_VERSION = 1;

    public static final String _ID = "";
    public static final String TITLE = "";
    public static final String AUTHOR = "";
    public static final String ISBN = "";
    public static final String PRICE = "";

    public static final String _AID = "";
    public static final String NAME = "";
    public static final String MIDDLENAME = "";
    public static final String LASTNAME = "";
    // SQL Statement to create a new database.

    private DatabaseHelper dbHelper;

    private SQLiteDatabase db;


    public static class DatabaseHelper extends SQLiteOpenHelper {

        private static final String BOOK_DATABASE_CREATE = "create table " + BOOK_TABLE + " ("
                + _ID + " integer primary key, "
                + TITLE + " text not null, "
                + AUTHOR + " text not null "
                + ISBN + " text not null "
                + PRICE + " text not null "
                + ")"; // TODO

        private static final String AUTHOR_DATABASE_CREATE = "create table " + AUTHOR_TABLE + " ("
                + _ID + " integer primary key autoincrement, "
                + NAME + " text not null, "
                + MIDDLENAME + " text "
                + LASTNAME + " text not null "
                + ")"; // TODO

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);


        }

        //Creates the tables everytime onCreate gets called (only once the app starts)
        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO
            db.execSQL(BOOK_DATABASE_CREATE);
            db.execSQL(AUTHOR_DATABASE_CREATE);
        }

        //When the helper is created by android, it passes in the current version of the db, and that is compare with the version that on disk
        // if there is a version mismatch onUpgrade gets call. Hopefully the version on disk is older than the version that is specified in the constructor
        //
        // A new version of the app is a new version of the db
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO
            //
            db.execSQL("DROP TABLE IF EXISTS "+BOOK_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+AUTHOR_TABLE);
            onCreate(db);
        }
    }


    public CartDbAdapter(Context _context) {
        dbHelper = new DatabaseHelper(_context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //opening the db
    public void open() throws SQLException {
        // TODO
    //This creates the db and table - open copy reference of the db stored in the adpter
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

    }

    public Cursor fetchAllBooks() {
        // TODO
       /* return db.query(BOOK_TABLE,
                new String[] {_ID, TITLE, AUTHOR},
                null, null, null, null, null);*/
         db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(BOOK_TABLE,
                new String[] {_ID, TITLE, AUTHOR, ISBN, PRICE},
                null, null, null, null, null);

        if(cursor != null)
        {
            cursor.moveToFirst();
            return cursor;
        }

        else{
            return null;
        }

    }

    public Book fetchBook(long rowId) {
        // TODO
        Book book = new Book();
        // TODO Return cursor to row and populate instance of Book
        return book;
    }

    public void persist(Book book) throws SQLException {
        // TODO
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, book.getTitle()); // etc
      //  contentValues.put(AUTHOR, book.getAuthorsAL()); // etc
        contentValues.put(ISBN, book.getIsbn()); // etc
        contentValues.put(PRICE, book.getPrice()); // etc
        db.insert(BOOK_TABLE, null, contentValues);
    }

    //2 arg is the where
    public boolean delete(Book book) {
        // TODO
        return db.delete(BOOK_TABLE, _ID + "=" + book.getId(), null) > 0;

    }

    public boolean deleteAll() {
        // TODO
        return db.delete(BOOK_TABLE,null, null) > 0;
       // return false;
    }

    public void close() {
        // TODO
        db.close();
    }

}
/*public class CartDbAdapter
{
    public static final String DATABASE_NAME = "BookStore.db";
    public static final int DATABASE_VERSION = 1;
    public static final String AUTHOR_TABLE = "AUTHOR_TABLE";
    public static final String BOOK_TABLE = "BOOK_TABLE";
    public static final String _ID = "";
    public static final String TITLE = "";
    public static final String AUTHOR = "";
    // SQL Statement to create a new database.
    public static final String BOOK_DATABASE_CREATE =
     "create table " + BOOK_TABLE + " ("
        + _ID + " integer primary key, "
        + TITLE + " text not null, ”
        + AUTHOR + " text not null “
        + ")";
}

private static class DatabaseHelper extends SQLiteOpenHelper
{

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, CartDbAdapter.DATABASE_NAME, null, 1);

        SQLiteDatabase db = this.getWritableDatabase();
    }

    //Creates the table when called
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //Execute the string that is getting passed
        db.execSQL(BOOK_DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+BOOK_TABLE);
    }
}*/