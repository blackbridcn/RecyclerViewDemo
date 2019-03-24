package blackbird.com.recyclerviewdemo.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class appService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public appService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
