package co.edu.udea.compumovil.gr04_20171.lab2;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jonnatan on 8/03/17.
 */

public class Session {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    public Session(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setLoggedin(boolean logggedin){
        editor.putBoolean("loggedInmode",logggedin);
        editor.commit();
    }

    public boolean loggedin(){
        return preferences.getBoolean("loggedInmode", false);
    }
}
