package verona1024.cleverhouse.activitys;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
 * Created by verona1024 on 21.03.15.
 */
public class Information extends ActionBarActivity {

    private CommonDBHelper commonDBHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        ListView listViewInformation = (ListView) findViewById(R.id.listViewInformation);

        commonDBHelper = new CommonDBHelper(getApplicationContext(),"CleverHouse",null,1);
        db = commonDBHelper.getWritableDatabase();

        Cursor c = db.query("house", null, null, null, null, null, null);

        ArrayList<RoomItem> names = new ArrayList<RoomItem>();

        if (c.moveToFirst()) {
            do {
                int nameColIndex = c.getColumnIndex("name");
                int temperatureColIndex = c.getColumnIndex("temperature");
                int wetnessColIndex = c.getColumnIndex("wetness");
                int lightsColIndex = c.getColumnIndex("lights");
                int doorsColIndex = c.getColumnIndex("doors");
                int windowsColIndex = c.getColumnIndex("windows");
                int balconColIndex = c.getColumnIndex("balcon");

                RoomItem item = new RoomItem();
                item.setName(c.getString(nameColIndex));
                item.setBalcon(c.getInt(balconColIndex));
                item.setDoors(c.getInt(doorsColIndex));
                item.setTemperature(c.getInt(temperatureColIndex));
                item.setWeatnes(c.getInt(wetnessColIndex));
                item.setLights(c.getInt(lightsColIndex));
                item.setWindows(c.getInt(windowsColIndex));

                names.add(item);
            } while (c.moveToNext());
        }

        //todo: получение из BD
//        String[] names = {"Все", "Кухня", "Зал", "Детская", "Ванна", "Туалет1", "Туалет2"};

        InformationArrayAdapter adapter = new InformationArrayAdapter(getApplicationContext(), R.layout.info_item, names);

        listViewInformation.setAdapter(adapter);
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
        if (id == R.id.action_settings) {
            return true;
        }
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

    /**
     * Класс адаптера наследуется от RecyclerView.Adapter с указанием класса, который будет хранить ссылки на виджеты элемента списка, т.е. класса, имплементирующего ViewHolder. В нашем случае класс объявлен внутри класса адаптера.
     */
//    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
//
//        private List<Record> records;
//
//        public RecyclerViewAdapter(List<Record> records) {
//            this.records = records;
//        }
//
//        /**
//         * Создание новых View и ViewHolder элемента списка, которые впоследствии могут переиспользоваться.
//         */
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item, viewGroup, false);
//            return new ViewHolder(v);
//        }
//
//        /**
//         * Заполнение виджетов View данными из элемента списка с номером i
//         */
//        @Override
//        public void onBindViewHolder(ViewHolder viewHolder, int i) {
//            Record record = records.get(i);
//            viewHolder.name.setText(record.getName());
//        }
//
//        @Override
//        public int getItemCount() {
//            return records.size();
//        }
//
//        /**
//         * Реализация класса ViewHolder, хранящего ссылки на виджеты.
//         */
//        class ViewHolder extends RecyclerView.ViewHolder {
//            private TextView name;
//            private ImageView icon;
//
//            public ViewHolder(View itemView) {
//                super(itemView);
//                name = (TextView) itemView.findViewById(R.id.recyclerViewItemName);
//                icon = (ImageView) itemView.findViewById(R.id.recyclerViewItemIcon);
//            }
//        }
//    }
}
