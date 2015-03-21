package verona1024.cleverhouse.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import verona1024.cleverhouse.activitys.MainActivity;
import verona1024.cleverhouse.widget.CleverHouseWidget;

/**
 * Created by verona1024;
 */
public class ConnectToCleverHouseSystemService extends Service {

    private ExecutorService executorService;
    private BinderSubBinder binder;

    @Override
    public void onCreate() {
        super.onCreate();
        executorService = Executors.newFixedThreadPool(12);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        RunableSubServise runableSubServise = new RunableSubServise(intent);
        executorService.execute(runableSubServise);

        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class RunableSubServise implements Runnable {

        Intent intent;

        RunableSubServise(Intent intent){
            this.intent = intent;
        }

        @Override
        public void run() {

            Intent intent2 = new Intent(MainActivity.BROADCAST_ACTION_MAINACTIVITY);
            intent2.putExtra(MainActivity.RESUME_MESSAGE_NAME,11111111);
            sendBroadcast(intent2);

            Log.w("ididid", "" + intent.getIntExtra("id",777));

            if(intent.hasExtra("id")){
                Intent intent1 = new Intent(CleverHouseWidget.BROADCAST_ACTION_WIDGET);
                intent1.putExtra("id",intent.getIntExtra("id",0));
                intent1.putExtra("temperature", 23);
                intent1.putExtra("wetness", 45);
                intent1.putExtra("lights", true);
                sendBroadcast(intent1);
            }
        }
    }

    class BinderSubBinder extends Binder {
        ConnectToCleverHouseSystemService getServices() {
            return ConnectToCleverHouseSystemService.this;
        }
    }
}
