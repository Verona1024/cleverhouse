package verona1024.cleverhouse.widget;

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
import android.widget.Toast;

import verona1024.cleverhouse.activitys.MainActivity;
import verona1024.cleverhouse.activitys.R;
import verona1024.cleverhouse.database.CommonDBHelper;
import verona1024.cleverhouse.database.WidgetInformation;
import verona1024.cleverhouse.services.ConnectToCleverHouseSystemService;

/**
 * Created by verona1024.
 */
public class CleverHouseWidget extends AppWidgetProvider {

    public final static String BROADCAST_ACTION_WIDGET = "com.verona1024.cleverhouse.widgetbroacastaction";

    ConnectToCleverHouseSystemService mService;
    boolean mBound = false;
    WidgetServiceBinding widgetServiceBinding;

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
//        Toast.makeText(context.getApplicationContext(),"smt",Toast.LENGTH_LONG).show();

//        CommonDBHelper db = CommonDBHelper.getInstance(context);
//
//        WidgetInformation wInfo = db.getWidgetInformation();
//
//        RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget);
//
//        widgetView.setTextViewText(R.id.textWidgetTemperature, wInfo.getTemperature() + " " + context.getString(R.string.gradus));
//        widgetView.setTextViewText(R.id.textWidgetWetness, wInfo.getWeatness() + " " + context.getString(R.string.percent));
//        widgetView.setTextViewText(R.id.textWidgetLights, wInfo.isLights() ? context.getString(R.string.on) : context.getString(R.string.off));
//        Toast.makeText(context.getApplicationContext(),"" + wInfo.isLights(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int i : appWidgetIds){

            Toast.makeText(context.getApplicationContext(),"update",Toast.LENGTH_LONG).show();

            CommonDBHelper db = CommonDBHelper.getInstance(context);

            WidgetInformation wInfo = db.getWidgetInformation();

            RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget);

            widgetView.setTextViewText(R.id.textWidgetTemperature, wInfo.getTemperature() + " " + context.getString(R.string.gradus));
            widgetView.setTextViewText(R.id.textWidgetWetness, wInfo.getWeatness() + " " + context.getString(R.string.percent));
            widgetView.setTextViewText(R.id.textWidgetLights, wInfo.isLights() ? context.getString(R.string.on) : context.getString(R.string.off));
            Toast.makeText(context.getApplicationContext(),"" + wInfo.isLights(),Toast.LENGTH_LONG).show();

//            Intent intent1 = new Intent(context, ConnectToCleverHouseSystemService.class);
//            intent1.putExtra("id", i);
//            context.getApplicationContext().startService(intent1);
//            Log.w("UPDATEWIDGET","" + i);
//
//            Intent intent1 = new Intent(context.getApplicationContext(),ConnectToCleverHouseSystemService.class);
//            widgetServiceBinding = new WidgetServiceBinding(i);
//            context.getApplicationContext().bindService(intent1, widgetServiceBinding, Context.BIND_AUTO_CREATE);

//            appWidgetManager.updateWidget(context, appWidgetManager, i);
            appWidgetManager.updateAppWidget(i,widgetView);


//            AppWidgetManager.getInstance(context).updateAppWidget(intent.getIntExtra("id",0), widgetView);
        }
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {
        int id;

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            ConnectToCleverHouseSystemService.LocalBinder binder = (ConnectToCleverHouseSystemService.LocalBinder) service;
            mService = binder.getService();
            mService.broacastChanges(24);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
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
            context.getApplicationContext().unbindService(widgetServiceBinding);
            mBound = false;
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
