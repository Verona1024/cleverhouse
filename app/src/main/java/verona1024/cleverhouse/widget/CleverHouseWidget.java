package verona1024.cleverhouse.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import verona1024.cleverhouse.activitys.MainActivity;
import verona1024.cleverhouse.activitys.R;
import verona1024.cleverhouse.services.ConnectToCleverHouseSystemService;

/**
 * Created by verona1024.
 */
public class CleverHouseWidget extends AppWidgetProvider {

    public final static String BROADCAST_ACTION_WIDGET = "com.verona1024.cleverhouse.widgetbroacastaction";

    private BroadcastReceiver broadcastReceiver;

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int i : appWidgetIds){

            Intent intent1 = new Intent(context, ConnectToCleverHouseSystemService.class);
            intent1.putExtra("id", i);
            context.getApplicationContext().startService(intent1);

            updateWidget(context, appWidgetManager, i);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
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
