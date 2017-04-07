package co.edu.udea.compumovil.gr04_20171.lab3.user.addEdit;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import co.edu.udea.compumovil.gr04_20171.lab3.R;
import co.edu.udea.compumovil.gr04_20171.lab3.user.data.Response;
import co.edu.udea.compumovil.gr04_20171.lab3.user.data.User;
import co.edu.udea.compumovil.gr04_20171.lab3.user.data.UserInterface;
import co.edu.udea.compumovil.gr04_20171.lab3.user.login.LoginActivity;
import co.edu.udea.compumovil.gr04_20171.lab3.utils.Cifrar;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;


public class AddEditUserActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_USER = 1;
    private final static int SELECT_PHOTO = 12345;
    // TODO: Rename and change types of parameters
    private String mUsername;

    private String url;
    private RestAdapter adapter;
    UserInterface userInterface;
    //private UserDbHelper mUserDbHelper;

    private FloatingActionButton mSaveButton;
    private ImageView mAvatarImage;
    private TextInputEditText mUsernameField;
    private TextInputEditText mPasswordField;
    private TextInputEditText mEmailField;
    private TextInputEditText mAgeField;
    private TextInputLayout mUsernameLabel;
    private TextInputLayout mPasswordLabel;
    private TextInputLayout mEmailLabel;
    private TextInputLayout mAgeLabel;

    private String username = null;
    private String password = null;
    private String email = null;
    private String age = null;
    private String pictureUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_user);

        //String userUsername = getIntent().getStringExtra(UsersActivity.EXTRA_USER_ID);
        //String userUsername = null;

        //setTitle(userUsername == null ? "Añadir abogado" : "Editar abogado");

        url = "http://eventsapp-jrios328590030.codeanyapp.com:3000/api";
        adapter = new RestAdapter.Builder().setEndpoint(url).build();
        userInterface = adapter.create(UserInterface.class);

        // Referencias UI
        mSaveButton = (FloatingActionButton) findViewById(R.id.fab);
        mAvatarImage = (ImageView) findViewById(R.id.avatar);
        mUsernameField = (TextInputEditText) findViewById(R.id.et_username);
        mPasswordField = (TextInputEditText) findViewById(R.id.et_password);
        mEmailField = (TextInputEditText) findViewById(R.id.et_email);
        mAgeField = (TextInputEditText) findViewById(R.id.et_age);
        mUsernameLabel = (TextInputLayout) findViewById(R.id.til_username);
        mPasswordLabel = (TextInputLayout) findViewById(R.id.til_password);
        mEmailLabel = (TextInputLayout) findViewById(R.id.til_email);
        mAgeLabel = (TextInputLayout) findViewById(R.id.til_age);


        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditUser();
            }
        });

        mAvatarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });


       // mUserDbHelper = new UserDbHelper(this);

        // Carga de datos
        if (mUsername != null) {
            loadUser();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri selectedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            //cerrar el cursor
            cursor.close();
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
            mAvatarImage.setBackground(bitmapDrawable);
            pictureUri = picturePath;

        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void loadUser() {

    }

    private void addEditUser(){
        boolean error = false;

        username = mUsernameField.getText().toString();
        password = mPasswordField.getText().toString();
        email = mEmailField.getText().toString();

        if (TextUtils.isEmpty(username)) {
            mUsernameLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailLabel.setError(getString(R.string.field_error));
            error = true;
        }

        /*
        Cursor cursorUser = mUserDbHelper.getUserByUsername(username);
        Log.d("cursor", String.valueOf(cursorUser.getCount()));
        if (cursorUser.getCount() > 0){
            showSignupError("El nombre de usuario no esta disponible");
            error = true;
        }*/

        if (error) {
            return;
        }

        Cifrar cifrar = new Cifrar();
        password = cifrar.cifrar(password);
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        Log.d("password cifrado",password);
        Log.d("user",user.getUsername());
        userInterface.createUser(user, new Callback<Response>() {
            @Override
            public void success(Response response, retrofit.client.Response response2) {
                showSignupSuccess("Registro exitoso");
                showScreenLogin();
            }

            @Override
            public void failure(RetrofitError error) {
                String merror = error.getMessage();
                showSignupError(merror);
            }
        });
    }

    private void showSignupSuccess(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void showSignupError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }


    private void showScreenLogin()  {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
