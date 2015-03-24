package verona1024.cleverhouse.services;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import verona1024.cleverhouse.database.CommonDBHelper;
import verona1024.cleverhouse.widget.CleverHouseWidget;

/**
 * Created by verona1024;
 */
public class ConnectToCleverHouseSystemService extends Service {

    private ExecutorService executorService;
//    private BinderSubBinder binder;
    private final IBinder binder = new LocalBinder();
    private CommonDBHelper commonDBHelper;
    private SQLiteDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        executorService = Executors.newFixedThreadPool(12);
        commonDBHelper = CommonDBHelper.getInstance(this);
        db = commonDBHelper.getWritableDatabase();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        RunableSubServise runableSubServise = new RunableSubServise(intent);
        executorService.execute(runableSubServise);

        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        commonDBHelper.close();
    }

    class RunableSubServise implements Runnable {

        Intent intent;

        RunableSubServise(Intent intent){
            this.intent = intent;
        }

        @Override
        public void run() {

//            todo: get from http

            Log.w("intent.hasExtra(\"id\")","" + intent.hasExtra("id"));

            db.delete("house",null,null);

//            Cursor c = db.query("house", null, null, null, null, null, null);
            ContentValues contentValues = new ContentValues();
            for (int i = 0 ; i <=20 ; i++){
                contentValues.put("name","Комната " + i);
                contentValues.put("temperature", i*7);
                contentValues.put("wetness", i*8);
                contentValues.put("lights", i);
                contentValues.put("windows", i);
                contentValues.put("doors", i);
                contentValues.put("balcon", i);

                long rowID = db.insert("house", null, contentValues);
            }

//            Intent intent2 = new Intent(MainActivity.BROADCAST_ACTION_MAINACTIVITY);
//            intent2.putExtra(MainActivity.RESUME_MESSAGE_NAME,11111111);
//            sendBroadcast(intent2);

            if(intent.hasExtra("id")){
                Intent intent1 = new Intent(CleverHouseWidget.BROADCAST_ACTION_WIDGET);
                intent1.putExtra("id",intent.getIntExtra("id",0));
                intent1.putExtra("temperature", 23);
                intent1.putExtra("wetness", 45);
                intent1.putExtra("lights", true);
                sendBroadcast(intent1);
            }
//            commonDBHelper.close();
        }


    }

    public void broacastChanges (int id) {
        Intent intent1 = new Intent(CleverHouseWidget.BROADCAST_ACTION_WIDGET);
        intent1.putExtra("id",id);
        intent1.putExtra("temperature", 23);
        intent1.putExtra("wetness", 45);
        intent1.putExtra("lights", true);
        sendBroadcast(intent1);
    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public ConnectToCleverHouseSystemService getService() {
            // Return this instance of LocalService so clients can call public methods
            return ConnectToCleverHouseSystemService.this;
        }
    }

//    class BinderSubBinder extends Binder {
//        ConnectToCleverHouseSystemService getServices() {
//            return ConnectToCleverHouseSystemService.this;
//        }
//    }
}
