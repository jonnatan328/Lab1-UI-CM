package co.edu.udea.compumovil.gr04_20171.lab2.event.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import co.edu.udea.compumovil.gr04_20171.lab2.event.data.EventContract.EventEntry;
/**
 * Created by jonnatan on 9/03/17.
 */

public class EventDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Users.db";

    public EventDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + EventEntry.TABLE_NAME + " ("
                + EventEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EventEntry.PICTURE_URI + " TEXT,"
                + EventEntry.NAME + " TEXT NOT NULL ,"
                + EventEntry.DESCRIPTION + " TEXT NOT NULL ,"
                + EventEntry.RATING + " INT NOT NULL,"
                + EventEntry.PERSON_IN_CHARGE + " TEXT NOT NULL,"
                + EventEntry.DATE + " TEXT,"
                + EventEntry.LOCATION + " TEXT,"
                + "UNIQUE (" + EventEntry.NAME +"))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getEventByName(String name) {
        String selection = EventEntry.NAME + " LIKE ?"; // WHERE id LIKE ?
        String selectionArgs[] = new String[]{name};
        Cursor cursor = getReadableDatabase().query(
                EventEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
        return cursor;
    }

    public Cursor getAllEvents() {
        return getReadableDatabase()
                .query(
                        EventEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public long saveEvent(Event event) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                EventEntry.TABLE_NAME,
                null,
                event.toContentValues());

    }

    public int updateEvent(Event event, String name) {
        return getWritableDatabase().update(
                EventEntry.TABLE_NAME,
                event.toContentValues(),
                EventEntry.NAME + " LIKE ?",
                new String[]{name}
        );
    }
}
