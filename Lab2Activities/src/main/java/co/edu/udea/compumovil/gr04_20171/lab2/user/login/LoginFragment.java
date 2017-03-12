package co.edu.udea.compumovil.gr04_20171.lab2.user.login;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import co.edu.udea.compumovil.gr04_20171.lab2.R;
import co.edu.udea.compumovil.gr04_20171.lab2.Session;
import co.edu.udea.compumovil.gr04_20171.lab2.WelcomeActivity;
import co.edu.udea.compumovil.gr04_20171.lab2.user.addEdit.AddEditUserActivity;
import co.edu.udea.compumovil.gr04_20171.lab2.user.data.User;
import co.edu.udea.compumovil.gr04_20171.lab2.user.data.UserDbHelper;
import co.edu.udea.compumovil.gr04_20171.lab2.utils.Cifrar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_LOGIN = 0;
    private static Context cntxofParent;

    private UserLoginTask mAuthTask = null;
    private Session session;
    private UserDbHelper mUserDbHelper;

    private Button mLoginButton;
    private TextView mRegister;
    private TextInputEditText mUsernameField;
    private TextInputEditText mPasswordField;
    private ProgressDialog progressDialog;
    private boolean isLogin;

    public LoginFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cntxofParent=getActivity();
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        // Instancia de helper
        mUserDbHelper = new UserDbHelper(getActivity());
        //instancia de la session
        session = new Session(getActivity());
        // Referencias UI
        mLoginButton = (Button) root.findViewById(R.id.loginButton);
        mRegister = (TextView) root.findViewById(R.id.registerButton);
        mUsernameField = (TextInputEditText) root.findViewById(R.id.et_usernameLogin);
        mPasswordField = (TextInputEditText) root.findViewById(R.id.et_passwordLogin);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                showRegisterScreen();
            }
        });

        if (session.loggedin()){
            startActivity(new Intent(getActivity(),WelcomeActivity.class));
            getActivity().finish();
        }

        return root;
    }

    private void showRegisterScreen() {
        Intent intent = new Intent(getActivity(), AddEditUserActivity.class);
        intent.putExtra("parentActivity",getActivity().getClass().getSimpleName());
        startActivityForResult(intent, AddEditUserActivity.REQUEST_ADD_USER);
    }

    private void loginUser() {
        Log.d(TAG, "Login");

        if (mAuthTask != null) {
            return;
        }

        // Store values at the time of the login attempt.
        String username = mUsernameField.getText().toString();
        String password = mPasswordField.getText().toString();

        if (!validate(username,password)) {
            return;
        }

        progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Login);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autenticando...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (isLogin){
                            onLoginSuccess();
                        }else {
                            onLoginFailed();
                        }
                        progressDialog.dismiss();
                    }
                }, 3000);

        password = Cifrar.cifrar(password);
        Log.d("password ingres cifrada", password);
        // logica para loguearse
        mAuthTask = new UserLoginTask(username, password);
        mAuthTask.execute((Void) null);
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Integer> {
        private final String mUsername;
        private final String mPassword;

        UserLoginTask(String username, String password) {
            mUsername = username;
            mPassword = password;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            String passwordDb;
            Cursor cursor = mUserDbHelper.getUserByUsername(mUsername);
            if (cursor.getCount() == 0) {
                return 2;
            }
            cursor.moveToPosition(0);
            User user = new User(cursor);
            passwordDb = user.getPassword();
            if (!mPassword.equals(passwordDb)) {
                return 3;
            }
            return 1;
        }

        @Override
        protected void onPostExecute(final Integer success) {
            mAuthTask = null;
            switch (success) {
                case 1:
                    session.setLoggedin(true);
                    isLogin = true;
                    showAppointmentsScreen();
                    break;
                case 2:
                    isLogin = false;
                    showLoginError("Nombre de usuario o contraseña inválidos");
                    break;
                case 3:
                    isLogin = false;
                    showLoginError("Nombre de usuario o contraseña inválidos");
                    break;
            }
        }
    }

    private void showLoginError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();

    }

    private void showAppointmentsScreen() {
        Intent intent = new Intent(getActivity(), WelcomeActivity.class);
        startActivityForResult(intent, WelcomeActivity.REQUEST_WELCOME_USER);
    }

    public void onLoginFailed() {
        // Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        mLoginButton.setEnabled(true);
    }

    public void onLoginSuccess() {
        mLoginButton.setEnabled(true);
        getActivity().finish();
    }

    public boolean validate(String username, String password) {
        boolean valid = true;

        if (username.isEmpty() ) { //validar si es email : !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            mUsernameField.setError("Nombre de usuario requerido");
            valid = false;
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mPasswordField.setError("Campo requerido entre 4 y 10 caracteres alfanumericos");
            valid = false;
        }
        return valid;
    }


}
