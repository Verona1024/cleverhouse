package verona1024.cleverhouse.activitys;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import verona1024.cleverhouse.services.ConnectToCleverHouseSystemService;
import verona1024.cleverhouse.widget.WidgetBroadcastReceiver;


public class MainActivity extends ActionBarActivity {

    ConnectToCleverHouseSystemService mService;
    boolean mBound = false;

    private ImageButton imageButtonLights;
    private ImageButton imageButtonInformation;
    private ImageButton imageButtonSecurity;
    private ImageButton buttonPeople;
    private ImageButton buttonWindows;
    private ImageButton buttonAlarms;

//    private BroadcastReceiver broadcastReceiver;

    public final static String BROADCAST_ACTION_MAINACTIVITY = "com.verona1024.cleverhouse.servicebackbroadcast";
    public final static String BROADCAST_ACTION_WIDGET = "com.verona1024.cleverhouse.widgetbroacastaction";
    public final static String RESUME_MESSAGE_NAME = "RESUME_MESSAGE_NAME";
    public final static int RESUME_MESSAGE = 1;

    private boolean bounder = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

//        Log.w("onCreate","onCreate");

        imageButtonLights = (ImageButton) findViewById(R.id.imageButtonLights);
        imageButtonInformation = (ImageButton) findViewById(R.id.imageButtonInformation);
        imageButtonSecurity = (ImageButton) findViewById(R.id.imageButtonSecurity);
//        buttonPeople = (ImageButton) findViewById(R.id.buttonPeople);
//        buttonWindows = (ImageButton) findViewById(R.id.buttonWindows);
//        buttonAlarms = (ImageButton) findViewById(R.id.buttonAlarms);

        imageButtonLights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Lights.class);
                startActivity(intent);
                MainActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        imageButtonInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Information.class);
                startActivity(intent);
                MainActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        imageButtonSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.drawable.ic_launcher)
                                .setContentTitle(getApplication().getString(R.string.app_name))
                                .setContentText("Охрана")
                                .setAutoCancel(true);


                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());

                stackBuilder.addParentStack(MainActivity.class);

                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                int mId = 2;
                mNotificationManager.notify(mId, mBuilder.build());
            }
        });

        IntentFilter intentFilter1 = new IntentFilter(BROADCAST_ACTION_WIDGET);
        registerReceiver(new WidgetBroadcastReceiver(), intentFilter1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(getApplicationContext(),ConnectToCleverHouseSystemService.class);
        bindService(intent,mConnection,Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Log.w("onDestroy","onDestroy");
//        unregisterReceiver(broadcastReceiver);
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            Log.w("---------------------","----------------------");
            ConnectToCleverHouseSystemService.LocalBinder binder = (ConnectToCleverHouseSystemService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
}
