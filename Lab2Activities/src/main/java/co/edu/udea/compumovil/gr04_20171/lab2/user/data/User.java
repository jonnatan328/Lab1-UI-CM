package co.edu.udea.compumovil.gr04_20171.lab2.user.data;

import android.content.ContentValues;
import android.database.Cursor;

import co.edu.udea.compumovil.gr04_20171.lab2.user.data.UserContract.UserEntry;


import static android.R.attr.name;

/**
 * Created by jonnatan on 26/02/17.
 * Entidad usuario
 */
public class User {
    private String username;
    private String password;
    private String email;
    private String age;
    private String pictureUri;

    public User(String username, String password, String email, String age, String pictureUri) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
        this.pictureUri = pictureUri;
    }

    public User(Cursor cursor) {
        username = cursor.getString(cursor.getColumnIndex(UserEntry.USERNAME));
        password = cursor.getString(cursor.getColumnIndex(UserEntry.PASSWORD));
        email = cursor.getString(cursor.getColumnIndex(UserEntry.EMAIL));
        age = cursor.getString(cursor.getColumnIndex(UserEntry.AGE));
        pictureUri = cursor.getString(cursor.getColumnIndex(UserEntry.PICTURE_URI));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPictureUri() {
        return pictureUri;
    }

    public void setPictureUri(String pictureUri) {
        this.pictureUri = pictureUri;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.USERNAME, username);
        values.put(UserContract.UserEntry.PASSWORD, password);
        values.put(UserContract.UserEntry.EMAIL, email);
        values.put(UserContract.UserEntry.AGE, age);
        values.put(UserContract.UserEntry.PICTURE_URI, pictureUri);
        return values;
    }
}
