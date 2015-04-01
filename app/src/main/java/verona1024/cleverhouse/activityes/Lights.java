package verona1024.cleverhouse.activityes;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by verona1024 on 20.03.15.
 */
public class Lights extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lights);

        ListView listViewLights = (ListView) findViewById(R.id.listViewLights);

        //todo: получение из BD
        String[] names = {"Все", "Кухня", "Зал", "Детская", "Ванна", "Туалет1", "Туалет2"};

        LightsArrayAdapter adapter = new LightsArrayAdapter(getApplicationContext(), R.layout.lights_item, names);

        listViewLights.setAdapter(adapter);



//        ContextThemeWrapper ctw = ContextThemeWrapper(getActionBar(), R.style.Color1SwitchStyle);
//        SwitchCompat sc = new SwitchCompat(ctw);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private class LightsArrayAdapter extends ArrayAdapter<String>{
        private Context context;
        private String[] strings;
        private int textViewResourceId;

        public LightsArrayAdapter(Context context, int textViewResourceId, String[] strings) {
            super(context, textViewResourceId, strings);
            this.context = context;
            this.strings = strings;
            this.textViewResourceId = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(textViewResourceId, parent, false);

            LinearLayout linearLayout = (LinearLayout) rowView.findViewById(R.id.linearLayoutLightsItem);

            ContextThemeWrapper ctw = new ContextThemeWrapper(inflater.getContext(), R.style.SwitchCompatStyle);
            SwitchCompat sc = new SwitchCompat(ctw);
            sc.setBackground(null);


            TextView switcher = (TextView) rowView.findViewById(R.id.textViewLightsItem);
            switcher.setText(strings[position]);

            linearLayout.addView(sc);

            return rowView;
        }
    }
}
