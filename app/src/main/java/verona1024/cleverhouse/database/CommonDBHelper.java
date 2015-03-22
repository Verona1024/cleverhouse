package verona1024.cleverhouse.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by verona1024 on 22.03.15.
 */
public class CommonDBHelper extends SQLiteOpenHelper {

    public CommonDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table house ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "temperature integer,"
                + "wetness integer,"
                + "lights integer,"
                + "doors integer,"
                + "windows integer,"
                + "balcon integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean openDB() {
        return true;
    }

    public ArrayList<RoomItem> getListRooms(){
        ArrayList<RoomItem> list = new ArrayList<RoomItem>();
        return list;
    }
}
