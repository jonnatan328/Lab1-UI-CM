package co.edu.udea.compumovil.gr04_20171.lab2.event.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;

import co.edu.udea.compumovil.gr04_20171.lab2.R;
import co.edu.udea.compumovil.gr04_20171.lab2.event.data.EventContract.EventEntry;
/**
 * Created by jonnatan on 9/03/17.
 */

public class EventDbHelper extends SQLiteOpenHelper {

    protected static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "Events.db";

    public EventDbHelper(Context context) {
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
        mockEvent(sqLiteDatabase, new Event(String.valueOf(R.drawable.school),"ACDC UdeA", "ACDC en concierto con Shakira en la UdeA",
                 2, "WHAT", new Date(2017),"UdeA"));
        mockEvent(sqLiteDatabase, new Event(String.valueOf(R.drawable.school),"Besaton", "Besaton de 3 horas",
                4, "Carlos Perez", new Date(2017),"Parque de los deseos"));
        mockEvent(sqLiteDatabase, new Event(String.valueOf(R.drawable.school),"Concierto por la paz", "Grandes artistas en concierto por la paz",
                2, "Pedro Gomez", new Date(2017),"Frontera con Venezuela"));
        mockEvent(sqLiteDatabase, new Event(String.valueOf(R.drawable.school),"Desfile de carros antiguos", "Los mejores carros antiguos en Medellin",
                2, "Juan Carmona", new Date(2017),"Avenida del Rio"));
        mockEvent(sqLiteDatabase, new Event(String.valueOf(R.drawable.school),"Recreación para niños", "Recreación para los niños del area metropolitana",
                2, "Santiago Cano", new Date(2017),"Jardin Botanico"));
        System.out.println("2--------------------------------------------------------------------------");

    }

    public long mockEvent(SQLiteDatabase db, Event event) {
        return db.insert(
                EventEntry.TABLE_NAME,
                null,
                event.toContentValues());
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

    public Cursor getEventById(String eventId) {
        Cursor cursor = getReadableDatabase().query(
                EventEntry.TABLE_NAME,
                null,
                EventEntry._ID + " LIKE ?",
                new String[]{eventId},
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
    /*
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
    }*/

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
