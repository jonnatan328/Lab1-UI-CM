package co.edu.udea.compumovil.gr04_20171.lab1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jonnatan on 15/02/17.
 */
public class AdaptadorPasatiempos extends ArrayAdapter<String> {

    private final Context context;
    ArrayList<String> nombresPasatiempos;

    public AdaptadorPasatiempos(Context context, ArrayList<String> nombresPasatiempos) {
        super(context, R.layout.item_list_view, nombresPasatiempos);
        this.context = context;
        this.nombresPasatiempos = nombresPasatiempos;
    }

    static class ViewHolder {
        protected TextView nameTextView;
    }

    public int getCount() {
        return nombresPasatiempos.size();
    }

    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflamos nuestra vista con el layout
        View view = null;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.item_list_view, parent, false);
        final ViewHolder viewHolder = new ViewHolder();

        // *** instanciamos a los recursos
        viewHolder.nameTextView = (TextView) view.findViewById(R.id.nombrePasatiempo);

        final CheckBox checkBoxPasa = (CheckBox) view.findViewById(R.id.checkboxpasatiempos);
        checkBoxPasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout = (LinearLayout) checkBoxPasa.getParent();
                RatingBar ratingBar = (RatingBar) linearLayout.findViewById(R.id.ratingBar);
                final boolean isChecked = checkBoxPasa.isChecked();
                if(isChecked){
                    ratingBar.setIsIndicator(false);
                }
                else{
                    ratingBar.setIsIndicator(true);
                    ratingBar.setRating(0);
                }
            }
        });

        // importante!!! establecemos el mensaje
        viewHolder.nameTextView.setText(nombresPasatiempos.get(position));

        return view;
    }

}
