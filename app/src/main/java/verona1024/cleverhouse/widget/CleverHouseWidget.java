package verona1024.cleverhouse.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import verona1024.cleverhouse.activitys.MainActivity;
import verona1024.cleverhouse.activitys.R;
import verona1024.cleverhouse.services.ConnectToCleverHouseSystemService;

/**
 * Created by verona1024.
 */
public class CleverHouseWidget extends AppWidgetProvider {

    public final static String BROADCAST_ACTION_WIDGET = "com.verona1024.cleverhouse.widgetbroacastaction";
    public final static String UPDATE_ALL_WIDGETS = "com.verona1024.cleverhouse.update_all_widgets";

    ConnectToCleverHouseSystemService mService;
    private boolean mBound = false;
//    WidgetServiceBinding widgetServiceBinding;

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Intent intent = new Intent(context, CleverHouseWidget.class);
        intent.setAction(UPDATE_ALL_WIDGETS);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(),60000, pIntent);
        Intent intent1 = new Intent(context,ConnectToCleverHouseSystemService.class);
        context.getApplicationContext().bindService(intent1, myConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int i : appWidgetIds){


//
//            RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget);
//
//            widgetView.setTextViewText(R.id.textWidgetTemperature, widgetInformation.getTemperature() + " " + context.getString(R.string.gradus));
//            widgetView.setTextViewText(R.id.textWidgetWetness, widgetInformation.getWeatness() + " " + context.getString(R.string.percent));
//            widgetView.setTextViewText(R.id.textWidgetLights, widgetInformation.isLights() ? context.getString(R.string.on) : context.getString(R.string.off));

            Log.w("onUpdate","3");
            updateWidget(context, appWidgetManager, i);
//            appWidgetManager.updateAppWidget(i,widgetView);
            Log.w("onUpdate","4");
        }
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection myConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.w("onServiceConnected","1");
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            ConnectToCleverHouseSystemService.LocalBinder binder = (ConnectToCleverHouseSystemService.LocalBinder) service;
            mService = binder.getService();
            mService.getWidgetInformation();

//            mService.broacastChanges(24);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
            Log.w("onServiceConnected","2");
        }
    };

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.w("DISABLEDWIDGET","dsd");
        if (mBound) {
            context.getApplicationContext().unbindService(myConnection);
            mBound = false;
        }
        Intent intent = new Intent(context, CleverHouseWidget.class);
        intent.setAction(UPDATE_ALL_WIDGETS);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equalsIgnoreCase(UPDATE_ALL_WIDGETS)) {
            ComponentName thisAppWidget = new ComponentName(context.getPackageName(), getClass().getName());
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int ids[] = appWidgetManager.getAppWidgetIds(thisAppWidget);
            for (int appWidgetID : ids) {
                updateWidget(context, appWidgetManager, appWidgetID);
            }
        }
    }

    public static void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){

        RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget);

        // Обработка нажатия кнопки. Открывает Приложение
        Intent intent = new Intent(context, MainActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        PendingIntent pIntent = PendingIntent.getActivity(context, appWidgetId, intent, 0);
        widgetView.setOnClickPendingIntent(R.id.widgetButton,pIntent);

        //Обновление виджета
        appWidgetManager.updateAppWidget(appWidgetId, widgetView);
    }
}
