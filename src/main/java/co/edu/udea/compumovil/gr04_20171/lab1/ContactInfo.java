package co.edu.udea.compumovil.gr04_20171.lab1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

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

    }


}
