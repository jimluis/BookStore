package edu.stevens.cs522.bookstore.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.stevens.cs522.bookstore.entities.Author;
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

    private static final int DATABASE_VERSION = 3;

    public static final String _ID = "_id";
    public static final String TITLE = "title";
    public static final String AUTHOR = "author";
    public static final String ISBN = "isbn";
    public static final String PRICE = "price";
    public static final String BOOK_FK = "book_fk";

    public static final String _AID = "_id";
    public static final String NAME = "name";
    public static final String MIDDLENAME = "middlename";
    public static final String LASTNAME = "lastname";

    public static final String GET_BOOK_DATA = "SELECT b._id, b.title, b.price, b.isbn, GROUP_CONCAT(a.name,'|') as authors  FROM books b JOIN authors a ON b._id = a.book_fk and b._id = ? GROUP BY b._id, b.title, b.price, b. isbn ";
    // SQL Statement to create a new database.

    //SELECT b._id, b.title, b.price, b.isbn, GROUP_CONCAT(a.name,'|') as authors  FROM books b, authors a where b._id = a.book_fk and b._id = 4 GROUP BY b._id, b.title, b.price, b. isbn
    //
    private DatabaseHelper dbHelper;

    private SQLiteDatabase db;


    public static class DatabaseHelper extends SQLiteOpenHelper {

        private static final String BOOK_DATABASE_CREATE = "create table " + BOOK_TABLE + " ("
                +  _ID + " integer primary key, "
                + TITLE + " text not null, "
                + AUTHOR + " text not null, "
                + ISBN + " text not null, "
                + PRICE + " text "
                //+ BOOKID + " text not null "
                + ")";
        // TODO

        private static final String AUTHOR_DATABASE_CREATE = "create table " + AUTHOR_TABLE + " ("
                + _AID + " integer primary key, "
                + NAME + " text, "
                + MIDDLENAME + " text, "
                + LASTNAME + " text, "
                + BOOK_FK + " INTEGER NOT NULL, " +
                "FOREING KEY "+BOOK_FK+" REFERENCES "+BOOK_TABLE+" ("+_ID+") ON DELETE CASCADE"
                +
                ")";
        // TODO

        private static final String AUTHOR_INDEX = "CREATE INDEX AuthorsBookIndex ON Authors("+BOOK_FK+")";

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);


        }

        //Creates the tables everytime onCreate gets called (only once the app starts)
        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO
            db.execSQL(BOOK_DATABASE_CREATE);
            db.execSQL(AUTHOR_DATABASE_CREATE);
            db.execSQL(AUTHOR_INDEX);
            db.execSQL("PRAGMA foreign_keys=ON");
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
        db = dbHelper.getWritableDatabase();
    }

    public Cursor fetchAllBooks() {
        // TODO
       /* return db.query(BOOK_TABLE,
                new String[] {_ID, TITLE, AUTHOR},
                null, null, null, null, null);*/
        db = dbHelper.getReadableDatabase();
        //String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit
       //SELECT b._id, b.title, b.price, b.isbn, GROUP_CONCAT(a.name,'|') as authors  FROM books b JOIN authors a ON b._id = a.book_fk GROUP BY b._id, b.title, b.price, b. isbn

        Cursor cursor = db.query(BOOK_TABLE,
                new String[] {_ID, TITLE, AUTHOR, ISBN, PRICE},
                null, null, null, null, null);

      //  Cursor cursor = db.rawQuery(GET_BOOK_DATA, null);
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
        Cursor cursor;// = db.query(BOOK_TABLE, new String[] {}, rowId, null);
        return book;
    }

    public boolean persist(Book book) throws SQLException {
        // TODO

        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        contentValues.put(TITLE, book.getTitle()); // etc
        contentValues.put(AUTHOR,book.authorNamesString(book));//book.getAuthorsAL()); // etc
        contentValues.put(ISBN, book.getIsbn()); // etc
        contentValues.put(PRICE, book.getPrice()); // etc

        //Author authors = fetchAuthors(book.getId());

        long result = db.insert(BOOK_TABLE, null, contentValues);

        if(result == -1)
            return false;
        else
        {
            String id = String.valueOf(result);
            book.setId(id);

            for(int i = 0; i < book.getAuthorsAL().size(); i++)
            {
                Author author = book.getAuthorsAL().get(i);
                author.setBookId(result);
                persistAuthors(author);
            }

            return true;
        }

    }

    //2 arg is the where
    public Integer delete(Book book) {
        // TODO
    //Deleting from both tables
        db.delete(BOOK_TABLE, _ID + "=" + book.getId(), null);
        return db.delete(AUTHOR_TABLE, BOOK_FK + "=" + book.getId(), null);
    }

    public Integer deleteAll() {
        // TODO
        db.delete(BOOK_TABLE,null, null);
        return db.delete(AUTHOR_TABLE,null, null);
        // return false;
    }

    public void close() {
        // TODO
        db.close();
    }

    //----------------------------------------------

    public Cursor fetchAllAuthors() {
        // TODO
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(AUTHOR_TABLE,
                new String[] {NAME, MIDDLENAME, LASTNAME},
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

    public Author fetchAuthors(int rowId) {
        // TODO
        Author author = new Author();
        // TODO Return cursor to row and populate instance of Book
        return author;
    }

    public boolean persistAuthors(Author authors) throws SQLException {
        // TODO
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, authors.getFirstName()); // etc
        contentValues.put(MIDDLENAME, authors.getMiddleInitial()); // etc
        contentValues.put(LASTNAME, authors.getLastName()); // etc
        contentValues.put(BOOK_FK, authors.getBookId()); // etc


        Long result = db.insert(AUTHOR_TABLE, null, contentValues);


        if(result == -1)
            return false;
        else
            return true;
    }

    //2 arg is the where
    public boolean deleteAuthors(Author authors) {
        // TODO
        return db.delete(AUTHOR_TABLE, _AID + "=" + authors.getId(), null) > 0;

    }

    public boolean deleteAllAuthors() {
        // TODO
        return db.delete(AUTHOR_TABLE,null, null) > 0;
        // return false;
    }


}