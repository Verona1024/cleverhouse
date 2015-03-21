package verona1024.cleverhouse.activitys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import verona1024.cleverhouse.services.ConnectToCleverHouseSystemService;
import verona1024.cleverhouse.widget.WidgetBroadcastReceiver;


public class MainActivity extends ActionBarActivity {

    private ImageButton imageButtonLights;
    private ImageButton imageButtonInformation;
    private ImageButton buttonDoors;
    private ImageButton buttonPeople;
    private ImageButton buttonWindows;
    private ImageButton buttonAlarms;

    private BroadcastReceiver broadcastReceiver;

    public final static String BROADCAST_ACTION_MAINACTIVITY = "com.verona1024.cleverhouse.servicebackbroadcast";
    public final static String BROADCAST_ACTION_WIDGET = "com.verona1024.cleverhouse.widgetbroacastaction";
    public final static String RESUME_MESSAGE_NAME = "RESUME_MESSAGE_NAME";
    public final static int RESUME_MESSAGE = 1;

    private boolean bounder = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Log.w("onCreate","onCreate");

        imageButtonLights = (ImageButton) findViewById(R.id.imageButtonLights);
        imageButtonInformation = (ImageButton) findViewById(R.id.imageButtonInformation);
//        buttonDoors = (ImageButton) findViewById(R.id.buttonDoors);
//        buttonPeople = (ImageButton) findViewById(R.id.buttonPeople);
//        buttonWindows = (ImageButton) findViewById(R.id.buttonWindows);
//        buttonAlarms = (ImageButton) findViewById(R.id.buttonAlarms);

        imageButtonLights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Lights.class);
                startActivity(intent);
            }
        });
        imageButtonInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Information.class);
                startActivity(intent);
            }
        });

        startService(new Intent(this, ConnectToCleverHouseSystemService.class));

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.w("" + RESUME_MESSAGE_NAME, "" + intent.getIntExtra(RESUME_MESSAGE_NAME, 0));
            }
        };

        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION_MAINACTIVITY);
        registerReceiver(broadcastReceiver,intentFilter);

        IntentFilter intentFilter1 = new IntentFilter(BROADCAST_ACTION_WIDGET);
        registerReceiver(new WidgetBroadcastReceiver(), intentFilter1);
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
        Log.w("onDestroy","onDestroy");
        unregisterReceiver(broadcastReceiver);
    }
}
