package verona1024.cleverhouse.services;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import verona1024.cleverhouse.activityes.R;
import verona1024.cleverhouse.database.CommonDBHelper;
import verona1024.cleverhouse.database.WidgetInformation;
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
        executorService = Executors.newFixedThreadPool(4);
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

//            todo: оформить


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

    public void getWidgetInformation (){
        new RequestTaskWidget().execute(getString(R.string.httpGetStatus));
    }
    public void turnLightOn (){
        new RequestTaskGet().execute(getString(R.string.httpGetLightOn));
    }
    public void turnLightOff (){
        new RequestTaskGet().execute(getString(R.string.httpGetLightOff));
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

    private class RequestTaskWidget extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            try {
                response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    responseString = out.toString();
                    out.close();
                    Log.w("doInBackground",responseString);
                } else{
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.w("result",result);

            WidgetInformation widgetInformation = new WidgetInformation();

            RemoteViews widgetView = new RemoteViews(getApplicationContext().getPackageName(), R.layout.widget);

            JSONObject json = new JSONObject();
            try {
                json = new JSONObject(result);
                widgetInformation.setWeatness(json.getInt("wetness"));
                widgetInformation.setTemperature(json.getInt("temperature"));
                widgetInformation.setLights(true);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            widgetView.setTextViewText(R.id.textWidgetTemperature, widgetInformation.getTemperature() + " " + getString(R.string.gradus));
            widgetView.setTextViewText(R.id.textWidgetWetness, widgetInformation.getWeatness() + " " + getString(R.string.percent));
            widgetView.setTextViewText(R.id.textWidgetLights, widgetInformation.isLights() ? getString(R.string.on) : getString(R.string.off));


            ComponentName thisWidget = new ComponentName(getApplicationContext(), CleverHouseWidget.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
            manager.updateAppWidget(thisWidget, widgetView);
            Log.w("upadet", "updatet");
            Log.w("upadet",widgetInformation.getTemperature() + "");
            //Do anything with response..
        }
    }

    private class RequestTaskGet extends AsyncTask<String, Boolean, String>{

        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            try {
                response = httpclient.execute(new HttpGet(params[0]));
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    responseString = out.toString();
                    out.close();
                    Log.w("doInBackground",responseString);
                } else{
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;
        }
    }

}
