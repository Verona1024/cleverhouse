package verona1024.cleverhouse.widget;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import verona1024.cleverhouse.activitys.R;

/**
 * Created by verona1024 on 19.03.15.
 */
public class WidgetBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        Log.w("broadcastReceiver", "broadcastReceiver");

//        Toast.makeText(context.getApplicationContext(),"WidgetBroadcastReceiver",Toast.LENGTH_LONG).show();

        RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget);

        widgetView.setTextViewText(R.id.textWidgetTemperature,intent.getIntExtra("temperature", 0) + " " + context.getString(R.string.gradus));
        widgetView.setTextViewText(R.id.textWidgetWetness,intent.getIntExtra("wetness", 0) + " " + context.getString(R.string.percent));
        widgetView.setTextViewText(R.id.textWidgetLights, intent.getBooleanExtra("lights", false) ? context.getString(R.string.on) : context.getString(R.string.off));

        AppWidgetManager.getInstance(context).updateAppWidget(intent.getIntExtra("id",0), widgetView);
    }

    public static void setDataToWidget(){

    }
}
