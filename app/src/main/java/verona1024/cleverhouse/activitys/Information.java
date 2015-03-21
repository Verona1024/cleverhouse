package verona1024.cleverhouse.activitys;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by verona1024 on 21.03.15.
 */
public class Information extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
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
