package co.edu.udea.compumovil.gr04_20171.lab2.event.data;

import android.content.ContentValues;
import android.database.Cursor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import co.edu.udea.compumovil.gr04_20171.lab2.event.data.EventContract.EventEntry;
import co.edu.udea.compumovil.gr04_20171.lab2.user.data.UserContract;

/**
 * Created by jonnatan on 9/03/17.
 */

public class Event {
    private String pictureUri;
    private String name;
    private String description;
    private int rating;
    private String personInCharge;
    private Date date;
    private String location;
    private SimpleDateFormat dateFormat;

    public Event() {}

    public Event(int id, String pictureUri, String name, String description, int rating, String personInCharge, Date date, String location) {
        this.pictureUri = pictureUri;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.personInCharge = personInCharge;
        this.date = date;
        this.location = location;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    public Event(Cursor cursor) throws ParseException {
        pictureUri = cursor.getString(cursor.getColumnIndex(EventEntry.PICTURE_URI));
        name = cursor.getString(cursor.getColumnIndex(EventEntry.NAME));
        description = cursor.getString(cursor.getColumnIndex(EventEntry.DESCRIPTION));
        rating = cursor.getInt(cursor.getColumnIndex(EventEntry.RATING));
        personInCharge = cursor.getString(cursor.getColumnIndex(EventEntry.PERSON_IN_CHARGE));
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = new Date();
        date = dateFormat.parse(cursor.getString(cursor.getColumnIndex(EventEntry.DATE)));
        location = cursor.getString(cursor.getColumnIndex(EventEntry.LOCATION));
    }


    public String getPictureUri() {
        return pictureUri;
    }

    public void setPictureUri(String pictureUri) {
        this.pictureUri = pictureUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getPersonInCharge() {
        return personInCharge;
    }

    public void setPersonInCharge(String personInCharge) {
        this.personInCharge = personInCharge;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(EventEntry.PICTURE_URI, pictureUri);
        values.put(EventEntry.NAME, name);
        values.put(EventEntry.DESCRIPTION, description);
        values.put(EventEntry.RATING, rating);
        values.put(EventEntry.PERSON_IN_CHARGE, personInCharge);
        values.put(EventEntry.DATE, dateFormat.format(date));
        values.put(EventEntry.LOCATION, location);
        return values;
    }
}
