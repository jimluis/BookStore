package edu.stevens.cs522.bookstore.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import java.util.regex.Pattern;

/**
 * Created by dduggan.
 */

public class BookContract implements BaseColumns {

    public static final String _ID = "_id";

    public static final String TITLE = "title";

    public static final String AUTHORS = "author";

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

    public static String getAuthors(Cursor cursor) {
        if (authorColumn < 0) {
            authorColumn =  cursor.getColumnIndexOrThrow(AUTHORS);;
        }
        return cursor.getString(authorColumn);//readStringArray(cursor.getString(authorColumn));
    }


    // TODO complete definitions of other getter and setter operations

    public static void putAuthors(ContentValues values, String title)
    {
        values.put(AUTHORS, title);
    }

    public static String getIsbn(Cursor cursor) {

        return cursor.getString(cursor.getColumnIndexOrThrow(ISBN));
    }

    public static void putIsbn(ContentValues values, String title)
    {
        values.put(ISBN, title);
    }

    public static String getPrice(Cursor cursor) {

        return cursor.getString(cursor.getColumnIndexOrThrow(PRICE));
    }

    public static void putPrice(ContentValues values, String title)
    {
        values.put(PRICE, title);
    }



    public static String getId(Cursor cursor) {

        return cursor.getString(cursor.getColumnIndexOrThrow(_ID));
    }

    public static void putId(ContentValues values, String title)
    {
        values.put(_ID, title);
    }
}
