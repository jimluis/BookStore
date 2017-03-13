package edu.stevens.cs522.bookstore.contracts;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by luisfelipejimenez on 3/6/17.
 */

public class AuthorContract
{
    public static final String FIRST_NAME = "first";

    public static final String MIDDLE_INITIAL = "initial";

    public static final String LAST_NAME = "last";

    /*
     * FIRST_NAME column
     */

    private static int firstNameColumn = -1;

    public static String getFirstName(Cursor cursor) {
        if (firstNameColumn < 0) {
            firstNameColumn =  cursor.getColumnIndexOrThrow(FIRST_NAME);;
        }
        return cursor.getString(firstNameColumn);
    }

    public static void putFirstName(ContentValues values, String firstName) {
        values.put(FIRST_NAME, firstName);
    }

    // TODO complete the definitions of the other operations


}
