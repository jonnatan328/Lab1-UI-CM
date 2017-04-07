package co.edu.udea.compumovil.gr04_20171.lab3.event.data;

import android.provider.BaseColumns;

/**
 * Created by jonnatan on 9/03/17.
 */

public class EventContract {
    public static abstract class EventEntry implements BaseColumns {
        public static final String TABLE_NAME ="event";

        public static final String PICTURE_URI = "pictureUri";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String RATING = "rating";
        public static final String PERSON_IN_CHARGE = "personInCharge";
        public static final String DATE = "date";
        public static final String LOCATION = "location";
    }
}
