package verona1024.cleverhouse.activitys;

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

/**
 * Created by verona1024 on 21.03.15.
 */
public class Information extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        ListView listViewInformation = (ListView) findViewById(R.id.listViewInformation);

        //todo: получение из BD
        String[] names = {"Все", "Кухня", "Зал", "Детская", "Ванна", "Туалет1", "Туалет2"};

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

    private class InformationArrayAdapter extends ArrayAdapter<String> {
        private Context context;
        private String[] strings;
        private int textViewResourceId;

        public InformationArrayAdapter(Context context, int textViewResourceId, String[] strings) {
            super(context, textViewResourceId, strings);
            this.context = context;
            this.strings = strings;
            this.textViewResourceId = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(textViewResourceId, parent, false);

            TextView switcher = (TextView) rowView.findViewById(R.id.info_text_bla);

            switcher.setText(strings[position]);

            return rowView;
        }
    }

    private class Item {
        private String name;
        private int temperature;
        private int weatnes;

        public Item(String name, int temperature, int weatnes){
            this.name = name;
            this.temperature = temperature;
            this.weatnes = weatnes;
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
