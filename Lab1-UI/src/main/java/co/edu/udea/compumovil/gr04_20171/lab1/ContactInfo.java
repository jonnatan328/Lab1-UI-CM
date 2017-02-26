package co.edu.udea.compumovil.gr04_20171.lab1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        //Autocomplete for the countries
        // Get a reference to the AutoCompleteTextView in the layout
        AutoCompleteTextView textViewCountries = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextPais);
        // Get the string array
        String[] countries = getResources().getStringArray(R.array.countries_array);
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapterCountries = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, countries);
        textViewCountries.setAdapter(adapterCountries);


        //Autocomplete for the cities
        // Get a reference to the AutoCompleteTextView in the layout
        AutoCompleteTextView textViewCities = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextCity);
        // Get the string array
        String[] cities = getResources().getStringArray(R.array.cities_array);
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapterCities = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, cities);
        textViewCities.setAdapter(adapterCities);

        //get the personal data of the first activity
        Bundle extras = getIntent().getExtras();
        final ArrayList<String> personalData = extras.getStringArrayList("personalInfo");

        //get views of contact
        final EditText telefono = (EditText) findViewById(R.id.editTextTelefono);
        final EditText correo = (EditText) findViewById(R.id.editTextCorreo);
        final EditText direccion = (EditText) findViewById(R.id.editTextAddress);
        final AutoCompleteTextView pais = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextPais);
        final AutoCompleteTextView ciudad = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextCity);

        // Notify an element of array
        Toast.makeText
                (getApplicationContext(), personalData.get(1), Toast.LENGTH_SHORT)
                .show();

        //Event click for next button
        Button boton = (Button) findViewById(R.id.buttonSiguiente);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get data of contact
                String valorTelefono = telefono.getText().toString();
                String valorCorreo = correo.getText().toString();
                String valorDireccion = direccion.getText().toString();
                String valorPais = pais.getText().toString();
                String valorCiudad = ciudad.getText().toString();

                //validate each value of contact
                if(valorTelefono.length() == 0){
                    telefono.setError("Requerido");//R.string.errorNombre
                }
                else if(valorCorreo.length() == 0){
                    correo.setError("Requerido");
                }else if(valorDireccion.length() == 0){
                    direccion.setError("Requerido");
                }
                else if(valorPais.length() == 0){
                    pais.setError("Requerido");
                }
                else if(valorCiudad.length() == 0){
                    ciudad.setError("Requerido");
                }else{
                    ArrayList<String> dataContact = new ArrayList();
                    dataContact.add(valorTelefono);
                    dataContact.add(valorCorreo);
                    dataContact.add(valorPais);
                    dataContact.add(valorCiudad);
                    dataContact.add(valorDireccion);
                    Intent intent = new Intent(ContactInfo.this, OtherInfo.class);
                    intent.putExtra("personalInfo",personalData);
                    intent.putExtra("contactInfo",dataContact);
                    startActivity(intent);
                }


            }
        });

    }


}
