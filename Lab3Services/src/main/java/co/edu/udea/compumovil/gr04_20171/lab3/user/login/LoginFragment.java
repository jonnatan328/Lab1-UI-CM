package co.edu.udea.compumovil.gr04_20171.lab3.user.login;


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

import co.edu.udea.compumovil.gr04_20171.lab3.HomeActivity;
import co.edu.udea.compumovil.gr04_20171.lab3.R;
import co.edu.udea.compumovil.gr04_20171.lab3.Session;
//import co.edu.udea.compumovil.gr04_20171.lab3.WelcomeActivity;
import co.edu.udea.compumovil.gr04_20171.lab3.user.addEdit.AddEditUserActivity;
import co.edu.udea.compumovil.gr04_20171.lab3.user.data.Affiliate;
import co.edu.udea.compumovil.gr04_20171.lab3.user.data.LoginBody;
import co.edu.udea.compumovil.gr04_20171.lab3.user.data.UserInterface;
import co.edu.udea.compumovil.gr04_20171.lab3.utils.Cifrar;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

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

   // private UserLoginTask mAuthTask = null;
    private Session session;

    private String url;
    private RestAdapter adapter;
    UserInterface userInterface;
    //private UserDbHelper mUserDbHelper;

    private Button mLoginButton;
    private TextView mRegister;
    private TextInputEditText mEmailField;
    private TextInputEditText mPasswordField;
    private ProgressDialog progressDialog;
    private boolean isLogin;
    private LoginBody loginBody;

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

        url = "http://eventsapp-jrios328590030.codeanyapp.com:3000/api/Users";
        adapter = new RestAdapter.Builder().setEndpoint(url).build();
        userInterface = adapter.create(UserInterface.class);

        // Instancia de helper
        //mUserDbHelper = new UserDbHelper(getActivity());


        //instancia de la session
        session = new Session(getActivity());
        // Referencias UI
        mLoginButton = (Button) root.findViewById(R.id.loginButton);
        mRegister = (TextView) root.findViewById(R.id.registerButton);
        mEmailField = (TextInputEditText) root.findViewById(R.id.et_email_Login);
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
            startActivity(new Intent(getActivity(),HomeActivity.class));
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

        boolean cancel = false;
        View focusView = null;

        // Store values at the time of the login attempt.
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        if (!validate(email,password)) {
            cancel = true;
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

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            password = Cifrar.cifrar(password);
            Log.d("password ingres cifrada", password);
            // logica para loguearse
            loginBody.setEmail(email);
            loginBody.setPassword(password);
            userInterface.login(loginBody, new Callback<Affiliate>() {
                @Override
                public void success(Affiliate affiliate, Response response) {
                    session.setLoggedin(true);
                    session.setUserId(affiliate.getUserId().toString());
                    isLogin = true;
                    showAppointmentsScreen();
                }

                @Override
                public void failure(RetrofitError error) {
                    isLogin = false;
                    showLoginError(error.getMessage());
                }
            });

        }
    }

    private void showLoginError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();

    }

    private void showAppointmentsScreen() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
    }

    public void onLoginFailed() {
        // Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        mLoginButton.setEnabled(true);
    }

    public void onLoginSuccess() {
        mLoginButton.setEnabled(true);
        getActivity().finish();
    }

    public boolean validate(String email, String password) {
        boolean valid = true;

        if (email.isEmpty() ) { //validar si es email : !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            mEmailField.setError("Nombre de usuario requerido");
            valid = false;
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mPasswordField.setError("Campo requerido entre 4 y 10 caracteres alfanumericos");
            valid = false;
        }
        return valid;
    }


}
