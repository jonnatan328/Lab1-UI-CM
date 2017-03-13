package co.edu.udea.compumovil.gr04_20171.lab2.event.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr04_20171.lab2.R;
import co.edu.udea.compumovil.gr04_20171.lab2.event.data.EventContract.EventEntry;
/**
 * Created by jonnatan on 9/03/17.
 */

public class EventDbHelper extends SQLiteOpenHelper {

    protected static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "Users.db";
    private EventDbHelper eventDbHelper;

    public EventDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Toast.makeText(null, "-----------", Toast.LENGTH_SHORT).show();
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

        loadEvents(sqLiteDatabase);
        System.out.println("1--------------------------------------------------------------------------");
    }

    public void loadEvents(SQLiteDatabase sqLiteDatabase) {

        ContentValues acdc = new ContentValues();
        acdc.put(EventEntry.NAME, "ACDC UdeA");
        acdc.put(EventEntry.DESCRIPTION, "ACDC en concierto con Shakira en la UdeA");
        acdc.put(EventEntry.PICTURE_URI, String.valueOf(R.drawable.school));
        acdc.put(EventEntry.RATING, "2");
        acdc.put(EventEntry.PERSON_IN_CHARGE, "WHAT");
        acdc.put(EventEntry.DATE, "HOY MISMO");
        acdc.put(EventEntry.LOCATION, "UdeA");
        sqLiteDatabase.insert(EventEntry.TABLE_NAME, null, acdc);

        ContentValues diaDelHombre = new ContentValues();
        diaDelHombre.put(EventEntry.NAME, "UdeA conscientiza");
        diaDelHombre.put(EventEntry.DESCRIPTION, "El dia mas importante en la udea");
        diaDelHombre.put(EventEntry.PICTURE_URI, String.valueOf(R.drawable.school));
        diaDelHombre.put(EventEntry.RATING, "5");
        diaDelHombre.put(EventEntry.PERSON_IN_CHARGE, "WHAT2");
        diaDelHombre.put(EventEntry.DATE, "HOY MISMO2");
        diaDelHombre.put(EventEntry.LOCATION, "UdeA2");
        sqLiteDatabase.insert(EventEntry.TABLE_NAME, null, diaDelHombre);

        loadEvents(sqLiteDatabase);

        System.out.println("2--------------------------------------------------------------------------");
        sqLiteDatabase.close();

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
        String selectQuery = "SELECT * FROM " + EventEntry.TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        sqLiteDatabase.close();
        return cursor;
        /*return getReadableDatabase()
                .query(
                        EventEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);*/
    }

    public List<Event> getAllEventsInList() {
        List<Event> eventsList = new ArrayList<Event>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(EventEntry.TABLE_NAME, null, null, null, null, null, null);
        if(cursor.moveToFirst()){
          do {
              Event event = new Event();
              event.setPictureUri(cursor.getString(1));
              event.setName(cursor.getString(2));
              event.setDescription(cursor.getString(3));
              event.setRating(Integer.parseInt(cursor.getString(4)));
              event.setPersonInCharge(cursor.getString(5));
              event.setDate(Date.valueOf(cursor.getString(6)));
              event.setLocation(cursor.getString(7));

              eventsList.add(event);

          }while (cursor.moveToNext());
        }
        cursor.close();
        return eventsList;
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
