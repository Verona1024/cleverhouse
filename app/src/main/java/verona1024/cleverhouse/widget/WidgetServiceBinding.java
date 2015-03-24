package verona1024.cleverhouse.widget;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import verona1024.cleverhouse.services.ConnectToCleverHouseSystemService;

/**
 * Created by verona1024 on 24.03.15.
 */
public class WidgetServiceBinding implements ServiceConnection {
    ConnectToCleverHouseSystemService mService;
    int id;

    public WidgetServiceBinding(int id){
        this.id = id;
    }

    @Override
    public void onServiceConnected(ComponentName className, IBinder service) {
        // We've bound to LocalService, cast the IBinder and get LocalService instance
        ConnectToCleverHouseSystemService.LocalBinder binder = (ConnectToCleverHouseSystemService.LocalBinder) service;
        mService = binder.getService();
        mService.broacastChanges(id);
//        mBound = true;
    }

    @Override
    public void onServiceDisconnected(ComponentName arg0) {
//        mBound = false;
    }
}
