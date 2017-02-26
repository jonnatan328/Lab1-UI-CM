package co.edu.udea.compumovil.gr04_20171.lab1;

import android.content.Intent;
import android.graphics.Color;
import android.icu.util.RangeValueIterator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PersonalInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        final EditText textNombres = (EditText)findViewById(R.id.EditTextNombres);
        final EditText textApellidos = (EditText)findViewById(R.id.EditTextApellidos);
        final RadioGroup genero = (RadioGroup) findViewById(R.id.RadioGroupOpcSexo);
        final Spinner escolaridad = (Spinner) findViewById(R.id.SpinnerEscolaridad);
        final TextView fechaNac = (TextView) findViewById(R.id.TextViewFechaNac);



        //Event click for next button
        Button boton = (Button) findViewById(R.id.botonSigPerso);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valueNombres = textNombres.getText().toString();
                String valueApellidos = textApellidos.getText().toString();
                if(valueNombres.length() == 0){
                    textNombres.setError("Requerido");//R.string.errorNombre
                }
                else if(valueApellidos.length() == 0){
                    textApellidos.setError("Requerido");
                }
                else {
                    int id = genero.getCheckedRadioButtonId();
                    RadioButton sexo = (RadioButton) findViewById(id);
                    ArrayList<String> data = new ArrayList();
                    data.add(valueNombres);
                    data.add(valueApellidos);
                    data.add((String) sexo.getText());
                    data.add((String) fechaNac.getText());
                    data.add((String) escolaridad.getSelectedItem());
                    Intent intent = new Intent(PersonalInfo.this, ContactInfo.class);
                    intent.putExtra("personalInfo", data);
                    startActivity(intent);
                }


            }
        });


        //Create spinner with the grades of .....
        Spinner spinner = (Spinner) findViewById(R.id.SpinnerEscolaridad);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gradosEscolaridad, android.R.layout.simple_spinner_item);


        /*final List<String> gradosEscolaridadList = new ArrayList<>(R.array.gradosEscolaridad);
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_item,gradosEscolaridadList){
            @Override
            public boolean isEnabled(int position){
                return position != 0;
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };*/
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getApplicationContext(), selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

}
