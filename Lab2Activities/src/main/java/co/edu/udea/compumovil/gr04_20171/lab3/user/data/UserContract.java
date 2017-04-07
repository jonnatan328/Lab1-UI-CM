package co.edu.udea.compumovil.gr04_20171.lab3.user.data;

import android.provider.BaseColumns;

/**
 * Created by jonnatan on 26/02/17.
 * Esquema de la base de datos para usuario
 */
public class UserContract {
    public static abstract class UserEntry implements BaseColumns {
        public static final String TABLE_NAME ="user";

        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String EMAIL = "email";
        public static final String AGE = "age";
        public static final String PICTURE_URI = "pictureUri";
        ;
    }

}
