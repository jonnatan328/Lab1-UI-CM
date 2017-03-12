package co.edu.udea.compumovil.gr04_20171.lab1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OtherInfo extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_info);
        listView = (ListView) findViewById(R.id.listView);
        String[] mTestArray;
        // creamos el listado
        mTestArray = getResources().getStringArray(R.array.listaPasatiempos);
        arrayList = new ArrayList<String>(Arrays.asList(mTestArray));
        // creamos adaptador
        adapter = new AdaptadorPasatiempos(this, (ArrayList<String>) arrayList);

        // establecemos el adaptador en la lista
        listView.setAdapter(adapter);

        //get data of personal and contact activity
        Bundle extras = getIntent().getExtras();
        final ArrayList<String> personalData = extras.getStringArrayList("personalInfo");
        final ArrayList<String> contactData = extras.getStringArrayList("contactInfo");

        final TextView textviewMostrar = (TextView) findViewById(R.id.informacionCompleta);

        Button boton = (Button) findViewById(R.id.botonMostrar);
        boton.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                int personalInfoSize = personalData.size();
                textviewMostrar.append(getResources().getString(R.string.title_activity_personal_info));
                textviewMostrar.append("\n");
                for(int i = 0; i < personalInfoSize; i++) {
                    textviewMostrar.append(personalData.get(i));
                    textviewMostrar.append("\n");
                }
                int contactSize = personalData.size();
                textviewMostrar.append("\n");
                textviewMostrar.append(getResources().getString(R.string.title_activity_contact_info));
                textviewMostrar.append("\n");
                for(int i = 0; i < contactSize; i++) {
                    textviewMostrar.append(contactData.get(i));
                    textviewMostrar.append("\n");
                }
                int otherInfoSize = arrayList.size();
                textviewMostrar.append("\n");
                textviewMostrar.append(getResources().getString(R.string.title_activity_other_info));
                textviewMostrar.append("\n");
                for(int i = 0; i < otherInfoSize; i++) {
                    View fila = listView.getChildAt(i);
                    CheckBox checkBox = (CheckBox) fila.findViewById(R.id.checkboxpasatiempos);
                    if(checkBox.isChecked()){
                        textviewMostrar.append(arrayList.get(i) + " ");
                        RatingBar ratingBar = (RatingBar) fila.findViewById(R.id.ratingBar);
                        textviewMostrar.append(String.valueOf(ratingBar.getRating()));
                        textviewMostrar.append("\n");
                    }

                }

            }
        });

    }


}

