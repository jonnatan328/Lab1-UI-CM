package co.edu.udea.compumovil.gr04_20171.lab2.user.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import co.edu.udea.compumovil.gr04_20171.lab2.user.data.UserContract.UserEntry;

/**
 * Created by jonnatan on 26/02/17.
 */
public class UserDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Users.db";
    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     */
    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param sqLiteDatabase The database.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + UserEntry.TABLE_NAME + " ("
                + UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + UserEntry.USERNAME + " TEXT NOT NULL,"
                + UserEntry.PASSWORD + " TEXT NOT NULL ,"
                + UserEntry.EMAIL + " TEXT NOT NULL ,"
                + UserEntry.AGE + " INT NOT NULL,"
                + UserEntry.PICTURE_URI + " TEXT,"
                + "UNIQUE (" + UserEntry.USERNAME + ","+ UserEntry.EMAIL +"))");

    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getUserByUsername(String username) {
        String selection = UserEntry.USERNAME + " LIKE ?"; // WHERE id LIKE ?
        String selectionArgs[] = new String[]{username};
        Cursor cursor = getReadableDatabase().query(
                UserEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
        return cursor;
    }

    public Cursor getAllUsers() {
        return getReadableDatabase()
                .query(
                        UserEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public long saveUser(User user) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                UserEntry.TABLE_NAME,
                null,
                user.toContentValues());

    }

    public int updateUser(User user, String username) {
        return getWritableDatabase().update(
                UserEntry.TABLE_NAME,
                user.toContentValues(),
                UserEntry.USERNAME + " LIKE ?",
                new String[]{username}
        );
    }

}
