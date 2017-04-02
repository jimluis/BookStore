package edu.stevens.cs522.bookstore.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import edu.stevens.cs522.bookstore.entities.Author;

import static android.R.attr.author;

/**
 * Created by dduggan.
 */

public class AuthorContract implements BaseColumns {

    public static final String FIRST_NAME = "name";

    public static final String MIDDLE_INITIAL = "middlename";

    public static final String LAST_NAME = "lastname";

    public static final String BOOK_ID = "book_id";

    /*
     * FIRST_NAME column
     */

    private static int firstNameColumn = -1;

    public static String getFirstName(Cursor cursor) {
        if (firstNameColumn < 0) {
            firstNameColumn =  cursor.getColumnIndexOrThrow(FIRST_NAME);;
        }
        return cursor.getString(cursor.getColumnIndexOrThrow(FIRST_NAME));
    }

    public static void putFirstName(ContentValues values, String firstName) {
        values.put(FIRST_NAME, firstName);
    }

    // TODO complete the definitions of the other operations

    public static String getMiddleInitial(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(MIDDLE_INITIAL));
    }

    public static void putMiddleInitial(ContentValues values, String firstName) {
        values.put(MIDDLE_INITIAL, firstName);
    }

    public static String getLastName(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(LAST_NAME));
    }

    public static void putLastName(ContentValues values, String firstName) {
        values.put(LAST_NAME, firstName);
    }

    public static String getBookId(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(BOOK_ID));//getString(firstNameColumn);
    }

    public static void putBookId(ContentValues values, String firstName) {
        values.put(BOOK_ID, firstName);
    }
}
