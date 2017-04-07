package co.edu.udea.compumovil.gr04_20171.lab3.user.profile;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import co.edu.udea.compumovil.gr04_20171.lab3.R;
import co.edu.udea.compumovil.gr04_20171.lab3.Session;
import co.edu.udea.compumovil.gr04_20171.lab3.user.data.User;
import co.edu.udea.compumovil.gr04_20171.lab3.user.data.UserDbHelper;


public class ProfileFragment extends Fragment {

    TextView profileUsername;
    TextView profileEmail;
    TextView profilePassword;
    TextView profileAge;
    private Session session;

    private UserDbHelper userDbHelper;

    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //instancia de la session
        session = new Session(getActivity());
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profileUsername = (TextView) view.findViewById (R.id.profile_username);
        profileEmail = (TextView) view.findViewById (R.id.profile_email);
        profilePassword = (TextView) view.findViewById (R.id.profile_password);
        profileAge = (TextView) view.findViewById (R.id.profile_age);
        userDbHelper = new UserDbHelper(getActivity());
        Log.d("username", session.getUsername());
        Cursor cursorUser = userDbHelper.getUserByUsername(session.getUsername());
        cursorUser.moveToPosition(0);
        User user = null;
        user = new User(cursorUser);
        loadUserData(user);
        return view;
    }

    public void loadUserData(User user){
        profileUsername.setText("Nombre de usuario: " + user.getUsername().toString());
        profileEmail.setText("Correo: " + user.getEmail().toString());
        profileAge.setText("Edad: " + user.getAge().toString());
    }
}
