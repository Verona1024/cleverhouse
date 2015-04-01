package verona1024.cleverhouse.activityes;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import verona1024.cleverhouse.database.CommonDBHelper;
import verona1024.cleverhouse.database.RoomItem;

/**
 * Created by verona1024.
 */
public class Information extends ActionBarActivity {

    private CommonDBHelper commonDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        ListView listViewInformation = (ListView) findViewById(R.id.listViewInformation);
        commonDBHelper = CommonDBHelper.getInstance(this);
        ArrayList<RoomItem> names = commonDBHelper.getListRooms();
        InformationArrayAdapter adapter = new InformationArrayAdapter(getApplicationContext(), R.layout.info_item, names);

        listViewInformation.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    private class InformationArrayAdapter extends ArrayAdapter<RoomItem> {
        private Context context;
        private ArrayList<RoomItem> strings;
        private int textViewResourceId;

        public InformationArrayAdapter(Context context, int textViewResourceId, ArrayList<RoomItem> strings) {
            super(context, textViewResourceId, strings);
            this.context = context;
            this.strings = strings;
            this.textViewResourceId = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(textViewResourceId, parent, false);

            RoomItem item = strings.get(position);
//
            TextView name = (TextView) rowView.findViewById(R.id.textViewInformationName);
            TextView balcon = (TextView) rowView.findViewById(R.id.textViewInformationItemBalcon);
            TextView doors = (TextView) rowView.findViewById(R.id.textViewInformationItemDoors);
            TextView lights = (TextView) rowView.findViewById(R.id.textViewInformationItemLights);
            TextView temperature = (TextView) rowView.findViewById(R.id.textViewInformationItemTemperature);
            TextView wetnes = (TextView) rowView.findViewById(R.id.textViewInformationItemwetnes);
            TextView windows = (TextView) rowView.findViewById(R.id.textViewInformationItemWindows);

            name.setText(item.getName());
            temperature.setText("" + item.getTemperature() + " " + getApplicationContext().getString(R.string.gradus));
            wetnes.setText("" + item.getWeatnes() + " " + getApplicationContext().getString(R.string.percent));

            if(item.getBalcon() != 0){
                balcon.setText(getApplicationContext().getString(R.string.balcon_yes));
            } else {
                balcon.setText(getApplicationContext().getString(R.string.balcon_no));
            }

            if(item.getDoors() != 0){
                doors.setText(getApplicationContext().getString(R.string.doors_yes) + item.getDoors());
            } else {
                doors.setText(getApplicationContext().getString(R.string.doors_no));
            }

            if(item.getLights() != 0){
                lights.setText(getApplicationContext().getString(R.string.lights_yes));
            } else {
                lights.setText(getApplicationContext().getString(R.string.lights_no));
            }

            if(item.getWindows() != 0){
                windows.setText(getApplicationContext().getString(R.string.window_yes) + item.getWindows());
            } else {
                windows.setText(getApplicationContext().getString(R.string.window_no));
            }

            return rowView;
        }
    }
}
