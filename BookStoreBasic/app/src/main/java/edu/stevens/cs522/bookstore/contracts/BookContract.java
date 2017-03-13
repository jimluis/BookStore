package edu.stevens.cs522.bookstore.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import java.util.regex.Pattern;

/**
 * Created by luisfelipejimenez on 3/6/17.
 */

public class BookContract implements BaseColumns {

    public static final String TITLE = "title";

    public static final String AUTHORS = "authors";

    public static final String ISBN = "isbn";

    public static final String PRICE = "price";

    /*
     * TITLE column
     */

    private static int titleColumn = -1;

    public static String getTitle(Cursor cursor) {
        if (titleColumn < 0) {
            titleColumn =  cursor.getColumnIndexOrThrow(TITLE);;
        }
        return cursor.getString(titleColumn);
    }

    public static void putTitle(ContentValues values, String title) {
        values.put(TITLE, title);
    }

    /*
     * Synthetic authors column
     */
    public static final char SEPARATOR_CHAR = '|';

    private static final Pattern SEPARATOR =
            Pattern.compile(Character.toString(SEPARATOR_CHAR), Pattern.LITERAL);

    public static String[] readStringArray(String in) {
        return SEPARATOR.split(in);
    }

    private static int authorColumn = -1;

    public static String[] getAuthors(Cursor cursor) {
        if (authorColumn < 0) {
            authorColumn =  cursor.getColumnIndexOrThrow(AUTHORS);;
        }
        return readStringArray(cursor.getString(authorColumn));
    }


    // TODO complete definitions of other getter and setter operations


}

/*public class BookContract
{

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String AUTHOR = "Author";
    public static final String ISBN = "isbn";
    public static final String PRICE = "price";



    public static String getId(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(ID));
    }

    public static void putId(ContentValues values, String id)
    {
        values.put(ID, id);
    }


    public static String getTitle(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
    }

    public static void putTitle(ContentValues values, String title)
    {
        values.put(TITLE, title);
    }


    public static String getAuthor(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(AUTHOR));
    }

    public static void putAuthor(ContentValues values, String author)
    {
        values.put(AUTHOR, author);
    }

    public static String getIsbn(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(ISBN));
    }

    public static void putIsbn(ContentValues values, String isbn)
    {
        values.put(ISBN, isbn);
    }

    public static String getPrice(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(PRICE));
    }

    public static void putProce(ContentValues values, String price)
    {
        values.put(PRICE, price);
    }


}*/
