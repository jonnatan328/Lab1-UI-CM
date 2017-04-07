package co.edu.udea.compumovil.gr04_20171.lab3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import co.edu.udea.compumovil.gr04_20171.lab3.user.login.LoginActivity;

public class WelcomeActivity extends AppCompatActivity {
    public static final int REQUEST_WELCOME_USER = 1;
    private Session session;
    private Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        session = new Session(this);
        if(!session.loggedin()){
            logout();
        }
        btnLogout = (Button)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout() {
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
    }


}
