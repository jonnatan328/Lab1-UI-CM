package co.edu.udea.compumovil.gr04_20171.lab1;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ListActivity;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class OtherInfo extends AppCompatActivity {

    private ListView listView;
    //private ArrayAdapter<String> adapter;
    private ArrayAdapter<Pasatiempo> adapter;
    //private ArrayList<String> arrayString;
    private ArrayList<Pasatiempo> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_info);
        listView = (ListView)findViewById(R.id.listView);
        setLisData();
        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayString);
        adapter = new AdaptadorPasatiempos(this, R.layout.item_list_view, arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(onItemClickListener());
    }

    private AdapterView.OnItemClickListener onItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*final Dialog dialog = new Dialog(OtherInfo.this);
                dialog.setContentView(R.layout.layout_dialog);
                dialog.setTitle("Movie details");

                TextView name = (TextView) dialog.findViewById(R.id.v);
                TextView country = (TextView) dialog.findViewById(R.id.country);
                TextView starRate = (TextView) dialog.findViewById(R.id.rate);

                Movie movie = (Movie) parent.getAdapter().getItem(position);
                name.setText("Movie name: " + movie.getName());
                country.setText("Producing country: " + movie.getCountry());
                starRate.setText("Your rate: " + movie.getRatingStar());

                dialog.show();*/
            }
        };
    }

    private void setLisData() {
        //Array que asociaremos al adaptador

        /*arrayString = new ArrayList<>();
        arrayString.add("Elemento 1");
        arrayString.add("Elemento 2");
        arrayString.add("Elemento 3");*/
        arrayList = new ArrayList<>();
        arrayList.add(new Pasatiempo(true, "Leer", 2));
        arrayList.add(new Pasatiempo(false, "Ver TV", 0));
        arrayList.add(new Pasatiempo(true, "Bailar", 4));
        arrayList.add(new Pasatiempo(false, "Cantar", 0));
        arrayList.add(new Pasatiempo(false, "Nadar", 0));
    }
}

