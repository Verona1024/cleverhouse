package verona1024.cleverhouse.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by verona1024 on 22.03.15.
 */
public class CommonDBHelper extends SQLiteOpenHelper {

    private static CommonDBHelper mInstance = null;

    private static final String DATABASE_NAME = "house";
    private static final String DATABASE_TABLE = "rooms";
    private static final int DATABASE_VERSION = 1;
    private final Context mCtx;

//    public CommonDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }

    /**
     * constructor should be private to prevent direct instantiation.
     * make call to static factory method "getInstance()" instead.
     */
    private CommonDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mCtx = context;
    }

    public static synchronized CommonDBHelper getInstance(Context ctx) {
        /**
         * use the application context as suggested by CommonsWare.
         * this will ensure that you dont accidentally leak an Activitys
         * context (see this article for more information:
         * http://android-developers.blogspot.nl/2009/01/avoiding-memory-leaks.html)
         */
        if (mInstance == null) {
            mInstance = new CommonDBHelper(ctx.getApplicationContext());
        }
        return mInstance;
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

    // полный список комнат со всеми полями
    public ArrayList<RoomItem> getListRooms(){
        ArrayList<RoomItem> list = new ArrayList<RoomItem>();

        SQLiteDatabase db = mInstance.getWritableDatabase();
        Cursor c = db.query("house", null, null, null, null, null, null);

//        ArrayList<RoomItem> names = new ArrayList<RoomItem>();

        if (c.moveToFirst()) do {
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

            list.add(item);
        } while (c.moveToNext());

        return list;
    }

    // список названий комнат с id
    public ArrayList<RoomItemNameId> getRoomNames (){
        ArrayList<RoomItemNameId> list = new ArrayList<RoomItemNameId>();

//        SQLiteDatabase db = mInstance.getWritableDatabase();
//        Cursor c = db.rawQuery("SELECT id, name FROM house",null);

//        if (c.moveToFirst()) do {
//            int nameColIndex = c.getColumnIndex("name");
//            int idColIndex = c.getColumnIndex("id");
//
//            RoomItemNameId roomItemNameId;
//            roomItemNameId.ID = c.getInt(idColIndex);
//
//
//            list.add(item);
//        } while (c.moveToNext());

        return list;
    }

    public WidgetInformation getWidgetInformation () {
        WidgetInformation info = new WidgetInformation();

        info.setLights(true);
        info.setTemperature(23);
        info.setWeatness(46);
        return info;
    }

}
